from core.line import Line


class Section:
    def __init__(self, name: str):
        self.name = name
        self.line: Line = None

    def create_line(self, label: str, operator: str, operand: str):
        l = Line(label, operator, operand)
        if self.line is None:
            self.line = l
