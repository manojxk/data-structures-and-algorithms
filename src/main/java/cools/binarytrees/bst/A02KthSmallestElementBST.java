package cools.binarytrees.bst;

/*
 Problem: Kth Smallest Element in a BST

 Given the root of a binary search tree, and an integer k, return the kth smallest value (1-indexed) of all the values of the nodes in the tree.

 Example:

 Input:
        3
       / \
      1   4
       \
        2

 k = 1
 Output: 1

 Explanation:
 The in-order traversal of the BST is [1, 2, 3, 4]. The 1st smallest element is 1.
*/

/*
 Solution Steps:

 1. Perform an in-order traversal of the BST, which will give the node values in sorted order.
 2. During the traversal, count the nodes. When the count reaches k, return the current node's value.
 3. The in-order traversal ensures the nodes are visited in ascending order.
*/

public class A02KthSmallestElementBST {

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

  private int count = 0; // To keep track of the count of nodes visited
  private int result = -1; // To store the kth smallest value

  // Function to find the kth smallest element in the BST
  public int kthSmallest(TreeNode root, int k) {
    inOrderTraversal(root, k);
    return result; // Return the result after traversal
  }

  // Helper function to perform in-order traversal and find the kth smallest element
  private void inOrderTraversal(TreeNode node, int k) {
    if (node == null) return;

    // Traverse the left subtree
    inOrderTraversal(node.left, k);

    // Increment the count for each node visited
    count++;

    // If the count matches k, store the result and return
    if (count == k) {
      result = node.val;
      return;
    }

    // Traverse the right subtree
    inOrderTraversal(node.right, k);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02KthSmallestElementBST solution = new A02KthSmallestElementBST();

    // Example: Create a sample BST: [3, 1, 4, null, 2]
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(1);
    root.right = new TreeNode(4);
    root.left.right = new TreeNode(2);

    int k = 1; // Find the 1st smallest element
    System.out.println("Kth Smallest Element: " + solution.kthSmallest(root, k)); // Output: 1
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary search tree. In the worst case, we may need to visit all nodes.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack. In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
