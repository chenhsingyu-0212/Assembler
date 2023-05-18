package src.Java;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class WriterFile {
    private File file;

    public WriterFile(String path){
        this.file = new File(path);
    }

    public void writerLine(ArrayList<String> res){
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("");
            for(String l : res){
                writer.append(l + "\n");
            }
            writer.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error");
        }
    }
}
