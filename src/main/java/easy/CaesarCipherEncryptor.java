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
/*Brute Force Solution
Approach:
Iterate through Each Character: For each character in the string, convert it to its corresponding ASCII value.
Shift the Character: Add the key to this ASCII value, considering the wrap-around for letters. If the new ASCII value exceeds 'z', calculate the correct wrap-around.
Construct the New String: Convert the shifted ASCII values back to characters and append them to a result string.
Time Complexity:
O(n): We iterate through each character in the string exactly once, where n is the length of the string.
Space Complexity:
O(n): We use additional space for the result string which stores the modified characters.*/

package easy;

public class CaesarCipherEncryptor {

  // Function to perform the Caesar Cipher encryption
  public static String caesarCipherEncryptor(String str, int key) {
    StringBuilder newString = new StringBuilder();
    int newKey = key % 26; // To handle cases where key is greater than 26

    // Iterate through each character in the input string
    for (char letter : str.toCharArray()) {
      char newLetter = getNewLetter(letter, newKey);
      newString.append(newLetter);
    }

    return newString.toString();
  }

  // Helper function to get the new shifted letter
  private static char getNewLetter(char letter, int key) {
    int newLetterCode = letter + key;

    // Handle wrap-around for letters after 'z'
    if (newLetterCode <= 'z') {
      return (char) newLetterCode;
    } else {
      return (char) ('a' + newLetterCode - 'z' - 1);
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
Case 1: No Wrap-Around Needed (newLetterCode <= 'z'):

If the shifted character is still within the bounds of the alphabet ('a' to 'z'), it simply returns the new character by converting the newLetterCode back to a char.
Example:
If letter is 'x' (ASCII 120) and key is 2, newLetterCode becomes 122 ('z'). Since 122 is within the range of lowercase letters, it returns 'z'.
Case 2: Wrap-Around Needed (newLetterCode > 'z'):

If the newLetterCode exceeds the ASCII value of 'z', the algorithm needs to wrap around to the beginning of the alphabet ('a').

The wrap-around calculation is done by determining how far newLetterCode has exceeded 'z', and then starting from 'a':

java
Copy code
return (char) ('a' + newLetterCode - 'z' - 1);
Here's how it works:

newLetterCode - 'z' - 1 gives the number of positions beyond 'z'.
Adding this to 'a' starts the count from the beginning of the alphabet.
The result is the correct character after the wrap-around.
        Example:

If letter is 'y' (ASCII 121) and key is 3, newLetterCode becomes 124, which exceeds 'z' (ASCII 122) by 2 positions.
        So, 124 - 122 - 1 = 1, and 'a' + 1 = 'b'.
The correct shifted character is 'b'.*/
