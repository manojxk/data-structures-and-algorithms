package cools.dp.longestcommonsubsequence;

public class A03PrintingLongestCommonSubsequence {

  // Recursive function to find LCS and print it

  /*  Time Complexity:
  O(2^n) due to the branching recursion at each step.
  Space Complexity:
  O(m + n) for the recursion stack.*/
  public static String lcsRecursive(String S1, String S2, int m, int n) {
    // Base case: if either string is empty, return an empty string
    if (m == 0 || n == 0) {
      return "";
    }

    // If last characters match, include this character and recurse for the remaining strings
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      return lcsRecursive(S1, S2, m - 1, n - 1) + S1.charAt(m - 1);
    } else {
      // Else, consider two cases: reducing S1 or reducing S2, and take the longer result
      String left = lcsRecursive(S1, S2, m - 1, n);
      String right = lcsRecursive(S1, S2, m, n - 1);
      return left.length() > right.length() ? left : right;
    }
  }

  // Memoization table
  static String[][] dp;

  // Recursive function with memoization to find LCS and print it
  public static String lcsMemoization(String S1, String S2, int m, int n) {
    // Base case: if either string is empty, return an empty string
    if (m == 0 || n == 0) {
      return "";
    }

    // If the subproblem has already been solved, return the result
    if (dp[m][n] != null) {
      return dp[m][n];
    }

    // If last characters match, include this character and recurse for the remaining strings
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      dp[m][n] = lcsMemoization(S1, S2, m - 1, n - 1) + S1.charAt(m - 1);
    } else {
      // Else, consider two cases: reducing S1 or reducing S2, and take the longer result
      String left = lcsMemoization(S1, S2, m - 1, n);
      String right = lcsMemoization(S1, S2, m, n - 1);
      dp[m][n] = left.length() > right.length() ? left : right;
    }

    return dp[m][n];
  }

  // Tabulation function to find and print LCS

  /*  Time Complexity:
  O(m * n) for filling the DP table and backtracking.
  Space Complexity:
  O(m * n) for the DP table*/
  public static String lcsTabulation(String S1, String S2) {
    int m = S1.length();
    int n = S2.length();

    // Create a DP table to store the lengths of LCS of substrings
    int[][] dp = new int[m + 1][n + 1];

    // Build the DP table
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // Backtrack from dp[m][n] to find the LCS
    StringBuilder lcs = new StringBuilder();
    int i = m, j = n;
    while (i > 0 && j > 0) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        // If characters match, include this character in the LCS
        lcs.append(S1.charAt(i - 1));
        i--;
        j--;
      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        // Move to the direction of the larger value
        i--;
      } else {
        j--;
      }
    }

    // The LCS is built in reverse order, so we reverse it before returning
    return lcs.reverse().toString();
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";
    int m = S1.length();
    int n = S2.length();

    // Initialize memoization table
    dp = new String[m + 1][n + 1];

    System.out.println("Memoization LCS: " + lcsMemoization(S1, S2, m, n)); // Output: ABDF
  }
}
