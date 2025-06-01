**Problem Explanation**
You have two integer arrays, for example:

```
array1 = [-1, 5, 10, 20, 28, 3]
array2 = [26, 134, 135, 15, 17]
```

You want to find **one number from each array** so that their **absolute difference** is as small as possible. Return that pair of numbers.

In the sample above, the closest‐difference pair is `[28, 26]` since |28 − 26| = 2, and you cannot get a smaller difference than 2 with any other cross‐array pairing.

---

## Approach: Sort + Two Pointers

1. **Sort both arrays in ascending order.**

   * After sorting:

     ```
     array1 → [-1, 3, 5, 10, 20, 28]
     array2 → [15, 17, 26, 134, 135]
     ```
   * Sorting costs O(n log n) and O(m log m), where n and m are the lengths of array1 and array2.

2. **Initialize two pointers** `idx1 = 0` (for array1) and `idx2 = 0` (for array2).
   Also keep:

   * `smallestDiff = +∞` (to track the minimum difference found so far),
   * `result = new int[2]` (to store the best pair).

3. **Traverse both arrays simultaneously**:

   * While `idx1 < array1.length && idx2 < array2.length`:

     1. Let `firstNum  = array1[idx1]`
        and `secondNum = array2[idx2]`.
     2. Compute the absolute difference:

        * If `firstNum < secondNum`:
          `currentDiff = secondNum - firstNum;`
          Then do `idx1++` (because increasing `idx1` might bring array1’s value closer to array2’s).
        * Else if `secondNum < firstNum`:
          `currentDiff = firstNum - secondNum;`
          Then do `idx2++`.
        * Else (they are equal), you’ve found `currentDiff = 0` → return `[firstNum, secondNum]` immediately (cannot get better than zero).
     3. If `currentDiff < smallestDiff`, update:

        ```java
        smallestDiff = currentDiff;
        result = new int[]{ firstNum, secondNum };
        ```
   * This pointer movement ensures you scan both arrays in ascending order and always “push” the smaller of the two current values forward, looking for a closer match.

4. **Return** `result` once one of the pointers reaches the end.

Because both arrays are sorted, moving the pointer on the smaller element is guaranteed to move toward a potentially closer difference.

---

## Java Implementation

```java
package medium.arrays;

import java.util.Arrays;

public class A02SmallestDifference {

  /**
   * Returns a pair [x, y], where x is from array1 and y is from array2,
   * such that |x - y| is minimized. Both input arrays must be non-empty.
   *
   * Time:  O(n log n + m log m)  -- sorting dominates,
   *                                plus O(n+m) for the two-pointer scan
   * Space: O(1) extra            -- aside from in-place sorting
   */
  public static int[] smallestDifference(int[] array1, int[] array2) {
    // 1) Sort both arrays
    Arrays.sort(array1);
    Arrays.sort(array2);

    int idx1 = 0;                       // pointer into array1
    int idx2 = 0;                       // pointer into array2
    int smallestDiff = Integer.MAX_VALUE;
    int[] result = new int[2];

    // 2) Traverse with two pointers
    while (idx1 < array1.length && idx2 < array2.length) {
      int firstNum  = array1[idx1];
      int secondNum = array2[idx2];
      int currentDiff;

      if (firstNum < secondNum) {
        currentDiff = secondNum - firstNum;
        idx1++; // increase the smaller element
      } else if (secondNum < firstNum) {
        currentDiff = firstNum - secondNum;
        idx2++; // increase the smaller element
      } else {
        // Difference is zero → best possible
        return new int[]{ firstNum, secondNum };
      }

      // 3) Update if this is the smallest found so far
      if (currentDiff < smallestDiff) {
        smallestDiff = currentDiff;
        result[0] = firstNum;
        result[1] = secondNum;
      }
    }

    // 4) Return the pair with the smallest difference found
    return result;
  }

  // Example usage
  public static void main(String[] args) {
    int[] array1 = { -1, 5, 10, 20, 28, 3 };
    int[] array2 = { 26, 134, 135, 15, 17 };

    int[] answer = smallestDifference(array1, array2);
    System.out.println(
      "Pair with the smallest difference: [" 
      + answer[0] + ", " + answer[1] + "]"
    );
    // Expected Output: [28, 26]
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * Sorting `array1`: O(n log n)
  * Sorting `array2`: O(m log m)
  * Two-pointer scan (worst case, touching all elements once): O(n + m)
    → Combined: **O(n log n + m log m)**.

* **Space Complexity:**

  * Sorting is done in-place (Java’s `Arrays.sort` for primitives uses an in-place algorithm with O(log n) stack overhead), so extra space is effectively **O(1)** aside from the output pair.

This two-pointer method is optimal and straightforward whenever you need “smallest (or largest) difference” between two sorted lists.
