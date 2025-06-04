Below is a step-by-step explanation of how the “Repair BST” solution works, followed by a brief complexity analysis.

---

## Problem Restatement

A Binary Search Tree (BST) initially had all nodes in correct order, but exactly two of its nodes were swapped by mistake. As a result, the in-order traversal of the tree is no longer sorted. You must restore (“recover”) the tree by swapping those two nodes back—**without** changing the tree’s structure and using only constant extra space (aside from the recursion stack).

---

## High-Level Idea

1. **In-order of a correct BST is sorted**.
2. If exactly two nodes are swapped, an in-order walk will reveal exactly two anomalies (inversions) in the otherwise ascending sequence.
3. By doing a single in-order traversal, we can detect those two misplaced nodes:

   * The first time we see a node whose value is less than its predecessor, that predecessor is the “first wrong” node.
   * The second (or next) time we see a node out of order, that node is the “second wrong” node.
4. Finally, swap their values back, and the tree is repaired.

---

## Code Walkthrough

```java
public class A03RepairBST {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int v) { this.val = v; }
  }

  // Pointers to keep track of the two nodes that must be swapped
  TreeNode firstNode = null;
  TreeNode secondNode = null;

  // Keeps track of the previously visited node during in-order traversal
  TreeNode prevNode = new TreeNode(Integer.MIN_VALUE);

  /** 
   * Main entry: fixes the BST by identifying and swapping the two misplaced nodes.
   */
  public void recoverTree(TreeNode root) {
    // 1) Traverse the tree inorder, populating firstNode & secondNode when anomalies appear
    traverseInOrder(root);

    // 2) Once both are found, swap their values
    if (firstNode != null && secondNode != null) {
      int tmp = firstNode.val;
      firstNode.val = secondNode.val;
      secondNode.val = tmp;
    }
  }

  /**
   * Recursively performs in-order traversal (left → node → right). 
   * Whenever we spot a “prevNode.val > current.val,” we mark the involved nodes.
   */
  private void traverseInOrder(TreeNode root) {
    if (root == null) return;

    // Go as far left as possible
    traverseInOrder(root.left);

    // “Visit” phase: compare prevNode and current root
    if (prevNode.val > root.val) {
      // If it’s the first inverted pair, record prevNode as firstNode
      if (firstNode == null) {
        firstNode = prevNode;
      }
      // Always record the current node as secondNode (might be overwritten once)
      secondNode = root;
    }
    // Update prevNode to the current node
    prevNode = root;

    // Then explore right subtree
    traverseInOrder(root.right);
  }

  // Utility to print in-order (for demonstration)
  public static void printInOrder(TreeNode root) {
    if (root == null) return;
    printInOrder(root.left);
    System.out.print(root.val + " ");
    printInOrder(root.right);
  }

  public static void main(String[] args) {
    // Build example: [3,1,4,null,null,2] where “2” and “4” were swapped
    //
    //     3
    //    / \
    //   1   4
    //      /
    //     2 
    //
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(1);
    root.right = new TreeNode(4);
    root.right.left = new TreeNode(2);

    System.out.print("Before: ");
    printInOrder(root); // prints: 1 3 2 4  (not sorted)

    A03RepairBST solver = new A03RepairBST();
    solver.recoverTree(root);

    System.out.print("\nAfter:  ");
    printInOrder(root); // prints: 1 2 3 4  (sorted again)
  }
}
```

### Step-By-Step

1. **Initialize**

   ```java
   TreeNode firstNode  = null;
   TreeNode secondNode = null;
   TreeNode prevNode   = new TreeNode(Integer.MIN_VALUE);
   ```

   * `firstNode` and `secondNode` will eventually point to the two swapped nodes.
   * `prevNode` starts at `Integer.MIN_VALUE` so that the very first comparison never triggers a false positive.

2. **In-order Traversal**

   * We call `traverseInOrder(root)`.
   * As we recurse, we always go “left → process current → right.”
   * Whenever we “process current,” we compare:

     ```java
     if (prevNode.val > current.val) { ... }
     ```

     That indicates we just saw an inversion in what should be ascending order.

3. **Detecting the Two Misplaced Nodes**

   * The **first** time `prevNode.val > current.val` occurs, we set:

     ```java
     if (firstNode == null) {
       firstNode = prevNode;
     }
     secondNode = current;
     ```

     In other words:

     * **`firstNode = prevNode`** (the larger node that should’ve come later),
     * **`secondNode = current`** (the smaller node that should’ve come earlier).
   * If a second inversion appears later, we do **not** overwrite `firstNode` (it’s already set), but we do update:

     ```java
     secondNode = current;
     ```

     to point to the newly found “current” that is out of place.
   * By the end of the in-order pass, `firstNode` will point to the first swapped node, and `secondNode` to the second swapped node.

4. **Swap Their Values**

   ```java
   if (firstNode != null && secondNode != null) {
     int tmp = firstNode.val;
     firstNode.val = secondNode.val;
     secondNode.val = tmp;
   }
   ```

   * Swapping their `val` fields fixes the BST. The pointers and tree structure remain exactly the same.

---

## Why This Finds Exactly Two Nodes

* In a correct BST, an in-order walk visits node values in strictly increasing order. If two nodes are swapped, the in-order sequence will have one of two patterns:

  1. **Adjacent swap** (e.g. … 2, 4, 3, 5, …) → you see exactly *one* inversion (4 > 3).

     * In that case, you catch it when `prevNode = 4` and `current = 3`. You set `firstNode = 4`, `secondNode = 3`.
  2. **Non-adjacent swap** (e.g. … 7, 3, 5, 4, 10, …) → there will be *two* inversions:

     * First when `prevNode = 7` and `current = 3` (set `firstNode=7, secondNode=3`).
     * Second when `prevNode = 5` and `current = 4` (update `secondNode=4`).
     * By the end, `firstNode=7` and `secondNode=4`, which are precisely the two that must be swapped back.

After swapping those two values, the in-order sequence is once again sorted.

---

## Complexity

* **Time Complexity**:

  * The `traverseInOrder` method visits each node exactly once. Every visit does O(1) work (a constant‐time comparison and pointer updates).
  * Therefore, the overall time is **O(n)**, where n is the total number of nodes.

* **Space Complexity**:

  * We do not allocate any data structures that grow with n. We only keep a few pointers (`firstNode`, `secondNode`, `prevNode`).
  * The only extra space is the recursion stack used by in-order traversal, which in the worst case is O(h), where h is the height of the tree.

    * In a balanced BST, h = O(log n).
    * In the worst (skewed) case, h = O(n).
  * Hence, space is **O(h)** due solely to recursion. (We do *not* use any arrays or lists of size n.)

---

### Recap

1. Perform a single in-order traversal.
2. Whenever you see `prevNode.val > current.val`, record the offending nodes as `firstNode` (the larger one) and `secondNode` (the smaller one).
3. After traversal, swap `firstNode.val` with `secondNode.val`.
4. Done—the BST is restored.

That completes the in-place repair of a BST with exactly two swapped nodes, in **O(n)** time and **O(h)** space.
