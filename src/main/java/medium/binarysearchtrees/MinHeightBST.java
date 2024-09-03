/*
 * Problem Statement:
 * Given a non-empty sorted array of distinct integers, write a function to construct
 * a Binary Search Tree (BST) with minimal height and return the root of the BST.
 *
 * The constructed BST must satisfy the following properties:
 * 1. Each node in the BST has an integer value, a left child node, and a right child node.
 * 2. The value of each node is strictly greater than the values of all nodes in its left subtree.
 * 3. The value of each node is less than or equal to the values of all nodes in its right subtree.
 * 4. The height of the BST is minimized.
 *
 * Constraints:
 * - The array is non-empty and sorted in ascending order.
 * - The array contains distinct integers.
 *
 * Example:
 * Input:
 * array = [1, 2, 5, 7, 10, 13, 14, 15, 22]
 *
 * Output:
 *         10
 *       /    \
 *      2      14
 *    /  \    /  \
 *   1    5  13   15
 *           \     \
 *            7    22
 * // This is one example of a BST with minimal height that can be created from the input array.
 */
/*Optimized Solution: Recursive Approach
Approach:
Divide and Conquer: The key to minimizing the height of the BST is to choose the middle element of the sorted array as the root. This ensures that the tree is as balanced as possible.
Recursive Construction: Recursively repeat this process for the left and right subarrays to construct the left and right subtrees.
Time Complexity:
O(n): Each element in the array is processed exactly once.
Space Complexity:
O(log n): The space complexity is determined by the depth of the recursion tree, which is log(n) for a balanced tree.*/

package medium.binarysearchtrees;

public class MinHeightBST {
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

  // Optimized Solution: Recursive Approach
  public static BST minHeightBST(int[] array) {
    return constructMinHeightBST(array, 0, array.length - 1);
  }

  private static BST constructMinHeightBST(int[] array, int startIdx, int endIdx) {
    if (startIdx > endIdx) {
      return null;
    }

    int midIdx = (startIdx + endIdx) / 2;
    BST bst = new BST(array[midIdx]);

    bst.left = constructMinHeightBST(array, startIdx, midIdx - 1);
    bst.right = constructMinHeightBST(array, midIdx + 1, endIdx);

    return bst;
  }

  // Helper function to print the BST (Preorder Traversal)
  public static void printBST(BST node) {
    if (node == null) return;
    System.out.print(node.value + " ");
    printBST(node.left);
    printBST(node.right);
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 5, 7, 10, 13, 14, 15, 22};
    BST root = minHeightBST(array);
    printBST(root); // Output: 10 2 1 5 7 14 13 15 22
  }
}
