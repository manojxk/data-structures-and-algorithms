**Problem Restatement**
You have an array of “measuring cups,” each described by a pair `[cupLow, cupHigh]` which represents that using that cup exactly once will add *some* amount between `cupLow` and `cupHigh` (inclusive) into your mixing bowl. You may reuse any cup arbitrarily many times. Given a target range `[low, high]`, determine whether it is possible to end up with a total measured volume somewhere in that inclusive range by choosing a (finite) combination of cups.

For example, if the available cups are `[[200,210], [450,465], [800,850]]` and your goal is any amount in `[2100,2300]`, you must decide if some multiset of these ranges can be “summed” to land anywhere between 2100 and 2300. In fact, one valid solution is to use three `800–850` cups (total range `[2400,2550]`) overshoots; instead use two `800–850` plus one `450–465`:

* Two “800–850” cups give you a range of `[1600,1700]`.
* One “450–465” cup gives you `[450,465]`.
* Summing these ranges gives you `[2050,2165]`, which does overlap `[2100,2300]`. Hence “true.”

We want a function that outputs `true` or `false` accordingly.

---

## High‐Level Approach: DFS + Memoization

1. **Reduce the Target Range**
   Suppose your current “target interval” is `[low, high]`. If you choose to use a particular cup `[cupLow, cupHigh]` once, then the remaining volume you still must measure is in the range

   $$
     [\,low - cupHigh \;,\;\; high - cupLow\,].
   $$

   Why?

   * If that cup actually contributed its **maximum** possible amount `cupHigh`, then you must still make at least `low - cupHigh` (because if you contributed `cupHigh`, you reduce the needed minimum by that much).
   * If that cup actually contributed its **minimum** possible amount `cupLow`, then you must still make at most `high - cupLow` (because you cannot exceed `high - cupLow` after having already poured in `cupLow`).
   * In other words, once you “commit” one use of `[cupLow, cupHigh]`, whatever net volume remains to be made must lie somewhere in `[low - cupHigh,\, high - cupLow]`.

   Thus, for each cup, you recurse on the new target interval `[low - cupHigh,\, high - cupLow]`.

2. **Base Cases**

   * If the resulting target interval ever straddles zero—i.e. `low ≤ 0 ≤ high`—you can stop and return `true`. That means “no matter how large or small the cup you just used ended up being (between its `cupLow` and `cupHigh`), you can cover zero remainder, so you already lie in the desired final range.”
   * If at any point `low > high`, that interval is invalid (it represents an impossible range of volumes), so return `false`.

3. **Memoization**
   Because `low` and `high` may take on many possible integer values as you recurse (and you can loop through the same pair `[low, high]` from different cup‐choices), you want to avoid recomputing identical subproblems. Store each computed boolean result in a map keyed by `(low,high)`. If you revisit the same `(low,high)`, return the memoized answer immediately.

4. **Depth‐First Search**

   * Start from the original `[low, high]`.
   * For each cup in `measuringCups`, derive the next interval and call DFS recursively on it.
   * If *any* branch returns `true`, you store and bubble up `true`.
   * If you exhaust all cups without success, store and return `false`.

Because we reduce both `low` and `high` by at least `min(cupLow)` and `min(cupHigh)` each step, the intervals shrink (eventually pushing `high` below zero or `low` above `high`), guaranteeing termination for integer cups and targets.

---

## Detailed Code Walkthrough

```java
package hard.recursion;

import java.util.*;

public class MeasuringCups {

  /**
   * Entry point: returns true if you can measure some total volume in [low..high], 
   * using any combination of cups (each cup used 0 or more times). 
   */
  public static boolean canMeasureInRange(int[][] measuringCups, int low, int high) {
    // We will memoize on (low, high) pairs to avoid repeated work.
    Map<String, Boolean> memo = new HashMap<>();
    return canMeasureInRangeHelper(measuringCups, low, high, memo);
  }

  /**
   * Recursive helper:
   * - `low`, `high` is the remaining range we need to fulfill.
   * - If low ≤ 0 ≤ high, we have overlapped the zero point → success.
   * - If low > high, that range is empty → failure.
   * - Otherwise, try each cup [cupLow, cupHigh] once and recurse on the shrunk interval.
   */
  private static boolean canMeasureInRangeHelper(
      int[][] measuringCups, int low, int high, Map<String, Boolean> memo) {

    // 1) Build a memo key string "low:high"
    String key = low + ":" + high;
    if (memo.containsKey(key)) {
      return memo.get(key);
    }

    // 2) Base‐case: if [low..high] includes 0, we are done
    if (low <= 0 && high >= 0) {
      memo.put(key, true);
      return true;
    }

    // 3) Invalid interval → we overshot
    if (low > high) {
      memo.put(key, false);
      return false;
    }

    // 4) Try each cup once
    for (int[] cup : measuringCups) {
      int cupLow  = cup[0];
      int cupHigh = cup[1];
      // Subtract the cup’s extreme possibilities from [low..high]:
      // NewLow = low  - cupHigh   (if we used the cup at its maximum)
      // NewHigh = high - cupLow   (if we used the cup at its minimum)
      int nextLow  = low  - cupHigh;
      int nextHigh = high - cupLow;

      if (canMeasureInRangeHelper(measuringCups, nextLow, nextHigh, memo)) {
        memo.put(key, true);
        return true;
      }
    }

    // 5) No cup choice leads to success
    memo.put(key, false);
    return false;
  }

  // Example test driver
  public static void main(String[] args) {
    int[][] measuringCups = {
      {200, 210},
      {450, 465},
      {800, 850}
    };
    int low = 2100;
    int high = 2300;

    boolean result = canMeasureInRange(measuringCups, low, high);
    System.out.println("Can measure in range [" + low + ", " + high + "]? " + result);
    // Expected: true
  }
}
```

---

## Why This Correctly Captures the Problem

1. **Representing “Any Use of a Cup”**

   * If a cup can deliver anywhere from `cupLow` up to `cupHigh` volume, you don’t have to pick a single exact number at that moment. Instead, you account for the *worst‐case overlap* on the interval.
   * After using one cup, the remainder you need to measure must fall in the intersection of all ranges that cup could produce. That intersection is exactly `[low − cupHigh,  high − cupLow]`.

2. **Base Case (low ≤ 0 ≤ high)**

   * If your remaining interval includes zero, it means “no matter what exact amount (between the min and max of the last cup) was actually added, the total so far lies somewhere in `[originalLow..originalHigh]`.” Thus you can stop and return `true`.

3. **Pruning Invalid Intervals (low > high)**

   * If at any point `low > high`, there is no way to pick an actual number from `[low..high]` (because it’s reversed). That branch cannot succeed, so return `false`.

4. **Memoization Key**

   * We use a String key: `low + ":" + high`. Since `low` and `high` can each be positive or negative integers, this uniquely identifies a subproblem.
   * Every time we finish processing `[low..high]`, we store `memo.put(key, booleanResult)`.
   * On re‐encounter, we immediately return the stored result, avoiding exponential blow‐up.

---

## Example Execution (Simplified)

Take the sample:

```
measuringCups = [ [200,210], [450,465], [800,850] ]
low = 2100,  high = 2300
```

1. Call `canMeasureInRangeHelper(cups, 2100, 2300)`.

   * Neither `2100 ≤ 0` nor `2100 > 2300`, so we try each cup in turn:

2. Using `[200,210]` first:

   * We go to `nextLow = 2100 − 210 = 1890`
   * `nextHigh = 2300 − 200 = 2100`
     so call `canMeasureInRangeHelper(cups, 1890, 2100)`.

3. That in turn tries:

   * Cup `[200,210]` again:
     `nextLow = 1890 − 210 = 1680, nextHigh = 2100 − 200 = 1900` → recursive, and so on.
   * Eventually we can add `[800,850]` twice, plus `[450,465]` once, etc., until we produce a sub‐interval that straddles zero.

   One particular successful chain is:

   * Subtract `[800,850]` → from `[2100..2300]` to `[2100−850, 2300−800] = [1250, 1500]`
   * Subtract `[800,850]` again → `[1250−850, 1500−800] = [400, 700]`
   * Subtract `[450,465]` → `[400−465, 700−450] = [−65, 250]`

   Now `[−65..250]` includes zero, so we return `true`.

Because we memoize each visited `[low..high]`, we never re‐explore the exact same subproblem twice. As soon as one chain yields zero, everything unwinds and we return `true`.

---

## Complexity Analysis

* **Time Complexity**

  * Let *n* be the number of cup‐types.
  * In the worst case, `low` and `high` will range downwards from the original target until `low > high` or you hit a zero‐overlap. Each unique `(low, high)` pair can be visited at most once because of memoization.
  * Every visit tries up to *n* cups.
  * If the numerical difference `(originalHigh − originalLow)` is roughly *D*, then each recursive step typically reduces both endpoints by at least the smallest `cupLow`, so the number of distinct `(low, high)` states is bounded by roughly *D* (scaled by that minimum cup size). In other words, you cannot visit more than O(*D*) distinct intervals, and for each you do O(*n*) work.
  * Hence the total time is about **O(n × D)**, where `D = (originalHigh − originalLow)` (plus some small overhead).

* **Space Complexity**

  * **Memo table** stores up to O(*D*) distinct string keys.
  * The recursion stack depth can be at most O(*D*) in the worst case (each step lowers `high` by at least one).
  * Therefore, overall space is **O(D)** (plus the output of memo entries).

Because both *D* and *n* can be large, in practice you rely on the fact that using various cups quickly drives the interval to overlap zero or become invalid—pruning a large portion of the search.

---

## Summary

1. We reformulate “can we measure something in `[low..high]`?” as:

   * Pick one cup `[cupLow, cupHigh]` → the remainder must lie in `[ low − cupHigh  ,  high − cupLow ]`.
2. We recursively check whether that new remainder‐interval is solvable.
3. We use two base checks:

   * If `low ≤ 0 ≤ high`, we have succeeded (true).
   * If `low > high`, that’s impossible (false).
4. We memoize `(low, high)` so we never re‐solve the same subproblem twice.
5. If any cup leads to a “true” recursive call, we store and return `true`. Otherwise, return `false`.

This yields an elegant DFS + memoization approach that solves the “Measuring Cups” problem in roughly **O(n × (high−low))** time and **O(high−low)** space.
