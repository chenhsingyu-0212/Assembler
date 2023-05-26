class SymbolTable:
    table: dict[str, int]
    __slots__ = tuple(__annotations__)

    def __init__(self) -> None:
        self.table = {"A": 0, "X": 1, "L": 2, "P": 8, "SW": 9, "B": 3, "S": 4, "T": 5, "F": 6}

    def __setitem__(self, name: str, addr: int):
        if name not in self.table:
            self.table[name] = addr

    def __getitem__(self, name: str):
        if name in self.table:
            return self.table[name]
        return None
