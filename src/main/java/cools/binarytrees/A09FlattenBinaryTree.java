package cools.binarytrees;

/*
 Problem: Flatten Binary Tree to Linked List

 Given the root of a binary tree, flatten the tree into a "linked list".

 The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list,
 and the left child pointer is always null.

 The "linked list" should be in the same order as a pre-order traversal of the binary tree.

 Example:

 Input:
        1
       / \
      2   5
     / \   \
    3   4   6

 Output:
 1 -> 2 -> 3 -> 4 -> 5 -> 6

 Explanation: After flattening, the binary tree is transformed into a "linked list" with each node's right child pointing to the next node.
*/

/*
 Solution Steps:

 1. Use a recursive approach to flatten the tree.
 2. For each node, recursively flatten the left and right subtrees.
 3. Temporarily store the right subtree, then move the left subtree to the right.
 4. Move to the end of the new right subtree (previously the left subtree).
 5. Attach the stored right subtree to the end of the flattened left subtree.
 6. The left subtree of each node is set to null since we are simulating a linked list.
*/

public class A09FlattenBinaryTree {

  // Main function to run and test the solution
  public static void main(String[] args) {
    A09FlattenBinaryTree solution = new A09FlattenBinaryTree();

    // Example: Create a sample binary tree: [1, 2, 5, 3, 4, null, 6]
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(5);
    root.left.left = new TreeNode(3);
    root.left.right = new TreeNode(4);
    root.right.right = new TreeNode(6);

    // Flatten the binary tree into a "linked list"
    solution.flatten(root);

    // Output the flattened tree (linked list)
    TreeNode current = root;
    while (current != null) {
      System.out.print(current.val + " -> ");
      current = current.right;
    }
    // Expected Output: 1 -> 2 -> 3 -> 4 -> 5 -> 6 ->
  }

  // Function to flatten the binary tree into a "linked list"
  public void flatten(TreeNode root) {
    // Base case: if the root is null, return
    if (root == null) return;

    // Recursively flatten the left and right subtrees
    flatten(root.left);
    flatten(root.right);

    // Temporarily store the right subtree
    TreeNode tempRight = root.right;

    // Move the left subtree to the right
    root.right = root.left;
    root.left = null; // Set the left subtree to null

    // Move to the end of the new right subtree (which was the left subtree)
    TreeNode current = root;
    while (current.right != null) {
      current = current.right;
    }

    // Attach the temporarily stored right subtree to the end of the new right subtree
    current.right = tempRight;
  }

  // TreeNode class definition
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited once during the flattening process.

   Space Complexity:
   - O(h), where h is the height of the tree. This is the space required for the recursion stack.
   - In the worst case (unbalanced tree), the space complexity is O(n). In the best case (balanced tree), the space complexity is O(log n).
  */
}
