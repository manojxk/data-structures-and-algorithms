package easy;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

  // Brute force recursive solution
  /*  Time Complexity:
  O(2^n): This is because the function makes two recursive calls for every non-base case, leading to an exponential number of calls.
  Space Complexity:
  O(n): The recursion stack depth is proportional to n.*/

  public static int getNthFib(int n) {
    // Base cases
    if (n == 1) {
      return 0;
    } else if (n == 2) {
      return 1;
    } else {
      // Recursive case
      return getNthFib(n - 1) + getNthFib(n - 2);
    }
  }

  public static int getNthFibOptimized(int n) {
    Map<Integer, Integer> memo = new HashMap<>();
    memo.put(1, 0); // F0
    memo.put(2, 1); // F1
    return getNthFibOptimized(n, memo);
  }

  /* Time Complexity:
  O(n): Each Fibonacci number is computed only once and stored for future reference.
  Space Complexity:
  O(n): We store the Fibonacci numbers in an array up to n.*/
  private static int getNthFibOptimized(int n, Map<Integer, Integer> memo) {
    if (memo.containsKey(n)) {
      return memo.get(n);
    }
    int nthFib = getNthFibOptimized(n - 1, memo) + getNthFibOptimized(n - 2, memo);
    memo.put(n, nthFib);
    return nthFib;
  }

  public static int getNthFibIter(int n) {
    if (n == 1) return 0;
    if (n == 2) return 1;

    int prevPrev = 0; // F0
    int prev = 1; // F1
    int current = 0;

    for (int i = 3; i <= n; i++) {
      current = prevPrev + prev;
      prevPrev = prev;
      prev = current;
    }

    return current;
  }

  public static void main(String[] args) {
    // Test cases
    System.out.println(getNthFib(2)); // Expected output: 1
    System.out.println(getNthFib(6)); // Expected output: 5
  }
}
