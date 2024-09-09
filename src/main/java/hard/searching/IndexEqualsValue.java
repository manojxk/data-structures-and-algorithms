package hard.searching;

/**
 * Problem Statement:
 *
 * <p>You are given a sorted array of distinct integers. The task is to find the first index in the
 * array such that the index is equal to the value at that index (i.e., index == array[index]).
 *
 * <p>If such an index exists, return the minimum index where index == array[index]. If no such
 * index exists, return -1.
 *
 * <p>Assumptions: - The integers in the array are distinct.
 *
 * <p>Sample Input: array = [-5, -3, 0, 3, 4, 5, 9]
 *
 * <p>Sample Output: 3 // because 3 == array[3]
 */
public class IndexEqualsValue {

  // Brute Force Approach:
  // Loop through each index and check if the value at that index is equal to the index.
  // Time Complexity: O(n) - where 'n' is the number of elements in the array.
  // Space Complexity: O(1) - constant space.
  public static int indexEqualsValueBruteForce(int[] array) {
    for (int i = 0; i < array.length; i++) {
      if (i == array[i]) {
        return i; // Return the first index where index == array[index]
      }
    }
    return -1; // No index found
  }

  // Optimized Approach using Binary Search:
  // Time Complexity: O(log n) - binary search reduces the search space by half in each step.
  // Space Complexity: O(1) - constant space.
  public static int indexEqualsValueBinarySearch(int[] array) {
    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (array[mid] == mid) {
        right = mid - 1; // Keep searching to the left for the smallest index
      } else if (array[mid] < mid) {
        left = mid + 1; // Search the right half
      } else {
        right = mid - 1; // Search the left half
      }
    }

    // Check if left points to a valid index
    if (left < array.length && array[left] == left) {
      return left;
    }

    return -1; // No index found
  }

  // Main function to test both solutions
  public static void main(String[] args) {
    int[] array = {0, 1, 2, 3, 4, 5, 9};

    // Brute Force Solution
    int resultBruteForce = indexEqualsValueBruteForce(array);
    System.out.println("Brute Force Solution: " + resultBruteForce);

    // Optimized Binary Search Solution
    int resultBinarySearch = indexEqualsValueBinarySearch(array);
    System.out.println("Optimized Binary Search Solution: " + resultBinarySearch);
  }
}
