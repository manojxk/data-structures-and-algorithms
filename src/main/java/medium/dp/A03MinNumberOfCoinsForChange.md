**Problem Restatement**
Given a target amount `n` (a non‐negative integer) and an array of distinct positive coin denominations `denoms[]`, find the **minimum number of coins** needed (using an unlimited supply of each denomination) to make exactly `n`. If it’s impossible to form `n` from those coins, return `-1`.

> **Example**
>
> ```
> n = 7  
> denoms = [1, 5, 10]  
> ```
>
> You can make 7 as `5 + 1 + 1`, using 3 coins. There is no way to use fewer than 3 coins. Hence the answer is **3**.

---

## 1. Brute‐Force Recursive Solution (Exponential Time, O(n) Space)

### Idea

At each recursive step, you have to decide which coin to use next. Suppose you still need to form an amount `remaining`. You can try:

1. **Use one coin** of any denomination `coin` in `denoms[]`, provided `coin ≤ remaining`. If you pick `coin`, then your new “remaining” becomes `remaining – coin`, and you recurse on that subproblem.
2. Among all possible choices of which `coin` to use first, you take the one that leads to the **fewest total coins** (plus one for the coin you just used).

Formally, let

```
minCoinsHelper(remaining, denoms):
  if remaining == 0: return 0     // base case: made exactly 0 with zero coins
  if remaining < 0:  return ∞     // no way (overshot), treat as impossible

  best = ∞
  for each coin in denoms:
    result = minCoinsHelper(remaining – coin, denoms)
    if result ≠ ∞:
      best = min(best, result + 1)
  return best
```

Then call `minCoinsHelper(n, denoms)`. If it returns ∞ (or Java’s `Integer.MAX_VALUE`), you output `-1`.

### Java Code

```java
package medium.dp;

public class A03MinNumberOfCoinsForChange {

  // Brute‐Force recursive approach
  public static int minNumberOfCoinsForChange(int n, int[] denoms) {
    int result = minCoinsHelper(n, denoms);
    // If result is still Integer.MAX_VALUE, no combination could form exactly n
    return (result == Integer.MAX_VALUE) ? -1 : result;
  }

  private static int minCoinsHelper(int remaining, int[] denoms) {
    // Base cases
    if (remaining == 0) {
      return 0; // zero coins needed to form 0
    }
    if (remaining < 0) {
      return Integer.MAX_VALUE; // impossible to form a negative amount
    }

    int best = Integer.MAX_VALUE;
    // Try every coin as the “next coin”
    for (int coin : denoms) {
      int res = minCoinsHelper(remaining - coin, denoms);
      if (res != Integer.MAX_VALUE) {
        best = Math.min(best, res + 1);
      }
    }
    return best;
  }

  public static void main(String[] args) {
    int[] denoms = {1, 5, 10};
    int n = 7;
    // Brute‐force result (exponential time)
    System.out.println(minNumberOfCoinsForChange(n, denoms)); // Expected: 3
  }
}
```

* **Time Complexity:** Exponential, roughly $O(d^n)$ in the worst case ($d$ = number of denominations, $n$ = target).
* **Space Complexity:** $O(n)$ due to recursion depth.

This solution quickly becomes infeasible for moderate or large `n`.

---

## 2. Optimized Dynamic‐Programming Solution (O(n × d) Time, O(n) Space)

### Idea

Build an array `minCoins[]` of length `n + 1`, where each entry `minCoins[amount]` stores the **minimum number of coins** needed to form exactly `amount`. Initialize all entries to “infinite” (here, `Integer.MAX_VALUE`), except `minCoins[0] = 0` (zero coins to form 0). Then, for each coin denomination and each `amount` from `coin` to `n`, update:

```
minCoins[amount] = min(minCoins[amount],
                      minCoins[amount – coin] + 1)
```

* `minCoins[amount – coin] + 1` means: “if you already know how to form (amount – coin) with X coins, then forming `amount` by adding one `coin` takes (X + 1) coins.”
* Taking the minimum with the old `minCoins[amount]` ensures you keep the best (fewest‐coins) solution so far.

After processing all denominations, if `minCoins[n]` is still “infinite,” return `-1`; otherwise, return `minCoins[n]`.

### Step‐by‐Step

1. Let `minCoins` be an integer array of size `n + 1`.
2. Fill it with `Integer.MAX_VALUE` (to represent “impossible” initially).
3. Set `minCoins[0] = 0`.
4. For each `coin` in `denoms[]`:

   * For `amount = coin` to `n`:

     * If `minCoins[amount – coin] != Integer.MAX_VALUE`, do

       ```
       minCoins[amount] = min(minCoins[amount],
                              minCoins[amount – coin] + 1);
       ```
5. If `minCoins[n]` is still `Integer.MAX_VALUE`, return `-1`. Otherwise, return `minCoins[n]`.

### Java Code

```java
package medium.dp;

import java.util.Arrays;

public class A03MinNumberOfCoinsForChange {

  // DP approach: O(n * d) time, O(n) space
  public static int minNumberOfCoinsForChangeDP(int n, int[] denoms) {
    int[] minCoins = new int[n + 1];
    Arrays.fill(minCoins, Integer.MAX_VALUE);
    minCoins[0] = 0; // 0 coins needed to make amount 0

    for (int coin : denoms) {
      for (int amount = coin; amount <= n; amount++) {
        if (minCoins[amount - coin] != Integer.MAX_VALUE) {
          minCoins[amount] = Math.min(minCoins[amount],
                                     minCoins[amount - coin] + 1);
        }
      }
    }

    return (minCoins[n] == Integer.MAX_VALUE) ? -1 : minCoins[n];
  }

  public static void main(String[] args) {
    int[] denoms = {1, 5, 10};
    int n = 7;
    // DP result
    System.out.println(minNumberOfCoinsForChangeDP(n, denoms)); // Expected: 3
  }
}
```

* **Time Complexity:** $O(n \times d)$, where

  * $n$ = target amount,
  * $d$ = number of denominations.
    Each coin causes a loop over amounts up to $n$.
* **Space Complexity:** $O(n)$ for the `minCoins[]` array.

This DP solution is feasible even for fairly large `n` (up to, say, a few million, depending on environment) provided the product $n \times d$ stays within time limits.

---

## Complexity Summary

| Approach                      | Time Complexity | Space Complexity |
| ----------------------------- | --------------- | ---------------- |
| Brute‐Force Recursive         | $O(d^n)$        | $O(n)$           |
| Dynamic Programming (Optimal) | $O(n \times d)$ | $O(n)$           |

* Use the **DP approach** for any non-trivial `n` or sizable set of denominations.
* The **recursive brute force** is only feasible for very small inputs due to exponential growth.
