**Problem Explanation**
You have a binary tree whose nodes each contain an integer, a left child, and a right child. You need to determine whether this tree satisfies **Binary Search Tree (BST)** properties:

1. Every node’s value is **greater than** all values in its **left** subtree.
2. Every node’s value is **less than or equal to** all values in its **right** subtree.
3. Both left and right subtrees must themselves be valid BSTs.

> **Example:**
>
> ```
>         10
>        /  \
>       5    15
>      / \   / \
>     2   5 13 22
>    /        \
>   1         14
> ```
>
> This tree is valid:
>
> * All nodes in the left subtree of 10 are < 10.
> * All nodes in the right subtree of 10 are ≥ 10.
> * Similarly, at each node (e.g. 15’s left child is 13 < 15, right child is 22 ≥ 15), etc.

---

## Approach: Recursive Range‐Checking (O(n) Time | O(h) Space)

A common way to verify BST validity is to ensure **every** node’s value falls within a valid range as you recurse down the tree:

1. Start at the root with the range $(-\infty, +\infty)$.
2. When you go **left** from a node with value `v`, you tighten the **upper bound** to `v – 1` (or simply `v` if we allow equality on the right).
3. When you go **right** from a node with value `v`, you tighten the **lower bound** to `v`.
4. At each node, check `minValue < node.value ≤ maxValue`. If it ever fails, the tree is not a valid BST. Otherwise, recurse on both children with their updated bounds.
5. If you reach a `null` node, return `true` (an empty subtree is valid).

Because every node is visited exactly once and checks only O(1) work, the overall time is **O(n)**. The recursion stack depth is at most the tree’s height `h`, giving **O(h)** space.

---

## Detailed Steps

```text
validateBST(node, minValue, maxValue):
  1. If node is null:
       return true
  2. If node.value ≤ minValue  OR  node.value > maxValue:
       return false
  3. Return (
       validateBST(node.left,  minValue,      node.value)  AND
       validateBST(node.right, node.value, maxValue)
     )
```

* **minValue** and **maxValue** are inclusive/exclusive bounds.

  * We require `minValue < node.value ≤ maxValue`.
  * By convention, we let the right side be “≤ maxValue” so that duplicates can only appear on the right subtree.

* **Initial call:**

  ```java
  validateBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  ```

  This sets no real bounds at the top.

* **When going left:**
  You require every node in the left subtree to be **strictly less than** `node.value`, so pass `(minValue, node.value - 1)` or `(minValue, node.value)` if allowing equality on the right.

* **When going right:**
  You require every node in the right subtree to be **greater than or equal to** `node.value`, so pass `(node.value, maxValue)`.

---

## Java Implementation

```java
package medium.binarysearchtrees;

public class ValidateBST {

  // Definition of a tree node
  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  /**
   * Initiates the recursive validation with infinite bounds.
   *
   * @param root root of the tree
   * @return true if the tree is a valid BST, false otherwise
   */
  public static boolean validateBSTOptimized(TreeNode root) {
    // Use the entire integer range as initial bounds
    return validateBSTHelper(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Recursively checks whether each node’s value falls within (minValue, maxValue].
   *
   * @param node     the current node
   * @param minValue the lower bound (exclusive)
   * @param maxValue the upper bound (inclusive)
   * @return true if the subtree rooted at node is a valid BST within the given bounds
   */
  private static boolean validateBSTHelper(
      TreeNode node, int minValue, int maxValue
  ) {
    // An empty subtree is valid
    if (node == null) {
      return true;
    }

    // If the current node violates the range, it’s not a BST
    if (node.value <= minValue || node.value > maxValue) {
      return false;
    }

    // Recurse on left: upper bound becomes node.value - 1 (or node.value if allowing duplicates on right)
    // Recurse on right: lower bound becomes node.value
    return validateBSTHelper(node.left,  minValue, node.value)
        && validateBSTHelper(node.right, node.value, maxValue);
  }

  // Example usage and test
  public static void main(String[] args) {
    // Construct the example tree:
    //        10
    //       /  \
    //      5    15
    //     / \   / \
    //    2   5 13 22
    //   /        \
    //  1         14

    TreeNode root = new TreeNode(10);
    root.left        = new TreeNode(5);
    root.right       = new TreeNode(15);
    root.left.left   = new TreeNode(2);
    root.left.right  = new TreeNode(5);
    root.right.left  = new TreeNode(13);
    root.right.right = new TreeNode(22);
    root.left.left.left  = new TreeNode(1);
    root.right.left.right = new TreeNode(14);

    System.out.println(validateBSTOptimized(root)); 
    // Expected output: true (the tree is a valid BST)
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**
  We visit **each node exactly once** and perform a constant‐time bound check. Therefore, **O(n)** where *n* = number of nodes.

* **Space Complexity:**
  The only additional space is the **call stack** during recursion, which can go as deep as the tree’s height `h`.

  * In a balanced BST, `h = O(log n)`.
  * In the worst‐case (skewed tree), `h = O(n)`.

Hence, **O(h)** space.

---

### Why Range‐Checking Works

At each node:

* All values in its left subtree must be **strictly less than** the node’s value;
* All values in its right subtree must be **greater than or equal to** the node’s value.

By passing down `(minValue, maxValue)` bounds that shrink appropriately whenever you go left or right, you ensure **every** node meets all ancestor constraints. If any node violates those bounds, the entire tree cannot be a valid BST.

This is a clean, recursive way to verify BST properties in a single O(n) pass.
