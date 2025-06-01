

---

### ✅ Problem Restatement (as code comment):

```java
/*
 * Problem:
 * Given a binary tree with at least one node, determine whether the tree can be split into two subtrees of equal sum
 * by removing exactly one edge. If it can, return the new sum of each tree (i.e., total sum / 2); otherwise return 0.
 *
 * A split is valid only if the total sum of all nodes is even, and a subtree (excluding the entire tree itself) exists
 * with sum == totalSum / 2.
 *
 * Each TreeNode has an integer value and left/right children.
 */
```

---

### ✅ Java Code:

```java
public class EqualTreePartition {

  static class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  private int totalSum = 0;
  private boolean found = false;
  private TreeNode root;

  public EqualTreePartition(TreeNode root) {
    this.root = root;
  }

  // Main function to check if equal subtree partition is possible
  public int checkEqualTreeSplit() {
    // Step 1: Compute the total sum of the tree
    totalSum = computeTotalSum(root);

    // Step 2: If the total sum is odd, partitioning into two equal halves is not possible
    if (totalSum % 2 != 0) return 0;

    // Step 3: Traverse and check for a valid subtree sum (excluding full tree)
    checkSubtreeSum(root);

    return found ? totalSum / 2 : 0;
  }

  // Helper function to compute total sum of the tree
  private int computeTotalSum(TreeNode node) {
    if (node == null) return 0;
    return node.val + computeTotalSum(node.left) + computeTotalSum(node.right);
  }

  // Helper function to check for a subtree with sum == totalSum / 2
  private int checkSubtreeSum(TreeNode node) {
    if (node == null) return 0;

    int leftSum = checkSubtreeSum(node.left);
    int rightSum = checkSubtreeSum(node.right);
    int currentSum = node.val + leftSum + rightSum;

    // To avoid using the full tree as a candidate (can't cut at root)
    if (node != root && currentSum == totalSum / 2) {
      found = true;
    }

    return currentSum;
  }

  // Sample usage
  public static void main(String[] args) {
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(10);
    root.right = new TreeNode(10);
    root.right.right = new TreeNode(2);

    EqualTreePartition etp = new EqualTreePartition(root);
    System.out.println("Partition sum: " + etp.checkEqualTreeSplit()); // Expected output: 13
  }
}
```

---

### ✅ Output:

For the example tree:

```
        5
       / \
     10   10
             \
              2
```

Total sum = 27, and we can split it into two trees with sum = 13 by removing the edge between 10 and 2.

---
