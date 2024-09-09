package cools.maths;

/*
 Problem: Plus One

 You are given a large integer represented as an integer array digits, where each digits[i] is the ith digit of the integer.
 The digits are ordered from most significant to least significant in left-to-right order. The large integer does not contain any leading 0's.
 Increment the large integer by one and return the resulting array of digits.

 Example 1:
 Input: digits = [1, 2, 3]
 Output: [1, 2, 4]
 Explanation: The array represents the integer 123. Incrementing by one gives 123 + 1 = 124.

 Example 2:
 Input: digits = [4, 3, 2, 1]
 Output: [4, 3, 2, 2]
 Explanation: The array represents the integer 4321. Incrementing by one gives 4321 + 1 = 4322.

 Example 3:
 Input: digits = [9]
 Output: [1, 0]
 Explanation: The array represents the integer 9. Incrementing by one gives 9 + 1 = 10.

 Constraints:
 - 1 <= digits.length <= 100
 - 0 <= digits[i] <= 9
 - digits does not contain any leading 0's.

 Solution Approach:
 1. Traverse the array from the last digit (rightmost digit) to the first (most significant).
 2. Add 1 to the last digit and check if it causes a carry (i.e., the digit becomes 10).
 3. If there is a carry, set the current digit to 0 and move to the next digit.
 4. Continue this process until there are no more carries.
 5. If the carry propagates through all digits (e.g., input is 999), prepend 1 to the result.
*/

import java.util.Arrays;

public class A02PlusOne {

  // Function to increment the large integer represented as an array
  public int[] plusOne(int[] digits) {
    int n = digits.length;

    // Traverse the array from the last digit to the first
    for (int i = n - 1; i >= 0; i--) {
      // If the current digit is less than 9, we can simply add 1 and return the result
      if (digits[i] < 9) {
        digits[i]++;
        return digits; // No carry, return the result
      }

      // If the current digit is 9, it becomes 0 (carry over to the next significant digit)
      digits[i] = 0;
    }

    // If we reach this point, it means all digits were 9 (e.g., 999 -> 1000)
    // We need to create a new array with an extra digit at the front
    int[] result = new int[n + 1];
    result[0] = 1; // Set the first digit to 1, rest are 0 by default
    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02PlusOne solution = new A02PlusOne();

    // Example 1
    int[] digits1 = {1, 2, 3,9,9};
    System.out.println(
        "Result: " + Arrays.toString(solution.plusOne(digits1))); // Output: [1, 2, 4]

    // Example 2
    int[] digits2 = {4, 3, 2, 1};
    System.out.println(
        "Result: " + Arrays.toString(solution.plusOne(digits2))); // Output: [4, 3, 2, 2]

    // Example 3
    int[] digits3 = {9,9,9,9};
    System.out.println("Result: " + Arrays.toString(solution.plusOne(digits3))); // Output: [1, 0]
  }

  /*
   Time Complexity:
   - O(n), where n is the number of digits in the input array. We process each digit once.

   Space Complexity:
   - O(n), where n is the number of digits. In the worst case (e.g., 999 -> 1000), we need an additional space for the result.
  */
}
