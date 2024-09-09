package cools.binarytrees;

/*
 Problem: Sum Root to Leaf Numbers

 You are given the root of a binary tree containing digits from 0 to 9 only.
 Each root-to-leaf path in the tree represents a number.

 For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
 Return the total sum of all root-to-leaf numbers.

 A leaf node is a node with no children.
 Test cases are generated so that the answer will fit in a 32-bit integer.

 Example:

 Input:
        1
       / \
      2   3

 Output:
 25

 Explanation:
 The root-to-leaf paths are 1->2 (which represents 12) and 1->3 (which represents 13).
 Therefore, the sum = 12 + 13 = 25.
*/

/*
 Solution Steps:

 1. Use a recursive approach to traverse the tree from root to each leaf.
 2. For each node, maintain the number formed by the path from the root to the current node.
 3. When a leaf node is reached, the accumulated number is added to the total sum.
 4. Recursively traverse both left and right subtrees, updating the accumulated number at each step.
 5. Return the total sum after all paths are processed.
*/

public class A10SumRootToLeafNumbers {

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

  // Function to calculate the total sum of all root-to-leaf numbers
  public int sumNumbers(TreeNode root) {
    return sumNumbersHelper(root, 0);
  }

  // Helper function to recursively calculate the sum
  private int sumNumbersHelper(TreeNode node, int currentSum) {
    // Base case: if the node is null, return 0 (no contribution to the sum)
    if (node == null) return 0;

    // Update the current sum by adding the current node's value
    currentSum = currentSum * 10 + node.val;

    // If it's a leaf node, return the current accumulated sum
    if (node.left == null && node.right == null) {
      return currentSum;
    }

    // Recursively calculate the sum for the left and right subtrees
    int leftSum = sumNumbersHelper(node.left, currentSum);
    int rightSum = sumNumbersHelper(node.right, currentSum);

    // Return the sum of the left and right subtree sums
    return leftSum + rightSum;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A10SumRootToLeafNumbers solution = new A10SumRootToLeafNumbers();

    // Example: Create a sample binary tree: [1, 2, 3]
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);

    // Calculate and print the total sum of root-to-leaf numbers
    System.out.println(
        "Total sum of root-to-leaf numbers: " + solution.sumNumbers(root)); // Output: 25
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited exactly once to compute the path sums.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   - In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}

/*public class SumRootToLeafNumbers {
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  public static int branchSums(BinaryTree root) {
    List<Integer> sums = new ArrayList<>();
    calculateBranchSums(root, 0, sums);

    return sums.stream().mapToInt(Integer::intValue).sum();
  }

  private static void calculateBranchSums(BinaryTree node, int runningSum, List<Integer> sums) {
    if (node == null) {
      return;
    }
    int newRunningSum = runningSum * 10 + node.value;
    if (node.left == null && node.right == null) {
      sums.add(newRunningSum);
      return;
    }
    calculateBranchSums(node.left, newRunningSum, sums);
    calculateBranchSums(node.right, newRunningSum, sums);
  }
}*/
