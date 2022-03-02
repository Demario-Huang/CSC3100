//
//  Created by Jiawei Lian, 21/11/08. 
//  levelOrder, levelOrderBottom, and zigzagLevelOrder are modified from LeetCode Solution.
//  https://leetcode-cn.com/problems/binary-tree-level-order-traversal/solution/er-cha-shu-de-ceng-xu-bian-li-by-leetcode-solution/
//  https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/solution/er-cha-shu-de-ceng-ci-bian-li-ii-by-leetcode-solut/
//  https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/solution/er-cha-shu-de-ju-chi-xing-ceng-xu-bian-l-qsun/
//

import java.util.*;

public class Traversal {

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
        if (root == null) {
            return res;
        }

        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                Node node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            res.add(level);
        }

        return res;
    }

    public List<List<Integer>> levelOrderBottom(Node root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if (root == null) {
            return res;
        }
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                level.add(node.val);
                Node left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }
            }
            res.add(0, level);
        }
        return res;
    }

    public List<List<Integer>> zigzagLevelOrder(Node root) {
        List<List<Integer>> res = new LinkedList<List<Integer>>();
        if (root == null) {
            return res;
        }

        Queue<Node> nodeQueue = new LinkedList<Node>();
        nodeQueue.offer(root);
        boolean isOrderLeft = true;

        while (!nodeQueue.isEmpty()) {
            Deque<Integer> levelList = new LinkedList<Integer>();
            int size = nodeQueue.size();
            for (int i = 0; i < size; ++i) {
                Node curNode = nodeQueue.poll();
                if (isOrderLeft) {
                    levelList.offerLast(curNode.val);
                } else {
                    levelList.offerFirst(curNode.val);
                }
                if (curNode.left != null) {
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    nodeQueue.offer(curNode.right);
                }
            }
            res.add(new LinkedList<Integer>(levelList));
            isOrderLeft = !isOrderLeft;
        }

        return res;
    }

}