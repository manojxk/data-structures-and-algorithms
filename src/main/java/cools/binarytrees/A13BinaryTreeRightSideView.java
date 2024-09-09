package cools.binarytrees;

/*
 Problem: Binary Tree Right Side View

 Given the root of a binary tree, imagine yourself standing on the right side of it,
 return the values of the nodes you can see ordered from top to bottom.

 Example:

 Input:
        1
       / \
      2   3
       \   \
        5   4

 Output: [1, 3, 4]

 Explanation:
 The right side view is 1 -> 3 -> 4.
*/

/*
 Solution Steps:

 1. Perform a level-order traversal (BFS).
 2. For each level, store the last node visible from the right side.
 3. Return the list of visible nodes from all levels.
*/

import java.util.*;

public class A13BinaryTreeRightSideView {

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

  public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      TreeNode currentNode = null;

      for (int i = 0; i < levelSize; i++) {
        currentNode = queue.poll();

        if (currentNode.left != null) queue.add(currentNode.left);
        if (currentNode.right != null) queue.add(currentNode.right);
      }
      result.add(currentNode.val); // The last node in the level is visible from the right side
    }

    return result;
  }

  public static void main(String[] args) {
    A13BinaryTreeRightSideView solution = new A13BinaryTreeRightSideView();

    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(4);

    System.out.println(solution.rightSideView(root)); // Output: [1, 3, 4]
  }

  /*
   Time Complexity: O(n), where n is the number of nodes in the binary tree.
   Space Complexity: O(n), where n is the number of nodes in the binary tree (due to queue usage in BFS).
  */
}
