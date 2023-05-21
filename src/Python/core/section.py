from core.lexer import RawLine


class Section:
    name: str
    lines: list[RawLine]
    __slots__ = tuple(__annotations__)

    def __init__(self, name: str):
        self.name = name
        self.lines = []
