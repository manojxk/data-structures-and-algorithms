package cools.arrays;

/*
 Problem: Jump Game II

 You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].
 Each element nums[i] represents the maximum length of a forward jump from index i.

 Return the minimum number of jumps to reach nums[n - 1]. The test cases are generated such that you can reach nums[n - 1].

 Constraints:
 - 1 <= nums.length <= 10^4
 - 0 <= nums[i] <= 1000
 - You can always reach the last index.

 Example 1:
 Input: nums = [2,3,1,1,4]
 Output: 2
 Explanation:
 The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.

 Example 2:
 Input: nums = [2,3,0,1,4]
 Output: 2

 Solution Approach:
 1. Use a **greedy approach** to track the minimum jumps needed.
 2. Keep track of the farthest index you can reach during each jump, and count jumps when you move to the farthest point reachable in the current jump.
 3. Traverse the array while maintaining a `currentEnd` variable that represents the farthest point we can reach in the current jump. When we move past this point, increase the jump count.
*/

public class A10JumpGameII {

  // Function to calculate the minimum number of jumps to reach the last index
  public int jump(int[] nums) {
    if (nums.length == 1) return 0; // No jumps needed if we're already at the last index

    int jumps = 0; // Track the number of jumps
    int farthest = 0; // Track the farthest point reachable so far
    int currentEnd = 0; // Track the farthest point reachable in the current jump

    // Traverse the array except for the last element
    for (int i = 0; i < nums.length - 1; i++) {
      farthest = Math.max(farthest, i + nums[i]); // Update the farthest point reachable

      // If we reach the end of the current jump, increase the jump count
      if (i == currentEnd) {
        jumps++;
        currentEnd = farthest; // Update the current end to the farthest point reachable

        // If the current end exceeds or reaches the last index, return the number of jumps
        if (currentEnd >= nums.length - 1) {
          return jumps;
        }
      }
    }

    return jumps;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A10JumpGameII solution = new A10JumpGameII();

    // Example 1
    int[] nums1 = {2, 3, 1, 1, 4};
    System.out.println("Minimum Jumps: " + solution.jump(nums1)); // Output: 2

    // Example 2
    int[] nums2 = {2, 3, 0, 1, 4};
    System.out.println("Minimum Jumps: " + solution.jump(nums2)); // Output: 2
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are using only constant extra space for variables.
  */
}
