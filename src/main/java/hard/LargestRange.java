/*
 * Problem Statement:
 * Write a function that takes in an array of integers and returns an array of length 2
 * representing the largest range of consecutive integers found in the array.
 *
 * The first number in the output array should be the starting number of the range,
 * and the second number should be the ending number of the range.
 *
 * A range is defined as a set of consecutive numbers in the set of real integers.
 * For example, the output array [2, 6] represents the range {2, 3, 4, 5, 6}, which is a range of length 5.
 * Note that the numbers in the input array do not need to be sorted or adjacent to form a range.
 *
 * You can assume that there will only be one largest range.
 *
 * Example:
 * Input: array = [1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6]
 * Output: [0, 7]
 */
/*Approach:
We can optimize the solution by using a HashSet to store the elements of the array. We then iterate through each element and attempt to extend the range to both the left and right by checking for consecutive numbers in the set. Once a number is part of a range, it is removed from the set to prevent redundant checks.

Time Complexity:
O(n): We only iterate through the array once, and each lookup or removal from the HashSet is O(1).
Space Complexity:
O(n): We use additional space proportional to the size of the input array to store elements in the HashSet.*/

package hard;

import java.util.Arrays;
import java.util.HashSet;

public class LargestRange {

  // Optimized Solution
  public static int[] largestRange(int[] array) {
    HashSet<Integer> nums = new HashSet<>();
    for (int num : array) {
      nums.add(num);
    }

    int bestLength = 0;
    int[] bestRange = new int[2];

    for (int num : array) {
      if (!nums.contains(num)) continue;

      nums.remove(num);
      int left = num - 1;
      int right = num + 1;

      while (nums.contains(left)) {
        nums.remove(left);
        left--;
      }

      while (nums.contains(right)) {
        nums.remove(right);
        right++;
      }

      int currentLength = right - left - 1;
      if (currentLength > bestLength) {
        bestLength = currentLength;
        bestRange = new int[] {left + 1, right - 1};
      }
    }

    return bestRange;
  }

  public static void main(String[] args) {
    int[] array = {1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6};
    System.out.println(Arrays.toString(largestRange(array))); // Output: [0, 7]
  }
}
