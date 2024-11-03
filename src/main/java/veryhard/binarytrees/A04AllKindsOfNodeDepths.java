package veryhard.binarytrees;

/*
 Problem: All Kinds of Node Depths

 Given a binary tree, return the sum of all node depths. The depth of a node is
 defined as the number of edges between the node and the tree's root.

 The sum of all node depths is the sum of the depths of all the nodes in the tree.

 Example Input:
        1
      /   \
     2     3
    / \   / \
   4   5 6   7
  / \
 8   9

 Example Output:
  26  // Explanation:
  Node depths are: 0 (1), 1 (2, 3), 2 (4, 5, 6, 7), 3 (8, 9)
  Sum = 0 + (1 + 1) + (2 + 2 + 2 + 2) + (3 + 3) = 26

 Solution Steps:
 1. Define a recursive function that computes the depth of every node in a tree.
 2. For each node, recursively compute the depths of its left and right subtrees.
 3. The depth contribution of a node is the sum of all nodes in its subtree, including itself.
 4. Use a helper function to traverse the tree and sum all node depths.
*/

public class A04AllKindsOfNodeDepths {

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

  // Function to compute the sum of all kinds of node depths
  public int allKindsOfNodeDepths(TreeNode root) {
    return nodeDepthsHelper(root);
  }

  // Helper function to calculate the sum of depths of all nodes in the tree
  private int nodeDepthsHelper(TreeNode node) {
    if (node == null) return 0; // Base case: If node is null, return 0.

    // Calculate the depth sum of left and right subtrees
    int leftSum = nodeDepthsHelper(node.left);
    int rightSum = nodeDepthsHelper(node.right);

    // Calculate the sum of node depths for the current node's subtree
    int currentNodeDepthSum = calculateNodeDepths(node, 0);

    // Total sum includes the depth sum from both subtrees and the current node's depth sum
    return currentNodeDepthSum + leftSum + rightSum;
  }

  // Function to calculate the depth sum for a given node's subtree
  private int calculateNodeDepths(TreeNode node, int currentDepth) {
    if (node == null) return 0; // Base case: If node is null, return 0.

    // Depth sum is current depth + depth of left subtree + depth of right subtree
    return currentDepth
        + calculateNodeDepths(node.left, currentDepth + 1)
        + calculateNodeDepths(node.right, currentDepth + 1);
  }

  // Driver code to test the All Kinds of Node Depths function
  public static void main(String[] args) {
    A04AllKindsOfNodeDepths solution = new A04AllKindsOfNodeDepths();

    // Example: Create the sample binary tree
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(7);
    root.left.left.left = new TreeNode(8);
    root.left.left.right = new TreeNode(9);

    // Compute and print the sum of all node depths
    int result = solution.allKindsOfNodeDepths(root);
    System.out.println("Sum of all node depths: " + result); // Expected Output: 26
  }

  /*
   Time Complexity:
   - O(n^2), where n is the number of nodes in the tree. This is because for each node, we are calculating the depth sum for every node in its subtree, leading to quadratic complexity.

   Space Complexity:
   - O(h), where h is the height of the tree, due to the recursion stack.
   - In the worst case (skewed tree), the height could be O(n), leading to O(n) space complexity.
   - In the best case (balanced tree), the height is O(log n), so the space complexity is O(log n).
  */
}
