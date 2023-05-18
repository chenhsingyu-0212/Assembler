package src.Java;

import java.util.HashMap;
import java.util.Map;

public class OPTAB {
    private Map<String, String> OPCodeTable;

    public OPTAB(){
        OPCodeTable = new HashMap<>();
        OPCodeTable.put("ADD", "3 18");
        OPCodeTable.put("ADDF", "3 58");
        OPCodeTable.put("ADDR", "2 90");
        OPCodeTable.put("AND", "3 40");
        OPCodeTable.put("CLEAR", "2 B4");
        OPCodeTable.put("COMP", "3 28");
        OPCodeTable.put("COMPF", "3 88");
        OPCodeTable.put("COMPR", "2 A0");
        OPCodeTable.put("DIV", "3 24");
        OPCodeTable.put("DIVF", "3 64");
        OPCodeTable.put("DIVR", "2 9C");
        OPCodeTable.put("FIX", "1 C4");
        OPCodeTable.put("FLOAT", "1 C0");
        OPCodeTable.put("HIO", "1 F4");
        OPCodeTable.put("J", "3 3C");
        OPCodeTable.put("JEQ", "3 30");
        OPCodeTable.put("JGT", "3 34");
        OPCodeTable.put("JLT", "3 38");
        OPCodeTable.put("JSUB", "3 48");
        OPCodeTable.put("LDA", "3 00");
        OPCodeTable.put("LDB", "3 68");
        OPCodeTable.put("LDCH", "3 50");
        OPCodeTable.put("LDF", "3 70");
        OPCodeTable.put("LDL", "3 08");
        OPCodeTable.put("LDS", "3 6C");
        OPCodeTable.put("LDT", "3 74");
        OPCodeTable.put("LDX", "3 04");
        OPCodeTable.put("LPS", "3 D0");
        OPCodeTable.put("MUL", "3 20");
        OPCodeTable.put("MULF", "3 60");
        OPCodeTable.put("MULR", "2 98");
        OPCodeTable.put("NORM", "1 C8");
        OPCodeTable.put("OR", "3 44");
        OPCodeTable.put("RD", "3 D8");
        OPCodeTable.put("RMO", "2 AC");
        OPCodeTable.put("RSUB", "3 4C");
        OPCodeTable.put("SHIFTL", "2 A4");
        OPCodeTable.put("SHIFTR", "2 A8");
        OPCodeTable.put("SIO", "1 F0");
        OPCodeTable.put("SSK", "3 EC");
        OPCodeTable.put("STA", "3 0C");
        OPCodeTable.put("STB", "3 78");
        OPCodeTable.put("STCH", "3 54");
        OPCodeTable.put("STF", "3 80");
        OPCodeTable.put("STI", "3 D4");
        OPCodeTable.put("STL", "3 14");
        OPCodeTable.put("STS", "3 7C");
        OPCodeTable.put("STSW", "3 E8");
        OPCodeTable.put("STT", "3 84");
        OPCodeTable.put("STX", "3 10");
        OPCodeTable.put("SUB", "3 1C");
        OPCodeTable.put("SUBF", "3 5C");
        OPCodeTable.put("SUBR", "2 94");
        OPCodeTable.put("SVC", "2 B0");
        OPCodeTable.put("TD", "3 E0");
        OPCodeTable.put("TIO", "1 F8");
        OPCodeTable.put("TIX", "3 2C");
        OPCodeTable.put("TIXR", "2 B8");
        OPCodeTable.put("WD", "3 DC");
    }

    public String getOPCode(String instruction){
        return OPCodeTable.get(instruction).substring(2);
    }

    public int getFormat(String instruction){
        return Integer.parseInt(OPCodeTable.get(instruction).substring(0, 1));
    }

    public boolean isInstruction(String word){
        return OPCodeTable.containsKey(word);
    }
}
