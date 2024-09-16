/*
 * Problem Statement:
 *
 * Given a string of lowercase English-alphabet letters, write a function that
 * returns the index of the first non-repeating character in the string.
 * The first non-repeating character is the first character in the string that
 * occurs only once.
 *
 * If there are no non-repeating characters, the function should return -1.
 *
 * Sample Input:
 * string = "abcdcaf"
 *
 * Sample Output:
 * 1  // The first non-repeating character is "b" and is found at index 1.
 */

package easy.strings;

import java.util.HashMap;
import java.util.Map;

public class A06FirstNonRepeatingCharacter {

  // Brute force solution to find the first non-repeating character's index
  /*
   * Approach:
   * - For each character in the string, check if it appears more than once.
   * - The first character that appears only once is the non-repeating character, and its index is returned.
   *
   * Time Complexity:
   * - O(n^2): For each character in the string, we traverse the string again to count its occurrences.
   *
   * Space Complexity:
   * - O(1): No extra space is required beyond a few variables.
   */
  public static int firstNonRepeatingCharacter(String string) {
    for (int i = 0; i < string.length(); i++) {
      char currentChar = string.charAt(i);
      boolean isRepeating = false;
      for (int j = 0; j < string.length(); j++) {
        if (i != j && currentChar == string.charAt(j)) {
          isRepeating = true;
          break;
        }
      }
      if (!isRepeating) {
        return i;
      }
    }
    return -1;
  }

  // Optimized solution to find the first non-repeating character's index
  /*
   * Approach:
   * - Use a frequency map (hash map) to count the occurrences of each character in the string.
   * - After populating the frequency map, traverse the string again to find the first character with a frequency of 1.
   *
   * Time Complexity:
   * - O(n): We traverse the string twice: once to populate the frequency map and once to find the first non-repeating character.
   *
   * Space Complexity:
   * - O(k): Where k is the number of unique characters in the string. However, since the alphabet size is fixed (26 lowercase letters), this can be considered O(1).
   */
  public static int firstNonRepeatingCharacterOptimized(String string) {
    Map<Character, Integer> frequencyMap = new HashMap<>();

    // Step 1: Populate the frequency map
    for (char c : string.toCharArray()) {
      frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
    }

    // Step 2: Find the first character with frequency 1
    for (int i = 0; i < string.length(); i++) {
      if (frequencyMap.get(string.charAt(i)) == 1) {
        return i;
      }
    }

    return -1; // No non-repeating character found
  }

  public static void main(String[] args) {
    // Sample Input
    String string = "abcdcaf";

    // Get the index of the first non-repeating character (Brute Force)
    int result = firstNonRepeatingCharacter(string);
    System.out.println(result); // Expected Output: 1

    // Get the index of the first non-repeating character (Optimized)
    int resultOptimized = firstNonRepeatingCharacterOptimized(string);
    System.out.println(resultOptimized); // Expected Output: 1
  }
}
