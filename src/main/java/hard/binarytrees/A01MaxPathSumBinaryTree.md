Below is a detailed walkthrough of how the “Binary Tree Maximum Path Sum” solution works, followed by a brief analysis of its time and space complexity.

---

## 1. Problem Restatement

Given the root of a (possibly negative‐valued) binary tree, a **path** is any sequence of nodes where each adjacent pair in the sequence is connected by a parent–child edge, and no node is visited more than once. The **path sum** is the sum of all node values along that path. Paths do *not* have to pass through the root; they can begin and end anywhere in the tree, as long as they use only parent–child links.

We need to find the single path (of length ≥ 1) whose sum is as large as possible, and return that maximum sum.

---

## 2. Key Observations

1. **Any “maximum” path in a tree either:**

   * Passes through some node X as the “highest” (turning) point, with (optionally) one segment going downward into X’s left subtree and another segment going downward into X’s right subtree.
   * Or simply runs entirely down one side (left or right) of X, if including both sides would reduce the total because negative values outweigh positives.

2. When you compute — for a given node X — the best possible path that *starts at X* and goes *down* into one subtree, you must pick the larger of:

   * `X.val` alone,
   * `X.val + (best downward path from left child)`,
   * `X.val + (best downward path from right child)`.

   You call that single‐arm “best‐downwards‐from‐X”. Call it `maxArm(X)`.

3. At the same time, the best path that **passes through** X as the peak (i.e. that uses both children) would be:

   ```
   candidateThroughX = X.val 
                     + max(0, bestArm(leftChild)) 
                     + max(0, bestArm(rightChild))
   ```

   (We use `max(0, bestArm)` so that if a child’s contribution is negative, we “cut it off” rather than dragging the sum down.)

4. The global maximum path anywhere in the entire tree is simply the maximum of all those “through‐X” candidates over every node X.

Hence, a standard approach is:

* Recursively compute, for each node X, both:

  * `bestArm(X)`: the maximum sum of a path that starts at X and goes downward *into exactly one* of its subtrees (or just uses X alone).
  * Meanwhile, update a global variable `ans` with the best “pass‐through‐X” = `X.val + (nonnegative left‐arm) + (nonnegative right‐arm)`.

---

## 3. Code Walkthrough

```java
public class A11BinaryTreeMaximumPathSum {

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int v) { this.val = v; }
  }

  // This array holds a single int so that helper can update it by reference.
  // ans[0] always tracks the best path‐sum seen so far.
  private int[] ans = new int[1];

  public int maxPathSum(TreeNode root) {
    ans[0] = Integer.MIN_VALUE; 
    maxPathSumUtil(root);
    return ans[0];
  }

  /**
   * Recursively returns the maximum “arm” sum rooted at `node`:
   *   maxArm(node) = the maximum path‐sum of any path that starts at `node` and goes downward
   *   (choosing at most one of its two children), or just `node.val` alone if both children would
   *   drag it lower.
   *
   * Along the way, it also updates ans[0] to reflect the best “through‐node” path‐sum found anywhere.
   */
  private int maxPathSumUtil(TreeNode node) {
    if (node == null) {
      // If there is no node, pretend its “arm” contribution is 0. 
      // (We will do max(0, left) and max(0, right), so this is safe.)
      return 0;
    }

    // Recursively compute how far we can extend a path downward in the left subtree:
    int leftArm  = maxPathSumUtil(node.left);
    // Recursively compute how far we can extend a path downward in the right subtree:
    int rightArm = maxPathSumUtil(node.right);

    // If either child’s “arm” is negative, we would never include it, so clamp to zero:
    int leftMax  = Math.max(leftArm,  0);
    int rightMax = Math.max(rightArm, 0);

    // A path that “passes through” this node and goes into both children:
    int throughNode = node.val + leftMax + rightMax;

    // Update the global maximum if “throughNode” is better than anything seen so far:
    ans[0] = Math.max(ans[0], throughNode);

    // Now return the best single‐arm path starting here,
    // i.e. node.val + whichever child‐arm is larger (if positive).
    return node.val + Math.max(leftMax, rightMax);
  }

  // For demonstration:
  public static void main(String[] args) {
    A11BinaryTreeMaximumPathSum solver = new A11BinaryTreeMaximumPathSum();

    // Example tree:
    //       -10
    //       /  \
    //      9    20
    //          /  \
    //         15   7
    //
    TreeNode root = new TreeNode(-10);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);

    System.out.println(solver.maxPathSum(root)); // Should print 42
    // Explanation: The best path is 15 → 20 → 7 = 42.
  }
}
```

### Detailed Explanation

1. **Global Variable Setup**
   We store `ans` in an `int[]` of length 1 rather than a simple `int`, because Java passes object references by value. This allows the helper method to modify `ans[0]` and have it be visible to the caller. We initialize `ans[0] = Integer.MIN_VALUE` so even a tree of all‐negative values will get updated correctly.

2. **Recursion: `maxPathSumUtil(node)`**

   * **Base Case:** If `node == null`, return 0. (In effect, a missing child contributes 0 to any upward path.)

   * **Recurse Left & Right:**

     ```java
     int leftArm  = maxPathSumUtil(node.left);
     int rightArm = maxPathSumUtil(node.right);
     ```

     Each call returns the best “single‐arm” path sum from that child downward.

   * **Clamp Negative Contributions to 0**
     A child’s arm might be negative (if every path in that child’s subtree sums to something < 0). Including a negative arm would only reduce our total. So we do:

     ```java
     int leftMax  = Math.max(leftArm,  0);
     int rightMax = Math.max(rightArm, 0);
     ```

     If `leftArm` was negative, we treat it as 0, effectively not including that side at all.

   * **Compute Path Through the Current Node**
     A path that “goes through” `node` and uses both subtrees (left → node → right) has sum:

     ```
     throughNode = node.val + leftMax + rightMax
     ```

     We now compare that against our global `ans[0]`:

     ```java
     ans[0] = Math.max(ans[0], throughNode);
     ```

     That ensures `ans[0]` is always the largest path sum encountered anywhere in the tree so far.

   * **Return the Best Single‐Arm Upward**
     Finally, if this node’s parent wants to extend a path through us, it can only pick one side (whichever is larger) plus `node.val`. That is:

     ```java
     return node.val + Math.max(leftMax, rightMax);
     ```

     Because any path going upward cannot branch into both children simultaneously—it must remain a single line.

3. **Result**
   After calling `maxPathSumUtil(root)`, `ans[0]` holds the maximum path sum of any path in the tree, including those that might pass through and split at some node. We then return `ans[0]`.

---

## 4. Example Walkthrough (〈−10,9,20,15,7〉)

```
        -10
        /  \
       9    20
           /  \
          15   7
```

1. Start at the root (−10). To compute anything, we first recursively compute for its left child (9) and right child (20).

2. For `node = 9` (a leaf):

   * `leftArm  = maxPathSumUtil(null) = 0`
   * `rightArm = maxPathSumUtil(null) = 0`
   * `leftMax  = max(0, 0) = 0`
   * `rightMax = max(0, 0) = 0`
   * `throughNode = 9 + 0 + 0 = 9`
   * `ans[0] = max(Integer.MIN_VALUE, 9) = 9`
   * Return `9 + max(0, 0) = 9` up to its parent.

3. For `node = 15` (a leaf under 20’s left):

   * Similar logic yields `throughNode = 15`, so `ans[0] = max(9, 15) = 15`.
   * Return `15` up.

4. For `node = 7` (a leaf under 20’s right):

   * Yields `throughNode = 7`, so `ans[0] = max(15, 7) = 15` (no change).
   * Return `7` up.

5. Now `node = 20`:

   * `leftArm  = 15` (from step 3)
   * `rightArm = 7`  (from step 4)
   * `leftMax  = max(15, 0) = 15`
   * `rightMax = max(7,  0) = 7`
   * `throughNode = 20 + 15 + 7 = 42`
   * `ans[0] = max(15, 42) = 42`
   * Return `20 + max(15, 7) = 35` up to its parent (−10).

6. Finally, `node = −10` (the root):

   * `leftArm  = 9`   (returned from step 2)
   * `rightArm = 35`  (returned from step 5)
   * `leftMax  = max(9,  0) = 9`
   * `rightMax = max(35, 0) = 35`
   * `throughNode = (−10) + 9 + 35 = 34`
   * `ans[0] = max(42, 34) = 42` (remains 42)
   * Return `−10 + max(9, 35) = 25` if a parent awaited it—but since this was the root, recursion ends here.

At the end, `ans[0]` is **42**, which corresponds to the path 15 → 20 → 7.

---

## 5. Time & Space Complexity

* **Time Complexity**:  O(n)
  Each node is visited exactly once. At each node, we do a constant amount of work—two recursive calls, a few max/comparisons, and updating `ans[0]`. So overall, with n nodes, the runtime is O(n).

* **Space Complexity**:  O(h)
  The extra space comes from the recursion stack. In the worst case (tree is a straight line), h = n, so space = O(n). In a balanced tree, h = O(log n), so space = O(log n). Other than that stack, we only use a constant number of local variables and the single `int[] ans`.

---

### Recap

1. **For each node**, we gather from its left and right subtrees:

   * `leftArm  = best single‐arm path sum from left child`.
   * `rightArm = best single‐arm path sum from right child`.
2. We clamp negative contributions to zero, so `leftMax = max(leftArm, 0)`, `rightMax = max(rightArm, 0)`.
3. The best path that “goes through” this node is `node.val + leftMax + rightMax`. We compare this against our global maximum and update it.
4. We return for the parent the best “one‐armed” extension: `node.val + max(leftMax, rightMax)`.
5. In a single O(n) traversal, we find the maximum path sum anywhere in the tree.

That completes the solution.
