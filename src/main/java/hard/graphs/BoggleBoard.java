package hard.graphs;

/**
 * Problem Statement:
 *
 * <p>You are given a two-dimensional matrix (boggle board) of potentially unequal height and width
 * containing letters. The matrix represents a boggle board. Additionally, you are provided with a
 * list of words.
 *
 * <p>Your task is to write a function that returns an array of all the words contained in the
 * boggle board. A word is considered to be in the boggle board if it can be formed by connecting
 * adjacent letters in the board. These connections can be horizontal, vertical, or diagonal, and a
 * single letter at a given position cannot be used more than once to form a word. However, the same
 * word can overlap or use the same letters from different positions.
 *
 * <p>The function should return all the words from the provided list that are present in the boggle
 * board. The words can be returned in any order.
 *
 * <p>Sample Input: board = [ ["t", "h", "i", "s", "i", "s", "a"], ["s", "i", "m", "p", "l", "e",
 * "x"], ["b", "x", "x", "x", "x", "e", "b"], ["x", "o", "g", "g", "l", "x", "o"], ["x", "x", "x",
 * "D", "T", "r", "a"], ["R", "E", "P", "E", "A", "d", "x"], ["x", "x", "x", "x", "x", "x", "x"],
 * ["N", "O", "T", "R", "E", "-", "P"], ["x", "x", "D", "E", "T", "A", "E"], ], words = [ "this",
 * "is", "not", "a", "simple", "boggle", "board", "test", "REPEATED", "NOTRE-PEATED", ]
 *
 * <p>Sample Output: ["this", "is", "a", "simple", "boggle", "board", "NOTRE-PEATED"] // The order
 * of words doesn't matter.
 */
import java.util.*;

public class BoggleBoard {

  // Brute Force Approach:
  // For each word in the list, search for the word in the board using DFS.
  // Time Complexity: O(w * b * 8^l), where 'w' is the number of words, 'b' is the number of cells
  // in the board, and 'l' is the length of the word.
  // Space Complexity: O(b), for the recursion stack.
  public static List<String> findWordsBruteForce(char[][] board, String[] words) {
    List<String> foundWords = new ArrayList<>();
    for (String word : words) {
      if (isWordInBoard(board, word)) {
        foundWords.add(word);
      }
    }
    return foundWords;
  }

  private static boolean isWordInBoard(char[][] board, String word) {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (board[i][j] == word.charAt(0) && dfs(board, word, i, j, 0)) {
          return true;
        }
      }
    }
    return false;
  }

  private static boolean dfs(char[][] board, String word, int i, int j, int index) {
    if (index == word.length()) {
      return true;
    }
    if (i < 0
        || i >= board.length
        || j < 0
        || j >= board[0].length
        || board[i][j] != word.charAt(index)) {
      return false;
    }
    char temp = board[i][j];
    board[i][j] = '#'; // Mark as visited
    boolean found =
        dfs(board, word, i - 1, j, index + 1)
            || dfs(board, word, i + 1, j, index + 1)
            || dfs(board, word, i, j - 1, index + 1)
            || dfs(board, word, i, j + 1, index + 1)
            || dfs(board, word, i - 1, j - 1, index + 1)
            || dfs(board, word, i - 1, j + 1, index + 1)
            || dfs(board, word, i + 1, j - 1, index + 1)
            || dfs(board, word, i + 1, j + 1, index + 1);
    board[i][j] = temp; // Unmark
    return found;
  }

  // Optimized Approach using Trie:
  // Time Complexity: O(b * 8^l + n), where 'b' is the number of cells, 'l' is the average word
  // length, and 'n' is the total number of letters in the Trie.
  // Space Complexity: O(n) - Space required for storing the Trie structure.
  public static List<String> findWordsOptimized(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String word : words) {
      trie.insert(word);
    }

    Set<String> foundWords = new HashSet<>();
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        dfsOptimized(board, trie.root, i, j, foundWords);
      }
    }

    return new ArrayList<>(foundWords);
  }

  private static void dfsOptimized(
      char[][] board, TrieNode node, int i, int j, Set<String> foundWords) {
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] == '#') {
      return;
    }
    char letter = board[i][j];
    if (!node.children.containsKey(letter)) {
      return;
    }

    TrieNode childNode = node.children.get(letter);
    if (childNode.word != null) {
      foundWords.add(childNode.word);
      childNode.word = null; // To avoid duplicates
    }

    char temp = board[i][j];
    board[i][j] = '#'; // Mark as visited
    dfsOptimized(board, childNode, i - 1, j, foundWords); // Up
    dfsOptimized(board, childNode, i + 1, j, foundWords); // Down
    dfsOptimized(board, childNode, i, j - 1, foundWords); // Left
    dfsOptimized(board, childNode, i, j + 1, foundWords); // Right
    dfsOptimized(board, childNode, i - 1, j - 1, foundWords); // Top-Left Diagonal
    dfsOptimized(board, childNode, i - 1, j + 1, foundWords); // Top-Right Diagonal
    dfsOptimized(board, childNode, i + 1, j - 1, foundWords); // Bottom-Left Diagonal
    dfsOptimized(board, childNode, i + 1, j + 1, foundWords); // Bottom-Right Diagonal
    board[i][j] = temp; // Unmark
  }

  // Trie class for optimized word search
  static class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    String word = null;
  }

  static class Trie {
    TrieNode root = new TrieNode();

    public void insert(String word) {
      TrieNode currentNode = root;
      for (char letter : word.toCharArray()) {
        currentNode.children.putIfAbsent(letter, new TrieNode());
        currentNode = currentNode.children.get(letter);
      }
      currentNode.word = word;
    }
  }

  // Main function to test both solutions
  public static void main(String[] args) {
    char[][] board = {
      {'t', 'h', 'i', 's', 'i', 's', 'a'},
      {'s', 'i', 'm', 'p', 'l', 'e', 'x'},
      {'b', 'x', 'x', 'x', 'x', 'e', 'b'},
      {'x', 'o', 'g', 'g', 'l', 'x', 'o'},
      {'x', 'x', 'x', 'D', 'T', 'r', 'a'},
      {'R', 'E', 'P', 'E', 'A', 'd', 'x'},
      {'x', 'x', 'x', 'x', 'x', 'x', 'x'},
      {'N', 'O', 'T', 'R', 'E', '-', 'P'},
      {'x', 'x', 'D', 'E', 'T', 'A', 'E'},
    };

    String[] words = {
      "this", "is", "not", "a", "simple", "boggle", "board", "test", "REPEATED", "NOTRE-PEATED"
    };

    // Brute Force Solution
    List<String> resultBruteForce = findWordsBruteForce(board, words);
    System.out.println("Brute Force Solution: " + resultBruteForce);

    // Optimized Trie Solution
    List<String> resultOptimized = findWordsOptimized(board, words);
    System.out.println("Optimized Trie Solution: " + resultOptimized);
  }
}
