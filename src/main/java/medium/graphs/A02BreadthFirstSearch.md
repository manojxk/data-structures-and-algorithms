**Problem Explanation**
You have an undirected graph represented by an **adjacency list**, and you need to perform a **Breadth-First Search (BFS)** starting from a given node. In BFS, you visit all of a node’s neighbors before moving on to neighbors’ neighbors—i.e., you explore the graph **level by level**.

Concretely, given a graph such as:

```
0: [1, 2]
1: [0, 3, 4]
2: [0, 4]
3: [1]
4: [1, 2]
```

and a start node `0`, a BFS traversal would visit nodes in this order:

```
0 → (then its neighbors) 1, 2 → (then neighbors of 1 and 2 not yet visited) 3, 4
```

So the output list is `[0, 1, 2, 3, 4]`.

---

## Approach (Level-Order Exploration with a Queue)

1. **Maintain a queue** of nodes to visit, initialized with the start node.
2. **Maintain a `visited[]` array** (or set) so you never enqueue and process the same node twice.
3. **While** the queue is not empty:

   * **Dequeue** the front node, call it `current`.
   * **Add** `current` to your output/traversal list.
   * **For each neighbor** of `current`, if it has not yet been visited:

     * Mark it visited
     * Enqueue it
4. At the end, your traversal list holds the BFS order of all nodes reachable from the start.

Because you enqueue each node exactly once and examine each edge exactly once, the time complexity is $O(V + E)$, where $V$ = number of vertices, $E$ = number of edges.

---

## Step-by-Step Walkthrough

1. **Initialization**

   * Suppose the graph has $V$ nodes labeled $0\ldots V-1$.
   * Create `boolean[] visited = new boolean[V]`, all initially `false`.
   * Create an empty `List<Integer> traversal` to record the visit order.
   * Create a `Queue<Integer> queue = new LinkedList<>()`.
   * Mark the `startNode` as visited (`visited[startNode] = true`), and enqueue it (`queue.add(startNode)`).

2. **Main Loop**

   ```java
   while (!queue.isEmpty()) {
     int current = queue.poll();      // Dequeue front of queue
     traversal.add(current);          // Record that we “visited” it now

     // For every neighbor of current:
     for (int neighbor : graph.get(current)) {
       if (!visited[neighbor]) {
         visited[neighbor] = true;    // Mark as soon as we enqueue
         queue.add(neighbor);
       }
     }
   }
   ```

   * You always process nodes in the exact order they were enqueued, ensuring all distance-1 neighbors of `startNode` come first, then all distance-2 neighbors, etc.

3. **Result**
   Once the queue is empty, `traversal` contains the BFS order: every node reachable from `startNode`, in increasing “layers” of graph distance.

---

## Java Code

```java
package medium.graphs;

import java.util.*;

public class A02BreadthFirstSearch {

  /**
   * Performs Breadth-First Search (BFS) on an undirected graph, starting from 'startNode'.
   *
   * @param startNode The node from which to begin BFS
   * @param graph     Adjacency list representation: graph.get(u) is a List<Integer> of neighbors of u
   * @return          A List<Integer> of nodes in the order they are visited by BFS
   *
   * Time Complexity:  O(V + E), where
   *   V = number of vertices (graph.size())
   *   E = total number of edges (sum of neighbor-list lengths)
   *
   * Space Complexity: O(V), for the visited[] array and the queue
   */
  public static List<Integer> bfs(int startNode, Map<Integer, List<Integer>> graph) {
    int V = graph.size();
    List<Integer> traversal = new ArrayList<>();
    boolean[] visited = new boolean[V];
    Queue<Integer> queue = new LinkedList<>();

    // Mark the start node as visited and enqueue it
    visited[startNode] = true;
    queue.add(startNode);

    // Process until no more nodes are in the queue
    while (!queue.isEmpty()) {
      int current = queue.poll();       // Dequeue the next node
      traversal.add(current);           // “Visit” it

      // Enqueue all unvisited neighbors
      List<Integer> neighbors = graph.get(current);
      if (neighbors != null) {
        for (int neighbor : neighbors) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.add(neighbor);
          }
        }
      }
    }

    return traversal;
  }

  public static void main(String[] args) {
    // Example graph (undirected, adjacency list)
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(0, Arrays.asList(1, 2));
    graph.put(1, Arrays.asList(0, 3, 4));
    graph.put(2, Arrays.asList(0, 4));
    graph.put(3, Arrays.asList(1));
    graph.put(4, Arrays.asList(1, 2));
    // If a node has no neighbors, its list can be empty or omitted

    int startNode = 0;
    List<Integer> result = bfs(startNode, graph);
    System.out.println("BFS Traversal from Node " + startNode + ": " + result);
    // Expected: [0, 1, 2, 3, 4]
  }
}
```

### Explanation of Key Lines

* **`boolean[] visited = new boolean[V];`**
  Keeps track of which nodes we’ve already enqueued, so we never add the same node twice.
* **`queue.add(startNode); visited[startNode] = true;`**
  We mark the start as visited before enqueuing, preventing it from being enqueued again.
* **`while (!queue.isEmpty()) { ... }`**
  Continuously dequeue one node, record it, and enqueue its unvisited neighbors.
* **`visited[neighbor] = true; queue.add(neighbor);`**
  We must mark “visited” as soon as we enqueue, not when we dequeue. This prevents enqueuing the same neighbor multiple times if two different nodes share that neighbor.

---

## Complexity Analysis

* **Time Complexity ($O(V + E)$)**

  * Every vertex is enqueued (and dequeued) **once**.
  * Every edge is examined exactly once (when you loop over each node’s adjacency list).
    → total time is proportional to “number of vertices + number of edges.”

* **Space Complexity ($O(V)$)**

  * The `visited[]` array is size $V$.
  * The queue can, in the worst case, hold up to $V$ nodes (for example, if you visit one entire “level” of a balanced tree).
    → overall extra space is $O(V)$.

---

**Summary**

1. Initialize a queue with the start node and mark it visited.
2. Repeatedly dequeue a node, record it, and enqueue all its unvisited neighbors (marking them visited immediately).
3. When the queue is empty, you’ve visited all nodes reachable from the start in FIFO (layered) order.
4. Return the recorded traversal list.
