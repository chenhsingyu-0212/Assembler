import re

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
                if re.match(r"^\s*$", line):
                    s.lines.append("")
                    continue
                l = RawLine(line)
                s.create_line(l.label, l.operator, l.operand)
