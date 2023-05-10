package src.Java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReaderFile {
    File file;
    String path;

    public ReaderFile(String path){
        this.file = new File(path);
        this.path = path;
    }

    public ArrayList<String> readerLine(){
        ArrayList<String> res = new ArrayList<>();
        
        try {
            FileReader reader = new FileReader(file);
            BufferedReader source = new BufferedReader(reader);
            for(String l = source.readLine(); l != null ; l = source.readLine()){
                if(l.length() != 0){
                    res.add(l);
                }
            }
            reader.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error");
        }

        return res;
    }
}
