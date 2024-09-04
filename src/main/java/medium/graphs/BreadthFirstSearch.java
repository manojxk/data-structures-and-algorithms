package medium.graphs;

import java.util.*;

public class BreadthFirstSearch {

  // Function to add an edge to the graph
  public static void addEdge(List<List<Integer>> adj, int u, int v) {
    adj.get(u).add(v);
    adj.get(v).add(u); // Undirected graph
  }

  // BFS from given source s
  public static void bfs(List<List<Integer>> adj, int s, boolean[] visited) {
    Queue<Integer> q = new LinkedList<>();

    // Mark the source node as visited and enqueue it
    visited[s] = true;
    q.add(s);

    // Iterate over the queue
    while (!q.isEmpty()) {
      int curr = q.poll(); // Dequeue a vertex
      System.out.print(curr + " ");

      // Get all adjacent vertices of curr
      for (int x : adj.get(curr)) {
        if (!visited[x]) {
          visited[x] = true; // Mark as visited
          q.add(x); // Enqueue it
        }
      }
    }
  }

  // Perform BFS for the entire graph
  public static void bfsDisconnected(List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()]; // Not visited

    for (int i = 0; i < adj.size(); i++) {
      if (!visited[i]) {
        bfs(adj, i, visited);
      }
    }
  }

  public static void main(String[] args) {
    int V = 6; // Number of vertices
    List<List<Integer>> adj = new ArrayList<>(V);

    // Initialize adjacency lists
    for (int i = 0; i < V; i++) {
      adj.add(new ArrayList<>());
    }

    // Add edges to the graph
    addEdge(adj, 0, 1);
    addEdge(adj, 0, 2);
    addEdge(adj, 3, 4);
    addEdge(adj, 4, 5);

    // Perform BFS traversal for the entire graph
    bfsDisconnected(adj);
  }
}
