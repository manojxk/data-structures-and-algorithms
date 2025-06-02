**Problem Restatement**
You have two non‐empty singly linked lists, each representing a nonnegative integer in **reverse order**, i.e.:

* The head node holds the least significant digit (ones place).
* Each subsequent node holds the next digit (tens, hundreds, etc.).

Write a function that **returns a new linked list** (again in reversed digit order) representing the sum of those two integers. You must **not modify** either input list.

> **Example**
>
> ```
> Input:  (2 → 4 → 7 → 1)  +  (9 → 4 → 5)
>           ↑—LS-digit            ↑—LS-digit  
>     This corresponds to 1742 + 549 = 2291
> Output: 1 → 9 → 2 → 2  
>           ↑—LS-digit  
>     Which corresponds to 2291
> ```

---

## Approach

1. **Dummy Head Technique**

   * Create a “dummy” node `dummyHead = new ListNode(0)`.
   * Keep a pointer `current = dummyHead`, which will build the result list from left (least significant digit) to right.

2. **Traverse Both Lists Simultaneously**

   * Maintain an integer `carry = 0`.
   * While at least one of `l1` or `l2` is non‐null, or `carry != 0`:

     1. Let `x = (l1 != null) ? l1.value : 0`.
     2. Let `y = (l2 != null) ? l2.value : 0`.
     3. Compute `sum = carry + x + y`.
     4. The resulting digit at this position is `sum % 10`. Create a new node with that value:

        ```java
        current.next = new ListNode(sum % 10);
        current = current.next;
        ```
     5. Update `carry = sum / 10`.
     6. Move `l1 = (l1 != null) ? l1.next : null`.
     7. Move `l2 = (l2 != null) ? l2.next : null`.

3. **Return** `dummyHead.next` as the head of the summed‐list. The dummy helps avoid special‐casing the first node creation.

Because you process each digit from least to most significant, handling the carry automatically, you produce the correct sum in a single pass over both lists.

---

## Full Java Code

```java
package medium.linkedlists;

public class A03LinkedListSum {

  // Definition for a singly‐linked list node
  static class ListNode {
    int value;
    ListNode next;
    ListNode(int x) { value = x; }
  }

  /**
   * Adds two numbers represented by reversed‐order linked lists l1 and l2.
   * Returns a brand‐new linked list (also reversed‐order) with their sum.
   *
   * Time Complexity:  O(max(n, m)), where n and m are the lengths of l1 and l2.
   * Space Complexity: O(max(n, m) + 1), for the result list (possibly one extra node for final carry).
   */
  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummyHead = new ListNode(0);
    ListNode current = dummyHead;
    int carry = 0;

    // Continue until both lists are exhausted and no carry remains
    while (l1 != null || l2 != null || carry != 0) {
      int x = (l1 != null) ? l1.value : 0;
      int y = (l2 != null) ? l2.value : 0;
      int sum = carry + x + y;

      carry = sum / 10;
      int digit = sum % 10;
      current.next = new ListNode(digit);
      current = current.next;

      if (l1 != null) l1 = l1.next;
      if (l2 != null) l2 = l2.next;
    }

    return dummyHead.next;
  }

  // Helper to print a linked list from head to tail
  public static void printList(ListNode head) {
    ListNode cur = head;
    while (cur != null) {
      System.out.print(cur.value);
      if (cur.next != null) System.out.print(" -> ");
      cur = cur.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // Example 1: 2 -> 4 -> 7 -> 1  represents 1742
    ListNode l1 = new ListNode(2);
    l1.next = new ListNode(4);
    l1.next.next = new ListNode(7);
    l1.next.next.next = new ListNode(1);

    // Example 2: 9 -> 4 -> 5  represents 549
    ListNode l2 = new ListNode(9);
    l2.next = new ListNode(4);
    l2.next.next = new ListNode(5);

    System.out.print("List 1: ");
    printList(l1);  // 2 -> 4 -> 7 -> 1
    System.out.print("List 2: ");
    printList(l2);  // 9 -> 4 -> 5

    ListNode result = addTwoNumbers(l1, l2);
    System.out.print("Sum:    ");
    printList(result); // Expected 1 -> 9 -> 2 -> 2  (2291)
  }
}
```

---

## Explanation of Key Points

* **Least Significant Digit First**
  Because each node’s “value” is a single digit (0–9) and the list is stored in **reverse** (head = ones place), you can simply add `l1.value + l2.value + carry` to produce the next output digit. No need to reverse the input or output.

* **Handling Unequal Length**
  If one list is shorter, use `x = 0` when `l1` is null (likewise for `l2`). The loop continues as long as there is some digit or a nonzero carry.

* **Carry Propagation**
  After `sum = x + y + carry`, the next digit is `sum % 10`, and the new carry is `sum / 10`. At the very end, if `carry` is still nonzero (e.g.\ when summing 999 + 1 → 1000), the loop’s condition `carry != 0` ensures you create an extra leading node for that carry.

* **Dummy Head**
  Using a dummy node at the start means you don’t have to check “is this the first node we’re adding?” You always do `current.next = new ListNode(...)` and then move `current = current.next`. Finally, return `dummyHead.next`.

---

### Complexity Recap

* **Time Complexity:**
  You walk through at most $\max(n,m)$ nodes (where $n$ and $m$ are the lengths of the two input lists), performing constant work per node (adding digits, creating a new node). Hence **$O(\max(n,m))$**.

* **Space Complexity:**
  You allocate a new node for each digit in the result. In the worst case, the sum has one extra digit (carry at the very end). So the result list length is at most $\max(n,m) + 1$. Thus **$O(\max(n,m))$** extra space (excluding the input lists).

This completes a clean, one‐pass solution to sum two reversed‐order linked‐list integers.
