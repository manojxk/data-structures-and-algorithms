package hard.famousalgorithms;

import java.util.*;

// Class to represent a node in the graph with a vertex and weight

public class PrimAlgorithm {
  static class Node implements Comparable<Node> {
    int vertex, weight;

    Node(int vertex, int weight) {
      this.vertex = vertex;
      this.weight = weight;
    }

    // Comparator function to compare nodes by weight
    public int compareTo(Node other) {
      return this.weight - other.weight;
    }
  }

  // Function to construct the MST using Prim's Algorithm
  public void primMST(int[][] graph, int vertices) {
    // To store the minimum edge weight to reach each vertex
    int[] key = new int[vertices];
    // To store the parent of each vertex in the MST
    int[] parent = new int[vertices];
    // To track which vertices are included in the MST
    boolean[] inMST = new boolean[vertices];
    // Priority queue (min-heap) to pick the minimum weight edge
    PriorityQueue<Node> pq = new PriorityQueue<>();

    // Initialize all keys to a large value and mark vertices as not included in the MST
    Arrays.fill(key, Integer.MAX_VALUE);
    Arrays.fill(inMST, false);

    // Start with vertex 0
    key[0] = 0; // Starting vertex has weight 0
    parent[0] = -1; // The first node is always the root of the MST
    pq.add(new Node(0, key[0]));

    // Loop until all vertices are included in the MST
    while (!pq.isEmpty()) {
      // Pick the vertex with the minimum key value
      int u = pq.poll().vertex;

      // Mark the vertex as part of the MST
      inMST[u] = true;

      // Loop through all adjacent vertices of u
      for (int v = 0; v < vertices; v++) {
        // If graph[u][v] is non-zero and v is not in MST and graph[u][v] is smaller than the key of
        // v
        if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
          // Update key[v] and parent[v]
          key[v] = graph[u][v];
          parent[v] = u;
          pq.add(new Node(v, key[v]));
        }
      }
    }

    // Print the MST
    printMST(parent, graph, vertices);
  }

  // Function to print the constructed MST
  private void printMST(int[] parent, int[][] graph, int vertices) {
    System.out.println("Edge \tWeight");
    for (int i = 1; i < vertices; i++) {
      System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
    }
  }

  // Driver code to test the implementation
  public static void main(String[] args) {
    // Create a graph represented by an adjacency matrix
    int vertices = 5;
    int[][] graph = {
      {0, 2, 0, 6, 0},
      {2, 0, 3, 8, 5},
      {0, 3, 0, 0, 7},
      {6, 8, 0, 0, 9},
      {0, 5, 7, 9, 0}
    };

    // Instantiate the PrimMST class and call the primMST function
    PrimAlgorithm mst = new PrimAlgorithm();
    mst.primMST(graph, vertices);
  }
}
