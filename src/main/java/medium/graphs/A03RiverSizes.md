**Problem Explanation**

You are given a 2D matrix (an array of arrays) of 0s and 1s. In this matrix:

* A value of **1** represents part of a “river.”
* A value of **0** represents land.

A **river** is defined as a group of horizontally or vertically adjacent 1s (diagonal adjacency does **not** count). Your task is to find **all** the distinct rivers in the matrix and return a list of their **sizes** (i.e., the number of 1s in each connected group). The order in which you return those sizes does not matter.

> **Example**
>
> ```
> Input matrix:
> [
>   [1, 0, 0, 1, 0],
>   [1, 0, 1, 0, 0],
>   [0, 0, 1, 0, 1],
>   [1, 0, 1, 1, 1]
> ]
>
> There are three rivers:
>  - A river of size 2 at the top-left (positions (0,0) and (1,0)).
>  - A river of size 1 at (0,3).
>  - A larger river of size 5 connecting (1,2), (2,2), (3,2), (3,3), (3,4).
>
> Output: [2, 1, 5]
> ```

---

## High-Level Approach

1. **Visited Tracking**
   We will maintain a 2D boolean array `visited[][]` of the same dimensions as the input `matrix`. Initially, all entries are `false`. When we “explore” a cell (either it’s land or part of a river), we mark it `visited = true` so we don’t count it again.

2. **Iterate Over Every Cell**
   We will loop through each cell `(i, j)` in the matrix:

   * If it is **0** (land) or we have already marked it visited, we **skip** it.
   * If it is **1** and **not visited**, this is the start (or part) of a new river. We now need to measure **how large** that river is. We call a helper function to explore all connected 1s (via DFS or BFS), count them, and mark them visited.

3. **Depth‐First Search (DFS) Exploration**
   From a starting cell `(i, j)` that contains a 1, we will recursively (or with an explicit stack) explore its four neighbors—up, down, left, and right—whenever they:

   * Stay inside the matrix bounds.
   * Are not already visited.
   * Contain a 1.
     Each time we step into such a neighbor, we mark it visited and continue exploring its neighbors in turn. We maintain a running `size` counter. Once the DFS finishes, `size` holds the total number of connected 1s in that river. We add `size` to our result list.

4. **Collect All River Sizes**
   Each time the main loop finds an unvisited 1, it triggers a DFS to measure that entire river and returns its size. We add that size to an `ArrayList<Integer> sizes`. After scanning every cell, `sizes` holds the sizes of all rivers.

5. **Return the List**
   Finally, we simply return `sizes`.

Because each cell in the matrix is visited **exactly once**—either marked visited immediately (if it is 0) or marked as part of a DFS for a river (if it is 1)—the time complexity is **O(n·m)**, where `n` and `m` are the number of rows and columns. The worst‐case recursion depth (when all 1s connect into a single river) could be **n·m**, so the extra space used by the recursion stack is also **O(n·m)**. The `visited[][]` boolean array itself uses **O(n·m)** space as well.

---

## Step-by-Step Solution Outline

1. **Initialize**

   * Let `rows = matrix.length` and `cols = matrix[0].length`.
   * Create a 2D boolean array `visited = new boolean[rows][cols]`.
   * Create an `ArrayList<Integer> sizes = new ArrayList<>()` to hold the river sizes.

2. **Scan Every Cell**

   ```java
   for (int i = 0; i < rows; i++) {
     for (int j = 0; j < cols; j++) {
       // If this cell is unvisited and is a 1, it starts a new river
       if (!visited[i][j] && matrix[i][j] == 1) {
         int size = dfsRiverSize(matrix, visited, i, j);
         sizes.add(size);
       }
     }
   }
   ```

   * If `matrix[i][j] == 0` or `visited[i][j] == true`, skip it.
   * Otherwise, call `dfsRiverSize(...)` to measure that entire connected river.

3. **Depth‐First Search Helper**

   ```java
   private static int dfsRiverSize(int[][] matrix, boolean[][] visited, int i, int j) {
     // Base conditions: check bounds, visited, or if it’s water (0).
     if (i < 0 || i >= matrix.length 
         || j < 0 || j >= matrix[0].length 
         || visited[i][j] 
         || matrix[i][j] == 0) {
       return 0;
     }

     // Mark this cell as visited
     visited[i][j] = true;
     int size = 1;  // Count this 1

     // Explore neighbors (up, down, left, right)
     size += dfsRiverSize(matrix, visited, i - 1, j); // up
     size += dfsRiverSize(matrix, visited, i + 1, j); // down
     size += dfsRiverSize(matrix, visited, i, j - 1); // left
     size += dfsRiverSize(matrix, visited, i, j + 1); // right

     return size;
   }
   ```

   * We return 0 immediately if `(i, j)` is out of bounds, already visited, or has `matrix[i][j] == 0`.
   * Otherwise, mark it visited, start `size = 1`, then recursively add all four directions’ contributions.

4. **Return the Result**
   After the nested loop finishes, `sizes` contains one integer per river, each representing how many 1s were in that connected component. Return `sizes`.

---

## Complete Java Code

```java
package medium.graphs;

import java.util.ArrayList;
import java.util.List;

public class RiverSizes {

  /**
   * Given a 2D matrix of 0s and 1s, where each 1 represents part of a river
   * (connected orthogonally), this method returns a list of the sizes of all rivers.
   *
   * Time Complexity:  O(n * m), where n = number of rows, m = number of columns.
   *                  We visit each cell exactly once.
   * Space Complexity: O(n * m), for the 'visited' array and the recursion stack in the worst case.
   */
  public static List<Integer> riverSizes(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    boolean[][] visited = new boolean[rows][cols];
    List<Integer> sizes = new ArrayList<>();

    // 1) Iterate over every cell
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // If this cell is an unvisited part of a river, explore it
        if (!visited[i][j] && matrix[i][j] == 1) {
          int size = dfsRiverSize(matrix, visited, i, j);
          sizes.add(size);
        }
      }
    }

    return sizes;
  }

  /**
   * Recursively explores a river using DFS, counting how many 1s are connected.
   *
   * @param matrix  the input grid of 0s and 1s
   * @param visited marks which cells have been explored already
   * @param i       current row index
   * @param j       current column index
   * @return the size of the river component rooted at (i, j)
   */
  private static int dfsRiverSize(int[][] matrix, boolean[][] visited, int i, int j) {
    // Base case: out of bounds, already visited, or not part of a river
    if (i < 0
        || i >= matrix.length
        || j < 0
        || j >= matrix[0].length
        || visited[i][j]
        || matrix[i][j] == 0) {
      return 0;
    }

    // Mark as visited and count this cell
    visited[i][j] = true;
    int size = 1;

    // Explore all four orthogonal directions
    size += dfsRiverSize(matrix, visited, i - 1, j); // up
    size += dfsRiverSize(matrix, visited, i + 1, j); // down
    size += dfsRiverSize(matrix, visited, i, j - 1); // left
    size += dfsRiverSize(matrix, visited, i, j + 1); // right

    return size;
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 0, 0, 1, 0},
      {1, 0, 1, 0, 0},
      {0, 0, 1, 0, 1},
      {1, 0, 1, 1, 1}
    };

    List<Integer> sizes = riverSizes(matrix);
    System.out.println(sizes); // Example output: [2, 1, 5]
  }
}
```

---

## Detailed Explanation for Beginners

1. **Why do we need a `visited` array?**

   * Because once you’ve counted a particular “1” as part of a river, you must not count it again.
   * If you didn’t mark it visited, your DFS could loop in circles, or count the same cell multiple times.

2. **How does DFS “grow” a river size?**

   * Whenever you find a starting cell `(i, j)` where `matrix[i][j] == 1` and `visited[i][j] == false`, you know—you have stumbled on a new river (or a new section of a river).
   * You then ask: “Okay, how many 1s are connected to me orthogonally?” That is exactly what `dfsRiverSize(...)` will answer by looking in all four directions.
   * Each time you move to a neighbor that is also 1 and not visited, you mark it visited and add it to the count. That neighbor, in turn, explores its own neighbors recursively.
   * When the recursion finishes for that starting cell, you have counted every 1 in that river.

3. **Why is time complexity O(n·m)?**

   * We have a nested loop over all cells `(i, j)`, so that’s O(n·m).
   * Inside, whenever we call DFS from a particular 1, the DFS visits every connected 1 exactly once and marks it visited. Because every cell is marked visited the first time you land on it—whether it’s 0 or 1—you never process any cell more than once.
   * Summing up all DFS calls plus the outer loops, each of the `n·m` cells is touched at most a constant number of times → O(n·m).

4. **Why is space complexity O(n·m)?**

   * The `visited` array itself is size `n×m`.
   * In the worst case (every cell is a 1 in a single massive river), the recursion stack could go as deep as `n·m` if you have one long snake‐like path of 1s. That also costs O(n·m) stack frames in the call hierarchy. So the total extra space is proportional to `n·m`.

---

With these explanations and the step‐by‐step code above, you now have a complete, beginner‐friendly solution in Java that finds all river sizes in a binary matrix.
