package cools.arrays;

/*
 Problem: Best Time to Buy and Sell Stock

 You are given an array prices where prices[i] is the price of a given stock on the ith day.
 You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

 Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

 Constraints:
 - prices.length >= 1
 - All elements in prices are non-negative integers

 Example 1:
 Input: prices = [7,1,5,3,6,4]
 Output: 5
 Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6 - 1 = 5.

 Example 2:
 Input: prices = [7,6,4,3,1]
 Output: 0
 Explanation: No transactions are done and the max profit = 0.

 Solution Approach:
 1. Use a single pass to track the minimum price encountered so far and calculate the maximum profit at each step.
 2. As we iterate through the array:
    - Track the minimum price to buy stock.
    - At each price, calculate the potential profit by subtracting the minimum price from the current price.
    - Update the maximum profit if the current potential profit is higher than the previously recorded maximum.
 3. This approach ensures that we only pass through the array once, making it efficient.

*/

public class A07BestTimeToBuyAndSellStock {

  // Function to calculate the maximum profit
  public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) return 0;

    int minPrice = Integer.MAX_VALUE; // Variable to track the minimum price encountered so far
    int maxProfit = 0; // Variable to track the maximum profit

    // Traverse through all prices
    for (int price : prices) {
      // Update the minimum price if the current price is lower
      if (price < minPrice) {
        minPrice = price;
      }

      // Calculate potential profit for the current price and update maxProfit if it's higher
      int potentialProfit = price - minPrice;
      if (potentialProfit > maxProfit) {
        maxProfit = potentialProfit;
      }
    }

    return maxProfit; // Return the maximum profit achieved
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A07BestTimeToBuyAndSellStock solution = new A07BestTimeToBuyAndSellStock();

    // Example 1
    int[] prices1 = {7, 1, 5, 3, 6, 4};
    System.out.println("Maximum Profit: " + solution.maxProfit(prices1)); // Output: 5

    // Example 2
    int[] prices2 = {7, 6, 4, 3, 1};
    System.out.println("Maximum Profit: " + solution.maxProfit(prices2)); // Output: 0
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We pass through the array once.

   Space Complexity:
   - O(1), since we are using only constant extra space (variables to track the minimum price and maximum profit).
  */
}
