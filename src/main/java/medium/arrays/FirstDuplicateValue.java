/*
 * Problem Statement:
 * Given an array of integers between 1 and n (inclusive), where n is the length of the array,
 * write a function that returns the first integer that appears more than once when the array is
 * read from left to right.
 *
 * The function should return the integer whose first duplicate value has the smallest index.
 * If no integer appears more than once, the function should return -1.
 *
 * Note: You are allowed to mutate the input array.
 *
 * Example 1:
 * Input: array = [2, 1, 5, 2, 3, 3, 4]
 * Output: 2  // 2 is the first integer that appears more than once.
 * // 3 also appears more than once, but the second 3 appears after the second 2.
 *
 * Example 2:
 * Input: array = [2, 1, 5, 3, 3, 2, 4]
 * Output: 3  // 3 is the first integer that appears more than once.
 * // 2 also appears more than once, but the second 2 appears after the second 3.
 */

package medium.arrays;

import java.util.HashSet;

public class FirstDuplicateValue {
  // Brute Force Solution
  /*  Brute Force Solution
  Approach:
  The brute force solution involves using two nested loops. For each element, we check if it appears again in the subsequent elements.

  Time Complexity:
  O(n^2): We have a nested loop where each element is compared with all other elements, leading to quadratic time complexity.
  Space Complexity:
  O(1): The solution only uses a constant amount of extra space.*/
  public static int firstDuplicateValueBruteForce(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = i + 1; j < array.length; j++) {
        if (array[i] == array[j]) {
          return array[i];
        }
      }
    }
    return -1; // Return -1 if no duplicate is found
  }

  /*    Optimized Solution 1: Using a HashSet
  Approach:
  We can optimize the solution by using a HashSet to keep track of the elements we’ve already seen. As we iterate through the array, we check if the current element is already in the HashSet. If it is, then it’s the first duplicate.

  Time Complexity:
  O(n): We iterate through the array once, making this solution linear in time.
  Space Complexity:
  O(n): The HashSet requires space proportional to the number of elements in the array.*/
  public static int firstDuplicateValueHashSet(int[] array) {
    HashSet<Integer> seen = new HashSet<>();
    for (int value : array) {
      if (seen.contains(value)) {
        return value;
      } else {
        seen.add(value);
      }
    }
    return -1; // Return -1 if no duplicate is found
  }

  /*    Optimized Solution 2: Using the Array as a HashMap
  Approach:
  This solution takes advantage of the fact that the integers are between 1 and n (inclusive). By treating the array elements as indices and marking visited elements by negating the values at those indices, we can identify duplicates.

  Traverse the array.
  For each value x, consider abs(x) as the index.
  If the value at array[abs(x) - 1] is negative, x is a duplicate.
          Otherwise, negate the value at array[abs(x) - 1].
  Time Complexity:
  O(n): We iterate through the array once.
  Space Complexity:
  O(1): No additional space is used apart from the input array.*/
  // Optimized Solution using Array as HashMap
  public static int firstDuplicateValueArrayAsHashMap(int[] array) {
    for (int i = 0; i < array.length; i++) {
      int absValue = Math.abs(array[i]);
      if (array[absValue - 1] < 0) {
        return absValue;
      } else {
        array[absValue - 1] = -array[absValue - 1];
      }
    }
    return -1; // Return -1 if no duplicate is found
  }

  /**
   * This function returns the first integer that appears more than once when the array is read from
   * left to right.
   *
   * @param array The input array.
   * @return The first integer that appears more than once, or -1 if no integer appears more than
   *     once.
   */
  public static int firstDuplicateValueCopilot(int[] array) {
    // Iterate over the array.
    for (int i = 0; i < array.length; i++) {
      // Use the absolute value of the current element as an index.
      int idx = Math.abs(array[i]) - 1;

      // If the element at the index is negative, return the absolute value of the current element.
      if (array[idx] < 0) {
        return Math.abs(array[i]);
      }

      // Otherwise, negate the element at the index.
      array[idx] *= -1;
    }

    // If no integer appears more than once, return -1.
    return -1;
  }

  public static void main(String[] args) {
    int[] array1 = {2, 1, 5, 2, 3, 3, 4};
    int[] array2 = {2, 1, 5, 3, 3, 2, 4};
    System.out.println(firstDuplicateValueBruteForce(array1)); // Output: 2
    System.out.println(firstDuplicateValueBruteForce(array2)); // Output: 3
  }
}
