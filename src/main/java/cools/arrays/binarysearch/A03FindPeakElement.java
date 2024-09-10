package cools.arrays.binarysearch;

/*
 Problem: Find Peak Element

 A peak element is an element that is strictly greater than its neighbors. Given a 0-indexed integer array nums,
 find a peak element and return its index. If the array contains multiple peaks, return the index to any of the peaks.

 You must write an algorithm that runs in O(log n) time.

 Example 1:
 Input: nums = [1,2,3,1]
 Output: 2
 Explanation: 3 is a peak element and your function should return the index number 2.

 Example 2:
 Input: nums = [1,2,1,3,5,6,4]
 Output: 5
 Explanation: Your function can return either index number 1 where the peak element is 2, or index number 5 where the peak element is 6.

 Constraints:
 - 1 <= nums.length <= 1000
 - -2^31 <= nums[i] <= 2^31 - 1
 - nums[i] != nums[i + 1] for all valid i.

 Solution Approach:
 1. Since the problem asks for O(log n) complexity, binary search is a suitable approach.
 2. We check the middle element and compare it with its neighbors.
 3. If the middle element is greater than its right neighbor, the peak lies in the left half (or it could be the middle itself).
 4. If the middle element is less than its right neighbor, the peak lies in the right half.
 5. We continue this process until we find a peak element.
*/

public class A03FindPeakElement {

  // Function to find the peak element in the array
  public int findPeakElement(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    // Binary search to find a peak element
    while (left < right) {
      int mid = left + (right - left) / 2;

      // If mid element is greater than its right neighbor, the peak is in the left half
      if (nums[mid] > nums[mid + 1]) {
        right = mid;
      }
      // If mid element is less than its right neighbor, the peak is in the right half
      else {
        left = mid + 1;
      }
    }

    // At the end of the binary search, left will be pointing to a peak element
    return left;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03FindPeakElement solution = new A03FindPeakElement();

    // Example 1
    int[] nums1 = {1, 2, 3, 1};
    System.out.println("Peak Element Index: " + solution.findPeakElement(nums1)); // Output: 2

    // Example 2
    int[] nums2 = {1, 2, 1, 3, 5, 6, 4};
    System.out.println("Peak Element Index: " + solution.findPeakElement(nums2)); // Output: 5
  }

  /*
   Time Complexity:
   - O(log n), where n is the length of the array. We are performing binary search which divides the search space in half at each step.

   Space Complexity:
   - O(1), since we are not using any extra space apart from variables to store the left, right, and mid pointers.
  */
}
