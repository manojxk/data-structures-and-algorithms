package cools.dp.misc;

/*
 Problem: Longest Increasing Subsequence

 Given an integer array nums, return the length of the longest strictly increasing subsequence.

 Example 1:
 Input: nums = [10,9,2,5,3,7,101,18]
 Output: 4
 Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.

 Example 2:
 Input: nums = [0,1,0,3,2,3]
 Output: 4

 Example 3:
 Input: nums = [7,7,7,7,7,7,7]
 Output: 1

 Constraints:
 - 1 <= nums.length <= 2500
 - -10^4 <= nums[i] <= 10^4

 Solution Approach:
 1. This is a dynamic programming problem where the goal is to find the length of the longest subsequence of increasing integers.
 2. The idea is to maintain an array `dp` where dp[i] represents the length of the longest increasing subsequence that ends with `nums[i]`.
 3. For each element `nums[i]`, we check all previous elements `nums[j]` where `j < i` and update `dp[i]` if `nums[j] < nums[i]`.
 4. Finally, the result is the maximum value in the `dp` array, representing the length of the longest increasing subsequence.

*/

public class A04LongestIncreasingSubsequence {

  // Function to find the length of the longest increasing subsequence
  public int lengthOfLIS(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    // Array to store the length of the longest subsequence ending at each index
    int[] dp = new int[nums.length];

    // Initialize dp with 1, as each element is a subsequence of length 1
    for (int i = 0; i < nums.length; i++) {
      dp[i] = 1;
    }

    // Iterate over the array to calculate the longest increasing subsequence
    for (int i = 1; i < nums.length; i++) {
      for (int j = 0; j < i; j++) {
        // If nums[i] is greater than nums[j], we can form a longer subsequence
        if (nums[i] > nums[j]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
    }

    // Find the maximum value in dp, which represents the length of the longest subsequence
    int maxLength = 0;
    for (int length : dp) {
      maxLength = Math.max(maxLength, length);
    }

    return maxLength;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04LongestIncreasingSubsequence solution = new A04LongestIncreasingSubsequence();

    // Example 1
    int[] nums1 = {10, 9, 2, 5, 3, 7, 101, 18};
    System.out.println(
        "Length of Longest Increasing Subsequence: " + solution.lengthOfLIS(nums1)); // Output: 4

    // Example 2
    int[] nums2 = {0, 1, 0, 3, 2, 3};
    System.out.println(
        "Length of Longest Increasing Subsequence: " + solution.lengthOfLIS(nums2)); // Output: 4

    // Example 3
    int[] nums3 = {7, 7, 7, 7, 7, 7, 7};
    System.out.println(
        "Length of Longest Increasing Subsequence: " + solution.lengthOfLIS(nums3)); // Output: 1
  }

  /*
   Time Complexity:
   - O(n^2), where n is the length of the array. We have two nested loops: the outer loop iterates over each element, and the inner loop checks all previous elements.

   Space Complexity:
   - O(n), where n is the length of the array. We use an additional dp array of size n to store the length of the longest subsequence for each element.
  */
}
