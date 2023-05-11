#include <bits/stdc++.h>
#include "FileMgr.h"
#define OPTAB_FILE "opcode_table.txt"
#define SYMTAB_FILE "symble_table.txt"
#define INTERMEDIATE_FILE "intermediate.txt"
#define SOURCE_FILE "source.txt"
#define OBJ_FILE "object.txt"

FileMgr file;

void pass1();
void pass2();

int main () {
    cout << "=== SIC/XE assembler ===\n\n";
    cout << "==== pass 1 ====\n\n";

    // read source data
    vector<string> src = file.read(SOURCE_FILE);
    // read opcode_table data
    vector<string> optab = file.read(OPTAB_FILE);

}

void pass1() {

}

void pass2() {

}