package cools.arrays.binarysearch;

/*
 Problem: Search Insert Position

 Given a sorted array of distinct integers and a target value, return the index if the target is found.
 If not, return the index where it would be if it were inserted in order.

 You must write an algorithm with O(log n) runtime complexity.

 Example 1:
 Input: nums = [1,3,5,6], target = 5
 Output: 2

 Example 2:
 Input: nums = [1,3,5,6], target = 2
 Output: 1

 Example 3:
 Input: nums = [1,3,5,6], target = 7
 Output: 4

 Constraints:
 - 1 <= nums.length <= 10^4
 - -10^4 <= nums[i] <= 10^4
 - nums contains distinct values sorted in ascending order.
 - -10^4 <= target <= 10^4

 Solution Approach:
 1. Use binary search to find the target or the position where it would be inserted.
 2. If the target is found, return its index.
 3. If the target is not found, return the index where it can be inserted in the sorted order.

*/

public class A01SearchInsertPosition {

  // Function to search the insert position of the target
  public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    // Perform binary search
    while (left <= right) {
      int mid = left + (right - left) / 2;

      // If the target is found, return the index
      if (nums[mid] == target) {
        return mid;
      }
      // If the target is smaller, search the left half
      else if (nums[mid] > target) {
        right = mid - 1;
      }
      // If the target is larger, search the right half
      else {
        left = mid + 1;
      }
    }

    // If the target is not found, return the insert position
    return left;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01SearchInsertPosition solution = new A01SearchInsertPosition();

    // Example 1
    int[] nums1 = {1, 3, 5, 6};
    int target1 = 5;
    System.out.println("Insert Position: " + solution.searchInsert(nums1, target1)); // Output: 2

    // Example 2
    int[] nums2 = {1, 3, 5, 6};
    int target2 = 2;
    System.out.println("Insert Position: " + solution.searchInsert(nums2, target2)); // Output: 1

    // Example 3
    int[] nums3 = {1, 3, 5, 6};
    int target3 = 7;
    System.out.println("Insert Position: " + solution.searchInsert(nums3, target3)); // Output: 4
  }

  /*
   Time Complexity:
   - O(log n), where n is the length of the array. We perform a binary search which divides the search space by half at each step.

   Space Complexity:
   - O(1), since we are not using any additional space except for a few variables.
  */
}
