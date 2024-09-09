package cools.binarytrees;

/*
 Problem: Invert Binary Tree

 Given the root of a binary tree, invert the tree, and return its root.

 Inverting a binary tree means swapping the left and right child nodes for every node in the tree.

 Example:

 Input:
        4
       / \
      2   7
     / \ / \
    1  3 6  9

 Output:
        4
       / \
      7   2
     / \ / \
    9  6 3  1

 Explanation: After inverting the tree, the left and right children of all nodes are swapped.
*/

/*
 Solution Steps:

 1. If the tree is empty (i.e., the root node is null), there is nothing to invert, return null.
 2. Swap the left and right child nodes of the current root.
 3. Recursively call the inversion function on the left and right subtrees.
 4. Return the root after inversion.
*/

public class A03InvertBinaryTree {

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

  // Function to invert the binary tree
  public TreeNode invertTree(TreeNode root) {
    // Base case: if the root is null, return null
    if (root == null) return null;

    // Swap the left and right children
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;

    // Recursively invert the left and right subtrees
    invertTree(root.left);
    invertTree(root.right);

    // Return the root after inversion
    return root;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03InvertBinaryTree solution = new A03InvertBinaryTree();

    // Example: Create a sample binary tree: [4, 2, 7, 1, 3, 6, 9]
    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(2);
    root.right = new TreeNode(7);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(3);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(9);

    // Invert the binary tree and print the new root
    TreeNode invertedRoot = solution.invertTree(root);
    System.out.println("Root after inversion: " + invertedRoot.val); // Output: 4
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited once to swap its children.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
