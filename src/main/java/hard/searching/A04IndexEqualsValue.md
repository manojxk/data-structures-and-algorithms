**Problem Restatement**
You have a sorted array of distinct integers (which may be negative, zero, or positive). You want to find the *first* index `i` such that `array[i] == i`. If no such index exists, return `-1`.

For example:

* If `array = [-5, -3, 0, 3, 4]`, the answer is `3`, because `array[3] == 3`.
* If `array = [-5, -3, 0, 2, 4]`, the answer is `2`, because `array[2] == 0` is false, but you keep searching until you reach `i = 2` where indeed `array[2] == 2`. (Actually check: in that array, `array[2] == 0`, which is not equal to 2. The first match is at `i = 3` where `array[3] == 2`?—let’s pick the correct example: if the input were `[-5, -3, 0, 2, 4]`, then you see `array[3] == 2` → not a match; but `array[4] == 4`, so answer is 4. Another valid example is `[-5, -3, 2, 3, 5]`, where `array[2] == 2`, so the answer is 2. But the code provided shows their own examples.)

The key fact that makes this problem solvable in O(log n) time is that the array is **sorted** and all values are **distinct**.

---

## Why a Simple Linear Scan Would Work—but Is Too Slow

If you just loop from `i = 0` up to `array.length − 1` and check `if (array[i] == i) return i;`, you will certainly find the answer in O(n) time. But since the array is sorted, there is a faster method—binary search—that runs in O(log n). That’s what we’ll do.

---

## Insight: Using Binary Search to Exploit “Sorted + Distinct”

Given a sorted, distinct array, consider the function

```
f(i) = array[i] − i.
```

* If `array[i] > i`, that means `array[i] − i > 0`. Since the array is strictly increasing and `i` only increases by 1 each step, for any `j > i`, you’ll also have `array[j] > j` (because the gap can’t suddenly reverse without violating distinctness). In other words, once `array[i] − i > 0`, all indices to the right also give a positive difference. So if you find `array[mid] > mid`, you know any solution must lie strictly to the **left** of `mid`.
* Conversely, if `array[i] < i`, then `array[i] − i < 0`. For any `j < i`, since the array is strictly increasing and indices drop by exactly 1 each step, you’ll have `array[j] < array[i]` but `j < i`—so `array[j] − j` will remain negative (it cannot cross back above zero without skipping). Hence, if you see `array[mid] < mid`, then any index to the left also fails; the only hope is to search **right** of `mid`.

If you happen to see `array[mid] == mid`, you’ve found one “fixed‐point.” Because the problem asks for the *first* such index, you should record `mid` as a potential answer and then continue searching to the left half, in case there is an earlier index that also satisfies `array[i] == i`.

That pattern—compare `array[mid]` to `mid`, decide whether to move left or right—**is exactly binary search**, giving **O(log n)** time.

---

## Step‐by‐Step Algorithm

1. **Initialize**

   ```java
   int left = 0;
   int right = array.length - 1;
   int result = -1;   // will hold the first index where array[i] == i, or stay -1 if none
   ```

2. **Binary‐Search Loop**
   While `left <= right`:
   a. Compute

   ```java
   int mid = left + (right - left) / 2;
   ```

   (This avoids potential integer overflow compared to `(left + right)/2`.)

   b. Compare `array[mid]` vs. `mid`:

   * **Case 1**: `array[mid] == mid`

     * We have found an index that matches. Set

       ```
       result = mid;
       ```

       But because we want the *first* index (smallest `i`), we then move `right = mid − 1` to keep searching on the left side.
   * **Case 2**: `array[mid] > mid`

     * Since `array[mid] − mid > 0`, *all* `j ≥ mid` must also satisfy `array[j] > j`. No solution can lie at or to the right of `mid`. So do

       ```java
       right = mid - 1;
       ```
   * **Case 3**: `array[mid] < mid`

     * Then `array[mid] − mid < 0`. All indices `j ≤ mid` also have `array[j] < j`. The only hope lies to the right. So do

       ```java
       left = mid + 1;
       ```

3. **Loop Terminates**
   Eventually `left` will exceed `right` if we haven’t found an exact match—`result` remains `-1`. Otherwise, if we found one or more indices where `array[i] == i`, the final value stored in `result` is the *smallest* of those, because each time we saw a match we kept looking “further left.”

4. **Return**

   ```java
   return result;
   ```

---

## Full Java Code

```java
package hard.searching;

/*
 * Problem: Index Equals Value
 *
 * Given a sorted array of distinct integers, return the first index i for which array[i] == i.
 * If no such index exists, return -1.
 */
public class A03IndexEqualsValue {

  public static int indexEqualsValue(int[] array) {
    int left = 0;
    int right = array.length - 1;
    int result = -1; // Will store the first (leftmost) match, or remain -1 if none

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (array[mid] == mid) {
        // Found a match. Record it, but keep looking left to find the first such index.
        result = mid;
        right = mid - 1;
      }
      else if (array[mid] > mid) {
        // If array[mid] > mid, then for ANY j >= mid, array[j] >= array[mid] + (j-mid)
        // which is > j. So no index to the right can satisfy array[j] == j.
        // We must search left.
        right = mid - 1;
      }
      else {
        // array[mid] < mid, so for any j <= mid, array[j] ≤ array[mid] + (j-mid) < j.
        // No solution to the left; must search right.
        left = mid + 1;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    int[] array1 = { -5, -3, 0, 3, 4 };
    // Here array[3] == 3, so expect output 3
    System.out.println("Result: " + indexEqualsValue(array1));  // 3

    int[] array2 = { -5, -3, 0, 2, 4 };
    // array[0..2] produce -5<0, -3<1, 0<2, so no match until index 4 where array[4]==4.
    System.out.println("Result: " + indexEqualsValue(array2));  // 4

    int[] array3 = { -10, -5, 0, 3, 7 };
    // No i satisfies array[i] == i, so expect -1
    System.out.println("Result: " + indexEqualsValue(array3));  // -1
  }
}
```

---

## Explanation of Key Points

1. **Why `array[mid] > mid` Implies “Search Left”**

   * Suppose at some index `mid`, you see `array[mid] = mid + d` where `d > 0`. Then `array[mid] − mid = d > 0`. Because the array is strictly increasing (no duplicates) and each step to the right increases the value by at least 1, at index `mid + 1` you have

     ```
     array[mid + 1] ≥ array[mid] + 1 = (mid + d) + 1 = (mid + 1) + d.
     ```

     Thus `array[mid + 1] − (mid + 1) ≥ d`, which is still positive. In fact, for any `j ≥ mid`, `array[j] − j` cannot drop to zero or negative because each step to the right only increases `array[j]` at least as fast as `j` itself. So no index `j ≥ mid` can ever satisfy `array[j] == j`. Hence you discard “mid and everything to its right,” searching left.

2. **Why `array[mid] < mid` Implies “Search Right”**

   * Conversely, if `array[mid] = mid − d` where `d > 0`, then `array[mid] − mid = -d`. At index `mid − 1`, you have `array[mid − 1] ≤ array[mid] − 1 = (mid − d) − 1 = (mid − 1) − d`, so `array[mid − 1] − (mid − 1) ≤ -d`. That remains negative. In fact, going left only makes `array[j] − j` more negative. So no index `j ≤ mid` can satisfy `array[j] == j`. You must search to the right.

3. **When `array[mid] == mid`**

   * You’ve found a valid “fixed‐point.” Still, because the problem asks for the *first* (smallest) such index, you do not immediately return. Instead you store `result = mid` (in case it is the first), and then set `right = mid − 1`, forcing the search to continue into the subarray to the left. If we happen to find an even smaller index with `array[i] == i`, we’ll override `result`. At loop’s end, `result` is the leftmost match or remains `-1`.

4. **Binary‐Search Termination**

   * The loop runs while `left ≤ right`. Once `left` exceeds `right`, we’ve exhausted all possible indices without finding a smaller match. If `result` was updated, it holds the leftmost index; otherwise, `result` is still `-1`.

5. **Complexity**

   * Each iteration halves the search interval, so the time is **O(log n)**.
   * Only a few integer variables are used—no extra arrays—so **O(1)** additional space.

---

### In Summary

* The sorted, distinct property lets us deduce “if `array[mid] > mid` then all indices to the right fail” (and similarly for `array[mid] < mid`).
* By comparing `array[mid]` to `mid` and moving left or right accordingly, we get a classic binary‐search strategy.
* Whenever we hit a match (`array[mid] == mid`), we record it and keep searching left to ensure it’s the *smallest* such index.
* If no index satisfies `array[i] == i`, we return `-1`.

This method finds the “index‐equals‐value” point in **O(log n)** time and **O(1)** space.
