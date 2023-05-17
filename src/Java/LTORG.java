package src.Java;

import java.util.ArrayList;

public class LTORG {
    private ArrayList<String[]> res;

    public LTORG(){
        res = new ArrayList<>();
    }

    public void process(ArrayList<String[]> rawDatas){
        String[] ltorg = new String[3];
        for(String[] data : rawDatas){
            if(data.length >= 2){
                if(data[1].equals("LTORG") || data[1].equals("END")){
                    res.add(data);
                    res.add(ltorg);
                    ltorg = new String[3];
                    continue;
                }else if(data.length >= 3){
                    if(data[2].charAt(0) == '='){
                        ltorg[0] = "*";
                        ltorg[1] = data[2];
                        ltorg[2] = "";
                    }
                }
            }

            res.add(data);
        }
    }

    public ArrayList<String[]> getData(){
        return res;
    }
}
