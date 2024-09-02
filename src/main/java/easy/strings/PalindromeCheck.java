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

public class PalindromeCheck {
  /*    1. String Concatenation Approach
  Explanation:
  Reverse the string by appending each character to a new string.
  Compare the reversed string with the original string.
  Time Complexity:
  O(n^2): String concatenation in Java creates a new string each time, resulting in O(n) operations for each concatenation within a loop.
  Space Complexity:
  O(n): Additional space is used to store the reversed string.*/
  public static boolean isPalindrome(String string) {
    String reversedString = "";
    for (int i = string.length() - 1; i >= 0; i--) {
      reversedString += string.charAt(i); // String concatenation
    }
    return string.equals(reversedString);
  }

  /*  2. StringBuilder Approach
  Explanation:
  Use StringBuilder to efficiently reverse the string.
  Compare the reversed string with the original string.
  Time Complexity:
  O(n): Reversing the string and comparing both take O(n) time.
  Space Complexity:
  O(n): Space is required for the StringBuilder object.*/
  public static boolean isPalindrome2(String string) {
    StringBuilder sb = new StringBuilder(string);
    String reversedString = sb.reverse().toString(); // Efficient reversal
    return string.equals(reversedString);
  }

  /*
  3. Recursive Approach
  Explanation:
  Check if the first and last characters of the string are the same.
  Recursively check the substring formed by removing the first and last characters.
  Time Complexity:
  O(n): The recursion runs once for each character.
  Space Complexity:
  O(n): The call stack can grow up to n levels deep.*/
  public static boolean isPalindrome3(String string) {
    return isPalindromeHelper(string, 0, string.length() - 1);
  }

  private static boolean isPalindromeHelper(String string, int left, int right) {
    if (left >= right) {
      return true; // Base case: pointers have met or crossed
    }
    if (string.charAt(left) != string.charAt(right)) {
      return false; // Characters do not match
    }
    return isPalindromeHelper(string, left + 1, right - 1); // Recurse
  }

  /*  4. Two-Pointer Technique
  Explanation:
  Use two pointers, one starting at the beginning and the other at the end of the string.
  Compare the characters at these pointers and move the pointers inward.
  Time Complexity:
  O(n): The loop runs for each character in the string.
  Space Complexity:
  O(1): No extra space is required apart from a few variables.*/
  public static boolean isPalindrome4(String string) {
    int left = 0;
    int right = string.length() - 1;

    while (left < right) {
      if (string.charAt(left) != string.charAt(right)) {
        return false; // Characters do not match
      }
      left++;
      right--;
    }

    return true; // All characters matched
  }
}
