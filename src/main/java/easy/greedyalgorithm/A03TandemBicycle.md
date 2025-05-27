**Problem Explanation**

You have two groups of riders, **Group A** and **Group B**, each with the same number of members. Each rider has a fixed **speed**. You’ll create **tandem bicycles**, each ridden by exactly two people—one from Group A and one from Group B. The speed of each tandem is the **maximum** of its two riders’ speeds (because the faster rider sets the pace).

Your task is to pair riders to achieve either:

* **Maximum total speed** across all tandems, or
* **Minimum total speed** across all tandems,

depending on a boolean flag `maximizeSpeed`.

---

## Key Insight (Greedy)

1. **Sorting** helps you make optimal pairings by aligning riders in order of speed.
2. For **maximum** total speed, you want your **fastest** riders to dominate each tandem. That happens if you pair the **fastest** of one group with the **slowest** of the other (so that each fast rider “overshadows” a slow partner).
3. For **minimum** total speed, you want to **dampen** the effect of fast riders, pairing them with **equally fast** partners so that the maximum of each pair is as small as possible. That happens if you pair corresponding ranks—fastest with fastest, second-fastest with second-fastest, etc.

---

## Step-by-Step Solution

1. **Sort** both `groupA` and `groupB` in **ascending** order.
2. Initialize `totalSpeed = 0` and let `n = groupA.length`.
3. **If** `maximizeSpeed == true`:

   * For each index `i` from `0` to `n-1`, pair:

     * `groupA[i]` (the iᵗʰ slowest in A)
     * with `groupB[n - 1 - i]` (the iᵗʰ fastest in B).
   * Add `max(groupA[i], groupB[n - 1 - i])` to `totalSpeed`.
4. **Else** (`maximizeSpeed == false`):

   * For each index `i` from `0` to `n-1`, pair:

     * `groupA[i]` with `groupB[i]` (matching speed ranks).
   * Add `max(groupA[i], groupB[i])` to `totalSpeed`.
5. Return `totalSpeed`.

This runs in **O(n log n)** time (sorting) plus **O(n)** for the pairing loop, and uses **O(1)** extra space beyond the input arrays.

---

## Java Implementation

```java
package easy.greedyalgorithm;

import java.util.Arrays;

public class A03TandemBicycle {

  /**
   * Calculates the total speed of tandem bikes under two strategies:
   * - maximizeSpeed = true: maximize total speed.
   * - maximizeSpeed = false: minimize total speed.
   *
   * @param groupA        speeds of riders in Group A
   * @param groupB        speeds of riders in Group B
   * @param maximizeSpeed whether to maximize or minimize total speed
   * @return the sum of tandem speeds under the chosen strategy
   */
  public int calculateTandemSpeed(int[] groupA, int[] groupB, boolean maximizeSpeed) {
    // 1) Sort both groups in ascending order
    Arrays.sort(groupA);
    Arrays.sort(groupB);

    int totalSpeed = 0;
    int n = groupA.length;

    if (maximizeSpeed) {
      // 2a) For maximum total speed:
      // Pair the i-th slowest in A with the i-th fastest in B.
      for (int i = 0; i < n; i++) {
        int speedA = groupA[i];
        int speedB = groupB[n - 1 - i];
        // Tandem speed is the faster rider
        totalSpeed += Math.max(speedA, speedB);
      }
    } else {
      // 2b) For minimum total speed:
      // Pair the i-th slowest in A with the i-th slowest in B.
      for (int i = 0; i < n; i++) {
        totalSpeed += Math.max(groupA[i], groupB[i]);
      }
    }

    return totalSpeed;
  }

  public static void main(String[] args) {
    A03TandemBicycle solver = new A03TandemBicycle();

    int[] groupA = {5, 5, 3, 9, 2};
    int[] groupB = {3, 6, 7, 2, 1};

    // Maximize total speed
    int maxSpeed = solver.calculateTandemSpeed(groupA, groupB, true);
    System.out.println("Maximum Tandem Speed: " + maxSpeed);
    // Expected: 32
    // Explanation of one pairing:
    //   A-sorted  = [2,3,5,5,9]
    //   B-sorted  = [1,2,3,6,7]
    // Pair A[0] with B[4] → max(2,7)=7
    // Pair A[1] with B[3] → max(3,6)=6
    // Pair A[2] with B[2] → max(5,3)=5
    // Pair A[3] with B[1] → max(5,2)=5
    // Pair A[4] with B[0] → max(9,1)=9
    // Sum = 7+6+5+5+9 = 32

    // Minimize total speed
    int minSpeed = solver.calculateTandemSpeed(groupA, groupB, false);
    System.out.println("Minimum Tandem Speed: " + minSpeed);
    // Expected: 25
    // Explanation of one pairing:
    //   A-sorted = [2,3,5,5,9]
    //   B-sorted = [1,2,3,6,7]
    // Pair by index:
    //   max(2,1)=2, max(3,2)=3, max(5,3)=5, max(5,6)=6, max(9,7)=9
    // Sum = 2+3+5+6+9 = 25
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * Sorting two arrays of length $n$: **O(n log n)**.
  * One pass over length $n$ to compute sums: **O(n)**.
  * **Overall:** $O(n\,\log n)$.

* **Space Complexity:**

  * Sorting is done in-place (Java’s `Arrays.sort` on primitives): **O(1)** extra.
  * A few loop variables and accumulators: **O(1)** extra.

This greedy strategy ensures that the strongest riders either accentuate or temper the tandem speeds as required, all in optimal time and space.
