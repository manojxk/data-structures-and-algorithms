**Problem Restatement**
You want to build a data structure that supports two operations on a stream of non‐negative (or any) integers, arriving one at a time:

1. **addNumber(num)**: add the new integer `num` into your structure, and
2. **getMedian()**: return the median of all numbers seen so far.

* If you have seen an odd number of values, the median is the middle value in sorted order.
* If you have seen an even number of values, the median is the average of the two middle values in sorted order.

For example, if the stream is `5, 10, 1, 7`, then:

* After adding 5, the only number is `[5]`, so the median is `5`.
* After adding 10, the numbers are `[5, 10]`. The two middle values are 5 and 10, so the median is `(5 + 10)/2 = 7.5`.
* After adding 1, the numbers become `[1, 5, 10]`. The middle (2nd) element is `5`, so the median is `5`.
* After adding 7, the numbers become `[1, 5, 7, 10]`. The two middle elements are 5 and 7, so the median is `(5 + 7)/2 = 6`.

We need to support these two operations fast (each in roughly logarithmic time), even as the number of elements grows arbitrarily large.

---

## Approach: Two Heaps (Max‐Heap + Min‐Heap)

A common, efficient way to maintain a dynamic “running median” is to keep:

1. **A Max‐Heap** (often called `lowerHalf`) containing the *smaller half* of all numbers seen so far, and
2. **A Min‐Heap** (called `upperHalf`) containing the *larger half* of all numbers seen so far.

By “smaller half,” we mean roughly the bottom half of the sorted list; by “larger half,” the top half. As we insert a new number, we decide which heap it should go into. We also keep the size‐difference between the two heaps at no more than 1, so that:

* If there are an **odd** number of total elements, we keep the extra one in the Max‐Heap (`lowerHalf`).
* If there are an **even** number of total elements, we keep each heap the same size.

Once that invariant holds, the median is simply:

* If both heaps have the same size (even total count),

  $$
    \text{median} \;=\; \bigl(\text{max‐heap.top} + \text{min‐heap.top}\bigr) \,/\, 2.0.
  $$
* If one heap is larger by exactly one element (odd total count), that larger heap must be the Max‐Heap `lowerHalf`, so

  $$
    \text{median} \;=\; \text{max‐heap.top}.
  $$

Why does this work?

* The Max‐Heap’s root (`lowerHalf.peek()`) is the largest element in the “lower half.”
* The Min‐Heap’s root (`upperHalf.peek()`) is the smallest element in the “upper half.”
* If each heap has size *n*, then the two middle elements of the combined `2n` numbers are exactly those two roots.
* If the Max‐Heap has size *n+1* and the Min‐Heap has size *n*, then the median is the largest of the lower half, i.e. the Max‐Heap’s root, which is indeed the middle element out of the `2n+1` total.

---

## Step‐by‐Step Implementation in Java

Below is a complete Java class that maintains the running median via two heaps. We will walk through each part.

```java
import java.util.Collections;
import java.util.PriorityQueue;

public class A01ContinuousMedian {
  // 1) Declare the two heaps
  
  // Max‐heap (to store the smaller half of numbers)
  private PriorityQueue<Integer> lowerHalf;
  
  // Min‐heap (to store the larger half of numbers)
  private PriorityQueue<Integer> upperHalf;

  /** Constructor: initialize both heaps */
  public A01ContinuousMedian() {
    // Java’s PriorityQueue is a min‐heap by default. 
    // To get a max‐heap, we use Collections.reverseOrder() comparator
    lowerHalf = new PriorityQueue<>(Collections.reverseOrder());
    upperHalf = new PriorityQueue<>();
  }

  /**
   * 2) Add a new number `num` into the data structure.
   *
   *    (a) If `lowerHalf` is empty or `num <= lowerHalf.peek()`, 
   *        then `num` belongs to the “lower half,” so insert it there.
   *    (b) Otherwise, insert `num` into `upperHalf`.
   *    (c) Re‐balance so the size difference is at most 1:
   *        - If `lowerHalf.size() > upperHalf.size() + 1`, move one element 
   *          from `lowerHalf` to `upperHalf`.
   *        - If `upperHalf.size() > lowerHalf.size()`, move one element 
   *          from `upperHalf` to `lowerHalf`.
   */
  public void addNumber(int num) {
    // (a) Decide which heap to put `num` into:
    if (lowerHalf.isEmpty() || num <= lowerHalf.peek()) {
      lowerHalf.add(num);
    } else {
      upperHalf.add(num);
    }

    // (b) Balance the sizes if needed:
    if (lowerHalf.size() > upperHalf.size() + 1) {
      // lowerHalf has two more elements: move the root over
      upperHalf.add(lowerHalf.poll());
    } else if (upperHalf.size() > lowerHalf.size()) {
      // upperHalf is bigger: move their root down to lowerHalf
      lowerHalf.add(upperHalf.poll());
    }
  }

  /**
   * 3) Get the current median in O(1) time:
   *    - If both heaps have the same size, median = (lowerHalf.peek() + upperHalf.peek()) / 2.0
   *    - Otherwise, lowerHalf has one extra element, so median = lowerHalf.peek().
   */
  public double getMedian() {
    if (lowerHalf.size() == upperHalf.size()) {
      // Even total number of elements
      return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
    } else {
      // Odd total: lowerHalf has one extra element
      return lowerHalf.peek();
    }
  }

  // ---------------------------------------------------
  // 4) Example usage
  // ---------------------------------------------------
  public static void main(String[] args) {
    A01ContinuousMedian continuousMedian = new A01ContinuousMedian();

    int[] numbers = {5, 10, 1, 7};

    for (int num : numbers) {
      continuousMedian.addNumber(num);
      System.out.println(
        "After adding " + num + ", median is: " + continuousMedian.getMedian()
      );
    }
    
    // Expected output:
    //   After adding 5, median is: 5.0
    //   After adding 10, median is: 7.5
    //   After adding 1, median is: 5.0
    //   After adding 7, median is: 6.0
  }
}
```

---

## Detailed Explanation of Each Part

### 1) Data Members

```java
private PriorityQueue<Integer> lowerHalf;
private PriorityQueue<Integer> upperHalf;
```

* `lowerHalf` is a **max‐heap** implemented by `new PriorityQueue<>(Collections.reverseOrder())`. It stores the “smaller half” of all input numbers, so its root (the largest value in that half) is always at `lowerHalf.peek()`.
* `upperHalf` is a **min‐heap** (the default `PriorityQueue<>()` in Java). It stores the “larger half,” so its root (the smallest value in that half) is at `upperHalf.peek()`.

### 2) Inserting a New Number

```java
public void addNumber(int num) {
  if (lowerHalf.isEmpty() || num <= lowerHalf.peek()) {
    lowerHalf.add(num);
  } else {
    upperHalf.add(num);
  }

  // Re‐balance if sizes differ by more than 1
  if (lowerHalf.size() > upperHalf.size() + 1) {
    upperHalf.add(lowerHalf.poll());
  } else if (upperHalf.size() > lowerHalf.size()) {
    lowerHalf.add(upperHalf.poll());
  }
}
```

1. **Choose a Heap**

   * If `lowerHalf` is empty, clearly we must put the first element there.
   * Otherwise, compare `num` to `lowerHalf.peek()`, which is the largest value currently in the “lower half.”

     * If `num ≤ lowerHalf.peek()`, `num` belongs in the lower half (because it is not larger than the biggest element in that half). So we do `lowerHalf.add(num)`.
     * Otherwise (i.e. `num > lowerHalf.peek()`), `num` belongs in the upper half, so `upperHalf.add(num)`.

2. **Balance Heaps**
   After inserting `num`, the sizes of the two heaps may differ by more than 1. But we want them to differ by at most 1, because:

   * If they differ by more than 1, we cannot read off a correct median at any time.
   * Specifically, we want

     * `| lowerHalf.size() − upperHalf.size() | ≤ 1`, and
     * If the total number of elements is odd, the extra one is always in `lowerHalf`.

   So we do:

   ```java
   if (lowerHalf.size() > upperHalf.size() + 1) {
     // lowerHalf has two more elements than upperHalf
     // Move the top of lowerHalf into upperHalf
     upperHalf.add(lowerHalf.poll());
   } else if (upperHalf.size() > lowerHalf.size()) {
     // upperHalf has more elements than lowerHalf (impossible by more than 1 if we only inserted one element)
     // Move the top of upperHalf into lowerHalf
     lowerHalf.add(upperHalf.poll());
   }
   ```

   * After this re‐balancing step, either:

     1. `lowerHalf.size() == upperHalf.size()`, or
     2. `lowerHalf.size() == upperHalf.size() + 1`.

### 3) Retrieving the Median

```java
public double getMedian() {
  if (lowerHalf.size() == upperHalf.size()) {
    return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
  } else {
    // Since we always keep the extra element (if odd total) in lowerHalf:
    return lowerHalf.peek();
  }
}
```

* **Case 1 (even total)**:
  If we have seen `2k` numbers so far, then after balancing we guarantee that `lowerHalf.size() == upperHalf.size() == k`.

  * The “two middle values” in sorted order are exactly

    * `lowerHalf.peek()` (the largest of the lower half) and
    * `upperHalf.peek()` (the smallest of the upper half).
  * So the median is their average: `(lowerHalf.peek() + upperHalf.peek()) / 2.0`.

* **Case 2 (odd total)**:
  If we have seen `2k + 1` numbers, then after balancing we guarantee that `lowerHalf.size() == k + 1` and `upperHalf.size() == k`.

  * In that scenario, there is exactly one middle element in the combined sorted sequence, and it must be the largest element of the lower half—precisely `lowerHalf.peek()`.
  * Thus the median is `lowerHalf.peek()` (as a `double`).

Since `peek()` on a priority queue is O(1), retrieving the median is constant time.

---

## Why This Maintains Correctness

* Every new number goes into one of the two heaps so that all numbers in `lowerHalf` are ≤ every number in `upperHalf`.
* After each insertion, we rebalance so that the difference in sizes is at most 1.
* By induction, after *any* number of insertions (say *N* inserts), the two heaps always split the set of *N* numbers into two halves whose sizes differ by at most 1, and every element in the lower half is ≤ every element in the upper half.
* Therefore, the median can be read off as described above.

---

## Time and Space Complexity

* **addNumber(num)**

  * Inserting into a Java `PriorityQueue` (heap) is **O(log k)**, where *k* is the size of that heap.
  * We might perform one `poll()` and one `add()` across the two heaps to re‐balance. Each of those is also **O(log k)**.
  * Since the heaps together contain *N* elements total (where *N* is how many you have seen so far), each individual heap has size at most *⌈N/2⌉*. In Big‐O notation, each insertion is **O(log N)**.

* **getMedian()**

  * We only look at `peek()` of each heap and possibly do an average. That is **O(1)**.

* **Space Complexity**

  * We store every inserted integer in exactly one of the two heaps. So if you insert *N* numbers, total space is **O(N)**.

---

## Example Walkthrough

Suppose you call:

```java
A01ContinuousMedian cm = new A01ContinuousMedian();
cm.addNumber(5);
System.out.println(cm.getMedian());  // 5.0
cm.addNumber(10);
System.out.println(cm.getMedian());  // 7.5
cm.addNumber(1);
System.out.println(cm.getMedian());  // 5.0
cm.addNumber(7);
System.out.println(cm.getMedian());  // 6.0
```

1. **Add 5**

   * `lowerHalf` is empty, so we do `lowerHalf.add(5)`.
   * Now `lowerHalf.size() = 1`, `upperHalf.size() = 0`. No rebalancing needed.
   * `getMedian()`: odd total (1 vs. 0), so median = `lowerHalf.peek() = 5`.

2. **Add 10**

   * Compare 10 with `lowerHalf.peek() = 5`. Since 10 > 5, we put 10 into `upperHalf`.
   * Now `lowerHalf.size()=1`, `upperHalf.size()=1`. Balanced.
   * `getMedian()`: sizes equal, so median = `(lowerHalf.peek() + upperHalf.peek()) / 2 = (5 + 10)/2 = 7.5`.

3. **Add 1**

   * Compare 1 with `lowerHalf.peek() = 5`. Since 1 ≤ 5, add 1 into `lowerHalf`.
   * Now `lowerHalf.size()=2`, `upperHalf.size()=1`. That’s okay (difference = 1). No rebalancing needed.
   * `getMedian()`: odd total (2 vs. 1), so median = `lowerHalf.peek() =` the max of `{5, 1}`, which is `5`.

4. **Add 7**

   * Compare 7 with `lowerHalf.peek() = 5`. Since 7 > 5, add 7 into `upperHalf`.
   * Now `lowerHalf.size()=2`, `upperHalf.size()=2`. Balanced.
   * `getMedian()`: even total, so median = `(lowerHalf.peek() + upperHalf.peek())/2`.

     * `lowerHalf.peek()` = `5`, (lower half is `{5, 1}`),
     * `upperHalf.peek()` = `7`, (upper half is `{7, 10}` sorted),
     * median = `(5 + 7)/2 = 6`.

All steps match our expectations.

---

## Key Takeaways

1. **Why Two Heaps?**

   * A max‐heap quickly gives you the largest element of the lower half.
   * A min‐heap quickly gives you the smallest element of the upper half.
   * By always balancing their sizes to differ by at most 1, you ensure that the median is immediately available as either one root (if odd count) or the average of both roots (if even count).

2. **Insertion Cost**

   * Each `addNumber` costs O(log N) due to heap insertions and (sometimes) a rebalancing step.
   * Retrieving the median is O(1).

3. **Space**

   * You store all seen numbers across the two heaps (O(N) space).

This completes the full explanation of how to maintain a **Continuous Median** on an infinite stream of numbers using two heaps in Java.
