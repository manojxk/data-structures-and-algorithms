package medium.graphs;

/*
 * Problem Statement:
 * Implement Breadth-First Search (BFS) for a graph. Given a graph represented as an adjacency list and a starting node, perform BFS to visit all reachable nodes.
 *
 * BFS explores all neighbors of a node before moving on to the next level of neighbors. BFS uses a queue to keep track of the nodes to be visited.
 *
 * Example:
 *
 * Input:
 * Graph: {
 *     0: [1, 2],
 *     1: [0, 3, 4],
 *     2: [0, 4],
 *     3: [1],
 *     4: [1, 2]
 * }
 * Starting Node: 0
 *
 * Output:
 * BFS Traversal from Node 0: [0, 1, 2, 3, 4]
 */

/*
 * Solution Approach:
 *
 * 1. Create a graph using an adjacency list.
 * 2. Use a queue to explore nodes level by level.
 * 3. Keep track of visited nodes using a boolean array to prevent visiting a node more than once.
 * 4. Start from the given node, enqueue it, and explore all of its neighbors.
 */

import java.util.*;

public class A02BreadthFirstSearch {

  // Function to perform BFS from a given start node
  public static List<Integer> bfs(int startNode, Map<Integer, List<Integer>> graph) {
    // List to store the BFS traversal result
    List<Integer> traversal = new ArrayList<>();

    // Queue to store nodes to be visited
    Queue<Integer> queue = new LinkedList<>();

    // Boolean array to keep track of visited nodes
    boolean[] visited = new boolean[graph.size()];

    // Start BFS from the start node
    queue.add(startNode);
    visited[startNode] = true;

    while (!queue.isEmpty()) {
      // Dequeue a node and process it
      int currentNode = queue.poll();
      traversal.add(currentNode);

      // Get all neighbors of the current node
      List<Integer> neighbors = graph.get(currentNode);
      if (neighbors != null) {
        // Enqueue all unvisited neighbors
        for (int neighbor : neighbors) {
          if (!visited[neighbor]) {
            queue.add(neighbor);
            visited[neighbor] = true;
          }
        }
      }
    }

    return traversal;
  }

  // Main function to test the BFS implementation
  public static void main(String[] args) {
    // Create a graph using an adjacency list
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(0, Arrays.asList(1, 2));
    graph.put(1, Arrays.asList(0, 3, 4));
    graph.put(2, Arrays.asList(0, 4));
    graph.put(3, Arrays.asList(1));
    graph.put(4, Arrays.asList(1, 2));

    // Starting node for BFS
    int startNode = 0;

    // Perform BFS and get the traversal order
    List<Integer> result = bfs(startNode, graph);

    // Print the BFS traversal result
    System.out.println("BFS Traversal from Node " + startNode + ": " + result);
  }

  /*
   * Time Complexity:
   * O(V + E), where V is the number of vertices and E is the number of edges.
   * Each node and edge is processed once.
   *
   * Space Complexity:
   * O(V), where V is the number of vertices.
   * This is for the visited array and the queue.
   */
}
