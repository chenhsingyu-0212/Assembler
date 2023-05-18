# Assembler with Java

## Description
use java to writer the assembler

## How to run Project 
please input following shell in cmd to run assembler: 
```shell
cd Assembler/assembler
./Java_Assembler.bat
```

## Change `inputFile/outputFile` name
if your want to change `inputFile/outputFile` name with this assembler. Your can open Java_Assembler.bat file, you will see following shell:
```shell
javac src/Java/*.java
java src/Java/Assembler "Figure 2.15.txt" "OP_Code.txt" "OP_Program.txt"
```
- **First line**: complier all `.java` file.
- **Second line**: run Assembler file, the first string `"Figure 2.15.txt"` is inputFile name, second string `"OP_Code.txt"` is output file with OP Code file name and third string `"OP_Program.txt"` is output file with OP Program file name.

You can try to change this file to get what file name you need.