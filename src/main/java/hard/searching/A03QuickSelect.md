**Problem Restatement**
Given a zero‐indexed array of distinct integers and an integer `k` (with `1 ≤ k ≤ array.length`), return the *kth smallest* element in the array. For example, if

```
array = [8, 5, 2, 9, 7, 6, 3],  
k = 3  
```

then the sorted order is `[2, 3, 5, 6, 7, 8, 9]`, so the 3rd smallest is `5`. We want an algorithm that runs in **O(n)** time on average (rather than sorting all elements in **O(n log n)** time).

---

## Approaches Overview

1. **Brute‐Force (Sort + Index) → O(n log n) Time**

   * Sort the entire array in ascending order (e.g. via `Arrays.sort`).
   * Return the element at index `k−1`.
   * Although simple, this costs **O(n log n)**.

2. **Quickselect (Average‐Case O(n) Time)**

   * Quickselect is an in‐place, partition‐based selection algorithm closely related to quicksort.
   * We repeatedly partition around a pivot so that, after partitioning, all elements smaller than the pivot lie to its left and all larger lie to its right.
   * If the pivot’s final index is exactly `k−1`, we found the kth smallest. Otherwise, we recurse (or iterate) into the side that must contain the kth smallest.
   * On average—if the pivot is chosen “randomly enough”—each partition step cuts the problem size by a constant factor, yielding **O(n)** expected time. (Worst‐case is **O(n^2)** if the pivot is always the largest or smallest element.)

---

## 1) Brute‐Force (Sort + Index)

```java
public static int kthSmallestBruteForce(int[] array, int k) {
  Arrays.sort(array);      // O(n log n)
  return array[k - 1];     // Directly pick the (k−1)th index
}
```

* **Time Complexity:** O(n log n)
* **Space Complexity:** O(1) (sort uses in‐place O(1) extra if implemented accordingly, or O(log n) for recursion stack)

---

## 2) Quickselect (Average O(n) Time)

### Key Idea

* Use the same partition step as in quicksort.
* Partition the current subarray `array[left…right]` around the pivot `array[right]`.

  * After partition, the pivot ends up at some index `p`.
  * All elements to its left are `< pivot`, all to its right are `> pivot`.
* If `p == k−1`, then `array[p]` is exactly the kth smallest. Return it.
* If `p > k−1`, the kth smallest must lie in the left segment; set `right = p−1` and repeat.
* If `p < k−1`, the kth smallest lies in the right segment; set `left = p+1` and repeat.

Because each partition “places one element in its final sorted position” and reduces the search range, Quickselect runs in **O(n)** time on average.

### Code Walkthrough

```java
public class QuickSelect {

  /**
   * Returns the kth smallest element (1 ≤ k ≤ array.length) using Quickselect.
   * Converts k to zero‐based by calling quickselect(..., k−1).
   */
  public static int kthSmallestQuickselect(int[] array, int k) {
    // We want the element whose zero‐based order is (k−1).
    return quickselect(array, 0, array.length - 1, k - 1);
  }

  /**
   * In‐place Quickselect on array[left..right], looking for the element of zero-based rank k.
   * Returns array[k] once that position is “correctly” partitioned.
   */
  private static int quickselect(int[] array, int left, int right, int k) {
    while (true) {
      // If the subarray has only one element, that element is the answer.
      if (left == right) {
        return array[left];
      }
      // Partition around a pivot (we choose array[right] as pivot for simplicity).
      int pivotIndex = partition(array, left, right);

      if (k == pivotIndex) {
        // The pivot is exactly the kth‐smallest
        return array[k];
      } 
      else if (k < pivotIndex) {
        // kth‐smallest is in the left subarray
        right = pivotIndex - 1;
      } 
      else {
        // kth‐smallest is in the right subarray
        left = pivotIndex + 1;
      }
    }
  }

  /**
   * Partitions array[left..right] around pivot = array[right].
   * Moves all elements < pivot to the left side, all ≥ pivot to the right side,
   * and finally places the pivot in its correct position. Returns that final index.
   */
  private static int partition(int[] array, int left, int right) {
    int pivot = array[right];
    int storeIndex = left;

    // Move every element < pivot to the “storeIndex” frontier, then increment it.
    for (int j = left; j < right; j++) {
      if (array[j] < pivot) {
        swap(array, storeIndex, j);
        storeIndex++;
      }
    }
    // Place pivot in its rightful position by swapping with storeIndex.
    swap(array, storeIndex, right);
    return storeIndex;
  }

  /** Swaps array[i] and array[j]. */
  private static void swap(int[] array, int i, int j) {
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  // Test driver
  public static void main(String[] args) {
    int[] array = { 8, 5, 2, 9, 7, 6, 3 };
    int k = 3;  // We want the 3rd smallest
    int result = kthSmallestQuickselect(array, k);
    System.out.println("3rd smallest is: " + result);
    // Expected output: 5
  }
}
```

### Why This Works

1. **Partition Step**

   * We pick `pivot = array[right]`.
   * Maintain a pointer `storeIndex = left`.
   * For every index `j` from `left` to `right − 1`:

     * If `array[j] < pivot`, swap it with `array[storeIndex]` and increment `storeIndex`.
   * Finally, swap `array[storeIndex]` and `array[right]`. Now the pivot sits at index `storeIndex`, and

     * All elements `< pivot` lie in `array[left..storeIndex−1]`.
     * All elements `> pivot` lie in `array[storeIndex+1..right]`.
   * `partition(...)` returns `storeIndex`.

2. **Selecting Which Side to Recur/Iterate**

   * Suppose `p = partition(...)`. Now `array[p]` sits at the exact spot it would in a fully sorted array.
   * If `p == k`, then `array[p]` is the kth smallest (0‐based). Return it immediately.
   * Otherwise, if `k < p`, the kth smallest must lie strictly in `left..(p−1)`. Set `right = p − 1` and loop again.
   * Otherwise, `k > p`, so the kth smallest lies in `(p+1)..right`. Set `left = p + 1` and loop again.

3. **Average‐Case O(n)**

   * On average, a random pivot splits the range roughly in half. Each partition step processes the entire current subarray once (O(n) work) but reduces the search interval size by about half. This recurrence solves to O(n) on average. (Worst‐case occurs if the pivot is always the smallest or largest, leading to O(n^2), but randomization or median‐of‐three pivot selection can mitigate that.)

4. **In‐Place & O(1) Extra Space**

   * We never allocate new arrays or lists—swaps are done in place. Only a few integer variables (`left`, `right`, `pivotIndex`, etc.) are needed, so extra space is O(1).

---

## Summary of Complexities

* **Brute‐Force (sorting):** O(n log n) time, O(1) extra space.
* **Quickselect (average):** O(n) average time, O(1) space (worst‐case O(n^2) time if unlucky pivots).

In practice, Quickselect is very fast on average and typically preferred when you only need the kth element rather than a fully sorted array.
