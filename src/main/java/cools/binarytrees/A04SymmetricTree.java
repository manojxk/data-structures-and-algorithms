package cools.binarytrees;

/*
 Problem: Symmetric Tree

 Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).

 Example:

 Input:
        1
       / \
      2   2
     / \ / \
    3  4 4  3

 Output: true

 Explanation: The tree is symmetric around its center.

 Another Example:

 Input:
        1
       / \
      2   2
       \   \
       3    3

 Output: false

 Explanation: The tree is not symmetric because the subtrees are not mirrors of each other.
*/

/*
 Solution Steps:

 1. If the tree is empty (i.e., the root is null), it is considered symmetric, return true.
 2. Use a helper function to check if two trees are mirror images of each other.
 3. In the helper function, compare the values of the two trees:
    a) Both trees must be null for them to be symmetric.
    b) If one tree is null and the other is not, they are not symmetric.
    c) The values of the current nodes in both trees must match.
 4. Recursively check if the left subtree of one tree is a mirror of the right subtree of the other tree.
 5. Return true if both subtrees are symmetric.
*/

public class A04SymmetricTree {

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

  // Function to check if the binary tree is symmetric
  public boolean isSymmetric(TreeNode root) {
    // A tree is symmetric if the left and right subtrees are mirrors of each other
    return isMirror(root, root);
  }

  // Helper function to recursively check if two trees are mirrors of each other
  private boolean isMirror(TreeNode t1, TreeNode t2) {
    // Base case 1: If both nodes are null, they are mirrors
    if (t1 == null && t2 == null) return true;

    // Base case 2: If one node is null and the other is not, they are not mirrors
    if (t1 == null || t2 == null) return false;

    // Check if the values of the current nodes are equal
    // and recursively check if:
    // 1. The left subtree of t1 is a mirror of the right subtree of t2
    // 2. The right subtree of t1 is a mirror of the left subtree of t2
    return (t1.val == t2.val) && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04SymmetricTree solution = new A04SymmetricTree();

    // Example: Create a sample symmetric binary tree: [1, 2, 2, 3, 4, 4, 3]
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(2);
    root.left.left = new TreeNode(3);
    root.left.right = new TreeNode(4);
    root.right.left = new TreeNode(4);
    root.right.right = new TreeNode(3);

    // Check if the binary tree is symmetric
    System.out.println("Is the tree symmetric? " + solution.isSymmetric(root)); // Output: true
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the tree. Each node is visited once to check if the tree is symmetric.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
