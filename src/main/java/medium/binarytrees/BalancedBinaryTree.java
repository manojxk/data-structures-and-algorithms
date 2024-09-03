package medium.binarytrees;

class BalancedBinaryTree {
  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
      this.val = 0;
      this.left = null;
      this.right = null;
    }

    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  // Function to calculate the height of the tree
  private int height(TreeNode root) {
    if (root == null) {
      return 0;
    }
    // Recursively calculate the height of the left and right subtrees
    return 1 + Math.max(height(root.left), height(root.right));
  }

  // Function to check if the binary tree is balanced
  public boolean isBalanced(TreeNode root) {
    if (root == null) {
      return true;
    }

    // Get the height of left and right subtrees
    int leftHeight = height(root.left);
    int rightHeight = height(root.right);

    // Check if the current node is balanced and recursively check for left and right subtrees
    if (Math.abs(leftHeight - rightHeight) <= 1
        && isBalanced(root.left)
        && isBalanced(root.right)) {
      return true;
    }

    return false;
  }
}

/*Time Complexity:
The time complexity of the given solution is O(n^2) in the worst case, where n is the number of nodes in the tree. This is because for each node, we are calculating the height of its subtrees, leading to redundant calculations.
Space Complexity:
The space complexity is O(h), where h is the height of the tree, due to the recursive call stack.*/



/*import java.util.*;

class Program {
  // This is an input class. Do not edit.
  static class BinaryTree {
    public int value;
    public BinaryTree left = null;
    public BinaryTree right = null;

    public BinaryTree(int value) {
      this.value = value;
    }
  }

  static class TreeInfo {
    public boolean isBalanced;
    public int height;

    public TreeInfo(boolean isBalanced, int height) {
      this.isBalanced = isBalanced;
      this.height = height;
    }
  }

  // O(n) time | O(h) space - where n is the number of nodes in the binary tree
  public boolean heightBalancedBinaryTree(BinaryTree tree) {
    TreeInfo treeInfo = getTreeInfo(tree);
    return treeInfo.isBalanced;
  }

  public TreeInfo getTreeInfo(BinaryTree node) {
    if (node == null) {
      return new TreeInfo(true, -1);
    }

    TreeInfo leftSubtreeInfo = getTreeInfo(node.left);
    TreeInfo rightSubtreeInfo = getTreeInfo(node.right);

    boolean isBalanced = leftSubtreeInfo.isBalanced && rightSubtreeInfo.isBalanced &&
            Math.abs(leftSubtreeInfo.height - rightSubtreeInfo.height) <= 1;

    int height = Math.max(leftSubtreeInfo.height, rightSubtreeInfo.height) + 1;

    return new TreeInfo(isBalanced, height);
  }
}*/
/*
Explanation:
BinaryTree Class:

Represents a node in the binary tree, containing a value, and references to left and right child nodes.
TreeInfo Class:

A helper class to store two pieces of information:
isBalanced: A boolean indicating whether the subtree rooted at the node is balanced.
        height: The height of the subtree rooted at the node.
heightBalancedBinaryTree Method:

The main method that checks whether the entire binary tree is height-balanced.
Calls getTreeInfo on the root node and returns the isBalanced attribute from the resulting TreeInfo object.
getTreeInfo Method:

A recursive method that computes the TreeInfo for the subtree rooted at the given node.
If the node is null, it returns a TreeInfo object with isBalanced as true and height as -1.
It recursively computes the TreeInfo for the left and right subtrees.
The tree rooted at the current node is balanced if:
Both the left and right subtrees are balanced.
The difference in height between the left and right subtrees is at most 1.
The height of the current node's subtree is 1 plus the maximum of the heights of the left and right subtrees.
Finally, it returns a TreeInfo object containing the computed isBalanced and height values.
Time Complexity:
O(n), where n is the number of nodes in the tree. Each node is visited exactly once.
Space Complexity:
O(h), where h is the height of the tree. This space is required for the recursive call stack. In the worst case (for a completely unbalanced tree), this space complexity can become O(n).*/
