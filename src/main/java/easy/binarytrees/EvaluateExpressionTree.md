**Problem Restatement**

You have a **binary expression tree** where:

* **Leaf nodes** store **positive integers** (operands).
* **Internal nodes** store **operators**, encoded as negative integers:

  * `-1` → **`+`** (addition)
  * `-2` → **`-`** (subtraction)
  * `-3` → **`/`** (integer division, truncate toward zero)
  * `-4` → **`*`** (multiplication)

Your task is to **evaluate** this tree and return the single integer result. You must evaluate **bottom-up** (i.e., compute each subtree before using its result at its parent).

---

## Approach: Recursive Postorder Traversal

1. **Base Case (Leaf):**

   * If the node has **no left or right child**, it’s an **operand**.
   * **Return** its value directly.

2. **Recursive Case (Operator Node):**

   * **Recurse** on the **left** subtree to get `leftValue`.
   * **Recurse** on the **right** subtree to get `rightValue`.
   * **Apply** the operator at the current node to these two values:

     * `-1`: compute `leftValue + rightValue`
     * `-2`: compute `leftValue - rightValue`
     * `-3`: compute `leftValue / rightValue` (integer division)
     * `-4`: compute `leftValue * rightValue`
   * **Return** the result.

Because each node is visited **exactly once**, and you combine child results before returning to the parent, this is a classic **postorder** evaluation of an expression tree.

---

## Detailed Steps

```text
evaluate(node):
  if node is null:
    return 0  // (tree always valid, so you rarely hit this)

  // 1) If leaf, you’ve reached an operand:
  if node.left == null AND node.right == null:
    return node.value

  // 2) Otherwise, it’s an operator:
  leftValue  = evaluate(node.left)
  rightValue = evaluate(node.right)

  // 3) Perform the operator stored in node.value:
  switch (node.value):
    case -1: return leftValue + rightValue
    case -2: return leftValue - rightValue
    case -3: return leftValue / rightValue
    case -4: return leftValue * rightValue
    default: throw error for invalid operator
```

---

## Java Implementation

```java
package easy.binarytrees;

public class EvaluateExpressionTree {

  // Definition of a binary-tree node
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
   * Evaluates the binary expression tree and returns its integer result.
   *
   * Time Complexity:  O(n)  — each node visited once
   * Space Complexity: O(h)  — recursion stack depth (h = tree height)
   */
  public static int evaluateExpressionTree(BinaryTree root) {
    // Defensive check (though problem guarantees a valid tree)
    if (root == null) {
      return 0;
    }

    // Base case: leaf node → operand
    if (root.left == null && root.right == null) {
      return root.value;
    }

    // Recursive evaluation of left and right subtrees
    int leftValue  = evaluateExpressionTree(root.left);
    int rightValue = evaluateExpressionTree(root.right);

    // Apply the operator at this node
    switch (root.value) {
      case -1: // Addition
        return leftValue + rightValue;
      case -2: // Subtraction
        return leftValue - rightValue;
      case -3: // Division (integer division truncates toward zero)
        return leftValue / rightValue;
      case -4: // Multiplication
        return leftValue * rightValue;
      default:
        throw new IllegalArgumentException(
          "Invalid operator code in node: " + root.value
        );
    }
  }

  public static void main(String[] args) {
    /*
          -1
         /   \
       -2     -3
      /  \   /  \
    -4    2 8    3
    / \
   2   3

    Represents: ((2 * 3) - 2) + (8 / 3)
    Expected result: 6
    */

    BinaryTree root = new BinaryTree(-1);
    root.left        = new BinaryTree(-2);
    root.right       = new BinaryTree(-3);

    root.left.left   = new BinaryTree(-4);
    root.left.right  = new BinaryTree(2);

    root.left.left.left  = new BinaryTree(2);
    root.left.left.right = new BinaryTree(3);

    root.right.left  = new BinaryTree(8);
    root.right.right = new BinaryTree(3);

    int result = evaluateExpressionTree(root);
    System.out.println("Evaluated result: " + result);  // Prints 6
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**
  You visit **each** of the $n$ nodes exactly **once** and do **O(1)** work per node → **O(n)**.

* **Space Complexity:**
  The maximum call‐stack depth equals the tree’s height $h$.

  * For a **balanced** tree, $h = O(\log n)$.
  * For a **skewed** tree, $h = O(n)$.

Thus, **O(h)** extra space.
