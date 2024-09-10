package cools.arrays;

/*
 Problem: Maximum Subarray

 Given an integer array nums, find the subarray with the largest sum, and return its sum.

 Example 1:
 Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 Output: 6
 Explanation: The subarray [4,-1,2,1] has the largest sum 6.

 Example 2:
 Input: nums = [1]
 Output: 1
 Explanation: The subarray [1] has the largest sum 1.

 Example 3:
 Input: nums = [5,4,-1,7,8]
 Output: 23
 Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.

 Constraints:
 - 1 <= nums.length <= 10^5
 - -10^4 <= nums[i] <= 10^4

 Follow-up:
 - Solve using an O(n) solution with Kadane's Algorithm.
 - You can also try the divide and conquer approach.

 Solution Approach:
 1. **Kadane's Algorithm**:
    - The idea is to iterate through the array and at each step, calculate the maximum sum that ends at the current index.
    - Maintain a global variable to keep track of the maximum sum encountered so far.
    - For each element at index `i`, decide whether to add it to the previous sum or start a new subarray from `i`.
*/

public class A20MaximumSubarray {

  // Function to find the maximum sum of a subarray
  public int maxSubArray(int[] nums) {
    // Initialize the current sum as the first element and global maximum as the first element
    int currentSum = nums[0];
    int maxSum = nums[0];

    // Iterate through the array starting from the second element
    for (int i = 1; i < nums.length; i++) {
      // Calculate the maximum subarray sum ending at the current index
      currentSum = Math.max(nums[i], currentSum + nums[i]);

      // Update the global maximum sum if the current sum is greater
      maxSum = Math.max(maxSum, currentSum);
    }

    return maxSum;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A20MaximumSubarray solution = new A20MaximumSubarray();

    // Example 1
    int[] nums1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
    System.out.println("Maximum Subarray Sum: " + solution.maxSubArray(nums1)); // Output: 6

    // Example 2
    int[] nums2 = {1};
    System.out.println("Maximum Subarray Sum: " + solution.maxSubArray(nums2)); // Output: 1

    // Example 3
    int[] nums3 = {5, 4, -1, 7, 8};
    System.out.println("Maximum Subarray Sum: " + solution.maxSubArray(nums3)); // Output: 23
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we only use a few variables to store the current and maximum sums.
  */
}
