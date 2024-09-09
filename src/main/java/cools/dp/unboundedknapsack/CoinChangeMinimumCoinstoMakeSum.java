package cools.dp.unboundedknapsack;

import java.util.Arrays;

public class CoinChangeMinimumCoinstoMakeSum {

  // Tabulation function to find the minimum number of coins to make the sum
  public static int minCoinsTabulation(int[] coins, int sum) {
    // Initialize the dp array
    int[] dp = new int[sum + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0; // Base case: No coins needed for sum 0

    // Fill the dp table
    for (int i = 1; i <= sum; i++) {
      for (int coin : coins) {
        if (coin <= i && dp[i - coin] != Integer.MAX_VALUE) {
          dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        }
      }
    }

    // If dp[sum] is still Integer.MAX_VALUE, it means it's impossible to make the sum
    return dp[sum] == Integer.MAX_VALUE ? -1 : dp[sum];
  }

  public static void main(String[] args) {
    int[] coins = {25, 10, 5};
    int sum = 30;

    System.out.println("Tabulation result: " + minCoinsTabulation(coins, sum)); // Output: 2
  }
}
