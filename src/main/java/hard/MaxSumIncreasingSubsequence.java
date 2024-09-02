/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of integers and returns the greatest sum that can be generated from a strictly increasing subsequence in the array, as well as an array of the numbers in that subsequence.
 *
 * Definition:
 * - A subsequence of an array is a set of numbers that aren't necessarily adjacent in the array but that are in the same order as they appear in the array.
 * - For instance, the numbers [1, 3, 4] form a subsequence of the array [1, 2, 3, 4], and so do the numbers [2, 4].
 * - A single number in an array and the array itself are both valid subsequences.
 *
 * Assumption:
 * - There will only be one increasing subsequence with the greatest sum.
 *
 * Example:
 * Input: array = [10, 70, 20, 30, 50, 11, 30]
 * Output: [110, [10, 20, 30, 50]]  // The subsequence [10, 20, 30, 50] is strictly increasing and yields the greatest sum: 110.
 */
/*Optimized Solution: Dynamic Programming
Approach:
The optimized approach uses dynamic programming to build up the solution incrementally:

Subsequence Sum Array: We create an array where each element represents the maximum sum of any increasing subsequence ending with that element.
Backtracking Array: We maintain another array to keep track of the indices of the elements that contribute to the maximum sum.
Time Complexity:
O(n^2): The nested loop checks each pair of elements, ensuring that the subsequence is increasing.
Space Complexity:
O(n): Extra space is used for the subsequence sum array and the backtracking array.*/

package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxSumIncreasingSubsequence {

  // Optimized Solution using Dynamic Programming
  public static List<Object> maxSumIncreasingSubsequenceDP(int[] array) {
    int[] sums = array.clone();
    int[] sequences = new int[array.length];
    Arrays.fill(sequences, Integer.MIN_VALUE);

    int maxSumIdx = 0;

    for (int i = 0; i < array.length; i++) {
      int currentNum = array[i];
      for (int j = 0; j < i; j++) {
        if (array[j] < currentNum && sums[j] + currentNum > sums[i]) {
          sums[i] = sums[j] + currentNum;
          sequences[i] = j;
        }
      }
      if (sums[i] > sums[maxSumIdx]) {
        maxSumIdx = i;
      }
    }

    return buildSequence(array, sequences, maxSumIdx, sums[maxSumIdx]);
  }

  private static List<Object> buildSequence(int[] array, int[] sequences, int currentIdx, int sum) {
    List<Integer> sequence = new ArrayList<>();
    while (currentIdx != Integer.MIN_VALUE) {
      sequence.add(0, array[currentIdx]);
      currentIdx = sequences[currentIdx];
    }
    return Arrays.asList(sum, sequence);
  }

  public static void main(String[] args) {
    int[] array = {10, 70, 20, 30, 50, 11, 30};
    List<Object> result = maxSumIncreasingSubsequenceDP(array);
    System.out.println("Greatest Sum: " + result.get(0));
    System.out.println("Subsequence: " + result.get(1));
  }
}
