#include "FileMgr.h"

int main() {
    FileMgr file;
    vector<string> datas = file.read("test.txt");
    for(string data: datas) {
        cout << data << endl;
    }
    vector<string> v {"987", "654", "321"};
    file.write("test.txt", v);
}