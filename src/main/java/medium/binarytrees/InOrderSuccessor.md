**Problem Restatement**
You have a binary tree in which each node not only has pointers to its left and right children, but also a pointer to its parent. Given a reference to a particular node in this tree, you must find its **in‐order successor**—that is, the node that appears immediately after the given node when you list all nodes in **in‐order traversal** (left → node → right). If such a successor doesn’t exist (i.e. the given node is the last one in in‐order), return `null`.

---

## In‐Order Traversal Refresher

* In‐order traversal of a binary tree visits nodes in ascending order of “left subtree first, then current node, then right subtree.”
* Concretely, if you wrote a recursive in‐order routine:

  ```java
  void inOrder(Node node) {
    if (node == null) return;
    inOrder(node.left);
    visit(node);
    inOrder(node.right);
  }
  ```

  then the “visit” sequence is exactly the sorted sequence (for a BST) or the left‐to‐right sequence for any binary tree.

---

## How to Find the In‐Order Successor Using Parent Pointers

You want a **constant‐space** (O(1) extra memory) solution that runs in **O(h)** time, where *h* is the height of the tree. There are two primary cases to consider:

1. **Node Has a Right Child**

   * If the given node `n` has a non‐null `n.right`, then its in‐order successor is simply the **leftmost** node in `n.right`’s subtree.
   * Why? Because once you finish visiting `n`, in‐order goes next to whatever is “smallest” in `n`’s right subtree—i.e. you step into the right child, then keep going left until you can’t.

2. **Node Doesn’t Have a Right Child**

   * If `n.right` is `null`, then `n` has no subtree after it on the right. In in‐order terms, you must now backtrack to an ancestor—specifically, you ascend via parent pointers until you move **up** from a left child to its parent. That parent is the first node you encounter that hasn’t yet been “visited” (in in‐order) but whose left subtree (which includes `n`) you already finished.
   * Concretely, start at `current = n`, look at `parent = current.parent`.

     * If `current` was `parent.left`, that means the parent is the next node to visit in‐order. So you stop and return that `parent`.
     * If `current` was `parent.right`, you keep going up: `current = parent; parent = parent.parent;` until you either find a situation where `current == parent.left` or you run out of parents (i.e. `parent == null`). If you reach `null`, there is no successor.

Combining these two rules covers all nodes in the tree.

---

## Detailed Step‐by‐Step Algorithm

Suppose you have a class:

```java
static class BinaryTree {
  int value;
  BinaryTree left;
  BinaryTree right;
  BinaryTree parent;
  BinaryTree(int value) { this.value = value; }
}
```

And you are given a pointer `node` whose in‐order successor you want. Do the following:

1. **If `node.right != null`:**

   * Return `getLeftmostChild(node.right)`.
   * That helper simply descends as far left as it can:

     ```java
     private static BinaryTree getLeftmostChild(BinaryTree n) {
       while (n.left != null) {
         n = n.left;
       }
       return n;
     }
     ```

2. **Otherwise (no right child):**

   * Return `getRightAncestor(node)`.
   * That helper climbs up until you find a parent where you came from its left child:

     ```java
     private static BinaryTree getRightAncestor(BinaryTree node) {
       BinaryTree current = node;
       BinaryTree parent  = node.parent;
       // Keep climbing until either parent is null or we come from the left
       while (parent != null && parent.right == current) {
         current = parent;
         parent = parent.parent;
       }
       // Now either parent == null (no successor) or we arrived from parent.left
       return parent;
     }
     ```

3. **Putting it together:**

   ```java
   public static BinaryTree findSuccessor(BinaryTree node) {
     if (node == null) return null;
     // Case 1: If there is a right subtree, the successor is its leftmost node
     if (node.right != null) {
       return getLeftmostChild(node.right);
     }
     // Case 2: No right subtree—climb up until you find a parent that treats you as its left child
     return getRightAncestor(node);
   }
   ```

* **Why this works:**

  * **Right child case:** In‐order says “after visiting this node, go visit its right subtree starting from the leftmost.”
  * **No right child case:** In‐order says “I’ve finished both left subtree and current node, so go up until you can go up from a left subtree—because that parent is the next node in line.”

* **Time Complexity:** O(h) in the worst case, because you either:

  * Follow a chain of left pointers down from `node.right` (height ≤ h), or
  * Follow a chain of parent pointers up from `node` (height ≤ h).

* **Space Complexity:** O(1) extra, since you only use a few pointer variables (no recursion or extra data structures).

---

## Full Java Example

```java
package medium.binarytrees;

public class InOrderSuccessor {
  
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;
    BinaryTree parent;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
      this.parent = null;
    }
  }

  // 1) Public method that returns the in-order successor (or null if none)
  public static BinaryTree findSuccessor(BinaryTree node) {
    if (node == null) {
      return null;
    }
    // Case 1: Node has a right child
    if (node.right != null) {
      return getLeftmostChild(node.right);
    }
    // Case 2: No right child — climb up to find the first parent where node is in left subtree
    return getRightAncestor(node);
  }

  // Helper to find the leftmost node in a subtree
  private static BinaryTree getLeftmostChild(BinaryTree node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  // Helper to climb up until you find a node that is a left child of its parent
  private static BinaryTree getRightAncestor(BinaryTree node) {
    BinaryTree current = node;
    BinaryTree parent  = node.parent;

    // Keep climbing while current is the right child of its parent
    while (parent != null && parent.right == current) {
      current = parent;
      parent = parent.parent;
    }
    // Now parent is either null (no successor) or current is parent.left
    return parent;
  }

  public static void main(String[] args) {
    /*
       Build this sample tree (not necessarily a BST, but any binary tree with parent pointers):
             1
            / \
           2   3
          / \
         4   5
        /
       6

       In-order order: [6, 4, 2, 5, 1, 3]
       Successor of 5 is 1, of 1 is 3, of 3 is null, etc.
    */

    BinaryTree root = new BinaryTree(1);
    BinaryTree node2 = new BinaryTree(2);
    BinaryTree node3 = new BinaryTree(3);
    BinaryTree node4 = new BinaryTree(4);
    BinaryTree node5 = new BinaryTree(5);
    BinaryTree node6 = new BinaryTree(6);

    root.left = node2;
    root.right = node3;
    node2.left = node4;
    node2.right = node5;
    node4.left = node6;

    // Set parent pointers
    node2.parent = root;
    node3.parent = root;
    node4.parent = node2;
    node5.parent = node2;
    node6.parent = node4;

    // Example: find the successor of node5 (value = 5)
    BinaryTree succ = findSuccessor(node5);
    if (succ != null) {
      System.out.println("Successor of 5 is: " + succ.value);
      // Expected: 1
    } else {
      System.out.println("No successor.");
    }

    // Another example: successor of node1 (value = 1) should be 3
    BinaryTree succOf1 = findSuccessor(root);
    System.out.println("Successor of 1 is: " + (succOf1 != null ? succOf1.value : "null"));
    // Expected: 3

    // Successor of node3 should be null (3 is last in-order)
    BinaryTree succOf3 = findSuccessor(node3);
    System.out.println("Successor of 3 is: " + (succOf3 != null ? succOf3.value : "null"));
    // Expected: null
  }
}
```

---

## Explanation of Each Case

1. **Node Has a Right Child**

   * If `n.right` is not null, the next in‐order visit is **the leftmost node in that right subtree**. For example, if `n` = 5 and `n.right` subtree is:

     ```
       8
      / \
     6   9
     ```

     Then successor is 6 (the leftmost descendant of 8).

2. **Node Has No Right Child**

   * You must go **up** to a parent whose left subtree (somewhere) contains `n`. In in‐order, you’ve already visited `n` and its entire left subtree. The next node is the parent—provided `n` was that parent’s left child. If `n` was the parent’s right child, it means you’ve already visited both children of that parent, so you keep going up. Eventually:

     * Either you find a parent where you come from that parent’s left—so the parent is the next node to visit.
     * Or you reach the root and never find such a parent, meaning `n` was the very last node in in‐order, so there is no successor.

3. **When There Is No Successor**

   * If you climb from the right‐most node all the way up to the root without ever having been a left child, you end up with `parent == null`. Return `null`, meaning no successor.

---

## Complexity Recap

* **Time Complexity:** O(h), where *h* is the height of the tree. You either:

  * Descend left from `node.right`, which can take at most *h* steps, or
  * Ascend up via parent pointers, which can also take at most *h* steps.
* **Space Complexity:** O(1) extra, since you only use a few pointers (`current` and `parent`).

This method leverages the fact that each node knows its parent, making it unnecessary to store an explicit stack or to search from the root.

---

**Summary:**

* **If `node.right` exists** → successor = leftmost descendant of `node.right`.
* **Else** → climb parent pointers until you come from a left child; that parent is the successor.
* If you reach `null` at the top, there is no in‐order successor.
