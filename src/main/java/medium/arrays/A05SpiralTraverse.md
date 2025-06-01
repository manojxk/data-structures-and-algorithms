**Problem Explanation**
You have a 2D integer array (matrix) of size $m \times n$. You need to visit **every element exactly once** in a “spiral” pattern:

1. Start at the top-left corner.
2. Move **right** along the top row until you hit the right boundary.
3. Move **down** along the rightmost column.
4. Move **left** along the bottom row.
5. Move **up** along the leftmost column.
6. Shrink the boundaries inward (because the outer layer is now fully visited) and repeat until all elements are collected.

---

## Step-by-Step Approach

1. **Initialize Four Boundaries**

   * `top = 0`
   * `bottom = m − 1`  (last row index)
   * `left = 0`
   * `right = n − 1`   (last column index)

2. **While** `top ≤ bottom` **and** `left ≤ right`, do the following four traversals (if still within bounds), adding each visited element to a result list:

   a) **Traverse from left → right** along row `top`:

   ```java
   for (int col = left; col <= right; col++) {
     result.add(matrix[top][col]);
   }
   top++;  // “Remove” that top row from further consideration
   ```

   b) **Traverse from top → bottom** along column `right`:

   ```java
   for (int row = top; row <= bottom; row++) {
     result.add(matrix[row][right]);
   }
   right--;  // “Remove” that rightmost column
   ```

   c) **Traverse from right → left** along row `bottom`, **only if** `top ≤ bottom` (it might have crossed):

   ```java
   if (top <= bottom) {
     for (int col = right; col >= left; col--) {
       result.add(matrix[bottom][col]);
     }
     bottom--;  // “Remove” that bottom row
   }
   ```

   d) **Traverse from bottom → top** along column `left`, **only if** `left ≤ right`:

   ```java
   if (left <= right) {
     for (int row = bottom; row >= top; row--) {
       result.add(matrix[row][left]);
     }
     left++;  // “Remove” that leftmost column
   }
   ```

3. **Repeat** until `top > bottom` **or** `left > right`, meaning you’ve exhausted all rows or all columns.

4. **Return** the collected `result` list, which now holds every element in spiral order.

---

## Java Implementation

```java
package medium.arrays;

import java.util.ArrayList;
import java.util.List;

public class A05SpiralTraverse {

  /**
   * Returns all elements of the given 2D matrix in spiral order.
   *
   * Time Complexity:  O(m * n)  — visits each element exactly once
   * Space Complexity: O(m * n)  — for storing the result list
   *
   * @param matrix an m x n integer matrix (non-null)
   * @return       a List<Integer> containing the spiral ordering
   */
  public static List<Integer> spiralTraverse(int[][] matrix) {
    List<Integer> result = new ArrayList<>();

    // If the matrix is empty, return an empty list
    if (matrix.length == 0) {
      return result;
    }

    int m = matrix.length;       // number of rows
    int n = matrix[0].length;    // number of columns

    // Initialize the four boundaries
    int top = 0;
    int bottom = m - 1;
    int left = 0;
    int right = n - 1;

    // Traverse layers one by one
    while (top <= bottom && left <= right) {
      // 1) Traverse from left to right along the top row
      for (int col = left; col <= right; col++) {
        result.add(matrix[top][col]);
      }
      top++;  // move the top boundary down

      // 2) Traverse from top to bottom along the right column
      for (int row = top; row <= bottom; row++) {
        result.add(matrix[row][right]);
      }
      right--;  // move the right boundary left

      // 3) Traverse from right to left along the bottom row (if still in bounds)
      if (top <= bottom) {
        for (int col = right; col >= left; col--) {
          result.add(matrix[bottom][col]);
        }
        bottom--;  // move the bottom boundary up
      }

      // 4) Traverse from bottom to top along the left column (if still in bounds)
      if (left <= right) {
        for (int row = bottom; row >= top; row--) {
          result.add(matrix[row][left]);
        }
        left++;  // move the left boundary right
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Example 1:
    int[][] matrix1 = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };
    // Expected spiral: [1, 2, 3, 6, 9, 8, 7, 4, 5]
    System.out.println("Spiral Order: " + spiralTraverse(matrix1));

    // Example 2: Rectangular matrix
    int[][] matrix2 = {
      {1,  2,  3,  4},
      {5,  6,  7,  8},
      {9, 10, 11, 12}
    };
    // Expected spiral: [1,2,3,4,8,12,11,10,9,5,6,7]
    System.out.println("Spiral Order: " + spiralTraverse(matrix2));

    // Example 3: Single row
    int[][] matrix3 = {
      {1, 2, 3, 4, 5}
    };
    // Expected spiral: [1,2,3,4,5]
    System.out.println("Spiral Order: " + spiralTraverse(matrix3));

    // Example 4: Single column
    int[][] matrix4 = {
      {1},
      {2},
      {3},
      {4}
    };
    // Expected spiral: [1,2,3,4]
    System.out.println("Spiral Order: " + spiralTraverse(matrix4));

    // Example 5: 2×2
    int[][] matrix5 = {
      {1, 2},
      {3, 4}
    };
    // Expected spiral: [1,2,4,3]
    System.out.println("Spiral Order: " + spiralTraverse(matrix5));
  }
}
```

---

## How It Works

* **Top boundary**: The row index of the first unvisited row.
* **Bottom boundary**: The row index of the last unvisited row.
* **Left boundary**: The column index of the first unvisited column.
* **Right boundary**: The column index of the last unvisited column.

Each time you complete one pass—from left→right, top→bottom, right→left, bottom→top—you “shrink” the appropriate boundary inward. This ensures you never revisit an element, and you cover the matrix in a spiral until all boundaries cross.

---

## Complexity Analysis

* **Time Complexity:** $O(m \times n)$. Every element is added exactly once.
* **Space Complexity:** $O(m \times n)$. The size of the result list storing all elements.

This method cleanly handles square, rectangular, single‐row, and single‐column matrices, producing the spiral traversal in a single while‐loop of boundary checks.
