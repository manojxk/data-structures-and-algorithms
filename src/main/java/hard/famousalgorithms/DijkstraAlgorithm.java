/*Problem Breakdown
You are given a directed graph in the form of an adjacency list, where each vertex has edges pointing to other vertices with positive weights (distances). The goal is to find the shortest distance from a given start vertex to all other vertices using Dijkstra's Algorithm.

Dijkstra's Algorithm Overview
Dijkstra's algorithm is a greedy algorithm used to find the shortest paths from a starting node to all other nodes in a graph with non-negative weights.

Steps of Dijkstra's Algorithm:

Initialize distances: Set the distance to the start node as 0 and all other nodes as infinity.
Min-Priority Queue: Use a priority queue (min-heap) to get the node with the smallest known distance that hasn't been visited yet.
Relaxation: For the current node, check all its neighbors. If a shorter path to a neighbor is found through the current node, update the shortest known distance to that neighbor.
Repeat: Continue this process until all nodes are processed (either visited or unreachable).
Handle unreachable nodes: If a node cannot be reached, mark its distance as -1.*/

package hard.famousalgorithms;

import java.util.*;

public class DijkstraAlgorithm {

  public static int[] dijkstra(int start, List<List<int[]>> edges) {
    // Number of vertices in the graph
    int n = edges.size();

    // Array to store the shortest distance from 'start' to each node
    int[] distances = new int[n];

    // Initialize distances to infinity, except for the start node (distance = 0)
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    // Min-heap priority queue to get the next node with the shortest known distance
    PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    minHeap.add(new int[] {start, 0}); // Add the start node to the heap

    // Array to track visited nodes
    boolean[] visited = new boolean[n];

    // Process the graph using Dijkstra's algorithm
    while (!minHeap.isEmpty()) {
      int[] current = minHeap.poll(); // Get the node with the smallest distance
      int currentNode = current[0];
      int currentDistance = current[1];

      // If the node is already visited, skip it
      if (visited[currentNode]) {
        continue;
      }
      visited[currentNode] = true;

      // Explore all neighbors of the current node
      for (int[] neighbor : edges.get(currentNode)) {
        int neighborNode = neighbor[0];
        int edgeWeight = neighbor[1];

        // Calculate the distance to the neighbor through the current node
        int newDistance = currentDistance + edgeWeight;

        // If this new distance is shorter, update and push to the heap
        if (newDistance < distances[neighborNode]) {
          distances[neighborNode] = newDistance;
          minHeap.add(new int[] {neighborNode, newDistance});
        }
      }
    }

    // Convert unreachable nodes' distances to -1
    for (int i = 0; i < n; i++) {
      if (distances[i] == Integer.MAX_VALUE) {
        distances[i] = -1;
      }
    }

    return distances;
  }

  public static void main(String[] args) {
    // Sample input
    int start = 0;
    List<List<int[]>> edges = new ArrayList<>();

    edges.add(Arrays.asList(new int[] {1, 7})); // Edges from vertex 0
    edges.add(
        Arrays.asList(
            new int[] {2, 6}, new int[] {3, 20}, new int[] {4, 3})); // Edges from vertex 1
    edges.add(Arrays.asList(new int[] {3, 14})); // Edges from vertex 2
    edges.add(Arrays.asList(new int[] {4, 2})); // Edges from vertex 3
    edges.add(new ArrayList<>()); // No edges from vertex 4
    edges.add(new ArrayList<>()); // No edges from vertex 5

    // Calculate shortest paths
    int[] result = dijkstra(start, edges);

    // Output the result
    System.out.println(Arrays.toString(result)); // Expected output: [0, 7, 13, 27, 10, -1]
  }
}
/*
Explanation
Input Representation:

The graph is represented as an adjacency list List<List<int[]>>, where each element is a list of edges, and each edge is represented as an array of two integers [destination, distance].
Distance Array:

An array distances[] is used to keep track of the shortest distance from the start node to all other nodes. It is initialized with Integer.MAX_VALUE (infinity) except for the start node, which is 0.
Min-Heap (Priority Queue):

The priority queue (minHeap) is used to pick the node with the smallest known distance efficiently. It stores pairs [node, distance], and we always process the node with the smallest distance first.
        Relaxation:

For each node, we check its neighbors. If the distance to a neighbor through the current node is shorter than the previously known distance, we update it and push it into the heap.
Handling Unreachable Nodes:

After processing all nodes, any node still with Integer.MAX_VALUE in the distances[] array is unreachable, and its value is set to -1.
Time Complexity:
O((V + E) log V), where V is the number of vertices and E is the number of edges.
V log V comes from the priority queue operations (insert and extract min).
E log V comes from the edge relaxation step, as each edge is processed once.
Space Complexity:
O(V + E), where V is the space for the distances array and visited nodes, and E is the space for the adjacency list.
        Conclusion
The code effectively uses Dijkstra’s algorithm to compute the shortest paths in a graph. The time complexity ensures that even for larger graphs, the algorithm remains efficient.*/
