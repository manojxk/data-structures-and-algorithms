**Problem Restatement**
We have a (not necessarily balanced) binary tree in which each node holds an integer value. We want to locate every subtree that:

1. Forms a valid **Binary Search Tree** (BST) according to these rules:

   * All nodes in its left subtree have values *strictly less than* the root’s value.
   * All nodes in its right subtree have values *greater than or equal to* the root’s value.

2. Contains **at least 3 nodes**.

For each such valid‐BST subtree, we sum up the values of all nodes in that subtree, and then we add those sums together (across all qualifying subtrees). Finally, we return the total of those sums.

---

## 1. Why “in‐order” or “post‐order” At All?

* If you want to test whether a subtree is a BST and also compute its node count and node‐sum efficiently, a common technique is to gather information from each subtree’s children *before* deciding about the parent.
* In other words, at each node, we should first inspect its left child (and learn whether that child’s entire subtree is a BST, its sum, its min & max values, and how many nodes) and inspect its right child (the same). Only after we have that information can we decide:

  1. “Is the current node + left‐subtree + right‐subtree a valid BST?”
  2. “If so, what is its total number of nodes, its sum, and its min & max?”

That “gather from children, then decide on the parent” corresponds to a **post‐order** traversal.

---

## 2. Data We Need to Track for Each Subtree

When we examine a node `X`, in order to know whether `X`’s entire subtree is a valid BST, we must know, for each of `X`’s children:

1. **Is that child’s subtree itself already a valid BST?**
2. **How many nodes** are in that child’s subtree?
3. **What is the minimum value** inside that child’s subtree?
4. **What is the maximum value** inside that child’s subtree?
5. **What is the sum** of all node‐values in that child’s subtree?

— Only with all of that in hand can we check “Does this current node’s value sit correctly between \[leftSubtree’s max] and \[rightSubtree’s min]?” If yes, and if both children were valid BSTs themselves, then the new combined subtree is a valid BST.

Because we need to pass these five pieces of information “up” from each child into its parent, we define a small helper class:

```java
static class SubtreeInfo {
  boolean isBST;     // true if this entire subtree is already a valid BST
  int     size;      // how many nodes in this subtree
  int     minValue;  // the smallest value anywhere in this subtree
  int     maxValue;  // the largest value anywhere in this subtree
  int     sum;       // sum of all node‐values in this subtree

  SubtreeInfo(
    boolean isBST,
    int     size,
    int     minValue,
    int     maxValue,
    int     sum
  ) {
    this.isBST    = isBST;
    this.size     = size;
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.sum      = sum;
  }
}
```

For any `TreeNode node`, we will compute a `SubtreeInfo` object that describes the entire subtree rooted at `node`.

---

## 3. The Core Recursion: `traverse(TreeNode node)`

```java
private SubtreeInfo traverse(TreeNode node) {
  if (node == null) {
    // An empty subtree is trivially “a valid BST of size 0.”
    // We choose minValue = +∞, maxValue = −∞ so that any real node’s value
    // automatically sits between those extremes.
    return new SubtreeInfo(
      true,              // isBST = true (an empty tree is a valid BST)
      0,                 // size = 0
      Integer.MAX_VALUE, // minValue = +∞ (no real values lower than this)
      Integer.MIN_VALUE, // maxValue = −∞ (no real values higher than this)
      0                  // sum = 0
    );
  }

  // 1) Recurse into the left child
  SubtreeInfo leftInfo  = traverse(node.left);
  // 2) Recurse into the right child
  SubtreeInfo rightInfo = traverse(node.right);

  // 3) Check if both children’s subtrees are BSTs AND the current node’s
  //    value “fits” between leftInfo.maxValue and rightInfo.minValue:
  boolean isBST = (
       leftInfo.isBST
    && rightInfo.isBST
    && node.val >  leftInfo.maxValue
    && node.val <= rightInfo.minValue
  );

  // 4) Compute this subtree’s size = (left size) + (right size) + 1 for 'node'
  int currentSize = leftInfo.size + rightInfo.size + 1;

  // 5) Compute this subtree’s sum = (left sum) + (right sum) + (node’s value)
  int currentSum = leftInfo.sum + rightInfo.sum + node.val;

  // 6) Determine this subtree’s minValue and maxValue:
  int currentMinValue = Math.min(node.val, leftInfo.minValue);
  int currentMaxValue = Math.max(node.val, rightInfo.maxValue);

  // 7) If this subtree is a valid BST **and** has at least 3 nodes,
  //    add its total sum to our running global totalSum.
  if (isBST && currentSize >= 3) {
    totalSum += currentSum;
  }

  // 8) Return a new SubtreeInfo describing this entire subtree
  return new SubtreeInfo(
    isBST,
    currentSize,
    currentMinValue,
    currentMaxValue,
    currentSum
  );
}
```

### Why Those Steps Work

* **Base Case (node == null):**
  We want “an empty subtree” to behave in a way that never blocks a parent from forming a BST. So we return `isBST = true`, `size = 0`, `minValue = +∞`, and `maxValue = −∞`. That way, a parent’s check `node.val > leftInfo.maxValue` is always `true` if the left side is empty (because `node.val > (−∞)`), and `node.val <= rightInfo.minValue` is always `true` if the right side is empty (because `node.val <= (+∞)`).

* **Recurse Left + Right**
  We gather `(isBST, size, minValue, maxValue, sum)` for each subtree.

* **Check `isBST` at Current Node**
  We require three conditions:

  1. `leftInfo.isBST` must be true.
  2. `rightInfo.isBST` must be true.
  3. The current node’s value sits strictly above all values in the left subtree (`node.val > leftInfo.maxValue`)
     and at or below all values in the right subtree (`node.val <= rightInfo.minValue`).
     Only if all three are true is the **combined** tree a valid BST.

* **Compute new `size` and `sum`**
  Simple addition:

  * `size = left.size + right.size + 1`
  * `sum  = left.sum  + right.sum  + node.val`

* **Compute new `minValue` and `maxValue`**

  * The minimum in this subtree is either the current node’s value, or something from the left subtree (whichever is smaller).
  * The maximum is either the current node’s value, or something from the right subtree (whichever is larger).

* **Accumulate `totalSum`**

  * If we see that `isBST == true && currentSize >= 3`, that means “this node’s entire subtree forms a valid BST with at least 3 nodes.” We add `currentSum` to a running global variable `totalSum`.

---

## 4. Putting It All Together

```java
int totalSum = 0;

public int sumBSTs(TreeNode root) {
  traverse(root);
  return totalSum;
}
```

* We declare `totalSum` as a field.
* We call `traverse(root)`, which explores every node’s subtree exactly once (post‐order), and whenever a subtree qualifies (valid BST + ≥ 3 nodes), it increments `totalSum` by that subtree’s sum.
* At the end, we return `totalSum`.

---

## 5. Worked Example

Consider this tree:

```
          10
         /  \
        5    15
       / \   / \
      1   8 12  20
```

* Start at root = 10. We must first recurse into its left child (5), then into 5’s left child (1), then into 1’s left child (null), into 1’s right child (null), process node=1, return info for subtree rooted at 1:

  * leftInfo = (isBST=true, size=0, min=+∞, max=−∞, sum=0)
  * rightInfo= same
  * Check if `1` is a BST root: yes (no children).
  * `currentSize=1, currentSum=1, currentMin=1, currentMax=1`.
  * Since size=1 (<3), we do \_not\_ add to totalSum. Return `SubtreeInfo(true,1,1,1,1)`.

* Back at node=5’s left subtree done. Now go to node=5’s right child (8). Its children are both null, so you get `SubtreeInfo(true,1,8,8,8)` with size=1. Again no add because size<3.

* Now process node=5 itself:

  * leftInfo = (isBST=true, size=1, min=1, max=1, sum=1)
  * rightInfo= (isBST=true, size=1, min=8, max=8, sum=8)
  * Check `5 > leftInfo.max(1)` ? yes.
  * Check `5 <= rightInfo.min(8)` ? yes.
  * So `isBST = true`.
  * `currentSize = 1 + 1 + 1 = 3`
  * `currentSum  = 1 + 8 + 5 = 14`
  * Now `size >= 3` and `isBST=true`, so **add 14 to `totalSum`**.
  * Return `SubtreeInfo(true,3,1,8,14)` up.

* Move back up to root=10. But first, process the entire left side is done. Next, we process the right subtree rooted at 15:

  1. Recurse into 15’s left child (12) → both children null → `SubtreeInfo(true,1,12,12,12)` (size=1 → not added).
  2. Recurse into 15’s right child (20) → both children null → `SubtreeInfo(true,1,20,20,20)` (size=1 → not added).
  3. At node=15 itself:

     * leftInfo  = (isBST=true, size=1, min=12, max=12, sum=12)
     * rightInfo = (isBST=true, size=1, min=20, max=20, sum=20)
     * Check `15 > 12`? yes.
     * Check `15 <= 20`? yes.
     * So `isBST=true`.
     * `currentSize = 1 + 1 + 1 = 3`
     * `currentSum  = 12 + 20 + 15 = 47`
     * size≥3 and isBST, so **add 47 to `totalSum`**.
     * Return `SubtreeInfo(true, 3, 12, 20, 47)` up.

* Finally, process the root = 10:

  * leftInfo  = (isBST=true, size=3, min=1, max=8, sum=14)
  * rightInfo = (isBST=true, size=3, min=12, max=20, sum=47)
  * Check `10 > leftInfo.max(8)`? yes.
  * Check `10 <= rightInfo.min(12)`? yes.
  * So `isBST = true`.
  * `currentSize = 3 + 3 + 1 = 7`
  * `currentSum  = 14 + 47 + 10 = 71`
  * size≥3 and isBST, so **add 71 to `totalSum`**.

At that point, we have collected all qualifying subtree sums:

* Subtree rooted at **5** contributed 14
* Subtree rooted at **15** contributed 47
* Subtree rooted at **10** contributed 71

Total = 14 + 47 + 71 = **132**.

> **However**, the problem statement’s example claimed the output was 61. Why the difference?
>
> In the prompt’s example, they said “The subtree rooted at 15 has fewer than 3 nodes, so it’s not included.” But in our illustration, 15 had exactly 3 nodes (15,12,20).
>
> If, instead, the example tree were exactly:
>
> ```
>      10
>     /  \
>    5    15
>   / \     \
>  1   8     20
>         (No 12)
> ```
>
> then the subtree at 15 only has 2 nodes (15 and 20), so it would not qualify. In that adjusted scenario:
>
> * Only **5‐subtree** (size=3, sum=1+5+8=14)
> * Only **10‐subtree** (size=5, sum=1+5+8+10+20=44)
>   would count, giving 14 + 44 = 58 (not 61—so clearly the exact shape matters).
>
> In short, the given code is correct, but you must trust your own tree’s structure. If the prompt’s example said “subtree(15) has fewer than 3 nodes,” then the node 12 must not have been present in that example’s right‐subtree.

---

## 6. Complexity Analysis

* **Time Complexity**:
  We visit each node exactly once in a post‐order traversal. At each node, we do only O(1) work: logical checks, additions, and comparisons of five integers. Hence total time is **O(n)**, where n = number of nodes.

* **Space Complexity**:

  * We use one `SubtreeInfo` object per recursive call, but as soon as we return from a call, that object is eligible for garbage‐collection (assuming no other references).
  * The only extra memory truly “on the stack” is the recursion depth, which is O(h), where h = height of the tree. In a balanced tree, h ≈ O(log n). In the worst (degenerate) case, h ≈ O(n).
  * Aside from that recursion stack, there is no other data structure of size O(n). Therefore, extra space is **O(h)**.

---

### Summary

1. **Traverse in post‐order** so that children’s BST‐validity, sums, min/max, and sizes are known before checking their parent.
2. At each node, compute:

   * `isBST = (left.isBST) ∧ (right.isBST) ∧ (node.val > left.max) ∧ (node.val ≤ right.min)`.
   * `size = left.size + right.size + 1`.
   * `sum  = left.sum  + right.sum  + node.val`.
   * `minValue = min(node.val, left.minValue)`
   * `maxValue = max(node.val, right.maxValue)`
3. If `isBST` is true and `size ≥ 3`, add `sum` to a global `totalSum`.
4. Return a `SubtreeInfo` packet up the recursion so the parent can do the same checks.

In one pass, we accumulate the sum of all “subtrees that are valid BSTs and have at least 3 nodes.” This solves the problem in **O(n)** time and **O(h)** space (where h is the tree’s height).


```java
public class A04SumOfBSTs {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  // Helper class to store information about each subtree during traversal
  static class SubtreeInfo {
    boolean isBST; // Indicates if the subtree is a valid BST
    int size; // Size of the subtree (number of nodes)
    int minValue; // Minimum value in the subtree
    int maxValue; // Maximum value in the subtree
    int sum; // Sum of all node values in the subtree

    public SubtreeInfo(boolean isBST, int size, int minValue, int maxValue, int sum) {
      this.isBST = isBST;
      this.size = size;
      this.minValue = minValue;
      this.maxValue = maxValue;
      this.sum = sum;
    }
  }

  int totalSum = 0;

  // Function to calculate the sum of all BSTs with at least 3 nodes
  public int sumBSTs(TreeNode root) {
    traverse(root);
    return totalSum;
  }

  // Post-order traversal helper function to process each subtree
  private SubtreeInfo traverse(TreeNode node) {
    // Base case: an empty subtree is considered a valid BST with size 0
    if (node == null) {
      return new SubtreeInfo(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
    }

    // Recursively process the left and right subtrees
    SubtreeInfo leftInfo = traverse(node.left);
    SubtreeInfo rightInfo = traverse(node.right);

    // Check if the current subtree is a valid BST
    boolean isBST =
        leftInfo.isBST
            && rightInfo.isBST
            && node.val > leftInfo.maxValue
            && node.val <= rightInfo.minValue;

    // Calculate the size and sum of the current subtree
    int currentSize = leftInfo.size + rightInfo.size + 1;
    int currentSum = leftInfo.sum + rightInfo.sum + node.val;

    // Update the minimum and maximum values in the current subtree
    int currentMinValue = Math.min(node.val, leftInfo.minValue);
    int currentMaxValue = Math.max(node.val, rightInfo.maxValue);

    // If the current subtree is a valid BST and contains at least 3 nodes, add its sum to the total
    // sum
    if (isBST && currentSize >= 3) {
      totalSum += currentSum;
    }

    // Return information about the current subtree
    return new SubtreeInfo(isBST, currentSize, currentMinValue, currentMaxValue, currentSum);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A04SumOfBSTs solution = new A04SumOfBSTs();

    // Example 1: Binary tree input
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(5);
    root.right = new TreeNode(15);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(8);
    root.right.left = new TreeNode(12);
    root.right.right = new TreeNode(20);

    // Sum of all valid BSTs with at least 3 nodes
    System.out.println(
        "Sum of all BSTs with at least 3 nodes: " + solution.sumBSTs(root)); // Output: 61
  }
```









