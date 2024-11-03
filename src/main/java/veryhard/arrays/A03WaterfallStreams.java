package veryhard.arrays;

/*
 * Problem Statement:
 *
 * You are given a 2D array representing a cross-section of a waterfall stream flowing over a one-dimensional elevation map.
 * Each element in the array is either a 1 (block) or a 0 (empty space). The water flows from the top of the array (first row)
 * downwards, and when it encounters a block (1), it can split and flow to the left and/or right. Water will only continue to flow
 * horizontally if there is an empty space to flow into. Otherwise, it will fall downward.
 *
 * Write a function that takes the 2D array and a starting column, where water starts flowing. The function should return an array
 * of the percentages of water that reaches each column at the bottom row.
 *
 * Example:
 *
 * Input:
 * grid = [
 *   [0, 0, 0, 0, 0],
 *   [1, 0, 1, 0, 0],
 *   [0, 0, 0, 0, 0],
 *   [0, 1, 0, 0, 0]
 * ]
 * source = 2
 *
 * Output:
 * [0.0, 0.0, 0.25, 0.25, 0.25]
 *
 * Explanation:
 * Water starts flowing from column 2. It splits upon hitting the block in row 1, flows left and right, and eventually reaches
 * the bottom at different columns. 25% of the water ends up in columns 2, 3, and 4, while no water reaches columns 0 and 1.
 */

/*
 * Solution Approach:
 *
 * 1. Start by simulating the water flow from the given source column.
 * 2. Water will flow down if there's no block, or it will split and flow left and right when it encounters a block.
 * 3. Track the percentage of water that reaches each cell.
 * 4. Once water reaches the bottom row, store the percentage of water that reaches each column.
 */

public class A03WaterfallStreams {

  // Function to calculate the water percentage that reaches each column in the bottom row
  public static double[] waterfallStreams(double[][] grid, int source) {
    // Initialize water flow in the source column with 100% water
    double[] previousRow = new double[grid[0].length];
    previousRow[source] = 100.0;

    // Iterate over each row, simulating the flow of water
    for (int row = 0; row < grid.length; row++) {
      double[] currentRow = new double[grid[0].length];

      for (int col = 0; col < grid[0].length; col++) {
        if (previousRow[col] > 0) { // If there's water in the current column
          if (grid[row][col] == 1) { // If we hit a block
            // Split water left and right if possible
            spreadWater(grid, currentRow, previousRow[col], row, col);
          } else {
            // Otherwise, water flows directly downwards
            currentRow[col] += previousRow[col];
          }
        }
      }
      previousRow = currentRow; // Move to the next row
    }

    return previousRow; // Return the water percentage at the bottom row
  }

  // Helper function to spread water left and right when encountering a block
  private static void spreadWater(
      double[][] grid, double[] currentRow, double waterAmount, int row, int col) {
    // Spread water to the left
    int leftCol = col - 1;
    while (leftCol >= 0 && grid[row][leftCol] == 0) {
      leftCol--;
    }
    if (leftCol + 1 < col) {
      currentRow[leftCol + 1] += waterAmount / 2;
    }

    // Spread water to the right
    int rightCol = col + 1;
    while (rightCol < grid[0].length && grid[row][rightCol] == 0) {
      rightCol++;
    }
    if (rightCol - 1 > col) {
      currentRow[rightCol - 1] += waterAmount / 2;
    }
  }

  // Main function to test the Waterfall Streams implementation
  public static void main(String[] args) {
    double[][] grid = {
      {0, 0, 0, 0, 0},
      {1, 0, 1, 0, 0},
      {0, 0, 0, 0, 0},
      {0, 1, 0, 0, 0}
    };
    int source = 2;

    // Output: [0.0, 0.0, 0.25, 0.25, 0.25]
    double[] result = waterfallStreams(grid, source);
    System.out.print("Waterfall percentages at the bottom: ");
    for (double d : result) {
      System.out.printf("%.2f ", d);
    }
  }

  /*
   * Time Complexity:
   * O(r * c), where r is the number of rows and c is the number of columns in the grid.
   * We iterate over every element of the grid once.
   *
   * Space Complexity:
   * O(c), where c is the number of columns. We maintain two arrays to store the percentage of water for the current row and previous row.
   */
}
