**Problem Explanation**
You have a **sorted** array of **distinct** integers, for example:

```
[1, 2, 5, 7, 10, 13, 14, 15, 22]
```

Your goal is to build a **binary search tree (BST)** whose height is as **small** as possible. In a BST:

* Every node’s left‐child subtree contains only values **less** than the node’s value.
* Every node’s right‐child subtree contains only values **greater** than the node’s value.

A perfectly balanced BST (where each subtree is roughly the same size) has the **minimum possible height** for the given number of nodes. In general, the **height** of a tree is the number of edges on the longest path from the root to a leaf; we minimize that value.

---

## Why the “Middle‐Element” Strategy Works

Because the input array is already **sorted**, the easiest way to ensure balance is:

1. Take the **middle** element of the array and make it the **root** of your BST.
2. Recursively do the same for the **left half** of the array (this becomes the root’s left subtree) and for the **right half** (this becomes the root’s right subtree).

By always splitting the remaining elements into two halves of as even size as possible, you guarantee that no branch becomes significantly deeper than any other. This yields a BST of minimal height.

---

## Step‐by‐Step Solution

1. **Define a BST node class** that stores an integer value plus `left`/`right` child references.

2. **Write a recursive helper**:

   * Input parameters: the sorted array, a `start` index, and an `end` index.
   * **Base case:** if `start > end`, return `null` (no node to create).
   * Otherwise, compute the array’s middle index:

     ```java
     int mid = (start + end) / 2;
     ```
   * Create a new BST node with value `array[mid]`.
   * Recursively build its left subtree from the subarray `start … mid-1`.
   * Recursively build its right subtree from the subarray `mid+1 … end`.
   * Return this newly created node as the root of that subtree.

3. The **public** function simply kicks off this recursion over the entire array (`start = 0`, `end = array.length − 1`) and returns the root of the full tree.

4. (Optional) To verify, you can do an **in‐order** traversal of the resulting BST. In‐order on a valid BST yields the original sorted sequence.

---

## Java Code

```java
package medium.binarysearchtrees;

public class MinHeightBST {

  // 1) Definition of a BST node
  static class BST {
    int value;
    BST left;
    BST right;

    public BST(int value) {
      this.value = value;
      this.left  = null;
      this.right = null;
    }
  }

  /**
   * Public method to create a BST of minimal height from a sorted array.
   * 
   * @param array a sorted array of distinct integers
   * @return the root node of a height-balanced BST
   */
  public BST minHeightBST(int[] array) {
    // Kick off the recursive construction
    return constructMinHeightBST(array, 0, array.length - 1);
  }

  /**
   * Recursive helper that builds a BST from array[start..end].
   *
   * @param array the sorted input array
   * @param start the starting index of the current subarray
   * @param end   the ending index of the current subarray
   * @return the root of a BST built from array[start..end], or null if start > end
   */
  private BST constructMinHeightBST(int[] array, int start, int end) {
    // 2a) Base case: no elements left
    if (start > end) {
      return null;
    }

    // 2b) Pick the middle element as root to keep the tree balanced
    int mid = (start + end) / 2;
    BST root = new BST(array[mid]);

    // 2c) Recursively build the left subtree from elements before mid
    root.left = constructMinHeightBST(array, start, mid - 1);

    // 2d) Recursively build the right subtree from elements after mid
    root.right = constructMinHeightBST(array, mid + 1, end);

    return root;
  }

  /**
   * In-order traversal (left, root, right) for testing.
   * If the tree is a valid BST, this prints the original sorted array.
   */
  public void printInOrder(BST root) {
    if (root == null) {
      return;
    }
    printInOrder(root.left);
    System.out.print(root.value + " ");
    printInOrder(root.right);
  }

  // 3) Example usage
  public static void main(String[] args) {
    MinHeightBST builder = new MinHeightBST();
    int[] array = {1, 2, 5, 7, 10, 13, 14, 15, 22};

    BST root = builder.minHeightBST(array);
    System.out.println("In-order traversal of the constructed BST:");
    builder.printInOrder(root);
    // Expected output (same as input, since it's a valid BST):
    // 1 2 5 7 10 13 14 15 22
  }

  /*
   * Complexity Analysis:
   *
   * Time Complexity:
   * - Each call to constructMinHeightBST splits the array roughly in half.
   * - You create exactly n nodes (one per array element).
   * - Each element is used exactly once, so the total time is O(n).
   *
   * Space Complexity:
   * - The recursion stack goes as deep as the height of the tree.
   * - For a balanced tree built from n elements, the height is O(log n).
   * - Therefore, the call-stack space is O(log n) in the best (balanced) case.
   * - In the worst case (if the array were already “degenerate,” but here it’s sorted and
   *   we always pick the middle, so we remain balanced), it would be O(n).
   * - We also allocate O(n) total nodes, but that’s the output itself, not “extra” space beyond the tree.
   */
}
```

---

### How This Works (Beginner’s Perspective)

1. **Why pick the middle element?**
   A BST is “balanced” when its left and right subtrees have nearly the same number of nodes. The height of a tree is minimized when each subtree is as even as possible. By choosing `array[mid]` as the root, we ensure that exactly half (or half–1) of the remaining elements go to the left subtree and half (or half–1) to the right subtree. This splits the problem into two smaller, similar subproblems of size roughly $n/2$, leading to a tree of height approximately $\log_2(n)$.

2. **Recursion stopping condition**

   * If `start > end`, the subarray is empty—there are no elements left to turn into nodes, so we return `null`.

3. **Building left and right subtrees**

   * After making the “middle” element into a node, we handle everything **to the left** of `mid` as a separate BST (recursive call).
   * Then we handle everything **to the right** of `mid` as another separate BST.
   * Each call returns a subtree you attach to either `root.left` or `root.right`.

4. **Verifying via in-order traversal**

   * An in-order walk of any BST prints its elements in sorted order. If you print the new tree in‐order, you should see exactly the original sorted array.

5. **Complexity in simple terms**

   * You touch each array element exactly once to create a node → that’s **O(n)** time.
   * Each recursive call uses constant work besides its two child calls, so we don’t multiply by anything more than 2 each time. The height of recursion is the height of the resulting tree (balanced at \~$\log n$), so the extra “call stack” uses O(log n) space.

This divide‐and‐conquer approach—pick middle, recurse on left half, recurse on right half—is a classic way to convert any sorted array into a height‐balanced BST.
