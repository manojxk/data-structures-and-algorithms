package cools.arrays;

/*
 Problem: Majority Element

 Given an array nums of size n, return the majority element. The majority element is the element that appears more than ⌊n / 2⌋ times.
 You may assume that the majority element always exists in the array.

 Constraints:
 - n == nums.length
 - 1 <= n <= 5 * 10^4
 - -10^9 <= nums[i] <= 10^9

 Example 1:
 Input: nums = [3,2,3]
 Output: 3

 Example 2:
 Input: nums = [2,2,1,1,1,2,2]
 Output: 2

 Solution Approach:
 1. **Boyer-Moore Voting Algorithm**:
    - The problem can be solved in linear time and constant space using the Boyer-Moore Voting Algorithm.
    - This algorithm works by maintaining a candidate for the majority element and a counter. If the current number is the same as the candidate, the counter is incremented. If it’s different, the counter is decremented.
    - If the counter becomes 0, the candidate is updated to the current number, and the process continues.
    - In the end, the candidate is guaranteed to be the majority element.
*/

public class A05MajorityElement {

  // Function to find the majority element using Boyer-Moore Voting Algorithm
  public int majorityElement(int[] nums) {
    int count = 0; // Counter for the candidate
    Integer candidate = null; // Candidate for the majority element

    // Traverse through the array
    for (int num : nums) {
      if (count == 0) {
        candidate = num; // Update candidate when count is 0
      }

      // Increment count if the current number is the candidate, otherwise decrement it
      count += (num == candidate) ? 1 : -1;
    }

    return candidate; // The candidate is the majority element
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05MajorityElement solution = new A05MajorityElement();

    // Example 1
    int[] nums1 = {3, 2, 3};
    System.out.println("Majority Element: " + solution.majorityElement(nums1)); // Output: 3

    // Example 2
    int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
    System.out.println("Majority Element: " + solution.majorityElement(nums2)); // Output: 2
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are only using a few variables to store the candidate and the count.
  */
}
