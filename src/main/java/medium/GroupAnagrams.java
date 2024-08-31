/*
 * Problem Statement:
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

/*Brute Force Solution
Approach:
Sort Each Word: For each word in the array, sort the characters and use the sorted string as a key.
Group Anagrams: Use a data structure (like a HashMap) to map each sorted string to a list of anagrams.
Return the Groups: Return the values from the map as the groups of anagrams.
Time Complexity:
O(n * m * log(m)): Where n is the number of words and m is the maximum length of a word. Sorting each word takes O(m * log(m)).
Space Complexity:
O(n * m): Space required for the HashMap and the grouped anagrams.*/

package medium;

import java.util.*;

public class GroupAnagrams {

  // Brute Force Solution
  public static List<List<String>> groupAnagrams(String[] words) {
    Map<String, List<String>> map = new HashMap<>();

    for (String word : words) {
      char[] charArray = word.toCharArray();
      Arrays.sort(charArray);
      String sortedWord = new String(charArray);

      if (!map.containsKey(sortedWord)) {
        map.put(sortedWord, new ArrayList<>());
      }
      map.get(sortedWord).add(word);
    }

    return new ArrayList<>(map.values());
  }

  public static void main(String[] args) {
    String[] words = {"yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"};
    List<List<String>> groupedAnagrams = groupAnagrams(words);
    System.out.println(
        groupedAnagrams); // Output: [["yo", "oy"], ["flop", "olfp"], ["act", "tac", "cat"],
    // ["foo"]]
  }
}
