/*
 * Problem Statement:
 * Given a list of integers 'nums', write a function that determines whether
 * there exists a zero-sum subarray within 'nums'.
 *
 * A zero-sum subarray is any contiguous section of the array where the sum
 * of all the elements is zero.
 *
 * Note:
 * - A subarray can be as small as one element and as large as the entire array.
 * - The function should return a boolean value (true or false).
 *
 * Example:
 * Input: nums = [-5, -5, 2, 3, -2]
 * Output: true
 * // The subarray [-5, 2, 3] has a sum of 0.
 */

/*Brute Force Solution
Approach:
The brute force approach checks every possible subarray within the given array and calculates the sum. If the sum equals zero, the function returns true. Otherwise, after all checks, it returns false.

Time Complexity:
O(n^3): We iterate through every possible subarray (O(n^2)) and calculate the sum for each subarray (O(n)), leading to a cubic time complexity.
Space Complexity:
O(1): No extra space is used other than a few variables.*/

/*Optimized Solution: Using HashMap (Prefix Sum Technique)
Approach:
Prefix Sum Technique: As we iterate through the array, we maintain a running sum (prefix sum) of the elements.
HashMap Usage: We store each prefix sum in a HashMap. If the prefix sum has been seen before, it indicates that the subarray between the previous occurrence and the current index sums to zero.
Time Complexity:
O(n): We traverse the array once, and each operation (insertion, lookup) in the HashMap is O(1) on average.
Space Complexity:
O(n): In the worst case, we store each prefix sum in the HashMap, leading to linear space usage.*/

package medium.arrays;

import java.util.HashSet;

public class ZeroSumSubarray {

  // Brute Force Solution
  public static boolean hasZeroSumSubarrayBruteForce(int[] nums) {
    int n = nums.length;

    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        int sum = 0;
        for (int k = i; k <= j; k++) {
          sum += nums[k];
        }
        if (sum == 0) {
          return true;
        }
      }
    }
    return false;
  }

  // Optimized Solution using HashMap and Prefix Sum
  public static boolean hasZeroSumSubarray(int[] nums) {
    HashSet<Integer> prefixSums = new HashSet<>();
    int sum = 0;

    for (int num : nums) {
      sum += num;
      if (sum == 0 || prefixSums.contains(sum)) {
        return true;
      }
      prefixSums.add(sum);
    }

    return false;
  }

  public static void main(String[] args) {
    int[] nums = {-5, -5, 2, 3, -2};
    System.out.println(hasZeroSumSubarrayBruteForce(nums)); // Output: true
  }
}
