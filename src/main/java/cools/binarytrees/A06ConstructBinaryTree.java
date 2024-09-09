package cools.binarytrees;

/*
 Problem: Construct Binary Tree from Preorder and Inorder Traversal

 Given two integer arrays `preorder` and `inorder` where:
 - `preorder` is the preorder traversal of a binary tree, and
 - `inorder` is the inorder traversal of the same binary tree,

 Construct and return the binary tree.

 Example:

 Input:
 Preorder: [3, 9, 20, 15, 7]
 Inorder:  [9, 3, 15, 20, 7]

 Output:
       3
      / \
     9  20
       /  \
      15   7

 Explanation: The tree is constructed as follows:
 - Preorder gives us the root of the tree as the first element.
 - Inorder helps us determine the left and right subtrees by locating the root in the inorder array.
*/

/*
 Solution Steps:

 1. The first element in the `preorder` array is always the root of the tree.
 2. Use the `inorder` array to determine the left and right subtrees:
    a) Find the position of the root in the `inorder` array.
    b) Elements to the left of the root in `inorder` form the left subtree.
    c) Elements to the right of the root in `inorder` form the right subtree.
 3. Recursively construct the left and right subtrees by using the respective portions of the `preorder` and `inorder` arrays.
 4. Return the constructed tree.
*/

import java.util.HashMap;
import java.util.Map;

public class A06ConstructBinaryTree {

  private int preorderIndex; // Tracks the current index in the preorder array
  private Map<Integer, Integer>
      inorderIndexMap; // Stores the value -> index mapping for the inorder array

  // Function to build a binary tree from preorder and inorder arrays
  public TreeNode buildTree(int[] preorder, int[] inorder) {
    preorderIndex = 0;
    inorderIndexMap = new HashMap<>();

    // Build a hashmap to store value -> its index relations for inorder traversal
    for (int i = 0; i < inorder.length; i++) {
      inorderIndexMap.put(inorder[i], i);
    }

    // Call the recursive function to construct the binary tree
    return arrayToTree(preorder, 0, preorder.length - 1);
  }

  // Recursive function to build the binary tree
  private TreeNode arrayToTree(int[] preorder, int left, int right) {
    // Base case: if there are no elements to construct the tree
    if (left > right) {
      return null;
    }

    // Select the preorderIndex element as the root and increment it
    int rootValue = preorder[preorderIndex++];
    TreeNode root = new TreeNode(rootValue);

    // Recursively build the left and right subtrees
    // Exclude inorderIndexMap[rootValue] element because it's the root
    root.left = arrayToTree(preorder, left, inorderIndexMap.get(rootValue) - 1);
    root.right = arrayToTree(preorder, inorderIndexMap.get(rootValue) + 1, right);

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

  // Main method to test the solution
  public static void main(String[] args) {
    A06ConstructBinaryTree solution = new A06ConstructBinaryTree();

    // Example input
    int[] preorder = {3, 9, 20, 15, 7};
    int[] inorder = {9, 3, 15, 20, 7};

    // Build the tree
    TreeNode root = solution.buildTree(preorder, inorder);

    // Output the root value (for testing)
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
