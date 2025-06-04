**Problem Restatement**
Given an integer array `nums` and a target value `target`, find **all unique quadruplets**—that is, four‐element combinations `[a,b,c,d]`—such that

```
a + b + c + d = target
```

You must return each quadruplet only once (no duplicate sets), regardless of the order in which they appear in `nums`. If no quadruplet sums to `target`, return an empty list.

---

## Overall Approach (O(n³) Time, O(log n) or O(n) Extra Space)

1. **Sort the Array**
   Sorting makes it easy to:

   * Skip duplicates automatically (adjacent equal values).
   * Use a two‐pointer approach to find pairs that sum to a given value in $O(n)$ time.

2. **Fix the First Two Numbers with Nested Loops**

   * Use an index `i` running from `0` to `nums.length - 4` (inclusive) for the first element.
   * Use an index `j` running from `i+1` to `nums.length - 3` (inclusive) for the second element.
   * Skip any `i` or `j` that would repeat the same value as the previous iteration (to avoid duplicates).

3. **Two‐Pointer to Find the Remaining Pair**

   * Let

     ```
     left  = j + 1; 
     right = nums.length - 1;
     ```
   * Compute

     ```
     sum = nums[i] + nums[j] + nums[left] + nums[right];
     ```

     and compare with `target`.

     * If `sum == target`, you found one valid quadruplet. Add `[nums[i], nums[j], nums[left], nums[right]]` to the result.

       * Then advance `left` past any duplicates of `nums[left]`, and move `right` backward past any duplicates of `nums[right]`, before continuing.
     * If `sum < target`, increase `left++` (because the array is sorted, so moving left pointer rightward raises the sum).
     * If `sum > target`, decrease `right--`.

4. **Collect All Unique Quadruplets**

   * Because we skip duplicate values of `i`, `j`, `left`, and `right` whenever we see the same number a second time in a row, we guarantee that each quadruplet is output only once.

---

## Detailed Step‐by‐Step Explanation

```java
public List<List<Integer>> fourSum(int[] nums, int target) {
  List<List<Integer>> result = new ArrayList<>();
  if (nums == null || nums.length < 4) {
    return result;
  }

  // 1) Sort the array to enable two‐pointer and easy duplicate skipping
  Arrays.sort(nums);

  // 2) Fix the first number with index i
  for (int i = 0; i < nums.length - 3; i++) {
    // Skip duplicate values for i
    if (i > 0 && nums[i] == nums[i - 1]) {
      continue;
    }

    // 3) Fix the second number with index j
    for (int j = i + 1; j < nums.length - 2; j++) {
      // Skip duplicate values for j
      if (j > i + 1 && nums[j] == nums[j - 1]) {
        continue;
      }

      // 4) Now use two pointers to find the last two numbers
      int left  = j + 1;
      int right = nums.length - 1;

      while (left < right) {
        long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
        if (sum == target) {
          // Found a valid quadruplet
          result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

          // Skip over duplicates on the left side
          while (left < right && nums[left] == nums[left + 1]) {
            left++;
          }
          // Skip over duplicates on the right side
          while (left < right && nums[right] == nums[right - 1]) {
            right--;
          }

          // Move both pointers inward
          left++;
          right--;
        }
        else if (sum < target) {
          left++;   // We need a larger sum
        }
        else {
          right--;  // We need a smaller sum
        }
      }
    }
  }
  return result;
}
```

### Key Points and Duplicate‐Skipping

* **Sorting**

  * After `Arrays.sort(nums)`, the array is in ascending order. Then skipping duplicates is as simple as checking `if (nums[k] == nums[k - 1]) continue;`.

* **Outer loop (index `i`)**

  * It runs from `0` up to `nums.length - 4` (inclusive).
  * Before processing each `i`, we check

    ```java
    if (i > 0 && nums[i] == nums[i - 1]) continue;
    ```

    to avoid generating quadruplets that start with the same first element more than once.

* **Inner loop (index `j`)**

  * For each `i`, `j` runs from `i + 1` to `nums.length - 3` (inclusive).
  * Similarly, skip any `j` where `nums[j] == nums[j - 1]` (but only if `j > i+1`) to avoid duplicate second elements at the same first element.

* **Two‐pointer section (`left` and `right`)**

  * We set `left = j + 1` and `right = nums.length - 1`.
  * We compute `sum = nums[i] + nums[j] + nums[left] + nums[right]`.

    * **If** it equals `target`, record this quadruplet. Then advance `left` while `nums[left] == nums[left + 1]` (skipping duplicates of the third element), and similarly decrease `right` while `nums[right] == nums[right - 1]`. Finally, do `left++` and `right--` to move on.
    * **If** it’s less than `target`, you need a bigger sum, so increment `left++` (moving to a larger number).
    * **If** it’s greater than `target`, you need a smaller sum, so decrement `right--`.

This ensures each quadruplet `[nums[i], nums[j], nums[left], nums[right]]` is unique, because any time you would reuse the same number for `i`, `j`, `left`, or `right`, you explicitly skip it.

---

## Complexity Analysis

* **Time Complexity: $O(n^3)$**

  * Sorting takes $O(n \log n)$.
  * Then we have two nested loops ($i$-loop and $j$-loop), each up to $n$, and inside those loops a two‐pointer sweep in $O(n)$.
  * In total: $O(n \log n) + O(n^3) = O(n^3)$ for large $n$.
  * Since the problem constraint says `1 <= nums.length <= 200`, an $O(n^3)$ solution is acceptable.

* **Space Complexity: $O(\log n)$ or $O(n)$**

  * The extra space beyond the input and output list is just what the sorting algorithm requires (e.g. Java’s built‐in `Arrays.sort(...)` may use $O(\log n)$ stack space).
  * We also allocate a `List<List<Integer>> result` that holds at most $O(n^3)$ quadruplets in the worst case, but that’s unavoidable if you have to return all solutions. In practice, the number of valid quadruplets is usually far smaller than $\binom{n}{4}$.

---

## Example Walkthrough

```java
int[] nums = {1, 0, -1, 0, -2, 2};
int target = 0;
```

1. **Sort** → `nums = [-2, -1, 0, 0, 1, 2]`.

2. **i = 0** (`nums[0] = -2`):

   * Skip nothing yet (first iteration).
   * **j = 1** (`nums[1] = -1`):

     * `left = 2` → `nums[left] = 0`
     * `right = 5` → `nums[right] = 2`
     * sum = $(-2) + (-1) + 0 + 2 = -1$ < 0 → `left++` → now `left = 3`
     * sum = $(-2) + (-1) + 0 + 2 = -1$ still, but now `nums[left]` is `0` at index 3 (duplicate). → `left++` → `left = 4`
     * sum = $(-2) + (-1) + 1 + 2 = 0$. Found quadruplet: `[-2, -1, 1, 2]`.

       * Skip duplicates? `nums[left] = 1`, next is `nums[5] = 2`—no duplicates there. Similarly on right. So `left++` → `left=5`, `right--`→ `right=4`; now `left >= right`, so stop.
   * **j = 2** (`nums[2] = 0`):

     * But check if it’s a duplicate of `nums[1]`? No (`0 != -1`), so proceed.
     * `left = 3`, `right = 5`:

       * sum = $(-2) + 0 + 0 + 2 = 0$. Found `[-2, 0, 0, 2]`. Skip duplicates of `0` on left:

         * `nums[3]==nums[2]`? Actually, `nums[2]=0` and `nums[3]=0`, but we only skip if `left < right && nums[left] == nums[left+1]`. Here `left=3`, `left+1=4`: `nums[3]=0` vs. `nums[4]=1` → not equal. So no skip.
       * `left++ → 4`, `right-- → 4`; `left >= right` → stop.
   * **j = 3** (`nums[3] = 0`):

     * But `nums[3] == nums[2]` (both `0`), so skip to avoid duplicate second element.
   * **j = 4** (`nums[4] = 1`):

     * `left = 5`, `right = 5` → `left >= right` already, so no pairs.
   * End of `j` loop.

3. **i = 1** (`nums[1] = -1`):

   * **j = 2** (`nums[2] = 0`):

     * `left = 3`, `right = 5`:

       * sum = $(-1) + 0 + 0 + 2 = 1$ > 0 → `right--` → `right = 4`.
       * sum = $(-1) + 0 + 0 + 1 = 0$. Found `[ -1, 0, 0, 1 ]`.
       * Skip duplicates on left for `0`:

         * `nums[3]=0`, `nums[4]=1` → no skip.
       * `left=4`, `right=3` → stop.
   * **j = 3** (`nums[3] = 0`):

     * But `nums[3] == nums[2]`, so skip.
   * **j = 4** (`nums[4] = 1`):

     * No valid `left < right`.

4. **i = 2** (`nums[2] = 0`):

   * Skip if duplicate? `nums[2] == nums[1]`? No (`0 != -1`), so proceed.
   * **j = 3** (`nums[3] = 0`):

     * `left = 4`, `right = 5`:

       * sum = `0 + 0 + 1 + 2 = 3` > 0 → `right--` → `right = 4`.
       * `left >= right` → no quadruplet.
   * **j = 4** (`nums[4] = 1`): no pairs.

5. **i ≥ 3** will leave fewer than 3 elements to the right, so the loops end.

**Collected quadruplets:**

```
[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
```

No duplicates appear, because we always skipped repeated values of `i`, `j`, `left`, and `right`.

---

## Final Code (with `main` for Testing)

```java
package hard.arrays;

import java.util.*;

public class A01FourNumberSum {

  /**
   * Returns all unique quadruplets [a, b, c, d] from `nums` such that a+b+c+d == target.
   * No duplicate quadruplets are returned.
   *
   * Time Complexity:  O(n^3)
   * Space Complexity: O(log n) (for sorting) or O(n) depending on implementation
   */
  public List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();

    if (nums == null || nums.length < 4) {
      return result;
    }

    Arrays.sort(nums);

    for (int i = 0; i < nums.length - 3; i++) {
      // Skip duplicate i
      if (i > 0 && nums[i] == nums[i - 1]) {
        continue;
      }

      for (int j = i + 1; j < nums.length - 2; j++) {
        // Skip duplicate j
        if (j > i + 1 && nums[j] == nums[j - 1]) {
          continue;
        }

        int left  = j + 1;
        int right = nums.length - 1;

        while (left < right) {
          long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];

          if (sum == target) {
            result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

            // Skip duplicates for left
            while (left < right && nums[left] == nums[left + 1]) {
              left++;
            }
            // Skip duplicates for right
            while (left < right && nums[right] == nums[right - 1]) {
              right--;
            }

            left++;
            right--;
          }
          else if (sum < target) {
            left++;
          }
          else {
            right--;
          }
        }
      }
    }

    return result;
  }

  public static void main(String[] args) {
    A01FourNumberSum solution = new A01FourNumberSum();

    int[] nums1 = {1, 0, -1, 0, -2, 2};
    int target1 = 0;
    List<List<Integer>> result1 = solution.fourSum(nums1, target1);
    System.out.println("Quadruplets summing to 0:");
    for (List<Integer> quad : result1) {
      System.out.println(quad);
    }
    // Expected: [-2, -1, 1, 2]
    //           [-2, 0, 0, 2]
    //           [-1, 0, 0, 1]

    int[] nums2 = {2, 2, 2, 2, 2};
    int target2 = 8;
    List<List<Integer>> result2 = solution.fourSum(nums2, target2);
    System.out.println("Quadruplets summing to 8:");
    for (List<Integer> quad : result2) {
      System.out.println(quad);
    }
    // Expected: [2, 2, 2, 2]
  }
}
```

---

### Summary

* Sort first to enable easy skipping of duplicates.
* Use two nested loops for the first two numbers, then a classic two‐pointer approach to complete the quadruplet.
* Skip any repeated values at each stage (`i`, `j`, `left`, `right`) to ensure each quadruplet is reported exactly once.
* Overall time complexity is $O(n^3)$, which is acceptable for $n \le 200$.
* Space overhead is dominated by the sorting algorithm and the output list.
