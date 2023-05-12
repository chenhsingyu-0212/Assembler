#include "FileMgr.h"
#include <bits/stdc++.h>

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

int main() {
    FileMgr file;
    vector<string> src = file.read("test.txt");
    
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
        string str;
        
        // ss << hex << setw(4) << setfill(0) << LOCCTR << "\t" << LABEL << "\t" << OPCODE << "\t" << FIRST_OPERAND << "\n";
        // inter.push_back(str.format("%04X\t%s\t%s\t%s\n", LOCCTR, LABEL, OPCODE, FIRST_OPERAND));
    } else {
        cout << "\nError: No START at first line\n";
        return -1;
    }
}