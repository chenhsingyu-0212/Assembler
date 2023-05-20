from core.line import Line


class Section:
    name: str
    lines: list[Line]
    __slots__ = tuple(__annotations__)

    def __init__(self, name: str):
        self.name = name
        self.lines = []

    def create_line(self, label: str, operator: str, operand: str):
        l = Line(label, operator, operand)
        self.lines.append(l)
