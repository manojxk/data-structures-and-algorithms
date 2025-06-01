**Problem Restatement**
An array of integers is **monotonic** if it is either entirely:

* **Non-decreasing** (each element is ≥ the one before), or
* **Non-increasing** (each element is ≤ the one before).

Given an integer array, determine whether it is monotonic. Return `true` if it is, or `false` otherwise.

---

## Key Insight

You only need to check **one full pass** of the array to see if it ever “goes downhill” (violating non-decreasing) or “goes uphill” (violating non-increasing). If **neither** pattern is violated, the array is monotonic. Concretely:

1. Initialize two flags:

   * `isNonDecreasing = true`
   * `isNonIncreasing = true`
2. Loop through each adjacent pair `(array[i-1], array[i])` for `i = 1 … n-1`:

   * If `array[i] < array[i-1]`, then it **cannot** be non-decreasing → set `isNonDecreasing = false`.
   * If `array[i] > array[i-1]`, then it **cannot** be non-increasing → set `isNonIncreasing = false`.
3. After the loop, if **either** `isNonDecreasing` **or** `isNonIncreasing` remains `true`, the array is monotonic.

Because we check both conditions in one pass, the time complexity is **O(n)** and we only use a couple of boolean variables → **O(1)** extra space.

---

## Step-by-Step Walkthrough

1. **Edge Case**

   * If the array has length `0` or `1`, it’s trivially monotonic. You can return `true` immediately.

2. **Initialize Flags**

   ```java
   boolean isNonDecreasing = true;
   boolean isNonIncreasing = true;
   ```

3. **Single Pass Check**

   ```java
   for (int i = 1; i < array.length; i++) {
     if (array[i] < array[i - 1]) {
       isNonDecreasing = false;  // There's a “downward” violation
     }
     if (array[i] > array[i - 1]) {
       isNonIncreasing = false;  // There's an “upward” violation
     }
   }
   ```

   * Whenever you see `array[i] < array[i - 1]`, you know the array cannot be entirely non-decreasing.
   * Whenever you see `array[i] > array[i - 1]`, you know the array cannot be entirely non-increasing.

4. **Return the Result**

   ```java
   return isNonDecreasing || isNonIncreasing;
   ```

   If at least one of the two possible monotonic “directions” still holds, you’ve confirmed the array is monotonic.

---

## Complete Java Code

```java
package medium.arrays;

public class A04MonotonicArray {

  public static boolean isMonotonic(int[] array) {
    // Edge case: length 0 or 1 is automatically monotonic
    if (array.length <= 1) return true;

    boolean isNonDecreasing = true;
    boolean isNonIncreasing = true;

    // Check adjacent pairs once
    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[i - 1]) {
        isNonDecreasing = false;  // Violation of non-decreasing
      }
      if (array[i] > array[i - 1]) {
        isNonIncreasing = false;  // Violation of non-increasing
      }
    }

    // Array is monotonic if it never violated at least one pattern
    return isNonDecreasing || isNonIncreasing;
  }

  public static void main(String[] args) {
    int[] array1 = {1, 2, 2, 3};
    System.out.println(isMonotonic(array1));  // true

    int[] array2 = {6, 5, 4, 4};
    System.out.println(isMonotonic(array2));  // true

    int[] array3 = {1, 3, 2};
    System.out.println(isMonotonic(array3));  // false
  }
}
import java.util.*;

class Program {
  public static boolean isMonotonic(int[] array) {
    if (array == null || array.length <= 1) {
      return true;  // Empty or single-element arrays are monotonic
    }

    boolean seenIncrease = false;
    boolean seenDecrease = false;

    for (int i = 0; i < array.length - 1; i++) {
      if (array[i] < array[i + 1]) {
        seenIncrease = true;
      } else if (array[i] > array[i + 1]) {
        seenDecrease = true;
      }
      // If both trends appear, it’s not monotonic
      if (seenIncrease && seenDecrease) {
        return false;
      }
    }

    return true;
  }

  // Main function to test the isMonotonic method
  public static void main(String[] args) {
    int[] array1 = {1, 2, 2, 3};
    System.out.println("Is monotonic: " + isMonotonic(array1)); // Output: true

    int[] array2 = {5, 4, 4, 3, 1};
    System.out.println("Is monotonic: " + isMonotonic(array2)); // Output: true

    int[] array3 = {1, 3, 2};
    System.out.println("Is monotonic: " + isMonotonic(array3)); // Output: false

    int[] array4 = {1, 1, 1, 1, 1};
    System.out.println("Is monotonic: " + isMonotonic(array4)); // Output: true
  }
}






```

---

## Complexity Analysis

* **Time Complexity:**
  You traverse the array exactly once (checking each adjacent pair), so **O(n)** where n = `array.length`.

* **Space Complexity:**
  Only two boolean flags and a few loop variables are used, so **O(1)** extra space.

---

This one-pass approach efficiently determines monotonicity by simultaneously tracking both “never decreasing” and “never increasing” conditions.
