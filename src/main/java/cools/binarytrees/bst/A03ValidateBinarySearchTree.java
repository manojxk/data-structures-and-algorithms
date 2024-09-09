package cools.binarytrees.bst;

/*
 Problem: Validate Binary Search Tree

 Given the root of a binary tree, determine if it is a valid binary search tree (BST).

 A valid BST is defined as follows:
 1. The left subtree of a node contains only nodes with keys less than the node's key.
 2. The right subtree of a node contains only nodes with keys greater than the node's key.
 3. Both the left and right subtrees must also be binary search trees.

 Example:

 Input:
        2
       / \
      1   3

 Output: true

 Explanation:
 This is a valid binary search tree.

 Another Example:

 Input:
        5
       / \
      1   4
         / \
        3   6

 Output: false

 Explanation:
 The root node's value is 5, but the right child node's value is 4, which violates the BST property.
*/

/*
 Solution Steps:

 1. Use recursion to validate the binary search tree by checking the allowed range for each node.
 2. The initial range for the root node is (-∞, ∞).
 3. For each node, ensure that:
    a) The node's value is greater than the allowed minimum and less than the allowed maximum.
    b) Recursively validate the left subtree with an updated maximum value (the current node's value).
    c) Recursively validate the right subtree with an updated minimum value (the current node's value).
 4. If any node violates the BST properties, return false. Otherwise, return true.
*/

public class A03ValidateBinarySearchTree {

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

  // Function to validate if the binary tree is a valid BST
  public boolean isValidBST(TreeNode root) {
    return isValidBSTHelper(root, null, null);
  }

  // Helper function to validate the BST with given min and max values
  private boolean isValidBSTHelper(TreeNode node, Integer min, Integer max) {
    // Base case: if the node is null, it is a valid subtree
    if (node == null) return true;

    // Check if the node's value violates the min/max constraints
    if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
      return false;
    }

    // Recursively check the left subtree and the right subtree
    // Left subtree must have values less than the current node (update max)
    // Right subtree must have values greater than the current node (update min)
    return isValidBSTHelper(node.left, min, node.val)
        && isValidBSTHelper(node.right, node.val, max);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03ValidateBinarySearchTree solution = new A03ValidateBinarySearchTree();

    // Example 1: Create a valid BST: [2, 1, 3]
    TreeNode root1 = new TreeNode(2);
    root1.left = new TreeNode(1);
    root1.right = new TreeNode(3);

    System.out.println("Is valid BST? " + solution.isValidBST(root1)); // Output: true

    // Example 2: Create an invalid BST: [5, 1, 4, null, null, 3, 6]
    TreeNode root2 = new TreeNode(5);
    root2.left = new TreeNode(1);
    root2.right = new TreeNode(4);
    root2.right.left = new TreeNode(3);
    root2.right.right = new TreeNode(6);

    System.out.println("Is valid BST? " + solution.isValidBST(root2)); // Output: false
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited once during the traversal.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack. In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
