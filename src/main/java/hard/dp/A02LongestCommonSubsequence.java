package hard.dp;

import cools.dp.longestcommonsubsequence.A03PrintingLongestCommonSubsequence;

public class A02LongestCommonSubsequence {

  public static String longestCommonSubsequence(String str1, String str2) {

    return A03PrintingLongestCommonSubsequence.lcsRecursive(
        str1, str2, str1.length(), str2.length());
  }
}
