package cools.maths;

/*
 Problem: Pow(x, n)

 Implement pow(x, n), which calculates x raised to the power n (i.e., x^n).

 Example 1:
 Input: x = 2.00000, n = 10
 Output: 1024.00000

 Example 2:
 Input: x = 2.10000, n = 3
 Output: 9.26100

 Example 3:
 Input: x = 2.00000, n = -2
 Output: 0.25000
 Explanation: 2^-2 = 1/(2^2) = 1/4 = 0.25

 Constraints:
 - -100.0 < x < 100.0
 - -2^31 <= n <= 2^31 - 1
 - n is an integer.
 - The result should be rounded to 5 decimal places.

 Solution Approach:
 1. This is a classic problem that can be solved using **Exponentiation by Squaring**.
 2. If n is negative, compute 1/(x^|n|).
 3. For positive n, we use the fact that:
    - If n is even, x^n = (x^2)^(n/2).
    - If n is odd, x^n = x * x^(n-1).
 4. This reduces the number of multiplications to O(log n).
*/

public class A05PowerFunction {

  // Function to calculate x raised to the power n
  public double myPow(double x, int n) {
    // Handle negative power case
    long N = n; // Convert n to long to avoid overflow issues with -2^31
    if (N < 0) {
      x = 1 / x; // If n is negative, calculate 1 / (x^|n|)
      N = -N;
    }
    return powHelper(x, N); // Call the helper function
  }

  // Helper function to compute x^n using recursion
  private double powHelper(double x, long n) {
    // Base case: x^0 = 1
    if (n == 0) {
      return 1.0;
    }

    // Recursive case: If n is even, x^n = (x^2)^(n/2)
    double half = powHelper(x, n / 2);

    if (n % 2 == 0) {
      return half * half; // If n is even
    } else {
      return half * half * x; // If n is odd
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05PowerFunction solution = new A05PowerFunction();

    // Example 1
    double x1 = 2.00000;
    int n1 = 10;
    System.out.printf("Pow(%f, %d) = %.5f%n", x1, n1, solution.myPow(x1, n1)); // Output: 1024.00000

    // Example 2
    double x2 = 2.10000;
    int n2 = 3;
    System.out.printf("Pow(%f, %d) = %.5f%n", x2, n2, solution.myPow(x2, n2)); // Output: 9.26100

    // Example 3
    double x3 = 2.00000;
    int n3 = -2;
    System.out.printf("Pow(%f, %d) = %.5f%n", x3, n3, solution.myPow(x3, n3)); // Output: 0.25000
  }

  /*
   Time Complexity:
   - O(log n), since we divide n by 2 at each step.

   Space Complexity:
   - O(log n), due to the recursive stack space.
  */
}
