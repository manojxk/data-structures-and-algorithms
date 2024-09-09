package cools.arrays;

import java.util.Arrays;

/*
 Problem: Merge Sorted Array

 You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n,
 representing the number of elements in nums1 and nums2 respectively.

 Merge nums1 and nums2 into a single array sorted in non-decreasing order. The final sorted array should not be returned by the function,
 but instead be stored inside the array nums1. To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged,
 and the last n elements are set to 0 and should be ignored. nums2 has a length of n.

 Example 1:
 Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 Output: [1,2,2,3,5,6]

 Example 2:
 Input: nums1 = [1], m = 1, nums2 = [], n = 0
 Output: [1]

 Example 3:
 Input: nums1 = [0], m = 0, nums2 = [1], n = 1
 Output: [1]

 Solution Approach:
 1. Use a **three-pointer** technique to merge the two arrays from the back.
 2. Start from the end of both `nums1` and `nums2` and place the larger element at the correct position in `nums1` starting from the back.
 3. This avoids overwriting any elements in `nums1` that haven't been processed yet.
*/

public class A01MergeSortedArray {

  // Function to merge two sorted arrays nums1 and nums2
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int p1 = m - 1; // Pointer for nums1 (starting from the last valid element)
    int p2 = n - 1; // Pointer for nums2 (starting from the last element)
    int p = m + n - 1; // Pointer for the last position in nums1

    // Traverse the arrays from the back and place the larger element at the current position in
    // nums1
    while (p1 >= 0 && p2 >= 0) {
      if (nums1[p1] > nums2[p2]) {
        nums1[p] = nums1[p1];
        p1--;
      } else {
        nums1[p] = nums2[p2];
        p2--;
      }
      p--;
    }

    // If there are remaining elements in nums2, copy them over to nums1
    while (p2 >= 0) {
      nums1[p] = nums2[p2];
      p2--;
      p--;
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01MergeSortedArray solution = new A01MergeSortedArray();

    // Example 1
    int[] nums1 = {1, 2, 3, 0, 0, 0};
    int[] nums2 = {2, 5, 6};
    solution.merge(nums1, 3, nums2, 3);
    System.out.println("Merged array: " + Arrays.toString(nums1)); // Output: [1, 2, 2, 3, 5, 6]

    // Example 2
    int[] nums1_2 = {1};
    int[] nums2_2 = {};
    solution.merge(nums1_2, 1, nums2_2, 0);
    System.out.println("Merged array: " + Arrays.toString(nums1_2)); // Output: [1]

    // Example 3
    int[] nums1_3 = {0};
    int[] nums2_3 = {1};
    solution.merge(nums1_3, 0, nums2_3, 1);
    System.out.println("Merged array: " + Arrays.toString(nums1_3)); // Output: [1]
  }

  /*
   Time Complexity:
   - O(m + n), where m is the number of elements in nums1 and n is the number of elements in nums2.
     We process each element from both arrays once.

   Space Complexity:
   - O(1), since we are modifying nums1 in place without using any additional space.
  */
}
