from core.line import RawLine
from core.section import Section


class Assembler:
    sections: list[Section]
    __slots__ = tuple(__annotations__)

    def __init__(self):
        self.sections = []

    def parse(self, file: str):
        s = Section("COPY")
        self.sections.append(s)
        with open(file, "r", encoding="utf-8") as f:
            for line in f:
                s.lines.append(l := RawLine(line))
                if l.operand != "" and l.operand[0] == "=":
                    l.add_literal(s.literal_table, s.symbol_table)
                if l.operator == "LTORG":
                    s.create_literal_lines()
            s.create_literal_lines()

    def execute(self, file: str):
        for s in self.sections:
            s.pass1()
            s.pass2()
            s.create_object_code(file)
