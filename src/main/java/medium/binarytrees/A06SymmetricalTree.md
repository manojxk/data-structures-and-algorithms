**Problem Restatement**
You have a binary tree (not necessarily a BST). You need to determine whether the tree is **symmetrical**—that is, whether its left and right subtrees are perfect “mirror images” of each other. Concretely, a tree is symmetric if:

* The root’s left and right children both exist (or both are null).
* The left subtree of the root is a mirror reflection of the right subtree.
* At every pair of corresponding nodes in those subtrees, the values match, and their own children are also mirrored.

For example, this tree is symmetric:

```
        1
       / \
      2   2
     / \ / \
    3  4 4  3
   / \      / \
  5   6    6   5
```

Here, the left side `[2 → (3,4) → (5,6)]` exactly mirrors the right side `[2 → (4,3) → ( … )]`, so `isSymmetrical = true`.

---

## Approach 1: Recursive Mirror‐Check (Depth‐First)

### Intuition

To check symmetry recursively, you compare two subtrees `tree1` and `tree2` to see if they are “mirror images.” Two null subtrees mirror each other. Otherwise:

1. The **current node values** must be equal: `tree1.value == tree2.value`.
2. The **left child** of `tree1` must mirror the **right child** of `tree2`
   **AND** the **right child** of `tree1` must mirror the **left child** of `tree2`.
3. Recurse on those pairs.

You begin by calling this helper on `root.left` and `root.right` of the entire tree. If at any point a mismatch or structure mismatch (one is null, the other is not) occurs, return false.

### Steps

1. If `root` is `null`, return `true` (an empty tree is trivially symmetric).

2. Else, call `isMirror(root.left, root.right)`:

   ```java
   private static boolean isMirror(BinaryTree tree1, BinaryTree tree2) {
     // If both are null, they mirror each other
     if (tree1 == null && tree2 == null) return true;
     // If exactly one is null, not symmetric
     if (tree1 == null || tree2 == null) return false;

     // Check current values match, and recurse on children in mirror fashion
     return (tree1.value == tree2.value)
         && isMirror(tree1.left, tree2.right)
         && isMirror(tree1.right, tree2.left);
   }
   ```

3. Return that boolean result.

### Java Code

```java
package medium.binarytrees;

public class SymmetricalBinaryTree {

  // Definition of a binary tree node
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left  = null;
      this.right = null;
    }
  }

  /**
   * Determines if a binary tree is symmetric (a mirror of itself).
   *
   * Time Complexity:  O(n), because we visit each node once in the worst case.
   * Space Complexity: O(h), where h is the tree's height (recursion stack).
   */
  public static boolean isSymmetrical(BinaryTree root) {
    if (root == null) {
      return true;  // An empty tree is symmetric
    }
    // Compare left and right subtrees
    return isMirror(root.left, root.right);
  }

  // Helper that checks if two subtrees are mirrors of each other
  private static boolean isMirror(BinaryTree tree1, BinaryTree tree2) {
    // Both null → they mirror
    if (tree1 == null && tree2 == null) return true;
    // Exactly one null → not symmetric
    if (tree1 == null || tree2 == null) return false;

    // Check current node values match, and recurse on children in mirror order
    return (tree1.value == tree2.value)
        && isMirror(tree1.left,  tree2.right)
        && isMirror(tree1.right, tree2.left);
  }

  public static void main(String[] args) {
    // Build the example tree:
    //
    //        1
    //       / \
    //      2   2
    //     / \ / \
    //    3  4 4  3
    //   / \      / \
    //  5   6    6   5
    //
    BinaryTree root = new BinaryTree(1);
    root.left  = new BinaryTree(2);
    root.right = new BinaryTree(2);
    root.left.left = new BinaryTree(3);
    root.left.right = new BinaryTree(4);
    root.right.left  = new BinaryTree(4);
    root.right.right = new BinaryTree(3);
    root.left.left.left  = new BinaryTree(5);
    root.left.left.right = new BinaryTree(6);
    root.right.right.left  = new BinaryTree(6);
    root.right.right.right = new BinaryTree(5);

    System.out.println(isSymmetrical(root));  // Should print: true
  }
}
```

---

## Approach 2: Iterative Mirror‐Check (Breadth‐First Using a Queue)

If you prefer not to use recursion, you can do a breadth‐first “pairwise comparison” with a queue:

1. If `root` is `null`, return `true`.
2. Create a queue of node‐pairs (`Queue<BinaryTree>`). Enqueue `root.left` and `root.right` as the initial pair.
3. While the queue is not empty, dequeue two nodes `(tree1, tree2)`:

   * If **both** are `null`, continue (that pair is symmetric).
   * If **exactly one** is `null`, return `false` (structure mismatch).
   * If their `value`s differ, return `false` (value mismatch).
   * Otherwise, enqueue these four children in mirror order:

     1. `tree1.left` and `tree2.right`
     2. `tree1.right` and `tree2.left`
4. If you finish processing all pairs without a mismatch, return `true`.

### Java Code

```java
package medium.binarytrees;

import java.util.LinkedList;
import java.util.Queue;

public class SymmetricalBinaryTree {

  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left  = null;
      this.right = null;
    }
  }

  /**
   * Iterative method to check symmetry using a queue of node‐pairs.
   *
   * Time Complexity:  O(n)    — each node is enqueued/dequeued at most once.
   * Space Complexity: O(n)    — the queue could hold up to O(n) nodes in the worst (full) level.
   */
  public static boolean isSymmetricalIterative(BinaryTree root) {
    if (root == null) {
      return true;
    }

    // Use a queue to store pairs of nodes to compare
    Queue<BinaryTree> queue = new LinkedList<>();
    queue.add(root.left);
    queue.add(root.right);

    while (!queue.isEmpty()) {
      BinaryTree tree1 = queue.poll();
      BinaryTree tree2 = queue.poll();

      // 1) Both null → symmetric at this pair
      if (tree1 == null && tree2 == null) {
        continue;
      }
      // 2) Exactly one null → not symmetric
      if (tree1 == null || tree2 == null) {
        return false;
      }
      // 3) Value mismatch → not symmetric
      if (tree1.value != tree2.value) {
        return false;
      }

      // 4) Enqueue children in mirror order
      queue.add(tree1.left);
      queue.add(tree2.right);
      queue.add(tree1.right);
      queue.add(tree2.left);
    }

    // If we never found a mismatch, the tree is symmetric
    return true;
  }

  public static void main(String[] args) {
    // Build the same example tree as above
    BinaryTree root = new BinaryTree(1);
    root.left  = new BinaryTree(2);
    root.right = new BinaryTree(2);
    root.left.left  = new BinaryTree(3);
    root.left.right = new BinaryTree(4);
    root.right.left  = new BinaryTree(4);
    root.right.right = new BinaryTree(3);
    root.left.left.left = new BinaryTree(5);
    root.left.left.right = new BinaryTree(6);
    root.right.right.left = new BinaryTree(6);
    root.right.right.right = new BinaryTree(5);

    System.out.println(isSymmetricalIterative(root));  // Should print: true
  }
}
```

---

## Complexity Comparison

| Approach               | Time Complexity | Space Complexity                     |
| ---------------------- | --------------- | ------------------------------------ |
| Recursive (DFS)        | O(n)            | O(h) recursion stack (height)        |
| Iterative (BFS, queue) | O(n)            | O(n) in the worst case for the queue |

* **Time Complexity (both):** You visit each tree node exactly once → **O(n)**.
* **Space Complexity (recursive):** The recursion stack depth is the height **h** of the tree. In the worst skewed case, **h = n**, but if the tree is balanced, **h = O(log n)**.
* **Space Complexity (iterative):** The queue might hold an entire level of the tree. In the worst case (a full binary tree), the bottom level can have \~n/2 nodes → **O(n)**.

---

### Summary

1. **Recursive Mirror Check**

   * Very straightforward: Recursively compare `(left.left vs right.right)` and `(left.right vs right.left)` at every step.
   * Uses **O(h)** stack space.

2. **Iterative Mirror Check**

   * Use a queue of node‐pairs. Enqueue children in mirror order, and compare values as you go.
   * Uses **O(n)** space in the worst case for the queue (but avoids recursion).

Either method will correctly return `true` for a symmetric tree and `false` otherwise.
