package easy.recursion;

import java.util.HashMap;
import java.util.Map;

public class A01Fibonacci {

  /*
   * Problem: Fibonacci Series
   *
   * The Fibonacci sequence is defined as follows:
   * F(1) = 0, F(2) = 1, and F(n) = F(n-1) + F(n-2) for n > 2.
   *
   * You are asked to implement three different methods to compute the n-th Fibonacci number:
   * 1. Brute force recursive solution.
   * 2. Optimized recursive solution using memoization.
   * 3. Iterative solution.
   *
   * Example 1:
   * Input: n = 2
   * Output: 1
   *
   * Example 2:
   * Input: n = 6
   * Output: 5
   */

  // Brute force recursive solution
  /*
   * Time Complexity:
   * O(2^n): This is because the function makes two recursive calls for every non-base case, leading to an exponential number of calls.
   *
   * Space Complexity:
   * O(n): The recursion stack depth is proportional to n.
   */
  public static int getNthFib(int n) {
    // Base cases
    if (n == 1) {
      return 0;
    } else if (n == 2) {
      return 1;
    } else {
      // Recursive case: F(n) = F(n-1) + F(n-2)
      return getNthFib(n - 1) + getNthFib(n - 2);
    }
  }

  // Optimized recursive solution using memoization
  public static int getNthFibOptimized(int n) {
    // Memoization map to store previously computed Fibonacci numbers
    Map<Integer, Integer> memo = new HashMap<>();
    memo.put(1, 0); // F(1)
    memo.put(2, 1); // F(2)
    return getNthFibOptimized(n, memo);
  }

  /*
   * Time Complexity:
   * O(n): Each Fibonacci number is computed only once and stored for future reference.
   *
   * Space Complexity:
   * O(n): We store the Fibonacci numbers in a map up to n and the depth of the recursion is also proportional to n.
   */
  private static int getNthFibOptimized(int n, Map<Integer, Integer> memo) {
    // Check if the result is already in the memo
    if (memo.containsKey(n)) {
      return memo.get(n);
    }
    // Recursive case: compute and store in memo
    int nthFib = getNthFibOptimized(n - 1, memo) + getNthFibOptimized(n - 2, memo);
    memo.put(n, nthFib);
    return nthFib;
  }

  // Iterative solution for Fibonacci
  public static int getNthFibIter(int n) {
    // Base cases
    if (n == 1) return 0;
    if (n == 2) return 1;

    // Variables to store the two previous Fibonacci numbers
    int prevPrev = 0; // F(1)
    int prev = 1; // F(2)
    int current = 0;

    // Iteratively compute Fibonacci numbers
    for (int i = 3; i <= n; i++) {
      current = prevPrev + prev;
      prevPrev = prev;
      prev = current;
    }

    return current;
  }

  /*
   * Time Complexity:
   * O(n): We compute Fibonacci numbers iteratively, making n-2 iterations.
   *
   * Space Complexity:
   * O(1): We use constant space for storing only three variables (prevPrev, prev, current).
   */

  public static void main(String[] args) {
    // Test cases
    System.out.println(getNthFib(2)); // Expected output: 1
    System.out.println(getNthFib(6)); // Expected output: 5
    System.out.println(getNthFibOptimized(6)); // Expected output: 5 (Optimized)
    System.out.println(getNthFibIter(6)); // Expected output: 5 (Iterative)
  }
}
