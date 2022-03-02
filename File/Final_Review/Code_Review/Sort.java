import java.util.ArrayList;

// import java.io.*;
// import java.util.*;

public class Sort {
    
    /* Always keep the complexity for O(N^2) */
    public static int[] bubble(int[] array){
        int temp = 0;
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array.length; j++){
                if(array[i] < array[j]){
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
        return array;
    }

    /* Always keep the complexity for O(N^2) */
    public static int[] insertion(int[] array){
        int temp = 0;
        for(int i = 0; i < array.length; i++){
            int minindex = i;
            for(int j = minindex; j < array.length; j++){
                if(array[j] < array[minindex]){
                    minindex = j;
                }
            }
            temp = array[i];
            array[i] = array[minindex];
            array[minindex] = temp;
        }
        return array;
    }

    public static int partion(int[] array , int left, int right, int pivot){
        int new_pivot = left;
        // swap(array[pivot], array[right]);
        for(int i = left; i < right; i++){
            if(array[i] < array[pivot]){
                new_pivot++;
                // swap
            }
        }
        // swap back;
        return new_pivot;
    }

    /* Worst case is O(N^2), average case is O(NlogN) */
    public static int[] quick(int[] array, int left, int right){
        if(right > left){
            int pivot = left;
            int new_pivot = partion(array, left, right, pivot);
            quick(array, new_pivot + 1,right);
            quick(array, left ,new_pivot -1);
        }
        return array;
    }

    public static int[] merge(int[] array){
        return array;
    }

    public static void Print(String name,int[] array){
        System.out.println("Sort name: " + name);
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] +" ");
        }
        System.out.println("  ");
    }



    public static void main(String[] args) {
        System.out.println("This is the sorting demo ");
        int[] inital = new int[] {2,-3,14,67,23,41,5,11,3,100};
        int[] result = new int[10];
        result = bubble(inital);
        Print("bubble", result);
        result = insertion(result);
        Print("insertion", result);
    }
}
