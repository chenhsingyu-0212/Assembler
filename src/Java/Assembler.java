package src.Java;

import java.util.ArrayList;

public class Assembler {
    public static void main(String[] args) {
        // ReaderFile inputFile = new ReaderFile("Figure 2.15.txt");
        ReaderFile inputFile = new ReaderFile(args[0]);
        ArrayList<String> line = inputFile.readerLine();

        // WriterFile outputFile = new WriterFile("OutputFile.txt");
        WriterFile outputFile = new WriterFile(args[1]);
        ArrayList<String> ans = new ArrayList<>();

        for(String l : line){
            String[] arr = l.split("    ");
            for(String a : arr){
                ans.add(a);
            }
            ans.add("\n");
        }

        outputFile.writerLine(ans);
    }
}
