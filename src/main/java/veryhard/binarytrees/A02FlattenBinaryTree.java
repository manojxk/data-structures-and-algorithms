package veryhard.binarytrees;

/*
 * Problem Statement:
 *
 * Write a function that takes in a Binary Tree, flattens it, and returns its leftmost node.
 * A flattened Binary Tree is a structure that's nearly identical to a doubly linked list
 * (except that nodes have left and right pointers instead of prev and next pointers),
 * where nodes follow the original tree's left-to-right order (in-order traversal).
 *
 * The flattening should be done in place, meaning that the original data structure should be mutated,
 * and no new structure should be created.
 *
 * If the input Binary Tree happens to be a valid Binary Search Tree (BST), the nodes in the flattened tree will be sorted.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node.
 * Children nodes can either be BinaryTree nodes themselves or null.
 *
 * Example:
 * Input:
 *        1
 *       / \
 *      2   3
 *     / \  /
 *    4   5 6
 *       / \
 *      7   8
 *
 * Output:
 * Flattened Binary Tree as doubly linked list: 1 <-> 2 <-> 4 <-> 5 <-> 7 <-> 8 <-> 3 <-> 6
 */

/*
 * Solution Approach:
 *
 * 1. Perform an in-order traversal of the binary tree.
 * 2. For each node:
 *    - Attach the left subtree as the right subtree of the current node.
 *    - Move the original right subtree to the end of the newly attached right subtree.
 * 3. Flatten the tree in place by adjusting the left and right pointers, and return the leftmost node.
 */

public class A02FlattenBinaryTree {

  // Definition for a binary tree node
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

  // Function to flatten the binary tree and return the leftmost node
  public static BinaryTree flattenBinaryTree(BinaryTree root) {
    if (root == null) return null;

    // Helper function to recursively flatten the tree
    return flattenTreeHelper(root)[0]; // Return the leftmost node
  }

  // Helper function that returns the leftmost and rightmost nodes of the flattened tree
  private static BinaryTree[] flattenTreeHelper(BinaryTree node) {
    if (node == null) {
      return new BinaryTree[] {null, null};
    }

    // Recursively flatten the left and right subtrees
    BinaryTree[] leftSubtree = flattenTreeHelper(node.left);
    BinaryTree[] rightSubtree = flattenTreeHelper(node.right);

    // Flatten the current node
    if (leftSubtree[1] != null) {
      leftSubtree[1].right = node;
      node.left = leftSubtree[1];
    }

    if (rightSubtree[0] != null) {
      node.right = rightSubtree[0];
      rightSubtree[0].left = node;
    }

    // The leftmost node is either from the left subtree or the current node
    BinaryTree leftmost = leftSubtree[0] != null ? leftSubtree[0] : node;
    // The rightmost node is either from the right subtree or the current node
    BinaryTree rightmost = rightSubtree[1] != null ? rightSubtree[1] : node;

    return new BinaryTree[] {leftmost, rightmost};
  }

  // Main function to test the Flatten Binary Tree implementation
  public static void main(String[] args) {
    // Constructing the binary tree
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.left.right.left = new BinaryTree(7);
    root.left.right.right = new BinaryTree(8);

    // Flatten the binary tree and get the leftmost node
    BinaryTree leftmost = flattenBinaryTree(root);

    // Print the flattened tree in linked-list format
    printFlattenedTree(leftmost); // Expected output: 1 <-> 2 <-> 4 <-> 5 <-> 7 <-> 8 <-> 3 <-> 6
  }

  // Helper function to print the flattened tree as a linked list
  public static void printFlattenedTree(BinaryTree node) {
    BinaryTree current = node;
    while (current != null) {
      System.out.print(current.value);
      current = current.right;
      if (current != null) {
        System.out.print(" <-> ");
      }
    }
    System.out.println();
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of nodes in the binary tree. Each node is visited once during the recursion.
   *
   * Space Complexity:
   * O(h), where h is the height of the tree. This is due to the recursion stack. In the worst case (for a skewed tree),
   * this could be O(n). For a balanced tree, the space complexity is O(log n).
   */
}
