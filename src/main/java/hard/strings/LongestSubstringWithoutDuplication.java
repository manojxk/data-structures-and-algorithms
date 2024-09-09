package hard.strings;

/**
 * Problem Statement:
 *
 * <p>You are given a string, and the task is to return the longest substring that does not contain
 * any duplicate characters. You can assume that there will only be one longest substring without
 * duplicates in the input string.
 *
 * <p>Sample Input: string = "clementisacap"
 *
 * <p>Sample Output: "mentisac"
 *
 * <p>Explanation: The longest substring without any duplicate characters in the given input is
 * "mentisac". This substring is 8 characters long.
 */
import java.util.*;

public class LongestSubstringWithoutDuplication {

  // Optimized Approach using the Sliding Window Technique with Hash Map:
  // Time Complexity: O(n), where n is the length of the input string. Each character is processed
  // once.
  // Space Complexity: O(min(n, m)), where m is the size of the character set (for example, 26 for
  // lowercase letters).
  public static String longestSubstringWithoutDuplication(String string) {
    Map<Character, Integer> lastSeen =
        new HashMap<>(); // Store the last seen index of each character
    int longestStart = 0; // Start index of the longest substring
    int longestLength = 0; // Length of the longest substring
    int startIdx = 0; // Current start index of the sliding window

    for (int i = 0; i < string.length(); i++) {
      char currentChar = string.charAt(i);

      // If the current character has been seen before and its last seen index is within the current
      // window
      if (lastSeen.containsKey(currentChar) && lastSeen.get(currentChar) >= startIdx) {
        // Move the start of the window to one position after the last occurrence of the current
        // character
        startIdx = lastSeen.get(currentChar) + 1;
      }

      // Update the last seen index of the current character
      lastSeen.put(currentChar, i);

      // Calculate the length of the current substring without duplicates
      int currentLength = i - startIdx + 1;

      // Update the longest substring if the current one is longer
      if (currentLength > longestLength) {
        longestLength = currentLength;
        longestStart = startIdx;
      }
    }

    // Return the longest substring
    return string.substring(longestStart, longestStart + longestLength);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    String input = "clementisacap";
    String result = longestSubstringWithoutDuplication(input);
    System.out.println(
        "Longest substring without duplication: " + result); // Expected output: "mentisac"
  }
}
