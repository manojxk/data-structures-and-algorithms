**Problem Restatement**
You have two sorted singly linked lists, `l1` and `l2`, each of whose nodes contains an integer value (in ascending order). Your task is to merge them into one sorted linked list (in ascending order), splicing together the nodes of the original two lists (i.e. no new nodes besides a “dummy” placeholder). Finally, return the head of this merged list.

For example:

```
l1: 1 → 3 → 5 → 7 → null  
l2: 2 → 4 → 6 → 8 → null  
```

After merging, you should get:

```
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → null
```

---

## Approach

1. **Use a “dummy” starter node**:
   Create a new `ListNode dummy = new ListNode(0);` and keep a pointer `current = dummy`. This dummy node’s `.next` will become the head of the merged list once we finish splicing.

2. **Maintain two pointers** — one (`p1`) walking through `l1`, and one (`p2`) walking through `l2`. Both lists are already sorted.

3. **Compare values and append the smaller node**:

   * While both `p1` and `p2` are non‐null:

     * If `p1.value ≤ p2.value`, then `current.next = p1;` and advance `p1 = p1.next`.
     * Else, `current.next = p2;` and advance `p2 = p2.next`.
     * In either case, move `current = current.next`.

4. **One list may run out before the other**:

   * Once either `p1 == null` or `p2 == null`, break out of that loop.
   * Append whichever list still has remaining nodes by doing `current.next = (p1 != null ? p1 : p2);`. Because the remaining list is already sorted, you can just link it on.

5. **Return the merged head**:

   * The merged list actually starts at `dummy.next`.

Since every node from `l1` and `l2` is visited exactly once, the time complexity is **O(n + m)**. We only use a constant number of extra pointers, so the space complexity is **O(1)**.

---

## Complete Java Code

```java
package hard.linkedlists;

public class A03MergeLinkedLists {

  // Definition for singly‐linked list node
  static class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /**
   * Merges two sorted linked lists l1 and l2 into one sorted list.
   * Returns the head of the newly merged list.
   *
   * Time Complexity:  O(n + m)
   *   (where n = length of l1, m = length of l2)
   * Space Complexity: O(1)  (only a few pointers used)
   */
  public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    // 1) Create a dummy starter node
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;

    // 2) Use two pointers to walk through l1 and l2
    ListNode p1 = l1;
    ListNode p2 = l2;

    // 3) While both lists still have nodes, compare and append the smaller one
    while (p1 != null && p2 != null) {
      if (p1.value <= p2.value) {
        current.next = p1;
        p1 = p1.next;
      } else {
        current.next = p2;
        p2 = p2.next;
      }
      current = current.next;
    }

    // 4) At least one of p1/p2 is now null. Append the remaining non‐null list:
    if (p1 != null) {
      current.next = p1;
    } else {
      current.next = p2;
    }

    // 5) The merged list’s head is dummy.next
    return dummy.next;
  }

  /** Utility to print a linked list (space‐separated). */
  public static void printList(ListNode head) {
    ListNode curr = head;
    while (curr != null) {
      System.out.print(curr.value + " ");
      curr = curr.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Example 1:
    // l1: 1 → 3 → 5 → 7
    ListNode l1 = new ListNode(1);
    l1.next = new ListNode(3);
    l1.next.next = new ListNode(5);
    l1.next.next.next = new ListNode(7);

    // l2: 2 → 4 → 6 → 8
    ListNode l2 = new ListNode(2);
    l2.next = new ListNode(4);
    l2.next.next = new ListNode(6);
    l2.next.next.next = new ListNode(8);

    System.out.println("List 1:");
    printList(l1);  // 1 3 5 7
    System.out.println("List 2:");
    printList(l2);  // 2 4 6 8

    ListNode merged = mergeTwoLists(l1, l2);
    System.out.println("Merged List:");
    printList(merged);  // 1 2 3 4 5 6 7 8

    // Example 2:
    // l1: 5 → 6 → 7
    ListNode a1 = new ListNode(5);
    a1.next = new ListNode(6);
    a1.next.next = new ListNode(7);

    // l2: 1 → 2 → 3
    ListNode a2 = new ListNode(1);
    a2.next = new ListNode(2);
    a2.next.next = new ListNode(3);

    System.out.println("\nList A1:");
    printList(a1);  // 5 6 7
    System.out.println("List A2:");
    printList(a2);  // 1 2 3

    ListNode merged2 = mergeTwoLists(a1, a2);
    System.out.println("Merged List:");
    printList(merged2);  // 1 2 3 5 6 7
  }
}
```

---

### Explanation of Key Steps

1. **Dummy Node Setup**

   ```java
   ListNode dummy = new ListNode(0);
   ListNode current = dummy;
   ```

   * We create a single “dummy” node whose value doesn’t matter (we choose 0). Its role is purely to hold the `next` pointer for the very first node in the merged list—this avoids having to check “is this the first node?” inside the loop.
   * `current` tracks the tail of the merged list as we build it. At the end, `dummy.next` will be the true head.

2. **Walking through Both Lists**

   ```java
   while (p1 != null && p2 != null) {
     if (p1.value <= p2.value) {
       current.next = p1;
       p1 = p1.next;
     } else {
       current.next = p2;
       p2 = p2.next;
     }
     current = current.next;
   }
   ```

   * At each iteration, compare `p1.value` and `p2.value`.
   * Whichever is smaller—splice that node onto `current.next`, then advance that pointer (`p1` or `p2`).
   * Advance `current` so it always points to the newly‐added node.

3. **Appending the Remainder**

   ```java
   if (p1 != null) {
     current.next = p1;
   } else {
     current.next = p2;
   }
   ```

   * Once one of the lists is exhausted, the other may still have nodes. Because it was already sorted, we can just link `current.next` to the non‐null remainder.

4. **Return Merged Head**

   ```java
   return dummy.next;
   ```

   * Since `dummy` was merely a placeholder, the real merged list starts at `dummy.next`.

---

### Complexity

* **Time Complexity:**
  Each node from `l1` and `l2` is visited exactly once, compared at most once, and spliced exactly once. If `n =` length of `l1` and `m =` length of `l2`, the total time is

  $$
    O(n + m).
  $$

* **Space Complexity:**
  We only used a handful of extra pointers (`dummy`, `current`, plus the two walk‐through pointers `p1`, `p2`). No additional nodes were allocated (beyond the dummy). Hence

  $$
    O(1).
  $$

That completes the in‐place merging of two sorted linked lists in linear time with constant extra space.
