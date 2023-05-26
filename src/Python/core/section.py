import math
import sys

from core.line import RawLine
from core.mnemonics import DIRECTIVE, OPCODE
from core.object_code import ObjectCode
from core.table import SymbolTable


class Section:
    name: str
    lines: list[RawLine]
    locctr: int
    symbol_table: SymbolTable
    start_addr: int
    length: int
    base: str
    object_code: ObjectCode
    __slots__ = tuple(__annotations__)

    def __init__(self, name: str):
        self.name = name
        self.lines = []
        self.locctr = 0
        self.symbol_table = SymbolTable()
        self.start_addr = 0
        self.base = ""
        self.object_code = ObjectCode()

    def analyze_symbol(self, line: RawLine):
        if line.operator.replace("+", "") in OPCODE:
            self.analyze_operator(line)
        else:
            self.analyze_directive(line)

    def analyze_operator(self, line: RawLine):
        token = line.operator.replace("+", "")
        line.format, line.opcode = OPCODE[token]["format"], OPCODE[token]["opcode"]
        if line.operator[0] == "+":
            line.format = 4
        self.locctr += line.format

    def analyze_directive(self, line: RawLine):
        token = line.operator
        if token not in DIRECTIVE:
            sys.exit(f'Error: invalid operator "{token}".')
        line.format = DIRECTIVE[token]["format"]
        if token == "RESW":
            self.locctr += 3 * int(line.operand)
        elif token == "WORD":
            self.locctr += 3
        elif token == "RESB":
            self.locctr += int(line.operand)
        elif token == "BYTE":
            if line.operand[0] == "C":
                self.locctr += len(line.operand[2:-1])
            elif line.operand[0] == "X":
                self.locctr += math.ceil(len(line.operand[2:-1]) / 2)
        elif token == "BASE":
            self.base = line.operand

    def pass1(self):
        lines = iter(self.lines)
        l = self.lines[0]
        if l.operator == "START":
            self.locctr = self.start_addr = l.addr = int(l.operand, 16)
            next(lines)
        else:
            self.locctr = self.start_addr = 0
        for l in lines:
            if l.operator == "END":
                break
            l.addr = self.locctr
            if l.label != "":
                if self.symbol_table[l.label] is not None:
                    sys.exit("Error: defining duplicate symbol.")
                self.symbol_table[l.label] = self.locctr
            if l.operator != "":
                self.analyze_symbol(l)
        self.length = self.locctr - self.start_addr

    def pass2(self):
        n = len(self.lines) - 1
        l = self.lines[0]
        if l.operator == "START":
            self.object_code.set_header(l.label, self.start_addr, self.length)
            start = 1
        else:
            self.object_code.set_header("COPY", self.start_addr, self.length)
            start = 0
        for i, l in enumerate(self.lines, start):
            if l.operator == "":
                continue
            if l.operator == "END":
                self.object_code.set_end(self.start_addr)
                break
            l.generate_objcode(self.symbol_table, self.symbol_table[self.base])

            if i < n:
                self.object_code.append_text(l, self.lines[i])
            else:
                self.object_code.append_text(l, None)  # last line of the section

            # relocation record
            if l.operator[0] == "+" and l.operand[0] != "#":
                self.object_code.append_mod(l.addr, 5)

    def create_object_program(self, file: str):
        with open(f"{file}.prog", "w", encoding="utf-8") as f:
            for l in self.lines:
                if l.operator == "":
                    f.write(f"{l.line}")
                    continue
                addr = getattr(l, "addr", "")
                if addr != "":
                    addr = f"{addr:04X}"
                obj = getattr(l, "obj_code", "")
                if obj != "":
                    obj = f"{obj:X}"
                f.write(f"{addr} {l.label:6} {l.operator:6} {l.operand:6} {obj}\n")
            f.close()

    def create_object_code(self, file: str):
        self.create_object_program(file)
        with open(f"{file}.obj", "w", encoding="utf-8") as f:
            f.write(str(self.object_code.header))
            f.write("".join(map(str, self.object_code.texts)))
            f.write("".join(map(str, self.object_code.mods)))
            f.write(str(self.object_code.end))
            f.close()
