/*
 * Problem Statement:
 *
 * You are given a `Node` class that has a `name` and an array of optional
 * `children` nodes. When put together, these nodes form an acyclic tree-like structure.
 *
 * Implement the `depthFirstSearch` method on the `Node` class, which takes
 * an empty list as input, traverses the tree using the Depth-First Search (DFS)
 * approach (specifically navigating the tree from left to right), stores all
 * of the nodes' names in the input list, and returns it.
 *
 * Example:
 * Input:
 *       A
 *     /  |  \
 *    B   C   D
 *   / \     / \
 *  E   F   G   H
 *     / \   \
 *    I   J   K
 *
 * Output: ["A", "B", "E", "F", "I", "J", "C", "D", "G", "K", "H"]
 *
 * Explanation:
 * The DFS traversal starts from the root node "A" and explores as far as
 * possible along each branch before backtracking. This results in the output
 * list: ["A", "B", "E", "F", "I", "J", "C", "D", "G", "K", "H"].
 */

package easy.graphs;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearch {
  static class Node {
    String name;
    List<Node> children;

    public Node(String name) {
      this.name = name;
      this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
      this.children.add(child);
    }
  }

  // Standalone DFS function
  public static List<String> depthFirstSearch(Node root, List<String> result) {
    if (root == null) return result;

    result.add(root.name); // Add the current node's name to the list
    for (Node child : root.children) {
      depthFirstSearch(child, result); // Recursively call DFS on each child
    }
    return result;
  }

  public static void main(String[] args) {
    // Create the tree structure
    Node root = new Node("A");
    Node nodeB = new Node("B");
    Node nodeC = new Node("C");
    Node nodeD = new Node("D");

    root.addChild(nodeB);
    root.addChild(nodeC);
    root.addChild(nodeD);

    nodeB.addChild(new Node("E"));
    Node nodeF = new Node("F");
    nodeB.addChild(nodeF);

    nodeD.addChild(new Node("G"));
    nodeD.addChild(new Node("H"));

    nodeF.addChild(new Node("I"));
    nodeF.addChild(new Node("J"));

    Node nodeG = nodeD.children.get(0);
    nodeG.addChild(new Node("K"));

    // Perform DFS
    List<String> result = new ArrayList<>();
    depthFirstSearch(root, result);

    // Output the DFS result
    System.out.println(
        result); // Expected Output: ["A", "B", "E", "F", "I", "J", "C", "D", "G", "K", "H"]
  }
}

/*
Time Complexity:
O(V + E): Where V is the number of vertices (nodes) and E is the number of edges (connections between nodes). In a DFS, every node and every edge is visited once.
Space Complexity:
O(V): The space complexity is determined by the recursion stack, which could go as deep as the height of the tree.*/

/*Explanation:
Node Class:

The Node class has a name and a list of children.
The addChild method allows you to add a child node to the current node.
depthFirstSearch Function:

This is a standalone function that takes the root node and an empty list as input.
The function adds the current node’s name to the list and then recursively calls itself on each child node.
It returns the list containing the names of nodes in DFS order.
Main Method:

We create the tree structure by manually adding child nodes.
Then, we call the depthFirstSearch function starting from the root node and print the result.
Time and Space Complexity:
Time Complexity: O(V + E) where V is the number of vertices (nodes) and E is the number of edges. Each node and edge is visited once.
Space Complexity: O(V) due to the recursion stack, which could go as deep as the number of nodes in the tree.*/
