LOC = 0


def split_inst(line: str):
    """split instruction"""
    tokens = list(line.split())
    if len(tokens) == 0:
        return {"OPCODE": None}
    elif len(tokens) == 3:
        return {"LABEL": tokens[0], "OPCODE": tokens[1], "OPERAND": tokens[2]}
    elif len(tokens) == 2:
        return {"OPCODE": tokens[0], "OPERAND": tokens[1]}
    elif len(tokens) == 1:
        return {"OPCODE": tokens[0]}
    else:
        return {"OPCODE": "."}


def pass1(path: str):
    with open(path, encoding="utf-8") as f:
        w = open("test.txt", "w", encoding="utf-8")
        for line in f:
            tokens = split_inst(line)
            w.write(" ".join(tokens))
            w.write("\n")
        w.close()


pass1("Figure 2.15.txt")
