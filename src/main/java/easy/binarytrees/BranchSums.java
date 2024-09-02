/**
 * This class provides a solution to calculate branch sums in a binary tree.
 * A branch sum is defined as the sum of all node values in a path from the
 * root node to any leaf node. The function returns a list of branch sums
 * ordered from the leftmost branch sum to the rightmost branch sum.
 * Sample Input:
 *            1
 *          /   \
 *         2     3
 *       /   \  /  \
 *      4     5 6   7
 *    /   \  /
 *   8     9 10
 * Sample Output:
 * [15, 16, 18, 10, 11]
 * // 15 == 1 + 2 + 4 + 8
 * // 16 == 1 + 2 + 4 + 9
 * // 18 == 1 + 2 + 5 + 10
 * // 10 == 1 + 3 + 6
 * // 11 == 1 + 3 + 7
 */


package easy.binarytrees;

import java.util.ArrayList;
import java.util.List;


public class BranchSums {

  // Definition of the BinaryTree class
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


  public static List<Integer> branchSums(BinaryTree root) {
    List<Integer> sums = new ArrayList<>();
    calculateBranchSums(root, 0, sums);
    return sums;
  }


  private static void calculateBranchSums(BinaryTree node, int runningSum, List<Integer> sums) {
    if (node == null) {
      return;
    }
    int newRunningSum = runningSum + node.value;
    if (node.left == null && node.right == null) {
      sums.add(newRunningSum);
      return;
    }
    calculateBranchSums(node.left, newRunningSum, sums);
    calculateBranchSums(node.right, newRunningSum, sums);
  }

  // Main method to test the branch sums calculation
  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right = new BinaryTree(7);
    root.left.left.left = new BinaryTree(8);
    root.left.left.right = new BinaryTree(9);
    root.left.right.left = new BinaryTree(10);

    List<Integer> result = branchSums(root);
    System.out.println(result); // Output: [15, 16, 18, 10, 11]
  }
}


/*Time Complexity:
O(n): We visit each node in the tree exactly once, where n is the number of nodes.
Space Complexity:
O(n): The space used by the list to store the branch sums and by the recursive call stack.*/
