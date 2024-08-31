/*
 * Problem Statement:
 * Minesweeper is a video game where the player clicks on squares in a grid that contains hidden mines.
 * The goal is to reveal all the squares without detonating any mines. Each revealed square shows either the
 * number of adjacent mines or is blank if there are no adjacent mines.
 *
 * You're given a 2D array of strings representing a Minesweeper board in progress. Each string represents:
 * - "M": An unclicked mine.
 * - "X": A clicked mine, indicating a lost game.
 * - "H": A hidden cell that hasn't been clicked.
 * - "0-8": A revealed cell showing the number of adjacent mines. Numbered cells can't be clicked again.
 *
 * You're also given a row and column that represents the indices of the square the player clicks next.
 * Your task is to write a function that returns the updated board after the click. The function can mutate the input board.
 *
 * Rules:
 * - If the player clicks on a mine ("M"), replace it with "X".
 * - If the player clicks on a cell adjacent to a mine, replace "H" with the number of adjacent mines.
 * - If the player clicks on a cell with no adjacent mines, replace "H" with "0" and reveal all adjacent hidden cells
 *   as if the player clicked on them.
 *
 * Assume that the given row and column will always represent a legal move.
 *
 * Example:
 *
 * Sample Input #1:
 * board = [
 *  ["M", "M"],
 *  ["H", "H"],
 *  ["H", "H"]
 * ]
 * row = 2
 * column = 0
 *
 * Sample Output #1:
 * [
 *  ["M", "M"],
 *  ["2", "2"],
 *  ["0", "0"]
 * ]
 *
 * Sample Input #2:
 * board = [
 *  ["H", "H", "H", "H", "M"],
 *  ["H", "1", "M", "H", "1"],
 *  ["H", "H", "H", "H", "H"],
 *  ["H", "H", "H", "H", "H"]
 * ]
 * row = 3
 * column = 4
 *
 * Sample Output #2:
 * [
 *  ["0", "1", "H", "H", "M"],
 *  ["0", "1", "M", "2", "1"],
 *  ["0", "1", "1", "1", "0"],
 *  ["0", "0", "0", "0", "0"]
 * ]
 */
/*Approach:
Handling Mines: If the clicked cell contains a mine ("M"), replace it with "X" to indicate game over.
Counting Adjacent Mines: For other cells, count the number of adjacent mines. If the clicked cell is adjacent to mines, update it with that count.
Recursive Reveal: If the clicked cell has no adjacent mines, reveal all adjacent hidden cells ("H") recursively.
Time Complexity:
O(m * n): Where m and n are the dimensions of the board. In the worst case, you might have to reveal every cell.
Space Complexity:
O(m * n): Space used by the recursion stack in the worst case where the board is fully revealed.*/

package medium;

public class Minesweeper {
  private static final int[][] DIRECTIONS = {
    {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
  };

  public static void click(char[][] board, int row, int col) {
    if (board[row][col] == 'M') {
      board[row][col] = 'X';
    } else {
      reveal(board, row, col);
    }
  }

  private static void reveal(char[][] board, int row, int col) {
    if (row < 0
        || row >= board.length
        || col < 0
        || col >= board[0].length
        || board[row][col] != 'H') {
      return;
    }

    int mines = countMines(board, row, col);
    if (mines > 0) {
      board[row][col] = (char) (mines + '0');
    } else {
      board[row][col] = '0';
      for (int[] dir : DIRECTIONS) {
        reveal(board, row + dir[0], col + dir[1]);
      }
    }
  }

  private static int countMines(char[][] board, int row, int col) {
    int count = 0;
    for (int[] dir : DIRECTIONS) {
      int r = row + dir[0], c = col + dir[1];
      if (r >= 0 && r < board.length && c >= 0 && c < board[0].length && board[r][c] == 'M') {
        count++;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    char[][] board = {
      {'H', 'H', 'H', 'H'},
      {'H', 'M', 'H', 'H'},
      {'H', 'H', 'H', 'H'},
      {'H', 'H', 'H', 'H'}
    };
    click(board, 0, 0);
    for (char[] row : board) {
      for (char cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
  }
}
