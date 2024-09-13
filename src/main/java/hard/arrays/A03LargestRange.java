package hard.arrays;

/*
 Problem: Largest Range

 Write a function that takes in an array of integers and returns an array of length 2 representing the largest range of integers contained in the array.
 The first number in the output array should be the first number in the range, and the second number should be the last number in the range.
 A range of numbers is defined as a set of numbers that come right after each other in the set of real integers.

 For example, the output array [1, 3] represents the range [1, 2, 3].

 The function should return the largest range, which is the longest set of numbers that come right after each other in the input array.

 Example 1:
 Input: nums = [1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6]
 Output: [0, 7]
 Explanation: The largest range is from 0 to 7.

 Example 2:
 Input: nums = [4, 2, 1, 3]
 Output: [1, 4]
 Explanation: The largest range is from 1 to 4.

 Constraints:
 - You can assume that there will be only one largest range.
 - 0 <= nums.length <= 10^6
 - The values in the array can be positive or negative, but they are distinct integers.

 Solution Approach:
 1. Use a **HashSet** to store the numbers for fast lookup.
 2. Iterate through the array and, for each number, try to expand the range by looking for consecutive numbers in the set.
 3. Once a range is fully explored, compare its length with the current largest range and update accordingly.
 4. Mark the numbers that have been processed to avoid redundant work.
*/

import java.util.*;

public class A03LargestRange {

  // Function to find the largest range of consecutive numbers in the array
  public int[] largestRange(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new int[0];
    }

    // Step 1: Store all numbers in a set for fast lookup
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
      numSet.add(num);
    }

    int longestLength = 0;
    int[] bestRange = new int[2];

    // Step 2: Iterate through each number in the array
    for (int num : nums) {
      if (!numSet.contains(num)) {
        continue; // Skip if the number has already been processed
      }

      numSet.remove(num); // Mark the number as processed

      // Step 3: Expand the range to the left (decreasing numbers)
      int left = num - 1;
      while (numSet.contains(left)) {
        numSet.remove(left);
        left--;
      }

      // Step 4: Expand the range to the right (increasing numbers)
      int right = num + 1;
      while (numSet.contains(right)) {
        numSet.remove(right);
        right++;
      }

      int currentLength = right - left - 1;

      // Step 5: Update the best range if the current one is longer
      if (currentLength > longestLength) {
        longestLength = currentLength;
        bestRange =
            new int[] {
              left + 1, right - 1
            }; // Adjust because left and right are one step beyond the range
      }
    }

    return bestRange; // Return the largest range
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03LargestRange solution = new A03LargestRange();

    // Example 1: nums = [1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6]
    int[] nums1 = {1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6};
    int[] result1 = solution.largestRange(nums1);
    System.out.println("Largest Range: [" + result1[0] + ", " + result1[1] + "]"); // Output: [0, 7]

    // Example 2: nums = [4, 2, 1, 3]
    int[] nums2 = {4, 2, 1, 3};
    int[] result2 = solution.largestRange(nums2);
    System.out.println("Largest Range: [" + result2[0] + ", " + result2[1] + "]"); // Output: [1, 4]
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the input array. Each number is processed once and each range is explored once.

   Space Complexity:
   - O(n), where n is the number of elements in the input array. We use a HashSet to store all the numbers.
  */
}
