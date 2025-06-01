**Problem Explanation**
You have an integer array (for example, `[3, 4, -7, 1, 2, -6, 3]`). You want to know **whether there is any contiguous subarray (of length ≥ 1) whose sum is exactly zero**. In the example above, the subarray `[3, 4, -7]` sums to `0`, so the answer is `true`.

> More formally:
> A **subarray** means a sequence of consecutive elements in the original array. You need to check if there exists any index range `[i..j]` (with `0 ≤ i ≤ j < n`) such that
>
> ```
> array[i] + array[i+1] + ... + array[j] == 0.
> ```

If you can find even one such range, return `true`; otherwise return `false`.

---

## Why the Brute-Force Approach Is Too Slow (O(n²))

A straightforward (but inefficient) way is:

1. Loop over every possible **start** index `i` from `0` to `n−1`.
2. For each `i`, compute the running sum of `array[i] + array[i+1] + ...` in a nested loop over `j` (`i ≤ j < n`).
3. Whenever that running sum becomes zero, you can immediately return `true`.
4. If you finish all pairs `(i, j)` without hitting zero, return `false`.

That looks like:

```java
public static boolean hasZeroSumSubarrayBruteForce(int[] nums) {
  int n = nums.length;
  for (int i = 0; i < n; i++) {
    int sum = 0;
    for (int j = i; j < n; j++) {
      sum += nums[j];
      if (sum == 0) {
        return true;
      }
    }
  }
  return false;
}
```

* **Why it’s O(n²):** For each of the `n` choices of `i`, you potentially scan the remaining `n – i` elements to build sums. That nested structure gives a worst-case of about $\frac{n(n+1)}{2}$ additions, i.e. **O(n²)**.
* For large arrays, this becomes prohibitively slow.

---

## Efficient Approach Using Prefix Sums and a HashSet (O(n) Time, O(n) Space)

### Key Idea

If you keep track of your **running (cumulative) sum** as you move from left to right, you’ll notice:

* Suppose at index `j` you have a cumulative sum `S_j = array[0] + array[1] + … + array[j]`.
* If at some prior index `i < j` you had the same cumulative sum `S_i = S_j`, then

  ```
  array[i+1] + array[i+2] + … + array[j] = S_j – S_i = 0.
  ```

  That means the subarray `(i+1) … j` sums to zero.
* Also, if at any point your **running sum** itself is exactly `0` (i.e. `S_j == 0`), then the subarray `0 … j` sums to zero.

So you only need to detect if you ever see a repeated prefix‐sum value (or hit zero). As soon as you detect that, you know there is a zero‐sum subarray somewhere behind you.

### Step-by-Step Solution

1. **Initialize** an empty `HashSet<Integer> seenSums`.
2. **Initialize** a variable `currentSum = 0`.
3. **Traverse** the array from left to right (`for (int x : array)`):

   * Add `x` to `currentSum`.
   * **Check**:

     * If `currentSum == 0`:
       That means the subarray from index `0` to the current index sums to zero. Return `true`.
     * If `currentSum` is already in `seenSums`:
       That means at some earlier position `k` you had the same running sum `S_k = currentSum`. Then the subarray from `(k+1)` to the current index sums to zero. Return `true`.
   * Otherwise, insert `currentSum` into `seenSums` and move on.
4. If you finish the entire array without ever returning `true`, then no zero‐sum subarray exists; return `false`.

This runs in **O(n)** time because each prefix sum is computed once, and each HashSet lookup/insertion is on average O(1). You’re using **O(n)** extra space to store up to `n` distinct prefix sums.

---

## Full Java Code

```java
package medium.arrays;

import java.util.HashSet;
import java.util.Set;

public class A11ZeroSumSubarray {

  /**
   * Brute‐force: O(n^2) time
   * Checks every possible subarray sum.
   */
  public static boolean hasZeroSumSubarrayBruteForce(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += nums[j];
        if (sum == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Efficient: O(n) time, O(n) space
   * Uses prefix sums and a HashSet to detect any repeated prefix sum (or zero).
   */
  public static boolean hasZeroSumSubarray(int[] array) {
    Set<Integer> seenSums = new HashSet<>();
    int currentSum = 0;

    for (int x : array) {
      currentSum += x;

      // If the running sum is zero, subarray [0..currentIndex] sums to zero
      if (currentSum == 0) {
        return true;
      }
      // If we’ve seen this prefix sum before, some internal subarray sums to zero
      if (seenSums.contains(currentSum)) {
        return true;
      }

      // Otherwise, remember this prefix sum
      seenSums.add(currentSum);
    }

    // No zero-sum subarray found
    return false;
  }

  public static void main(String[] args) {
    int[] array = {3, 4, -7, 1, 2, -6, 3};

    System.out.println(
      "Brute‐Force says zero-sum exists? " 
      + hasZeroSumSubarrayBruteForce(array)
    );  // true

    System.out.println(
      "Optimized says zero-sum exists? " 
      + hasZeroSumSubarray(array)
    );  // true
  }
}
```

---

## Explanation for Beginners

1. **What is a “prefix sum”?**

   * As you walk through the array from left to right, keep a running total of all elements so far. That running total at index `i` is called the **prefix sum** up to `i`.

2. **Why does a repeated prefix sum imply a zero‐sum subarray?**

   * If at index `j` your prefix sum is `S_j`, and at some earlier index `i` your prefix sum was also `S_i = S_j`, then the sum of the subarray from `i+1` to `j` must be zero, because

     $$
       (array[i+1] + \cdots + array[j]) 
       = S_j - S_i 
       = 0.
     $$

3. **Why do we also check `currentSum == 0`?**

   * If your running sum from the very start is already zero at index `j`, that means the subarray `[0..j]` itself sums to zero, so you can return `true` immediately.

4. **How does the HashSet help?**

   * We insert each prefix sum into a HashSet after seeing it. The moment we try to insert a prefix sum that is already in the set, we know a zero‐sum subarray exists.
   * If we reach the end with no repeats and no prefix sum of zero, there is no zero‐sum subarray.

5. **Complexities**

   * **Time:** O(n), because you walk through the array once. Each time you check/insert into the HashSet in O(1) average time.
   * **Space:** O(n), since in the worst‐case none of the prefix sums would repeat, and you’d store up to `n` distinct sums.

This method is both **simple** and **efficient**, and it works even if the array contains both positive and negative numbers.
