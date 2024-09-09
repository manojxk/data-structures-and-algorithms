/*
 * Problem Statement:
 * Given the root node of a Binary Tree, a target value of a node contained in the tree, and a positive integer k,
 * write a function that returns the values of all nodes that are exactly distance k from the node with the target value.
 *
 * The distance between two nodes is defined as the number of edges that must be traversed to go from one node to the other.
 * For example, the distance between a node and its immediate left or right child is 1. The distance between a node and its parent is also 1.
 *
 * Each BinaryTree node has an integer value, a left child node, and a right child node. Children nodes can either be BinaryTree nodes themselves or null.
 *
 * You can assume that all BinaryTree node values will be unique, and your function can return the output values in any order.
 *
 * Example:
 * Input:
 *        tree = 1
 *              /   \
 *             2     3
 *           /   \     \
 *          4     5     6
 *                    /   \
 *                   7     8
 *        target = 3
 *        k = 2
 *
 * Output: [2, 7, 8]  // The order of the values in the output can vary.
 */
/*Brute Force Solution
Approach:
Perform a DFS (Depth-First Search) to locate the target node.
Once the target node is found, perform a BFS (Breadth-First Search) starting from the target node to find all nodes at a distance k.
Also, traverse upwards to check for nodes that are k distance away from the target by considering the parent nodes.
Time Complexity:
O(n): We visit each node once during the DFS and BFS.
Space Complexity:
O(n): Space for the recursion stack during DFS and space for the queue during BFS.*/
package hard.binarytrees;

import java.util.*;

public class NodesAtDistanceK {

  static class TreeNode {
    int value;
    TreeNode left;
    TreeNode right;

    TreeNode(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Function to find the target node and store parent references
  private static void findParents(
      TreeNode node, Map<TreeNode, TreeNode> parentTrack, TreeNode parent) {
    if (node != null) {
      parentTrack.put(node, parent);
      findParents(node.left, parentTrack, node);
      findParents(node.right, parentTrack, node);
    }
  }

  // Main function to find nodes at distance k from target
  public static List<Integer> distanceK(TreeNode root, int target, int k) {
    Map<TreeNode, TreeNode> parentTrack = new HashMap<>();
    findParents(root, parentTrack, null);

    // Find the target node
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    TreeNode targetNode = null;
    while (!queue.isEmpty()) {
      TreeNode current = queue.poll();
      if (current.value == target) {
        targetNode = current;
        break;
      }
      if (current.left != null) queue.add(current.left);
      if (current.right != null) queue.add(current.right);
    }

    // Use BFS to find all nodes at distance k from the target
    queue.clear();
    queue.add(targetNode);
    Set<TreeNode> visited = new HashSet<>();
    visited.add(targetNode);
    int currentLevel = 0;

    while (!queue.isEmpty()) {
      if (currentLevel == k) {
        List<Integer> result = new ArrayList<>();
        for (TreeNode node : queue) {
          result.add(node.value);
        }
        return result;
      }
      int levelSize = queue.size();
      for (int i = 0; i < levelSize; i++) {
        TreeNode current = queue.poll();
        if (current.left != null && visited.add(current.left)) {
          queue.add(current.left);
        }
        if (current.right != null && visited.add(current.right)) {
          queue.add(current.right);
        }
        TreeNode parent = parentTrack.get(current);
        if (parent != null && visited.add(parent)) {
          queue.add(parent);
        }
      }
      currentLevel++;
    }

    return new ArrayList<>(); // Return empty list if no such nodes found
  }

  public static void main(String[] args) {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(6);
    root.right.right.left = new TreeNode(7);
    root.right.right.right = new TreeNode(8);

    List<Integer> result = distanceK(root, 3, 2);
    System.out.println("Nodes at distance K are: " + result);
  }
}
