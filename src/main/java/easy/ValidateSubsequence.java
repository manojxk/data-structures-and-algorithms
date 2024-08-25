/*
 * Problem Statement:
 * Given two non-empty arrays of integers, write a function that determines whether the second array is a subsequence of the first one.
 *
 * A subsequence of an array is a set of numbers that aren't necessarily adjacent in the array but appear in the same order as they do in the array.
 *
 * Example:
 * Input: array = [5, 1, 22, 25, 6, -1, 8, 10], sequence = [1, 6, -1, 10]
 * Output: true
 *
 * Explanation:
 * The numbers [1, 6, -1, 10] appear in the array [5, 1, 22, 25, 6, -1, 8, 10] in the same order, making it a subsequence.
 */

package easy;

public class ValidateSubsequence {

  // Optimized Solution
  public static boolean isSubsequence(int[] array, int[] sequence) {
    int seqIndex = 0;

    for (int num : array) {
      if (seqIndex == sequence.length) {
        break;
      }
      if (num == sequence[seqIndex]) {
        seqIndex++;
      }
    }
    return seqIndex == sequence.length;
  }

  public static void main(String[] args) {
    int[] array = {5, 1, 22, 25, 6, -1, 8, 10};
    int[] sequence = {1, 6, -1, 10};
    System.out.println(isSubsequence(array, sequence)); // Output: true
  }
}
/*
O(n) time | O(1) space.*/
