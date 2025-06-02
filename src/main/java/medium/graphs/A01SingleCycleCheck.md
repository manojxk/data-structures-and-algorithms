**Problem Explanation**
You are given an integer array `array`, where each element at index *i* represents a “jump” of that many steps forward (if positive) or backward (if negative) in the array. The array is considered **circular**, so if a jump takes you past the last index, you wrap around to the front (and vice versa for negative jumps).

We say the array forms a **single cycle** if, starting from index 0 and following each jump in turn, you:

1. Visit **every** index in the array **exactly once**, and
2. End up back at index 0 on your final jump.

In other words, after exactly *n* jumps (where *n* is the array’s length), you must have landed back at 0, and you must never revisit any other index before that.

---

## Why This Problem Matters

A “single cycle” means no index is “left behind” and no index is visited more than once. If the jumps form a proper permutation of the indices that hits every position exactly once, it’s a valid cycle. Otherwise, either:

* You return to index 0 too early (before visiting all indices), or
* You land on some index a second time before covering all indices, or
* After *n* jumps you do not come back to index 0.

You need to detect precisely that “perfect” cycle.

---

## Approach: One-Pass Simulation with a Visit Counter (O(n) Time • O(1) Space)

1. **Keep a counter** `numElementsVisited`, initially 0.
2. **Start** at `currentIndex = 0`.
3. **Repeat** until `numElementsVisited` reaches the array’s length *n*:
   a. If `numElementsVisited > 0` and `currentIndex == 0`, you’ve returned to the start **too early** → immediately return `false`.
   b. Increment `numElementsVisited`.
   c. Compute the next index via the “jump” stored in `array[currentIndex]`, wrapping around if needed.
4. After you’ve made exactly *n* jumps (so `numElementsVisited == n`), check if `currentIndex` is back to 0.

   * If yes, you visited every index exactly once in a single cycle → return `true`.
   * If not, either you landed somewhere else or you didn’t cover all indices properly → return `false`.

Why it works in **O(n)** time and **O(1)** space:

* You only “visit” each index once. After *n* moves, you either succeed or detect a failure early.
* You never use any auxiliary data structure (no `visited[]` array). Instead, you detect a premature return‐to-0 by checking `currentIndex == 0` before having visited all *n* indices.

---

## Step-by-Step Logic

1. **Initialization**

   ```java
   int numElementsVisited = 0;
   int currentIndex = 0;   // start at index 0
   int n = array.length;
   ```

2. **Traverse**

   ```java
   while (numElementsVisited < n) {
     // If we’re back at 0 too soon, fail
     if (numElementsVisited > 0 && currentIndex == 0) {
       return false;
     }
     numElementsVisited++;
     currentIndex = getNextIndex(currentIndex, array);
   }
   ```

3. **Final Check**

   ```java
   return currentIndex == 0;
   ```

4. **Computing Next Index (with wrap‐around)**

   ```java
   private static int getNextIndex(int currentIndex, int[] array) {
     int jump = array[currentIndex];
     int nextIndex = (currentIndex + jump) % array.length;
     if (nextIndex < 0) {
       nextIndex += array.length;
     }
     return nextIndex;
   }
   ```

   * `(currentIndex + jump) % n` handles forward jumps that go past the end or backward jumps that go below 0 (in which case `%` yields a negative).
   * If `nextIndex` is negative, we add `n` to wrap it into the valid range $0, n−1$.

---

## Full Java Implementation

```java
package medium.graphs;

public class A01SingleCycleCheck {

  /**
   * Returns true if the jumps in the array form a single cycle visiting
   * every index exactly once and returning to index 0 at the end.
   *
   * Time Complexity:  O(n)  — we visit exactly n indices (or fail early).
   * Space Complexity: O(1)  — only a few counters/indices are used.
   */
  public static boolean hasSingleCycle(int[] array) {
    int numElementsVisited = 0;
    int currentIndex = 0;
    int n = array.length;

    while (numElementsVisited < n) {
      // If we return to 0 before visiting all n elements, it’s not a single cycle
      if (numElementsVisited > 0 && currentIndex == 0) {
        return false;
      }

      numElementsVisited++;
      currentIndex = getNextIndex(currentIndex, array);
    }

    // After n jumps, we must land back at 0 to complete a single cycle
    return currentIndex == 0;
  }

  /**
   * Computes the next index by jumping from currentIndex by the value in array,
   * wrapping around if necessary.
   */
  private static int getNextIndex(int currentIndex, int[] array) {
    int n = array.length;
    int jump = array[currentIndex];
    int nextIndex = (currentIndex + jump) % n;
    if (nextIndex < 0) {
      nextIndex += n;
    }
    return nextIndex;
  }

  public static void main(String[] args) {
    // Example from the prompt
    int[] array = { 2,  3,  1, -4, -4,  2 };
    // Explanation:
    // Start at 0 → jump +2 → index 2
    // index 2 → jump +1 → index 3
    // index 3 → jump -4 → index (3 - 4) mod 6 = -1 mod 6 = 5
    // index 5 → jump +2 → index 1
    // index 1 → jump +3 → index 4
    // index 4 → jump -4 → index (4 - 4) mod 6 = 0
    // We made 6 jumps, visited every index once, and returned to 0 → single cycle.
    System.out.println("Does the array form a single cycle? " 
                       + hasSingleCycle(array)); // true

    // Another example: not a single cycle
    int[] array2 = { 1,  1,  1,  1,  2 };
    // Let’s see: from 0→1→2→3→4→1…, we never visit index 0 again after the first move,
    // and we revisit index 1 before covering all indices. So this is false.
    System.out.println("Does the array form a single cycle? " 
                       + hasSingleCycle(array2)); // false
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * You perform exactly one iteration for each of the *n* array elements, updating `currentIndex` each time (with constant-time arithmetic).
  * In the worst case, you visit all *n* indices exactly once and then check you landed on 0 → **O(n)**.

* **Space Complexity:**

  * You only use a few integer variables (`numElementsVisited, currentIndex, n, jump, nextIndex`) → **O(1)** extra space.

This in‐place, one‐pass method reliably detects whether the array’s jumps form a perfect single cycle.
