package cools.dp.longestcommonsubsequence;

/*Given a string, find the length of the longest repeating subsequence, such that the two subsequences don’t have same string character at the same position, i.e. any ith character in the two subsequences shouldn’t have the same index in the original string*/

public class A09LongestRepeatingSubsequence {
  /*Approach:
  The Longest Repeating Subsequence (LRS) problem can be reduced to the Longest Common Subsequence (LCS) problem where we compare the string with itself, but with the constraint that no two characters from the subsequences are from the same index.

      Steps:
    Compare the string with itself.
    Ensure that no two characters are from the same index.
    Use dynamic programming (either memoization or tabulation) to compute the solution.*/

  // Recursive function to find the length of LRS
  public static int lrsRecursive(String S, int m, int n) {
    // Base case: if either string is empty, return 0
    if (m == 0 || n == 0) {
      return 0;
    }

    // If characters match and are not at the same index, include the character
    if (S.charAt(m - 1) == S.charAt(n - 1) && m != n) {
      return 1 + lrsRecursive(S, m - 1, n - 1);
    } else {
      // Else, find the maximum by skipping one character from either string
      return Math.max(lrsRecursive(S, m - 1, n), lrsRecursive(S, m, n - 1));
    }
  }

  // Tabulation function to find the length of LRS
  public static int lrsTabulation(String S) {
    int n = S.length();

    // Create a DP table to store lengths of LRS
    int[][] dp = new int[n + 1][n + 1];

    // Build the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        // If characters match and are not at the same index
        if (S.charAt(i - 1) == S.charAt(j - 1) && i != j) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          // Else, find the maximum by skipping one character from either string
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // The length of the LRS is stored in dp[n][n]
    return dp[n][n];
  }

  public static void main(String[] args) {
    String S = "AABEBCDD";
    int n = S.length();

    System.out.println("Recursive LRS length: " + lrsRecursive(S, n, n)); // Output: 3
  }
}
