package cools.binarytrees;

/*
 Problem: Binary Tree Maximum Path Sum

 A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them.
 A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

 The path sum of a path is the sum of the node's values in the path.

 Given the root of a binary tree, return the maximum path sum of any non-empty path.

 Example:

 Input:
        -10
       /   \
      9     20
           /  \
          15   7

 Output: 42

 Explanation:
 The path with the maximum sum is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
*/

/*
 Solution Steps:

 1. Use a recursive function to explore each node.
 2. For each node, calculate two things:
    a) The maximum path sum **including** the current node as the root of the path.
    b) The maximum path sum that can be contributed **from** the current node to its parent.
 3. Update the global maximum path sum if the path through the current node (including both left and right subtrees) is greater than the previously recorded maximum.
 4. Recursively compute these sums for each node's left and right subtrees.
 5. Return the global maximum path sum after processing all nodes.
*/

public class A11BinaryTreeMaximumPathSum {

  // TreeNode class definition
  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }
  }

  // Function to calculate the maximum path sum in the binary tree
  public int maxPathSum(TreeNode root) {
    int[] res =
        new int[1]; // Store the result in an array (since Java doesn't support pass-by-reference)
    res[0] = Integer.MIN_VALUE; // Initialize with the minimum possible value
    maxPathSumUtil(root, res);
    return res[0];
  }

  // Helper function to recursively calculate the maximum path sum
  public int maxPathSumUtil(TreeNode root, int[] ans) {
    // Base case: if the node is null, return 0
    if (root == null) return 0;

    // Recursively calculate the maximum path sum of the left and right subtrees
    int left = maxPathSumUtil(root.left, ans);
    int right = maxPathSumUtil(root.right, ans);

    // Calculate the maximum path sum for the current node considering all possibilities
    int nodeMax =
        Math.max(
            Math.max(root.val, root.val + left + right),
            Math.max(root.val + left, root.val + right));

    // Update the global maximum path sum
    ans[0] = Math.max(ans[0], nodeMax);

    // Return the maximum path sum for a single path starting from this node
    return Math.max(root.val, Math.max(root.val + left, root.val + right));
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A11BinaryTreeMaximumPathSum solution = new A11BinaryTreeMaximumPathSum();

    // Example: Create a sample binary tree: [-10, 9, 20, null, null, 15, 7]
    TreeNode root = new TreeNode(-10);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    // Calculate and print the maximum path sum
    System.out.println("Maximum Path Sum: " + solution.maxPathSum(root)); // Output: 42
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited exactly once to compute the maximum path sum.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   - In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
