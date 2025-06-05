**Problem Statement**
You have a directed graph with non‐negative edge weights, represented as an adjacency list. Given a starting vertex `start`, find the shortest‐path distance from `start` to every other vertex in the graph. If a vertex is unreachable from `start`, its distance should be reported as `−1`.

---

## 1) Understanding the Problem

* You are given **n** vertices (numbered `0` through `n−1`) and a list of directed edges.
* Each edge from vertex `u` to vertex `v` has a positive weight (cost).
* You must compute, for every vertex `i`, the minimum total weight of any path from `start` to `i`.

  * If no path exists, the answer for that vertex is `−1`.
  * Otherwise, report the sum of edge weights along the cheapest route.

This is exactly the scenario that **Dijkstra’s algorithm** solves, because all weights are non‐negative. Dijkstra’s algorithm is a greedy, breadth‐like search that repeatedly picks the unvisited vertex with the smallest known distance from `start`, “finalizes” it, and uses it to relax (improve) distances to its neighbors.

---

## 2) High‐Level Approach (Dijkstra’s Algorithm)

1. **Initialization**

   * Create an array `distances[]` of size `n`, where `distances[i]` will hold “the shortest known distance from `start` to node i.”
   * Initialize all entries of `distances[]` to “infinity” (or a very large number), except `distances[start] = 0`, since the distance from `start` to itself is 0.
   * Create a boolean array `visited[]` of size `n`, all initially `false`. This marks which vertices have been “finalized” (i.e. we know their shortest distance for sure).

2. **Priority Queue (Min‐Heap)**

   * We need to pick, at each step, the **unvisited** vertex whose `distances[]` value is smallest.
   * Use a `PriorityQueue<int[]>` (min‐heap) keyed by `distances`. Each entry is a pair `[node, distance]`.
   * Initially, push `[start, 0]` into the heap.

3. **Main Loop**

   * While the priority queue is not empty:
     a. Pop the top element `(currentNode, currentDist)`—this is the unvisited vertex with the smallest known distance.
     b. If `currentNode` is already marked `visited`, skip it (we have already finalized it).
     c. Otherwise, mark `visited[currentNode] = true`. Now `distances[currentNode] = currentDist` is guaranteed to be the final shortest distance for `currentNode`.
     d. **Relaxation**: For each directed edge `currentNode → neighbor` with weight `w`:

     * If `visited[neighbor]` is still `false`, compute a “new possible distance”

       ```
       newDist = currentDist + w
       ```
     * If `newDist < distances[neighbor]`, update

       ```
       distances[neighbor] = newDist
       ```

       and push `[neighbor, newDist]` into the priority queue.

4. **Finalize Unreachable Nodes**

   * After the loop, any vertex that still has `distances[i] = INF` was never reached. Replace those entries with `−1` to indicate “unreachable.”

---

## 3) Detailed Code with Explanation

```java
import java.util.*;

public class A01DijkstraAlgorithm {

  /**
   * Computes the shortest distances from ‘start’ to every other vertex in a directed graph
   * with non‐negative weights, using Dijkstra’s algorithm.
   *
   * @param start  The index of the start vertex.
   * @param edges  Adjacency list of size n, where edges.get(u) is a list of pairs [v, w],
   *               meaning there is a directed edge u → v of weight w.
   * @return       An array distances[] of length n, where distances[i] is the shortest
   *               distance from ‘start’ to i, or –1 if i is unreachable.
   */
  public static int[] dijkstra(int start, List<List<int[]>> edges) {
    int n = edges.size();               // Number of vertices
    int[] distances = new int[n];       // distances[i] = shortest known distance to i
    boolean[] visited = new boolean[n]; // visited[i] = whether i’s distance is finalized

    // 1) Initialize all distances to “infinity” (here, Integer.MAX_VALUE).
    //    Then set distances[start] = 0.
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    // 2) Min‐heap ordered by distance. Each element is [node, dist].
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    // Push the start node (distance = 0)
    minHeap.offer(new int[]{ start, 0 });

    // 3) While there are still vertices to process in the heap:
    while (!minHeap.isEmpty()) {
      int[] top = minHeap.poll();
      int currentNode = top[0];
      int currentDist = top[1];

      // If we already visited this node, skip it
      if (visited[currentNode]) {
        continue;
      }
      // Mark as visited (we now finalize distance[currentNode])
      visited[currentNode] = true;

      // 4) Relaxation: for each neighbor of currentNode
      // edges.get(currentNode) is a list of pairs [neighborNode, edgeWeight]
      for (int[] neighborEntry : edges.get(currentNode)) {
        int neighborNode = neighborEntry[0];
        int edgeWeight  = neighborEntry[1];

        // If neighbor is already finalized, no need to relax it
        if (visited[neighborNode]) {
          continue;
        }

        // Proposed new distance to neighbor:
        int newDist = currentDist + edgeWeight;
        // If this new distance is shorter, update and push onto heap
        if (newDist < distances[neighborNode]) {
          distances[neighborNode] = newDist;
          minHeap.offer(new int[]{ neighborNode, newDist });
        }
      }
    }

    // 5) Replace any remaining “infinity” entries with –1 (unreachable)
    for (int i = 0; i < n; i++) {
      if (distances[i] == Integer.MAX_VALUE) {
        distances[i] = -1;
      }
    }

    return distances;
  }

  // Example usage and a quick test in main()
  public static void main(String[] args) {
    // Let’s build a sample directed graph with 6 vertices (0..5).
    // We store adjacency as: edges.get(u) = List of [v, w] pairs meaning “u → v (weight w).”
    int start = 0;  // We will compute shortest paths from vertex 0.

    List<List<int[]>> edges = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      edges.add(new ArrayList<>());
    }

    // Suppose the edges are:
    // 0 → 1 (7)
    edges.get(0).add(new int[]{ 1, 7 });

    // 1 → 2 (6), 1 → 3 (20), 1 → 4 (3)
    edges.get(1).add(new int[]{ 2, 6 });
    edges.get(1).add(new int[]{ 3, 20 });
    edges.get(1).add(new int[]{ 4, 3 });

    // 2 → 3 (14)
    edges.get(2).add(new int[]{ 3, 14 });

    // 3 → 4 (2)
    edges.get(3).add(new int[]{ 4, 2 });

    // 4 has no outgoing edges
    // 5 has no outgoing edges (so 5 is completely disconnected)

    // Now run Dijkstra from vertex 0:
    int[] result = dijkstra(start, edges);

    // Print the array of distances[0..5]
    System.out.println(Arrays.toString(result));
    // Expected output:
    //   [0, 7, 13, 27, 10, -1]
    //
    // Explanation of results:
    // - distances[0] = 0   (start to itself)
    // - distances[1] = 7   (0 → 1 costs 7)
    // - distances[2] = 13  (0 → 1 → 2 costs 7 + 6 = 13)
    // - distances[3] = 27  (0 → 1 → 2 → 3 costs 7 + 6 + 14 = 27) 
    //                     or (0 → 1 → 3 directly costs 7 + 20 = 27; both match)
    // - distances[4] = 10  (0 → 1 → 4 costs 7 + 3 = 10)
    // - distances[5] = -1  (unreachable from 0)
  }
}
```

---

## 4) Step‐by‐Step Explanation

1. **Data Structures**

   * `int[] distances` (size n) – holds our best‐so‐far distance from `start` to each vertex.
   * `boolean[] visited` (size n) – marks whether we have “finalized” that vertex’s distance.
   * `PriorityQueue<int[]> minHeap` – a min‐heap of pairs `[vertex, distance]` ordered by `distance`.

2. **Initialization**

   ```java
   Arrays.fill(distances, Integer.MAX_VALUE);
   distances[start] = 0;
   minHeap.offer(new int[]{start, 0});
   ```

   * We set every node’s distance to “infinity” (here `Integer.MAX_VALUE`), except the start vertex gets distance 0.
   * We push `[start, 0]` into the min‐heap.

3. **Main BFS‐like Loop**

   ```java
   while (!minHeap.isEmpty()) {
     int[] top = minHeap.poll();
     int currentNode = top[0];
     int currentDist = top[1];
     if (visited[currentNode]) continue;

     visited[currentNode] = true;
     // Relax all neighbors of currentNode…
     for (int[] edge : edges.get(currentNode)) {
       int neighborNode = edge[0];
       int edgeWeight  = edge[1];
       if (visited[neighborNode]) continue;
       int newDist = currentDist + edgeWeight;
       if (newDist < distances[neighborNode]) {
         distances[neighborNode] = newDist;
         minHeap.offer(new int[]{neighborNode, newDist});
       }
     }
   }
   ```

   * We pop the smallest‐distance unvisited vertex `(currentNode, currentDist)` from the heap.
   * If it’s already marked `visited`, we skip it (maybe we inserted it earlier with a larger distance, then later found a shorter route to it).
   * Otherwise, we mark `visited[currentNode] = true`, meaning “we now know for sure that `distances[currentNode]` is the shortest possible.”
   * Then we iterate through every outgoing edge from `currentNode` to a neighbor `neighborNode` (with weight `edgeWeight`).
   * If `neighborNode` is not yet finalized (`visited[neighborNode] == false`), we check whether going via `currentNode` shortens `distances[neighborNode]`. If so, we update and push `[neighborNode, newDist]` into the heap.

4. **Finalization of Distances**

   * At the end of that loop, `distances[i]` contains either a finite shortest distance or remains `Integer.MAX_VALUE` if unreachable.

   ```java
   for (int i = 0; i < n; i++) {
     if (distances[i] == Integer.MAX_VALUE) {
       distances[i] = -1;  // mark unreachable
     }
   }
   ```

   * We replace “infinity” with `−1` to indicate that no path from `start` reaches that node.

---

## 5) Time & Space Complexity

1. **Time Complexity: O((V + E) log V)**

   * `V` = number of vertices, `E` = number of edges.
   * Each vertex can be inserted into the min‐heap multiple times, but each time we extract a pair or insert a pair costs O(log V).
   * We extract at most `V` times (once per vertex finalization), costing `O(V log V)`.
   * We relax each edge exactly once (when its source vertex is finalized). Each relaxation may push one new pair into the heap, costing `O(log V)`. Over all edges, that is `O(E log V)`.
   * Sum: `O(V log V + E log V) = O((V + E) log V)`.

2. **Space Complexity: O(V + E)**

   * The adjacency‐list `edges` itself uses `O(V + E)` space.
   * We keep `distances[]` and `visited[]` of size `V`: `O(V)`.
   * The priority queue can hold up to `O(E)` entries in the worst case (every edge relaxation pushes one entry). But since each vertex typically is finalized once (and we skip already `visited[]` ones), the total distinct entries is also on the order of `O(E)`.
   * Overall: **O(V + E)**.

---

## 6) Why This Works & Caveats

* **Why Non‐Negative Weights Matter**
  Dijkstra’s algorithm relies on the invariant that once you pop a vertex from the min‐heap, its distance is final. That only holds if no negative‐weight edges exist—otherwise you might find a shorter route later via a negative cycle. Here, all weights are positive, so once we “lock in” `currentDist` for `currentNode`, no future path can undercut it.

* **Visited Array**
  We mark a node `visited[node] = true` only when we poll it out of the heap. Even if that vertex had appeared in the heap earlier with a larger distance, we skip re‐processing it. This prevents infinite loops on cycles and ensures each node is processed once.

* **Handling Unreachable Vertices**
  If some node cannot be reached from `start`, it never gets extracted with a finite distance; its `distance[i]` remains `Integer.MAX_VALUE`. We then convert that to `−1`.

---

**That completes the step‐by‐step explanation of Dijkstra’s algorithm**—from the problem statement to the code, including how we maintain the priority queue, relax edges, and produce the final distance array.
