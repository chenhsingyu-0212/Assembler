package src.Java;

import java.util.ArrayList;
import java.util.Map;

public class CreateOPCode {
    private ArrayList<String> opCode;
    private ArrayList<String> mod;
    private OPTAB optab = new OPTAB();
    private EXTREF extref;
    private Register register = new Register();

    public CreateOPCode(ArrayList<String[]> datas, ArrayList<String> loc, ArrayList<Integer> format,
            Map<String, String> symtab) {
        opCode = new ArrayList<>();
        extref = new EXTREF();
        mod = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            if (format.get(i) != 0) {
                if (format.get(i) == 1) {
                    String op = optab.getOPCode(datas.get(i)[1]);
                    while (op.length() < 2) {
                        op = "0" + op;
                    }
                    opCode.add(op);
                } else if (format.get(i) == 2) {
                    String op = optab.getOPCode(datas.get(i)[1]);
                    while (op.length() < 2) {
                        op = "0" + op;
                    }
                    String[] symbol = datas.get(i)[2].split(",");
                    if (symbol.length > 1) {
                        opCode.add(op + register.getRegister(symbol[0]) + register.getRegister(symbol[1]));
                    } else {
                        opCode.add(op + register.getRegister(symbol[0]) + 0);
                    }
                } else if (format.get(i) == 3) {
                    String op = optab.getOPCode(datas.get(i)[1]);
                    if (datas.get(i)[2].charAt(0) == '#') {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 1);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        String symbol = datas.get(i)[2].substring(1);
                        try {
                            String disp = Integer.toString(Integer.parseInt(symbol), 16);
                            while (disp.length() < 3) {
                                disp = "0" + disp;
                            }
                            opCode.add(op + "0" + disp);
                        } catch (Exception e) {
                            // TODO: handle exception
                            opCode.add(op + getDisp(i, symbol, loc, false, extref, symtab));
                        }
                    } else if (datas.get(i)[2].charAt(0) == '@') {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 2);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        String symbol = datas.get(i)[2].substring(1);
                        opCode.add(op + getDisp(i, symbol, loc, false, extref, symtab));
                    } else {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 3);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        String[] symbol = datas.get(i)[2].split(",");
                        if (symbol.length > 1 && symbol[1].equals("X")) {
                            opCode.add(op + getDisp(i, symbol[0], loc, true, extref, symtab));
                        } else {
                            opCode.add(op + getDisp(i, symbol[0], loc, false, extref, symtab));
                        }
                    }
                } else if (format.get(i) == 4) {
                    String m = "";
                    String op = optab.getOPCode(datas.get(i)[1].substring(1));
                    if (datas.get(i)[2].charAt(0) == '#') {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 1);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        String symbol = datas.get(i)[2].substring(1);
                        try {
                            String disp = Integer.toString(Integer.parseInt(symbol), 16);
                            while (disp.length() < 3) {
                                disp = "0" + disp;
                            }
                            opCode.add(op + "0" + disp);
                        } catch (Exception e) {
                            // TODO: handle exception
                            opCode.add(op + "100000");
                        }
                    } else if (datas.get(i)[2].charAt(0) == '@') {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 2);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        opCode.add(op + "100000");
                    } else {
                        op = Integer.toHexString(Integer.parseInt(op, 16) + 3);
                        while (op.length() < 2) {
                            op = "0" + op;
                        }
                        String[] symbol = datas.get(i)[2].split(",");
                        if (symbol.length > 1 && symbol[1].equals("X")) {
                            opCode.add(op + "900000");
                        } else {
                            opCode.add(op + "100000");
                        }
                        m = symbol[0];
                    }
                    String l = Integer.toHexString(Integer.parseInt(loc.get(i), 16) + 1);
                    while (l.length() < 6) {
                        l = "0" + l;
                    }
                    mod.add("M" + l + "05" + "+" + m);
                } else if (format.get(i) == 5) {
                    opCode.add(datas.get(i)[2].substring(2, datas.get(i)[2].length() - 1));
                } else if (format.get(i) == 6) {
                    String d = datas.get(i)[2].substring(2, datas.get(i)[2].length() - 1);
                    String op = "";
                    for (char c : d.toCharArray()) {
                        op += Integer.toHexString((int) c);
                    }
                    opCode.add(op);
                } else if (format.get(i) == 7) {
                    try {
                        String op = Integer.toHexString(Integer.parseInt(datas.get(i)[2]));
                        while (op.length() < 6) {
                            op = "0" + op;
                        }
                        opCode.add(op);
                    } catch (Exception e) {
                        // TODO: handle exception
                        opCode.add("000000");
                        String[] data = datas.get(i)[2].split("[^a-zA-Z]");
                        for (int j = 0; j < data.length; j++) {
                            String l = loc.get(i);
                            while (l.length() < 6) {
                                l = "0" + l;
                            }
                            String f = "M" + l + "06" + (j % 2 == 0 ? "+" : "-") + data[j];
                            mod.add(f);
                        }
                    }
                } else if (format.get(i) == 8) {
                    opCode.add(datas.get(i)[1].substring(3, datas.get(i)[1].length() - 1));
                } else if (format.get(i) == 9) {
                    String d = datas.get(i)[1].substring(3, datas.get(i)[1].length() - 1);
                    String op = "";
                    for (char c : d.toCharArray()) {
                        op += Integer.toHexString((int) c);
                    }
                    opCode.add(op);
                } else if (format.get(i) == 10) {
                    opCode.add("4F0000");
                } else {
                    opCode.add("");
                }
            } else {
                if (datas.get(i).length > 1 && datas.get(i)[1].equals("EXTREF")) {
                    extref = new EXTREF();
                    extref.add(datas.get(i)[2]);
                    mod.add("");
                }
                opCode.add("");
            }
        }

        mod.remove(0);
    }

    public String getDisp(int i, String symbol, ArrayList<String> loc, Boolean x, EXTREF extref,
            Map<String, String> symtab) {
        int disp = 0;
        int xbpi = 0;

        if (extref.isEXTREF(symbol)) {
            xbpi = 0;
            if (x)
                xbpi += 8;
        } else if (symtab.containsKey(symbol)) {
            disp = Integer.parseInt(symtab.get(symbol), 16) - Integer.parseInt(loc.get(i + 1), 16);
            if (-2048 <= disp && disp < 0) {
                disp = Integer.parseInt("FFF", 16) + disp + 1;
                xbpi += 2;
            } else if (0 <= disp && disp <= 2047) {
                xbpi += 2;
            } else if (0 <= disp && disp <= 4095) {
                xbpi += 4;
            }
            if (x)
                xbpi += 8;
        }

        String ans = Integer.toString(disp, 16);
        while (ans.length() < 3) {
            ans = "0" + ans;
        }

        return Integer.toString(xbpi, 16) + ans;
    }

    public String getAddress(int i, String symbol, ArrayList<String> loc, Boolean x, Boolean e, EXTREF extref,
            Map<String, String> symtab) {
        int disp = 0;
        int xbpi = 0;

        if (extref.isEXTREF(symbol)) {
            xbpi = 0;
            if (x)
                xbpi += 8;
        } else if (symtab.containsKey(symbol)) {
            disp = Integer.parseInt(symtab.get(symbol), 16) - Integer.parseInt(loc.get(i + 1), 16);
            if (-2048 <= disp && disp < 0) {
                disp = Integer.parseInt("FFF", 16) + disp + 1;
                xbpi += 2;
            } else if (0 <= disp && disp <= 2047) {
                xbpi += 2;
            } else if (0 <= disp && disp <= 4095) {
                xbpi += 4;
            }
            if (x)
                xbpi += 8;
        }

        String ans = Integer.toString(disp, 16);
        while (ans.length() < 3) {
            ans = "0" + ans;
        }

        return Integer.toString(xbpi, 16) + ans;
    }

    public ArrayList<String> getMod() {
        return mod;
    }

    public ArrayList<String> getOPCode() {
        return opCode;
    }
}
