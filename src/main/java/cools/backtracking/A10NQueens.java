package cools.backtracking;

import java.util.ArrayList;
import java.util.*;

public class A10NQueens {
  public List<List<String>> solveNQueens(int n) {
    List<List<String>> solutions = new ArrayList<>(); // To store the final solutions
    char[][] board = new char[n][n]; // To represent the chessboard

    // Initialize the board with empty spaces
    for (char[] row : board) {
      java.util.Arrays.fill(row, '.');
    }

    // Start backtracking from the first row
    backtrack(solutions, board, 0);
    return solutions;
  }

  // Backtracking function to place queens row by row
  private void backtrack(List<List<String>> solutions, char[][] board, int row) {
    if (row == board.length) {
      // Base case: if all rows are filled, add the board configuration to the solutions
      solutions.add(constructBoard(board));
      return;
    }

    // Try placing a queen in each column of the current row
    for (int col = 0; col < board.length; col++) {
      if (isValid(board, row, col)) {
        // Place the queen
        board[row][col] = 'Q';
        // Recur for the next row
        backtrack(solutions, board, row + 1);
        // Backtrack: remove the queen and try the next position
        board[row][col] = '.';
      }
    }
  }

  // Check if it's valid to place a queen at board[row][col]
  private boolean isValid(char[][] board, int row, int col) {
    // Check the same column for queens
    for (int i = 0; i < row; i++) {
      if (board[i][col] == 'Q') return false;
    }

    // Check the diagonal (upper left)
    for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == 'Q') return false;
    }

    // Check the diagonal (upper right)
    for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
      if (board[i][j] == 'Q') return false;
    }

    return true; // If no conflict, it's a valid placement
  }

  // Construct the board configuration from the current char array
  private List<String> constructBoard(char[][] board) {
    List<String> result = new ArrayList<>();
    for (char[] row : board) {

      result.add(new String(row));
    }
    return result;
  }

  public static void main(String[] args) {
    A10NQueens solution = new A10NQueens();
    int n = 4; // Example input
    List<List<String>> result = solution.solveNQueens(n);

    // Print the result
    for (List<String> board : result) {
      for (String row : board) {
        System.out.println(row);
      }
      System.out.println(); // Separate each solution by a blank line
    }
  }
}
