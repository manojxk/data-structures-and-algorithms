package cools.arrays;

/*
 Problem: Longest Common Prefix

 Write a function to find the longest common prefix string amongst an array of strings.
 If there is no common prefix, return an empty string "".

 Constraints:
 - 1 <= strs.length <= 200
 - 0 <= strs[i].length <= 200
 - strs[i] consists of only lowercase English letters.

 Example 1:
 Input: strs = ["flower", "flow", "flight"]
 Output: "fl"
 Explanation: The longest common prefix is "fl" as it is common to all strings.

 Example 2:
 Input: strs = ["dog", "racecar", "car"]
 Output: ""
 Explanation: There is no common prefix among the input strings.

 Solution Approach:
 1. Use the first string as the initial prefix.
 2. Iterate through the remaining strings and gradually shorten the prefix until it matches the beginning of each string.
 3. If no common prefix exists, return an empty string.
*/

public class A19LongestCommonPrefix {

  // Function to find the longest common prefix
  public String A19LongestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) {
      return ""; // Return an empty string if the input array is empty
    }

    // Step 1: Take the first string as the initial prefix
    String prefix = strs[0];

    // Step 2: Iterate through the remaining strings
    for (int i = 1; i < strs.length; i++) {
      // Step 3: Shorten the prefix until it matches the start of the current string
      while (strs[i].indexOf(prefix) != 0) {
        prefix = prefix.substring(0, prefix.length() - 1);
        if (prefix.isEmpty()) {
          return ""; // If there's no common prefix, return an empty string
        }
      }
    }

    return prefix; // Return the final common prefix
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A19LongestCommonPrefix solution = new A19LongestCommonPrefix();

    // Example 1
    String[] strs1 = {"flower", "flow", "flight"};
    System.out.println(
        "Longest Common Prefix: " + solution.A19LongestCommonPrefix(strs1)); // Output: "fl"

    // Example 2
    String[] strs2 = {"dog", "racecar", "car"};
    System.out.println(
        "Longest Common Prefix: " + solution.A19LongestCommonPrefix(strs2)); // Output: ""
  }

  /*
   Time Complexity:
   - O(S), where S is the sum of all characters in all strings. In the worst case, every character is checked at least once.

   Space Complexity:
   - O(1), since we only use a few extra variables and no additional data structures proportional to the input size.
  */
}
