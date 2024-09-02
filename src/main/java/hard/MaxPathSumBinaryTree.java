/*
 * Problem Statement:
 * Write a function that takes in a Binary Tree and returns its maximum path sum.
 *
 * A path is defined as any collection of connected nodes in the tree, where each node in the path is connected to no more than two other nodes. The path sum is the sum of the values of the nodes in the path.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node. Child nodes can either be BinaryTree nodes themselves or null.
 *
 * The goal is to find the maximum possible sum of values from any path in the binary tree.
 *
 * Example:
 * Input:
 *          1
 *        /   \
 *       2     3
 *      / \   / \
 *     4   5 6   7
 *
 * Output: 18   // Path: 5 -> 2 -> 1 -> 3 -> 7
 */
/*Approach:
This approach uses a Depth-First Search (DFS) algorithm to traverse the tree. For each node, you calculate the maximum path sum including that node as the root. You then recursively calculate the maximum path sums for the left and right subtrees. The maximum path sum at each node is the greater of the following:

The node’s value alone.
The node's value plus the maximum path sum from either its left or right subtree.
The node’s value plus the maximum path sum from both its left and right subtrees (this includes the node as part of the overall path).
Finally, we keep track of the maximum path sum found during the traversal.

Time Complexity:
O(n): We visit each node exactly once, making this solution linear in time complexity.
Space Complexity:
O(h): The space complexity is determined by the recursion stack, where h is the height of the tree.*/

package hard;

public class MaxPathSumBinaryTree {
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

  // Helper function to calculate maximum path sum
  private static int findMaxSum(BinaryTree node, int[] maxSum) {
    if (node == null) {
      return 0;
    }

    // Recursively calculate the maximum path sum from the left and right subtrees
    int leftMax = Math.max(findMaxSum(node.left, maxSum), 0); // Ignore negative sums
    int rightMax = Math.max(findMaxSum(node.right, maxSum), 0); // Ignore negative sums

    // Current path sum including the node
    int currentPathSum = node.value + leftMax + rightMax;

    // Update the maximum path sum found so far
    maxSum[0] = Math.max(maxSum[0], currentPathSum);

    // Return the maximum sum for a path extending to the parent node
    return node.value + Math.max(leftMax, rightMax);
  }

  // Function to return the maximum path sum
  public static int maxPathSum(BinaryTree tree) {
    int[] maxSum = new int[] {Integer.MIN_VALUE};
    findMaxSum(tree, maxSum);
    return maxSum[0];
  }

  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right = new BinaryTree(7);

    System.out.println(maxPathSum(root)); // Output: 18
  }
}
