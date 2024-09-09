package cools.arrays;

/*
 Problem: Length of Last Word

 Given a string s consisting of words and spaces, return the length of the last word in the string.
 A word is a maximal substring consisting of non-space characters only.

 Constraints:
 - 1 <= s.length <= 10^4
 - s consists of only English letters and spaces.
 - There will be at least one word in s.

 Example 1:
 Input: s = "Hello World"
 Output: 5
 Explanation: The last word is "World" with length 5.

 Example 2:
 Input: s = "   fly me   to   the moon  "
 Output: 4
 Explanation: The last word is "moon" with length 4.

 Example 3:
 Input: s = "luffy is still joyboy"
 Output: 6
 Explanation: The last word is "joyboy" with length 6.

 Solution Approach:
 1. Trim any trailing spaces from the string.
 2. Find the index of the last space in the string.
 3. The length of the last word is the difference between the string length and the index of the last space.
*/

public class A18LengthOfLastWord {

  // Function to return the length of the last word
  public int A18LengthOfLastWord(String s) {
    // Step 1: Trim trailing spaces (if any)
    s = s.trim();

    // Step 2: Find the index of the last space in the trimmed string
    int lastSpaceIndex = s.lastIndexOf(' ');

    // Step 3: The length of the last word is the length of the string minus the last space index -
    // 1
    return s.length() - lastSpaceIndex - 1;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A18LengthOfLastWord solution = new A18LengthOfLastWord();

    // Example 1
    String s1 = "Hello World";
    System.out.println("Length of Last Word: " + solution.A18LengthOfLastWord(s1)); // Output: 5

    // Example 2
    String s2 = "   fly me   to   the moon  ";
    System.out.println("Length of Last Word: " + solution.A18LengthOfLastWord(s2)); // Output: 4

    // Example 3
    String s3 = "luffy is still joyboy";
    System.out.println("Length of Last Word: " + solution.A18LengthOfLastWord(s3)); // Output: 6
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the input string. We traverse the string to find the last word.

   Space Complexity:
   - O(1), as we use a constant amount of extra space.
  */
}
