**Problem Restatement**

You have a pile of coins, each with a positive integer value (denomination). You want to figure out the **smallest amount of money** that you **cannot** form by choosing some subset of these coins.

* For example, if your coins are `[1, 2, 5]`, you can form:

  * 1 (just the 1-coin)
  * 2 (just the 2-coin)
  * 3 (1 + 2)
  * 5 (just the 5-coin)
  * 6 (1 + 5)
  * 7 (2 + 5)
  * 8 (1 + 2 + 5)
    The **smallest** amount you **cannot** form is **4** (because there is no way to make exactly 4 with 1, 2, and 5).

Given an array of coin values, return that smallest non-constructible amount.

---

## Why the Naïve Solution Fails

A brute-force way would be to generate *every* subset of coins, compute its sum, mark which sums are possible, and then scan upwards to find the first missing sum. But with *n* coins there are 2ⁿ subsets—completely impractical even for moderate *n*.

---

## Efficient Greedy Approach

1. **Sort the coins** in **ascending** order.
2. Maintain a running variable `currentChange` that represents:

   > **“All amounts from 1 up to `currentChange` can be formed with the coins I’ve processed so far.”**
3. As you consider the next coin `coin[i]`:

   * If `coin[i]` is **less than or equal to** `currentChange + 1`,
     you can extend the range of constructible amounts up to `currentChange + coin[i]`.
   * Otherwise, there’s a **gap**: you cannot form `currentChange + 1`. You can stop immediately—**that** is the answer.
4. If you finish processing every coin, then you can construct everything up to `currentChange`, so the answer is `currentChange + 1`.

---

### Intuition Behind `currentChange`

* Initially, with no coins, you can form **nothing**, so the next amount you can’t form is `1` (i.e. `currentChange = 0`).
* Suppose after sorting you pick up coins one by one:

  * If your next coin is `≤ currentChange + 1`, it “fills in” all gaps up to `currentChange + coin`.
  * If it’s bigger than `currentChange + 1`, you have a hole at `currentChange + 1` that no combination can fill (all your coins so far were too small, and this one is already too large).

---

### Worked Example

Coins: `[5, 7, 1, 1, 2, 3, 22]`

1. **Sort:** `[1, 1, 2, 3, 5, 7, 22]`
2. Initialize `currentChange = 0`.
3. Process each coin:

| Coin | Condition                     | Action                                   | New `currentChange` |
| ---- | ----------------------------- | ---------------------------------------- | ------------------- |
| 1    | 1 ≤ 0+1 → true                | extend range by 1 → covers up to 0+1     | 1                   |
| 1    | 1 ≤ 1+1 → true                | extend by 1 → covers up to 1+1 = 2       | 2                   |
| 2    | 2 ≤ 2+1 → true                | extend by 2 → covers up to 2+2 = 4       | 4                   |
| 3    | 3 ≤ 4+1 → true                | extend by 3 → covers up to 4+3 = 7       | 7                   |
| 5    | 5 ≤ 7+1 → true                | extend by 5 → covers up to 7+5 = 12      | 12                  |
| 7    | 7 ≤ 12+1 → true               | extend by 7 → covers up to 12+7 = 19     | 19                  |
| 22   | 22 ≤ 19+1 → **false** (22>20) | **stop** → smallest missing is 19+1 = 20 | —                   |

**Answer:** `20`

---

## Java Implementation

```java
package easy.arrays;

import java.util.Arrays;

public class NonConstructibleChange {

  /**
   * Returns the smallest amount of change that cannot be created
   * with any combination of the given positive coins.
   *
   * Time:  O(n log n)  — sorting dominates
   * Space: O(1)       — aside from the input array (sorting in-place)
   */
  public static int nonConstructibleChange(int[] coins) {
    // 1) Sort the coins
    Arrays.sort(coins);

    // 2) Track the maximum change we can create so far (1..currentChange)
    int currentChange = 0;

    // 3) Extend that range using each coin in order
    for (int coin : coins) {
      // If this coin is too large to bridge the next gap (currentChange+1),
      // we have found our smallest missing amount.
      if (coin > currentChange + 1) {
        return currentChange + 1;
      }
      // Otherwise, we extend our constructible range
      currentChange += coin;
    }

    // 4) If all coins are used without gap, the next missing is currentChange+1
    return currentChange + 1;
  }

  public static void main(String[] args) {
    int[] coins = {5, 7, 1, 1, 2, 3, 22};
    int answer = nonConstructibleChange(coins);
    System.out.println("Minimum non-constructible change: " + answer);
    // Expected output: 20
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * Sorting: **O(n log n)**
  * Single pass through the sorted array: **O(n)**
    → **Overall:** O(n log n)

* **Space Complexity:**

  * In-place sort (Java’s `Arrays.sort` on primitives uses quicksort variants): **O(1)** extra
  * You only use a handful of variables (`currentChange`, loop index).

This greedy algorithm is both simple to implement and optimal for this problem.
