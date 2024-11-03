package veryhard.binarytrees;

/*
 Problem: Compare Leaf Traversal

 Given two binary trees, check if their leaf traversals are the same. A leaf traversal
 refers to the sequence of leaf nodes in the tree from left to right. If both trees
 have the same leaf sequence, return true; otherwise, return false.

 Example Input:
 Tree 1:
          1
        /   \
       2     3
      / \   / \
     4   5 6   7
            \
             8
 Tree 2:
          1
        /   \
       2     3
      / \     \
     4   5     6
              / \
             8   7

 Example Output:
 true  // Both trees have the leaf sequence [4, 5, 8, 7]

 Solution Steps:
 1. Define a helper function that recursively collects the leaf nodes of a binary tree.
 2. Traverse both trees, collecting the leaf nodes into separate lists.
 3. Compare the leaf node lists for both trees. If they are identical, return true; otherwise, return false.
*/

import java.util.*;

public class A05CompareLeafTraversal {

  // Definition for a binary tree node
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

  // Function to compare the leaf traversal of two binary trees
  public boolean compareLeafTraversal(TreeNode root1, TreeNode root2) {
    List<Integer> leaves1 = new ArrayList<>();
    List<Integer> leaves2 = new ArrayList<>();

    // Collect leaf nodes for both trees
    collectLeaves(root1, leaves1);
    collectLeaves(root2, leaves2);

    // Compare the two leaf node lists
    return leaves1.equals(leaves2); // Return true if both lists are the same, false otherwise
  }

  // Helper function to collect leaf nodes in a tree using DFS
  private void collectLeaves(TreeNode node, List<Integer> leaves) {
    if (node == null) return; // Base case: If node is null, return

    // If the node is a leaf, add its value to the leaves list
    if (node.left == null && node.right == null) {
      leaves.add(node.val);
      return;
    }

    // Recursively collect leaves from the left and right subtrees
    collectLeaves(node.left, leaves);
    collectLeaves(node.right, leaves);
  }

  // Driver code to test the leaf traversal comparison
  public static void main(String[] args) {
    A05CompareLeafTraversal solution = new A05CompareLeafTraversal();

    // Example: Create two sample binary trees
    TreeNode root1 = new TreeNode(1);
    root1.left = new TreeNode(2);
    root1.right = new TreeNode(3);
    root1.left.left = new TreeNode(4);
    root1.left.right = new TreeNode(5);
    root1.right.left = new TreeNode(6);
    root1.right.right = new TreeNode(7);
    root1.right.left.right = new TreeNode(8);

    TreeNode root2 = new TreeNode(1);
    root2.left = new TreeNode(2);
    root2.right = new TreeNode(3);
    root2.left.left = new TreeNode(4);
    root2.left.right = new TreeNode(5);
    root2.right.right = new TreeNode(6);
    root2.right.right.left = new TreeNode(8);
    root2.right.right.right = new TreeNode(7);

    // Compare leaf traversals of the two trees
    boolean result = solution.compareLeafTraversal(root1, root2);
    System.out.println(
        "Do the trees have the same leaf traversal? " + result); // Expected Output: true
  }

  /*
   Time Complexity:
   - O(n + m), where n and m are the number of nodes in Tree 1 and Tree 2, respectively. We visit each node in both trees once during the DFS traversal.

   Space Complexity:
   - O(h1 + h2), where h1 and h2 are the heights of Tree 1 and Tree 2, respectively. This space is used by the recursion stack during the DFS traversal.
   - Additionally, we use O(l1 + l2) space to store the leaf nodes, where l1 and l2 are the number of leaf nodes in Tree 1 and Tree 2, respectively.
  */
}
