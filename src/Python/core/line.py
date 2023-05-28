import re

from core.mnemonics import DIRECTIVE
from core.table import SymbolTable


class RawLine:
    line: str
    label: str
    operator: str
    operand: str
    operands: list[str]
    addr: int
    is_comment: bool
    format: int
    opcode: int
    obj_code: int
    __slots__ = tuple(__annotations__)

    def __init__(self, line: str):
        self.line = line
        tokenize(self)
        if self.operand != "":
            self.operands = list(self.operand.split(","))

    def count_disp(self, target_address: int, base: int) -> tuple[int]:
        """return (disp, b, p)"""
        if target_address is None:
            return 0, 0, 0

        addr = self.addr + self.format
        if -2048 < target_address - addr < 0:
            return 0x1000 + target_address - addr, 0, 1  # 2's complement
        elif 0 <= target_address - addr < 2048:
            return target_address - addr, 0, 1
        return target_address - base, 1, 0

    def generate_objcode(self, symbol_table: SymbolTable, base: int):
        opcode = getattr(self, "opcode", None)
        if self.operator in DIRECTIVE or opcode is None:
            if self.operator == "BYTE":
                if self.operand[0] == "C":
                    self.obj_code = int(self.operand[2:-1].encode("utf-8").hex(), 16)
                    self.format = len(self.operand[2:-1])
                elif self.operand[0] == "X":
                    self.obj_code = int(self.operand[2:-1], 16)
                    self.format = len(self.operand[2:-1]) // 2
            elif self.operator == "WORD":
                self.obj_code = int(self.operand)
                self.format = (int(self.operand).bit_length() + 7) // 8
            return
        if self.format == 3:
            # n => indirect addressing
            # i => immediate addressing
            # x => indexing
            # b => base relative addressing
            # p => pc relative addressing
            # e => extended addressing
            if self.operator == "RSUB":
                self.obj_code = 0x4F0000
                return
            n, i, x, b, p = 1, 1, 0, 0, 0

            # addressing mode
            if self.operand[0] == "@":
                n, i = 1, 0
                disp, b, p = self.count_disp(symbol_table[self.operand[1:]], base)
            elif self.operand[0] == "#":
                n, i = 0, 1
                if self.operand[1:].isnumeric():
                    disp = int(self.operand[1:])
                else:
                    disp, b, p = self.count_disp(symbol_table[self.operand[1:]], base)
            else:
                disp, b, p = self.count_disp(symbol_table[self.operands[0]], base)

            # indexing
            if self.operand[-1] == "X":
                x = 1

            self.obj_code = (
                (opcode << 16) + (n << 17) + (i << 16) + (x << 15) + (b << 14) + (p << 13) + disp
            )
        elif self.format == 4:
            n, i, x, b, p = 1, 1, 0, 0, 0
            if self.operand[0] == "#":
                n, i = 0, 1
                if self.operand[1:].isnumeric():
                    disp = int(self.operand[1:])
                else:
                    disp = symbol_table[self.operand[1:]]
            else:
                disp = symbol_table[self.operands[0]]

            if self.operand[-1] == "X":
                x = 1

            self.obj_code = (
                (opcode << 24)
                + (n << 25)
                + (i << 24)
                + (x << 23)
                + (b << 22)
                + (p << 21)
                + (1 << 20)
                + disp
            )

        elif self.format == 2:
            if len(self.operands) == 1:
                self.obj_code = (opcode << 8) + (symbol_table[self.operands[0]] << 4)
            else:
                self.obj_code = (
                    (opcode << 8)
                    + (symbol_table[self.operands[0]] << 4)
                    + symbol_table[self.operands[1]]
                )
        elif self.format == 1:
            self.obj_code = self.opcode


def tokenize(obj: RawLine):
    if re.match(r"^\s*\.", obj.line):
        obj.is_comment = True
        obj.label, obj.operator, obj.operand = "", "", ""
    else:
        line = re.sub(r"\s*,\s*X", ",X", obj.line)
        obj.is_comment = False
        tokens = list(line.split())
        if len(tokens) == 3:
            obj.label, obj.operator, obj.operand = tokens[0], tokens[1], tokens[2]
        elif len(tokens) == 2:
            obj.label, obj.operator, obj.operand = "", tokens[0], tokens[1]
        elif len(tokens) == 1:
            obj.label, obj.operator, obj.operand = "", tokens[0], ""
        elif len(tokens) == 0:
            obj.label, obj.operator, obj.operand = "", "", ""
