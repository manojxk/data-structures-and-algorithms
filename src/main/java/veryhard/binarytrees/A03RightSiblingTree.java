package veryhard.binarytrees;

/*
 Problem: Transform a Binary Tree into a Right Sibling Tree.

 Given a Binary Tree, transform it into a Right Sibling Tree and return its root.
 A Right Sibling Tree is obtained by making every node in a Binary Tree have its
 right property point to its right sibling instead of its right child. A node's
 right sibling is the node immediately to its right on the same level or null
 if there is no node immediately to its right.

 The transformation should be done in place.

 Example Input:
          1
        /   \
       2     3
      / \   / \
     4   5 6   7
    / \     \     \
   8   9    10   12
                / \
               14  13

 Example Output:
           1
        /
       2-----3
      / \
     4---5-----6-----7
    / \     /       /
   8---9  10-----12--13
                /
               14

 Solution Steps:
 1. Base Case: If the tree is empty (i.e., root is null), return null.
 2. For each node, if the node has a left child, set its left child's right pointer to point to its right sibling (i.e., the right child).
 3. If the node has a right child, connect it to the next sibling (the left child of its parent's next right sibling).
 4. Recursively process the left and right children of the node.
 5. The transformation must be done in place; no new data structures should be created.
*/

public class A03RightSiblingTree {

  // Definition for a binary tree node.
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

  // Function to transform the binary tree into a Right Sibling Tree
  public TreeNode transformToRightSiblingTree(TreeNode root) {
    if (root == null) return null; // Base case: If the root is null, return null.
    connectSiblings(root); // Start the recursive transformation from the root.
    return root; // Return the transformed tree's root.
  }

  // Helper function to recursively connect siblings
  private void connectSiblings(TreeNode node) {
    if (node == null || node.left == null)
      return; // Base case: If the node or its left child is null, return.

    // Connect the left child's right to the right child (sibling connection within the same parent)
    node.left.right = node.right;

    // If the current node has a right child, connect its right child to the next sibling in line
    if (node.right != null) {
      node.right.right =
          getNextSibling(node); // Connect the right child's right to the next sibling.
    }

    // Recursively apply the transformation to the left and right subtrees
    connectSiblings(node.left);
    connectSiblings(node.right);
  }

  // Function to find the next sibling for the right child of the current node
  private TreeNode getNextSibling(TreeNode node) {
    // Traverse the right subtree of the parent to find the next sibling at the same level
    while (node != null) {
      if (node.right != null && node.right.left != null) {
        return node.right.left; // Return the next left child if found.
      }
      node = node.right; // Move to the next node in the parent's right subtree.
    }
    return null; // If no sibling found, return null.
  }

  // Driver code to test the Right Sibling Tree transformation
  public static void main(String[] args) {
    A03RightSiblingTree solution = new A03RightSiblingTree();

    // Example: Create the sample binary tree
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(7);
    root.left.left.left = new TreeNode(8);
    root.left.left.right = new TreeNode(9);
    root.left.right.right = new TreeNode(10);
    root.right.left.left = new TreeNode(11);
    root.right.right.left = new TreeNode(12);
    root.right.right.right = new TreeNode(13);
    root.right.right.left.left = new TreeNode(14);

    // Transform the tree into a Right Sibling Tree
    TreeNode transformedRoot = solution.transformToRightSiblingTree(root);

    // Output root value to confirm the transformation
    System.out.println(
        "Transformed Right Sibling Tree root: " + transformedRoot.val); // Should print 1
  }

  /*
   Time Complexity:
   - O(n), where n is the number of nodes in the binary tree. Each node is visited exactly once during the recursion.

   Space Complexity:
   - O(h), where h is the height of the tree, due to the recursion stack.
   - In the worst case, the height could be O(n) for a skewed tree, leading to O(n) space complexity.
   - For a balanced tree, the height would be O(log n), resulting in O(log n) space complexity.
  */
}
