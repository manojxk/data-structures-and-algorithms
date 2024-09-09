package cools.binarytrees;

/*
 Problem: Binary Tree Level Order Traversal

 Given the root of a binary tree, return the level order traversal of its nodes' values.
 (i.e., from left to right, level by level).

 Example:

 Input:
        3
       / \
      9  20
         /  \
        15   7

 Output: [[3], [9, 20], [15, 7]]

 Explanation:
 Perform level-order traversal from top to bottom, left to right at each level.
*/

/*
 Solution Steps:

 1. Use a queue to perform level-order traversal (BFS).
 2. For each level, store the nodes' values in a list and add it to the result list.
 3. Return the result.
*/

import java.util.*;

public class A15BinaryTreeLevelOrderTraversal {

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

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> levelNodes = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        TreeNode currentNode = queue.poll();
        levelNodes.add(currentNode.val);

        if (currentNode.left != null) queue.add(currentNode.left);
        if (currentNode.right != null) queue.add(currentNode.right);
      }
      result.add(levelNodes);
    }

    return result;
  }

  public static void main(String[] args) {
    A15BinaryTreeLevelOrderTraversal solution = new A15BinaryTreeLevelOrderTraversal();

    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    System.out.println(solution.levelOrder(root)); // Output: [[3], [9, 20], [15, 7]]
  }

  /*
   Time Complexity: O(n), where n is the number of nodes in the binary tree.
   Space Complexity: O(n), where n is the number of nodes in the binary tree (due to queue usage in BFS).
  */
}
