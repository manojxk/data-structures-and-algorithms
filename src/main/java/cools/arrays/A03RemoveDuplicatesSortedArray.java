package cools.arrays;

import java.util.Arrays;

/*
 Problem: Remove Duplicates from Sorted Array

 Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once.
 The relative order of the elements should be kept the same.

 Constraints:
 - You need to modify the array in place and return the number of unique elements (k).
 - It doesn't matter what values are left in the array after the first k elements.

 Example 1:
 Input: nums = [1,1,2]
 Output: 2, nums = [1,2,_]
 Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2.

 Example 2:
 Input: nums = [0,0,1,1,1,2,2,3,3,4]
 Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
 Explanation: Your function should return k = 5, with the first five elements of nums being 0, 1, 2, 3, and 4.

 Solution Approach:
 1. Use the **two-pointer technique**:
    - One pointer (`i`) keeps track of the current element.
    - Another pointer (`k`) keeps track of the position where the next unique element should be placed.
 2. Traverse the array and check if the current element is different from the previous one. If it is, place it at the position `k` and increment `k`.
 3. After the loop, the first `k` elements will be the unique elements, and the array is modified in place.
*/

public class A03RemoveDuplicatesSortedArray {

  // Function to remove duplicates from sorted array
  public int removeDuplicates(int[] nums) {
    // If the array is empty, return 0
    if (nums.length == 0) {
      return 0;
    }

    int k = 1; // Pointer for the next position of unique elements

    // Traverse the array starting from the second element
    for (int i = 1; i < nums.length; i++) {
      // If the current element is different from the previous one, place it at position k
      if (nums[i] != nums[i - 1]) {
        nums[k] = nums[i];
        k++; // Increment k for the next unique element
      }
    }

    // Return the number of unique elements
    return k;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03RemoveDuplicatesSortedArray solution = new A03RemoveDuplicatesSortedArray();

    // Example 1
    int[] nums1 = {1, 1, 2};
    int k1 = solution.removeDuplicates(nums1);
    System.out.println(
        "Result: " + k1 + ", Modified Array: " + Arrays.toString(nums1)); // Output: 2, [1, 2, _]

    // Example 2
    int[] nums2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
    int k2 = solution.removeDuplicates(nums2);
    System.out.println(
        "Result: "
            + k2
            + ", Modified Array: "
            + Arrays.toString(nums2)); // Output: 5, [0, 1, 2, 3, 4, _, _, _, _, _]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are modifying the array in place and using only constant extra space.
  */
}
