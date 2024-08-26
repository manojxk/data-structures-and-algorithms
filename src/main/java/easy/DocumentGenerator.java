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
/*Brute Force Solution
Approach:
The brute force approach involves counting the frequency of each character in both the characters string and the document string. Then, we check whether each character in the document string is present in the characters string with equal or greater frequency.

Time Complexity:
O(n * m): Where n is the length of the document string and m is the length of the characters string. For each character in the document, we count its occurrences in the characters string.
Space Complexity:
O(1): No extra space is required beyond a few variables for counting (assuming character sets are bounded, e.g., ASCII).*/

/*
Optimized Solution
Approach:
A more efficient approach involves using a frequency map (hash map) to count the frequency of each character in the characters string and then verifying if each character in the document string is present in the map with the required frequency.

Time Complexity:
O(n + m): Where n is the length of the characters string and m is the length of the document string. We traverse both strings once.
Space Complexity:
O(k): Where k is the number of unique characters in the characters string.*/
package easy;

import java.util.HashMap;
import java.util.Map;

public class DocumentGenerator {

  // Brute force solution to check if the document can be generated
  public static boolean canGenerateDocument(String characters, String document) {
    // Iterate through each character in the document string
    for (int i = 0; i < document.length(); i++) {
      char docChar = document.charAt(i);
      int docCharCount = countCharacterFrequency(document, docChar);
      int charCount = countCharacterFrequency(characters, docChar);

      // If the character count in the document exceeds that in the characters, return false
      if (docCharCount > charCount) {
        return false;
      }
    }

    // If all characters meet the required frequency, return true
    return true;
  }

  // Helper function to count the frequency of a character in a string
  private static int countCharacterFrequency(String str, char targetChar) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == targetChar) {
        count++;
      }
    }
    return count;
  }

  // Optimized solution to check if the document can be generated
  public static boolean canGenerateDocumentOptimized(String characters, String document) {
    Map<Character, Integer> charFrequencyMap = new HashMap<>();

    // Populate the frequency map with the characters string
    for (char c : characters.toCharArray()) {
      charFrequencyMap.put(c, charFrequencyMap.getOrDefault(c, 0) + 1);
    }

    // Verify if the document can be generated
    for (char c : document.toCharArray()) {
      if (!charFrequencyMap.containsKey(c) || charFrequencyMap.get(c) == 0) {
        return false;
      }
      charFrequencyMap.put(c, charFrequencyMap.get(c) - 1);
    }

    return true;
  }

  public static void main(String[] args) {
    // Sample Input
    String characters = "Bste!hetsi ogEAxpelrt x ";
    String document = "AlgoExpert is the Best!";

    // Check if the document can be generated
    boolean result = canGenerateDocument(characters, document);

    // Output the result
    System.out.println(result); // Expected Output: true
  }
}
