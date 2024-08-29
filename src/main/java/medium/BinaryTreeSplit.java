/*
 * Problem Statement:
 * Write a function that takes in a Binary Tree with at least one node and
 * checks if that Binary Tree can be split into two Binary Trees of equal sum
 * by removing a single edge. If this split is possible, return the new sum of each
 * Binary Tree, otherwise return 0.
 *
 * The sum of a Binary Tree is the sum of all values in that Binary Tree.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right
 * child node. Children nodes can either be BinaryTree nodes themselves or null.
 *
 * Example:
 *
 * tree =      1
 *           /    \
 *          3      -2
 *        /  \    /  \
 *       6   -5  5    2
 *      /
 *     2
 *
 * Output:
 * 6  // Remove the edge to the left of the root node, creating two trees, each with sums of 6
 */
/*
Brute Force Solution
Approach:
The brute force approach involves calculating the sum of the entire tree and then, for every possible edge that could be removed, checking if the sum of the resulting subtrees is equal. This requires exploring all possible edges in the tree.

Time Complexity:
O(n^2): For each node, the sum of its subtree is calculated, which can take O(n) time. This is done for all nodes, leading to O(n^2) overall time complexity.
Space Complexity:
O(h): Space complexity is due to the recursion stack, where h is the height of the tree.*/

package medium;

public class BinaryTreeSplit {

  // Brute Force Solution: Checking all possible splits
  public static int canSplitBinaryTree(BinaryTree root) {
    int totalSum = treeSum(root);

    // Check if there exists a subtree whose sum is half of the total sum
    return canSplitHelper(root, totalSum) ? totalSum / 2 : 0;
  }

  // Helper method to calculate the sum of a tree
  private static int treeSum(BinaryTree node) {
    if (node == null) return 0;
    return node.value + treeSum(node.left) + treeSum(node.right);
  }

  // Helper method to check if a valid split exists
  private static boolean canSplitHelper(BinaryTree node, int totalSum) {
    if (node == null) return false;

    int subtreeSum = treeSum(node);

    // Check if removing this subtree results in two equal sums
    if (subtreeSum * 2 == totalSum) return true;

    // Recursively check in left and right subtrees
    return canSplitHelper(node.left, totalSum) || canSplitHelper(node.right, totalSum);
  }

  // Helper class to define the structure of a BinaryTree node
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

  public static void main(String[] args) {
    // Constructing the tree
    BinaryTree tree = new BinaryTree(1);
    tree.left = new BinaryTree(3);
    tree.right = new BinaryTree(-2);
    tree.left.left = new BinaryTree(6);
    tree.left.right = new BinaryTree(-5);
    tree.left.left.left = new BinaryTree(2);
    tree.right.left = new BinaryTree(5);
    tree.right.right = new BinaryTree(2);

    // Checking if the tree can be split into two trees with equal sum
    System.out.println(canSplitBinaryTree(tree)); // Output: 6
  }
}

/*
Optimized Solution
Approach:
The optimized approach avoids recalculating the sum of subtrees repeatedly. Instead, it calculates the total sum of the tree once and then checks for possible splits by calculating subtree sums during a single traversal. If any subtree has a sum equal to half of the total sum, the tree can be split.

Time Complexity:
O(n): The tree is traversed only once to calculate subtree sums and check for a valid split.
Space Complexity:
O(h): Space complexity is determined by the recursion stack, where h is the height of the tree.*/

/*public class BinaryTreeSplit {

  // Optimized Solution: Single traversal to check for a valid split
  public static int canSplitBinaryTree(BinaryTree root) {
    int totalSum = treeSum(root);
    if (totalSum % 2 != 0) return 0; // If totalSum is odd, can't split into equal parts

    if (canSplitHelper(root, totalSum / 2) != -1) {
      return totalSum / 2;
    }

    return 0;
  }

  // Helper method to calculate the sum of a tree and check for a valid split
  private static int canSplitHelper(BinaryTree node, int halfSum) {
    if (node == null) return 0;

    // Calculate current subtree sum
    int subtreeSum = node.value
            + canSplitHelper(node.left, halfSum)
            + canSplitHelper(node.right, halfSum);

    // Check if this subtree can form a valid split
    if (subtreeSum == halfSum) {
      return -1;  // Marking as found (avoid counting this subtree again)
    }

    return subtreeSum;
  }

  // Helper method to calculate the total sum of the tree
  private static int treeSum(BinaryTree node) {
    if (node == null) return 0;
    return node.value + treeSum(node.left) + treeSum(node.right);
  }

  // Helper class to define the structure of a BinaryTree node
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

  public static void main(String[] args) {
    // Constructing the tree
    BinaryTree tree = new BinaryTree(1);
    tree.left = new BinaryTree(3);
    tree.right = new BinaryTree(-2);
    tree.left.left = new BinaryTree(6);
    tree.left.right = new BinaryTree(-5);
    tree.left.left.left = new BinaryTree(2);
    tree.right.left = new BinaryTree(5);
    tree.right.right = new BinaryTree(2);

    // Checking if the tree can be split into two trees with equal sum
    System.out.println(canSplitBinaryTree(tree));  // Output: 6
  }
}*/
