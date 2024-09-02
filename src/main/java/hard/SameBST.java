/*
 * Problem Statement:
 * Given two arrays of integers, determine whether they represent the same Binary Search Tree (BST).
 * An array is said to represent the BST obtained by inserting each integer in the array from left to right into the BST.
 *
 * You are not allowed to construct any BSTs in your code.
 *
 * A Binary Search Tree (BST) is a binary tree where each node has the following properties:
 * - The value of the node is strictly greater than the values of all nodes in its left subtree.
 * - The value of the node is less than or equal to the values of all nodes in its right subtree.
 * - Both the left and right subtrees must also be valid BSTs or be empty (None/null).
 *
 * Example:
 * Input:
 * arrayOne = [10, 15, 8, 12, 94, 81, 5, 2, 11]
 * arrayTwo = [10, 8, 5, 15, 2, 12, 11, 94, 81]
 * Output: true  // Both arrays represent the same BST structure.
 */
/*Approach:
The brute force approach would involve comparing the BSTs formed by each array, but as the problem explicitly disallows constructing any BSTs, this approach is not feasible.

Instead, the comparison is done by recursively checking:

If the roots of both subtrees (represented by the first elements of the arrays) are the same.
If the left and right subtrees of the two arrays are structurally identical.
Time Complexity:
O(n^2): In the worst case, we might compare almost every element with every other element due to recursive calls.
Space Complexity:
O(n^2): Due to recursive calls and subarray slicing.*/
/*Optimized Solution
Approach:
The optimized approach avoids unnecessary subarray slicing by passing indices instead. This significantly reduces space complexity.

Time Complexity:
O(n^2): Similar to the brute force approach, but more efficient due to reduced overhead in slicing.
Space Complexity:
O(d), where d is the depth of the recursive calls, typically O(log n) for balanced BSTs and O(n) for unbalanced.*/

package hard;

import java.util.List;
import java.util.ArrayList;

public class SameBST {

  // Brute Force Solution
  public static boolean sameBstsBruteForce(int[] arrayOne, int[] arrayTwo) {
    return areSameBsts(arrayOne, arrayTwo);
  }

  private static boolean areSameBsts(int[] arrayOne, int[] arrayTwo) {
    if (arrayOne.length != arrayTwo.length) {
      return false;
    }
    if (arrayOne.length == 0 && arrayTwo.length == 0) {
      return true;
    }
    if (arrayOne[0] != arrayTwo[0]) {
      return false;
    }

    int[] leftSubtreeOne = getSmaller(arrayOne);
    int[] leftSubtreeTwo = getSmaller(arrayTwo);
    int[] rightSubtreeOne = getLargerOrEqual(arrayOne);
    int[] rightSubtreeTwo = getLargerOrEqual(arrayTwo);

    return areSameBsts(leftSubtreeOne, leftSubtreeTwo)
        && areSameBsts(rightSubtreeOne, rightSubtreeTwo);
  }

  private static int[] getSmaller(int[] array) {
    List<Integer> smaller = new ArrayList<>();
    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[0]) {
        smaller.add(array[i]);
      }
    }
    return smaller.stream().mapToInt(i -> i).toArray();
  }

  private static int[] getLargerOrEqual(int[] array) {
    List<Integer> largerOrEqual = new ArrayList<>();
    for (int i = 1; i < array.length; i++) {
      if (array[i] >= array[0]) {
        largerOrEqual.add(array[i]);
      }
    }
    return largerOrEqual.stream().mapToInt(i -> i).toArray();
  }

  public static void main(String[] args) {
    int[] arrayOne = {10, 15, 8, 12, 94, 81, 5, 2, 11};
    int[] arrayTwo = {10, 8, 5, 15, 2, 12, 11, 94, 81};
    System.out.println(sameBstsBruteForce(arrayOne, arrayTwo)); // Output: true
  }
}


/*
public class SameBST {

  // Optimized Solution
  public static boolean sameBstsOptimized(int[] arrayOne, int[] arrayTwo) {
    return areSameBsts(arrayOne, arrayTwo, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean areSameBsts(int[] arrayOne, int[] arrayTwo, int rootIdxOne, int rootIdxTwo, int minValue, int maxValue) {
    if (rootIdxOne == -1 || rootIdxTwo == -1) {
      return rootIdxOne == rootIdxTwo;
    }
    if (arrayOne[rootIdxOne] != arrayTwo[rootIdxTwo]) {
      return false;
    }

    int leftRootIdxOne = getIdxOfFirstSmaller(arrayOne, rootIdxOne, minValue);
    int leftRootIdxTwo = getIdxOfFirstSmaller(arrayTwo, rootIdxTwo, minValue);
    int rightRootIdxOne = getIdxOfFirstLargerOrEqual(arrayOne, rootIdxOne, maxValue);
    int rightRootIdxTwo = getIdxOfFirstLargerOrEqual(arrayTwo, rootIdxTwo, maxValue);

    boolean leftAreSame = areSameBsts(arrayOne, arrayTwo, leftRootIdxOne, leftRootIdxTwo, minValue, arrayOne[rootIdxOne]);
    boolean rightAreSame = areSameBsts(arrayOne, arrayTwo, rightRootIdxOne, rightRootIdxTwo, arrayOne[rootIdxOne], maxValue);

    return leftAreSame && rightAreSame;
  }

  private static int getIdxOfFirstSmaller(int[] array, int startIdx, int minValue) {
    for (int i = startIdx + 1; i < array.length; i++) {
      if (array[i] < array[startIdx] && array[i] >= minValue) {
        return i;
      }
    }
    return -1;
  }

  private static int getIdxOfFirstLargerOrEqual(int[] array, int startIdx, int maxValue) {
    for (int i = startIdx + 1; i < array.length; i++) {
      if (array[i] >= array[startIdx] && array[i] < maxValue) {
        return i;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] arrayOne = {10, 15, 8, 12, 94, 81, 5, 2, 11};
    int[] arrayTwo = {10, 8, 5, 15, 2, 12, 11, 94, 81};
    System.out.println(sameBstsOptimized(arrayOne, arrayTwo));  // Output: true
  }
}

*/
