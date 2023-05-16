from core.lexer import RawLine
from core.section import Section


class Assembler:
    def __init__(self):
        self.sections: list[Section] = []

    def parse(self, file):
        s = Section("COPY")
        self.sections.append(s)
        with open(file, "r", encoding="utf-8") as f:
            for line in f:
                l = RawLine(line)
                # s.create_line()
