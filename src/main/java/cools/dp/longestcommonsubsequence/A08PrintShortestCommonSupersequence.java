package cools.dp.longestcommonsubsequence;

public class A08PrintShortestCommonSupersequence {

  public static String scsRecursive(String S1, String S2, int m, int n) {
    // Get the LCS first
    String lcs = A03PrintingLongestCommonSubsequence.lcsRecursive(S1, S2, m, n);

    // Construct the supersequence
    StringBuilder scs = new StringBuilder();
    int i = 0, j = 0;
    for (char c : lcs.toCharArray()) {
      while (S1.charAt(i) != c) scs.append(S1.charAt(i++));
      while (S2.charAt(j) != c) scs.append(S2.charAt(j++));
      scs.append(c);
      i++;
      j++;
    }

    // Append remaining characters from both strings
    scs.append(S1.substring(i)).append(S2.substring(j));

    return scs.toString();
  }

  // Tabulation function to find and print SCS
  public static String scsTabulation(String S1, String S2) {
    int m = S1.length();
    int n = S2.length();

    // Create a DP table to store lengths of LCS
    int[][] dp = new int[m + 1][n + 1];

    // Build the DP table for LCS
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // Now construct the SCS using the DP table
    StringBuilder scs = new StringBuilder();
    int i = m, j = n;
    while (i > 0 && j > 0) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        scs.append(S1.charAt(i - 1));
        i--;
        j--;
      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        scs.append(S1.charAt(i - 1));
        i--;
      } else {
        scs.append(S2.charAt(j - 1));
        j--;
      }
    }

    // Add remaining characters of S1 or S2
    while (i > 0) {
      scs.append(S1.charAt(i - 1));
      i--;
    }
    while (j > 0) {
      scs.append(S2.charAt(j - 1));
      j--;
    }

    // Since we constructed the string backwards, reverse it
    return scs.reverse().toString();
  }

  public static void main(String[] args) {
    String S1 = "AGGTAB";
    String S2 = "GXTXAYB";
    int m = S1.length();
    int n = S2.length();

    System.out.println("Recursive SCS: " + scsRecursive(S1, S2, m, n)); // Output: AGGXTXAYB
  }
}
