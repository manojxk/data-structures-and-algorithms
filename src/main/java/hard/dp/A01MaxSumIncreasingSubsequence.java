package hard.dp;

/*
 Problem: Max Sum Increasing Subsequence

 You are given an array of integers. Write a function that returns both:
 1. The maximum sum of the increasing subsequence in the array, and
 2. The subsequence itself.

 A subsequence is a sequence that can be derived from the array by deleting some or no elements, without changing the order of the remaining elements.
 The sum of the subsequence is the sum of its elements, and the subsequence must be strictly increasing.

 Example 1:
 Input: [10, 70, 20, 30, 50, 11, 30]
 Output: [110, [10, 20, 30, 50]]
 Explanation: The maximum sum increasing subsequence is [10, 20, 30, 50] and the sum is 110.

 Example 2:
 Input: [8, 12, 2, 3, 15, 5, 7]
 Output: [35, [8, 12, 15]]
 Explanation: The maximum sum increasing subsequence is [8, 12, 15] and the sum is 35.

 Constraints:
 - The input array can have at most 1000 elements.
 - The values in the array can be negative or positive.

 Solution Approach:
 1. Use dynamic programming to store the maximum sum of increasing subsequences up to each element.
 2. Keep track of the previous indices for reconstructing the subsequence.
 3. The result is the maximum sum from these subsequences along with the actual subsequence.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A01MaxSumIncreasingSubsequence {

  // Function to find the maximum sum increasing subsequence and the subsequence itself
  public static List<List<Integer>> maxSumIncreasingSubsequence(int[] array) {
    // Step 1: Initialize the dp arrays to store the maximum sums and previous indices
    int[] sums = array.clone(); // To store the maximum sum up to each index
    int[] previousIndices =
        new int[array.length]; // To track the previous element index in the sequence
    Arrays.fill(previousIndices, -1); // Initialize to -1 (no previous element)

    // Step 2: Perform the dynamic programming solution
    int maxSumIdx = 0; // To track the index with the maximum sum
    for (int i = 1; i < array.length; i++) {
      for (int j = 0; j < i; j++) {
        if (array[i] > array[j] && sums[i] < sums[j] + array[i]) {
          sums[i] = sums[j] + array[i];
          previousIndices[i] = j;
        }
      }
      if (sums[i] > sums[maxSumIdx]) {
        maxSumIdx = i;
      }
    }

    // Step 3: Reconstruct the subsequence
    List<Integer> subsequence = new ArrayList<>();
    int currentIdx = maxSumIdx;
    while (currentIdx != -1) {
      subsequence.add(0, array[currentIdx]); // Add elements to the subsequence in reverse order
      currentIdx = previousIndices[currentIdx];
    }

    // Step 4: Prepare the result
    List<List<Integer>> result = new ArrayList<>();
    result.add(Arrays.asList(sums[maxSumIdx])); // The maximum sum
    result.add(subsequence); // The subsequence

    return result;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int[] array1 = {10, 70, 20, 30, 50, 11, 30};
    List<List<Integer>> result1 = maxSumIncreasingSubsequence(array1);
    System.out.println("Max sum: " + result1.get(0).get(0)); // Output: 110
    System.out.println("Subsequence: " + result1.get(1)); // Output: [10, 20, 30, 50]

    // Example 2
    int[] array2 = {8, 12, 2, 3, 15, 5, 7};
    List<List<Integer>> result2 = maxSumIncreasingSubsequence(array2);
    System.out.println("Max sum: " + result2.get(0).get(0)); // Output: 35
    System.out.println("Subsequence: " + result2.get(1)); // Output: [8, 12, 15]
  }

  /*
   Time Complexity:
   - O(n^2), where n is the number of elements in the input array. The double loop iterates through each pair of elements.

   Space Complexity:
   - O(n), where n is the number of elements in the input array. We use space for the 'sums' and 'previousIndices' arrays.
  */
}
