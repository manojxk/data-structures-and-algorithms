**Problem Overview**

Bubble Sort is one of the simplest sorting algorithms. Given an array of numbers, it repeatedly steps through the array, compares **adjacent** pairs of elements, and **swaps** them if they are in the wrong order. After each full pass through the array, the **largest** unsorted element has “bubbled up” to its correct position at the end. You then repeat on the remaining unsorted portion until no more swaps are needed.

---

## Why Bubble Sort Works

1. **Adjacent Swaps**
   By only swapping neighbors, we gradually move larger elements rightward and smaller elements leftward.

2. **Multiple Passes**
   After the first pass, the **single largest** element is guaranteed to be at the end. After the second, the **second-largest** is in place, and so on.

3. **Early Exit Optimization**
   If you make a full pass without performing **any** swaps, the array is already sorted—you can stop early.

---

## Step-by-Step Approach

1. **Determine Pass Count**
   You’ll need at most **n−1** passes for an array of length **n** (after that, everything must be sorted).

2. **Inner Loop**
   For pass **i** (starting at 0), compare indices

   $$
     j = 0, 1, \dots, (n - 2 - i)
   $$

   Compare `arr[j]` to `arr[j+1]`. If `arr[j] > arr[j+1]`, **swap** them.

3. **Track Swaps**
   Use a boolean `swapped`. Set it to `true` whenever you swap. If after a full pass it remains `false`, **break** early.

4. **Reduce Work Each Pass**
   Because the last **i** elements are already in place after **i** passes, you only need to go up to `n−1−i` in the inner loop.

---

## Java Implementation

```java
package easy.sorting;

public class A01BubbleSort {

  /** 
   * Sorts the array in-place using Bubble Sort.
   *
   * @param arr the array to sort
   * Time Complexity:
   *   - Worst/Average: O(n^2)
   *   - Best (already sorted): O(n)
   * Space Complexity: O(1) — in-place
   */
  public void bubbleSort(int[] arr) {
    int n = arr.length;

    // Outer loop: up to n-1 passes
    for (int i = 0; i < n - 1; i++) {
      boolean swapped = false;

      // Inner loop: bubble the largest of the unsorted portion to the end
      for (int j = 0; j < n - 1 - i; j++) {
        if (arr[j] > arr[j + 1]) {
          // Swap adjacent elements
          int temp = arr[j];
          arr[j]   = arr[j + 1];
          arr[j + 1] = temp;
          swapped = true;
        }
      }

      // If no swaps occurred, the array is sorted
      if (!swapped) {
        break;
      }
    }
  }

  // Demo
  public static void main(String[] args) {
    A01BubbleSort sorter = new A01BubbleSort();
    int[] arr = {64, 34, 25, 12, 22, 11, 90};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    sorter.bubbleSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }
}
```

---

## Walk-Through Example

Array: `[64, 34, 25, 12, 22, 11, 90]`

* **Pass 1** (`i=0`, compare up to index 5):

  * Swap `64` & `34` → `[34, 64, ...]`
  * Swap `64` & `25` → `[34, 25, 64, ...]`
  * … eventually `90` stays at end.
* **Pass 2** (`i=1`, compare up to index 4), you bubble the next-largest (`64`) into its place.
* Continue until no swaps occur.

After all necessary passes, you end up with `[11, 12, 22, 25, 34, 64, 90]`.

---

### Complexity Recap

* **Time Complexity**

  * **Worst/Average:** O(n²) — two nested loops
  * **Best:** O(n) — if the array is already sorted (no swaps in the first pass)

* **Space Complexity:**

  * O(1) — in-place sorting with only a few extra variables

Bubble Sort is **easy to understand** and implement, but due to O(n²) time it’s only practical for small arrays or as an educational tool.
