package cools.dp.knapsack;

import java.util.Arrays;

/*
 * Time Complexity: O(2^n) - In the worst case, we have 2 choices (include or exclude) for every item,
 * leading to a binary tree with 2^n leaves.
 *
 * Space Complexity: O(n) - The recursion stack will at most go n levels deep, where n is the number of items.
 */
public class A01KnapsackProblem {
  // Memoization table to store results of subproblems
  static int[][] dp;

  // Recursive function to solve the 0/1 knapsack problem
  public static int knapsackRecursive(int[] weights, int[] profits, int capacity, int n) {
    // Base case: if no items are left or the capacity is 0
    if (n == 0 || capacity == 0) {
      return 0;
    }

    // If the weight of the nth item is more than the knapsack's capacity, it cannot be included
    if (weights[n - 1] > capacity) {
      return knapsackRecursive(weights, profits, capacity, n - 1);
    }

    // Else, we have two choices:
    // 1. Exclude the nth item and solve for the rest
    // 2. Include the nth item, subtract its weight from the capacity and solve for the rest
    // We return the maximum of the two choices
    else {
      int includeItem =
          profits[n - 1] + knapsackRecursive(weights, profits, capacity - weights[n - 1], n - 1);
      int excludeItem = knapsackRecursive(weights, profits, capacity, n - 1);
      return Math.max(includeItem, excludeItem);
    }
  }

  // Recursive function with memoization to solve the 0/1 knapsack problem
  /*  Time Complexity
  The time complexity of this memoized solution is O(n * W), where:

  n is the number of items.
  W is the capacity of the knapsack.
  This is because each subproblem is solved at most once, and there are n * W subproblems.

  Space Complexity
  The space complexity is O(n * W) for the memoization table, plus O(n) for the recursion stack, giving a total of O(n * W).*/
  public static int knapsackMemo(int[] weights, int[] profits, int capacity, int n) {
    // Base case: if no items are left or the capacity is 0
    if (n == 0 || capacity == 0) {
      return 0;
    }

    // If this subproblem has already been solved, return the stored result
    if (dp[n][capacity] != -1) {
      return dp[n][capacity];
    }

    // If the weight of the nth item is more than the knapsack's capacity, it cannot be included
    if (weights[n - 1] > capacity) {
      dp[n][capacity] = knapsackMemo(weights, profits, capacity, n - 1);
    }
    // Else, we have two choices:
    // 1. Exclude the nth item and solve for the rest
    // 2. Include the nth item, subtract its weight from the capacity and solve for the rest
    else {
      int includeItem =
          profits[n - 1] + knapsackMemo(weights, profits, capacity - weights[n - 1], n - 1);
      int excludeItem = knapsackMemo(weights, profits, capacity, n - 1);
      dp[n][capacity] = Math.max(includeItem, excludeItem);
    }

    // Return the result
    return dp[n][capacity];
  }

  /*Time Complexity
  The time complexity of this tabulation approach is O(n * W), where:

  n is the number of items.
  W is the capacity of the knapsack.
  This is because we are filling a 2D table with n * W entries, and each entry takes constant time to compute.

          Space Complexity
  The space complexity is also O(n * W) because we are using a 2D table of size n+1 by W+1 to store the results.

  This tabulation approach is efficient and avoids the overhead of recursion or storing unnecessary subproblem results multiple times.*/
  public static int knapsackTabulation(int[] weights, int[] profits, int capacity) {
    int n = profits.length;

    // Create a 2D DP table where dp[i][j] represents the maximum profit for the first i items and
    // capacity j
    int[][] dp = new int[n + 1][capacity + 1];

    // Fill the DP table
    for (int i = 0; i <= n; i++) {
      for (int w = 0; w <= capacity; w++) {
        if (i == 0 || w == 0) {
          // Base case: no items or capacity 0 means 0 profit
          dp[i][w] = 0;
        } else if (weights[i - 1] <= w) {
          // If the current item can be included, take the maximum of:
          // 1. Profit excluding the item
          // 2. Profit including the item (add its profit and reduce capacity by its weight)
          dp[i][w] = Math.max(dp[i - 1][w], profits[i - 1] + dp[i - 1][w - weights[i - 1]]);
        } else {
          // If the current item's weight exceeds the capacity, exclude the item
          dp[i][w] = dp[i - 1][w];
        }
      }
    }

    // The answer is the value in dp[n][capacity], which represents the maximum profit for all items
    // and full capacity
    return dp[n][capacity];
  }

  public static void main(String[] args) {
    int[] weights = {1, 3, 4, 5}; // weights of items
    int[] profits = {1, 4, 5, 7}; // profits associated with items
    int capacity = 7; // maximum capacity of the knapsack

    int n = profits.length; // number of items

    // Initialize the memoization table with -1
    dp = new int[n + 1][capacity + 1];
    for (int[] row : dp) {
      Arrays.fill(row, -1);
    }

    // Call the memoized recursive function to get the maximum profit
    int maxProfit = knapsackMemo(weights, profits, capacity, n);

    // Output the result
    System.out.println("The maximum profit is: " + maxProfit);
  }
}
