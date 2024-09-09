package cools.graphs;

/*
 Problem: Clone Graph

 Given a reference of a node in a connected undirected graph, return a deep copy (clone) of the graph.

 Each node in the graph contains:
 - An integer value `val`.
 - A list of its neighbors `List<Node>`.

 The deep copy must have the same structure as the original graph. That means every node and its neighbors must be cloned, and the clone must have no shared references with the original graph.

 Example:

 Input:
 - A reference to node 1 in the graph described by the adjacency list:
 [[2,4],[1,3],[2,4],[1,3]]
 Output:
 - A deep copy of the graph.

 Constraints:
 - The graph is connected.
 - The number of nodes in the graph is between 1 and 100.
 - Each node's value is between 1 and 100.
 - The given node will always have `val = 1`.

 Solution Approach:
 1. Use DFS or BFS to traverse the graph.
 2. During traversal, clone each node and its neighbors.
 3. Store visited nodes in a hash map to avoid revisiting them and to handle cycles in the graph.
*/

import java.util.*;

public class A03CloneGraph {

  // Definition for a Node.
  static class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
      val = 0;
      neighbors = new ArrayList<Node>();
    }

    public Node(int val) {
      this.val = val;
      neighbors = new ArrayList<Node>();
    }

    public Node(int val, ArrayList<Node> neighbors) {
      this.val = val;
      this.neighbors = neighbors;
    }
  }

  // Function to clone a graph using Depth-First Search (DFS)
  public Node cloneGraph(Node node) {
    if (node == null) return null;

    // Map to store the original node and its cloned version
    Map<Node, Node> visited = new HashMap<>();

    // Start the DFS traversal and clone process
    return dfs(node, visited);
  }

  // Helper function to perform DFS and clone the graph
  private Node dfs(Node node, Map<Node, Node> visited) {
    // If the node has already been cloned, return the cloned node
    if (visited.containsKey(node)) {
      return visited.get(node);
    }

    // Create a new clone of the current node
    Node cloneNode = new Node(node.val);
    // Mark this node as visited (cloned)
    visited.put(node, cloneNode);

    // Clone all the neighbors of the current node
    for (Node neighbor : node.neighbors) {
      cloneNode.neighbors.add(dfs(neighbor, visited));
    }

    // Return the cloned node
    return cloneNode;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example test case:
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);

    node1.neighbors.add(node2);
    node1.neighbors.add(node4);
    node2.neighbors.add(node1);
    node2.neighbors.add(node3);
    node3.neighbors.add(node2);
    node3.neighbors.add(node4);
    node4.neighbors.add(node1);
    node4.neighbors.add(node3);

    A03CloneGraph solution = new A03CloneGraph();
    Node clonedGraph = solution.cloneGraph(node1);

    // Print the cloned graph's structure to verify correctness
    System.out.println(
        "Cloned Node 1 Neighbors: " + clonedGraph.neighbors.size()); // Should match original
    System.out.println("Cloned Node 1 Val: " + clonedGraph.val); // Should be 1
  }

  /*
   Time Complexity:
   - O(V + E), where V is the number of vertices (nodes) and E is the number of edges in the graph. Each node and edge is visited once during the traversal.

   Space Complexity:
   - O(V), where V is the number of nodes. We use a hash map to store the cloned nodes.
   - Additionally, recursion takes space proportional to the depth of the recursion stack, which is O(V) in the worst case.
  */
}
