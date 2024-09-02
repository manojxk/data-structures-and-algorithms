/*
 * Problem Statement:
 *
 * You are given a non-empty list of non-empty strings. Your task is to write a
 * function that returns a list of characters that are common to all strings
 * in the list, ignoring multiplicity. This means each character that appears in
 * all strings will only appear once in the output list.
 *
 * Note:
 * - The strings can contain any characters, including non-alphanumeric ones.
 * - The order of characters in the output list does not matter.
 *
 * Sample Input:
 * strings = ["abc", "bcd", "cbaccd"]
 *
 * Sample Output:
 * ["b", "c"] // The characters could be ordered differently.
 */
/*Brute Force Solution
Approach:
The brute force solution involves the following steps:

Convert the first string into a set of characters.
For each subsequent string, retain only the characters that are common with the set.
After processing all strings, the set will contain only the characters that are common to all strings.
Time Complexity:
O(n * m): Where n is the number of strings and m is the average length of the strings. This is because we iterate over each character in each string.
Space Complexity:
O(k): Where k is the number of unique characters in the first string.*/

package easy.strings;

import java.util.*;

public class CommonCharacters {

  // Function to find common characters in all strings
  public static List<Character> commonChars(List<String> strings) {
    if (strings == null || strings.size() == 0) return new ArrayList<>();

    // Start with a set of characters from the first string
    Set<Character> commonSet = new HashSet<>();
    for (char c : strings.get(0).toCharArray()) {
      commonSet.add(c);
    }

    // Iterate through each subsequent string
    for (int i = 1; i < strings.size(); i++) {
      Set<Character> currentSet = new HashSet<>();
      for (char c : strings.get(i).toCharArray()) {
        if (commonSet.contains(c)) {
          currentSet.add(c); // Retain only characters that are common
        }
      }
      commonSet = currentSet; // Update the common set
    }

    return new ArrayList<>(commonSet);
  }

  public static void main(String[] args) {
    // Sample Input
    List<String> strings = Arrays.asList("abc", "bcd", "cbaccd");

    // Calculate common characters
    List<Character> result = commonChars(strings);

    // Output the result
    System.out.println(result); // Expected Output: [b, c]
  }
}
