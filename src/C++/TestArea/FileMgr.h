#ifndef FileMgr_H
#define FileMgr_H
#include <iostream>
#include <vector>
#include <fstream>
using namespace std;

class FileMgr {
private:
    fstream file;
    string src;
public:
    vector<string> read(string src) {
        vector<string> res;
        this->file.open(src, ios::in);
        if (this->file.fail()) {
            cout << "Error: Fail to read from file " << src << "\n";
            exit(-1);
        }
        string line;
        while (getline(file, line)) {
            res.push_back(line);
        }
        this->file.close();
        return res; 
    }

    void write(string src, vector<string> datas) {
        this->file.open(src, ios::out);
        if (this->file.fail()) {
            cout << "Error: Fail to write to file " << src << "\n";
            exit(-1);
        }
        for (string data : datas) {
            this->file << data << "\n";
        }
        this->file.close();
    }
};

#endif