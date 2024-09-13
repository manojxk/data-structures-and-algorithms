package hard.binarysearchtree;

/*
 Problem: Repair Binary Search Tree (BST)

 Given a Binary Search Tree (BST) in which two nodes were swapped by mistake, your task is to recover the tree without changing its structure.

 A Binary Search Tree (BST) is defined as follows:
 - The left subtree of a node contains only nodes with values less than the node's value.
 - The right subtree of a node contains only nodes with values greater than or equal to the node's value.

 You must restore the tree by swapping the two nodes back to their correct positions.

 Example 1:
 Input: [3, 1, 4, null, null, 2]
 Output: [3, 1, 4, null, null, 2]
 Explanation: The tree was:
         3
        / \
       1   4
          /
         2
 The nodes 2 and 4 were swapped. You should return:
         3
        / \
       1   2
          /
         4

 Constraints:
 - You are guaranteed to have exactly two nodes swapped by mistake.
 - The problem should be solved without using additional space (in-place).

 Solution Approach:
 1. Perform an in-order traversal to detect the two nodes that were swapped (in a BST, in-order traversal should yield sorted values).
 2. Find the two swapped nodes:
    - The first node is found when a node has a value greater than the next node in the in-order sequence.
    - The second node is found when another node has a value greater than its subsequent node.
 3. Swap the values of the two nodes to restore the BST.
*/

public class A03RepairBST {

  // Definition for a binary tree node
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }
  }

  TreeNode firstNode = null;
  TreeNode secondNode = null;
  TreeNode prevNode = new TreeNode(Integer.MIN_VALUE); // Initialize to smallest possible value

  // Function to repair the Binary Search Tree
  public void recoverTree(TreeNode root) {
    // Step 1: Perform an in-order traversal to detect the swapped nodes
    traverseInOrder(root);

    // Step 2: Swap the values of the two swapped nodes
    if (firstNode != null && secondNode != null) {
      int temp = firstNode.val;
      firstNode.val = secondNode.val;
      secondNode.val = temp;
    }
  }

  // Helper function to perform an in-order traversal and detect swapped nodes
  private void traverseInOrder(TreeNode root) {
    if (root == null) {
      return;
    }

    // Traverse the left subtree
    traverseInOrder(root.left);

    // Detect swapped nodes
    if (prevNode.val > root.val) {
      // First swapped node is the larger one
      if (firstNode == null) {
        firstNode = prevNode;
      }
      // Second swapped node is the smaller one
      secondNode = root;
    }

    // Update the previous node
    prevNode = root;

    // Traverse the right subtree
    traverseInOrder(root.right);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A03RepairBST solution = new A03RepairBST();

    // Example tree: 3, 1, 4, null, null, 2 (nodes 2 and 4 are swapped)
    TreeNode root = new TreeNode(3);
    root.left = new TreeNode(1);
    root.right = new TreeNode(4);
    root.right.left = new TreeNode(2);

    // Before fixing the tree
    System.out.println("Before repairing:");
    printInOrder(root); // Expected Output: 1 3 2 4 (which is incorrect for a BST)

    // Repair the tree
    solution.recoverTree(root);

    // After fixing the tree
    System.out.println("\nAfter repairing:");
    printInOrder(root); // Expected Output: 1 3 4 2 (BST is now valid)
  }

  // Helper function to print the tree in order (for validation)
  public static void printInOrder(TreeNode root) {
    if (root != null) {
      printInOrder(root.left);
      System.out.print(root.val + " ");
      printInOrder(root.right);
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the BST. We perform an in-order traversal, which processes each node once.

   Space Complexity:
   - O(h), where h is the height of the BST. The space is used by the recursive stack in the in-order traversal. The height of the tree is O(log n) for a balanced tree and O(n) in the worst case (skewed tree).
  */
}
