import java.io.*;
import java.util.*;


public class  PrioirtyQueue {
    
    static int current_index = 0;
    static int capacity = 10;
    static Node[] PrioirtyQueue = new Node[capacity];

    public static class Node{
        int priority;
        String name;
        public Node(int priority, String name) {
            this.priority = priority;
            this.name = name;
        }
        public int getPriority(){
            return this.priority;
        }
    }

    public static boolean IsFull(){
        return (current_index == capacity -1);
    }

    public static boolean IsEmpty(){
        return (current_index == 0);
    }

    
    public static void PercolateUp(){
        Node temp = PrioirtyQueue[current_index];
        int index = current_index -1 ;
        while(current_index > 1 && (temp.getPriority() > PrioirtyQueue[index/2].getPriority()) ){
            PrioirtyQueue[index] = PrioirtyQueue[index/2];
            index = index/2;
        }
        PrioirtyQueue[index] = temp;
    }

    public static void PercolateDown(){
        int index = 0;
        Node temp = PrioirtyQueue[0];
        while(index < current_index && ((PrioirtyQueue[index].getPriority() < PrioirtyQueue[index*2+1].getPriority()) || (PrioirtyQueue[index].getPriority() < PrioirtyQueue[index*2+1+2].getPriority())) )  {
            // if less than left child, switch left child
            // if less than right child, swicth right child
        }
    }

    public static Node Delete(){
        if(IsEmpty()) return null;
        Node result = PrioirtyQueue[0];
        PrioirtyQueue[0] = PrioirtyQueue[current_index];
        PrioirtyQueue[current_index] =  null;
        PercolateDown();
        current_index--;
        return result;
    }

    public static void Insert(int priority, String name){
        if(IsFull()) return;
        Node node = new Node(priority, name);
        PrioirtyQueue[current_index] = node;
        current_index++;
        PercolateUp();
    }


    public static void main(String[] args){
        System.out.println("This is the PrioirtyQueue demo");

    }
}
