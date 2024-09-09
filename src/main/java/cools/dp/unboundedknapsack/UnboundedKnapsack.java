package cools.dp.unboundedknapsack;

import java.util.Arrays;

/*Problem Statement: Unbounded Knapsack (Repetition of Items Allowed)
You are given a knapsack with a maximum capacity W and a set of n items. Each item has a value val[i] and a weight wt[i]. Your task is to determine the maximum value that can be achieved by filling the knapsack with these items. The difference between this problem and the classic 0/1 Knapsack problem is that in this case, you can use unlimited instances of each item, meaning you can include any item multiple times.

        Input:
W: An integer representing the maximum capacity of the knapsack.
        val[]: An array of size n, where val[i] represents the value of the i-th item.
wt[]: An array of size n, where wt[i] represents the weight of the i-th item.
n: The number of items.
Output:
An integer representing the maximum value that can be obtained by filling the knapsack with unlimited repetitions of the items.
Constraints:
        1 ≤ n ≤ 1000
        1 ≤ W ≤ 1000
        1 ≤ val[i], wt[i] ≤ 1000*/
public class UnboundedKnapsack {

  // Recursive function to solve the unbounded knapsack problem
  /*  Time Complexity:
  O(2^n) (exponential) due to branching recursion.
  Space Complexity:
  O(n) for the recursion stack.*/
  public static int unboundedKnapsackRecursive(int W, int[] wt, int[] val, int n) {
    // Base case: if weight becomes 0 or no items left, return 0
    if (W == 0 || n == 0) {
      return 0;
    }

    // If the current item's weight exceeds the knapsack capacity, ignore it
    if (wt[n - 1] > W) {
      return unboundedKnapsackRecursive(W, wt, val, n - 1);
    }

    // Else, return the maximum of:
    // 1. Not including the item (move to the next item)
    // 2. Including the item and allowing repetition (stay on the same item)
    else {
      return Math.max(
          unboundedKnapsackRecursive(W, wt, val, n - 1),
          val[n - 1] + unboundedKnapsackRecursive(W - wt[n - 1], wt, val, n));
    }
  }

  // Memoization table
  static int[][] dp;

  // Recursive function with memoization to solve the unbounded knapsack problem
  /*  Time Complexity:
  O(n * W), where n is the number of items and W is the knapsack capacity.
  Space Complexity:
  O(n * W) for the memoization table.*/
  public static int unboundedKnapsackMemoization(int W, int[] wt, int[] val, int n) {
    // Base case: if weight becomes 0 or no items left, return 0
    if (W == 0 || n == 0) {
      return 0;
    }

    // If the result is already computed, return it
    if (dp[n][W] != -1) {
      return dp[n][W];
    }

    // If the current item's weight exceeds the knapsack capacity, ignore it
    if (wt[n - 1] > W) {
      dp[n][W] = unboundedKnapsackMemoization(W, wt, val, n - 1);
    } else {
      // Else, return the maximum of not including or including the item (allow repetition)
      dp[n][W] =
          Math.max(
              unboundedKnapsackMemoization(W, wt, val, n - 1),
              val[n - 1] + unboundedKnapsackMemoization(W - wt[n - 1], wt, val, n));
    }

    return dp[n][W];
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
          dp[i][w] = Math.max(dp[i - 1][w], profits[i - 1] + dp[i][w - weights[i - 1]]);
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
    int W = 8;
    int[] val = {10, 40, 50, 70};
    int[] wt = {1, 3, 4, 5};
    int n = val.length;

    // Initialize the memoization table with -1
    dp = new int[n + 1][W + 1];
    for (int[] row : dp) {
      Arrays.fill(row, -1);
    }

    System.out.println(
        "Memoization result: " + unboundedKnapsackMemoization(W, wt, val, n)); // Output: 110

    System.out.println("Tabulation result : " + knapsackTabulation(wt, val, W));
  }
}
