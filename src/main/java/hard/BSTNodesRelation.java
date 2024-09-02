/*
 * Problem Statement:
 * You are given three nodes (nodeOne, nodeTwo, and nodeThree) that are contained in the same Binary Search Tree (BST).
 * Write a function that returns a boolean indicating whether one of nodeOne or nodeThree is an ancestor of nodeTwo,
 * and the other node is a descendant of nodeTwo.
 *
 * Definitions:
 * - A node N is a descendant of another node M if M is an ancestor of N, meaning N is in the subtree rooted at M.
 * - A node M is an ancestor of another node N if N is a descendant of M.
 *
 * It is not guaranteed that nodeOne or nodeThree will be ancestors or descendants of nodeTwo,
 * but it is guaranteed that all three nodes are unique and non-null.
 *
 * Example:
 * Input:
 * tree =    5
 *          / \
 *         2   7
 *        / \ / \
 *       1  4 6  8
 *      /  /
 *     0  3
 * nodeOne = 5 (node with value 5)
 * nodeTwo = 2 (node with value 2)
 * nodeThree = 3 (node with value 3)
 * Output: true  // nodeOne is an ancestor of nodeTwo, and nodeThree is a descendant of nodeTwo.
 */
/*Brute Force Solution
Approach:
Ancestor Check: Determine if one node is an ancestor of nodeTwo. This can be done by traversing the tree from the root and checking if either nodeOne or nodeThree is reached first.
Descendant Check: If an ancestor is found, verify whether the other node is a descendant of nodeTwo by traversing the subtree rooted at nodeTwo.
Time Complexity:
O(h + h'), where h is the height of the tree up to the ancestor and h' is the height of the subtree where we search for the descendant. This is because we might need to traverse both the path from the root to nodeTwo and from nodeTwo to the descendant.
        Space Complexity:
        O(h), where h is the height of the tree, due to the recursive call stack.*/
package hard;

public class BSTNodesRelation {
  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      left = null;
      right = null;
    }
  }

  public static boolean validateNodesRelation(
      TreeNode nodeOne, TreeNode nodeTwo, TreeNode nodeThree) {
    if (isAncestor(nodeOne, nodeTwo)) {
      return isDescendant(nodeTwo, nodeThree);
    } else if (isAncestor(nodeThree, nodeTwo)) {
      return isDescendant(nodeTwo, nodeOne);
    }
    return false;
  }

  private static boolean isAncestor(TreeNode ancestor, TreeNode node) {
    if (ancestor == null) return false;
    if (ancestor == node) return true;
    return isAncestor(ancestor.left, node) || isAncestor(ancestor.right, node);
  }

  private static boolean isDescendant(TreeNode node, TreeNode descendant) {
    if (node == null) return false;
    if (node == descendant) return true;
    return isDescendant(node.left, descendant) || isDescendant(node.right, descendant);
  }

  public static void main(String[] args) {
    // Example tree structure
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(2);
    root.right = new TreeNode(7);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(4);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(8);
    root.left.left.left = new TreeNode(0);
    root.left.right.left = new TreeNode(3);

    TreeNode nodeOne = root; // Node with value 5
    TreeNode nodeTwo = root.left; // Node with value 2
    TreeNode nodeThree = root.left.right.left; // Node with value 3

    System.out.println(validateNodesRelation(nodeOne, nodeTwo, nodeThree)); // Output: true
  }
}
/*
Optimized Solution
Approach:
Single Traversal: Instead of two separate checks (ancestor and descendant), the problem can be solved in a single traversal:

Traverse the tree and simultaneously check if one of nodeOne or nodeThree is an ancestor of nodeTwo while keeping track of the potential descendant.
Condition Verification: During the traversal, once an ancestor is identified, immediately check for the descendant in the corresponding subtree.

Time Complexity:
O(h), where h is the height of the tree. The solution traverses the tree only once.
Space Complexity:
O(1), excluding the recursive call stack. The algorithm uses a constant amount of space.*/
/*public class BSTNodesRelation {

  public static boolean validateNodesRelationOptimized(TreeNode nodeOne, TreeNode nodeTwo, TreeNode nodeThree) {
    if (isAncestorOptimized(nodeOne, nodeTwo)) {
      return isDescendantOptimized(nodeTwo, nodeThree);
    } else if (isAncestorOptimized(nodeThree, nodeTwo)) {
      return isDescendantOptimized(nodeTwo, nodeOne);
    }
    return false;
  }

  private static boolean isAncestorOptimized(TreeNode ancestor, TreeNode node) {
    while (ancestor != null) {
      if (ancestor == node) return true;
      ancestor = (node.value < ancestor.value) ? ancestor.left : ancestor.right;
    }
    return false;
  }

  private static boolean isDescendantOptimized(TreeNode node, TreeNode descendant) {
    while (node != null) {
      if (node == descendant) return true;
      node = (descendant.value < node.value) ? node.left : node.right;
    }
    return false;
  }

  public static void main(String[] args) {
    // Example tree structure
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(2);
    root.right = new TreeNode(7);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(4);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(8);
    root.left.left.left = new TreeNode(0);
    root.left.right.left = new TreeNode(3);

    TreeNode nodeOne = root;           // Node with value 5
    TreeNode nodeTwo = root.left;      // Node with value 2
    TreeNode nodeThree = root.left.right.left;  // Node with value 3

    System.out.println(validateNodesRelationOptimized(nodeOne, nodeTwo, nodeThree)); // Output: true
  }
}*/
