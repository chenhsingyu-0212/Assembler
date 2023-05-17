package src.Java;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ReaderFile {
    private File file;

    public ReaderFile(String path){
        this.file = new File(path);
    }

    public ArrayList<String[]> readerLine(){
        ArrayList<String[]> res = new ArrayList<>();
        
        try {
            FileReader reader = new FileReader(file);
            BufferedReader source = new BufferedReader(reader);
            for(String l = source.readLine(); l != null ; l = source.readLine()){
                if(l.length() != 0){
                    String[] arr = l.split("\t");
                    res.add(arr);
                }else{
                    String[] arr = {" "};
                    res.add(arr);
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
