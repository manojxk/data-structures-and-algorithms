package cools.binarytrees;

/*
 Problem: Maximum Depth of Binary Tree

 Given the root of a binary tree, return its maximum depth.
 A binary tree's maximum depth is the number of nodes along the longest path
 from the root node down to the farthest leaf node.

 Example:

 Input:
        3
       / \
      9  20
        /  \
       15   7

 Output: 3

 Explanation: The maximum depth is the path from root -> 20 -> 7, which has 3 nodes.
*/

/*
 Solution Steps:

 1. If the tree is empty (i.e., the root is null), the depth is 0.
 2. Recursively calculate the depth of the left and right subtrees.
 3. The maximum depth at any node is the greater of the left subtree's depth and the right subtree's depth, plus 1 (for the current node).
 4. Continue this process for all nodes, and return the maximum depth.
*/

public class A01MaximumDepthBinaryTree {

  // TreeNode class definition
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

  // Function to calculate the maximum depth of the binary tree
  public int maxDepth(TreeNode root) {
    // Base case: if the root is null, the depth is 0
    if (root == null) return 0;

    // Recursively calculate the depth of the left and right subtrees
    int leftDepth = maxDepth(root.left);
    int rightDepth = maxDepth(root.right);

    // The depth of the current node is 1 + the maximum depth of its subtrees
    return Math.max(leftDepth, rightDepth) + 1;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01MaximumDepthBinaryTree solution = new A01MaximumDepthBinaryTree();

    // Example: Create a sample binary tree: [3, 9, 20, null, null, 15, 7]
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    // Calculate and print the maximum depth of the binary tree
    System.out.println("Maximum Depth: " + solution.maxDepth(root)); // Output: 3
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited exactly once to compute its depth.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   - In the worst case (completely unbalanced tree), the height could be n (i.e., the number of nodes), resulting in O(n) space complexity.
   - In the best case (balanced tree), the height is log(n), so the space complexity is O(log n).
  */
}
