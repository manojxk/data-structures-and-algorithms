package cools.binarytrees.bst;

/*
 Problem: Minimum Absolute Difference in BST

 Given the root of a Binary Search Tree (BST), return the minimum absolute difference between the values of any two different nodes in the tree.

 Example:

 Input:
        4
       / \
      2   6
     / \
    1   3

 Output: 1

 Explanation:
 The minimum absolute difference is between nodes 2 and 3, which is |2 - 3| = 1.
*/

/*
 Solution Steps:

 1. Perform an in-order traversal of the BST to get the node values in sorted order.
 2. During the traversal, compute the difference between the current node's value and the previous node's value.
 3. Track the minimum difference and update it when a smaller difference is found.
 4. Return the minimum difference after the traversal is complete.
*/

public class A01MinimumAbsoluteDifferenceBST {

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

  private Integer prevValue = null; // To store the previous node's value during in-order traversal
  private int minDiff = Integer.MAX_VALUE; // To track the minimum absolute difference

  // Function to calculate the minimum absolute difference in the BST
  public int getMinimumDifference(TreeNode root) {
    // Perform in-order traversal to get the values in sorted order
    inOrderTraversal(root);
    return minDiff;
  }

  // Helper function to perform in-order traversal
  private void inOrderTraversal(TreeNode node) {
    if (node == null) return;

    // Traverse the left subtree
    inOrderTraversal(node.left);

    // Calculate the difference with the previous node (if exists)
    if (prevValue != null) {
      minDiff = Math.min(minDiff, Math.abs(node.val - prevValue));
    }

    // Update the previous node's value to the current node's value
    prevValue = node.val;

    // Traverse the right subtree
    inOrderTraversal(node.right);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01MinimumAbsoluteDifferenceBST solution = new A01MinimumAbsoluteDifferenceBST();

    // Example: Create a sample BST: [4, 2, 6, 1, 3]
    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(2);
    root.right = new TreeNode(6);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(3);

    // Calculate and print the minimum absolute difference in the BST
    System.out.println(
        "Minimum Absolute Difference: " + solution.getMinimumDifference(root)); // Output: 1
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary search tree. We visit each node once during the in-order traversal.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack. In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
