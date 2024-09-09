package cools.binarytrees;

/*
 Problem: Lowest Common Ancestor of a Binary Tree

 Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

 The "lowest common ancestor" is defined between two nodes p and q as the lowest node in the tree that has both p and q as descendants
 (where we allow a node to be a descendant of itself).

 Example:

 Input:
        3
       / \
      5   1
     / \ / \
    6  2 0  8
      / \
     7   4

 Nodes p = 5, q = 1
 Output: 3

 Explanation:
 The LCA of nodes 5 and 1 is 3 because 3 is the lowest node that has both 5 and 1 as descendants.

 Another Example:

 Nodes p = 5, q = 4
 Output: 5

 Explanation:
 The LCA of nodes 5 and 4 is 5 because 5 is an ancestor of 4.
*/

/*
 Solution Steps:

 1. If the current node is null, return null.
 2. If the current node is either p or q, return the current node (because we found one of the nodes).
 3. Recursively check the left and right subtrees to find p and q.
 4. If both the left and right subtrees return non-null values, then the current node is the LCA (because it is the lowest node that has both p and q as descendants).
 5. If only one of the subtrees returns a non-null value, return that non-null node (this means p and q are both located in that subtree).
 6. Return the result.
*/

public class A12LowestCommonAncestorBinaryTree {

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

  // Function to find the lowest common ancestor of two nodes
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    // Base case: if the current node is null or equal to p or q, return the current node
    if (root == null || root == p || root == q) {
      return root;
    }

    // Recursively search for p and q in the left and right subtrees
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    // If both left and right subtrees return non-null values, the current node is the LCA
    if (left != null && right != null) {
      return root;
    }

    // Otherwise, return the non-null subtree (left or right)
    return (left != null) ? left : right;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A12LowestCommonAncestorBinaryTree solution = new A12LowestCommonAncestorBinaryTree();

    // Example: Create a sample binary tree: [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(5);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(6);
    root.left.right = new TreeNode(2);
    root.right.left = new TreeNode(0);
    root.right.right = new TreeNode(8);
    root.left.right.left = new TreeNode(7);
    root.left.right.right = new TreeNode(4);

    // Nodes p = 5, q = 1
    TreeNode p = root.left; // Node 5
    TreeNode q = root.right; // Node 1

    // Find and print the lowest common ancestor
    TreeNode lca = solution.lowestCommonAncestor(root, p, q);
    System.out.println("Lowest Common Ancestor of 5 and 1: " + lca.val); // Output: 3

    // Nodes p = 5, q = 4
    p = root.left; // Node 5
    q = root.left.right.right; // Node 4

    // Find and print the lowest common ancestor
    lca = solution.lowestCommonAncestor(root, p, q);
    System.out.println("Lowest Common Ancestor of 5 and 4: " + lca.val); // Output: 5
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. In the worst case, we visit every node in the tree.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   - In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
