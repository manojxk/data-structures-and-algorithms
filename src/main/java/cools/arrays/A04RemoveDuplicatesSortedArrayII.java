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

 Solution Approach:
 1. Use the **two-pointer technique**.
 2. One pointer (`i`) traverses the array, and another pointer (`k`) keeps track of the next position to place a valid element.
 3. For each element, if it appears less than or equal to 2 times, place it at the position `k`.
 4. After the loop, the first `k` elements of `nums` will contain the valid elements, and `k` will be the number of valid elements.
*/

import java.util.Arrays;

public class A04RemoveDuplicatesSortedArrayII {

  // Function to remove duplicates where each element appears at most twice
  public int removeDuplicates(int[] nums) {
    if (nums.length <= 2) {
      return nums.length; // If there are 2 or fewer elements, no need to process further
    }

    int k = 2; // Pointer for the next position of valid elements, start from index 2

    // Traverse the array starting from the third element
    for (int i = 2; i < nums.length; i++) {
      // If the current element is different from the element at position k-2, place it at position
      // k
      if (nums[i] != nums[k - 2]) {
        nums[k] = nums[i];
        k++; // Increment k for the next valid element
      }
    }

    // Return the number of elements that can appear at most twice
    return k;
  }

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

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are modifying the array in place and using only constant extra space.
  */
}
