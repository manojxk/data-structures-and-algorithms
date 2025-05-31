**Problem Explanation**

You are given a **non-empty** array of **distinct** integers and a **target sum**. Your task is to find **all unique triplets** (three-element subsets) in the array that add up exactly to the target sum. Each triplet should be returned as a list of three numbers in **ascending order**. Furthermore, the collection of all triplets should itself be sorted so that each triplet appears in ascending order by its values.

> **Key points**:
>
> * The input array has **no duplicate numbers**, so any triplet you output will also be unique as long as you avoid rearranging the same elements in different orders.
> * You want **all** triplets $a, b, c$ (with a < b < c) such that a + b + c = target$Sum$.
> * If there are no such triplets, you return an empty list $$.

**Example**

```
array     = [12, 3, 1, 2, -6, 5, -8, 6]
targetSum = 0

All triplets that sum to 0:
  [-8,  2,  6]   // (−8 + 2 + 6 = 0)
  [-8,  3,  5]   // (−8 + 3 + 5 = 0)
  [-6,  1,  5]   // (−6 + 1 + 5 = 0)

Output (sorted by their values):
  [
    [-8, 2,  6],
    [-8, 3,  5],
    [-6, 1,  5]
  ]
```

---

## Brute-Force Approach (O(n³) Time, O(k) Space)

### Intuition

1. You could literally **try all possible combinations of three distinct indices** (i, j, k) in the array, check if `array[i] + array[j] + array[k] == targetSum`, and if so record the triplet (in sorted order).
2. To avoid duplicates, you’d need to track which triplets you’ve already added—often by sorting each found triplet and storing it in a set.

This works but is **O(n³)**, because there are roughly $\binom{n}{3}$ ways to choose three elements. For large $n$, this is too slow.

---

## Optimized Two-Pointer Approach (O(n²) Time, O(1) Extra Space)

### Key Insight

If the array is **sorted**, then for each chosen “first” element, you can find the other two elements (a “two-sum” problem) using a **two-pointer** scan in **linear time**. This reduces the triple-nested loops into a double-nested loop plus a two-pointer inner loop.

### Step-by-Step

1. **Sort the input array** in ascending order.

   * Example:

     ```
     [12, 3, 1, 2, -6, 5, -8, 6]
     → [-8, -6, 1, 2, 3, 5, 6, 12]
     ```
   * Sorting takes **O(n log n)** time.

2. **Initialize** an empty list of triplets:

   ```java
   List<List<Integer>> triplets = new ArrayList<>();
   ```

3. **Iterate** over the array with an index `i` from `0` to `n - 3` (inclusive). For each index `i`:

   * Let `firstValue = array[i]`.

   * We now want to find **two more numbers** (to the right of `i`) that sum to `targetSum - firstValue`. Call this remainder `twoSumTarget = targetSum - firstValue`.

   * Set two pointers:

     ```java
     int left  = i + 1;
     int right = n - 1;
     ```

   * While `left < right`:

     1. Compute `currentSum = array[left] + array[right]`.
     2. Compare `currentSum` with `twoSumTarget`:

        * **If** `currentSum == twoSumTarget`, you found a valid triplet:

          $$
            [\, firstValue,\; array[left],\; array[right]\,].
          $$

          Add this triplet to `triplets`.
          Then, **move both pointers inward** to look for another pair:

          ```java
          left++;
          right--;
          ```

          But first, **skip duplicates** on the left (if `array[left]` is the same as the previous) and on the right (if `array[right]` is the same as the next), to avoid recording the same triplet more than once.
        * **Else if** `currentSum < twoSumTarget`, that means we need a larger sum → move `left++` (since the array is sorted, increasing `left` raises `currentSum`).
        * **Else** (i.e. `currentSum > twoSumTarget`), we need a smaller sum → move `right--`.

   * **Skip duplicate “firstValue”**: Before you do the two-pointer scan for index `i`, check if `i > 0 && array[i] == array[i - 1]`. If so, continue to the next `i` to avoid reprocessing the same “firstValue” and thus avoid duplicate triplets.

4. **Return** the list `triplets`, which is already in ascending lexicographical order because:

   * You sorted the entire array from the start.
   * You always produce each triplet in ascending order (`[array[i], array[left], array[right]]`).
   * You move `i` from leftmost to rightmost, and for each `i`, you move `left` from smaller toward larger, ensuring any newly found triplet is ≥ previously recorded ones.

---

## Complete Java Implementation

```java
package medium.arrays;

import java.util.*;

public class A01ThreeNumberSum {

  /**
   * Returns all unique triplets [a, b, c] from the input array such that
   * a + b + c == targetSum. Each triplet is sorted in ascending order,
   * and the list of triplets is also sorted lexicographically.
   *
   * Time Complexity:  O(n^2)      // n log n to sort + O(n^2) for the two-pointer scans
   * Space Complexity: O(1) extra  // ignoring the output list
   *
   * @param array     an array of distinct integers
   * @param targetSum the desired sum of each triplet
   * @return a List of Lists, where each inner List is a triplet that sums to targetSum
   */
  public static List<List<Integer>> threeNumberSum(int[] array, int targetSum) {
    // 1) Sort the array in ascending order
    Arrays.sort(array);
    List<List<Integer>> triplets = new ArrayList<>();

    int n = array.length;
    // 2) Fix the first element of each triplet using index i
    for (int i = 0; i < n - 2; i++) {
      // (a) Skip duplicate first elements to avoid redundant triplets
      if (i > 0 && array[i] == array[i - 1]) {
        continue;
      }

      int firstValue   = array[i];
      int twoSumTarget = targetSum - firstValue;

      // 3) Initialize two pointers for the remaining two elements
      int left  = i + 1;
      int right = n - 1;

      // 4) Move left/right pointers to find pairs that sum to twoSumTarget
      while (left < right) {
        int currentSum = array[left] + array[right];
        if (currentSum == twoSumTarget) {
          // Found a valid triplet: [firstValue, array[left], array[right]]
          triplets.add(Arrays.asList(firstValue, array[left], array[right]));

          // Move both pointers inward, but skip duplicates
          int leftValue  = array[left];
          int rightValue = array[right];

          // Skip duplicates on the left side
          while (left < right && array[left] == leftValue) {
            left++;
          }
          // Skip duplicates on the right side
          while (left < right && array[right] == rightValue) {
            right--;
          }
        } else if (currentSum < twoSumTarget) {
          // We need a larger sum → advance left pointer
          left++;
        } else {
          // We need a smaller sum → decrement right pointer
          right--;
        }
      }
    }

    return triplets;
  }

  // (Optional) Brute-Force version for understanding, though we prefer the above:
  public static List<List<Integer>> threeNumberSumBruteForce(int[] array, int targetSum) {
    List<List<Integer>> triplets = new ArrayList<>();
    Set<List<Integer>> seen     = new HashSet<>(); // to avoid duplicates

    int n = array.length;
    for (int i = 0; i < n - 2; i++) {
      for (int j = i + 1; j < n - 1; j++) {
        for (int k = j + 1; k < n; k++) {
          if (array[i] + array[j] + array[k] == targetSum) {
            List<Integer> candidate = Arrays.asList(array[i], array[j], array[k]);
            // Sort the triple (since the input itself might not be sorted)
            Collections.sort(candidate);
            if (!seen.contains(candidate)) {
              seen.add(candidate);
              triplets.add(candidate);
            }
          }
        }
      }
    }
    // Finally, sort the list of triplets lexicographically
    Collections.sort(triplets, (a, b) -> {
      for (int idx = 0; idx < 3; idx++) {
        if (!a.get(idx).equals(b.get(idx))) {
          return Integer.compare(a.get(idx), b.get(idx));
        }
      }
      return 0;
    });

    return triplets;
  }

  // Example usage
  public static void main(String[] args) {
    int[] array     = {12, 3, 1, 2, -6, 5, -8, 6};
    int targetSum   = 0;

    // Optimized two-pointer approach
    List<List<Integer>> result = threeNumberSum(array, targetSum);
    System.out.println(result);
    // Expected Output: [[-8, 2, 6], [-8, 3, 5], [-6, 1, 5]]

    // (Optional) See how the brute-force version also works
    List<List<Integer>> brute = threeNumberSumBruteForce(array, targetSum);
    System.out.println(brute);
    // Should match the optimized result once sorted
  }
}
```

---

## Explanation of Key Steps

1. **Sorting**
   We sort once at the beginning so that:

   * We can easily skip duplicates (if they existed—though the problem says “distinct integers,” so duplicates shouldn’t appear).
   * We can use the two-pointer technique: when the array is in ascending order, increasing the `left` pointer or decreasing the `right` pointer has predictable effects on the sum (it gets larger or smaller).

2. **Choosing the “First” Element**

   * We fix `array[i]` as the first element of a potential triplet.
   * The required remainder we now need is `targetSum - array[i]`.

3. **Two-Pointer “Two-Sum” Subroutine**

   * Having sorted the array, every time we want to “find two numbers among the subarray \[i+1 … n−1] that add up to twoSumTarget,” we can initialize `left = i+1`, `right = n−1`.
   * Compute `currentSum = array[left] + array[right]`.

     * If it exactly equals `twoSumTarget`, we record this triplet.
     * If it’s too small, we increment `left` (to increase the sum).
     * If it’s too large, we decrement `right`.

4. **Skipping Duplicates**

   * After finding one valid combination at `left` and `right`, we still move both pointers inward.
   * Because the input says “distinct integers,” duplicates in the same array won’t occur. If duplicates did appear, we’d skip them by checking `while (left < right && array[left] == array[left+1]) left++;` and similar for `right`.

---

## Complexity Analysis

* **Time Complexity:**

  1. **Sorting:** O(n log n)
  2. **Main loop** over `i` from `0` to `n − 3`: that’s O(n) iterations

     * Inside each, the **two-pointer scan** runs in O(n) (because `left` and `right` only move inward, never backward).
       → **Overall:** $O(n \log n + n \cdot n) = O(n^2)$.

* **Space Complexity:**

  * We sort the array **in place** (no extra array beyond O(log n) for recursion/stack in Java’s sort).
  * We only allocate the `triplets` list for output; besides that, we use a constant number of pointers/variables.
    → **O(1)** extra space (ignoring the output list).

---

### Final Notes for Beginners

* **Why sort first?** Because then you can use a **two-pointer** strategy to find two other numbers that sum to a given remainder. If the array were unsorted, you’d have to revert to a nested loop or a hash-based method—each of which also ends up O(n²) but is slightly more cumbersome to organize triplet ordering.
* By always building each triplet as `[array[i], array[left], array[right]]` from a **sorted** array, you guarantee each small triplet is in ascending order. Then, because you iterate `i` from left to right, you inherently push smaller triplets (lexicographically) before larger ones, so the final list of triplets is already sorted.
* Avoiding duplicates is critical if the input array might contain repeats. Although this problem states “distinct integers,” it’s good practice—when duplications might exist—to skip over equal consecutive values for `i`, `left`, or `right`, so that you never output the same combination twice.

With these ideas in mind, the two-pointer pattern for “three-sum” is a powerful template:

1. **Sort**,
2. **Fix one element**,
3. **Find a two-sum** in the remainder using two pointers,
4. **Move pointers** inward based on comparisons.
