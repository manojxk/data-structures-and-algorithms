/*
 * Problem Statement:
 * Implement a Trie (Prefix Tree) with the following operations:
 * - `insert(String word)` inserts a word into the trie.
 * - `search(String word)` returns true if the word exists in the trie.
 * - `startsWith(String prefix)` returns true if there is any word in the trie that starts with the given prefix.
 *
 * Example:
 * Trie trie = new Trie();
 * trie.insert("apple");
 * trie.search("apple");   // return true
 * trie.search("app");     // return false
 * trie.startsWith("app"); // return true
 * trie.insert("app");
 * trie.search("app");     // return true
 */

class TrieNode {
    TrieNode[] children;  // Array of children nodes (26 lowercase English letters)
    boolean isEnd;        // Boolean flag to mark the end of a word

    // Constructor for TrieNode
    public TrieNode() {
        children = new TrieNode[26];  // 26 lowercase English letters
        isEnd = false;  // Initially, no node marks the end of a word
    }
}

public class Trie {

    private TrieNode root;

    // Initializes the Trie data structure
    public Trie() {
        root = new TrieNode();  // Root node of the trie
    }

    // Inserts a word into the trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';  // Map character to an index (0 for 'a', 1 for 'b', etc.)
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();  // Create a new node if it doesn't exist
            }
            node = node.children[index];  // Move to the next node
        }
        node.isEnd = true;  // Mark the end of the word
    }

    // Returns true if the word is in the trie
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);  // Find the last node corresponding to the word
        return node != null && node.isEnd;  // Return true if it's a valid word and marks the end
    }

    // Returns true if there is any word in the trie that starts with the given prefix
    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;  // Check if the prefix exists in the trie
    }

    // Helper method to search for a prefix or word in the trie
    private TrieNode searchPrefix(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';  // Map character to an index
            if (node.children[index] == null) {
                return null;  // If the node doesn't exist, the word or prefix doesn't exist
            }
            node = node.children[index];  // Move to the next node
        }
        return node;  // Return the last node corresponding to the word or prefix
    }

    // Main function to test the Trie implementation
    public static void main(String[] args) {
        Trie trie = new Trie();

        // Test insert and search
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // Output: true
        System.out.println(trie.search("app"));     // Output: false
        System.out.println(trie.startsWith("app")); // Output: true

        trie.insert("app");
        System.out.println(trie.search("app"));     // Output: true
    }

    /*
     * Time Complexity:
     * - `insert()`: O(m), where m is the length of the word being inserted.
     * - `search()`: O(m), where m is the length of the word being searched.
     * - `startsWith()`: O(m), where m is the length of the prefix being checked.
     *
     * Space Complexity:
     * O(m * n), where m is the maximum length of a word, and n is the number of words inserted.
     */
}
