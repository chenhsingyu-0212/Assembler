class Line:
    def __init__(self, label: str, operator: str, operand: str, addr: int = None):
        self.label = label
        self.operator = operator
        self.operand = operand
        self.addr = addr
