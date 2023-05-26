import os
import sys

from core.assembler import Assembler

if __name__ == "__main__":
    if len(sys.argv) < 2 or len(sys.argv) > 3:
        sys.exit("Error: should give exactly one assembly file as input.")

    path = sys.argv[1]
    if os.path.exists(path) is False:
        sys.exit("Error: the given file does not exist.")
    filename = path

    # remove file extension if exists
    if path.rfind(".") != -1:
        filename = path[: path.rfind(".")]

    a = Assembler()
    a.parse(path)
    a.execute(filename)
