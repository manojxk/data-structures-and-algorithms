package cools.arrays;

/*
 Problem: Product of Array Except Self

 Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
 You must write an algorithm that runs in O(n) time and without using the division operation.

 Constraints:
 - 2 <= nums.length <= 10^5
 - -30 <= nums[i] <= 30
 - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.

 Follow-up: Can you solve the problem in O(1) extra space complexity? (The output array does not count as extra space.)

 Example 1:
 Input: nums = [1,2,3,4]
 Output: [24, 12, 8, 6]

 Example 2:
 Input: nums = [-1, 1, 0, -3, 3]
 Output: [0, 0, 9, 0, 0]

 Solution Approach:
 1. Use two passes: one for the product of elements before each index, and one for the product of elements after each index.
 2. First, create an output array where each element at index i holds the product of all elements to the left of i.
 3. In a second pass, multiply each element in the output array by the product of all elements to the right of i.
*/

import java.util.Arrays;

public class A12ProductOfArrayExceptSelf {

  // Function to return an array where each element is the product of all other elements except
  // itself
  public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];

    // Step 1: Calculate the product of all elements to the left of each index
    result[0] = 1; // No elements to the left of the first element
    for (int i = 1; i < n; i++) {
      result[i] = result[i - 1] * nums[i - 1];
    }

    // Step 2: Calculate the product of all elements to the right of each index and multiply it with
    // the left product
    int rightProduct = 1; // No elements to the right of the last element
    for (int i = n - 1; i >= 0; i--) {
      result[i] *= rightProduct; // Multiply with the right product
      rightProduct *= nums[i]; // Update the right product
    }

    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A12ProductOfArrayExceptSelf solution = new A12ProductOfArrayExceptSelf();

    // Example 1
    int[] nums1 = {1, 2, 3, 4};
    int[] result1 = solution.productExceptSelf(nums1);
    System.out.println("Output: " + Arrays.toString(result1)); // Output: [24, 12, 8, 6]

    // Example 2
    int[] nums2 = {-1, 1, 0, -3, 3};
    int[] result2 = solution.productExceptSelf(nums2);
    System.out.println("Output: " + Arrays.toString(result2)); // Output: [0, 0, 9, 0, 0]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array twice, once from left to right and once from right to left.

   Space Complexity:
   - O(1) extra space, as we are modifying the result array in place and using constant extra space. The output array does not count toward space complexity.
  */
}
