package src.Java;

import java.util.ArrayList;
import java.util.Map;

public class PassTwo {
    private CreateOPCode createOPCode;
    private ArrayList<String> opCode;

    public PassTwo(ArrayList<String[]> passOneData, ArrayList<String> passOneLOC, ArrayList<Integer> passOneFormat,
            Map<String, String> passOneSYMTAB) {
        opCode = new ArrayList<>();

        // Create
        createOPCode = new CreateOPCode(passOneData, passOneLOC, passOneFormat, passOneSYMTAB);
        opCode = createOPCode.getOPCode();
    }

    public ArrayList<String> getOPCode() {
        return opCode;
    }
}
