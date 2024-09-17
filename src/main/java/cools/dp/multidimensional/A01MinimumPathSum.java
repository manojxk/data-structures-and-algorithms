package cools.dp.multidimensional;

/*
 * Problem Statement:
 *
 * Given a grid of size m x n filled with non-negative numbers, find a path from the top-left corner to the bottom-right corner,
 * such that the sum of the numbers along the path is minimized. You can only move either down or right at any point in time.
 *
 * Example 1:
 * Input: grid = [[1, 3, 1], [1, 5, 1], [4, 2, 1]]
 * Output: 7
 * Explanation: The path 1 → 3 → 1 → 1 → 1 minimizes the sum to 7.
 *
 * Example 2:
 * Input: grid = [[1, 2, 3], [4, 5, 6]]
 * Output: 12
 *
 * Approach:
 * 1. Use dynamic programming to create a 2D table to store the minimum path sum to reach each cell.
 * 2. Initialize the first row and first column, as you can only reach these cells by moving right or down.
 * 3. For each other cell, calculate the minimum path sum by adding the current cell's value to the minimum value from either
 *    the top or the left neighbor.
 * 4. Return the value at the bottom-right cell, which contains the minimum path sum from the top-left to the bottom-right.
 *
 * Time Complexity:
 * O(m * n): We traverse the entire grid once.
 *
 * Space Complexity:
 * O(m * n): We use an additional 2D array to store the minimum path sums for each cell.
 */

public class A01MinimumPathSum {

  // Function to calculate the minimum path sum in the grid
  public int minPathSum(int[][] grid) {
    int r = grid.length; // Number of rows
    int c = grid[0].length; // Number of columns

    // Create a 2D array to store the minimum path sum at each cell
    int[][] t = new int[r][c];

    // Initialize the top-left corner
    t[0][0] = grid[0][0];

    // Step 1: Initialize the first column (can only come from above)
    for (int i = 1; i < r; i++) {
      t[i][0] = t[i - 1][0] + grid[i][0];
    }

    // Step 2: Initialize the first row (can only come from the left)
    for (int i = 1; i < c; i++) {
      t[0][i] = t[0][i - 1] + grid[0][i];
    }

    // Step 3: Fill in the rest of the grid
    for (int i = 1; i < r; i++) {
      for (int j = 1; j < c; j++) {
        // Calculate the minimum path sum to reach this cell
        t[i][j] = Math.min(t[i - 1][j], t[i][j - 1]) + grid[i][j];
      }
    }

    // Return the value at the bottom-right corner
    return t[r - 1][c - 1];
  }

  // Main function to test the Minimum Path Sum implementation
  public static void main(String[] args) {
    A01MinimumPathSum solution = new A01MinimumPathSum();

    // Example 1 input
    int[][] grid1 = {
      {1, 3, 1},
      {1, 5, 1},
      {4, 2, 1}
    };

    // Calculate and print the minimum path sum
    int result1 = solution.minPathSum(grid1);
    System.out.println("Minimum Path Sum (Example 1): " + result1); // Output: 7

    // Example 2 input
    int[][] grid2 = {
      {1, 2, 3},
      {4, 5, 6}
    };

    // Calculate and print the minimum path sum
    int result2 = solution.minPathSum(grid2);
    System.out.println("Minimum Path Sum (Example 2): " + result2); // Output: 12
  }

  /*
   Time Complexity:
   O(m * n): We traverse each cell in the grid once to compute the minimum path sum.

   Space Complexity:
   O(m * n): We use a 2D array to store the minimum path sums for each cell.
  */
}
