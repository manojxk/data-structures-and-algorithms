package hard.famousalgorithms;

import java.util.*;

public class A01DijkstraAlgorithm {

  /*
  Problem Breakdown:
  - Given a directed graph with positive weights, the goal is to find the shortest
    distance from a given start vertex to all other vertices using Dijkstra's Algorithm.

  Dijkstra's Algorithm Overview:
  - A greedy algorithm to find the shortest paths from a source node to all other nodes
    in a graph with non-negative weights.

  Steps of Dijkstra's Algorithm:
  1. Initialize distances:
     - Set the distance to the start node as 0 and all other nodes as infinity.
  2. Min-Priority Queue:
     - Use a priority queue (min-heap) to get the node with the smallest known distance
       that hasn't been visited yet.
  3. Relaxation:
     - For the current node, check all its neighbors.
     - If a shorter path to a neighbor is found through the current node,
       update the shortest known distance to that neighbor.
  4. Repeat:
     - Continue this process until all nodes are processed (either visited or unreachable).
  5. Handle unreachable nodes:
     - If a node cannot be reached, mark its distance as -1.

  Time Complexity:
  - O((V + E) log V), where V is the number of vertices and E is the number of edges.
    - V log V comes from priority queue operations (insert and extract min).
    - E log V comes from edge relaxation, as each edge is processed once.

  Space Complexity:
  - O(V + E), where V is the space for the distance array and visited nodes,
    and E is the space for the adjacency list.
  */

  public static int[] dijkstra(int start, List<List<int[]>> edges) {
    // Number of vertices in the graph
    int n = edges.size();

    // Array to store the shortest distance from 'start' to each node
    int[] distances = new int[n];

    /*
    Step 1: Initialize distances
    - Set all distances to infinity, except for the start node.
    - The start node is initialized to 0, meaning the distance to itself is 0.
    */
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    /*
    Step 2: Min-Priority Queue
    - Use a min-heap to prioritize nodes based on their known shortest distance.
    - Push the start node into the heap with distance 0.
    */
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    minHeap.add(new int[] {start, 0});

    // Array to track visited nodes (to avoid processing a node more than once)
    boolean[] visited = new boolean[n];

    /*
    Step 3: Process the graph using Dijkstra's algorithm
    - Continue processing nodes from the priority queue until all reachable nodes are visited.
    */
    while (!minHeap.isEmpty()) {
      int[] current = minHeap.poll(); // Get the node with the smallest distance
      int currentNode = current[0];
      int currentDistance = current[1];

      // Step 4: Skip already visited nodes to avoid reprocessing
      if (visited[currentNode]) {
        continue;
      }
      visited[currentNode] = true;

      /*
      Step 5: Relaxation step
      - Check each neighbor of the current node.
      - If the new calculated distance to the neighbor is shorter than the known distance,
        update the distance and push it into the priority queue.
      */
      for (int[] neighbor : edges.get(currentNode)) {
        int neighborNode = neighbor[0];
        int edgeWeight = neighbor[1];

        // Calculate the distance to the neighbor via the current node
        int newDistance = currentDistance + edgeWeight;

        // Update the distance if the new path is shorter
        if (newDistance < distances[neighborNode]) {
          distances[neighborNode] = newDistance;
          minHeap.add(new int[] {neighborNode, newDistance});
        }
      }
    }

    /*
    Step 6: Handle unreachable nodes
    - If a node's distance is still infinity, it means the node is unreachable from the start node.
    - Mark the distance of such nodes as -1.
    */
    for (int i = 0; i < n; i++) {
      if (distances[i] == Integer.MAX_VALUE) {
        distances[i] = -1;
      }
    }

    // Return the array containing the shortest distances from the start node to all other nodes.
    return distances;
  }

  public static void main(String[] args) {
    /*
    Example Input:
    - We represent the graph using an adjacency list.
    - Each node points to its neighbors and the edge weights (distances).
    - For example, node 0 has an edge to node 1 with weight 7.
    */
    int start = 0;
    List<List<int[]>> edges = new ArrayList<>();

    // Graph adjacency list representation
    edges.add(Arrays.asList(new int[] {1, 7})); // Edges from vertex 0
    edges.add(Arrays.asList(new int[] {2, 6}, new int[] {3, 20}, new int[] {4, 3})); // Vertex 1
    edges.add(Arrays.asList(new int[] {3, 14})); // Vertex 2
    edges.add(Arrays.asList(new int[] {4, 2})); // Vertex 3
    edges.add(new ArrayList<>()); // Vertex 4 (no outgoing edges)
    edges.add(new ArrayList<>()); // Vertex 5 (no outgoing edges)

    /*
    Call Dijkstra's Algorithm:
    - We start from vertex 0, and the function will return an array of shortest distances.
    */
    int[] result = dijkstra(start, edges);

    // Output the shortest distances from the start node to all other nodes
    System.out.println(Arrays.toString(result)); // Expected output: [0, 7, 13, 27, 10, -1]
  }
}


/* Time Complexity (TC): O((V + E) * log V)
V: Number of vertices.
E: Number of edges.
log V: Comes from the priority queue operations (insert and extract min).
Breakdown:
Priority Queue Operations:

Extracting the minimum element and inserting updated distances into the min-heap takes O(log V).
Each vertex can be processed once, and each edge can be relaxed once.
Total Complexity:

O(V log V) for processing vertices.
O(E log V) for edge relaxation.
Combined: O((V + E) * log V).
Space Complexity (SC): O(V + E)
V: Space for distance array and visited array (O(V)).
E: Space for storing the adjacency list (O(E)).
Breakdown:
Adjacency List: O(V + E) for storing edges.
Distance & Visited Arrays: O(V) for tracking distances and visited nodes.
Priority Queue: At most O(V) elements in the queue.
Summary:

TC: O((V + E) * log V).
SC: O(V + E). */
