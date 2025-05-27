**Problem Explanation**

You have a list of task **durations** (positive integers). You’ll execute the tasks **one after another**, and:

* The **waiting time** for a given task is the **sum of durations of all tasks before it**.
* Your goal is to **order** the tasks so that the **total waiting time** (sum of each task’s waiting time) is as **small** as possible.

---

## Brute-Force (Not Practical)

You could try **every permutation** of tasks, compute the total waiting time for each, and pick the minimum—but there are $n!$ permutations, which explodes even for modest $n$. This approach is only for understanding, not for real use.

---

## Greedy Insight

To **minimize** total waiting time, you want **short** tasks to happen **earlier**, so that **many** later tasks benefit from a small wait. Concretely:

1. **Sort** the durations in **ascending** order.
2. Execute them in that order.

This guarantees the smallest tasks add their durations to as few subsequent tasks as possible, while large tasks (which contribute more waiting time each) appear later and thus contribute to fewer waiting times.

---

## Solution Steps

1. **Sort** the `durations` array:

   ```java
   Arrays.sort(durations);
   ```
2. Initialize `totalWaitingTime = 0` and let `n = durations.length`.
3. For each task at index `i` (0-based):

   * There are $n - (i+1)$ tasks **after** it.
   * So its duration contributes to **each** of those tasks’ waiting time.
   * **Add** `(n - (i+1)) * durations[i]` to `totalWaitingTime`.
4. **Return** `totalWaitingTime`.

---

## Java Implementation

```java
package easy.greedyalgorithm;

import java.util.Arrays;

public class A01MinimumWaitingTime {

  /**
   * Calculates the minimum total waiting time by sorting tasks
   * so shorter ones happen first.
   *
   * Time:  O(n log n) -- sorting dominates
   * Space: O(1)      -- in-place sort, constant extra variables
   */
  public int calculateMinimumWaitingTime(int[] durations) {
    Arrays.sort(durations);  // Step 1

    int totalWaitingTime = 0;
    int n = durations.length;

    // Step 2 & 3: accumulate each task's contribution
    for (int i = 0; i < n; i++) {
      int tasksRemaining = n - (i + 1);
      totalWaitingTime += tasksRemaining * durations[i];
    }

    return totalWaitingTime; // Step 4
  }

  public static void main(String[] args) {
    A01MinimumWaitingTime calc = new A01MinimumWaitingTime();
    int[] durations = {3, 2, 1};

    int result = calc.calculateMinimumWaitingTime(durations);
    System.out.println("Minimum Waiting Time: " + result);
    // Explanation:
    // Sorted → [1,2,3]
    // Waiting times: 1 contributes to two tasks → 2*1 = 2
    //                2 contributes to one task  → 1*2 = 2
    //                3 contributes to zero tasks → 0*3 = 0
    // Total = 2 + 2 + 0 = 4
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * Sorting: **O(n log n)**
  * Summation loop: **O(n)**
    → **Overall:** O(n log n)

* **Space Complexity:**

  * Sorting in-place uses **O(1)** extra space.
  * A few integer variables → **O(1)**.

This greedy solution is both **simple** and **optimal** for minimizing total waiting time.
