package cools.arrays;

/*
 Problem: Candy

 There are n children standing in a line. Each child is assigned a rating value given in the integer array ratings.
 You are giving candies to these children subjected to the following requirements:
 - Each child must have at least one candy.
 - Children with a higher rating get more candies than their neighbors.

 Return the minimum number of candies you need to distribute the candies to the children.

 Constraints:
 - n == ratings.length
 - 1 <= n <= 2 * 10^4
 - 0 <= ratings[i] <= 2 * 10^4

 Example 1:
 Input: ratings = [1,0,2]
 Output: 5
 Explanation: You can allocate to the first, second and third child with 2, 1, and 2 candies respectively.

 Example 2:
 Input: ratings = [1,2,2]
 Output: 4
 Explanation: You can allocate to the first, second, and third child with 1, 2, and 1 candies respectively.
 The third child gets 1 candy because it satisfies the conditions.

 Solution Approach:
 1. Use two passes through the ratings array:
    - First pass: Assign more candies to children with higher ratings compared to the left neighbor.
    - Second pass: Assign more candies to children with higher ratings compared to the right neighbor, ensuring the correct minimal distribution.
 2. Sum up the candies to get the total minimum number required.
*/

public class A14Candy {

  // Function to calculate the minimum number of candies needed
  public int candy(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];

    // Step 1: Initialize each child with at least one candy
    for (int i = 0; i < n; i++) {
      candies[i] = 1;
    }

    // Step 2: Left to right pass - give more candies to children with a higher rating than the left
    // neighbor
    for (int i = 1; i < n; i++) {
      if (ratings[i] > ratings[i - 1]) {
        candies[i] = candies[i - 1] + 1;
      }
    }

    // Step 3: Right to left pass - give more candies to children with a higher rating than the
    // right neighbor
    for (int i = n - 2; i >= 0; i--) {
      if (ratings[i] > ratings[i + 1]) {
        candies[i] = Math.max(candies[i], candies[i + 1] + 1);
      }
    }

    // Step 4: Sum up the total number of candies
    int totalCandies = 0;
    for (int candy : candies) {
      totalCandies += candy;
    }

    return totalCandies;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A14Candy solution = new A14Candy();

    // Example 1
    int[] ratings1 = {1, 0, 2};
    System.out.println("Minimum Candies: " + solution.candy(ratings1)); // Output: 5

    // Example 2
    int[] ratings2 = {1, 2, 2};
    System.out.println("Minimum Candies: " + solution.candy(ratings2)); // Output: 4
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the ratings array. We traverse the array twice (left to right, and right to left).

   Space Complexity:
   - O(n), since we use an extra array to store the number of candies for each child.
  */
}
