**Problem Explanation**
You have two binary trees, each node storing an integer `value`, plus pointers `left` and `right`. You want to **merge** them into a single tree. The rule is:

* If both trees have a node at the same “position,” the merged node’s value should be the **sum** of those two values.
* If only one tree has a node in that position, the merged tree simply “takes” that node unchanged.

For example:

```
Tree 1:          Tree 2:
    1                1
   / \              / \
  3   2            5   9
 / \                \  / \
7   4                2 7  6
```

After merging:

1. The root nodes overlap: 1 + 1 = 2.
2. Their left children overlap: 3 + 5 = 8.
3. Their right children overlap: 2 + 9 = 11.
4. For Tree 1’s left‐left: 7 (Tree 1) + null (Tree 2) = 7.
5. For Tree 1’s left‐right: 4 (Tree 1) + null (Tree 2) = 4.
6. For Tree 2’s left‐left: null (Tree 1) + 2 (Tree 2) = 2.
7. For Tree 2’s right‐left: null (Tree 1) + 7 (Tree 2) = 7.
8. For Tree 2’s right‐right: null (Tree 1) + 6 (Tree 2) = 6.

Resulting merged tree:

```
      2
     /  \
    8    11
   / \   / \
  9   4 7   6
```

(Notice that “9” on the far left comes from `7 (Tree 1) + 2 (Tree 2)` because Tree 2 had a node there that Tree 1 did not, and similarly for other leaf positions.)

---

## Approach 1: Recursive Merge (Depth‐First)

### Key Idea

* If **either** of the two corresponding nodes is `null`, return the other node as is.
* If **both** nodes exist, create/overwrite one node’s `value` with the sum of the two, then recursively merge their left children and their right children.

Recursion ensures each pair of overlapping nodes is handled in a top‐down fashion.

### Step‐by‐Step

1. **Base cases**

   * If `tree1` is `null`, return `tree2`.
   * If `tree2` is `null`, return `tree1`.
     This covers any situation where only one tree has a node at this position.

2. **Merge the current nodes**

   * Both `tree1` and `tree2` exist. Update `tree1.value` to `tree1.value + tree2.value`.

3. **Recurse on children**

   * `tree1.left = mergeTrees(tree1.left,  tree2.left)`
   * `tree1.right = mergeTrees(tree1.right, tree2.right)`

4. **Return** `tree1` as the root of the merged subtree.

Over the entire traversal, each pair of overlapping nodes is visited exactly once. If one side is missing, we immediately attach the existing subtree without further recursion on that side.

### Java Code

```java
package medium.binarytrees;

public class MergeBinaryTrees {

  // Definition for a binary‐tree node
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
   * Recursively merges two binary trees. When both nodes overlap,
   * sums their values. Otherwise, returns whichever subtree is non‐null.
   *
   * Time Complexity:  O(n)  
   *   We visit each overlapping node pair exactly once. If one tree is smaller,
   *   the remainder of the larger tree is just attached without further recursion.
   * Space Complexity: O(h)  
   *   The recursion stack can go as deep as the height h of the merged tree
   *   (worst‐case unbalanced: h = n; balanced: h = log n).
   */
  public static BinaryTree mergeTrees(BinaryTree tree1, BinaryTree tree2) {
    // 1) If one side is null, return the other side
    if (tree1 == null) return tree2;
    if (tree2 == null) return tree1;

    // 2) Both nodes exist → sum their values
    tree1.value += tree2.value;

    // 3) Recursively merge left children
    tree1.left  = mergeTrees(tree1.left,  tree2.left);

    // 4) Recursively merge right children
    tree1.right = mergeTrees(tree1.right, tree2.right);

    return tree1; // tree1 is now the root of the merged subtree
  }

  // (Optional) Simple pre‐order print to verify the merged tree
  public static void printPreOrder(BinaryTree node) {
    if (node == null) return;
    System.out.print(node.value + " ");
    printPreOrder(node.left);
    printPreOrder(node.right);
  }

  public static void main(String[] args) {
    // Example trees:
    //
    // tree1:       tree2:
    //    1            1
    //   / \          / \
    //  3   2        5   9
    // / \            \  / \
    //7   4            2 7  6

    BinaryTree tree1 = new BinaryTree(1);
    tree1.left  = new BinaryTree(3);
    tree1.right = new BinaryTree(2);
    tree1.left.left  = new BinaryTree(7);
    tree1.left.right = new BinaryTree(4);

    BinaryTree tree2 = new BinaryTree(1);
    tree2.left       = new BinaryTree(5);
    tree2.right      = new BinaryTree(9);
    tree2.left.left  = new BinaryTree(2);
    tree2.right.left = new BinaryTree(7);
    tree2.right.right= new BinaryTree(6);

    BinaryTree merged = mergeTrees(tree1, tree2);

    // Print in pre‐order to check values:
    // Expected pre‐order of merged: 2 8 9 4 11 7 6
    printPreOrder(merged);
    // Output: 2 8 9 4 11 7 6
  }
}
```

---

## Approach 2: Iterative Merge Using a Stack

### Key Idea

We can avoid recursion (and its call‐stack overhead) by using a **stack** of node‐pairs to process. Each entry is a pair `(node1, node2)` from the two trees. We mutate `node1` in place:

1. Push `(tree1, tree2)` onto the stack.
2. While the stack isn’t empty:

   * Pop `(n1, n2)`.
   * If either is `null`, continue—no action needed, since “null” means attach the other side.
   * **Merge values**: `n1.value += n2.value`.
   * **Left Child**:

     * If `n1.left` is `null`, set `n1.left = n2.left` (attach the entire left subtree of `n2`).
     * Otherwise, push `(n1.left, n2.left)` onto the stack to merge those subtrees later.
   * **Right Child**:

     * If `n1.right` is `null`, set `n1.right = n2.right`.
     * Otherwise, push `(n1.right, n2.right)` onto the stack.

At the end, `tree1` has been mutated to hold the merged result, so we return that.

### Java Code

```java
package medium.binarytrees;

import java.util.Stack;

public class MergeBinaryTrees {

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
   * Iteratively merges two binary trees using a stack of node‐pairs.
   * Modifies tree1 in place by summing overlapping nodes and attaching
   * any non‐overlapping subtrees from tree2.
   *
   * Time Complexity:  O(n)  
   *   Each overlapping node pair is pushed and popped exactly once.
   * Space Complexity: O(h)  
   *   The stack may hold up to O(h) pairs in the worst case, where h is the height.
   */
  public static BinaryTree mergeTreesIterative(BinaryTree tree1, BinaryTree tree2) {
    if (tree1 == null) return tree2;
    if (tree2 == null) return tree1;

    // Stack holds pairs of nodes to merge
    Stack<Pair> stack = new Stack<>();
    stack.push(new Pair(tree1, tree2));

    while (!stack.isEmpty()) {
      Pair current = stack.pop();
      BinaryTree n1 = current.tree1;
      BinaryTree n2 = current.tree2;

      if (n1 == null || n2 == null) {
        // If one is null, either nothing to merge or we already attached the other side earlier
        continue;
      }

      // Merge values
      n1.value += n2.value;

      // Merge left children
      if (n1.left == null) {
        // If tree1 has no left child, just attach tree2’s left subtree
        n1.left = n2.left;
      } else {
        // Both have left children → push them to merge later
        stack.push(new Pair(n1.left, n2.left));
      }

      // Merge right children
      if (n1.right == null) {
        // If tree1 has no right child, attach tree2’s right subtree
        n1.right = n2.right;
      } else {
        // Both have right children → push them to merge later
        stack.push(new Pair(n1.right, n2.right));
      }
    }

    return tree1; // tree1 is now the root of the merged tree
  }

  // Simple pair holder for stacking node pairs
  static class Pair {
    BinaryTree tree1;
    BinaryTree tree2;

    Pair(BinaryTree t1, BinaryTree t2) {
      this.tree1 = t1;
      this.tree2 = t2;
    }
  }

  // (Optional) Pre‐order print for verification
  public static void printPreOrder(BinaryTree node) {
    if (node == null) return;
    System.out.print(node.value + " ");
    printPreOrder(node.left);
    printPreOrder(node.right);
  }

  public static void main(String[] args) {
    // Build example trees (same as in the recursive example)
    BinaryTree tree1 = new BinaryTree(1);
    tree1.left  = new BinaryTree(3);
    tree1.right = new BinaryTree(2);
    tree1.left.left  = new BinaryTree(7);
    tree1.left.right = new BinaryTree(4);

    BinaryTree tree2 = new BinaryTree(1);
    tree2.left       = new BinaryTree(5);
    tree2.right      = new BinaryTree(9);
    tree2.left.left  = new BinaryTree(2);
    tree2.right.left = new BinaryTree(7);
    tree2.right.right= new BinaryTree(6);

    BinaryTree merged = mergeTreesIterative(tree1, tree2);

    // Print pre‐order to check
    printPreOrder(merged);
    // Expected pre‐order: 2 8 9 4 11 7 6
  }
}
```

---

## Complexity Analysis

| Approach             | Time Complexity | Space Complexity       | Notes                                                            |
| -------------------- | --------------- | ---------------------- | ---------------------------------------------------------------- |
| Recursive Merge      | O(n)            | O(h) (recursion stack) | Visits each overlapping node once; stack depth = height of tree. |
| Iterative with Stack | O(n)            | O(h) (stack size)      | Each pair of overlapping nodes is pushed exactly once.           |

* **n** is the total number of nodes that actually overlap or exist in at least one tree.
* **h** is the height of the resulting merged tree (worst‐case unbalanced: h = n; balanced: h = log n).

In both cases, **every node** in at least one of the two input trees is eventually part of the merged structure, so we say time is **O(n)**. All merging is done in place by reusing and mutating `tree1`’s nodes, attaching `tree2`’s nodes when needed.
