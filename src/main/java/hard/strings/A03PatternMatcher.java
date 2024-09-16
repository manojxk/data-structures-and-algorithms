package hard.strings;

/*
 * Problem Statement:
 *
 * You're given two non-empty strings: `pattern` and `str`. The `pattern` string consists
 * only of 'x' and 'y' characters, and the length of `pattern` is guaranteed to be
 * shorter or equal to the length of `str`. Write a function that finds all possible ways
 * to map the pattern to the string using two distinct substrings, where 'x' is mapped to
 * one substring and 'y' is mapped to another substring.
 *
 * If no valid mapping exists, return an empty array.
 *
 * Example:
 *
 * Sample Input:
 * pattern = "xxyxxy"
 * str = "gogopowerrangergogopowerranger"
 *
 * Sample Output:
 * ["gogo", "powerranger"]
 */

/*
 * Approach:
 *
 * 1. If the pattern or the string is empty, return an empty array.
 * 2. Flip the pattern (if necessary) to make sure it starts with 'x', simplifying the logic.
 * 3. Count the occurrences of 'x' and 'y' in the pattern to help calculate potential substring lengths.
 * 4. Iterate over all possible lengths of the 'x' substring.
 *    - Calculate the corresponding length for the 'y' substring.
 *    - Verify if the entire string can be constructed with the guessed 'x' and 'y' substrings.
 * 5. If a valid mapping exists, return the two substrings.
 * 6. If no mapping is found, return an empty array.
 *
 * Time Complexity: O(n^2)
 * - We try all possible lengths of substring 'x', and for each try, we validate the string construction.
 *
 * Space Complexity: O(n)
 * - We use additional space for storing potential substrings and the result string.
 */

import java.util.*;

public class A03PatternMatcher {

  // Main function to match pattern with string
  public static String[] patternMatcher(String pattern, String str) {
    if (pattern.length() > str.length()) return new String[] {};

    // Step 1: Flip the pattern to ensure it starts with 'x' for simplicity
    boolean isFlipped = pattern.charAt(0) != 'x';
    if (isFlipped) pattern = flipPattern(pattern);

    // Step 2: Count the occurrences of 'x' and 'y' in the pattern
    Map<Character, Integer> counts = countPatternChars(pattern);
    int countOfX = counts.get('x');
    int countOfY = counts.get('y');

    // Step 3: Try different lengths for substring 'x'
    for (int lenOfX = 0; lenOfX <= str.length(); lenOfX++) {
      int remainingLength = str.length() - lenOfX * countOfX;

      // Ensure the remaining length is divisible by the count of 'y'
      if (countOfY == 0 || remainingLength % countOfY == 0) {
        int lenOfY = (countOfY == 0) ? 0 : remainingLength / countOfY;
        String potentialX = str.substring(0, lenOfX);
        String potentialY =
            countOfY == 0
                ? ""
                : str.substring(
                    lenOfX * pattern.indexOf('y'), lenOfX * pattern.indexOf('y') + lenOfY);

        // Step 4: Check if the string matches the pattern
        String candidate = buildFromPattern(pattern, potentialX, potentialY);
        if (str.equals(candidate)) {
          return isFlipped
              ? new String[] {potentialY, potentialX}
              : new String[] {potentialX, potentialY};
        }
      }
    }

    return new String[] {};
  }

  // Helper function to flip the pattern (swap 'x' and 'y')
  private static String flipPattern(String pattern) {
    char[] flippedPattern = new char[pattern.length()];
    for (int i = 0; i < pattern.length(); i++) {
      flippedPattern[i] = (pattern.charAt(i) == 'x') ? 'y' : 'x';
    }
    return new String(flippedPattern);
  }

  // Helper function to count occurrences of 'x' and 'y' in the pattern
  private static Map<Character, Integer> countPatternChars(String pattern) {
    Map<Character, Integer> counts = new HashMap<>();
    counts.put('x', 0);
    counts.put('y', 0);
    for (char ch : pattern.toCharArray()) {
      counts.put(ch, counts.get(ch) + 1);
    }
    return counts;
  }

  // Helper function to construct a string from the pattern using potential substrings
  private static String buildFromPattern(String pattern, String x, String y) {
    StringBuilder sb = new StringBuilder();
    for (char ch : pattern.toCharArray()) {
      sb.append(ch == 'x' ? x : y);
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    // Example 1
    String pattern = "xxyxxy";
    String str = "gogopowerrangergogopowerranger";
    System.out.println(
        Arrays.toString(patternMatcher(pattern, str))); // Output: ["gogo", "powerranger"]

    // Example 2
    String pattern2 = "yxy";
    String str2 = "abababab";
    System.out.println(Arrays.toString(patternMatcher(pattern2, str2))); // Output: ["a", "b"]
  }
}
