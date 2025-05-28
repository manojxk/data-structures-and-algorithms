**Problem Restatement**

You have an unsorted array of integers, for example:

```
[64, 25, 12, 22, 11]
```

You need to sort it in **ascending** order **in place**, without using any extra arrays. After sorting, this array should become:

```
[11, 12, 22, 25, 64]
```

---

## Selection Sort: Intuition

1. **Divide** the array into a **sorted** portion (initially empty) on the left and an **unsorted** portion on the right.
2. **Repeatedly** pick the **smallest** element from the unsorted portion and **swap** it into the next position of the sorted portion.
3. Each swap **grows** the sorted portion by one element, and **shrinks** the unsorted portion by one.

Visually, after the first pass, the smallest element lands at index 0. After the second pass, the second-smallest lands at index 1, and so on, until the whole array is sorted.

---

## Step-by-Step Algorithm

Given array `arr` of length `n`:

1. **Loop** `i` from `0` to `n−2` (we don’t need to place anything for the last index, it’s implied):

   * **Assume** the smallest element in the unsorted portion lives at `minIndex = i`.
   * **Scan** the unsorted portion `j` from `i+1` to `n−1`:

     * If `arr[j] < arr[minIndex]`, update `minIndex = j`.
   * **Swap** `arr[i]` and `arr[minIndex]`.

     * Now the element at `i` is the correct iᵗʰ smallest.
2. **Repeat** until `i` reaches `n−2`. At that point, the array is fully sorted.

---

## Java Implementation

```java
package easy.sorting;

public class A03SelectionSort {

  /**
   * Sorts the array in-place using Selection Sort.
   *
   * @param arr  the array to sort
   */
  public void selectionSort(int[] arr) {
    int n = arr.length;

    // For each position i in the array:
    for (int i = 0; i < n - 1; i++) {
      // 1) Find the index of the smallest element in arr[i..n-1]
      int minIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (arr[j] < arr[minIndex]) {
          minIndex = j;
        }
      }

      // 2) Swap the smallest found with the element at i
      int temp    = arr[minIndex];
      arr[minIndex] = arr[i];
      arr[i]        = temp;
    }
  }

  // Demo
  public static void main(String[] args) {
    A03SelectionSort sorter = new A03SelectionSort();
    int[] arr = {64, 25, 12, 22, 11};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    sorter.selectionSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
    // Expected output: 11 12 22 25 64
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * You have an **outer loop** that runs $n-1$ times.
  * For each $i$, you scan the remaining $n-i-1$ elements to find the minimum.
  * Total work is $\sum_{i=0}^{n-2}(n-i-1) = \frac{n(n-1)}{2}$, which is **O(n²)**.

* **Space Complexity:**

  * **O(1)** — sorting is done in place with only a couple of temporary variables (`minIndex` and `temp`).

---

### Key Points for a Beginner

* **In-place**: No extra arrays are created; you only swap within the original.
* **Unstable**: If two equal elements exist, their relative order might change after sorting.
* **Predictable**: Always O(n²), regardless of initial order, but simple to implement and understand.

While Selection Sort isn’t efficient for large $n$, it’s a great way to grasp basic sorting mechanics and in-place algorithms.
