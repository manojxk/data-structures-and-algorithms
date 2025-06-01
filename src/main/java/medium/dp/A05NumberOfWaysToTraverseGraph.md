**Problem Explanation**

You have a grid (a rectangular graph) of size **width × height**. You start at the **top-left** corner (call it `(0,0)`) and want to reach the **bottom-right** corner `(width−1, height−1)`. At each step, you may move only **right** or **down**, never up or left. You need to count **how many distinct paths** satisfy these rules.

For example, if the grid is 2 × 3 (width = 2, height = 3), it looks like this (each cell labeled with coordinates `(x,y)`):

```
(0,0) — (1,0)
  |      |
(0,1) — (1,1)
  |      |
(0,2) — (1,2)
```

To go from `(0, 0)` to `(1, 2)`, you must make exactly 1 “right” move (to increase your x-coordinate by 1) and 2 “down” moves (to increase your y-coordinate by 2), in some order. Concretely, there are three valid sequences of moves:

1. Down → Down → Right
2. Down → Right → Down
3. Right → Down → Down

Hence there are **3** ways.

---

## 1. Brute-Force (Recursive) Approach

### Idea

* At position `(x, y)`, you can move either to `(x+1, y)` (go right) **if** `x+1 < width`, or to `(x, y+1)` (go down) **if** `y+1 < height`.
* Recursively count how many ways lead to the end from each possibility.

This yields a simple recurrence:

Let `W(w, h)` = number of ways to traverse an `w × h` grid (where we interpret “grid of width w” as needing w−1 right-moves and h−1 down-moves total). Equivalently, start at `(0,0)` and want to end at `(w−1, h−1)`.

* If `w = 1` or `h = 1`, there is exactly **1** way: you can only go straight down (if height > 1) or straight right (if width > 1).
* Otherwise, you either take your first step **right** (reducing width by 1) or **down** (reducing height by 1). So

  ```
  W(w, h)
    = W(w−1, h)    // first move is “right”
    + W(w, h−1)    // first move is “down”
  ```

### Pseudocode

```java
public int numberOfWaysToTraverseGraph(int width, int height) {
  // Base case: if either dimension is 1, there's only one straight path
  if (width == 1 || height == 1) {
    return 1;
  }
  // Recurrence: go right (width−1, height) plus go down (width, height−1)
  return numberOfWaysToTraverseGraph(width - 1, height)
       + numberOfWaysToTraverseGraph(width, height - 1);
}
```

### Time & Space Complexity

* **Time Complexity:**
  Each call spawns two recursive calls, leading to an exponential tree of calls. In roughly `O(2^(w+h))` time in the worst case (since most subproblems repeat).
* **Space Complexity:**
  The recursion depth can go as deep as `w + h` (if you keep moving down or right one at a time), so `O(w + h)` stack space.

Because of this exponential blow-up, the brute-force recursion is practical only for very small grids.

---

## 2. Dynamic-Programming (DP) Approach

### Idea

Observe that the same subproblem `W(a, b)` will keep getting recomputed in the naive recursion. We can store those counts in a 2D array and fill it systematically.

Define a 2D array `dp` of size `(height + 1) × (width + 1)`, but we’ll treat indices starting from 1 for convenience:

* `dp[r][c]` = number of ways to reach cell `(c−1, r−1)` in a `width × height` scenario. (Here row = r, column = c.)

Instead, it’s simpler to say: We want to fill up a table `dp[1..height][1..width]` such that

1. `dp[1][c] = 1` for all `c` (if there is only one row, you can only move right across that row).
2. `dp[r][1] = 1` for all `r` (if there is only one column, you can only move down along that column).
3. For all `r > 1` and `c > 1`,

   ```
   dp[r][c] = dp[r][c - 1] + dp[r - 1][c]
   ```

   because to reach `(c−1, r−1)`, you either came from `(c−2, r−1)` (one step left) or from `(c−1, r−2)` (one step up).

At the end, `dp[height][width]` holds the number of ways to traverse a `width × height` grid.

### Java Implementation

```java
public int numberOfWaysToTraverseGraph(int width, int height) {
  // We build a (height+1) x (width+1) array to avoid index-out-of-bounds
  int[][] dp = new int[height + 1][width + 1];

  // Fill the base cases: any cell in row 1 or column 1 has exactly one way
  for (int r = 1; r <= height; r++) {
    for (int c = 1; c <= width; c++) {
      if (r == 1 || c == 1) {
        dp[r][c] = 1;
      } else {
        dp[r][c] = dp[r][c - 1] + dp[r - 1][c];
      }
    }
  }

  return dp[height][width];
}
```

### Time & Space Complexity

* **Time Complexity:**
  We fill a table of size `height × width`, performing a constant-time addition at each cell → **O(width × height)**.
* **Space Complexity:**
  We use an extra 2D array of size `(height + 1) × (width + 1)`, so **O(width × height)** space.

---

## 3. Combinatorial (O(w + h) Time • O(1) Space) Approach



### Java Implementation

```java
// Copyright © 2023 AlgoExpert LLC. All rights reserved.

import java.util.*;

class Program {
  // O(n + m) time | O(1) space - where n is
  // the width of the graph and m is the height
  public int numberOfWaysToTraverseGraph(int width, int height) {
    int xDistanceToCorner = width - 1;
    int yDistanceToCorner = height - 1;

    // The number of permutations of right and down movements
    // is the number of ways to reach the bottom right corner.
    int numerator = factorial(xDistanceToCorner + yDistanceToCorner);
    int denominator =
      factorial(xDistanceToCorner) * factorial(yDistanceToCorner);
    return numerator / denominator;
  }

  public int factorial(int num) {
    int result = 1;

    for (int n = 2; n < num + 1; n++) {
      result *= n;
    }

    return result;
  }
}

```

### Time & Space Complexity

* **Time Complexity:**
  Computing factorial of `N = width + height − 2` is O(N) in a simple loop. So overall **O(width + height)**.
* **Space Complexity:**
  We only use a few integer/long variables → **O(1)** extra space.

> **Note on Overflow**:
>
> * If `width + height − 2` gets large (say > 20), `factorial(n)` can exceed the range of a 64-bit `long`. In those cases, you can compute the binomial coefficient with an iterative product that divides early, or use a library for big integers. But for many interview‐style or moderate‐sized grids, the simple factorial approach suffices.

---

## Summary of the Three Approaches

| Approach                                | Time Complexity | Space Complexity | When to Use                                                                       |
| --------------------------------------- | --------------- | ---------------- | --------------------------------------------------------------------------------- |
| 1. Brute‐Force Recursive                | O(2^(w + h))    | O(w + h)         | Very small widths/heights only (exponential).                                     |
| 2. Dynamic Programming (2D table)       | O(w × h)        | O(w × h)         | Reasonably sized grids when you can afford O(wh) space.                           |
| 3. Combinatorial (binomial coefficient) | O(w + h)        | O(1)             | Large grids where you need an O(1)-space formula; watch out for integer overflow. |

For most practical purposes, **Approach 3** (using combinations) is the fastest and most space-efficient. If you need clarity or you’re in an environment where factorials overflow too quickly, use **Approach 2** (DP), which is straightforward and runs in polynomial time with polynomial space. Approach 1 (the naive recursion) is typically only used as a teaching example, since it blows up exponentially for modest grid sizes.
