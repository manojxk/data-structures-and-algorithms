**Problem Explanation**

You’re given a 2D matrix (an *m*×*n* array of arrays), for example:

```java
[
  [1, 2, 3],
  [4, 5, 6]
]
```

The **transpose** of this matrix is another matrix where:

* The original **rows** become the new **columns**,
* The original **columns** become the new **rows**.

So the transpose of the example above is a 2×3 matrix turned into a 3×2 matrix:

```
Original (2×3):
 [1, 2, 3]
 [4, 5, 6]

Transposed (3×2):
 [1, 4]
 [2, 5]
 [3, 6]
```

---

## Approach

Because the element at row *r*, column *c* in the original needs to go to row *c*, column *r* in the transpose, we can:

1. **Read dimensions**
   Let `rows = matrix.length` (number of rows) and
   `cols = matrix[0].length` (number of columns).

2. **Allocate output**
   Create a new 2D array `transposed` of size `cols × rows`.

3. **Fill it in**
   Loop through every position `(r, c)` in the original:

   ```java
   transposed[c][r] = matrix[r][c];
   ```

   This “flips” rows ↔ columns.

4. **Return** the new `transposed` array.

For **square matrices** (*rows* == *cols*), you can even swap elements **in-place** across the diagonal, but for a general rectangular matrix you need the extra array.

---

## Step-by-Step Solution

1. **Determine dimensions**:

   ```java
   int rows = matrix.length;
   int cols = matrix[0].length;
   ```
2. **Create result array**:

   ```java
   int[][] transposed = new int[cols][rows];
   ```
3. **Populate**:

   ```java
   for (int r = 0; r < rows; r++) {
     for (int c = 0; c < cols; c++) {
       transposed[c][r] = matrix[r][c];
     }
   }
   ```
4. **Return** `transposed`.

---

## Java Code

```java
package easy.arrays;

public class TransposeMatrix {

  /**
   * Returns the transpose of the given matrix.
   * Works for any m x n matrix.
   *
   * Time:  O(m * n)  — we visit each element exactly once
   * Space: O(m * n)  — for the new transposed matrix
   */
  public static int[][] transpose(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Allocate the transposed matrix of size cols x rows
    int[][] transposed = new int[cols][rows];

    // Flip rows and columns
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        transposed[c][r] = matrix[r][c];
      }
    }

    return transposed;
  }

  /**
   * In-place transpose for a square matrix (n x n).
   * Swaps each element above the diagonal with its mirror.
   *
   * Time:  O(n^2)
   * Space: O(1)
   */
  public static void transposeSquareMatrix(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        // swap matrix[i][j] with matrix[j][i]
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }
  }

   // In-Place Transpose for Square Matrices
  public static void transposeSquareMatrix(int[][] matrix) {
    int n = matrix.length;

    // Swap elements across the diagonal
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        // Swap element at (i, j) with element at (j, i)
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }
  }

  // Example usage
  public static void main(String[] args) {
    int[][] matrix = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };

    int[][] t = transpose(matrix);

    System.out.println("Transposed Matrix:");
    for (int[] row : t) {
      for (int v : row) {
        System.out.print(v + " ");
      }
      System.out.println();
    }
    // Output:
    // 1 4 7
    // 2 5 8
    // 3 6 9
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**
  You visit each of the *m·n* elements exactly once → **O(m·n)**.

* **Space Complexity:**
  You allocate a new array of size *n·m* → **O(m·n)**.
  (For the in-place square-matrix version, extra space is **O(1)**.)
