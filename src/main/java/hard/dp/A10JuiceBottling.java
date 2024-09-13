package hard.dp;

/*
 Problem: Juice Bottling - Maximize Revenue

 You are given an array of integers `prices` where the index represents the quantity of juice,
 and the value at that index represents the price of that quantity of juice.
 The goal is to determine the optimal way to split the juice such that it maximizes the revenue.

 The total number of units of juice is `n - 1`, where `n` is the length of the `prices` array.

 You need to return a list of juice quantities in ascending order that maximize the total revenue.

 **Constraints**:
 - prices[0] is always 0 since no juice has no value.
 - Larger quantities of juice might not always be more expensive than smaller ones.
 - There is only one optimal solution for each test case.

 Example 1:
 Input: prices = [0, 1, 3, 2]
 Output: [1, 2]
 Explanation:
     We have 3 total units of juice (n - 1 = 3).
     The optimal way to split is 1 unit and 2 units, with revenue of 1 + 3 = 4.
     The output is [1, 2] (in ascending order).

 Solution Approach:
 1. The goal is to maximize the revenue for `n - 1` units of juice by determining the best way to split the juice.
 2. Use Dynamic Programming (DP) to solve the problem:
    - Define `dp[i]` as the maximum revenue for `i` units of juice.
    - Initialize `dp[i]` as the price for `i` units (i.e., `dp[i] = prices[i]`).
    - Try splitting the quantity into smaller pieces (e.g., `i` can be split into `j` and `i - j`).
    - For each split, update `dp[i]` to reflect the best possible revenue (i.e., `dp[i] = max(dp[i], dp[j] + dp[i - j])`).
    - Keep track of the splits using a `split[]` array.
 3. Once the DP table is filled, reconstruct the sequence of quantities that give the maximum revenue.

 Time Complexity: O(n^2), where `n` is the total number of units (n = length of prices array). We compute each `dp[i]` by looking at all possible splits.
 Space Complexity: O(n), where `n` is the total number of units. This is used to store the DP array and the split array.
*/

import java.util.ArrayList;
import java.util.List;

public class A10JuiceBottling {

  // Function to determine the optimal way to bottle the juice
  public static List<Integer> maxRevenueJuiceBottling(int[] prices) {
    int n = prices.length;
    int[] dp = new int[n]; // dp[i] will store the maximum revenue for i units of juice
    int[] split = new int[n]; // split[i] will store the optimal split for i units of juice

    // Step 1: Initialize the base case: dp[0] = 0 (no juice, no revenue)
    dp[0] = 0;

    // Step 2: Fill the dp array using dynamic programming
    for (int i = 1; i < n; i++) {
      dp[i] = prices[i]; // Initially, the best revenue for i units is the price of i units directly
      for (int j = 1; j < i; j++) {
        // Try splitting the i units into j and i - j units
        if (dp[i] < dp[j] + dp[i - j]) {
          dp[i] = dp[j] + dp[i - j]; // Update the maximum revenue
          split[i] = j; // Record the split point
        }
      }
    }

    // Step 3: Reconstruct the quantities that maximize the revenue
    List<Integer> result = new ArrayList<>();
    int quantity = n - 1; // Total juice units available (n - 1)

    // Backtrack to find the optimal quantities
    while (quantity > 0) {
      if (split[quantity] == 0) {
        // No split, just take the whole quantity
        result.add(quantity);
        break;
      } else {
        // Add the optimal split and reduce the quantity
        result.add(split[quantity]);
        quantity -= split[quantity];
      }
    }

    // Step 4: Sort the result in ascending order and return
    result.sort(Integer::compareTo);
    return result;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int[] prices = {0, 1, 3, 2}; // We have 3 units of juice in total
    List<Integer> result = maxRevenueJuiceBottling(prices);
    System.out.println("Optimal juice quantities: " + result); // Output: [1, 2]
  }

  /*
   Time Complexity:
   - O(n^2), where `n` is the total number of units of juice. For each quantity `i`, we look at all possible splits, resulting in a quadratic time complexity.

   Space Complexity:
   - O(n), where `n` is the total number of units. We use the `dp` and `split` arrays, each of size `n`.
  */
}
