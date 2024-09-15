package medium.tries;

/*
 Problem: Suffix Trie Construction

 A suffix trie is a trie (a type of prefix tree) that stores all the suffixes of a given string.
 The task is to construct a suffix trie for a given string.

 Each node in the suffix trie represents a single character, and the entire trie stores all possible suffixes of the string.
 This allows for efficient string matching operations.

 Example:

 Input: "babc"

 Output:
 {
   'b': {'a': {'b': {'c': {'*': {}}}}},
   'a': {'b': {'c': {'*': {}}}},
   'b': {'c': {'*': {}}},
   'c': {'*': {}}
 }

 Explanation:
 The '*' character is used to indicate the end of a suffix.
*/

/*
 Solution Steps:

 1. Iterate over the string, starting from each character and adding all suffixes of the string to the trie.
 2. For each suffix, traverse the trie and add the characters one by one. If a character is not already present, add a new node.
 3. After adding all characters of a suffix, add a terminal '*' character to mark the end of the suffix.
*/

import java.util.HashMap;
import java.util.Map;

public class A01SuffixTrieConstruction {

  // Suffix Trie Node class
  static class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
  }

  // Suffix Trie class
  static class SuffixTrie {
    TrieNode root = new TrieNode(); // Root of the suffix trie
    final char END_SYMBOL = '*'; // Symbol to indicate the end of a suffix

    // Constructor that takes the input string and builds the suffix trie
    public SuffixTrie(String str) {
      constructTrie(str); // Call the helper method to construct the suffix trie
    }

    // Method to construct the suffix trie
    private void constructTrie(String str) {
      // For each index in the string, insert the suffix starting at that index
      for (int i = 0; i < str.length(); i++) {
        insertSuffixStartingAt(str, i);
      }
    }

    // Helper method to insert a suffix into the trie starting at a given index
    private void insertSuffixStartingAt(String str, int startIdx) {
      TrieNode currentNode = root;

      // Iterate over the suffix and insert each character
      for (int i = startIdx; i < str.length(); i++) {
        char currentChar = str.charAt(i);

        // If the character is not already in the current node, add it
        if (!currentNode.children.containsKey(currentChar)) {
          currentNode.children.put(currentChar, new TrieNode());
        }

        // Move to the next node
        currentNode = currentNode.children.get(currentChar);
      }

      // After inserting all characters, mark the end of the suffix
      currentNode.children.put(END_SYMBOL, null);
    }

    // Method to check if a string (substring) is contained in the trie
    public boolean contains(String str) {
      TrieNode currentNode = root;

      // Traverse the trie and check if the string exists
      for (int i = 0; i < str.length(); i++) {
        char currentChar = str.charAt(i);

        // If the character is not found, return false
        if (!currentNode.children.containsKey(currentChar)) {
          return false;
        }

        // Move to the next node
        currentNode = currentNode.children.get(currentChar);
      }

      // If traversal completed, the string exists in the trie
      return currentNode.children.containsKey(END_SYMBOL);
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    String input = "babc";
    SuffixTrie suffixTrie = new SuffixTrie(input);

    // Test if the substrings are in the suffix trie
    System.out.println(
        "Does the trie contain 'abc'? " + suffixTrie.contains("abc")); // Output: true
    System.out.println(
        "Does the trie contain 'babc'? " + suffixTrie.contains("babc")); // Output: true
    System.out.println("Does the trie contain 'ba'? " + suffixTrie.contains("ba")); // Output: true
    System.out.println(
        "Does the trie contain 'xyz'? " + suffixTrie.contains("xyz")); // Output: false
  }

  /*
   Time Complexity:
   - O(n^2), where n is the length of the input string. This is because we insert all suffixes, and for each suffix, we may traverse up to n characters.

   Space Complexity:
   - O(n^2), where n is the length of the input string. This is the space required to store all suffixes in the trie.
  */
}
