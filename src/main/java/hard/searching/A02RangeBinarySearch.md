**Problem Restatement**
Given a sorted array of distinct or non‐distinct integers and a target value, return a two‐element array `[firstIndex, lastIndex]` such that:

* `array[firstIndex] == target`,
* `array[lastIndex] == target`,
* every index `i` between `firstIndex` and `lastIndex` also satisfies `array[i] == target`, and
* if the target does not appear at all, return `[-1, -1]`.

For example, if

```
array = [0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73]
target = 45
```

then `45` first appears at index `4` and last appears at index `9`, so the answer is `[4, 9]`.

---

## High‐Level Approach

1. **Binary‐Search for the First Occurrence**
   We do a modified binary search that “leans left” whenever we see the target. Concretely:

   * Keep two pointers `left = 0` and `right = array.length − 1`.
   * While `left ≤ right`, compute `mid = left + (right − left)/2`.

     * If `array[mid] < target`, move `left = mid + 1`.
     * If `array[mid] > target`, move `right = mid − 1`.
     * If `array[mid] == target`, record `firstOccurrence = mid` (it might be the first), but continue searching to the left by setting `right = mid − 1`.
   * At loop’s end, `firstOccurrence` holds the leftmost index where `array[index] == target`, or remains −1 if we never saw the target.

2. **Binary‐Search for the Last Occurrence**
   Exactly the same idea, but “lean right” on a match:

   * Again `left = 0`, `right = array.length − 1`.
   * While `left ≤ right`, let `mid = left + (right − left)/2`.

     * If `array[mid] < target`, do `left = mid + 1`.
     * If `array[mid] > target`, do `right = mid − 1`.
     * If `array[mid] == target`, record `lastOccurrence = mid`, but continue searching to the right by setting `left = mid + 1`.
   * At the end, `lastOccurrence` is the rightmost index of the target (or −1 if never found).

3. **Combine Results**

   * If the first‐occurrence search returned −1, the target does not exist. Return `[-1, −1]`.
   * Otherwise, return `[firstOccurrence, lastOccurrence]`.

Because each of those searches is just standard binary search with a slight twist, each runs in **O(log n)** time. Space usage is **O(1)** since we only keep a few integer pointers.

---

## Detailed Code with Explanatory Comments

```java
package hard.searching;

public class A02RangeBinarySearch {

  /**
   * Returns a two‐element array [firstIndex, lastIndex] showing where 'target'
   * appears in the sorted array. If 'target' is not found, returns [-1, -1].
   *
   * Time Complexity: O(log n) because we perform two binary searches.  
   * Space Complexity: O(1) since only constant extra variables are used.
   */
  public static int[] searchRange(int[] array, int target) {
    // Initialize result to [-1, -1]. If we never find 'target', we return this.
    int[] result = { -1, -1 };

    // 1) Find the first (leftmost) occurrence of target:
    int first = findFirstOccurrence(array, target);
    if (first == -1) {
      // Target does not exist at all, so we can return immediately.
      return result;  
    }

    // 2) Find the last (rightmost) occurrence of target:
    int last = findLastOccurrence(array, target);

    // Store them in the result array
    result[0] = first;
    result[1] = last;
    return result;
  }

  /**
   * Helper: binary search to find the leftmost index where array[index] == target.
   * If not found, returns -1.
   */
  private static int findFirstOccurrence(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;
    int firstOccurrence = -1;

    while (left <= right) {
      // Avoid overflow with (left + right)/2 → use left + (right - left)/2
      int mid = left + (right - left) / 2;

      if (array[mid] < target) {
        // Target must lie strictly to the right of mid
        left = mid + 1;
      } 
      else if (array[mid] > target) {
        // Target must lie strictly to the left of mid
        right = mid - 1;
      } 
      else {
        // We found one occurrence at mid. Record it, but keep searching left.
        firstOccurrence = mid;
        right = mid - 1;
      }
    }

    return firstOccurrence;
  }

  /**
   * Helper: binary search to find the rightmost index where array[index] == target.
   * If not found, returns -1.
   */
  private static int findLastOccurrence(int[] array, int target) {
    int left = 0;
    int right = array.length - 1;
    int lastOccurrence = -1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (array[mid] < target) {
        // Go right
        left = mid + 1;
      } 
      else if (array[mid] > target) {
        // Go left
        right = mid - 1;
      } 
      else {
        // We found one at mid. Record it, but keep searching right.
        lastOccurrence = mid;
        left = mid + 1;
      }
    }

    return lastOccurrence;
  }

  // Simple test driver
  public static void main(String[] args) {
    int[] array = { 0, 1, 21, 33, 45, 45, 45, 45, 45, 45, 61, 71, 73 };
    int target = 45;

    int[] result = searchRange(array, target);
    System.out.println("First and Last Indices: ["
        + result[0] + ", " + result[1] + "]");
    // Expected output: [4, 9]
  }
}
```

---

## Why This Works

1. **Finding the First Occurrence**

   * Whenever `array[mid] == target`, we might be in the “middle” of a block of equal elements. By setting `firstOccurrence = mid` and then doing `right = mid − 1`, we force the search to continue on the left subarray, in case an earlier copy of `target` still exists. When the loop finishes, `firstOccurrence` is the smallest index where we saw `target`.

2. **Finding the Last Occurrence**

   * Similar logic, but whenever `array[mid] == target`, we set `lastOccurrence = mid` then do `left = mid + 1`. That forces the search into the right subarray to see if any later copy of `target` exists. At the end, `lastOccurrence` is the largest index where `array[index] == target`.

3. **Edge Cases**

   * If the array does not contain `target` at all, neither search finds a match—both return −1. We detect that (`first == -1`) and immediately return `[-1, -1]`.
   * If the array has exactly one element equal to `target`, both searches will return the same index.

4. **Runtime**
   Each helper (`findFirstOccurrence` and `findLastOccurrence`) is a standard binary search that does one comparison per loop iteration and halves the search range each time. That is **O(log n)** time. Since we do two such searches, the total is still **O(log n)**.

5. **Space**
   We only use a few integer variables—no extra arrays—so space is **O(1)**.

---

**In summary**, this two‐pass binary‐search technique efficiently finds the leftmost and rightmost indices of the target in a sorted array. Each individual search is a slight variation on classical binary search, where finding `array[mid] == target` does not immediately return but instead “leans” left (for the first occurrence) or “leans” right (for the last occurrence), thus locating the correct boundary. Continuous halving of the search interval yields an overall **O(log n)** runtime and **O(1)** extra space.
