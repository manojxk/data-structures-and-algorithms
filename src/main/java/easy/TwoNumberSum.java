/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of distinct integers and an integer representing a target sum.
 * The function should return an array containing any two numbers from the input array that sum up to the target sum.
 * If no such pair exists, the function should return an empty array.
 *
 * Conditions:
 * - The input array will contain distinct integers.
 * - The target sum must be obtained by summing two different integers in the array; a single integer cannot be used twice.
 * - You can assume that there will be at most one pair of numbers summing up to the target sum.
 *
 * Example:
 * Input: array = [3, 5, -4, 8, 11, 1, -1, 6], targetSum = 10
 * Output: [-1, 11]  // The order of the numbers in the output can vary.
 */

package easy;

import java.util.Arrays;
import java.util.HashSet;

public class TwoNumberSum {

  // Brute Force Solution
  public static int[] twoNumberSumBruteForce(int[] array, int targetSum) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = i + 1; j < array.length; j++) {
        if (array[i] + array[j] == targetSum) {
          return new int[] {array[i], array[j]};
        }
      }
    }
    return new int[0]; // Return an empty array if no pair is found
  }

  public static int[] twoNumberSumHashSet(int[] array, int targetSum) {
    HashSet<Integer> numbers = new HashSet<>();
    for (int num : array) {
      int potentialMatch = targetSum - num;
      if (numbers.contains(potentialMatch)) {
        return new int[] {potentialMatch, num};
      } else {
        numbers.add(num);
      }
    }
    return new int[0]; // Return an empty array if no pair is found
  }

  public static int[] twoNumberSumTwoPointers(int[] array, int targetSum) {
    Arrays.sort(array);
    int left = 0;
    int right = array.length - 1;

    while (left < right) {
      int currentSum = array[left] + array[right];
      if (currentSum == targetSum) {
        return new int[] {array[left], array[right]};
      } else if (currentSum < targetSum) {
        left++;
      } else {
        right--;
      }
    }
    return new int[0]; // Return an empty array if no pair is found
  }

  public static void main(String[] args) {
    int[] array = {3, 5, -4, 8, 11, 1, -1, 6};
    int targetSum = 10;
    System.out.println(
        Arrays.toString(twoNumberSumBruteForce(array, targetSum))); // Output: [-1, 11]
  }
}

/*Brute Force Solution: O(n^2) time | O(1) space.
HashSet Solution: O(n) time | O(n) space.
Two-Pointer Solution: O(n log n) time | O(1) space.*/
