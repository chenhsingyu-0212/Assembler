import re

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

    def generate_objcode(self, symbol_table: SymbolTable, base: int):
        opcode = getattr(self, "opcode", None)
        if opcode is None:
            if self.operator == "BYTE":
                if self.operand[0] == "C":
                    self.obj_code = int(self.operand[2:-1].encode("utf-8").hex(), 16)
                elif self.operand[0] == "X":
                    self.obj_code = int(self.operand[2:-1], 16)
            elif self.operator == "WORD":
                self.obj_code = int(self.operand)
        if self.format == 3:
            # n => indirect addressing
            # i => immediate addressing
            # x => indexing
            # b => base relative addressing
            # p => pc relative addressing
            # e => extended addressing
            n, i, x, b, p, e = 1, 1, 0, 0, 0, 0
            target_address = symbol_table[self.operands[0]]
            if self.operands[0][0] == "@":
                n, i = 1, 0
            elif self.operands[0][0] == "#":
                n, i = 0, 1
            elif self.operands[0][0] == "+":
                n, i, e = 1, 1, 1

            if self.operands[1] == "X":
                x = 1

            if target_address is None:
                disp = 0
            elif n == 1 and i == 1:  # simple addressing
                disp = target_address
            elif -2048 <= self.addr - target_address <= 2047:  # base relative
                disp = target_address - self.addr
                p = 1
            else:
                disp = target_address - base
                b = 1

            if e:  # is format 4
                self.obj_code = (
                    opcode << 24 + n << 26 + i << 25 + x << 23 + b << 22 + p << 21 + e << 20 + disp
                )
            else:
                self.obj_code = (
                    opcode << 16 + n << 17 + i << 16 + x << 15 + b << 14 + p << 13 + disp
                )
        elif self.format == 2:
            if len(self.operands) == 1:
                self.obj_code = opcode << 8 + self.operands[0] << 4
            else:
                self.obj_code = opcode << 8 + self.operands[0] << 4 + self.operands[1]
        elif self.format == 1:
            self.obj_code = self.opcode


def tokenize(obj: RawLine):
    if re.match(r"^\s*\.", obj.line):
        obj.is_comment = True
        obj.label, obj.operator, obj.operand = "", "", ""
    else:
        obj.is_comment = False
        tokens = list(obj.line.split())
        if len(tokens) == 3:
            obj.label, obj.operator, obj.operand = tokens[0], tokens[1], tokens[2]
        elif len(tokens) == 2:
            obj.label, obj.operator, obj.operand = "", tokens[0], tokens[1]
        elif len(tokens) == 1:
            obj.label, obj.operator, obj.operand = "", tokens[0], ""
        elif len(tokens) == 1:
            obj.label, obj.operator, obj.operand = "", "", ""
