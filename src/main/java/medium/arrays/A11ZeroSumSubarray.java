package medium.arrays;

/*
 Problem: Zero Sum Subarray

 You are given an array of integers, and your task is to determine if there exists a subarray (of size at least one) whose elements sum up to zero.

 Example:

 Input: [3, 4, -7, 1, 2, -6, 3]
 Output: true

 Explanation:
 - The subarray [3, 4, -7] sums to zero.
*/

/*
 Solution Steps:

 1. Use a HashSet to store the cumulative sum of elements from the beginning of the array.
 2. Traverse the array and keep a running sum of the elements.
 3. At each element, check if the current cumulative sum is 0 or if it has been seen before in the HashSet:
    - If it is, a subarray with zero sum exists, and return true.
 4. If no such subarray is found, return false at the end.
*/

import java.util.HashSet;
import java.util.Set;

public class A11ZeroSumSubarray {
  public static boolean hasZeroSumSubarrayBruteForce(int[] nums) {
    int n = nums.length;

    for (int i = 0; i < n; i++) {
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += nums[j];
        if (sum == 0) return true;
      }
    }
    return false;
  }

  // Function to check if there exists a subarray with a sum of zero
  public static boolean hasZeroSumSubarray(int[] array) {
    // Step 1: Initialize a HashSet to store cumulative sums
    Set<Integer> cumulativeSums = new HashSet<>();
    int currentSum = 0;

    // Step 2: Traverse through the array
    for (int num : array) {
      currentSum += num; // Calculate the cumulative sum

      // Step 3: Check if the current sum is zero or has been seen before
      if (currentSum == 0 || cumulativeSums.contains(currentSum)) {
        return true; // Zero-sum subarray found
      }

      // Add the current cumulative sum to the set
      cumulativeSums.add(currentSum);
    }

    return false; // No zero-sum subarray found
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array = {3, 4, -7, 1, 2, -6, 3};
    System.out.println(
        "Does the array contain a zero-sum subarray Brute Force ? "
            + hasZeroSumSubarrayBruteForce(array));
    System.out.println(
        "Does the array contain a zero-sum subarray? " + hasZeroSumSubarray(array)); // Output: true
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We traverse the array once, and each insertion/check operation in the HashSet takes O(1).

   Space Complexity:
   - O(n), where n is the number of elements in the array. This is the space used by the HashSet to store cumulative sums.
  */
}
