package cools.dp.misc;

/*
 Problem: Climbing Stairs

 You are climbing a staircase. It takes n steps to reach the top.
 Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 Example 1:
 Input: n = 2
 Output: 2
 Explanation: There are two ways to climb to the top:
 1. 1 step + 1 step
 2. 2 steps

 Example 2:
 Input: n = 3
 Output: 3
 Explanation: There are three ways to climb to the top:
 1. 1 step + 1 step + 1 step
 2. 1 step + 2 steps
 3. 2 steps + 1 step

 Constraints:
 - 1 <= n <= 45

 Solution Approach:
 1. The problem is a variation of the Fibonacci sequence.
 2. To reach the n-th step, you can come from either (n-1)-th step or (n-2)-th step.
 3. The number of distinct ways to reach the n-th step is the sum of ways to reach (n-1) and (n-2).
 4. The base cases are:
    - If n == 1, there's only 1 way to climb.
    - If n == 2, there are 2 ways to climb.
 5. We can solve this using dynamic programming or an iterative approach.
*/

public class A01ClimbingStairs {

  // Function to calculate the number of distinct ways to climb to the top
  public int climbStairs(int n) {
    if (n == 1) {
      return 1;
    }
    if (n == 2) {
      return 2;
    }

    // Variables to store the number of ways to reach the previous two steps
    int oneStepBefore = 2;
    int twoStepsBefore = 1;
    int allWays = 0;

    // Iteratively calculate the number of ways to reach the n-th step
    for (int i = 3; i <= n; i++) {
      allWays = oneStepBefore + twoStepsBefore;
      twoStepsBefore = oneStepBefore;
      oneStepBefore = allWays;
    }

    return allWays;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01ClimbingStairs solution = new A01ClimbingStairs();

    // Example 1
    int n1 = 2;
    System.out.println(
        "Number of ways to climb " + n1 + " steps: " + solution.climbStairs(n1)); // Output: 2

    // Example 2
    int n2 = 3;
    System.out.println(
        "Number of ways to climb " + n2 + " steps: " + solution.climbStairs(n2)); // Output: 3

    // Example 3
    int n3 = 5;
    System.out.println(
        "Number of ways to climb " + n3 + " steps: " + solution.climbStairs(n3)); // Output: 8
  }

  /*
   Time Complexity:
   - O(n), where n is the number of steps. We calculate the number of ways for each step once.

   Space Complexity:
   - O(1), since we only use a constant amount of extra space to store variables.
  */
}
