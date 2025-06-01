**Problem Explanation**

You’re given a non-empty integer array `nums` of length $n$. A **majority element** is defined as an element that appears **more than** $n/2$ times in the array. It’s guaranteed that **one** such element always exists. Your task is to **find and return** that majority element.

> **Examples**
>
> 1. `nums = [3, 2, 3]`
>
>    * Length $n = 3$. More than $3/2 = 1.5$ means an element must appear at least **2** times.
>    * The number 3 appears twice, so the output is **3**.
> 2. `nums = [2, 2, 1, 1, 1, 2, 2]`
>
>    * Length $n = 7$. More than $7/2 = 3.5$ means an element must appear at least **4** times.
>    * The number 2 appears 4 times; number 1 appears 3 times. So the majority element is **2**.

---

## Brute‐Force Approach (O(n²) Time • O(1) Space)

A straightforward (but slow) method is:

1. For each index `i` from `0` to `n−1`:

   * Count how many times `nums[i]` appears by scanning the entire array.
   * If that count exceeds $n/2$, return `nums[i]`.

Since you scan the array once for each element, this is $O(n) \times O(n) = O(n^2)$.

```java
public static int findMajorityElementBruteForce(int[] array) {
  int n = array.length;
  int majorityCount = n / 2; // Because appearance must exceed n/2

  for (int i = 0; i < n; i++) {
    int count = 0;
    for (int j = 0; j < n; j++) {
      if (array[i] == array[j]) {
        count++;
      }
    }
    if (count > majorityCount) {
      return array[i];
    }
  }
  // Since a majority element is guaranteed to exist, we never actually reach here.
  return -1;
}
```

* **Time Complexity:** $O(n^2)$
* **Space Complexity:** $O(1)$ extra space

---

## Efficient Approach: Boyer–Moore Voting Algorithm (O(n) Time • O(1) Space)

Because you know *there is* a majority element, you can use the **Boyer–Moore Voting** method to find it in one pass with constant space.

### Key Idea

* You keep a **“candidate”** for majority and a **counter**.
* Whenever the counter drops to 0, you pick the current element as the new candidate.
* Whenever you see an element equal to the candidate, increment the counter; otherwise decrement the counter.
* In the end, the “surviving” candidate is guaranteed to be the majority element.

Intuitively, each time you “pair up” one candidate occurrence with a non-candidate occurrence by decrementing the count, you are canceling them out in terms of majority. Because the true majority element appears more than all others combined, it will remain as `candidate` when you finish.

### Step‐by‐Step

1. Initialize two variables:

   ```java
   int candidate = 0;
   int count = 0;
   ```
2. Iterate through each number `num` in `nums`:

   * If `count == 0`, set `candidate = num` and `count = 1`.
   * Else if `num == candidate`, do `count++`.
   * Else do `count--`.
3. After you finish the single pass, `candidate` holds the majority element. Because we know one must exist, there’s no need for a final verification pass.

### Why It Works

* Imagine you group each occurrence of the candidate element with one occurrence of a different element; each pair effectively “cancels out.”
* Since the true majority appears more than half the time, it cannot be fully canceled. It will survive as the final `candidate` after all pairings.

### Java Code

```java
package medium.arrays;

public class A13MajorityElement {

  /**
   * Finds and returns the majority element (appearing > n/2 times).
   * Uses Boyer–Moore Voting, which runs in O(n) time and O(1) space.
   *
   * @param nums the input array with guaranteed majority element
   * @return the majority element
   */
  public static int majorityElement(int[] nums) {
    int candidate = 0;
    int count = 0;

    // 1) Find a candidate
    for (int num : nums) {
      if (count == 0) {
        candidate = num;
        count = 1;
      } else if (num == candidate) {
        count++;
      } else {
        count--;
      }
    }

    // 2) Because a majority element is guaranteed, candidate is it
    return candidate;
  }

  public static void main(String[] args) {
    int[] nums1 = {3, 2, 3};
    System.out.println("Majority element: " + majorityElement(nums1));
    // Output: 3

    int[] nums2 = {2, 2, 1, 1, 1, 2, 2};
    System.out.println("Majority element: " + majorityElement(nums2));
    // Output: 2
  }
}
```

* **Time Complexity:** $O(n)$ — single pass through the array.
* **Space Complexity:** $O(1)$ — only two integer variables (`candidate` and `count`).

---

### Complete Explanation for Beginners

1. **Initialize**

   * `candidate` = anything (we’ll overwrite it when `count` becomes 0),
   * `count` = 0.

2. **Traverse the array**

   ```java
   for (int num : nums) {
     if (count == 0) {
       candidate = num; // pick a new candidate
       count = 1;
     }
     else if (num == candidate) {
       count++; // seeing the same value strengthens our choice
     } else {
       count--; // this num is different, “cancels” one count
     }
   }
   ```

   * **Whenever** `count` drops to 0, you choose the next `num` as your new `candidate`.
   * If the current `num` matches the `candidate`, you increment `count`. Otherwise, you decrement `count`.

3. **Result**
   When you finish iterating, `candidate` must be the majority element. You don’t need a second pass to verify because the problem guarantees one exists.

4. **Return** `candidate`.

This is a classic algorithm known as **Boyer–Moore Voting**, and it’s the standard way to find a guaranteed majority in linear time, constant space.
