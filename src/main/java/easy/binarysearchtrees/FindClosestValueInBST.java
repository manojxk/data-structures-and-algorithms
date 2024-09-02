/*
 * Problem Statement:
 *
 * Write a function that takes in a Binary Search Tree (BST) and a target
 * integer value and returns the closest value to that target value contained
 * in the BST.
 *
 * You can assume that there will only be one closest value.
 *
 * Each BST node has an integer `value`, a `left` child node, and a `right`
 * child node. A node is said to be a valid BST node if and only if it satisfies
 * the BST property: its `value` is strictly greater than the values of every
 * node to its left; its `value` is less than or equal to the values of every
 * node to its right; and its children nodes are either valid BST nodes
 * themselves or `None` / `null`.
 *
 * Example:
 * Input: tree =   10
 *                /   \
 *               5     15
 *              / \   /  \
 *             2   5 13  22
 *            /       \
 *           1        14
 *        target = 12
 * Output: 13
 */

package easy.binarysearchtrees;

public class FindClosestValueInBST {

  static class BST {
    int value;
    BST left;
    BST right;

    BST(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Optimized Solution using the BST property
  public static int findClosestValueInBst(BST root, int target) {
    return findClosestValueInBstHelper(root, target, root.value);
  }

  private static int findClosestValueInBstHelper(BST root, int target, int closest) {
    BST currentNode = root;
    while (currentNode != null) {
      if (Math.abs(target - closest) > Math.abs(target - currentNode.value)) {
        closest = currentNode.value;
      }
      if (target < currentNode.value) {
        currentNode = currentNode.left;
      } else if (target > currentNode.value) {
        currentNode = currentNode.right;
      } else {
        break;
      }
    }
    return closest;
  }

  private static int findClosestValueInBstHelperRecursive(BST tree, int target, int closest) {
    if (tree == null) {
      return closest;
    }

    if (Math.abs(target - closest) > Math.abs(target - tree.value)) {
      closest = tree.value;
    }

    if (target < tree.value) {
      return findClosestValueInBstHelper(tree.left, target, closest);
    } else if (target > tree.value) {
      return findClosestValueInBstHelper(tree.right, target, closest);
    } else {
      return closest;
    }
  }

  public static void main(String[] args) {
    BST root = new BST(10);
    root.left = new BST(5);
    root.right = new BST(15);
    root.left.left = new BST(2);
    root.left.right = new BST(5);
    root.right.left = new BST(13);
    root.right.right = new BST(22);
    root.left.left.left = new BST(1);
    root.right.left.right = new BST(14);

    int target = 12;
    int closest = findClosestValueInBst(root, target);
    System.out.println("Closest value to " + target + " is " + closest);
  }
}

/*
Iterative
O(log n) time and O(1) space for balanced BSTs;
O(n) time in the worst case.

Recursive

Time Complexity:
O(log n): In the best case, where the BST is balanced.
O(n): In the worst case, where the BST is unbalanced (similar to a linked list).
Space Complexity:
O(log n): In the best case, due to the call stack in a balanced tree.
O(n): In the worst case, due to the call stack in an unbalanced tree.

*/
