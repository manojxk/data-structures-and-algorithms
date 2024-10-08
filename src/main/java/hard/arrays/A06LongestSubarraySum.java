package hard.arrays;/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of non-negative integers and a non-negative integer
 * representing a target sum. The function should find the longest subarray where the sum of the values
 * in the subarray equals the target sum. Return an array containing the starting index and ending index
 * of this subarray, both inclusive.
 *
 * If no subarray sums up to the target sum, the function should return an empty array.
 * It is guaranteed that there will be at most one valid answer.
 *
 * Example 1:
 * Input: array = [1, 2, 3, 4, 3, 3, 1, 2, 1, 2], targetSum = 10
 * Output: [4, 8]
 * Explanation: The longest subarray that sums to 10 starts at index 4 and ends at index 8.
 *
 * Example 2:
 * Input: array = [1, 2, 3], targetSum = 6
 * Output: [0, 2]
 * Explanation: The subarray [1, 2, 3] sums to 6 and starts at index 0 and ends at index 2.
 *
 * Example 3:
 * Input: array = [5, 5, 5, 5], targetSum = 10
 * Output: [0, 1]
 * Explanation: The subarray [5, 5] sums to 10 and starts at index 0 and ends at index 1.
 *
 * Example 4:
 * Input: array = [1, 2, 3], targetSum = 10
 * Output: []
 * Explanation: No subarray sums up to the target sum.
 *
 * Constraints:
 * - The array will have at least one element.
 * - All elements in the array are non-negative integers.
 * - The target sum is a non-negative integer.
 */

import java.util.Arrays;

public class A06LongestSubarraySum {

  /*
   * Brute Force Solution
   * Approach:
   * - Use nested loops to check all possible subarrays.
   * - Calculate the sum of each subarray and track the length of the longest subarray that matches the target sum.
   *
   * Time Complexity: O(n^2) where n is the length of the array.
   * Space Complexity: O(1), no extra space is used beyond variables to track indices and lengths.
   */
  public static int[] longestSubarraySumBruteForce(int[] array, int targetSum) {
    int n = array.length;
    int maxLength = 0;
    int startIndex = -1;
    int endIndex = -1;

    for (int i = 0; i < n; i++) {
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += array[j];
        if (sum == targetSum && (j - i + 1) > maxLength) {
          maxLength = j - i + 1;
          startIndex = i;
          endIndex = j;
        }
      }
    }

    return startIndex == -1 ? new int[] {} : new int[] { startIndex, endIndex };
  }

  /*
   * Optimized Solution using Sliding Window
   * Approach:
   * - Use a sliding window approach with two pointers (start and end) and a variable to keep track of the current sum.
   * - Expand the window by moving the end pointer and contract the window by moving the start pointer to maintain or achieve the target sum.
   * - Track the maximum length of subarrays that meet the target sum condition.
   *
   * Time Complexity: O(n), where n is the length of the array.
   * Space Complexity: O(1), no extra space beyond variables to track indices and lengths.
   */
  public static int[] longestSubarraySumSlidingWindow(int[] array, int targetSum) {
    int n = array.length;
    int start = 0;
    int end = 0;
    int currentSum = 0;
    int maxLength = 0;
    int startIndex = -1;
    int endIndex = -1;

    // Sliding window mechanism
    while (end < n) {
      currentSum += array[end];

      // Shrink the window if the current sum exceeds the target
      while (currentSum > targetSum && start <= end) {
        currentSum -= array[start];
        start++;
      }

      // Check if the current window matches the target sum
      if (currentSum == targetSum) {
        if ((end - start + 1) > maxLength) {
          maxLength = end - start + 1;
          startIndex = start;
          endIndex = end;
        }
      }

      end++;
    }

    return startIndex == -1 ? new int[] {} : new int[] { startIndex, endIndex };
  }

  // Main function to test the solutions
  public static void main(String[] args) {
    int[] array = { 1, 2, 3, 4, 3, 3, 1, 2, 1, 2 };
    int targetSum = 10;

    // Brute force solution
    System.out.println("Brute Force Solution: " + Arrays.toString(longestSubarraySumBruteForce(array, targetSum)));
    // Output: [4, 8]

    // Optimized solution using sliding window
    System.out.println("Sliding Window Solution: " + Arrays.toString(longestSubarraySumSlidingWindow(array, targetSum)));
    // Output: [4, 8]
  }
}
