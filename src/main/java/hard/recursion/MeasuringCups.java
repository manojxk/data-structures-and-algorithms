package hard.recursion;

/**
 * Problem Statement:
 *
 * <p>You are given a list of measuring cups, each of which has a low (L) and high (H) line,
 * representing the possible range of a measurement using that cup. You are also given a target
 * range represented by two integers `low` and `high`.
 *
 * <p>Your task is to write a function that returns a boolean indicating whether you can measure a
 * volume that lies within the target range using the cups. You can use any combination of cups
 * multiple times to achieve the measurement.
 *
 * <p>The cups cannot be poured into each other, and once you've used a cup, its contents are
 * transferred to a larger bowl.
 *
 * <p>Sample Input: measuringCups = [ [200, 210], [450, 465], [800, 850], ] low = 2100 high = 2300
 *
 * <p>Sample Output: true
 *
 * <p>The goal is to determine if we can measure a volume between 2100 and 2300 using the cups.
 */
import java.util.*;

public class MeasuringCups {

  // Optimized Approach: Recursive DFS + Memoization
  // Time Complexity: O(n * (high - low)), where n is the number of measuring cups.
  // Space Complexity: O(n * (high - low)), for storing memoized states.

  public static boolean canMeasureInRange(int[][] measuringCups, int low, int high) {
    // Memoization map to store previously computed results for subproblems
    Map<String, Boolean> memo = new HashMap<>();
    return canMeasureInRangeHelper(measuringCups, low, high, memo);
  }

  // Recursive helper function to check if we can measure a range using the cups
  private static boolean canMeasureInRangeHelper(
      int[][] measuringCups, int low, int high, Map<String, Boolean> memo) {
    // Memoization key
    String key = low + ":" + high;

    // If already computed, return the memoized result
    if (memo.containsKey(key)) {
      return memo.get(key);
    }

    // Base case: if the target range is achievable (low <= 0 <= high), return true
    if (low <= 0 && high >= 0) {
      return true;
    }

    // If the range is invalid (low is greater than high), return false
    if (low > high) {
      return false;
    }

    // Try using each measuring cup and recursively check if the target can be measured
    for (int[] cup : measuringCups) {
      int cupLow = cup[0];
      int cupHigh = cup[1];
      // Subtract the cup's range from the target range and recurse
      if (canMeasureInRangeHelper(measuringCups, low - cupLow, high - cupHigh, memo)) {
        memo.put(key, true);
        return true;
      }
    }

    // If no valid combination is found, memoize and return false
    memo.put(key, false);
    return false;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Test case
    int[][] measuringCups = {
      {200, 210},
      {450, 465},
      {800, 850}
    };
    int low = 2100;
    int high = 2300;

    // Check if the target range can be measured
    boolean result = canMeasureInRange(measuringCups, low, high);
    System.out.println("Can measure in range: " + result); // Expected Output: true
  }
}
