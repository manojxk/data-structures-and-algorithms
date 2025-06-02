Below are four distinct methods to find the intersection node of two singly‐linked lists. Each method is described in turn, along with its time and space complexity and why it works.

---

## Problem Restatement

> You have two singly‐linked lists (say, **List A** and **List B**) that may (or may not) share a “tail” segment. In other words, at some node onward, every `.next` reference is the same between the two lists. You must return the first node at which they intersect (i.e. the node reference, not just the value). If they do not intersect, return `null`. You may not modify either list or create new nodes.

Example (arrows denote `next` pointers):

```
List A: 2 → 3 → 1
                   ↘
                    4 → 5
                   ↗
List B:      8 → 7
```

Here, both lists share the node with value 4 (and then node 5), so the intersection is at the node whose `.value == 4`.

It is guaranteed that at most one such intersection can exist, and if it exists, the total “extra” nodes before intersection in A plus the extra nodes before intersection in B sums to exactly the same number of nodes as remain in the shared tail (though that guarantee isn’t strictly required for correctness).

---

## 1. Brute Force (O(n·m) Time, O(1) Space)

**Idea**
For each node in List A, scan List B from head to tail looking for the exact same node reference. If you ever find it, that is your intersection. If you exhaust List B and find no match, move to the next node in List A. If you finish all of List A without ever matching, return `null`.

**Pseudocode**

```
for each nodeA in List A:
    for each nodeB in List B:
        if nodeA == nodeB:   // compare references, not just values
            return nodeA
return null
```

**Why It Works**
Every node in A is compared against every node in B until you find the first common reference. If there is an intersection, then at some pair you will match exactly that shared node reference.

**Complexity**

* Time: $O(n \times m)$, where $n =$ length of A and $m =$ length of B, because for every A‐node you do a full scan of B.
* Space: $O(1)$ extra—just a couple of pointers.

---

## 2. Hash‐Set (O(n + m) Time, O(n) or O(m) Space)

**Idea**

1. Traverse List A once and insert each node’s reference into a `HashSet<ListNode>`.
2. Traverse List B once; for each node in B, check if it exists in the set from step 1. The first such hit is the intersection. If you finish B with no hit, return `null`.

**Pseudocode**

```
nodesSeen = empty HashSet

// Step 1: Walk List A
for each nodeA in List A:
    nodesSeen.add(nodeA)

// Step 2: Walk List B
for each nodeB in List B:
    if nodesSeen.contains(nodeB):
        return nodeB

return null
```

**Why It Works**
Any node that appears in the shared tail is in List A’s set, so the first time List B steps into that shared segment, you detect it in O(1). This effectively finds the earliest intersection in one pass of B.

**Complexity**

* Time: $O(n + m)$. You traverse A once (cost $O(n)$) to build the set, then B once (cost $O(m)$) to check membership.
* Space: $O(n)$ if you store nodes of A, or $O(m)$ if you choose to build the set from B and then scan A (either way, linear extra space).

---

## 3. Two‐Pointer Technique (O(n + m) Time, O(1) Space)

**Idea**
Use two pointers, `pointerA` and `pointerB`, initialized at the heads of A and B respectively. Advance them step by step. Whenever a pointer reaches the end of its list, redirect it to the head of the *other* list. Continue until the two pointers meet or both become `null`.

1. `pointerA` starts at headA, `pointerB` starts at headB.
2. While `pointerA != pointerB`:

   * Move `pointerA = (pointerA == null) ? headB : pointerA.next;`
   * Move `pointerB = (pointerB == null) ? headA : pointerB.next;`
3. When the loop exits, either both are `null` (no intersection) or they point to the same shared node.

**Why It Works**
— Suppose List A has $n$ nodes before the intersection, and List B has $m$ nodes before the intersection, and the shared tail has $t$ nodes. Then:

* In the first pass,

  * `pointerA` traverses all $n + t$ nodes of A, then jumps to head of B and travels the first $m$ nodes of B (so total $n + t + m$ steps).
  * `pointerB` traverses all $m + t$ nodes of B, then jumps to head of A and travels the first $n$ nodes of A (so total $m + t + n$ steps).

After exactly $n + t + m$ steps each, they will rendezvous at the first node of the shared tail. If there is no shared tail (i.e.\ no intersection), both pointers will become `null` after total $(n + m)$ steps and exit.

**Complexity**

* Time: $O(n + m)$. Each pointer traverses at most $n + m$ nodes.
* Space: $O(1)$. Only two pointers are used, no extra data structures.

---

## 4. Length‐Difference Alignment (O(n + m) Time, O(1) Space)

**Idea**

1. Compute the lengths of both lists (call them `lenA` and `lenB`).
2. Advance the head pointer of the longer list by $\lvert\,\text{lenA} - \text{lenB}\rvert$ steps. This “aligns” the two lists so that the remaining number of nodes to traverse in each is the same.
3. Then move both pointers in lockstep (one step at a time) until either they become equal (intersection) or both reach `null` (no intersection).

**Pseudocode**

```
lenA = lengthOfList(headA)
lenB = lengthOfList(headB)

if (lenA > lenB) {
  headA = advanceBy(headA, lenA - lenB)
} else {
  headB = advanceBy(headB, lenB - lenA)
}

// Now headA and headB have the same number of nodes to go until end
while (headA != null && headB != null) {
  if (headA == headB) {
    return headA;  // intersection
  }
  headA = headA.next;
  headB = headB.next;
}
return null;
```

**Helper**

* `lengthOfList(node)`: Traverse to count how many nodes.
* `advanceBy(node, steps)`: Move `steps` times from `node.next` down the list.

**Why It Works**
By “chopping off” the extra nodes in the longer list, both pointers end up traversing exactly the same number of steps before reaching the (possible) shared tail. If there is an intersection, they will arrive at it simultaneously. If there is none, they’ll both hit `null`.

**Complexity**

* Time: $O(n + m)$. One full pass of each list to measure length, then up to $\max(n,m)$ steps for simultaneous traversal.
* Space: $O(1)$. Only a fixed number of pointers and counters.

---

## Summary of Approaches

| Method                      | Time Complexity | Space Complexity | Notes                                                                           |
| --------------------------- | --------------- | ---------------- | ------------------------------------------------------------------------------- |
| Brute‐Force                 | $O(n \times m)$ | $O(1)$           | Compare every node of A against every node of B.                                |
| Hash‐Set                    | $O(n + m)$      | $O(\min(n,m))$   | Store all nodes of one list in a `HashSet`, then scan the other.                |
| Two‐Pointer (Switching)     | $O(n + m)$      | $O(1)$           | Switch heads when you reach the end; they rendezvous at intersection.           |
| Length‐Difference Alignment | $O(n + m)$      | $O(1)$           | Advance the longer list’s pointer by the length difference, then walk together. |

In practice, both the **Two‐Pointer** and **Length‐Difference** methods run in linear time and use constant extra space, so they are preferred over the brute‐force and hash‐set solutions. The two‐pointer “switching” approach is particularly concise and does not require computing list lengths explicitly.

---

### Full Code Listing (Combined)

```java
package medium.linkedlists;

import java.util.HashSet;

public class A04LinkedListIntersection {

  // Definition for singly‐linked list node
  static class ListNode {
    int value;
    ListNode next;
    ListNode(int value) { this.value = value; this.next = null; }
  }

  // 1) Brute‐Force (O(n·m) time, O(1) space)
  public static ListNode getIntersectionNodeBruteForce(ListNode headA, ListNode headB) {
    ListNode a = headA;
    while (a != null) {
      ListNode b = headB;
      while (b != null) {
        if (a == b) {
          return a;
        }
        b = b.next;
      }
      a = a.next;
    }
    return null;
  }

  // 2) Hash‐Set (O(n + m) time, O(n) or O(m) space)
  public static ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
    HashSet<ListNode> seen = new HashSet<>();
    ListNode a = headA;
    while (a != null) {
      seen.add(a);
      a = a.next;
    }
    ListNode b = headB;
    while (b != null) {
      if (seen.contains(b)) {
        return b;
      }
      b = b.next;
    }
    return null;
  }

  // 3) Two‐Pointer Switching (O(n + m) time, O(1) space)
  public static ListNode getIntersectionNodeTwoPointers(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode a = headA;
    ListNode b = headB;
    // Advance each pointer, switching to the other head upon reaching null
    while (a != b) {
      a = (a == null) ? headB : a.next;
      b = (b == null) ? headA : b.next;
    }
    // Either both null (no intersection) or both at intersection node
    return a;
  }

  // 4) Length‐Difference Alignment (O(n + m) time, O(1) space)
  public static ListNode getIntersectionNodeLengthBased(ListNode headA, ListNode headB) {
    int lenA = getLength(headA);
    int lenB = getLength(headB);

    // Move the longer list forward by |lenA − lenB|
    ListNode a = headA, b = headB;
    if (lenA > lenB) {
      a = advanceBy(a, lenA - lenB);
    } else {
      b = advanceBy(b, lenB - lenA);
    }

    while (a != null && b != null) {
      if (a == b) {
        return a;
      }
      a = a.next;
      b = b.next;
    }
    return null;
  }

  private static int getLength(ListNode head) {
    int length = 0;
    while (head != null) {
      length++;
      head = head.next;
    }
    return length;
  }

  private static ListNode advanceBy(ListNode node, int steps) {
    while (steps > 0 && node != null) {
      node = node.next;
      steps--;
    }
    return node;
  }

  // Main driver to demonstrate all four methods
  public static void main(String[] args) {
    // Build two intersecting lists:
    //    A: 2 → 3
    //             ↘
    //              1 → 4
    //             ↗
    //    B: 8 → 7
    ListNode common = new ListNode(1);
    common.next = new ListNode(4);

    ListNode headA = new ListNode(2);
    headA.next = new ListNode(3);
    headA.next.next = common;   // intersection at “1”

    ListNode headB = new ListNode(8);
    headB.next = new ListNode(7);
    headB.next.next = common;   // intersection at “1”

    // 1) Brute‐Force
    ListNode inter1 = getIntersectionNodeBruteForce(headA, headB);
    System.out.println("Brute‐Force: " +
        (inter1 == null ? "null" : inter1.value));  // expects 1

    // 2) Hash‐Set
    ListNode inter2 = getIntersectionNodeHashSet(headA, headB);
    System.out.println("Hash‐Set: " +
        (inter2 == null ? "null" : inter2.value));  // expects 1

    // 3) Two‐Pointer Switching
    ListNode inter3 = getIntersectionNodeTwoPointers(headA, headB);
    System.out.println("Two‐Pointer: " +
        (inter3 == null ? "null" : inter3.value));  // expects 1

    // 4) Length‐Difference Alignment
    ListNode inter4 = getIntersectionNodeLengthBased(headA, headB);
    System.out.println("Length‐Based: " +
        (inter4 == null ? "null" : inter4.value));  // expects 1
  }
}
```

Feel free to choose whichever approach best fits your use case:

* **Two‐Pointer Switching** is the most concise and uses constant extra space.
* **Length‐Difference** is also $O(1)$ extra space but requires computing each list’s length first.
* **Hash‐Set** is simpler to understand but uses $O(n)$ additional memory.
* **Brute‐Force** is easiest to reason about but is $O(n \times m)$ and becomes slow on long lists.
