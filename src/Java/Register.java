package src.Java;

import java.util.HashMap;
import java.util.Map;

public class Register {
    private Map<String, String> register;

    public Register(){
        register = new HashMap<>();
        register.put("A", "0");
        register.put("X", "1");
        register.put("L", "2");
        register.put("B", "3");
        register.put("S", "4");
        register.put("T", "5");
        register.put("F", "6");
        register.put("PC", "8");
        register.put("SW", "9");
    }

    public String getRegister(String key){
        return register.get(key);
    }
}
