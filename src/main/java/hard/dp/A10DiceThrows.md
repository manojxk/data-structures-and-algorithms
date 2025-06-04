**Problem Restatement**
Given:

* An integer `n` (number of dice),
* An integer `m` (number of faces on each die, labeled 1 through `m`),
* A target sum `x`,

count the **number of distinct ways** to roll those `n` dice so that the sockets’ face‐values add up exactly to `x`. Each die can show any face from 1 up to `m`, and the order in which dice land matters (e.g. rolling (1, 4, 3) is different from (4, 1, 3)). If there is no way to total `x`, the answer is 0.

For example:

1. With `n = 2`, `m = 6`, `x = 7`:
   The six valid ordered outcomes are

   ```
     (1,6), (2,5), (3,4), (4,3), (5,2), (6,1)
   ```

   so the answer is 6.

2. With `n = 3`, `m = 4`, `x = 5`:
   The valid triples (in order) are

   ```
     (1,1,3), (1,3,1), (3,1,1),   // all permutations of {1,1,3} 
     (1,2,2), (2,1,2), (2,2,1)    // all permutations of {1,2,2}
   ```

   for a total of 6 ways.

---

## Approach: Dynamic Programming (DP) in O(n · x · m) time

Brute‐forcing all `m^n` possible rolls becomes impossible when `n` and `m` grow even moderately large (e.g. n=10, m=100 → 100^10 possibilities!). Instead, notice that we can build the count of ways “die by die,” tracking partial sums.

Let us define a 2D DP table:

```
dp[i][s] = “the number of ways to obtain total sum s using exactly i dice.”
```

* Here `i` ranges from 0 to `n`,
* and `s` ranges from 0 to `x`.

At the end, we want `dp[n][x]`.

### Base Case

* With 0 dice, the only “sum” we can get is 0 (by not rolling anything). So:

  ```
  dp[0][0] = 1,
  dp[0][s] = 0    for every s > 0.
  ```

### Recurrence

Once we have filled row `i − 1` (i.e. all ways to achieve various sums using `i − 1` dice), we add one more die (# `i`) which can show any face from 1 to `m`. Suppose we want to know `dp[i][s]` for some `s`. The last die can show any face `f ∈ {1,2,…,m}`, but if it shows `f`, then the previous `i − 1` dice must have summed to `s − f`. Therefore:

```
dp[i][s] = ∑_{f = 1..m, f ≤ s}  dp[i − 1][ s − f ]
```

Every time the new die shows `f`, we count how many ways the first `i−1` dice made `s−f`. Summing over `f = 1..m` (but only those `f` for which `s − f ≥ 0`) gives the total ways to make `s` with `i` dice.

### Algorithm Steps

1. **Initialize a 2D array**
   Create `int[][] dp = new int[n+1][x+1];`.

   * We only need sums up to `x`, so each row has length `x+1`.
   * Each row index `i` goes from `0..n` (0 dice up to n dice).

2. **Set base case**

   ```
   dp[0][0] = 1;           // Exactly one way to have sum 0 with zero dice
   for (int s = 1; s <= x; s++) {
     dp[0][s] = 0;         // No way to get sum >0 with zero dice
   }
   ```

3. **Fill DP row by row**
   For each `i = 1..n`:
     For each `s = 1..x`:
       Initialize `dp[i][s] = 0`.
       Then for every possible face value `f = 1..m`:
       — If `f ≤ s`, add `dp[i−1][s−f]` into `dp[i][s]`.
       — If `f > s`, break or skip because you cannot roll a face larger than your desired sub‐sum.

   Concretely:

   ```java
   for (int i = 1; i <= n; i++) {
     for (int sum = 1; sum <= x; sum++) {
       dp[i][sum] = 0;
       for (int face = 1; face <= m; face++) {
         if (face > sum) break;    // No need to continue if face exceeds current sum
         dp[i][sum] += dp[i - 1][sum - face];
       }
     }
   }
   ```

4. **Answer**
   After filling all rows, the desired count is `dp[n][x]`. If no combination yields `x`, that cell remains 0.

---

## Complete Java Code

```java
public class A09DiceThrows {

  /**
   * Returns the number of ways to roll n dice (each with faces 1..m) so that the total sum is exactly x.
   * Time Complexity:  O(n · x · m)
   *   - We fill an (n+1)×(x+1) table. For each cell dp[i][sum], we loop over up to m faces.
   * Space Complexity: O(n · x)
   *   - We store dp[i][sum] for 0 ≤ i ≤ n and 0 ≤ sum ≤ x.
   */
  public static int diceThrows(int n, int m, int x) {
    // If x is 0 but n > 0, there is no way to roll positive faces to sum 0.
    // If x < n or x > n*m, we could early‐exit, but we’ll fill DP in any case.
    // We just guard the array dimension.
    if (n <= 0 || x < 0) {
      return 0;
    }

    // 1) Create DP array: (n+1) rows and (x+1) columns
    int[][] dp = new int[n + 1][x + 1];

    // 2) Base case: with 0 dice, exactly one way to get sum 0
    dp[0][0] = 1;
    for (int sum = 1; sum <= x; sum++) {
      dp[0][sum] = 0;
    }

    // 3) Fill the table for i = 1..n
    for (int i = 1; i <= n; i++) {
      for (int sum = 1; sum <= x; sum++) {
        int ways = 0;
        // Try rolling a face f = 1..m
        for (int face = 1; face <= m; face++) {
          if (face > sum) {
            // If the face itself exceeds the partial sum,
            // we cannot subtract it; no further f will help.
            break;
          }
          ways += dp[i - 1][sum - face];
        }
        dp[i][sum] = ways;
      }
      // dp[i][0] remains 0 for i≥1 (you cannot get sum=0 with a positive‐faced die)
      dp[i][0] = 0;
    }

    // 4) The answer is dp[n][x]
    return dp[n][x];
  }

  // ------------------------------------------
  // Simple tests
  // ------------------------------------------
  public static void main(String[] args) {
    // Example 1: n=2, m=6, x=7
    // Ways: (1,6),(2,5),(3,4),(4,3),(5,2),(6,1) => 6.
    System.out.println("n=2,m=6,x=7 → " + diceThrows(2, 6, 7));  // expected 6

    // Example 2: n=3, m=4, x=5
    // Ways: permutations of {1,1,3} and {1,2,2} => total 6.
    System.out.println("n=3,m=4,x=5 → " + diceThrows(3, 4, 5));  // expected 6

    // Some additional checks:
    // If x < n (smallest sum is n*1 = n), no solution:
    System.out.println("n=3,m=6,x=2 → " + diceThrows(3, 6, 2));  // expected 0

    // If x > n*m (largest sum is n*m), no solution:
    System.out.println("n=3,m=6,x=19 → " + diceThrows(3, 6, 19)); // expected 0

    // Larger example: n=5, m=6, x=18
    // We won't enumerate here, but code returns something.
    System.out.println("n=5,m=6,x=18 → " + diceThrows(5, 6, 18));
  }
}
```

---

## Explanation of Key Points

1. **`dp[i][sum]` Meaning**

   * Number of ways to roll exactly `i` dice so that their total sum is `sum`.

2. **Base Case**

   * `dp[0][0] = 1` because there is exactly one way to have “zero dice” that total `0`: roll nothing.
   * For any `sum > 0`, `dp[0][sum] = 0` (no way to reach a positive sum with zero dice).

3. **Filling Transition**

   * We fix an `i ≥ 1`. To compute `dp[i][sum]`, consider the value shown on the final (i-th) die. If that last die shows `f` (where `1 ≤ f ≤ m`), then the first `i−1` dice must have totaled `sum − f`. Hence we add up all `dp[i−1][sum − f]` over `f = 1..m` (but only those `f` for which `sum − f ≥ 0`).

   In formula form:

   $$
     dp[i][sum] \;=\; \sum_{f=1}^{m} \bigl[\, sum − f ≥ 0 \;\bigl\rfloor\, dp[i-1][\,sum − f\,].
   $$

   If `f > sum`, we break early because no further `f` (which only grows larger) can satisfy `sum − f ≥ 0`.

4. **Final Answer**

   * After filling the 2D table up to row `i = n`, the cell `dp[n][x]` holds exactly “the number of ways to roll `n` dice so that their sum is `x`.”

---

## Complexity Summary

* **Time Complexity:**
  We have two nested loops:

  1. Outer loop over `i = 1..n` → `n` iterations.
  2. Inner loop over `sum = 1..x` → `x` iterations.
  3. Innermost summation over `face = 1..m` (worst‐case `m` visits unless `face > sum` causes an early break).
     Altogether: **O(n · x · m)**.

* **Space Complexity:**
  We allocate an integer table `dp` of size `(n+1) × (x+1)`. Hence **O(n · x)** extra space.

---

**That completes a step‐by‐step DP solution** for counting the ways to roll `n` m‐sided dice to total exactly `x`.






```java
public class A09DiceThrows {

  // Function to find the number of ways to get a sum x with n dice and m faces
  public static int diceThrows(int n, int m, int x) {
    // Step 1: Initialize the DP table with 0s
    int[][] dp = new int[n + 1][x + 1];

    // Step 2: Base case: there's one way to get a sum of 0 with 0 dice
    dp[0][0] = 1;

    // Step 3: Fill the DP table
    for (int dice = 1; dice <= n; dice++) {
      for (int sum = 1; sum <= x; sum++) {
        // For each face of the dice, update the DP table
        for (int face = 1; face <= m; face++) {
          if (face <= sum) {
            dp[dice][sum] += dp[dice - 1][sum - face];
          }
        }
      }
    }

    // Step 4: The result is the number of ways to get the sum x with n dice
    return dp[n][x];
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int n1 = 2, m1 = 6, x1 = 7;
    System.out.println(
        "Ways to get sum " + x1 + " with " + n1 + " dice: " + diceThrows(n1, m1, x1)); // Output: 6

    // Example 2
    int n2 = 3, m2 = 4, x2 = 5;
    System.out.println(
        "Ways to get sum " + x2 + " with " + n2 + " dice: " + diceThrows(n2, m2, x2)); // Output: 6
  }

  /*
   Time Complexity:
   - O(n * x * m), where n is the number of dice, x is the target sum, and m is the number of faces on the dice.

   Space Complexity:
   - O(n * x), where n is the number of dice and x is the target sum.
  */
}
```
