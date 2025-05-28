**Problem Restatement**

You have an unsorted array of at least three integers. You need to return the **three largest** numbers in **ascending order**, **without** sorting the original array.

For example, given

```java
[141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7]
```

you should return

```java
[18, 141, 541]
```

because 541, 141, and 18 are the three largest values in the array, and we list them from smallest to largest.

---

## Brute‐Force Approach

1. **Copy** the array so you don’t modify the original.
2. **Sort** the copy in **descending** order.
3. Take the **first three** elements from the sorted copy.
4. **Reverse** that three‐element list to get ascending order.

* **Time:** O(n log n) for sorting
* **Space:** O(n) for the copy

---

## Optimal O(n) | O(1) Solution

We can do it in **one pass** with **constant extra space** by keeping track of the current three largest values in a fixed‐size array:

1. Initialize an array `threeLargest` of size 3 to `{Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}`.
2. Iterate once through the input array. For each number `num`, compare it to the current three largest:

   * If `num` is larger than the **largest** (`threeLargest[2]`), shift the top two down and place `num` at index 2.
   * Else if `num` is larger than the **middle** (`threeLargest[1]`), shift the smallest up and put `num` at index 1.
   * Else if `num` is larger than the **smallest** (`threeLargest[0]`), replace index 0 with `num`.
3. At the end, `threeLargest` holds the three largest values in **ascending** order.

### Why It Works

* By always “inserting” a new candidate into its correct slot (and shifting the smaller ones down), we maintain the sorted order of the top three as we scan.
* We never sort the whole array—just a constant‐size buffer of three items.

---

## Java Implementation

```java
package easy.searching;

import java.util.Arrays;

public class ThreeLargestNumbers {

  /**
   * Returns the three largest numbers in the array in ascending order,
   * without sorting the input array.
   *
   * Time Complexity:  O(n) — one pass through the array
   * Space Complexity: O(1) — only a fixed array of size 3
   */
  public static int[] findThreeLargestNumbers(int[] array) {
    // This will hold the three largest in ascending order:
    int[] threeLargest = {
      Integer.MIN_VALUE,
      Integer.MIN_VALUE,
      Integer.MIN_VALUE
    };

    for (int num : array) {
      updateLargest(threeLargest, num);
    }

    return threeLargest;
  }

  // Inserts num into threeLargest if it belongs among the top three
  private static void updateLargest(int[] threeLargest, int num) {
    // Compare against the largest (index 2), middle (1), and smallest (0)
    if (num > threeLargest[2]) {
      shiftAndUpdate(threeLargest, num, 2);
    } else if (num > threeLargest[1]) {
      shiftAndUpdate(threeLargest, num, 1);
    } else if (num > threeLargest[0]) {
      shiftAndUpdate(threeLargest, num, 0);
    }
  }

  /**
   * Shifts elements to the left up to idx, then places num at threeLargest[idx].
   * For example, if idx==2:
   *   threeLargest[0] ← threeLargest[1]
   *   threeLargest[1] ← threeLargest[2]
   *   threeLargest[2] ← num
   */
  private static void shiftAndUpdate(int[] threeLargest, int num, int idx) {
    for (int i = 0; i < idx; i++) {
      threeLargest[i] = threeLargest[i + 1];
    }
    threeLargest[idx] = num;
  }

  // Demo
  public static void main(String[] args) {
    int[] array = {
      141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7
    };
    int[] result = findThreeLargestNumbers(array);
    System.out.println(Arrays.toString(result));
    // Expected output: [18, 141, 541]
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**
  We do **one pass** over the input array of length *n*, performing only constant‐time comparisons and assignments per element → **O(n)**.

* **Space Complexity:**
  We use exactly one extra array of size 3, plus a handful of variables → **O(1)**.
