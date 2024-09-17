package easy.graphs;

/*
 * Problem Statement:
 * Implement Depth First Search (DFS) for a graph. The goal is to explore all the nodes in the graph by visiting each node once, starting from a given node, and traversing as far as possible along each branch before backtracking.
 *
 * DFS is used for exploring graph data structures. It starts at a given node and explores as far as possible along each branch before backtracking. This traversal is performed recursively.
 *
 * Example:
 *
 * Input:
 * Graph: {
 *     0: [1, 2],
 *     1: [2],
 *     2: [0, 3],
 *     3: [3]
 * }
 *
 * Starting Node: 2
 *
 * Output:
 * DFS Traversal from Node 2: [2, 0, 1, 3]
 */

/*
 * Solution Approach:
 *
 * 1. Create a graph using an adjacency list.
 * 2. Use a recursive helper function to visit each node in the graph.
 * 3. Keep track of visited nodes using a boolean array to prevent cycles.
 * 4. Start from the given node and recursively visit all unvisited adjacent nodes.
 */

import java.util.*;

public class A01DepthFirstSearch {

  // Helper function to perform DFS from a given node
  public static void dfs(
      int node, boolean[] visited, Map<Integer, List<Integer>> graph, List<Integer> traversal) {
    // Mark the current node as visited
    visited[node] = true;
    traversal.add(node);

    // Get all adjacent vertices of the current node
    List<Integer> neighbors = graph.get(node);

    // Recursively visit each unvisited neighbor
    if (neighbors != null) {
      for (int neighbor : neighbors) {
        if (!visited[neighbor]) {
          dfs(neighbor, visited, graph, traversal);
        }
      }
    }
  }

  // Function to perform DFS for an entire graph
  public static List<Integer> depthFirstSearch(int startNode, Map<Integer, List<Integer>> graph) {
    // Create a boolean array to keep track of visited nodes
    boolean[] visited = new boolean[graph.size()];
    List<Integer> traversal = new ArrayList<>();

    // Start DFS from the given node
    dfs(startNode, visited, graph, traversal);

    return traversal;
  }

  // Main function to test the DFS implementation
  public static void main(String[] args) {
    // Create a graph using an adjacency list
    Map<Integer, List<Integer>> graph = new HashMap<>();
    graph.put(0, Arrays.asList(1, 2));
    graph.put(1, Arrays.asList(2));
    graph.put(2, Arrays.asList(0, 3));
    graph.put(3, Arrays.asList(3));

    // Starting node
    int startNode = 2;

    // Perform DFS starting from the given node
    List<Integer> result = depthFirstSearch(startNode, graph);

    // Print the DFS traversal
    System.out.println("DFS Traversal from Node " + startNode + ": " + result);
  }

  /*
   * Time Complexity:
   * O(V + E), where V is the number of vertices and E is the number of edges.
   * Each node and edge is visited once.
   *
   * Space Complexity:
   * O(V), where V is the number of vertices.
   * This is for the recursion stack and the visited array.
   */
}
