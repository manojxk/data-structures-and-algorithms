**Problem Restatement**
You are given an integer array and a target value `toMove`. You must rearrange the array **in-place** so that **all occurrences** of `toMove` appear at the **end** of the array, while there is no need for preserving the relative order of all **other** elements.

> **Example**
> Input:
> `array = [2, 1, 2, 2, 2, 3, 4, 2]`,
> `toMove = 2`
>
> Output (one valid arrangement):
> `[1, 3, 4, 2, 2, 2, 2, 2]`
>
> Here, every `2` has been moved to the rightmost positions, and the numbers `[1, 3, 4]` retain their original relative order.

---

## Brute‐Force Approach (O(n) Time • O(n) Space)

1. **Create** a new array `result` of the same length.
2. **First pass:** copy every element `x != toMove` into `result`, in order.
3. **Second pass:** fill the remaining slots of `result` with `toMove`.
4. **Copy** `result` back into the original array.

This ensures all non‐`toMove` elements appear first (in their original order), then all `toMove` elements. However, it uses **O(n)** extra space.

```java
public static int[] moveElementToEndBruteForce(int[] array, int toMove) {
  int n = array.length;
  int[] result = new int[n];
  int idx = 0;

  // 1) Copy every non-toMove into result
  for (int x : array) {
    if (x != toMove) {
      result[idx++] = x;
    }
  }

  // 2) Fill the rest with toMove
  while (idx < n) {
    result[idx++] = toMove;
  }

  // 3) Copy back
  System.arraycopy(result, 0, array, 0, n);
  return array;
}
```

* **Time Complexity:** O(n) — two simple passes over the array.
* **Space Complexity:** O(n) — extra array to build the result.

---

## Optimized In‐Place Two‐Pointer Approach (O(n) Time • O(1) Space)

We can avoid any auxiliary array by using **two pointers**:

1. **Initialize:**

   * `left = 0`
   * `right = array.length - 1`

2. **Loop** while `left < right`:

   * **Move** `right` leftward as long as `array[right] == toMove` (because any `toMove` at the rightmost end is already “in place”).
   * **Move** `left` rightward as long as `array[left] != toMove` (because non‐`toMove` values belong on the left).
   * After these two scans, if `left < right`, then we know `array[left] == toMove` and `array[right] != toMove`.

     * **Swap** `array[left]` with `array[right]`.
     * Increment `left++` and decrement `right--`, and continue.

By always swapping a `toMove` from the left with a non‐`toMove` from the right, we push all `toMove` elements toward the end, and each non‐`toMove` automatically shifts left. The relative order of non‐`toMove` elements is preserved because we never change their positions relative to each other—each swap only moves a `toMove` further right.

```java
public static int[] moveElementToEnd(int[] array, int toMove) {
  int left = 0;
  int right = array.length - 1;

  while (left < right) {
    // 1) Move 'right' leftward past any toMove values
    while (left < right && array[right] == toMove) {
      right--;
    }
    // 2) Move 'left' rightward past any non-toMove values
    while (left < right && array[left] != toMove) {
      left++;
    }
    // 3) If left < right, swap the toMove (at left) with non-toMove (at right)
    if (left < right) {
      int temp = array[left];
      array[left] = array[right];
      array[right] = temp;
      left++;
      right--;
    }
  }
  return array;
}
```

### Why This Works

* **Whenever** you find a `toMove` at `array[left]` and a non‐`toMove` at `array[right]`, swapping pushes the non‐`toMove` left (preserving its relative order among other non‐`toMove` elements) and sends the `toMove` to the right.
* By continuously moving the `right` pointer leftward until it finds a non‐`toMove`, you ensure you never swap a `toMove` into the wrong spot.
* You only ever move each pointer in one direction—so there are at most **n** total pointer‐moves. Each swap also takes O(1).

---

## Complexity Analysis

* **Time Complexity:** O(n)

  * Each element is touched at most once by `left` or `right`.
  * The two‐pointer scans inside the loop cumulatively traverse the array only once.

* **Space Complexity:** O(1)

  * All operations are in‐place, using only two indices and one temporary variable for swapping.

---

## Full Java Class

```java
package medium.arrays;

import java.util.Arrays;

public class A03MoveElementToEnd {

  // Brute-Force approach (uses extra array)
  public static int[] moveElementToEndBruteForce(int[] array, int toMove) {
    int n = array.length;
    int[] result = new int[n];
    int idx = 0;

    // Place all non-toMove elements first
    for (int x : array) {
      if (x != toMove) {
        result[idx++] = x;
      }
    }
    // Then fill the rest with toMove
    while (idx < n) {
      result[idx++] = toMove;
    }
    // Copy back
    System.arraycopy(result, 0, array, 0, n);
    return array;
  }

  // Optimized in-place two-pointer approach
  public static int[] moveElementToEnd(int[] array, int toMove) {
    int left = 0;
    int right = array.length - 1;

    while (left < right) {
      // Step 1: Move right pointer leftward past any toMove values
      while (left < right && array[right] == toMove) {
        right--;
      }
      // Step 2: Move left pointer rightward past any non-toMove values
      while (left < right && array[left] != toMove) {
        left++;
      }
      // Step 3: If left < right, swap the toMove at left with non-toMove at right
      if (left < right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
        left++;
        right--;
      }
    }
    return array;
  }

  // Main method to test the implementation
  public static void main(String[] args) {
    int[] array = {2, 1, 2, 2, 2, 3, 4, 2};
    int target = 2;

    // Using the optimized in-place method
    int[] result = moveElementToEnd(array, target);
    System.out.println("After moving target to end: " + Arrays.toString(result));
    // Expected output: [1, 3, 4, 2, 2, 2, 2, 2]
  }
}
```

This two-pointer solution runs in **O(n)** time and uses **O(1)** extra space, making it optimal for moving all instances of a target to the array’s end while preserving the order of other elements.
