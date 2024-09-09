package cools.binarytrees;

/*
 Problem: Populating Next Right Pointers in Each Node II

 Given a binary tree where each node has an integer value and left, right, and next pointers,
 populate each node's next pointer to point to its next right node.
 If there is no next right node, the next pointer should be set to null.

 Initially, all next pointers are set to null.

 Example:

 Input:
       1
     /  \
    2    3
   / \    \
  4   5    7

 Output:
       1 -> NULL
     /  \
    2 -> 3 -> NULL
   / \     \
  4-> 5 ->  7 -> NULL

 Explanation:
 The next pointers should be set such that each node points to its next right node.
*/

/*
 Solution Steps:

 1. Perform a level-order traversal of the binary tree (BFS approach).
 2. For each node, link its next pointer to the node on the right at the same level,
    if such a node exists.
 3. Use a queue to track the nodes at each level, and iterate through the level's nodes.
 4. For each node at the current level, link its next pointer to the node next in the queue.
 5. If there is no node next in the queue at the same level, set the next pointer to null.
*/

import java.util.*;

public class A08PopulateNextRightPointer {
  // Definition for a binary tree node with the next pointer.
  static class Node {
    int val;
    Node left;
    Node right;
    Node next;

    Node(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
      this.next = null;
    }
  }

  public Node connect(Node root) {
    if (root == null) return root;

    // Use a queue to facilitate level-order traversal
    Queue<Node> q = new LinkedList<>();
    q.add(root);

    while (!q.isEmpty()) {
      int n = q.size(); // Number of nodes at the current level

      for (int i = 1; i <= n; i++) { //
        Node x = q.poll(); // Get the front node from the queue

        // If it's not the last node in the current level, connect it to the next node
        if (i < n) {
          Node y = q.peek(); // Peek the next node in the queue
          x.next = y;
        } else {
          x.next = null; // Last node of the level should point to null
        }

        // Add the left and right children of the current node to the queue
        if (x.left != null) q.add(x.left);
        if (x.right != null) q.add(x.right);
      }
    }

    return root;
  }

  public static void main(String[] args) {
    A08PopulateNextRightPointer solution = new A08PopulateNextRightPointer();

    // Example: Construct a binary tree [1,2,3,4,5,6,7]
    Node root = new Node(1);
    root.left = new Node(2);
    root.right = new Node(3);
    root.left.left = new Node(4);
    root.left.right = new Node(5);
    root.right.left = new Node(6);
    root.right.right = new Node(7);

    // Populate the next right pointers
    Node result = solution.connect(root);

    // Output for validation, e.g., checking connections
    System.out.println("Next of Node 1: " + (result.next == null ? "null" : result.next.val));
    System.out.println("Next of Node 2: " + result.left.next.val); // Should print 3
    System.out.println("Next of Node 4: " + result.left.left.next.val); // Should print 5
  }
  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited exactly once during the level-order traversal.

   Space Complexity:
   - O(n), where n is the number of nodes in the binary tree. In the worst case, the space used by the queue is proportional to the number of nodes at the maximum level of the tree, which could be up to n/2 nodes for a complete binary tree.
  */
}
