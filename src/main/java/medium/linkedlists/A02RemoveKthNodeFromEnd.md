**Problem Restatement**
Remove the $k$-th node from the end of a singly linked list and return the head of the modified list. You should do this in one pass (i.e.\ $O(n)$ time) and $O(1)$ extra space.

> **Example 1**
>
> ```
> Input:  1 → 2 → 3 → 4 → 5,  k = 2
> Output: 1 → 2 → 3 → 5
> Explanation: The 2nd node from the end is “4,” so we remove it.
> ```

> **Example 2**
>
> ```
> Input:  1 → 2,  k = 2
> Output: 2
> Explanation: The 2nd node from the end is “1,” so we remove it (the head).
> ```

---

## Approach: Two‐Pointer (Fast and Slow)

1. **Use two pointers**, `first` and `second`, both starting at the head of the list.
2. **Advance `first` by $k$ nodes**. After this step, `first` is $k$ steps ahead of `second`.

   * If `first` becomes `null` immediately after advancing $k$ times, that means $k$ is exactly the length of the list. In that case, the “$k$-th from the end” is actually the head node. So you simply return `head.next` (i.e.\ remove the head).
3. **Move both `first` and `second` simultaneously** until `first.next` becomes `null`.

   * At that moment, `first` is at the last node, and `second` is right **before** the node you want to remove (because `second` started $k$ steps behind).
4. **Unlink the $k$-th from last** by doing

   ```java
   second.next = second.next.next;
   ```
5. **Return the original head** (unless you already removed it in step 2).

Because you only traverse each node at most once, the time complexity is $O(n)$. You only use two pointers and a few variables, so the extra space is $O(1)$.

---

## Code Explanation

```java
public class A02RemoveKthNodeFromEnd {

  // List‐node definition
  static class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
      this.next = null;
    }
  }

  // Removes the k-th node from the end and returns the (possibly new) head
  public ListNode removeKthFromEnd(ListNode head, int k) {
    // 1) Initialize two pointers at the head
    ListNode first = head;
    ListNode second = head;

    // 2) Move 'first' k steps ahead
    for (int i = 0; i < k; i++) {
      first = first.next;
    }

    // 3) If first is now null, we have k == length of list,
    //    so the node to remove is the head itself.
    if (first == null) {
      // Remove head by returning head.next
      return head.next;
    }

    // 4) Otherwise, advance both pointers until 'first' reaches the last node
    //    (i.e., first.next == null). Then 'second' will be right before the node to remove.
    while (first.next != null) {
      first = first.next;
      second = second.next;
    }

    // 5) Unlink the k-th from last:
    //    second.next is the node to remove, so skip it.
    second.next = second.next.next;

    // 6) Return original head
    return head;
  }

  // Helper to print out the list (for testing)
  public static void printList(ListNode head) {
    ListNode cur = head;
    while (cur != null) {
      System.out.print(cur.val + " ");
      cur = cur.next;
    }
    System.out.println();
  }

  public static void main(String[] args) {
    A02RemoveKthNodeFromEnd solution = new A02RemoveKthNodeFromEnd();

    // Build: 1 → 2 → 3 → 4 → 5
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);

    System.out.print("Original List: ");
    printList(head);  // 1 2 3 4 5

    // Remove the 2nd node from end (which is 4)
    head = solution.removeKthFromEnd(head, 2);
    System.out.print("After removing 2nd from end: ");
    printList(head);  // 1 2 3 5

    // Example 2: [1 → 2], k = 2 → remove head
    ListNode head2 = new ListNode(1);
    head2.next = new ListNode(2);
    System.out.print("List: ");
    printList(head2);  // 1 2
    head2 = solution.removeKthFromEnd(head2, 2);
    System.out.print("After removing 2nd from end: ");
    printList(head2);  // 2
  }
}
```

### Step‐by‐Step

1. **Setup**

   ```java
   ListNode first = head;
   ListNode second = head;
   ```

   Both pointers start at the head.

2. **Advance `first` by $k$ steps**

   ```java
   for (int i = 0; i < k; i++) {
     first = first.next;
   }
   ```

   * If the list has exactly $k$ nodes, after these $k$ steps `first` becomes `null`. That means the node to remove is the head itself, so we return `head.next`.

3. **Move both pointers until `first.next == null`**

   ```java
   while (first.next != null) {
     first = first.next;
     second = second.next;
   }
   ```

   * Now `first` will land on the **last** node. Simultaneously, `second` has moved so that `second.next` is the node to remove (the $k$-th from the end).

4. **Remove it**

   ```java
   second.next = second.next.next;
   ```

   This bypasses the node to remove.

5. **Return `head`**
   Unless we removed the head in the special case, the head never changes, so return it.

---

## Complexity

* **Time:** $O(n)$. We do two passes at most:

  1. Move `first` $k$ steps ($O(k)$),
  2. Advance both pointers until the end ($O(n - k)$).
     Combined, that’s $O(n)$.
* **Space:** $O(1)$. Only a constant number of extra pointers/variables.

This completes a concise, one‐pass solution to remove the $k$-th node from the end of a singly linked list.
