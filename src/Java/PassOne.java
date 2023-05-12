package src.Java;

import java.util.ArrayList;

public class PassOne {
    private LTORG ltorg;
    private LOCCTR locctr;
    private ArrayList<String[]> data;
    private ArrayList<String> loc;

    public PassOne(ArrayList<String[]> rawDatas) {
        data = new ArrayList<>();
        loc = new ArrayList<>();

        // LTORG
        LTORG ltorg = new LTORG();
        ltorg.process(rawDatas);
        data = ltorg.getData();

        // LOCCTR
        LOCCTR locctr = new LOCCTR();
        locctr.create(data);
        loc = locctr.getLOC();
    }

    public ArrayList<String[]> getData(){
        return data;
    }

    public ArrayList<String> getLOC(){
        return loc;
    }
}
