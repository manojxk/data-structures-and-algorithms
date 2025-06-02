**Problem Restatement**
You have an $m \times n$ Minesweeper board represented by a 2D array of characters. Each cell is one of:

* `'M'` = an unclicked mine
* `'X'` = a clicked mine (game lost)
* `'H'` = a hidden (unclicked) square
* `'0'–'8'` = a revealed square showing the count of adjacent mines

When the player clicks on cell $(r,c)$:

1. If it contains `'M'`, change it to `'X'` (the player hit a mine and loses).
2. Otherwise, reveal that cell:

   * Compute how many mines are adjacent to $(r,c)$ (in any of the 8 directions).
   * If the count is nonzero, replace `'H'` at $(r,c)$ with the digit `'1'..'8'`.
   * If the count is zero, replace `'H'` at $(r,c)$ with `'0'`, and then **recursively reveal** all adjacent hidden (`'H'`) cells (exactly as if the player clicked each one).

Return (and print) the mutated board after that one click.

---

## Approach in Detail

1. **Check for a mine**

   * If the clicked cell `board[r][c] == 'M'`, set `board[r][c] = 'X'` and stop.
   * Otherwise, proceed to “reveal” logic.

2. **Reveal function**
   We define a recursive `reveal(board, r, c)` that does the following if and only if the current cell is `'H'` (hidden). Otherwise, it returns immediately:

   1. Count how many mines (`'M'`) surround $(r,c)$.
   2. If that count is **> 0**, set `board[r][c] = (char)('0' + count)` and stop (do not recurse further from here).
   3. If that count is **0**, set `board[r][c] = '0'`, then for each of the 8 neighbors $(r + dr, c + dc)$, call `reveal(board, r+dr, c+dc)` to continue revealing.

3. **Helper to count adjacent mines**
   Check all eight direction offsets $\{\pm1, 0\}\times\{\pm1,0\} \setminus \{(0,0)\}$. For each neighbor inside bounds, if it equals `'M'`, increment a counter. Return that count.

4. **Driver**

   * If the clicked cell is `'M'`, flip to `'X'` and return.
   * Otherwise call `reveal(board, row, col)` once.

Because each hidden cell will be visited at most once by `reveal(...)`, the total time is $O(m \times n)$. The recursion stack can, in the worst case (an entire board of zero‐adjacent cells), reach $O(m \times n)$ depth.

---

## Complete Java Code

```java
package medium.recursion;

public class Minesweeper {
  // Eight directions: up-left, up, up-right, left, right, down-left, down, down-right
  private static final int[][] DIRECTIONS = {
    { -1, -1 }, { -1, 0 }, { -1, 1 },
    { 0, -1 },            { 0, 1 },
    { 1, -1 },  { 1, 0 },  { 1, 1 }
  };

  /**
   * Simulates a single player click on the Minesweeper board.
   * Modifies the board in place according to Minesweeper rules.
   *
   * @param board 2D array of chars: 'M', 'X', 'H', or '0'...'8'.
   * @param row   The clicked row index.
   * @param col   The clicked column index.
   */
  public static void click(char[][] board, int row, int col) {
    // 1) If the clicked cell is an unclicked mine, change it to 'X' and return.
    if (board[row][col] == 'M') {
      board[row][col] = 'X';
      return;
    }
    // 2) Otherwise, reveal from that cell.
    reveal(board, row, col);
  }

  /**
   * Recursively reveals the cell at (row, col) if it's 'H'.
   * If there are adjacent mines, set the digit. If no adjacent mines, set '0' and recurse.
   */
  private static void reveal(char[][] board, int row, int col) {
    int m = board.length;
    int n = board[0].length;

    // If out of bounds or not hidden, do nothing.
    if (row < 0 || row >= m || col < 0 || col >= n || board[row][col] != 'H') {
      return;
    }

    // 1) Count adjacent mines
    int mineCount = 0;
    for (int[] dir : DIRECTIONS) {
      int r2 = row + dir[0];
      int c2 = col + dir[1];
      if (r2 >= 0 && r2 < m && c2 >= 0 && c2 < n && board[r2][c2] == 'M') {
        mineCount++;
      }
    }

    // 2) If there are adjacent mines, reveal the number and stop
    if (mineCount > 0) {
      board[row][col] = (char) (mineCount + '0');
      return;
    }

    // 3) Otherwise (no adjacent mines), reveal as '0' and recurse on neighbors
    board[row][col] = '0';
    for (int[] dir : DIRECTIONS) {
      reveal(board, row + dir[0], col + dir[1]);
    }
  }

  // Demo / Testing
  public static void main(String[] args) {
    // Sample Input #1
    char[][] board1 = {
      { 'M', 'M' },
      { 'H', 'H' },
      { 'H', 'H' }
    };
    click(board1, 2, 0);
    // Expected Output:
    // [ ['M','M'],
    //   ['2','2'],
    //   ['0','0'] ]
    System.out.println("Output #1:");
    printBoard(board1);

    // Sample Input #2
    char[][] board2 = {
      { 'H', 'H', 'H', 'H', 'M' },
      { 'H', '1', 'M', 'H', '1' },
      { 'H', 'H', 'H', 'H', 'H' },
      { 'H', 'H', 'H', 'H', 'H' }
    };
    click(board2, 3, 4);
    // Expected Output:
    // [ ['0','1','H','H','M'],
    //   ['0','1','M','2','1'],
    //   ['0','1','1','1','0'],
    //   ['0','0','0','0','0'] ]
    System.out.println("Output #2:");
    printBoard(board2);
  }

  // Helper to print the board
  private static void printBoard(char[][] board) {
    for (char[] row : board) {
      for (char cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
    System.out.println();
  }
}
```

---

### Explanation of the Two Sample Cases

1. **Sample #1**

   ```
   Initial:
   M M
   H H
   H H
   Click at (2,0):
   - board[2][0] == 'H', so we count adjacent mines at (2,0):
     Neighbors: (1,0)=H, (1,1)=H, (2,1)=H → Mine count = 0
     ⇒ board[2][0] = '0' and reveal its neighbors:

     reveal(1,0):
       Adjacent mines: (0,0)=M, (0,1)=M, (2,0)='0', (2,1)=H, (1,1)=H → 2 mines
       ⇒ board[1][0] = '2', STOP (no further recursion here).

     reveal(1,1):
       Adjacent mines: (0,0)=M, (0,1)=M, (1,2) out‐of‐bounds, (2,0)='0', (2,1)=H
       ⇒ 2 mines, board[1][1] = '2', STOP.

     reveal(2,1):
       Adjacent mines: (1,0)='H', (1,1)='H', ... actually still unseen,
                     but we only count actual 'M' → mines = 0
       ⇒ board[2][1] = '0', then recurse on (1,1), (1,2), (2,0), (2,2)…
       Only (1,1) and (2,0) are in bounds and 'H'; but 1,1 is now '2', 2,0 is '0'. So recursion returns immediately.

   Final:
   M M
   2 2
   0 0
   ```

2. **Sample #2**

   ```
   Initial:
   H H H H M
   H 1 M H 1
   H H H H H
   H H H H H
   Click at (3,4):
   - board[3][4] == 'H'. Count adjacent mines around (3,4):
     Neighbors: (2,3)=H, (2,4)=H, (3,3)=H → 0 mines ⇒ board[3][4] = '0'
     Recurse on its eight neighbors: (2,3), (2,4), (3,3), (4,3) out‐of‐bounds, etc.

     reveal(2,3):
       Adjacent to (1,2)='M'? Yes. Also (1,3)=H, (1,4)='1', (2,2)=H, (2,4)=H, (3,2)=H, (3,3)=H, (3,4)='0'.
       Mines = 1 ⇒ board[2][3] = '1', STOP.

     reveal(2,4):
       Adjacent to (1,3)=H, (1,4)='1', (2,3)='1', (3,3)=H, (3,4)='0'. No actual 'M' among those → 0 mines
       ⇒ board[2][4] = '0', recurse on (1,3), (1,4), (2,3), (3,3), (3,4)...

         reveal(1,3):
           Adjacent includes (1,2)='M' & (0,4)='M' → 2 mines ⇒ board[1][3] = '2', STOP.

         reveal(3,3):
           Adjacent includes (2,2)=H, (2,3)='1', (2,4)='0', (3,2)=H, (3,4)='0' → 0 mines
           ⇒ board[3][3] = '0', recurse on neighbors:
             (2,2), (2,3), (2,4), (3,2), (3,4), (4,3) out‐of‐bounds...
             
             reveal(2,2):
               Adjacent to (1,1)='1'? No mine. But (1,2)='M'? Yes → 1 mine ⇒ board[2][2]='1', STOP.

             reveal(3,2):
               Adjacent to (2,1)=H, (2,2)='1', (2,3)='1', (3,1)=H, (3,3)='0' → 0 mines
               ⇒ board[3][2] = '0', recurse further:
                 reveal(2,1):
                   Adjacent to (1,0)=H, (1,1)='1', (1,2)='M'→ mines=1 ⇒ board[2][1]='1', STOP.
                 reveal(3,1):
                   Adjacent to (2,0)=H, (2,1)='1', (2,2)='1', (3,0)=H, (3,2)='0' → 0 mines
                   ⇒ board[3][1] = '0', recurse:
                     reveal(2,0):
                       Adjacent to (1,0)=H, (1,1)='1', (2,1)='1', (3,0)=H, (3,1)='0' → 0 mines
                       ⇒ board[2][0] = '0', recurse:
                         reveal(1,0):
                           Adjacent to (0,0)=H, (0,1)=H, (1,1)='1', (2,0)='0', (2,1)='1' → 0 mines ⇒ '0'
                           …and so on until all connected zeros expand.

   Final board (after all reveals):
   0 1 H H M
   0 1 M 2 1
   0 1 1 1 0
   0 0 0 0 0
   ```

---

## Complexity Analysis

* **Time Complexity:** $O(m \times n)$, because each cell is revealed at most once in the worst‐case recursive expansion.
* **Space Complexity:** $O(m \times n)$ in the worst case, for the recursion stack (if the board is all zeros and floods out).

This completes the Minesweeper “click” simulation. The board is modified in place and printed after performing the single click.
