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

import java.util.Stack;

public class ReverseWords {

  // Optimized Solution
  public static String reverseWordsInString(String str) {
    Stack<String> stack = new Stack<>();
    int start = 0;

    // Manually extract words and push them onto a stack
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == ' ') {
        if (start != i) {
          stack.push(str.substring(start, i));
        }
        start = i + 1;
      }
    }

    // Add the last word if the string does not end with a whitespace
    if (start < str.length()) {
      stack.push(str.substring(start));
    }

    // Form the reversed string
    StringBuilder reversedString = new StringBuilder();
    while (!stack.isEmpty()) {
      reversedString.append(stack.pop()).append(" ");
    }

    // Remove the trailing whitespace
    return reversedString.toString().trim();
  }

  public static void main(String[] args) {
    String str = "AlgoExpert is the best!";
    String result = reverseWordsInString(str);
    System.out.println(result); // Output: "best! the is AlgoExpert"
  }
}
