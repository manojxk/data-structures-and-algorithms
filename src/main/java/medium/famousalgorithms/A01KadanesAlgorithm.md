**Problem Explanation**
We have a **non‐empty** array of integers (which may include negative numbers). We want to find the **maximum sum** that can be obtained by adding up a **contiguous subarray** (i.e., a sequence of adjacent elements) from the given array. You must select at least one element, so “empty” subarrays aren’t allowed.

> **Example**
>
> ```
> array = [3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4]
> ```
>
> One optimal choice is the subarray
>
> ```
> [1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1]
> ```
>
> Summing these gives
>
> ```
> 1 + 3 + (−2) + 3 + 4 + 7 + 2 + (−9) + 6 + 3 + 1 = 19.
> ```
>
> That is the largest possible sum of any contiguous subarray, so the answer is **19**.

---

## Brute‐Force Approach (O(n³) Time • O(1) Space)

### Intuition

* If you aren’t sure where the best subarray is, you could try **every possible** contiguous subarray, compute its sum, and keep track of the largest-sum seen.
* To enumerate all contiguous subarrays of an $n$-element array, choose:

  1. A **start index** `start` from 0 to $n-1$.
  2. An **end index** `end` from `start` to $n-1$.
     Each pair `(start, end)` defines the subarray `array[start..end]`.
* Then, for each candidate subarray, add up all elements between `start` and `end` to get its sum. Compare that sum to a running `maxSum` and update if it’s larger.

### Step‐by‐Step

1. Initialize `maxSum = Integer.MIN_VALUE`.
2. For `start` from 0 to $n – 1$:

   * For `end` from `start` to $n – 1$:

     1. Compute `currentSum = 0`.
     2. For `k` from `start` to `end`, do `currentSum += array[k]`.
     3. If `currentSum > maxSum`, set `maxSum = currentSum`.
3. After all loops, `maxSum` is the maximum subarray sum. Return it.

```java
public static int maxSubarraySumBruteForce(int[] array) {
  int maxSum = Integer.MIN_VALUE; // Start as very small so any sum will be ≥ it
  int n = array.length;

  // 1) Choose every possible start index
  for (int start = 0; start < n; start++) {
    // 2) Choose every possible end index ≥ start
    for (int end = start; end < n; end++) {
      int currentSum = 0;
      // 3) Sum all elements from start to end
      for (int k = start; k <= end; k++) {
        currentSum += array[k];
      }
      // 4) Update maxSum if this subarray is better
      if (currentSum > maxSum) {
        maxSum = currentSum;
      }
    }
  }

  return maxSum;
}
```

* **Time Complexity:**

  * There are $n$ choices for `start`, for each we have up to $n$ choices for `end`, and for each `(start,end)` we do up to $n$ additions.
  * That triple‐nested structure yields **O(n³)**.
* **Space Complexity:**

  * We only used a few primitive variables (`start`, `end`, `currentSum`, `maxSum`), so **O(1)** extra space.

> **Why this is impractical for large $n$:**
> If $n = 10{,}000$, doing $10{,}000^3 = 10^{12}$ operations is far too slow. We need a much faster method.

---

## Optimized Solution: Kadane’s Algorithm (O(n) Time • O(1) Space)

### Key Idea

* As you scan from left to right, keep track of the **maximum‐sum subarray ending exactly at the current index** (call this `currentSum`).
* Also keep a global `maxSum` of any subarray seen so far.
* When you move to a new element `num`, you have two choices for a best subarray ending here:

  1. Either you **start fresh** with `num` alone, if adding `num` to the old `currentSum` would be worse (i.e. if `currentSum` was negative).
  2. Or you **extend** the old subarray by adding `num`.
* Formally:

  ```java
  currentSum = Math.max(num, currentSum + num);
  maxSum = Math.max(maxSum, currentSum);
  ```

  * `currentSum + num` means “take the best subarray ending at the previous index, then tack on `num`.”
  * `num` alone means “throw away whatever we had before, start a brand‐new subarray at this index.”
  * Whichever is larger becomes the new `currentSum`.
  * Then compare `currentSum` to `maxSum`, updating if needed.

### Why This Works

* If the partial sum up to the previous index (i.e. `currentSum`) was **negative**, continuing it into the next element can only make your sum worse; better to start over at the new element.
* If `currentSum` was **nonnegative**, tacking on the new element can only make your sum larger.
* This logic ensures you always maintain, at each index `i`, the maximum possible sum of a subarray that **ends exactly at i**. By taking a global maximum over all those `currentSum` values, you get the maximum over **all** subarrays.

### Step‐by‐Step

1. Initialize:

   ```java
   int maxSum = Integer.MIN_VALUE; // smallest possible, so any subarray will be ≥ it
   int currentSum = 0;             // no subarray yet
   ```

2. Loop through each element `num` in `array` (from left to right):

   ```java
   currentSum = Math.max(num, currentSum + num);
   maxSum = Math.max(maxSum, currentSum);
   ```

3. After the loop ends, `maxSum` is the answer. Return it.

```java
public static int maxSubarraySumKadane(int[] array) {
  int maxSum = Integer.MIN_VALUE; // Will hold the maximum subarray sum found
  int currentSum = 0;             // Max subarray sum ending at the current index

  for (int num : array) {
    // Either start fresh at this number, or extend the previous subarray
    currentSum = Math.max(num, currentSum + num);
    // Update global max if needed
    maxSum = Math.max(maxSum, currentSum);
  }

  return maxSum;
}
```

* **Time Complexity:**

  * We make exactly **one pass** over the array, doing constant‐time work at each index → **O(n)**.
* **Space Complexity:**

  * We only use two integer variables (`currentSum` and `maxSum`), so **O(1)** extra space.

---

## Full Example in Java

Putting both approaches together, here is a complete `KadanesAlgorithm` class with a `main` method for testing:

```java
package medium.famousalgorithms;

public class KadanesAlgorithm {

  // 1) Brute‐Force Solution (O(n^3) Time, O(1) Space)
  public static int maxSubarraySumBruteForce(int[] array) {
    int maxSum = Integer.MIN_VALUE;
    int n = array.length;

    for (int start = 0; start < n; start++) {
      for (int end = start; end < n; end++) {
        int currentSum = 0;
        for (int k = start; k <= end; k++) {
          currentSum += array[k];
        }
        if (currentSum > maxSum) {
          maxSum = currentSum;
        }
      }
    }

    return maxSum;
  }

  // 2) Kadane’s Algorithm (O(n) Time, O(1) Space)
  public static int maxSubarraySumKadane(int[] array) {
    int maxSum = Integer.MIN_VALUE;
    int currentSum = 0;

    for (int num : array) {
      // Decide: start new subarray at 'num', or extend the previous one
      currentSum = Math.max(num, currentSum + num);
      // Update overall maximum
      maxSum = Math.max(maxSum, currentSum);
    }

    return maxSum;
  }

  // Main method to test both implementations
  public static void main(String[] args) {
    int[] array = {
      3, 5, -9, 1, 3, -2, 3, 4, 7, 2, -9, 6, 3, 1, -5, 4
    };

    System.out.println("Brute‐Force result: " 
        + maxSubarraySumBruteForce(array));  // Expected: 19
    System.out.println("Kadane’s result:    " 
        + maxSubarraySumKadane(array));      // Expected: 19
  }
}
```

---

## Understanding Kadane’s Algorithm for Beginners

1. **`currentSum` always tracks the “best subarray ending here.”**

   * If adding the new element helps, we do `currentSum + num`.
   * If the new element is larger than continuing, we “start fresh” from `num`.
   * This is because a negative running sum only drags the total down.

2. **`maxSum` is the best of all those “best‐ending‐here” values** over the entire pass.

   * Each time you update `currentSum`, you compare it to `maxSum`.
   * At the end, `maxSum` is the largest possible contiguous‐subarray sum anywhere in the array.

3. Kadane’s Algorithm is extremely efficient: **one loop**, **two variables**, **constant space**.

---

### Complexity Summary

| Approach           | Time Complexity | Space Complexity |
| ------------------ | --------------- | ---------------- |
| Brute‐Force        | O(n³)           | O(1)             |
| Kadane’s Algorithm | O(n)            | O(1)             |

For realistically sized arrays, always use **Kadane’s Algorithm** unless you’re just experimenting or learning, because the brute‐force approach becomes prohibitively slow even for moderate values of $n$.
