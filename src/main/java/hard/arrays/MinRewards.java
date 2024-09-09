/*
 * Problem Statement:
 * Imagine you are a teacher who has just graded the final exam in a class.
 * You have a list of student scores on the final exam, and you want to reward your students fairly.
 * The rules for distributing rewards are as follows:
 *
 * 1. All students must receive at least one reward.
 * 2. Any student must receive strictly more rewards than an adjacent student
 *    with a lower score and must receive strictly fewer rewards than an adjacent student with a higher score.
 *
 * Write a function that takes in a list of scores and returns the minimum number of rewards
 * that must be given to satisfy these two rules.
 *
 * Assumptions:
 * - All scores are unique, so no two students have the same score.
 *
 * Example:
 * Input: scores = [8, 4, 2, 1, 3, 6, 7, 9, 5]
 * Output: 25  // The rewards distribution would be [4, 3, 2, 1, 2, 3, 4, 5, 1]
 */
/*Optimized Solution
Approach:
We can optimize the solution by making two passes through the list:

Left to Right Pass: Ensure that every student with a higher score than the previous one gets more rewards.
Right to Left Pass: Ensure that every student with a higher score than the next one gets more rewards.
This guarantees that both conditions are satisfied efficiently.

Time Complexity:
O(n): We make two linear passes through the list.
Space Complexity:
O(n): We use an extra array to store the rewards for each student.*/
package hard.arrays;

import java.util.Arrays;

public class MinRewards {

  // Optimized Solution
  public static int minRewardsOptimized(int[] scores) {
    int n = scores.length;
    int[] rewards = new int[n];
    Arrays.fill(rewards, 1); // Initialize rewards with 1 for each student

    // Left to Right Pass
    for (int i = 1; i < n; i++) {
      if (scores[i] > scores[i - 1]) {
        rewards[i] = rewards[i - 1] + 1;
      }
    }

    // Right to Left Pass
    for (int i = n - 2; i >= 0; i--) {
      if (scores[i] > scores[i + 1]) {
        rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
      }
    }

    return Arrays.stream(rewards).sum();
  }

  public static void main(String[] args) {
    int[] scores = {8, 4, 2, 1, 3, 6, 7, 9, 5};
    System.out.println(minRewardsOptimized(scores)); // Output: 25
  }
}
