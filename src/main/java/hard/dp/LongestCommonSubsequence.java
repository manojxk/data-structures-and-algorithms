/**
 * Problem Statement: Longest Common Subsequence
 *
 * <p>You are given two non-empty strings, str1 and str2. Write a function that returns their
 * longest common subsequence.
 *
 * <p>A subsequence of a string is a set of characters that aren't necessarily adjacent but appear
 * in the same order as they appear in the string. For example, the characters ["a", "c", "d"] form
 * a subsequence of the string "abcd", and so do the characters ["b", "d"]. Note that a single
 * character or the entire string is also considered a valid subsequence.
 *
 * <p>You can assume that there will be only one longest common subsequence between the two input
 * strings.
 *
 * <p>Function Signature: List<Character> longestCommonSubsequence(String str1, String str2);
 *
 * <p>Input: - Two strings str1 and str2.
 *
 * <p>Output: - A list of characters representing the longest common subsequence between the two
 * strings.
 *
 * <p>Constraints: - The strings str1 and str2 will only contain uppercase English letters. - You
 * can assume there will only be one longest common subsequence.
 *
 * <p>Example:
 *
 * <p>Input: str1 = "ZXVVYZW" str2 = "XKYKZPW"
 *
 * <p>Output: ["X", "Y", "Z", "W"]
 *
 * <p>Explanation: The longest common subsequence between "ZXVVYZW" and "XKYKZPW" is ["X", "Y", "Z",
 * "W"].
 */
package hard.dp;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence {

  public static List<Character> longestCommonSubsequence(String str1, String str2) {
    int len1 = str1.length();
    int len2 = str2.length();
    // dp array to store the lengths of LCS
    int[][] dp = new int[len1 + 1][len2 + 1];

    // Fill the dp array
    for (int i = 1; i <= len1; i++) {
      for (int j = 1; j <= len2; j++) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1; // Characters match, extend LCS
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]); // Take the max of ignoring one char
        }
      }
    }

    // Backtrack to find the LCS characters
    return buildSequence(dp, str1, str2);
  }

  private static List<Character> buildSequence(int[][] dp, String str1, String str2) {
    List<Character> lcs = new ArrayList<>();
    int i = str1.length();
    int j = str2.length();

    while (i > 0 && j > 0) {
      if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
        lcs.add(0, str1.charAt(i - 1)); // Add character to the result list
        i--;
        j--;
      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        i--; // Move up in the DP table
      } else {
        j--; // Move left in the DP table
      }
    }
    return lcs; // LCS found
  }

  public static void main(String[] args) {
    String str1 = "ZXVVYZW";
    String str2 = "XKYKZPW";
    List<Character> result = longestCommonSubsequence(str1, str2);
    System.out.println(result);
  }
}
/*
Dynamic Programming Table:

Create a 2D array dp where dp[i][j] will store the length of the LCS between the first i characters of str1 and the first j characters of str2.
State Transition:

If the characters str1[i-1] and str2[j-1] match, then dp[i][j] = dp[i-1][j-1] + 1, meaning we can extend the LCS ending at i-1 and j-1.
Otherwise, if the characters don’t match, the LCS up to dp[i][j] will be the maximum of dp[i-1][j] (ignoring the current character of str1) or dp[i][j-1] (ignoring the current character of str2).
Reconstructing the LCS:

After filling up the dp table, we can backtrack from dp[len1][len2] to reconstruct the LCS by following the decisions made in the DP table.
Base Cases:

If either string is empty, the LCS is also empty, so initialize dp[0][j] and dp[i][0] to 0.*/

/*
Explanation:
Initialization:

We create a 2D array dp where each dp[i][j] represents the length of the LCS for the first i characters of str1 and the first j characters of str2.
Filling the DP Table:

We fill in the dp table by checking characters from both strings one by one:
If str1[i-1] == str2[j-1], it means these characters can be part of the LCS, so we increment the LCS by 1 (dp[i][j] = dp[i-1][j-1] + 1).
Otherwise, we take the maximum of either ignoring the current character from str1 (dp[i-1][j]) or ignoring the current character from str2 (dp[i][j-1]).
Backtracking:

After filling the DP table, the value in dp[len1][len2] gives us the length of the LCS.
To construct the actual LCS, we backtrack from dp[len1][len2] to find the characters that are part of the LCS.
Time Complexity:
Time Complexity: O(n * m) where n is the length of str1 and m is the length of str2. This is because we are filling up an n x m DP table.
Space Complexity: O(n * m) due to the 2D DP array used to store the LCS lengths.*/
