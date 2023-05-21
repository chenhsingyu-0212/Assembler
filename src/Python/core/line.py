class Line:
    label: str
    operator: str
    operand: str
    addr: int
    __slots__ = tuple(__annotations__)

    def __init__(self, label: str, operator: str, operand: str):
        self.label = label
        self.operator = operator
        self.operand = operand
