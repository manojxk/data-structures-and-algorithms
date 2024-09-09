package hard.famousalgorithms;

import java.util.*;

// Class to represent an edge in the graph

public class KruskalAlgorithm {
  static class Edge implements Comparable<Edge> {
    int src, dest, weight;

    // Constructor to initialize an edge
    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

    // Comparator function to sort edges by weight
    public int compareTo(Edge other) {
      return this.weight - other.weight;
    }
  }

  int vertices;
  List<Edge> edges; // List of edges in the graph

  // Constructor to initialize the graph with the number of vertices
  KruskalAlgorithm(int vertices) {
    this.vertices = vertices;
    edges = new ArrayList<>();
  }

  // Add an edge to the graph
  void addEdge(int src, int dest, int weight) {
    edges.add(new Edge(src, dest, weight));
  }

  // Find function for Union-Find with path compression
  int find(int[] parent, int i) {
    if (parent[i] != i) {
      parent[i] = find(parent, parent[i]); // Path compression
    }
    return parent[i];
  }

  // Union function for Union-Find (union by rank)
  void union(int[] parent, int[] rank, int x, int y) {
    int rootX = find(parent, x);
    int rootY = find(parent, y);

    // Attach smaller rank tree under root of higher rank tree
    if (rank[rootX] < rank[rootY]) {
      parent[rootX] = rootY;
    } else if (rank[rootX] > rank[rootY]) {
      parent[rootY] = rootX;
    } else {
      parent[rootY] = rootX;
      rank[rootX]++;
    }
  }

  // Main function to construct MST using Kruskal's algorithm
  void kruskalMST() {
    List<Edge> result = new ArrayList<>(); // Store the resulting MST
    Collections.sort(edges); // Step 1: Sort all edges by weight

    int[] parent = new int[vertices]; // To store the parent of each vertex for Union-Find
    int[] rank = new int[vertices]; // To store the rank of each vertex for Union-Find

    // Step 2: Initialize each vertex as its own parent (for Union-Find)
    for (int i = 0; i < vertices; i++) {
      parent[i] = i;
      rank[i] = 0;
    }

    // Step 3: Process each edge in sorted order
    for (Edge edge : edges) {
      int rootSrc = find(parent, edge.src); // Find root of the source vertex
      int rootDest = find(parent, edge.dest); // Find root of the destination vertex

      // If including this edge doesn't form a cycle, add it to the result
      if (rootSrc != rootDest) {
        result.add(edge);
        union(parent, rank, rootSrc, rootDest); // Union of the two vertices
      }

      // If we already have V-1 edges, we can stop
      if (result.size() == vertices - 1) {
        break;
      }
    }

    // Step 4: Print the result
    System.out.println("Following are the edges in the constructed MST:");
    for (Edge edge : result) {
      System.out.println(edge.src + " -- " + edge.dest + " == " + edge.weight);
    }
  }

  // Main function to test the Kruskal's algorithm
  public static void main(String[] args) {
    // Create a graph with 4 vertices
    KruskalAlgorithm graph = new KruskalAlgorithm(4);

    // Add edges: (src, dest, weight)
    graph.addEdge(0, 1, 10);
    graph.addEdge(0, 2, 6);
    graph.addEdge(0, 3, 5);
    graph.addEdge(1, 3, 15);
    graph.addEdge(2, 3, 4);

    // Find and print the MST
    graph.kruskalMST();
  }
}
/*
Step-by-Step Explanation:
Edge Class:

This class defines an edge with three attributes: src (source vertex), dest (destination vertex), and weight (weight of the edge).
It implements the Comparable interface to allow sorting edges by weight.
KruskalSimplified Class:

It represents the graph. The constructor initializes the graph with a given number of vertices.
The graph uses a list of edges (edges).
addEdge Method:

Adds an edge to the list of edges with the specified source, destination, and weight.
        find Method (Union-Find with Path Compression):

It recursively finds the root of the vertex i. If the vertex is not its own parent, it updates the parent to the root (path compression).
union Method (Union by Rank):

Combines two sets (vertices) into one. The set with the smaller rank becomes the child of the set with the higher rank. If both have the same rank, one becomes the child, and its rank is incremented.
kruskalMST Method:

Step 1: Sorts all edges by weight using Collections.sort(). This ensures that we consider the smallest weight edges first (greedy approach).
Step 2: Initializes the parent array, where each vertex is its own parent, and the rank array is initialized to zero.
        Step 3: Processes edges in the sorted order:
For each edge, it checks if the source and destination vertices belong to different sets using the find() method. If they do, it adds the edge to the result and merges the sets using the union() method.
Step 4: Stops when the MST contains V-1 edges (where V is the number of vertices).
Step 5: Prints the edges of the MST.
Main Method:

The graph is created with 4 vertices.
Edges are added with the given source, destination, and weight.
Kruskal’s algorithm is applied to find the MST, and the result is printed.
Time Complexity:
Sorting edges: Sorting all edges takes O(E log E), where E is the number of edges.
Union-Find operations: The find and union operations take nearly constant time, so the overall complexity is O(E log V).
Thus, the total time complexity is O(E log E), which simplifies to O(E log V).

Space Complexity:
O(V) for storing the parent and rank arrays.
O(E) for storing the edges list.*/
