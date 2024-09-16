package medium.arrays;

/*
 Problem: Majority Element

 You are given an array of integers. A majority element is an element that appears more than n/2 times, where n is the length of the array.
 Your task is to find and return the majority element. It is guaranteed that a majority element exists.

 Example:

 Input: [3, 2, 3]
 Output: 3

 Input: [2, 2, 1, 1, 1, 2, 2]
 Output: 2
*/

/*
 Solution Steps (Boyer-Moore Voting Algorithm):

 1. Initialize a candidate element and a count.
 2. Traverse through the array:
    - If the count is 0, pick the current element as the new candidate.
    - If the current element is the same as the candidate, increment the count.
    - If the current element is different, decrement the count.
 3. At the end of the traversal, the candidate will be the majority element.
*/

public class A13MajorityElement {
  public static int findMajorityElementBruteForce(int[] array) {
    int n = array.length;
    int majorityCount = n / 2;

    for (int i = 0; i < n; i++) {
      int count = 0;
      for (int j = 0; j < n; j++) {
        if (array[i] == array[j]) {
          count++;
        }
      }
      if (count > majorityCount) {
        return array[i];
      }
    }
    return -1; // This should never be reached because there is always a majority element
  }

  // Function to find the majority element using Boyer-Moore Voting Algorithm
  public static int majorityElement(int[] nums) {
    int candidate = 0;
    int count = 0;

    // Step 1: Traverse through the array
    for (int num : nums) {
      if (count == 0) {
        // Step 2: Pick the current element as the candidate if count is 0
        candidate = num;
      }

      // Step 3: Adjust the count based on whether the current element is the candidate
      if (num == candidate) {
        count++;
      } else {
        count--;
      }
    }

    // Step 4: The candidate is guaranteed to be the majority element
    return candidate;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] nums1 = {3, 2, 3};
    System.out.println("Majority element: " + majorityElement(nums1)); // Output: 3

    int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
    System.out.println("Majority element: " + majorityElement(nums2)); // Output: 2
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We traverse the array once.

   Space Complexity:
   - O(1), since we are using only a few extra variables (candidate and count).
  */
}
