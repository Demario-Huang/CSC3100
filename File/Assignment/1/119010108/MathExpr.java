import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;


public class MathExpr {
    public static double parse(String str) {

        /* set an NaN number */
        double invalid = 0.0/0.0;

        /* transfer the string to processible array */
        String[] arr = ToArray(str);
        if (arr[0] == null) {
            return invalid;
        }

        /* convert the infix to suffix */
        String[] postarr = ToSuffix(arr);
        if (postarr[0].equals("error")) {
            return invalid;
        }

        /* computation */
        int [] result = Compute(postarr);
        if (result[0] == 0){
            return invalid;
        }
        return result[1];
    }

    public static boolean isOper(String str) {
        if (   str.equals("(")
            || str.equals(")")
            || str.equals("*") 
            || str.equals("/")
            || str.equals("+")
            || str.equals("-")
            || str.equals("sin")
            || str.equals("cos")
            || str.equals("tan")
            || str.equals("sqrt")){ 
                return true; 
        } 
        else return false;
    }

    public static boolean isNum(char ch){
        int asc = ch;
        if (asc < 48  || asc > 57){
            return false;
        }
        else return true;
    }

    public static String[] ToArray(String str){

        

        int strlen = str.length();
        String[] Array = new String[strlen];
        char[] buffer = new char[strlen]; // store the digit into buffer, and combine them into number when needed
        int Index = 0;
        int buffercount = 0;

        for (int i = 0; i < strlen; i++){

            char ch = str.charAt(i);

            if (ch == '-' && i == 0){
                Array[0] = "-1"; // if the negative number occur in first position, change it to -1 * number/function 
                Array[1] = "*";
                Index+=2;
            }

            else if (isNum(ch) || ch == '.' ){ 
                if (i == 0 && ch == '.'){
                    Array[0] = null; // let array[0] to null represent invalid 
                    return Array;
                }
                buffer[buffercount] = ch;
                buffercount++;
            }

            else if(ch == ' '){
                if (i != 0 && (str.charAt(i-1) == '.' || str.charAt(i+1) == '.')){
                    Array[0] = null;
                    return Array;
                }
                if (buffercount != 0){
                    String number = "";
                        for (int j = 0; j < buffercount; j++){
                            number += buffer[j];
                        }
                        Array[Index] = number;
                        Index++;
                        buffercount = 0;
                }
                else{ 
                    // do nothing 
                }
            }

            else{
                boolean Check_operator =(  (str.charAt(i) == '(') 
                                        || (str.charAt(i) == ')')
                                        || (str.charAt(i) == '+') 
                                        || (str.charAt(i) == '-')
                                        || (str.charAt(i) == '*')
                                        || (str.charAt(i) == '/')
                                        || (  (i+ 3< strlen) &&   str.substring(i,i+3).equals("sin") )  
                                        || (  (i+ 3< strlen) &&   str.substring(i,i+3).equals("cos") ) 
                                        || (  (i+ 3< strlen) &&   str.substring(i,i+3).equals("tan") )
                                        || (  (i+ 4< strlen) &&   str.substring(i,i+4).equals("sqrt")) );
                
                if (Check_operator){

                    if (buffercount != 0){
                        String number = "";
                        for (int j = 0; j < buffercount; j++){
                            number += buffer[j];
                        }
                        Array[Index] = number;
                        Index++;
                        buffercount = 0;
                    }

                    if (i + 3 < strlen) {
                        if (str.substring(i,i+3).equals("sin") || str.substring(i,i+3).equals("cos")||str.substring(i,i+3).equals("tan") ) {
                            Array[Index] = str.substring(i,i+3);
                            Index++;
                            i += 2;
                        }
                    }
                    
                    if ( i + 4 < strlen){
                        if (str.substring(i,i+4).equals("sqrt")){
                            Array[Index] = str.substring(i,i+4);
                            Index++;
                            i += 3;
                        }
                    }
                    
                    if ( ch == '(' || ch == ')' || ch == '+' || ch == '-' || ch == '*' || ch == '/'){
                        if (ch == '-' && str.charAt(i-1) == '('){
                            buffer[buffercount] = ch;
                            buffercount++;
                        }
                        else{
                            String op = String.valueOf(str.charAt(i));
                            Array[Index] = op;
                            Index++;
                        }
                    }
                }

                else{
                    Array[0] = null;
                    return Array;
                }
            }
        }

        if (buffercount != 0){
            String number = "";
            for (int j = 0; j < buffercount; j++){
                number += buffer[j];
            }
            Array[Index] = number;
            Index++;
            buffercount = 0;
        }

        return Array;
    }
    
    public static int getPriority(String str){
        switch (str){
            case "+" : return 1;
            case "-" : return 1;
            case "*":  return 2;
            case "/":  return 2;
            case "sin":  return 3;
            case "cos":  return 3;
            case "tan": return 3;
            case "sqrt" :  return 3;
            default: return 999;
        }
    }

    public static String[] ToSuffix(String[] arr){

        try{

            Stack<String> operstack = new Stack<String>();
            int arrlen = arr.length;

            for (int i = 0; i < arr.length; i++){
                if (arr[i] == null) arrlen--;
            }

            String[]  Array = new String[arrlen];

            int index = 0;

            for (int i = 0; i < arrlen; i++){
                if (isOper(arr[i])){
                    if (operstack.isEmpty()){
                        operstack.push(arr[i]);
                    }
                    else{
                        if (arr[i].equals("(")){
                            operstack.push(arr[i]);
                        }
                        else if (arr[i].equals(")")){
                            while (operstack.peek().equals("(") == false){
                                Array[index] = operstack.pop();
                                index++;
                            }
                            operstack.pop(); 
                        }
                        else{
                            if (operstack.peek().equals("(")){
                                operstack.push(arr[i]);
                            }
                            else{
                                if (!operstack.isEmpty() &&  (getPriority(arr[i]) > getPriority(operstack.peek()) ) ){
                                    operstack.push(arr[i]);
                                }
                                else{
                                    while (!operstack.isEmpty() && (getPriority(arr[i]) <= getPriority(operstack.peek()) ) ){
                                        Array[index] = operstack.pop();
                                        index++;
                                        if (operstack.isEmpty() || operstack.peek().equals("(")){
                                            operstack.push(arr[i]);
                                            break;
                                        }
                                    }
                                    if (getPriority(arr[i]) > getPriority(operstack.peek()) ){
                                        operstack.push(arr[i]);
                                    }
                                }
                            }
                        }
                    }
                }
                
                else if (!isOper(arr[i])){
                    Array[index] = arr[i];
                    index++;
                }
                else {
                    // do nothing
                }
            }

            while (!operstack.isEmpty()){
                Array[index] = operstack.pop();
                index++;
            }
            String[] result = new String[index];
            for (int j = 0; j < index; j++){
                result[j] = Array[j];
            }
            return result;
            }
        catch(Exception e){
            String[] error = new String[1];
            error[0] = "error";
            return error;
        }

        
    }

    public static int[] Compute(String[] arr){

        try{
            // create a double array, first represent the signal bit, second is the result 
            int[] result = new int[2];

            // set the signal bit to 1, which means the caculation now is valid
            result[0] = 1;

            Stack<String> numstack = new Stack<String>();
            int arrlen = arr.length;

            for (int i = 0; i < arrlen; i++){
                if (!isOper(arr[i])){
                    numstack.push(arr[i]);
                }

                else{
                    if (arr[i].equals("+")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double num2 = Double.parseDouble(numstack.pop());
                        Double tmp = (double) num1 + num2;
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("-")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double num2 = Double.parseDouble(numstack.pop());
                        Double tmp = (double) num2 - num1;
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("*")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double num2 = Double.parseDouble(numstack.pop());
                        Double tmp = (double)num1 * num2;
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("/")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double num2 = Double.parseDouble(numstack.pop());
                        Double tmp = (double) num2 / num1;
                        if ( Double.isNaN(tmp) ){
                            result[0] = 0;
                        }
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("sin")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double tmp = (double)Math.sin(num1);
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("cos")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double tmp = (double)Math.cos(num1);
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("tan")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double tmp = (double)Math.tan(num1);
                        numstack.push(tmp.toString());
                    }

                    else if (arr[i].equals("sqrt")){
                        Double num1 = Double.parseDouble(numstack.pop());
                        Double tmp = (double)Math.sqrt(num1);
                        if ( Double.isNaN(tmp) ){
                            result[0] = 0;
                        }
                        numstack.push(tmp.toString());
                    }

                    else{
                        // do nothing
                    }
                }
            }

            Double compute_result = Double.parseDouble(numstack.pop());

            if (!numstack.isEmpty()){
                result[0] = 0;
            }

            double tmp = Math.round(compute_result);
            result[1] = (int)tmp; 
            return result;
        }
        
        catch(Exception e){
            int[] error = new int[2];
            error[0] = 0; 
            error[1] = 0;
            return error;
        }
        
    }
}
