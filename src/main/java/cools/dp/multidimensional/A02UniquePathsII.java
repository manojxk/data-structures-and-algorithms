package cools.dp.multidimensional;

/*
 * Problem Statement:
 *
 * You are given an m x n grid representing a robot's path. The robot is located at the top-left corner (grid[0][0])
 * and wants to move to the bottom-right corner (grid[m-1][n-1]). The robot can only move either down or right at any time.
 * The grid contains obstacles (marked as 1) and open spaces (marked as 0). A path that the robot takes cannot include
 * any square that is an obstacle.
 *
 * The goal is to return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 *
 * Example 1:
 * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * Output: 2
 *
 * Example 2:
 * Input: obstacleGrid = [[0,1],[0,0]]
 * Output: 1
 *
 * Constraints:
 * - m == obstacleGrid.length
 * - n == obstacleGrid[i].length
 * - 1 <= m, n <= 100
 * - obstacleGrid[i][j] is 0 or 1
 */

/*
 * Approach:
 * 1. Use dynamic programming to keep track of the number of ways to reach each cell.
 * 2. Initialize a 2D array `dp` where dp[i][j] represents the number of ways to reach cell (i, j).
 * 3. If a cell contains an obstacle (grid[i][j] == 1), the value of dp[i][j] will be 0, as no path can pass through this cell.
 * 4. Initialize the first row and first column separately since they only have one possible direction.
 * 5. For each remaining cell, dp[i][j] is the sum of dp[i-1][j] (from the top) and dp[i][j-1] (from the left).
 * 6. Base case: The starting point dp[0][0] is 1 unless there is an obstacle there.
 */

public class A02UniquePathsII {

  public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
    // Handle edge cases
    if (obstacleGrid == null || obstacleGrid.length == 0) {
      return 0;
    }

    int m = obstacleGrid.length; // Number of rows
    int n = obstacleGrid[0].length; // Number of columns

    // If the starting point or the destination is an obstacle, there are no paths
    if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) {
      return 0;
    }

    // Create a dp array to store the number of paths to each cell
    int[][] dp = new int[m][n];

    // Initialize the starting point
    dp[0][0] = 1;

    // Fill the first column: There is only one way to reach any cell in the first column (from the
    // top), unless blocked by an obstacle
    for (int i = 1; i < m; i++) {
      if (obstacleGrid[i][0] == 1) {
        dp[i][0] = 0; // Obstacle found, no path can continue
      } else {
        dp[i][0] = dp[i - 1][0]; // Carry forward the number of paths from the cell above
      }
    }

    // Fill the first row: There is only one way to reach any cell in the first row (from the left),
    // unless blocked by an obstacle
    for (int i = 1; i < n; i++) {
      if (obstacleGrid[0][i] == 1) {
        dp[0][i] = 0; // Obstacle found, no path can continue
      } else {
        dp[0][i] = dp[0][i - 1]; // Carry forward the number of paths from the cell to the left
      }
    }

    // Fill the rest of the grid using dynamic programming
    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (obstacleGrid[i][j] == 1) {
          dp[i][j] = 0; // No path through an obstacle
        } else {
          dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // Sum the paths from the top and the left
        }
      }
    }

    // The bottom-right corner will contain the number of unique paths to reach the destination
    return dp[m - 1][n - 1];
  }

  // Main function to test the implementation
  public static void main(String[] args) {
    // Example 1
    int[][] obstacleGrid1 = {
      {0, 0, 0},
      {0, 1, 0},
      {0, 0, 0}
    };
    System.out.println(
        "Number of unique paths: " + uniquePathsWithObstacles(obstacleGrid1)); // Output: 2

    // Example 2
    int[][] obstacleGrid2 = {
      {0, 1},
      {0, 0}
    };
    System.out.println(
        "Number of unique paths: " + uniquePathsWithObstacles(obstacleGrid2)); // Output: 1
  }
}

/*
 * Time Complexity:
 * O(m * n), where m is the number of rows and n is the number of columns in the grid.
 * We visit each cell once and compute the number of paths based on previous results.
 *
 * Space Complexity:
 * O(m * n), as we use a 2D dp array to store the number of paths to each cell.
 */
