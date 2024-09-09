package cools.graphs;

/*
 Problem: Surrounded Regions

 You are given an m x n matrix board containing letters 'X' and 'O'. Capture regions that are surrounded:
 1. A cell is connected to adjacent cells horizontally or vertically.
 2. A region is formed by connecting every 'O' cell.
 3. A region is surrounded by 'X' cells if none of its 'O' cells are on the edge of the board.

 You need to capture all surrounded regions by replacing all 'O's with 'X's in the board.

 Example 1:

 Input:
 board = [["X","X","X","X"],
          ["X","O","O","X"],
          ["X","X","O","X"],
          ["X","O","X","X"]]

 Output:
 [["X","X","X","X"],
  ["X","X","X","X"],
  ["X","X","X","X"],
  ["X","O","X","X"]]

 Explanation:
 The bottom 'O' in the second column is not captured because it's connected to the edge.

 Example 2:

 Input:
 board = [["X"]]

 Output: [["X"]]
*/

/*
 Solution Steps:

 1. First, identify the 'O' regions that are connected to the borders and mark them as safe.
 2. Perform a DFS/BFS from the border cells containing 'O' and mark all connected 'O's with a temporary marker (like 'S') to indicate they should not be captured.
 3. After marking the safe 'O's, iterate through the board:
    a) Convert all unmarked 'O's to 'X's because they are surrounded.
    b) Convert all marked 'S's back to 'O's because they are not surrounded.
 4. Return the modified board.
*/

public class A02SurroundedRegions {

  // Directions array to simplify moving up, down, left, and right
  private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

  // Function to capture all surrounded regions
  public void solve(char[][] board) {
    if (board == null || board.length == 0) return;

    int rows = board.length;
    int cols = board[0].length;

    // Step 1: Mark all 'O's connected to the borders as safe by DFS
    for (int i = 0; i < rows; i++) {
      if (board[i][0] == 'O') {
        dfs(board, i, 0);
      }
      if (board[i][cols - 1] == 'O') {
        dfs(board, i, cols - 1);
      }
    }

    for (int j = 0; j < cols; j++) {
      if (board[0][j] == 'O') {
        dfs(board, 0, j);
      }
      if (board[rows - 1][j] == 'O') {
        dfs(board, rows - 1, j);
      }
    }

    // Step 2: Replace all remaining 'O's with 'X's and all 'S's (safe) back to 'O's
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (board[i][j] == 'O') {
          board[i][j] = 'X'; // Surrounded 'O's
        }
        if (board[i][j] == 'S') {
          board[i][j] = 'O'; // Safe 'O's connected to borders
        }
      }
    }
  }

  // DFS to mark safe 'O's (those connected to the borders)
  private void dfs(char[][] board, int row, int col) {
    int rows = board.length;
    int cols = board[0].length;

    // Base case: if out of bounds or not an 'O', return
    if (row < 0 || row >= rows || col < 0 || col >= cols || board[row][col] != 'O') {
      return;
    }

    // Mark the current 'O' as 'S' (safe)
    board[row][col] = 'S';

    // Visit all adjacent cells (up, down, left, right)
    for (int[] direction : DIRECTIONS) {
      dfs(board, row + direction[0], col + direction[1]);
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02SurroundedRegions solution = new A02SurroundedRegions();

    char[][] board1 = {
      {'X', 'X', 'X', 'X'},
      {'X', 'O', 'O', 'X'},
      {'X', 'X', 'O', 'X'},
      {'X', 'O', 'X', 'X'}
    };

    solution.solve(board1);
    printBoard(board1); // Output:
    // [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]

    char[][] board2 = {{'X'}};

    solution.solve(board2);
    printBoard(board2); // Output: [["X"]]
  }

  // Helper function to print the board
  private static void printBoard(char[][] board) {
    for (char[] row : board) {
      for (char cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  /*
   Time Complexity:
   - O(m * n), where m is the number of rows and n is the number of columns in the board. Each cell is visited once during the DFS traversal.

   Space Complexity:
   - O(m * n), in the worst case, the DFS recursion stack can go as deep as the number of cells in the grid.
  */
}
