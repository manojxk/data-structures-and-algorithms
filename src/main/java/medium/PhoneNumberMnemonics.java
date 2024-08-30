/*
 * Problem Statement:
 * Given a stringified phone number of any non-zero length, write a function that returns all mnemonics
 * for this phone number in any order.
 *
 * A mnemonic is defined as a pattern of letters, ideas, or associations that assist in remembering something.
 * For this problem, a valid mnemonic may only contain letters and the digits 0 and 1.
 * If a digit is able to be represented by a letter, then it must be.
 * Digits 0 and 1 are the only two digits that don't have letter representations on the keypad.
 *
 * Example:
 * Input: phoneNumber = "1905"
 * Output:
 * [
 *  "1w0j", "1w0k", "1w0l",
 *  "1x0j", "1x0k", "1x0l",
 *  "1y0j", "1y0k", "1y0l",
 *  "1z0j", "1z0k", "1z0l"
 * ]
 *
 * Note: The mnemonics could be ordered differently.
 */

package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PhoneNumberMnemonics {

  // Mapping from digits to their corresponding letters
  private Map<Character, String> digitToLetters = new HashMap<>();
  // List to store the final result of combinations
  private List<String> result = new ArrayList<>();

  // Backtracking function to generate combinations
  private void backtrack(int pos, StringBuilder currentCombination, String digits) {
    // Base case: If the current combination is the same length as the digits string
    if (currentCombination.length() == digits.length()) {
      result.add(currentCombination.toString());
      return;
    }

    // Loop through each character in the digits string starting from 'pos'
    for (int i = pos; i < digits.length(); i++) {
      char digit = digits.charAt(i);
      String letters = digitToLetters.get(digit);

      // Loop through each letter corresponding to the current digit
      for (int j = 0; j < letters.length(); j++) {
        currentCombination.append(letters.charAt(j)); // Add letter to the current combination
        backtrack(i + 1, currentCombination, digits); // Recursive call to backtrack
        currentCombination.deleteCharAt(
            currentCombination.length() - 1); // Backtrack by removing the last letter
      }
    }
  }

  // Main function to generate all letter combinations for a given string of digits
  public List<String> letterCombinations(String digits) {
    if (digits.length() == 0) {
      return new ArrayList<>(); // Return an empty list if the input is empty
    }

    // Initialize the mapping from digits to their corresponding letters
    digitToLetters.put('2', "abc");
    digitToLetters.put('3', "def");
    digitToLetters.put('4', "ghi");
    digitToLetters.put('5', "jkl");
    digitToLetters.put('6', "mno");
    digitToLetters.put('7', "pqrs");
    digitToLetters.put('8', "tuv");
    digitToLetters.put('9', "wxyz");

    // Start the backtracking process with an empty combination and at position 0
    backtrack(0, new StringBuilder(), digits);

    return result; // Return the final list of combinations
  }

  // Example usage
  public static void main(String[] args) {
    PhoneNumberMnemonics solution = new PhoneNumberMnemonics();
    String digits = "23";
    List<String> combinations = solution.letterCombinations(digits);
    System.out.println(combinations); // Output: [ad, ae, af, bd, be, bf, cd, ce, cf]
  }
}

/*Explanation:
Initialization of Mappings:

In the Java version, we use a HashMap<Character, String> to store the mapping of digits to letters, just like the unordered_map<char, string> in C++.
We initialize this mapping in the letterCombinations method.
Result Storage:

The result is stored in a List<String>, similar to vector<string> in C++. This list will hold all the possible letter combinations.
Backtracking Function:

The backtrack function is used to explore all possible combinations recursively. We use a StringBuilder in Java instead of a string in C++ for building the current combination, which is more efficient for frequent modifications.
Base Case:

The base case checks if the current combination's length matches the input digits' length. If it does, the combination is added to the result list.
Recursive Case:

For each digit in the input string, the function loops through the corresponding letters and recursively builds combinations by adding each letter to the current combination and then removing it (backtracking) after the recursive call.
Main Function:

The letterCombinations function initializes the digit-to-letter mapping and starts the backtracking process.
Example Usage:

The main method demonstrates how to use the letterCombinations function by passing a string of digits and printing the resulting combinations.
Time Complexity:
O(4^n): Similar to the C++ version, the time complexity is exponential since each digit can correspond to up to 4 letters.
Space Complexity:
O(4^n): The space complexity remains the same due to the storage of all possible combinations.*/
