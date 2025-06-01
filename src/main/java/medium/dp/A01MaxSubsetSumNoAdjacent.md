**Problem Explanation**
Given an array of positive integers, we want to find the largest sum we can obtain by adding up elements that are **never adjacent** in the original array. For an empty array, the answer is 0.

> **Example**
>
> ```
> array = [75, 105, 120, 75, 90, 135]
> ```
>
> One valid choice of non‐adjacent elements is `[75 (index 0), 120 (index 2), 135 (index 5)]`, which sums to `330`. No other combination of non‐adjacent picks exceeds 330, so the answer is 330.

---

## 1. Brute‐Force Recursive Solution (O(2ⁿ) Time • O(n) Space)

### Idea

At each index `i`, you have two choices:

1. **Include** `array[i]` in your sum, then skip to index `i−2` (because you can’t take the adjacent element `i−1`).
2. **Exclude** `array[i]` and move to index `i−1`.

You recursively compute the best of those two choices, until you reach the beginning of the array.

### Pseudo‐Steps

1. If `index < 0`, no elements remain, return 0.
2. If `index == 0`, only one element is possible: return `array[0]`.
3. Otherwise:

   * Let **include** = `array[index] + helper(array, index − 2)`.
   * Let **exclude** = `helper(array, index − 1)`.
   * Return `max(include, exclude)`.

### Java Code

```java
public class A01MaxSubsetSumNoAdjacent {

  // 1) Brute‐Force Recursive Method
  public static int maxSubsetSumNoAdjacent(int[] array) {
    if (array.length == 0) return 0;
    return maxSubsetSumNoAdjacentHelper(array, array.length - 1);
  }

  private static int maxSubsetSumNoAdjacentHelper(int[] array, int index) {
    // Base cases
    if (index < 0) return 0;
    if (index == 0) return array[0];

    // Option A: include array[index], skip index-1
    int include = array[index]
                + maxSubsetSumNoAdjacentHelper(array, index - 2);

    // Option B: exclude array[index], move to index-1
    int exclude = maxSubsetSumNoAdjacentHelper(array, index - 1);

    // Choose whichever is larger
    return Math.max(include, exclude);
  }

  public static void main(String[] args) {
    int[] array = {75, 105, 120, 75, 90, 135};
    System.out.println(maxSubsetSumNoAdjacent(array)); // 330
  }
}
```

* **Time Complexity:**
  Each call branches into two recursive calls (include/exclude), leading to $O(2^n)$ in the worst case.
* **Space Complexity:**
  The recursion depth can go up to $O(n)$, so $O(n)$ call‐stack space.

---

## 2. Optimized DP Solution (O(n) Time • O(1) Space)

We can avoid exponential recursion by keeping track—at each index—of:

* `prevTwo`: the best sum up to index `i − 2`
* `prevOne`: the best sum up to index `i − 1`

When you arrive at `i`, either you:

* **Include** `array[i]` (and therefore add it to `prevTwo`), or
* **Exclude** `array[i]` (and keep `prevOne`)

The new best sum at `i` is `max(prevOne, prevTwo + array[i])`. Then shift your window so that:

* `prevTwo ← prevOne`
* `prevOne ← currentBest`

By rolling forward in a single pass, you only need constant extra space.

### Pseudo‐Steps

1. If the array is empty, return 0.
2. If it has one element, return `array[0]`.
3. Let

   ```
   prevTwo = array[0];
   prevOne = max(array[0], array[1]);
   ```
4. For `i` from 2 to `array.length − 1`:

   ```
   current = max(prevOne, prevTwo + array[i]);
   prevTwo = prevOne;
   prevOne = current;
   ```
5. Return `prevOne` (the best up to the last index).

### Java Code

```java
public class A01MaxSubsetSumNoAdjacent {

  // 2) Optimized DP Method (O(n) Time, O(1) Space)
  public static int maxSubsetSumNoAdjacentDP(int[] array) {
    if (array.length == 0) return 0;
    if (array.length == 1) return array[0];

    int prevTwo = array[0];
    int prevOne = Math.max(array[0], array[1]);

    for (int i = 2; i < array.length; i++) {
      int current = Math.max(prevOne, prevTwo + array[i]);
      prevTwo = prevOne;
      prevOne = current;
    }

    return prevOne;
  }

  public static void main(String[] args) {
    int[] array = {75, 105, 120, 75, 90, 135};
    System.out.println(maxSubsetSumNoAdjacentDP(array)); // 330
  }
}
```

* **Time Complexity:** $O(n)$ because you make exactly one pass through the array.
* **Space Complexity:** $O(1)$ extra space—only a few integer variables (`prevTwo`, `prevOne`, `current`).

---

## 3. Alternative DP with O(n) Extra Space (For Clarity)

If you prefer a DP array, you can store in `dp[i]` the best sum you can achieve up to index `i`. Then:

1. `dp[0] = array[0]`
2. `dp[1] = max(array[0], array[1])`
3. For `i` from 2 to n−1:

   ```
   dp[i] = max(dp[i - 1], dp[i - 2] + array[i]);
   ```
4. Return `dp[n − 1]`.

```java
public class A01MaxSubsetSumNoAdjacent {

  // Alternative DP Approach (O(n) Time, O(n) Space)
  public static int findMaxSubsetSum(int[] array) {
    int n = array.length;
    if (n == 0) return 0;
    if (n == 1) return array[0];
    if (n == 2) return Math.max(array[0], array[1]);

    int[] dp = new int[n];
    dp[0] = array[0];
    dp[1] = Math.max(array[0], array[1]);

    for (int i = 2; i < n; i++) {
      dp[i] = Math.max(dp[i - 1], dp[i - 2] + array[i]);
    }

    return dp[n - 1];
  }

  public static void main(String[] args) {
    int[] array = {75, 105, 120, 75, 90, 135};
    System.out.println(findMaxSubsetSum(array)); // 330
  }
}
```

* **Time Complexity:** $O(n)$.
* **Space Complexity:** $O(n)$ for the `dp` array.

---

### Summary

* **Brute‐Force Recursion:** Exponential time, but simple to understand:
  $\text{T}(n) = \text{T}(n-1) + \text{T}(n-2)$.
* **Optimized DP (O(n) time, O(1) space):** Keep only two running “best” values (`prevTwo`, `prevOne`) and update in one pass.
* **DP Array (O(n) time, O(n) space):** Fill a `dp[]` table where `dp[i]` is the best sum up to index `i`.

For large inputs, you should use the **O(n) time, O(1) space** solution `maxSubsetSumNoAdjacentDP`.
