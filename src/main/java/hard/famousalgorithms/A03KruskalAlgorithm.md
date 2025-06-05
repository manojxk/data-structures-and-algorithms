Below is a complete walkthrough of both **Kruskal’s** and **Prim’s** algorithms for finding a Minimum Spanning Tree (MST) in a weighted, undirected graph. For each, we include:

1. A description of the problem they solve
2. A step‐by‐step explanation of the code you provided
3. Time and space‐complexity analysis

---

## 1) Kruskal’s Algorithm

### 1.1 Problem Statement

Given an undirected, weighted graph $G = (V, E)$, find a subset of edges $T \subseteq E$ that connects all vertices (i.e.\ spans $V$) without forming any cycles, and whose total edge‐weight sum is as small as possible. Such a subset $T$ is called a **Minimum Spanning Tree** (MST).

Kruskal’s algorithm achieves this by **sorting all edges** in non‐decreasing order of weight and then repeatedly adding the smallest edge that does not form a cycle, until we have exactly $|V| - 1$ edges.

---

### 1.2 Key Data Structures

1. **Edge List** (`List<Edge> edges`)

   * We store every edge in a single list, each with `(src, dest, weight)`.
   * We will sort this list by `weight`.

2. **Union‐Find / Disjoint‐Set Union (DSU)**

   * We maintain two arrays:

     * `parent[i]` = “the representative (root) of the set containing vertex $i$”.
     * `rank[i]` = an (approximate) “tree‐height” measure to union by rank.
   * `find(...)` with path compression lets us determine which set (component) a vertex belongs to.
   * `union(...)` merges two sets if they are currently disjoint.

3. **Result List** (`List<Edge> result`)

   * We will collect exactly $|V|-1$ edges here—those that end up in the MST.

---

### 1.3 Step‐by‐Step Code Explanation

```java
public class KruskalAlgorithm {
  // 1) Inner class: represents one edge, implements Comparable so we can sort by weight.
  static class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

    // Sort edges in ascending order of weight:
    public int compareTo(Edge other) {
      return this.weight - other.weight;
    }
  }

  int vertices;        // Number of vertices in the graph (|V|)
  List<Edge> edges;    // All edges (|E|) will be stored here

  // Constructor: given number of vertices, initialize the edge‐list
  KruskalAlgorithm(int vertices) {
    this.vertices = vertices;
    edges = new ArrayList<>();
  }

  // Add an undirected edge (u—v) with a given weight
  void addEdge(int src, int dest, int weight) {
    edges.add(new Edge(src, dest, weight));
  }

  // 2) Union‐Find “find” with path compression
  int find(int[] parent, int i) {
    if (parent[i] != i) {
      parent[i] = find(parent, parent[i]);  // Recursively find the root, then compress path
    }
    return parent[i];
  }

  // 3) Union by rank: attach the smaller‐rank tree under the larger‐rank tree
  void union(int[] parent, int[] rank, int x, int y) {
    int rootX = find(parent, x);
    int rootY = find(parent, y);

    if (rootX == rootY) return;  // Already in the same set

    if (rank[rootX] < rank[rootY]) {
      parent[rootX] = rootY;
    } else if (rank[rootX] > rank[rootY]) {
      parent[rootY] = rootX;
    } else {
      // Same rank → choose one as new root and increment its rank
      parent[rootY] = rootX;
      rank[rootX]++;
    }
  }

  // 4) Build MST via Kruskal’s algorithm
  void kruskalMST() {
    // List to hold our final MST edges (we need exactly vertices − 1 edges)
    List<Edge> result = new ArrayList<>();

    // Step A: Sort all edges in non‐decreasing order of weight
    Collections.sort(edges);

    // Step B: Initialize Union‐Find sets. Each vertex is its own parent, rank = 0
    int[] parent = new int[vertices];
    int[] rank   = new int[vertices];
    for (int i = 0; i < vertices; i++) {
      parent[i] = i;
      rank[i]   = 0;
    }

    // Step C: Iterate over sorted edges, pick the smallest one that doesn’t form a cycle
    for (Edge edge : edges) {
      int rootSrc  = find(parent, edge.src);
      int rootDest = find(parent, edge.dest);

      // If they belong to different sets, adding this edge won’t form a cycle
      if (rootSrc != rootDest) {
        result.add(edge);               // Include this edge in MST
        union(parent, rank, rootSrc, rootDest);
      }

      // If we have included V−1 edges, stop early
      if (result.size() == vertices - 1) {
        break;
      }
    }

    // Step D: Print the edges in the MST
    System.out.println("Following are the edges in the constructed MST:");
    for (Edge edge : result) {
      System.out.println(edge.src + " -- " + edge.dest + " == " + edge.weight);
    }
  }

  // 5) Example usage
  public static void main(String[] args) {
    KruskalAlgorithm graph = new KruskalAlgorithm(4);

    graph.addEdge(0, 1, 10);
    graph.addEdge(0, 2, 6);
    graph.addEdge(0, 3, 5);
    graph.addEdge(1, 3, 15);
    graph.addEdge(2, 3, 4);

    graph.kruskalMST();
  }
}
```

#### How It Works, Step by Step

1. **Edge Collection & Sorting**

   * We store every edge `(u, v, w)` in a list called `edges`.
   * We sort that list by ascending `weight` (using `Collections.sort` and `Edge`’s `compareTo`).

2. **Union‐Find Initialization**

   * We create two arrays of size `V`:

     * `parent[i] = i` initially, so each vertex is its own “set.”
     * `rank[i] = 0` initially.

3. **Processing Each Edge in Sorted Order**

   * For each edge `e` in the now‐sorted list:

     1. Let `rootSrc = find(parent, e.src)` and `rootDest = find(parent, e.dest)`.
     2. If `rootSrc != rootDest`, that means adding edge `e` will not create a cycle (they lie in different components).

        * We `union(rootSrc, rootDest)`, merging the two components in Union‐Find.
        * We add `e` to our MST result list.
     3. Once we’ve selected `V−1` edges, we can stop (a spanning tree on $V$ vertices always has exactly $V-1$ edges).

4. **Result**

   * We end up with a list of $V-1$ edges, each chosen in ascending‐weight order, and none forming a cycle. That is the MST.

---

### 1.4 Complexity Analysis

* **Sorting the Edges**

  * We have $|E|$ edges. Sorting them by weight takes $O(|E|\log |E|)$.
  * Since $\lvert E\rvert$ can be at most $V^2$ in a dense graph, it’s also sometimes written $O(E \log V)$. Usually we say $O(E\log E)$.

* **Union‐Find Operations**

  * We process each edge exactly once in the loop.
  * Each time we do a `find(...)` (and possibly a `union(...)`). With path compression + union by rank, each operation is effectively $O(\alpha(V))$, where $\alpha$ is the inverse Ackermann function (practically constant).
  * So processing all $E$ edges for cycle‐checks + unions is $O(E \,\alpha(V)) \approx O(E)$.

* **Total Time Complexity**

  $$
    O\bigl(\,E \log E \;+\; E\,\alpha(V)\bigr) \;=\; O(E \log E)\approx O(E\log V).
  $$

* **Space Complexity**

  * We store all edges in a list: $O(E)$.
  * We keep two arrays `parent[]` and `rank[]` of length $V$: $O(V)$.
  * The result list holds $V-1$ edges: $O(V)$.
  * Total: $\boxed{O(V + E)}$.

---

## 2) Prim’s Algorithm

### 2.1 Problem Statement

Again, we want an MST of an undirected, weighted graph $G=(V,E)$. Prim’s approach grows one spanning tree at a time, always adding the cheapest edge that expands the current tree by exactly one new vertex. We continue until all vertices are included.

---

### 2.2 Key Data Structures

1. **Adjacency Matrix or List**

   * In your code, you used an adjacency‐matrix `int[][] graph`, where `graph[u][v]` is the weight of the edge $u\text{–}v$, or 0 if none.
   * We also maintain an array `key[v]` = “the minimum weight edge that can connect vertex $v$ to the tree grown so far.”
   * A boolean array `inMST[v]` = “whether $v$ is already included in the MST.”
   * An array `parent[v]` = “which vertex in the MST currently connects to $v$ with that minimum weight.”
   * A min‐heap (priority queue) of `(vertex, key[vertex])` pairs, to quickly pick the next vertex outside the tree that can be connected with the smallest weight.

---

### 2.3 Step‐by‐Step Code Explanation

```java
public class PrimAlgorithm {
  // 1) Inner class: represents a node in the PQ with (vertex, weight)
  static class Node implements Comparable<Node> {
    int vertex, weight;

    Node(int vertex, int weight) {
      this.vertex = vertex;
      this.weight = weight;
    }

    public int compareTo(Node other) {
      return this.weight - other.weight;
    }
  }

  // 2) Build MST using Prim’s (given adjacency‐matrix “graph” and number of vertices)
  public void primMST(int[][] graph, int vertices) {
    // 2a) key[v] = minimum edge‐weight to connect v to the MST so far
    int[] key = new int[vertices];
    // 2b) parent[v] = which vertex currently connects v inside the MST
    int[] parent = new int[vertices];
    // 2c) inMST[v] = true if v is already included in the MST
    boolean[] inMST = new boolean[vertices];

    // 2d) Min‐heap over (vertex, key[vertex]) so we can extract the cheapest “frontier” vertex
    PriorityQueue<Node> pq = new PriorityQueue<>();

    // 2e) Initialize all keys to +∞, none in MST yet
    Arrays.fill(key, Integer.MAX_VALUE);
    Arrays.fill(inMST, false);

    // 2f) Start from vertex 0 (arbitrary): key[0] = 0 (so PQ will pick it first)
    key[0] = 0;
    parent[0] = -1; // root has no parent
    pq.offer(new Node(0, key[0]));

    // 3) Loop until the PQ is empty or we’ve included all vertices
    while (!pq.isEmpty()) {
      // 3a) Extract the vertex u with minimum key[u], not yet in MST
      int u = pq.poll().vertex;
      inMST[u] = true;  // mark u as included

      // 3b) For every neighbor v of u (i.e. graph[u][v] != 0):
      for (int v = 0; v < vertices; v++) {
        // If there's an edge u—v, v is not yet in MST, and the edge weight < key[v], update:
        if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
          key[v]    = graph[u][v];
          parent[v] = u;
          pq.offer(new Node(v, key[v]));
        }
      }
    }

    // 4) Print the edges of the MST. Every v ≠ 0 has parent[v], and edge (parent[v]—v) is in MST
    printMST(parent, graph, vertices);
  }

  // Helper to print the final MST edges
  private void printMST(int[] parent, int[][] graph, int vertices) {
    System.out.println("Edge \tWeight");
    for (int i = 1; i < vertices; i++) {
      System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }
  }

  // 5) Example usage
  public static void main(String[] args) {
    int vertices = 5;
    int[][] graph = {
      {0, 2, 0, 6, 0},
      {2, 0, 3, 8, 5},
      {0, 3, 0, 0, 7},
      {6, 8, 0, 0, 9},
      {0, 5, 7, 9, 0}
    };

    PrimAlgorithm mst = new PrimAlgorithm();
    mst.primMST(graph, vertices);
  }
}
```

#### How It Works, Step by Step

1. **Initialization**

   * `key[v]` = $+\infty$ for every vertex $v$. Later, `key[v]` will hold the lightest edge connecting $v$ to the currently built MST.
   * `inMST[v] = false` for every $v$ initially.
   * Pick vertex `0` to start (an arbitrary choice); set `key[0] = 0` so that the PQ will extract vertex 0 first.
   * `parent[0] = −1` (root of the MST).
   * Add `(0, key[0]) = (0, 0)` into the priority queue.

2. **Main Loop**

   * While the PQ is not empty:

     1. **Extract‐Min**: Pop the node with smallest `weight`—call it `u`. Because `key[u]` was its weight, this is the cheapest way to connect `u` to the current MST.
     2. Mark `inMST[u] = true`. Vertex $u$ is now officially part of the MST.
     3. **Edge Relaxation**: For every other vertex `v` (from `0` to `vertices−1`):

        * If `graph[u][v] != 0`, that means an edge $u\text{–}v$ exists with weight `w = graph[u][v]`.
        * If `v` is not yet in the MST (`!inMST[v]`) and `w < key[v]`, then we have found a *cheaper* edge to connect `v` to the tree we’re building—via `u`. So we update:

          ```java
          key[v] = w;        // cheapest edge to connect v so far
          parent[v] = u;     // v is connected from u
          pq.offer(new Node(v, key[v]));
          ```
        * This always ensures `key[v]` is “the minimum edge‐weight by which v can join the MST.”
   * Once the PQ drains, **every vertex** has been added to the MST exactly once (since we always extract a vertex only when it is the cheapest frontier node).

3. **Build/Print the MST**

   * After the loop, `parent[v]` tells you which edge `(parent[v]—v)` is in the MST. For `v = 1..(vertices−1)`, we print

     ```
     parent[v]  –  v   (weight = graph[v][parent[v]])
     ```
   * Vertex 0 had no parent (it was the first root), so we skip it when printing.

---

### 2.4 Complexity Analysis

* **Priority Queue Operations**

  * We will push a new `(v, key[v])` for nearly every time we find a cheaper edge. In the worst case—if every time we decrease an edge weight there is a “decrease‐key”—we can end up with up to $E$ insertions. Each insertion/extraction from a binary‐heap PQ takes $O(\log V)$.

* **Main Loop Work**

  * We loop until every vertex is included once—so we do exactly $V$ extractions from the PQ: $O(V \log V)$.
  * Each time we extract `u`, we scan all possible neighbors `v = 0..(vertices−1)` to check if `graph[u][v] != 0`. Because we used an adjacency‐matrix, that scan takes $O(V)$ per extracted vertex. Over $V$ vertices, that is $O(V^2)$.
  * If this were an adjacency‐list representation, scanning actual neighbors would sum to $O(E)$ total. But in your code, you used an adjacency matrix, so scanning all `v` is $O(V)$. Over $V$ extractions, that is $O(V^2)$.

* **Total Time Complexity**

  * With an adjacency‐matrix approach:

    $$
      O\bigl(V\log V + V^2\bigr) \;=\; O\bigl(V^2\bigr)\quad \bigl(\text{since }V^2\text{ dominates }V\log V\bigr).
    $$
  * If you had used an adjacency‐list, it would be

    $$
      O\bigl(V\log V + E\log V\bigr)\;=\; O\bigl((V + E)\log V\bigr).
    $$

  (We still pay a $\log V$ factor for each push/pop, but scanning neighbors sums to $O(E)$.)

* **Space Complexity**

  * `key[]`, `parent[]`, `inMST[]` each cost $O(V)$.
  * The PQ can hold up to $O(E)$ nodes in the worst case (every time we “decrease key” we push a new `(v, weight)`, so we might have multiple entries per vertex).
  * If adjacency‐matrix is explicitly stored, that is $O(V^2)$. In your code, `int[][] graph` is $O(V^2)$.
  * Overall: $O(V^2)$ dominated by the adjacency‐matrix.

---

## 3) Summary Comparison

|                             | Kruskal’s                                                                   | Prim’s (adjacency‐matrix)                                                                         |
| --------------------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------- |
| **Approach**                | Sort all edges $\to$ pick smallest edge that doesn’t form a cycle (via DSU) | Maintain a growing tree, always attach cheapest edge to a new vertex                              |
| **Data Structure**          | Edge list + Union‐Find (Parent\[], Rank\[])                                 | Adjacency‐matrix (or list) + Min‐Heap + arrays                                                    |
| **Main Loop**               | Process edges in ascending weight until $V-1$ edges selected                | Extract‐min vertex from PQ until all $V$ vertices included                                        |
| **Cycle‐Check**             | Done implicitly by “are the two endpoints in the same DSU set?”             | No explicit cycle check: we only add edges from an already‐built tree to a vertex not in the tree |
| **Time (dense vs. sparse)** | $O(E \log E + E\,\alpha(V)) \approx O(E\log V)$                             | (Using adjacency‐list) $O((V+E)\log V)$; (using matrix) $O(V^2)$                                  |
| **Space**                   | $O(V + E)$ for edges + parent/rank arrays                                   | $O(V^2)$ if adjacency‐matrix, or $O(V + E)$ if adjacency‐list                                     |

* **Kruskal** is usually easier to implement if you already have a list of all edges. Sorting is $O(E\log E)$.
* **Prim** is often preferred if the graph is dense (so $E\approx V^2$) because adjacency‐matrix + a simple array version can sometimes be $O(V^2)$ without any $\log V$ factors. But if the graph is sparse ($E \ll V^2$), an adjacency‐list + PQ version achieves $O((V+E)\log V)$, which can be better.

---

## 4) Final Note

* Both algorithms produce a valid MST of an undirected, connected, weighted graph.
* Kruskal’s sorts edges and uses DSU to avoid cycles.
* Prim’s grows a single tree, always taking the cheapest next edge to attach a new vertex.
* Their implementations differ, but the end result is the same: a set of $V-1$ edges whose total weight is minimal, connecting all vertices.

I hope this detailed explanation clarifies **how Kruskal’s and Prim’s algorithms** work, and why the provided code correctly implements each one.
