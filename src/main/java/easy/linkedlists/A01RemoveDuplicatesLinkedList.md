**Problem Restatement**

You have a **sorted** singly-linked list, where each node contains an integer value and a pointer to the next node. Because the list is sorted in non-decreasing order, any **duplicate** values will appear in **adjacent** nodes. Your task is to **remove** all duplicate nodes so that each value appears **exactly once**, and return the head of the cleaned-up list.

---

## Why This Works Only on a Sorted List

* In an **unsorted** list, duplicates could be scattered anywhere, and removing them in one pass would require extra memory (e.g. a hash set) and more logic.
* But because the list is **sorted**, whenever you see two adjacent nodes with the **same** value, you know for sure the **second** one is a duplicate. You can simply skip it.

---

## Approach Overview

1. **Edge cases**

   * If the list is **empty** (`head == null`) or has **only one** node (`head.next == null`), there are no duplicates to remove. Just return `head`.

2. **Single pass traversal**

   * Maintain a pointer `current` starting at the head.
   * While `current` and `current.next` are not null:

     * **Compare** `current.val` with `current.next.val`.

       * If they are **equal**, we’ve found a duplicate:

         * **Skip** the next node by setting `current.next = current.next.next`.
         * (Don’t advance `current`, because there might be more duplicates following.)
       * Otherwise, they’re **different**, so advance `current = current.next`.

3. **Finish**

   * When you reach the end of the list, all duplicates have been removed in-place. Return the original `head`.

---

## Step-by-Step Example

List: `1 → 1 → 2 → 3 → 3`

* **Start**: `current` points to the **first** node (value = 1).

  * `current.next.val` is also 1 → duplicate.
  * **Skip** the second node:

    * Link the first node directly to the third: `1 → 2 → 3 → 3`.
  * Do **not** move `current`.

* **Still at** the first node (`1`):

  * Now `current.next.val` is 2 → not a duplicate.
  * **Move** `current` to the node with value = 2.

* **At** node `2`:

  * `current.next.val` is 3 → not a duplicate.
  * **Move** `current` to node `3` (the first 3).

* **At** first node `3`:

  * `current.next.val` is also 3 → duplicate.
  * **Skip** the second 3: `… → 3 → null`.
  * Do **not** move `current`.

* **Still at** the first 3:

  * Now `current.next` is `null` → end loop.

Resulting list: `1 → 2 → 3`.

---

## Java Implementation

```java
package easy.linkedlists;

public class A01RemoveDuplicatesLinkedList {

  // Definition for a node in the singly-linked list.
  static class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
      this.val = val;
      this.next = null;
    }
  }

  /**
   * Removes duplicates from a sorted linked list in-place.
   *
   * @param head The head of the sorted linked list.
   * @return The head of the list after removing all duplicates.
   *
   * Time Complexity:  O(n), where n is the number of nodes, since we traverse once.
   * Space Complexity: O(1), constant extra space.
   */
  public ListNode deleteDuplicates(ListNode head) {
    // 1) Base cases: empty list or single node → no duplicates possible
    if (head == null || head.next == null) {
      return head;
    }

    // 2) Traverse with a pointer
    ListNode current = head;

    while (current != null && current.next != null) {
      if (current.val == current.next.val) {
        // Duplicate found: skip the next node
        current.next = current.next.next;
      } else {
        // No duplicate: move to the next distinct node
        current = current.next;
      }
    }

    // 3) Return the modified list
    return head;
  }

  // Helper to build and test the list
  public static void main(String[] args) {
    A01RemoveDuplicatesLinkedList solution = new A01RemoveDuplicatesLinkedList();

    // Build example list: 1 -> 1 -> 2 -> 3 -> 3
    ListNode head = new ListNode(1);
    head.next = new ListNode(1);
    head.next.next = new ListNode(2);
    head.next.next.next = new ListNode(3);
    head.next.next.next.next = new ListNode(3);

    // Remove duplicates
    ListNode result = solution.deleteDuplicates(head);

    // Print the result: expected 1 -> 2 -> 3
    while (result != null) {
      System.out.print(result.val);
      if (result.next != null) System.out.print(" -> ");
      result = result.next;
    }
    // Output should be: 1 -> 2 -> 3
  }
}
```

---

### Complexity Summary

* **Time:**
  We examine each node at most once, so **O(n)**.

* **Space:**
  We only use a couple of pointers—**O(1)** extra space.
