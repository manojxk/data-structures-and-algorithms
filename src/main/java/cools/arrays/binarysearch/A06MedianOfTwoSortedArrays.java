package cools.arrays.binarysearch;

/*
 Problem: Median of Two Sorted Arrays

 Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 The overall run time complexity should be O(log (m+n)).

 Example 1:
 Input: nums1 = [1,3], nums2 = [2]
 Output: 2.00000
 Explanation: Merged array = [1, 2, 3], and the median is 2.

 Example 2:
 Input: nums1 = [1,2], nums2 = [3,4]
 Output: 2.50000
 Explanation: Merged array = [1, 2, 3, 4], and the median is (2 + 3) / 2 = 2.5.

 Constraints:
 - nums1.length == m
 - nums2.length == n
 - 0 <= m <= 1000
 - 0 <= n <= 1000
 - 1 <= m + n <= 2000
 - -10^6 <= nums1[i], nums2[i] <= 10^6

 Solution Approach:
 1. We can use **binary search** on the smaller array to find the correct partition between nums1 and nums2.
 2. The partition divides the arrays into two halves where all elements on the left are smaller than all elements on the right.
 3. Using binary search on nums1 (or the smaller array), we find the correct partition where the median lies.
 4. The median is calculated based on the maximum of the left partition and the minimum of the right partition.

*/

public class A06MedianOfTwoSortedArrays {

  // Function to find the median of two sorted arrays
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    // Ensure that nums1 is the smaller array to perform binary search on
    if (nums1.length > nums2.length) {
      return findMedianSortedArrays(nums2, nums1);
    }

    int m = nums1.length;
    int n = nums2.length;
    int low = 0;
    int high = m;

    // Perform binary search on the smaller array
    while (low <= high) {
      int partition1 = (low + high) / 2;
      int partition2 = (m + n + 1) / 2 - partition1;

      // If partition1 is 0, it means nothing is there on the left side of nums1
      // If partition1 is m, it means all elements of nums1 are on the left side
      int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
      int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

      // Similarly for nums2
      int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
      int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

      // Check if we have found the correct partition
      if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
        // If the combined array size is even
        if ((m + n) % 2 == 0) {
          return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
        }
        // If the combined array size is odd
        else {
          return Math.max(maxLeft1, maxLeft2);
        }
      }
      // If we are too far on the right side, move left
      else if (maxLeft1 > minRight2) {
        high = partition1 - 1;
      }
      // If we are too far on the left side, move right
      else {
        low = partition1 + 1;
      }
    }

    // If no valid partition is found, throw an exception (this shouldn't happen given valid input)
    throw new IllegalArgumentException("Input arrays are not valid.");
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A06MedianOfTwoSortedArrays solution = new A06MedianOfTwoSortedArrays();

    // Example 1
    int[] nums1 = {1, 3};
    int[] nums2 = {2};
    System.out.println("Median: " + solution.findMedianSortedArrays(nums1, nums2)); // Output: 2.0

    // Example 2
    int[] nums3 = {1, 2};
    int[] nums4 = {3, 4};
    System.out.println("Median: " + solution.findMedianSortedArrays(nums3, nums4)); // Output: 2.5
  }

  /*
   Time Complexity:
   - O(log(min(m, n))), where m and n are the lengths of the two arrays. We perform binary search on the smaller array.

   Space Complexity:
   - O(1), since we only use a constant amount of extra space for variables.
  */
}
