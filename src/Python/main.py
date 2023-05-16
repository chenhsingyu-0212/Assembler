import sys
import os

from core.assembler import Assembler

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Please specific one assembly file as argument.")
        sys.exit()

    path = sys.argv[1]
    if os.path.exists(path) is False:
        print("The file does not exist.")
        sys.exit()
    filename = path

    # remove file extension if exists
    if path.rfind(".") != -1:
        filename = path[: path.rfind(".")]

    a = Assembler()
