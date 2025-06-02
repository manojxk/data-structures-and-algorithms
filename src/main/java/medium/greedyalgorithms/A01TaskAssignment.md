**Problem Restatement**
You have an array of task durations (each a positive integer). You must assign exactly two tasks to each worker. Your goal is to minimize the **maximum** total duration assigned to any worker. Return a list of index‐pairs $\[i₁,j₁$, $i₂,j₂$, …] showing which two tasks go together.

> **Example**
>
> ```
> tasks = [1, 3, 5, 3, 1, 4]
>
> One optimal assignment:
>   Worker A: tasks[0]=1  + tasks[2]=5  → total 6
>   Worker B: tasks[4]=1  + tasks[5]=4  → total 5
>   Worker C: tasks[1]=3  + tasks[3]=3  → total 6
>
> Max working time = 6 (which is minimal possible).
> Output pairs (in any order): [[0,2], [4,5], [1,3]]
> ```

---

## Approach: Greedy Pairing by Sorting

1. **Sort tasks by duration** (but keep track of original indices).
2. **Pair the smallest‐duration task** with the **largest‐duration task**. This “longest + shortest” pairing minimizes the load’s maximum.
3. Continue inward: 2nd smallest with 2nd largest, 3rd smallest with 3rd largest, etc.
4. Each pair’s indices come from the original array.

Why this works: Pairing extremes “evens out” the sums. If you paired two large tasks together, their sum would be very large; instead, pairing each large with a small keeps every worker’s total as close as possible to the overall average.

---

## Steps

1. **Build an index array**

   * Create an `Integer[] indices = new Integer[n]` where `indices[i] = i`.
   * This array will let us sort tasks by ascending durations while still remembering original positions.

2. **Sort `indices` by `tasks[indices[i]]`**

   ```java
   Arrays.sort(indices, (a, b) -> tasks[a] - tasks[b]);
   ```

   * Now `indices[0]` points to the index of the smallest‐duration task, `indices[n−1]` points to the largest, etc.

3. **Two‐Pointer Pairing**

   * Set `left = 0`, `right = n−1`.
   * While `left < right`, form a pair:

     ```java
     taskPairs.add(new int[]{ indices[left], indices[right] });
     left++;
     right--;
     ```
   * This pairs the smallest remaining with the largest remaining, then moves inward.

4. **Return** the list of index‐pairs.

The time cost is dominated by sorting: **O(n log n)**. We only allocate an auxiliary index array of length *n*, so space is **O(n)**.

---

## Complete Java Code

```java
package medium.greedyalgorithms;

import java.util.*;

public class A01TaskAssignment {

  /**
   * Assigns tasks in pairs to minimize the maximum total duration per pair.
   *
   * @param tasks Array of task durations.
   * @return A list of integer‐pairs {i, j}, each representing two tasks assigned to one worker.
   */
  public static List<int[]> assignTasks(int[] tasks) {
    int n = tasks.length;
    List<int[]> taskPairs = new ArrayList<>();

    // 1) Build an index array to remember original positions
    Integer[] indices = new Integer[n];
    for (int i = 0; i < n; i++) {
      indices[i] = i;
    }

    // 2) Sort indices by ascending task duration
    Arrays.sort(indices, (a, b) -> tasks[a] - tasks[b]);

    // 3) Pair smallest with largest, then move inward
    int left = 0, right = n - 1;
    while (left < right) {
      taskPairs.add(new int[]{ indices[left], indices[right] });
      left++;
      right--;
    }

    return taskPairs;
  }

  public static void main(String[] args) {
    int[] tasks = {1, 3, 5, 3, 1, 4};

    List<int[]> taskPairs = assignTasks(tasks);
    System.out.println("Task Assignments (pairs of indices):");
    for (int[] pair : taskPairs) {
      System.out.println(Arrays.toString(pair));
    }
  }
}
```

**Explanation of Key Points**

* We never physically reorder the `tasks` array; we only sort the `indices` array by comparing durations.
* Each time we take `indices[left]` and `indices[right]`, we fetch the original indices of the shortest and longest remaining tasks.
* Moving inward guarantees every worker’s sum is as balanced as possible, minimizing the maximum workload.

This completes the greedy pairing solution in **O(n log n)** time and **O(n)** extra space.
