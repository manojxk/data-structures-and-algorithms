Below is a complete, step-by-step explanation of the “Find Nodes Distance K” solution, including its approach, why it works, and its time/space complexity.

---

## Problem Restatement

We have a binary tree where every node has a unique integer value. We are given:

* The tree’s root pointer `root`.
* A specific node pointer `target` somewhere in that tree.
* An integer `K ≥ 0`.

We must return the list of all node‐values that lie exactly **K edges** away from the `target` node. In other words, if you start at `target` and walk exactly K steps (each step going to a parent or a child), which nodes do you end up at?

Because in a usual binary tree you cannot move “up” from a child to its parent without extra bookkeeping, the main trick is to first create a mapping from every node ↔ its parent. Once you have that, you can do a standard breadth‐first search (BFS) starting from `target`, allowing moves to left‐child, right‐child, or parent, until you reach distance K.

---

## High-Level Approach

1. **Build a Parent Map**
   Traverse the tree once (e.g. DFS) to record, for each node, its parent node in a `Map<TreeNode, TreeNode>`. In that map:

   * `parentMap.get(x)` returns the parent of `x`, or `null` if `x` is the root.

2. **Breadth-First Search from `target`**

   * Start a BFS (level‐order) from the `target` node. Maintain a `visited` set to avoid revisiting the same node twice.
   * At each step of BFS, you can move to up to three neighbors of any current node:

     1. The left child (if it exists and isn’t visited yet).
     2. The right child (if it exists and isn’t visited yet).
     3. The parent (if it exists and isn’t visited yet).

3. **Count Levels Until K**

   * Initialize `distance = 0` and put `target` into the queue.
   * While the queue is not empty:

     * If `distance == K`, then every node currently in the queue is exactly K steps from the original `target`. Collect their values and return them.
     * Otherwise, increment `distance` by 1, and expand the BFS frontier by dequeuing exactly one full level’s worth of nodes, enqueueing each node’s unvisited neighbors (left, right, parent).

4. **Return Result**

   * As soon as we reach `distance == K`, the current queue contents are all nodes at distance K; extract and return their `val` in a list.
   * If the queue empties before distance K, there simply are no nodes at exactly that distance; return an empty list.

---

## Detailed Code Explanation

```java
public class A02FindNodesDistanceK {

  // Standard TreeNode definition
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int v) { this.val = v; }
  }

  public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    // 1) Build a map from each node to its parent.
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    buildParentMap(root, null, parentMap);

    // 2) Initialize BFS from target.
    Queue<TreeNode> queue = new LinkedList<>();
    Set<TreeNode> visited = new HashSet<>();
    queue.add(target);
    visited.add(target);

    int currentDistance = 0;

    // 3) Standard level‐order BFS, stopping when distance == K.
    while (!queue.isEmpty()) {
      if (currentDistance == K) {
        // All nodes currently in `queue` are exactly K edges from target.
        for (TreeNode node : queue) {
          result.add(node.val);
        }
        return result;
      }

      int size = queue.size();
      // Process exactly one level
      for (int i = 0; i < size; i++) {
        TreeNode curr = queue.poll();

        // a) Check left child
        if (curr.left != null && !visited.contains(curr.left)) {
          visited.add(curr.left);
          queue.add(curr.left);
        }
        // b) Check right child
        if (curr.right != null && !visited.contains(curr.right)) {
          visited.add(curr.right);
          queue.add(curr.right);
        }
        // c) Check parent
        TreeNode parent = parentMap.get(curr);
        if (parent != null && !visited.contains(parent)) {
          visited.add(parent);
          queue.add(parent);
        }
      }

      currentDistance++;
    }

    // If we exhaust the BFS without hitting distance K, return empty.
    return result;
  }

  // Recursively record each node’s parent in parentMap.
  private void buildParentMap(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
    if (node == null) return;
    parentMap.put(node, parent);
    buildParentMap(node.left,  node, parentMap);
    buildParentMap(node.right, node, parentMap);
  }

  // (Main method and testing code omitted for brevity.)
}
```

### Step-by-Step

1. **`buildParentMap(root, null, parentMap)`**

   * Called with `parent = null` at the root.
   * Whenever we recurse into a child, we pass the current node as that child’s parent.
   * At the end, each entry in `parentMap` satisfies: `parentMap.get(child) == child’s parent`.

2. **Initialize BFS**

   ```java
   queue.add(target);
   visited.add(target);
   currentDistance = 0;
   ```

   We mark `target` as visited immediately so we never step back into it from its parent or children.

3. **BFS Loop**

   ```java
   while (!queue.isEmpty()) {
     if (currentDistance == K) {
       // Collect all node.val from queue into result, then return.
     }
     int size = queue.size();
     for (int i = 0; i < size; i++) {
       TreeNode curr = queue.poll();
       // Add any unvisited neighbors: left, right, parent.
     }
     currentDistance++;
   }
   ```

   * We compare `currentDistance` to `K` **before** expanding the next level. The moment `currentDistance == K`, the queue holds exactly those nodes that are K edges from `target`.
   * If `K = 0`, the very first iteration sees `currentDistance == 0`, so we immediately return `[target.val]`.

4. **Neighbor Logic**
   For each dequeued `curr`:

   * If `curr.left` is non‐null and not yet visited, `enqueue(curr.left)` and mark visited.
   * Same for `curr.right`.
   * Then look up `parentMap.get(curr)`. If it exists and is unvisited, enqueue it and mark visited.

   By marking each newly enqueued node as visited immediately, we guarantee each node enters the queue at most once, so the BFS is linear in tree‐size.

5. **Returning the Result**

   * As soon as we discover `currentDistance == K`, we dump every `queue`‐member’s `val` into the `result` list and return it.
   * If we finish the loop (queue becomes empty) without ever doing that, it means the tree does not contain any node at exactly K distance, and we return an empty list.

---

## 4. Time and Space Complexity

* **Time Complexity**: O(n)

  * Building the parent map does one DFS over all `n` nodes → O(n).
  * The subsequent BFS also visits each node at most once → O(n).
  * Total: O(n) + O(n) = O(n).

* **Space Complexity**: O(n)

  1. **Parent Map**: We store an entry for each of the n nodes → O(n) space.
  2. **Visited Set**: Also potentially holds up to n nodes → O(n).
  3. **Queue**: In the worst case (e.g. a very wide level), it might hold O(n) nodes.
  4. **Call Stack** (for `buildParentMap`): In the worst case (skewed tree), recursion depth = O(n).
     All combined is O(n) total space.

---

### Example Walkthrough

For the sample tree:

```
        3
       / \
      5   1
     / \  / \
    6  2 0  8
      / \
     7  4
```

* Build `parentMap` so that each node knows its parent.
* Suppose `target = 5` and `K = 2`.
* Start BFS at distance=0: queue = \[5].

  * distance=0 ≠ 2, so expand:

    * Neighbors of 5: left=6, right=2, parent=3. Enqueue them (visited = {5,6,2,3}).
* Now distance=1: queue = \[6, 2, 3].

  * distance=1 ≠ 2, so expand each:

    1. From `6`: neighbors are parent=5 (already visited), no children. → no new enqueues.
    2. From `2`: neighbors: left=7, right=4, parent=5 (visited). → enqueue 7 and 4 (visited add them).
    3. From `3`: neighbors: left=5 (visited), right=1 → enqueue 1.
  * After processing all three, queue now holds \[7, 4, 1].
  * Mark visited = {5,6,2,3,7,4,1}.
* Now distance=2: queue = \[7, 4, 1], and `currentDistance == K`. We collect their values `[7, 4, 1]` and return.

---

## 5. Final Notes

* The two‐phase approach (build parent pointers, then BFS from `target`) is critical because a plain tree does not let you “go up” to a parent.
* The `visited` set prevents cycles—once you move up to a parent, you must not move directly back down to the same child, or else you’d loop indefinitely.
* Running time is linear in the number of nodes, which is optimal for any exact‐distance BFS in a tree.

That completes the explanation. The solution finds all nodes at exactly K distance from `target` in **O(n)** time and **O(n)** space.
