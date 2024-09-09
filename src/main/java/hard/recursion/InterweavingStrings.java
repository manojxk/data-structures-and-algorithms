package hard.recursion;

/**
 * Problem Statement:
 *
 * <p>You are given three strings `one`, `two`, and `three`. Write a function that returns a boolean
 * value indicating whether the third string (`three`) can be formed by interweaving the first two
 * strings (`one` and `two`).
 *
 * <p>To interweave strings means to merge them by alternating their characters without changing the
 * relative order of characters within each string.
 *
 * <p>Example: - "abc" and "123" can be interwoven as: - "a1b2c3" - "abc123" - "ab1c23" - Characters
 * from the strings must maintain their original relative order.
 *
 * <p>Sample Input: one = "algoexpert" two = "your-dream-job" three = "your-algodream-expertjob"
 *
 * <p>Sample Output: true
 *
 * <p>Explanation: The string "your-algodream-expertjob" is an interweaving of the first two strings
 * "algoexpert" and "your-dream-job". The letters in each string maintain their relative order.
 */
public class InterweavingStrings {

  // Brute Force Approach:
  // Recursively check all possible ways to interleave the characters of `one` and `two`
  // Time Complexity: O(2^(n + m)) - Each recursive call branches in two directions
  // Space Complexity: O(n + m) - Recursion depth, where n and m are lengths of `one` and `two`
  public static boolean interweavingStringsBruteForce(String one, String two, String three) {
    if (three.length() != one.length() + two.length()) {
      return false; // If lengths don't match, it can't be interwoven
    }
    return areInterwoven(one, two, three, 0, 0);
  }

  // Recursive helper function for brute force
  private static boolean areInterwoven(String one, String two, String three, int i, int j) {
    int k = i + j; // Index for the third string

    if (k == three.length()) {
      return true; // Base case: if we've processed all characters in `three`
    }

    // Try matching with `one` or `two`
    if (i < one.length() && one.charAt(i) == three.charAt(k)) {
      if (areInterwoven(one, two, three, i + 1, j)) {
        return true;
      }
    }

    if (j < two.length() && two.charAt(j) == three.charAt(k)) {
      if (areInterwoven(one, two, three, i, j + 1)) {
        return true;
      }
    }

    return false; // No match found, return false
  }

  // Optimized Approach with Memoization:
  // Time Complexity: O(n * m) - Each unique combination of i, j is computed once
  // Space Complexity: O(n * m) - Memoization table to store intermediate results
  public static boolean interweavingStringsOptimized(String one, String two, String three) {
    if (three.length() != one.length() + two.length()) {
      return false;
    }

    Boolean[][] memo = new Boolean[one.length() + 1][two.length() + 1];
    return areInterwovenMemo(one, two, three, 0, 0, memo);
  }

  // Recursive helper function with memoization
  private static boolean areInterwovenMemo(
      String one, String two, String three, int i, int j, Boolean[][] memo) {
    if (memo[i][j] != null) {
      return memo[i][j]; // Return cached result if already computed
    }

    int k = i + j; // Index for `three`
    if (k == three.length()) {
      return true; // Base case: all characters in `three` are matched
    }

    boolean canInterweave = false;

    // Try matching with `one`
    if (i < one.length() && one.charAt(i) == three.charAt(k)) {
      canInterweave = areInterwovenMemo(one, two, three, i + 1, j, memo);
    }

    // Try matching with `two`
    if (!canInterweave && j < two.length() && two.charAt(j) == three.charAt(k)) {
      canInterweave = areInterwovenMemo(one, two, three, i, j + 1, memo);
    }

    memo[i][j] = canInterweave; // Store result in memo table
    return canInterweave;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample Input
    String one = "algoexpert";
    String two = "your-dream-job";
    String three = "your-algodream-expertjob";

    // Brute Force Solution
    System.out.println("Brute Force Solution: " + interweavingStringsBruteForce(one, two, three));

    // Optimized Solution with Memoization
    System.out.println("Optimized Solution: " + interweavingStringsOptimized(one, two, three));
  }
}
