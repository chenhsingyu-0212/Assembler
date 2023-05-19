package src.Java;

import java.util.ArrayList;
import java.util.Map;

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
            res[0] = loc.get(i);
            res[1] = (datas.get(i).length >= 1 ? datas.get(i)[0] : "");
            res[2] = (datas.get(i).length >= 2 ? datas.get(i)[1] : "");
            res[3] = (datas.get(i).length >= 3 ? datas.get(i)[2] : "");
            res[4] = opcode.get(i);
            String f = String.format("%-8s%-8s%-8s%-24s%-15s", res[0], res[1], res[2], res[3], res[4]);
            code.add(f);
        }
    }// %8s %8s %8s %24s %15s

    public void opProgram(ArrayList<String[]> datas, ArrayList<String> loc, Map<String, String> symtab,
            ArrayList<String> total, ArrayList<String> opcode, ArrayList<String> mod) {
        String code = "";
        String line = "";
        int count = 0;
        String e = "";

        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).length > 1 && (datas.get(i)[1].equals("START") || datas.get(i)[1].equals("CSECT"))) {
                String t = total.get(0);
                while (t.length() < 6) {
                    t = "0" + t;
                }
                String f = String.format("%1s%-6s%06d%6s", "H", datas.get(i)[0],
                        Integer.parseInt((datas.get(i).length > 2 ? datas.get(i)[2] : "0")), t);
                total.remove(0);
                program.add(f);
                if(datas.get(i)[1].equals("START")){
                    e = loc.get(i);
                    while(e.length() < 6){
                        e = "0" + e;
                    }
                } else {
                    e = "";
                }
            } else if (datas.get(i).length > 1 && (datas.get(i)[1].equals("EXTDEF"))) {
                String[] data = datas.get(i)[2].split(",");
                String f = "D";
                for (String d : data) {
                    String s = symtab.get(d);
                    while (s.length() < 6) {
                        s = "0" + s;
                    }
                    f = f + String.format("%-6s%6s", d, s);
                }
                program.add(f);
            } else if (datas.get(i).length > 1 && (datas.get(i)[1].equals("EXTREF"))) {
                String[] data = datas.get(i)[2].split(",");
                String f = "R";
                for (String d : data) {
                    f = f + String.format("%-6s", d);
                }
                program.add(f);
            } else if (!opcode.get(i).equals("")) {
                if (line.equals("")) {
                    line = loc.get(i);
                }

                int len = opcode.get(i).length() / 2;
                count += len;
                code += opcode.get(i);

                int j = 1;
                while (i + j < datas.size() && loc.get(i + j).equals("")) {
                    j++;
                }

                if (i + 1 >= datas.size() || opcode.get(i + j).equals("")
                        || count + opcode.get(i + j).length() / 2 >= 30) {
                    while (line.length() < 6) {
                        line = "0" + line;
                    }
                    String c = Integer.toHexString(count);
                    while (c.length() < 2) {
                        c = "0" + c;
                    }
                    String f = String.format("%1s%6s%2s%-30s", "T", line, c, code);
                    program.add(f);
                    code = "";
                    count = 0;
                    line = "";
                }
            } else if (datas.get(i)[0].equals(" ")) {
                while(mod.get(0).length() > 0){
                    program.add(mod.get(0));
                    mod.remove(0);
                }
                mod.remove(0);
                program.add("E" + e);
                program.add("");
                program.add("");
                program.add("");
            }
        }

        while(!mod.isEmpty()){
            program.add(mod.get(0));
            mod.remove(0);
        }
        program.add("E" + e);
    }

    public ArrayList<String> getData(boolean isProgram) {
        if (isProgram == true) {
            return program;
        }
        return code;
    }
}
