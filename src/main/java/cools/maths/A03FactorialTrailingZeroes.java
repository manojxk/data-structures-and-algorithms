package cools.maths;

/*
 Problem: Factorial Trailing Zeroes

 Given an integer n, return the number of trailing zeroes in n!.

 Note:
 - n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
 - Trailing zeroes in a number are produced by multiplying factors of 10.
 - Each 10 is produced by a factor of 2 and a factor of 5 in the factorial. Since there are usually more factors of 2 than 5, the number of trailing zeros is determined by the number of factors of 5.

 Example 1:
 Input: n = 3
 Output: 0
 Explanation: 3! = 6, no trailing zero.

 Example 2:
 Input: n = 5
 Output: 1
 Explanation: 5! = 120, one trailing zero.

 Example 3:
 Input: n = 0
 Output: 0

 Constraints:
 - 0 <= n <= 10^4

 Solution Approach:
 1. Trailing zeroes are created by factors of 10, and 10 is produced by multiplying 2 and 5.
 2. In the factorial, the number of factors of 2 is much larger than the number of factors of 5, so the number of trailing zeroes is determined by the number of factors of 5 in n!.
 3. To count how many times 5 appears as a factor, count n/5, n/25, n/125, etc., until n/5^k is zero.
*/

public class A03FactorialTrailingZeroes {

  // Function to count the number of trailing zeroes in n!
  public int trailingZeroes(int n) {
    int count = 0;

    // Count the number of factors of 5 in n!
    while (n > 0) {
      n /= 5; // Divide n by 5, 25, 125, etc.
      count += n;
    }

    return count;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A03FactorialTrailingZeroes solution = new A03FactorialTrailingZeroes();

    // Example 1
    int n1 = 10003;
    System.out.println(
        "Trailing Zeroes in " + n1 + "! : " + solution.trailingZeroes(n1)); // Output: 0

    // Example 2
    int n2 = 50000;
    System.out.println(
        "Trailing Zeroes in " + n2 + "! : " + solution.trailingZeroes(n2)); // Output: 1

    // Example 3
    int n3 = 0;
    System.out.println(
        "Trailing Zeroes in " + n3 + "! : " + solution.trailingZeroes(n3)); // Output: 0
  }

  /*
   Time Complexity:
   - O(log5(n)), because we repeatedly divide n by 5.

   Space Complexity:
   - O(1), since we only use a few variables.
  */

}
