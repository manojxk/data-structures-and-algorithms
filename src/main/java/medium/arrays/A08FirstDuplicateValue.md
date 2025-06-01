**Problem Restatement**
You have an integer array of length *n*, and each value is guaranteed to be between 1 and *n* (inclusive). You want to find the **first** integer in the array that appears **more than once**, where “first” is determined by the smallest index of the **second** occurrence. If no integer repeats, return –1.

> **Example**
> Input: `[2, 1, 3, 5, 3, 2]`
> Output: `3`
> Explanation: The number 3 appears twice (at indices 2 and 4), and its second appearance (index 4) comes before the second appearance of 2 (which is at index 5). Hence 3 is the “first” duplicate.

---

## 1. Brute‐Force Method (O(n²) Time • O(1) Space)

1. Loop over each index *i* from 0 to n−2.
2. For each *i*, scan every index *j* > *i*.
3. If you find `array[j] == array[i]`, then `array[i]` is a duplicate, and its second occurrence is at *j*. Return `array[i]` immediately.
4. If you finish both loops without finding any match, return –1.

```java
public static int firstDuplicateValueBruteForce(int[] array) {
  int n = array.length;
  for (int i = 0; i < n - 1; i++) {
    for (int j = i + 1; j < n; j++) {
      if (array[i] == array[j]) {
        return array[i];
      }
    }
  }
  return -1;
}
```

* **Time Complexity:** O(n²), because for each index *i* you potentially scan all remaining elements.
* **Space Complexity:** O(1), only a couple of loop variables.

---

## 2. HashSet Method (O(n) Time • O(n) Space)

1. Create an empty `HashSet<Integer> seen`.
2. Traverse the array from left to right:

   * If `seen.contains(array[i])`, you’ve found your first duplicate → return `array[i]`.
   * Otherwise, do `seen.add(array[i])`.
3. If you finish the loop with no duplicates found, return –1.

```java
public static int firstDuplicateValueHashSet(int[] array) {
  HashSet<Integer> seen = new HashSet<>();
  for (int value : array) {
    if (seen.contains(value)) {
      return value;
    }
    seen.add(value);
  }
  return -1;
}
```

* **Time Complexity:** O(n), because each insertion/look‐up in a HashSet is on average O(1).
* **Space Complexity:** O(n), since in the worst case you might add every element to the set before finding a duplicate (or none).

---

## 3. Optimized In‐Place Method (O(n) Time • O(1) Space)

Because each value is between 1 and n, you can use the array itself as a marker of which numbers you’ve seen. The trick is:

* When you see a value `x = |array[i]|`, you go to index `x − 1` in the array.
* If `array[x − 1]` is already negative, it means you have “visited” x before → you found your first duplicate. Return x.
* Otherwise, set `array[x − 1] = -array[x − 1]` to mark that you have seen x once.

In code:

```java
public static int firstDuplicateValue(int[] array) {
  for (int i = 0; i < array.length; i++) {
    int absValue = Math.abs(array[i]);       // in case it was already negated
    if (array[absValue - 1] < 0) {
      // If the slot is already negative, x has appeared before
      return absValue;
    }
    // Mark x as seen by negating at index x-1
    array[absValue - 1] = -array[absValue - 1];
  }
  return -1;
}
```

* **Why it works:**

  * The first time you encounter an integer x, you negate `array[x−1]`.
  * If you ever read `array[x−1] < 0` later, it means “the position (x−1) was negated before,” so you are seeing x a second time.

* **Edge Cases:**

  * Because we take `abs(array[i])`, we handle the fact that previous steps may have negated that entry.
  * If no duplicate appears, every index `(value−1)` gets negated exactly once, and you never hit a negative‐check condition, so you return –1 at the end.

* **Time Complexity:** O(n), since we visit each element once.

* **Space Complexity:** O(1), because we only use a handful of integer variables. (We do modify the input array in place; if you must preserve it, you could copy first, but that costs O(n) extra space.)

---

## Complete Class (for Testing)

```java
package medium.arrays;

public class A08FirstDuplicateValue {

  // Brute‐force O(n²) approach
  public static int firstDuplicateValueBruteForce(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = i + 1; j < array.length; j++) {
        if (array[i] == array[j]) {
          return array[i];
        }
      }
    }
    return -1;
  }

  // HashSet O(n) approach with O(n) extra space
  public static int firstDuplicateValueHashSet(int[] array) {
    java.util.HashSet<Integer> seen = new java.util.HashSet<>();
    for (int value : array) {
      if (seen.contains(value)) {
        return value;
      }
      seen.add(value);
    }
    return -1;
  }

  // Optimized in‐place O(n) approach with O(1) extra space
  public static int firstDuplicateValue(int[] array) {
    for (int i = 0; i < array.length; i++) {
      int absValue = Math.abs(array[i]);
      if (array[absValue - 1] < 0) {
        return absValue;
      }
      array[absValue - 1] = -array[absValue - 1];
    }
    return -1;
  }

  public static void main(String[] args) {
    int[] array = {2, 1, 3, 5, 3, 2};
    System.out.println("Brute‐force: " 
        + firstDuplicateValueBruteForce(array));     // 3

    // Reset array before each method if needed, since the optimized method mutates it.
    array = new int[]{2, 1, 3, 5, 3, 2};
    System.out.println("With HashSet: " 
        + firstDuplicateValueHashSet(array));        // 3

    array = new int[]{2, 1, 3, 5, 3, 2};
    System.out.println("In‐place optimized: " 
        + firstDuplicateValue(array));               // 3
  }
}
```

Use whichever method fits your constraints:

* **Use the in-place method** if you want O(1) extra space and are allowed to modify the input array.
* **Use the HashSet method** if you need to preserve the original array and can afford O(n) extra space.
* **Use the brute-force method** only for very small arrays or when simplicity matters more than performance.
