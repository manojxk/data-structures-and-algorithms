/*
 * Problem Statement:
 * You are given two positive integers representing the height of a staircase and the maximum number of steps that you can advance up the staircase at a time.
 * Write a function that returns the number of distinct ways you can climb the staircase.
 *
 * Conditions:
 * - You can take any number of steps between 1 and maxSteps (inclusive) at a time.
 * - The maximum number of steps (maxSteps) will always be less than or equal to the height of the staircase.
 *
 * Example:
 * Input: height = 4, maxSteps = 2
 * Output: 5
 *
 * Explanation:
 * You can climb the staircase in the following 5 distinct ways:
 * 1. 1 step, 1 step, 1 step, 1 step
 * 2. 1 step, 1 step, 2 steps
 * 3. 1 step, 2 steps, 1 step
 * 4. 2 steps, 1 step, 1 step
 * 5. 2 steps, 2 steps
 */
/*Brute Force Solution
Approach:
The brute force solution involves trying all possible combinations of steps that sum up to the height of the staircase. This can be achieved using a recursive approach.

Time Complexity:
O(maxSteps^height): The time complexity is exponential because at each step, you have up to maxSteps choices.
Space Complexity:
O(height): The space complexity is linear due to the recursive call stack.*/
package medium.recursion;

import java.util.HashMap;

public class StaircaseTraversal {

  // Brute Force Recursive Solution
  public static int countWaysBruteForce(int height, int maxSteps) {
    if (height == 0) return 1; // Base case: 1 way to stay at ground level
    int totalWays = 0;
    for (int i = 1; i <= maxSteps; i++) {
      if (height - i >= 0) {
        totalWays += countWaysBruteForce(height - i, maxSteps);
      }
    }
    return totalWays;
  }

  /*  Optimized Solution 1: Memoization
  Approach:
  To avoid recalculating results for the same subproblems, we can use memoization. We store the results of subproblems in an array and reuse them when needed.

  Time Complexity:
  O(height * maxSteps): We solve each subproblem only once, leading to a polynomial time complexity.
  Space Complexity:
  O(height): The space complexity is linear, required to store the results of subproblems.*/

  // Optimized Recursive Solution with Memoization
  public static int countWaysMemoization(int height, int maxSteps) {
    HashMap<Integer, Integer> memo = new HashMap<>();
    return countWaysHelper(height, maxSteps, memo);
  }

  private static int countWaysHelper(int height, int maxSteps, HashMap<Integer, Integer> memo) {
    if (height == 0) return 1; // Base case
    if (memo.containsKey(height)) return memo.get(height);

    int totalWays = 0;
    for (int i = 1; i <= maxSteps; i++) {
      if (height - i >= 0) {
        totalWays += countWaysHelper(height - i, maxSteps, memo);
      }
    }

    memo.put(height, totalWays);
    return totalWays;
  }

  /*  Optimized Solution 2: Dynamic Programming
  Approach:
  We can use dynamic programming to iteratively build the solution from the ground up. We define an array dp where dp[i] represents the number of ways to reach the i-th step.

  Time Complexity:
  O(height * maxSteps): We fill the dp array in a bottom-up manner.
  Space Complexity:
  O(height): The space complexity is linear, required to store the dp array.*/
  // Optimized Solution with Dynamic Programming
  public static int countWaysDP(int height, int maxSteps) {
    int[] dp = new int[height + 1];
    dp[0] = 1; // Base case: 1 way to stay at ground level

    for (int i = 1; i <= height; i++) {
      for (int j = 1; j <= maxSteps; j++) {
        if (i - j >= 0) {
          dp[i] += dp[i - j];
        }
      }
    }

    return dp[height];
  }

  public static void main(String[] args) {
    int height = 4;
    int maxSteps = 2;
    System.out.println(countWaysBruteForce(height, maxSteps)); // Output: 5
  }
}
