import java.io.*;
import java.util.*;

public class AddSparseMatrix{

    static int matrix_row;
    static int matrix_col;

    static FileReader f1;
    static FileReader f2;

    static Scanner scan1;
    static Scanner scan2;

    static FileWriter fw;
    static BufferedWriter bw;

    public static int Handle_Input(String[] args) throws Exception {
        f1 = new FileReader(args[0]);
        f2 = new FileReader(args[1]);

        scan1 = new Scanner(f1);
        scan2 = new Scanner(f2);

        String cur_line1 = scan1.nextLine();
        String cur_line2 = scan2.nextLine();

        if ((!cur_line1.equals(cur_line2)) || (cur_line1 == null) || (cur_line2 == null)){
            System.err.println("The input is not valid");
            return -1;
        }

        String sp[] = cur_line1.split(", ");
        matrix_row = Integer.parseInt(sp[0]);
        matrix_col = Integer.parseInt(sp[1]);

        bw.write(matrix_row + ", " + matrix_col);
        return 0;
    }

    public static void Write(int rowindex, int colindex, int element, int flag)throws Exception{
        if (flag == 1){
            // System.out.print("\n" +rowindex + " ");
            bw.write("\n" + rowindex + " ");
        }
        else if (flag == -1){
            // System.out.print(":");
            bw.write(":");
        }
        else{
            // System.out.print(colindex + ":" + element + " ");
            bw.write(colindex + ":" + element + " ");
        }
    }

    public static void Calculate(FileReader f1,FileReader f2,Scanner scan1,Scanner scan2) throws Exception{

        String cur_line1 = null;
        String cur_line2 = null;
        int rowindex = 0;

        boolean flag = true; // true represent scan input1 file, false represent scan input2

        while( scan1.hasNext() || scan2.hasNext() ){

            if(flag) cur_line1 = scan1.nextLine();
            else cur_line2 = scan2.nextLine();

            if(!flag){
                String[] tokens1 = cur_line1.split(" ");
                String[] tokens2 = cur_line2.split(" ");
                
                if(Integer.parseInt(tokens1[0]) == Integer.parseInt(tokens2[0])) rowindex =  Integer.parseInt(tokens1[0]);
                else System.exit(1);

                Write(rowindex,0,0,1);

                int count1 = 1;
                int count2 = 1;

                while(count1 != tokens1.length  && count2 != tokens2.length && !tokens1[1].equals(":") && !tokens2[1].equals(":")){
                    String[] coltoken1 = tokens1[count1].split(":");
                    String[] coltoken2 = tokens2[count2].split(":");

                    int      colindex1  = Integer.parseInt(coltoken1[0]);
                    int      colelement1 = Integer.parseInt(coltoken1[1]);

                    int      colindex2  = Integer.parseInt(coltoken2[0]);
                    int      colelement2 = Integer.parseInt(coltoken2[1]);

                    if(colindex1 < colindex2){
                        Write(rowindex, colindex1, colelement1,0);
                        count1++;
                    }
                    else if(colindex2 < colindex1){
                        Write(rowindex, colindex2, colelement2,0);
                        count2++;
                    }
                    else {
                        if(colelement1 + colelement2 != 0)Write(rowindex, colindex1,colelement1 +colelement2,0);
                        count1++;
                        count2++;
                    }           
                }

                while(count1 != tokens1.length && !tokens1[1].equals(":")){
                    String[] coltoken1 = tokens1[count1].split(":");
                    int      colindex1  = Integer.parseInt(coltoken1[0]);
                    int      colelement1 = Integer.parseInt(coltoken1[1]);
                    Write(rowindex, colindex1, colelement1,0);
                    count1++;
                }

                while(count2 != tokens2.length && !tokens2[1].equals(":")){
                    String[] coltoken2 = tokens2[count2].split(":");
                    int      colindex2  = Integer.parseInt(coltoken2[0]);
                    int      colelement2 = Integer.parseInt(coltoken2[1]);
                    Write(rowindex, colindex2, colelement2,0);
                    count2++;
                }


                if(tokens1[1].equals(":") && !tokens2[1].equals(":")){
                    while(count2 != tokens2.length){
                        String[] coltoken2 = tokens2[count2].split(":");
                        int      colindex2  = Integer.parseInt(coltoken2[0]);
                        int      colelement2 = Integer.parseInt(coltoken2[1]);
                        Write(rowindex, colindex2, colelement2,0);
                        count2++;
                    }
                }
                if(tokens2[1].equals(":") && !tokens1[1].equals(":")){
                    while(count1 != tokens1.length){
                        String[] coltoken1 = tokens1[count1].split(":");
                        int      colindex1  = Integer.parseInt(coltoken1[0]);
                        int      colelement1 = Integer.parseInt(coltoken1[1]);
                        Write(rowindex, colindex1, colelement1,0);
                        count1++;
                    }
                }
                if(tokens2[1].equals(":") &&tokens1[1].equals(":") ){
                    Write(0,0,0,-1);
                }

            }
            
            flag = !flag;
        }


    }

    public static void main(String[] args) throws Exception{

        fw = new FileWriter(args[2]);
        bw = new BufferedWriter(fw);

        Handle_Input(args);
        Calculate(f1,f2,scan1,scan2);
        
        bw.write("\n");
        bw.flush();
        bw.close();
    }
     
}