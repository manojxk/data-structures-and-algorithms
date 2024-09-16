/*
 * Problem Statement:
 *
 * Write a function that takes in an array of strings and groups anagrams together.
 *
 * Anagrams are strings made up of exactly the same letters, where the order doesn't matter.
 * For example, "cinema" and "iceman" are anagrams; similarly, "foo" and "ofo" are anagrams.
 *
 * Your function should return a list of anagram groups in no particular order.
 *
 * Example:
 *
 * Sample Input:
 * words = ["yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"]
 *
 * Sample Output:
 * [["yo", "oy"], ["flop", "olfp"], ["act", "tac", "cat"], ["foo"]]
 */

package medium.strings;

import java.util.*;

public class A02GroupAnagrams {

  // Optimized Solution for Grouping Anagrams
  /*
   * Approach:
   * - For each word in the input array, sort the characters to generate a "canonical" form of the word.
   * - Use this sorted version of the word as a key in a HashMap.
   * - Each key will map to a list of anagrams, where words that share the same sorted version are grouped together.
   *
   * Time Complexity:
   * - O(n * m * log(m)): Where `n` is the number of words and `m` is the maximum length of a word.
   *   Sorting each word takes O(m * log(m)), and we do this for each word in the array.
   *
   * Space Complexity:
   * - O(n * m): The space required for the HashMap and the lists of grouped anagrams, where `n` is the number of words and `m` is the length of each word.
   */
  public static List<List<String>> groupAnagrams(String[] words) {
    // Step 1: Create a map to hold the sorted version of words as keys and anagram lists as values
    Map<String, List<String>> map = new HashMap<>();

    // Step 2: Process each word in the input array
    for (String word : words) {
      char[] charArray = word.toCharArray(); // Convert the word to a character array
      Arrays.sort(charArray); // Sort the characters in alphabetical order
      String sortedWord = new String(charArray); // Convert the sorted characters back to a string

      // Step 3: If the sorted word is not already in the map, add it with a new list
      if (!map.containsKey(sortedWord)) {
        map.put(sortedWord, new ArrayList<>());
      }

      // Step 4: Add the original word to the list corresponding to the sorted word
      map.get(sortedWord).add(word);
    }

    // Step 5: Return the list of anagram groups
    return new ArrayList<>(map.values());
  }

  public static void main(String[] args) {
    // Sample Input
    String[] words = {"yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"};

    // Group the anagrams
    List<List<String>> groupedAnagrams = groupAnagrams(words);

    // Output the result
    System.out.println(
        groupedAnagrams); // Expected Output: [["yo", "oy"], ["flop", "olfp"], ["act", "tac",
                          // "cat"], ["foo"]]
  }
}

/*
 * Approach Summary:
 * 1. Sort each word's characters to create a "canonical" form of the word, which will act as a key.
 * 2. Use a HashMap to group the words with the same sorted characters (i.e., anagrams).
 * 3. Add words to their respective anagram group, and return the list of all anagram groups.
 *
 * Example Walkthrough:
 * For the input:
 * words = ["yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"]
 *
 * - The sorted form of "yo" and "oy" is "oy", so they will be grouped together.
 * - The sorted form of "flop" and "olfp" is "flop", so they will be grouped together.
 * - "act", "tac", and "cat" all have the sorted form "act", so they will be grouped together.
 * - "foo" will remain alone since it has no anagram.
 *
 * Time Complexity:
 * - O(n * m * log(m)): Sorting each word takes O(m * log(m)), where `m` is the length of the word. We do this for all `n` words in the input array.
 *
 * Space Complexity:
 * - O(n * m): The space required for the HashMap and the lists of grouped anagrams, where `n` is the number of words and `m` is the length of the longest word.
 */
