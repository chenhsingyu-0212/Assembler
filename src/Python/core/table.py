class SymbolTable():
    table: dict[str, int]
    __slots__ = tuple(__annotations__)

    def __init__(self) -> None:
        self.table = {}

    def __setitem__(self, name: str, addr: int):
        if name not in self.table:
            self.table[name] = addr

    def __getitem__(self, name: str):
        if name in self.table:
            return self.table[name]
        return None
