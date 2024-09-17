package cools.arrays;

/*
 Problem: Collatz Conjecture (3n + 1 Problem)

 The Collatz Conjecture is a conjecture in mathematics that concerns a sequence defined as follows:
 - Start with any positive integer n.
 - Then, each term is obtained from the previous term as follows:
   - If the previous term is even, the next term is one half of the previous term.
   - If the previous term is odd, the next term is 3 times the previous term plus 1.
 - The conjecture is that no matter what value of n, the sequence will always reach 1.

 Example:

 Input: n = 6
 Output: 8

 Explanation:
 The sequence for n = 6 is: 6 -> 3 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1
 It takes 8 steps to reach 1.

 The task is to determine how many steps it takes to reach 1 for a given integer n.
*/

/*
 Solution Steps:

 1. Start with a positive integer n.
 2. While n is not 1, apply the following rules:
    - If n is even, divide it by 2.
    - If n is odd, multiply it by 3 and add 1.
 3. Keep a count of the number of steps taken to reach 1.
 4. Return the count once n equals 1.
*/

public class CollatzConjecture {

  // Function to calculate the number of steps to reach 1
  public int collatzSteps(int n) {
    int steps = 0;

    // Repeat until n becomes 1
    while (n != 1) {
      if (n % 2 == 0) {
        n /= 2; // If n is even, divide by 2
      } else {
        n = 3 * n + 1; // If n is odd, multiply by 3 and add 1
      }
      steps++; // Increment the step count
    }

    return steps;
  }

  // Main function to test the Collatz Conjecture implementation
  public static void main(String[] args) {
    CollatzConjecture collatz = new CollatzConjecture();

    // Example input
    int n = 6;

    // Calculate and print the number of steps to reach 1
    int result = collatz.collatzSteps(n);
    System.out.println("Number of steps to reach 1: " + result); // Output: 8
  }

  /*
   Time Complexity:
   - O(log n), where n is the starting number. The number decreases by about half every few steps, so the time complexity is approximately logarithmic.

   Space Complexity:
   - O(1), since we only use a few variables to track the current value of n and the step count.
  */
}
