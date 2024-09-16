/*
 * Problem Statement:
 *
 * Write a function that takes in a non-empty array of distinct integers and an
 * integer representing a target sum. The function should find all triplets in
 * the array that sum up to the target sum and return a two-dimensional array of
 * all these triplets. The numbers in each triplet should be ordered in ascending
 * order, and the triplets themselves should be ordered in ascending order with
 * respect to the numbers they hold.
 *
 * If no three numbers sum up to the target sum, the function should return an
 * empty array.
 *
 * Sample Input:
 * array = [12, 3, 1, 2, -6, 5, -8, 6]
 * targetSum = 0
 *
 * Sample Output:
 * [[-8, 2, 6], [-8, 3, 5], [-6, 1, 5]]
 */

/*Time Complexity:
O(n^3): The time complexity is cubic because of the three nested loops, making this approach less efficient for large arrays.
Space Complexity:
O(k): The space complexity is proportional to the number of valid triplets found, where
𝑘 is the number of valid triplets.*/
/*Optimized Solution
Approach:
Sorting the Array:

Start by sorting the array. This allows for efficient use of the two-pointer technique to find pairs that sum up to a given value.
Finding Triplets:

Iterate over each number, using it as a base number.
For each base number, use two pointers to find two other numbers that, along with the base number, sum up to the target.
This approach is efficient because it reduces the problem to a simpler two-sum problem for each base number.
Avoiding Duplicates:

Ensure that the solution avoids duplicate triplets by skipping over duplicate numbers during iteration.
Time Complexity:
O(n^2): Due to the outer loop iterating through each element and the inner loop using the two-pointer technique, the time complexity is quadratic.
Space Complexity:
O(1): The space complexity is constant aside from the output list, as no additional space proportional to the input size is required.*/

package medium.arrays;

import java.util.*;

public class A01ThreeNumberSum {
  public static List<List<Integer>> threeNumberSumBruteForce(int[] array, int targetSum) {
    List<List<Integer>> triplets = new ArrayList<>();
    Set<List<Integer>> seen = new HashSet<>(); // To avoid duplicate triplets

    // Iterate over all possible triplets using three nested loops
    for (int i = 0; i < array.length - 2; i++) {
      for (int j = i + 1; j < array.length - 1; j++) {
        for (int k = j + 1; k < array.length; k++) {
          if (array[i] + array[j] + array[k] == targetSum) {

            List<Integer> triplet = Arrays.asList(array[i], array[j], array[k]);
            Collections.sort(triplet); // Sort to handle the triplet order
            if (!seen.contains(triplet)) {
              seen.add(triplet);
              triplets.add(triplet);
            }
          }
        }
      }
    }

    return triplets;
  }

  public static List<List<Integer>> threeNumberSum(int[] array, int targetSum) {
    // Sort the array to facilitate the two-pointer approach
    Arrays.sort(array);
    List<List<Integer>> triplets = new ArrayList<>();

    // Iterate through the array, using each number as the base
    for (int i = 0; i < array.length - 2; i++) {
      // Avoid duplicates by skipping the same element
      if (i > 0 && array[i] == array[i - 1]) continue;

      int left = i + 1;
      int right = array.length - 1;

      // Use two pointers to find the other two numbers
      while (left < right) {
        int sum = array[i] + array[left] + array[right];
        if (sum == targetSum) {
          triplets.add(Arrays.asList(array[i], array[left], array[right]));
          // Move both pointers and skip duplicates
          while (left < right && array[left] == array[left + 1]) left++;
          while (left < right && array[right] == array[right - 1]) right--;
          left++;
          right--;
        } else if (sum < targetSum) {
          left++;
        } else {
          right--;
        }
      }
    }

    return triplets;
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {12, 3, 1, 2, -6, 5, -8, 6};
    int targetSum = 0;

    // Find and print the triplets
    List<List<Integer>> result = threeNumberSum(array, targetSum);
    System.out.println(result); // Expected Output: [[-8, 2, 6], [-8, 3, 5], [-6, 1, 5]]
  }
}
