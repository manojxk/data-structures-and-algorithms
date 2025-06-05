Below is a complete, step‐by‐step explanation of how Radix Sort works (using Counting Sort as a subroutine), followed by the full Java implementation you provided and a discussion of its time and space complexities.

---

## 1. High‐Level Idea of Radix Sort

1. **Goal**: Sort an array of non‐negative integers (e.g. `[170, 45, 75, 90, 802, 24, 2, 66]`) in ascending order **without** comparing items directly (i.e., no “>`” or `<\` between whole numbers). Instead, we “bucket” or “count” based on each digit, from the least significant digit (units place) up to the most significant digit.

2. **Key Observation**: If you can stably sort the array by the least significant digit (0–9), then sort by the next digit (tens), and so on up to the most significant digit, you will end up with a fully sorted array. Crucially, each step must be **stable**—items that share the same digit at the current position must remain in the same relative order they had after the previous pass.

3. **Phases of the Algorithm**

   * **Determine the maximum number** in the array so we know how many digit‐positions we must process. For example, if the maximum is `802`, it has three digits, so we will do three passes: units (`exp = 1`), tens (`exp = 10`), and hundreds (`exp = 100`).
   * **For each digit‐position `exp` (1, 10, 100, …)**:

     1. Extract the digit of each element at position `exp`:

        ```
        digit = (array[i] / exp) % 10
        ```

        • If `exp=1`, that gives the units’ digit.
        • If `exp=10`, that gives the tens’ digit.
        • If `exp=100`, that gives the hundreds’ digit.
     2. Run a **stable Counting Sort** (or “bucket‐counting”) based on those extracted digits from 0 up to 9. The stable Counting Sort will reorder the array so that items with a smaller digit come before items with a larger digit, but **preserve** the original relative ordering of items that share the same digit.
   * After you finish processing every required digit (up to the digit‐length of the maximum element), the entire array will be in ascending order.

---

## 2. Detailed Steps

### 2.1. Find the Maximum Value

```java
private static int getMax(int[] array) {
  int max = array[0];
  for (int num : array) {
    if (num > max) {
      max = num;
    }
  }
  return max;
}
```

* We scan once to find the largest element (e.g. `802` in the sample).
* Knowing `max` = 802 tells us we need to process `exp = 1`, `10`, and `100` (stop when `max/exp == 0`).

### 2.2. Outer Loop over Digit Positions

```java
public static void radixSort(int[] array) {
  int max = getMax(array);

  // exp = 1 → units, exp = 10 → tens, exp = 100 → hundreds, etc.
  for (int exp = 1; max / exp > 0; exp *= 10) {
    countingSortByDigit(array, exp);
  }
}
```

* As long as `max / exp > 0`, we still have another digit to process.
  • If `exp = 1`, we look at `(array[i] / 1) % 10` (units).
  • If `exp = 10`, we look at `(array[i] / 10) % 10` (tens).
  • And so on until `max/exp == 0` (no more digits).

### 2.3. Stable Counting Sort on a Single Digit

```java
private static void countingSortByDigit(int[] array, int exp) {
  int n = array.length;
  int[] output = new int[n];    // Will hold the sorted result for this pass
  int[] count = new int[10];    // Because each digit ∈ [0..9]

  // 1) Count how many elements have each digit (0..9) at position `exp`
  for (int i = 0; i < n; i++) {
    int digit = (array[i] / exp) % 10;
    count[digit]++;
  }

  // 2) Transform count[] so that count[i] now contains the "last index"
  //    in the output array where digit i should go (cumulative sum).
  for (int i = 1; i < 10; i++) {
    count[i] += count[i - 1];
  }

  // 3) Build the output array in a stable way:
  //    We iterate from right to left, so that items with the same digit
  //    end up in the same relative order they appeared in the original array.
  for (int i = n - 1; i >= 0; i--) {
    int digit = (array[i] / exp) % 10;
    int position = count[digit] - 1;  // Zero‐based index in output
    output[position] = array[i];
    count[digit]--;                     // Decrement so the next identical digit goes left
  }

  // 4) Copy the partially sorted output[] back into the original array
  for (int i = 0; i < n; i++) {
    array[i] = output[i];
  }
}
```

* **Step 1**: Build a frequency array `count[0..9]`. Increment `count[digit]` for each element’s digit at the current `exp`.
* **Step 2**: Convert `count[]` into cumulative form so `count[i]` tells us how many items have digit ≤ *i*. That also means the last index at which digit *i* should be placed in the output is `count[i] - 1`.
* **Step 3**: Iterate **backwards** through the original array (index `n-1` down to `0`), find each element’s digit, place the element at `output[count[digit] - 1]`, then decrement `count[digit]`. This backwards pass is what ensures **stability**—ties (equal digits) remain in the order they appeared in `array[]`.
* **Step 4**: Overwrite the original array with `output[]`. Now the array is sorted by that particular digit, but within each “bucket” of equal digits, the original order (from the previous pass) is preserved.

---

## 3. Complete Java Code

```java
package hard.sorting;

import java.util.Arrays;

public class RadixSort {

  /**
   * Sorts the given array of non‐negative integers in ascending order
   * using Radix Sort. Internally uses a stable Counting Sort on each digit.
   *
   * Time Complexity: O(d · (n + b)) where
   *   - n = number of elements in the array,
   *   - d = number of digits in the largest element,
   *   - b = base (10 in our decimal system).
   * Since b = 10 is constant, this is essentially O(d · n). If d is bounded
   * by O(log₁₀(maxValue)), this is still linearish for fixed‐size integers.
   *
   * Space Complexity: O(n + b) = O(n + 10) ≈ O(n), for the output array and count array.
   */
  public static void radixSort(int[] array) {
    // 1) Find the maximum element so we know how many digit‐positions to process
    int max = getMax(array);

    // 2) Do a stable counting sort for each digit, from least significant (exp = 1)
    //    to most significant (exp = 10, 100, ...), as long as max/exp > 0.
    for (int exp = 1; max / exp > 0; exp *= 10) {
      countingSortByDigit(array, exp);
    }
  }

  /** Returns the maximum value in the array (assumes array.length > 0). */
  private static int getMax(int[] array) {
    int max = array[0];
    for (int num : array) {
      if (num > max) {
        max = num;
      }
    }
    return max;
  }

  /**
   * A stable Counting Sort that sorts `array[]` according to the digit at place `exp`.
   * e.g., if exp=1, it sorts by units digit; if exp=10, it sorts by tens digit; etc.
   *
   * @param array The array to sort in place (partially, based on the current digit).
   * @param exp   1, 10, 100, ... indicates which digit to sort by.
   */
  private static void countingSortByDigit(int[] array, int exp) {
    int n = array.length;
    int[] output = new int[n]; // Will hold the sorted result by this digit
    int[] count = new int[10]; // Digit ranges from 0 to 9

    // 1) Build the frequency count of each digit
    for (int i = 0; i < n; i++) {
      int digit = (array[i] / exp) % 10;  // Extract the digit at place exp
      count[digit]++;
    }

    // 2) Transform count[] into cumulative count: count[i] now holds the
    //    number of elements ≤ digit i. So index for digit i is (count[i] - 1).
    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }

    // 3) Build the output array in **stable** order by iterating from right to left
    for (int i = n - 1; i >= 0; i--) {
      int digit = (array[i] / exp) % 10;
      int pos = count[digit] - 1;   // The correct position in output[] for this element
      output[pos] = array[i];
      count[digit]--;               // Decrement so next same digit goes to the prior slot
    }

    // 4) Copy output[] back into the original array
    for (int i = 0; i < n; i++) {
      array[i] = output[i];
    }
  }

  /** Driver code to test the Radix Sort implementation. */
  public static void main(String[] args) {
    int[] array = {170, 45, 75, 90, 802, 24, 2, 66};

    System.out.println("Original Array:  " + Arrays.toString(array));
    radixSort(array);
    System.out.println("Sorted Array:    " + Arrays.toString(array));
  }
}
```

**When you run the above `main`, you will see:**

```
Original Array:  [170, 45, 75, 90, 802, 24, 2, 66]
Sorted Array:    [2, 24, 45, 66, 75, 90, 170, 802]
```

---

## 4. Time and Space Complexity

1. **Time Complexity**

   * Let **n** be the number of elements in the array.
   * Let **d** = number of digits of the largest element (in base‐10). E.g., if the maximum is 802, then `d = 3`.
   * Each pass (for a single digit) does:

     1. One linear scan to build the digit‐frequency array: **O(n)**.
     2. One loop of length `10` to build the cumulative counts: **O(10) = O(1)**.
     3. One backward scan of length **n** to place elements into the `output` array—**O(n)**.
     4. One final copy of **n** elements back into the original array—**O(n)**.
        So each digit pass costs **O(n + 10 + n + n) = O(n)**.
   * Since there are **d** digit passes (one for each digit‐position up to the most significant digit), total cost is

     ```
     O(d · n).
     ```
   * In typical integer ranges, `d = O(log₁₀(maxValue))`. If all numbers fit within a fixed word size (e.g., 32‐bit integers), then `d` is at most about 10, so Radix Sort effectively runs in **O(n)** for a fixed integer width.

2. **Space Complexity**

   * We allocate one extra array `output[]` of length **n**.
   * We also allocate one `count[]` array of length **b = 10** (for digits 0..9).
   * Thus, extra space is **O(n + 10) = O(n)**. We do not use any other large data structures.

---

## 5. When to Use Radix Sort

* **Large arrays of non‐negative integers** (e.g., 32‐bit IDs) can often be sorted faster in practice if the range is moderate.
* Unlike comparison‐based sorts (QuickSort, MergeSort, HeapSort), which have a lower bound of **O(n log n)**, Radix Sort can achieve roughly **O(n)** if `d` (number of digits) is small compared to `n`.
* If your integers can be negative as well, you can modify the approach by separating negatives from positives, sorting each with Radix Sort (perhaps on their absolute values), and then combining them (negatives go in reverse order). But the code above assumes **non‐negative** only.

---

### Summary

1. **Radix Sort** does not compare entire numbers; it sorts by each digit from least significant to most significant, using a **stable Counting Sort** as a subroutine.
2. The algorithm repeatedly “buckets” by digit, guaranteeing the partial ordering carries forward.
3. The result is a fully sorted array in ascending order.
4. **Overall complexity** is `O(d·n)` time (linear in `n` when `d` is considered constant for fixed‐width integers) and `O(n)` extra space.

Feel free to reuse the code above or incorporate it directly into your project: it will correctly sort any array of non‐negative integers using Radix Sort.
