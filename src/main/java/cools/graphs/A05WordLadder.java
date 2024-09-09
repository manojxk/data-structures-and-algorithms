package cools.graphs;

/*
 Problem: Word Ladder

 A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 1. Every adjacent pair of words differs by a single letter.
 2. Every si (1 <= i <= k) is in wordList.
 3. sk == endWord.

 Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

 Example 1:
 Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 Output: 5
 Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> "cog", which is 5 words long.

 Example 2:
 Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 Output: 0
 Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.

 Constraints:
 - 1 <= beginWord.length <= 10
 - endWord.length == beginWord.length
 - 1 <= wordList.length <= 5000
 - wordList[i].length == beginWord.length
 - beginWord, endWord, and wordList[i] consist of lowercase English letters.
 - beginWord != endWord
 - All the words in wordList are unique.
 - The given wordList does not contain duplicates.

 Solution Approach:
 1. This is a shortest path problem in an unweighted graph where nodes are words, and edges exist between words that differ by one character.
 2. Use Breadth-First Search (BFS) to explore the shortest transformation sequence.
 3. BFS ensures that the first time you reach the endWord, it's via the shortest path.
*/

import java.util.*;

public class A05WordLadder {
  // Helper class to store word and level as a pair for BFS
  static class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey() {
      return key;
    }

    public V getValue() {
      return value;
    }
  }

  // Function to find the length of the shortest transformation sequence
  public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    // Convert the wordList into a set for fast lookup
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0; // If the endWord is not in the wordList, return 0

    // Queue for BFS where each element is a pair (currentWord, level)
    Queue<Pair<String, Integer>> queue = new LinkedList<>();
    queue.add(new Pair<>(beginWord, 1)); // Start with the beginWord and a level of 1

    // Perform BFS
    while (!queue.isEmpty()) {
      Pair<String, Integer> current = queue.poll();
      String currentWord = current.getKey();
      int level = current.getValue();

      // If we reach the endWord, return the level (distance)
      if (currentWord.equals(endWord)) {
        return level;
      }

      // Try all possible single-letter transformations
      for (int i = 0; i < currentWord.length(); i++) {
        char[] wordChars = currentWord.toCharArray();

        for (char c = 'a'; c <= 'z'; c++) {
          char originalChar = wordChars[i];
          wordChars[i] = c;
          String newWord = new String(wordChars);

          // If the transformed word is in the wordSet, add it to the queue and remove from wordSet
          if (wordSet.contains(newWord)) {
            queue.add(new Pair<>(newWord, level + 1));
            wordSet.remove(newWord); // Mark the word as visited
          }

          // Restore the original character
          wordChars[i] = originalChar;
        }
      }
    }

    // If no transformation sequence exists, return 0
    return 0;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05WordLadder solution = new A05WordLadder();

    // Example 1
    String beginWord1 = "hit";
    String endWord1 = "cog";
    List<String> wordList1 = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    System.out.println(
        "Shortest Transformation Sequence Length: "
            + solution.ladderLength(beginWord1, endWord1, wordList1)); // Output: 5

    // Example 2
    String beginWord2 = "hit";
    String endWord2 = "cog";
    List<String> wordList2 = Arrays.asList("hot", "dot", "dog", "lot", "log");
    System.out.println(
        "Shortest Transformation Sequence Length: "
            + solution.ladderLength(beginWord2, endWord2, wordList2)); // Output: 0
  }

  /*
   Time Complexity:
   - O(M^2 * N), where M is the length of each word and N is the number of words in the wordList.
     We process each word and for each word, we try all possible single-character transformations.

   Space Complexity:
   - O(M * N), where M is the length of each word and N is the number of words in the wordList.
     This is the space required for the BFS queue and the word set.
  */
}
