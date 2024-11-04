/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of integers that are sorted in ascending order.
 * The function should return a new array of the same length with the squares of the original integers,
 * also sorted in ascending order.
 *
 * Example:
 * Input: array = [1, 2, 3, 5, 6, 8, 9]
 * Output: [1, 4, 9, 25, 36, 64, 81]
 *
 * Explanation:
 * The output array contains the squares of the input array's elements, sorted in ascending order.
 */

package easy.arrays;

import java.util.Arrays;

public class SortedSquaredArray {

  // Brute Force Solution
  public static int[] sortedSquaredArrayBruteForce(int[] array) {
    int[] squaredArray = new int[array.length];

    // Square each element
    for (int i = 0; i < array.length; i++) {
      squaredArray[i] = array[i] * array[i];
    }

    // Sort the squared array
    Arrays.sort(squaredArray);

    return squaredArray;
  }

  
    // Solution 1: Two Pointer Approach
    // Time Complexity: O(n) | Space Complexity: O(n)
    public static int[] sortedSquaredArray(int[] array) {
        // Initialize an array to hold the squared values
        int[] squaredArray = new int[array.length];
        // Use two pointers, one starting from the beginning and one from the end
        int left = 0;
        int right = array.length - 1;
        int index = array.length - 1;

        // Iterate until all elements have been squared and added
        while (left <= right) {
            int leftSquare = array[left] * array[left];
            int rightSquare = array[right] * array[right];

            // Place the larger square at the current index from the back
            if (leftSquare > rightSquare) {
                squaredArray[index] = leftSquare;
                left++;
            } else {
                squaredArray[index] = rightSquare;
                right--;
            }
            index--;
        }

        return squaredArray;
    }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 5, 6, 8, 9};
    System.out.println(
        Arrays.toString(sortedSquaredArrayBruteForce(array))); // Output: [1, 4, 9, 25, 36, 64, 81]
  }
}

/*
Brute Force Solution: O(n log n) time | O(n) space.
Optimized Solution: O(n) time | O(n) space.
*/
