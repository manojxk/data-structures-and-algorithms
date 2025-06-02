**Problem Restatement**
You have a staircase of a given `height` (number of steps). You can climb between 1 and `maxSteps` steps in a single move. In how many distinct ways can you reach the top (exactly `height` steps total)? For example, if `height = 4` and `maxSteps = 2`, you may climb 1 or 2 steps at a time. The valid ways to reach step 4 are:

1. 1, 1, 1, 1
2. 1, 1, 2
3. 1, 2, 1
4. 2, 1, 1
5. 2, 2

Thus, the answer is 5.

---

## 1. Brute-Force Recursive Solution

**Approach**

* At any position (remaining stairs = `h`), you try all possible next‐step choices from `1` to `maxSteps` (as long as `h – i >= 0`).
* Recurse on the smaller subproblem `h – i`.
* If you reach exactly `h == 0`, that counts as 1 valid way.

**Code**

```java
public static int countWaysBruteForce(int height, int maxSteps) {
  // Base case: exactly at top (one valid way)
  if (height == 0) return 1;

  int totalWays = 0;
  // Try taking i steps next, for i = 1..maxSteps
  for (int i = 1; i <= maxSteps; i++) {
    if (height - i >= 0) {
      totalWays += countWaysBruteForce(height - i, maxSteps);
    }
  }
  return totalWays;
}
```

**Complexity**

* **Time:** Exponential—approximately $O(\maxSteps^{\,height})$ in the worst case, because each call branches into up to `maxSteps` recursive calls.
* **Space:** $O(\text{height})$ due to recursion depth.

---

## 2. Optimized with Memoization

We can avoid recalculating the same subproblem many times by storing results in a hash map keyed by the remaining `height`.

**Approach**

* Use a helper `countWaysHelper(h, maxSteps, memo)` where `memo` maps an integer `h` to the number of ways to climb `h` steps.
* If a subproblem `h` has been solved, return `memo.get(h)` immediately.
* Otherwise compute it by summing all `countWaysHelper(h – i, maxSteps, memo)` for `i = 1..maxSteps` (when `h – i >= 0`), store in `memo`, and return.

**Code**

```java
import java.util.HashMap;

public static int countWaysMemoization(int height, int maxSteps) {
  HashMap<Integer, Integer> memo = new HashMap<>();
  return countWaysHelper(height, maxSteps, memo);
}

private static int countWaysHelper(int height, int maxSteps, HashMap<Integer, Integer> memo) {
  // Base case
  if (height == 0) return 1;
  // If we have computed this before, return it
  if (memo.containsKey(height)) return memo.get(height);

  int totalWays = 0;
  for (int i = 1; i <= maxSteps; i++) {
    if (height - i >= 0) {
      totalWays += countWaysHelper(height - i, maxSteps, memo);
    }
  }

  memo.put(height, totalWays);
  return totalWays;
}
```

**Complexity**

* **Time:** $O(\text{height} \times \maxSteps)$. Each `height` from 0..`height` is computed exactly once, and each time we loop up to `maxSteps`.
* **Space:** $O(\text{height})$ for the `memo` plus $O(\text{height})$ recursion depth in the worst case.

---

## 3. Bottom-Up Dynamic Programming

We build an array `dp` of size `height + 1`, where `dp[i]` = number of ways to reach exactly `i` steps.

**Approach**

1. Initialize `dp[0] = 1` (one way to “stand still” at the bottom).
2. For `i = 1` to `height`:

   * Let `dp[i] = 0` initially.
   * For each `j` from 1 to `maxSteps`, if `i - j >= 0`, add `dp[i - j]` to `dp[i]`.
   * This effectively says: to get to step `i`, your last jump could have been `j` steps from step `i - j`, and there are `dp[i - j]` ways to get to `i - j`.

At the end, `dp[height]` is the answer.

**Code**

```java
public static int countWaysDP(int height, int maxSteps) {
  int[] dp = new int[height + 1];
  dp[0] = 1; // One way to “stay” at step 0

  for (int i = 1; i <= height; i++) {
    dp[i] = 0;
    for (int j = 1; j <= maxSteps; j++) {
      if (i - j >= 0) {
        dp[i] += dp[i - j];
      }
    }
  }

  return dp[height];
}
```

**Complexity**

* **Time:** $O(\text{height} \times \maxSteps)$ (double loop).
* **Space:** $O(\text{height})$ for the `dp` array.

---

## Testing All Three Approaches

```java
public class StaircaseTraversal {

  public static void main(String[] args) {
    int height = 4;
    int maxSteps = 2;

    System.out.println("Brute Force:      " 
        + countWaysBruteForce(height, maxSteps));      // 5
    System.out.println("Memoization:      " 
        + countWaysMemoization(height, maxSteps));     // 5
    System.out.println("Dynamic Program:  " 
        + countWaysDP(height, maxSteps));              // 5
  }

  // 1) Brute Force
  public static int countWaysBruteForce(int height, int maxSteps) {
    if (height == 0) return 1;
    int totalWays = 0;
    for (int i = 1; i <= maxSteps; i++) {
      if (height - i >= 0) {
        totalWays += countWaysBruteForce(height - i, maxSteps);
      }
    }
    return totalWays;
  }

  // 2) Memoization
  public static int countWaysMemoization(int height, int maxSteps) {
    HashMap<Integer, Integer> memo = new HashMap<>();
    return countWaysHelper(height, maxSteps, memo);
  }

  private static int countWaysHelper(int height, int maxSteps, HashMap<Integer, Integer> memo) {
    if (height == 0) return 1;
    if (memo.containsKey(height)) return memo.get(height);

    int totalWays = 0;
    for (int i = 1; i <= maxSteps; i++) {
      if (height - i >= 0) {
        totalWays += countWaysHelper(height - i, maxSteps, memo);
      }
    }

    memo.put(height, totalWays);
    return totalWays;
  }

  // 3) Dynamic Programming
  public static int countWaysDP(int height, int maxSteps) {
    int[] dp = new int[height + 1];
    dp[0] = 1;

    for (int i = 1; i <= height; i++) {
      dp[i] = 0;
      for (int j = 1; j <= maxSteps; j++) {
        if (i - j >= 0) {
          dp[i] += dp[i - j];
        }
      }
    }
    return dp[height];
  }
}
```

All three methods print **5** for `height = 4, maxSteps = 2`.

---

### Summary

* **Brute Force (Recursive):**

  * Very simple to understand, but exponentially slow.
  * Time: $O(\maxSteps^{\,height})$, Space: $O(height)$.

* **Memoization (Top-Down DP):**

  * Caches subproblem results in a `HashMap`.
  * Time: $O(\text{height} \times \maxSteps)$, Space: $O(\text{height})$.

* **Bottom-Up DP:**

  * Builds a `dp[]` array from 0 up to `height`.
  * Time: $O(\text{height} \times \maxSteps)$, Space: $O(\text{height})$.

Use memoization or bottom-up DP for large `height` and `maxSteps`.
