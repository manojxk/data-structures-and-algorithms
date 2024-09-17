/*
 * Problem Statement:
 * Write a function that takes in a string of words separated by one or more whitespaces
 * and returns a string that has these words in reverse order.
 *
 * For this problem, a word can contain special characters, punctuation, and numbers.
 * The words in the string will be separated by one or more whitespaces, and the reversed
 * string must contain the same whitespaces as the original string.
 *
 * Constraints:
 * - You're not allowed to use any built-in `split` or `reverse` methods/functions.
 * - You are allowed to use a built-in `join` method/function.
 *
 * Edge Cases:
 * - The input string may not contain any words.
 *
 * Example:
 *
 * Sample Input:
 * string = "AlgoExpert is the best!"
 *
 * Sample Output:
 * "best! the is AlgoExpert"
 */

package medium.strings;

import java.util.*;

public class A04ReverseWords {

  /*
   * Brute Force Solution:
   * Time Complexity: O(n)
   * - Where `n` is the length of the input string.
   * - We split the string into words, reverse the array of words, and join them together.
   *
   * Space Complexity: O(n)
   * - We store the array of words and the final reversed string.
   */
  public static String reverseWordsBruteForce(String s) {
    // Step 1: Trim leading and trailing whitespaces and split the string into words
    // by one or more spaces.
    String[] words = s.trim().split("\\s+");
    

    // Step 2: Reverse the array of words.
    List<String> wordList = Arrays.asList(words);
    Collections.reverse(wordList);

    // Step 3: Join the reversed words using a single space.
    return String.join(" ", wordList);
  }

  /*
   * Optimized Solution:
   * Time Complexity: O(n)
   * - Where `n` is the length of the input string. We perform two passes through the string:
   *   - First, to reverse individual words.
   *   - Second, to reverse the entire character array.
   *
   * Space Complexity: O(1)
   * - Since the input is modified in place (apart from the input array), no additional space is used.
   */

  // Function to reverse a part of a character array from index `begin` to `end`.
  public static void reverse(char[] s, int begin, int end) {
    while (begin < end) {
      char temp = s[begin];
      s[begin++] = s[end];
      s[end--] = temp;
    }
  }

  /*
   * Optimized solution to reverse the words in a string.
   * Time Complexity: O(n)
   * - We traverse the input string to reverse individual words and then reverse the entire string.
   *
   * Space Complexity: O(1)
   * - The input string is modified in place, so no extra space is used.
   */
  public static void reverseWordsOptimized(char[] s) {
    int wordBegin = -1;

    // Step 1: Reverse each word in the string.
    for (int i = 0; i < s.length; i++) {
      // Mark the start of a word.
      if (wordBegin == -1 && s[i] != ' ') {
        wordBegin = i;
      }

      // Mark the end of the word and reverse it.
      if (wordBegin != -1 && (i + 1 == s.length || s[i + 1] == ' ')) {
        reverse(s, wordBegin, i);
        wordBegin = -1;
      }
    }

    // Step 2: Reverse the entire string to get the words in the correct order.
    reverse(s, 0, s.length - 1);
  }

  public static void main(String[] args) {
    // Test Brute Force Solution
    String str = "AlgoExpert is the best!";
    String resultBruteForce = reverseWordsBruteForce(str);
    System.out.println(
        "Brute Force Output: " + resultBruteForce); // Output: "best! the is AlgoExpert"

    // Test Optimized Solution
    char[] strOptimized = "AlgoExpert is the best!".toCharArray();
    reverseWordsOptimized(strOptimized);
    System.out.println(
        "Optimized Output: " + new String(strOptimized)); // Output: "best! the is AlgoExpert"
  }
}
