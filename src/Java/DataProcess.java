package src.Java;

import java.util.ArrayList;

public class DataProcess {
    private ArrayList<String> code;
    private ArrayList<String> program;
    
    public DataProcess(ArrayList<String[]> datas, ArrayList<String> loc){
        code = new ArrayList<>();
        program = new ArrayList<>();
        String firstLine = String.format("%-8s%-40s%-15s", "Loc", "Source statement", "Object code");
        code.add(firstLine);

        for (int i = 0; i < datas.size(); i++) {
            String[] res = new String[5];
            res[0] = loc.get(i);
            res[1] = (datas.get(i).length >= 1 ? datas.get(i)[0] : "");
            res[2] = (datas.get(i).length >= 2 ? datas.get(i)[1] : "");
            res[3] = (datas.get(i).length >= 3 ? datas.get(i)[2] : "");
            res[4] = "";
            process(res);
        }
    }// %8s %8s %8s %24s %15s

    public void process(String[] data){
        String f = String.format("%-8s%-8s%-8s%-24s%-15s", data[0], data[1], data[2], data[3], data[4]);
        code.add(f);
    }

    public ArrayList<String> getData(boolean isProgram){
        if(isProgram == true){
            return program;
        }
        return code;
    }
}
