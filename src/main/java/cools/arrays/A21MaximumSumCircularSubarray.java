package cools.arrays;

/*
 Problem: Maximum Sum Circular Subarray

 Given a circular integer array nums of length n, return the maximum possible sum of a non-empty subarray of nums.

 Example 1:
 Input: nums = [1,-2,3,-2]
 Output: 3
 Explanation: Subarray [3] has maximum sum 3.

 Example 2:
 Input: nums = [5,-3,5]
 Output: 10
 Explanation: Subarray [5,5] has maximum sum 5 + 5 = 10.

 Example 3:
 Input: nums = [-3,-2,-3]
 Output: -2
 Explanation: Subarray [-2] has maximum sum -2.

 Constraints:
 - n == nums.length
 - 1 <= n <= 3 * 10^4
 - -3 * 10^4 <= nums[i] <= 3 * 10^4

 Solution Approach:
 1. There are two cases:
    - Case 1: The maximum sum subarray lies in the middle of the array (i.e., it does not wrap around).
    - Case 2: The maximum sum subarray wraps around (i.e., it includes the end and the beginning of the array).
 2. For **Case 1**, use **Kadane’s Algorithm** to find the maximum subarray sum in the normal (non-circular) array.
 3. For **Case 2**, we need to find the sum of the entire array minus the minimum subarray sum (which excludes the worst subarray).
    - The wrapped maximum sum = Total sum of the array - minimum subarray sum.
 4. If all elements are negative, return the result from **Case 1** (i.e., the largest single element).

*/

public class A21MaximumSumCircularSubarray {

  // Function to find the maximum sum of a circular subarray
  public int maxSubarraySumCircular(int[] nums) {
    int totalSum = 0; // Sum of the entire array
    int maxSum = nums[0]; // Maximum subarray sum (non-circular)
    int minSum = nums[0]; // Minimum subarray sum
    int currentMax = nums[0]; // Current max subarray sum for Kadane's algorithm
    int currentMin = nums[0]; // Current min subarray sum for Kadane's algorithm

    // Traverse through the array to calculate total sum, max sum, and min sum
    for (int i = 1; i < nums.length; i++) {
      totalSum += nums[i];
      // Kadane's algorithm for maximum subarray sum
      currentMax = Math.max(nums[i], currentMax + nums[i]);
      maxSum = Math.max(maxSum, currentMax);

      // Kadane's algorithm for minimum subarray sum
      currentMin = Math.min(nums[i], currentMin + nums[i]);
      minSum = Math.min(minSum, currentMin);
    }

    // Edge case: If all elements are negative, return the maximum sum (non-circular)
    if (totalSum == minSum) {
      return maxSum;
    }

    // Return the maximum of maxSum (non-circular case) or totalSum - minSum (circular case)
    return Math.max(maxSum, totalSum - minSum);
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A21MaximumSumCircularSubarray solution = new A21MaximumSumCircularSubarray();

    // Example 1
    int[] nums1 = {1, -2, 3, -2};
    System.out.println(
        "Maximum Circular Subarray Sum: " + solution.maxSubarraySumCircular(nums1)); // Output: 3

    // Example 2
    int[] nums2 = {5, -3, 5};
    System.out.println(
        "Maximum Circular Subarray Sum: " + solution.maxSubarraySumCircular(nums2)); // Output: 10

    // Example 3
    int[] nums3 = {-3, -2, -3};
    System.out.println(
        "Maximum Circular Subarray Sum: " + solution.maxSubarraySumCircular(nums3)); // Output: -2
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once to calculate the total sum, max sum, and min sum.

   Space Complexity:
   - O(1), since we only use a few variables to store sums and do not use any extra space proportional to the input size.
  */
}
