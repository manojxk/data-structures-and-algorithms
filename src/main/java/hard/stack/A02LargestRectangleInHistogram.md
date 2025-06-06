**Problem Statement**
You are given an array of non‐negative integers `heights[]`, where each integer represents the height of a bar in a histogram. Each bar has width 1, and they stand side by side. Your task is to compute the area of the **largest rectangle** that can be formed under this histogram.

* For example, if `heights = [2, 1, 5, 6, 2, 3]`, the largest rectangle has area 10 (using bars of heights 5 and 6, spanning width 2).

Formally:

> Return the maximum possible value of `height × width`, where `height` is chosen from one of the bar heights, and `width` is the number of consecutive bars over which that minimum height still applies.

---

## 1) Understand the Naïve Idea (Brute Force)

A straightforward—but inefficient—way to solve this is:

1. For each bar at index `i`, treat it as the limiting height of some rectangle.
2. Extend left from `i` as far as all bars are ≥ `heights[i]`; similarly extend right from `i` as far as bars are ≥ `heights[i]`.
3. The total width for bar `i` is `(rightIndex – leftIndex + 1)`. Multiply that by `heights[i]` to get area.
4. Compute that for every `i` and take the maximum.

However, doing this “extend left/right” for each `i` takes **O(n)** in the worst case, and repeating for all `n` bars gives **O(n²)** time. When `n` is large, this becomes too slow.

---

## 2) Efficient Approach Using Stacks in O(n) Time

We can achieve **O(n)** by noticing that, for each bar `i`, we really only need to know **how far left** and **how far right** we can go before a smaller bar appears. In other words:

* “Nearest Smaller to Left” (NSL) index: the first index to the left of `i` whose height is strictly less than `heights[i]`.
* “Nearest Smaller to Right” (NSR) index: the first index to the right of `i` whose height is strictly less than `heights[i]`.

Once you know:

* `left[i] =` index of the nearest smaller bar on the left of `i` (or `–1` if none),
* `right[i] =` index of the nearest smaller bar on the right of `i` (or `n` if none),

then the **width** of the largest rectangle that uses `heights[i]` as its height is:

```
width[i] = (right[i] – left[i] – 1).
```

Why?

* All indices in the (open) interval `(left[i], right[i])` have height ≥ `heights[i]`.
* That interval’s length is `right[i] – left[i] – 1`.
* Therefore, bar `i` can extend left until `left[i] + 1` and right until `right[i] – 1`, inclusive.

Then the area for bar `i` is:

```
area[i] = heights[i] × width[i].
```

Finally, the answer is `max(area[i])` over all `i`.

### How to Find NSL/NSR in O(n) Using a Stack

#### 2.1 Nearest Smaller to Left (NSL)

* We will build an array `left[]` of length `n`.
* We iterate `i` from `0` up to `n−1`.
* Maintain a stack that holds pairs `(height, index)` in **increasing** order of height from bottom to top.
* For each `i`, we pop from the stack until either the stack is empty or the top’s `height < heights[i]`.

  * If the stack is empty after popping, then no smaller bar exists on the left; set `left[i] = –1`.
  * Otherwise, `left[i] = stack.peek().index`.
* Then push `(heights[i], i)` onto the stack.

This way, each bar is pushed and popped at most once, for **O(n)** total.

#### 2.2 Nearest Smaller to Right (NSR)

* Similarly, build an array `right[]` of length `n`.
* But now iterate `i` from `n−1` down to `0`, again using a stack that keeps `(height, index)` in increasing‐height order.
* For each `i`, pop until the stack is empty or top’s height < `heights[i]`.

  * If empty, `right[i] = n` (sentinel meaning “no smaller to the right”).
  * Otherwise, `right[i] = stack.peek().index`.
* Then push `(heights[i], i)`.
* Finally, reverse `right[]` if you happened to accumulate results in reverse order; but it’s simpler to just build `right[i]` directly at index `i`.

---

## 3) Step-by-Step Code and Explanation

Below is a complete Java solution with detailed comments, suitable for someone learning these ideas for the first time.

```java
package hard.stack;

import java.util.*;

/*
 * Problem: Largest Rectangle In Histogram
 *
 * Given an array of bar heights (histogram), return the area of the largest rectangle under the histogram.
 *
 * Example:
 *   heights = [2, 1, 5, 6, 2, 3]
 *   largest area = 10  (using bars of height 5 and 6, width = 2)
 */

public class A02LargestRectangleInHistogram {

  /**
   * 1) Brute Force version (for comparison).
   *    Time: O(n^2).  Space: O(1).
   *    For each bar i, expand left/right to find the maximal width.
   */
  public static int largestRectangleBruteForce(int[] heights) {
    int n = heights.length;
    int maxArea = 0;

    // Try every possible starting bar i
    for (int i = 0; i < n; i++) {
      int minHeight = heights[i];

      // Expand j from i to the end, keeping track of the minimum height encountered
      for (int j = i; j < n; j++) {
        minHeight = Math.min(minHeight, heights[j]);
        // Width of rectangle from i to j is (j - i + 1)
        int width = j - i + 1;
        int area = minHeight * width;
        maxArea = Math.max(maxArea, area);
      }
    }
    return maxArea;
  }

  /**
   * 2) Efficient O(n) solution using stacks to compute NSL and NSR.
   *
   *    Steps:
   *      A) Compute 'left[i]' = index of Nearest Smaller to Left of i (or -1 if none).
   *      B) Compute 'right[i]' = index of Nearest Smaller to Right of i (or n if none).
   *      C) width[i] = right[i] - left[i] - 1
   *      D) area[i] = heights[i] * width[i]
   *      E) return max(area[i])
   */

  // A) Compute Nearest Smaller to Left (NSL) for each i
  public static int[] computeNSL(int[] heights) {
    int n = heights.length;
    int[] left = new int[n];
    // Stack of pairs (height, index), but we’ll just store int[] of size 2
    Stack<int[]> stack = new Stack<>();

    for (int i = 0; i < n; i++) {
      // Pop until we find a smaller height or stack becomes empty
      while (!stack.isEmpty() && stack.peek()[0] >= heights[i]) {
        stack.pop();
      }
      // If stack is empty, no smaller to left
      if (stack.isEmpty()) {
        left[i] = -1;
      } else {
        // Top of stack is nearest smaller on left
        left[i] = stack.peek()[1];
      }
      // Push current bar onto stack
      stack.push(new int[]{ heights[i], i });
    }
    return left;
  }

  // B) Compute Nearest Smaller to Right (NSR) for each i
  public static int[] computeNSR(int[] heights) {
    int n = heights.length;
    int[] right = new int[n];
    Stack<int[]> stack = new Stack<>();

    // Process from right to left
    for (int i = n - 1; i >= 0; i--) {
      // Pop until we find a smaller height or stack is empty
      while (!stack.isEmpty() && stack.peek()[0] >= heights[i]) {
        stack.pop();
      }
      // If stack is empty, no smaller to right
      if (stack.isEmpty()) {
        right[i] = n; // sentinel meaning “off the right end”
      } else {
        // Top of stack is nearest smaller on right
        right[i] = stack.peek()[1];
      }
      // Push current bar
      stack.push(new int[]{ heights[i], i });
    }
    return right;
  }

  // C+D+E) Use NSL and NSR to compute the maximum area
  public static int largestRectangleArea(int[] heights) {
    int n = heights.length;
    if (n == 0) return 0;

    // A) nearest smaller to left
    int[] left = computeNSL(heights);
    // B) nearest smaller to right
    int[] right = computeNSR(heights);

    int maxArea = 0;

    // C) For each bar i, the width of the largest rectangle using bar i’s height is:
    //      width = (right[i] - left[i] - 1)
    // D) area = heights[i] * width
    for (int i = 0; i < n; i++) {
      int width = right[i] - left[i] - 1;
      int area = heights[i] * width;
      maxArea = Math.max(maxArea, area);
    }

    return maxArea;
  }

  // ------------------------------------------------------------
  // Example / Test
  // ------------------------------------------------------------
  public static void main(String[] args) {
    int[] heights = {2, 1, 5, 6, 2, 3};

    System.out.println("Brute‐Force Result:  " 
         + largestRectangleBruteForce(heights)); // Expected: 10
    System.out.println("O(n) Stack‐Based Result:  " 
         + largestRectangleArea(heights));       // Expected: 10
  }
}
```

---

## 4) Detailed Explanation for Beginners

1. **Why Nearest Smaller to Left (NSL)?**

   * For a given bar `i`, we want to know how far left we can extend a rectangle of height `heights[i]`. We can only extend left until we hit a bar whose height is strictly less than `heights[i]`.
   * That bar’s index is exactly `NSL(i)`. If there is none, we imagine an index of `–1`.

2. **Why Nearest Smaller to Right (NSR)?**

   * Similarly, to know how far right we can go, we look for the first index `j > i` such that `heights[j] < heights[i]`. That is `NSR(i)`. If none exists, we treat `NSR(i) = n`.

3. **How to Compute NSL in O(n) with a Single Pass and a Stack**

   * We maintain a stack of pairs `(height, index)`. We iterate `i` from left to right.
   * Before pushing `(heights[i], i)`, we pop off any bars on the stack whose height ≥ `heights[i]`. Why? Because they cannot serve as “nearest smaller” for any future bar to the right—they are taller or equal.
   * When we pop until the top’s height < `heights[i]` (or stack empties), two cases:

     * If stack is empty, no smaller bar exists to the left, so `left[i] = –1`.
     * Otherwise, `left[i] = stack.peek().index`.
   * Finally, push `(heights[i], i)` onto the stack.
   * Each bar is pushed exactly once and popped at most once, so total cost is O(n).

4. **How to Compute NSR in O(n) with a Single Reverse Pass**

   * Now iterate `i` from right to left, use the same logic:

     * While stack is non‐empty and top’s height ≥ `heights[i]`, pop.
     * If stack empties, `right[i] = n`. Otherwise, `right[i] = stack.peek().index`.
     * Push `(heights[i], i)` onto the stack.
   * Reverse order is necessary so that “right” neighbors have been processed first.

5. **Putting It Together**

   * Once you have `left[i]` and `right[i]`, the **maximum width** for a rectangle of height `heights[i]` is from `(left[i] + 1)` to `(right[i] − 1)` inclusive. That width equals `(right[i] − left[i] − 1)`.
   * The area of that rectangle is `heights[i] * (right[i] − left[i] − 1)`.
   * You compute that for all `i` in one final O(n) loop and take the maximum.

---

## 5) Complexity Analysis

* **Time Complexity**

  1. Computing NSL takes O(n) because each index is pushed/popped at most once.
  2. Computing NSR also takes O(n).
  3. Final pass to compute areas and find the maximum takes O(n).
     Therefore, total is **O(n)**.

* **Space Complexity**

  * We used two auxiliary arrays `left[]` and `right[]` of length `n` each, and two stacks that each hold at most `n` elements in the worst case. Hence total auxiliary space is **O(n)**.

---

**In summary**, by precomputing, for each bar, how far it can stretch left and right before hitting a shorter bar, we avoid the O(n²) brute‐force expansion. Using a stack to find nearest smaller bars in a single pass yields an overall O(n) solution.
