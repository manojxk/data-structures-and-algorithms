**Problem Restatement**

A **branch sum** in a binary tree is the total of all node values along a path from the **root** down to a **leaf**. Your task is to compute a list of all branch sums, ordered from the **leftmost** leaf branch to the **rightmost** leaf branch.

Given the sample tree:

```
           1
         /   \
        2     3
      /   \  /  \
     4     5 6   7
   /   \  /
  8     9 10
```

The leaf-paths and their sums are:

1. 1 → 2 → 4 → 8  ⇒ sum =  1+2+4+8  = **15**
2. 1 → 2 → 4 → 9  ⇒ sum =  1+2+4+9  = **16**
3. 1 → 2 → 5 → 10 ⇒ sum =  1+2+5+10 = **18**
4. 1 → 3 → 6      ⇒ sum =  1+3+6    = **10**
5. 1 → 3 → 7      ⇒ sum =  1+3+7    = **11**

So the result list is `[15, 16, 18, 10, 11]`.

---

## Approach: Depth-First Traversal (Recursive)

We’ll do a **single** DFS (preorder) down every path, carrying along a **running sum**:

1. **Start** at the root with `runningSum = 0`.
2. **At each node**:

   * Add the node’s value to `runningSum`.
   * If it’s a **leaf** (no left and no right child), **record** `runningSum` in the results list.
   * Otherwise, **recurse** on its left child (if any) and on its right child (if any), passing along the updated `runningSum`.

Because we recurse **first left, then right**, our sums naturally fill the list in left-to-right branch order.

---

## Step-by-Step Breakdown

1. **Helper Signature**

   ```java
   private static void calculateBranchSums(
       BinaryTree node, int runningSum, List<Integer> sums
   )
   ```

   * `node`: current tree node
   * `runningSum`: sum of values from root down to **just before** this node
   * `sums`: list to collect final branch sums

2. **Base Case**
   If `node == null`, there’s nothing to add. Just return.

3. **Update Running Sum**

   ```java
   int newRunningSum = runningSum + node.value;
   ```

4. **Leaf Check**

   ```java
   if (node.left == null && node.right == null) {
     sums.add(newRunningSum);   // record final sum for this branch
     return;
   }
   ```

5. **Recurse Children**

   ```java
   calculateBranchSums(node.left,  newRunningSum, sums);
   calculateBranchSums(node.right, newRunningSum, sums);
   ```

6. **Driver Function**

   ```java
   public static List<Integer> branchSums(BinaryTree root) {
     List<Integer> sums = new ArrayList<>();
     calculateBranchSums(root, 0, sums);
     return sums;
   }
   ```

---

## Full Java Code

```java
package easy.binarytrees;

import java.util.ArrayList;
import java.util.List;

public class BranchSums {

  // Definition of a binary tree node
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

  // Public method to get all branch sums
  public static List<Integer> branchSums(BinaryTree root) {
    List<Integer> sums = new ArrayList<>();
    calculateBranchSums(root, 0, sums);
    return sums;
  }

  // Helper method: recursively traverse and collect sums
  private static void calculateBranchSums(
      BinaryTree node,
      int runningSum,
      List<Integer> sums
  ) {
    if (node == null) {
      return; // nothing to do for a null child
    }

    int newRunningSum = runningSum + node.value;

    // If leaf node, record the branch sum
    if (node.left == null && node.right == null) {
      sums.add(newRunningSum);
      return;
    }

    // Otherwise, traverse left then right
    calculateBranchSums(node.left,  newRunningSum, sums);
    calculateBranchSums(node.right, newRunningSum, sums);
  }

  // Example usage and testing
  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(1);
    root.left  = new BinaryTree(2);
    root.right = new BinaryTree(3);

    root.left.left  = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.left = new BinaryTree(6);
    root.right.right= new BinaryTree(7);

    root.left.left.left  = new BinaryTree(8);
    root.left.left.right = new BinaryTree(9);
    root.left.right.left = new BinaryTree(10);

    List<Integer> result = branchSums(root);
    System.out.println(result);  // [15, 16, 18, 10, 11]
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**
  We visit **each node exactly once**, performing O(1) work at each → **O(n)**, where *n* is the number of nodes.

* **Space Complexity:**

  * **Call stack** for recursion can go as deep as the tree’s height *h*.

    * In a balanced tree, *h* = O(log n).
    * In the worst (skewed) case, *h* = O(n).
  * **Output list** holds one sum per leaf; in the worst case (every node is a leaf), you store O(n) sums.

So overall, **O(n)** space in the worst case, and **O(log n)** additional space if the tree is well balanced.
