package medium.graphs;

/*
 * Problem Statement:
 * Given a directed or undirected graph, write a function to detect if the graph contains a cycle.
 * A cycle exists in a graph if there is a path that starts from a node and leads back to the same node by traversing through edges.
 *
 * Example:
 * For an undirected graph:
 *
 * Input:
 * Graph: {
 *     0: [1, 2],
 *     1: [0, 2],
 *     2: [0, 1, 3],
 *     3: [2]
 * }
 *
 * Output:
 * true // Explanation: There is a cycle: 0 -> 1 -> 2 -> 0
 */

import java.util.*;

public class A03CycleInGraph {

  /*
   * Solution Approach for Directed Graph:
   *
   * 1. For a directed graph, we use DFS and keep track of nodes in the current recursion stack.
   * 2. If we encounter a node that is already in the recursion stack, it means there is a cycle.
   */

  // Function to detect a cycle in a directed graph
  public static boolean hasCycleDirected(
      int node, boolean[] visited, boolean[] recStack, Map<Integer, List<Integer>> graph) {
    // Mark the current node as visited and add it to the recursion stack
    visited[node] = true;
    recStack[node] = true;

    // Get all neighbors of the current node
    List<Integer> neighbors = graph.get(node);

    if (neighbors != null) {
      // Explore each neighbor
      for (int neighbor : neighbors) {
        // If the neighbor is not visited, recursively check for cycles
        if (!visited[neighbor]) {
          if (hasCycleDirected(neighbor, visited, recStack, graph)) {
            return true; // Cycle detected
          }
        }
        // If the neighbor is in the recursion stack, there is a cycle
        else if (recStack[neighbor]) {
          return true;
        }
      }
    }

    // Remove the current node from the recursion stack
    recStack[node] = false;

    return false;
  }

  // Function to check if there is a cycle in the directed graph
  public static boolean containsCycleDirected(Map<Integer, List<Integer>> graph) {
    int numNodes = graph.size();
    boolean[] visited = new boolean[numNodes];
    boolean[] recStack = new boolean[numNodes];

    // Perform DFS for each unvisited node
    for (int node : graph.keySet()) {
      if (!visited[node]) {
        if (hasCycleDirected(node, visited, recStack, graph)) {
          return true; // Cycle detected
        }
      }
    }

    return false; // No cycle found
  }

  // Main function to test the Cycle Detection implementation
  public static void main(String[] args) {

    // Example for directed graph
    Map<Integer, List<Integer>> directedGraph = new HashMap<>();
    directedGraph.put(0, Arrays.asList(1));
    directedGraph.put(1, Arrays.asList(2));
    directedGraph.put(2, Arrays.asList(0, 3));
    directedGraph.put(3, Arrays.asList(3));

    // Check for a cycle in a directed graph
    System.out.println(
        "Cycle in directed graph: " + containsCycleDirected(directedGraph)); // Output: true
  }

  /*
   * Time Complexity:
   * O(V + E), where V is the number of vertices and E is the number of edges.
   * Each node and edge is visited once during DFS traversal.
   *
   * Space Complexity:
   * O(V), where V is the number of vertices. This is for storing the visited array, recursion stack, and adjacency list.
   */
}
