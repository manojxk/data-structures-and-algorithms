**Problem Explanation**

You’re given two arrays of integers:

1. **`array`** (length *n*) – the “source” array, e.g. `[5, 1, 22, 25, 6, -1, 8, 10]`.
2. **`sequence`** (length *m*) – the “candidate” subsequence, e.g. `[1, 6, -1, 10]`.

A **subsequence** means that you can get all the numbers in `sequence` by removing zero or more elements from `array` **without reordering** the remaining elements. Your job is to check **whether** `sequence` really is a subsequence of `array`. In the example above, it is, because you can “pick off” 1, then 6, then -1, then 10 in that order from the source array.

---

## Approach: Two-Pointer (Iterative)

We’ll walk through both arrays **once** using two indices:

* `arrayIndex` scans through `array`.
* `seqIndex` scans through `sequence`.

Whenever `array[arrayIndex] == sequence[seqIndex]`, we’ve matched one element of the subsequence, so we advance `seqIndex` by 1 (to look for the next element). Regardless of match or not, we always advance `arrayIndex` to keep scanning the source. If by the time we’ve scanned enough of `array` we have advanced `seqIndex` all the way to `sequence.length`, then every element of `sequence` was found in order.

### Step-by-Step

1. **Initialize pointers:**

   ```java
   int arrayIndex   = 0;
   int sequenceIndex = 0;
   ```
2. **Scan `array` while both pointers are in range:**

   ```java
   while (arrayIndex < array.length && sequenceIndex < sequence.length) {
       // ...
       arrayIndex++;
   }
   ```
3. **Check for a match:**

   ```java
   if (array[arrayIndex] == sequence[sequenceIndex]) {
       sequenceIndex++;  // We found the next subsequence element!
   }
   ```
4. **After the loop:**

   * If `sequenceIndex == sequence.length`, we matched *all* elements → return `true`.
   * Otherwise → return `false`.

---

## Java Implementation

```java
public class ValidateSubsequence {

    /**
     * Returns true if 'sequence' is a subsequence of 'array'.
     * Time: O(n + m) | Space: O(1)
     */
    public static boolean isValidSubsequence(int[] array, int[] sequence) {
        int arrayIndex    = 0;
        int sequenceIndex = 0;

        // Traverse both arrays until one runs out
        while (arrayIndex < array.length && sequenceIndex < sequence.length) {
            // If we find the current sequence element in array, advance the sequence pointer
            if (array[arrayIndex] == sequence[sequenceIndex]) {
                sequenceIndex++;
            }
            // Always advance the array pointer
            arrayIndex++;
        }

        // If we've matched every element in 'sequence', it's a valid subsequence
        return sequenceIndex == sequence.length;
    }

    public static void main(String[] args) {
        int[] array    = {5, 1, 22, 25, 6, -1, 8, 10};
        int[] sequence = {1, 6, -1, 10};

        boolean result = isValidSubsequence(array, sequence);
        System.out.println("Is valid subsequence? " + result);  // true
    }
}
```

---

## Complexity Analysis

* **Time Complexity:**
  You make at most one pass through each array: **O(n + m)**, where

  * *n* = `array.length`
  * *m* = `sequence.length`

* **Space Complexity:**
  Only a couple of integer pointers are used (no extra data structures): **O(1)**.

This two-pointer technique is optimal for this problem, giving you a clear, linear-time check and constant extra space.
