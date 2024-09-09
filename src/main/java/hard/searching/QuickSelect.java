package hard.searching;

/**
 * Problem Statement:
 *
 * <p>You are given an array of distinct integers and an integer k. The task is to return the kth
 * smallest integer in the array. The solution should achieve this in linear time on average.
 *
 * <p>Assumptions: - The integers in the array are distinct. - The value of k is valid, i.e., 1 <= k
 * <= length of the array.
 *
 * <p>Sample Input: array = [8, 5, 2, 9, 7, 6, 3] k = 3
 *
 * <p>Sample Output: 5
 *
 * <p>Explanation: The 3rd smallest integer in the array is 5.
 */
import java.util.*;

public class QuickSelect {

  // Brute Force Approach:
  // Sort the array and then return the element at the (k - 1)th index.
  // Time Complexity: O(n log n) - due to sorting.
  // Space Complexity: O(1) - if sorting is done in-place.
  public static int kthSmallestBruteForce(int[] array, int k) {
    Arrays.sort(array); // Sort the array in O(n log n)
    return array[k - 1]; // Return the kth smallest element
  }

  // Optimized Approach using Quickselect:
  // Time Complexity: O(n) on average, O(n^2) in the worst case (if the pivot is consistently the
  // largest or smallest element).
  // Space Complexity: O(1) - in-place quickselect.
  public static int kthSmallestQuickselect(int[] array, int k) {
    return quickselect(array, 0, array.length - 1, k - 1);
  }

  private static int quickselect(int[] array, int left, int right, int k) {
    while (true) {
      if (left == right) {
        return array[left]; // Base case: one element left
      }
      int pivotIndex = partition(array, left, right);

      if (k == pivotIndex) {
        return array[k];
      } else if (k < pivotIndex) {
        right = pivotIndex - 1; // Search in the left part
      } else {
        left = pivotIndex + 1; // Search in the right part
      }
    }
  }

  // Helper function to partition the array and return the pivot index
  private static int partition(int[] array, int left, int right) {
    int pivot = array[right];
    int i = left;

    for (int j = left; j < right; j++) {
      if (array[j] < pivot) {
        swap(array, i, j);
        i++;
      }
    }
    swap(array, i, right); // Place the pivot in its correct position
    return i;
  }

  // Helper function to swap two elements in the array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  // Main function to test both solutions
  public static void main(String[] args) {
    int[] array = {8, 5, 2, 9, 7, 6, 3};
    int k = 3;

    // Brute Force Solution
    int resultBruteForce = kthSmallestBruteForce(array, k);
    System.out.println("Brute Force Solution: " + resultBruteForce);

    // Optimized Quickselect Solution
    int resultQuickselect = kthSmallestQuickselect(array, k);
    System.out.println("Optimized Quickselect Solution: " + resultQuickselect);
  }
}
