package src.Java;

import java.util.ArrayList;

public class EXTREF {
    private ArrayList<String> extref;

    public EXTREF(){
        extref = new ArrayList<>();
    }

    public void add(String str){
        String[] ref = str.split(",");
        for(String r : ref){
            extref.add(r);
        }
    }

    public boolean isEXTREF(String word){
        return extref.contains(word);
    }
}
