package cools.dp.knapsack;

public class A04CountofSubsetsSumWithGivenSum {

  // Recursive function to count the subsets with the given sum
  /*  Time Complexity:
  O(2^n) because each element can either be included or excluded.
  Space Complexity:
  O(n) due to the recursion stack depth.*/
  public static int countSubsetsRecursive(int[] arr, int n, int sum) {
    // Base case: If sum becomes 0, we've found a valid subset
    if (sum == 0) {
      return 1;
    }

    // Base case: If no items are left and sum is not 0, no valid subset
    if (n == 0) {
      return 0;
    }

    // If the current element is greater than the sum, ignore it
    if (arr[n - 1] > sum) {
      return countSubsetsRecursive(arr, n - 1, sum);
    }

    // Otherwise, count the subsets by including or excluding the current element
    return countSubsetsRecursive(arr, n - 1, sum)
        + countSubsetsRecursive(arr, n - 1, sum - arr[n - 1]);
  }

  // Memoization table
  static Integer[][] dp;

  // Recursive function with memoization to count subsets with the given sum
  /*  Time Complexity:
  O(n * sum) due to memoization.
  Space Complexity:
  O(n * sum) for the memoization table.*/
  public static int countSubsetsMemoization(int[] arr, int n, int sum) {
    // Base case: If sum becomes 0, we've found a valid subset
    if (sum == 0) {
      return 1;
    }

    // Base case: If no items are left and sum is not 0, no valid subset
    if (n == 0) {
      return 0;
    }

    // If subproblem is already solved, return the cached result
    if (dp[n][sum] != null) {
      return dp[n][sum];
    }

    // If the current element is greater than the sum, ignore it
    if (arr[n - 1] > sum) {
      dp[n][sum] = countSubsetsMemoization(arr, n - 1, sum);
    } else {
      // Store the result of including or excluding the current element
      dp[n][sum] =
          countSubsetsMemoization(arr, n - 1, sum)
              + countSubsetsMemoization(arr, n - 1, sum - arr[n - 1]);
    }

    return dp[n][sum];
  }

  /*  Time Complexity:
  O(n * sum) for filling the DP table.
  Space Complexity:
  O(n * sum) for the DP table.*/

  // Tabulation (bottom-up DP) function to count subsets with the given sum
  public static int countSubsetsTabulation(int[] arr, int sum) {
    int n = arr.length;

    // Create a DP table where dp[i][j] stores the number of subsets of the first i elements with
    // sum j
    int[][] dp = new int[n + 1][sum + 1];

    // If sum is 0, there's exactly one subset (the empty subset) for any number of items
    for (int i = 0; i <= n; i++) {
      dp[i][0] = 1;
    }

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= sum; j++) {
        // If the current element is greater than the sum, exclude it
        if (arr[i - 1] > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          // Include or exclude the current element
          dp[i][j] = dp[i - 1][j] + dp[i - 1][j - arr[i - 1]];
        }
      }
    }

    // The result is in dp[n][sum], which stores the number of subsets with sum = sum
    return dp[n][sum];
  }

  public static void main(String[] args) {
    int[] arr = {1, 2, 3, 3};
    int X = 6;
    int n = arr.length;

    // Initialize memoization table
    dp = new Integer[n + 1][X + 1];

    System.out.println("Memoization result: " + countSubsetsMemoization(arr, n, X)); // Output: 3
  }
}
