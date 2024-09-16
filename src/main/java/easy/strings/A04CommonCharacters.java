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

package easy.strings;

import java.util.*;

public class A04CommonCharacters {

  // Function to find common characters in all strings
  public static List<Character> commonChars(List<String> strings) {
    if (strings == null || strings.size() == 0) return new ArrayList<>();

    // Start with a set of characters from the first string
    Set<Character> commonSet = new HashSet<>();
    for (char c : strings.get(0).toCharArray()) {
      commonSet.add(c); // Initialize with characters of the first string
    }

    // Iterate through each subsequent string
    for (int i = 1; i < strings.size(); i++) {
      Set<Character> currentSet = new HashSet<>();
      for (char c : strings.get(i).toCharArray()) {
        if (commonSet.contains(c)) {
          currentSet.add(c); // Retain only characters that are common
        }
      }
      commonSet = currentSet; // Update the common set with the intersection
    }

    return new ArrayList<>(commonSet); // Convert the set to a list
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

/*
 * Approach:
 * - The brute force approach involves using sets to find the common characters across all strings.
 * - We initialize a set with the characters from the first string.
 * - For each subsequent string, we retain only those characters that are present in the common set.
 * - This effectively gives us the intersection of characters present in all strings.
 *
 * Steps:
 * 1. Initialize a set of characters with the first string.
 * 2. For each subsequent string, retain only the characters that are present in the common set.
 * 3. After processing all strings, the set will contain only the characters common to all strings.
 * 4. Return the result as a list.
 *
 * Time Complexity:
 * - O(n * m): Where `n` is the number of strings and `m` is the average length of the strings. We iterate over each character in each string and check for its presence in the common set.
 *
 * Space Complexity:
 * - O(k): Where `k` is the number of unique characters in the first string. We store the common characters in a set.
 */
