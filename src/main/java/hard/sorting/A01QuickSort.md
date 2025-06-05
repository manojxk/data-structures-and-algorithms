**Quick Sort** is a classic divide‐and‐conquer sorting algorithm that—on average—runs in **O(n log n)** time and sorts in place. Below is a step-by-step walkthrough, followed by a complete Java implementation and complexity analysis.

---

### How Quick Sort Works

1. **Choose a Pivot.**
   Pick one element from the array to act as a “pivot.” In this example, we’ll always choose the **last element** of the current subarray.

2. **Partition Around the Pivot.**
   Reorder (partition) the subarray so that:

   * All elements **less than** the pivot end up on the left side.
   * All elements **greater than or equal to** the pivot end up on the right side.
     At the end of this partition step, the pivot itself is placed in its **correct final position** (in a fully sorted array). The partition function returns the index where the pivot lands.

3. **Recurse on Subarrays.**
   Now you have two smaller subproblems:

   * Sort the portion on the left of the pivot (everything < pivot).
   * Sort the portion on the right of the pivot (everything ≥ pivot).
     Because the pivot is already in its final spot, you never include it when recursing.

4. **Base Case.**
   Once a subarray has size 0 or 1, it is already sorted, so you stop recursing.

---

### Detailed Code (Java)

```java
package hard.sorting;

public class A01QuickSort {

  /**
   * Sorts the portion arr[low..high] (inclusive) in ascending order, in place.
   * 
   * @param arr  The array to be sorted.
   * @param low  The starting index of the subarray.
   * @param high The ending index of the subarray.
   */
  public void quickSort(int[] arr, int low, int high) {
    // Only proceed if there are at least two elements
    if (low < high) {
      // 1) Partition the subarray and get the pivot index
      int pivotIndex = partition(arr, low, high);

      // 2) Recursively sort the left subarray (elements < pivot)
      quickSort(arr, low, pivotIndex - 1);

      // 3) Recursively sort the right subarray (elements ≥ pivot)
      quickSort(arr, pivotIndex + 1, high);
    }
    // If low >= high, subarray has 0 or 1 element—already sorted
  }

  /**
   * Partitions arr[low..high] around pivot = arr[high]. After partitioning,
   * - All elements < pivot will lie to the left of pivot’s final index.
   * - All elements ≥ pivot will lie to the right of pivot’s final index.
   * 
   * Returns the final index where the pivot is placed.
   */
  private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];      // Choose the last element as pivot
    int i = low - 1;            // i tracks the “border” of elements < pivot

    // Iterate j from low to high − 1
    for (int j = low; j < high; j++) {
      if (arr[j] < pivot) {
        // Expand the “< pivot” region by one, then swap
        i++;
        swap(arr, i, j);
      }
      // Otherwise, arr[j] ≥ pivot → leave it on the right side
    }

    // Finally place pivot in position i+1
    swap(arr, i + 1, high);
    return i + 1;  // pivot’s final index
  }

  /**
   * Swaps arr[i] and arr[j] in place.
   */
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // Simple test driver
  public static void main(String[] args) {
    A01QuickSort sorter = new A01QuickSort();
    int[] arr = { 10, 80, 30, 90, 40, 50, 70 };

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Sort the entire array: indices 0 through arr.length−1
    sorter.quickSort(arr, 0, arr.length - 1);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }
}
```

---

### How Partitioning Works (Step-by-Step)

Suppose our subarray is `[10, 80, 30, 90, 40, 50, 70]` and `low = 0`, `high = 6`.

* We choose `pivot = 70` (the element at index 6).
* Initialize `i = low − 1 = −1`.

Then loop `j` from `0` to `5` (high−1):

| j | arr\[j] | Compare to pivot (=70) | Action                             | i after action |
| - | ------- | ---------------------- | ---------------------------------- | -------------- |
| 0 | 10      | 10 < 70                | i=0 → swap(arr\[0], arr\[0]) no-op | 0              |
| 1 | 80      | 80 ≥ 70                | do nothing                         | 0              |
| 2 | 30      | 30 < 70                | i=1 → swap(arr\[1], arr\[2])       | 1              |
| 3 | 90      | 90 ≥ 70                | do nothing                         | 1              |
| 4 | 40      | 40 < 70                | i=2 → swap(arr\[2], arr\[4])       | 2              |
| 5 | 50      | 50 < 70                | i=3 → swap(arr\[3], arr\[5])       | 3              |

After that loop, `i = 3`. The subarray looks like this:

```
[10, 30, 40, 50, 90, 80, 70]
  ^   ^   ^   ^   ^   ^   ^
 0   1   2   3   4   5   6
```

Here indices `0..3` hold values `< 70`. Indices `4..5` hold values `≥ 70`. Finally, swap `arr[i+1]` with `arr[high]`:

* `swap(arr[4], arr[6])` → swap 90 and 70.

Now the pivot `70` sits at index `4`. Everything left of index 4 is `< 70`, everything right is `≥ 70`. Partition returns `4`.

---

### Recursion / Subarray Sorting

* After partition, pivot = 70 is “in place” at index 4.
* Recursively sort the two halves:

  1. `quickSort(arr, 0, 3)` → sorts `[10, 30, 40, 50]`
  2. `quickSort(arr, 5, 6)` → sorts `[80, 90]`
* Each of those recursively partitions further until all subarrays have length ≤ 1.

---

## Time & Space Complexity

* **Average‐Case Time Complexity:**

  * Partitioning an n‐element subarray takes O(n) work.
  * On average, the pivot roughly splits the subarray into two subarrays of size n/2.
  * So the recurrence is

    $$
      T(n) = T(n/2) + T(n/2) + O(n) \quad\Longrightarrow\quad T(n) = O(n \log n).
    $$
  * More precisely, because Quick Sort’s average pivot choice is around the median, the expected time to completely sort is **O(n log n)**.
* **Worst‐Case Time Complexity:**

  * If the pivot is always chosen poorly (e.g. the array is already sorted and pivot = last element), then each partition call only adds one element in its final place, leaving subproblems of size `n−1` and `0`.
  * That recurrence is

    $$
      T(n) = T(n−1) + O(n),  
    $$

    which solves to **O(n²)**.
* **Space Complexity:**

  * Quick Sort is done in‐place, so aside from the input array, only a small number of local variables are used.
  * However, the **recursion stack** can go as deep as the height of the recursion tree:

    * **Average case:** Recursion depth is O(log n), so extra space is O(log n).
    * **Worst case:** Recursion depth becomes O(n) (because every pivot lands at one end), so extra space is O(n).

---

## Practical Tips

* **Pivot Choice Matters.** Always picking the last element is easy to code, but if your data is already nearly sorted, that consistently picks a “bad” pivot and degrades to O(n²).

  * Common strategies to reduce that risk:

    1. **Random Pivot:** Before each partition, swap a randomly chosen index into the `high` position.
    2. **Median‐of‐Three:** Look at `arr[low], arr[mid], arr[high]`, choose their median as the pivot.

* **In‐Place vs. Stable.** Standard Quick Sort is **in-place** (needs no extra array) but **not stable** (equal elements may reorder). If stability is required, other sorting algorithms (e.g. Merge Sort) are better choices.

* **Tail Recursion Optimization.** You can always recurse on the smaller half first and then loop on the larger half to keep the recursion depth at O(log n) even in some unbalanced cases. Many libraries implement that to bound stack usage.

---

### Summary

```java
public class A01QuickSort {
  public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
      int pivotIndex = partition(arr, low, high);
      quickSort(arr, low, pivotIndex - 1);
      quickSort(arr, pivotIndex + 1, high);
    }
  }

  private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (arr[j] < pivot) {
        i++;
        swap(arr, i, j);
      }
    }
    swap(arr, i + 1, high);
    return i + 1;
  }

  private void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }

  public static void main(String[] args) {
    A01QuickSort sorter = new A01QuickSort();
    int[] arr = {10, 80, 30, 90, 40, 50, 70};
    sorter.quickSort(arr, 0, arr.length - 1);
    // Now arr == [10, 30, 40, 50, 70, 80, 90]
  }
}
```

* **Average Time:** O(n log n)
* **Worst‐Case Time:** O(n²) (if pivot is always the smallest or largest element)
* **Space (Recursion Stack):** O(log n) on average, O(n) worst‐case

Quick Sort is one of the fastest comparison‐sorts in practice (with good pivot selection) and is widely used in standard libraries (often with enhancements to guarantee roughly O(n log n) in real‐world data).
