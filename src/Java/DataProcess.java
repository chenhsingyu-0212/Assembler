package src.Java;

import java.util.ArrayList;

public class DataProcess {
    private ArrayList<String> code;
    private ArrayList<String> program;

    public DataProcess() {
        code = new ArrayList<>();
        program = new ArrayList<>();
    }

    public void opCode(ArrayList<String[]> datas, ArrayList<String> loc, ArrayList<String> opcode) {
        String firstLine = String.format("%-8s%-40s%-15s", "Loc", "Source statement", "Object code");
        code.add(firstLine);

        for (int i = 0; i < datas.size(); i++) {
            String[] res = new String[5];
            res[0] = loc.get(i).toUpperCase();
            res[1] = (datas.get(i).length >= 1 ? datas.get(i)[0] : "");
            res[2] = (datas.get(i).length >= 2 ? datas.get(i)[1] : "");
            res[3] = (datas.get(i).length >= 3 ? datas.get(i)[2] : "");
            res[4] = opcode.get(i).toUpperCase();
            String f = String.format("%-8s%-8s%-8s%-24s%-15s", res[0], res[1], res[2], res[3], res[4]);
            code.add(f);
        }
    }// %8s %8s %8s %24s %15s

    public void opProgram(String[] data) {
        
    }

    public ArrayList<String> getData(boolean isProgram) {
        if (isProgram == true) {
            return program;
        }
        return code;
    }
}
