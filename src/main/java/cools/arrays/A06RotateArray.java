package cools.arrays;

/*
 Problem: Rotate Array

 Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.

 Example 1:
 Input: nums = [1,2,3,4,5,6,7], k = 3
 Output: [5,6,7,1,2,3,4]
 Explanation:
 Rotate 1 step to the right: [7,1,2,3,4,5,6]
 Rotate 2 steps to the right: [6,7,1,2,3,4,5]
 Rotate 3 steps to the right: [5,6,7,1,2,3,4]

 Example 2:
 Input: nums = [-1,-100,3,99], k = 2
 Output: [3,99,-1,-100]
 Explanation:
 Rotate 1 step to the right: [99,-1,-100,3]
 Rotate 2 steps to the right: [3,99,-1,-100]

 Constraints:
 - 1 <= nums.length <= 10^5
 - -2^31 <= nums[i] <= 2^31 - 1
 - 0 <= k <= 10^5

 Follow-up:
 - Try to solve it in-place with O(1) extra space.

 Solution Approach:
 1. **Reverse-based Approach**:
    - First, reverse the entire array.
    - Then, reverse the first `k` elements.
    - Finally, reverse the remaining `n - k` elements.
 2. This approach works because rotating right by `k` steps is equivalent to:
    - Moving the last `k` elements to the front.
 3. To ensure the solution works for large `k` values, use `k = k % n` (where `n` is the length of the array).

*/

import java.util.Arrays;

public class A06RotateArray {

  // Function to rotate the array by k steps
  public void rotate(int[] nums, int k) {
    int n = nums.length;
    k = k % n; // Handle cases where k > n

    // Step 1: Reverse the entire array
    reverse(nums, 0, n - 1);

    // Step 2: Reverse the first k elements
    reverse(nums, 0, k - 1);

    // Step 3: Reverse the remaining n - k elements
    reverse(nums, k, n - 1);
  }

  // Helper function to reverse the elements of an array between two indices
  private void reverse(int[] nums, int start, int end) {
    while (start < end) {
      // Swap elements at start and end
      int temp = nums[start];
      nums[start] = nums[end];
      nums[end] = temp;
      start++;
      end--;
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A06RotateArray solution = new A06RotateArray();

    // Example 1
    int[] nums1 = {1, 2, 3, 4, 5, 6, 7};
    solution.rotate(nums1, 3);
    System.out.println("Rotated Array: " + Arrays.toString(nums1)); // Output: [5,6,7,1,2,3,4]

    // Example 2
    int[] nums2 = {-1, -100, 3, 99};
    solution.rotate(nums2, 2);
    System.out.println("Rotated Array: " + Arrays.toString(nums2)); // Output: [3,99,-1,-100]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We perform three passes over the array (each pass is O(n)).

   Space Complexity:
   - O(1), since we are modifying the array in place and using only constant extra space.
  */
}
