**Problem Explanation**

You are given:

* A **target amount** `n` (a non‐negative integer).
* An array `denoms[]` of **distinct positive integers**, each representing a coin denomination (for example, `1, 5, 10, ...`).

You have an **unlimited supply** of each coin. Your task is to count **how many different ways** you can make change for the amount `n` using those denominations. Order does **not** matter—if you use one 1-coin and one 5-coin to make 6, that is the same combination as using one 5-coin and one 1-coin; we count that as a single way.

> **Example**
>
> ```
> n = 6  
> denoms = [1, 5]  
> ```
>
> There are exactly **two** ways to make 6 with \[1, 5]:
>
> 1. Use six 1-coins (1+1+1+1+1+1 = 6)
> 2. Use one 5-coin and one 1-coin (5+1 = 6)
>    Hence, the answer is **2**.

If `n = 0`, by convention there is **exactly one** way to make “0” — that is, by choosing no coins at all.

If `denoms` is empty, there are **zero** ways to make any positive `n` (and one way to make 0).

---

## 1. Brute‐Force Recursive Solution (Exponential Time, O(n) Space)

### Idea

We can think of a recursive process that, at each step, decides whether to **use** one coin of the current denomination or **skip** to the next denomination. Suppose `denoms[index]` is the denomination we’re looking at. If we still need to make an amount `amount`:

1. **Include** one coin of `denoms[index]`, which reduces the remaining `amount` by `denoms[index]`. Then we stay on `index` (because we can use unlimited coins of this same value).
2. **Exclude** `denoms[index]` entirely and move on to `index + 1` (so we’d consider the next smaller denomination).

We keep doing that until:

* If `amount == 0`, we’ve found a valid way → return 1.
* If `amount < 0`, we overshot → return 0.
* If `index` goes beyond the last denomination (and `amount > 0`), we have no way to make it → return 0.

Summing the counts of those two branches at each node enumerates all possible combinations.

### Recursive Pseudocode

```
countWays(amount, denoms, index):
  if amount == 0: return 1       // Found a valid combination
  if amount < 0 OR index == denoms.length: return 0  // No valid way

  // Case A: use one coin of value denoms[index]
  include = countWays(amount - denoms[index], denoms, index)

  // Case B: skip this denomination, try the next
  exclude = countWays(amount, denoms, index + 1)

  return include + exclude
```

Then the initial call is `countWays(n, denoms, 0)`.

### Java Implementation

```java
package medium.dp;

public class A02NumberOfWaysToMakeChange {

  /**
   * Brute‐Force recursive approach to count number of ways to make change.
   * Time:    O(2^n)  (exponential in the worst case)
   * Space:   O(n)    (recursion stack depth)
   */
  public static int numberOfWaysToMakeChange(int n, int[] denoms) {
    // Start recursion from index 0
    return countWays(n, denoms, 0);
  }

  private static int countWays(int amount, int[] denoms, int index) {
    // If we’ve made the exact amount, that’s one valid way
    if (amount == 0) {
      return 1;
    }

    // If we overshot or used all denominations without making amount, no way
    if (amount < 0 || index == denoms.length) {
      return 0;
    }

    // Option 1: Include one coin of denoms[index] and stay at same index
    int include = countWays(amount - denoms[index], denoms, index);

    // Option 2: Skip this denomination entirely and move to index + 1
    int exclude = countWays(amount, denoms, index + 1);

    // Total ways is sum of both options
    return include + exclude;
  }

  // Quick test
  public static void main(String[] args) {
    int n = 6;
    int[] denoms = {1, 5};
    System.out.println(numberOfWaysToMakeChange(n, denoms));
    // Expected Output: 2
  }
}
```

This works correctly, but it quickly becomes too slow when `n` or the number of denominations grows, because it repeatedly recomputes the same subproblems.

---

## 2. Optimized Dynamic‐Programming Solution (O(n × m) Time, O(n) Space)

### Idea

Instead of exploring all subsets recursively, we build up a **DP array** `ways[]` of length `n + 1`, where `ways[x]` will represent **“the number of ways to make amount x”** using the denominations we’ve seen so far. We start by initializing `ways[0] = 1`, because there is exactly **one** way to make amount 0: using no coins.

Then we iterate **denomination by denomination**, and for each coin value `coin = denoms[i]`, we update the `ways[]` array in a left‐to‐right manner:

* For each `amount` from `coin` to `n`, we can add new ways by “using one coin of this value.” Concretely:

  ```
  ways[amount] += ways[amount – coin]
  ```

  Because any way that previously made `(amount - coin)` can be extended by adding one `coin` to now make `amount`.

By processing coins one by one, each `ways[amount]` accumulates all possible combinations that end in any sequence of the denominations up to the current one.

### Step‐by‐Step

1. **Initialize** an array `ways` of size `n + 1` with all zeros.

   ```
   ways[0] = 1;  // Base case: one way to make 0
   for (int i = 1; i <= n; i++) {
     ways[i] = 0;
   }
   ```

2. **Loop** over each `coin` in `denoms`:

   ```java
   for (int coin : denoms) {
     for (int amount = coin; amount <= n; amount++) {
       // Take all ways that make (amount - coin) 
       // and extend them by adding one 'coin'
       ways[amount] += ways[amount - coin];
     }
   }
   ```

3. **Answer** will be `ways[n]`, the number of ways to make amount `n` using all denominations.

### Why It Works

* After processing the first denomination `denoms[0]`, `ways[x]` holds the number of ways to make `x` using only that first coin type (which is either 0 or 1 if `x` is a multiple of that coin).
* When we move to the next coin, say `denoms[1]`, we allow combinations that include any number of `denoms[1]` plus any combination of `denoms[0]`. The nested loop `amount = coin … n` ensures we count “all ways to build (amount‐coin) using earlier coins” and add one `coin` of the current type.

Because we do a single pass over `denoms` (length = m) and a pass over amounts up to `n` each time, the running time is **O(m × n)**.

### Java Implementation

```java
package medium.dp;

public class A02NumberOfWaysToMakeChange {

  /**
   * DP approach to count number of ways to make change for 'n' using 'denoms'.
   * Time:    O(n * m)  where n = target amount, m = number of denominations
   * Space:   O(n)      for the ways[] array
   */
  public static int numberOfWaysToMakeChangeDP(int n, int[] denoms) {
    // ways[x] = number of ways to make amount x
    int[] ways = new int[n + 1];
    ways[0] = 1;  // one way to make 0: choose no coins

    // Process each coin one at a time
    for (int coin : denoms) {
      // For every amount from coin..n, accumulate new ways
      for (int amount = coin; amount <= n; amount++) {
        ways[amount] += ways[amount - coin];
      }
    }

    return ways[n];  // number of ways to make exactly n
  }

  public static void main(String[] args) {
    int n = 6;
    int[] denoms = {1, 5};
    System.out.println(numberOfWaysToMakeChangeDP(n, denoms));
    // Expected Output: 2
  }
}
```

---

## Complexity Analysis

1. **Brute-Force Recursive**

   * **Time Complexity:** $O(2^n)$, because for each call you branch into “include coin” / “exclude coin,” leading to an exponential recursion tree.
   * **Space Complexity:** $O(n)$ for the recursion stack in the worst case (if you descend one denomination at a time).

2. **Dynamic Programming**

   * **Time Complexity:** $O(n \times m)$, where

     * $n$ = target amount,
     * $m$ = number of denominations.
       Each of the $m$ denominations loops over `amount` from `coin` to `n`.
   * **Space Complexity:** $O(n)$ to maintain the `ways[]` array of length $n + 1$.

Because $O(n \times m)$ is usually acceptable when $n$ and $m$ aren’t too large (e.g., $n$ up to a few thousand, $m$ up to a few hundred), the DP solution is **vastly** more efficient in practice than the exponential brute force.
