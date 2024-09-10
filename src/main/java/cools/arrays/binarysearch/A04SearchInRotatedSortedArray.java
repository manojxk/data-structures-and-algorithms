package cools.arrays.binarysearch;



/*
 Problem: Search in Rotated Sorted Array

 There is an integer array nums sorted in ascending order (with distinct values), which may be rotated at an unknown pivot.
 Given a rotated sorted array and a target value, return the index of the target if it is present in the array, or -1 if it is not.
 You must write an algorithm with O(log n) runtime complexity.

 Example 1:
 Input: nums = [4,5,6,7,0,1,2], target = 0
 Output: 4

 Example 2:
 Input: nums = [4,5,6,7,0,1,2], target = 3
 Output: -1

 Example 3:
 Input: nums = [1], target = 0
 Output: -1

 Constraints:
 - 1 <= nums.length <= 5000
 - -10^4 <= nums[i] <= 10^4
 - All values of nums are distinct.
 - nums is possibly rotated.
 - -10^4 <= target <= 10^4

 Solution Approach:
 1. We can use **binary search** to achieve O(log n) time complexity.
 2. Since the array is rotated, we need to modify the binary search algorithm to consider the rotated halves of the array.
 3. We need to determine which half is sorted (left or right) and decide the search direction based on that.
*/

public class A04SearchInRotatedSortedArray {

    // Function to search for the target in the rotated sorted array
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        // Perform binary search
        while (left <= right) {
            int mid = left + (right - left) / 2;

            // Check if mid element is the target
            if (nums[mid] == target) {
                return mid;
            }

            // Determine which side is sorted
            if (nums[left] <= nums[mid]) {
                // Left half is sorted
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;  // Target is in the left half
                } else {
                    left = mid + 1;  // Target is in the right half
                }
            } else {
                // Right half is sorted
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;  // Target is in the right half
                } else {
                    right = mid - 1;  // Target is in the left half
                }
            }
        }

        // If the target is not found, return -1
        return -1;
    }

    // Main function to run and test the solution
    public static void main(String[] args) {
        A04SearchInRotatedSortedArray solution = new A04SearchInRotatedSortedArray();

        // Example 1
        int[] nums1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        System.out.println("Target found at index: " + solution.search(nums1, target1));  // Output: 4

        // Example 2
        int[] nums2 = {4, 5, 6, 7, 0, 1, 2};
        int target2 = 3;
        System.out.println("Target found at index: " + solution.search(nums2, target2));  // Output: -1

        // Example 3
        int[] nums3 = {1};
        int target3 = 0;
        System.out.println("Target found at index: " + solution.search(nums3, target3));  // Output: -1
    }

    /*
     Time Complexity:
     - O(log n), where n is the length of the array. We are performing binary search which divides the search space in half at each step.

     Space Complexity:
     - O(1), since we are not using any extra space apart from variables to store the left, right, and mid pointers.
    */
}

