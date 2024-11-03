package veryhard.dp;

/*
 Problem: Max Profit With K Transactions (Recursive Solution)

 You are given an array of integers where the element at index i represents the price of a stock on day i.
 You are allowed to complete at most K transactions. Write a recursive function that returns the maximum
 profit that you can make with at most K transactions.

 A transaction consists of buying one share of the stock and selling one share of the stock.

 Example Input:
 prices = [5, 11, 3, 50, 60, 90], k = 2

 Example Output:
 93  // Explanation: Buy at 5, sell at 11. Buy at 3, sell at 90. Total profit = (11 - 5) + (90 - 3) = 93

 Solution Steps:
 1. Define a recursive function that computes the maximum profit for a given state.
 2. At each step, decide whether to buy/sell or skip the transaction.
 3. Use memoization to avoid recomputation of overlapping subproblems.
 4. Base case: If we have completed all transactions or reached the end of prices, return 0.
*/

import java.util.HashMap;
import java.util.Map;

public class A01MaxProfitWithKTransactions {

  // Recursive function to calculate maximum profit
  public int maxProfitWithKTransactions(int[] prices, int k) {
    if (prices == null || prices.length == 0 || k == 0)
      return 0; // Base case: No transactions allowed or no prices available

    // Create a memoization table to store results of subproblems
    Map<String, Integer> memo = new HashMap<>();
    // Start recursive function with 0th day, k transactions allowed, and no stock bought (canBuy =
    // true)
    return maxProfitRecursive(prices, k, 0, true, memo);
  }

  // Recursive function with memoization
  private int maxProfitRecursive(
      int[] prices, int k, int day, boolean canBuy, Map<String, Integer> memo) {
    // Base case: No more transactions allowed or we've reached the end of the prices array
    if (k == 0 || day >= prices.length) {
      return 0;
    }

    // Memoization key for the current state
    String memoKey = day + "-" + k + "-" + canBuy;

    // Check if we have already computed this subproblem
    if (memo.containsKey(memoKey)) {
      return memo.get(memoKey);
    }

    int doNothing = maxProfitRecursive(prices, k, day + 1, canBuy, memo); // Option to do nothing

    if (canBuy) {
      // Option to buy: Subtract the price and go to the next day with a sell option
      int buy = -prices[day] + maxProfitRecursive(prices, k, day + 1, false, memo);
      memo.put(memoKey, Math.max(buy, doNothing)); // Memoize the result
    } else {
      // Option to sell: Add the price and decrease the transaction count
      int sell = prices[day] + maxProfitRecursive(prices, k - 1, day + 1, true, memo);
      memo.put(memoKey, Math.max(sell, doNothing)); // Memoize the result
    }

    return memo.get(memoKey); // Return the maximum profit for this state
  }

  // Driver code to test the recursive solution
  public static void main(String[] args) {
    A01MaxProfitWithKTransactions solution = new A01MaxProfitWithKTransactions();

    // Example Input
    int[] prices = {5, 11, 3, 50, 60, 90};
    int k = 2;

    // Calculate and print the maximum profit
    int maxProfit = solution.maxProfitWithKTransactions(prices, k);
    System.out.println(
        "Maximum Profit with " + k + " transactions: " + maxProfit); // Expected Output: 93
  }

  /*
   Time Complexity:
   - O(n * k), where n is the number of days and k is the number of transactions.
     Each subproblem is computed once due to memoization.

   Space Complexity:
   - O(n * k), for the memoization table storing results of subproblems.
   - O(n), for the recursion stack depth, which can go up to n levels.
  */
}
