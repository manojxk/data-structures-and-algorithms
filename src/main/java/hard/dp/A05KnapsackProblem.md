Below is a step-by-step, beginner-friendly walkthrough of all four implementations you provided for the 0/1 Knapsack Problem. We’ll cover:

1. **Problem restatement**
2. **Brute-force recursive solution** (`knapsackRecursive`)
3. **Top-down memoized recursion** (`knapsackMemo`)
4. **Bottom-up tabulation** (`knapsackTabulation`)
5. **Reconstructing which items were chosen** (`knapsackProblem` in the second class)

Throughout, we’ll highlight how each approach works, why it has the stated time/space costs, and how to extract the actual item selection in the tabulation version.

---

## 1. Problem Restatement

You have:

* An array of **weights**: `weights[i]` is the weight of the $i$th item.
* An array of **profits** (or values): `profits[i]` is the profit (value) of the $i$th item.
* A knapsack with capacity $W$ (let’s call it `capacity` in code).

You want to pick a subset of these items so that:

* The **total weight** of chosen items does not exceed `capacity`, and
* The **total profit** of chosen items is as large as possible.

Because it’s “0/1 Knapsack,” you either take an entire item or leave it; you cannot take fractional parts.

---

## 2. Brute-Force Recursive Solution

**Signature:**

```java
public static int knapsackRecursive(int[] weights, int[] profits, int capacity, int n)
```

* `weights[]` and `profits[]` each have length $n$.
* We interpret “considering the first $n$ items, what’s the best profit we can achieve with a remaining capacity of `capacity`?”
* We’ll call it initially with `knapsackRecursive(weights, profits, capacity, n)`, meaning “consider all $n$ items.”

### How It Works

1. **Base Case:**

   * If `n == 0` or `capacity == 0`, no items are left to consider or the knapsack is full → return 0 profit.

     ```java
     if (n == 0 || capacity == 0) {
       return 0;
     }
     ```
2. **If the $n$th item’s weight exceeds current capacity**, we **cannot** include it.
   So we skip that item and solve for the first $n-1$ items with the same capacity:

   ```java
   if (weights[n - 1] > capacity) {
     return knapsackRecursive(weights, profits, capacity, n - 1);
   }
   ```
3. **Otherwise**, we have two choices for the $n$th item (index `n-1` in the arrays):

   * **Include it.** Then we gain `profits[n - 1]`, and we reduce our remaining capacity by `weights[n - 1]`. We recursively solve the subproblem of “first $n-1$ items with capacity `capacity - weights[n - 1]`.”

     ```java
     int includeItem = profits[n - 1]
                     + knapsackRecursive(weights, profits, capacity - weights[n - 1], n - 1);
     ```
   * **Exclude it.** We get 0 profit from this item, and we still have the same capacity. So solve “first $n-1$ items with capacity.”

     ```java
     int excludeItem = knapsackRecursive(weights, profits, capacity, n - 1);
     ```
   * **Pick the better of the two**:

     ```java
     return Math.max(includeItem, excludeItem);
     ```

Putting it all together:

```java
public static int knapsackRecursive(int[] weights, int[] profits, int capacity, int n) {
  // 1) Base case
  if (n == 0 || capacity == 0) {
    return 0;
  }

  // 2) If the current item is too heavy, skip it
  if (weights[n - 1] > capacity) {
    return knapsackRecursive(weights, profits, capacity, n - 1);
  }
  else {
    // 3a) Include the item
    int includeItem = profits[n - 1] 
                    + knapsackRecursive(weights, profits,
                                        capacity - weights[n - 1],
                                        n - 1);
    // 3b) Exclude the item
    int excludeItem = knapsackRecursive(weights, profits, capacity, n - 1);

    // 3c) Take whichever gives higher profit
    return Math.max(includeItem, excludeItem);
  }
}
```

### Why It’s $O(2^n)$ Time

* At each step, except when an item is too heavy, we make **two recursive calls**: one including the item and one excluding it.
* In effect, you build a recursion tree of height $n$, and at each level you branch into two subcalls → about $2^n$ leaves.
* Hence **exponential** time.

### Why It’s $O(n)$ Space

* The only extra space is the recursion stack. In the worst case (we always choose to include/exclude all items one by one), the recursion depth is $n$. Hence $O(n)$ stack usage.

---

## 3. Top-Down Memoized Solution

**Signature:**

```java
public static int knapsackMemo(int[] weights,
                               int[] profits,
                               int capacity,
                               int n)
```

* We add a static or global 2D array `dp[n+1][capacity+1]`, initially filled with $-1$.
* Whenever we compute `knapsackMemo(weights, profits, c, k)`, we store the result in `dp[k][c]`.
* If we ever revisit that same subproblem (`dp[k][c] != -1`), we simply return it instead of recomputing.

### Setup

Before calling the memoized function, we do:

```java
dp = new int[n + 1][capacity + 1];
for (int[] row : dp) {
  Arrays.fill(row, -1);
}
int maxProfit = knapsackMemo(weights, profits, capacity, n);
```

Here `dp[i][j]` is intended to record “maximum profit using first $i$ items with knapsack capacity $j$.” If it’s $-1$, we haven’t solved that subproblem yet.

### How It Works

We simply copy the logic of the brute-force recursion, but:

1. **First check** if `dp[n][capacity]` is already known. If yes, return it.

   ```java
   if (dp[n][capacity] != -1) {
     return dp[n][capacity];
   }
   ```
2. Then do the same “too heavy?” check or “include vs exclude?” logic.
3. Store the result into `dp[n][capacity]` before returning.

Concretely:

```java
public static int knapsackMemo(int[] weights, int[] profits, int capacity, int n) {
  // Base case
  if (n == 0 || capacity == 0) {
    return 0;
  }

  // If we’ve already solved this subproblem, return it
  if (dp[n][capacity] != -1) {
    return dp[n][capacity];
  }

  // If item is too heavy, must skip it
  if (weights[n - 1] > capacity) {
    dp[n][capacity] = knapsackMemo(weights, profits, capacity, n - 1);
  }
  else {
    // Option 1: include it
    int includeItem = profits[n - 1]
                    + knapsackMemo(weights, profits,
                                   capacity - weights[n - 1],
                                   n - 1);
    // Option 2: exclude it
    int excludeItem = knapsackMemo(weights, profits, capacity, n - 1);

    dp[n][capacity] = Math.max(includeItem, excludeItem);
  }

  return dp[n][capacity];
}
```

### Why It’s $O(n \times W)$ Time

* **Without memoization**, each subproblem `knapsackRecursive(n, capacity)` branches into two calls → exponential blow-up.
* **With memoization**, there are only $(n+1)\times(capacity+1)$ distinct subproblems (because $n$ can range 0…n and capacity can range 0…W).
* Each subproblem is computed at most **once**, and each computation does a constant amount of work beyond recursive calls.
* Therefore, total time = $O(n \times W)$, where $W =$ `capacity`.

### Space Complexity: $O(n \times W)$

* We store a 2D table `dp[n+1][W+1]`. That alone is $O(n \times W)$ space.
* Plus the recursion stack of depth up to $n$. So total is $O(n \times W)$ (dominated by the DP table).

---

## 4. Bottom-Up Tabulation

**Signature:**

```java
public static int knapsackTabulation(int[] weights, int[] profits, int capacity)
```

* We build a 2D array `dp[i][w]` where:

  * $i = 0 \dots n$ (number of items considered)
  * $w = 0 \dots \text{capacity}$
  * `dp[i][w]` = the maximum profit achievable using **exactly the first $i$ items** and a knapsack of capacity $w$.

### How We Fill It

1. **Initialize** `dp[0][w] = 0` for all `w` (if no items are considered, profit is 0).
   Also `dp[i][0] = 0` for all `i` (if capacity is 0, profit is 0).
2. We iterate `i` from 1 to `n` (meaning “considering items 0…i−1”), and for each `i` we iterate `w` from 0 to `capacity`.
3. At each `(i, w)`, we have two choices:

   * **Exclude** item $i-1$. Then profit = `dp[i−1][w]` (whatever best we got without this item and same capacity).
   * **Include** item $i-1$ (only possible if `weights[i−1] ≤ w`). Then profit =

     ```
     profits[i - 1] + dp[i - 1][ w - weights[i - 1] ]
     ```

     because if we include item $i-1$, we gain `profits[i−1]` but our remaining capacity for the *previous* items is `w – weights[i−1]`.
4. So we fill

   ```java
   if (weights[i - 1] <= w) {
     dp[i][w] = Math.max(
                   dp[i - 1][w],                              // exclude
                   profits[i - 1] + dp[i - 1][ w - weights[i - 1] ]  // include
                 );
   } else {
     dp[i][w] = dp[i - 1][w]; // we can’t include; must exclude
   }
   ```
5. After filling the entire table, the answer is in `dp[n][capacity]` (using all `n` items, full `capacity`).

### Code

```java
public static int knapsackTabulation(int[] weights, int[] profits, int capacity) {
  int n = profits.length;
  // dp[i][w] = max profit using first i items with capacity w
  int[][] dp = new int[n + 1][capacity + 1];

  // Fill the table row by row
  for (int i = 0; i <= n; i++) {
    for (int w = 0; w <= capacity; w++) {
      if (i == 0 || w == 0) {
        // Base case: no items or zero capacity ⇒ profit = 0
        dp[i][w] = 0;
      }
      else if (weights[i - 1] <= w) {
        // We can choose to include or exclude item (i-1)
        int includeProfit = profits[i - 1] + dp[i - 1][ w - weights[i - 1] ];
        int excludeProfit = dp[i - 1][w];
        dp[i][w] = Math.max(includeProfit, excludeProfit);
      }
      else {
        // Too heavy to include
        dp[i][w] = dp[i - 1][w];
      }
    }
  }

  // The answer: best profit using ALL n items with full capacity
  return dp[n][capacity];
}
```

### Why It’s $O(n \times W)$ Time and Space

* We have an outer loop `i = 0..n` and an inner loop `w = 0..capacity`. That’s $(n+1) \times (W+1)$ iterations, each doing only $O(1)$ work → **$O(n\cdot W)$ time**.
* The 2D table `dp` has size $(n+1) \times (W+1)$ → **$O(n\cdot W)$ space**.

---

## 5. Reconstructing Which Items Were Selected

In many knapsack variants, it’s useful not only to know the maximum profit but also which items achieve that profit. The second class you gave—`A05KnapsackProblem` in the “hard.dp” package—does exactly that using bottom-up tabulation **plus** a backtracking step.

**Signature:**

```java
public static List<Object> knapsackProblem(int[] values,
                                           int[] weights,
                                           int capacity)
```

* Returns a `List<Object>` where:

  1. `result.get(0)` = the maximum profit (an `Integer`), and
  2. `result.get(1)` = a `List<Integer>` of item‐indices that were chosen.

### Steps

1. **Build the same DP table** as in `knapsackTabulation`:

   ```java
   int n = values.length;
   int[][] dp = new int[n + 1][capacity + 1];

   for (int i = 1; i <= n; i++) {
     for (int w = 0; w <= capacity; w++) {
       if (weights[i - 1] <= w) {
         dp[i][w] = Math.max(
                        dp[i - 1][w], 
                        values[i - 1] + dp[i - 1][w - weights[i - 1]]
                      );
       } else {
         dp[i][w] = dp[i - 1][w];
       }
     }
   }
   // At this point, dp[n][capacity] is the maximum profit.
   int maxValue = dp[n][capacity];
   ```

2. **Backtrack to find which items contributed**. Start from `i = n, w = capacity`.
   While `i > 0`, check:

   ```java
   if (dp[i][w] != dp[i - 1][w]) {
     // That means item (i−1) *was* included, because excluding it would have left the same profit as row i−1.
     selectedItems.add(i - 1);
     w -= weights[i - 1]; // Reduce capacity by that item’s weight
   }
   // Otherwise, if dp[i][w] == dp[i-1][w], item (i−1) was not included.
   i--;
   ```

   * Each time you see the profit differ when you move from row i to row i−1 (same column w), you know the i−1 item must have been used.
   * Continue until `i == 0` or `w == 0`.

3. **Reverse the `selectedItems` list** (since you found them in reverse order from n→1).

4. **Return** a list containing `[maxValue, selectedItems]`.

### Code

```java
public static List<Object> knapsackProblem(int[] values, int[] weights, int capacity) {
  int n = values.length;

  // Build the DP table
  int[][] dp = new int[n + 1][capacity + 1];
  for (int i = 1; i <= n; i++) {
    for (int w = 0; w <= capacity; w++) {
      if (weights[i - 1] <= w) {
        dp[i][w] = Math.max(
                        dp[i - 1][w], 
                        values[i - 1] + dp[i - 1][w - weights[i - 1]]
                      );
      } else {
        dp[i][w] = dp[i - 1][w];
      }
    }
  }

  // The maximum profit is dp[n][capacity]
  int maxValue = dp[n][capacity];

  // Backtrack to find which items were included
  List<Integer> selectedItems = new ArrayList<>();
  int w = capacity;
  for (int i = n; i > 0; i--) {
    if (dp[i][w] != dp[i - 1][w]) {
      // Item (i-1) must have been included
      selectedItems.add(i - 1);
      w -= weights[i - 1];
    }
    // else: item (i-1) was not included; just move on
  }

  // Reverse so it’s in ascending index order
  Collections.reverse(selectedItems);

  // Prepare the return: [ maximum profit, list of chosen item indices ]
  List<Object> result = new ArrayList<>();
  result.add(maxValue);
  result.add(selectedItems);
  return result;
}
```

#### Example Walk‐through

Suppose

```
values  = [60, 100, 120]
weights = [ 10,  20,  30]
capacity = 50
```

1. We fill a $4 \times 51$ DP table (rows $i = 0..3$, columns $w = 0..50$).
2. After filling, we find `dp[3][50] = 220`.
3. To see which items made that 220:

   * Start at $i = 3, w = 50$.
   * `dp[3][50] = 220` vs `dp[2][50] = 160`. Since they differ, item `2` (profit 120, weight 30) was included. → add index `2`; reduce `w` to `50−30=20`.
   * Now $i = 2, w = 20$. Compare `dp[2][20] = 100` vs `dp[1][20] = 60`. They differ ⇒ item `1` (profit 100, weight 20) was included. → add index `1`; reduce `w` to `20−20 = 0`.
   * Now $i = 1, w = 0$. Either column is zero, so we stop.
   * Reverse `[2, 1]` → `[1, 2]`.

So the selected items are `1` and `2` (0-based), giving total profit $100 + 120 = 220$.

---

## Summary of All Four Approaches

| Approach                        | Time Complexity | Space Complexity | Main Idea                                                        |
| ------------------------------- | --------------- | ---------------- | ---------------------------------------------------------------- |
| **Simple Recursion**            | $O(2^n)$        | $O(n)$ (stack)   | Recurse “include or exclude” each item without caching.          |
| **Top-Down Memoization**        | $O(n \times W)$ | $O(n \times W)$  | Same recursion, but store results in `dp[n][cap]` to reuse.      |
| **Bottom-Up Tabulation**        | $O(n \times W)$ | $O(n \times W)$  | Fill a 2D `dp[i][w]` table iteratively from smaller subproblems. |
| **Tabulation + Reconstruction** | $O(n \times W)$ | $O(n \times W)$  | Same table as bottom-up, then backtrack to find chosen items.    |

* In **brute-force recursion**, you try all subsets of items → exponential time.
* In **memoized recursion**, each $(i,\,c)$ state is computed once → $O(nW)$.
* In **tabulation**, you fill all $(i,\,w)$ from $i=0..n$, $w=0..W$ → $O(nW)$.

Additionally, by storing the entire DP table in tabulation, you can **trace back** which items were used to achieve the maximum profit. The idea is simple: whenever

```
dp[i][w] ≠ dp[i - 1][w]
```

it means “including item i−1 gave us a strictly better profit than excluding it.”
You then subtract `weights[i−1]` from `w` and continue with `i−1`.

---

### Complete Example

Putting it all together, you might combine these classes like so:

```java
public class KnapsackAllWays {
  static int[][] dp; // for memo

  // 1) Brute-force recursion
  public static int knapsackRecursive(int[] weights, int[] profits, int capacity, int n) {
    if (n == 0 || capacity == 0) return 0;
    if (weights[n - 1] > capacity) {
      return knapsackRecursive(weights, profits, capacity, n - 1);
    }
    else {
      int includeItem = profits[n - 1] 
                      + knapsackRecursive(weights, profits, capacity - weights[n - 1], n - 1);
      int excludeItem = knapsackRecursive(weights, profits, capacity, n - 1);
      return Math.max(includeItem, excludeItem);
    }
  }

  // 2) Top-down memoized recursion
  public static int knapsackMemo(int[] weights, int[] profits, int capacity, int n) {
    if (n == 0 || capacity == 0) return 0;
    if (dp[n][capacity] != -1) return dp[n][capacity];

    if (weights[n - 1] > capacity) {
      dp[n][capacity] = knapsackMemo(weights, profits, capacity, n - 1);
    } else {
      int includeItem = profits[n - 1] 
                      + knapsackMemo(weights, profits, capacity - weights[n - 1], n - 1);
      int excludeItem = knapsackMemo(weights, profits, capacity, n - 1);
      dp[n][capacity] = Math.max(includeItem, excludeItem);
    }
    return dp[n][capacity];
  }

  // 3) Bottom-up tabulation
  public static int knapsackTabulation(int[] weights, int[] profits, int capacity) {
    int n = profits.length;
    int[][] table = new int[n + 1][capacity + 1];
    for (int i = 1; i <= n; i++) {
      for (int w = 0; w <= capacity; w++) {
        if (weights[i - 1] <= w) {
          table[i][w] = Math.max(
                           table[i - 1][w],
                           profits[i - 1] + table[i - 1][w - weights[i - 1]]
                         );
        } else {
          table[i][w] = table[i - 1][w];
        }
      }
    }
    return table[n][capacity];
  }

  // 4) Tabulation + backtrack to find which items were chosen
  public static List<Object> knapsackProblem(int[] values, int[] weights, int capacity) {
    int n = values.length;
    int[][] dpTable = new int[n + 1][capacity + 1];
    for (int i = 1; i <= n; i++) {
      for (int w = 0; w <= capacity; w++) {
        if (weights[i - 1] <= w) {
          int includeProfit = values[i - 1] + dpTable[i - 1][w - weights[i - 1]];
          int excludeProfit = dpTable[i - 1][w];
          dpTable[i][w] = Math.max(includeProfit, excludeProfit);
        } else {
          dpTable[i][w] = dpTable[i - 1][w];
        }
      }
    }

    int maxValue = dpTable[n][capacity];

    // Backtrack
    List<Integer> picked = new ArrayList<>();
    int w = capacity;
    for (int i = n; i > 0; i--) {
      if (dpTable[i][w] != dpTable[i - 1][w]) {
        picked.add(i - 1);       // item i-1 was included
        w -= weights[i - 1];
      }
    }
    Collections.reverse(picked);

    List<Object> result = new ArrayList<>();
    result.add(maxValue);
    result.add(picked);
    return result;
  }

  public static void main(String[] args) {
    int[] weights = {1, 3, 4, 5};
    int[] profits = {1, 4, 5, 7};
    int capacity = 7;
    int n = weights.length;

    // 1) Brute-force (very slow for large n)
    System.out.println("Brute-force: " 
            + knapsackRecursive(weights, profits, capacity, n));

    // 2) Memoized
    dp = new int[n + 1][capacity + 1];
    for (int[] row : dp) Arrays.fill(row, -1);
    System.out.println("Memoized: " 
            + knapsackMemo(weights, profits, capacity, n));

    // 3) Tabulation
    System.out.println("Tabulation: " 
            + knapsackTabulation(weights, profits, capacity));

    // 4) Tabulation + backtrack
    List<Object> answer = knapsackProblem(profits, weights, capacity);
    // answer.get(0) = max profit, answer.get(1) = list of chosen indices
    System.out.println("Max Profit with Items: " + answer.get(0));
    System.out.println("Items chosen: " + answer.get(1));
  }
}
```

### Recap

* **Recursive (no memo)**: Simple to write, but $O(2^n)$ time.
* **Top-down memo**: Same logic but caches the result of each $(i,\,cap)$ in a 2D array so each subproblem is computed only once → $O(nW)$ time and space.
* **Bottom-up tabulation**: Iteratively fill a 2D DP table of size $(n+1)\times(capacity+1)$ → $O(nW)$ time and space, no recursion overhead.
* **Backtracking in tabulation**: Once you have the filled table, you can walk backward from $(n,\,capacity)$ row by row to see which items contributed to the optimal profit.

All together, these patterns cover the entire spectrum of 0/1 Knapsack solutions—from naive recursion out to a fully efficient DP that also extracts the chosen items.
