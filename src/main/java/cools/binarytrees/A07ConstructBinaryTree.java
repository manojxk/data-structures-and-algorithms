package cools.binarytrees;

/*
 Problem: Construct Binary Tree from Inorder and Postorder Traversal

 Given two integer arrays `inorder` and `postorder` where:
 - `inorder` is the inorder traversal of a binary tree, and
 - `postorder` is the postorder traversal of the same binary tree,

 Construct and return the binary tree.

 Example:

 Input:
 Inorder:    [9, 3, 15, 20, 7]
 Postorder:  [9, 15, 7, 20, 3]

 Output:
       3
      / \
     9  20
       /  \
      15   7

 Explanation: The tree is constructed as follows:
 - Postorder gives us the root of the tree as the last element.
 - Inorder helps us determine the left and right subtrees by locating the root in the inorder array.
*/

/*
 Solution Steps:

 1. The last element in the `postorder` array is always the root of the tree.
 2. Use the `inorder` array to determine the left and right subtrees:
    a) Find the position of the root in the `inorder` array.
    b) Elements to the left of the root in `inorder` form the left subtree.
    c) Elements to the right of the root in `inorder` form the right subtree.
 3. Recursively construct the left and right subtrees by using the respective portions of the `inorder` and `postorder` arrays.
 4. Return the constructed tree.
*/

import java.util.HashMap;
import java.util.Map;

public class A07ConstructBinaryTree {

  private int postIdx; // Index in the postorder array
  private Map<Integer, Integer>
      inorderIndexMap; // HashMap to store value -> index mapping for inorder

  // Function to build the tree from inorder and postorder arrays
  public TreeNode buildTree(int[] inorder, int[] postorder) {
    // Initialize the map to store index positions for inorder traversal
    inorderIndexMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inorderIndexMap.put(inorder[i], i);
    }

    // Initialize the postorder index
    postIdx = postorder.length - 1;

    // Call the helper function to construct the binary tree
    return helper(postorder, 0, inorder.length - 1);
  }

  // Helper function to recursively construct the binary tree
  private TreeNode helper(int[] postorder, int left, int right) {
    // Base case: no elements to construct the tree
    if (left > right) {
      return null;
    }

    // Get the root value from the postorder array and decrement postIdx
    int rootVal = postorder[postIdx--];
    TreeNode root = new TreeNode(rootVal);

    // Recursively build the right subtree first, followed by the left subtree
    // Since postorder traversal visits left-right-root, we process right subtree first
    root.right = helper(postorder, inorderIndexMap.get(rootVal) + 1, right);
    root.left = helper(postorder, left, inorderIndexMap.get(rootVal) - 1);

    return root;
  }

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

  // Main function to test the solution
  public static void main(String[] args) {
    A07ConstructBinaryTree solution = new A07ConstructBinaryTree();

    // Example input
    int[] inorder = {9, 3, 15, 20, 7};
    int[] postorder = {9, 15, 7, 20, 3};

    // Build the tree
    TreeNode root = solution.buildTree(inorder, postorder);

    // Print the root value (for testing)
    System.out.println("Root of the tree: " + root.val); // Expected output: 3
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the tree. We visit each node once during the construction of the tree.

   Space Complexity:
   - O(n), where n is the number of nodes, due to the space required to store the inorder index map and the recursion stack.
   In the worst case (unbalanced tree), the height of the tree can be n, leading to O(n) space complexity.
  */
}
