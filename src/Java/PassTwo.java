package src.Java;

import java.util.ArrayList;
import java.util.Map;

public class PassTwo {
    private CreateOPCode createOPCode;
    private ArrayList<String> opCode;
    private ArrayList<String> mod;

    public PassTwo(ArrayList<String[]> passOneData, ArrayList<String> passOneLOC, ArrayList<Integer> passOneFormat,
            Map<String, String> passOneSYMTAB) {
        opCode = new ArrayList<>();
        mod = new ArrayList<>();

        // Create
        createOPCode = new CreateOPCode(passOneData, passOneLOC, passOneFormat, passOneSYMTAB);
        opCode = createOPCode.getOPCode();
        mod = createOPCode.getMod();
    }

    public ArrayList<String> getOPCode() {
        return opCode;
    }

    public ArrayList<String> getMod(){
        return mod;
    }
}
