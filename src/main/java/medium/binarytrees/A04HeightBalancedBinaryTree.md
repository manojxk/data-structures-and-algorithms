**Problem Restatement**
Determine whether a binary tree is height-balanced. A tree is height-balanced if, for every node, the heights of its left and right subtrees differ by at most 1.

---

## Optimized O(n) Solution (One Pass)

Instead of computing heights repeatedly (which leads to O(n²) in the naive approach), we can do a single post-order traversal that returns:

* The subtree’s height if it is balanced, or
* –1 if it is **not** balanced.

When a recursive call returns –1, we immediately bubble that “unbalanced” marker up without further work.

### Key Idea

```text
computeHeight(node):
  if node is null:
    return 0   // an empty tree has height 0 and is trivially balanced

  leftHeight = computeHeight(node.left)
  if leftHeight == -1:
    return -1  // left subtree is unbalanced → this tree is unbalanced

  rightHeight = computeHeight(node.right)
  if rightHeight == -1:
    return -1  // right subtree is unbalanced → this tree is unbalanced

  if |leftHeight – rightHeight| > 1:
    return -1  // current node is unbalanced

  return 1 + max(leftHeight, rightHeight)
    // otherwise, return the height of this subtree
```

* Whenever any subtree is discovered unbalanced (we see a -1), we propagate -1 up so that every ancestor also quickly knows “this entire branch is unbalanced.”
* If neither child is unbalanced, we check the height difference.

  * If it’s more than 1, we return -1.
  * Otherwise we return the normal height.

Finally, the tree is balanced if and only if `computeHeight(root)` is **not** -1.

---

### Java Implementation

```java
package medium.binarytrees;

public class BalancedBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }
  }

  /** 
   * Returns true if the tree is height-balanced, false otherwise.
   * Time:  O(n)   – each node is visited once
   * Space: O(h)   – recursion stack up to the tree’s height h
   */
  public boolean isBalanced(TreeNode root) {
    // If computeHeight returns -1, the tree isn’t balanced
    return computeHeight(root) != -1;
  }

  /**
   * Returns the height of the subtree if balanced, or -1 if unbalanced.
   * Height is defined as (# edges on longest path from this node down to a leaf).
   */
  private int computeHeight(TreeNode node) {
    if (node == null) {
      return 0; // An empty tree has height 0
    }

    // 1) Recursively compute left subtree height
    int leftHeight = computeHeight(node.left);
    if (leftHeight == -1) {
      // Left subtree is already unbalanced → propagate -1 up
      return -1;
    }

    // 2) Recursively compute right subtree height
    int rightHeight = computeHeight(node.right);
    if (rightHeight == -1) {
      // Right subtree is already unbalanced → propagate -1 up
      return -1;
    }

    // 3) Check current node’s balance condition
    if (Math.abs(leftHeight - rightHeight) > 1) {
      // This node’s subtrees differ by more than 1 → unbalanced
      return -1;
    }

    // 4) Otherwise, return this subtree’s height
    return 1 + Math.max(leftHeight, rightHeight);
  }

  // (Optional) Example usage
  public static void main(String[] args) {
    /*
         Example Tree:
               1
              / \
             2   3
            /
           4
      This tree is balanced (left subtree height=2, right=1).
    */
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);

    BalancedBinaryTree solver = new BalancedBinaryTree();
    System.out.println(solver.isBalanced(root)); // true

    /*
         Unbalanced example:
               1
              /
             2
            /
           3
      Here, left subtree height=2 and right=0, difference=2 > 1.
    */
    TreeNode root2 = new TreeNode(1);
    root2.left = new TreeNode(2);
    root2.left.left = new TreeNode(3);
    System.out.println(solver.isBalanced(root2)); // false
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**
  Each node is processed exactly once. At each node, we do O(1) work (check heights and compute `max`). Therefore **O(n)** where n = number of nodes.

* **Space Complexity:**
  The recursion stack depth is proportional to the tree’s height **h**. In the worst case (completely skewed), **h = n**, so O(n). In a balanced tree, **h = O(log n)**.

This one‐pass approach avoids the redundant height‐computations of the naive O(n²) solution and detects imbalance as soon as it occurs, returning –1 up the call chain.
