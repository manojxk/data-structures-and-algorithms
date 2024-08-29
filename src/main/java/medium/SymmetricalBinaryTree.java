/*
 * Problem Statement:
 * Write a function that takes in a Binary Tree and returns whether that tree is symmetrical.
 * A tree is symmetrical if the left and right subtrees are mirror images of each other.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node.
 * Children nodes can either be BinaryTree nodes themselves or null.
 *
 * Example:
 *
 * tree =      1
 *           /    \
 *          2      2
 *        /  \   /   \
 *       3    4 4     3
 *      / \          / \
 *     5   6        6   5
 *
 * Output:
 * True
 */
/*Brute Force Solution
Approach:
The brute force approach involves checking if the left and right subtrees of the root are mirror images of each other. This can be done by recursively comparing the left subtree of the left child with the right subtree of the right child, and vice versa.

Time Complexity:
O(n): Where n is the number of nodes in the tree. Each node is visited once.
Space Complexity:
O(h): Where h is the height of the tree. The space complexity is due to the recursion stack.*/
package medium;

import java.util.LinkedList;
import java.util.Queue;

public class SymmetricalBinaryTree {

  // Brute Force Solution: Recursive Approach
  public static boolean isSymmetrical(BinaryTree root) {
    if (root == null) return true;
    return isMirror(root.left, root.right);
  }

  // Helper method to check if two subtrees are mirrors of each other
  private static boolean isMirror(BinaryTree tree1, BinaryTree tree2) {
    // Base cases: both null or one null
    if (tree1 == null && tree2 == null) return true;
    if (tree1 == null || tree2 == null) return false;

    // Check if current nodes are equal and their subtrees are mirrors
    return (tree1.value == tree2.value)
        && isMirror(tree1.left, tree2.right)
        && isMirror(tree1.right, tree2.left);
  }
  public static boolean isSymmetricalIterative(BinaryTree root) {
    if (root == null) return true;

    Queue<BinaryTree> queue = new LinkedList<>();
    queue.add(root.left);
    queue.add(root.right);

    while (!queue.isEmpty()) {
      BinaryTree tree1 = queue.poll();
      BinaryTree tree2 = queue.poll();

      if (tree1 == null && tree2 == null) continue;
      if (tree1 == null || tree2 == null) return false;
      if (tree1.value != tree2.value) return false;

      queue.add(tree1.left);
      queue.add(tree2.right);
      queue.add(tree1.right);
      queue.add(tree2.left);
    }

    return true;
  }


  // Helper class to define the structure of a BinaryTree node
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  public static void main(String[] args) {
    // Constructing the tree
    BinaryTree tree = new BinaryTree(1);
    tree.left = new BinaryTree(2);
    tree.right = new BinaryTree(2);
    tree.left.left = new BinaryTree(3);
    tree.left.right = new BinaryTree(4);
    tree.right.left = new BinaryTree(4);
    tree.right.right = new BinaryTree(3);
    tree.left.left.left = new BinaryTree(5);
    tree.left.left.right = new BinaryTree(6);
    tree.right.right.left = new BinaryTree(6);
    tree.right.right.right = new BinaryTree(5);

    // Checking if the tree is symmetrical
    System.out.println(isSymmetrical(tree)); // Output: True
  }
}

/*
Optimized Solution
Approach:
The optimized solution involves using the same recursive method as above, but an iterative approach using a queue or stack can also be used. This method is essentially a BFS or DFS traversal where we compare the nodes at the same level.

Time Complexity:
O(n): Similar to the recursive approach, where n is the total number of nodes in the tree.
Space Complexity:
O(h): Space complexity is determined by the queue/stack used in the iterative approach, which will store up to h levels of nodes.*/
