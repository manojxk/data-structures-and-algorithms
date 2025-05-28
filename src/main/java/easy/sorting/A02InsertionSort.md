**Problem Explanation**

Insertion Sort builds a sorted portion of the array one element at a time. Imagine you have a hand of playing cards: you pick up one card (the “key”) and insert it into its correct position among the cards you’re already holding (which are kept sorted). You repeat this for each card until all cards (elements) are in order.

---

## Step-by-Step Approach

1. **Divide** the array virtually into a **sorted** part (initially just the first element) and an **unsorted** part (the rest).
2. **Iterate** `i` from `1` to `n−1` (where `n` is the array length):

   * Let `key = arr[i]`.
   * Let `j = i−1`, the index of the last element in the sorted portion.
3. **Shift** all elements in the sorted portion that are **greater than** `key` one position to the **right**:

   ```java
   while (j >= 0 && arr[j] > key) {
     arr[j + 1] = arr[j];
     j--;
   }
   ```
4. **Insert** `key` into its correct spot, which is `j+1` after the loop ends:

   ```java
   arr[j + 1] = key;
   ```
5. After processing every `i`, the entire array becomes sorted.

---

## Java Implementation

```java
package easy.sorting;

public class A02InsertionSort {

  /**
   * Sorts the array in-place using Insertion Sort.
   *
   * Time Complexity:
   * - Worst/Average Case: O(n^2)
   * - Best Case (already sorted): O(n)
   *
   * Space Complexity: O(1) — in-place
   */
  public void insertionSort(int[] arr) {
    int n = arr.length;

    // 1) Iterate from the second element through the end
    for (int i = 1; i < n; i++) {
      int key = arr[i];   // the element to insert
      int j   = i - 1;    // last index of the sorted portion

      // 2) Shift elements greater than key to the right
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        j--;
      }

      // 3) Place key into its correct location
      arr[j + 1] = key;
    }
  }

  // Demonstration
  public static void main(String[] args) {
    A02InsertionSort sorter = new A02InsertionSort();
    int[] arr = {12, 11, 13, 5, 6};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    sorter.insertionSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    // Expected output: 5 6 11 12 13
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**

  * **Worst/Average:** O(n²), because each insertion may shift up to i elements, summing to \~n²/2 operations.
  * **Best:** O(n), when the array is already sorted—no shifts, only one comparison per element.

* **Space Complexity:**

  * **O(1)**, since sorting is done in-place with only a few auxiliary variables (`key`, `j`, and loop counters).

Insertion Sort is simple to understand and efficient for **small** or nearly-sorted arrays, but for large random datasets its O(n²) time usually makes more advanced sorts (like Merge Sort or Quick Sort) a better choice.
