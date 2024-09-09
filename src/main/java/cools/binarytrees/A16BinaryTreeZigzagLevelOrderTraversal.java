package cools.binarytrees;

/*
 Problem: Binary Tree Zigzag Level Order Traversal

 Given the root of a binary tree, return the zigzag level order traversal of its nodes' values.
 (i.e., from left to right, then right to left for the next level and alternate between).

 Example:

 Input:
        3
       / \
      9  20
         /  \
        15   7

 Output: [[3], [20, 9], [15, 7]]

 Explanation:
 Perform zigzag traversal by alternating between left-to-right and right-to-left at each level.
*/

/*
 Solution Steps:

 1. Use a queue to perform level-order traversal (BFS).
 2. Use a flag to alternate between left-to-right and right-to-left traversal at each level.
 3. At each level, add the node values to a list and reverse it if the flag is set to right-to-left.
 4. Return the result list.
*/

import java.util.*;

public class A16BinaryTreeZigzagLevelOrderTraversal {

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }
  }

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    boolean leftToRight = true;

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      LinkedList<Integer> levelNodes = new LinkedList<>();

      for (int i = 0; i < levelSize; i++) {
        TreeNode currentNode = queue.poll();

        // Add nodes in left-to-right or right-to-left order based on the flag
        if (leftToRight) {
          levelNodes.addLast(currentNode.val);
        } else {
          levelNodes.addFirst(currentNode.val);
        }

        if (currentNode.left != null) queue.add(currentNode.left);
        if (currentNode.right != null) queue.add(currentNode.right);
      }

      result.add(levelNodes);
      leftToRight = !leftToRight; // Toggle the direction for the next level
    }

    return result;
  }

  public static void main(String[] args) {
    A16BinaryTreeZigzagLevelOrderTraversal solution = new A16BinaryTreeZigzagLevelOrderTraversal();

    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    System.out.println(solution.zigzagLevelOrder(root)); // Output: [[3], [20, 9], [15, 7]]
  }

  /*
   Time Complexity: O(n), where n is the number of nodes in the binary tree.
   Space Complexity: O(n), where n is the number of nodes in the binary tree (due to queue usage in BFS).
  */
}
