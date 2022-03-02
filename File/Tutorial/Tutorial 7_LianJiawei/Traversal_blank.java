//
//  Created by Jiawei Lian, 21/11/08. 
//

import java.util.*;

public class Traversal_blank {

    public static List<List<Integer>> test_level_order_1(BinarySearchTree BST) {
        Node root = BST.getRoot();
        List<List<Integer>> res = BST.levelOrder(root);
        return res;
    }

    public static List<List<Integer>> test_level_order_2(BinarySearchTree BST) {
        Node root = BST.getRoot();
        List<List<Integer>> res = BST.levelOrderBottom(root);
        return res;
    }

    public static List<List<Integer>> test_zigzag(BinarySearchTree BST) {
        Node root = BST.getRoot();
        List<List<Integer>> res = BST.zigzagLevelOrder(root);
        return res;
    }

    public static void main(String[] args) {
        int[] sample = { 15, 6, 18, 7, 3, 4, 2, 13, 9, 17, 20 };
        BinarySearchTree BST = new BinarySearchTree();

        for (int i = 0; i < sample.length; i++) {
            BST.insert(sample[i]);
        }

        BST.inOrder();

        int x = Integer.parseInt(args[0]);
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        if (x == 1) {
            res = test_level_order_1(BST);
            
        } else if (x == 2) {
            res = test_level_order_2(BST);
        } else if (x == 3) {
            res = test_zigzag(BST);
        }

        System.out.println(res);
    }

}

class Node {
    int val;
    Node left;
    Node right;

    Node() {}

    Node(int val) {
        this.val = val;
    }

    Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class BinarySearchTree {
    private Node root;

    public BinarySearchTree() {}

    public BinarySearchTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void insert(int x) {
        root = insert(x, root);
    }

    Node insert(int x, Node n) {
        if (n == null)
            return new Node(x);

        if (x < n.val)
            n.left = insert(x, n.left);
        else
            n.right = insert(x, n.right);

        return n;
    }

    private void inOrder(Node x) {
        if (x != null) {
            inOrder(x.left);
            System.out.print(x.val + " ");
            inOrder(x.right);
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        return res;
    }

    public List<List<Integer>> levelOrderBottom(Node root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();

        return res;
    }

    public List<List<Integer>> zigzagLevelOrder(Node root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();

        return res;
    }

}