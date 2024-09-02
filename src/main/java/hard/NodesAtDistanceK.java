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
package hard;

import java.util.*;

public class NodesAtDistanceK {
  static class BinaryTree {
    int value;
    BinaryTree left;
    BinaryTree right;

    BinaryTree(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  // Helper function to find the target node and populate the map with parent pointers
  private static BinaryTree findTargetAndParents(
      BinaryTree node, int target, Map<BinaryTree, BinaryTree> parentMap) {
    if (node == null) return null;
    if (node.value == target) return node;

    if (node.left != null) {
      parentMap.put(node.left, node);
      BinaryTree left = findTargetAndParents(node.left, target, parentMap);
      if (left != null) return left;
    }

    if (node.right != null) {
      parentMap.put(node.right, node);
      BinaryTree right = findTargetAndParents(node.right, target, parentMap);
      if (right != null) return right;
    }

    return null;
  }

  // Brute Force Solution
  public static List<Integer> nodesAtDistanceKBruteForce(BinaryTree root, int target, int k) {
    // To store parent pointers
    Map<BinaryTree, BinaryTree> parentMap = new HashMap<>();
    BinaryTree targetNode = findTargetAndParents(root, target, parentMap);

    if (targetNode == null) return new ArrayList<>();

    // Perform BFS from targetNode to find all nodes at distance k
    Queue<BinaryTree> queue = new LinkedList<>();
    queue.offer(targetNode);

    Set<BinaryTree> visited = new HashSet<>();
    visited.add(targetNode);

    int currentLevel = 0;

    while (!queue.isEmpty()) {
      if (currentLevel == k) break;

      int size = queue.size();
      for (int i = 0; i < size; i++) {
        BinaryTree currentNode = queue.poll();

        if (currentNode.left != null && !visited.contains(currentNode.left)) {
          visited.add(currentNode.left);
          queue.offer(currentNode.left);
        }

        if (currentNode.right != null && !visited.contains(currentNode.right)) {
          visited.add(currentNode.right);
          queue.offer(currentNode.right);
        }

        BinaryTree parentNode = parentMap.get(currentNode);
        if (parentNode != null && !visited.contains(parentNode)) {
          visited.add(parentNode);
          queue.offer(parentNode);
        }
      }
      currentLevel++;
    }

    // Collect nodes at distance k
    List<Integer> result = new ArrayList<>();
    while (!queue.isEmpty()) {
      result.add(queue.poll().value);
    }

    return result;
  }

  public static void main(String[] args) {
    BinaryTree root = new BinaryTree(1);
    root.left = new BinaryTree(2);
    root.right = new BinaryTree(3);
    root.left.left = new BinaryTree(4);
    root.left.right = new BinaryTree(5);
    root.right.right = new BinaryTree(6);
    root.right.right.left = new BinaryTree(7);
    root.right.right.right = new BinaryTree(8);

    int target = 3;
    int k = 2;

    System.out.println(nodesAtDistanceKBruteForce(root, target, k)); // Output: [2, 7, 8]
  }
}
