**Problem Restatement**
Given the root of a binary tree, return the **diameter**—that is, the length (in edges) of the longest path between any two nodes.  A path’s length is defined by the number of edges it contains.  This path may or may not pass through the root.

---

## Why the Naïve (O(n²)) Solution Is Too Slow

A straightforward approach is:

1. Define a helper `height(node)` that returns the (node‐to‐leaf) height in terms of **node count**.
2. For each node `root`, compute:

   * `lheight = height(root.left)`
   * `rheight = height(root.right)`
   * The “longest through this node” in **node‐count** terms is `lheight + rheight + 1`.
   * Recursively compute the diameter of `root.left` and `root.right` to handle the case where the longest path doesn’t pass through the current node.
   * Return the maximum of those three.
3. Subtract 1 from that “node‐count” result to get the **edge count**.

```java
public int height(TreeNode root) {
  if (root == null) return 0;
  return 1 + Math.max(height(root.left), height(root.right));
}

public int diameter(TreeNode root) {
  if (root == null) return 0;
  int lheight = height(root.left);
  int rheight = height(root.right);
  int cross = lheight + rheight + 1;          // node‐count of the path through root
  int leftDia  = diameter(root.left);
  int rightDia = diameter(root.right);
  return Math.max(cross, Math.max(leftDia, rightDia));
}

public int diameterOfBinaryTree(TreeNode root) {
  return diameter(root) - 1;  // convert node‐count to edge‐count
}
```

* **Time Complexity:** O(n²) in the worst case (e.g., a completely skewed tree) because each call to `diameter(…)` calls `height(…)` on every node, and `height(…)` itself can be O(n).
* **Space Complexity:** O(h) for the recursion stack, where h is the height of the tree.

For large or unbalanced trees, O(n²) is too slow. We can do **O(n)** instead.

---

## Optimal O(n) Solution: Single‐Pass Height + Diameter

To achieve O(n), we perform **one depth‐first traversal** that, for each node, computes:

* The **height** of that node’s subtree (in edge‐count),
* While simultaneously updating a global **maximum diameter** in edge‐count.

We define a helper `dfs(node)` that returns the height (in edges) of `node`. Within that same call, we compute:

* `leftHeight = dfs(node.left)`
* `rightHeight = dfs(node.right)`
* The longest path **through** `node` in **edges** is `leftHeight + rightHeight`.  (Because if you measure height in edges, the path from a left‐subtree leaf up through the node and down to a right‐subtree leaf has `leftHeight` edges up plus `rightHeight` edges down.)
* Keep a class‐level variable `maxDiameter` and update:

  ```
  maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
  ```
* Then return `1 + Math.max(leftHeight, rightHeight)` as the height (in edges) of this subtree (one extra edge from `node` down to its taller child).

By computing both height and diameter in one DFS, each node is visited once, yielding **O(n)** time. The recursion stack uses **O(h)** space.

---

### Java Code

```java
package medium.binarytrees;

public class BinaryTreeDiameter {
  // Definition for a binary tree node.
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  // Class‐level variable to track the maximum diameter found so far (in edges).
  private int maxDiameter = 0;

  /**
   * Returns the diameter of the binary tree (number of edges in the longest path).
   * Time Complexity:  O(n)       (each node visited once)
   * Space Complexity: O(h)      (recursion stack, where h = tree height)
   */
  public int diameterOfBinaryTree(TreeNode root) {
    maxDiameter = 0;
    computeHeight(root);
    return maxDiameter;
  }

  /**
   * Computes the height of the subtree rooted at `node` in terms of edges.
   * While doing so, updates maxDiameter = max(maxDiameter, leftHeight + rightHeight).
   *
   * @param node the current subtree root
   * @return the height (in edges) of this subtree
   */
  private int computeHeight(TreeNode node) {
    if (node == null) {
      return 0; // height of an empty subtree is 0 edges
    }

    // Recursively compute left and right subtree heights (in edges)
    int leftHeight  = computeHeight(node.left);
    int rightHeight = computeHeight(node.right);

    // The longest path through this node has leftHeight + rightHeight edges
    int diameterThroughNode = leftHeight + rightHeight;
    if (diameterThroughNode > maxDiameter) {
      maxDiameter = diameterThroughNode;
    }

    // Height of this subtree is 1 edge + max of child heights
    return 1 + Math.max(leftHeight, rightHeight);
  }

  // Example usage
  public static void main(String[] args) {
    /*
         Construct this binary tree:
                 1
                / \
               3   2
              / \   \
             7   4   5
            /         \
           8           6
           (… and so on …)
     */
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(3);
    root.right = new TreeNode(2);
    root.left.left = new TreeNode(7);
    root.left.right = new TreeNode(4);
    root.right.right = new TreeNode(5);
    root.left.left.left = new TreeNode(8);
    root.right.right.right = new TreeNode(6);

    BinaryTreeDiameter solution = new BinaryTreeDiameter();
    int diameter = solution.diameterOfBinaryTree(root);
    System.out.println("Diameter of the binary tree is: " + diameter);
    // This prints the number of edges in the longest path. 
    // For example structure, if the longest path is [8→7→3→1→2→5→6], that's 6 edges → prints 6.
  }
}
```

---

## Why This Is O(n)

* **Each node** calls `computeHeight(node)` exactly once.

* In that call, we do two recursive calls (for left and right), compute one sum (`leftHeight + rightHeight`), update `maxDiameter` in O(1), and return a height in O(1).

* No extra loops or repeated height computations.
  → **Overall time:** O(n).

* The recursion stack grows to the tree’s height **h** in the worst case.
  → **Space:** O(h).

In a balanced tree, **h** is O(log n). In a degenerate (linked‐list‐shaped) tree, **h** can be O(n), but we still only use O(n) space, not O(n²).

---

### Quick Summary

* The **naïve** “compute height inside diameter” approach can degrade to **O(n²)**.
* The **optimized** “single DFS that returns height while updating a global diameter” is **O(n) time, O(h) space**.

Use the optimized method whenever performance on large or unbalanced trees matters.
