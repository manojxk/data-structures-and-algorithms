**Problem Restatement**
Given an integer array `array` whose elements are known to be drawn only from a set of three distinct values (`order[0]`, `order[1]`, `order[2]`), rearrange `array` **in place** so that all occurrences of `order[0]` come first, then all occurrences of `order[1]`, and finally all occurrences of `order[2]`.

> **Example**
>
> ```
> array = [1, 0, 0, -1, -1, 0, 1, 1]
> order = [0, 1, -1]
> ```
>
> After sorting,
>
> ```
> array → [0, 0, 0, 1, 1, 1, -1, -1]
> ```

---

## Approach

1. **Count Occurrences**

   * Let `first = order[0]`, `second = order[1]`, `third = order[2]`.
   * Make a single pass over `array` and count how many times each of these three values appears. Call those counts `firstCount`, `secondCount`, `thirdCount`.

2. **Overwrite the Array in Three Phases**

   * In a second pass (actually, just a single loop that writes in three segments), place:

     1. `firstCount` copies of `first`,
     2. then `secondCount` copies of `second`,
     3. then `thirdCount` copies of `third`.
   * Because the array is known to contain only these three values, once you place exactly `firstCount + secondCount + thirdCount == array.length` elements, you’re done.

This is a two‐pass, in‐place approach, which costs $O(n)$ time and $O(1)$ extra space.

---

## Complete Java Code

```java
package medium.sorting;

/*
 Problem: Three Number Sort

 Given two arrays:
 1. array[]: an array containing only three distinct integer values (e.g. [0, 1, -1]).
 2. order[]: exactly those three distinct values in the desired output order.

 Rearrange `array[]` in place so that all elements equal to order[0] appear first,
 then all elements equal to order[1], and finally all elements equal to order[2].

 Time Complexity: O(n)
 Space Complexity: O(1)
*/

public class A01ThreeNumberSort {

  /**
   * Sorts the input `array` in place according to the three-value order given by `order`.
   *
   * @param array The integer array, guaranteed to contain only values from order[0], order[1], order[2].
   * @param order An array of length 3, specifying the desired order of values.
   */
  public void threeNumberSort(int[] array, int[] order) {
    int first  = order[0];
    int second = order[1];
    int third  = order[2];

    // 1) Count how many times each of the three values appears
    int firstCount = 0;
    int secondCount = 0;
    int thirdCount = 0;

    for (int num : array) {
      if (num == first) {
        firstCount++;
      } else if (num == second) {
        secondCount++;
      } else { // num == third
        thirdCount++;
      }
    }

    // 2) Overwrite array in three phases
    int idx = 0;

    // Place all 'first' values
    while (firstCount-- > 0) {
      array[idx++] = first;
    }

    // Place all 'second' values
    while (secondCount-- > 0) {
      array[idx++] = second;
    }

    // Place all 'third' values
    while (thirdCount-- > 0) {
      array[idx++] = third;
    }
  }

  // --- Test / Demonstration ---
  public static void main(String[] args) {
    A01ThreeNumberSort sorter = new A01ThreeNumberSort();

    int[] array = {1, 0, 0, -1, -1, 0, 1, 1};
    int[] order = {0, 1, -1};

    System.out.println("Original Array:");
    for (int num : array) {
      System.out.print(num + " ");
    }
    System.out.println();

    sorter.threeNumberSort(array, order);

    System.out.println("Sorted Array:");
    for (int num : array) {
      System.out.print(num + " ");
    }
    System.out.println();
    // Expected output: [0, 0, 0, 1, 1, 1, -1, -1]
  }
}
```

### How It Works

1. **Counting Phase (First Pass)**

   * We scan once through `array`.
   * Each time we see a value equal to `order[0]`, we increment `firstCount`; if it equals `order[1]`, increment `secondCount`; otherwise (it must be `order[2]`), increment `thirdCount`.

2. **Overwrite Phase (Second Pass)**

   * We reset an index `idx = 0`.
   * Fill the first `firstCount` slots in `array` with `order[0]`.
   * Then fill the next `secondCount` slots with `order[1]`.
   * Finally, fill the remaining `thirdCount` slots with `order[2]`.

Because we know the total length is exactly `firstCount + secondCount + thirdCount`, we end up writing every element exactly once, in the correct order.

---

### Complexity

* **Time Complexity:**

  * Counting pass: $O(n)$.
  * Overwriting pass: $O(n)$.
    $\implies O(n)$ overall.

* **Space Complexity:**

  * We only use a few integer counters and the input `array` itself is mutated.
    $\implies O(1)$ extra space.

This completes an in‐place, linear‐time “Three Number Sort” according to the given order.
