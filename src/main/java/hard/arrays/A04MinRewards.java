package hard.arrays;

/*
 Problem: Min Rewards

 You are given an array representing the scores of students in a classroom. The students sit in a line, and each student should receive a reward based on their score.

 You must give out rewards according to these conditions:
 - Every student must receive at least one reward.
 - Any student with a higher score than an adjacent student should receive more rewards than the adjacent student.

 Return the minimum number of rewards you need to give to each student such that all conditions are satisfied.

 Example 1:
 Input: scores = [8, 4, 2, 1, 3, 6, 7, 9, 5]
 Output: 25
 Explanation: The rewards could be distributed as follows:
 [4, 3, 2, 1, 2, 3, 4, 5, 1]

 Example 2:
 Input: scores = [1, 0, 2]
 Output: 5
 Explanation: The rewards could be distributed as [2, 1, 2].

 Constraints:
 - 1 <= scores.length <= 100,000
 - 0 <= scores[i] <= 10^5

 Solution Approach:
 1. Create two passes through the scores array:
    - First pass: Traverse from left to right, ensuring that students with higher scores than the previous one get more rewards.
    - Second pass: Traverse from right to left, ensuring that students with higher scores than the next one get more rewards.
 2. The final reward for each student is the maximum of the rewards calculated in both passes.
*/

import java.util.Arrays;

public class A04MinRewards {

  // Function to calculate the minimum number of rewards
  public static int minRewards(int[] scores) {
    if (scores == null || scores.length == 0) {
      return 0;
    }

    int n = scores.length;
    int[] rewards = new int[n];
    Arrays.fill(rewards, 1); // Step 1: Initialize all rewards with 1

    // Step 2: Left-to-right pass
    for (int i = 1; i < n; i++) {
      if (scores[i] > scores[i - 1]) {
        rewards[i] = rewards[i - 1] + 1; // Give more reward than the left neighbor
      }
    }

    // Step 3: Right-to-left pass
    for (int i = n - 2; i >= 0; i--) {
      if (scores[i] > scores[i + 1]) {
        rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1); // Ensure the reward condition
      }
    }

    // Step 4: Sum up the rewards
    int totalRewards = 0;
    for (int reward : rewards) {
      totalRewards += reward;
    }

    return totalRewards; // Return the total number of rewards
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04MinRewards solution = new A04MinRewards();

    // Example 1: scores = [8, 4, 2, 1, 3, 6, 7, 9, 5]
    int[] scores1 = {8, 4, 2, 1, 3, 6, 7, 9, 5};
    System.out.println(
        "Minimum rewards for scores1: " + solution.minRewards(scores1)); // Output: 25

    // Example 2: scores = [1, 0, 2]
    int[] scores2 = {1, 0, 2};
    System.out.println("Minimum rewards for scores2: " + solution.minRewards(scores2)); // Output: 5
  }

  /*
   Time Complexity:
   - O(n), where n is the number of students (length of the scores array). We make two linear passes through the array.

   Space Complexity:
   - O(n), where n is the number of students. We use an additional array to store the rewards for each student.
  */
}
