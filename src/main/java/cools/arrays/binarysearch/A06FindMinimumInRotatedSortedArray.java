package cools.arrays.binarysearch; /*
                                    Problem: Find Minimum in Rotated Sorted Array

                                    Suppose an array of length n sorted in ascending order is rotated between 1 and n times. Given the sorted rotated array nums of unique elements,
                                    return the minimum element of this array.

                                    Example 1:
                                    Input: nums = [3,4,5,1,2]
                                    Output: 1
                                    Explanation: The original array was [1,2,3,4,5] rotated 3 times.

                                    Example 2:
                                    Input: nums = [4,5,6,7,0,1,2]
                                    Output: 0
                                    Explanation: The original array was [0,1,2,4,5,6,7] rotated 4 times.

                                    Example 3:
                                    Input: nums = [11,13,15,17]
                                    Output: 11
                                    Explanation: The array was not rotated.

                                    Constraints:
                                    - 1 <= nums.length <= 5000
                                    - -5000 <= nums[i] <= 5000
                                    - All the integers of nums are unique.
                                    - nums is guaranteed to be rotated at least once.

                                    Solution Approach:
                                    1. Since the array is rotated and sorted, binary search can be used to find the minimum element in O(log n) time.
                                    2. At each step, check if the middle element is greater than the rightmost element.
                                    3. If nums[mid] > nums[right], then the minimum is in the right half. Otherwise, it's in the left half (including mid).
                                    4. Continue this process until the minimum element is found.
                                   */

public class A06FindMinimumInRotatedSortedArray {

  // Function to find the minimum element in the rotated sorted array
  public int findMin(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    // Binary search to find the minimum element
    while (left < right) {
      int mid = left + (right - left) / 2;

      // If the middle element is greater than the rightmost element, the minimum is in the right
      // half
      if (nums[mid] > nums[right]) {
        left = mid + 1;
      }
      // Otherwise, the minimum is in the left half (including mid)
      else if (nums[mid] <= nums[right]) {
        right = mid;
      }
    }

    // The left pointer will point to the minimum element
    return nums[left];
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A06FindMinimumInRotatedSortedArray solution = new A06FindMinimumInRotatedSortedArray();

    // Example 1
    int[] nums1 = {3, 4, 5, 1, 2};
    System.out.println("Minimum Element: " + solution.findMin(nums1)); // Output: 1

    // Example 2
    int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
    System.out.println("Minimum Element: " + solution.findMin(nums2)); // Output: 0

    // Example 3
    int[] nums3 = {11, 13, 15, 17};
    System.out.println("Minimum Element: " + solution.findMin(nums3)); // Output: 11
  }

  /*
   Time Complexity:
   - O(log n), where n is the length of the array. We perform binary search which divides the search space in half at each step.

   Space Complexity:
   - O(1), since we are not using any extra space apart from variables to store the left, right, and mid pointers.
  */
}
