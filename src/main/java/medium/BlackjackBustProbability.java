/*
 * Problem Statement:
 * In the game of Blackjack, the dealer must draw cards until the sum of the values of their cards
 * is greater than or equal to a target value minus 4. For example, with a target of 21,
 * the dealer must continue drawing cards until their total is at least 17, at which point they stop ("stand").
 * If the dealer's total exceeds the target, they "bust" and lose.
 *
 * Your task is to write a function that calculates the probability that the dealer will bust
 * given a target value and a starting hand value.
 *
 * The function should return this probability rounded to three decimal places.
 *
 * Assumptions:
 * - The deck is infinite, and each card has a value between 1 and 10, with an equal probability of being drawn.
 * - The target value is always a positive integer.
 * - The starting hand value is a positive integer less than or equal to the target value.
 *
 * Example:
 * Input: target = 21, startingHand = 15
 * Output: 0.45
 */

/*Brute Force Solution
Approach:
The brute force approach involves simulating all possible card draws and recursively calculating the probability of busting. We sum the probabilities of busting based on each possible draw (from 1 to 10).

Time Complexity:
O((target - startingHand) * 10^(target - startingHand)): The time complexity is exponential due to the recursive nature of the solution, as it explores all possible paths.
Space Complexity:
O(target - startingHand): This is the depth of the recursion stack, which depends on how far the current hand is from the target.*/

package medium;

import java.text.DecimalFormat;

public class BlackjackBustProbability {
    // Brute Force Solution
    public static double calculateBustProbabilityBruteForce(int target, int startingHand) {
        if (startingHand >= target - 4) {
            return 0.0;  // If starting hand is already within the safe zone, no bust chance
        }
        if (startingHand > target) {
            return 1.0;  // If the starting hand is already over the target, it's a bust
        }

        double bustProbability = 0.0;
        for (int draw = 1; draw <= 10; draw++) {
            bustProbability += (1.0 / 10) * calculateBustProbabilityBruteForce(target, startingHand + draw);
        }
        return bustProbability;
    }
    // Optimized Solution using Dynamic Programming
    public static double calculateBustProbabilityDP(int target, int startingHand) {
        double[] dp = new double[target + 1];

        // Base case: If currentHand >= target - 4, probability of busting is 0
        for (int i = target - 4; i <= target; i++) {
            dp[i] = 0.0;
        }

        for (int i = target - 5; i >= startingHand; i--) {
            double bustProbability = 0.0;
            for (int draw = 1; draw <= 10; draw++) {
                if (i + draw > target) {
                    bustProbability += 1.0 / 10;
                } else {
                    bustProbability += (1.0 / 10) * dp[i + draw];
                }
            }
            dp[i] = bustProbability;
        }

        return dp[startingHand];
    }

    public static void main(String[] args) {
        int target = 21;
        int startingHand = 15;
        double probability = calculateBustProbabilityBruteForce(target, startingHand);
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println(df.format(probability));  // Output: Rounded probability, e.g., 0.45
    }
}
/*
Optimized Solution: Dynamic Programming
Approach:
To avoid recalculating the same probabilities multiple times, we can use dynamic programming to store the results of subproblems. We calculate the probability iteratively starting from the target minus 4 and working our way down.

Time Complexity:
O((target - startingHand) * 10): This solution is linear with respect to the number of possible hand values and the number of card values (1-10).
Space Complexity:
O(target - startingHand): We need space to store the calculated probabilities for all possible hand values.*/

/*import java.util.*;

class Program {
    // O(t - s) time | O(t - s) space - where t is the target, and s is the starting hand
    public float blackjackProbability(int target, int startingHand) {
        HashMap<Integer, Float> memo = new HashMap<Integer, Float>();
        return Math.round(
                this.calculateProbability(target, startingHand, memo) * 1000f
        )
                / 1000f;
    }

    private float calculateProbability(
            int target, int currentHand, HashMap<Integer, Float> memo
    ) {
        // Check if the probability for this hand value has already been computed
        if (memo.containsKey(currentHand)) {
            return memo.get(currentHand);
        }

        // If the current hand value exceeds the target, the dealer busts
        if (currentHand > target) {
            return 1;
        }

        // If the current hand value plus 4 is greater than or equal to the target,
        // the dealer will stop drawing cards, so the probability of busting is 0
        if (currentHand + 4 >= target) {
            return 0;
        }

        // Calculate the total probability of busting by drawing each possible card
        float totalProbability = 0;
        for (int drawnCard = 1; drawnCard <= 10; drawnCard++) {
            totalProbability +=
                    0.1f * calculateProbability(target, currentHand + drawnCard, memo);
        }

        // Store the computed probability in the memoization map
        memo.put(currentHand, totalProbability);

        return totalProbability;
    }

    public static void main(String[] args) {
        Program p = new Program();
        int target = 21;
        int startingHand = 15;
        System.out.println(p.blackjackProbability(target, startingHand));  // Example Output: 0.45
    }
}
Explanation
Function blackjackProbability(int target, int startingHand):

Purpose: Initializes the memoization structure (a HashMap) and calls the recursive method to compute the probability of busting. It also rounds the result to three decimal places.
        Parameters:
target: The target value the dealer is trying to achieve without busting.
        startingHand: The dealer’s initial hand value.
Returns: The rounded probability of busting.
        Function calculateProbability(int target, int currentHand, HashMap<Integer, Float> memo):

Purpose: Computes the probability of busting from a given hand value (currentHand). It uses memoization to store and reuse results of subproblems to avoid redundant calculations.

Parameters:

target: The target value.
        currentHand: The current sum of the dealer's cards.
memo: A map storing previously computed probabilities for different hand values.
        Returns: The probability of busting from the currentHand.

Key Logic:

Memoization Check: If the probability for currentHand is already computed, return it from memo.
Bust Check: If currentHand exceeds target, return 1 (dealer busts).
Stand Check: If currentHand + 4 is greater than or equal to the target, return 0 (dealer will stop drawing cards).
Recurrence Relation: For each possible drawn card (1 through 10), recursively calculate the probability of busting and accumulate it. Each drawn card has an equal probability of 0.1.
Memoization Storage: Store the computed probability in the memo map before returning it.
Main Method:

Purpose: Provides an example of how to use the blackjackProbability method and prints the result.
Time and Space Complexity
Time Complexity: O(t - s) — The algorithm computes the probability for each possible hand value from startingHand to target, storing results in memo to avoid redundant calculations.
Space Complexity: O(t - s) — The space complexity is mainly due to the memo map, which stores the computed probabilities for each possible hand value.*/
