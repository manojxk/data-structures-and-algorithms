**Problem Statement**

You’re given a **sorted** array of integers and a **target** value. You need to find the **index** of the target in the array. If the target isn’t present, return **–1**.

---

## 1. Brute-Force Approach

1. **Scan** each element in the array from left to right.
2. **Compare** it to the target; if they match, **return** that index.
3. If you finish the loop without finding the target, **return** –1.

```java
public static int findTargetBruteForce(int[] array, int target) {
  for (int i = 0; i < array.length; i++) {
    if (array[i] == target) {
      return i;
    }
  }
  return -1;
}
```

* **Time Complexity:** O(n)
* **Space Complexity:** O(1)

---

## 2. Binary Search (Iterative)

Because the array is **sorted**, you can do much better than O(n) by repeatedly **halving** the search range:

1. Maintain two pointers, `left` and `right`, initially `0` and `array.length–1`.
2. While `left ≤ right`:

   * Compute `mid = left + (right – left) / 2` (avoids integer overflow).
   * If `array[mid] == target`, **return** `mid`.
   * If `array[mid] < target`, move `left` to `mid + 1` (search right half).
   * Else, move `right` to `mid – 1` (search left half).
3. If you exit the loop, the target isn’t in the array → **return** –1.

```java
public static int binarySearchIterative(int[] array, int target) {
  int left = 0;
  int right = array.length - 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;
    if (array[mid] == target) {
      return mid;
    } else if (array[mid] < target) {
      left = mid + 1;
    } else {
      right = mid - 1;
    }
  }

  return -1;
}
```

* **Time Complexity:** O(log n)
* **Space Complexity:** O(1)

---

## 3. Binary Search (Recursive)

A recursive version follows the same logic, passing updated `left` and `right` bounds:

```java
public static int binarySearchRecursive(
    int[] array, int target, int left, int right
) {
  if (left > right) {
    return -1;  // no match in this range
  }

  int mid = left + (right - left) / 2;
  if (array[mid] == target) {
    return mid;
  } else if (array[mid] < target) {
    return binarySearchRecursive(array, target, mid + 1, right);
  } else {
    return binarySearchRecursive(array, target, left, mid - 1);
  }
}
```

* **Time Complexity:** O(log n)
* **Space Complexity:** O(log n) due to the recursion stack

---

## Example Usage

```java
public static void main(String[] args) {
  int[] array = {0, 1, 21, 33, 45, 45, 61, 71, 72, 73};
  int target = 33;

  // Iterative
  int idx1 = binarySearchIterative(array, target);
  System.out.println("Iterative Result: " + idx1);  // 3

  // Recursive
  int idx2 = binarySearchRecursive(array, target, 0, array.length - 1);
  System.out.println("Recursive Result: " + idx2);  // 3
}
```

---

**Key Takeaways**

* **Brute‐force** search is simple but O(n).
* **Binary search** leverages the sorted order to find the target in **O(log n)** time.
* The **iterative** version uses constant space; the **recursive** version uses extra stack space proportional to the height of the search tree (log n).
