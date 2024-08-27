/*
 * Problem Statement:
 * Write a function that takes in a Binary Tree and inverts it.
 * In other words, the function should swap every left node in the tree for its corresponding right node.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node.
 * Children nodes can either be BinaryTree nodes themselves or None / null.
 *
 * Example:
 * Input:
 *        1
 *       / \
 *      2   3
 *     / \ / \
 *    4  5 6  7
 *   / \
 *  8   9
 *
 * Output:
 *        1
 *       / \
 *      3   2
 *     / \ / \
 *    7  6 5  4
 *            / \
 *           9   8
 */
/*
Brute Force Solution: Recursive Approach
Approach:
Recursion: The simplest way to invert a binary tree is to recursively swap the left and right child of every node.
Base Case: If the current node is null, return (this handles leaf nodes and prevents further recursion).
Swap: For the current node, swap the left and right children.
Recursive Call: Recursively call the function for both the left and right subtrees.
Time Complexity:
O(n): Each node is visited exactly once, so the time complexity is linear.
Space Complexity:
O(h): The space complexity is determined by the recursion stack, which in the worst case (a completely unbalanced tree) is O(n), but in a balanced tree, it's O(h) where h is the height of the tree.
*/

package medium;

import java.util.LinkedList;
import java.util.Queue;

public class InvertBinaryTree {
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

  // Recursive solution to invert a binary tree
  public static void invertBinaryTree(BinaryTree root) {
    // Base case: if the tree is empty, return
    if (root == null) return;

    // Swap the left and right children
    BinaryTree temp = root.left;
    root.left = root.right;
    root.right = temp;

    // Recursively invert the left and right subtrees
    invertBinaryTree(root.left);
    invertBinaryTree(root.right);
  }

  public static void invertBinaryTreeIterative(BinaryTree root) {
    if (root == null) return;

    Queue<BinaryTree> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      BinaryTree current = queue.poll();

      // Swap the left and right children
      BinaryTree temp = current.left;
      current.left = current.right;
      current.right = temp;

      // Add children to the queue
      if (current.left != null) queue.add(current.left);
      if (current.right != null) queue.add(current.right);
    }
  }

  public static void main(String[] args) {
    // Constructing the example binary tree
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right = new BinaryTree(7);
    root.left.left.left = new BinaryTree(8);
    root.left.left.right = new BinaryTree(9);

    // Invert the binary tree
    invertBinaryTree(root);

    // The tree is now inverted, and you can traverse it to see the structure.
  }
}

/*

Optimized Solution: Iterative Approach Using a Queue
Approach:
Level-Order Traversal: Use a queue to perform a level-order traversal (breadth-first search) of the tree.
Swap: For each node, swap its left and right children.
Queue Operations: Add the children to the queue and continue the process until the queue is empty.
Time Complexity:
O(n): Each node is visited exactly once.
Space Complexity:
O(n): In the worst case, the queue will hold all nodes at the deepest level of the tree, which could be up to n/2 nodes.*/
