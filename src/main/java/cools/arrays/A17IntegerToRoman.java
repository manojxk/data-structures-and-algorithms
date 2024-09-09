package cools.arrays;

/*
 Problem: Integer to Roman

 Roman numerals are represented by seven different symbols:
 Symbol       Value
 I             1
 V             5
 X             10
 L             50
 C             100
 D             500
 M             1000

 Roman numerals are formed by converting decimal place values from highest to lowest. Certain numbers are represented using a subtractive notation, for example:
 - 4 is represented as IV (1 less than 5)
 - 9 is represented as IX (1 less than 10)

 Given an integer num, convert it to a Roman numeral.

 Constraints:
 - 1 <= num <= 3999

 Example 1:
 Input: num = 3749
 Output: "MMMDCCXLIX"
 Explanation:
 3000 = MMM, 700 = DCC, 40 = XL, 9 = IX

 Example 2:
 Input: num = 58
 Output: "LVIII"
 Explanation: 50 = L, 8 = VIII

 Example 3:
 Input: num = 1994
 Output: "MCMXCIV"
 Explanation: 1000 = M, 900 = CM, 90 = XC, 4 = IV

 Solution Approach:
 1. Use a greedy approach by checking for the largest Roman numeral symbols that can be subtracted from the number.
 2. Append the corresponding Roman numeral symbol to the result and subtract its value from the number.
 3. Repeat the process until the number becomes 0.
*/

public class A17IntegerToRoman {

  // Function to convert an integer to a Roman numeral
  public String intToRoman(int num) {
    // Arrays to store Roman symbols and their corresponding values
    String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
    int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

    StringBuilder result = new StringBuilder(); // StringBuilder to construct the Roman numeral

    // Iterate through the values and symbols
    for (int i = 0; i < romanValues.length; i++) {
      // While num is greater than the current Roman value, append the symbol and subtract its value
      while (num >= romanValues[i]) {
        result.append(romanSymbols[i]);
        num -= romanValues[i];
      }
    }

    return result.toString(); // Return the final Roman numeral
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A17IntegerToRoman solution = new A17IntegerToRoman();

    // Example 1
    int num1 = 5;
    System.out.println("Roman Numeral: " + solution.intToRoman(num1)); // Output: "MMMDCCXLIX"

    // Example 2
    int num2 = 58;
    System.out.println("Roman Numeral: " + solution.intToRoman(num2)); // Output: "LVIII"

    // Example 3
    int num3 = 1994;
    System.out.println("Roman Numeral: " + solution.intToRoman(num3)); // Output: "MCMXCIV"
  }

  /*
   Time Complexity:
   - O(1), since the maximum value of num is fixed at 3999, and we iterate through a constant number of Roman numeral symbols.

   Space Complexity:
   - O(1), since we use only constant extra space to store the Roman numeral symbols and the result.
  */
}
