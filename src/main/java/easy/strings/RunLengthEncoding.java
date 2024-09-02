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
/*Approach
Initialize Variables:

encodedString: A StringBuilder to accumulate the encoded string.
        currentRunLength: An integer to keep track of the current run length of consecutive identical characters.
Iterate Through the String:

For each character, check if it matches the previous character.
If it does, increment the currentRunLength.
If it doesn't or if the currentRunLength reaches 9 (to handle the case of runs longer than 9), append the current run to encodedString and reset the currentRunLength.
Handle the Last Run:

After the loop, append the final run of characters to the encodedString.
Return the Encoded String:

Convert the StringBuilder to a string and return it.
Time Complexity:
O(n): We iterate through each character in the string exactly once, where n is the length of the string.
Space Complexity:
O(n): The additional space used is proportional to the length of the input string because of the StringBuilder.*/

package easy.strings;

public class RunLengthEncoding {

  public static String runLengthEncode(String string) {
    StringBuilder encodedString = new StringBuilder();
    int currentRunLength = 1;

    // Iterate through the string to encode it
    for (int i = 1; i < string.length(); i++) {
      char currentChar = string.charAt(i);
      char previousChar = string.charAt(i - 1);

      // Check if the current character matches the previous one
      if (currentChar != previousChar || currentRunLength == 9) {
        encodedString.append(currentRunLength);
        encodedString.append(previousChar);
        currentRunLength = 1; // Reset run length
      } else {
        currentRunLength++;
      }
    }

    // Handle the last run
    encodedString.append(currentRunLength);
    encodedString.append(string.charAt(string.length() - 1));

    return encodedString.toString();
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
