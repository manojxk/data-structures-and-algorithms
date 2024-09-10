package cools.arrays.binarysearch;

/*
 Problem: Find First and Last Position of Element in Sorted Array

 Given an array of integers nums sorted in non-decreasing order, find the starting and ending position of a given target value.
 If target is not found in the array, return [-1, -1].

 You must write an algorithm with O(log n) runtime complexity.

 Example 1:
 Input: nums = [5,7,7,8,8,10], target = 8
 Output: [3,4]

 Example 2:
 Input: nums = [5,7,7,8,8,10], target = 6
 Output: [-1, -1]

 Example 3:
 Input: nums = [], target = 0
 Output: [-1, -1]

 Solution Approach:
 1. Since the problem requires O(log n) complexity, binary search is the suitable approach.
 2. We will perform two binary searches:
    - First to find the **first position** of the target.
    - Second to find the **last position** of the target.
 3. By combining the results of both binary searches, we can determine the range of indices where the target occurs.
*/

import java.util.Arrays;

public class A05FirstLastPosition {

  // Function to find the first and last position of target in the array
  public int[] searchRange(int[] nums, int target) {
    int[] result = new int[2]; // Array to store the result
    result[0] = findFirstPosition(nums, target); // Find first position
    result[1] = findLastPosition(nums, target); // Find last position
    return result;
  }

  // Function to find the first position of the target using binary search
  private int findFirstPosition(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int firstPos = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) {
        firstPos = mid; // Potential first occurrence found
        right = mid - 1; // Search in the left half to find the first occurrence
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return firstPos;
  }

  // Function to find the last position of the target using binary search
  private int findLastPosition(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int lastPos = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (nums[mid] == target) {
        lastPos = mid; // Potential last occurrence found
        left = mid + 1; // Search in the right half to find the last occurrence
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return lastPos;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05FirstLastPosition solution = new A05FirstLastPosition();

    // Example 1
    int[] nums1 = {5, 7, 7, 8, 8, 10};
    int target1 = 8;
    System.out.println(
        "First and Last Position: "
            + Arrays.toString(solution.searchRange(nums1, target1))); // Output: [3, 4]

    // Example 2
    int[] nums2 = {5, 7, 7, 8, 8, 10};
    int target2 = 6;
    System.out.println(
        "First and Last Position: "
            + Arrays.toString(solution.searchRange(nums2, target2))); // Output: [-1, -1]

    // Example 3
    int[] nums3 = {};
    int target3 = 0;
    System.out.println(
        "First and Last Position: "
            + Arrays.toString(solution.searchRange(nums3, target3))); // Output: [-1, -1]
  }

  /*
   Time Complexity:
   - O(log n), where n is the length of the array. We perform two binary searches which each take O(log n).

   Space Complexity:
   - O(1), since we only use a constant amount of extra space for variables.
  */
}
