/*
 * Problem Statement:
 *
 * Write a function that takes in a non-empty string and returns its run-length encoding.
 *
 * Run-length encoding is a form of lossless data compression where consecutive
 * identical characters (runs) are stored as a single data value and count.
 * For example, "AAA" would be encoded as "3A".
 *
 * Constraints:
 * - The input string can contain all sorts of special characters, including numbers.
 * - If a run of identical characters is 10 or more characters long,
 *   it should be encoded in a split fashion. For instance, "AAAAAAAAAAAA"
 *   (12 A's) should be encoded as "9A3A".
 *
 * Sample Input:
 * string = "AAAAAAAAAAAAABBCCCCDD"
 *
 * Sample Output:
 * "9A4A2B4C2D"
 */

package easy.strings;

public class A03RunLengthEncoding {

  // Function to perform run-length encoding
  public static String runLengthEncode(String string) {
    StringBuilder encodedString = new StringBuilder(); // To build the encoded string
    int currentRunLength = 1; // To track the length of consecutive characters

    // Iterate through the string starting from the second character
    for (int i = 1; i < string.length(); i++) {
      char currentChar = string.charAt(i); // Current character
      char previousChar = string.charAt(i - 1); // Previous character

      // If current character doesn't match the previous or the run length reaches 9, append the
      // current run to encoded string
      if (currentChar != previousChar || currentRunLength == 9) {
        encodedString.append(currentRunLength); // Append the run length
        encodedString.append(previousChar); // Append the character
        currentRunLength = 1; // Reset the run length for the new character
      } else {
        currentRunLength++; // Increment the run length if characters match
      }
    }

    // Append the final run (handling the last group of characters)
    encodedString.append(currentRunLength);
    encodedString.append(string.charAt(string.length() - 1));

    return encodedString.toString(); // Return the encoded string
  }

  public static void main(String[] args) {
    // Sample Input
    String string = "AAAAAAAAAAAAABBCCCCDD";

    // Perform Run-Length Encoding
    String result = runLengthEncode(string);

    // Output the result
    System.out.println(result); // Expected Output: "9A4A2B4C2D"
  }
}

/*
 * Approach:
 * - We need to compress consecutive identical characters in the input string using run-length encoding.
 * - To handle sequences longer than 9 characters, split the run into multiple smaller runs.
 *
 * Steps:
 * 1. Initialize a StringBuilder to accumulate the encoded string.
 * 2. Iterate through the input string:
 *    - If the current character matches the previous character, increment the run length.
 *    - If the run length reaches 9 or the current character differs from the previous one, append the run to the encoded string and reset the run length.
 * 3. Handle the last sequence of characters after the loop finishes.
 * 4. Return the encoded string.
 *
 * Time Complexity:
 * - O(n): We iterate through each character in the string exactly once, where n is the length of the string.
 *
 * Space Complexity:
 * - O(n): We use additional space for the StringBuilder object, which stores the encoded string.
 */
