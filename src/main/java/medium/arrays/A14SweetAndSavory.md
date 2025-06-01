**Problem Restatement**  
Given an array of dish tastes (positive values = sweet, negative values = savory) and a target total taste, find one sweet-and-savory pair whose sum is as close as possible to—but not exceeding—the target. Return that pair.

---

## Approach: Two-Pointer After Separation and Sorting

1. **Separate** the dishes into two arrays:
   - `savoryDishes[]` ← all negative (savory) values  
   - `sweetDishes[]`  ← all positive (sweet) values

2. **Sort** both arrays in ascending order:
   - After sorting, `savoryDishes` is ascending negative values (e.g. `[-5, -3, …]`).  
   - `sweetDishes` is ascending positive values (e.g. `[1, 3, 7, 9]`).

3. **Initialize**:
   - `closestSum = Integer.MIN_VALUE`  
   - `bestPair = new int[2]`  
   - Two pointers:  
     ```
     i = 0                     // index in savoryDishes (starting at smallest, i.e. most negative)
     j = sweetDishes.length-1  // index in sweetDishes (starting at largest sweet)
     ```

4. **While** `i < savoryDishes.length && j >= 0`:
   - Compute `currentSum = savoryDishes[i] + sweetDishes[j]`.  
   - **If** `currentSum > target`, that sum is too large → **decrement** `j` (move to smaller sweet to reduce the sum).  
   - **Else** (`currentSum ≤ target`), it’s valid.  
     - **If** `currentSum > closestSum`, update:
       ```
       closestSum = currentSum;
       bestPair[0] = savoryDishes[i];
       bestPair[1] = sweetDishes[j];
       ```
     - Then **increment** `i` (to try a less-negative savory, which might bring the sum even closer to target).

5. **Return** `bestPair`.

Because each pointer only moves in one direction, this runs in **O(n log n)** time overall (sorting dominates), and uses **O(n)** space for the two separate arrays.

---

## Java Implementation

```java
package medium.arrays;

import java.util.Arrays;

public class A14SweetAndSavory {

  /**
   * Finds one sweet (positive) and one savory (negative) dish whose sum is
   * the closest possible to the target without exceeding it.
   *
   * Time Complexity:  O(n log n)  — sorting dominates
   * Space Complexity: O(n)
   *
   * @param dishes an array of integers (positive = sweet, negative = savory)
   * @param target the maximum allowed sum
   * @return a pair [savory, sweet] whose sum is ≤ target and as large as possible
   */
  public static int[] findSweetAndSavoryCombo(int[] dishes, int target) {
    // 1) Separate into savory (negative) and sweet (positive) arrays
    int[] savoryDishes = Arrays.stream(dishes).filter(d -> d < 0).toArray();
    int[] sweetDishes  = Arrays.stream(dishes).filter(d -> d > 0).toArray();

    // 2) Sort both arrays
    Arrays.sort(savoryDishes); // most negative → less negative
    Arrays.sort(sweetDishes);  // smallest sweet → largest sweet

    // 3) Two-pointer scan for best (savory + sweet) ≤ target
    int closestSum = Integer.MIN_VALUE;
    int[] bestPair = new int[2];

    int i = 0;                    // index into savoryDishes
    int j = sweetDishes.length - 1; // index into sweetDishes

    while (i < savoryDishes.length && j >= 0) {
      int currentSum = savoryDishes[i] + sweetDishes[j];

      // If the sum exceeds target, move j left to reduce it
      if (currentSum > target) {
        j--;
      } else {
        // currentSum ≤ target: check if it’s closer (i.e. larger) than previous best
        if (currentSum > closestSum) {
          closestSum = currentSum;
          bestPair[0] = savoryDishes[i];
          bestPair[1] = sweetDishes[j];
        }
        // Try a less-negative savory (increase i) to see if we get even closer
        i++;
      }
    }

    return bestPair;
  }

  public static void main(String[] args) {
    int[] dishes = { -3, -5, 1, 7, 3, 9 };
    int target = 8;

    int[] result = findSweetAndSavoryCombo(dishes, target);
    System.out.println(
      "Best Sweet and Savory combo: " + Arrays.toString(result)
    );
    // Possible output: [-5, 9] since -5 + 9 = 4 ≤ 8, and that is as close as we can get
  }
}
```

**How It Works in the Example**  
- `savoryDishes = [-5, -3]`, `sweetDishes = [1, 3, 7, 9]` after sorting.  
- Initialize `i = 0` (savory = –5), `j = 3` (sweet = 9).  
  1. `currentSum = -5 + 9 = 4` (≤ 8), and `4 > Integer.MIN_VALUE`, so `bestPair = [-5, 9]`, `closestSum = 4`. Increment `i` → 1.  
  2. Now `i = 1` (savory = –3), `j = 3` (sweet = 9).  
     `currentSum = -3 + 9 = 6` (≤ 8) and `6 > 4`, so `bestPair = [-3, 9]`, `closestSum = 6`. Increment `i` → 2.  
  3. Now `i = 2` is out of bounds for savory, so we stop.  
  The final `bestPair` is `[-3, 9]` (sum = 6). In fact, that is closer to 8 than 4.  

If the code finds any combination whose sum still ≤ 8 but even larger (say, –3 + 9 = 6), it updates. Once pointers cross, you return the best pair found.

---

### Complexity Analysis

- **Time Complexity:**  
  - Splitting arrays via streams: O(n)  
  - Sorting each: let \(s = |\text{savoryDishes}|, \; t = |\text{sweetDishes}|\). Then sorting costs \(O(s \log s + t \log t)\), but \(s + t \approx n\).  
  - Two-pointer scan: O(s + t) = O(n).  
  → Overall dominated by sorting → **O(n log n)**.

- **Space Complexity:**  
  - We allocate two new arrays (`savoryDishes` and `sweetDishes`), whose combined size is still \(n\).  
  → **O(n)** extra space.

This method efficiently finds one pair (one negative and one positive) whose sum is as large as possible without exceeding the given target.
