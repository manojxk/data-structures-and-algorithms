package cools.backtracking;

/*Given an m x n grid of characters board and a string word, return true if word exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.*/

public class A09WordSearch {

  public boolean exist(char[][] board, String word) {
    int rows = board.length;
    int cols = board[0].length;

    // Start backtracking for each cell in the grid
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        if (backtrack(board, word, row, col, 0)) {
          return true;
        }
      }
    }
    return false;
  }

  // Backtracking function to check if we can find the word from the current position
  private boolean backtrack(char[][] board, String word, int row, int col, int index) {
    // Base case: if the current index matches the word's length, we've found the word
    if (index == word.length()) {
      return true;
    }

    // Boundary checks and whether the current cell matches the word's character
    if (row < 0
        || row >= board.length
        || col < 0
        || col >= board[0].length
        || board[row][col] != word.charAt(index)) {
      return false;
    }

    // Temporarily mark the cell as visited
    char temp = board[row][col];
    board[row][col] = '#'; // Mark as visited

    // Explore all four directions (up, down, left, right)
    boolean found =
        backtrack(board, word, row + 1, col, index + 1)
            || backtrack(board, word, row - 1, col, index + 1)
            || backtrack(board, word, row, col + 1, index + 1)
            || backtrack(board, word, row, col - 1, index + 1);

    // Restore the original value of the cell after backtracking
    board[row][col] = temp;

    return found;
  }

  public static void main(String[] args) {
    A09WordSearch solution = new A09WordSearch();

    char[][] board = {
      {'A', 'B', 'C', 'E'},
      {'S', 'F', 'C', 'S'},
      {'A', 'D', 'E', 'E'}
    };

    String word1 = "ABCCED";
    System.out.println(solution.exist(board, word1)); // Output: true

    String word2 = "SEE";
    System.out.println(solution.exist(board, word2)); // Output: true

    String word3 = "ABCB";
    System.out.println(solution.exist(board, word3)); // Output: false
  }
}
