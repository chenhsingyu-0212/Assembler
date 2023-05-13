import sys

from core.assembler import Assembler

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Please specific one assembly file as argument.")
        sys.exit()

    path = sys.argv[1]
    filename = path

    # remove file extension if exists
    if path.rfind(".") != -1:
        filename = path[: path.rfind(".")]

    a = Assembler()
