**Problem Restatement**
You have a **binary search tree** (BST) of integers and a positive integer **k**. You need to return the **kᵗʰ largest** value in that BST. By “kᵗʰ largest,” we mean if you listed all the tree’s values in **ascending** order, the kᵗʰ largest is the element at index (size – k). For example, if the in‐order of the BST is `[1, 2, 3, 5, 5, 15, 17, 20, 22]` and `k = 3`, the 3ʳᵈ largest is `17`.

---

## Why a BST Helps

In a BST:

* All values in the **left subtree** of any node are **smaller** than that node’s value.
* All values in the **right subtree** are **greater** than that node’s value.

Because of this ordering, an **in‐order traversal** (left → node → right) produces a **sorted list in ascending order**. Consequently, to find the **kᵗʰ largest**, you could traverse the whole tree in‐order, collect the values in a list, and then index from the back. That’s the **brute‐force** approach.

However, if you only need the kᵗʰ largest—without collecting everything—you can do a “reverse in‐order” traversal (right → node → left), visiting the tree’s values **from largest down to smallest**. As soon as you’ve visited k nodes, the current node’s value is the kᵗʰ largest, and you can stop.

---

## 1. Brute‐Force Solution (O(n) Time • O(n) Space)

1. **In‐Order traverse** the BST and append each node’s value into a `List<Integer> inOrderList`.

   * By the time you finish, `inOrderList` is sorted ascending.
2. If the list has length `N`, the **kᵗʰ largest** is at index `N – k`. Return `inOrderList.get(N – k)`.

### Java Code for Brute Force

```java
// Definition of a BST node
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

// 1) In-order traversal to collect values
private static void inOrderTraversal(TreeNode node, List<Integer> list) {
  if (node == null) return;
  inOrderTraversal(node.left, list);
  list.add(node.value);
  inOrderTraversal(node.right, list);
}

// 2) Brute-force method
public static int findKthLargestBruteForce(TreeNode root, int k) {
  List<Integer> inOrderList = new ArrayList<>();
  inOrderTraversal(root, inOrderList);
  int n = inOrderList.size();
  // k-th largest is the element at index (n - k)
  return inOrderList.get(n - k);
}
```

* **Time Complexity:**

  * The in‐order traversal visits **all n nodes** exactly once → **O(n)**.
  * Getting `inOrderList.get(n - k)` is constant time.
    → **Total:** O(n).
* **Space Complexity:**

  * We build a list of size **n** to store all node values → **O(n)**.

---

## 2. Optimized Solution: Reverse In‐Order Traversal (O(h + k) Time • O(h) Space)

Instead of collecting all values, we can traverse from **largest to smallest** (right → node → left) and **stop as soon as we’ve visited k nodes**:

1. Maintain a **counter** `count = 0` and a variable `result = -1`.
2. Perform a **recursive** “reverse in‐order”:

   * Recurse into `node.right` (the subtree of larger values).
   * When coming back, increment `count`. If `count == k`, set `result = node.value` and **return early**.
   * If you still need more, recurse into `node.left`.
3. Once `count` reaches k, you’ve found the kᵗʰ largest.

Because we’ll visit at most **k nodes plus the height** of the tree on the way down, this takes **O(h + k)** time, where **h** is the tree’s height. In a balanced BST, **h = O(log n)**, so this is **very efficient** when k is small. The recursion stack uses O(h) space.

### Java Code for Optimized Method

```java
public class KthLargestInBST {
  // Counter of how many nodes we’ve seen so far
  static int count = 0;
  // To store the k-th largest once we find it
  static int result = -1;

  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      this.left  = null;
      this.right = null;
    }
  }

  // Public method that resets count/result and starts recursion
  public static int findKthLargestOptimized(TreeNode root, int k) {
    count = 0;
    result = -1;
    reverseInOrderTraversal(root, k);
    return result;
  }

  // Helper: reverse in-order (right -> node -> left)
  private static void reverseInOrderTraversal(TreeNode node, int k) {
    if (node == null || count >= k) {
      return;  // either no node, or we already found k nodes
    }

    // 1) Visit right subtree first (largest values)
    reverseInOrderTraversal(node.right, k);

    // 2) “Visit” this node
    count++;
    if (count == k) {
      result = node.value;
      return; // we found the k-th largest, so we can stop
    }
    

    // 3) Visit left subtree only if we still need more
    reverseInOrderTraversal(node.left, k);
  }

  // Example usage and test
  public static void main(String[] args) {
    /*
         15
        /  \
       5    20
      / \   / \
     2   5 17 22
    / \
   1   3

   In-order (ascending): [1, 2, 3, 5, 5, 15, 17, 20, 22]
   k=3 → 3rd largest = 17
    */
    TreeNode root = new TreeNode(15);
    root.left = new TreeNode(5);
    root.right = new TreeNode(20);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(17);
    root.right.right = new TreeNode(22);
    root.left.left.left = new TreeNode(1);
    root.left.left.right = new TreeNode(3);

    int k = 3;
    System.out.println(findKthLargestBruteForce(root, k));     // 17
    System.out.println(findKthLargestOptimized(root, k));      // 17
  }
}
```

* **Time Complexity:**

  * In the worst case, you traverse down the rightmost path (height **h**) plus visit **k** nodes before stopping → **O(h + k)**.
  * If the BST is balanced, **h = O(log n)**, so it becomes **O(log n + k)**.
* **Space Complexity:**

  * The recursion stack grows at most **h** deep → **O(h)**.
  * In a balanced tree, **h = O(log n)** (worst‐case unbalanced: **O(n)**).

---

## Summary of Both Methods

| Approach                     | Time Complexity | Space Complexity | When to Use                                 |
| ---------------------------- | --------------- | ---------------- | ------------------------------------------- |
| Brute Force (in‐order list)  | O(n)            | O(n)             | If you don’t mind storing all values.       |
| Optimized (reverse in‐order) | O(h + k)        | O(h)             | When you only need the kᵗʰ largest quickly. |

* The **brute‐force** approach is simpler conceptually (just gather all values in sorted order, then index).
* The **optimized** approach saves space by not building a giant list and can terminate early once you find the kᵗʰ largest. It is especially fast when k is small relative to n.

Both implementations above will correctly print `17` for the sample tree with `k = 3`.
