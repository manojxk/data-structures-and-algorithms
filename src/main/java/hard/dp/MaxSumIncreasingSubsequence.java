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
/*
* Approach
Dynamic Programming:

We will use dynamic programming to solve this problem.
Create an array maxSums where each element at index i represents the maximum sum of an increasing subsequence that ends at index i.
Similarly, create an array sequences to store the index of the previous element that can be part of the subsequence ending at index i. This helps in reconstructing the subsequence later.
Initialization:

Set maxSums[i] to array[i] since the smallest subsequence starting at any index is the element itself.
State Transition:

For each element array[i], check all previous elements array[j] (where j < i). If array[j] < array[i], it means array[i] can extend the increasing subsequence ending at array[j]. Update maxSums[i] accordingly if a greater sum can be formed by adding array[i] to the subsequence ending at array[j].
Result:

After processing all elements, the greatest sum can be found in the maxSums array. The corresponding subsequence can be reconstructed using the sequences array.
*
* */

package hard.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxSumIncreasingSubsequence {

  public static List<Object> maxSumIncreasingSubsequence(int[] array) {
    int[] maxSums =
        array.clone(); // Stores the maximum sums for each subsequence ending at each index
    int[] sequences =
        new int[array.length]; // Stores the previous index for reconstructing the subsequence
    for (int i = 0; i < array.length; i++) {
      sequences[i] = -1; // Initialize sequences with -1 (no previous index)
    }

    // Initialize variables to track the maximum sum and its corresponding index
    int maxSumIdx = 0;

    // Fill the maxSums array
    for (int i = 1; i < array.length; i++) {
      for (int j = 0; j < i; j++) {
        if (array[j] < array[i] && maxSums[i] < maxSums[j] + array[i]) {
          maxSums[i] = maxSums[j] + array[i];
          sequences[i] = j; // Store the previous index in the sequence
        }
      }
      // Update maxSumIdx if we found a greater sum at index `i`
      if (maxSums[i] > maxSums[maxSumIdx]) {
        maxSumIdx = i;
      }
    }

    // Reconstruct the subsequence from the sequences array
    List<Integer> subsequence = buildSequence(array, sequences, maxSumIdx);
    // Return the maximum sum and the subsequence
    List<Object> result = new ArrayList<>();
    result.add(maxSums[maxSumIdx]); // Greatest sum
    result.add(subsequence); // Subsequence that forms the greatest sum
    return result;
  }

  private static List<Integer> buildSequence(int[] array, int[] sequences, int currentIdx) {
    List<Integer> sequence = new ArrayList<>();
    while (currentIdx != -1) {
      sequence.add(0, array[currentIdx]); // Add elements in reverse order
      currentIdx = sequences[currentIdx]; // Move to the previous index in the sequence
    }
    return sequence;
  }

  public static void main(String[] args) {
    int[] array = {10, 70, 20, 30, 50, 11, 30};
    System.out.println(maxSumIncreasingSubsequence(array));
  }
}
/*
Explanation:
Initialization:

maxSums[i]: This array holds the maximum sum of an increasing subsequence ending at index i. Initially, each element is set to the corresponding value in array because the smallest subsequence is the element itself.
sequences[i]: This array stores the index of the previous element that forms the subsequence for array[i]. It's initialized to -1 since no element precedes the first one in the sequence.
Main Logic:

The nested loops check all elements array[j] before array[i]. If array[j] < array[i] and the subsequence sum is greater by including array[i], we update maxSums[i] and track the previous index in sequences[i].
Reconstructing the Sequence:

After filling up maxSums and sequences, the function buildSequence is used to backtrack from the index with the maximum sum (maxSumIdx). We add elements to the sequence in reverse order by following the sequences array.
Time Complexity:
Time Complexity: O(n^2) where n is the length of the input array. This is due to the nested loops that iterate over the array.
Space Complexity: O(n) for storing the maxSums, sequences, and the resulting subsequence.*/
