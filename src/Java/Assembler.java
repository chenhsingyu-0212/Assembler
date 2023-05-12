package src.Java;

import java.util.ArrayList;

public class Assembler {
    public static void main(String[] args) {
        // ReaderFile
        // ReaderFile inputFile = new ReaderFile(args[0]);
        ReaderFile inputFile = new ReaderFile("Figure 2.15.txt");
        ArrayList<String[]> rawDatas = inputFile.readerLine();

        // Test Start
        // LTORG
        PassOne passOne = new PassOne(rawDatas);

        // Data Process
        DataProcess dataProcess = new DataProcess(passOne.getData(), passOne.getLOC());
        // Test End

        // WriterFile Figure 2.16
        // WriterFile outputFile_2_16 = new WriterFile(args[1]);
        WriterFile outputFile_2_16 = new WriterFile("OP_Code.txt");
        outputFile_2_16.writerLine(dataProcess.getData(false));

        // WriterFile Figure 2.17
        // WriterFile outputFile_2_17 = new WriterFile(args[2]);
        WriterFile outputFile_2_17 = new WriterFile("OP_Program.txt");
        outputFile_2_17.writerLine(dataProcess.getData(true));
    }
}
