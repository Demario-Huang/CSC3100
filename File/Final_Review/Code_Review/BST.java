import java.io.*;
import java.util.*;

public class BST{

    public static class TreeNode{
        int element;
        TreeNode left;
        TreeNode right;
        TreeNode parent;

        public TreeNode(int element){
            this.element = element;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    
    static TreeNode ROOT = null;

    public static TreeNode FindRoot(){
        return ROOT;
    }

    /* search the node based on the value */
    /* there might be a problem that there will be a value in 2 same node, and we just delete the first one */
    public static TreeNode Search(int element, TreeNode current){ 
        TreeNode result = null;
        if(current != null){
            if(current.element == element) return current;
            else if(element < current.element) result = Search(element, current.left);
            else result = Search(element, current.right);
        }
        return result;
    }

    public static TreeNode FindMin(TreeNode current){
        TreeNode result = null;
        if(current.left != null)result = FindMin(current.left);
        else result = current;
        return result;
    }

    public static TreeNode FindMax(TreeNode current){
        TreeNode result = null;
        if(current.right != null)result = FindMax(current.right);
        else result = current;
        return result;
    }

    public static TreeNode FindSuccesor(int element){
        TreeNode result = null;
        TreeNode node = Search(element,ROOT);
        if(node == null) return null;
        
        if(node.right != null) result = FindMin(node.right);
        else{
            TreeNode trailing = node.parent;
            while(trailing != null && trailing.right == node){ // reach the root or find the left child
                node = trailing;
                trailing = node.parent;
            }
            result = trailing;
        }
        return result;
    }

    public static TreeNode FindPredecessor(int element){
        TreeNode result = null;
        TreeNode node = Search(element, ROOT);  
        if(node == null) return null;

        if(node.left != null) result = FindMax(node.left);
        else{
            TreeNode trailing = node.parent;
            while(trailing != null && trailing.left == node){ // reach the root or find the right child 
                node = trailing;
                trailing = node.parent;
            }
            result = trailing;
        }
        return result;
    }

    public static TreeNode Delete(int element){
        TreeNode result = null;
        if(ROOT == null) return null;
        else{
            TreeNode node = Search(element, ROOT);
            if(node == null) return null;
            else{
                if(node.left == null && node.right == null){
                    if(node.parent.left == node) node.parent.left = null;
                    else node.parent.right = null;
                    node = null;
                }
                else if(node.left == null || node.right == null){
                    if(node.parent.left ==  node){
                        if(node.left != null){
                            node.parent.left = node.left;
                            node = null;
                        }else{
                            node.parent.left = node.right;
                            node = null;
                        }
                    }else{
                        if(node.left != null){
                            node.parent.right = node.left;
                            node = null;
                        }else{
                            node.parent.right = node.right;
                            node = null;
                        }
                    }
                }
                else{
                    TreeNode target = FindSuccesor(node.element);
                    if(target.right != null) target.parent.right = target.right;
                    node = target;
                    target = null; 
                }
            }
        }

        return result;
    }

    public static void AddNode(int element){
        if(ROOT==null)  ROOT = new TreeNode(element);
        else{
            TreeNode newNode = new TreeNode(element);
            TreeNode current = ROOT;
            TreeNode trailing = current;
            while(current != null){
                trailing = current;
                if(current.element < newNode.element) current = current.right;
                else current = current.left;
            }
            if(newNode.element < trailing.element) trailing.left = newNode;
            else trailing.right = newNode;
            newNode.parent = trailing;
        }
    }

    /* Inorder Traversing */
    public static void Inorder_Print(TreeNode node){
        if(node == null) return;
        System.out.println("num is " + node.element);
        Inorder_Print(node.left);
        Inorder_Print(node.right);
    }


    public static void main(String[] args) throws Exception{
        System.out.println("------This Is The BST Demo------");
        AddNode(15);
        AddNode(6);
        AddNode(18);
        AddNode(3);
        AddNode(2);
        AddNode(4);
        AddNode(7);
        AddNode(9);
        AddNode(13);
        AddNode(18);
        AddNode(17);
        AddNode(20);
        Inorder_Print(ROOT);
        TreeNode result = Search(17,ROOT);
        if(result != null)System.out.println(result.parent.element);
        else System.out.println("null!");

        result = FindPredecessor(7);
        if(result != null)System.out.println(result.element);
    }
}