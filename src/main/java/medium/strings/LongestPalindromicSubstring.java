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
package medium.strings;

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

  // Function to find the longest palindromic substring
  public static String longestPalindrome(String s) {
    if (s == null || s.isEmpty())
      return ""; // If the string is null or empty, return an empty string
    int start = 0, end = 0; // Initialize start and end of the longest palindromic substring
    for (int i = 0; i < s.length(); i++) {
      int len1 = expandAroundCenter(s, i, i); // Length of the palindrome with the center at i
      int len2 =
              expandAroundCenter(
                      s, i, i + 1); // Length of the palindrome with the center between i and i + 1
      int len = Math.max(len1, len2); // The length of the longer palindrome
      if (len
              > end - start) { // If the longer palindrome is longer than the current longest palindrome
        start = i - (len - 1) / 2; // Update the start of the longest palindrome
        end = i + len / 2; // Update the end of the longest palindrome
      }
    }
    return s.substring(start, end + 1); // Return the longest palindromic substring
  }

  // Helper function to expand around the center and return the length of the palindrome
  private static int expandAroundCenter(String s, int left, int right) {
    while (left >= 0
            && right < s.length()
            && s.charAt(left)
            == s.charAt(right)) { // While the characters at the left and right are equal
      left--; // Move the left pointer to the left
      right++; // Move the right pointer to the right
    }
    return right - left - 1; // Return the length of the palindrome
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


