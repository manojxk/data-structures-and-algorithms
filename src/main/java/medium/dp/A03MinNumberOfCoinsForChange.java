/*
 * Problem Statement:
 *
 * Given an array of positive integers representing coin denominations and a
 * single non-negative integer `n` representing a target amount of money,
 * write a function that returns the smallest number of coins needed to
 * make change for (to sum up to) that target amount using the given coin
 * denominations.
 *
 * Note that you have access to an unlimited amount of coins. In other words,
 * if the denominations are [1, 5, 10], you have access to an unlimited
 * amount of 1s, 5s, and 10s.
 *
 * If it's impossible to make change for the target amount, return -1.
 *
 * Example:
 *
 * n = 7
 * denoms = [1, 5, 10]
 *
 * Output:
 * 3 // 2x1 + 1x5
 */

/*
 * Brute Force Solution
 * Approach:
 * The brute force approach involves recursively exploring all possible combinations of coins that sum up to the target amount `n`.
 * This involves checking every denomination and seeing how many coins are needed if we include the current denomination.
 *
 * Time Complexity:
 * O(d^n): Where `d` is the number of denominations, and `n` is the target amount.
 * This is because each coin denomination has multiple choices at each step, leading to an exponential number of combinations.
 *
 * Space Complexity:
 * O(n): Due to the recursion stack.
 */

package medium.dp;

import java.util.Arrays;

public class A03MinNumberOfCoinsForChange {

  // Brute Force Solution: Recursive Approach
  public static int minNumberOfCoinsForChange(int n, int[] denoms) {
    int result = minCoinsHelper(n, denoms);
    return result == Integer.MAX_VALUE ? -1 : result;
  }

  // Helper method to recursively find the minimum number of coins
  private static int minCoinsHelper(int n, int[] denoms) {
    if (n == 0) return 0;
    if (n < 0) return Integer.MAX_VALUE;

    int minCoins = Integer.MAX_VALUE;
    for (int coin : denoms) {
      int res = minCoinsHelper(n - coin, denoms);
      if (res != Integer.MAX_VALUE) {
        minCoins = Math.min(minCoins, res + 1);
      }
    }
    return minCoins;
  }

  /*
   * Optimized Solution: Dynamic Programming
   * Approach:
   * The optimized approach uses dynamic programming to store the minimum number of coins required to achieve every amount from 0 to `n`.
   * We maintain an array `minCoins` where `minCoins[i]` represents the minimum number of coins required to make change for amount `i`.
   *
   * Time Complexity:
   * O(n * d): Where `n` is the target amount, and `d` is the number of denominations.
   *
   * Space Complexity:
   * O(n): For the DP array storing the minimum coins required for each amount up to `n`.
   */

  // Optimized Solution: Dynamic Programming Approach
  public static int minNumberOfCoinsForChangeDP(int n, int[] denoms) {
    int[] minCoins = new int[n + 1];
    Arrays.fill(minCoins, Integer.MAX_VALUE);
    minCoins[0] = 0;

    for (int denom : denoms) {
      for (int amount = denom; amount <= n; amount++) {
        if (minCoins[amount - denom] != Integer.MAX_VALUE) {
          minCoins[amount] = Math.min(minCoins[amount], minCoins[amount - denom] + 1);
        }
      }
    }

    return minCoins[n] == Integer.MAX_VALUE ? -1 : minCoins[n];
  }

  // Main function to test the Min Number of Coins for Change implementation
  public static void main(String[] args) {
    int[] denoms = {1, 5, 10};
    int n = 7;

    // Brute Force Solution Output
    System.out.println(minNumberOfCoinsForChange(n, denoms)); // Output: 3

    // Optimized Dynamic Programming Solution Output
    System.out.println(minNumberOfCoinsForChangeDP(n, denoms)); // Output: 3
  }
}

/*
 * Summary:
 *
 * Brute Force Solution (Recursive)
 * Time Complexity: O(d^n)
 * Space Complexity: O(n)
 *
 * Optimized Solution (Dynamic Programming)
 * Time Complexity: O(n * d) where `n` is the target amount and `d` is the number of denominations
 * Space Complexity: O(n) for storing the `minCoins` array
 */
