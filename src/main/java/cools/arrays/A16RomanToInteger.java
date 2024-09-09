package cools.arrays;

/*
 Problem: Roman to Integer

 Roman numerals are represented by seven different symbols: I, V, X, L, C, D, and M.
 Symbol       Value
 I             1
 V             5
 X             10
 L             50
 C             100
 D             500
 M             1000

 For example:
 - 2 is written as II, which is two ones added together.
 - 12 is written as XII, which is X + II.
 - 27 is written as XXVII, which is XX + V + II.
 - 4 is written as IV, where I before V subtracts 1 from 5, making 4.

 Given a Roman numeral, convert it to an integer.

 Constraints:
 - 1 <= s.length <= 15
 - s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 - It is guaranteed that s is a valid Roman numeral in the range [1, 3999].

 Example 1:
 Input: s = "III"
 Output: 3

 Example 2:
 Input: s = "IV"
 Output: 4

 Example 3:
 Input: s = "IX"
 Output: 9

 Example 4:
 Input: s = "LVIII"
 Output: 58
 Explanation: L = 50, V = 5, III = 3.

 Solution Approach:
 1. Traverse the string from left to right.
 2. If the current character has a lower value than the next character, subtract its value. Otherwise, add its value.
 3. The subtraction case happens for the combinations like IV, IX, etc.
*/

import java.util.HashMap;
import java.util.Map;

public class A16RomanToInteger {

  // Function to convert a Roman numeral to an integer
  public int romanToInt(String s) {
    // Map to store Roman numerals and their corresponding integer values
    Map<Character, Integer> romanMap = new HashMap<>();
    romanMap.put('I', 1);
    romanMap.put('V', 5);
    romanMap.put('X', 10);
    romanMap.put('L', 50);
    romanMap.put('C', 100);
    romanMap.put('D', 500);
    romanMap.put('M', 1000);

    int result = 0; // Variable to store the final result
    int n = s.length();

    // Traverse the Roman numeral string
    for (int i = 0; i < n; i++) {
      int currentVal = romanMap.get(s.charAt(i));

      // If this is not the last character and the next character has a higher value, subtract the
      // current value
      if (i < n - 1 && currentVal < romanMap.get(s.charAt(i + 1))) {
        result -= currentVal;
      } else {
        result += currentVal; // Otherwise, add the current value
      }
    }

    return result; // Return the final converted integer
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A16RomanToInteger solution = new A16RomanToInteger();

    // Example 1
    String roman1 = "III";
    System.out.println("Integer: " + solution.romanToInt(roman1)); // Output: 3

    // Example 2
    String roman2 = "IV";
    System.out.println("Integer: " + solution.romanToInt(roman2)); // Output: 4

    // Example 3
    String roman3 = "IX";
    System.out.println("Integer: " + solution.romanToInt(roman3)); // Output: 9

    // Example 4
    String roman4 = "LVIII";
    System.out.println("Integer: " + solution.romanToInt(roman4)); // Output: 58
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the Roman numeral string. We traverse the string once.

   Space Complexity:
   - O(1), as the space used by the map is constant (since the number of Roman numerals is fixed).
  */
}
