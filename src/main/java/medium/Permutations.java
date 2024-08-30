/*
 * Problem Statement:
 * Write a function that takes in an array of unique integers and returns an array containing all possible permutations of those integers.
 * The permutations can be in any order, and the function should handle an empty array by returning an empty array as well.
 *
 * Example:
 * Input: array = [1, 2, 3]
 * Output: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
 */
/*Brute Force Solution
Approach:
The brute force solution involves generating all possible permutations of the array by recursively swapping elements.

Time Complexity:
O(n * n!): There are n! permutations, and generating each permutation takes O(n) time.
Space Complexity:
O(n * n!): Storing all permutations requires O(n \* n!) space.*/

package medium;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

  // Brute Force Solution
  public static List<List<Integer>> getPermutationsBruteForce(int[] array) {
    List<List<Integer>> permutations = new ArrayList<>();
    generatePermutations(0, array, permutations);
    return permutations;
  }

  private static void generatePermutations(
      int index, int[] array, List<List<Integer>> permutations) {
    if (index == array.length - 1) {
      List<Integer> permutation = new ArrayList<>();
      for (int num : array) {
        permutation.add(num);
      }
      permutations.add(permutation);
    } else {
      for (int i = index; i < array.length; i++) {
        swap(array, index, i);
        generatePermutations(index + 1, array, permutations);
        swap(array, index, i); // backtrack
      }
    }
  }

  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3};
    List<List<Integer>> permutations = getPermutationsBruteForce(array);
    System.out.println(
        permutations); // Output: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
  }
}
