/*
 * Problem Statement:
 * Write a function that takes a positive integer represented as a string `number`
 * and an integer `numDigits`. Remove `numDigits` from the string so that the number
 * represented by the string is as large as possible afterwards.
 *
 * Note that the order of the remaining digits cannot be changed. You can assume
 * `numDigits` will always be less than the length of `number` and greater than or
 * equal to 0.
 *
 * Example:
 *
 * Sample Input:
 * number = "462839"
 * numDigits = 2
 *
 * Sample Output:
 * "6839"  // remove digits 4 and 2
 */
/*Approach:
Iterate Over the Digits: Traverse the digits of the number.
Remove Digits: Use a stack to maintain the digits of the resulting largest number. For each digit in the input string:
Pop digits from the stack if they are smaller than the current digit and if there are still digits left to remove.
Handle Remaining Digits: If there are any digits left to remove after processing the entire string, remove them from the end of the stack.
Construct the Result: Convert the stack back to a string to form the final largest number.
Time Complexity:
O(n): Where n is the length of the string. Each digit is pushed and popped from the stack at most once.
Space Complexity:
O(n): To store the resulting digits.*/

package medium;

import java.util.*;

public class BestDigits {

  // O(n) time | O(n) space - where n is the length of the input string
  public static String bestDigits(String number, int numDigits) {
    Stack<Character> stack = new Stack<Character>();

    for (int idx = 0; idx < number.length(); idx++) {
      char character = number.charAt(idx);
      while (numDigits > 0 && !stack.isEmpty() && character > stack.peek()) {
        numDigits--;
        stack.pop();
      }
      stack.push(character);
    }

    while (numDigits > 0) {
      numDigits--;
      stack.pop();
    }

    // build final string from stack
    StringBuilder bestDigitString = new StringBuilder();
    while (!stack.isEmpty()) {
      bestDigitString.append(stack.pop());
    }

    bestDigitString.reverse();

    return bestDigitString.toString();
  }

  public static void main(String[] args) {
    String number = "462839";
    int numDigits = 2;
    String result = bestDigits(number, numDigits);
    System.out.println(result); // Output: "6839"
  }
}
