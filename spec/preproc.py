import sys

with open(sys.argv[1], "r") as fin:
    c = False
    for line in fin.readlines():
        if not c:
            if "C level begin" in line:
                c = True
            else:
                print(line, end="")
        else:
            if "C level end" in line:
                c = False
                