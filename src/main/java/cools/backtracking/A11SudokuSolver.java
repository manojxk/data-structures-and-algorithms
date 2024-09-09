package cools.backtracking;

public class A11SudokuSolver {

  // Main function to solve the Sudoku board
  public static boolean solveSudoku(int[][] board) {
    return solve(board);
  }

  // Backtracking function to solve the board
  private static boolean solve(int[][] board) {
    // Iterate through each cell on the board
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        // If the current cell is empty (represented by 0)
        if (board[row][col] == 0) {
          // Try filling the cell with digits 1-9
          for (int num = 1; num <= 9; num++) {
            // Check if placing `num` is valid
            if (isValid(board, row, col, num)) {
              // Place the number in the cell
              board[row][col] = num;

              // Recursively attempt to solve the rest of the board
              if (solve(board)) {
                return true; // If successful, return true
              }

              // Backtrack: reset the cell if the number leads to an invalid state
              board[row][col] = 0;
            }
          }
          // If no valid number can be placed, return false (trigger backtracking)
          return false;
        }
      }
    }
    // If the board is fully solved, return true
    return true;
  }

  // Helper function to check if placing a number is valid
  private static boolean isValid(int[][] board, int row, int col, int num) {
    // Check the row for duplicates
    for (int i = 0; i < 9; i++) {
      if (board[row][i] == num) {
        return false;
      }
    }

    // Check the column for duplicates
    for (int i = 0; i < 9; i++) {
      if (board[i][col] == num) {
        return false;
      }
    }

    // Check the 3x3 subgrid for duplicates
    int subgridRowStart = (row / 3) * 3;
    int subgridColStart = (col / 3) * 3;
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[subgridRowStart + i][subgridColStart + j] == num) {
          return false;
        }
      }
    }

    // If the number is not found in the row, column, or subgrid, it's a valid placement
    return true;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample input board
    int[][] board = {
      {7, 8, 0, 4, 0, 0, 1, 2, 0},
      {6, 0, 0, 0, 7, 5, 0, 0, 9},
      {0, 0, 0, 6, 0, 1, 0, 7, 8},
      {0, 0, 7, 0, 4, 0, 2, 6, 0},
      {0, 0, 1, 0, 5, 0, 9, 3, 0},
      {9, 0, 4, 0, 6, 0, 0, 0, 5},
      {0, 7, 0, 3, 0, 0, 0, 1, 2},
      {1, 2, 0, 0, 0, 7, 4, 0, 0},
      {0, 4, 9, 2, 0, 6, 0, 0, 7},
    };

    // Solve the board
    if (solveSudoku(board)) {
      // Print the solved board
      System.out.println("Solved Sudoku Board:");
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          System.out.print(board[i][j] + " ");
        }
        System.out.println();
      }
    } else {
      System.out.println("No solution exists.");
    }
  }
}
/*
https://leetcode.com/discuss/study-guide/1405817/backtracking-algorithm-problems-to-practice*/
