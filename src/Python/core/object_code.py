from core.line import RawLine


class HeaderRecord:
    """
    Col. 1: H
    Col. 2-7: Program name
    Col. 8-13: Starting address of object program
    Col. 14-19: Length of object program in bytes
    """

    name: str
    start_addr: int
    length: int
    __slots__ = tuple(__annotations__)

    def __init__(self, name: str, start_addr: int, length: int):
        if len(name) > 6:
            self.name = name[:6]
        else:
            self.name = name
        self.start_addr = start_addr
        self.length = length

    def __str__(self) -> str:
        return f"H^{self.name:<6}{self.start_addr:06X}{self.length:06X}\n"


class TextRecord:
    """
    Col. 1: T
    Col. 2-7: Starting address of object code in this record
    Col. 8-9: Length of object code in this record in bytes
    Col. 10-69: Object code, represented in hexadecimal
    """

    start_addr: int
    length: int
    object_code: list[str]
    __slots__ = tuple(__annotations__)

    def __init__(self):
        self.start_addr = -1
        self.length = 0
        self.object_code = []

    def __str__(self) -> str:
        return f"T^{self.start_addr:06X}^{self.length:02X}^{''.join(self.object_code)}\n"

    def append_text(self, current_line: RawLine):
        # print(current_line.obj_code)
        self.length += current_line.format
        if current_line.format == 3:
            self.object_code.append(f"{current_line.obj_code:06X}^")
        elif current_line.format == 4:
            self.object_code.append(f"{current_line.obj_code:08X}^")
        elif current_line.format == 2:
            self.object_code.append(f"{current_line.obj_code:04X}^")
        elif current_line.format == 1:
            self.object_code.append(f"{current_line.obj_code:02X}^")


class ModRecord:
    """
    Col. 1: M
    Col. 2-7: Address of the first byte to be modified
    """

    start_addr: int
    length: int
    __slots__ = tuple(__annotations__)

    def __init__(self, start_addr: int, length: int):
        self.start_addr = start_addr
        self.length = length

    def __str__(self) -> str:
        return f"M^{self.start_addr:06X}^{self.length:02X}\n"


class EndRecord:
    """
    Col. 1: E
    Col. 2-7: Address of first executable instruction in object program
    """

    start_addr: int
    __slots__ = tuple(__annotations__)

    def __init__(self, start_addr):
        self.start_addr = start_addr

    def __str__(self) -> str:
        return f"E^{self.start_addr:06X}\n"


class ObjectCode:
    header: HeaderRecord
    texts: list[TextRecord]
    mods: list[ModRecord]
    end: EndRecord
    __slots__ = tuple(__annotations__)

    def __init__(self):
        self.texts = [TextRecord()]
        self.mods = []

    def set_header(self, name: str, start_addr: int, length: int):
        self.header = HeaderRecord(name, start_addr, length)

    def append_text(self, current_line: RawLine, next_line: RawLine | None):
        if getattr(current_line, "obj_code", None) is None:
            return
        t = self.texts[-1]
        if t.length + current_line.format > 30:
            # 60 half-bytes = 30 bytes
            t = TextRecord()
            self.texts.append(t)
        if t.length == 0:
            t.start_addr = current_line.addr
        t.append_text(current_line)
        if next_line is None:
            return
        if next_line.operator in ("RESW", "RESB"):
            self.texts.append(TextRecord())

    def append_mod(self, addr: int, length: int):
        self.mods.append(ModRecord(addr + 1, length))

    def set_end(self, start_addr: int):
        self.end = EndRecord(start_addr)
