Below is a complete, step‐by‐step explanation of the “Juice Bottling” problem, including the dynamic‐programming approach, how the code works line by line, and a walkthrough on the sample input. I’ve written everything assuming you’re new to DP, so there are no gaps in the reasoning.

---

## Problem Restatement

* You have an array `prices[]` of length `n`.

  * `prices[k]` tells you the revenue you would get if you bottle **exactly** `k` units of juice in one container.
  * By convention, `prices[0] = 0`, because “zero units” brings zero revenue.
* In total, you have `n − 1` units of juice available (since the array is length `n`, the maximum meaningful “full‐size” is `n−1` units).
* You may decide to “split” those `n−1` units into smaller containers of various sizes. For instance, if `n−1 = 3`, you could either:

  1. Put all 3 units in one container (and earn `prices[3]`),
  2. Split into one container of size 1 and one of size 2 (earning `prices[1] + prices[2]`), or
  3. Split into three containers of size 1 each (earning `prices[1] + prices[1] + prices[1]`).

Your task is:

> **Choose a way of partitioning the total `n−1` units into one or more containers so that the sum of the revenues of those containers is as large as possible.**
> Return the list of container‐sizes (in ascending order) that achieves that maximum total revenue.

You can assume there is only one unique optimal partition.

---

## Why This Is Essentially “Rod Cutting” in DP

This is exactly the classic “rod‐cutting” (or “juicing”) DP problem in disguise. Think of the total `n−1` units of juice as a rod of length `n−1`. You know the price for selling a rod of length `k` is `prices[k]`. You want to cut that rod into smaller pieces (anywhere you like), sell each piece, and maximize total revenue.

**Key Observations**:

1. **Recursive substructure**: If you have `i` units left to allocate, you can choose to allocate the first container of size `j` (where `1 ≤ j ≤ i`), receive `prices[j]` revenue for that piece, and then optimally solve the same problem for `i−j` remaining units.
2. **Overlapping subproblems**: If you compute “best revenue for 1 unit,” “best revenue for 2 units,” … “best revenue for i units,” you’ll reuse those results many times. That calls for a DP table.

---

## Dynamic Programming (DP) Formulation

1. Let `dp[i]` = the **maximum** revenue you can get from exactly `i` units of juice.
2. Base case: `dp[0] = 0` (zero units → zero revenue).
3. Recurrence for `i ≥ 1`:

   ```text
   dp[i] = max_{1 ≤ j ≤ i} ( prices[j] + dp[i - j] )
   ```

   In words: If you have `i` units to bottle, try “first bottle = j units” (get `prices[j]`), then the best you can do with the remaining `(i − j)` units is already stored in `dp[i − j]`. You pick the `j` that maximizes `prices[j] + dp[i − j]`.
4. We ultimately want `dp[n−1]`, because that represents “maximum revenue using all n−1 units.”

To reconstruct **which cuts (container sizes)** achieve that maximum, we also store an auxiliary array `split[i]`:

* `split[i] = j` means “when you have `i` units, the best move is to bottle `j` units first, then optimally split the remaining `i−j`.”
* If `split[i] = 0`, that indicates “it’s best to leave all `i` units uncut,” so you just take `i` as a single container.

---

## Code Walkthrough

```java
import java.util.ArrayList;
import java.util.List;

public class A10JuiceBottling {

  // Function that returns a list of container‐sizes (in ascending order)
  // that yields the maximum revenue for exactly (n−1) units of juice.
  public static List<Integer> maxRevenueJuiceBottling(int[] prices) {
    int n = prices.length;       
    // We actually have (n−1) total units of juice to split.
    // dp[i] = maximum revenue for i units.
    int[] dp = new int[n];
    // split[i] = the size j (1 ≤ j ≤ i) that gives the best revenue for i units.
    // If split[i]=0, it means “don’t cut i at all, just use the entire i‐sized container.”
    int[] split = new int[n];

    // 1) Base case: dp[0] = 0 (zero units yield zero dollars).
    dp[0] = 0;

    // 2) Fill in dp[1], dp[2], …, dp[n−1] in increasing order:
    for (int i = 1; i < n; i++) {
      // Initially assume “no cut”: put all i units in one container.
      dp[i] = prices[i];
      split[i] = 0;   // 0 indicates “no split; just take the whole i.”

      // Now try splitting i into (j) and (i−j), for all j=1..(i−1).
      for (int j = 1; j < i; j++) {
        int candidateRevenue = dp[j] + dp[i - j];
        // If cutting off a container of size j, plus the best way to
        // handle (i−j) units, gives more revenue than the current best:
        if (candidateRevenue > dp[i]) {
          dp[i] = candidateRevenue;
          split[i] = j;  // Record “best first piece = j units.”
        }
      }
    }

    // 3) Reconstruct which container‐sizes gave that optimal dp[n−1].
    // We will backtrack from 'quantity = n−1' down to zero.
    List<Integer> result = new ArrayList<>();
    int quantity = n - 1;

    // While we still have some positive quantity left, keep peeling off 'split[quantity]'.
    while (quantity > 0) {
      if (split[quantity] == 0) {
        // If split[quantity] is 0, that means the best move for 'quantity' units
        // was “don’t split—just make one container of all of them.”
        result.add(quantity);
        break;
      } else {
        // Otherwise, the best first cut is of size 'split[quantity]':
        int firstPiece = split[quantity];
        result.add(firstPiece);
        // Then reduce 'quantity' by that piece size and continue.
        quantity -= firstPiece;
      }
    }

    // 4) Sort the container sizes in ascending order before returning.
    // The problem statement wants them in ascending order.
    result.sort(Integer::compareTo);
    return result;
  }

  // A simple main() to test
  public static void main(String[] args) {
    // Example from the prompt:
    // prices[0] = 0, prices[1] = 1, prices[2] = 3, prices[3] = 2
    // We have (n−1)=3 units total.
    int[] prices = {0, 1, 3, 2};
    List<Integer> result = maxRevenueJuiceBottling(prices);
    System.out.println("Optimal container sizes: " + result);
    // Expected: [1, 2], because:
    //   revenue(1) + revenue(2) = prices[1] + prices[2] = 1 + 3 = 4,
    //   which beats any other splitting (like just 3→2, or 1+1+1, etc.)
  }
}
```

---

## Detailed Explanation, Line by Line

1. **Arrays `dp[]` and `split[]`**

   ```java
   int[] dp = new int[n];
   int[] split = new int[n];
   ```

   * `dp[i]` will ultimately hold “maximum revenue if you have exactly `i` units of juice.”
   * `split[i]` will store which first‐piece size `j` (between `1` and `i`) achieves that maximum. If `split[i] == 0`, we interpret that as “the best way to handle `i` units is not to cut them at all (sell them as a single `i`‐unit container).”

2. **Base Case**

   ```java
   dp[0] = 0;
   ```

   * If you have zero units of juice, you cannot produce any revenue—so `dp[0] = 0`.

3. **Fill the DP table**

   ```java
   for (int i = 1; i < n; i++) {
     // (case “no cut at all”)
     dp[i] = prices[i];    
     split[i] = 0;         // 0 means “use the entire i as one container.”

     // Try every possible first‐cut j, where 1 <= j < i
     for (int j = 1; j < i; j++) {
       int candidateRevenue = dp[j] + dp[i - j];
       if (candidateRevenue > dp[i]) {
         dp[i] = candidateRevenue;
         split[i] = j;     // record that “cut off j units first” is best so far
       }
     }
   }
   ```

   * **Initially** we set `dp[i] = prices[i]` because maybe the best move is “don’t split: just sell one container of size `i` for `prices[i]`.”
   * Then, for each `j` from `1` to `i−1`, we consider “first bottle is size `j` (giving `prices[j]`, but we already computed the best form of those `j` units in `dp[j]`) plus optimally bottle the remaining `(i−j)` units (which is `dp[i−j]`).”
   * So we compute `candidateRevenue = dp[j] + dp[i−j]`. If that beats `prices[i]` (the “no‐cut” baseline) or any previously tested split, we update `dp[i]` to that larger candidate, and note `split[i] = j` to remember how we achieved it.

4. **Reconstruct the Optimal Partition**

   ```java
   List<Integer> result = new ArrayList<>();
   int quantity = n - 1;

   while (quantity > 0) {
     if (split[quantity] == 0) {
       // If split[quantity] is 0, the best single move for ‘quantity’ was “sell it all as one piece.”
       result.add(quantity);
       break;
     } else {
       // Otherwise, we first take off a chunk of size split[quantity].
       int firstPiece = split[quantity];
       result.add(firstPiece);
       quantity -= firstPiece;  // reduce the remaining units, and continue
     }
   }
   ```

   * Set `quantity = n−1` because that is the total amount of juice to allocate.
   * As long as `quantity > 0`, look at `split[quantity]`:

     * If `split[quantity] == 0`, that means “for these `quantity` units, it was best not to cut anymore—sell them as one container of size `quantity`.” We add that to `result` and break.
     * Otherwise, `split[quantity] = j` tells us that “the optimal first piece is size `j`, then handle the remaining `(quantity−j)` units.” So we add `j` to `result`, subtract `j` from `quantity`, and loop again.
   * By the time we exit this `while`, we have collected all container sizes whose sum is exactly `n−1`, and they correspond to the maximal‐revenue split.

5. **Sort and Return**

   ```java
   result.sort(Integer::compareTo);
   return result;
   ```

   * The problem states “output the container sizes in ascending order.” We sort the `List<Integer>` and return it.
   * If we never found any split (i.e. if `dp[n−1]` was just `prices[n−1]`, then `split[n−1]` stayed zero, so we immediately add `n−1` to `result` and break—returning a single element `[n−1]`).

---

## Step‐by‐Step on the Example

Take `prices = [0, 1, 3, 2]`. Then `n = 4`, so we have `n−1 = 3` total juice units.

1. **Initialize**

   ```
   dp = [0, 0, 0, 0]
   split = [0, 0, 0, 0]
   ```

   We know `dp[0] = 0` at the outset.

2. **Compute dp\[1]**

   * Start with “no‐cut”: `dp[1] = prices[1] = 1`, so `dp[1] = 1`, `split[1] = 0`.
   * There is no `j` from 1..0 (empty loop), so we do not change it.
     Result:

   ```
   dp[1] = 1,  split[1] = 0
   ```

3. **Compute dp\[2]**

   * Baseline: `dp[2] = prices[2] = 3`, `split[2] = 0`. (Meaning, if we bottle all 2 units together, we get 3.)
   * Now try splitting `2` as `(j=1) + (i−j=1)`:

     * That candidate revenue is `dp[1] + dp[1] = 1 + 1 = 2`, which is *less* than the baseline `3`. So we do not update.
   * Final:

   ```
   dp[2] = 3,  split[2] = 0  // best is “no split” for 2 units
   ```

4. **Compute dp\[3]**

   * Baseline: `dp[3] = prices[3] = 2`, `split[3] = 0`. (Sell all 3 at once for revenue=2.)
   * Try splitting `3` into `(j=1) + (i−j=2)`:

     * candidate = `dp[1] + dp[2] = 1 + 3 = 4`. That is *better* than the current `dp[3] = 2`, so update:

       ```
       dp[3] ← 4,  split[3] ← 1
       ```

       (Meaning: “first sell 1 unit, then handle 2 units.”)
   * Next, try `(j=2) + (i−j=1)`:

     * candidate = `dp[2] + dp[1] = 3 + 1 = 4`. That ties the best, so we could keep `split[3] = 1` (there’s exactly one unique solution assumed).
   * Final:

   ```
   dp[3] = 4,  split[3] = 1
   ```

   Thus, `dp[]` table looks like:

   ```
   i :  0   1   2   3
   dp: [0,  1,  3,  4]
   split: [0,  0,  0,  1]
   ```

   * `dp[3] = 4` means “maximum revenue selling 3 units is 4 dollars.”
   * `split[3] = 1` records that to get revenue 4, you first sell a container of size 1 (getting `prices[1]=1`), then you optimally sell the remaining 2 units (which yields `dp[2]=3`). So total = 1 + 3 = 4.

5. **Reconstructing the Optimal Partition**

   * We want to break up exactly 3 units (`quantity = 3` initially).
   * Since `split[3] = 1`, we take “first container of size 1” (add `1` to `result`), and reduce `quantity = 3 – 1 = 2`.
   * Next, look at `split[2]`. It is `0`, so “no further split” for the remaining 2. That means the second container is just size 2. We add `2` to `result` and break the loop.
   * We end up with `result = [1, 2]`. Sorting is trivial (it’s already in ascending order).

Therefore, the final answer is `[1, 2]`. That indeed yields total revenue = `prices[1] + prices[2] = 1 + 3 = 4`, which is the maximum possible.

---

## Time & Space Complexity

1. **Brute‐Force**

   ```java
   maxSumSubmatrixBruteForce(matrix, size)
   ```

   * **Time Complexity**:

     * We consider every top‐left corner `(i,j)` → roughly $(n - size + 1) × (m - size + 1)$ positions → on the order of $O(nm)$.
     * For each `(i,j)`, we loop `size × size` to sum the block → $O(\text{size}^2)$.
     * Total: $O(nm \times \text{size}^2)$.
   * **Space Complexity**:

     * Only a few integers (`maxSum`, `currentSum`, loop indices). ↦ $O(1)$.

2. **Optimized with Prefix Sums**

   ```java
   maxSumSubmatrixOptimized(matrix, size)
   ```

   * **Time Complexity**:

     1. Building the `(rows+1) × (cols+1)` prefix‐sum array takes $O(nm)$.
     2. Then sliding over each valid top‐left corner `(i,j)`—again roughly $O(nm)$—and computing the `size×size` block sum in $O(1)$ each.

     * Total: $O(nm)$.
   * **Space Complexity**:

     * We store a 2D array `prefixSums` of size $(n+1) × (m+1)$. ↦ $O(nm)$.
     * Plus a few extra integers for indices (negligible).

3. **Juice Bottling (Rod Cutting) DP**

   ```java
   maxRevenueJuiceBottling(prices)
   ```

   * Let `n = prices.length`. We actually want to optimize for `n−1` units.
   * **Time Complexity**:

     * The outer loop runs `i` from `1` to `n−1` → that's $n$ iterations.
     * For each `i`, we try all splits `j = 1 … i−1`, which is up to `O(i)` work. Summing over `i = 1..(n−1)` gives $1 + 2 + 3 + \dots + (n−1)$ = $O(n^2)$.
     * Therefore: **O(n²)**.
   * **Space Complexity**:

     * We use two integer arrays of length `n` (`dp[]` and `split[]`), plus the final `List<Integer>` to store up to `n` pieces in the worst case. That’s **O(n)** extra space.

---

## Final Remarks

* **Crucial insight**: any time you see “You can split an item of size `i` into two parts `(j, i−j)` and add their best solutions,” you suspect a rod‐cutting/DP approach.
* **Reconstruction**: is always a two‐step process in DP:

  1. You fill your `dp[i]` table so that it holds the maximum value.
  2. You keep a companion array (here, `split[i]`) that says *which choice led to that maximum*. Then you backtrack from `i = n−1` down to `0`, collecting the piece sizes.
* **Sorting the result**: we finally sort the list of piece sizes because the problem statement demands “the sizes in ascending order.”

That’s the full, end‐to‐end explanation—no gaps—from the high‐level DP idea to the Java implementation to the final complexity analysis. Good luck, and happy coding!
