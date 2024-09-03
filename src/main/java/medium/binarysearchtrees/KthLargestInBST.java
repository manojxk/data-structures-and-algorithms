/*
 * Problem Statement:
 * Given a Binary Search Tree (BST) and a positive integer `k`, write a function
 * that returns the `k`th largest integer present in the BST.
 *
 * Assumptions:
 * - The BST contains only integer values.
 * - The integer `k` is less than or equal to the number of nodes in the tree.
 * - Duplicate integers in the BST are treated as distinct values.
 *
 * Example:
 * Input:
 *        15
 *       /  \
 *      5    20
 *     / \   / \
 *    2   5 17 22
 *   / \
 *  1   3
 * k = 3
 *
 * Output: 17
 *
 * Explanation:
 * The elements in the BST in ascending order are [1, 2, 3, 5, 5, 15, 17, 20, 22].
 * The 3rd largest element in this order is 17.
 */

package medium.binarysearchtrees;

import java.util.ArrayList;
import java.util.List;

public class KthLargestInBST {
  // Optimized Solution using Reverse In-Order Traversal
  static int count = 0;
  static int result = -1;

  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Brute Force Solution
  public static int findKthLargestBruteForce(TreeNode root, int k) {
    List<Integer> inOrderList = new ArrayList<>();
    inOrderTraversal(root, inOrderList);
    return inOrderList.get(inOrderList.size() - k);
  }

  private static void inOrderTraversal(TreeNode node, List<Integer> list) {
    if (node == null) return;
    inOrderTraversal(node.left, list);
    list.add(node.value);
    inOrderTraversal(node.right, list);
  }

  public static int findKthLargestOptimized(TreeNode root, int k) {
    count = 0;
    result = -1;
    reverseInOrderTraversal(root, k);
    return result;
  }

  private static void reverseInOrderTraversal(TreeNode node, int k) {
    if (node == null || count >= k) return;

    reverseInOrderTraversal(node.right, k);

    count++;
    if (count == k) {
      result = node.value;
      return;
    }

    reverseInOrderTraversal(node.left, k);
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(15);
    root.left = new TreeNode(5);
    root.right = new TreeNode(20);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(17);
    root.right.right = new TreeNode(22);
    root.left.left.left = new TreeNode(1);
    root.left.left.right = new TreeNode(3);

    int k = 3;
    System.out.println(findKthLargestBruteForce(root, k)); // Output: 17
  }
}

/*
Optimized Solution: Reverse In-Order Traversal
Approach:
Reverse In-Order Traversal: Perform a reverse in-order traversal (right -> node -> left), which visits the largest elements first.
Early Exit: As soon as the kth largest element is found, stop the traversal.
Time Complexity:
O(h + k): In the worst case, the time complexity is proportional to the height of the tree plus k, where h is the height of the tree.
Space Complexity:
O(h): The space complexity is determined by the recursion stack, which is proportional to the height of the tree.*/
