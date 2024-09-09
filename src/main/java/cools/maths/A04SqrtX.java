package cools.maths;

/*
 Problem: Sqrt(x)

 Given a non-negative integer x, return the square root of x rounded down to the nearest integer.
 The returned integer should be non-negative as well.

 You must not use any built-in exponent function or operator (like `pow(x, 0.5)` in C++ or `x ** 0.5` in Python).

 Example 1:
 Input: x = 4
 Output: 2
 Explanation: The square root of 4 is 2, so we return 2.

 Example 2:
 Input: x = 8
 Output: 2
 Explanation: The square root of 8 is approximately 2.82842..., and since we round it down to the nearest integer, 2 is returned.

 Constraints:
 - 0 <= x <= 2^31 - 1

 Solution Approach:
 1. Use binary search to find the square root.
 2. We know that the square root of a number lies between 0 and x.
 3. Perform binary search to find the largest number whose square is less than or equal to x.
*/

public class A04SqrtX {

  // Function to compute the square root of x
  public int mySqrt(int x) {
    // Handle special case for x = 0 or 1
    if (x == 0 || x == 1) {
      return x;
    }

    int left = 1, right = x;
    int result = 0; // Store the result

    // Perform binary search to find the square root
    while (left <= right) {
      int mid = left + (right - left) / 2;

      // Check if mid * mid == x to find the exact square root
      if (mid == x / mid) {
        return mid;
      } else if (mid < x / mid) { // mid * mid is less than x
        left = mid + 1;
        result = mid; // Store the closest result
      } else { // mid * mid is greater than x
        right = mid - 1;
      }
    }

    return result; // Return the square root rounded down
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04SqrtX solution = new A04SqrtX();

    // Example 1
    int x1 = 4;
    System.out.println("Square root of " + x1 + " is: " + solution.mySqrt(x1)); // Output: 2

    // Example 2
    int x2 = 8;
    System.out.println("Square root of " + x2 + " is: " + solution.mySqrt(x2)); // Output: 2

    // Example 3
    int x3 = 25;
    System.out.println("Square root of " + x3 + " is: " + solution.mySqrt(x3)); // Output: 5
  }

  /*
   Time Complexity:
   - O(log(x)), because we are using binary search to reduce the search space by half at each step.

   Space Complexity:
   - O(1), since we are using a constant amount of space.
  */
}
