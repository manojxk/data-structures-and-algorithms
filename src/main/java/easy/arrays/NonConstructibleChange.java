/*
 * Problem Statement:
 *
 * Given an array of positive integers representing the values of coins in your possession,
 * write a function that returns the minimum amount of change (the minimum sum of money) that
 * you cannot create using the given coins. The given coins can have any positive integer value
 * and aren't necessarily unique (i.e., you can have multiple coins of the same value).
 *
 * Example:
 * Input: coins = [5, 7, 1, 1, 2, 3, 22]
 * Output: 20
 *
 * Explanation:
 * The smallest amount of change that cannot be created using these coins is 20.
 *
 * Edge Case:
 * If no coins are given, the smallest amount of change that cannot be created is 1.
 */

/*Brute Force Solution
Approach:
A brute force solution would involve generating all possible sums that can be created using the given coins and
then finding the smallest positive integer that cannot be formed. However, this approach would be highly inefficient
and infeasible for large inputs due to the exponential number of combinations.

Time Complexity:
O(2^n): The time complexity is exponential, where n is the number of coins. This is because all possible combinations
of coins need to be checked.
Space Complexity:
O(n): The space complexity would be linear due to the recursive stack or the space used to store combinations.
Note: The brute force solution is not implemented due to its impracticality.*/

package easy.arrays;

import java.util.Arrays;

public class NonConstructibleChange {
  /**
   * This function calculates the minimum amount of change that cannot be created by summing the
   * elements of a given array.
   *
   * @param coins An array of integers representing the values of the coins.
   * @return The minimum sum of money that cannot be created by summing the elements of the array.
   */

  // Optimized Solution using Greedy Approach
  public static int minimumChange(int[] coins) {
    // Sort the array in ascending order.
    Arrays.sort(coins);

    // Initialize currentChangeCreated to 0.
    int currentChangeCreated = 0;

    // Iterate over the array.
    for (int coin : coins) {
      // If the current coin is greater than the currentChangeCreated plus 1,
      // we know we cannot create the amount of currentChangeCreated plus 1,
      // and we return it.
      if (coin > currentChangeCreated + 1) {
        return currentChangeCreated + 1;
      }

      // Otherwise, we add the value of the coin to currentChangeCreated.
      currentChangeCreated += coin;
    }

    // If we've gone through all the coins and we can still create an amount of change
    // equal to currentChangeCreated, then the smallest amount of change we can't create
    // is currentChangeCreated plus 1.
    return currentChangeCreated + 1;
  }

  public static void main(String[] args) {
    int[] coins = {5, 7, 1, 1, 2, 3, 22};
    System.out.println(minimumChange(coins)); // Output: 20
  }
}

/*
Brute Force Solution: O(2^n) time | O(n) space (not implemented due to inefficiency).
Optimized Solution: O(n log n) time | O(1) space.*/
