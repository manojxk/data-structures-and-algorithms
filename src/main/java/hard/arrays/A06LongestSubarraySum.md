**Problem Restatement**
You have a non‐empty array of non‐negative integers `array[]` and a non‐negative integer `targetSum`. Find, among all subarrays whose elements sum to exactly `targetSum`, the *longest* such subarray. Return a two‐element array `[startIndex, endIndex]` (inclusive). If no subarray sums to `targetSum`, return an empty array. It’s guaranteed at most one longest subarray exists.

---

## 1. Brute‐Force Approach (O(n²) Time, O(1) Space)

1. **Idea**

   * Try every possible starting index `i` from `0` to `n−1`.
   * For each `i`, iterate `j` from `i` to `n−1`, maintain a running sum `sum += array[j]`.
   * Whenever `sum == targetSum`, check if `(j − i + 1)` is longer than the longest seen so far; if yes, record `startIndex = i` and `endIndex = j`.

2. **Code**

   ```java
   public static int[] longestSubarraySumBruteForce(int[] array, int targetSum) {
     int n = array.length;
     int maxLength = 0;
     int startIndex = -1;
     int endIndex = -1;

     for (int i = 0; i < n; i++) {
       int sum = 0;
       for (int j = i; j < n; j++) {
         sum += array[j];
         if (sum == targetSum && (j - i + 1) > maxLength) {
           maxLength = j - i + 1;
           startIndex = i;
           endIndex = j;
         }
       }
     }

     if (startIndex == -1) {
       return new int[] {};  // No subarray found
     }
     return new int[] { startIndex, endIndex };
   }
   ```

3. **Complexities**

   * Time: O(n²) because of the two nested loops.
   * Space: O(1) extra space (only a few integer variables).

---

## 2. Optimized Sliding‐Window Approach (O(n) Time, O(1) Space)

Because all integers are non‐negative, increasing the window’s right end never decreases the sum; it only grows or stays the same. This allows a classic “two‐pointer” or “sliding window” technique:

1. **Maintain** two pointers `start` and `end`, initially both at 0, and a running `currentSum = 0`.
2. **Expand** by moving `end` forward, adding `array[end]` to `currentSum`.
3. **While** `currentSum > targetSum` (i.e. too large), **shrink** the window from the left by subtracting `array[start]` and incrementing `start`.
4. **If** `currentSum == targetSum`, see if the window length `(end − start + 1)` exceeds the best so far; if yes, update `bestStart = start`, `bestEnd = end`.
5. Continue until `end` reaches `n`.

Because every element enters and leaves the window at most once, the total time is O(n).

### Code

```java
public static int[] longestSubarraySumSlidingWindow(int[] array, int targetSum) {
  int n = array.length;
  int start = 0, end = 0;
  int currentSum = 0;
  int maxLength = 0;
  int startIndex = -1, endIndex = -1;

  while (end < n) {
    // Expand window by including array[end]
    currentSum += array[end];

    // If sum is too big, shrink from the left until sum <= targetSum
    while (currentSum > targetSum && start <= end) {
      currentSum -= array[start];
      start++;
    }

    // If we hit exactly targetSum, check length
    if (currentSum == targetSum) {
      int length = end - start + 1;
      if (length > maxLength) {
        maxLength = length;
        startIndex = start;
        endIndex = end;
      }
    }

    end++;
  }

  // If no subarray was found, return empty array
  if (startIndex == -1) {
    return new int[] {};
  }
  return new int[] { startIndex, endIndex };
}
```

### How It Works

* We keep adding elements at `end` into `currentSum`.
* If `currentSum` exceeds `targetSum`, we move `start` forward, subtracting those values until `currentSum ≤ targetSum`.
* Whenever `currentSum == targetSum`, we check if this window is the longest seen.
* Because array elements are non‐negative, once `currentSum` exceeds `targetSum`, pushing `end` further only makes it larger; so we must shrink from `start`.
* Each index enters and leaves the window at most once, giving **O(n)** time.

---

## 3. Complete Java Class with Both Solutions

```java
import java.util.Arrays;

public class A06LongestSubarraySum {

  // Brute-Force: O(n^2) time, O(1) space
  public static int[] longestSubarraySumBruteForce(int[] array, int targetSum) {
    int n = array.length;
    int maxLength = 0;
    int startIndex = -1;
    int endIndex = -1;

    for (int i = 0; i < n; i++) {
      int sum = 0;
      for (int j = i; j < n; j++) {
        sum += array[j];
        if (sum == targetSum && (j - i + 1) > maxLength) {
          maxLength = j - i + 1;
          startIndex = i;
          endIndex = j;
        }
      }
    }

    if (startIndex == -1) {
      return new int[] {};
    }
    return new int[] { startIndex, endIndex };
  }

  // Optimized Sliding Window: O(n) time, O(1) space
  public static int[] longestSubarraySumSlidingWindow(int[] array, int targetSum) {
    int n = array.length;
    int start = 0, end = 0;
    int currentSum = 0;
    int maxLength = 0;
    int startIndex = -1, endIndex = -1;

    while (end < n) {
      currentSum += array[end];

      while (currentSum > targetSum && start <= end) {
        currentSum -= array[start];
        start++;
      }

      if (currentSum == targetSum) {
        int length = end - start + 1;
        if (length > maxLength) {
          maxLength = length;
          startIndex = start;
          endIndex = end;
        }
      }

      end++;
    }

    if (startIndex == -1) {
      return new int[] {};
    }
    return new int[] { startIndex, endIndex };
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4, 3, 3, 1, 2, 1, 2};
    int targetSum = 10;

    System.out.println("Brute Force Solution: "
        + Arrays.toString(longestSubarraySumBruteForce(array, targetSum)));
    // Expected Output: [4, 8]

    System.out.println("Sliding Window Solution: "
        + Arrays.toString(longestSubarraySumSlidingWindow(array, targetSum)));
    // Expected Output: [4, 8]
  }
}
```

---

### Complexity Summary

* **Brute‐Force**

  * Time: O(n²)
  * Space: O(1)

* **Sliding‐Window**

  * Time: O(n)
  * Space: O(1)

Use the sliding‐window version whenever possible for large arrays, since it runs in linear time.
