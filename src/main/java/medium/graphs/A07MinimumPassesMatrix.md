**Problem Restatement**
You have an $N \times M$ integer matrix containing negative numbers, zeroes, and positive numbers. In one “pass,” any negative cell that is **adjacent** (up, down, left, or right) to a positive cell becomes positive (we multiply it by –1). Zeros do not convert anything. You must find the **minimum number of passes** required so that **all** negative numbers become positive. If it is impossible to convert every negative, return –1.

> **Example**
>
> ```
> Input:
> [
>   [ 0, -1, -3,  2,  0],
>   [ 1, -2, -5, -1, -3],
>   [ 3,  0,  0, -4, -1]
> ]
>
> Pass 1 converts:
>   - (-1) at (0,1) and (-3) at (1,2) and (-1) at (1,3) and (-3) at (1,4) and (-4) at (2,3) 
>     → those adjacent to positives → become positive.
> Matrix becomes:
> [
>   [ 0,  1, -3,  2,  0],
>   [ 1,  2,  5,  1, -3],
>   [ 3,  0,  0,  4, -1]
> ]
>
> Pass 2 converts:
>   - (-3) at (0,2), (-3) at (1,4), (-1) at (2,4) 
>     → now all are adjacent to positives.  
> Matrix becomes:
> [
>   [ 0,  1,  3,  2,  0],
>   [ 1,  2,  5,  1,  3],
>   [ 3,  0,  0,  4,  1]
> ]
>
> Pass 3:  
>   - There are no more negatives left, so we’re done.  
>
> Output: 3
> ```

---

## High‐Level Approach: Multi‐Source BFS

1. **Gather All Initial Positives**

   * We put every positive cell’s coordinates into a queue. These are our “sources” because a positive can convert adjacent negatives in the next pass.

2. **Count Initial Negatives**

   * Keep a counter of all negative cells. If it’s zero at the start, return 0 immediately (no passes needed).

3. **Breadth‐First Search (BFS) Over Passes**

   * We process the queue level by level. Each “level” of BFS corresponds to one pass.
   * At pass $k$, every cell in the queue is guaranteed to be positive (either originally or converted in pass $k-1$). For each such cell $(r,c)$, look at its four neighbors $(r-1,c), (r+1,c), (r,c-1), (r,c+1)$.

     * If a neighbor is negative, flip it to positive and enqueue it for the **next** pass. Also decrement the negative‐count.
   * After processing all cells that were enqueued at the start of this pass, if at least one conversion happened, increment `passes` by 1. Otherwise, if no new neighbor was flipped, we can’t convert anything further.

4. **Check Completion**

   * When the BFS queue becomes empty, check if any negatives remain (negative‐count > 0). If yes, return –1; otherwise return `passes`.

---

## Detailed Steps

1. **Initialize**

   ```java
   int rows = matrix.length;
   int cols = matrix[0].length;
   Queue<int[]> queue = new LinkedList<>();
   int negativeCount = 0;
   ```

   * We will store coordinates $(r, c)$ of every positive cell in `queue`.
   * We also keep `negativeCount` to know when we’ve converted them all.

2. **Populate Queue and Count Negatives**

   ```java
   for (int r = 0; r < rows; r++) {
     for (int c = 0; c < cols; c++) {
       if (matrix[r][c] > 0) {
         queue.offer(new int[]{r, c});
       } else if (matrix[r][c] < 0) {
         negativeCount++;
       }
     }
   }
   // If there are no negatives at all, zero passes needed:
   if (negativeCount == 0) return 0;
   ```

3. **BFS Passes**

   * We keep an array of four direction vectors for “up, down, left, right”:

     ```java
     int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
     int passes = 0;
     ```
   * While there are still cells in the queue to process:

     ```java
     while (!queue.isEmpty()) {
       int size = queue.size();
       boolean convertedAny = false; // Did we flip at least one neighbor this pass?

       // Process exactly 'size' entries—these are all positives from the previous pass
       for (int i = 0; i < size; i++) {
         int[] cell = queue.poll();
         int r = cell[0], c = cell[1];

         // Check all 4 neighbors
         for (int[] d : directions) {
           int nr = r + d[0], nc = c + d[1];
           // If neighbor is in bounds and negative, convert it
           if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
               && matrix[nr][nc] < 0) {
             matrix[nr][nc] *= -1;      // flip to positive
             queue.offer(new int[]{nr, nc});
             negativeCount--;
             convertedAny = true;
           }
         }
       }

       // If we did convert at least one, that counts as one complete pass
       if (convertedAny) {
         passes++;
       }
     }
     ```

4. **Final Check**

   ```java
   return (negativeCount == 0) ? passes : -1;
   ```

   * If any negative remains unreachable, we return -1. Otherwise, `passes` holds the minimum number of conversions needed.

---

## Why This Works

* **Multi‐Source BFS**: By enqueuing **all positive cells initially**, we effectively start from all sources simultaneously. In each BFS “level” (i.e., one full iteration over the queue’s current size), every positive cell can convert its negative neighbors **in parallel**. That corresponds exactly to how the problem describes a “pass.”
* **Tracking `negativeCount`** ensures that once it hits zero, we know every negative has been flipped.
* If the queue empties out while still `negativeCount > 0`, there are isolated negative cells that never touched a positive—so the answer is -1.

---

## Time & Space Complexity

* **Time Complexity:** $O(N \times M)$, where $N = \text{rows}$ and $M = \text{cols}$.

  * We push each positive (and each newly converted positive) into the queue exactly once.
  * Each dequeued cell checks up to four neighbors. Therefore, each matrix cell and each edge is touched a constant number of times.

* **Space Complexity:** $O(N \times M)$.

  * In the worst case, the queue can hold all cells (if they all become positive eventually).
  * We also store no other large auxiliary structures beyond this queue and a few counters.

---

## Final Java Code

```java
package medium.graphs;

import java.util.*;

public class MinimumPassesMatrix {

  /**
   * Returns the minimum number of passes needed to convert all negative integers in the matrix to positives.
   * If it’s impossible, returns -1.
   */
  public static int minimumPasses(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int negativeCount = 0;

    // Step 1: Enqueue all positive cells and count negatives
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        if (matrix[r][c] > 0) {
          queue.offer(new int[]{r, c});
        } else if (matrix[r][c] < 0) {
          negativeCount++;
        }
      }
    }
    // If there are no negatives, no passes needed
    if (negativeCount == 0) return 0;

    int passes = 0;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // Step 2: BFS by “levels,” each level = one pass
    while (!queue.isEmpty()) {
      int size = queue.size();
      boolean convertedAny = false;

      for (int i = 0; i < size; i++) {
        int[] cell = queue.poll();
        int r = cell[0], c = cell[1];

        for (int[] d : directions) {
          int nr = r + d[0], nc = c + d[1];
          if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
              && matrix[nr][nc] < 0) {
            // Convert the negative neighbor to positive
            matrix[nr][nc] *= -1;
            queue.offer(new int[]{nr, nc});
            negativeCount--;
            convertedAny = true;
          }
        }
      }

      // If we flipped at least one this pass, increment pass count
      if (convertedAny) {
        passes++;
      }
    }

    // Step 3: If any negative remains, impossible → -1; else return passes
    return (negativeCount == 0) ? passes : -1;
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {0, -1, -3,  2,  0},
      {1, -2, -5, -1, -3},
      {3,  0,  0, -4, -1}
    };
    int result = minimumPasses(matrix);
    System.out.println("Minimum passes required: " + result); // Should print 3
  }
}
```

* When you run the above with the sample matrix, you’ll see `3`, matching the example.
* If there is at least one negative that can never touch a positive (e.g. it’s completely surrounded by zeroes), you’ll get `-1`.

This completes the BFS‐based solution to find the minimum number of passes to convert all negatives into positives (or conclude it’s impossible).
