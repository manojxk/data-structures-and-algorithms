package hard.arrays;

/*
 Problem: Four Number Sum

 Given an array of integers nums and a target value, return all unique quadruplets [a, b, c, d] such that:
 - a + b + c + d = target
 - The quadruplets must not contain duplicate sets of numbers.

 Example 1:
 Input: nums = [1,0,-1,0,-2,2], target = 0
 Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]

 Example 2:
 Input: nums = [2,2,2,2,2], target = 8
 Output: [[2,2,2,2]]

 Constraints:
 - 1 <= nums.length <= 200
 - -10^9 <= nums[i] <= 10^9
 - -10^9 <= target <= 10^9

 Solution Approach:
 1. Sort the array to avoid duplicates easily.
 2. Use two nested loops to fix the first two numbers and then use a two-pointer approach to find the remaining two numbers.
 3. Skip over duplicate values for both the first and second numbers as well as during the two-pointer traversal.

*/

import java.util.*;

public class A01FourNumberSum {

  // Function to find all unique quadruplets that sum to the target
  public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();

    if (nums == null || nums.length < 4) {
      return result;
    }

    // Step 1: Sort the array
    Arrays.sort(nums);

    // Step 2: Use two nested loops for the first two numbers
    for (int i = 0; i < nums.length - 3; i++) {
      // Skip duplicate numbers for the first number
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }

      for (int j = i + 1; j < nums.length - 2; j++) {
        // Skip duplicate numbers for the second number
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;
        }

        // Step 3: Use two-pointer approach for the remaining two numbers
        int left = j + 1;
        int right = nums.length - 1;

        while (left < right) {
          int sum = nums[i] + nums[j] + nums[left] + nums[right];

          if (sum == target) {
            // Add the quadruplet to the result list
            result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

            // Skip duplicates for the third number
            while (left < right && nums[left] == nums[left + 1]) {
              left++;
            }

            // Skip duplicates for the fourth number
            while (left < right && nums[right] == nums[right - 1]) {
              right--;
            }

            // Move both pointers inward
            left++;
            right--;
          } else if (sum < target) {
            left++; // If the sum is too small, move the left pointer to the right
          } else {
            right--; // If the sum is too large, move the right pointer to the left
          }
        }
      }
    }

    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01FourNumberSum solution = new A01FourNumberSum();

    // Example 1: nums = [1,0,-1,0,-2,2], target = 0
    int[] nums1 = {1, 0, -1, 0, -2, 2};
    int target1 = 0;
    List<List<Integer>> result1 = solution.fourSum(nums1, target1);
    System.out.println("Unique quadruplets for target 0:");
    for (List<Integer> quadruplet : result1) {
      System.out.println(quadruplet); // Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
    }

    // Example 2: nums = [2,2,2,2,2], target = 8
    int[] nums2 = {2, 2, 2, 2, 2};
    int target2 = 8;
    List<List<Integer>> result2 = solution.fourSum(nums2, target2);
    System.out.println("Unique quadruplets for target 8:");
    for (List<Integer> quadruplet : result2) {
      System.out.println(quadruplet); // Output: [[2, 2, 2, 2]]
    }
  }

  /*
   Time Complexity:
   - O(n^3), where n is the number of elements in the array. We use two nested loops and a two-pointer technique inside those loops.

   Space Complexity:
   - O(log n) or O(n) depending on the sorting algorithm, as we use space for sorting the array and storing results.
  */
}
