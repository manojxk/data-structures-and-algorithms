package cools.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class A08PhoneNumberLetterCombinations {

  // Mapping digits to their corresponding letters
  // Mapping from digits to their corresponding letters
  private static final Map<Character, String> digitToLetters = new HashMap<>();

  public List<String> letterCombinations(String digits) {
    List<String> result = new ArrayList<>();
    if (digits == null || digits.isEmpty()) {
      return result; // Return empty if input is empty
    }
    backtrack(result, new StringBuilder(), digits, 0);
    return result;
  }

  // Backtracking function to generate all combinations
  private void backtrack(
      List<String> result, StringBuilder currentCombination, String digits, int index) {
    // Base case: if we've processed all digits, add the current combination to the result
    if (index == digits.length()) {
      result.add(currentCombination.toString());
      return;
    }

    // Get the corresponding letters for the current digit
    String letters = digitToLetters.get(digits.charAt(index));

    // Loop through each letter and recursively add it to the current combination
    for (char letter : letters.toCharArray()) {
      currentCombination.append(letter); // Choose a letter
      backtrack(result, currentCombination, digits, index + 1); // Move to the next digit
      currentCombination.deleteCharAt(
          currentCombination.length() - 1); // Backtrack by removing the last added letter
    }
  }

  public static void main(String[] args) {
    A08PhoneNumberLetterCombinations solution = new A08PhoneNumberLetterCombinations();
    // Initialize the mapping from digits to their corresponding letters
    digitToLetters.put('0', "");
    digitToLetters.put('1', "");
    digitToLetters.put('2', "abc");
    digitToLetters.put('3', "def");
    digitToLetters.put('4', "ghi");
    digitToLetters.put('5', "jkl");
    digitToLetters.put('6', "mno");
    digitToLetters.put('7', "pqrs");
    digitToLetters.put('8', "tuv");
    digitToLetters.put('9', "wxyz");

    String digits1 = "23"; // Example input
    System.out.println(solution.letterCombinations(digits1));
    // Output: [ad, ae, af, bd, be, bf, cd, ce, cf]

    String digits2 = ""; // Example input with empty string
    System.out.println(solution.letterCombinations(digits2));
    // Output: []
  }
}
