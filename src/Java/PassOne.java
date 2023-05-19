package src.Java;

import java.util.ArrayList;
import java.util.Map;

public class PassOne {
    private LTORG ltorg;
    private LOCCTR locctr;
    private ArrayList<String[]> data;
    private ArrayList<String> loc;
    private ArrayList<Integer> format;
    private Map<String, String> symtab;
    private ArrayList<String> total;

    public PassOne(ArrayList<String[]> rawDatas) {
        data = new ArrayList<>();
        loc = new ArrayList<>();
        format = new ArrayList<>();

        // LTORG
        ltorg = new LTORG();
        ltorg.process(rawDatas);
        data = ltorg.getData();

        // LOCCTR
        locctr = new LOCCTR();
        locctr.create(data);
        loc = locctr.getLOC();
        format = locctr.getFormat();
        symtab = locctr.getSYMTAB();
        total = locctr.getTotal();
    }

    public ArrayList<String[]> getData(){
        return data;
    }

    public ArrayList<String> getLOC(){
        return loc;
    }

    public ArrayList<Integer> getFormat(){
        return format;
    }

    public Map<String, String> getSYMTAB(){
        return symtab;
    }

    public ArrayList<String> getTotal(){
        return total;
    }
}
