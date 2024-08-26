/*
 * Problem Statement:
 *
 * You are given a list of unique strings. Write a function that returns a list of
 * semordnilap pairs.
 *
 * A semordnilap pair is defined as a set of different strings where the reverse
 * of one word is the same as the forward version of the other. For example, the
 * words "diaper" and "repaid" are a semordnilap pair, as are the words
 * "palindromes" and "semordnilap".
 *
 * The order of the returned pairs and the order of the strings within each pair
 * does not matter.
 *
 * Sample Input:
 * words = ["diaper", "abc", "test", "cba", "repaid"]
 *
 * Sample Output:
 * [["diaper", "repaid"], ["abc", "cba"]]
 */
/*Optimized Solution
Approach:
Set for Fast Lookup:
Convert the list of words into a set to allow for O(1) average-time complexity lookups.
For each word in the list, check if its reverse exists in the set.
If a match is found, it means the word and its reverse form a semordnilap pair.
Remove both the word and its reverse from the set to avoid duplicating pairs.
Order and Uniqueness:
Since the problem doesn't require pairs to be in any particular order, and the pairs themselves don't need to follow a specific order, the solution can directly append pairs as they are found.
Time Complexity:
O(n * m): Where n is the number of words in the list and m is the average length of the words. The time complexity is dominated by the reverse operation for each word and the set lookup.
Space Complexity:
O(n): For storing the words in a set.*/

package easy;

import java.util.*;

public class SemordnilapPairs {

  public static List<List<String>> findSemordnilapPairs(String[] words) {
    Set<String> wordSet = new HashSet<>(Arrays.asList(words));
    List<List<String>> semordnilapPairs = new ArrayList<>();

    for (String word : words) {
      String reversedWord = new StringBuilder(word).reverse().toString();

      // Check if the reverse exists in the set and is not the same as the current word
      if (wordSet.contains(reversedWord) && !reversedWord.equals(word)) {
        semordnilapPairs.add(Arrays.asList(word, reversedWord));
        // Remove both words to prevent duplicate pairs
        wordSet.remove(word);
        wordSet.remove(reversedWord);
      }
    }

    return semordnilapPairs;
  }

  public static void main(String[] args) {
    // Sample Input
    String[] words = {"diaper", "abc", "test", "cba", "repaid"};

    // Find and print semordnilap pairs
    List<List<String>> result = findSemordnilapPairs(words);
    System.out.println(result); // Expected Output: [["diaper", "repaid"], ["abc", "cba"]]
  }
}
