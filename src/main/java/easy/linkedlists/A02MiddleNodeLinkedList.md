**Problem Explanation**

You’re given the **head** of a singly linked list and need to return the **middle node**. If the list has an **odd** number of nodes, there is a single middle node. If the list has an **even** number of nodes, you should return the **second** of the two middle nodes.

* Example 1: `1 → 2 → 3 → 4 → 5` (5 nodes) → middle is `3`.
* Example 2: `1 → 2 → 3 → 4 → 5 → 6` (6 nodes) → two middles are `3` and `4`, so return `4`.

---

## 1. Brute-Force Approach: Count & Index

### Idea

1. **Count** the total number of nodes, $n$.
2. Compute the **middle index** as $\lfloor n/2\rfloor$ (0-based). That gives you the “second” middle when $n$ is even.
3. **Traverse** again up to that index and return that node.

### Steps

1. Initialize `count = 0` and `current = head`.
2. Loop through the list to increment `count`.
3. Compute `middleIndex = count / 2`.
4. Reset `current = head` and move it forward `middleIndex` steps.
5. Return `current`.

### Java Code

```java
public class A02MiddleNodeLinkedList {

  static class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; this.next = null; }
  }

  /** Brute-force: two passes. */
  public static ListNode findMiddleNodeByCounting(ListNode head) {
    // 1) Count nodes
    int count = 0;
    ListNode curr = head;
    while (curr != null) {
      count++;
      curr = curr.next;
    }

    // 2) Compute middle index (second middle if even)
    int middleIndex = count / 2;

    // 3) Traverse to that index
    curr = head;
    for (int i = 0; i < middleIndex; i++) {
      curr = curr.next;
    }

    // 4) curr now points to the middle node
    return curr;
  }

  /*
   * Time Complexity:  O(n)  — one pass to count, one pass to reach middle
   * Space Complexity: O(1)  — only a few variables regardless of list size
   */

  // (You can test this in main if you like, building a sample list and calling this method.)
}
```

---

## 2. Optimized Approach: Two-Pointer (Fast & Slow)

### Idea

Use **two pointers** that move at different speeds:

* **Slow** pointer advances **1** node per step.
* **Fast** pointer advances **2** nodes per step.

By the time **fast** reaches the end of the list:

* If the list length is odd, **fast** lands exactly on the last node, and **slow** is at the true middle.
* If the list length is even, **fast** becomes `null` after “jumping” past the last node, and **slow** will be at the **second** middle.

This gives you the middle in **one pass**.

### Steps

1. Initialize `slow = head`, `fast = head`.
2. While `fast != null && fast.next != null`:

   * `slow = slow.next;`
   * `fast = fast.next.next;`
3. When the loop ends, **slow** points to the desired middle node.
4. Return `slow`.

### Java Code

```java
public class A02MiddleNodeLinkedList {

  static class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; this.next = null; }
  }

  /** Optimized: one pass with two pointers. */
  public static ListNode findMiddleNode(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    // Advance fast by two and slow by one until fast hits the end
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
    }

    // Slow now points to the middle (second middle if even length)
    return slow;
  }

  /*
   * Time Complexity:  O(n)  — single pass through the list
   * Space Complexity: O(1)  — only two pointers are used
   */
}
```

---

Both approaches run in **linear time** and use **constant extra space**. The two-pointer method is preferred for its simplicity and single-pass nature.
