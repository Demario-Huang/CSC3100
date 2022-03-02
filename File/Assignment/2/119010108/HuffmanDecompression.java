import java.io.*;
import java.util.*;

public class HuffmanDecompression {

    public static void main(String[] args) throws Exception {
        
        /* read the compressed file string */
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");     

        /* create a map for store the code and letter */ 
        HashMap<String,String> codemap = new HashMap<>();

        /* crearte a buffer reader to read the dictionary */
        BufferedReader reader = java.nio.file.Files.newBufferedReader(java.nio.file.Paths.get(args[1]));
        
        /* read the dictionary and transform it to map */        
        String line;
        while ((line = reader.readLine()) != null){
            String[] letterarray = line.split(":");
            int code = Integer.parseInt(letterarray[0]);
            String letter = String.valueOf(((char) code));
            codemap.put(letterarray[1],letter);
        }

        /* decompressed code */
        String decompressedCode = "";
        int index = 0;
        while (index < inputText.length())
        {
            Iterator iter = codemap.entrySet().iterator();
     
            while(iter.hasNext()){
                HashMap.Entry entry = (HashMap.Entry)iter.next();
                String code = (String)entry.getKey();
                if (inputText.indexOf(code,index)==index)
                {
                    index += code.length();
                    decompressedCode += (String)entry.getValue();
                }
            }
        }

        FileWriter fwriter = new FileWriter(args[2], false);
        BufferedWriter bwriter = new BufferedWriter(fwriter);
        bwriter.write(decompressedCode);
        bwriter.flush();
        bwriter.close();


        return;
    }
}
