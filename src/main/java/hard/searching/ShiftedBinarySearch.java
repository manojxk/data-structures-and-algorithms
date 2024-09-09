package hard.searching;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes in a sorted array of distinct integers that has been shifted
 * (rotated) by some amount. The function should return the index of the target if found, otherwise
 * return -1. The function should implement a variation of Binary Search.
 *
 * <p>Sample Input: array = [45, 61, 71, 72, 73, 0, 1, 21, 33, 37] target = 33
 *
 * <p>Sample Output: 8
 */
public class ShiftedBinarySearch {

  // Optimized Binary Search for Rotated Sorted Array
  // Time Complexity: O(log n) - Binary search reduces the problem size by half in each iteration.
  // Space Complexity: O(1) - Constant space used.
  public static int shiftedBinarySearch(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
      int mid = (left + right) / 2;

      // Check if the middle element is the target
      if (array[mid] == target) {
        return mid;
      }

      // Determine which half of the array is sorted
      if (array[left] <= array[mid]) {
        // Left half is sorted
        if (target >= array[left] && target < array[mid]) {
          // Target is in the left half
          right = mid - 1;
        } else {
          // Target is in the right half
          left = mid + 1;
        }
      } else {
        // Right half is sorted
        if (target > array[mid] && target <= array[right]) {
          // Target is in the right half
          left = mid + 1;
        } else {
          // Target is in the left half
          right = mid - 1;
        }
      }
    }

    // If the target is not found
    return -1;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int[] array = {45, 61, 71, 72, 73, 0, 1, 21, 33, 37};
    int target = 33;

    int result = shiftedBinarySearch(array, target);
    System.out.println("Target found at index: " + result); // Expected output: 8
  }
}
