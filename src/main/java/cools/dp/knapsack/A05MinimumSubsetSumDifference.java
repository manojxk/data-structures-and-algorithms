/*Given a set of integers, the task is to divide it into two sets S1 and S2 such that the absolute difference between their sums is minimum.
If there is a set S with n elements, then if we assume Subset1 has m elements, Subset2 must have n-m elements and the value of abs(sum(Subset1) – sum(Subset2)) should be minimum.

Example:
Input: arr[] = {1, 6, 11, 5}
Output: 1
Explanation: S1 = {1, 5, 6}, sum = 12, S2 = {11}, sum = 11, Absolute Difference (12 – 11) = 1

Input: arr[] = {1, 5, 11, 5}
Output: 0
Explanation: S1 = {1, 5, 5}, sum = 11, S2 = {11}, sum = 11, Absolute Difference (11 – 11) = 0*/

/*
Approach:
To minimize the absolute difference between the sums of two subsets, we can solve the problem using dynamic programming. Here's the step-by-step breakdown of the approach:

Subset Sum Approach: First, we compute the total sum of the array. The objective is to find a subset whose sum is as close as possible to half of the total sum, as this would minimize the difference between the two subsets.

Dynamic Programming: Use a boolean DP array dp[] where dp[i] indicates whether a subset with sum i is possible with the given array elements.

Minimization: Once the DP table is filled, find the maximum j such that dp[j] is true and j is less than or equal to half of the total sum. The minimum difference will be total_sum - 2 * j.*/

package cools.dp.knapsack;

public class A05MinimumSubsetSumDifference {
  public static int isSubsetSumTabulation(int[] set, int sum) {
    int n = set.length;

    // Compute total sum of the array
    int totalSum = 0;
    for (int num : set) {
      totalSum += num;
    }

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

    // Find the largest j such that dp[n][j] is true and j <= totalSum / 2
    int minDifference = Integer.MAX_VALUE;
    for (int j = totalSum / 2; j >= 0; j--) {
      if (dp[n][j]) {
        minDifference = totalSum - 2 * j;
        break;
      }
    }
    return minDifference;
  }
}
