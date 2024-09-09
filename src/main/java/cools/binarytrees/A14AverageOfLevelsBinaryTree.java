package cools.binarytrees;

/*
 Problem: Average of Levels in Binary Tree

 Given a binary tree, return the average value of the nodes on each level in the form of an array.

 Example:

 Input:
        3
       / \
      9  20
         /  \
        15   7

 Output: [3, 14.5, 11]

 Explanation:
 The average of nodes on level 0 is 3, on level 1 is (9 + 20) / 2 = 14.5, and on level 2 is (15 + 7) / 2 = 11.
*/

/*
 Solution Steps:

 1. Perform a level-order traversal (BFS).
 2. At each level, compute the sum of node values and divide by the number of nodes at that level to get the average.
 3. Return the list of averages.
*/

import java.util.*;

public class A14AverageOfLevelsBinaryTree {

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

  public List<Double> averageOfLevels(TreeNode root) {
    List<Double> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      double levelSum = 0;

      for (int i = 0; i < levelSize; i++) {
        TreeNode currentNode = queue.poll();
        levelSum += currentNode.val;

        if (currentNode.left != null) queue.add(currentNode.left);
        if (currentNode.right != null) queue.add(currentNode.right);
      }
      result.add(levelSum / levelSize); // Add the average value for this level
    }

    return result;
  }

  public static void main(String[] args) {
    A14AverageOfLevelsBinaryTree solution = new A14AverageOfLevelsBinaryTree();

    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    System.out.println(solution.averageOfLevels(root)); // Output: [3.0, 14.5, 11.0]
  }

  /*
   Time Complexity: O(n), where n is the number of nodes in the binary tree.
   Space Complexity: O(n), where n is the number of nodes in the binary tree (due to queue usage in BFS).
  */
}
