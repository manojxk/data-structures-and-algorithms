**Problem Restatement**
Given an array `buildings[]` where each element is the positive height of a building, and a direction (`"EAST"` or `"WEST"`), return a list of the indices of all buildings that can see the sunset. All buildings face the same direction:

* If `direction == "EAST"`, each building looks **rightward** along the array.
* If `direction == "WEST"`, each building looks **leftward** along the array.

A building “sees the sunset” if its height is strictly greater than every building in front of it in the direction it faces.

> **Example 1**
>
> ```
> buildings = [3, 5, 4, 4, 3, 1, 3, 2]
> direction = "EAST"
> ```
>
> Output: `[1, 3, 6, 7]`
>
> * Building 1 (height 5) sees over all buildings to its right.
> * Building 3 (height 4) sees over the 4, 3, 1, 3, 2 to its right.
> * Building 6 (height 3) sees over the 2 to its right.
> * Building 7 (height 2) is the farthest right, so it always sees the sunset.

> **Example 2**
>
> ```
> buildings = [3, 5, 4, 4, 3, 1, 3, 2]
> direction = "WEST"
> ```
>
> Output: `[0, 1]`
>
> * Building 0 (height 3) is on the far left, so it sees the sunset.
> * Building 1 (height 5) is taller than building 0, so it also sees over everything to its left.

---

## Approach

1. **Decide Traversal Order**

   * If facing **EAST**, you must compare each building to all buildings **to its right**. It’s easiest to scan from **right to left** and keep track of the maximum height seen so far.
   * If facing **WEST**, you compare each building to those **to its left**. Scan from **left to right**, again maintaining the maximum height seen so far.

2. **Maintain `maxHeight`**

   * Initialize `maxHeight = 0`.
   * Whenever you visit a building at index `i`, if `buildings[i] > maxHeight`, then building `i` can see the sunset (because it’s taller than every building you’ve already looked at in that direction).
   * In that case, record `i` in your result and update `maxHeight = buildings[i]`.

3. **Collect Indices in Ascending Order**

   * When facing **WEST**, scanning from left→right naturally yields ascending indices, so you can add `i` to the result list directly.
   * When facing **EAST**, scanning from right→left collects indices in descending order. After the scan, reverse the collected list to restore ascending index order.

4. **Return** the result list.

This runs in **O(n)** time (one pass through the array) and **O(n)** space for storing result indices.

---

## Java Implementation

```java
package medium.stacks;

import java.util.*;

public class A03SunsetViews {

  /**
   * Returns the list of indices of buildings that can see the sunset.
   *
   * @param buildings an array of positive building heights
   * @param direction either "EAST" or "WEST"
   * @return a list of indices (in ascending order) of buildings that see the sunset
   */
  public static List<Integer> sunsetViews(int[] buildings, String direction) {
    List<Integer> result = new ArrayList<>();
    int maxHeight = 0;

    if (direction.equals("EAST")) {
      // Scan from right to left (furthest-east building first)
      for (int i = buildings.length - 1; i >= 0; i--) {
        if (buildings[i] > maxHeight) {
          result.add(i);
          maxHeight = buildings[i];
        }
      }
      // Collected in descending index order; reverse to ascending
      Collections.reverse(result);
    } else { // direction == "WEST"
      // Scan from left to right (furthest-west building first)
      for (int i = 0; i < buildings.length; i++) {
        if (buildings[i] > maxHeight) {
          result.add(i);
          maxHeight = buildings[i];
        }
      }
    }

    return result;
  }

  public static void main(String[] args) {
    int[] buildings = {3, 5, 4, 4, 3, 1, 3, 2};

    // Facing EAST
    System.out.println(sunsetViews(buildings, "EAST"));
    // Expected: [1, 3, 6, 7]

    // Facing WEST
    System.out.println(sunsetViews(buildings, "WEST"));
    // Expected: [0, 1]
  }
}
```

---

### Explanation of Key Steps

* **Facing EAST → scan right to left**

  * Start with `maxHeight = 0`.
  * Visit index 7 (height 2) → 2 > 0, so building 7 sees the sunset. Record `7`, set `maxHeight = 2`.
  * Next index 6 (height 3) → 3 > 2, building 6 sees it. Record `6`, set `maxHeight = 3`.
  * Index 5 (height 1) → 1 ≤ 3, cannot see (blocked).
  * Index 4 (height 3) → 3 ≤ 3, cannot see (needs to be **strictly** greater).
  * Index 3 (height 4) → 4 > 3, record `3`, update `maxHeight = 4`.
  * Index 2 (height 4) → 4 ≤ 4, skip.
  * Index 1 (height 5) → 5 > 4, record `1`, update `maxHeight = 5`.
  * Index 0 (height 3) → 3 ≤ 5, skip.
  * Now recorded `[7, 6, 3, 1]` in that order. Reverse → `[1, 3, 6, 7]`.

* **Facing WEST → scan left to right**

  * Start with `maxHeight = 0`.
  * Visit index 0 (height 3) → 3 > 0, record `0`, `maxHeight = 3`.
  * Index 1 (height 5) → 5 > 3, record `1`, `maxHeight = 5`.
  * Index 2 (height 4) → 4 ≤ 5, skip.
  * …and so on. No others exceed 5. Finally, result = `[0, 1]`.

Because we only do one linear scan and each comparison/update is $O(1)$, the overall complexity is $O(n)$. The result list can be size up to $n$, so we use $O(n)$ additional space for the output.
