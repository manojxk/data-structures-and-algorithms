**Problem Restatement**
You have an undirected graph given as an adjacency list in a 2D array `edges`, where `edges[i]` is the list of neighbors of node `i`. Determine whether this graph is **two-colorable** (i.e. bipartite). A graph is bipartite if you can color each node either 0 or 1 so that no two adjacent nodes share the same color. If there is a self-loop at any node (an edge from `i` to `i`), the graph cannot be bipartite.

---

## Key Ideas

1. **Two‐Coloring or “Bipartiteness”**

   * We want to assign each node one of two colors (0 or 1) so that every edge connects nodes of different colors.
   * Equivalently, the graph is bipartite if and only if there is no **odd‐length cycle**. But we don’t have to find cycles explicitly—we can color as we go.

2. **Breadth‐First Search (BFS) Coloring**

   * Pick any uncolored node, color it 0, and start a BFS from it.
   * Whenever you visit a node `u` of color `c`, you try to color each neighbor `v` with `1 - c`.
   * If a neighbor is already colored with the same color `c`, you have found a conflict → not bipartite.

3. **Self‐Loop Check**

   * If `edges[i]` contains `i` itself, that is a self‐loop. No two‐coloring is possible because a node would have to be different from itself. Immediately return `false` in that case.

4. **Disconnected Graphs**

   * The input might represent a graph with multiple connected components. A bipartite coloring on one component does not automatically cover the others.
   * Therefore, after finishing BFS from one uncolored node, you must look for another uncolored node and start BFS from there until all nodes are visited or you find a conflict.

---

## Step‐by‐Step Algorithm

1. **Initialize a `colors[]` array of length `n`** (where `n = edges.length`) with all entries set to `-1`, meaning “uncolored.”

   ```java
   int n = edges.length;
   int[] colors = new int[n];
   Arrays.fill(colors, -1);
   ```

2. **Loop over every node `start` from 0 to `n−1`**:

   * If `colors[start]` is still `-1`, it means we haven’t visited/color‐checked this node yet. We will run a BFS from it:

     ```java
     for (int start = 0; start < n; start++) {
       if (colors[start] == -1) {
         if (!bfsCheck(start, edges, colors)) {
           return false;  // Found a conflict or self‐loop in this component
         }
       }
     }
     ```

3. **BFS Helper `bfsCheck(int start, int[][] edges, int[] colors)`**:

   * Create a queue of integers (node indices).
   * Color `start` with 0 and enqueue it.
   * While the queue is not empty:

     1. Dequeue a node `u`. Let its color be `c = colors[u]`.
     2. For each neighbor `v` of `u` (i.e. each `v` in `edges[u]`):

        * **Self‐Loop Check**: If `v == u`, return `false`.
        * **If `v` is uncolored (`colors[v] == -1`)**, color it `1 - c` and enqueue `v`.
        * **Else if `colors[v] == c`**, then `u` and `v` would share the same color—conflict! Return `false`.
   * If BFS finishes with no conflict, return `true`.

   ```java
   private static boolean bfsCheck(int start, int[][] edges, int[] colors) {
     Queue<Integer> queue = new LinkedList<>();
     colors[start] = 0;     // color start with 0
     queue.offer(start);

     while (!queue.isEmpty()) {
       int u = queue.poll();
       int c = colors[u];

       for (int v : edges[u]) {
         if (v == u) {
           // Self‐loop => cannot be bipartite
           return false;
         }
         if (colors[v] == -1) {
           // Not colored yet => give it the opposite color, then enqueue
           colors[v] = 1 - c;
           queue.offer(v);
         } else if (colors[v] == c) {
           // Neighbor has the same color => conflict
           return false;
         }
       }
     }
     return true;
   }
   ```

4. **If all components pass the BFS check, return `true`.**
   If any BFS check returned `false`, we return `false` immediately.

---

## Full Java Implementation

```java
package medium.graphs;

import java.util.*;

public class TwoColorable {

  /**
   * Determines whether the given undirected graph (adjacency list) is bipartite (two-colorable).
   * 
   * @param edges adjacency list, where edges[i] is an array of neighbors of node i
   * @return true if the graph is two-colorable; false otherwise
   */
  public static boolean isTwoColorable(int[][] edges) {
    int n = edges.length;

    // colors[i] = -1 => uncolored; 0 or 1 => assigned color
    int[] colors = new int[n];
    Arrays.fill(colors, -1);

    // Check each component
    for (int start = 0; start < n; start++) {
      if (colors[start] == -1) {
        // Not visited yet: run BFS from this node
        if (!bfsCheck(start, edges, colors)) {
          return false;
        }
      }
    }
    return true;  // All components can be 2-colored
  }

  /**
   * BFS from 'start' to attempt to color its connected component with two colors.
   * Returns false if a conflict or self-loop is found; true otherwise.
   */
  private static boolean bfsCheck(int start, int[][] edges, int[] colors) {
    Queue<Integer> queue = new LinkedList<>();
    colors[start] = 0;  // Assign color 0 to the start
    queue.offer(start);

    while (!queue.isEmpty()) {
      int u = queue.poll();
      int c = colors[u];

      for (int v : edges[u]) {
        // Self-loop check
        if (v == u) {
          return false;
        }
        // If uncolored, give the neighbor the opposite color
        if (colors[v] == -1) {
          colors[v] = 1 - c;
          queue.offer(v);
        } else if (colors[v] == c) {
          // Conflict: neighbor already has the same color
          return false;
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {
    // Example 1: Triangle (3‐cycle) is not bipartite
    int[][] edges1 = {
      {1, 2},  // 0 connects to 1 and 2
      {0, 2},  // 1 connects to 0 and 2
      {0, 1}   // 2 connects to 0 and 1
    };
    System.out.println("Graph 1 is two-colorable: " + isTwoColorable(edges1));
    // Expected: false (because a 3-cycle is not bipartite)

    // Example 2: A square (4‐cycle) is bipartite
    int[][] edges2 = {
      {1, 3},  // 0 – 1 – 2 – 3 – 0
      {0, 2},
      {1, 3},
      {0, 2}
    };
    System.out.println("Graph 2 is two-colorable: " + isTwoColorable(edges2));
    // Expected: true
  }
}
```

---

## Explanation of Key Steps

1. **Color Array Initialization**

   * We store `colors[i]` = –1 if node `i` hasn’t been colored yet.
   * Once we pick a node to start, we set `colors[start] = 0` (arbitrary choice between 0 and 1).

2. **BFS Traversal**

   * We enqueue the starting node.
   * At each step, we take out node `u` with color `c = colors[u]`. All of its neighbors must be color `1−c`.
   * If we ever discover a neighbor that is already colored with `c`, that is a direct conflict (two adjacent nodes sharing the same color), so we immediately return `false`.

3. **Self‐Loop Check**

   * If an adjacency list contains the node itself (i.e. `v == u`), that is a “self‐loop.” No two‐coloring can satisfy a self‐loop, so we also immediately return `false`.

4. **Disconnected Components**

   * If after finishing BFS from one starting node, there remain uncolored nodes (`colors[i] == –1`), we pick another uncolored node as the start of a new BFS. This ensures every connected component is checked.

5. **Overall Result**

   * Only if every connected component can be properly two‐colored does the function return `true`. Otherwise, the first time a conflict or self‐loop is found, it returns `false`.

---

## Complexity Analysis

* **Time Complexity:** $O(V + E)$

  * We visit each vertex exactly once in the BFS (marking it colored), and for each vertex we inspect all its edges exactly once.
* **Space Complexity:** $O(V)$

  * The `colors[]` array has size $V$.
  * The BFS queue can hold up to $V$ vertices in the worst case.

This completes the explanation and implementation for checking whether a given undirected graph (possibly containing multiple disconnected components) is two‐colorable (bipartite).
