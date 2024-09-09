/**
 * Problem Statement: Knapsack Problem
 *
 * <p>You're given an array of arrays where each subarray holds two integer values and represents an
 * item; the first integer is the item's value, and the second integer is the item's weight. You're
 * also given an integer representing the maximum capacity of a knapsack that you have.
 *
 * <p>Your goal is to fit items in your knapsack without having the sum of their weights exceed the
 * knapsack's capacity, all the while maximizing their combined value. Note that you only have one
 * of each item at your disposal.
 *
 * <p>Write a function that returns the maximized combined value of the items that you should pick
 * as well as an array of the indices of each item picked.
 *
 * <p>If there are multiple combinations of items that maximize the total value in the knapsack,
 * your function can return any of them.
 *
 * <p>Function Signature: public static List<Object> knapsackProblem(int[][] items, int capacity);
 *
 * <p>Input: - An array of items where each item is represented as [value, weight]. - An integer
 * representing the maximum capacity of the knapsack.
 *
 * <p>Output: - An integer representing the maximum value that can be obtained. - An array of
 * integers representing the indices of the items included in the knapsack.
 *
 * <p>Example:
 *
 * <p>Input: int[][] items = {{1, 2}, {4, 3}, {5, 6}, {6, 7}}; int capacity = 10;
 *
 * <p>Output: [10, [1, 3]] // items [4, 3] and [6, 7]
 */
package hard.dp;

import java.util.ArrayList;
import java.util.List;

public class KnapsackProblem {

  public static List<Object> knapsackProblem(int[][] items, int capacity) {
    int n = items.length;

    // dp array where dp[i][j] represents the maximum value we can obtain with the first i items and
    // capacity j
    int[][] dp = new int[n + 1][capacity + 1];

    // Fill the dp array
    for (int i = 1; i <= n; i++) {
      int currentValue = items[i - 1][0]; // value of the current item
      int currentWeight = items[i - 1][1]; // weight of the current item

      for (int c = 0; c <= capacity; c++) {
        if (currentWeight > c) {
          dp[i][c] = dp[i - 1][c]; // Item too heavy, can't include it
        } else {
          dp[i][c] =
              Math.max(
                  dp[i - 1][c],
                  dp[i - 1][c - currentWeight]
                      + currentValue); // Take max of including or not including the item
        }
      }
    }

    // Find the items that were included
    List<Integer> selectedItems = getKnapsackItems(dp, items, capacity);

    // Result: maximum value and the indices of items selected
    List<Object> result = new ArrayList<>();
    result.add(dp[n][capacity]); // Maximum value
    result.add(selectedItems); // List of selected item indices
    return result;
  }

  // Helper function to retrieve the items selected for the knapsack
  private static List<Integer> getKnapsackItems(int[][] dp, int[][] items, int capacity) {
    List<Integer> selectedItems = new ArrayList<>();
    int i = items.length;
    int c = capacity;

    while (i > 0) {
      if (dp[i][c] != dp[i - 1][c]) { // If value changed, it means the item was included
        selectedItems.add(0, i - 1); // Add the item's index to the result
        c -= items[i - 1][1]; // Reduce the capacity by the weight of the item
      }
      i--;
    }
    return selectedItems;
  }

  public static void main(String[] args) {
    int[][] items = {{1, 2}, {4, 3}, {5, 6}, {6, 7}};
    int capacity = 10;
    System.out.println(knapsackProblem(items, capacity)); // Output: [10, [1, 3]]
  }
}
/*
Explanation:
Dynamic Programming Table (dp):

We use a 2D array dp where dp[i][j] represents the maximum value that can be obtained with the first i items and a capacity j.
For each item, we check whether to include it in the knapsack or not based on its weight and value.
The transition formula is:
If the item's weight is greater than the current capacity, we can't include the item: dp[i][c] = dp[i - 1][c].
Otherwise, we choose the maximum between including or not including the item: dp[i][c] = Math.max(dp[i - 1][c], dp[i - 1][c - currentWeight] + currentValue).
Backtracking to Find the Items:

After filling the dp table, we backtrack to find which items were included in the knapsack by checking where the value in the dp table changed (i.e., where we included an item).
Result:

The maximum value is stored in dp[n][capacity], where n is the number of items and capacity is the maximum capacity.
The indices of the items included in the knapsack are found by backtracking through the dp table.
Time Complexity:
Time Complexity: O(n * capacity), where n is the number of items and capacity is the maximum capacity of the knapsack. This is because we fill the dp table, which has dimensions n x capacity.
Space Complexity: O(n * capacity) for storing the dp table.*/
