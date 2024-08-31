/*
 * Problem Statement:
 *
 * Write a function that, given a string, returns its longest palindromic substring.
 *
 * A palindrome is defined as a string that reads the same forward and backward.
 * Single-character strings are also considered palindromes.
 *
 * Assumptions:
 * - There will be only one longest palindromic substring in the input string.
 *
 * Example:
 *
 * Sample Input:
 * string = "abaxyzzyxf"
 *
 * Sample Output:
 * "xyzzyx"
 */
/*Brute Force Solution
Approach:
Generate All Substrings: Generate all possible substrings of the input string.
Check for Palindromes: For each substring, check if it is a palindrome.
Track the Longest: Keep track of the longest palindromic substring found during the process.
Time Complexity:
O(n³):
Generating all substrings takes O(n²).
Checking if each substring is a palindrome takes O(n).
Therefore, the total time complexity is O(n²) * O(n) = O(n³).
Space Complexity:
O(1):
Only constant extra space is used for tracking the longest palindrome. However, if we consider the space required for the output substring, it would be O(n).*/
package medium;

public class LongestPalindromicSubstring {

  // Brute Force Solution
  public static String longestPalindromicSubstring(String str) {
    String longestPalindrome = "";

    for (int i = 0; i < str.length(); i++) {
      for (int j = i; j < str.length(); j++) {
        String substring = str.substring(i, j + 1);
        if (isPalindrome(substring) && substring.length() > longestPalindrome.length()) {
          longestPalindrome = substring;
        }
      }
    }
    return longestPalindrome;
  }

  // Helper function to check if a string is a palindrome
  private static boolean isPalindrome(String str) {
    int left = 0;
    int right = str.length() - 1;
    while (left < right) {
      if (str.charAt(left) != str.charAt(right)) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  // Optimized Solution using Expand Around Center
  public static String longestPalindromicSubstring2(String str) {
    String longestPalindrome = "";

    for (int i = 0; i < str.length(); i++) {
      // Check for odd-length palindromes
      String oddPalindrome = expandAroundCenter(str, i, i);
      if (oddPalindrome.length() > longestPalindrome.length()) {
        longestPalindrome = oddPalindrome;
      }

      // Check for even-length palindromes
      String evenPalindrome = expandAroundCenter(str, i, i + 1);
      if (evenPalindrome.length() > longestPalindrome.length()) {
        longestPalindrome = evenPalindrome;
      }
    }

    return longestPalindrome;
  }

  // Helper function to expand around the center and find the palindrome
  private static String expandAroundCenter(String str, int left, int right) {
    while (left >= 0 && right < str.length() && str.charAt(left) == str.charAt(right)) {
      left--;
      right++;
    }
    return str.substring(left + 1, right);
  }

  public static void main(String[] args) {
    String string = "abaxyzzyxf";
    System.out.println(longestPalindromicSubstring(string)); // Output: "xyzzyx"
  }
}
/*
Optimized Solution
Approach: Expand Around Center
Expand Around Each Character: Consider each character and each pair of characters in the string as potential centers of a palindrome.
Expand Outward: For each center, expand outward as long as the substring remains a palindrome.
Track the Longest: Track the longest palindromic substring found during this process.
Time Complexity:
O(n²):
Expanding around each center takes O(n) time, and since there are O(n) possible centers, the total time complexity is O(n) * O(n) = O(n²).
Space Complexity:
O(1):
Only constant extra space is used for tracking the longest palindrome. If considering the output substring, the space complexity would be O(n).*/
