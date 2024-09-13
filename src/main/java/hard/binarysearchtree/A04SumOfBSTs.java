package hard.binarysearchtree;

/*
 Problem: Sum of All Nodes in Subtrees that are BSTs with at Least 3 Nodes

 Given a binary tree, determine the sum of all the values of nodes that are part of any
 Binary Search Tree (BST) within the Binary Tree that contains at least 3 nodes.

 A Binary Search Tree (BST) is defined as follows:
 - The left subtree of a node contains only nodes with values less than the node's value.
 - The right subtree of a node contains only nodes with values greater than or equal to the node's value.

 We need to find all such subtrees in the binary tree that are valid BSTs and contain at least 3 nodes,
 and then sum the values of all nodes in these BSTs.

 Example 1:
 Input:
        10
       /  \
      5    15
     / \   / \
    1   8 12  20

 Output: 61
 Explanation: The subtrees rooted at nodes 10, 5, and 15 are valid BSTs. Their sums are:
 - BST rooted at node 5: sum = 1 + 5 + 8 = 14
 - BST rooted at node 10: sum = 1 + 5 + 8 + 10 + 12 + 15 + 20 = 71
 - Subtree rooted at node 15 has fewer than 3 nodes, so it's not included.

 Solution Approach:
 1. Perform a post-order traversal on the tree.
 2. For each subtree, check whether it forms a valid BST.
 3. If the subtree is a valid BST and contains at least 3 nodes, add its sum to the total sum.
 4. Return the total sum after processing all subtrees.
*/

public class A04SumOfBSTs {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  // Helper class to store information about each subtree during traversal
  static class SubtreeInfo {
    boolean isBST; // Indicates if the subtree is a valid BST
    int size; // Size of the subtree (number of nodes)
    int minValue; // Minimum value in the subtree
    int maxValue; // Maximum value in the subtree
    int sum; // Sum of all node values in the subtree

    public SubtreeInfo(boolean isBST, int size, int minValue, int maxValue, int sum) {
      this.isBST = isBST;
      this.size = size;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.sum = sum;
    }
  }

  int totalSum = 0;

  // Function to calculate the sum of all BSTs with at least 3 nodes
  public int sumBSTs(TreeNode root) {
    traverse(root);
    return totalSum;
  }

  // Post-order traversal helper function to process each subtree
  private SubtreeInfo traverse(TreeNode node) {
    // Base case: an empty subtree is considered a valid BST with size 0
    if (node == null) {
      return new SubtreeInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }

    // Recursively process the left and right subtrees
    SubtreeInfo leftInfo = traverse(node.left);
    SubtreeInfo rightInfo = traverse(node.right);

    // Check if the current subtree is a valid BST
    boolean isBST =
        leftInfo.isBST
            && rightInfo.isBST
            && node.val > leftInfo.maxValue
            && node.val <= rightInfo.minValue;

    // Calculate the size and sum of the current subtree
    int currentSize = leftInfo.size + rightInfo.size + 1;
    int currentSum = leftInfo.sum + rightInfo.sum + node.val;

    // Update the minimum and maximum values in the current subtree
    int currentMinValue = Math.min(node.val, leftInfo.minValue);
    int currentMaxValue = Math.max(node.val, rightInfo.maxValue);

    // If the current subtree is a valid BST and contains at least 3 nodes, add its sum to the total
    // sum
    if (isBST && currentSize >= 3) {
      totalSum += currentSum;
    }

    // Return information about the current subtree
    return new SubtreeInfo(isBST, currentSize, currentMinValue, currentMaxValue, currentSum);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A04SumOfBSTs solution = new A04SumOfBSTs();

    // Example 1: Binary tree input
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.right = new TreeNode(15);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(8);
    root.right.left = new TreeNode(12);
    root.right.right = new TreeNode(20);

    // Sum of all valid BSTs with at least 3 nodes
    System.out.println(
        "Sum of all BSTs with at least 3 nodes: " + solution.sumBSTs(root)); // Output: 61
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. We visit each node once during the traversal.

   Space Complexity:
   - O(h), where h is the height of the tree. This is due to the recursion stack used in the post-order traversal. In the worst case (a skewed tree), h = O(n).
  */
}
