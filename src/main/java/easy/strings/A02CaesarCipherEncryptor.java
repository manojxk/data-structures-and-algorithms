/*
 * Problem Statement:
 *
 * You are given a non-empty string of lowercase letters and a non-negative
 * integer representing a key. Write a function that returns a new string
 * obtained by shifting every letter in the input string by `k` positions
 * in the alphabet, where `k` is the key.
 *
 * Note:
 * - The letters should "wrap" around the alphabet; in other words, the
 *   letter 'z' shifted by one position should return the letter 'a'.
 *
 * Sample Input:
 * string = "xyz"
 * key = 2
 *
 * Sample Output:
 * "zab"
 */

package easy.strings;

public class A02CaesarCipherEncryptor {

  // Function to perform the Caesar Cipher encryption
  public static String caesarCipherEncryptor(String str, int key) {
    StringBuilder newString = new StringBuilder();
    int newKey = key % 26; // Ensure the key wraps around after 26 letters

    // Iterate through each character in the input string
    for (char letter : str.toCharArray()) {
      char newLetter = getNewLetter(letter, newKey); // Get the new shifted character
      newString.append(newLetter); // Append the new character to the result string
    }

    return newString.toString(); // Return the final encrypted string
  }

  // Helper function to get the new shifted letter
  private static char getNewLetter(char letter, int key) {
    int newLetterCode = letter + key;

    // Case 1: No wrap-around needed
    if (newLetterCode <= 'z') {
      return (char) newLetterCode; // If the new character is still within 'a' to 'z'
    }
    // Case 2: Wrap-around needed
    else {
      return (char) ('a' + newLetterCode - 'z' - 1); // Wrap around to the beginning of the alphabet
    }
  }

  public static void main(String[] args) {
    // Sample Input
    String str = "xyz";
    int key = 2;

    // Perform Caesar Cipher encryption
    String result = caesarCipherEncryptor(str, key);

    // Output the result
    System.out.println(result); // Expected Output: "zab"
  }
}

/*
 * Approach:
 * - We need to shift each character of the string by `key` positions.
 * - If the shifted character exceeds 'z', we wrap around to the beginning of the alphabet ('a').
 * - To handle keys larger than 26, we reduce the key using modulo 26 since there are 26 letters in the alphabet.
 *
 * Steps:
 * 1. Initialize a StringBuilder to build the resulting string.
 * 2. Iterate through each character of the input string.
 * 3. For each character, calculate its new shifted value using ASCII codes and handle the wrap-around when necessary.
 * 4. Append the new shifted character to the result.
 * 5. Return the final encrypted string.
 *
 * Time Complexity:
 * - O(n): We iterate through each character in the string exactly once, where n is the length of the string.
 *
 * Space Complexity:
 * - O(n): We use additional space for the result string which stores the modified characters.
 */

/*
 * Case 1: No Wrap-Around Needed (newLetterCode <= 'z'):
 * - If the shifted character is within the bounds of 'a' to 'z', we simply return the new character.
 * - Example: For 'x' with key 2, 'x' becomes 'z', and since 'z' is within bounds, we return 'z'.
 *
 * Case 2: Wrap-Around Needed (newLetterCode > 'z'):
 * - If the newLetterCode exceeds 'z', we calculate how far it exceeds and wrap around to 'a'.
 * - Example: For 'y' with key 3, 'y' becomes 124, but since it exceeds 'z', we calculate (124 - 'z' - 1) = 1, which gives 'b'.
 */
