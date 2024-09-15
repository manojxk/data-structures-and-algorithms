package hard.strings;

/*
 Problem: Longest Substring Without Duplication

 Given a string s, find the length of the longest substring without repeating characters. Additionally, print the longest substring itself.

 Example:

 Input: "abcabcbb"
 Output: 3
 Longest Substring: "abc"

 Explanation:
 The longest substring without repeating characters is "abc", which has a length of 3.

 Another Example:

 Input: "bbbbb"
 Output: 1
 Longest Substring: "b"

 Explanation:
 The longest substring without repeating characters is "b", which has a length of 1.
*/

/*
 Solution Steps:

 1. Use two pointers `i` and `j` to represent the sliding window.
 2. Use a `HashSet` to store the characters in the current window.
 3. As you move the `j` pointer to expand the window, check if the character at `j` is already in the set:
    a) If it is not in the set, add the character to the set and update the maximum length (`ans`).
    b) If it is in the set, remove characters starting from `i` until the duplicate is removed.
 4. Track the starting index of the longest substring (`maxStartIdx`) when you update the maximum length.
 5. After traversing the string, print the longest substring and return its length.
*/

import java.util.HashSet;

public class A01LongestSubstringWithoutDuplication {

  public int lengthOfLongestSubstring(String s) {
    // HashSet to store characters of the current window (substring)
    HashSet<Character> st = new HashSet<>();

    // Two pointers i and j to maintain the window, and ans to store the result
    int i = 0, j = 0, ans = 0;
    int maxStartIdx = 0; // To store the starting index of the longest substring

    // Traverse the string using the j pointer
    while (j < s.length()) {
      // If the character at j is not in the HashSet, add it to the set
      if (!st.contains(s.charAt(j))) {
        st.add(s.charAt(j));

        // Update the maximum length and the starting index of the longest substring
        if (ans < j - i + 1) {
          maxStartIdx = i;
          ans = j - i + 1;
        }

        // Move the right pointer (j) forward
        j++;
      } else {
        // If the character is already in the set, remove the character at i
        // and move the left pointer (i) forward
        st.remove(s.charAt(i));
        i++;
      }
    }

    // Print the longest substring without duplicates
    System.out.println(
        "Longest Substring Without Duplication: " + s.substring(maxStartIdx, maxStartIdx + ans));

    return ans; // Return the length of the longest substring without duplicates
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01LongestSubstringWithoutDuplication solution = new A01LongestSubstringWithoutDuplication();

    // Example 1: "abcabcbb"
    String input = "abcabcbb";
    System.out.println(
        "Length of Longest Substring: "
            + solution.lengthOfLongestSubstring(input)); // Output: 3, Substring: "abc"

    // Example 2: "bbbbb"
    input = "bbbbb";
    System.out.println(
        "Length of Longest Substring: "
            + solution.lengthOfLongestSubstring(input)); // Output: 1, Substring: "b"

    // Example 3: "pwwkew"
    input = "pwwkew";
    System.out.println(
        "Length of Longest Substring: "
            + solution.lengthOfLongestSubstring(input)); // Output: 3, Substring: "wke"
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the input string. Each character is visited at most twice (once by the left pointer i and once by the right pointer j).

   Space Complexity:
   - O(min(n, m)), where n is the length of the string and m is the size of the character set. The HashSet stores the unique characters in the current window.
  */
}
