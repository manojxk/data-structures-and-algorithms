/*
 * Problem Statement:
 * You're given two strings, `stringOne` and `stringTwo`. Write a function to determine if these two strings
 * can be made equal using only one edit. There are three possible edits:
 *
 * 1. Replace: One character in one string is swapped for a different character.
 * 2. Add: One character is added at any index in one string.
 * 3. Remove: One character is removed at any index in one string.
 *
 * If the strings are the same, the function should return true.
 *
 * Example:
 *
 * Sample Input:
 * stringOne = "hello"
 * stringTwo = "hollo"
 *
 * Sample Output:
 * True  // A single replace at index 1 of either string can make the strings equal
 */

/*
 * Brute Force Solution
 *
 * Approach:
 * 1. Check if the strings are equal. If they are, no edit is needed and the function returns true.
 * 2. If the lengths of the two strings differ by more than 1, return false, since more than one edit is needed.
 * 3. If the strings have the same length, check if they differ by exactly one character for a replace operation.
 * 4. If the lengths differ by 1, check if one string can be made equal to the other by either adding or removing a single character.
 *
 * Time Complexity: O(n)
 * - Where `n` is the length of the shorter string. We iterate through the strings once to compare them.
 *
 * Space Complexity: O(1)
 * - Constant space is used since no additional space is required besides a few variables.
 */

package medium.strings;

public class A06OneEditAway {

  // Main function to determine if two strings are one edit away
  public static boolean isOneEditAway(String stringOne, String stringTwo) {
    // Step 1: If the strings are equal, no edit is required
    if (stringOne.equals(stringTwo)) {
      return true;
    }

    int len1 = stringOne.length();
    int len2 = stringTwo.length();

    // Step 2: If the length difference is greater than 1, more than one edit is required, so return false
    if (Math.abs(len1 - len2) > 1) {
      return false;
    }

    // Step 3: If lengths are the same, check for a single replace operation
    if (len1 == len2) {
      return isOneReplaceAway(stringOne, stringTwo);
    }

    // Step 4: If lengths differ by 1, check for a single add/remove operation
    if (len1 + 1 == len2) {
      return isOneAddRemoveAway(stringOne, stringTwo); // stringTwo is longer
    } else if (len1 - 1 == len2) {
      return isOneAddRemoveAway(stringTwo, stringOne); // stringOne is longer
    }

    return false;
  }

  // Helper function to check if two strings differ by exactly one replacement
  private static boolean isOneReplaceAway(String str1, String str2) {
    boolean foundDifference = false;

    // Traverse both strings character by character
    for (int i = 0; i < str1.length(); i++) {
      // If a mismatch is found
      if (str1.charAt(i) != str2.charAt(i)) {
        // If this is the second mismatch, return false
        if (foundDifference) {
          return false;
        }
        foundDifference = true; // Mark the first mismatch
      }
    }

    return true;
  }

  // Helper function to check if two strings differ by one add/remove operation
  private static boolean isOneAddRemoveAway(String shorter, String longer) {
    int i = 0, j = 0;

    // Traverse both strings character by character
    while (i < shorter.length() && j < longer.length()) {
      // If a mismatch is found
      if (shorter.charAt(i) != longer.charAt(j)) {
        // If the indices are already misaligned, return false
        if (i != j) {
          return false;
        }
        j++; // Move forward in the longer string
      } else {
        i++; // Move forward in both strings
        j++;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    // Test Case 1
    String stringOne = "hello";
    String stringTwo = "hollo";
    boolean result = isOneEditAway(stringOne, stringTwo);
    System.out.println(result);  // Output: True

    // Test Case 2
    String stringOne2 = "hello";
    String stringTwo2 = "helo";
    boolean result2 = isOneEditAway(stringOne2, stringTwo2);
    System.out.println(result2);  // Output: True (one remove operation)

    // Test Case 3
    String stringOne3 = "hello";
    String stringTwo3 = "hello!";
    boolean result3 = isOneEditAway(stringOne3, stringTwo3);
    System.out.println(result3);  // Output: False (needs more than one edit)
  }
}
