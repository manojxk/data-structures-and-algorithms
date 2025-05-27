**Problem Overview**

You’re given:

* A **non-empty** array of **distinct** integers, e.g. `[3, 5, -4, 8, 11, 1, -1, 6]`.
* A **target sum**, e.g. `10`.

Your task is to find **any two different numbers** in the array whose sum equals the target. If such a pair exists, return it (order doesn’t matter); otherwise, return an empty array.

---

## 1. Brute-Force Approach

### Idea

Check **every possible pair** in the array and see if they sum to the target.

### Step-by-Step

1. Loop `i` from `0` to `array.length - 2`.
2. For each `i`, loop `j` from `i + 1` to `array.length - 1`.
3. If `array[i] + array[j] == targetSum`, return `[array[i], array[j]]`.
4. If no pair is found by the end of both loops, return an empty array.

### Code

```java
public static int[] twoNumberSumBruteForce(int[] array, int targetSum) {
  // 1) Try each pair (i,j)
  for (int i = 0; i < array.length - 1; i++) {
    for (int j = i + 1; j < array.length; j++) {
      // 2) Check if this pair sums to targetSum
      if (array[i] + array[j] == targetSum) {
        // 3) If yes, return the pair
        return new int[] { array[i], array[j] };
      }
    }
  }
  // 4) No valid pair found
  return new int[0];
}
```

* **Time Complexity:**
  We check $\frac{n(n-1)}{2}$ pairs → **O(n²)**.
* **Space Complexity:**
  We only use a few variables → **O(1)** extra space.

---

## 2. Hash-Set Approach

### Idea

As you scan the array once, store each number in a **HashSet**. For each number `num`, compute its **complement** (`targetSum - num`) and check if that complement is already in the set.

### Step-by-Step

1. Create an empty `HashSet<Integer> seen`.
2. Iterate through each `num` in the array:

   * Let `complement = targetSum - num`.
   * If `seen.contains(complement)`, you’ve found your pair: return `[complement, num]`.
   * Otherwise, add `num` to `seen`.
3. If you finish the loop without finding a complement, return an empty array.

### Code

```java
public static int[] twoNumberSumHashSet(int[] array, int targetSum) {
  HashSet<Integer> seen = new HashSet<>();
  for (int num : array) {
    int complement = targetSum - num;
    // Check if the complement was seen before
    if (seen.contains(complement)) {
      return new int[] { complement, num };
    }
    // Record this number for future complements
    seen.add(num);
  }
  return new int[0];
}
```

* **Time Complexity:**
  One pass through the array with O(1) set operations → **O(n)**.
* **Space Complexity:**
  The set can grow to size n → **O(n)** extra space.

---

## 3. Two-Pointer Approach

> **Precondition:** You need a **sorted** array.

### Idea

1. Sort the array.
2. Maintain two indices:

   * `left` starting at the beginning (`0`),
   * `right` starting at the end (`n-1`).
3. Compute `currentSum = array[left] + array[right]`.

   * If `currentSum == targetSum`, you’re done.
   * If `currentSum < targetSum`, increment `left` to get a larger sum.
   * If `currentSum > targetSum`, decrement `right` to get a smaller sum.
4. Stop when `left >= right`—no pair found.

### Step-by-Step

1. Call `Arrays.sort(array)` → now it’s in ascending order.
2. Initialize `left = 0`, `right = array.length - 1`.
3. While `left < right`:

   * Calculate `sum = array[left] + array[right]`.
   * Compare `sum` to `targetSum` and move pointers accordingly.
4. Return the found pair or an empty array.

### Code

```java
public static int[] twoNumberSumTwoPointers(int[] array, int targetSum) {
  // 1) Sort the array in-place
  Arrays.sort(array);
  int left = 0;
  int right = array.length - 1;

  // 2) Move pointers inward until they meet
  while (left < right) {
    int currentSum = array[left] + array[right];
    if (currentSum == targetSum) {
      return new int[] { array[left], array[right] };
    } else if (currentSum < targetSum) {
      left++;   // Need a bigger sum
    } else {
      right--;  // Need a smaller sum
    }
  }
  // 3) No valid pair
  return new int[0];
}
```

* **Time Complexity:**
  Sorting takes **O(n log n)**, then the two-pointer scan is **O(n)** → overall **O(n log n)**.
* **Space Complexity:**
  Sorting in Java may use **O(log n)** stack space, but otherwise **O(1)** extra.

---

## Which Approach to Use?

* **Brute-force:** Only for very small $n$.
* **Hash-set:** Fastest at **O(n)** time; uses **O(n)** extra space.
* **Two-pointer:** Uses minimal extra space; costs **O(n log n)** time due to sorting.

All three satisfy the requirement of at most one valid pair and return an empty array if none exists. Choose based on your constraints (time vs. space).
