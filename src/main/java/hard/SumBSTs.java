/*
 * Problem Statement:
 * Given a Binary Tree, determine the sum of all the values of nodes that are part of any
 * Binary Search Tree (BST) within the Binary Tree that contains at least 3 nodes.
 *
 * A Binary Search Tree (BST) is a special type of Binary Tree where each node satisfies the following conditions:
 * - The value of the node is strictly greater than the values of all nodes in its left subtree.
 * - The value of the node is less than or equal to the values of all nodes in its right subtree.
 * - Both the left and right subtrees must also be valid BSTs or be empty (None/null).
 *
 * Example 1:
 * Input:
 *           8
 *         /    \
 *       2       9
 *     /   \       \
 *   1     10       5
 *
 * Output: 13 // The subtree rooted at node 2 is a BST containing nodes 1, 2, and 10.
 *
 * Example 2:
 * Input:
 *            20
 *          /    \
 *         7      10
 *       /   \   /   \
 *      0     8 5    15
 *          / \ / \  / \
 *         7   9 2  13 22
 *              /      \
 *             1       14
 *
 * Output: 118 // The subtrees rooted at nodes 7 and 10 are both BSTs and their node values add up to 118.
 *
 * Example 3:
 * Input:
 *           20
 *         /    \
 *        9      10
 *      /   \   /   \
 *     0     8 6    15
 *         /  / \   / \
 *        7  2   5 17 22
 *           /      \
 *          1        14
 *
 * Output: 0 // No BST contains at least 3 nodes.
 */
/*Brute Force Solution
Approach:
The brute force approach involves checking every subtree within the Binary Tree to see if it forms a valid BST with at least 3 nodes. If it does, we sum the values of its nodes. This approach requires a lot of redundant checks and is inefficient.

Time Complexity:
O(n^3): For each node, we could check every possible subtree rooted at that node.
Space Complexity:
O(h): The space complexity is mainly due to the recursion stack, where h is the height of the tree.*/

package hard;

public class SumBSTs {

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

  // Brute Force Solution
  public static int sumOfBSTNodesBruteForce(BinaryTree root) {
    if (root == null) {
      return 0;
    }

    int sum = 0;
    if (isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE) && countNodes(root) >= 3) {
      sum += sumOfSubtree(root);
    }

    sum += sumOfBSTNodesBruteForce(root.left);
    sum += sumOfBSTNodesBruteForce(root.right);

    return sum;
  }

  private static boolean isBST(BinaryTree node, int min, int max) {
    if (node == null) {
      return true;
    }
    if (node.value < min || node.value > max) {
      return false;
    }
    return isBST(node.left, min, node.value - 1) && isBST(node.right, node.value, max);
  }

  private static int countNodes(BinaryTree node) {
    if (node == null) {
      return 0;
    }
    return 1 + countNodes(node.left) + countNodes(node.right);
  }

  private static int sumOfSubtree(BinaryTree node) {
    if (node == null) {
      return 0;
    }
    return node.value + sumOfSubtree(node.left) + sumOfSubtree(node.right);
  }

  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(8);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(9);
    root.left.left = new BinaryTree(1);
    root.left.right = new BinaryTree(10);
    root.right.right = new BinaryTree(5);

    System.out.println(sumOfBSTNodesBruteForce(root)); // Output: 13
  }
}
/*
Optimized Solution
Approach:
The optimized approach uses a bottom-up traversal to check if each subtree is a valid BST while simultaneously calculating the sum of nodes in that subtree. If the subtree forms a valid BST and contains at least 3 nodes, we add its sum to the total.

Time Complexity:
O(n): We traverse each node of the tree once.
Space Complexity:
O(h): Due to the recursion stack, where h is the height of the tree.*/
/*
public class BinaryTreeToBstSum {

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

  static class TreeInfo {
    boolean isBST;
    int size;
    int minValue;
    int maxValue;
    int sum;

    TreeInfo(boolean isBST, int size, int minValue, int maxValue, int sum) {
      this.isBST = isBST;
      this.size = size;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.sum = sum;
    }
  }

  public static int sumOfBSTNodesOptimized(BinaryTree root) {
    return sumBSTHelper(root).sum;
  }

  private static TreeInfo sumBSTHelper(BinaryTree node) {
    if (node == null) {
      return new TreeInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }

    TreeInfo leftInfo = sumBSTHelper(node.left);
    TreeInfo rightInfo = sumBSTHelper(node.right);

    boolean isBST = leftInfo.isBST && rightInfo.isBST && node.value > leftInfo.maxValue && node.value <= rightInfo.minValue;
    int size = 1 + leftInfo.size + rightInfo.size;
    int minValue = Math.min(node.value, leftInfo.minValue);
    int maxValue = Math.max(node.value, rightInfo.maxValue);

    int sum = leftInfo.sum + rightInfo.sum;
    if (isBST && size >= 3) {
      sum += node.value;
    }

    return new TreeInfo(isBST, size, minValue, maxValue, sum);
  }

  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(20);
    root.left = new BinaryTree(7);
    root.right = new BinaryTree(10);
    root.left.left = new BinaryTree(0);
    root.left.right = new BinaryTree(8);
    root.right.left = new BinaryTree(5);
    root.right.right = new BinaryTree(15);
    root.left.right.left = new BinaryTree(7);
    root.left.right.right = new BinaryTree(9);
    root.right.left.left = new BinaryTree(2);
    root.right.left.right = new BinaryTree(5);
    root.right.right.left = new BinaryTree(13);
    root.right.right.right = new BinaryTree(22);
    root.right.left.left.left = new BinaryTree(1);
    root.right.right.left.right = new BinaryTree(14);

    System.out.println(sumOfBSTNodesOptimized(root));  // Output: 118
  }
}
*/
