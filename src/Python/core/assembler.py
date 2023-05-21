from core.lexer import RawLine
from core.section import Section


class Assembler:
    sections: list[Section] = []
    __slots__ = tuple(__annotations__)

    def parse(self, file: str):
        s = Section("COPY")
        self.sections.append(s)
        with open(file, "r", encoding="utf-8") as f:
            for line in f:
                s.lines.append(RawLine(line))
