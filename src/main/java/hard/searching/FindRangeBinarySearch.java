package hard.searching;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes in a sorted array of integers as well as a target integer. The
 * function should return the range of indices at which the target number appears in the form of an
 * array. The first element of the result array should represent the first index of the target, and
 * the second element should represent the last index of the target.
 *
 * <p>If the target is not found, return [-1, -1].
 *
 * <p>Sample Input: array = [0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73] target = 45
 *
 * <p>Sample Output: [4, 9]
 */
public class FindRangeBinarySearch {

  // Main function that returns the range of indices where the target appears
  public static int[] searchRange(int[] array, int target) {
    int[] result = {-1, -1}; // Default result if the target is not found

    // Find the first occurrence of the target
    result[0] = findFirstOccurrence(array, target);

    // If the first occurrence is found, find the last occurrence
    if (result[0] != -1) {
      result[1] = findLastOccurrence(array, target);
    }

    return result;
  }

  // Function to find the first occurrence of the target using binary search
  private static int findFirstOccurrence(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;
    int firstOccurrence = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2; // Prevent potential overflow
      if (array[mid] == target) {
        firstOccurrence = mid; // Mark the first occurrence
        right = mid - 1; // Continue searching in the left half
      } else if (array[mid] < target) {
        left = mid + 1; // Search in the right half
      } else {
        right = mid - 1; // Search in the left half
      }
    }

    return firstOccurrence;
  }

  // Function to find the last occurrence of the target using binary search
  private static int findLastOccurrence(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;
    int lastOccurrence = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (array[mid] == target) {
        lastOccurrence = mid; // Mark the last occurrence
        left = mid + 1; // Continue searching in the right half
      } else if (array[mid] < target) {
        left = mid + 1; // Search in the right half
      } else {
        right = mid - 1; // Search in the left half
      }
    }

    return lastOccurrence;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int[] array = {0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73};
    int target = 45;

    int[] result = searchRange(array, target);
    System.out.println(
        "First and Last Indices: ["
            + result[0]
            + ", "
            + result[1]
            + "]"); // Expected Output: [4, 9]
  }
}
