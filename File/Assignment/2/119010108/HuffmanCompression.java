import java.io.*;
import java.util.*;


public class HuffmanCompression {

    /* a class of tree node  */
    public static class TreeNode{

        String element;
        TreeNode left;
        TreeNode right;

        public TreeNode(String element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }

        public TreeNode(String element, TreeNode left, TreeNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
       
        public String getname(){
            return this.element;
        }
    }

    /* used to get the minimum weight value in Huffmap */
    public static String getMinValue(HashMap<String,Integer> map){

        String result = "";
        if (map == null || map.size() == 0) return result;
        Collection<Integer> myvalues = map.values();
        Object[] obj= myvalues.toArray();
        Arrays.sort(obj);

        Integer value = Integer.parseInt( obj[0].toString() );
        
        for ( Map.Entry<String,Integer> entry : map.entrySet() ){
            if (value.equals(entry.getValue())){
                result = entry.getKey();
                return result;
            }
        }

        return result;
    }


    public static String getCompressedCode(String inputText, String[] huffmanCodes) {
        String compressedCode = "";

        for (int i = 0; i < inputText.length(); i++) {
            int index = (int) inputText.charAt(i);
            compressedCode += huffmanCodes[index];
        }

        return compressedCode;
    }
    
    public static void ConstructTree( ArrayList<TreeNode> HuffRoots, String Key1, String Key2, String newKey ){
            boolean FindKey1 = false;
            boolean FindKey2 = false;

            TreeNode cpkey1 = null;
            TreeNode cpkey2 = null;

            for ( TreeNode node : HuffRoots ){
                if(node.getname().equals(Key1)) {
                    FindKey1 = true;
                    cpkey1 = node;
                } 
                if (node.getname().equals(Key2)){
                    FindKey2 = true;
                    cpkey2 = node;
                } 
            }

            if (!FindKey1 && !FindKey2){
                TreeNode node1 = new TreeNode(Key1);
                TreeNode node2 = new TreeNode(Key2);
                TreeNode newroot = new TreeNode(newKey,node1,node2);
                HuffRoots.add(newroot);
            }

            if (FindKey1 && FindKey2){
                TreeNode newroot = new TreeNode(newKey, cpkey1,cpkey2);
                HuffRoots.add(newroot);
            }

            if (FindKey1 && !FindKey2){
                TreeNode node2 = new TreeNode(Key2);
                TreeNode newroot = new TreeNode(newKey,cpkey1,node2);
                HuffRoots.add(newroot);
            }

            if (!FindKey1 && FindKey2){
                TreeNode node1 = new TreeNode(Key1);
                TreeNode newroot = new TreeNode(newKey,node1, cpkey2);
                HuffRoots.add(newroot);
            }
    }

    /* used to store the relationship between letter and its binary code in Hufftree */
    public static HashMap<String,String> letterCodeMap= new HashMap<>();  

    public static void Tranverse(TreeNode node, String num){
        
        if (node.left == null && node.right == null){
            letterCodeMap.put(node.getname(), num);
        }
        else{
            Tranverse(node.left, num + "0");
            Tranverse(node.right,num + "1");
        }
    }


    public static String[] getHuffmanCode(String inputText) throws InterruptedException {
        String[] huffmanCodes = new String[128];

        /* initialize the dictionary to null */
        for ( String i : huffmanCodes ) i = null;

        /* Create a huffmap to store the  relationship between letter and its weight */
        HashMap<String,Integer> Huffmap = new HashMap<>();

        /* used to store all the root nodes, the last one represent the ROOT in HuffTree */
        ArrayList<TreeNode> HuffRoots = new ArrayList<>();

        /* count the weight of each letter */
        for (int i = 0; i < inputText.length(); ++i){
            String inputletter = String.valueOf(inputText.charAt(i));
            if ( Huffmap.containsKey(inputletter)) Huffmap.put(inputletter, Huffmap.get(inputletter) + 1);
            else Huffmap.put(inputletter,1);
        }
       
        /* used the Huffmap to create a Hufftree */
        while ( Huffmap.size() > 1 ){

            String Key1 = getMinValue(Huffmap);
            Integer Value1 = Huffmap.get(Key1);
            Huffmap.remove(Key1);

            String Key2 = getMinValue(Huffmap);
            Integer Value2 = Huffmap.get(Key2);
            Huffmap.remove(Key2);

            String newKey = Key1 + "&" + Key2;
            Huffmap.put(newKey, Value1 + Value2);

            ConstructTree(HuffRoots, Key1, Key2, newKey);
        }

        TreeNode root = HuffRoots.get(HuffRoots.size() -1);
        
        /* tranvese the tree to get the binary number */
        Tranverse(root,"");

        /* store the binary number into dictionary */
        Iterator iter = letterCodeMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            int index = (int)key.toString().charAt(0);
            huffmanCodes[index] = value.toString();
        }
      
        return huffmanCodes;
    }
    public static void main(String[] args) throws Exception {

        /* read the input string  */
        String inputText = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(args[0])), "US-ASCII");
        
        /* used to store the dictionary */
        String[] huffmanCodes = HuffmanCompression.getHuffmanCode(inputText);
        
        /* output the dictionary */
        FileWriter fwriter1 = new FileWriter(args[1], false);
        BufferedWriter bwriter1 = new BufferedWriter(fwriter1);
        for (int i = 0; i < huffmanCodes.length; i++) 
            if (huffmanCodes[i] != null) {
                bwriter1.write(Integer.toString(i) + ":" + huffmanCodes[i]);
                bwriter1.newLine();
            }
        bwriter1.flush();
        bwriter1.close();

        /* output the compressedCode */
        String compressedCode = HuffmanCompression.getCompressedCode(inputText, huffmanCodes);
        FileWriter fwriter2 = new FileWriter(args[2], false);
        BufferedWriter bwriter2 = new BufferedWriter(fwriter2);
        if (compressedCode != null) 
            bwriter2.write(compressedCode);
        bwriter2.flush();
        bwriter2.close();
    }
}
