/*Problem Statement:
You're given a list of edges representing a connected, unweighted, undirected graph with at least one node. Write a function that returns a boolean indicating whether the given graph is two-colorable (also called bipartite).

A graph is two-colorable if all of its nodes can be assigned one of two colors such that no two adjacent nodes (nodes connected directly by an edge) share the same color.

The given list is an adjacency list representing the graph. The number of vertices in the graph is equal to the length of edges, where each index i in edges contains a list of vertex i's neighbors, in no particular order. Each edge is represented by a positive integer that denotes an index in the list to which the current vertex is connected. Note that the graph is undirected, meaning that if a vertex v1 appears in the edge list of vertex v2, then v2 must appear in the edge list of v1.

Additionally, the graph may contain self-loops, where a vertex is connected to itself. Any graph containing a self-loop is not two-colorable.*/
/*
To determine if a given graph is two-colorable (or bipartite), we can use a Breadth-First Search (BFS) or Depth-First Search (DFS) approach. The idea is to try to color the graph using two colors while ensuring that no two adjacent nodes have the same color.

Problem Explanation:
Two-colorable graph (Bipartite graph): A graph is bipartite if you can split its set of vertices into two disjoint sets such that no two vertices within the same set are adjacent.
Input: The input is an adjacency list, where each index in the list represents a vertex, and the list at each index represents the vertices connected to it.
        Output: Return True if the graph is two-colorable; otherwise, return False.
        Approach:
Handle Self-Loops: If a vertex has a self-loop, the graph cannot be bipartite because a node cannot be in the same set as itself.
Coloring: Start from any node, color it with one color, and attempt to color all its adjacent nodes with the other color. If you ever need to color two adjacent nodes with the same color, the graph is not bipartite.
Connected Components: Since the graph might be disconnected, ensure that all nodes are visited by iterating through each node and performing the BFS or DFS.*/

package medium;

import java.util.*;

public class TwoColorable {

  /**
   * Function to check if the graph is two-colorable (bipartite).
   *
   * @param edges adjacency list of the graph
   * @return true if the graph is bipartite, false otherwise
   */
  public static boolean isTwoColorable(int[][] edges) {
    int n = edges.length;
    int[] colors = new int[n];
    Arrays.fill(colors, -1); // -1 indicates uncolored

    for (int start = 0; start < n; start++) {
      if (colors[start] == -1) {
        if (!bfsCheck(start, edges, colors)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * BFS check for two-colorability starting from a given node.
   *
   * @param start the starting node for BFS
   * @param edges adjacency list of the graph
   * @param colors array to store the colors of nodes
   * @return true if the subgraph is bipartite, false otherwise
   */
  private static boolean bfsCheck(int start, int[][] edges, int[] colors) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    colors[start] = 0; // Start coloring with color 0

    while (!queue.isEmpty()) {
      int node = queue.poll();
      int currentColor = colors[node];

      for (int neighbor : edges[node]) {
        if (neighbor == node) {
          // Self-loop detected
          return false;
        }

        if (colors[neighbor] == -1) {
          // If the neighbor is uncolored, color it with the opposite color
          colors[neighbor] = 1 - currentColor;
          queue.offer(neighbor);
        } else if (colors[neighbor] == currentColor) {
          // If the neighbor has the same color, it's not bipartite
          return false;
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {
    int[][] edges1 = {
      {1, 2},
      {0, 2},
      {0, 1}
    };

    int[][] edges2 = {
      {1, 3},
      {0, 2},
      {1, 3},
      {0, 2}
    };

    System.out.println("Graph 1 is two-colorable: " + isTwoColorable(edges1)); // Output: False
    System.out.println("Graph 2 is two-colorable: " + isTwoColorable(edges2)); // Output: True
  }
}
/*
Explanation of the Code:
Color Initialization: We start by initializing all nodes as uncolored (-1).
BFS Traversal: We use BFS to traverse the graph, attempting to color it. If we find a node that requires an invalid color (i.e., it would conflict with an adjacent node), we return False.
        Self-loops: If we detect a self-loop, we immediately return False as the graph cannot be bipartite.
Multiple Components: The loop in isTwoColorable ensures that we check all components of the graph, not just the one containing the first node.
Complexity:
Time Complexity:

O(V+E), where
𝑉
V is the number of vertices and
        𝐸
E is the number of edges.
Space Complexity:

O(V) for storing colors and the queue used in BFS.*/
