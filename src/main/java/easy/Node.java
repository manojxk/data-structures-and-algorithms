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

package easy;

import java.util.ArrayList;
import java.util.List;

public class Node {

  // Attributes of the Node class
  private String name;
  private List<Node> children;

  // Constructor for Node
  public Node(String name) {
    this.name = name;
    this.children = new ArrayList<>();
  }

  // Method to add a child Node
  public Node addChild(String name) {
    Node child = new Node(name);
    this.children.add(child);
    return this;
  }

  // DFS Method Implementation
  public List<String> depthFirstSearch(List<String> result) {
    result.add(this.name); // Add current Node's name to the result list

    for (Node child : this.children) {
      child.depthFirstSearch(result); // Recursively apply DFS to each child Node
    }

    return result;
  }

  public static void main(String[] args) {
    // Creating the sample tree as per the problem statement
    Node root = new Node("A");
    root.addChild("B").addChild("E").addChild("F").addChild("I").addChild("J");
    root.addChild("C");
    root.addChild("D").addChild("G").addChild("K").addChild("H");

    // Performing Depth-First Search
    List<String> result = new ArrayList<>();
    root.depthFirstSearch(result);

    // Output the result
    System.out.println(result); // Output: ["A", "B", "E", "F", "I", "J", "C", "D", "G", "K", "H"]
  }
}


/*
Time Complexity:
O(V + E): Where V is the number of vertices (nodes) and E is the number of edges (connections between nodes). In a DFS, every node and every edge is visited once.
Space Complexity:
O(V): The space complexity is determined by the recursion stack, which could go as deep as the height of the tree.*/
