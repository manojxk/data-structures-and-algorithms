Below is a step‐by‐step explanation of how the backtracking solution fills in the Sudoku board in place, along with its time/space complexity:

---

## 1. Method Overview

```java
public static boolean solveSudoku(int[][] board) {
  return solve(board);
}
```

* We call `solve(board)`, which attempts to fill in every empty cell (zeros) via recursion.
* If it finds a valid digit for every empty spot, it returns `true` and leaves `board` filled with a complete solution.
* If at any point no digit 1–9 can go into a particular empty cell, it backtracks and returns `false`.

---

## 2. The Core Backtracking Function

```java
private static boolean solve(int[][] board) {
  // 1) Scan the board for the next empty cell (represented by 0).
  for (int row = 0; row < 9; row++) {
    for (int col = 0; col < 9; col++) {
      if (board[row][col] == 0) {
        // We found an empty spot. Try each candidate digit 1..9 here.
        for (int num = 1; num <= 9; num++) {
          // 2) Check if placing `num` at (row, col) violates any Sudoku rule.
          if (isValid(board, row, col, num)) {
            // 3) If valid, tentatively place `num` and recurse.
            board[row][col] = num;

            // 4) Recurse. If it eventually leads to a full solution, bubble up true.
            if (solve(board)) {
              return true;
            }

            // 5) Otherwise, undo (backtrack) and try the next candidate.
            board[row][col] = 0;
          }
        }
        // 6) If no digit 1..9 worked at this (row, col), we cannot solve from here.
        //    Trigger backtracking:
        return false;
      }
    }
  }
  // 7) If we finished scanning all rows and columns without finding a zero,
  //    the board is fully filled. That means we have a valid solution:
  return true;
}
```

### How It Works

1. **Find the Next Empty Cell**

   * The nested loops (`row=0..8`, `col=0..8`) look for the first `board[row][col] == 0`.
   * As soon as we hit an empty cell, we try to fill it with digits 1 through 9.

2. **Check Validity with `isValid(...)`**

   * Before placing `num`, we must ensure it doesn’t already appear in the same row, same column, or same 3×3 subgrid.

3. **Place a Candidate and Recurse**

   * If `isValid(...)` returns `true`, we write `board[row][col] = num`.
   * Then we call `solve(board)` recursively.

4. **Successful Recursion**

   * If the recursive call returns `true`, that means the rest of the board (beyond this cell) can also be filled. We immediately return `true` so that the recursion “bubbles up” and halts further searching.

5. **Backtrack on Failure**

   * If the recursive call comes back `false`, it means “no matter how you fill subsequent cells, this choice of `num` at `(row, col)` leads to a dead end.”
   * We then reset `board[row][col] = 0` and try the next digit.

6. **No Digit Works**

   * If none of 1..9 can be placed at `(row, col)`, we return `false` immediately. That unchecked return to the caller signals “back up one level and try a different digit in a previous cell.”

7. **All Cells Are Nonzero**

   * If the loops complete without ever finding a zero, every position is filled. Since we only ever place a digit when `isValid(...)` is satisfied, we know the board obeys all Sudoku constraints. Hence we return `true` to indicate “we have a complete, valid solution.”

---

## 3. Detailed `isValid(...)` Check

```java
private static boolean isValid(int[][] board, int row, int col, int num) {
  // 1) Check this row for the same digit:
  for (int i = 0; i < 9; i++) {
    if (board[row][i] == num) {
      return false;
    }
  }

  // 2) Check this column for the same digit:
  for (int i = 0; i < 9; i++) {
    if (board[i][col] == num) {
      return false;
    }
  }

  // 3) Check the 3×3 subgrid containing (row, col).
  int subgridRowStart = (row / 3) * 3;
  int subgridColStart = (col / 3) * 3;
  for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
      if (board[subgridRowStart + i][subgridColStart + j] == num) {
        return false;
      }
    }
  }

  // 4) If no conflict was found, placing `num` here is valid.
  return true;
}
```

* **Row check (0–8)**: Makes sure `num` isn’t already placed in the same row.
* **Column check (0–8)**: Makes sure `num` isn’t in the same column either.
* **3×3 subgrid check**:

  * Compute the top‐left corner of the 3×3 block:

    ```
    subgridRowStart = (row / 3) * 3
    subgridColStart = (col / 3) * 3
    ```
  * Then inspect those 3×3 positions `(subgridRowStart + i, subgridColStart + j)` for `i, j` in `0..2`.
* If none contain `num`, we return `true`.

---

## 4. Putting It All Together

When you run:

```java
public static void main(String[] args) {
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

  if (solveSudoku(board)) {
    System.out.println("Solved Sudoku Board:");
    for (int r = 0; r < 9; r++) {
      for (int c = 0; c < 9; c++) {
        System.out.print(board[r][c] + " ");
      }
      System.out.println();
    }
  } else {
    System.out.println("No solution exists.");
  }
}
```

* The `solve(board)` recursion will find the unique valid digit for each zero slot, never violating row/column/3×3‐block constraints.
* Once every zero is replaced, `solve(...)` returns `true` at the deepest level and that “true” value propagates upward.
* Finally, the `main` method prints the fully‐filled board.

---

## 5. Time & Space Complexity

* **Time Complexity (Worst Case)**

  * In the absolute worst case, you might try every digit 1–9 at every empty cell, and for each placement you do an O(1) validity check (which itself scans up to 9 cells in the row, 9 in the column, and up to 9 in the 3×3 subgrid—constant work).
  * If there are up to **K** empty cells, the naive bound is roughly $O(9^K)$. However, typical Sudoku‐style constraints (especially on a “standard” valid puzzle with a unique solution) prune the search tree heavily.
  * For a 9×9 board with a valid (unique) solution, this backtracking usually finishes in well under a second in practice.

* **Space Complexity**

  * We only store the 9×9 `board` itself (given).
  * The recursion depth can go as deep as 81 (if the board were empty)—but more realistically it’s only as deep as the number of empty cells. That is **O(81) = O(1)** in big‐O terms (since 9×9 is a constant size).
  * We use no extra arrays or lists—just the call stack and the original 2D array.

---

## 6. Why This Always Finds the Unique Solution

1. We scan left‐to‐right, top‐to‐bottom, filling one empty cell at a time.
2. Whenever we place a digit, we immediately check all Sudoku constraints. Thus we never “break the rules.”
3. We exhaustively try each candidate 1–9 in ascending order. If none work, we backtrack.
4. The problem statement guarantees “the board has exactly one valid solution.” As soon as we fill every zero—because no other digit can violate uniqueness—we reach the base case and return `true`. The recursion unrolls, leaving the board completely filled with that unique solution.

---

### Final Summary

* **`solve(...)`:** Finds the next empty cell, tries digits 1–9, checks validity, recurses.
* **`isValid(...)`:** Ensures no row/column/subgrid conflict before placing a digit.
* **Backtracking:** If a choice leads to no solution downstream, we reset the cell to 0 and try the next candidate.
* **Complexities:**

  * Time: Exponential in theory, but practically O(1) for a 9×9 Sudoku with a unique solution (because the board size is fixed).
  * Space: O(1) extra, aside from the recursion stack which is at most depth 81.

Once `solveSudoku(board)` returns `true`, `board` is modified in place to reflect the filled‐in, valid Sudoku solution.
