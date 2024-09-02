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
/*Brute Force Solution
Approach:
Check if Strings Are Equal: If the strings are already equal, return true.
Replace Operation: If the strings have the same length, check if they differ by exactly one character.
Add/Remove Operation: If the strings differ in length by exactly one character, check if the shorter string can become the longer string by adding or removing one character.
Time Complexity:
O(n): Where n is the length of the shorter string. We need to iterate through the strings once.
Space Complexity:
O(1): No additional space is required.*/

package medium;

public class OneEditAway {

  public static boolean isOneEditAway(String stringOne, String stringTwo) {
    // If the strings are equal, no edit is required
    if (stringOne.equals(stringTwo)) {
      return true;
    }

    int len1 = stringOne.length();
    int len2 = stringTwo.length();

    // If the length difference is more than 1, return false
    if (Math.abs(len1 - len2) > 1) {
      return false;
    }

    // If lengths are equal, check for a single replace operation
    if (len1 == len2) {
      return isOneReplaceAway(stringOne, stringTwo);
    }

    // If lengths differ by 1, check for add/remove operation
    if (len1 + 1 == len2) {
      return isOneAddRemoveAway(stringOne, stringTwo);
    } else if (len1 - 1 == len2) {
      return isOneAddRemoveAway(stringTwo, stringOne);
    }

    return false;
  }

  // Helper function to check if strings are one replace away
  private static boolean isOneReplaceAway(String str1, String str2) {
    boolean foundDifference = false;
    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != str2.charAt(i)) {
        if (foundDifference) {
          return false;
        }
        foundDifference = true;
      }
    }
    return true;
  }

  // Helper function to check if strings are one add/remove away
  private static boolean isOneAddRemoveAway(String shorter, String longer) {
    int i = 0, j = 0;
    while (i < shorter.length() && j < longer.length()) {
      if (shorter.charAt(i) != longer.charAt(j)) {
        if (i != j) {
          return false;
        }
        j++;
      } else {
        i++;
        j++;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    // Sample Inputs
    String stringOne = "hello";
    String stringTwo = "hollo";

    boolean result = isOneEditAway(stringOne, stringTwo);
    System.out.println(result); // Output: True
  }
}
