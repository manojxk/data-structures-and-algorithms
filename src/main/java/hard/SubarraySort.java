/*
 * Problem Statement:
 * Write a function that takes in an array of at least two integers and returns an array
 * containing the starting and ending indices of the smallest subarray that needs to be sorted
 * in place so that the entire input array becomes sorted in ascending order.
 *
 * If the input array is already sorted, the function should return [-1, -1].
 *
 * Example:
 * Input: array = [1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]
 * Output: [3, 9]
 * Explanation: Sorting the subarray from index 3 to 9 sorts the entire array.
 */

package hard;

import java.util.Arrays;

public class SubarraySort {

  // Optimized Solution
  public static int[] subarraySortOptimized(int[] array) {
    int n = array.length;
    int minOutOfOrder = Integer.MAX_VALUE;
    int maxOutOfOrder = Integer.MIN_VALUE;

    // Identify out-of-order elements
    for (int i = 0; i < n; i++) {
      if (isOutOfOrder(i, array)) {
        minOutOfOrder = Math.min(minOutOfOrder, array[i]);
        maxOutOfOrder = Math.max(maxOutOfOrder, array[i]);
      }
    }

    // If the array is already sorted
    if (minOutOfOrder == Integer.MAX_VALUE) {
      return new int[] {-1, -1};
    }

    // Determine the correct position for minOutOfOrder and maxOutOfOrder
    int left = 0;
    while (minOutOfOrder >= array[left]) {
      left++;
    }

    int right = n - 1;
    while (maxOutOfOrder <= array[right]) {
      right--;
    }

    return new int[] {left, right};
  }

  // Helper function to check if an element is out of order
  private static boolean isOutOfOrder(int i, int[] array) {
    if (i == 0) {
      return array[i] > array[i + 1];
    }
    if (i == array.length - 1) {
      return array[i] < array[i - 1];
    }
    return array[i] > array[i + 1] || array[i] < array[i - 1];
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
    System.out.println(Arrays.toString(subarraySortOptimized(array))); // Output: [3, 9]
  }
}

/*Optimized Solution
Approach:
Identify Out of Order Elements: Traverse the array and find elements that are out of order compared to their neighboring elements.
Determine Boundaries: Once the out-of-order elements are identified, find the correct position in the array by comparing them with the smallest and largest out-of-order elements.
Time Complexity:
O(n): We traverse the array a constant number of times.
Space Complexity:
O(1): We use a constant amount of extra space for variables.*/
