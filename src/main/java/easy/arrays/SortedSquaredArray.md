**Problem Explanation**

You have a **sorted** array of integers (in ascending order), e.g.:

```
[1, 2, 3, 5, 6, 8, 9]
```

Your task is to produce a **new** array containing the **squares** of each number, but still **sorted** in ascending order. For the example above:

* Squaring each element gives `[1, 4, 9, 25, 36, 64, 81]`.
* That array is already sorted, so that’s your answer.

---

## Approaches

### 1. Brute-Force: Square & Sort

1. **Square every element** → O(n) work.
2. **Sort** the resulting array → O(n log n) work.

* **Time:** O(n) + O(n log n) ⇒ **O(n log n)**
* **Space:** O(n) to store the squared values

### 2. Optimized Two-Pointer: O(n) Time

Because the original array is sorted, the **largest absolute values** lie at the ends. Squaring pushes large negatives into large positives, so the biggest squares come from either end of the input. We can:

1. Keep two pointers, `left` at start and `right` at end.
2. Compare `array[left]²` vs. `array[right]²`.
3. Whichever is larger, write it into the **next free slot from the back** of the output array.
4. Move that pointer inward; repeat until `left > right`.

* **Time:** one pass → **O(n)**
* **Space:** output array of size n → **O(n)**

---

## Step-by-Step Two-Pointer Solution

1. Create `squaredArray` of the same length as input.
2. Initialize `left = 0`, `right = n - 1`, and `index = n - 1` (where `n = array.length`).
3. While `left <= right`:

   * Compute `leftSquare  = array[left] * array[left]`
   * Compute `rightSquare = array[right] * array[right]`
   * If `leftSquare > rightSquare`:

     * Place `leftSquare` at `squaredArray[index]`
     * `left++`
   * Else:

     * Place `rightSquare` at `squaredArray[index]`
     * `right--`
   * `index--`
4. Return `squaredArray`.

---

## Java Implementation

```java
package easy.arrays;

import java.util.Arrays;

public class SortedSquaredArray {

  /* 
   * Problem:
   * Given a sorted array of integers, return a new array of the same length
   * containing the squares of each integer, sorted in ascending order.
   *
   * Example:
   * Input:  [1, 2, 3, 5, 6, 8, 9]
   * Output: [1, 4, 9, 25, 36, 64, 81]
   */

  // Brute-Force Solution
  // Time: O(n log n) | Space: O(n)
  public static int[] sortedSquaredArrayBruteForce(int[] array) {
    int[] squared = new int[array.length];
    // 1) Square every element
    for (int i = 0; i < array.length; i++) {
      squared[i] = array[i] * array[i];
    }
    // 2) Sort the squared values
    Arrays.sort(squared);
    return squared;
  }

  // Optimized Two-Pointer Solution
  // Time: O(n) | Space: O(n)
  public static int[] sortedSquaredArray(int[] array) {
    int n = array.length;
    int[] squared = new int[n];
    int left = 0;
    int right = n - 1;
    int index = n - 1;

    // Build the output from the back
    while (left <= right) {
      int leftSquare  = array[left] * array[left];
      int rightSquare = array[right] * array[right];

      if (leftSquare > rightSquare) {
        squared[index] = leftSquare;
        left++;
      } else {
        squared[index] = rightSquare;
        right--;
      }
      index--;
    }

    return squared;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 5, 6, 8, 9};

    // Test Brute-Force
    System.out.println(
      "Brute-Force: " + Arrays.toString(sortedSquaredArrayBruteForce(array))
    ); // [1, 4, 9, 25, 36, 64, 81]

    // Test Two-Pointer
    System.out.println(
      "Two-Pointer: " + Arrays.toString(sortedSquaredArray(array))
    ); // [1, 4, 9, 25, 36, 64, 81]
  }
}
```

---

**Complexity Recap**

| Solution    | Time Complexity | Space Complexity |
| ----------- | --------------- | ---------------- |
| Brute-Force | O(n log n)      | O(n)             |
| Two-Pointer | O(n)            | O(n)             |

The **two-pointer** method is optimal when you need both speed and minimal extra work beyond the output array.
