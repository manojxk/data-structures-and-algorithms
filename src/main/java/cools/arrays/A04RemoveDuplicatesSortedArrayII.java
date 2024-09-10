package cools.arrays;

/*
 Problem: Remove Duplicates from Sorted Array II

 Given an integer array nums sorted in non-decreasing order, remove some duplicates in-place such that each unique element appears at most twice.
 The relative order of the elements should be kept the same.

 Return k after placing the final result in the first k slots of nums.

 Constraints:
 - You need to modify the array in place and return the number of unique elements (k) such that each unique element can appear at most twice.

 Example 1:
 Input: nums = [1,1,1,2,2,3]
 Output: 5, nums = [1,1,2,2,3,_]
 Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2, and 3.

 Example 2:
 Input: nums = [0,0,1,1,1,1,2,3,3]
 Output: 7, nums = [0,0,1,1,2,3,3,_,_]
 Explanation: Your function should return k = 7, with the first seven elements of nums being 0, 0, 1, 1, 2, 3, and 3.
*/

import java.util.Arrays;

public class A04RemoveDuplicatesSortedArrayII {

  // Function to remove duplicates where each element appears at most twice

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04RemoveDuplicatesSortedArrayII solution = new A04RemoveDuplicatesSortedArrayII();

    // Example 1
    int[] nums1 = {1, 1, 1, 2, 2, 3};
    int k1 = solution.removeDuplicates(nums1);
    System.out.println(
        "Result: "
            + k1
            + ", Modified Array: "
            + Arrays.toString(nums1)); // Output: 5, [1, 1, 2, 2, 3, _]

    // Example 2
    int[] nums2 = {0, 0, 1, 1, 1, 1, 2, 3, 3};
    int k2 = solution.removeDuplicates(nums2);
    System.out.println(
        "Result: "
            + k2
            + ", Modified Array: "
            + Arrays.toString(nums2)); // Output: 7, [0, 0, 1, 1, 2, 3, 3, _, _]
  }

  public int removeDuplicates(int[] nums) {
    int j = 1, count = 1;

    for (int i = 1; i < nums.length; i++) {
      if (nums[i] == nums[i - 1]) {
        count++;
      } else {
        count = 1;
      }

      if (count <= 2) {
        nums[j] = nums[i];
        j++;
      }
    }

    return j;
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are modifying the array in place and using only constant extra space.
  */
}
