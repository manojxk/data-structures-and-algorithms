**Problem Statement**
You have a singly linked list of integer values, for example:

```
0 → 1 → 2 → 3 → 4 → 5 → null
```

You are also given an integer `k`. Your task is to “shift” (a.k.a. rotate) the list by `k` positions to the right if `k` is positive, or to the left if `k` is negative. Shifting means that each node moves over by `|k|` places in the list, wrapping around at the end.

* If `k = 2`, shifting to the right by 2 turns

  ```
  0 → 1 → 2 → 3 → 4 → 5
  ```

  into

  ```
  4 → 5 → 0 → 1 → 2 → 3
  ```

  (Each node has effectively moved two places to the right, and “wrapped” back around.)

* If `k = -1`, shifting to the left by 1 turns

  ```
  0 → 1 → 2 → 3 → 4 → 5
  ```

  into

  ```
  1 → 2 → 3 → 4 → 5 → 0
  ```

  (Each node moves one place left, and the old head goes to the tail.)

If `k` is larger than the length of the list, you only need to shift by `k mod length`. If the resulting shift is zero (either because `k % length == 0`, or the list has length 0 or 1), the list remains unchanged.

Your function should return the new head of the shifted linked list.

---

## Approach & Step‐by‐Step Explanation

1. **Edge Cases**

   * If the list is empty (`head == null`) or has only one node (`head.next == null`), any shift will produce the same list. Return `head`.
   * If `k == 0`, no shifting is needed—just return `head`.

2. **Compute the Length and Locate the Tail**

   * Traverse the list once to count its length, say `n`, and also remember the last node (we’ll call it `tail`).
   * While doing that, we advance a pointer from `head` until we reach `null`, incrementing a counter each time.
   * At the end, `length = n`, and `tail` is the last real node (where `.next == null`).

3. **Form a Circular List**

   * After you know `tail`, set `tail.next = head`. This transforms the linked list into a circular list. Now there is no “null” at the end: it simply wraps around back to `head`.

4. **Normalize `k`**

   * Since shifting by `n` (the list’s length) lands you back where you started, do `k = k % n`.
   * If `k` is now negative (originally handed in as negative), convert it to an equivalent positive shift to the right by doing `k = k + n`.

     * (Example: if `n = 6` and you get `k = -1`, then `k % 6 = -1`, so you do `k = -1 + 6 = 5`. Shifting left by 1 is the same as shifting right by 5.)

5. **Find the “New Tail” Position**

   * In a right‐shift by `k`, the new head will be the node which is `(n − k)` steps from the old head (0‐indexed).
   * Concretely, start from `head` and advance `newTailPosition = n − k − 1` steps.

     * After those steps, the node you land on is the new “tail.”
     * Its `next` node will be the new head of the rotated list.

6. **Break the Circle**

   * Once you identify the new tail (call it `newTail`), let `newHead = newTail.next`.
   * Then do `newTail.next = null` to break the circular link.
   * Return `newHead` as the head of your shifted list.

---

### Why This Works

* By making the list temporarily circular, you eliminate having to worry about “wrapping around.”
* Rotating right by `k` places means the node that used to be at index `(n − k)` (0‐based) becomes the new head. Everything before that is appended to the tail in the same order.
* Converting a negative shift to an equivalent positive one (`k + n`) simply uses the fact that shifting left by 1 in a length‐6 list is the same as shifting right by 5.
* Finally, knocking out the link at `newTail.next = null` restores the singly linked structure (but with a new head).

---

## Complete Java Implementation

```java
package hard.linkedlists;

public class A04ShiftLinkedList {

  // Definition for a singly linked list node
  static class ListNode {
    int value;
    ListNode next;
    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /**
   * Shifts (rotates) the linked list by k positions to the right if k is positive,
   * or to the left if k is negative. Returns the new head of the shifted list.
   *
   * Time Complexity:  O(n), where n is the length of the list.
   *  - We traverse once to find length and tail (O(n)), then advance up to n−1 steps again (O(n)).
   * Space Complexity: O(1), using only a few pointers.
   */
  public static ListNode shiftLinkedList(ListNode head, int k) {
    // Edge cases: empty list or single node, or no shift needed
    if (head == null || head.next == null || k == 0) {
      return head;
    }

    // 1) Compute the length (n) and locate the tail node
    int length = 1;            // Start at 1 because we count the head itself
    ListNode tail = head;
    while (tail.next != null) {
      tail = tail.next;
      length++;
    }

    // 2) Make the list circular by connecting the tail's next to head
    tail.next = head;

    // 3) Normalize k so it lies in [0, length−1]
    k = k % length;            // If k > length, reduce it
    if (k < 0) {
      k += length;             // Convert negative shift to an equivalent positive shift
    }
    // If k is now 0, it means no rotation is needed: just break the circle and return head
    if (k == 0) {
      tail.next = null;        // Break the circular link
      return head;
    }

    // 4) Find the new tail’s position: it is (length − k − 1) steps from the original head
    int stepsToNewTail = length - k - 1;
    ListNode newTail = head;
    for (int i = 0; i < stepsToNewTail; i++) {
      newTail = newTail.next;
    }

    // 5) The new head is newTail.next
    ListNode newHead = newTail.next;

    // 6) Break the circle by setting newTail.next = null
    newTail.next = null;

    return newHead;
  }

  // Helper method to print the linked list (for testing)
  public static void printList(ListNode head) {
    ListNode current = head;
    while (current != null) {
      System.out.print(current.value + " ");
      current = current.next;
    }
    System.out.println();
  }

  // Example usage
  public static void main(String[] args) {
    // Build an example list: 0 → 1 → 2 → 3 → 4 → 5 → null
    ListNode head = new ListNode(0);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(4);
    head.next.next.next.next.next = new ListNode(5);

    System.out.println("Original List:");
    printList(head);  // 0 1 2 3 4 5

    // Example 1: shift by k = 2 (to the right)
    ListNode shiftedRight2 = shiftLinkedList(head, 2);
    System.out.println("Shifted Right by 2:");
    printList(shiftedRight2);  // Expected: 4 5 0 1 2 3

    // Rebuild the original list (because the previous call disrupted it)
    head = new ListNode(0);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(4);
    head.next.next.next.next.next = new ListNode(5);

    // Example 2: shift by k = -1 (to the left by 1)
    ListNode shiftedLeft1 = shiftLinkedList(head, -1);
    System.out.println("Shifted Left by 1 (i.e. k = -1):");
    printList(shiftedLeft1);   // Expected: 1 2 3 4 5 0
  }
}
```

---

## Detailed Walk‐Through of Key Lines

1. **Finding `length` and `tail`**

   ```java
   int length = 1;
   ListNode tail = head;
   while (tail.next != null) {
     tail = tail.next;
     length++;
   }
   ```

   * We start with `length = 1` (because `head` itself counts).
   * We advance `tail` until `tail.next == null`, counting each node.
   * At the end, `length` holds the total node count, and `tail` is the last node.

2. **Forming a Circular List**

   ```java
   tail.next = head;
   ```

   * Now there is no end—if you start at `head` and keep doing `.next`, you will eventually loop back to `head`.

3. **Normalizing `k`**

   ```java
   k = k % length;
   if (k < 0) {
     k += length;
   }
   ```

   * Using `k % length` ensures that shifting by exactly `length` (or multiples thereof) does nothing.
   * If `k` was negative, we convert it to an equivalent positive shift by adding `length`.

4. **Handling the “no‐shift” Case**

   ```java
   if (k == 0) {
     tail.next = null;    // break the circle
     return head;
   }
   ```

   * If `(k % length) == 0`, rotating by a full cycle returns the list unchanged. So we simply break the circular link (`tail.next = null`) and return the original `head`.

5. **Locating the New Tail**

   ```java
   int stepsToNewTail = length - k - 1;
   ListNode newTail = head;
   for (int i = 0; i < stepsToNewTail; i++) {
     newTail = newTail.next;
   }
   ```

   * After a right‐shift by `k`, the node that was at index `(length − k − 1)` (0‐based) becomes the new tail.
   * We walk from `head` exactly `stepsToNewTail` steps. At that point, `newTail.next` is the new head.

6. **Breaking the Circle**

   ```java
   ListNode newHead = newTail.next;
   newTail.next = null;
   return newHead;
   ```

   * `newHead` points to the node that was formerly `(length − k)`.
   * By setting `newTail.next = null`, we restore the singly linked structure (ending there), and so `newHead` is now the head of the rotated list.

---

## Time & Space Complexity

* **Time Complexity**: **O(n)**, where `n` equals the number of nodes. We do:

  1. One pass to compute `length` and find `tail` (O(n)).
  2. One pass (up to `length − k − 1` steps) to find `newTail` (O(n) in the worst case).
     Overall, that is O(n) + O(n) = O(n).

* **Space Complexity**: **O(1)** extra space. We only used a few pointers (`tail`, `newTail`, `newHead`) and a handful of integer variables (`length`, `k`, `stepsToNewTail`), all regardless of how large `n` is.

That completes the step‐by‐step explanation and Java implementation of shifting (rotating) a linked list by an arbitrary integer `k`.
