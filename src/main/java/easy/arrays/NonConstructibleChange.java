package easy.arrays;

/*
 Problem: Non-Constructible Change

 You are given an array of positive integers representing the values of coins in your possession.
 The array contains coins of different denominations, and your goal is to determine the minimum amount of change (money)
 that you cannot create using any combination of the coins.

 For example, if you have coins [1, 2, 5], you can create 1, 2, 3, 4, 5, 6, 7, and 8 units of change.
 The minimum amount of change that you cannot create is 9.

 Example:

 Input: [5, 7, 1, 1, 2, 3, 22]
 Output: 20

 Explanation:
 You can create change from 1 to 19 with the given coins, but you cannot create 20 units of change.
*/

/*
 Solution Steps:

 1. Sort the array of coins in increasing order. This helps us easily track the smallest amount of change that we cannot create.
 2. Initialize a variable `currentChange` to 0. This variable will represent the largest amount of change we can currently create.
 3. Iterate through the coins:
    a) For each coin, check if it's greater than `currentChange + 1`.
    b) If it is, it means there is a gap, and `currentChange + 1` is the smallest amount of change we can't create.
    c) If the coin is less than or equal to `currentChange + 1`, we add its value to `currentChange`, extending the range of change we can create.
 4. Return `currentChange + 1` as the smallest non-constructible change.

*/

import java.util.Arrays;

public class NonConstructibleChange {

  // Function to calculate the minimum non-constructible change
  public static int nonConstructibleChange(int[] coins) {
    // Step 1: Sort the coins array
    Arrays.sort(coins);

    int currentChange = 0; // Represents the current amount of change we can create

    // Step 2: Iterate through the sorted coins
    for (int coin : coins) {
      // If the current coin is greater than the change we can currently create + 1,
      // then we found the non-constructible change
      if (coin > currentChange + 1) {
        return currentChange + 1;
      }

      // Otherwise, add the current coin to the change we can create
      currentChange += coin;
    }

    // Step 3: If we go through all coins, return the next amount of change we cannot create
    return currentChange + 1;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] coins = {5, 7, 1, 1, 2, 3, 22};
    int result = nonConstructibleChange(coins);
    System.out.println("Minimum non-constructible change: " + result); // Output: 20
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of coins. Sorting the coins takes O(n log n), and iterating through them takes O(n).

   Space Complexity:
   - O(1), since we are using only a few variables and modifying the input in-place (ignoring the space used by sorting, which is in-place).
  */
}
