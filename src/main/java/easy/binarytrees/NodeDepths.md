**Problem Explanation**
You’re given a binary tree where each node has:

* An integer `value`.
* A `left` child (which may be `null`).
* A `right` child (which may be `null`).

The **depth** of a node is the number of edges from the **root** down to that node. For example, the root has depth 0; its children have depth 1; their children have depth 2; and so on.

Your task is to compute the **sum of depths** over **all** nodes in the tree.

> **Example Tree:**
>
> ```
>            1           ← depth 0
>          /   \
>         2     3        ← depth 1
>       /   \  /  \
>      4    5 6    7     ← depth 2
>    /   \
>   8     9              ← depth 3
> ```
>
> * Node 1 contributes 0
> * Nodes 2, 3 each contribute 1
> * Nodes 4, 5, 6, 7 each contribute 2
> * Nodes 8, 9 each contribute 3
>
> **Total** = 0 + (1+1) + (2+2+2+2) + (3+3) = 16

---

## Approach 1: Recursive Depth‐First Traversal

We can perform a **preorder** (or any order) DFS, carrying along the **current depth**:

1. **Signature**

   ```java
   int calculateNodeDepths(Node node, int depth)
   ```

   * `node` = the current node
   * `depth` = its depth from the root

2. **Base Case**

   * If `node == null`, return 0 (no contribution).

3. **Recursive Case**

   * Add the current `depth`.
   * Recurse left, passing `depth + 1`.
   * Recurse right, passing `depth + 1`.
   * Sum them all and return.

### Steps

1. Start by calling `calculateNodeDepths(root, 0)`.
2. At each node:

   * If null, return 0.
   * Compute `leftSum  = calculateNodeDepths(node.left,  depth + 1)`.
   * Compute `rightSum = calculateNodeDepths(node.right, depth + 1)`.
   * Return `depth + leftSum + rightSum`.

### Java Code

```java
public static int nodeDepthsRecursive(BinaryTree root) {
  return calculateNodeDepths(root, 0);
}

private static int calculateNodeDepths(BinaryTree node, int depth) {
  if (node == null) {
    return 0;
  }
  // Sum this node's depth plus depths in both subtrees
  return depth
       + calculateNodeDepths(node.left,  depth + 1)
       + calculateNodeDepths(node.right, depth + 1);
}
```

---

## Approach 2: Iterative Using a Stack

Alternatively, simulate the same DFS with an explicit stack of **(node, depth)** pairs:

1. **Push** `(root, 0)` onto the stack.
2. **While** the stack isn’t empty:

   * **Pop** `(node, depth)`.
   * If `node == null`, continue.
   * **Add** `depth` to your running total.
   * **Push** `(node.left,  depth + 1)`.
   * **Push** `(node.right, depth + 1)`.
3. **Return** the total.

### Steps

1. Initialize `sumOfDepths = 0` and `stack = [(root, 0)]`.
2. Loop until stack empty:

   * Pop `(node, depth)`.
   * If node not null:

     * `sumOfDepths += depth`.
     * Push its children with `depth+1`.
3. Return `sumOfDepths`.

### Java Code

```java
public static int nodeDepthsIterative(BinaryTree root) {
  int sumOfDepths = 0;
  Stack<NodeDepthPair> stack = new Stack<>();
  stack.push(new NodeDepthPair(root, 0));

  while (!stack.isEmpty()) {
    NodeDepthPair pair = stack.pop();
    BinaryTree node = pair.node;
    int depth = pair.depth;
    if (node == null) continue;
    sumOfDepths += depth;
    stack.push(new NodeDepthPair(node.left,  depth + 1));
    stack.push(new NodeDepthPair(node.right, depth + 1));
  }

  return sumOfDepths;
}

// Helper class
static class NodeDepthPair {
  BinaryTree node;
  int depth;
  NodeDepthPair(BinaryTree node, int depth) {
    this.node = node;
    this.depth = depth;
  }
}
```

---

## Complexity Analysis

| Method    | Time Complexity | Space Complexity                    |
| --------- | --------------- | ----------------------------------- |
| Recursive | O(n)            | O(h) — call stack (h = tree height) |
| Iterative | O(n)            | O(h) — explicit stack               |

* **n** = total nodes
* **h** ≈ log n for balanced trees, up to n for skewed trees

Both approaches visit each node exactly once (O(n)), and use additional stack/recursion proportional to the tree’s height.
