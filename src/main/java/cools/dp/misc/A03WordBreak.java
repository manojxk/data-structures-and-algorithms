package cools.dp.misc;

/*
 Problem: Word Break

 Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of
 one or more dictionary words.

 Note that the same word in the dictionary may be reused multiple times in the segmentation.

 Example 1:
 Input: s = "leetcode", wordDict = ["leet","code"]
 Output: true
 Explanation: Return true because "leetcode" can be segmented as "leet code".

 Example 2:
 Input: s = "applepenapple", wordDict = ["apple","pen"]
 Output: true
 Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 Note that you are allowed to reuse a dictionary word.

 Example 3:
 Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 Output: false

 Constraints:
 - 1 <= s.length <= 300
 - 1 <= wordDict.length <= 1000
 - 1 <= wordDict[i].length <= 20
 - s and wordDict[i] consist of only lowercase English letters.
 - All the strings of wordDict are unique.

 Solution Approach:
 1. This is a dynamic programming problem where we build a dp array where dp[i] represents whether the substring s[0..i-1] can be
    segmented into dictionary words.
 2. Start by initializing dp[0] as true (since an empty string can be segmented).
 3. Iterate over all substrings of s and check if any substring is in wordDict and the remainder of the string can be segmented.
 4. If dp[i] is true, this means that the substring s[0..i-1] can be segmented.

*/

import java.util.*;

public class A03WordBreak {

  // Function to check if the string can be segmented into dictionary words
  public boolean wordBreak(String s, List<String> wordDict) {
    // Convert the wordDict into a set for fast lookups
    Set<String> wordSet = new HashSet<>(wordDict);

    // Initialize a dp array where dp[i] represents if s[0..i-1] can be segmented
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true; // Base case: an empty string can be segmented

    // Iterate through the string
    for (int i = 1; i <= s.length(); i++) {
      // Check all possible substrings ending at i
      for (int j = 0; j < i; j++) {
        // If the substring s[j..i-1] is in the wordSet and dp[j] is true
        if (dp[j] && wordSet.contains(s.substring(j, i))) {
          dp[i] = true; // Mark dp[i] as true
          break; // No need to check further, we found a valid segmentation
        }
      }
    }

    return dp[s.length()]; // Return whether the entire string can be segmented
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03WordBreak solution = new A03WordBreak();

    // Example 1
    String s1 = "leetcode";
    List<String> wordDict1 = Arrays.asList("leet", "code");
    System.out.println(
        "Can segment \"" + s1 + "\": " + solution.wordBreak(s1, wordDict1)); // Output: true

    // Example 2
    String s2 = "applepenapple";
    List<String> wordDict2 = Arrays.asList("apple", "pen");
    System.out.println(
        "Can segment \"" + s2 + "\": " + solution.wordBreak(s2, wordDict2)); // Output: true

    // Example 3
    String s3 = "catsandog";
    List<String> wordDict3 = Arrays.asList("cats", "dog", "sand", "and", "cat");
    System.out.println(
        "Can segment \"" + s3 + "\": " + solution.wordBreak(s3, wordDict3)); // Output: false
  }

  /*
   Time Complexity:
   - O(n^2), where n is the length of the string s. The outer loop runs for each character in s, and the inner loop checks all
     possible substrings.

   Space Complexity:
   - O(n), where n is the length of the string s. We use a dp array of size n+1.
  */
}
