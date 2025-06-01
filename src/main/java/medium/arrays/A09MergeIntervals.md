**Problem Explanation**
You are given a list of intervals, each interval represented by two integers $start, end$. Your task is to **merge all overlapping intervals** and return a list of disjoint intervals that cover exactly the same ranges as the input. Overlap means one interval’s start lies before another’s end, e.g.:

```
Input:  [[1, 3], [2, 6], [8, 10], [15, 18]]
Output: [[1, 6], [8, 10], [15, 18]]
```

Here, \[1,3] and \[2,6] overlap, so they merge into \[1,6]. The intervals \[8,10] and \[15,18] do not overlap anyone else, so they remain as-is.

---

## Approach: Sort + One-Pass Merge (O(n log n) Time | O(n) Space)

1. **Sort by start time.**
   First, sort all intervals in ascending order of their start times. This guarantees that if one interval can overlap with any later interval, it must overlap with the very next one whose start lies before or equal to its end. Sorting costs **O(n log n)** where *n* = number of intervals.

2. **Initialize a merged‐list.**
   Create a new list, say `merged`, and add the first (sorted) interval to it.

3. **Iterate through each remaining interval**
   For each subsequent interval `current`:

   * Let `last` = the last interval in `merged` (i.e., `merged.get(merged.size() - 1)`).
   * **If** `current.start` ≤ `last.end`, they overlap or touch. In that case:

     * Merge them by updating `last.end = max(last.end, current.end)`.
     * (We do *not* add a new interval to the list; we simply extend the existing one.)
   * **Else** (no overlap), append `current` to `merged` as a brand‐new disjoint interval.

4. **Return** the `merged` list.

Because we sorted first and then did a single pass merging, the total time is **O(n log n)** + **O(n) = O(n log n)**. The extra space (besides the input array) is just the output list, so **O(n)**.

---

## Detailed Steps

```java
public List<int[]> merge(int[][] intervals) {
  int n = intervals.length;
  if (n <= 1) {
    // If there is 0 or 1 interval, nothing to merge
    return Arrays.asList(intervals);
  }

  // 1) Sort intervals by start time (ascending)
  Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

  // 2) Prepare a list to hold merged intervals
  List<int[]> merged = new ArrayList<>();
  // Add the first interval to start
  merged.add(intervals[0]);

  // 3) Iterate from the 2nd interval onward
  for (int i = 1; i < n; i++) {
    int[] last = merged.get(merged.size() - 1);
    int[] current = intervals[i];

    // If current.start <= last.end, they overlap
    if (current[0] <= last[1]) {
      // Merge by extending last.end to max(last.end, current.end)
      last[1] = Math.max(last[1], current[1]);
      // (No need to add a new interval, since we updated “last” in place.)
    } else {
      // No overlap → add current as its own interval
      merged.add(current);
    }
  }

  return merged;
}
```

---

## Example Walkthrough

**Input**:

```
[[1, 3], [2, 6], [8, 10], [15, 18]]
```

1. **Sort by start** (already sorted in this example):

   ```
   [1,3], [2,6], [8,10], [15,18]
   ```

2. **Initialize** `merged = [[1,3]]`.

3. **i = 1**

   * `current = [2,6]`
   * `last = [1,3]`
   * Since `2 ≤ 3`, they overlap.
   * Update `last.end = max(3, 6) = 6`, so `last` becomes `[1,6]`.
   * `merged` is now `[[1,6]]`.

4. **i = 2**

   * `current = [8,10]`
   * `last = [1,6]`
   * Since `8 > 6`, no overlap.
   * Append `[8,10]` → `merged = [[1,6], [8,10]]`.

5. **i = 3**

   * `current = [15,18]`
   * `last = [8,10]`
   * Since `15 > 10`, no overlap.
   * Append `[15,18]` → `merged = [[1,6], [8,10], [15,18]]`.

Return `[[1,6], [8,10], [15,18]]`.

---

## Complete Java Class

```java
package medium.arrays;

import java.util.*;

public class A09MergeIntervals {
  /**
   * Merges all overlapping intervals in the given array.
   *
   * Time Complexity:  O(n log n)
   *   - Sorting takes O(n log n).
   *   - Single pass to merge takes O(n).
   * Space Complexity: O(n)
   *   - The output list may contain up to n intervals in the worst case.
   */
  public List<int[]> merge(int[][] intervals) {
    int n = intervals.length;
    if (n <= 1) {
      // Nothing to merge if there are 0 or 1 intervals
      return Arrays.asList(intervals);
    }

    // 1) Sort intervals by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    // 2) Use a list to accumulate merged intervals
    List<int[]> merged = new ArrayList<>();
    merged.add(intervals[0]);  // start with the first interval

    // 3) Iterate through all other intervals
    for (int i = 1; i < n; i++) {
      int[] last = merged.get(merged.size() - 1);
      int[] current = intervals[i];

      if (current[0] <= last[1]) {
        // Overlap detected → merge by extending the end
        last[1] = Math.max(last[1], current[1]);
      } else {
        // No overlap → simply add this interval
        merged.add(current);
      }
    }

    return merged;
  }

  public static void main(String[] args) {
    A09MergeIntervals solution = new A09MergeIntervals();

    int[][] intervals = { {1, 3}, {2, 6}, {8, 10}, {15, 18} };
    List<int[]> result = solution.merge(intervals);

    // Print the merged intervals
    System.out.println("Merged Intervals:");
    for (int[] interval : result) {
      System.out.println(Arrays.toString(interval));
    }
    // Expected:
    // [1, 6]
    // [8, 10]
    // [15, 18]
  }
}
```

**Key Points**

* Sorting by start time ensures that any overlap can only happen between adjacent (in the sorted order) intervals or intervals that have already been merged.
* By always “merging into” the last interval in our output list, we avoid creating many small intervals and keep everything in O(n log n) time.
* If the next interval does not overlap, we simply append it.
* If it does, we update the last interval’s end to the maximum end of the two, effectively combining them.
