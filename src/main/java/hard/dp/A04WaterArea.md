**Problem Statement**

> Given an elevation map represented by an array of non‐negative integers `heights[]`, where each element is the height of a pillar at that index, compute how much water can be trapped after raining.

For example:

```
heights = [0,1,0,2,1,0,1,3,2,1,2,1]
```

Visually, water gets trapped in the “valleys” between taller pillars. The task is to sum up all those trapped units.

---

## Two‐Array (“LeftMax / RightMax”) Approach

### Key Observation

At each index `i`, the amount of water that can sit on top of pillar `i` is:

```
min( max height to the left of i,  max height to the right of i )  –  heights[i]
```

That is, water cannot exceed the shorter of the two “walls” on either side. If either side’s wall is shorter than `heights[i]`, no water collects above `i` (the result is zero there).

To compute this efficiently, we precompute for every index:

1. `leftMax[i]`  = the highest pillar height in `heights[0..i]`.
2. `rightMax[i]` = the highest pillar height in `heights[i..n−1]`.

Then

```
waterAt[i] = max( 0, min(leftMax[i], rightMax[i]) − heights[i] )
```

Summing `waterAt[i]` across all `i` gives the total trapped water.

---

### Step‐by‐Step Code Explanation

```java
public class A15TrappingRainWater {

  public static int trap(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0;
    }

    int n = heights.length;
    int totalWater = 0;

    // 1) Allocate two auxiliary arrays of length n:
    //    leftMax[i]  will hold the maximum height from the left up to i (inclusive).
    //    rightMax[i] will hold the maximum height from the right up to i (inclusive).
    int[] leftMax  = new int[n];
    int[] rightMax = new int[n];

    // 2) Fill leftMax[] in one left→right pass:
    leftMax[0] = heights[0];
    for (int i = 1; i < n; i++) {
      // The highest to the left of i is either the previous leftMax or heights[i].
      leftMax[i] = Math.max(leftMax[i - 1], heights[i]);
    }

    // 3) Fill rightMax[] in one right→left pass:
    rightMax[n - 1] = heights[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      // The highest to the right of i is either the next rightMax or heights[i].
      rightMax[i] = Math.max(rightMax[i + 1], heights[i]);
    }

    // 4) Now compute trapped water at each index:
    for (int i = 0; i < n; i++) {
      // Water level is limited by the shorter of the two “walls.”
      int waterLevel = Math.min(leftMax[i], rightMax[i]);

      // If that water level exceeds the pillar’s height, the difference is trapped water.
      if (waterLevel > heights[i]) {
        totalWater += waterLevel - heights[i];
      }
      // Otherwise (waterLevel <= heights[i]), no water is trapped at i (adds zero).
    }

    return totalWater;
  }

  public static void main(String[] args) {
    // Example 1
    int[] heights1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    System.out.println("Trapped Water: " + trap(heights1)); // 6

    // Example 2
    int[] heights2 = {4, 2, 0, 3, 2, 5};
    System.out.println("Trapped Water: " + trap(heights2)); // 9
  }
}
```

---

### Why This Works

1. **leftMax\[i]**:

   * By construction, `leftMax[i]` is the maximum height among `heights[0], heights[1], …, heights[i]`.
   * Any water above index `i` cannot exceed that highest left pillar.

2. **rightMax\[i]**:

   * Similarly, `rightMax[i]` is the maximum height among `heights[i], heights[i+1], …, heights[n−1]`.
   * Water above `i` cannot exceed that highest right pillar.

3. **Water at Index i**:

   * The water level that can stand above index `i` is therefore the smaller of those two walls:

     ```
     waterLevel = min(leftMax[i], rightMax[i]);
     ```
   * If that level is taller than the pillar itself (`heights[i]`), the excess `waterLevel − heights[i]` collects there.
   * If the pillar is already as tall (or taller) than either wall, no water can collect (`waterLevel ≤ heights[i]` ⇒ 0 trapped).

4. **Summation**

   * Summing `(waterLevel − heights[i])` for all `i` yields the total trapped volume.

---

### Time and Space Complexities

* **Time Complexity: O(n)**

  1. One pass to build `leftMax[]` (size n).
  2. One pass to build `rightMax[]` (size n).
  3. One pass to sum up the trapped water (size n).
     Altogether: 3·O(n) = O(n).

* **Space Complexity: O(n)**

  * Two additional arrays, `leftMax` and `rightMax`, each of length n, are used.
  * Apart from those, only a constant number of extra variables (`totalWater`, loop indices) are needed.

If you need to reduce auxiliary space to O(1), you can instead use a two‐pointer approach (moving inward from both ends), but this two‐array method is often the simplest to understand first.

---

**That completes the explanation of the “Trapping Rain Water” solution using leftMax and rightMax arrays.**
