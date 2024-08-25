/*
 * Problem Statement:
 * The distance between a node in a Binary Tree and the tree's root is called the node's depth.
 * Write a function that takes in a Binary Tree and returns the sum of its nodes' depths.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node.
 * Children nodes can either be BinaryTree nodes themselves or null.
 *
 * Example:
 * Input:
 *            1
 *          /   \
 *         2     3
 *       /   \ /   \
 *      4    5 6    7
 *    /   \
 *   8     9
 *
 * Output: 16
 *
 * Explanation:
 * The sum of the depths of all nodes is:
 * - Depth of node 1: 0
 * - Depth of node 2: 1
 * - Depth of node 3: 1
 * - Depth of node 4: 2
 * - Depth of node 5: 2
 * - Depth of node 6: 2
 * - Depth of node 7: 2
 * - Depth of node 8: 3
 * - Depth of node 9: 3
 * Total sum = 0 + 1 + 1 + 2 + 2 + 2 + 2 + 3 + 3 = 16
 */

package easy;

import java.util.Stack;

public class NodeDepths {

  // Binary Tree node definition
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

  public static int nodeDepthsRecursive(BinaryTree root) {
    return calculateNodeDepths(root, 0);
  }

  private static int calculateNodeDepths(BinaryTree node, int depth) {
    if (node == null) return 0;
    return depth
        + calculateNodeDepths(node.left, depth + 1)
        + calculateNodeDepths(node.right, depth + 1);
  }

  public static int nodeDepthsIterative(BinaryTree root) {
    int sumOfDepths = 0;
    Stack<NodeDepthPair> stack = new Stack<>();
    stack.push(new NodeDepthPair(root, 0));

    while (!stack.isEmpty()) {
      NodeDepthPair nodeDepthPair = stack.pop();
      BinaryTree node = nodeDepthPair.node;
      int depth = nodeDepthPair.depth;

      if (node == null) continue;
      sumOfDepths += depth;
      stack.push(new NodeDepthPair(node.left, depth + 1));
      stack.push(new NodeDepthPair(node.right, depth + 1));
    }

    return sumOfDepths;
  }

  // Helper class to store nodes with their depth
  static class NodeDepthPair {
    BinaryTree node;
    int depth;

    NodeDepthPair(BinaryTree node, int depth) {
      this.node = node;
      this.depth = depth;
    }
  }

  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree(1);
    tree.left = new BinaryTree(2);
    tree.right = new BinaryTree(3);
    tree.left.left = new BinaryTree(4);
    tree.left.right = new BinaryTree(5);
    tree.right.left = new BinaryTree(6);
    tree.right.right = new BinaryTree(7);
    tree.left.left.left = new BinaryTree(8);
    tree.left.left.right = new BinaryTree(9);

    System.out.println(nodeDepthsRecursive(tree)); // Output: 16
  }
}

/*
Brute Force Solution: O(n) time | O(h) space.
Optimized Solution: O(n) time | O(h) space.*/
