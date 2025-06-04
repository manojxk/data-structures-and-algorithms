**Problem Restatement**
Given an *n*×*m* matrix of integers, output all elements in a “zigzag” order starting from the top‐left corner and moving toward the bottom‐right. The zigzag pattern means alternating between moving “down‐right” and “up‐left,” switching direction whenever you hit a boundary.

---

## How the Algorithm Works

1. **Initialize**

   * Let `numRows = matrix.length` and `numCols = matrix[0].length`.
   * Start at `(row, col) = (0, 0)`.
   * Keep a boolean flag `goingDown = true` to indicate the current direction.
   * Create an empty `List<Integer> result` to collect the visited values.

2. **Repeat Until Every Cell Is Visited**
   In a `while (row < numRows && col < numCols)` loop:

   a. **Add** `matrix[row][col]` to `result`.

   b. **If** `goingDown == true` (we are moving “down‐right”):

   * If we are at the **bottom row** (`row == numRows–1`), we cannot move further down, so switch direction and move right:

     ```java
     goingDown = false;
     col++;
     ```
   * Else if we are at the **first column** (`col == 0`), we cannot move further left, so switch direction and move down:

     ```java
     goingDown = false;
     row++;
     ```
   * Otherwise, we are not on an edge, so simply step “down‐right” one cell:

     ```java
     row++;
     col--;
     ```

   c. **Else** (i.e. `goingDown == false`, we are moving “up‐left”):

   * If we are at the **rightmost column** (`col == numCols–1`), we cannot move further right, so switch direction and move down one row:

     ```java
     goingDown = true;
     row++;
     ```
   * Else if we are at the **top row** (`row == 0`), we cannot move further up, so switch direction and move right one column:

     ```java
     goingDown = true;
     col++;
     ```
   * Otherwise (no boundary), step “up‐left” one cell:

     ```java
     row--;
     col++;
     ```

3. **When `row` or `col` Goes Outside**
   As soon as `(row, col)` moves off the bottom (`row == numRows`) or off the right (`col == numCols`), we exit the loop. By that point, we have appended exactly `numRows × numCols` integers to `result` in zigzag order.

---

## Complete Java Code

```java
import java.util.*;

public class A05ZigzagTraverse {

  /**
   * Returns the elements of `matrix` in zigzag order (down-right / up-left).
   * Time:  O(n * m)    // we visit each cell exactly once
   * Space: O(n * m)    // we store all n*m elements in the output list
   */
  public static List<Integer> zigzagTraverse(int[][] matrix) {
    List<Integer> result = new ArrayList<>();

    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return result;
    }

    int numRows = matrix.length;
    int numCols = matrix[0].length;
    int row = 0, col = 0;
    boolean goingDown = true;  // start by moving “down-right”

    // We will visit exactly numRows * numCols elements
    while (row < numRows && col < numCols) {
      // 1) Add current element
      result.add(matrix[row][col]);

      // 2) Decide next (row, col) based on direction and boundary
      if (goingDown) {
        // We want to step “down-right,” except when we hit bottom or first column
        if (row == numRows - 1) {
          // Hit bottom row → must go right next, and flip direction
          goingDown = false;
          col++;
        } else if (col == 0) {
          // Hit first column (and not bottom) → must go down next, and flip direction
          goingDown = false;
          row++;
        } else {
          // Normal “down-right” step
          row++;
          col--;
        }
      } else {
        // We want to step “up-left,” except when we hit top or last column
        if (col == numCols - 1) {
          // Hit rightmost column → must go down next, and flip direction
          goingDown = true;
          row++;
        } else if (row == 0) {
          // Hit top row (and not last column) → must go right next, and flip direction
          goingDown = true;
          col++;
        } else {
          // Normal “up-left” step
          row--;
          col++;
        }
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Example 1
    int[][] matrix1 = {
      {1,  3,  4, 10},
      {2,  5,  9, 11},
      {6,  8, 12, 15},
      {7, 13, 14, 16}
    };
    System.out.println(zigzagTraverse(matrix1));
    // Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]

    // Example 2
    int[][] matrix2 = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };
    System.out.println(zigzagTraverse(matrix2));
    // Expected: [1, 4, 2, 3, 5, 7, 8, 6, 9]
  }
}
```

---

## Why This Correctly Traverses in Zigzag Order

1. **Starting Point (0,0)**
   We add `matrix[0][0]` first.

2. **goingDown = true ⇒ aim to move “down-right.”**

   * If we aren’t blocked by the bottom row or first column, “down-right” means `(row+1, col−1)`.
   * But if we are already on the bottom row, we can’t go down; instead, we must move right one column and flip direction.
   * If we are on the first column, we can’t move left; instead, we move down one row and flip direction.

3. **goingDown = false ⇒ aim to move “up-left.”**

   * If we aren’t blocked by the top row or last column, “up-left” is `(row−1, col+1)`.
   * But if we are on the top row, we can’t move up; instead, we move right one column and flip direction.
   * If we are on the last column, we can’t move right; instead, we move down one row and flip direction.

4. We repeat until our `(row, col)` indices no longer lie in the valid range `(row < numRows && col < numCols)`. At that point, we have visited every cell exactly once.

---

## Complexity

* **Time Complexity: O(n \* m)**

  * We add one element to `result` per iteration of the while‐loop. The loop continues until we have visited all `n * m` elements exactly once.
  * The boundary checks and index updates inside the loop are all constant‐time.

* **Space Complexity: O(n \* m)**

  * We store all `n * m` elements in the output `List<Integer>`.
  * Aside from that, we only use a few integer variables (`row`, `col`, `goingDown`).

This solves the “Zigzag Traverse” problem in linear time and linear space (linear in the total number of elements).
