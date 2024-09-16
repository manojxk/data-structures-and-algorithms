/*
 * Problem Statement:
 *
 * You are given two strings: one representing the available characters and the
 * other representing a document that you need to generate. Write a function that
 * determines whether it is possible to generate the document using the available
 * characters. The function should return `true` if the document can be generated,
 * and `false` otherwise.
 *
 * The document can be generated only if the frequency of each unique character
 * in the available characters string is greater than or equal to its frequency
 * in the document string. For instance, if you are given `characters = "abcabc"`
 * and `document = "aabbccc"`, the function should return `false` because one `c`
 * is missing.
 *
 * The document may include any characters, including special characters, capital
 * letters, numbers, and spaces. Note that it is always possible to generate an
 * empty string (`""`).
 *
 * Sample Input:
 * characters = "Bste!hetsi ogEAxpelrt x "
 * document = "AlgoExpert is the Best!"
 *
 * Sample Output:
 * true
 */

package easy.strings;

import java.util.HashMap;
import java.util.Map;

public class A05DocumentGenerator {

  // Optimized solution to check if the document can be generated
  /*
   * Approach:
   * - Use a frequency map (hash map) to count the occurrences of each character in the available characters string.
   * - Then, iterate through the document string and check if each character can be "used" by reducing its count in the map.
   *
   * Time Complexity:
   * - O(n + m): Where n is the length of the characters string and m is the length of the document. We traverse both strings once.
   *
   * Space Complexity:
   * - O(k): Where k is the number of unique characters in the characters string. The hash map stores the frequency of unique characters.
   */
  public static boolean canGenerateDocumentOptimized(String characters, String document) {
    Map<Character, Integer> charFrequencyMap = new HashMap<>();

    // Populate the frequency map with the characters string
    for (char c : characters.toCharArray()) {
      charFrequencyMap.put(c, charFrequencyMap.getOrDefault(c, 0) + 1);
    }

    // Verify if the document can be generated
    for (char c : document.toCharArray()) {
      // If a character in the document is missing or used up in the characters, return false
      if (!charFrequencyMap.containsKey(c) || charFrequencyMap.get(c) == 0) {
        return false;
      }
      charFrequencyMap.put(c, charFrequencyMap.get(c) - 1); // Reduce the count of the character
    }

    return true;
  }

  public static void main(String[] args) {
    // Sample Input
    String characters = "Bste!hetsi ogEAxpelrt x ";
    String document = "AlgoExpert is the Best!";

    // Check if the document can be generated
    boolean result = canGenerateDocumentOptimized(characters, document);

    // Output the result
    System.out.println(result); // Expected Output: true
  }
}
