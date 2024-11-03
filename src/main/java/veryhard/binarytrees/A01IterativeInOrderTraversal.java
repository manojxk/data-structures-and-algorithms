package veryhard.binarytrees;

/*
 * Problem Statement:
 *
 * Given the root of a binary tree, perform an in-order traversal iteratively.
 * In-order traversal visits the nodes of a binary tree in the following order:
 * - Traverse the left subtree
 * - Visit the root node
 * - Traverse the right subtree
 *
 * Example:
 *
 * Input:
 *      1
 *       \
 *        2
 *       /
 *      3
 *
 * Output: [1, 3, 2]
 *
 * Explanation:
 * In-order traversal of the tree visits nodes in this order: 1 -> 3 -> 2.
 */

/*
 * Solution Approach:
 *
 * 1. Use a stack to simulate the recursive function calls used in a recursive in-order traversal.
 * 2. Start from the root node and keep pushing all left nodes into the stack.
 * 3. When there are no more left nodes, pop from the stack, visit the node, and then process its right subtree.
 * 4. Continue this process until the stack is empty and all nodes are visited.
 */

import java.util.*;

public class A01IterativeInOrderTraversal {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  // Function to perform in-order traversal iteratively
  public static List<Integer> inOrderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;

    // Loop until all nodes are processed
    while (current != null || !stack.isEmpty()) {
      // Go to the leftmost node of the current subtree
      while (current != null) {
        stack.push(current);
        current = current.left;
      }

      // Process the node at the top of the stack
      current = stack.pop();
      result.add(current.val); // Visit the node

      // Move to the right subtree
      current = current.right;
    }

    return result;
  }

  // Main function to test the Iterative In-order Traversal implementation
  public static void main(String[] args) {
    // Constructing a binary tree
    //      1
    //       \
    //        2
    //       /
    //      3
    TreeNode root = new TreeNode(1);
    root.right = new TreeNode(2);
    root.right.left = new TreeNode(3);

    // Output: [1, 3, 2]
    List<Integer> result = inOrderTraversal(root);
    System.out.println("In-order traversal: " + result);
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the binary tree. Each node is visited once.
   *
   * Space Complexity:
   * O(h), where h is the height of the tree. This is due to the stack storing up to h nodes (in the worst case of a skewed tree).
   */
}
