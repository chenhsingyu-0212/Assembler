package src.Java;

import java.util.ArrayList;

public class LOCCTR {
    private OPTAB optab;
    private SYMTAB symtab;
    private ArrayList<String> res;

    public LOCCTR() {
        res = new ArrayList<>();
        optab = new OPTAB();
        symtab = new SYMTAB();
    }

    public ArrayList<String> getLOC(){
        return res;
    }

    public void create(ArrayList<String[]> datas) {
        String loc = "0000";

        for (String[] data : datas) {
            if (data.length >= 2 && (data[1].equals("START") || data[1].equals("CSECT"))) {
                loc = "0000";
                res.add(loc);
            } else if (data.length >= 2 && data[1].charAt(0) == '+') {
                res.add(loc);
                loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + 4, 16));
            } else if (data.length >= 2 && optab.isInstruction(data[1])) {
                res.add(loc);
                loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + optab.getFormat(data[1]), 16));
            } else if (data.length >= 2 && data[1].equals("RESW")) {
                res.add(loc);
                loc = String.format("%s",
                        Integer.toString(Integer.parseInt(loc, 16) + Integer.parseInt(data[2]) * 3, 16));
            } else if (data.length >= 2 && data[1].equals("RESB")) {
                res.add(loc);
                loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + Integer.parseInt(data[2]), 16));
            } else if (data.length >= 2 && data[1].equals("BYTE")) {
                res.add(loc);
                if (data.length >= 2 && data[1].charAt(1) == 'X') {
                    String word = data[1].substring(2, data[1].length() - 1);
                    loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + word.length() / 2, 16));
                } else{
                    String word = data[1].substring(2, data[1].length() - 1);
                    loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + word.length(), 16));
                }
            } else if (data.length >= 2 && data[1].equals("WORD")) {
                res.add(loc);
                loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + 3, 16));
            } else if (data.length >= 2 && data[1].charAt(0) == '=') {
                res.add(loc);
                if (data[1].charAt(1) == 'X') {
                    String word = data[1].substring(3, data[1].length() - 1);
                    loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + word.length() / 2, 16));
                } else{
                    String word = data[1].substring(3, data[1].length() - 1);
                    loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + word.length(), 16));
                }
            } else if (data.length >= 2 && data[1].equals("EQU")) {
                String[] value = data[2].split("-");
                String[] sign = data[2].split("[^A-Za-z]");
                boolean isAbsolute = true;

                int len = 0;
                for(String v : value){
                    try {
                        Integer.parseInt(v);
                    } catch (Exception e) {
                        // TODO: handle exception
                        len++;
                    }
                }
                if(len % 2 != 0) isAbsolute = false;

                if(isAbsolute == true){
                    boolean isAdd = true;
                    for(String s : sign){
                        if((s.equals("-") && isAdd == false) || (s.equals("+") && isAdd == true)){
                            isAbsolute = false;
                            break;
                        }else if(s.length() != 0){
                            isAdd = !isAdd;
                        }
                    }
                }

                if(isAbsolute){
                    loc = "0000";
                    for(int i = 0; i < value.length; i++){
                        if(i % 2 == 0){
                            loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) + Integer.parseInt(symtab.getSymbol(value[i]), 16), 16));
                        } else{
                            loc = String.format("%s", Integer.toString(Integer.parseInt(loc, 16) - Integer.parseInt(symtab.getSymbol(value[i]), 16), 16));
                        }
                    }
                    res.add(loc);
                } else{
                    res.add(loc);
                }
            } else {
                res.add("");
            }

            while(loc.length() != 4){
                loc = "0" + loc;
            }

            if(data[0].length() != 0){
                symtab.setSymbol(data[0], res.get(res.size() - 1));
            }
        }
    }
}
