**Problem Restatement**
Given an $N \times M$ matrix of 0s (white) and 1s (black), an **island** is any group of 1s that is connected vertically or horizontally **and does not touch any border cell**. Remove every such island by turning its 1s into 0s, while leaving any 1s that are connected (directly or indirectly) to the matrix boundary unchanged.

> **Example**
>
> ```
> Input:
> [
>   [1, 0, 0, 0, 0, 0],
>   [0, 1, 0, 1, 1, 1],
>   [0, 0, 1, 0, 1, 0],
>   [1, 1, 0, 0, 1, 0],
>   [1, 0, 1, 1, 0, 0],
>   [1, 0, 0, 0, 0, 1]
> ]
>
> Any 1s that can reach the border via a path of adjacent 1s are “safe.” Others are islands.
>
> Output (after removal):
> [
>   [1, 0, 0, 0, 0, 0],
>   [0, 0, 0, 1, 1, 1],
>   [0, 0, 0, 0, 1, 0],
>   [1, 1, 0, 0, 1, 0],
>   [1, 0, 0, 0, 0, 0],
>   [1, 0, 0, 0, 0, 1]
> ]
> ```
>
> Here, the “1” at $(1,1)$ and $(2,2)$ are islands (they never connect to any border cell), so they become 0. All other 1s are connected (directly or via neighbors) to the boundary, so they stay 1.

---

## Approach Overview

1. **Identify Border-Connected 1s (Safe Cells)**

   * Any 1 that lies on the outermost rows or columns, or that can be reached from such a 1 by moving up/down/left/right through adjacent 1s, is **not** an island. We will “mark” all those safe 1s first.

2. **Mark Safe Cells via DFS**

   * Traverse each cell on the four borders.
   * Whenever you see a 1 on the border that is not yet marked, start a **Depth-First Search (DFS)** from that cell to mark every reachable 1 (connected orthogonally) as “safe.”
   * We will temporarily overwrite each safe 1 with a different value—say, **2**—to distinguish it from unvisited 1s (potential islands).

3. **Remove Islands**

   * After marking, any cell that remains equal to 1 is part of an island. Change it to 0.
   * Any cell marked 2 was safe; change it back to 1.

Because each cell is visited at most once during the DFS marking, this runs in $O(N \times M)$ time and uses $O(N \times M)$ space on the recursion stack in the worst case.

---

## Detailed Steps

1. **Iterate Over Border Cells**

   * For each $(r, c)$ where $r = 0$ or $r = N-1$ or $c = 0$ or $c = M-1$, if `matrix[r][c] == 1`, call `markSafe(matrix, r, c)`.

2. **DFS to Mark Safe 1s**

   ```java
   private static void markSafe(int[][] matrix, int r, int c) {
     int N = matrix.length;
     int M = matrix[0].length;
     // Base case: out of bounds, or not a 1
     if (r < 0 || r >= N || c < 0 || c >= M || matrix[r][c] != 1) {
       return;
     }
     // Temporarily mark this 1 as “safe” by setting it to 2
     matrix[r][c] = 2;
     // Recurse in four directions
     markSafe(matrix, r - 1, c); // up
     markSafe(matrix, r + 1, c); // down
     markSafe(matrix, r, c - 1); // left
     markSafe(matrix, r, c + 1); // right
   }
   ```

   * Every time you encounter a 1, you change it to 2 and continue in all four directions.
   * This ensures all 1s reachable from the border (i.e. non-island cells) become 2.

3. **Convert Unmarked 1s (Islands) to 0; Convert 2s Back to 1**

   ```java
   for (int r = 0; r < N; r++) {
     for (int c = 0; c < M; c++) {
       if (matrix[r][c] == 1) {
         // Still 1 means it was not marked safe → it’s an island → remove it
         matrix[r][c] = 0;
       } else if (matrix[r][c] == 2) {
         // It was marked safe → revert back to 1
         matrix[r][c] = 1;
       }
       // Any 0 stays 0
     }
   }
   ```

4. **Return the Modified Matrix**
   After these two passes, every original island-cell (unvisited 1) is now 0, and every safe cell (temporarily 2) is restored to 1.

---

## Complete Java Implementation

```java
package medium.graphs;

public class RemoveIslands {

  /**
   * Removes all “islands” (1s not connected to any border 1) by turning them into 0s.
   *
   * @param matrix The input 2D array of 0s and 1s.
   * @return The same matrix after all islands have been removed.
   */
  public static int[][] removeIslands(int[][] matrix) {
    int N = matrix.length;
    int M = matrix[0].length;

    // 1) Mark all border-connected 1s as “safe” (temporary value = 2)
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < M; c++) {
        // Only look at border cells
        boolean isBorder = (r == 0) || (r == N - 1) || (c == 0) || (c == M - 1);
        if (isBorder && matrix[r][c] == 1) {
          markSafe(matrix, r, c);
        }
      }
    }

    // 2) Convert unmarked 1s (islands) → 0; convert 2s back to 1
    for (int r = 0; r < N; r++) {
      for (int c = 0; c < M; c++) {
        if (matrix[r][c] == 1) {
          // Still 1 means it was never marked safe → it's an island
          matrix[r][c] = 0;
        } else if (matrix[r][c] == 2) {
          // It was marked safe → restore to 1
          matrix[r][c] = 1;
        }
        // If it's 0, leave it as is
      }
    }

    return matrix;
  }

  /**
   * Recursively marks all 1s reachable from (r,c) as “safe” (change to 2).
   *
   * @param matrix The input grid
   * @param r      Row index
   * @param c      Column index
   */
  private static void markSafe(int[][] matrix, int r, int c) {
    int N = matrix.length;
    int M = matrix[0].length;

    // Base case: out of bounds or not a “1”
    if (r < 0 || r >= N || c < 0 || c >= M || matrix[r][c] != 1) {
      return;
    }
    // Mark current cell as safe
    matrix[r][c] = 2;

    // Recurse in all four directions
    markSafe(matrix, r - 1, c); // up
    markSafe(matrix, r + 1, c); // down
    markSafe(matrix, r, c - 1); // left
    markSafe(matrix, r, c + 1); // right
  }

  // For demonstration
  public static void main(String[] args) {
    int[][] matrix = {
      {1, 0, 0, 0, 0, 0},
      {0, 1, 0, 1, 1, 1},
      {0, 0, 1, 0, 1, 0},
      {1, 1, 0, 0, 1, 0},
      {1, 0, 1, 1, 0, 0},
      {1, 0, 0, 0, 0, 1}
    };

    int[][] result = removeIslands(matrix);

    // Print the resulting matrix
    for (int[] row : result) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }
    // Expected output:
    // 1 0 0 0 0 0
    // 0 0 0 1 1 1
    // 0 0 0 0 1 0
    // 1 1 0 0 1 0
    // 1 0 0 0 0 0
    // 1 0 0 0 0 1
  }
}
```

---

### Explanation of Key Points

* **Why only start DFS from border 1s?**
  An island is any group of 1s that does **not** touch the border. Conversely, any 1 connected (via its neighbors) to the border must remain. By marking all border-connected 1s first, any 1 left unmarked is guaranteed to be part of an interior island.

* **Why use a temporary marker “2”?**
  So that we do not confuse “already‐visited safe” cells with “still‐unvisited potential islands.” After the DFS, all safe cells become 2, then we sweep and revert 2s → 1s, while converting any leftover 1s → 0s.

* **Time Complexity: $O(N \times M)$**

  * The first double loop visits each border cell at most once, and each DFS call visits each reachable 1 at most once.
  * Across all border calls, every safe cell is marked once.
  * The final sweep also touches every cell once.
    → Overall linear in the number of cells.

* **Space Complexity: $O(N \times M)$**

  * We store no extra data structures except for the call stack of the DFS. In the worst case, if the entire matrix is 1s, a single DFS might push $N \times M$ calls onto the stack.

This completes the procedure to remove all islands, leaving only the black-pixel regions that connect to the border.
