**Problem Restatement**
You need to determine whether a given graph (directed or undirected) contains a cycle. In a graph, a **cycle** is a path that starts and ends at the same node without retracing edges in between.

---

## 1. Cycle Detection in a **Directed** Graph

### Key Idea

Use **Depth‐First Search (DFS)** while maintaining a **recursion stack** (`recStack[]`). If during DFS you visit a neighbor that is already in the current recursion stack, you have found a cycle.

### Steps

1. **Initialize** two arrays of size = number of vertices, `n`:

   * `visited[]` (all false initially)
   * `recStack[]` (all false initially)

2. **For** each node `u` in the graph, if `visited[u]` is false, call `dfsDirected(u)`.

3. **DFS Function** `dfsDirected(u)`:

   * Mark `visited[u] = true` and `recStack[u] = true`.
   * **For each** neighbor `v` of `u`:

     * If `visited[v]` is false, recursively call `dfsDirected(v)`.

       * If that recursion ever returns true, return true immediately.
     * Else if `recStack[v]` is true (meaning `v` is in the current DFS call path), return true (cycle found).
   * After exploring all neighbors, set `recStack[u] = false` (backtrack) and return false.

4. If any call to `dfsDirected(u)` returns true, the graph has a cycle. Otherwise, it does not.

### Java Code (Directed)

```java
package medium.graphs;

import java.util.*;

public class A03CycleInGraph {

  // DFS helper for directed cycle detection
  private static boolean dfsDirected(
      int u, boolean[] visited, boolean[] recStack, Map<Integer, List<Integer>> graph) {
    visited[u] = true;
    recStack[u] = true;

    List<Integer> neighbors = graph.get(u);
    if (neighbors != null) {
      for (int v : neighbors) {
        if (!visited[v]) {
          if (dfsDirected(v, visited, recStack, graph)) {
            return true;  // cycle found in deeper recursion
          }
        } else if (recStack[v]) {
          return true;  // v is in the current DFS path → cycle
        }
      }
    }

    // Done exploring u; remove it from recursion stack
    recStack[u] = false;
    return false;
  }

  // Main function to check for cycles in a directed graph
  public static boolean containsCycleDirected(Map<Integer, List<Integer>> graph) {
    int n = graph.size();
    boolean[] visited  = new boolean[n];
    boolean[] recStack = new boolean[n];

    for (int u : graph.keySet()) {
      if (!visited[u]) {
        if (dfsDirected(u, visited, recStack, graph)) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    // Example directed graph with a cycle: 0→1→2→0
    Map<Integer, List<Integer>> directedGraph = new HashMap<>();
    directedGraph.put(0, Arrays.asList(1));
    directedGraph.put(1, Arrays.asList(2));
    directedGraph.put(2, Arrays.asList(0, 3));
    directedGraph.put(3, Arrays.asList(3)); 
    // Note: 3→3 is a self‐loop, also counts as a cycle.

    System.out.println("Cycle in directed graph: " +
        containsCycleDirected(directedGraph));  // true
  }
}
```

* **Time Complexity:** O(V + E) — each vertex and edge is visited once.
* **Space Complexity:** O(V) — for `visited[]`, `recStack[]`, and the recursive call stack.

---

## 2. Cycle Detection in an **Undirected** Graph

### Key Idea

In an undirected graph, a naive DFS might think “parent → child → parent” is a cycle. To avoid that, track the **parent** node. Whenever you see a neighbor `v` that is already visited **and** `v` is not the parent of the current node, you have found a cycle.

### Steps

1. **Initialize** a `visited[]` array of size `n`, all false.

2. **For** each node `u` in the graph, if `visited[u]` is false, call `dfsUndirected(u, parent = -1)`.

3. **DFS Function** `dfsUndirected(u, parent)`:

   * Mark `visited[u] = true`.
   * **For each** neighbor `v` of `u`:

     * If `!visited[v]`, recursively call `dfsUndirected(v, u)`.

       * If that returns true, immediately return true.
     * Else if `v != parent`, return true (found visited neighbor that is not the node's parent → cycle).
   * Return false if no cycle found deeper.

4. If any call returns true, the graph has a cycle; otherwise, it does not.

### Java Code (Undirected)

```java
package medium.graphs;

import java.util.*;

public class A03CycleInGraph {

  // DFS helper for undirected cycle detection
  private static boolean dfsUndirected(
      int u, int parent, boolean[] visited, Map<Integer, List<Integer>> graph) {
    visited[u] = true;

    List<Integer> neighbors = graph.get(u);
    if (neighbors != null) {
      for (int v : neighbors) {
        if (!visited[v]) {
          if (dfsUndirected(v, u, visited, graph)) {
            return true;  // cycle found deeper
          }
        } else if (v != parent) {
          // visited v that is not parent → cycle
          return true;
        }
      }
    }
    return false;
  }

  // Main function to check for cycles in an undirected graph
  public static boolean containsCycleUndirected(Map<Integer, List<Integer>> graph) {
    int n = graph.size();
    boolean[] visited = new boolean[n];

    for (int u : graph.keySet()) {
      if (!visited[u]) {
        if (dfsUndirected(u, -1, visited, graph)) {
          return true;
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    // Example undirected graph with a cycle: 0–1–2–0
    Map<Integer, List<Integer>> undirectedGraph = new HashMap<>();
    undirectedGraph.put(0, Arrays.asList(1, 2));
    undirectedGraph.put(1, Arrays.asList(0, 2));
    undirectedGraph.put(2, Arrays.asList(0, 1, 3));
    undirectedGraph.put(3, Arrays.asList(2));

    System.out.println("Cycle in undirected graph: " +
        containsCycleUndirected(undirectedGraph));  // true
  }
}
```

* **Time Complexity:** O(V + E) — every edge and vertex is visited once.
* **Space Complexity:** O(V) — for `visited[]` and recursion stack.

---

### Summary of Methods

| Graph Type | Method                   | Time   | Space | Comment                                                      |
| ---------- | ------------------------ | ------ | ----- | ------------------------------------------------------------ |
| Directed   | DFS + `recStack[]`       | O(V+E) | O(V)  | If a neighbor is already “in recursion stack,” cycle exists. |
| Undirected | DFS + pass `parent` node | O(V+E) | O(V)  | If a neighbor is visited and not the parent, cycle exists.   |

Use **`containsCycleDirected(...)`** for directed graphs, and **`containsCycleUndirected(...)`** for undirected graphs. Both run in linear time and use linear extra space to track visitation state.
