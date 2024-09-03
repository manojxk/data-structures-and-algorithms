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
/*Brute Force Solution
Approach:
Create a Character Frequency Map: For each word, count the frequency of each character.
Merge Frequencies: Combine the frequencies to get the maximum count needed for each character across all words.
Build Result Array: Use the frequency map to create the output array with the exact number of each character required.
Time Complexity:
O(n * m): Where n is the number of words and m is the average length of a word.
Space Complexity:
O(1): The space complexity is constant with respect to the input size, as the character set is finite*/

package medium.strings;

import java.util.*;

public class SmallestCharArray {

  // Brute Force Solution
  public static List<Character> smallestCharArray(String[] words) {
    Map<Character, Integer> maxCharFrequency = new HashMap<>();

    for (String word : words) {
      Map<Character, Integer> charFrequency = new HashMap<>();
      for (char c : word.toCharArray()) {
        charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
      }
      // Merge frequencies
      for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
        char c = entry.getKey();
        int freq = entry.getValue();
        maxCharFrequency.put(c, Math.max(maxCharFrequency.getOrDefault(c, 0), freq));
      }
    }

    // Build the result array
    List<Character> result = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : maxCharFrequency.entrySet()) {
      char c = entry.getKey();
      int freq = entry.getValue();
      for (int i = 0; i < freq; i++) {
        result.add(c);
      }
    }

    return result;
  }

  public static void main(String[] args) {
    String[] words = {"this", "that", "did", "deed", "them!", "a"};
    List<Character> result = smallestCharArray(words);
    System.out.println(result); // Output: [t, t, h, i, s, a, d, d, e, e, m, !]
  }
}
