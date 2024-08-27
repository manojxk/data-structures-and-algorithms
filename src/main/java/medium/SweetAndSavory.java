/*
 * Problem Statement:
 * You are hosting an event at a food festival and need to find the best possible pairing of two dishes
 * that complement each other's flavor profile. Each dish has a flavor profile represented by an integer:
 * - A negative integer indicates a sweet dish.
 * - A positive integer indicates a savory dish.
 * The absolute value of the integer represents the intensity of the flavor.
 *
 * You are given an array of integers representing the flavor profiles of dishes and a target combined flavor profile.
 * The goal is to find a pairing of one sweet and one savory dish such that their combined flavor profile
 * is closest to the target without exceeding it. The returned array should be sorted such that
 * the sweet dish comes first.
 *
 * Constraints:
 * - The pairing must include one sweet and one savory dish.
 * - The combined flavor profile should not be more savory than the target.
 * - If there is no valid pairing, return [0, 0].
 * - Assume at most one best solution exists.
 *
 * Example:
 * Input: dishes = [-3, -5, 1, 7], target = 8
 * Output: [-3, 7]
 * // The combined profile of -3 + 7 = 4 is closest without going over.
 */

package medium;

import java.util.Arrays;

public class SweetAndSavory {

  // Optimized Solution using Sorting and Two-Pointer Technique
  public static int[] findBestPairingOptimized(int[] dishes, int target) {
    int[] sweetDishes = Arrays.stream(dishes).filter(d -> d < 0).toArray();
    int[] savoryDishes = Arrays.stream(dishes).filter(d -> d > 0).toArray();

    Arrays.sort(sweetDishes);
    Arrays.sort(savoryDishes);

    int bestSum = Integer.MIN_VALUE;
    int[] bestPair = {0, 0};

    int i = 0;
    int j = savoryDishes.length - 1;

    while (i < sweetDishes.length && j >= 0) {
      int sum = sweetDishes[i] + savoryDishes[j];
      if (sum <= target && sum > bestSum) {
        bestSum = sum;
        bestPair[0] = sweetDishes[i];
        bestPair[1] = savoryDishes[j];
      }
      if (sum > target) {
        j--;
      } else {
        i++;
      }
    }

    return bestPair;
  }

  public static void main(String[] args) {
    int[] dishes1 = {-3, -5, 1, 7};
    int target1 = 8;
    int[] result1 = findBestPairingOptimized(dishes1, target1);
    System.out.println("[" + result1[0] + ", " + result1[1] + "]"); // Output: [-3, 7]

    int[] dishes2 = {3, 5, 7, 2, 6, 8, 1};
    int target2 = 10;
    int[] result2 = findBestPairingOptimized(dishes2, target2);
    System.out.println("[" + result2[0] + ", " + result2[1] + "]"); // Output: [0, 0]
  }
}
/*
Optimized Solution: Sorting and Two-Pointer Technique
Approach:
Sort the Arrays: Separate sweet and savory dishes into two lists and sort them.
        Two-Pointer Technique: Use a two-pointer technique to find the pair with the combined flavor profile closest to the target without exceeding it.
Start one pointer at the beginning of the sorted sweet list and the other at the end of the sorted savory list.
Adjust the pointers to move closer to the target.
Time Complexity:
O(n log n): Sorting the two lists takes O(n log n) time, and finding the best pair takes O(n) time.
Space Complexity:
O(n): Additional space is used to store the two separate lists.*/
