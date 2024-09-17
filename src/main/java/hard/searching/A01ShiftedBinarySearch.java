/*
 * Problem: Shifted Binary Search
 *
 * You are given a sorted array that has been rotated at some unknown pivot.
 * (e.g., [3, 4, 5, 1, 2] is a rotated version of the sorted array [1, 2, 3, 4, 5])
 *
 * Write a function that takes in a target integer and returns the index of the target in the array.
 * If the target does not exist, return -1.
 *
 * You can assume that there are no duplicate values in the array.
 *
 * Example 1:
 * Input: array = [4, 5, 6, 7, 0, 1, 2], target = 0
 * Output: 4
 *
 * Example 2:
 * Input: array = [4, 5, 6, 7, 0, 1, 2], target = 3
 * Output: -1
 *
 * Example 3:
 * Input: array = [3, 1], target = 1
 * Output: 1
 */

/*
 * Solution Steps:
 *
 * 1. Use a modified binary search to find the target in the rotated array.
 * 2. In each step, check if the left half or right half is normally ordered.
 * 3. If the left half is sorted, check if the target is within the left range.
 *    - If it is, continue searching in the left half.
 *    - If it is not, continue searching in the right half.
 * 4. If the right half is sorted, check if the target is within the right range.
 *    - If it is, continue searching in the right half.
 *    - If it is not, continue searching in the left half.
 * 5. Repeat this process until the target is found or the search space is exhausted.
 */

public class A01ShiftedBinarySearch {

  // Function to perform a binary search on a shifted/rotated sorted array
  public static int shiftedBinarySearch(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;

    // Perform binary search
    while (left <= right) {
      int mid = left + (right - left) / 2;  // Calculate mid-point to avoid overflow

      // If the target is found, return its index
      if (array[mid] == target) {
        return mid;
      }

      // Check if the left half is sorted
      if (array[left] <= array[mid]) {
        // Target lies within the sorted left half
        if (array[left] <= target && target < array[mid]) {
          right = mid - 1;  // Search in the left half
        } else {
          left = mid + 1;   // Search in the right half
        }
      }
      // Else, the right half is sorted
      else {
        // Target lies within the sorted right half
        if (array[mid] < target && target <= array[right]) {
          left = mid + 1;   // Search in the right half
        } else {
          right = mid - 1;  // Search in the left half
        }
      }
    }

    // If target was not found, return -1
    return -1;
  }

  public static void main(String[] args) {
    // Test Case 1
    int[] array1 = {4, 5, 6, 7, 0, 1, 2};
    int target1 = 0;
    System.out.println("Index of target 0: " + shiftedBinarySearch(array1, target1));  // Output: 4

    // Test Case 2
    int[] array2 = {4, 5, 6, 7, 0, 1, 2};
    int target2 = 3;
    System.out.println("Index of target 3: " + shiftedBinarySearch(array2, target2));  // Output: -1

    // Test Case 3
    int[] array3 = {3, 1};
    int target3 = 1;
    System.out.println("Index of target 1: " + shiftedBinarySearch(array3, target3));  // Output: 1
  }

  /*
   * Time Complexity:
   * O(log n), where n is the number of elements in the array. Each step of the binary search reduces the search space by half.
   *
   * Space Complexity:
   * O(1), since the search is done in place with constant extra space.
   */
}
