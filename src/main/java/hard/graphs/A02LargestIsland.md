Below is a step‐by‐step explanation of how the provided `LargestIsland` code works, why it correctly computes the size of the largest island after flipping one 0→1, and its time/space complexity.

---

## Problem Restatement

You have an $n \times n$ grid of 0s and 1s:

* **1** represents land.
* **0** represents water.

An **island** is a 4‐directionally connected group of 1s (i.e.\ connections only up/down/left/right). You are allowed to flip **at most one** water cell (a single 0) into land (1). After performing that single flip (or choosing not to flip at all), what is the size of the largest possible island in the resulting grid?

If the grid is all 1s already, then flipping any 0 doesn’t apply; the answer is simply $n \times n$. If the grid is all 0s, flipping exactly one cell makes an island of size 1.

---

## High‐Level Strategy

1. **First pass (label & measure):**

   * Traverse the grid and run a DFS whenever you encounter a “1” that has not yet been labeled.
   * On that DFS, label every visited land‐cell with a unique “island index” (an integer ≥ 2) and count how many cells that island contains.
   * Store that count in a map from `islandIndex → islandSize`.
   * Meanwhile, track the largest island size found so far (in case we never flip any 0, that is already a candidate).

2. **Second pass (check each 0):**

   * For each water cell (grid cell == 0), look at its four neighbors. Collect the distinct island‐indices of those neighbors.
   * Sum “1 (for the flipped cell itself) + sizes of each distinct adjacent island.” That tells you the resulting island size if you flip this 0 to 1.
   * Keep track of the maximum over all such flips.

3. **Edge cases:**

   * If the grid had no 1s at all, then the first pass finds no islands and `maxIslandSize` stays 0. But in the second pass, each 0 can only produce an island of size 1 by itself—so the answer becomes 1.
   * If the grid was already all 1s, then in the first pass you label the entire grid as one island of size $n \times n$. In the second pass you never see any 0 to flip (or flipping doesn’t make a bigger island), so you return $n \times n$.

---

## Detailed Walk‐Through of the Code

```java
public class LargestIsland {

  // Perform a DFS from (i,j), marking every connected 1 by islandIndex,
  // and return the total size of that island.
  public static int dfs(int[][] grid, int i, int j, int islandIndex) {
    // 1) If out of bounds or not land (grid[i][j] != 1), stop.
    if (i < 0 || i >= grid.length ||
        j < 0 || j >= grid[0].length ||
        grid[i][j] != 1) {
      return 0;
    }

    // 2) Mark this land‐cell with the chosen islandIndex
    grid[i][j] = islandIndex;

    // 3) Recurse in 4 directions, counting every connected cell:
    int size = 1; // this cell
    size += dfs(grid, i + 1, j,     islandIndex); // down
    size += dfs(grid, i - 1, j,     islandIndex); // up
    size += dfs(grid, i,     j + 1, islandIndex); // right
    size += dfs(grid, i,     j - 1, islandIndex); // left

    return size;
  }

  public static int largestIsland(int[][] grid) {
    int n = grid.length;
    // Map from island index → size of that island
    Map<Integer, Integer> islandSizeMap = new HashMap<>();

    // We’ll label islands starting from index = 2 upward. (Why start at 2? Because 0 and 1 are already in use.)
    int islandIndex = 2;
    int maxIslandSize = 0;

    // ---------- STEP 1: Find and label every existing island ----------
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          // Perform DFS, recoloring all connected 1s to “islandIndex” and returning its size:
          int size = dfs(grid, i, j, islandIndex);
          islandSizeMap.put(islandIndex, size);

          // Keep track of the largest island found so far (in case we never flip a 0):
          maxIslandSize = Math.max(maxIslandSize, size);

          // Move on to the next unique index:
          islandIndex++;
        }
      }
    }

    // If we never found any land at all (all cells were 0),
    // maxIslandSize is still 0. But flipping a single 0 would create an island of size 1.
    // We’ll handle that below in the second pass; for now, keep maxIslandSize = 0.

    // ---------- STEP 2: For every 0, compute “if I flip this 0→1, how big does my island become?”
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 0) {
          // Look at up to 4 neighbors—collect distinct island indices:
          Set<Integer> adjacentIndices = new HashSet<>();
          if (i > 0 && grid[i - 1][j] > 1) adjacentIndices.add(grid[i - 1][j]);
          if (i < n - 1 && grid[i + 1][j] > 1) adjacentIndices.add(grid[i + 1][j]);
          if (j > 0 && grid[i][j - 1] > 1) adjacentIndices.add(grid[i][j - 1]);
          if (j < n - 1 && grid[i][j + 1] > 1) adjacentIndices.add(grid[i][j + 1]);

          // If we flip this 0 to 1, it itself contributes +1, plus the sizes of all distinct adjacent islands:
          int potentialSize = 1;
          for (int idx : adjacentIndices) {
            potentialSize += islandSizeMap.get(idx);
          }

          // Update our global maximum:
          maxIslandSize = Math.max(maxIslandSize, potentialSize);
        }
      }
    }

    return maxIslandSize;
  }

  public static void main(String[] args) {
    int[][] grid = {
      {0, 1, 1},
      {0, 0, 1},
      {1, 1, 0}
    };
    // Expected output: 3
    System.out.println("Largest Island Size: " + largestIsland(grid));
  }
}
```

### Step‐by‐Step Explanation

1. **Labeling All Existing Islands (DFS Pass)**

   * We scan each cell.

   * Whenever we see a `1`, we call `dfs(...)` with a brand‐new `islandIndex` (starting from 2).

   * Inside `dfs`:

     * As soon as we visit `grid[i][j] == 1`, we overwrite that cell’s value to `islandIndex` (e.g.\ 2, 3, 4, …) so that we won’t revisit it.
     * We recursively visit all four neighbors (up/down/left/right) that are still `== 1`. Each neighbor becomes part of the same island and is also marked with the same `islandIndex`.
     * We accumulate a `size` count for that island.
     * When the DFS returns, we know exactly how many cells had value 1 in that connected component. We store `islandSizeMap.put(islandIndex, size)`.

     Example: Suppose our small example was

     ```
       [1, 0, 1]
       [0, 1, 0]
       [1, 0, 0]
     ```

     * We might label the first “1” (at (0,0)) with index 2, but it’s all alone → size 1.
     * We label (0,2) with index 3 → size 1.
     * We label (1,1) with index 4 → size 1.
     * We label (2,0) with index 5 → size 1.
       − In that contrived case, every land cell was its own island of size 1.

   * While labeling, keep track of `maxIslandSize` among these original islands (in case flipping no 0 gives something larger).

2. **Flipping Each 0 → 1 (and Re‐Connecting)**

   * After the first pass, `grid[i][j] > 1` means “this cell is already part of the island whose index is `grid[i][j]`.”

     * We left every 0 unchanged.
     * We left every 1 turned into some index ≥ 2.

   * Now we scan again. Whenever we see a 0, we ask:

     * “If I flip this 0 to 1, which islands would it connect?”
     * That is simply “look at the four neighbors’ values.”

       * If the neighbor’s value is 0 or 1, ignore.
       * If the neighbor’s value is ≥ 2, that is the unique island index.
     * We gather all **distinct** island indices into a small set (e.g.\ `{3, 7, 10}`).
     * By flipping the current 0 to 1, that cell itself contributes +1, and it merges all of those distinct adjacent islands into a single island.
     * So the size of that merged island becomes

       $$
         1 + \sum_{\,\text{each }idx\in \text{adjacentIndices}\,}  \text{islandSizeMap.get}(idx).
       $$
     * We compare that “potential size” with our global `maxIslandSize` and keep whichever is larger.

3. **Return Result**

   * After checking every 0, the largest recorded island size is the answer.
   * Note: If the grid had no 1s to begin with, then after labeling pass, `islandSizeMap` is empty and `maxIslandSize` stayed 0. But in the second pass, each water cell has no adjacent islands—so `adjacentIndices` is empty, and “flipping that 0” yields a “potentialSize = 1.” Thus we end up returning 1.
   * If the grid was already all 1s, the first pass labeled everything as a single island of size $n \times n$. In the second pass, there are no 0s left to flip (or flipping a hypothetical 0 is moot), so `maxIslandSize` remains $n \times n$.

---

## Complexity Analysis

Let $n$ = number of rows = number of columns (since it’s an $n \times n$ grid). Denote

* $N = n^2$ = total number of cells.

### Time Complexity

1. **First Pass (Label + Size Calculation)**

   * We iterate every cell once. Each time we see an unlabeled 1, we do a DFS that visits each cell of that island exactly once. Because every land cell is labeled in that DFS, no cell is visited more than once overall.
   * Therefore, the total work in all DFS calls is $O(N)$.

2. **Second Pass (Checking Each 0)**

   * We iterate over all $N$ cells. Each time we see grid\[i]\[j]==0, we look at up to 4 neighbors (constant work), gather their distinct indices into a small set, and sum their sizes. Accessing `islandSizeMap.get(...)` is $O(1)$.
   * Hence the second pass is also $O(N)$.

Overall, the total time is

$$
  O(N) + O(N) \;=\; O(N)\;\;(\text{which is }O(n^2)).
$$

### Space Complexity

1. **Modifying `grid` In‐Place**

   * We reuse the original `grid` array to overwrite 1s → islandIndex. No extra 2D array is needed.

2. **`islandSizeMap`**

   * We store one entry per island. In the worst case, every land cell is isolated (no two 1s share an edge). Then you could have up to $O(N)$ distinct islands of size 1 each. So `islandSizeMap` can hold $O(N)$ entries.
   * That cost is $O(N)$.

3. **Recursion Stack**

   * In the worst case, one giant island covers all $N$ cells, and the DFS might go as deep as $O(N)$ in a skinny shape. But because each DFS step marks a cell with `islandIndex`, it actually can’t revisit; the maximum recursion depth is bounded by the maximum size of an island. In the absolute worst scenario, a single island might snake through all $N$ cells, so the recursion depth is $O(N)$.
   * So recursion stack space is $O(N)$ in the pathological worst case. (In practice, grids are usually “fatter” so it’s $O(\text{island size})$.)

Putting these together, the extra space beyond the input grid is $O(N)$.

---

## Example Execution

Consider the sample grid (same size as the test in `main`):

```
grid = [
  [0, 1, 1],
  [0, 0, 1],
  [1, 1, 0]
]
```

### First Pass

* We start scanning at (0,0): it’s 0, skip.

* At (0,1): we see a 1. Call `dfs(0,1,islandIndex=2)`:

  * Mark grid\[0]\[1] = 2; size = 1.
  * Recurse down (1,1)? That is 0 → returns 0.
  * Recurse up (–1,1)? Out of range → 0.
  * Recurse right (0,2)? That is 1 → mark it as 2; size=1 (from that cell).

    * From (0,2), its neighbors are: (1,2) is 1 → mark (1,2)=2, size=1. Its neighbors do not include any other new 1 → stop.
    * (0,2) also checks up/down/left, but all are either out of bounds or already marked.
  * Recurse left (0,1)? Already marked=2 → 0.

  In the end, all three cells (0,1),(0,2),(1,2) become “2.” That island’s size = 3. So `islandSizeMap.put(2,3)`. `maxIslandSize=3`.

* Continue scanning row 0:

  * (0,2) is now 2 → skip (already labeled).

* Next row (1,0) is 0 → skip; (1,1) is 0 → skip; (1,2) is 2 → skip.

* Next row (2,0) is 1 → call DFS with index=3:

  * Mark (2,0)=3, size=1.
  * Its neighbors: (2,1) is 1 → mark (2,1)=3, size=1. Neither of those neighbors has more unvisited 1s.
  * That island’s total size = 2. So `islandSizeMap.put(3,2)`, but `maxIslandSize` remains 3 (since 3>2).

At the end of pass 1, the grid looks like:

```
  [0, 2, 2]
  [0, 0, 2]
  [3, 3, 0]
```

with

```
  islandSizeMap = { 2→3,   3→2 }
  maxIslandSize = 3
```

### Second Pass

We look at every cell that is 0 and ask, “If I flip this to 1, which distinct islands neighbor me?”

* Cell (0,0) = 0:

  * Neighbors are (–1,0 out-of-bounds), (1,0=0), (0,–1 oob), (0,1=2).
  * Distinct adjacentIndices = {2}.
  * potentialSize = 1 + islandSizeMap.get(2) = 1 + 3 = 4.
  * Update `maxIslandSize = max(3, 4) = 4`.

* Cell (1,0) = 0:

  * Neighbors: (0,0=0), (2,0=3), (1,–1 oob), (1,1=0). Distinct = {3}.
  * potentialSize = 1 + 2 = 3. (smaller than current 4)

* Cell (1,1) = 0:

  * Neighbors: (0,1=2), (2,1=3), (1,0=0), (1,2=2). Distinct = {2, 3}.
  * potentialSize = 1 + 3 + 2 = 6.
  * Update `maxIslandSize = max(4, 6) = 6`.

* Cell (2,2) = 0:

  * Neighbors: (1,2=2), (3,2 oob), (2,1=3), (2,3 oob). Distinct = {2, 3}.
  * potentialSize = 1 + 3 + 2 = 6. (same as before)

No other zeros remain. The largest we saw is 6, so the answer is 6.

---

## Final Remarks

1. **Why label islands starting at 2?**

   * We need a way to distinguish “land that’s already been visited and assigned to island #X” from “unvisited land (1)” and “water (0).”
   * By overwriting all connected 1s with index 2, 3, 4, … we never confuse them with unvisited land, because 1 means “still to be processed” and any value ≥ 2 means “already part of an island.”

2. **Why use a `HashSet<Integer>` when merging neighbors?**

   * If a water cell has multiple adjacent cells that belong to the **same** island index (e.g.\ both “up” and “right” neighbors are labeled “2”), we should count that island’s size only once. Hence we collect adjacent island‐indices in a `Set` before summing.

3. **Edge Cases**

   * If the grid is entirely 1s from the very beginning, the first DFS pass groups them into a single island of size $n^2$. In the second pass, there are no 0s to consider, so the function returns $n^2$.
   * If the grid is entirely 0s, the first pass finds no islands and `maxIslandSize` stays 0. In the second pass, each 0 flips to a size‐1 island, so the maximum becomes 1.

4. **Complexities (Summary)**

   * **Time:** $O(n^2)$

     * One full DFS labeling (touches each cell exactly once).
     * One pass checking every 0’s four neighbors (constant work per cell).
   * **Space:** $O(n^2)$ (worst‐case recursion depth if one giant snake‐shaped island, plus the `islandSizeMap` storing up to $O(n^2)$ entries if every land cell were its own island).

This completes the explanation of how the “flip one 0→1 to maximize island” solution works.
