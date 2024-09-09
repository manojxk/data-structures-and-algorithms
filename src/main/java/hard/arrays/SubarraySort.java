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

package hard.arrays;

import java.util.Arrays;

public class SubarraySort {
  public static int[] subarraySort(int[] array) {
    int[] arrayCopy = array.clone(); // Make a copy of the original array
    Arrays.sort(arrayCopy); // Sort the copied array

    int start = 0; // Initialize the start of the subarray
    while (start < array.length
        && array[start]
            == arrayCopy[start]) { // While the elements at the start are equal in both arrays
      start++; // Move the start to the right
    }

    int end = array.length - 1; // Initialize the end of the subarray
    while (end > start
        && array[end] == arrayCopy[end]) { // While the elements at the end are equal in both arrays
      end--; // Move the end to the left
    }

    return new int[] {start, end}; // Return the start and end of the subarray
  }

  // Function to find the smallest subarray that needs to be sorted
  public static int[] subarraySortOptimized(int[] array) {
    // Initialize the minimum and maximum out-of-place elements
    int minOutOfPlace = Integer.MAX_VALUE;
    int maxOutOfPlace = Integer.MIN_VALUE;

    // Find the minimum and maximum out-of-place elements
    for (int i = 0; i < array.length; i++) {
      if (isOutOfPlace(i, array)) {
        minOutOfPlace = Math.min(minOutOfPlace, array[i]);
        maxOutOfPlace = Math.max(maxOutOfPlace, array[i]);
      }
    }

    // If no out-of-place element was found, the array is already sorted
    if (minOutOfPlace == Integer.MAX_VALUE) return new int[] {-1, -1};

    // Find the correct position of the minimum and maximum out-of-place elements
    int left = 0;
    while (minOutOfPlace >= array[left]) left++;
    int right = array.length - 1;
    while (maxOutOfPlace <= array[right]) right--;

    // Return the start and end of the subarray
    return new int[] {left, right};
  }

  // Helper function to check if an element is out of place
  private static boolean isOutOfPlace(int i, int[] array) {
    if (i == 0) return array[i] > array[i + 1];
    if (i == array.length - 1) return array[i] < array[i - 1];
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
