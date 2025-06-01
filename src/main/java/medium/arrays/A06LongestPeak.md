**Problem Explanation**
A **peak** in an array is a contiguous sequence of integers that strictly **increases** up to a single highest point (the “peak element”), then strictly **decreases**. A valid peak must be at least length 3 (because you need one element on either side of the peak).

For example, in the array

```
[1, 4, 10, 2, 1, 5, 6, 3, 2, 0]
```

we have two peaks:

* `[1, 4, 10, 2, 1]` – length 5, rising 1→4→10, then falling 10→2→1
* `[1, 5, 6, 3, 2, 0]` – length 6, rising 1→5→6, then falling 6→3→2→0

The **longest** peak is the latter, with length 6.

Your task is to return the length of the longest such peak. If there are no peaks (no element that’s strictly greater than both neighbors), return 0.

---

## Approach: One-Pass + Two-Way Expansion (O(n) Time, O(1) Space)

1. **Scan** the array from left to right looking for a **peak “tip”** – an index `i` such that

   ```
   array[i - 1] < array[i] > array[i + 1]
   ```

   (i.e. it’s strictly greater than both immediate neighbors). Since a peak requires one element on each side, we only check `i` from 1 up to `array.length – 2`.

2. **When you find a peak-tip at index i**:

   * **Expand left** from `i – 1` as long as you see strictly increasing values (compared to their right neighbor). In code, you decrement a pointer `leftIdx` while

     ```
     leftIdx >= 0  &&  array[leftIdx] < array[leftIdx + 1]
     ```
   * **Expand right** from `i + 1` as long as you see strictly decreasing values (compared to their left neighbor). That is, increment a pointer `rightIdx` while

     ```
     rightIdx < array.length  &&  array[rightIdx] < array[rightIdx - 1]
     ```
   * Once both expansions stop, the **length** of this peak is

     ```
     (rightIdx – 1) – (leftIdx + 1) + 1 = rightIdx – leftIdx – 1
     ```

     because `leftIdx` and `rightIdx` have moved one step too far. Update your global maximum if this peak’s length is larger.

3. **Skip ahead** to `i = rightIdx` after processing a peak, because everything between the original peak-tip and `rightIdx – 1` is part of this same peak, so you won’t find a new peak among them.

4. **Continue** until you have scanned all valid tip positions. Return the maximum peak length found (or 0 if none).

This is a **single pass** over the array plus, for each peak, a “while‐loop” expansion left and right that never revisits the same index twice. Therefore the overall time is still **O(n)**. You only use a constant number of pointers/variables → **O(1)** extra space.

---

## Java Implementation

```java
package medium.arrays;

public class A06LongestPeak {

  /**
   * Returns the length of the longest peak in the input array.
   * A peak is a strictly increasing sequence followed by a strictly decreasing sequence,
   * of total length at least 3.
   *
   * Time Complexity:  O(n)
   * Space Complexity: O(1)
   */
  public static int longestPeak(int[] array) {
    int longestPeakLength = 0;
    int n = array.length;
    int i = 1; // we'll check from index 1 up to n-2 for a potential peak tip

    while (i < n - 1) {
      // Check if array[i] is a peak-tip
      boolean isPeak = array[i - 1] < array[i] && array[i] > array[i + 1];
      if (!isPeak) {
        i++;
        continue;
      }

      // Expand left from i-1 while strictly rising
      int leftIdx = i - 2;
      while (leftIdx >= 0 && array[leftIdx] < array[leftIdx + 1]) {
        leftIdx--;
      }

      // Expand right from i+1 while strictly falling
      int rightIdx = i + 2;
      while (rightIdx < n && array[rightIdx] < array[rightIdx - 1]) {
        rightIdx++;
      }

      // Compute the length of this peak
      // leftIdx is now one index before the start of the rising sequence
      // rightIdx is one index after the end of the falling sequence
      int currentPeakLength = rightIdx - leftIdx - 1;
      longestPeakLength = Math.max(longestPeakLength, currentPeakLength);

      // Skip ahead: everything up through rightIdx - 1 is part of this peak
      i = rightIdx;
    }

    return longestPeakLength;
  }

  public static void main(String[] args) {
    // Example 1:
    int[] array1 = {1, 4, 10, 2, 1, 5, 6, 3, 2, 0};
    // Peaks: [1,4,10,2,1] (length 5) and [1,5,6,3,2,0] (length 6)
    // Longest = 6
    System.out.println("Longest Peak Length: " + longestPeak(array1));
    // Expected output: 6

    // Example 2 (no peak, strictly increasing):
    int[] array2 = {1, 2, 3, 4, 5};
    System.out.println("Longest Peak Length: " + longestPeak(array2));
    // Expected output: 0

    // Example 3 (one small peak of length 3):
    int[] array3 = {4, 10, 2};
    // Peak is [4,10,2], length 3
    System.out.println("Longest Peak Length: " + longestPeak(array3));
    // Expected output: 3

    // Example 4 (multiple peaks, tie for length):
    int[] array4 = {3, 5, 9, 6, 2, 7, 8, 4};
    // Peaks: [3,5,9,6,2] length 5 and [2,7,8,4] length 4 → longest = 5
    System.out.println("Longest Peak Length: " + longestPeak(array4));
    // Expected output: 5
  }
}
```

---

### Explanation of Key Steps

1. **Finding a Peak‐Tip**
   We only check `i` if `array[i - 1] < array[i] > array[i + 1]`. That guarantees a valid peak has at least 3 elements.

2. **Expanding Left**
   Starting from `i – 1`, we move left while the sequence is strictly increasing. As soon as `array[leftIdx] ≥ array[leftIdx + 1]`, we stop. After the loop, `leftIdx` is one index **before** the start of the “rising” portion.

3. **Expanding Right**
   Starting from `i + 1`, we move right while the sequence is strictly decreasing. When `array[rightIdx] ≥ array[rightIdx – 1]` or we go out of bounds, we stop. After the loop, `rightIdx` is one index **after** the end of the “falling” portion.

4. **Peak Length**
   The peak runs from `(leftIdx + 1)` through `(rightIdx – 1)` inclusive, so its length is

   ```
   (rightIdx – 1) – (leftIdx + 1) + 1  =  rightIdx – leftIdx – 1.
   ```

5. **Skipping Over the Entire Peak**
   Because every index from `(leftIdx + 1)` up to `(rightIdx – 1)` belongs to this peak, we set `i = rightIdx` to continue scanning **beyond** this peak. That prevents us from counting sub‐peaks inside the same rising‐and‐falling block more than once, keeping the overall runtime to O(n).

---

## Complexity Analysis

* **Time Complexity:**
  We loop `i` from `1` to `n – 2`. Each time we find a peak, we expand left and right, but **each element is visited at most once** during those expansions. Once an index has been consumed by a peak expansion, `i` jumps past the end of the peak. Thus the overall work is linear in `n` → **O(n)**.

* **Space Complexity:**
  We only use a constant number of integer pointers (`i, leftIdx, rightIdx`) and counters → **O(1)** extra space.

This method efficiently finds and measures each peak in one pass, tracking the maximum peak length as it goes.
