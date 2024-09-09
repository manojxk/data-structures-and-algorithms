/*
 * Problem Statement:
 * Write a function that takes in a Binary Tree and returns its maximum path sum.
 *
 * A path is defined as any collection of connected nodes in the tree, where each node in the path is connected to no more than two other nodes. The path sum is the sum of the valueues of the nodes in the path.
 *
 * Each BinaryTree node has an integer valueue, a left child node, and a right child node. Child nodes can either be BinaryTree nodes themselves or null.
 *
 * The goal is to find the maximum possible sum of valueues from any path in the binary tree.
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

The node’s valueue alone.
The node's valueue plus the maximum path sum from either its left or right subtree.
The node’s valueue plus the maximum path sum from both its left and right subtrees (this includes the node as part of the overall path).
Finally, we keep track of the maximum path sum found during the traversal.

Time Complexity:
O(n): We visit each node exactly once, making this solution linear in time complexity.
Space Complexity:
O(h): The space complexity is determined by the recursion stack, where h is the height of the tree.*/

package hard.binarytrees;

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

  // Helper function to compute the maximum path sum and update the global result
  public static int maxPathSumUtil(BinaryTree root, int[] ans) {
    // Base case: if the node is null, return 0
    if (root == null) {
      return 0;
    }

    // Recursively calculate the maximum path sum of the left and right subtrees
    int left = maxPathSumUtil(root.left, ans);
    int right = maxPathSumUtil(root.right, ans);

    // Calculate the maximum path sum for the current node considering all possibilities
    int nodeMax =
        Math.max(
            Math.max(root.value, root.value + left + right),
            Math.max(root.value + left, root.value + right));

    // Update the global maximum path sum
    ans[0] = Math.max(ans[0], nodeMax);

    // Return the maximum path sum for a single path starting from this node
    return Math.max(root.value, Math.max(root.value + left, root.value + right));
  }

  // Main function to find the maximum path sum in the binary tree
  public static int maxPathSum(BinaryTree root) {
    int[] res =
        new int[1]; // Store the result in an array (since Java doesn't support pass-by-reference)
    res[0] = Integer.MIN_VALUE; // Initialize with the minimum possible valueue
    maxPathSumUtil(root, res);
    return res[0]; // Return the result
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
