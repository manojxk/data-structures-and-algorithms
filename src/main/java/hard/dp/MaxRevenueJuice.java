package hard.dp;

/**
 * Problem Statement:
 *
 * <p>You are given an array of integers `prices` where the index represents the quantity of juice
 * and the value at that index represents the price of that quantity of juice. The goal is to
 * determine the optimal way to bottle the juice such that it maximizes the revenue.
 *
 * <p>The total number of units of juice is `n - 1`, where `n` is the length of the `prices` array.
 *
 * <p>The function should return a list of juice quantities in ascending order that maximize the
 * total revenue.
 *
 * <p>Notes: - `prices[0]` is always 0 since no juice has no value. - Larger quantities of juice
 * might not always be more expensive than smaller ones. - There is only one optimal solution for
 * each test case.
 *
 * <p>Sample Input: prices = [0, 1, 3, 2]
 *
 * <p>Sample Output: [1, 2] // We have 3 total units of juice. Splitting them into 1 and 2 units
 * gives maximum revenue of 4.
 */
import java.util.*;

public class MaxRevenueJuice {

  // Optimized Dynamic Programming Approach
  // Time Complexity: O(n^2), where n is the length of the prices array.
  // We iterate through each possible partition of the total juice quantity.
  // Space Complexity: O(n), as we use a DP array to store the maximum revenue at each step.
  public static List<Integer> maximizeRevenue(int[] prices) {
    int n = prices.length;
    int totalUnits = n - 1;

    // dp[i] will store the maximum revenue for i units of juice
    int[] dp = new int[n];
    // splits[i] will store the optimal first split to achieve the maximum revenue for i units
    int[] splits = new int[n];

    // Fill dp array using bottom-up approach
    for (int i = 1; i <= totalUnits; i++) {
      for (int j = 1; j <= i; j++) {
        int revenue = prices[j] + dp[i - j];
        if (revenue > dp[i]) {
          dp[i] = revenue;
          splits[i] = j;
        }
      }
    }

    // Now, recover the optimal solution by tracing back the splits
    List<Integer> result = new ArrayList<>();
    int remainingUnits = totalUnits;

    while (remainingUnits > 0) {
      result.add(splits[remainingUnits]);
      remainingUnits -= splits[remainingUnits];
    }

    Collections.sort(result); // Sort the result to return the juice quantities in ascending order
    return result;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample Input
    int[] prices = {0, 1, 3, 2};

    // Output the result
    List<Integer> result = maximizeRevenue(prices);
    System.out.println("Optimal juice quantities: " + result); // Expected output: [1, 2]
  }
}

/*Dynamic Programming Approach:

We use a DP array dp[] where dp[i] represents the maximum revenue that can be obtained from i units of juice.
The splits[] array is used to keep track of how the juice is divided to achieve the optimal revenue.
We iterate over every possible quantity of juice (i) and for each quantity, we check every possible split (j). If splitting i units into j units gives a higher revenue, we update dp[i] and store the split in splits[i].
Time Complexity: O(n^2), where n is the length of the prices array. For each i (from 1 to n-1), we check every possible split (j), leading to a nested loop.

Space Complexity: O(n), since we are using an additional DP array and a splits[] array of size n.

Approach Summary:
We use dynamic programming to compute the maximum revenue for each possible juice quantity.
After computing the DP table, we trace back the splits to reconstruct the exact quantities of juice that give the optimal revenue.*/
