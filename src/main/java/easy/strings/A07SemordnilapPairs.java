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

package easy.strings;

import java.util.*;

public class A07SemordnilapPairs {

  // Function to find semordnilap pairs
  public static List<List<String>> findSemordnilapPairs(String[] words) {
    Set<String> wordSet = new HashSet<>(Arrays.asList(words)); // Create a set for fast lookup
    List<List<String>> semordnilapPairs = new ArrayList<>(); // List to store the semordnilap pairs

    // Iterate through the words
    for (String word : words) {
      String reversedWord =
          new StringBuilder(word).reverse().toString(); // Reverse the current word

      // Check if the reversed word exists in the set and is not the same as the current word
      if (wordSet.contains(reversedWord) && !reversedWord.equals(word)) {
        semordnilapPairs.add(
            Arrays.asList(word, reversedWord)); // Add the semordnilap pair to the list
        wordSet.remove(word); // Remove the word from the set to avoid duplicates
        wordSet.remove(reversedWord); // Remove the reversed word as well
      }
    }

    return semordnilapPairs; // Return the list of semordnilap pairs
  }

  public static void main(String[] args) {
    // Sample Input
    String[] words = {"diaper", "abc", "test", "cba", "repaid"};

    // Find and print semordnilap pairs
    List<List<String>> result = findSemordnilapPairs(words);
    System.out.println(result); // Expected Output: [["diaper", "repaid"], ["abc", "cba"]]
  }
}

/*
 * Approach:
 * - Use a set for fast lookups.
 * - For each word, compute its reverse and check if it exists in the set.
 * - If the reverse exists, it's a semordnilap pair. Add it to the result list and remove both the word and its reverse from the set to avoid duplicating pairs.
 * - Ensure that no word is paired with itself by checking that the reversed word is not equal to the original word.
 *
 * Time Complexity:
 * - O(n * m): Where `n` is the number of words in the list and `m` is the average length of the words. The time complexity is dominated by the reverse operation for each word and the set lookup.
 *
 * Space Complexity:
 * - O(n): For storing the words in a set, where `n` is the number of words.
 */
