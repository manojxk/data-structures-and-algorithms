**Problem Restatement**
You have a singly linked list whose tail points back to some earlier node, creating a cycle. Write a function that returns the actual node where the cycle begins (not just its value). You must do this in **O(n)** time and **O(1)** extra space.

For example, if the list is

```
0 → 1 → 2 → 3 → 4 → 5 → 6
                ↑         ↓
                9 ← 8 ← 7
```

then the node labeled “4” is where the loop starts. Your function should return a reference to that node.

---

## Solution: Floyd’s Cycle‐Detection (Tortoise & Hare)

1. **Detect whether a cycle exists** by letting two pointers, `slow` and `fast`, both start at the head:

   * On each step, advance `slow` by one node:

     ```
     slow = slow.next;
     ```
   * Advance `fast` by *two* nodes (if possible):

     ```
     fast = fast.next.next;
     ```
   * If at any point `slow == fast`, a cycle is confirmed.
   * If `fast` reaches `null` (or `fast.next == null`), there is no cycle—return `null`.

2. **Locate the cycle’s starting node** once a cycle is detected:

   * Suppose `slow` and `fast` have met somewhere inside the loop.
   * Reset `slow` back to `head`, but keep `fast` where it is.
   * Now advance both `slow` and `fast` one step at a time:

     ```
     slow = slow.next;
     fast = fast.next;
     ```
   * They will meet again at exactly the node where the cycle begins. Return that node.

---

### Why This Works

* **Cycle‐Detection**: If there is a cycle of length `L`, the faster pointer (`fast`) moves two nodes per step while the slower pointer (`slow`) moves one. Inevitably, `fast` “laps” `slow` inside the cycle, guaranteeing `slow == fast` at some point.

* **Finding the Entrance**:
  Let’s call:

  * `μ` = the number of nodes from the head up to the loop start (distance to cycle).
  * `L` = the length of the cycle.
  * When `slow` and `fast` meet inside the cycle, say they have each traveled distances such that

    ```
    slow_dist = μ + k,
    fast_dist = μ + k + m·L,
    ```

    for some integers `k ≥ 0` and `m ≥ 1`, and `slow_dist = fast_dist/2`.  One can show that `k` is exactly the offset from the loop’s start to the meeting point. When you reset `slow` to `head` (distance 0) and leave `fast` at the meeting point (distance `μ + k` from head), moving each one step per iteration causes them to meet precisely after `μ` more steps—right at the cycle’s start.

---

## Complete Java Code

```java
package hard.linkedlists;

public class A01LoopDetection {

  // Definition for a singly linked list node
  static class LinkedList {
    int value;
    LinkedList next;

    LinkedList(int value) {
      this.value = value;
      this.next = null;
    }
  }

  /**
   * Returns the node where the loop begins (the “entrance” to the cycle),
   * or null if no loop exists. Runs in O(n) time using O(1) extra space.
   */
  public static LinkedList findLoopOrigin(LinkedList head) {
    // 1) Use two pointers to detect a cycle
    LinkedList slow = head;
    LinkedList fast = head;

    // Move slow by 1 step, fast by 2 steps, until they meet or fast reaches null
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) {
        // A cycle is detected
        break;
      }
    }

    // If fast reached the end, there is no cycle
    if (fast == null || fast.next == null) {
      return null;
    }

    // 2) To find the cycle’s start, reset slow back to head
    slow = head;

    // Move both slow and fast one step at a time; where they meet is the loop start
    while (slow != fast) {
      slow = slow.next;
      fast = fast.next;
    }

    // That meeting node is the entrance of the loop
    return slow;
  }

  public static void main(String[] args) {
    // Example: build a list 0→1→2→3→4→5→6→7→8→9 
    //                     ↖——————————————↙
    LinkedList head = new LinkedList(0);
    head.next = new LinkedList(1);
    head.next.next = new LinkedList(2);
    head.next.next.next = new LinkedList(3);
    head.next.next.next.next = new LinkedList(4);
    head.next.next.next.next.next = new LinkedList(5);
    head.next.next.next.next.next.next = new LinkedList(6);
    head.next.next.next.next.next.next.next = new LinkedList(7);
    head.next.next.next.next.next.next.next.next = new LinkedList(8);
    head.next.next.next.next.next.next.next.next.next = new LinkedList(9);
    // Make node 9 point to node 4, creating a loop
    head.next.next.next.next.next.next.next.next.next.next = head.next.next.next.next;

    LinkedList loopStart = findLoopOrigin(head);
    if (loopStart != null) {
      System.out.println("Loop starts at node with value: " + loopStart.value);
    } else {
      System.out.println("No loop detected.");
    }
  }
}
```

---

### Explanation of Key Steps

1. **Detecting the Cycle**

   ```java
   LinkedList slow = head;
   LinkedList fast = head;
   while (fast != null && fast.next != null) {
     slow = slow.next;
     fast = fast.next.next;
     if (slow == fast) {
       break;  // cycle found
     }
   }
   if (fast == null || fast.next == null) {
     return null;  // no cycle
   }
   ```

   * `slow` moves one node per iteration, `fast` moves two.
   * If there is a loop, eventually `fast` “laps” `slow` and they meet inside the cycle.
   * If `fast` becomes null (or `fast.next` is null), we’ve reached the end of the list—no cycle.

2. **Finding the Cycle’s Entrance**
   After detecting, `slow == fast` somewhere *inside* the loop. Let’s call that meeting point `M`. To find the loop’s start, set:

   ```java
   slow = head;
   while (slow != fast) {
     slow = slow.next;
     fast = fast.next;
   }
   ```

   * Now `slow` travels from the head, and `fast` travels from `M`, both one step at a time. They will meet at exactly the node where the loop first begins. That is the node we return.

3. **Time Complexity: O(n)**

   * The first phase (detecting) takes O(n) in the worst case.
   * The second phase (locating the entrance) also takes O(n) in the worst case.
   * Total is O(n).

4. **Space Complexity: O(1)**

   * We only used two pointers (`slow` and `fast`) and a few local variables. No extra data structures.

That completes a constant‐space, linear‐time solution to find the start of a cycle in a singly linked list.
