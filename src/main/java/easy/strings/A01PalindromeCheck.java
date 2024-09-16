/*
 * Problem Statement:
 *
 * Write a function that takes in a non-empty string and returns a boolean
 * indicating whether the string is a palindrome. A palindrome is defined as
 * a string that reads the same forward and backward. Note that single-character
 * strings are also considered palindromes.
 *
 * Sample Input:
 * string = "abcdcba"
 *
 * Sample Output:
 * true  // because "abcdcba" reads the same forward and backward
 */

package easy.strings;

public class A01PalindromeCheck {

  // 1. String Concatenation Approach
  /*
   * Explanation:
   * - Reverse the string by appending each character to a new string.
   * - Compare the reversed string with the original string.
   *
   * Time Complexity:
   * - O(n^2): String concatenation creates a new string every time, resulting in O(n) operations within a loop.
   *
   * Space Complexity:
   * - O(n): Additional space is used to store the reversed string.
   */
  public static boolean isPalindrome(String string) {
    String reversedString = "";
    for (int i = string.length() - 1; i >= 0; i--) {
      reversedString += string.charAt(i); // Inefficient string concatenation
    }
    return string.equals(reversedString);
  }

  // 2. StringBuilder Approach
  /*
   * Explanation:
   * - Use StringBuilder to efficiently reverse the string.
   * - Compare the reversed string with the original string.
   *
   * Time Complexity:
   * - O(n): Reversing the string and comparing it takes O(n) time.
   *
   * Space Complexity:
   * - O(n): Space is required for the StringBuilder object and reversed string.
   */
  public static boolean isPalindrome2(String string) {
    StringBuilder sb = new StringBuilder(string);
    String reversedString = sb.reverse().toString(); // Efficient reversal using StringBuilder
    return string.equals(reversedString);
  }

  // 3. Recursive Approach
  /*
   * Explanation:
   * - Recursively check if the first and last characters match.
   * - If they match, recurse on the substring formed by removing the first and last characters.
   *
   * Time Complexity:
   * - O(n): The recursion runs once for each character.
   *
   * Space Complexity:
   * - O(n): The call stack can grow up to n levels deep.
   */
  public static boolean isPalindrome3(String string) {
    return isPalindromeHelper(string, 0, string.length() - 1);
  }

  // Helper method for the recursive approach
  private static boolean isPalindromeHelper(String string, int left, int right) {
    if (left >= right) {
      return true; // Base case: when pointers cross or meet
    }
    if (string.charAt(left) != string.charAt(right)) {
      return false; // Mismatch found
    }
    return isPalindromeHelper(string, left + 1, right - 1); // Recursive call with narrowed bounds
  }

  // 4. Two-Pointer Technique
  /*
   * Explanation:
   * - Use two pointers, one starting at the beginning and one at the end.
   * - Compare characters at the two pointers and move inward if they match.
   *
   * Time Complexity:
   * - O(n): The loop runs once for each character.
   *
   * Space Complexity:
   * - O(1): No extra space is required beyond a few variables.
   */
  public static boolean isPalindrome4(String string) {
    int left = 0;
    int right = string.length() - 1;

    while (left < right) {
      if (string.charAt(left) != string.charAt(right)) {
        return false; // Mismatch found
      }
      left++; // Move left pointer inward
      right--; // Move right pointer inward
    }

    return true; // All characters matched
  }
}
