import math
import sys

from core.lexer import RawLine
from core.mnemonics import DIRECTIVE, OPCODE
from core.table import SymbolTable


class Section:
    name: str
    lines: list[RawLine]
    locctr: int
    symbol_table: SymbolTable
    start_addr: int
    length: int
    __slots__ = tuple(__annotations__)

    def __init__(self, name: str):
        self.name = name
        self.lines = []
        self.locctr = 0
        self.symbol_table = SymbolTable()
        self.start_addr = 0

    def analyze_symbol(self, line: RawLine):
        if line.operator.replace("+", "") in OPCODE:
            self.analyze_operator(line)
        else:
            self.analyze_directive(line)

    def analyze_operator(self, line: RawLine):
        token = line.operator.replace("+", "")
        line.format, line.opcode = OPCODE[token]["format"], OPCODE[token]["opcode"]
        if line.operator[0] == "+":
            self.locctr += 4
        else:
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

    def pass1(self):
        lines = iter(self.lines)
        l = self.lines[0]
        if l.operator == "START":
            self.locctr = self.start_addr = int(l.operand, 16)
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
