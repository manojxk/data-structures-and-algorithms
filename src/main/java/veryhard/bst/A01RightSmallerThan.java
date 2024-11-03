package veryhard.bst;

/*
 * Problem Statement:
 *
 * You are given an array of integers. For each integer in the array, write a function that returns the number of integers
 * that are to the right of the current integer and that are smaller than the current integer.
 *
 * The function should return an array of the same length, where each index i represents the number of integers
 * smaller than the integer at index i.
 *
 * Example:
 *
 * Input: [8, 5, 11, -1, 3, 4, 2]
 * Output: [5, 4, 4, 0, 1, 1, 0]
 *
 * Explanation:
 * - 8 has 5 integers smaller than it to its right: 5, -1, 3, 4, 2
 * - 5 has 4 integers smaller than it to its right: -1, 3, 4, 2
 * - 11 has 4 integers smaller than it to its right: -1, 3, 4, 2
 * - -1 has 0 integers smaller than it to its right
 * - 3 has 1 integer smaller than it to its right: 2
 * - 4 has 1 integer smaller than it to its right: 2
 * - 2 has 0 integers smaller than it to its right
 */

/*
 * Solution Approach:
 *
 * 1. A brute force approach would involve iterating through each element and counting how many elements to its right are smaller than it.
 *    However, this has O(n^2) time complexity and is inefficient for large arrays.
 *
 * 2. An optimized approach uses a Binary Search Tree (BST) to efficiently count and insert elements in the right-to-left order.
 *    We traverse the array from right to left, inserting each element into the BST, and while inserting, we count how many numbers
 *    are smaller than the current number.
 */

import java.util.*;

public class A01RightSmallerThan {

  // Class to define a Binary Search Tree Node
  static class BSTNode {
    int value;
    int leftSubtreeSize; // Number of nodes in the left subtree (smaller values)
    int count; // How many times this value appears in the tree
    BSTNode left;
    BSTNode right;

    public BSTNode(int value) {
      this.value = value;
      this.leftSubtreeSize = 0;
      this.count = 1;
      this.left = null;
      this.right = null;
    }
  }

  // Function to count the number of smaller elements to the right using BST
  public static List<Integer> rightSmallerThan(List<Integer> array) {
    if (array.size() == 0) return new ArrayList<>();
    List<Integer> result = new ArrayList<>(Collections.nCopies(array.size(), 0));

    // Initialize the Binary Search Tree with the last element
    BSTNode root = new BSTNode(array.get(array.size() - 1));

    // Start from the second last element and move to the left (right to left traversal)
    for (int i = array.size() - 2; i >= 0; i--) {
      result.set(i, insertAndCount(array.get(i), root));
    }

    return result;
  }

  // Helper function to insert a value into the BST and return the number of smaller elements
  private static int insertAndCount(int value, BSTNode root) {
    int smallerCount = 0;

    while (true) {
      // If value is smaller than the current node, go left
      if (value < root.value) {
        root.leftSubtreeSize++;
        if (root.left == null) {
          root.left = new BSTNode(value);
          break;
        } else {
          root = root.left;
        }
      }
      // If value is greater than the current node, go right
      else if (value > root.value) {
        smallerCount += root.leftSubtreeSize + root.count;
        if (root.right == null) {
          root.right = new BSTNode(value);
          break;
        } else {
          root = root.right;
        }
      }
      // If value equals the current node's value, increase the count and break
      else {
        smallerCount += root.leftSubtreeSize;
        root.count++;
        break;
      }
    }

    return smallerCount;
  }

  // Main function to test the Right Smaller Than implementation
  public static void main(String[] args) {
    List<Integer> array = Arrays.asList(8, 5, 11, -1, 3, 4, 2);

    // Output: [5, 4, 4, 0, 1, 1, 0]
    List<Integer> result = rightSmallerThan(array);
    System.out.println(result);
  }

  /*
   * Time Complexity:
   * O(n log n), where n is the number of elements in the array.
   * Inserting each element into the binary search tree takes O(log n) time, and we do this for every element in the array.
   *
   * Space Complexity:
   * O(n), where n is the number of elements in the array.
   * The space complexity is due to the space required to store the binary search tree and the result list.
   */
}
