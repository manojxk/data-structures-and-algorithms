/*
 * Problem Statement:
 *
 * Write a function that takes in an array of integers and returns a boolean
 * representing whether the array is monotonic.
 *
 * An array is said to be monotonic if its elements, from left to right, are
 * entirely non-increasing or entirely non-decreasing.
 *
 * Non-increasing elements aren't necessarily exclusively decreasing; they simply
 * don't increase. Similarly, non-decreasing elements aren't necessarily
 * exclusively increasing; they simply don't decrease.
 *
 * Note that empty arrays and arrays of one element are monotonic.
 *
 * Sample Input:
 * array = [-1, -5, -10, -1100, -1100, -1101, -1102, -9001]
 *
 * Sample Output:
 * true
 */

package medium;

public class MonotonicArray {

  public static boolean isMonotonic(int[] array) {
    if (array.length <= 1) {
      return true; // Arrays with one or zero elements are trivially monotonic
    }

    boolean isNonDecreasing = true;
    boolean isNonIncreasing = true;

    for (int i = 1; i < array.length; i++) {
      if (array[i] > array[i - 1]) {
        isNonIncreasing = false;
      }
      if (array[i] < array[i - 1]) {
        isNonDecreasing = false;
      }
    }

    // The array is monotonic if it's either non-decreasing or non-increasing
    return isNonDecreasing || isNonIncreasing;
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {-1, -5, -10, -1100, -1100, -1101, -1102, -9001};

    // Check if the array is monotonic
    boolean result = isMonotonic(array);

    // Print the result
    System.out.println(result); // Expected Output: true
  }
}
