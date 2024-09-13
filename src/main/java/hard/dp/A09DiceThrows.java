package hard.dp;

/*
 Problem: Dice Throws

 You are given `n` dice, each with `m` faces, and a target value `x`. You need to find the number of ways in which the dice can be thrown such that the sum of the values on the dice equals `x`.

 Example 1:
 Input: n = 2, m = 6, x = 7
 Output: 6
 Explanation:
 There are 6 ways to get a sum of 7 with two 6-sided dice:
 [1, 6], [2, 5], [3, 4], [4, 3], [5, 2], [6, 1]

 Example 2:
 Input: n = 3, m = 4, x = 5
 Output: 6
 Explanation:
 There are 6 ways to get a sum of 5 with three 4-sided dice:
 [1, 1, 3], [1, 2, 2], [1, 3, 1], [2, 1, 2], [2, 2, 1], [3, 1, 1]

 Constraints:
 - 1 <= n <= 50 (number of dice)
 - 1 <= m <= 100 (number of faces on each die)
 - 1 <= x <= 1000 (target sum)

 Solution Approach:
 1. Use dynamic programming to solve the problem. Let dp[i][j] represent the number of ways to get a sum `j` using `i` dice.
 2. The base case is dp[0][0] = 1, meaning there is one way to get a sum of 0 with 0 dice.
 3. For each die and each possible sum, update the DP table by adding the values from the previous dice rolls.
 4. The result is stored in dp[n][x], which gives the number of ways to get the sum `x` with `n` dice.
*/

public class A09DiceThrows {

  // Function to find the number of ways to get a sum x with n dice and m faces
  public static int diceThrows(int n, int m, int x) {
    // Step 1: Initialize the DP table with 0s
    int[][] dp = new int[n + 1][x + 1];

    // Step 2: Base case: there's one way to get a sum of 0 with 0 dice
    dp[0][0] = 1;

    // Step 3: Fill the DP table
    for (int dice = 1; dice <= n; dice++) {
      for (int sum = 1; sum <= x; sum++) {
        // For each face of the dice, update the DP table
        for (int face = 1; face <= m; face++) {
          if (face <= sum) {
            dp[dice][sum] += dp[dice - 1][sum - face];
          }
        }
      }
    }

    // Step 4: The result is the number of ways to get the sum x with n dice
    return dp[n][x];
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int n1 = 2, m1 = 6, x1 = 7;
    System.out.println(
        "Ways to get sum " + x1 + " with " + n1 + " dice: " + diceThrows(n1, m1, x1)); // Output: 6

    // Example 2
    int n2 = 3, m2 = 4, x2 = 5;
    System.out.println(
        "Ways to get sum " + x2 + " with " + n2 + " dice: " + diceThrows(n2, m2, x2)); // Output: 6
  }

  /*
   Time Complexity:
   - O(n * x * m), where n is the number of dice, x is the target sum, and m is the number of faces on the dice.

   Space Complexity:
   - O(n * x), where n is the number of dice and x is the target sum.
  */
}
