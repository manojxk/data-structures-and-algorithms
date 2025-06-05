**Problem Restatement**
You have a singly linked list—each node contains an integer `value` and a pointer `next` to the next node. For example, consider the list:

```
1 → 2 → 3 → 4 → 5 → null
```

Your task is to **reverse** the pointers so that the list becomes:

```
5 → 4 → 3 → 2 → 1 → null
```

You must return the new head of this reversed list. You should also be able to do this in **O(n)** time and **O(1)** extra space, where *n* is the length of the list.

In other words, after reversal, the node that originally held `5` becomes the head, and every arrow points the other direction.

---

## 1. Iterative Approach (O(n) time, O(1) space)

### Key Idea

We maintain three pointers as we traverse once through the list:

1. **`prev`**: A pointer to the node that should come “before” the current node in the reversed list.
2. **`current`**: A pointer to the node we are currently visiting.
3. **`next`**: A temporary pointer to remember `current.next` (so we don’t lose the rest of the list when we reverse the link).

At each step, we “flip” `current.next` from pointing forward into pointing backward at `prev`. Then we move all three pointers one step forward:

* `prev ← current`
* `current ← next`

When `current` becomes `null`, we have walked off the end of the original list, and `prev` now points to what was originally the last node—in fact, the new head of the reversed list. We return `prev`.

### Step‐by‐Step Algorithm

1. **Initialization**

   * `ListNode prev = null;`
   * `ListNode current = head;`
   * `ListNode next = null;`

2. **Loop until `current == null`**

   ```java
   while (current != null) {
       // (a) Remember the next node before we overwrite current.next
       next = current.next;

       // (b) Reverse the pointer: instead of current.next → next, do current.next → prev
       current.next = prev;

       // (c) Advance prev and current one step forward
       prev = current;
       current = next;
   }
   ```

3. **Return**

   * At the end of the loop, `current == null`, and `prev` points at the new head.
   * So return `prev`.

### Detailed Code

```java
package hard.linkedlists;

public class A02ReverseLinkedList {

  // Definition for singly‐linked list node
  static class ListNode {
    int value;
    ListNode next;

    public ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /**
   * Reverses a singly linked list iteratively and returns the new head.
   * Time:  O(n)   (we visit each node exactly once)
   * Space: O(1)   (only three extra pointers, regardless of list size)
   */
  public static ListNode reverseLinkedList(ListNode head) {
    ListNode prev = null;         // Will eventually become the new tail (old head)
    ListNode current = head;      // Used to walk through the original list
    ListNode next = null;         // Temporarily stores current.next

    // Walk until we reach the end of the original list
    while (current != null) {
      // 1) Remember the next node before breaking the link:
      next = current.next;

      // 2) Reverse the link: point current.next back to prev
      current.next = prev;

      // 3) Move prev and current forward by one node:
      prev = current;
      current = next;
    }

    // When current == null, prev is the new head of the reversed list
    return prev;
  }

  /** Helper to print a linked list, space‐separated. */
  public static void printList(ListNode head) {
    ListNode curr = head;
    while (curr != null) {
      System.out.print(curr.value + " ");
      curr = curr.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Build an example list: 1 -> 2 -> 3 -> 4 -> 5 -> null
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.println("Original list:");
    printList(head);   // prints: 1 2 3 4 5

    // Reverse it
    ListNode reversedHead = reverseLinkedList(head);

    System.out.println("Reversed list:");
    printList(reversedHead); // prints: 5 4 3 2 1
  }
}
```

---

## 2. Recursive Approach (O(n) time, O(n) call‐stack)

You can also reverse a singly linked list **recursively**. The idea is:

1. If the list is empty or has only one node, it is its own reverse—simply return `head`. (Base case.)
2. Otherwise, recursively reverse everything from `head.next` onward.
3. After the recursive call returns, you have `newHead` pointing at the head of the reversed sub‐list that used to start at `head.next`. Now you must append `head` at the end of that reversed sub‐list:

   * `head.next.next = head;`
     This sets the original second node’s `next` to point back at `head`, reversing that single link.
   * `head.next = null;`
     Now `head` becomes the last node (tail) in the reversed chain, so we sever its old forward pointer.
4. Return `newHead` upward so the caller gets the head of the fully reversed list.

### Detailed Code

```java
package hard.linkedlists;

public class A02ReverseLinkedList {

  // Node definition remains the same
  static class ListNode {
    int value;
    ListNode next;
    public ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /**
   * Recursively reverses a singly linked list.
   * Returns the new head of the reversed list.
   *  
   * Time:  O(n)   (each node is touched once in the recursion)
   * Space: O(n)   (recursion depth can go up to n)
   */
  public static ListNode reverseLinkedListRecursive(ListNode head) {
    // Base case: empty list or single node
    if (head == null || head.next == null) {
      return head;
    }

    // Recursively reverse from head.next onward
    ListNode newHead = reverseLinkedListRecursive(head.next);

    // Once we return, head.next is the last node of the reversed sub‐list.
    // So point that node’s next back at head:
    head.next.next = head;

    // Sever the old forward link from head to head.next
    head.next = null;

    // newHead is the head of the fully reversed list
    return newHead;
  }

  /** Helper to print a list, for testing. */
  public static void printList(ListNode head) {
    ListNode curr = head;
    while (curr != null) {
      System.out.print(curr.value + " ");
      curr = curr.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Build 1 -> 2 -> 3 -> 4 -> 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.println("Original list:");
    printList(head);   // 1 2 3 4 5

    // Reverse recursively
    ListNode reversedHead = reverseLinkedListRecursive(head);

    System.out.println("Reversed list (recursive):");
    printList(reversedHead); // 5 4 3 2 1
  }
}
```

---

## 3. Time and Space Complexity

| Method             | Time Complexity | Space Complexity             |
| ------------------ | --------------- | ---------------------------- |
| Iterative reversal | O(n)            | O(1)                         |
| Recursive reversal | O(n)            | O(n) call‐stack (worst case) |

* **Iterative**: We do exactly one pass down the list, reassigning `next` pointers. No recursion or extra data structures.
* **Recursive**: Also visits each node once, but the call stack grows to depth *n* in the worst case, so it uses O(n) space for those recursion frames.

---

### Summary

1. **Iterative approach** uses three pointers—`prev`, `current`, `next`—to reverse links in a single pass. This is O(n) time and O(1) extra space.
2. **Recursive approach** reverses the tail of the list first, then flips each link as the stack unwinds. This is still O(n) time but uses O(n) space on the call stack.
3. Both end up returning a new head that was originally the tail, and the original list is now fully reversed.

Either method is correct; in practice, the **iterative** solution is usually preferred so that you avoid potential stack‐overflow on very long lists.
