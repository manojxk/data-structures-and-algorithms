/*
 * Problem Statement:
 * Write three functions that traverse a given Binary Search Tree (BST) using in-order,
 * pre-order, and post-order tree-traversal techniques, respectively.
 *
 * Each function should:
 * 1. Take in a BST and an empty array as input.
 * 2. Traverse the BST and add each node's value to the input array.
 * 3. Return the input array after the traversal is complete.
 *
 * Traversal Techniques:
 * - In-Order: Traverse the left subtree, visit the root node, then traverse the right subtree.
 * - Pre-Order: Visit the root node, traverse the left subtree, then traverse the right subtree.
 * - Post-Order: Traverse the left subtree, traverse the right subtree, then visit the root node.
 *
 * Example:
 * Input:
 *        10
 *       /  \
 *      5    15
 *     / \     \
 *    2   5     22
 *   /
 *  1
 *
 * Output:
 * - inOrderTraverse: [1, 2, 5, 5, 10, 15, 22]
 * - preOrderTraverse: [10, 5, 2, 1, 5, 15, 22]
 * - postOrderTraverse: [1, 2, 5, 5, 22, 15, 10]
 */

package medium;

import java.util.*;

public class BSTTraversal {
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

  // In-Order Traversal
  public static void inOrderTraverse(TreeNode node, List<Integer> array) {
    if (node != null) {
      inOrderTraverse(node.left, array);
      array.add(node.value);
      inOrderTraverse(node.right, array);
    }
  }

  // Pre-Order Traversal
  public static void preOrderTraverse(TreeNode node, List<Integer> array) {
    if (node != null) {
      array.add(node.value);
      preOrderTraverse(node.left, array);
      preOrderTraverse(node.right, array);
    }
  }

  // Post-Order Traversal
  public static void postOrderTraverse(TreeNode node, List<Integer> array) {
    if (node != null) {
      postOrderTraverse(node.left, array);
      postOrderTraverse(node.right, array);
      array.add(node.value);
    }
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.right = new TreeNode(15);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(22);
    root.left.left.left = new TreeNode(1);

    List<Integer> inOrder = new ArrayList<>();
    inOrderTraverse(root, inOrder);
    System.out.println(inOrder); // Output: [1, 2, 5, 5, 10, 15, 22]

    List<Integer> preOrder = new ArrayList<>();
    preOrderTraverse(root, preOrder);
    System.out.println(preOrder); // Output: [10, 5, 2, 1, 5, 15, 22]

    List<Integer> postOrder = new ArrayList<>();
    postOrderTraverse(root, postOrder);
    System.out.println(postOrder); // Output: [1, 2, 5, 5, 22, 15, 10]
  }
}

/*Time Complexity:
O(n): Each node is visited exactly once.
Space Complexity:
O(h): The space complexity is determined by the call stack depth, where h is the height of the tree.*/
