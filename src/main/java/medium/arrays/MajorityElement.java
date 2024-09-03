/*
 * Problem Statement:
 * Given a non-empty, unordered array of positive integers, return the array's majority element.
 *
 * The majority element is the element that appears in over half of the array's indices.
 *
 * Important Constraints:
 * - You cannot sort the array.
 * - You cannot use more than constant space (O(1) space).
 * - You can assume that the input array will always have a majority element.
 *
 * Example:
 * Input: array = [1, 2, 3, 2, 2, 1, 2]
 * Output: 2
 * Explanation: The element 2 appears 4 times out of 7, which is more than half
 *              of the array's size, making it the majority element.
 */
/*Brute Force Solution
Approach:
For each element in the array, count how many times it appears.
If the count of an element is greater than half the size of the array, return that element as the majority element.
Time Complexity:
O(n^2): For each element, we scan the entire array to count occurrences, resulting in quadratic time complexity.
Space Complexity:
O(1): No additional space is used beyond a few variables.*/
package medium.arrays;

public class MajorityElement {

  // Brute Force Solution
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

  // O(n) time | O(1) space - where n is the number of elements in the array

  public int majorityElement(int[] array) {
    int count = 0;
    int answer = array[0];

    for (int value : array) {
      if (count == 0) {
        answer = value;
      }

      if (value == answer) {
        count++;
      } else {
        count--;
      }
    }
    return answer;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 2, 2, 1, 2};
    System.out.println(findMajorityElementBruteForce(array)); // Output: 2
  }
}

/*Optimized Solution: Boyer-Moore Voting Algorithm
Approach:
Candidate Selection: Traverse the array and maintain a current candidate for the majority element. Adjust the candidate based on the current element and its count.
        Verification: The candidate obtained after the first pass is then confirmed by counting its actual occurrences in a second pass.
Time Complexity:
O(n): The algorithm runs in linear time, making only a single pass through the array to identify the candidate and another pass to verify it.
Space Complexity:
O(1): Only a few variables are used, maintaining constant space.*/
