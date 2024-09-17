/*
 * Problem Statement:
 * Given an array of distinct positive integers representing coin denominations
 * and a single non-negative integer `n` representing a target amount of money,
 * write a function that returns the number of ways to make change for that target amount using the given coin denominations.
 *
 * Note that an unlimited amount of coins is at your disposal.
 *
 * Example:
 *
 * n = 6
 * denoms = [1, 5]
 *
 * Output:
 * 2  // The two ways are: [1x1 + 1x5] and [6x1]
 */

/*
 * Brute Force Solution
 *
 * Approach:
 * The brute force solution is to use a recursive approach to explore all possible ways to reach the target amount `n` using the given denominations.
 * At each step, the algorithm considers either including a coin in the current combination or skipping it.
 *
 * Time Complexity:
 * O(2^n): The time complexity is exponential due to the recursive exploration of all possibilities.
 *
 * Space Complexity:
 * O(n): The space complexity is determined by the maximum depth of the recursion stack.
 */

package medium.dp;

public class A02NumberOfWaysToMakeChange {

  // Brute Force Solution: Recursive Approach
  public static int numberOfWaysToMakeChange(int n, int[] denoms) {
    return countWays(n, denoms, 0);
  }

  // Helper method to recursively count the number of ways to make change
  private static int countWays(int amount, int[] denoms, int index) {
    // Base case: when the amount is zero, there's one way to make change (by using no coins)
    if (amount == 0) return 1;

    // Base case: if amount becomes negative or we've used all denominations, return 0
    if (amount < 0 || index >= denoms.length) return 0;

    // Recursive case: include the current denomination and exclude it
    int include = countWays(amount - denoms[index], denoms, index);
    int exclude = countWays(amount, denoms, index + 1);

    return include + exclude;
  }

  /*
   * Optimized Solution (Dynamic Programming)
   *
   * Approach:
   * The optimized solution uses dynamic programming to build up the number of ways to make change for all amounts from 0 to `n`.
   * The idea is to use a `ways` array where `ways[i]` represents the number of ways to make change for amount `i`.
   * We iterate through each denomination and update the `ways` array.
   *
   * Time Complexity:
   * O(n * m): Where `n` is the target amount and `m` is the number of denominations.
   * This is because we are iterating through each denomination for each amount up to `n`.
   *
   * Space Complexity:
   * O(n): The space complexity is determined by the `ways` array used to store the number of ways to make change for each amount.
   */

  // Optimized Solution: Dynamic Programming Approach
  public static int numberOfWaysToMakeChangeDP(int n, int[] denoms) {
    int[] ways = new int[n + 1];
    ways[0] = 1; // There is one way to make change for 0 amount

    for (int denom : denoms) {
      for (int amount = denom; amount <= n; amount++) {
        ways[amount] += ways[amount - denom];
      }
    }

    return ways[n];
  }

  // Main function to test the Number Of Ways To Make Change implementation
  public static void main(String[] args) {
    int n = 6;
    int[] denoms = {1, 5};

    // Brute Force Solution Output
    System.out.println(numberOfWaysToMakeChange(n, denoms)); // Output: 2

    // Optimized Dynamic Programming Solution Output
    System.out.println(numberOfWaysToMakeChangeDP(n, denoms)); // Output: 2
  }
}

/*
 * Summary:
 *
 * Brute Force Solution (Recursive)
 * Time Complexity: O(2^n)
 * Space Complexity: O(n)
 *
 * Optimized Solution (Dynamic Programming)
 * Time Complexity: O(n * m) where `n` is the target amount and `m` is the number of denominations
 * Space Complexity: O(n) for storing the `ways` array
 */
