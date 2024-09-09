package cools.binarytrees;

/*
 Problem: 100. Same Tree

 Given the roots of two binary trees p and q, write a function to check if they are the same or not.

 Two binary trees are considered the same if they are structurally identical,
 and the nodes have the same value.

 Example:

 Input:
     Tree 1       Tree 2
        1            1
       / \          / \
      2   3        2   3

 Output: true

 Explanation: Both trees are structurally identical and have the same node values.

 Another Example:

 Input:
     Tree 1       Tree 2
        1            1
       /              \
      2                2

 Output: false

 Explanation: Although the root nodes are the same, the structures of the trees are different.
*/

/*
 Solution Steps:

 1. If both trees are empty (i.e., the root nodes are null), they are considered the same.
 2. If one tree is empty and the other is not, they are not the same.
 3. If the values of the current nodes in both trees are different, the trees are not the same.
 4. Recursively check the left and right subtrees of both trees to ensure they are identical.
*/

public class A02SameTree {

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

  // Function to check if two binary trees are the same
  public boolean isSameTree(TreeNode p, TreeNode q) {
    // Base case 1: If both nodes are null, the trees are the same
    if (p == null && q == null) return true;

    // Base case 2: If one node is null and the other is not, the trees are not the same
    if (p == null || q == null) return false;

    // Check if the values of the current nodes are the same
    if (p.val != q.val) return false;

    // Recursively check the left and right subtrees of both trees
    return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02SameTree solution = new A02SameTree();

    // Example: Create two identical binary trees
    TreeNode p = new TreeNode(1);
    p.left = new TreeNode(2);
    p.right = new TreeNode(3);

    TreeNode q = new TreeNode(1);
    q.left = new TreeNode(2);
    q.right = new TreeNode(3);

    // Check if both trees are the same
    System.out.println("Are the trees the same? " + solution.isSameTree(p, q)); // Output: true
  }

  /*
   Time Complexity:
   - O(n), where n is the total number of nodes in the smaller tree. We traverse every node once to compare the two trees.

   Space Complexity:
   - O(h), where h is the height of the trees. This is the space required for the recursion stack.
   In the worst case (unbalanced tree), the height can be n, so the space complexity is O(n).
   In the best case (balanced tree), the height would be log(n), so the space complexity is O(log n).
  */
}
