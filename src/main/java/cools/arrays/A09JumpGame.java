package cools.arrays;

/*
 Problem: Jump Game

 You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.

 Return true if you can reach the last index, or false otherwise.

 Constraints:
 - 1 <= nums.length <= 10^4
 - 0 <= nums[i] <= 10^5

 Example 1:
 Input: nums = [2,3,1,1,4]
 Output: true
 Explanation:
 Jump 1 step from index 0 to index 1, then 3 steps to the last index.

 Example 2:
 Input: nums = [3,2,1,0,4]
 Output: false
 Explanation:
 You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.

 Solution Approach:
 1. Use a **greedy approach**:
    - Traverse the array and keep track of the maximum index you can reach.
    - At each step, update the farthest point that can be reached.
    - If at any index, you can no longer move forward (i.e., the maximum reachable index is less than the current index), return false.
    - If you can reach or exceed the last index, return true.
*/

public class A09JumpGame {

  // Function to determine if you can reach the last index
  public boolean canJump(int[] nums) {
    int maxReach = 0; // Variable to track the maximum index that can be reached so far

    // Traverse through all elements in the array
    for (int i = 0; i < nums.length; i++) {
      // If the current index is greater than the maximum reachable index, return false
      if (i > maxReach) {
        return false;
      }

      // Update the maximum reachable index
      maxReach = Math.max(maxReach, i + nums[i]);

      // If we can reach or exceed the last index, return true
      if (maxReach >= nums.length - 1) {
        return true;
      }
    }

    return false; // Return false if we can't reach the last index
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A09JumpGame solution = new A09JumpGame();

    // Example 1
    int[] nums1 = {2, 3, 1, 1, 4};
    System.out.println("Can Jump: " + solution.canJump(nums1)); // Output: true

    // Example 2
    int[] nums2 = {3, 2, 1, 0, 4};
    System.out.println("Can Jump: " + solution.canJump(nums2)); // Output: false
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We pass through the array once.

   Space Complexity:
   - O(1), since we are using only constant extra space (variables to track maxReach).
  */
}
