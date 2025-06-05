**Problem Statement**
You have an array in which every element is at most *k* positions away from its sorted position. In other words, if you took the sorted version of the array and looked at any element, it would lie somewhere within $k$ indices of its current place. Such an array is called **k‐sorted**. Your task is to rearrange this k‐sorted array so that it becomes fully sorted, using an efficient algorithm.

**Example**

* Let

  ```
  arr = [6, 5, 3, 2, 8, 10, 9],  k = 3
  ```
* We claim that each element is at most 3 positions away from where it should be in the sorted array. For instance:

  * In the fully sorted array `[2, 3, 5, 6, 8, 9, 10]`, the value `6` is at index 3. In the original, it is index 0—difference = 3 positions.
  * Likewise, `9` should be index 5 in sorted order but is at index 6 in the original—difference = 1 position ≤ 3.
* The goal is to transform `[6, 5, 3, 2, 8, 10, 9]` into `[2, 3, 5, 6, 8, 9, 10]`.

Because each element is “almost in place,” we can sort much faster than the usual $O(n \log n)$ for a completely arbitrary array. In fact, there is an $O(n \log k)$ solution using a **min‐heap** (priority queue).

---

## Intuition Behind the $O(n \log k)$ Algorithm

1. **Key Observation**
   Since every element is at most $k$ positions away from its correct sorted place, that means:

   * If you look at the first $k+1$ elements of the array, the *smallest* element of those must lie somewhere within those first $k+1$. In particular, that smallest element belongs at index 0 in the final sorted array.
   * After you remove whichever one was the smallest (and place it into index 0), the next smallest overall must lie somewhere among the next $k+1$ elements starting from index 1, and so on.

2. **Moving Window of Size $k+1$**

   * Think of taking a sliding window of length $k+1$ over the array. In each window, the *minimum* of that window must be the next item in the fully sorted array.
   * More concretely:

     * Cover indices $[0 \dots k]$. The element that belongs at final index 0 is the minimum in that range.
     * Once you “choose” that minimum and fix it at index 0, move the window right by 1: now consider indices $[1 \dots k+1]$. The min of that window must be the sorted value for index 1, and so on.

3. **Using a Min‐Heap**

   * We keep a min‐heap (priority queue) containing at most $k+1$ items at a time.
   * We first insert the first $k+1$ array elements into the min‐heap.
   * Then, at each step, we extract (pop) the smallest element from the heap and place it into the next position of the sorted output. After popping one, we push the next unseen element from the array into the heap, maintaining the heap’s size at $k+1$ until we run out of elements.
   * Once all input elements have been added at least once, we simply pop the remaining items out of the heap one by one to fill the remainder of the sorted array.

Because each insertion into or extraction from a heap of size at most $(k+1)$ costs $O(\log (k+1)) = O(\log k)$, and we do exactly $n$ such insertions + $n$ such removals total, the runtime becomes $O(n \log k)$. Space usage is $O(k)$ to hold the heap.

---

## Step‐by‐Step Solution

Below is a complete Java method that sorts a k‐sorted array in‐place using the described min‐heap approach. After the code, we’ll break down each section.

```java
import java.util.PriorityQueue;

public class A02KSortedArraySorter {

  /**
   * Sorts a k‐sorted array in place. That is, each element in arr[]
   * is at most k positions away from its correct position. After calling
   * this method, arr[] becomes fully sorted.
   *
   * Time Complexity: O(n log k)
   * Space Complexity: O(k)
   *
   * @param arr  The k‐sorted array of length n
   * @param k    Maximum distance any element is from its sorted position
   */
  public void sortKSortedArray(int[] arr, int k) {
    // 1) Create a min‐heap (Java’s PriorityQueue) that will hold at most k+1 elements
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    int n = arr.length;
    int index = 0;  // This will track the position in arr[] where we place sorted elements

    // 2) Build the initial heap with the first k+1 elements (or fewer if n < k+1)
    int limit = Math.min(n, k + 1);
    for (int i = 0; i < limit; i++) {
      minHeap.add(arr[i]);
    }

    // 3) For the rest of the array, do:
    //    - Pop the smallest from heap (this is the correct next sorted value)
    //    - Place it at arr[index], then index++ 
    //    - Push arr[i] (the new element) into the heap
    for (int i = k + 1; i < n; i++) {
      // Pop the smallest from the heap and put into arr[index]
      arr[index++] = minHeap.poll();
      // Push the next array element into our heap
      minHeap.add(arr[i]);
    }

    // 4) Now all n elements have been added to the heap at least once. The heap
    //    still contains k+1 (or fewer, as we near the end) items. Pop them all in order.
    while (!minHeap.isEmpty()) {
      arr[index++] = minHeap.poll();
    }
  }

  // ---------------------------------------------------------
  // For demonstration, we include a main() to test this method:
  // ---------------------------------------------------------
  public static void main(String[] args) {
    A02KSortedArraySorter sorter = new A02KSortedArraySorter();

    int[] arr = {6, 5, 3, 2, 8, 10, 9};
    int k = 3;

    System.out.println("Original k-sorted array:");
    printArray(arr);

    sorter.sortKSortedArray(arr, k);

    System.out.println("After sorting:");
    printArray(arr);
  }

  // Helper to print an array on one line
  private static void printArray(int[] a) {
    for (int x : a) {
      System.out.print(x + " ");
    }
    System.out.println();
  }
}
```

---

## In‐Depth Explanation

1. **Creating the Min‐Heap**

   ```java
   PriorityQueue<Integer> minHeap = new PriorityQueue<>();
   ```

   * Java’s `PriorityQueue` defaults to a min‐heap (i.e., `peek()` returns the smallest element).
   * We will ensure this heap never grows larger than $k+1$.

2. **Filling the Initial Window**

   ```java
   int limit = Math.min(n, k + 1);
   for (int i = 0; i < limit; i++) {
     minHeap.add(arr[i]);
   }
   ```

   * We push the first $k+1$ elements (or all elements if the array is shorter than $k+1$).
   * At this point, that heap contains the “first window” from `arr[0]` to `arr[k]`. The smallest in that window belongs at index `0` of the final sorted array.

3. **Sliding Through the Rest**

   ```java
   for (int i = k + 1; i < n; i++) {
     arr[index++] = minHeap.poll();   // pop the smallest → correct for arr[index]
     minHeap.add(arr[i]);             // add the next incoming element to keep heap size = k+1
   }
   ```

   * In each iteration,

     1. **Pop** the heap’s root (the smallest among the current $k+1$ items). That “smallest item” is in fact the proper next element in sorted order. We place it at `arr[index]`, then do `index++`.
     2. **Add** the next new array element `arr[i]` into the heap so that the heap remains size $k+1$ (until we run out of new elements to add).

   * After doing this for all `i = k … n−1`, every array element has been inserted exactly once, and we have extracted exactly `(n − (k+1))` elements from the heap so far, filling up `arr[0]` through `arr[n−(k+1) − 1]`.

4. **Draining the Last Remaining Elements**

   ```java
   while (!minHeap.isEmpty()) {
     arr[index++] = minHeap.poll();
   }
   ```

   * At this point, when `i = n−1`, we pushed in the last new element. Now the heap still contains some leftover items—specifically, it contains exactly $k+1$ items if $n > k$, or fewer if the array is small.
   * The smallest of those is the next in sorted order. We pop them one by one, each time placing into `arr[index]`, until the heap is empty. Because the heap always kept the smallest among all “unplaced” elements in its root, draining it in ascending order finishes sorting.

5. **Why This Works**

   * Because each element was at most $k$ positions away from where it belongs, it must appear within one of these sliding windows of size $k+1$ at the time it needs to be placed.
   * Every time we extract from the min‐heap, we are guaranteed to be pulling out the correct next‐smallest element among all that remain to be placed.
   * Thus the final array becomes completely sorted.

---

## Complexity Analysis

* **Time Complexity**

  * We perform exactly $n$ heap‐insertions (each is $O(\log (k+1)) = O(\log k)$) and $n$ heap‐deletions (each `poll()` is also $O(\log k)$).
  * Total work: $O(n \log k)$.

* **Space Complexity**

  * The heap itself holds at most $k+1$ elements, so it uses $O(k)$ extra space.
  * We sort “in place” within the original `arr[]` (aside from the heap).

Thus, if $k$ is much smaller than $n$, this runs far faster than a full $O(n \log n)$ sort.

---

## Summary

1. Because each element is at most $k$ positions away, the smallest among any “block” of $k+1$ items must be the next correct item in sorted order.
2. We keep a min‐heap of size at most $k+1$.

   * Insert the first $k+1$ items into the heap.
   * Then repeat for each remaining item:

     * Poll the heap’s root → that’s the next sorted element → store it in `arr[index++]`.
     * Push the next new array element into the heap to keep its size at $k+1$.
   * Finally, after all elements are in the heap at least once, poll the remaining $k$ (or fewer) items to finish.
3. Each heap operation is $O(\log k)$, and we do $2n$ such operations in total (n inserts, n polls).
4. Hence overall **$O(n \log k)$** time and **$O(k)$** extra space.

This completes a clear, “beginner‐friendly” explanation and step‐by‐step Java implementation of sorting a k‐sorted array in $O(n \log k)$ time.
