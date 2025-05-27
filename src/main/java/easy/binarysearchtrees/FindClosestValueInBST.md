**Problem Explanation**

You’re given a **Binary Search Tree (BST)** and a **target** integer. You must return the value in the BST that is **closest** to the target. “Closest” means minimizing the absolute difference $\lvert \text{node.value} - \text{target}\rvert$. You can assume exactly one such “closest” value exists.

---

## 1. Brute-Force Solution

**Idea:** Traverse **every** node in the tree (e.g., with a simple DFS), keep track of the node whose value has the smallest absolute difference to the target.

* **Time:** You visit all $n$ nodes → **O(n)**
* **Space:** The recursion stack (in the worst case of an unbalanced tree) could be **O(n)**; for a balanced tree it’s **O($\log n$)**.

---

## 2. Optimized Solution Using BST Property

Because it’s a BST, for any node:

* If the target is **less** than `node.value`, any closer value must lie in the **left** subtree.
* If the target is **greater**, any closer value must lie in the **right** subtree.

You can thus **walk down** the tree from the root, at each step:

1. Update your best “closest so far” if the current node is nearer.
2. Decide to go **left** or **right** based on comparing `target` to `node.value`.

This way you only traverse a path of length **height** $h$, not the entire tree.

* **Time:** O(h) — in a balanced BST, **O($\log n$)**; worst case (linked-list shape), **O(n)**.
* **Space (iterative):** O(1) extra.
* **Space (recursive):** O(h) call stack.

---

## Java Implementation

```java
package easy.binarysearchtrees;

public class FindClosestValueInBST {

  // Definition of a BST node
  static class BST {
    int value;
    BST left;
    BST right;

    BST(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  /**
   * Brute-Force: DFS traversal of every node, tracking the closest value seen.
   *
   * Time:  O(n)
   * Space: O(h) recursion stack (h = tree height)
   */
  public static int findClosestValueBruteForce(BST node, int target) {
    return findClosestHelper(node, target, node.value);
  }

  private static int findClosestHelper(BST node, int target, int closest) {
    if (node == null) {
      return closest;
    }
    // Update closest if this node is nearer
    if (Math.abs(target - node.value) < Math.abs(target - closest)) {
      closest = node.value;
    }
    // Recurse both subtrees
    closest = findClosestHelper(node.left,  target, closest);
    closest = findClosestHelper(node.right, target, closest);
    return closest;
  }

  /**
   * Optimized: Use BST property to walk down once.
   *
   * Time:  O(h)   (h = tree height)
   * Space: O(1)
   */
  public static int findClosestValueInBst(BST root, int target) {
    int closest = root.value;
    BST current = root;

    while (current != null) {
      // Update closest if this node is nearer
      if (Math.abs(target - current.value) < Math.abs(target - closest)) {
        closest = current.value;
      }
      // Decide which way to go
      if (target < current.value) {
        current = current.left;
      } else if (target > current.value) {
        current = current.right;
      } else {
        // Exact match
        break;
      }
    }

    return closest;
  }

  /**
   * Recursive variant of the optimized approach.
   *
   * Time:  O(h)
   * Space: O(h) (call stack)
   */
  public static int findClosestValueInBstRecursive(BST node, int target, int closest) {
    if (node == null) {
      return closest;
    }
    if (Math.abs(target - node.value) < Math.abs(target - closest)) {
      closest = node.value;
    }
    if (target < node.value) {
      return findClosestValueInBstRecursive(node.left,  target, closest);
    } else if (target > node.value) {
      return findClosestValueInBstRecursive(node.right, target, closest);
    } else {
      return closest; // exact match
    }
  }

  // Example usage and testing
  public static void main(String[] args) {
    // Construct the example BST:
    //          10
    //        /    \
    //       5      15
    //      / \    /  \
    //     2   5 13   22
    //    /        \
    //   1         14
    BST root = new BST(10);
    root.left = new BST(5);
    root.right = new BST(15);
    root.left.left = new BST(2);
    root.left.right = new BST(5);
    root.left.left.left = new BST(1);
    root.right.left = new BST(13);
    root.right.left.right = new BST(14);
    root.right.right = new BST(22);

    int target = 12;

    System.out.println("Brute-Force Closest:  "
        + findClosestValueBruteForce(root, target)); // 13
    System.out.println("Iterative Closest:   "
        + findClosestValueInBst(root, target));      // 13
    System.out.println("Recursive Closest:   "
        + findClosestValueInBstRecursive(root, target, root.value)); // 13
  }
}
```

---

## Complexity Summary

| Approach                  | Time Complexity | Space Complexity       |
| ------------------------- | --------------- | ---------------------- |
| Brute-Force DFS           | O(n)            | O(h) (recursion stack) |
| Optimized (Iterative) BST | O(h)            | O(1)                   |
| Optimized (Recursive) BST | O(h)            | O(h) (call stack)      |

* **n** = total number of nodes
* **h** = height of the BST (≈ log n if balanced, up to n if skewed)
