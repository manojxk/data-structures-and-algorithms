package cools.arrays;

/*
 Problem: Best Time to Buy and Sell Stock II

 You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
 On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time.
 However, you can buy it and then immediately sell it on the same day.

 Find and return the maximum profit you can achieve.

 Constraints:
 - prices.length >= 1
 - All elements in prices are non-negative integers

 Example 1:
 Input: prices = [7,1,5,3,6,4]
 Output: 7
 Explanation:
 Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5 - 1 = 4.
 Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6 - 3 = 3.
 Total profit is 4 + 3 = 7.

 Example 2:
 Input: prices = [1,2,3,4,5]
 Output: 4
 Explanation:
 Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5 - 1 = 4.
 Total profit is 4.

 Example 3:
 Input: prices = [7,6,4,3,1]
 Output: 0
 Explanation: There is no way to make a positive profit, so we never buy the stock.

 Solution Approach:
 1. To maximize profit, we can buy and sell multiple times whenever there is a positive difference between consecutive prices.
 2. This means we sum up all the positive differences between consecutive days' prices.
 3. For every increase in price from one day to the next, we can "buy" on the previous day and "sell" on the next day.
*/

public class A08BestTimeToBuyAndSellStockII {

  // Function to calculate the maximum profit
  public int maxProfit(int[] prices) {
    int maxProfit = 0; // Variable to store the total profit

    // Traverse through all prices
    for (int i = 1; i < prices.length; i++) {
      // If today's price is higher than yesterday's, we can make a profit
      if (prices[i] > prices[i - 1]) {
        maxProfit += prices[i] - prices[i - 1];
      }
    }

    return maxProfit; // Return the total profit
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A08BestTimeToBuyAndSellStockII solution = new A08BestTimeToBuyAndSellStockII();

    // Example 1
    int[] prices1 = {7, 1, 5, 3, 6, 4};
    System.out.println("Maximum Profit: " + solution.maxProfit(prices1)); // Output: 7

    // Example 2
    int[] prices2 = {1, 2, 3, 4, 5};
    System.out.println("Maximum Profit: " + solution.maxProfit(prices2)); // Output: 4

    // Example 3
    int[] prices3 = {7, 6, 4, 3, 1};
    System.out.println("Maximum Profit: " + solution.maxProfit(prices3)); // Output: 0
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We pass through the array once.

   Space Complexity:
   - O(1), since we are using only a few variables to store the profit.
  */
}
