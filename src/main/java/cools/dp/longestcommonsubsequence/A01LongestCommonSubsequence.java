/*Given two strings, S1 and S2, the task is to find the length of the Longest Common Subsequence. If there is no common subsequence, return 0. A subsequence is a string generated from the original string by deleting 0 or more characters and without changing the relative order of the remaining characters. For example , subsequences of “ABC” are “”, “A”, “B”, “C”, “AB”, “AC”, “BC” and “ABC”. In general a string of length n has 2n subsequences.*/

package cools.dp.longestcommonsubsequence;

import java.util.Arrays;

public class A01LongestCommonSubsequence {

  // Recursive function to find the length of LCS
  /*  Time Complexity:
  O(2^n) because at each step, we reduce either S1 or S2, leading to exponential combinations.
  Space Complexity:
  O(m + n) due to the recursion stack.*/
  public static int lcsRecursive(String S1, String S2, int m, int n) {
    // Base case: If either string is empty, LCS length is 0
    if (m == 0 || n == 0) {
      return 0;
    }

    // If the last characters match, reduce both strings
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      return 1 + lcsRecursive(S1, S2, m - 1, n - 1);
    } else {
      // Else, reduce one string at a time and take the maximum
      return Math.max(lcsRecursive(S1, S2, m - 1, n), lcsRecursive(S1, S2, m, n - 1));
    }
  }

  // Memoization table
  static int[][] dp;

  // Recursive function with memoization to find the length of LCS

  /*    Time Complexity:
  O(m * n), where m and n are the lengths of the strings S1 and S2. This is because we only solve each subproblem once.
  Space Complexity:
  O(m * n) for the memoization table.*/
  public static int lcsMemoization(String S1, String S2, int m, int n) {
    // Base case: If either string is empty, LCS length is 0
    if (m == 0 || n == 0) {
      return 0;
    }

    // If already computed, return the result
    if (dp[m][n] != -1) {
      return dp[m][n];
    }

    // If the last characters match, reduce both strings
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      dp[m][n] = 1 + lcsMemoization(S1, S2, m - 1, n - 1);
    } else {
      // Else, reduce one string at a time and take the maximum
      dp[m][n] = Math.max(lcsMemoization(S1, S2, m - 1, n), lcsMemoization(S1, S2, m, n - 1));
    }

    return dp[m][n];
  }

  // Tabulation (Bottom-up DP) function to find the length of LCS
  public static int lcsTabulation(String S1, String S2) {
    int m = S1.length();
    int n = S2.length();

    // Create a DP table to store results of subproblems
    int[][] dp = new int[m + 1][n + 1];

    // Build the DP table
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        //first row and first column
        if (i == 0 || j == 0) {
          dp[i][j] = 0;
          continue;
        }
        // If the characters match, take diagonal value + 1
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          // Else, take the maximum of left and top
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // The value in dp[m][n] contains the length of the LCS
    return dp[m][n];
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";
    int m = S1.length();
    int n = S2.length();

    // Initialize memoization table
    dp = new int[m + 1][n + 1];
    for (int[] row : dp) {
      Arrays.fill(row, -1);
    }

    System.out.println("Memoization LCS length: " + lcsMemoization(S1, S2, m, n)); // Output: 4
  }
}
