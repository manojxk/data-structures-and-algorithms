package cools.dp.unboundedknapsack;

public class CoinChangeCountWaystoMakeSum {

  public static int countSubsetsRecursive(int[] coins, int n, int sum) {
    // Base case: If sum becomes 0, there is one solution (do not pick any coins)
    if (sum == 0) {
      return 1;
    }

    // Base case: If no items are left and sum is not 0, there are no solutions
    if (n == 0) {
      return 0;
    }

    // If the current coin is greater than the sum, ignore it
    if (coins[n - 1] > sum) {
      return countSubsetsRecursive(coins, n - 1, sum);
    }

    // Two choices:
    // 1. Exclude the current coin and move to the next coin
    // 2. Include the current coin and stay at the current coin (as we can use it unlimited times)
    return countSubsetsRecursive(coins, n - 1, sum)
        + countSubsetsRecursive(coins, n, sum - coins[n - 1]);
  }

  static Integer[][] dp;

  public static int countSubsetsMemoization(int[] coins, int n, int sum) {

    if (sum == 0) {
      return 1;
    }

    if (n == 0) {
      return 0;
    }

    // If subproblem is already solved, return the cached result
    if (dp[n][sum] != null) {
      return dp[n][sum];
    }

    // If the current element is greater than the sum, ignore it
    if (coins[n - 1] > sum) {
      dp[n][sum] = countSubsetsMemoization(coins, n - 1, sum);
    } else {
      // Store the result of including or excluding the current element
      dp[n][sum] =
          countSubsetsMemoization(coins, n - 1, sum)
              + countSubsetsMemoization(coins, n, sum - coins[n - 1]);
    }

    return dp[n][sum];
  }

  public static int countSubsetsTabulation(int[] coins, int sum) {
    int n = coins.length;

    int[][] dp = new int[n + 1][sum + 1];

    for (int i = 0; i <= n; i++) {
      dp[i][0] = 1;
    }

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= sum; j++) {
        // If the current element is greater than the sum, exclude it
        if (coins[i - 1] > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          // Include or exclude the current element
          dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
        }
      }
    }

    // The result is in dp[n][sum], which stores the number of subsets with sum = sum
    return dp[n][sum];
  }

  public static void main(String[] args) {
    int[] coins = {5, 2, 3,6};
    int X = 10;
    int n = coins.length;

    // Initialize memoization table
    dp = new Integer[n + 1][X + 1];

    System.out.println("Recursive result: " + countSubsetsRecursive(coins, n, X)); // Output: 3
    System.out.println("Memoization result: " + countSubsetsMemoization(coins, n, X));
    System.out.println("Tabulation result: " + countSubsetsTabulation(coins, X));
  }
}
