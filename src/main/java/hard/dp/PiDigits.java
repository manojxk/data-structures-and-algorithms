package hard.dp;

/**
 * Problem Statement:
 *
 * <p>You are given a string representing the first n digits of Pi and a list of positive integers,
 * all provided in string format. The task is to determine the smallest number of spaces that can be
 * added to the digits of Pi such that all resulting numbers (after adding spaces) are found in the
 * list of integers.
 *
 * <p>A single number from the list can appear multiple times in the resulting numbers. For example,
 * if Pi is "3141" and the list of numbers is ["1", "3", "4"], the number "1" can appear twice in
 * the result after adding spaces: "3 | 1 | 4 | 1".
 *
 * <p>If no valid way exists to add spaces such that all resulting numbers are found in the list,
 * return -1.
 *
 * <p>Sample Input: pi = "3141592653589793238462643383279" numbers = ["314159265358979323846",
 * "26433", "8", "3279", "314159265", "35897932384626433832", "79"]
 *
 * <p>Sample Output: 2 // "314159265 | 35897932384626433832 | 79"
 */
import java.util.*;

public class PiDigits {

  // Brute Force Approach:
  // Try all possible ways of adding spaces and check if each split is in the list of numbers.
  // Time Complexity: O(2^n) - Every position in the string has two possibilities (add a space or
  // not).
  // Space Complexity: O(n) - Storing the result for the recursion depth.
  public static int minSpacesBruteForce(String pi, Set<String> numbers) {
    int minSpaces = findMinSpacesBruteForce(pi, numbers, 0);
    return minSpaces == Integer.MAX_VALUE ? -1 : minSpaces;
  }

  // Recursive helper function for brute force approach
  private static int findMinSpacesBruteForce(String pi, Set<String> numbers, int idx) {
    if (idx == pi.length()) return -1;
    int minSpaces = Integer.MAX_VALUE;

    // Try every possible split
    for (int i = idx; i < pi.length(); i++) {
      String prefix = pi.substring(idx, i + 1);
      if (numbers.contains(prefix)) {
        int minSpacesInSuffix = findMinSpacesBruteForce(pi, numbers, i + 1);
        if (minSpacesInSuffix != Integer.MAX_VALUE) {
          minSpaces = Math.min(minSpaces, minSpacesInSuffix + 1);
        }
      }
    }
    return minSpaces;
  }

  // Optimized Approach: Dynamic Programming
  // Time Complexity: O(n^2) - For each position in the string, we check every possible substring.
  // Space Complexity: O(n) - Using a DP array to store the minimum spaces for each position.
  public static int minSpacesOptimized(String pi, Set<String> numbers) {
    int[] dp = new int[pi.length()];
    Arrays.fill(dp, Integer.MAX_VALUE);

    // Start checking each character position in Pi
    for (int i = 0; i < pi.length(); i++) {
      String substring = pi.substring(0, i + 1);
      if (numbers.contains(substring)) {
        dp[i] = 0; // No space needed for a valid prefix
      }

      if (dp[i] == Integer.MAX_VALUE) continue;

      // Try every possible suffix from position i
      for (int j = i + 1; j < pi.length(); j++) {
        String prefix = pi.substring(i + 1, j + 1);
        if (numbers.contains(prefix)) {
          dp[j] = Math.min(dp[j], dp[i] + 1);
        }
      }
    }

    return dp[pi.length() - 1] == Integer.MAX_VALUE ? -1 : dp[pi.length() - 1];
  }

  public static void main(String[] args) {
    // Sample Input
    String pi = "3141592653589793238462643383279";
    String[] numbersArray = {
      "314159265358979323846", "26433", "8", "3279", "314159265", "35897932384626433832", "79"
    };
    Set<String> numbers = new HashSet<>(Arrays.asList(numbersArray));

    // Brute Force Solution
    System.out.println("Brute Force Solution: " + minSpacesBruteForce(pi, numbers));

    // Optimized Solution
    System.out.println("Optimized Solution: " + minSpacesOptimized(pi, numbers));
  }
}

/*
Brute Force Approach:

Time Complexity: O(2^n), where n is the length of the Pi string. This is because for each character, we either place a space or we don't, which results in two choices at each step, hence exponential time.
Space Complexity: O(n), where n is the recursion depth and the space required for storing the result.
        Optimized Approach (Dynamic Programming):

Time Complexity: O(n^2), where n is the length of the Pi string. For each position, we check every possible substring to see if it's in the list of valid numbers.
Space Complexity: O(n), for storing the dp array, which stores the minimum spaces required at each index of the Pi string.
Approach Summary:
In the brute force approach, we recursively try every possible way to split the string into valid numbers from the list, checking every substring starting at each position.
In the optimized approach, we use dynamic programming to store the minimum number of spaces required for each index, and build the solution incrementally, improving performance significantly.*/
