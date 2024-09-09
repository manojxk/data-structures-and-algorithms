package cools.maths;

/*
 Problem: Palindrome Number

 Given an integer x, return true if x is a palindrome, and false otherwise.

 A palindrome number is a number that reads the same forward and backward.

 Example 1:
 Input: x = 121
 Output: true
 Explanation: 121 reads as 121 from left to right and from right to left.

 Example 2:
 Input: x = -121
 Output: false
 Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.

 Example 3:
 Input: x = 10
 Output: false
 Explanation: Reads 01 from right to left. Therefore it is not a palindrome.

 Constraints:
 - -2^31 <= x <= 2^31 - 1
 - Can you solve it without converting the integer to a string?

 Solution Approach:
 1. Handle edge cases: if x is negative or if x ends with 0 but is not 0 itself, return false (since negative numbers and numbers ending with 0 cannot be palindromes).
 2. Reverse the second half of the number and compare it with the first half.
 3. If the reversed second half matches the first half, the number is a palindrome.
*/

public class A01PalindromeNumber {

  // Function to check if an integer is a palindrome
  public boolean isPalindrome(int x) {
    // Step 1: Handle special cases
    // If x is negative or if x ends with 0 but is not 0 itself, it is not a palindrome
    if (x < 0 || (x % 10 == 0 && x != 0)) {
      return false;
    }

    int reversedHalf = 0; // To store the reversed second half of the number

    // Step 2: Reverse the second half of the number
    while (x > reversedHalf) {
      reversedHalf = reversedHalf * 10 + x % 10; // Add the last digit of x to reversedHalf
      x /= 10; // Remove the last digit from x
    }

    // Step 3: Check if the first half is equal to the reversed second half
    // For odd-length numbers, we ignore the middle digit by doing reversedHalf / 10
    return x == reversedHalf || x == reversedHalf / 10;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01PalindromeNumber solution = new A01PalindromeNumber();

    // Example 1
    int x1 = 155551;
    System.out.println("Is " + x1 + " a palindrome? " + solution.isPalindrome(x1)); // Output: true

    // Example 2
    int x2 = -121;
    System.out.println("Is " + x2 + " a palindrome? " + solution.isPalindrome(x2)); // Output: false

    // Example 3
    int x3 = 1234321;
    System.out.println("Is " + x3 + " a palindrome? " + solution.isPalindrome(x3)); // Output: false
  }

  /*
   Time Complexity:
   - O(log10(n)), where n is the input number. We divide the number by 10 at every step, so the time complexity is proportional to the number of digits in the number.

   Space Complexity:
   - O(1), since we only use a few integer variables for the reverse and do not use any extra data structures.
  */
}
