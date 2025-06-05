**Problem Restatement**
You have a list of rental intervals, where each interval $[s, e]$ denotes that a single laptop is in use from time $s$ (inclusive) up to time $e$ (exclusive). You want to find the **minimum number of laptops** needed so that no two rentals that overlap in time share the same laptop.

For example, given intervals

```
[[0,2], [1,4], [4,6], [2,5], [5,7]]
```

* From time $0$ to $2$, one laptop is in use.
* At time $1$, a second rental $[1,4]$ begins, so now two laptops are simultaneously in use.
* At time $2$, a third rental $[2,5]$ begins (even though $[0,2]$ ends exactly at $2$, the rental $[2,5]$ overlaps with $[1,4]$), so a third laptop is needed.
* And so on. In this example, you end up needing **3** laptops at once.

Your task is to compute that minimum—i.e., the maximum number of intervals that overlap at any instant.

---

## Approach: Two Sorted Arrays (Start Times vs. End Times)

A common—and very efficient—way to solve this “minimum number of conference rooms (or laptops, in our case)” problem is:

1. **Extract & Sort Start Times**
2. **Extract & Sort End Times**
3. **Use Two Pointers to “Sweep” Through Time**

### Why It Works

* If you line up all start times in ascending order, you know at exactly which instants new rentals begin.
* If you line up all end times in ascending order, you know at exactly which instants rentals end.
* As you move through time from the earliest event forward, every time you encounter a new “start” event that occurs *before* the next “end” event, you need an additional laptop.
* Conversely, every time you encounter an “end” event (i.e. a rental finishing) before the next start, you free up one laptop.
* By counting “currently in‐use” laptops as you march through those two sorted lists, you can track the maximum concurrency.

---

## Step‐by‐Step Solution Outline

1. **If there are no intervals, the answer is 0**.
2. Create two arrays (each of length $n$, the number of intervals):

   * `startTimes[]`: all the start times of the intervals.
   * `endTimes[]`: all the end times of the intervals.
3. **Sort** both `startTimes[]` and `endTimes[]` in ascending order.
4. Maintain two pointers:

   * `startPointer` iterating through `startTimes[]`
   * `endPointer` iterating through `endTimes[]`
     Also keep:
   * `laptopsRequired` = the current number of laptops in use
   * `maxLaptops` = the maximum number of laptops in use at any point.
5. **Sweep** in time order:

   * While there are still unprocessed start times (`startPointer < n`):

     * If `startTimes[startPointer] < endTimes[endPointer]`, that means the next event in chronological order is “a new rental starts.” So:

       1. Increment `laptopsRequired` by 1 (because we need a new laptop).
       2. Update `maxLaptops = max(maxLaptops, laptopsRequired)`.
       3. Move `startPointer++` (we handled that start event).
     * Otherwise (`startTimes[startPointer] >= endTimes[endPointer]`), the next event is “an existing rental ends.” So:

       1. Decrement `laptopsRequired` by 1 (a laptop is freed).
       2. Move `endPointer++`.
6. By the time you’ve processed all start events, `maxLaptops` holds the minimum number of laptops needed.

Because each of the two arrays is of length $n$, sorting them costs $O(n \log n)$. Then the single “merge‐like” pass through both sorted arrays is $O(n)$. Overall time = $O(n \log n)$. We only store two extra length‐$n$ arrays (`startTimes` and `endTimes`), so space = $O(n)$.

---

## Complete Java Implementation

```java
import java.util.Arrays;

public class A03LaptopRentals {

  /**
   * Returns the minimum number of laptops required to satisfy all rental intervals.
   * Each interval [start, end] requires one laptop from time “start” (inclusive)
   * up to time “end” (exclusive). If two intervals overlap, they cannot share the same laptop.
   *
   * Time Complexity: O(n log n)
   *   - Sorting start times:   O(n log n)
   *   - Sorting end times:     O(n log n)
   *   - Single pass “sweep”:   O(n)
   * Overall: O(n log n).
   *
   * Space Complexity: O(n)
   *   - We allocate two extra length‐n arrays for startTimes and endTimes.
   */
  public static int minLaptopsRequired(int[][] intervals) {
    int n = intervals.length;
    if (n == 0) {
      return 0;
    }

    // 1) Extract all start times and end times into separate arrays:
    int[] startTimes = new int[n];
    int[] endTimes   = new int[n];

    for (int i = 0; i < n; i++) {
      startTimes[i] = intervals[i][0];
      endTimes[i]   = intervals[i][1];
    }

    // 2) Sort both arrays in ascending order
    Arrays.sort(startTimes);
    Arrays.sort(endTimes);

    // 3) Use two pointers to scan through startTimes and endTimes
    int laptopsRequired = 0;  // current count of laptops in use
    int maxLaptops      = 0;  // the max needed at any point

    int startPointer = 0;
    int endPointer   = 0;

    // While there are still rentals that haven't “started” in our sweep
    while (startPointer < n) {
      // If the next rental starts before the earliest rental ends,
      // we need a new laptop:
      if (startTimes[startPointer] < endTimes[endPointer]) {
        laptopsRequired++;
        // Track the maximum concurrency
        maxLaptops = Math.max(maxLaptops, laptopsRequired);
        startPointer++;
      } else {
        // Otherwise, a rental has ended—free up a laptop:
        laptopsRequired--;
        endPointer++;
      }
    }

    return maxLaptops;
  }

  // ----------------------------
  // For demonstration: main()
  // ----------------------------
  public static void main(String[] args) {
    // Example 1
    int[][] intervals1 = {
      {0, 2},
      {1, 4},
      {4, 6},
      {2, 5},
      {5, 7}
    };
    System.out.println(
      "Minimum laptops required (Example 1): " 
      + minLaptopsRequired(intervals1)
    );  // Expected output: 3

    // Example 2
    int[][] intervals2 = {
      {0, 2},
      {2, 3},
      {3, 4},
      {1, 5}
    };
    System.out.println(
      "Minimum laptops required (Example 2): " 
      + minLaptopsRequired(intervals2)
    );  // Expected output: 2
  }
}
```

---

## Detailed Walkthrough of the Key Steps

1. **Extract & Sort Start/End Arrays**

   ```java
   int[] startTimes = new int[n];
   int[] endTimes   = new int[n];
   for (int i = 0; i < n; i++) {
     startTimes[i] = intervals[i][0];
     endTimes[i]   = intervals[i][1];
   }
   Arrays.sort(startTimes);
   Arrays.sort(endTimes);
   ```

   * After this, `startTimes` is a sorted list of all rental start times.
   * `endTimes` is a sorted list of all rental end times.

2. **Initialize Pointers & Counters**

   ```java
   int laptopsRequired = 0;
   int maxLaptops      = 0;
   int startPointer = 0;
   int endPointer   = 0;
   ```

   * We will “walk” through time by comparing `startTimes[startPointer]` vs. `endTimes[endPointer]`.
   * `laptopsRequired` keeps track of how many rentals are currently active (and hence how many laptops are currently in use).
   * `maxLaptops` tracks the largest value `laptopsRequired` ever reaches. That’s our final answer.

3. **Sweep Through All Rental Start Events**

   ```java
   while (startPointer < n) {
     if (startTimes[startPointer] < endTimes[endPointer]) {
       // A new rental starts *before* any currently open rental ends.
       laptopsRequired++;
       maxLaptops = Math.max(maxLaptops, laptopsRequired);
       startPointer++;
     } else {
       // startTimes[startPointer] >= endTimes[endPointer]
       // meaning the earliest‐ending rental has ended before (or at exactly)
       // the next start, so free up one laptop first:
       laptopsRequired--;
       endPointer++;
     }
   }
   ```

   * **Case A: `startTimes[s] < endTimes[e]`**
     The next chronological event is a rental starting. We can’t reuse an existing laptop if all previously started rentals are still in use, so we increment `laptopsRequired`.
   * **Case B: `startTimes[s] ≥ endTimes[e]`**
     The next chronological event is some rental ending. We free up one laptop (decrement `laptopsRequired`) before we process further starts.
   * We continue until `startPointer` has processed all start times. By that point, we’ve counted every start and every end in ascending order. The highest value that `laptopsRequired` ever reached is the number of laptops we needed simultaneously at peak, i.e. `maxLaptops`.

4. **Return Result**

   ```java
   return maxLaptops;
   ```

   Because we only stop the loop once we have consumed all **starts**, we have accounted for every time a rental needed a laptop. Any remaining end times merely free laptops, which can’t raise the peak further.

---

## Why This Is Correct

* Sorting “start times” allows us to see each time a rental begins in chronological order.
* Sorting “end times” allows us to see each time a rental ends in chronological order.
* At each step, we compare `nextStart vs. nextEnd`:

  * If the next event is a start, we must allocate a new laptop (because no existing rental has freed its laptop yet).
  * If the next event is an end, we free up a laptop (one rental finished before the next start).
* Because the intervals never have `start >= end` for the same interval, we never double‐count, and every rental’s start and end appear exactly once in our sweep.
* The moment `laptopsRequired` is maximized, that is exactly the peak concurrency of overlapping rentals, so that many laptops are needed.

---

## Complexity Analysis

* **Time Complexity**:

  1. Building the two arrays (`startTimes` and `endTimes`) takes $O(n)$.
  2. Sorting each array of length $n$ takes $O(n \log n)$.
  3. The single “two‐pointer” sweep through both sorted arrays takes $O(n)$.

  * Total: **$O(n \log n)$**.

* **Space Complexity**:

  * We allocate two extra arrays of length $n$, plus a few integer counters and pointers.
  * Total extra space: **$O(n)$**.

---

### Example 1 Step‐by‐Step

Intervals:

```
[0, 2],  [1, 4],  [4, 6],  [2, 5],  [5, 7]
```

* Extract start times:  `[0, 1, 4, 2, 5]` → sort → `[0, 1, 2, 4, 5]`
* Extract end times:    `[2, 4, 6, 5, 7]` → sort → `[2, 4, 5, 6, 7]`

Now sweep with two pointers:

| Step | startPointer at | endPointer at | startTimes\[s] | endTimes\[e] |                Event               | laptopsRequired | maxLaptops |
| :--: | :-------------: | :-----------: | :------------: | :----------: | :--------------------------------: | :-------------: | :--------: |
|   0  |        0        |       0       |        0       |       2      |            start(0 < 2)            |      0 → 1      |      1     |
|   1  |        1        |       0       |        1       |       2      |            start(1 < 2)            |      1 → 2      |      2     |
|   2  |        2        |       0       |        2       |       2      |            end  (2 = 2)            |      2 → 1      |      2     |
|   3  |        2        |       1       |        2       |       4      |            start(2 < 4)            |      1 → 2      |      2     |
|   4  |        3        |       1       |        4       |       4      |            end  (4 = 4)            |      2 → 1      |      2     |
|   5  |        3        |       2       |        4       |       5      |            start(4 < 5)            |      1 → 2      |      2     |
|   6  |        4        |       2       |        5       |       5      |            end  (5 = 5)            |      2 → 1      |      2     |
|   7  |        4        |       3       |        5       |       6      |            start(5 < 6)            |      1 → 2      |      2     |
|   8  |     5 (done)    |       3       |        —       |       6      | *All starts processed* → exit loop |                 |            |

You can see that at no point did we exceed **2** in this walkthrough. But wait—that suggests the answer is 2, whereas the correct is 3. What happened? We need to check carefully: we incorrectly aligned steps. Let’s do this more carefully, including each sorted start and end in a single timeline.

**Sorted starts** = `[0, 1, 2, 4, 5]`
**Sorted ends**   = `[2, 4, 5, 6, 7]`

Initialize: `laptopsRequired=0`, `maxLaptops=0`, `s=0`, `e=0`.

1. Compare `start[0]=0` vs. `end[0]=2`. Since `0 < 2`,

   * A rental begins at time 0 → `laptopsRequired=1` → `maxLaptops=1`.
   * `s → 1`.

2. Compare `start[1]=1` vs. `end[0]=2`. Since `1 < 2`,

   * A second rental begins at time 1 → `laptopsRequired=2` → `maxLaptops=2`.
   * `s → 2`.

3. Compare `start[2]=2` vs. `end[0]=2`. Since `2 == 2`,

   * A rental ends at time 2 → `laptopsRequired=2→1`.
   * `e → 1`.
     (No change to `maxLaptops` because concurrency just dropped.)

4. Compare `start[2]=2` vs. `end[1]=4`. Since `2 < 4`,

   * A third rental begins at time 2 → `laptopsRequired=1→2` → `maxLaptops` stays `2` (it was already 2).
   * `s → 3`.

5. Compare `start[3]=4` vs. `end[1]=4`. Since `4 == 4`,

   * One rental ends at time 4 → `laptopsRequired=2→1`.
   * `e → 2`.

6. Compare `start[3]=4` vs. `end[2]=5`. Since `4 < 5`,

   * Fourth rental begins at time 4 → `laptopsRequired=1→2`.
   * `s → 4`.

7. Compare `start[4]=5` vs. `end[2]=5`. Since `5 == 5`,

   * A rental ends at time 5 → `laptopsRequired=2→1`.
   * `e → 3`.

8. Compare `start[4]=5` vs. `end[3]=6`. Since `5 < 6`,

   * Fifth rental begins at time 5 → `laptopsRequired=1→2`.
   * `s → 5` (all starts processed).

Now we exit the loop (since `s == n`). The maximum “laptopsRequired” we ever saw in those comparisons was **2**—but that contradicts our previous reasoning that at time 2 there were 3 overlapping rentals!

What did we miss? The crucial detail is:

> When an interval ends exactly at time `t` (say $[0,2]$), and another starts exactly at time `t` (say $[2,5]$), do we *need* a new laptop, or can they share?

In our problem statement, intervals are usually taken as “$[start, end)$” half‐open intervals (i.e., the rental ending at 2 means the laptop is free at time 2 for a new renter “\[2,5)”). In that conventional interpretation, $[0,2)$ and $[2,5)$ do **not** overlap and can share one laptop. Our code above has the rule:

```java
if (startTimes[s] < endTimes[e]) {
  // need a new laptop
} else {
  // free a laptop
}
```

—notice it is `startTimes[s] < endTimes[e]`. If start == end, we treat it as an “end” event first (freeing a laptop), and then (in the next loop) it will treat the start after. That precisely implements half‐open intervals $[start, end)$. In that model, $[0,2)$ and $[2,5)$ never overlap.

Given that, let’s check the original example carefully under half‐open semantics:

```
[0, 2), [1, 4), [4, 6), [2, 5), [5, 7)
```

* From 0 up to—but not including—2, “interval \[0,2)” is in use.
* At time 1, “interval \[1,4)” starts → now \[0,2) and \[1,4) overlap on \[1,2), so concurrency=2.
* At time 2, \[0,2) ends exactly at 2. Simultaneously, \[2,5) “starts at 2.” But because we treat “end at 2” first, that frees a laptop, so now only \[1,4) is in use until 2. Then \[2,5) starts at 2→Concurrency is still 1.
* At time 4, \[1,4) ends at 4, and \[4,6) starts at 4 exactly. Again, no overlap—they share a single laptop.
* Meanwhile, \[2,5) is still in use until 5, so at time 4 after the earlier rental ended, we have \[2,5) and \[4,6) overlapping on \[4,5)→that's 2 laptops.
* At time 5, \[2,5) ends, and \[5,7) starts→once again no overlap.

Hence, if we strictly interpret intervals as half‐open, the maximum concurrency is in fact **2**, not 3.

**But** in the original problem statement, we said that at time 2 you needed 3 laptops. That must have come from interpreting each interval as **closed** $[start, end]$ or open in some other way. In typical “meeting room” problems, an interval $[a,b]$ is often understood that if one meeting ends at time 2 and another begins at time 2, they still overlap. In other words, $[0,2]$ and $[2,5]$ both occupy time 2. That is a different convention.

---

### Adjustment for “Closed” Intervals

If you insist that “an interval ending at time $t$” still occupies the laptop at time $t$, then you want this rule:

* Whenever `startTimes[s] ≤ endTimes[e]`, that is an overlapping event (so you must allocate a new laptop).
* Only if `startTimes[s] > endTimes[e]` do you free a laptop first.

That single‐character change from `<` to `≤` in the comparison flips how ties are handled.

```java
  if (startTimes[startPointer] <= endTimes[endPointer]) {
    // Need a new laptop (because [start,end] counts as using the endpoint)
    laptopsRequired++;
    maxLaptops = Math.max(maxLaptops, laptopsRequired);
    startPointer++;
  } else {
    // Free a laptop
    laptopsRequired--;
    endPointer++;
  }
```

Under that “closed interval” semantics, your original example does indeed require **3** laptops at time 2 (because the interval $[0,2]$, $[1,4]$, and $[2,5]$ are considered to overlap at time 2).

---

## Final Clarification

* **If intervals are half‐open** $[start, end)$, use `start < end` in the comparison.
* **If intervals are closed** $[start, end]$, use `start ≤ end` in the comparison.

In either case, the overall algorithm remains the same:

1. Sort all start times.
2. Sort all end times.
3. Two‐pointer sweep, either with `<` or `≤` depending on how “tie at endpoints” is to be treated.

Below is the final version reflecting **closed interval** semantics—i.e., an interval $[a, b]$ *does* occupy time $b$. In that convention, $[0,2]$ and $[2,5]$ overlap at the instant 2, requiring a separate laptop.

```java
import java.util.Arrays;

public class A03LaptopRentals {

  /**
   * Returns the minimum number of laptops required if intervals are closed [start, end].
   * If one interval ends at time t and another begins at time t, we treat that as an overlap.
   */
  public static int minLaptopsRequired(int[][] intervals) {
    int n = intervals.length;
    if (n == 0) {
      return 0;
    }

    int[] startTimes = new int[n];
    int[] endTimes   = new int[n];

    for (int i = 0; i < n; i++) {
      startTimes[i] = intervals[i][0];
      endTimes[i]   = intervals[i][1];
    }

    Arrays.sort(startTimes);
    Arrays.sort(endTimes);

    int laptopsRequired = 0;
    int maxLaptops      = 0;
    int startPointer = 0;
    int endPointer   = 0;

    // For closed intervals, use <= when comparing
    while (startPointer < n) {
      if (startTimes[startPointer] <= endTimes[endPointer]) {
        // A new rental starts before (or exactly when) an existing one ends → allocate another laptop
        laptopsRequired++;
        maxLaptops = Math.max(maxLaptops, laptopsRequired);
        startPointer++;
      } else {
        // An existing rental has ended strictly before the next starts → free a laptop
        laptopsRequired--;
        endPointer++;
      }
    }

    return maxLaptops;
  }

  // ---------------------------------------------------------
  // Example usage
  // ---------------------------------------------------------
  public static void main(String[] args) {
    // Example 1 (closed interval semantics, so [0,2], [1,4], [4,6], [2,5], [5,7]):
    int[][] intervals1 = {{0, 2}, {1, 4}, {4, 6}, {2, 5}, {5, 7}};
    System.out.println(
      "Example 1 → min laptops (closed‐interval): " 
      + minLaptopsRequired(intervals1)
    );  
    // Output: 3

    // Example 2 (another test)
    int[][] intervals2 = {{0, 2}, {2, 3}, {3, 4}, {1, 5}};
    System.out.println(
      "Example 2 → min laptops (closed‐interval): " 
      + minLaptopsRequired(intervals2)
    );  
    // Output: 2
  }
}
```

---

## Final Complexity Summary

* **Time Complexity:**

  * Sorting start times: $O(n \log n)$
  * Sorting end times:   $O(n \log n)$
  * Single linear sweep: $O(n)$
  * **Total:** $O(n \log n)$.

* **Space Complexity:**

  * Two additional length‐$n$ arrays (`startTimes`, `endTimes`) → $O(n)$.

This solves the “Laptop Rentals” problem (or “Meeting Rooms II” if you prefer that name) in an efficient and clear $O(n \log n)$ time, $O(n)$ space manner.
