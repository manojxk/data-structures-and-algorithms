/*
 * Problem Statement:
 * Write a function that takes in an array of words and returns the smallest array
 * of characters needed to form all of the words. The characters don't need to be in any
 * particular order.
 *
 * Example:
 *
 * Sample Input:
 * words = ["this", "that", "did", "deed", "them!", "a"]
 *
 * Sample Output:
 * ["t", "t", "h", "i", "s", "a", "d", "d", "e", "e", "m", "!"]
 *
 * Note: The characters could be ordered differently.
 */

/*
 * Brute Force Solution
 *
 * Approach:
 * 1. For each word, count the frequency of each character in that word.
 * 2. Merge the frequency counts across all words, ensuring that for each character,
 *    we store the maximum number of times it appears in any word.
 * 3. Build the result array using the frequency counts.
 *
 * Time Complexity: O(n * m)
 * - Where n is the number of words and m is the average length of each word.
 * - We go through each word and count the frequency of its characters, followed by merging the frequencies.
 *
 * Space Complexity: O(1)
 * - The space complexity is O(1) because the character set is finite (26 letters, digits, and punctuation).
 * - Thus, the frequency map does not scale with input size.
 */

package medium.strings;

import java.util.*;

public class A05SmallestCharArray {

  // Brute Force Solution
  public static List<Character> smallestCharArray(String[] words) {
    // Step 1: Create a map to track the maximum frequency of each character across all words
    Map<Character, Integer> maxCharFrequency = new HashMap<>();

    // Step 2: For each word, calculate the character frequency
    for (String word : words) {
      // Local map to count character frequencies for the current word
      Map<Character, Integer> charFrequency = new HashMap<>();
      for (char c : word.toCharArray()) {
        charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
      }
      // Merge the frequency counts to get the maximum frequency across all words
      for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
        char c = entry.getKey();
        int freq = entry.getValue();
        // Store the maximum frequency of each character
        maxCharFrequency.put(c, Math.max(maxCharFrequency.getOrDefault(c, 0), freq));
      }
    }

    // Step 3: Build the result list using the maximum character frequencies
    List<Character> result = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : maxCharFrequency.entrySet()) {
      char c = entry.getKey();
      int freq = entry.getValue();
      // Add each character to the result the maximum number of times it appears
      for (int i = 0; i < freq; i++) {
        result.add(c);
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Example input
    String[] words = {"this", "that", "did", "deed", "them!", "a"};

    // Calculate the smallest array of characters needed to form all words
    List<Character> result = smallestCharArray(words);

    // Output the result (Note: the characters could be ordered differently)
    System.out.println(result); // Expected Output: [t, t, h, i, s, a, d, d, e, e, m, !]
  }
}
