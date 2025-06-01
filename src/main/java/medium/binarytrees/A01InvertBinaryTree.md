**Problem Restatement**
You have a binary tree (not necessarily a BST) where each node has a `value`, a `left` child, and a `right` child. “Inverting” (or “mirroring”) the tree means swapping every node’s left and right pointers throughout the entire tree. For example, given:

```
       1
      / \
     2   3
    / \ / \
   4  5 6  7
  / \
 8   9
```

After inversion, it should become:

```
       1
      / \
     3   2
    / \ / \
   7  6 5  4
         / \
        9   8
```

We’ll show two common ways to do this in Java:

1. A **recursive** (depth‐first) approach.
2. An **iterative** (breadth‐first) approach using a queue.

---

## 1. Recursive Solution (Depth‐First)

The simplest way is to walk the tree in any order (pre‐order is fine), and at each node just swap its left and right children, then recurse on each child.

### Steps (for each node `root`)

1. **Base case**: If `root` is `null`, do nothing and return immediately.
2. **Swap**: Temporarily store `root.left` in a variable, set `root.left = root.right`, and `root.right = temp`.
3. **Recurse**: Call the same function on `root.left` (which was originally `root.right`) and on `root.right` (which was originally `root.left`).

By the time you return from those recursive calls, the entire subtree underneath `root` will be fully inverted.

### Java Code

```java
package medium.binarytrees;

public class InvertBinaryTree {

  // Definition of a binary‐tree node
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  /**
   * Inverts a binary tree by recursively swapping left/right children at each node.
   *
   * Time Complexity:  O(n)    — we visit each of the n nodes exactly once.
   * Space Complexity: O(h)    — the recursion stack can go as deep as the tree's height h.
   *                           In the worst case (a completely skewed tree), h = n.
   */
  public static void invertBinaryTree(BinaryTree root) {
    // 1) Base case: nothing to do if node is null
    if (root == null) {
      return;
    }

    // 2) Swap left and right children
    BinaryTree temp = root.left;
    root.left = root.right;
    root.right = temp;

    // 3) Recurse on both children
    invertBinaryTree(root.left);
    invertBinaryTree(root.right);
  }

  // (Optional) Helper to print the tree in‐order for verification
  public static void printInOrder(BinaryTree node) {
    if (node == null) return;
    printInOrder(node.left);
    System.out.print(node.value + " ");
    printInOrder(node.right);
  }

  public static void main(String[] args) {
    // Build the example tree:
    //
    //        1
    //       / \
    //      2   3
    //     / \ / \
    //    4  5 6  7
    //   / \
    //  8   9
    //
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right = new BinaryTree(7);
    root.left.left.left = new BinaryTree(8);
    root.left.left.right = new BinaryTree(9);

    // Invert recursively
    invertBinaryTree(root);

    // Now the tree is inverted. In-order traversal should print:
    // (the original tree’s in‐order was 8 4 9 2 5 1 6 3 7)
    // after inversion it will be: 7 3 6 1 5 2 9 4 8
    printInOrder(root);
    // Output to console: 7 3 6 1 5 2 9 4 8
  }
}
```

---

## 2. Iterative Solution (Breadth‐First Using a Queue)

If you prefer not to use recursion, you can invert level by level. Each time you dequeue a node, swap its children and then enqueue them (if non‐null). Continue until the queue is empty.

### Steps

1. If `root` is `null`, return immediately.
2. Create a `Queue<BinaryTree>` and add `root`.
3. While the queue is not empty:

   * Dequeue the front node `current`.
   * **Swap** its children just as before:

     ```java
     BinaryTree temp = current.left;
     current.left = current.right;
     current.right = temp;
     ```
   * If `current.left != null`, enqueue `current.left`.
   * If `current.right != null`, enqueue `current.right`.
4. When the queue is empty, you’ve visited and swapped every node exactly once.

### Java Code

```java
package medium.binarytrees;

import java.util.LinkedList;
import java.util.Queue;

public class InvertBinaryTree {

  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  /**
   * Inverts a binary tree iteratively using a queue (level‐order traversal).
   *
   * Time Complexity:  O(n)    — each of the n nodes is enqueued and dequeued once.
   * Space Complexity: O(n)    — in the worst case, the queue might hold up to n/2 nodes.
   */
  public static void invertBinaryTreeIterative(BinaryTree root) {
    // 1) If tree is empty, do nothing
    if (root == null) return;

    // 2) Set up a queue for BFS
    Queue<BinaryTree> queue = new LinkedList<>();
    queue.add(root);

    // 3) Process nodes in FIFO order
    while (!queue.isEmpty()) {
      BinaryTree current = queue.poll();

      // Swap its left and right children
      BinaryTree temp = current.left;
      current.left = current.right;
      current.right = temp;

      // Enqueue children (after swap) if they exist
      if (current.left != null) queue.add(current.left);
      if (current.right != null) queue.add(current.right);
    }
  }

  // (Optional) In-order print for verification
  public static void printInOrder(BinaryTree node) {
    if (node == null) return;
    printInOrder(node.left);
    System.out.print(node.value + " ");
    printInOrder(node.right);
  }

  public static void main(String[] args) {
    // Build the example tree:
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right = new BinaryTree(7);
    root.left.left.left = new BinaryTree(8);
    root.left.left.right = new BinaryTree(9);

    // Invert iteratively
    invertBinaryTreeIterative(root);

    // Print in-order: should match the recursive example’s result
    printInOrder(root);
    // Output: 7 3 6 1 5 2 9 4 8
  }
}
```

---

## Complexity Comparison

| Method    | Time Complexity | Space Complexity                      | Notes                                           |
| --------- | --------------- | ------------------------------------- | ----------------------------------------------- |
| Recursive | O(n)            | O(h) (recursion stack)                | h = tree height; worst-case skewed → O(n)       |
| Iterative | O(n)            | O(n) (queue may hold up to n/2 nodes) | Guaranteed no recursion; uses extra queue space |

* **Time Complexity (both):** O(n), because each node is processed exactly once (to swap its children).
* **Space Complexity (recursive):** O(h), where h is the height of the tree. In a balanced tree, h = O(log n); a completely skewed tree has h = O(n).
* **Space Complexity (iterative):** O(n) in the worst case, since at the widest level of the tree, up to n/2 nodes can reside in the queue.

Choose **recursive** if you’re comfortable with a call stack and want simpler code. Choose **iterative** if you want to avoid recursion (e.g., if the tree might be extremely deep and risk a stack overflow).

---

**Summary:**

* **Recursive approach**: swap left/right at each node, then recurse on children—very direct and easy to read.
* **Iterative approach**: use a queue to do a level-order traversal, swapping children at each step—avoids recursion but requires extra queue space.

Both invert the tree in **O(n)** time.
