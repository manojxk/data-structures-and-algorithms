/*
 * Problem Statement:
 *
 * A tandem bicycle is a bicycle operated by two people: person A and person B.
 * Both people pedal the bicycle, but the person that pedals faster dictates
 * the speed of the bicycle. If person A pedals at a speed of 5, and person B
 * pedals at a speed of 4, the tandem bicycle moves at a speed of 5.
 *
 * You are given two lists of positive integers: one containing the speeds of
 * riders wearing red shirts and the other containing the speeds of riders
 * wearing blue shirts. Both lists have the same length, meaning that there
 * are as many red-shirt riders as there are blue-shirt riders.
 *
 * Your goal is to pair every rider wearing a red shirt with a rider wearing
 * a blue shirt to operate a tandem bicycle. You need to write a function that
 * returns either the maximum possible total speed or the minimum possible
 * total speed of all of the tandem bicycles being ridden, based on an input
 * parameter, `fastest`. If `fastest = true`, the function should return the
 * maximum possible total speed; otherwise, it should return the minimum
 * possible total speed.
 *
 * "Total speed" is defined as the sum of the speeds of all the tandem bicycles
 * being ridden. For example, if there are 4 riders (2 red-shirt riders and
 * 2 blue-shirt riders) with speeds of [1, 3, 4, 5], and they are paired on
 * tandem bicycles as follows: [1, 4], [5, 3], then the total speed of these
 * tandem bicycles is 4 + 5 = 9.
 *
 * Example:
 * Input:
 * redShirtSpeeds = [5, 5, 3, 9, 2]
 * blueShirtSpeeds = [3, 6, 7, 2, 1]
 * fastest = true
 *
 * Output:
 * 32
 */

/*Brute Force Solution
Approach:
The brute force approach involves generating all possible pairings of red-shirt and blue-shirt riders, and then calculating the total speed for both the maximum and minimum scenarios. This approach, however, is impractical due to its combinatorial complexity.

Time Complexity:
O(n!): The time complexity is factorial due to the need to generate all possible pairings.
Space Complexity:
O(n!): The space complexity is also factorial due to the storage required for all pairings.
Note: The brute force approach is not practical and is typically avoided due to its inefficiency. Instead, we directly discuss the optimized solution.*/
package easy.greedyalgorithm;

import java.util.Arrays;

public class TandemBicycle {

  // Function to calculate the total speed based on the pairing strategy
  public static int tandemBicycle(int[] redShirtSpeeds, int[] blueShirtSpeeds, boolean fastest) {
    // Sort both arrays to facilitate pairing
    Arrays.sort(redShirtSpeeds);
    Arrays.sort(blueShirtSpeeds);

    int totalSpeed = 0;

    // Calculate total speed based on whether we're looking for fastest or slowest
    for (int i = 0; i < redShirtSpeeds.length; i++) {
      if (fastest) {
        // Pair fastest from one group with slowest from the other
        int j = redShirtSpeeds.length - i - 1;
        totalSpeed += Math.max(redShirtSpeeds[i], blueShirtSpeeds[j]);
      } else {
        // Pair the fastest from both groups together
        totalSpeed += Math.max(redShirtSpeeds[i], blueShirtSpeeds[i]);
      }
    }

    return totalSpeed;
  }

  public static void main(String[] args) {
    int[] redShirtSpeeds = {5, 5, 3, 9, 2};
    int[] blueShirtSpeeds = {3, 6, 7, 2, 1};
    boolean fastest = true;

    int result = tandemBicycle(redShirtSpeeds, blueShirtSpeeds, fastest);
    System.out.println(result); // Output: 32
  }
}
/*
Time Complexity:
O(n log n): Sorting the arrays dominates the time complexity.
Space Complexity:
O(1): The algorithm requires a constant amount of extra space beyond the input storage.*/
