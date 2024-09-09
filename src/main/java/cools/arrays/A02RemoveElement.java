package cools.arrays;

/*
 Problem: Remove Element

 Given an integer array nums and an integer val, remove all occurrences of val in nums in-place.
 The order of the elements may be changed. Then return the number of elements in nums which are not equal to val.

 Constraints:
 - You need to modify the array in place and return the number of elements that are not equal to val.
 - It doesn't matter what values are left in the array after the first k elements (elements that are not equal to val).

 Example 1:
 Input: nums = [3,2,2,3], val = 3
 Output: 2, nums = [2,2,_,_]
 Explanation: Your function should return k = 2, with the first two elements of nums being 2.

 Example 2:
 Input: nums = [0,1,2,2,3,0,4,2], val = 2
 Output: 5, nums = [0,1,4,0,3,_,_,_]
 Explanation: Your function should return k = 5, with the first five elements containing [0,1,4,0,3].

 Solution Approach:
 1. Use a two-pointer technique:
    - One pointer (`i`) traverses the array.
    - Another pointer (`k`) keeps track of the position where the next non-val element should be placed.
 2. Traverse the array and for each element that is not equal to `val`, place it at position `k` and increment `k`.
 3. The array will be modified in place, and the first `k` elements will contain the desired values.
*/

import java.util.Arrays;

public class A02RemoveElement {

  // Function to remove all occurrences of val in nums
  public int removeElement(int[] nums, int val) {
    int k = 0; // Pointer for the next position of non-val elements

    // Traverse through all elements in nums
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != val) {
        nums[k] = nums[i]; // Place the non-val element at position k
        k++; // Move the pointer to the next position
      }
    }

    // Return the number of elements that are not equal to val
    return k;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02RemoveElement solution = new A02RemoveElement();

    // Example 1
    int[] nums1 = {3, 2, 2, 3};
    int val1 = 3;
    int k1 = solution.removeElement(nums1, val1);
    System.out.println(
        "Result: " + k1 + ", Modified Array: " + Arrays.toString(nums1)); // Output: 2, [2, 2, _, _]

    // Example 2
    int[] nums2 = {0, 1, 2, 2, 3, 0, 4, 2};
    int val2 = 2;
    int k2 = solution.removeElement(nums2, val2);
    System.out.println(
        "Result: "
            + k2
            + ", Modified Array: "
            + Arrays.toString(nums2)); // Output: 5, [0, 1, 4, 0, 3, _, _, _]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are modifying the array in place and using only constant extra space.
  */
}
