/*Problem
You are given a matrix of integers where each integer is either 0 or 1. The matrix represents an image where 0 represents white pixels and 1 represents black pixels. An "island" is defined as any group of 1s that are vertically or horizontally adjacent (but not diagonally) and that do not touch the borders of the matrix.

The goal is to modify the input matrix by removing all the islands. An island is removed by replacing all the 1s in that island with 0s. The function should return the modified matrix.*/

/*
Approach
Identify Border-Connected 1s:

First, traverse the matrix and find all 1s that are connected to the border. These 1s are not islands, so we can mark them as safe.
Mark Safe Zones:

We can use a Depth-First Search (DFS) or Breadth-First Search (BFS) to mark all 1s that are connected to these border 1s as safe.
Remove Islands:

Finally, traverse the matrix again. Any 1 that is not marked as safe is part of an island and should be turned into a 0.*/

package medium.graphs;

public class RemoveIslands {

  /**
   * Removes all islands from the matrix and returns the modified matrix.
   *
   * @param matrix The input matrix of 0s and 1s.
   * @return The modified matrix with islands removed.
   */
  public static int[][] removeIslands(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Step 1: Mark all border-connected 1s as safe
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        boolean isBorder = row == 0 || row == rows - 1 || col == 0 || col == cols - 1;
        if (isBorder && matrix[row][col] == 1) {
          markSafe(matrix, row, col);
        }
      }
    }

    // Step 2: Convert all unsafe 1s (islands) to 0s
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (matrix[row][col] == 1) {
          matrix[row][col] = 0;
        } else if (matrix[row][col] == 2) {
          matrix[row][col] = 1; // Convert safe 2s back to 1s
        }
      }
    }

    return matrix;
  }

  /**
   * Marks all connected 1s starting from (row, col) as safe (temporary 2s).
   *
   * @param matrix The input matrix.
   * @param row The current row position.
   * @param col The current column position.
   */
  private static void markSafe(int[][] matrix, int row, int col) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[row][col] != 1) {
      return;
    }

    matrix[row][col] = 2; // Temporarily mark as safe

    // Recursive DFS in all four possible directions
    markSafe(matrix, row - 1, col); // up
    markSafe(matrix, row + 1, col); // down
    markSafe(matrix, row, col - 1); // left
    markSafe(matrix, row, col + 1); // right
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 0, 0, 0, 0, 0},
      {0, 1, 0, 1, 1, 1},
      {0, 0, 1, 0, 1, 0},
      {1, 1, 0, 0, 1, 0},
      {1, 0, 1, 1, 0, 0},
      {1, 0, 0, 0, 0, 1}
    };

    int[][] result = removeIslands(matrix);

    // Output the result matrix
    for (int[] row : result) {
      for (int value : row) {
        System.out.print(value + " ");
      }
      System.out.println();
    }
  }
}

/*
Explanation of Code
Mark Border-Connected 1s:

The outer loop iterates over all border elements and calls the markSafe function for any 1 found on the border.
The markSafe function is a recursive DFS that marks all 1s connected to the border as 2 (temporary safe state).
Remove Islands:

        After marking, the second loop converts all unmarked 1s to 0s and converts the temporary 2s back to 1s.
Time and Space Complexity
Time Complexity:

O(N×M)

Each cell is visited a constant number of times, where
        𝑁
N is the number of rows and
        𝑀
M is the number of columns.
Space Complexity:

O(N×M)

Due to the recursive stack in the DFS, which could potentially reach the size of the matrix.*/
