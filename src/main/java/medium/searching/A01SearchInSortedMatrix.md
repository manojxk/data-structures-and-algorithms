**Problem Restatement**
You have an $m \times n$ matrix of **distinct** integers where each row is sorted ascending‐left‐to‐right, and each column is sorted ascending‐top‐to‐bottom. Given a `target` integer, find its $[row, col]$ if present; otherwise return `[-1, -1]`.

> **Example**
>
> ```
> matrix = [
>   [   1,    4,    7,   12,   15, 1000],
>   [   2,    5,   19,   31,   32, 1001],
>   [   3,    8,   24,   33,   35, 1002],
>   [  40,   41,   42,   44,   45, 1003],
>   [  99,  100,  103,  106,  128, 1004]
> ]
> target = 44
>
> Output: [3, 3]  // because matrix[3][3] == 44
> ```

---

## Approach: “Staircase” Search from Top-Right Corner

1. **Choose Starting Point**

   * Start at **row = 0**, **col = n–1** (top-right corner).
   * Call that value `x = matrix[row][col]`.

2. **Compare and Move**

   * If `x == target`, return `[row, col]`.
   * If `x > target`, then everything **below** in that column is ≥ `x` (because the column is sorted), so `target` cannot be anywhere in that column. **Move left**: `col--`.
   * If `x < target`, then everything **to the left** in that row is ≤ `x`, so `target` cannot be in that row to the left. **Move down**: `row++`.

3. **Termination**

   * Continue while `row < m` and `col >= 0`.
   * If you exit the loop without finding `target`, return `[-1, -1]`.

Because each step either decreases `col` by 1 or increases `row` by 1, you perform at most $m + n$ steps. Hence **time** is $O(m + n)$ and **space** is $O(1)$.

---

## Java Implementation

```java
package medium.searching;

public class SearchInMatrix {

  /**
   * Searches for 'target' in a row- and column-sorted matrix.
   * Returns [row, col] if found, otherwise [-1, -1].
   *
   * @param matrix The m×n integer matrix, rows and columns sorted.
   * @param target The integer to find.
   * @return A two-element array {row, col} or {-1, -1} if not present.
   */
  public static int[] searchMatrix(int[][] matrix, int target) {
    int m = matrix.length;
    int n = matrix[0].length;

    // Start at top-right corner
    int row = 0;
    int col = n - 1;

    while (row < m && col >= 0) {
      int x = matrix[row][col];
      if (x == target) {
        return new int[] { row, col };
      } else if (x > target) {
        // target cannot be in this column
        col--;
      } else {
        // x < target, so target cannot be in this row to the left
        row++;
      }
    }

    // Not found
    return new int[] { -1, -1 };
  }

  // Example usage
  public static void main(String[] args) {
    int[][] matrix = {
      {   1,    4,    7,   12,   15, 1000 },
      {   2,    5,   19,   31,   32, 1001 },
      {   3,    8,   24,   33,   35, 1002 },
      {  40,   41,   42,   44,   45, 1003 },
      {  99,  100,  103,  106,  128, 1004 }
    };
    int target = 44;
    int[] result = searchMatrix(matrix, target);
    System.out.println(java.util.Arrays.toString(result)); // prints [3, 3]
  }
}
```

**Explanation of Key Steps**

* We exploit the fact that from the top-right corner:

  * Everything below in the same column is larger (column-sorted).
  * Everything to the left in the same row is smaller (row-sorted).
* So if the current element is too large, we move left; if too small, we move down.

This “staircase” pattern checks one row or column element on each move, never revisiting a cell. The loop runs at most $m + n$ iterations.

---

### Complexity

* **Time:** $O(m + n)$, where $m$ = number of rows, $n$ = number of columns.
* **Space:** $O(1)$, only a few indices and temporary variables.

If the target is present, it returns its indices; otherwise `[-1, -1]`.
