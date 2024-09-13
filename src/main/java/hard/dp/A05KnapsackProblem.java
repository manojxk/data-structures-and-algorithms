package hard.dp;

/*
 Problem: 0/1 Knapsack Problem

 You are given two arrays: `weights` and `values`, where `weights[i]` and `values[i]` represent the weight and value of the ith item, respectively.
 You are also given a single integer `capacity`, which represents the maximum weight capacity of the knapsack.

 Your goal is to maximize the value you can carry in your knapsack, without exceeding the capacity.
 You can only carry each item once (0/1 Knapsack).

 In addition to returning the maximum value, return the items that were selected to maximize the value.

 Example 1:
 Input: values = [60, 100, 120], weights = [10, 20, 30], capacity = 50
 Output: [220, [1, 2]]
 Explanation: The maximum value is 220, and the items selected are 1 and 2 (items are 0-indexed).

 Example 2:
 Input: values = [10, 40, 30], weights = [5, 8, 7], capacity = 10
 Output: [40, [1]]
 Explanation: The maximum value is 40, and the item selected is 1.

 Constraints:
 - The number of items is between 1 and 1000.
 - The weight of each item is between 1 and 1000.
 - The capacity of the knapsack is between 1 and 1000.

 Solution Approach:
 1. Use dynamic programming to solve this problem.
 2. Create a 2D DP array where dp[i][j] represents the maximum value we can achieve with the first i items and a capacity of j.
 3. Reconstruct the items that were added to the knapsack by backtracking through the DP array.
*/

import java.util.*;

public class A05KnapsackProblem {

  // Function to solve the 0/1 Knapsack problem and return the maximum value and items selected
  public static List<Object> knapsackProblem(int[] values, int[] weights, int capacity) {
    int n = values.length;

    // Step 1: Create a DP table to store the maximum value for each capacity
    int[][] dp = new int[n + 1][capacity + 1];

    // Step 2: Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int w = 0; w <= capacity; w++) {
        if (weights[i - 1] <= w) {
          dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
        } else {
          dp[i][w] = dp[i - 1][w];
        }
      }
    }

    // Step 3: Backtrack to find the items that were selected
    List<Integer> selectedItems = new ArrayList<>();
    int maxValue = dp[n][capacity];
    int w = capacity;

    for (int i = n; i > 0; i--) {
      if (dp[i][w] != dp[i - 1][w]) {
        selectedItems.add(i - 1); // Item is selected, add it to the list
        w -= weights[i - 1]; // Reduce the remaining capacity
      }
    }

    // Reverse the selectedItems to maintain the correct order
    Collections.reverse(selectedItems);

    // Step 4: Prepare the result (maximum value and the selected items)
    List<Object> result = new ArrayList<>();
    result.add(maxValue); // Add the maximum value
    result.add(selectedItems); // Add the selected items

    return result;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1:
    int[] values1 = {60, 100, 120};
    int[] weights1 = {10, 20, 30};
    int capacity1 = 50;
    List<Object> result1 = knapsackProblem(values1, weights1, capacity1);
    System.out.println("Maximum value: " + result1.get(0)); // Output: 220
    System.out.println("Items selected: " + result1.get(1)); // Output: [1, 2]

    // Example 2:
    int[] values2 = {10, 40, 30};
    int[] weights2 = {5, 8, 7};
    int capacity2 = 10;
    List<Object> result2 = knapsackProblem(values2, weights2, capacity2);
    System.out.println("Maximum value: " + result2.get(0)); // Output: 40
    System.out.println("Items selected: " + result2.get(1)); // Output: [1]
  }

  /*
   Time Complexity:
   - O(n * capacity), where n is the number of items and capacity is the maximum capacity of the knapsack. We fill in a DP table of size (n+1) x (capacity+1).

   Space Complexity:
   - O(n * capacity), since we use a DP table to store values for each item and capacity combination.
  */
}
