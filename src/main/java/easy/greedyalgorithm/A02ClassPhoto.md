**Problem Explanation**

You have two groups of students, each with an array of heights:

* `redShirtHeights[]`
* `blueShirtHeights[]`

You want to line them up in **two rows** for a photo so that:

1. Every student in the **back** row is **strictly taller** than the student directly in front of them.
2. You can choose **which** color goes in front—either all red-shirted in front of blue-shirted, or vice versa.
3. You may reorder the students arbitrarily within each row.

Your task is to determine **if** such an arrangement is possible.

---

## Brute-Force Idea (Impractical)

You could:

1. Generate both choices (red-in-front vs. blue-in-front).
2. For each choice, try **every** permutation of students in each row and check pairwise heights.

That would be **O(2 · (n! × n!))**—completely infeasible even for small $n$.

---

## Greedy, Sorted-Pairs Solution

**Key insight:**
To minimize conflicts, you should “pair” the **shortest** in one group with the **shortest** in the other, the second-shortest with the second-shortest, and so on. Intuitively, if the shortest red is still shorter than the shortest blue, and the second-shortest red is still shorter than the second-shortest blue, etc., then the entire red row can stand in front of the blue row.

**Steps:**

1. **Sort** both `redShirtHeights` and `blueShirtHeights` in **ascending** order.
2. **Decide** which group goes in front by comparing the very first (smallest) elements:

   * If `redShirtHeights[0] < blueShirtHeights[0]`, put **red in front**, **blue in back**.
   * Otherwise, if `blueShirtHeights[0] < redShirtHeights[0]`, put **blue in front**, **red in back**.
   * If they’re **equal**, no strict ordering is possible → immediately **return false**.
3. **Iterate** through each index `i` from `0` to `n-1`:

   * If **red-in-front**, check `redShirtHeights[i] < blueShirtHeights[i]`.
   * Otherwise (blue-in-front), check `blueShirtHeights[i] < redShirtHeights[i]`.
   * If any pair fails the strict-inequality, **return false**.
4. If you pass all checks, **return true**.

---

## Java Implementation

```java
package easy.greedyalgorithm;

import java.util.Arrays;

public class A02ClassPhoto {

  /**
   * Determines whether you can arrange the two groups so that
   * everyone in the back row is strictly taller than the person
   * in front of them.
   *
   * Time Complexity:  O(n log n) — sorting dominates
   * Space Complexity: O(1)      — in-place sorting, constant extra space
   */
  public boolean canTakeClassPhoto(int[] redShirtHeights,
                                   int[] blueShirtHeights) {
    int n = redShirtHeights.length;
    // 1) Sort both arrays ascending
    Arrays.sort(redShirtHeights);
    Arrays.sort(blueShirtHeights);

    // 2) Decide who goes in front
    boolean redInFront;
    if (redShirtHeights[0] < blueShirtHeights[0]) {
      redInFront = true;       // red row is front, blue row is back
    } else if (blueShirtHeights[0] < redShirtHeights[0]) {
      redInFront = false;      // blue row is front, red row is back
    } else {
      // equal smallest heights ⇒ no strict inequality possible
      return false;
    }

    // 3) Check each paired position
    for (int i = 0; i < n; i++) {
      if (redInFront) {
        // red must be strictly shorter than blue
        if (redShirtHeights[i] >= blueShirtHeights[i]) {
          return false;
        }
      } else {
        // blue must be strictly shorter than red
        if (blueShirtHeights[i] >= redShirtHeights[i]) {
          return false;
        }
      }
    }

    // 4) All checks passed
    return true;
  }

  // Example usage
  public static void main(String[] args) {
    A02ClassPhoto solver = new A02ClassPhoto();
    int[] red   = {5, 8, 1, 3, 4};
    int[] blue  = {6, 9, 2, 4, 5};

    boolean canPhoto = solver.canTakeClassPhoto(red, blue);
    System.out.println("Can take class photo: " + canPhoto);
    // Expected: true
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * Sorting both arrays: **O(n log n)**
  * Single pass to compare heights: **O(n)**
    → **Overall:** O(n log n)

* **Space Complexity:**

  * The sorting is done in-place (Java’s `Arrays.sort` on primitives uses a modified quicksort/mergesort), so **O(1)** extra space beyond the input arrays and a few variables.
