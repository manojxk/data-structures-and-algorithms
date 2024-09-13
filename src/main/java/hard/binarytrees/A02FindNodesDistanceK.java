package hard.binarytrees;

/*
 Problem: Find Nodes Distance K

 You are given a binary tree, a target node in the tree, and an integer value K.
 Your task is to return the values of all nodes that are exactly K distance away from the target node.

 Example 1:
 Input:
        root = [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]
        target = 5
        K = 2
 Output: [7, 4, 1]

 Explanation:
         3
        / \
       5   1
      / \   / \
     6   2 0   8
        / \
       7   4

 Nodes 7, 4, and 1 are all at distance 2 from the target node 5.

 Constraints:
 - The number of nodes in the binary tree is between 1 and 500.
 - Each node has a unique value.

 Solution Approach:
 1. Perform a DFS to map all nodes to their parents.
 2. Start a BFS from the target node and explore nodes at a distance K from the target.
 3. Use a set to track visited nodes to avoid revisiting.
*/

import java.util.*;

public class A02FindNodesDistanceK {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  // Function to find all nodes at distance K from the target node
  public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    // Step 1: Create a parent map to store parent references for all nodes
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    buildParentMap(root, null, parentMap);

    // Step 2: Perform BFS starting from the target node
    Queue<TreeNode> queue = new LinkedList<>();
    Set<TreeNode> visited = new HashSet<>();
    queue.add(target);
    visited.add(target);

    int currentDistance = 0;

    // Step 3: BFS traversal to find nodes at distance K
    while (!queue.isEmpty()) {
      if (currentDistance == K) {
        for (TreeNode node : queue) {
          result.add(node.val);
        }
        return result;
      }

      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode currentNode = queue.poll();

        // Add the left child, right child, and parent of the current node
        if (currentNode.left != null && !visited.contains(currentNode.left)) {
          queue.add(currentNode.left);
          visited.add(currentNode.left);
        }
        if (currentNode.right != null && !visited.contains(currentNode.right)) {
          queue.add(currentNode.right);
          visited.add(currentNode.right);
        }
        TreeNode parentNode = parentMap.get(currentNode);
        if (parentNode != null && !visited.contains(parentNode)) {
          queue.add(parentNode);
          visited.add(parentNode);
        }
      }
      currentDistance++;
    }

    return result;
  }

  // Helper function to build the parent map
  private void buildParentMap(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
    if (node == null) return;
    parentMap.put(node, parent);
    buildParentMap(node.left, node, parentMap);
    buildParentMap(node.right, node, parentMap);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A02FindNodesDistanceK solution = new A02FindNodesDistanceK();

    // Example tree: [3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(5);
    root.right = new TreeNode(1);
    root.left.left = new TreeNode(6);
    root.left.right = new TreeNode(2);
    root.right.left = new TreeNode(0);
    root.right.right = new TreeNode(8);
    root.left.right.left = new TreeNode(7);
    root.left.right.right = new TreeNode(4);

    TreeNode target = root.left; // Target node is 5
    int K = 2;

    List<Integer> result = solution.distanceK(root, target, K);
    System.out.println("Nodes at distance K: " + result); // Output: [7, 4, 1]
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. We traverse the tree twice: once to build the parent map and once during the BFS.

   Space Complexity:
   - O(n), where n is the number of nodes in the binary tree. We use a map to store parent references and a queue to perform BFS.
  */
}
