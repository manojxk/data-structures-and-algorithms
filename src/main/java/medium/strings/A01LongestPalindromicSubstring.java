package medium.strings;

/*
 Problem: Longest Palindromic Substring

 Given a string s, return the longest palindromic substring in s.

 A palindrome is a string that reads the same forwards and backwards. For example, "racecar" is a palindrome.

 Example 1:
 Input: s = "babad"
 Output: "bab"
 Explanation: "aba" is also a valid answer.

 Example 2:
 Input: s = "cbbd"
 Output: "bb"

 Constraints:
 - 1 <= s.length <= 1000
 - s consists of only digits and English letters.

*/

/*
 Solution Steps (Expand Around Center):

 1. The basic idea is that for every character in the string, consider it as the center of a palindrome.
 2. There are two types of palindromes:
    - Odd-length palindromes (centered around a single character)
    - Even-length palindromes (centered between two characters)
 3. For each character, expand outward and check the maximum palindrome that can be formed.
 4. Track the start and end indices of the longest palindrome found.
 5. Return the substring that corresponds to the longest palindrome.

 Time Complexity: O(n^2) - Since we expand around each center and there are O(n) centers to explore (both single characters and pairs).
 Space Complexity: O(1) - Only a few variables are used to track the longest palindrome's indices.
*/

public class A01LongestPalindromicSubstring {
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

  // Function to return the longest palindromic substring
  public String longestPalindrome(String s) {
    if (s == null || s.length() < 1) return "";

    int start = 0, end = 0;

    for (int i = 0; i < s.length(); i++) {
      // Check for odd-length palindromes (center is at i)
      int len1 = expandAroundCenter(s, i, i);
      // Check for even-length palindromes (center is between i and i+1)
      int len2 = expandAroundCenter(s, i, i + 1);

      int len = Math.max(len1, len2);

      // Update the start and end indices of the longest palindrome found
      if (len > end - start) {
        start = i - (len - 1) / 2;
        end = i + len / 2;
      }
    }

    // Return the longest palindromic substring
    return s.substring(start, end + 1);
  }

  // Helper function to expand around the center and find the length of the palindrome
  private int expandAroundCenter(String s, int left, int right) {
    // Expand as long as the characters on both sides are equal and within bounds
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
      left--;
      right++;
    }
    // Return the length of the palindrome
    return right - left - 1;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01LongestPalindromicSubstring solution = new A01LongestPalindromicSubstring();

    // Example 1
    String s1 = "babad";
    System.out.println(
        "Longest Palindromic Substring of 'babad': "
            + solution.longestPalindrome(s1)); // Output: "bab" or "aba"

    // Example 2
    String s2 = "cbbd";
    System.out.println(
        "Longest Palindromic Substring of 'cbbd': "
            + solution.longestPalindrome(s2)); // Output: "bb"
  }

  /*
   Time Complexity:
   - O(n^2), where n is the length of the string. We explore all possible centers and for each center, we try to expand as much as possible.

   Space Complexity:
   - O(1), as we only use a few variables to store the start and end indices of the longest palindrome, and the helper function does not require extra space.
  */
}
