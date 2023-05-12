package src.Java;

import java.util.HashMap;
import java.util.Map;

public class SYMTAB {
    Map<String, String> res;

    public SYMTAB(){
        res = new HashMap<>();
    }

    public void setSymbol(String symbol, String loc){
        res.put(symbol, loc);
    }

    public String getSymbol(String symbol){
        return res.get(symbol);
    }
}
