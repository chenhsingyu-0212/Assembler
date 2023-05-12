#include <bits/stdc++.h>
#include "FileMgr.h"
#define OPTAB_FILE "opcode_table.txt"
#define SYMTAB_FILE "symble_table.txt"
#define INTERMEDIATE_FILE "intermediate.txt"
#define SOURCE_FILE "source.txt"
#define OBJ_FILE "object.txt"

FileMgr file;
vector<string> src, optab, inter;

int pass1();
int pass2();
int hexTodec(string);

int main () {
    cout << "=== SIC/XE Assembler ===\n\n";
    cout << "==== Pass 1 Start ====\n\n";

    src = file.read(SOURCE_FILE);
    optab = file.read(OPTAB_FILE);

    if (pass1() != 0) {
        cout << "\nError: error in pass 1\n";
        exit(-1);
    }

    cout << "\n==== Pass 1 End ====\n\n";
    cout << "==== Pass 2 Start ====\n\n";

    inter = file.read(INTERMEDIATE_FILE);
    optab = file.read(OPTAB_FILE);

    if (pass2() != 0) {
        cout << "\nError: error in pass 2\n";
        exit(-1);
    }

    cout << "\n==== Pass 2 End ====\n\n";
    system("PAUSE");
    return 0;
}

int pass1() {
    int currline = 0, startAddr = 0, LOCCTR = 0;
    string LABEL, OPCODE, FIRST_OPERAND, SECOND_OPERAND;
    stringstream ss;
    // read first input line
    ss << src[currline++];
    getline(ss, LABEL, '\t');
    getline(ss, OPCODE, '\t');
    getline(ss, FIRST_OPERAND, '\t');
    ss.clear();

    if (OPCODE == "START") {
        startAddr = hexTodec(FIRST_OPERAND);
        LOCCTR = startAddr;
        // ss << hex << setw(4) << setfill(0) << LOCCTR << "\t" << LABEL << "\t" << OPCODE << "\t" << FIRST_OPERAND << "\n";
        // inter.push_back(str.format("%04X\t%s\t%s\t%s\n", LOCCTR, LABEL, OPCODE, FIRST_OPERAND));
    } else {
        cout << "\nError: No START at first line\n";
        return -1;
    }

    return 0;
}

int pass2() {

}

int hexTodec(string hex) {
    int dec = 0;
    for (char c : hex) {
        int val = 0;
        if ('0' <= c && c <= '9') val = c - '0';
        else if ('A' <= c && c <= 'F') val = c - 'A' + 10;
        else if ('a' <= c && c <= 'f') val = c - 'a' + 10;
        else {
            cout << "Error: Wrong hexadecimal format\n";
            exit(-1);
        }
        dec = dec*16 + val;
    }
    return dec;
}

unsigned int objectCodeFormat2(int opcode, short regi1, short regi2) {
	return opcode*0x100 + regi1*0x10 + regi2;
}

unsigned int objectCodeFormat3(int opcode, short N, short I, short X, short B, short P, short E, int disp) {
	return opcode*0x10000 + N*0x20000 + I*0x10000 + X*0x8000 + B*0x4000 + P*0x2000 + E*0x1000 + (disp&0x00FFF);
}

unsigned int objectCodeFormat4(int opcode, short N, short I, short X, short B, short P, short E, int addr) {
	return opcode*0x1000000 + N*0x2000000 + I*0x1000000 + X*0x800000 + B*0x400000 + P*0x200000 + E*0x100000 + addr;
}
