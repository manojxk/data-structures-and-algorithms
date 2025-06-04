**Problem Restatement**
You have an integer array of length ≥ 2. You want to find the (contiguous) subarray which, if you sort it in ascending order, makes the entire array sorted. Specifically:

* Return a two‐element array `[start, end]` such that sorting `array[start..end]` (inclusive) causes the whole array to be in ascending order.
* If the array is already completely sorted, return `[-1, -1]`.

For example,

```
array = [1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]
```

The smallest subarray that needs sorting is from index `3` to index `9`:

```
  [7, 10, 11, 7, 12, 6, 7]
   ↑                 ↑
 start = 3       end = 9
```

If you sort those elements, the entire array becomes sorted. So the answer is `[3, 9]`.

---

## Overview of Solutions

1. **“Copy‐and‐Compare” (Brute‐Force, O(n log n) Time, O(n) Space)**

   * Make a copy of the original array and sort the copy.
   * Compare original vs. sorted copy from left to right to find the first index where they differ → that is `start`.
   * Compare original vs. sorted copy from right to left to find the first index where they differ → that is `end`.
   * If no difference is found in either pass, return `[-1, -1]`; otherwise return `[start, end]`.

2. **One‐Pass “Out‐of‐Order” Detection (Optimized, O(n) Time, O(1) Extra Space)**

   * Scan once to identify which elements are “out of place” relative to their neighbors (either larger than the next element or smaller than the previous).
   * Track the **minimum** and **maximum** values among those out‐of‐order elements.
   * Finally, find how far left the `minOutOfPlace` must go (the first index where `array[left] > minOutOfPlace`), and how far right the `maxOutOfPlace` must go (the last index where `array[right] < maxOutOfPlace`).
   * Those two indices are `[start, end]`. If no out‐of‐order element is found, return `[-1, -1]`.

Below is a detailed walkthrough of each approach.

---

## 1. Copy‐and‐Compare Approach (O(n log n) Time, O(n) Space)

### Steps

1. **Clone and Sort**

   ```java
   int[] arrayCopy = array.clone();
   Arrays.sort(arrayCopy);
   ```

   Now `arrayCopy` is the same elements but in ascending order.

2. **Find “start”**
   Walk `start` from `0` up to `array.length−1` while

   ```java
   array[start] == arrayCopy[start];
   ```

   Stop at the first index where they differ (or `start` reaches `array.length`). If all match, the array was already sorted.

3. **Find “end”**
   Walk `end` from `array.length−1` down to `start` while

   ```java
   array[end] == arrayCopy[end];
   ```

   Stop at the first place where they differ.

4. **Return**

   * If `start == array.length` (i.e., we never found any difference), return `[-1, -1]`.
   * Otherwise return `[start, end]`.

### Code

```java
import java.util.Arrays;

public class A02SubarraySort {
  public static int[] subarraySort(int[] array) {
    int n = array.length;
    int[] arrayCopy = array.clone();
    Arrays.sort(arrayCopy);

    int start = 0;
    // Move start forward while elements match
    while (start < n && array[start] == arrayCopy[start]) {
      start++;
    }

    // If start reaches n, everything matched => already sorted
    if (start == n) {
      return new int[] {-1, -1};
    }

    int end = n - 1;
    // Move end backward while elements match
    while (end > start && array[end] == arrayCopy[end]) {
      end--;
    }

    return new int[] { start, end };
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
    System.out.println(Arrays.toString(subarraySort(array))); // [3, 9]
  }
}
```

### Complexity

* **Time:**

  * Cloning: `O(n)`
  * Sorting the copy: `O(n log n)`
  * Two linear scans (for `start` and `end`): each `O(n)`
    → Total: **O(n log n)**.

* **Space:**

  * You allocate an `arrayCopy` of size `n`: **O(n)** extra space.

---

## 2. One‐Pass “Out‐of‐Order” Detection (O(n) Time, O(1) Space)

Instead of making a sorted copy, we can figure out exactly which region is “unsorted” by looking at the places where elements violate the ascending‐order rule. Concretely, any element that is:

* **Greater than its next neighbor**, or
* **Smaller than its previous neighbor**

must lie somewhere inside the subarray that needs sorting. We collect those “out‐of‐place” values, find the min and max among them, and then see how far they extend beyond their immediate neighbors.

### Steps

1. **Detect Out‐of‐Place Elements**

   * Initialize

     ```java
     int minOutOfPlace = Integer.MAX_VALUE;
     int maxOutOfPlace = Integer.MIN_VALUE;
     ```
   * Traverse the array index `i = 0..n−1`. For each `i`, check if `array[i]` is out of order relative to neighbors:

     ```java
     boolean outOfPlace;
     if (i == 0) {
       // First element is out‐of‐place if it’s greater than the next element
       outOfPlace = array[i] > array[i + 1];
     } else if (i == n - 1) {
       // Last element is out‐of‐place if it’s smaller than the previous element
       outOfPlace = array[i] < array[i - 1];
     } else {
       // Middle elements: out‐of‐place if bigger than next OR smaller than previous
       outOfPlace = array[i] > array[i + 1] || array[i] < array[i - 1];
     }
     ```
   * If `outOfPlace` is `true`, update `minOutOfPlace` and `maxOutOfPlace`:

     ```java
     minOutOfPlace = Math.min(minOutOfPlace, array[i]);
     maxOutOfPlace = Math.max(maxOutOfPlace, array[i]);
     ```

2. **If No Out‐of‐Place Element Found**

   * If `minOutOfPlace` remained `Integer.MAX_VALUE`, then the array never violated ascending order. Return `[-1, -1]`.

3. **Find Left Boundary**

   * Starting from `left=0`, move right as long as `array[left] <= minOutOfPlace`:

     ```java
     while (left < n && array[left] <= minOutOfPlace) {
       left++;
     }
     ```
   * That first index `left` where `array[left] > minOutOfPlace` is where the subarray must start (because `minOutOfPlace` truly belongs somewhere after index `left−1`).

4. **Find Right Boundary**

   * Starting from `right = n−1`, move left as long as `array[right] >= maxOutOfPlace`:

     ```java
     while (right >= 0 && array[right] >= maxOutOfPlace) {
       right--;
     }
     ```
   * That first index `right` where `array[right] < maxOutOfPlace` is where the subarray must end.

5. **Return** `[left, right]`.

### Code

```java
package hard.arrays;

public class A02SubarraySort {
  public static int[] subarraySortOptimized(int[] array) {
    int n = array.length;
    int minOutOfPlace = Integer.MAX_VALUE;
    int maxOutOfPlace = Integer.MIN_VALUE;

    // 1) Identify any element that is 'out of place' compared to neighbors
    for (int i = 0; i < n; i++) {
      boolean outOfPlace;
      if (i == 0) {
        // First element: compare only to next element
        outOfPlace = array[i] > array[i + 1];
      } else if (i == n - 1) {
        // Last element: compare only to previous element
        outOfPlace = array[i] < array[i - 1];
      } else {
        // Middle elements: compare to both neighbors
        outOfPlace = array[i] > array[i + 1] || array[i] < array[i - 1];
      }

      if (outOfPlace) {
        minOutOfPlace = Math.min(minOutOfPlace, array[i]);
        maxOutOfPlace = Math.max(maxOutOfPlace, array[i]);
      }
    }

    // 2) If no out-of-place element was found, the array is already sorted
    if (minOutOfPlace == Integer.MAX_VALUE) {
      return new int[]{ -1, -1 };
    }

    // 3) Find left boundary: first index where array[left] > minOutOfPlace
    int left = 0;
    while (left < n && array[left] <= minOutOfPlace) {
      left++;
    }

    // 4) Find right boundary: first index from right where array[right] < maxOutOfPlace
    int right = n - 1;
    while (right >= 0 && array[right] >= maxOutOfPlace) {
      right--;
    }

    return new int[]{ left, right };
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19};
    int[] result = subarraySortOptimized(array);
    System.out.println(java.util.Arrays.toString(result)); // [3, 9]
  }
}
```

### Why This Works

* **Any element that is already in perfect ascending order with its neighbors is “in place.”**

  * That means `array[i]` is not larger than `array[i+1]` and not smaller than `array[i−1]` (for interior elements).
  * If it violates that (i.e. `array[i] > array[i+1]` or `array[i] < array[i−1]`), it must end up inside the subarray we sort.

* **Collecting min/max of all “out‐of‐place” values** tells us the smallest and largest values that truly belong somewhere inside that unsorted segment.

  * Everything smaller than `minOutOfPlace` is already in correct ascending order on the left side.
  * Everything larger than `maxOutOfPlace` is already in correct ascending order on the right side.

* **Finding `left`**

  * We scan from the very left of `array` upward until we hit a value that is **greater** than `minOutOfPlace`.
  * That index must be the true left edge of the subarray we need to sort (because if some value `array[l]` ≤ `minOutOfPlace`, then `array[l]` belongs on or before index `l`, so it doesn’t need to move).

* **Finding `right`**

  * We scan from the very right of `array` down until we find a value that is **less** than `maxOutOfPlace`.
  * That index must be the true right edge of the subarray we need to sort.

* Anything between `[left..right]` definitely needs to be sorted, because it contains all the values that “jumped out of place.” Sorting that chunk fixes all “local inversions,” and as soon as that is sorted, the entire array becomes sorted.

### Complexity

* **Time:**

  * The first loop (identifying out‐of‐place elements) is `O(n)`.
  * The second loop (finding `left`) is `O(n)` in the worst case.
  * The third loop (finding `right`) is `O(n)`.
    → Total: **O(n)**.

* **Space:**

  * We only keep a few integer variables (`minOutOfPlace`, `maxOutOfPlace`, `left`, `right`), so **O(1)** extra space.

---

## Summary

* **Copy‐and‐Compare** (lines 1–20):

  * Sort a cloned array → `O(n log n)` time, `O(n)` space.
  * Compare indices from left and right until they differ.

* **One‐Pass Out‐of‐Order** (lines 22–64):

  * Identify all elements that are “out of place” in one sweep → track `minOutOfPlace` and `maxOutOfPlace`.
  * Find how far left `minOutOfPlace` must shift, and how far right `maxOutOfPlace` must shift.
  * This is **O(n)** time, **O(1)** extra space.

Either approach yields `[3, 9]` on the example `[1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]`.
