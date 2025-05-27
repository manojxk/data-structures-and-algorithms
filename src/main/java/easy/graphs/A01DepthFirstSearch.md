**Problem Explanation**

You have a **directed** graph represented by an **adjacency list** (a map from each node to the list of its neighbors). You want to perform a **Depth-First Search (DFS)** starting from a given node. DFS explores as far as possible along each branch before backtracking, visiting each node **once**.

For example, given the graph:

```java
0: [1, 2]
1: [2]
2: [0, 3]
3: [3]
```

and starting node `2`, a valid DFS order is `[2, 0, 1, 3]`.

---

## Approach

1. **Visited Tracking**
   Maintain a boolean array `visited[]` sized to the number of nodes (so you don’t revisit nodes and enter cycles).

2. **Recursive DFS Helper**
   Write a function `dfs(node, visited, graph, traversal)` that:

   * Marks `node` as visited.
   * Appends `node` to the `traversal` list.
   * Iterates over each neighbor of `node`:

     * If the neighbor is **not** yet visited, recursively call `dfs(neighbor, …)`.

3. **Driver Method**

   * Initialize `visited` and an empty `traversal` list.
   * Call the helper on the `startNode`.
   * Return the filled `traversal` list.

---

## Step-by-Step Solution

1. **Build** the adjacency list (`Map<Integer,List<Integer>> graph`).
2. **Initialize**:

   ```java
   boolean[] visited = new boolean[graph.size()];
   List<Integer> traversal = new ArrayList<>();
   ```
3. **Start** DFS:

   ```java
   dfs(startNode, visited, graph, traversal);
   ```
4. **Return** `traversal`.

In the helper `dfs(node, visited, graph, traversal)`:

* **Mark** `visited[node] = true`.
* **Add** `node` to `traversal`.
* **For** each `neighbor` in `graph.get(node)` (if any):

  * **If** `!visited[neighbor]`, call `dfs(neighbor, ...)`.

---

## Java Implementation

```java
package easy.graphs;

import java.util.*;

public class A01DepthFirstSearch {

  /**
   * Recursive helper for DFS.
   * 
   * @param node       the current node being visited
   * @param visited    array marking which nodes have been visited
   * @param graph      adjacency list of the graph
   * @param traversal  list to collect the DFS visitation order
   */
  public static void dfs(
      int node,
      boolean[] visited,
      Map<Integer, List<Integer>> graph,
      List<Integer> traversal
  ) {
    // 1) Mark current node visited and record it
    visited[node] = true;
    traversal.add(node);

    // 2) Recurse on each unvisited neighbor
    List<Integer> neighbors = graph.get(node);
    if (neighbors != null) {
      for (int neighbor : neighbors) {
        if (!visited[neighbor]) {
          dfs(neighbor, visited, graph, traversal);
        }
      }
    }
  }

  /**
   * Performs a DFS traversal of the graph from startNode.
   *
   * @param startNode  node to start DFS from
   * @param graph      adjacency list of the graph
   * @return           list of nodes in the order they were visited
   */
  public static List<Integer> depthFirstSearch(
      int startNode,
      Map<Integer, List<Integer>> graph
  ) {
    boolean[] visited = new boolean[graph.size()];
    List<Integer> traversal = new ArrayList<>();

    // Kick off the recursion
    dfs(startNode, visited, graph, traversal);

    return traversal;
  }

  public static void main(String[] args) {
    // 1) Build the graph
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(0, Arrays.asList(1, 2));
    graph.put(1, Arrays.asList(2));
    graph.put(2, Arrays.asList(0, 3));
    graph.put(3, Arrays.asList(3));

    int startNode = 2;

    // 2) Perform DFS
    List<Integer> result = depthFirstSearch(startNode, graph);

    // 3) Output the traversal order
    System.out.println("DFS Traversal from Node " 
                       + startNode + ": " + result);
    // Expected: [2, 0, 1, 3]
  }

  /*
   * Time Complexity: O(V + E)
   * - We visit each vertex (V) once, and explore each adjacency list (total edges E) once.
   *
   * Space Complexity: O(V)
   * - The visited array is size V.
   * - The recursion stack can grow up to O(V) in the worst case.
   */
}
```

---

### Complexity Analysis

* **Time:** O(V + E), where

  * V = number of vertices,
  * E = number of edges.
* **Space:** O(V), for the `visited` array and recursion stack.
