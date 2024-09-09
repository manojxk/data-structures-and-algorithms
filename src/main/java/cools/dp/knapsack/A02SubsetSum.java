package cools.dp.knapsack;

/*Given a set of non-negative integers and a value sum, the task is to check if there is a subset of the given set whose sum is equal to the given sum.

Examples:

Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 9
Output: True
Explanation: There is a subset (4, 5) with sum 9.

Input: set[] = {3, 34, 4, 12, 5, 2}, sum = 30
Output: False
Explanation: There is no subset that add up to 30.*/

public class A02SubsetSum {
  // Memoization table to store the results of subproblems
  static Boolean[][] dp;

  // Recursive function to check if there's a subset with the given sum
  /*  Time Complexity:
  O(2^n) because we are exploring all subsets.
  Space Complexity:
  O(n) due to the recursion stack depth.*/
  public static boolean isSubsetSumRecursive(int[] set, int n, int sum) {
    // Base case: if sum is 0, return true (empty subset)
    if (sum == 0) {
      return true;
    }

    // Base case: if no items are left and sum is not 0, return false
    if (n == 0 && sum != 0) {
      return false;
    }

    // If the current element is greater than the sum, ignore it
    if (set[n - 1] > sum) {
      return isSubsetSumRecursive(set, n - 1, sum);
    }

    // Otherwise, check if sum can be obtained by:
    // 1. Including the current element
    // 2. Excluding the current element
    return isSubsetSumRecursive(set, n - 1, sum)
        || isSubsetSumRecursive(set, n - 1, sum - set[n - 1]);
  }

  // Recursive function with memoization to check if there's a subset with the given sum

  /*  Time Complexity:
  O(n * sum) since each subproblem is solved only once.
  Space Complexity:
  O(n * sum) for the memoization table.*/

  public static boolean isSubsetSumMemoization(int[] set, int n, int sum) {
    // Base case: if sum is 0, return true
    if (sum == 0) {
      return true;
    }

    // Base case: if no items are left and sum is not 0, return false
    if (n == 0 && sum != 0) {
      return false;
    }

    // If the result is already computed, return it
    if (dp[n][sum] != null) {
      return dp[n][sum];
    }

    // If the current element is greater than the sum, ignore it
    if (set[n - 1] > sum) {
      dp[n][sum] = isSubsetSumMemoization(set, n - 1, sum);
    } else {
      // Store the result of including or excluding the current element
      dp[n][sum] =
          isSubsetSumMemoization(set, n - 1, sum)
              || isSubsetSumMemoization(set, n - 1, sum - set[n - 1]);
    }

    return dp[n][sum];
  }

  /*  Time Complexity:
  O(n * sum) due to the table filling.
  Space Complexity:
  O(n * sum) for the DP table.*/
  // Tabulation (bottom-up) function to check if there's a subset with the given sum
  public static boolean isSubsetSumTabulation(int[] set, int sum) {
    int n = set.length;
    // Create a DP table where dp[i][j] means whether a subset of first i elements has sum j
    boolean[][] dp = new boolean[n + 1][sum + 1];

    // If sum is 0, then answer is true (empty subset)
    for (int i = 0; i <= n; i++) {
      dp[i][0] = true;
    }

    // Fill the subset table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= sum; j++) {
        if (set[i - 1] > j) {
          dp[i][j] = dp[i - 1][j]; // Exclude the element if it's greater than the sum
        } else {
          dp[i][j] = dp[i - 1][j] || dp[i - 1][j - set[i - 1]]; // Include or exclude
        }
      }
    }

    // The result is in dp[n][sum], which tells if a subset with sum exists
    return dp[n][sum];
  }

  public static void main(String[] args) {
    int[] set = {3, 34, 4, 12, 5, 2};
    int sum = 9;
    int n = set.length;

    // Initialize the memoization table with null values
    dp = new Boolean[n + 1][sum + 1];

    System.out.println("Memoization result: " + isSubsetSumMemoization(set, n, sum));
  }
}
