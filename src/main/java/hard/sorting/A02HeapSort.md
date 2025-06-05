**Problem Statement**
You are given an array of integers (for example: `[12, 11, 13, 5, 6, 7]`). Your task is to **sort** this array in ascending order using **Heap Sort**, an in‐place comparison‐based sorting algorithm that relies on a binary heap data structure. The final output for the example should be `[5, 6, 7, 11, 12, 13]`.

In other words, you need to rearrange all elements in the given array so that they appear from smallest to largest, but you must do so by first turning the array into a **max‐heap**, and then repeatedly extracting the largest “root” element, swapping it to the end, and re‐heapifying the remaining portion.

---

## 1. What Is a “Heap”?

A **binary heap** is a nearly complete binary tree (i.e., all levels are full except possibly the last, which is filled from left to right) that satisfies the **heap property**:

* In a **max‐heap**, every parent node’s value is **greater than or equal to** the values of its children.
* (In a **min‐heap**, every parent is less than or equal to its children, but we’ll focus on max‐heaps here.)

When we store a binary heap in an array of length *n*, we use these index relationships (0‐based indexing):

* The node at index `i` is a parent.
* Its left child is at index `2·i + 1`.
* Its right child is at index `2·i + 2`.
* Conversely, any node at index `j` has a parent at `floor((j – 1) / 2)`.

Building a max‐heap out of an unsorted array is the first crucial step. Once you have a max‐heap, the largest element in the entire structure is guaranteed to be at index 0 (“the root”).

---

## 2. High‐Level Steps of Heap Sort

1. **Build a max‐heap** from the input array.
2. **Repeatedly extract** the maximum element (which is at index 0) and move it to the end of the array by swapping:

   * Swap `arr[0]` with `arr[i]`, where `i` starts at `n – 1` and decrements after each extraction.
   * Now the largest element is “locked” at `arr[i]`, outside the active heap region.
3. **Reduce** the considered heap size by 1 (ignore the last position since it’s already in the correct place).
4. **Heapify** (or “sift down”) the new root at `arr[0]` to restore the max‐heap property among the remaining (unsorted) region `arr[0..i – 1]`.
5. Repeat steps 2–4 until the heap size is 1 (no more extraction is needed). At that point, all elements are in ascending order in the array.

Because each extraction plus heapify costs `O(log n)`, and we do it *n* times, the overall time is `O(n log n)`. Building the initial heap in step 1 can be done in `O(n)` time by sifting‐down from the last non‐leaf node down to index 0.

---

## 3. Detailed Explanation for Beginners

Below is the step‐by‐step logic, plus fully commented Java code. If you are new to heaps or in‐place sorting, read carefully!

### 3.1. Building a Max‐Heap

1. We treat the input array itself as if it were a binary tree.
2. Starting from the last non‐leaf node (index `n/2 – 1`) down to index 0, we run a **heapify** (sift‐down) operation:

   * For each node at index `i`, “push” its value down the tree until the max‐heap property is satisfied in that subtree.
   * In code, that means checking the left and right child’s values, swapping the parent with the larger child if necessary, and continuing downward.

Why start at `n/2 – 1`? Because indices `n/2` through `n – 1` are all leaves (they have no children), so a leaf is already trivially a valid heap. Once we finish heapifying every parent node from bottom to top, the entire array represents a valid max‐heap.

### 3.2. Heapify (Sift‐Down) Operation

Suppose you have an array `arr[]` of length *n*. You want to ensure that the subtree rooted at index `i` is a max‐heap, under the assumption that both its left and right subtrees are already max‐heaps. The steps are:

1. Let `largest = i`.
2. Compute `left = 2*i + 1` and `right = 2*i + 2`.
3. If `left < heapSize && arr[left] > arr[largest]`, set `largest = left`.
4. If `right < heapSize && arr[right] > arr[largest]`, set `largest = right`.
5. If `largest != i`, swap `arr[i]` with `arr[largest]`, then recursively heapify at index `largest`.

In other words, if either child is bigger than the parent, swap the parent with the largest child, and continue heapifying down that child’s position. This pushes the smaller value downward until the heap property is restored.

### 3.3. Sorting Phase

Once you have a max‐heap:

1. The largest element is at `arr[0]`.
2. Swap `arr[0]` with `arr[n – 1]`. Now `arr[n – 1]` holds the largest value and will remain there forever.
3. Reduce the “active heap size” to `n – 1`. That means the heapify calls will only consider `arr[0..(n – 2)]`.
4. Call heapify on index 0 to fix the root in the reduced heap region. Now `arr[0]` is the next‐largest element among `arr[0..n – 2]`.
5. Repeat: swap `arr[0]`↔`arr[n – 2]`, decrease heap size to `n – 2`, heapify at 0, and so on—until the heap size becomes 1.

When you finish, the array is sorted from smallest (at index 0) to largest (at index n – 1).

---

## 4. Complete Java Implementation

```java
package hard.sorting;

public class A02HeapSort {

  /**
   * Public method to sort an array in ascending order using Heap Sort.
   * This method transforms the array into a max‐heap, then repeatedly
   * extracts the largest element into its correct final position.
   *
   * @param arr The array of integers to be sorted in place.
   */
  public void heapSort(int[] arr) {
    int n = arr.length;

    // 1) Build a max‐heap from the array (heapify every non‐leaf node).
    //    The last non‐leaf node is at index (n/2 - 1). We go down to index 0.
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(arr, n, i);
    }

    // 2) Extract the maximum (root of the heap) one by one,
    //    moving it to the “end” of the array, then fixing the heap.
    for (int i = n - 1; i > 0; i--) {
      // (a) Swap the current root (largest) with arr[i]
      swap(arr, 0, i);

      // (b) Now the heap size is effectively reduced by 1 (i.e., i)
      //     Call heapify on the new root to restore the max‐heap
      heapify(arr, i, 0);
    }
  }

  /**
   * Heapify—“sift down” the node at index i in arr[0..heapSize-1], 
   * assuming its left and right subtrees are already max‐heaps.
   *
   * @param arr       The array (representing a binary tree/heap).
   * @param heapSize  The number of elements in the heap portion (may be < arr.length).
   * @param i         The root index to heapify from.
   */
  private void heapify(int[] arr, int heapSize, int i) {
    int largest = i;            // Initialize largest as root
    int left = 2 * i + 1;       // left child index
    int right = 2 * i + 2;      // right child index

    // If left child exists and is greater than root:
    if (left < heapSize && arr[left] > arr[largest]) {
      largest = left;
    }

    // If right child exists and is greater than current largest:
    if (right < heapSize && arr[right] > arr[largest]) {
      largest = right;
    }

    // If largest is not the root, swap and continue heapifying the affected subtree
    if (largest != i) {
      swap(arr, i, largest);
      // Recursively heapify the subtree rooted at the swapped child
      heapify(arr, heapSize, largest);
    }
    // Otherwise, if largest == i, everything under i already satisfies max‐heap
  }

  /** Simple helper to swap two elements in an array. */
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  /** Main method to test the heapSort implementation. */
  public static void main(String[] args) {
    A02HeapSort sorter = new A02HeapSort();

    // Example array
    int[] arr = {12, 11, 13, 5, 6, 7};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();

    // Perform Heap Sort
    sorter.heapSort(arr);

    System.out.println("Sorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  /* 
   * Time Complexity:
   *   - Building the initial max‐heap (phase 1) takes O(n) time. 
   *     (Although it seems like n/2 calls to heapify—each of cost O(log n)—the aggregate is O(n).)
   *   - Extracting one element and re‐heapifying takes O(log n), done n – 1 times → O(n log n).
   *   - Overall: O(n + n log n) = O(n log n).
   *
   * Space Complexity:
   *   - O(1) extra space. Heap Sort is in‐place: aside from a few local variables,
   *     we do not allocate any extra arrays.
   */
}
```

---

## 5. Walkthrough on the Example `[12, 11, 13, 5, 6, 7]`

1. **Initial array**:

   ```
   [12, 11, 13, 5, 6, 7]
   ```

2. **Build max‐heap**

   * `n = 6`, so last non‐leaf index is `6/2 – 1 = 2`. We call `heapify(arr, 6, 2)`, then `heapify(arr, 6, 1)`, then `heapify(arr, 6, 0)` in that order.

   * i = 2:

     * Node at index 2 has value `13`. Its children are at indices `5` and `6` (but index 6 is out of bounds, so only child is `arr[5]=7`). Since `13 > 7`, the subtree is already a valid max‐heap.

   * i = 1:

     * Node at index 1 is `11`. Its children: left `arr[3]=5`, right `arr[4]=6`. Both children are smaller than 11, so no swap—still max‐heap in that subtree.

   * i = 0:

     * Node at index 0 is `12`. Its children: `arr[1]=11` and `arr[2]=13`. Among these, `13` is largest. So swap `12` with `13`. Now the array becomes:

       ```
       [13, 11, 12, 5, 6, 7]
       ```
     * After swapping, we must heapify the subtree rooted at index 2 (where we moved `12`). Node 2 is now `12`, children (indices 5 and 6): `arr[5]=7` (only valid child). Since 12 > 7, no further swap is needed.

   * Final max‐heap representation in array form (level‐order) is:

     ```
     [13, 11, 12, 5, 6, 7]
     ```

     which corresponds to the tree:

     ```
             13
           /    \
         11      12
        /  \    /
       5    6  7
     ```

3. **Sorting phase**

   * **Iteration i = 5** (heap size = 6 → 5 after extraction)

     * Swap root `arr[0]=13` with `arr[5]=7`:

       ```
       [7, 11, 12, 5, 6, 13]
       ```
     * Now “lock” 13 at the end. Reduce heap size to 5 (ignore index 5 onward).
     * Call `heapify(arr, 5, 0)` on `[7, 11, 12, 5, 6]`:

       * Node 0 is `7`, children are `arr[1]=11` and `arr[2]=12`. The larger child is `12` (index 2). Swap `7`↔`12`:

         ```
         [12, 11, 7, 5, 6, 13]
         ```
       * Now heapify subtree at index 2 (where 7 landed): children would be `arr[5]` and `arr[6]`, but both are out of the reduced heap (heap size is 5), so no further action.
     * After this step, the 5-element heap is `[12, 11, 7, 5, 6]`. Locked area is `[13]`.

   * **Iteration i = 4** (heap size = 5 → 4 after extraction)

     * Swap `arr[0]=12` with `arr[4]=6`:

       ```
       [6, 11, 7, 5, 12, 13]
       ```
     * “Lock” 12 at index 4. Now heap size = 4.
     * `heapify(arr, 4, 0)` on `[6, 11, 7, 5]`:

       * Node 0 is `6`, children `arr[1]=11`, `arr[2]=7`. Larger child is `11` (index 1). Swap:

         ```
         [11, 6, 7, 5, 12, 13]
         ```
       * Now heapify at index 1 (value `6`), children are at indices 3 and 4—but index 4 is out of reduced heap (size 4). Only child is `arr[3]=5`. Since `6 > 5`, no swap needed.
     * Reduced heap is `[11, 6, 7, 5]`. Locked region is `[12, 13]`.

   * **Iteration i = 3** (heap size = 4 → 3 after extraction)

     * Swap `arr[0]=11` with `arr[3]=5`:

       ```
       [5, 6, 7, 11, 12, 13]
       ```
     * Lock 11 at index 3. Heap size = 3.
     * `heapify(arr, 3, 0)` on `[5, 6, 7]`:

       * Node 0 is `5`, children `arr[1]=6`, `arr[2]=7`. Larger child is `7` (index 2). Swap:

         ```
         [7, 6, 5, 11, 12, 13]
         ```
       * Heapify at index 2 (value `5`). Its children would be indices 5 and 6—both are out of reduced heap (size 3). No action.
     * Reduced heap is `[7, 6, 5]`; locked region `[11, 12, 13]`.

   * **Iteration i = 2** (heap size = 3 → 2 after extraction)

     * Swap `arr[0]=7` with `arr[2]=5`:

       ```
       [5, 6, 7, 11, 12, 13]
       ```
     * Lock 7 at index 2. Heap size = 2.
     * `heapify(arr, 2, 0)` on `[5, 6]`:

       * Node 0 is `5`, children: only `arr[1]=6` (right child would be index 2, out of heap). Since `6 > 5`, swap:

         ```
         [6, 5, 7, 11, 12, 13]
         ```
       * No further children to check.
     * Reduced heap is `[6, 5]`; locked `[7, 11, 12, 13]`.

   * **Iteration i = 1** (heap size = 2 → 1 after extraction)

     * Swap `arr[0]=6` with `arr[1]=5`:

       ```
       [5, 6, 7, 11, 12, 13]
       ```
     * Lock 6 at index 1. Now heap size = 1.
     * A one‐element heap is already valid; no heapify needed.
     * Final locked region is `[6, 7, 11, 12, 13]`. The only remaining element in the heap is `arr[0]=5`, which is already in correct place.

4. **Result**
   After all those steps, the array is fully sorted:

   ```
   [5, 6, 7, 11, 12, 13]
   ```

---

## 6. Time & Space Complexity

* **Time Complexity**

  1. **Building the initial max‐heap** takes **O(n)** time in total. Although we call `heapify` from index `n/2 – 1` down to `0`, each `heapify` call costs at most `O(log n)`, but the majority of nodes are near the leaves and cost far less—ultimately the sum is `O(n)`.
  2. **Extracting the max n–1 times**: each extraction swaps the root and then calls `heapify` with a reduced heap size. Each `heapify` is `O(log n)` in the worst case. Doing this *n* times is `O(n log n)`.
  3. Overall: **O(n + n log n) = O(n log n)**.

* **Space Complexity**

  * We only used the original array itself (in‐place) and a small number of integer variables for indexing and swapping. No auxiliary arrays or data structures of size proportional to *n* are used.
  * Thus, extra space is **O(1)**.

---

## 7. Full Java Code Recap

Below is the final code in one place. Feel free to copy/paste and run it as-is.
Everything from building the heap, sifting down, swapping, and sorting is commented for clarity.

```java
package hard.sorting;

public class A02HeapSort {

  /**
   * Sorts the input array in ascending order using Heap Sort.
   *
   * @param arr The array of integers to be sorted in place.
   */
  public void heapSort(int[] arr) {
    int n = arr.length;

    // STEP 1: Build the max‐heap in the array
    // The last non‐leaf node index is (n/2 - 1). We heapify from that index down to 0.
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(arr, n, i);
    }

    // STEP 2: Extract max one by one (move it to end), then fix the heap each time
    for (int i = n - 1; i > 0; i--) {
      // 2a) Move current root (largest element) to the end of the array
      swap(arr, 0, i);

      // 2b) Call heapify on the reduced heap (size = i)
      heapify(arr, i, 0);
    }
  }

  /**
   * Ensures the subtree rooted at index i in arr[0..heapSize-1] satisfies max‐heap property,
   * assuming its left and right subtrees (if they exist) already do.
   *
   * @param arr      The array representing the heap.
   * @param heapSize The length of the heap portion (only arr[0..heapSize-1] is considered).
   * @param i        The root index of the subtree to heapify.
   */
  private void heapify(int[] arr, int heapSize, int i) {
    int largest = i;          // Initialize largest as root
    int left = 2 * i + 1;     // left child index
    int right = 2 * i + 2;    // right child index

    // If left child exists and is greater than root:
    if (left < heapSize && arr[left] > arr[largest]) {
      largest = left;
    }

    // If right child exists and is greater than current largest:
    if (right < heapSize && arr[right] > arr[largest]) {
      largest = right;
    }

    // If the largest is not i, swap and continue heapifying down
    if (largest != i) {
      swap(arr, i, largest);
      heapify(arr, heapSize, largest);
    }
    // If largest == i, that subtree is already a valid max‐heap
  }

  /** Swaps two elements in the array. */
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public static void main(String[] args) {
    A02HeapSort sorter = new A02HeapSort();

    int[] arr = {12, 11, 13, 5, 6, 7};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();

    sorter.heapSort(arr);

    System.out.println("Sorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    System.out.println();
  }

  /*
   * Time Complexity:
   *   - Building the max‐heap: O(n)
   *   - Extracting max and heapifying n–1 times: O(n log n)
   *   → Total: O(n log n).
   *
   * Space Complexity:
   *   - O(1) extra space (in‐place sort).
   */
}
```

---

### Final Remarks

* **Heap Sort** always runs in `O(n log n)` time, regardless of the initial order of the elements (no “best” or “worst” beyond that).
* It is **in‐place** (uses only constant extra space), unlike Merge Sort which requires O(n) extra space.
* However, Heap Sort is not a **stable** sort (equal elements might not keep their original relative order).

By following the steps above—building a max‐heap and then repeatedly extracting the root—you can sort any integer array in ascending order with guaranteed `O(n log n)` performance and only `O(1)` extra memory.
