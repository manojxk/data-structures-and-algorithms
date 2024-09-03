/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of integers and returns the maximum sum
 * that can be obtained by summing up all of the integers in a non-empty subarray of the input array.
 * A subarray must only contain adjacent numbers (numbers next to each other in the input array).
 *
 * Example:
 *
 * For the input array:
 * array = [3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4]
 *
 * The maximum sum of a contiguous subarray is 19, which corresponds to the subarray
 * [1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1].
 */
/*Brute Force Solution
Approach:
The brute force approach involves checking all possible subarrays and computing their sums to find the maximum sum.
This approach is not efficient for large arrays as it has a time complexity of


Time Complexity:
O(n^3): Due to the nested loops required to generate and sum all possible subarrays.
Space Complexity:
O(1): No extra space is required other than variables for summing and tracking the maximum sum.*/
package medium.famousalgorithms;

public class KadanesAlgorithm {

  // Brute Force Solution: Check all subarrays
  public static int maxSubarraySumBruteForce(int[] array) {
    int maxSum = Integer.MIN_VALUE;
    int n = array.length;

    for (int start = 0; start < n; start++) {
      for (int end = start; end < n; end++) {
        int currentSum = 0;
        for (int k = start; k <= end; k++) {
          currentSum += array[k];
        }
        maxSum = Math.max(maxSum, currentSum);
      }
    }

    return maxSum;
  }

  // Optimized Solution: Kadane's Algorithm
  public static int maxSubarraySumKadane(int[] array) {
    int maxSum = Integer.MIN_VALUE;
    int currentSum = 0;

    for (int num : array) {
      currentSum = Math.max(num, currentSum + num);
      maxSum = Math.max(maxSum, currentSum);
    }

    return maxSum;
  }

  public static void main(String[] args) {
    int[] array = {3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4};
    System.out.println(maxSubarraySumBruteForce(array)); // Output: 19
  }
}
/*
Optimized Solution: Kadane’s Algorithm
Approach:
Kadane's Algorithm provides an efficient way to find the maximum sum of a contiguous subarray. The algorithm maintains a running sum of the current subarray and updates the maximum sum found so far.

Time Complexity:
O(n): Where n is the length of the array. Each element is processed once.
Space Complexity:
O(1): Only a few extra variables are used for tracking the maximum sum and the current subarray sum.*/
