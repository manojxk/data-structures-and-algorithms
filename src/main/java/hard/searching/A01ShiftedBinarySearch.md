**Problem Restatement**
You have a sorted array of distinct integers that has been “rotated” at some unknown pivot. That means originally it was in strictly increasing order, like

```
[1, 2, 3, 4, 5, 6, 7]
```

but then someone picked a point—say between 7 and 2—and “spliced” the array so it became

```
[4, 5, 6, 7, 0, 1, 2]
```

Your job is to write a function that, given such a rotated array and a target integer, returns the index of the target if it exists, or `-1` if it does not. For example:

* If `array = [4, 5, 6, 7, 0, 1, 2]` and `target = 0`, your function should return `4`, because `array[4] == 0`.
* If `array = [4, 5, 6, 7, 0, 1, 2]` and `target = 3`, your function should return `-1`, because `3` is not in the array.
* If `array = [3, 1]` and `target = 1`, your function should return `1`.

Because the original array was sorted, we can adapt binary search to run in **O(log n)** time even though the pivot is unknown. We just need to carefully decide, at each step, whether the “left half” or the “right half” of our current interval is still in sorted order, then decide which half could contain the target.

---

## High‐Level Approach

1. **Maintain two pointers** `left = 0` and `right = array.length – 1`.
2. **While** `left ≤ right`, compute `mid = (left + right) / 2` (integer division).
3. If `array[mid] == target`, return `mid`. Done.
4. Otherwise, determine which side—the subarray from `left` to `mid` or the subarray from `mid` to `right`—is guaranteed to be in strictly increasing order:

   * If `array[left] ≤ array[mid]`, then the left half `[ left … mid ]` is sorted (because no rotation point lies inside it).
   * Otherwise, the right half `[ mid … right ]` must be sorted.
5. Once you know one half is sorted, check if the `target` lies within that sorted half’s numeric range. For example, if the left half is sorted and

   ```
   array[left] ≤ target < array[mid],
   ```

   then you know the target must be somewhere between index `left` and index `mid – 1`. In that case, set `right = mid – 1`. Otherwise, the target cannot be in `[left…mid]` (because it’s outside the sorted range), so it must (if it exists at all) lie in the other half—set `left = mid + 1`.
6. Similarly, if instead you discovered that the right half is the sorted one, you check

   ```
   array[mid] < target ≤ array[right].
   ```

   If that condition holds, you move into the right half by setting `left = mid + 1`; otherwise, search the left half by setting `right = mid – 1`.
7. Repeat until either you find `array[mid] == target` (and return `mid`) or exhaust the interval (`left > right`) and return `-1`.

This entire process does exactly one binary‐search‐style halving per loop, so its time complexity is **O(log n)**. We never allocate new arrays, so space complexity is **O(1)**.

---

## Step‐by‐Step Example

Take `array = [4, 5, 6, 7, 0, 1, 2]`, `target = 0`.

1. `left = 0`, `right = 6` (because `array.length – 1 = 6`).

   * Compute `mid = (0 + 6) / 2 = 3`.
   * `array[3] == 7`. That is not `0`.
   * Check if the left half `[0..3]` (i.e. `[4,5,6,7]`) is sorted. Indeed, `array[0] = 4 ≤ array[3] = 7`, so the left half is in ascending order.
   * Does `target = 0` lie in `[ array[0]..array[3] ]`, i.e. in `[4..7]`? No, because `0 < 4`. So the target cannot be in the left half. We must search the right half.
   * Consequently, set `left = mid + 1 = 4`. Now our search interval is `[4..6]`.

2. Now `left = 4`, `right = 6`.

   * Compute `mid = (4 + 6) / 2 = 5`.
   * `array[5] == 1`. Not `0`.
   * Check if left half `[4..5]` (i.e. `[0,1]`) is sorted: since `array[4] = 0 ≤ array[5] = 1`, it is indeed sorted.
   * Does `target = 0` lie in `[ array[4]..array[5] ] = [0..1]`? Yes, because `0 ≤ 0 ≤ 1`. So the target must be in `[4..mid−1]` or maybe at `mid` itself. But we already saw `array[5] != target`, so it must be at index `4`.
   * We set `right = mid − 1 = 4`. Now interval is `[4..4]`.

3. Now `left = 4`, `right = 4`.

   * Compute `mid = (4 + 4) / 2 = 4`.
   * `array[4] == 0`, which is our target. Return `4`.

Hence the function returns `4`.

---

## Detailed Java Code

```java
public class A01ShiftedBinarySearch {

  /**
   * Searches for target in a sorted but rotated array. 
   * Returns the index if found; otherwise returns -1.
   *
   * Time Complexity: O(log n)  
   * Space Complexity: O(1)
   */
  public static int shiftedBinarySearch(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
      // Avoid integer overflow when computing mid
      int mid = left + (right - left) / 2;

      // 1) If we found the target, immediately return its index
      if (array[mid] == target) {
        return mid;
      }

      // 2) Decide which half is “sorted” in ascending order:
      //    If the leftmost element ≤ array[mid], then [left..mid] is strictly sorted.
      if (array[left] <= array[mid]) {
        // The left half from index left to mid is in ascending order.

        // 2a) Check if the target lies between array[left] and array[mid]:
        if (array[left] <= target && target < array[mid]) {
          // If yes, narrow to the left half
          right = mid - 1;
        } else {
          // Otherwise, it must lie in the right half (if it exists at all)
          left = mid + 1;
        }
      }
      // 3) Otherwise, the right half [mid..right] must be sorted
      else {
        // The right half from index mid to right is in ascending order.

        // 3a) Check if the target lies between array[mid] and array[right]:
        if (array[mid] < target && target <= array[right]) {
          // If yes, narrow down to the right half
          left = mid + 1;
        } else {
          // Otherwise, search in the left half
          right = mid - 1;
        }
      }
    }

    // If we exit the loop, we never found the target
    return -1;
  }

  public static void main(String[] args) {
    // Example 1
    int[] array1 = {4, 5, 6, 7, 0, 1, 2};
    int target1 = 0;
    System.out.println("Index of target 0: "
        + shiftedBinarySearch(array1, target1));  // Expected: 4

    // Example 2
    int[] array2 = {4, 5, 6, 7, 0, 1, 2};
    int target2 = 3;
    System.out.println("Index of target 3: "
        + shiftedBinarySearch(array2, target2));  // Expected: -1

    // Example 3
    int[] array3 = {3, 1};
    int target3 = 1;
    System.out.println("Index of target 1: "
        + shiftedBinarySearch(array3, target3));  // Expected: 1
  }
}
```

---

## Explanation of Key Steps

1. **Compute `mid` Safely**

   ```java
   int mid = left + (right - left) / 2;
   ```

   This prevents overflow that can happen if you wrote `(left + right) / 2` when `left` and `right` are both near `Integer.MAX_VALUE`.

2. **Check for Direct Hit**

   ```java
   if (array[mid] == target) {
     return mid;
   }
   ```

3. **Determine Which Half Is Sorted**

   * If `array[left] ≤ array[mid]`, then the subarray from index `left` through `mid` is strictly increasing (because no rotation point lies between `left` and `mid`).
   * Otherwise, the subarray from index `mid` through `right` must be strictly increasing.

4. **Decide Which Side to Search Next**

   * **Case A: Left half `[left..mid]` is sorted**

     * If `target` lies in `[ array[left], array[mid] )`, search the left half via `right = mid − 1`.
     * Otherwise search the right half via `left = mid + 1`.
   * **Case B: Right half `[mid..right]` is sorted**

     * If `target` lies in `( array[mid], array[right] ]`, search the right half via `left = mid + 1`.
     * Otherwise search the left half via `right = mid − 1`.

   In each iteration, we discard half of the current interval, preserving **O(log n)** time.

5. **Loop Until Exhaustion or Found**
   We keep doing this while `left ≤ right`. If the loop ends without finding `target`, return `−1`.

---

## Time and Space Complexity

* **Time Complexity: O(log n)**
  Each iteration of the loop cuts the search interval in half. In a standard (unshifted) binary search, that yields **O(log n)**. Our checks to see which half is sorted and whether the target lies in that sorted half still take constant time, so each round is O(1). Thus overall O(log n).

* **Space Complexity: O(1)**
  We only use a handful of integer variables (`left`, `right`, `mid`, plus the inputs), so no additional arrays or data structures of size proportional to *n* are needed.

---

### Closing Remarks

By carefully examining whether the left or right segment around `mid` is in ascending order, we can always decide in **constant time** which half to discard, even though we do not know where the original rotation pivot was. This lets us maintain the classic binary‐search efficiency of **O(log n)** while correctly handling a rotated (shifted) sorted array.
