#!/usr/bin/python
import sys
str = sys.argv[1]
list = str.split(" ")
for code in list :
    c = chr(int(code))
    sys.stdout.write(c)
print
