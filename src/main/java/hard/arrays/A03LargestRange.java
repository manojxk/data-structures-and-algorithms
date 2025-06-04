Below is a step‐by‐step, beginner‐friendly explanation of how the “Largest Range” solution works, followed by the complete Java code (which you already have). The goal is to make sure every detail is crystal clear.

---

## 1. Restating the Problem

> **Largest Range**
> You are given an array of **distinct** integers (no repeats). We want to find the longest run of consecutive integers that appear anywhere in the array. Formally, if you could pick two integers `[start, end]` such that every integer between `start` and `end` (inclusive) appears somewhere in the input, then `[start, end]` is a “range.” Among all such ranges, return the one with the maximum length `(end – start + 1)`. If more than one range ties for maximum length, you may assume exactly one will be largest (so you don’t have to worry about ties).

**Example 1**

```
nums = [1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6]
```

* Observe that the integers `0, 1, 2, 3, 4, 5, 6, 7` all appear. That is the consecutive block from `0` up to `7`. Its length is `8`.
* No other consecutive block (e.g. `10, 11, 12` is length 3, or `10, 11, 12,  …` etc) is longer than 8.
* So the answer is `[0, 7]` because that represents the longest run of consecutive numbers we can find in the array.

**Example 2**

```
nums = [4, 2, 1, 3]
```

* Here, the numbers `1, 2, 3, 4` all appear, so the block `[1..4]` has length 4. Nothing larger appears, so the answer is `[1, 4]`.

---

## 2. Why Use a HashSet?

To test quickly whether some number `x` is “in the array,” we could scan the array linearly each time. But that would cost O(n) per check, leading to O(n²) behavior if we try to build big ranges by checking all neighbors.

Instead, we:

1. Put **every** number in a `HashSet<Integer>`.

   * A `HashSet` supports **O(1)** (on average) membership checks, i.e. `numSet.contains(x)` is constant time.
   * This lets us expand “left” and “right” around a starting number without rescanning the entire array each time.

2. Once a number has helped form a range, we **remove** it from the set.

   * That way, we never form the same range twice or re‐expand from a number that’s already been counted.
   * Every integer in the input is removed exactly once (either when you start from it, or when you expand past it), so the total cost of all removals is O(n).

Because of these two facts (constant‐time membership check, plus each element only used once), the entire method runs in **O(n)** time. We do pay O(n) memory for the `HashSet`.

---

## 3. High‐Level Steps of the Optimized Algorithm

1. **Build a Set**

   * Create `HashSet<Integer> numSet` and insert all elements of `nums` into it.

2. **Initialize Tracking Variables**

   * `int longestLength = 0;`
   * `int[] bestRange = new int[2];`
     These will keep track of the largest consecutive block seen so far.

3. **For Each Number `num` in `nums`:**
   a. If `num` is no longer in `numSet`, skip it (because it was already used in a previous expansion).
   b. Otherwise, `remove(num)` from `numSet`—mark it as “processed.”
   c. Expand **downward** from `num`, checking `num − 1`, `num − 2`, … as long as those are in `numSet`. Each time we find one, we remove it and keep going. When that stops, we know the left endpoint.
   d. Expand **upward** from `num`, checking `num + 1`, `num + 2`, … as long as those are in `numSet`. Each time we find one, we remove it and keep going. When that stops, we know the right endpoint.
   e. Calculate the length of the current block as `(right − left + 1)`. If it’s bigger than `longestLength`, update `longestLength` and set `bestRange = [left, right]`.

4. **Return** `bestRange`.

Because every integer is removed from the set the first time we touch it, no number ever participates in more than one full expansion pass. Hence the overall cost of all expansions combined is O(n).

---

## 4. Detailed Walkthrough of the Code

```java
import java.util.*;

public class A03LargestRange {

  public int[] largestRange(int[] nums) {
    // 0) Edge case: If the array is empty, return an empty result.
    if (nums == null || nums.length == 0) {
      return new int[0];
    }

    // 1) Put every integer into a HashSet for O(1) lookups & removals.
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
      numSet.add(num);
    }

    int longestLength = 0;
    int[] bestRange = new int[2];  // Will store [start, end] of the largest range found

    // 2) Iterate over each integer in the original array:
    for (int num : nums) {
      // If this number has already been removed, skip it.
      // (It means some earlier pass already expanded over it.)
      if (!numSet.contains(num)) {
        continue;
      }

      // Otherwise, remove it to mark “we are now processing num.”
      numSet.remove(num);

      // 3a) Expand downward (to find how far left the consecutive block goes).
      int left = num - 1;
      while (numSet.contains(left)) {
        // As soon as we find left ∈ the set, we remove it (so no future pass re‐uses it)
        numSet.remove(left);
        left--;  // move leftward
      }

      // 3b) Expand upward (to find how far right the consecutive block goes).
      int right = num + 1;
      while (numSet.contains(right)) {
        numSet.remove(right);
        right++;  // move rightward
      }

      // Now the actual consecutive block is [left + 1, right − 1].
      // (Because left and right went one step too far before the while‐loops stopped.)
      int currentStart  = left + 1;
      int currentEnd    = right - 1;
      int currentLength = currentEnd - currentStart + 1;

      // 4) If this block is longer than anything we've seen so far, update bestRange.
      if (currentLength > longestLength) {
        longestLength = currentLength;
        bestRange[0]  = currentStart;
        bestRange[1]  = currentEnd;
      }
    }

    // 5) Return the two‐element array [start, end] of the largest consecutive block.
    return bestRange;
  }

  public static void main(String[] args) {
    A03LargestRange solution = new A03LargestRange();

    int[] nums1 = {1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6};
    int[] result1 = solution.largestRange(nums1);
    System.out.println("Largest Range: [" + result1[0] + ", " + result1[1] + "]");  
    // Expected: [0, 7]

    int[] nums2 = {4, 2, 1, 3};
    int[] result2 = solution.largestRange(nums2);
    System.out.println("Largest Range: [" + result2[0] + ", " + result2[1] + "]");  
    // Expected: [1, 4]
  }
}
```

---

### Line‐By‐Line Explanation

1. **Edge Case Check**

   ```java
   if (nums == null || nums.length == 0) {
     return new int[0];
   }
   ```

   If the input is empty or `null`, we return an empty array (since there is no “largest range”).

2. **Build the HashSet**

   ```java
   Set<Integer> numSet = new HashSet<>();
   for (int num : nums) {
     numSet.add(num);
   }
   ```

   * By putting every element into a `HashSet`, we can check quickly `if (numSet.contains(x))` in constant time, and we can remove with `numSet.remove(x)` also in constant time (amortized).

3. **Initialize Tracking Variables**

   ```java
   int longestLength = 0;
   int[] bestRange    = new int[2];
   ```

   * We'll keep track of the length of the largest block found so far in `longestLength`.
   * Whenever we find a new block that beats that length, we store its `[start, end]` into `bestRange`.

4. **Loop Over Each Original Number**

   ```java
   for (int num : nums) {
     if (!numSet.contains(num)) {
       continue; 
     }
     numSet.remove(num);
     // … expand left and right …
   }
   ```

   * We iterate once through the original `nums`.
   * If `num` is no longer in the set (we already removed it during a previous range expansion), skip it, because that means it was already accounted for in some earlier block.
   * Otherwise, remove it from the set—marking that we’re about to expand a new range centered around `num`.

5. **Expand Leftward**

   ```java
   int left = num - 1;
   while (numSet.contains(left)) {
     numSet.remove(left);
     left--;
   }
   ```

   * We start from `left = num − 1`. As long as that exact integer is still in the set, we remove it (so it will never be used again) and move one step farther left.
   * When `numSet.contains(left)` becomes false, we know we’ve gone one step too far. The last valid integer was `left + 1`.

6. **Expand Rightward**

   ```java
   int right = num + 1;
   while (numSet.contains(right)) {
     numSet.remove(right);
     right++;
   }
   ```

   * Symmetrically, start from `right = num + 1`. While that integer remains in the set, remove it and move right.
   * When it finally stops, the last valid integer was `right − 1`.

7. **Compute That Range’s Boundaries**

   ```java
   int currentStart  = left + 1;   // because we went one step left too far
   int currentEnd    = right - 1;  // because we went one step right too far
   int currentLength = currentEnd - currentStart + 1;
   ```

   * The “consecutive block” that includes `num` runs from `currentStart` up to `currentEnd`.
   * Its length is `(currentEnd − currentStart + 1)`.

8. **Update the Global Best If It’s Larger**

   ```java
   if (currentLength > longestLength) {
     longestLength = currentLength;
     bestRange[0]  = currentStart;
     bestRange[1]  = currentEnd;
   }
   ```

   * If this newly found block is strictly longer than any we saw before, remember it.

9. **Return the Result**
   After all numbers have been processed (each number is removed either as the “center” of a block or as part of an expansion), `bestRange` holds the `[start, end]` of the largest consecutive block. We return that.

---

## 5. Complexity Analysis

* **Time Complexity:**

  * Building the `HashSet` from `nums`: $O(n)$.
  * The outer loop “for (int num : nums)” runs $n$ times.

    * Each time we either skip if `num` has been removed, or we remove it and expand left/right.
    * In total, every element of `nums` is removed from `numSet` exactly once—either as the “center” (`numSet.remove(num)`) or during a left/right expansion.
    * Each removal and each membership test (`contains`) is $O(1)$ on average.
    * Therefore, the combined cost of all expansions across all iterations is $O(n)$.
  * In sum: $O(n) + O(n) = O(n)$.

* **Space Complexity:**

  * We store every integer in a `HashSet` of size $n$.
  * The few extra integers (`left`, `right`, `longestLength`) cost only $O(1)$ space.
  * Therefore total extra space is **O(n)**.

---

## 6. Final Java Code (Again)

```java
import java.util.*;

public class A03LargestRange {

  /**
   * Returns a two‐element array [start, end] describing the longest consecutive range
   * of integers found in the input array. If no numbers (empty input), returns [].
   *
   * Time:  O(n)    (each number is inserted/removed from the set once, each lookup is O(1))
   * Space: O(n)    (the HashSet holds all input values)
   */
  public int[] largestRange(int[] nums) {
    if (nums == null || nums.length == 0) {
      return new int[0];
    }

    // Build the hash set
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
      numSet.add(num);
    }

    int longestLength = 0;
    int[] bestRange = new int[2];

    // Iterate through each value
    for (int num : nums) {
      // If it’s already been removed (i.e. accounted for), skip
      if (!numSet.contains(num)) continue;

      // Remove from set to mark “processing this num”
      numSet.remove(num);

      // Expand downward
      int left = num - 1;
      while (numSet.contains(left)) {
        numSet.remove(left);
        left--;
      }

      // Expand upward
      int right = num + 1;
      while (numSet.contains(right)) {
        numSet.remove(right);
        right++;
      }

      // Calculate the range we’ve found (left+1 .. right-1)
      int currentStart  = left + 1;
      int currentEnd    = right - 1;
      int currentLength = currentEnd - currentStart + 1;

      // Update the best range if this is longer
      if (currentLength > longestLength) {
        longestLength      = currentLength;
        bestRange[0]       = currentStart;
        bestRange[1]       = currentEnd;
      }
    }

    return bestRange;
  }

  // Simple main to test the code
  public static void main(String[] args) {
    A03LargestRange solution = new A03LargestRange();

    int[] nums1 = {1, 11, 3, 0, 15, 5, 2, 4, 10, 7, 12, 6};
    int[] range1 = solution.largestRange(nums1);
    System.out.println("Largest Range: [" + range1[0] + ", " + range1[1] + "]");  
    // Expected Output: [0, 7]

    int[] nums2 = {4, 2, 1, 3};
    int[] range2 = solution.largestRange(nums2);
    System.out.println("Largest Range: [" + range2[0] + ", " + range2[1] + "]");  
    // Expected Output: [1, 4]
  }
}
```

---

### Summary for Beginners

1. **Build a `HashSet`** of all the integers so you can check membership in constant time.
2. **For each integer `num`** (in the original list):

   * If it’s already been removed from the set, skip it.
   * Otherwise, remove it and expand left (–1, –2, …) and right (+1, +2, …) as long as those neighbors exist in the set. Each time you find a neighbor, remove it as well—so you never expand over the same number twice.
   * After you finish expanding, you know the entire consecutive block that includes `num`. Compare its length to the best you’ve found so far.
3. **Return the `[start, end]`** of the longest block you found.
4. This takes **O(n)** time total, because each number is “used up” (removed from the set) exactly once.

That’s the entire approach! It is both easy to understand at the conceptual level and also runs efficiently in linear time.
