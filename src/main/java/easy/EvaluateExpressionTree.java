/*
 * Problem Statement:
 * You're given a binary expression tree where:
 * - All leaf nodes represent operands, which are always positive integers.
 * - All non-leaf nodes represent operators, which are represented by specific negative integers:
 *   - -1: Addition operator (+)
 *   - -2: Subtraction operator (-)
 *   - -3: Division operator (/)
 *   - -4: Multiplication operator (*)
 *
 * Write a function to evaluate this tree mathematically and return a single resulting integer.
 *
 * The tree will always be a valid expression tree, and you should evaluate the bottom of the tree first.
 *
 * Example:
 * Input:
 *            -1
 *          /    \
 *        -2      -3
 *       /   \   /   \
 *     -4     2  8    3
 *    /   \
 *   2     3
 *
 * Output: 6
 * Explanation:
 * The expression tree corresponds to the expression: (((2 * 3) - 2) + (8 / 3))
 * - ((2 * 3) = 6) => -2 node evaluates to (6 - 2 = 4)
 * - (8 / 3 = 2) (rounded towards zero) => -3 node evaluates to 2
 * - The root node (-1) adds these results: (4 + 2 = 6)
 */

package easy;

public class EvaluateExpressionTree {

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

  // Recursive function to evaluate the expression tree
  public static int evaluateExpressionTree(BinaryTree root) {
    if (root == null) {
      return 0;
    }

    // If it's a leaf node (operand), return its value
    if (root.left == null && root.right == null) {
      return root.value;
    }

    // Recursively evaluate left and right subtrees
    int leftValue = evaluateExpressionTree(root.left);
    int rightValue = evaluateExpressionTree(root.right);

    // Apply the operator at the current node
    switch (root.value) {
      case -1: // Addition
        return leftValue + rightValue;
      case -2: // Subtraction
        return leftValue - rightValue;
      case -3: // Division
        return leftValue / rightValue;
      case -4: // Multiplication
        return leftValue * rightValue;
      default:
        throw new IllegalArgumentException("Invalid operator in the tree");
    }
  }

  public static void main(String[] args) {
    // Constructing the tree as per the sample input
    BinaryTree root = new BinaryTree(-1);
    root.left = new BinaryTree(-2);
    root.right = new BinaryTree(-3);
    root.left.left = new BinaryTree(-4);
    root.left.right = new BinaryTree(2);
    root.right.left = new BinaryTree(8);
    root.right.right = new BinaryTree(3);
    root.left.left.left = new BinaryTree(2);
    root.left.left.right = new BinaryTree(3);

    // Evaluating the expression tree
    int result = evaluateExpressionTree(root);
    System.out.println(result); // Output: 6
  }
}

/*

Time Complexity:
O(n): We visit each node exactly once, where n is the number of nodes in the tree.
Space Complexity:
O(h): Where h is the height of the tree. This is the space used by the recursion stack.*/
