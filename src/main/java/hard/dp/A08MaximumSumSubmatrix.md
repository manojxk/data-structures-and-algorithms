**Problem Restatement**
You are given:

* A 2D integer matrix `matrix` of size `n × m` (with `n` rows and `m` columns), and
* A positive integer `size`, where `1 ≤ size < min(n, m)`.

You must find the **maximum possible sum** of any contiguous submatrix of dimensions `size × size`. In other words, among all square blocks of side‐length `size` that you can “slide” over the given matrix, pick the one whose entries add up to the largest total.

If `matrix` is:

```
  [ [ 5,  3,  −1,  5 ],
    [−7,  3,   7,  8 ],
    [12,  8,   0, 10 ],
    [ 1, −8,  −8,  2 ] ]
```

and `size = 2`, we look at all 2×2 submatrices. For example:

* The submatrix whose top‐left corner is at `(0,0)` is

  ```
    [ 5,  3 ]
    [−7,  3 ]
  ```

  which sums to `5 + 3 + (−7) + 3 = 4`.
* The submatrix whose top‐left corner is at `(1,1)` is

  ```
     [ 3,   7 ]
     [ 8,   0 ]
  ```

  which sums to `3 + 7 + 8 + 0 = 18`.
* … etc for every valid top‐left corner `(i,j)` with `i ≤ n−size` and `j ≤ m−size`.

Among all of those 2×2 squares, the one above (at `(1,1)`) has the highest sum of `18`. Hence the answer is **18**.

---

## 1) Brute‐Force Solution

### Approach

1. **Loop over every possible top‐left corner** of a `size×size` block. If the matrix has `n` rows and `m` columns, then `i` can run from `0` to `n−size` (inclusive) and `j` can run from `0` to `m−size` (inclusive). Each `(i,j)` represents a candidate 2D block whose top‐left is `(i,j)` and whose bottom‐right is `(i+size−1, j+size−1)`.
2. **Compute the sum of that block** by doing two nested loops of length `size`:

   ```java
   int currentSum = 0;
   for (int x = i; x < i + size; x++) {
     for (int y = j; y < j + size; y++) {
       currentSum += matrix[x][y];
     }
   }
   ```
3. **Keep track of the maximum sum** seen so far. After computing `currentSum` for each `(i,j)`, update

   ```java
   maxSum = Math.max(maxSum, currentSum);
   ```

### Code

```java
public static int maxSumSubmatrixBruteForce(int[][] matrix, int size) {
  int rows = matrix.length;
  int cols = matrix[0].length;
  int maxSum = Integer.MIN_VALUE;

  // 1) Slide the top-left corner i from 0..(rows - size), j from 0..(cols - size)
  for (int i = 0; i <= rows - size; i++) {
    for (int j = 0; j <= cols - size; j++) {
      int currentSum = 0;

      // 2) Sum up all elements in the size×size block starting at (i,j)
      for (int x = i; x < i + size; x++) {
        for (int y = j; y < j + size; y++) {
          currentSum += matrix[x][y];
        }
      }

      // 3) Update the maximum
      maxSum = Math.max(maxSum, currentSum);
    }
  }

  return maxSum;
}
```

### Time & Space Complexity

* **Time Complexity:**

  * We have `(rows – size + 1)` choices for `i` and `(cols – size + 1)` choices for `j`. In the worst case, that’s on the order of $O(n \cdot m)$ candidate top‐left corners.
  * For each corner, we do a double loop of size `size × size`, which costs $O(\text{size}^2)$.
  * Overall:

    $$
      O\bigl((n - \text{size} + 1)\,(m - \text{size} + 1)\times \text{size}^2 \bigr)
      \;=\; O(n \, m \, \text{size}^2) \quad\text{(if }n\gg\text{size},\, m\gg\text{size}\text{).}
    $$

* **Space Complexity:**

  * We only used a few integer variables (`maxSum`, `currentSum`, loop indices).
  * Hence **O(1)** extra space.

---

## 2) Optimized Solution Using 2D Prefix Sums

Instead of recomputing each `size×size` block’s sum from scratch, we can preprocess a 2D **prefix‐sum** array in $O(n \times m)$ time and then query any submatrix sum in $O(1)$ time.

### Prefix‐Sum Idea

Define a new array `prefixSums` of dimension `(rows + 1) × (cols + 1)` so that:

```java
prefixSums[i][j] = sum of all matrix[r][c] with 0 ≤ r < i, 0 ≤ c < j.
```

(In other words, `prefixSums` is one row and one column larger than `matrix`, and we shift indices by +1 for convenience.)

We can fill `prefixSums` row by row:

```java
for (int i = 1; i <= rows; i++) {
  for (int j = 1; j <= cols; j++) {
    prefixSums[i][j] =
        matrix[i - 1][j - 1]
      + prefixSums[i - 1][j]
      + prefixSums[i][j - 1]
      - prefixSums[i - 1][j - 1];
  }
}
```

* Explanation:

  * `matrix[i-1][j-1]` is the new cell we’re adding.
  * `prefixSums[i-1][j]` is the sum of the entire rectangle above this cell.
  * `prefixSums[i][j-1]` is the sum of the entire rectangle to the left of this cell.
  * But we added the rectangle above‐left twice, so we subtract `prefixSums[i-1][j-1]` once.

Once we have that 2D prefix table, the sum of ANY submatrix whose top‐left corner is `(r1,c1)` and bottom‐right corner is `(r2,c2)` (0‐based in `matrix`) can be computed in O(1) as:

```
sum = prefixSums[r2+1][c2+1]
    - prefixSums[r1][c2+1]
    - prefixSums[r2+1][c1]
    + prefixSums[r1][c1]
```

(Those `+1` shifts come from how we defined `prefixSums` to be one larger in each dimension.)

### Applying to size×size Submatrices

We want all `size×size` blocks. A block with top‐left `(i,j)` in 0‐based `matrix` ends at `(i + size − 1, j + size − 1)`. In prefix‐sum coordinates that is:

* Bottom‐right in prefixSums: `(i + size, j + size)`
* Top‐left in prefixSums: `(i, j)`

Hence the block‐sum is:

```java
int currentSum =
    prefixSums[i + size][j + size]
  - prefixSums[i][j + size]
  - prefixSums[i + size][j]
  + prefixSums[i][j];
```

We iterate `i` from `0` to `rows – size`, and `j` from `0` to `cols – size`, take the above formula, and track the maximum.

### Code

```java
public static int maxSumSubmatrixOptimized(int[][] matrix, int size) {
  int rows = matrix.length;
  int cols = matrix[0].length;

  // 1) Build a (rows+1)×(cols+1) prefix‐sum array:
  int[][] prefixSums = new int[rows + 1][cols + 1];

  for (int i = 1; i <= rows; i++) {
    for (int j = 1; j <= cols; j++) {
      prefixSums[i][j] =
          matrix[i - 1][j - 1]
        + prefixSums[i - 1][j]
        + prefixSums[i][j - 1]
        - prefixSums[i - 1][j - 1];
    }
  }

  int maxSum = Integer.MIN_VALUE;

  // 2) Now slide over all top‐left corners (i,j) for size×size blocks:
  //    i runs 0 … rows-size, j runs 0 … cols-size.
  for (int i = 0; i <= rows - size; i++) {
    for (int j = 0; j <= cols - size; j++) {
      // Bottom‐right corner in the prefix table is (i+size, j+size)
      int bottomR = i + size;
      int bottomC = j + size;

      // Compute submatrix sum in O(1):
      int currentSum =
          prefixSums[bottomR][bottomC]
        - prefixSums[i][bottomC]
        - prefixSums[bottomR][j]
        + prefixSums[i][j];

      maxSum = Math.max(maxSum, currentSum);
    }
  }

  return maxSum;
}
```

### Time & Space Complexity

* **Time Complexity:**

  1. Building `prefixSums` takes $O(\,rows × cols\,)$.
  2. Iterating over all `(rows−size+1) × (cols−size+1)` positions and computing each block’s sum takes $O(\,rows × cols\,)$ as well (because each position requires only O(1) to get the sum).

  * Overall: **O(rows × cols)**.

* **Space Complexity:**

  * We allocate a 2D array `prefixSums` of size `(rows+1) × (cols+1)`, so that is **O(rows × cols)** extra space.
  * Aside from that, we only used a constant number of integer variables.

---

## 3) Putting It All Together

Below is a complete Java class that contains both the brute‐force and the prefix‐sum–based optimized solutions, plus a simple test in `main`.

```java
import java.util.Arrays;

public class A07MaxSumSubmatrix {

  // ---------------------------------------------------------
  // 1) Brute‐Force O(n*m*size^2)
  // ---------------------------------------------------------
  public static int maxSumSubmatrixBruteForce(int[][] matrix, int size) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int maxSum = Integer.MIN_VALUE;

    // Slide top‐left corner i=0..rows-size, j=0..cols-size
    for (int i = 0; i <= rows - size; i++) {
      for (int j = 0; j <= cols - size; j++) {
        int currentSum = 0;
        // Sum all entries in the size×size block
        for (int x = i; x < i + size; x++) {
          for (int y = j; y < j + size; y++) {
            currentSum += matrix[x][y];
          }
        }
        maxSum = Math.max(maxSum, currentSum);
      }
    }

    return maxSum;
  }

  // ---------------------------------------------------------
  // 2) Optimized Using 2D Prefix Sums: O(n*m)
  // ---------------------------------------------------------
  public static int maxSumSubmatrixOptimized(int[][] matrix, int size) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Build (rows+1)×(cols+1) prefix sums array
    int[][] prefixSums = new int[rows + 1][cols + 1];
    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        prefixSums[i][j] =
            matrix[i - 1][j - 1]
          + prefixSums[i - 1][j]
          + prefixSums[i][j - 1]
          - prefixSums[i - 1][j - 1];
      }
    }

    int maxSum = Integer.MIN_VALUE;

    // Slide over all valid top‐left corners (i,j)
    for (int i = 0; i <= rows - size; i++) {
      for (int j = 0; j <= cols - size; j++) {
        int bottomR = i + size; // prefix‐sum row index
        int bottomC = j + size; // prefix‐sum col index

        // submatrix (i..i+size−1, j..j+size−1) sum:
        int currentSum =
            prefixSums[bottomR][bottomC]
          - prefixSums[i][bottomC]
          - prefixSums[bottomR][j]
          + prefixSums[i][j];

        maxSum = Math.max(maxSum, currentSum);
      }
    }

    return maxSum;
  }

  // ---------------------------------------------------------
  // Main to test both methods
  // ---------------------------------------------------------
  public static void main(String[] args) {
    int[][] matrix = {
      { 5,   3,  -1,   5 },
      { -7,  3,   7,   8 },
      { 12,  8,   0,  10 },
      { 1,  -8,  -8,   2 }
    };
    int size = 2;

    System.out.println("Brute‐Force Result:   " 
        + maxSumSubmatrixBruteForce(matrix, size));     // Expected: 18
    System.out.println("Optimized Result:     " 
        + maxSumSubmatrixOptimized(matrix, size));      // Expected: 18
  }
}
```

---

### Summary

* **Brute‐Force**

  * Check every possible top‐left corner `(i,j)` of a `size×size` block.
  * Compute its sum by iterating over `size²` entries.
  * Update `maxSum`—overall time is $O(n \, m \, \text{size}^2)$.

* **Optimized with 2D Prefix Sums**

  * Precompute `prefixSums[i][j] =` sum of the rectangle from `(0,0)` to `(i−1, j−1)` in $O(n \, m)$.
  * Then each `size×size` block sum is retrieved in constant time via the inclusion/exclusion formula.
  * Final time is **O(n × m)**; space is also **O(n × m)** for the prefix table.

Either approach is correct; the prefix‐sum version is vastly faster when `size` is large or when `n, m` are large.
