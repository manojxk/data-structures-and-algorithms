/*Given the root of a binary tree, return the length of the diameter of the tree.

The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

The length of a path between two nodes is represented by the number of edges between them.*/

package medium;

// Definition for a binary tree node.

public class BinaryTreeDiameter {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  // Helper function to calculate the height of a binary tree
  public int height(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return 1 + Math.max(height(root.left), height(root.right));
  }

  // Recursive function to calculate the diameter of a binary tree
  public int diameter(TreeNode root) {
    if (root == null) return 0;

    int lheight = height(root.left);
    int rheight = height(root.right);

    int ldiameter = diameter(root.left);
    int rdiameter = diameter(root.right);

    return Math.max(lheight + rheight + 1, Math.max(ldiameter, rdiameter));
  }

  // Function to return the diameter of the binary tree
  public int diameterOfBinaryTree(TreeNode root) {
    return diameter(root) - 1; // Subtract 1 because the problem asks for the number of edges
  }

  // Main method to test the functionality
  public static void main(String[] args) {
    // Constructing the binary tree from the sample input
    TreeNode tree = new TreeNode(1);
    tree.left = new TreeNode(3);
    tree.right = new TreeNode(2);
    tree.left.left = new TreeNode(7);
    tree.left.right = new TreeNode(4);
    tree.left.left.left = new TreeNode(8);
    tree.left.right.right = new TreeNode(5);
    tree.left.left.left.left = new TreeNode(9);
    tree.left.right.right.right = new TreeNode(6);

    BinaryTreeDiameter solution = new BinaryTreeDiameter();
    int diameter = solution.diameterOfBinaryTree(tree);

    // Output the diameter of the binary tree
    System.out.println("Diameter of the binary tree is: " + diameter);
  }
}
