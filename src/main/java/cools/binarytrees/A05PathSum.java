package cools.binarytrees;

/*
 Problem: Path Sum

 Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path
 such that adding up all the values along the path equals targetSum.

 A leaf is a node with no children.

 Example:

 Input:
        5
       / \
      4   8
     /   / \
    11  13  4
   /  \      \
  7    2      1

 targetSum = 22

 Output: true

 Explanation: The path 5 -> 4 -> 11 -> 2 has a sum of 22.
*/

/*
 Solution Steps:

 1. If the tree is empty (i.e., the root is null), there is no path, so return false.
 2. If the current node is a leaf (both left and right children are null), check if the current node's value equals the targetSum.
 3. Recursively check the left and right subtrees, reducing the targetSum by the current node's value.
 4. Return true if either the left or right subtree has a path that equals the targetSum.
*/

public class A05PathSum {

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

  // Function to determine if there exists a root-to-leaf path with the given targetSum
  public boolean hasPathSum(TreeNode root, int targetSum) {
    // Base case: if the root is null, there is no path
    if (root == null) return false;

    // Check if we are at a leaf node
    if (root.left == null && root.right == null) {
      // If the leaf node's value equals targetSum, return true
      return root.val == targetSum;
    }

    // Recursively check the left and right subtrees, reducing the targetSum by the current node's
    // value
    return hasPathSum(root.left, targetSum - root.val)
        || hasPathSum(root.right, targetSum - root.val);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05PathSum solution = new A05PathSum();

    // Example: Create a binary tree [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1]
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(4);
    root.right = new TreeNode(8);
    root.left.left = new TreeNode(11);
    root.left.left.left = new TreeNode(7);
    root.left.left.right = new TreeNode(2);
    root.right.left = new TreeNode(13);
    root.right.right = new TreeNode(4);
    root.right.right.right = new TreeNode(1);

    // Check if there exists a path with the sum of 22
    System.out.println("Has path sum of 22: " + solution.hasPathSum(root, 22)); // Output: true
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited once to check for the path.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
