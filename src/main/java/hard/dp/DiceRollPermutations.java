package hard.dp;

/**
 * Problem Statement:
 *
 * <p>You are given a set of `numDice` dice, each with `numSides` sides, and a `target` integer. The
 * goal is to find how many dice-roll permutations result in a sum exactly equal to the `target`.
 * Each dice roll can result in any integer from 1 to `numSides`. Permutations of the same total
 * count as different (e.g., [2, 3] and [3, 2] are different).
 *
 * <p>If no dice-roll permutation sums to the target, return 0.
 *
 * <p>Sample Input: numDice = 2 numSides = 6 target = 7
 *
 * <p>Sample Output: 6 // Possible permutations: [1, 6], [2, 5], [3, 4], [4, 3], [5, 2], [6, 1]
 */
public class DiceRollPermutations {

  // Optimized Dynamic Programming Approach
  // Time Complexity: O(numDice * target * numSides), since we iterate over all dice and target
  // sums,
  // and for each subproblem, we check up to `numSides` possibilities.
  // Space Complexity: O(numDice * target), as we use a DP table to store solutions for subproblems.
  public static int numRollsToTarget(int numDice, int numSides, int target) {
    // Create a DP table where dp[d][t] represents the number of ways to roll d dice to get sum t
    int[][] dp = new int[numDice + 1][target + 1];

    // Base case: There's exactly 1 way to get sum 0 with 0 dice (do nothing)
    dp[0][0] = 1;

    // Iterate over the number of dice
    for (int d = 1; d <= numDice; d++) {
      // Iterate over all possible target sums from 1 to `target`
      for (int t = 1; t <= target; t++) {
        // Iterate over each possible face value of the dice
        for (int s = 1; s <= numSides; s++) {
          // If we can subtract the dice face value from the current target sum
          if (t - s >= 0) {
            dp[d][t] += dp[d - 1][t - s];
          }
        }
      }
    }

    // The result will be the number of ways to roll `numDice` dice to get the `target` sum
    return dp[numDice][target];
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample Input
    int numDice = 2;
    int numSides = 6;
    int target = 7;

    // Output the result
    System.out.println("Number of Permutations: " + numRollsToTarget(numDice, numSides, target));
    // Expected output: 6
  }
}
/*Dynamic Programming Approach:

We create a dp table where dp[d][t] represents the number of ways to roll d dice to get exactly t as the sum.
Base Case: dp[0][0] = 1, meaning there's exactly one way to get a sum of 0 with 0 dice (do nothing).
We iterate through the number of dice and for each possible target sum, we compute the number of ways to achieve that sum by considering all possible dice values from 1 to numSides.
Time Complexity: O(numDice * target * numSides). We process each dice and each target sum, checking all possible face values of the dice.

Space Complexity: O(numDice * target). We use a DP table to store the number of ways for each combination of dice and target sums.

Approach Summary:
We iteratively build up solutions for smaller subproblems using dynamic programming.
For each dice, we consider all possible ways to achieve the desired sum by adding the dice’s face value to the result of the previous subproblem (one fewer dice).*/
