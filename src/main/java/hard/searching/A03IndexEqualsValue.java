package hard.searching;

/*
 * Problem: Index Equals Value
 *
 * You are given a sorted array of distinct integers. Write a function that returns the first index
 * where the value at that index is equal to the index itself. If no such index exists, return -1.
 *
 * Example 1:
 * Input: array = [-5, -3, 0, 3, 4]
 * Output: 3
 * Explanation: At index 3, the value is 3.
 *
 * Example 2:
 * Input: array = [-5, -3, 0, 2, 4]
 * Output: 2
 * Explanation: At index 2, the value is 2.
 *
 * Example 3:
 * Input: array = [-10, -5, 0, 3, 7]
 * Output: -1
 * Explanation: There is no index where index == value.
 */

/*
 * Solution Steps:
 *
 * 1. Use binary search to find the index where index equals value.
 * 2. In each step, check if the mid element equals its index.
 * 3. If the value at mid equals the index, store the index and continue searching in the left half to find the first occurrence.
 * 4. If the value at mid is greater than the index, search in the left half.
 * 5. If the value at mid is less than the index, search in the right half.
 * 6. Continue this process until the correct index is found or the search space is exhausted.
 */

public class A03IndexEqualsValue {

  // Function to find the first index where index equals value
  public static int indexEqualsValue(int[] array) {
    int left = 0;
    int right = array.length - 1;
    int result = -1;

    // Binary search loop
    while (left <= right) {
      int mid = left + (right - left) / 2;

      // If mid equals its index, store the result and search the left half
      if (array[mid] == mid) {
        result = mid; // Potential answer
        right = mid - 1; // Look for earlier occurrence
      }
      // If the value is greater than the index, search the left half
      else if (array[mid] > mid) {
        right = mid - 1;
      }
      // If the value is less than the index, search the right half
      else {
        left = mid + 1;
      }
    }

    return result; // Return the result (either the index or -1)
  }

  public static void main(String[] args) {
    // Test Case 1: First occurrence is at index 3
    int[] array1 = {-5, -3, 0, 3, 4};
    System.out.println("Index where index equals value: " + indexEqualsValue(array1)); // Output: 3

    // Test Case 2: First occurrence is at index 2
    int[] array2 = {-5, -3, 0, 2, 4};
    System.out.println("Index where index equals value: " + indexEqualsValue(array2)); // Output: 2

    // Test Case 3: No index where index equals value
    int[] array3 = {-10, -5, 0, 3, 7};
    System.out.println("Index where index equals value: " + indexEqualsValue(array3)); // Output: -1
  }

  /*
   * Time Complexity:
   * O(log n), where n is the number of elements in the array. We perform binary search, which reduces the search space by half in each step.
   *
   * Space Complexity:
   * O(1), since the search is performed in place with constant extra space.
   */
}
