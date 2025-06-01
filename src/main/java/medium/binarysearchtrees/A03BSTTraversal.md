Below are the three recursive traversal functions for a BST (`TreeNode`), each of which takes a node and a (initially empty) list and appends node values in the required order.

```java
package medium.binarysearchtrees;

import java.util.*;

public class BSTTraversal {
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
   * In-Order Traversal (Left → Node → Right):
   * Visits all nodes in ascending order for a valid BST.
   */
  public static void inOrderTraverse(TreeNode node, List<Integer> array) {
    if (node == null) return;
    inOrderTraverse(node.left, array);
    array.add(node.value);
    inOrderTraverse(node.right, array);
  }

  /**
   * Pre-Order Traversal (Node → Left → Right):
   * Visits the root before its subtrees.
   */
  public static void preOrderTraverse(TreeNode node, List<Integer> array) {
    if (node == null) return;
    array.add(node.value);
    preOrderTraverse(node.left, array);
    preOrderTraverse(node.right, array);
  }

  /**
   * Post-Order Traversal (Left → Right → Node):
   * Visits the root after its subtrees.
   */
  public static void postOrderTraverse(TreeNode node, List<Integer> array) {
    if (node == null) return;
    postOrderTraverse(node.left, array);
    postOrderTraverse(node.right, array);
    array.add(node.value);
  }

  public static void main(String[] args) {
    // Construct the example BST:
    //
    //        10
    //       /  \
    //      5    15
    //     / \     \
    //    2   5     22
    //   /
    //  1
    //
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.right = new TreeNode(15);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(22);
    root.left.left.left = new TreeNode(1);

    // In-Order:  Left → Node → Right
    List<Integer> inOrder = new ArrayList<>();
    inOrderTraverse(root, inOrder);
    System.out.println("inOrderTraverse:  " + inOrder);
    // Output: [1, 2, 5, 5, 10, 15, 22]

    // Pre-Order: Node → Left → Right
    List<Integer> preOrder = new ArrayList<>();
    preOrderTraverse(root, preOrder);
    System.out.println("preOrderTraverse: " + preOrder);
    // Output: [10, 5, 2, 1, 5, 15, 22]

    // Post-Order: Left → Right → Node
    List<Integer> postOrder = new ArrayList<>();
    postOrderTraverse(root, postOrder);
    System.out.println("postOrderTraverse: " + postOrder);
    // Output: [1, 2, 5, 5, 22, 15, 10]
  }
}
```

**How each traversal works:**

* **In-Order (Left, Node, Right):**

  ```java
  if (node != null) {
    inOrderTraverse(node.left, array);
    array.add(node.value);
    inOrderTraverse(node.right, array);
  }
  ```

  Visits the entire left subtree first, then the current node, then the right subtree. In a BST this yields sorted (ascending) order.

* **Pre-Order (Node, Left, Right):**

  ```java
  if (node != null) {
    array.add(node.value);
    preOrderTraverse(node.left, array);
    preOrderTraverse(node.right, array);
  }
  ```

  Visits the current node before either subtree.

* **Post-Order (Left, Right, Node):**

  ```java
  if (node != null) {
    postOrderTraverse(node.left, array);
    postOrderTraverse(node.right, array);
    array.add(node.value);
  }
  ```

  Visits both subtrees first, then the current node.

**Complexities (all three):**

* **Time:** O(n) — each node is visited exactly once.
* **Space:** O(h) call‐stack, where h is the tree’s height (O(log n) if balanced, O(n) in the worst‐case).

No auxiliary data structures (beyond the output list) are required, and each function fills the supplied list in-place before returning it.
