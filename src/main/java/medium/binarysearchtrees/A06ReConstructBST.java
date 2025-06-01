/*
 * Problem Statement:
 * Given an array of integers representing the pre-order traversal of a Binary Search Tree (BST),
 * write a function to construct the BST and return its root node.
 *
 * The pre-order traversal visits nodes in the following order:
 * 1. Current node
 * 2. Left subtree
 * 3. Right subtree
 *
 * Each BST node has an integer value, a left child node, and a right child node.
 * The BST property must be maintained:
 * - The value of each node must be strictly greater than the values of all nodes in its left subtree.
 * - The value of each node must be less than or equal to the values of all nodes in its right subtree.
 *
 * Example:
 * Input: preOrderTraversalValues = [10, 4, 2, 1, 5, 17, 19, 18]
 * Output: The root node of the following BST:
 *
 *        10
 *      /    \
 *     4      17
 *   /   \      \
 *  2     5     19
 * /           /
 *1           18
 */
/*Brute Force Solution
Approach:
Iterative Insertion: The brute force solution would be to iteratively insert each element of the pre-order array into the BST. Start with the first element as the root and for each subsequent element, traverse the tree from the root, placing the new element in the correct position according to the BST properties.
Time Complexity:
O(n^2): For each insertion, we may need to traverse the height of the tree, leading to quadratic time complexity in the worst case.
Space Complexity:
O(n): Space is needed for the tree structure.*/

/*Optimized Solution: Recursive Approach with Bounds
Approach:
Recursive Construction: Use a helper function that constructs the BST by maintaining bounds for the values each node can take. Starting with the entire pre-order array, recursively assign values to nodes based on the BST properties and valid bounds.
Bounding: By maintaining valid value bounds for each node during recursion, we ensure that the constructed tree adheres to BST properties.
Time Complexity:
O(n): Each element is visited once during the construction, leading to linear time complexity.
Space Complexity:
O(n): The space complexity is determined by the recursion stack, which in the worst case (skewed tree) can be O(n).*/

package medium.binarysearchtrees;

public class ReConstructBST {
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

  // Brute Force Solution
  public static TreeNode constructBSTBruteForce(int[] preOrderTraversalValues) {
    if (preOrderTraversalValues.length == 0) return null;

    TreeNode root = new TreeNode(preOrderTraversalValues[0]);

    for (int i = 1; i < preOrderTraversalValues.length; i++) {
      insertIntoBST(root, preOrderTraversalValues[i]);
    }

    return root;
  }

  private static void insertIntoBST(TreeNode node, int value) {
    if (value < node.value) {
      if (node.left == null) {
        node.left = new TreeNode(value);
      } else {
        insertIntoBST(node.left, value);
      }
    } else {
      if (node.right == null) {
        node.right = new TreeNode(value);
      } else {
        insertIntoBST(node.right, value);
      }
    }
  }

  // Optimized Solution using Recursive Approach with Bounds
  public static TreeNode constructBSTOptimized(int[] preOrderTraversalValues) {
    return constructBSTFromPreorder(
        preOrderTraversalValues, Integer.MIN_VALUE, Integer.MAX_VALUE, new int[] {0});
  }

  private static TreeNode constructBSTFromPreorder(
      int[] preOrderTraversalValues, int minValue, int maxValue, int[] currentIndex) {
    if (currentIndex[0] == preOrderTraversalValues.length) return null;

    int currentValue = preOrderTraversalValues[currentIndex[0]];
    if (currentValue < minValue || currentValue > maxValue) return null;

    TreeNode root = new TreeNode(currentValue);
    currentIndex[0]++;

    root.left =
        constructBSTFromPreorder(preOrderTraversalValues, minValue, currentValue, currentIndex);
    root.right =
        constructBSTFromPreorder(preOrderTraversalValues, currentValue, maxValue, currentIndex);

    return root;
  }

  public static void main(String[] args) {
    int[] preOrderTraversalValues = {10, 4, 2, 1, 5, 17, 19, 18};
    TreeNode root = constructBSTBruteForce(preOrderTraversalValues);
    // Output will be the root of the constructed BST
  }
}
