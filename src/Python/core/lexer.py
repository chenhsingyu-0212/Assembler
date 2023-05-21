import re


class RawLine:
    line: str
    label: str
    operator: str
    operand: str
    addr: int
    is_comment: bool
    format: int
    opcode: int
    __slots__ = tuple(__annotations__)

    def __init__(self, line: str):
        self.line = line
        tokenize(self)


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
