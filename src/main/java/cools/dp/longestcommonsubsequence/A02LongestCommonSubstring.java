package cools.dp.longestcommonsubsequence;

public class A02LongestCommonSubstring {

  // Recursive function to find the length of the longest common substring
  /*  Time Complexity:
  O(2^(m + n)), where m and n are the lengths of s1 and s2. This is because each step explores two possibilities: either matching characters or skipping them.
  Space Complexity:
  O(m + n) due to the recursion stack.*/
  public static int lcsRecursive(String s1, String s2, int m, int n, int count) {
    // Base case: if either string is empty, return the current count
    if (m == 0 || n == 0) {
      return count;
    }

    // If the last characters match, increment the count and call the function for the remaining
    // strings
    if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
      count = lcsRecursive(s1, s2, m - 1, n - 1, count + 1);
    }

    // Return the maximum of continuing with a matching substring or starting fresh from the
    // remaining characters
    return Math.max(
        count, Math.max(lcsRecursive(s1, s2, m - 1, n, 0), lcsRecursive(s1, s2, m, n - 1, 0)));
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
        // first row and first column
        if (i == 0 || j == 0) {
          dp[i][j] = 0;
          continue;
        }
        // If the characters match, take diagonal value + 1
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          // Else, take the maximum of left and top
          dp[i][j] = 0;
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

    System.out.println("Memoization LCS length: " + lcsTabulation(S1, S2)); // Output: 4
  }
}
