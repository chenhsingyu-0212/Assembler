class RawLine:
    line: str
    label: str
    operator: str
    operand: str
    is_comment: bool
    __slots__ = tuple(__annotations__)

    def __init__(self, line: str):
        self.line = line
