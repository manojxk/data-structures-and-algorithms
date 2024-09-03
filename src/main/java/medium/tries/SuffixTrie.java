/*
 * Problem Statement:
 * Implement a Suffix Trie class that supports the following operations:
 *
 * 1. Populate the Suffix Trie from a given string by calling the `populateSuffixTrieFrom` method.
 * 2. Search for strings in the Suffix Trie.
 *
 * Each string added to the trie should end with a special end symbol "*".
 *
 * Example (Trie Creation):
 * Input: string = "babc"
 *
 * The structure below is the expected root of the trie:
 * {
 *   "c": {"*": true},
 *   "b": {
 *     "c": {"*": true},
 *     "a": {"b": {"c": {"*": true}}},
 *   },
 *   "a": {"b": {"c": {"*": true}}},
 * }
 *
 * Example (Trie Search):
 * Input: string = "abc"
 * Output: true
 */
/*
The brute force approach involves generating all suffixes of the input string and inserting each suffix into the trie one by one. This method works by iterating over each suffix and inserting its characters into the trie.

Time Complexity:
O(n^2): We generate n suffixes, and each suffix can be up to n characters long. Therefore, the time complexity is quadratic.
Space Complexity:
O(n^2): In the worst case, the trie will store all n(n+1)/2 possible substrings, leading to quadratic space usage.*/
package medium.tries;

import java.util.HashMap;
import java.util.Map;

public class SuffixTrie {

  public static class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
  }

  private TrieNode root = new TrieNode();
  private final char endSymbol = '*';

  // Constructor to populate the trie
  public SuffixTrie(String str) {
    populateSuffixTrieFrom(str);
  }

  // Brute Force Solution to populate the trie
  public void populateSuffixTrieFrom(String str) {
    for (int i = 0; i < str.length(); i++) {
      insertSubstringStartingAt(i, str);
    }
  }

  private void insertSubstringStartingAt(int index, String str) {
    TrieNode node = root;
    for (int i = index; i < str.length(); i++) {
      char letter = str.charAt(i);
      if (!node.children.containsKey(letter)) {
        node.children.put(letter, new TrieNode());
      }
      node = node.children.get(letter);
    }
    node.children.put(endSymbol, null); // Mark the end of the suffix
  }

  // Method to search for a string in the trie
  public boolean contains(String str) {
    TrieNode node = root;
    for (int i = 0; i < str.length(); i++) {
      char letter = str.charAt(i);
      if (!node.children.containsKey(letter)) {
        return false;
      }
      node = node.children.get(letter);
    }
    return node.children.containsKey(endSymbol);
  }

  public static void main(String[] args) {
    SuffixTrie trie = new SuffixTrie("babc");
    System.out.println(trie.contains("abc")); // Output: true
  }
}
