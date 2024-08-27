/*
 * Problem Statement:
 * Given a Binary Search Tree (BST), write a function to determine if the tree is a valid BST.
 *
 * A BST is valid if:
 * 1. The value of each node is strictly greater than the values of all nodes in its left subtree.
 * 2. The value of each node is less than or equal to the values of all nodes in its right subtree.
 * 3. The left and right subtrees themselves must also be valid BSTs.
 *
 * Constraints:
 * - Each node in the BST contains an integer value, a left child node, and a right child node.
 * - The tree may potentially be invalid.
 *
 * Example:
 * Input:
 *        10
 *       /  \
 *      5    15
 *     / \   / \
 *    2   5 13 22
 *   /        \
 *  1         14
 *
 * Output: true
 * // The given tree satisfies the BST properties and is therefore valid.
 */

package medium;

public class ValidateBST {
  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Optimized Solution using Range Checking
  public static boolean validateBSTOptimized(TreeNode root) {
    return validateBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean validateBSTHelper(TreeNode node, int minValue, int maxValue) {
    if (node == null) return true;

    if (node.value <= minValue || node.value > maxValue) {
      return false;
    }

    return validateBSTHelper(node.left, minValue, node.value)
        && validateBSTHelper(node.right, node.value, maxValue);
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.right = new TreeNode(15);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(13);
    root.right.right = new TreeNode(22);
    root.left.left.left = new TreeNode(1);
    root.right.left.right = new TreeNode(14);

    System.out.println(validateBSTOptimized(root)); // Output: true
  }
}
/*Optimized Solution: Recursive Approach with Range Checking
Approach:
Range Checking: For each node, maintain a valid range of values that it can take. The root node starts with an infinite range. For each left child, the range is updated to ensure all nodes are less than the current node's value. For each right child, the range is updated to ensure all nodes are greater than the current node's value.
Recursion: This check is performed recursively for all nodes in the tree.
Time Complexity:
O(n): Each node is visited exactly once, leading to linear time complexity.
Space Complexity:
O(h): The space complexity is determined by the call stack depth, where h is the height of the tree.*/
