package cools.binarytrees.bst;

/*
 Problem: Binary Search Tree (BST) - Search, Insert, and Remove

 A Binary Search Tree (BST) is a binary tree where each node has a value:
   - Nodes in the left subtree of a node have values less than the node's value.
   - Nodes in the right subtree of a node have values greater than the node's value.

 In this problem, implement the following operations on a BST:
   1. Search for a given value.
   2. Insert a new value into the BST while maintaining the properties of the BST.
   3. Remove a value from the BST and restructure it to maintain the BST properties.

 Example 1:
 Input: Insert values [50, 30, 20, 40, 70, 60, 80], Search value 40, Remove value 50
 Output (In-order traversal after insertion): [20, 30, 40, 50, 60, 70, 80]
 Output (Search): "Node with value 40 found."
 Output (In-order traversal after removal of 50): [20, 30, 40, 60, 70, 80]

 Example 2:
 Input: Insert values [10, 5, 15, 2, 7, 13, 18], Search value 5, Remove value 15
 Output (In-order traversal after insertion): [2, 5, 7, 10, 13, 15, 18]
 Output (Search): "Node with value 5 found."
 Output (In-order traversal after removal of 15): [2, 5, 7, 10, 13, 18]

 Constraints:
 - The number of nodes in the tree is in the range [0, 500].
 - -1000 <= Node.val <= 1000
 - Operations like Search, Insert, and Remove should maintain the properties of the BST.

 Solution Approach:
 1. **Search Operation**:
    - Traverse the BST starting from the root. Compare the target value with the current node’s value.
    - If the target is smaller, move to the left subtree; if greater, move to the right subtree. If found, return the node.

 2. **Insert Operation**:
    - Start at the root and recursively find the correct position for the new value.
    - Insert the new value as a leaf node while maintaining the BST property.

 3. **Remove Operation**:
    - Locate the node to be deleted. If it has no children, simply remove it.
    - If it has one child, replace it with its child.
    - If it has two children, find the inorder successor (smallest node in the right subtree), replace the value of the node to be deleted with the successor, and remove the successor.

*/

public class A04BinarySearchTree {

  // Definition of the TreeNode for BST
  static class TreeNode {
    int val;
    TreeNode left, right;

    public TreeNode(int val) {
      this.val = val;
    }
  }

  // Search for a value in the BST
  public TreeNode search(TreeNode root, int key) {
    // Base case: root is null or key is present at root
    if (root == null || root.val == key) {
      return root;
    }

    // Key is greater than root's value, search in the right subtree
    if (key > root.val) {
      return search(root.right, key);
    }

    // Key is smaller than root's value, search in the left subtree
    return search(root.left, key);
  }

  // Insert a new node with a given value into the BST
  public TreeNode insert(TreeNode root, int key) {
    // Base case: if the tree is empty, return a new node
    if (root == null) {
      return new TreeNode(key);
    }

    // Recur down the tree to insert the node in the correct position
    if (key < root.val) {
      root.left = insert(root.left, key);
    } else if (key > root.val) {
      root.right = insert(root.right, key);
    }

    // Return the (unchanged) root pointer
    return root;
  }

  // Remove a node with a given value from the BST
  public TreeNode remove(TreeNode root, int key) {
    // Base case: if the tree is empty
    if (root == null) {
      return null;
    }

    // Recur down the tree to find the node to remove
    if (key < root.val) {
      root.left = remove(root.left, key);
    } else if (key > root.val) {
      root.right = remove(root.right, key);
    } else {
      // Node to be deleted is found

      // Case 1: Node with only one child or no child
      if (root.left == null) {
        return root.right; // Return the right subtree
      } else if (root.right == null) {
        return root.left; // Return the left subtree
      }

      // Case 2: Node with two children
      // Find the smallest node in the right subtree (inorder successor)
      TreeNode minNode = findMin(root.right);

      // Replace the value of the node to be deleted with the inorder successor's value
      root.val = minNode.val;

      // Delete the inorder successor
      root.right = remove(root.right, minNode.val);
    }

    return root;
  }

  // Helper function to find the minimum node in a subtree
  private TreeNode findMin(TreeNode root) {
    while (root.left != null) {
      root = root.left;
    }
    return root;
  }

  // In-order traversal of the BST for displaying the tree
  public void inOrderTraversal(TreeNode root) {
    if (root != null) {
      inOrderTraversal(root.left);
      System.out.print(root.val + " ");
      inOrderTraversal(root.right);
    }
  }

  // Main function to run and test the operations
  public static void main(String[] args) {
    A04BinarySearchTree bst = new A04BinarySearchTree();

    TreeNode root = null;

    // Insert values into the BST
    root = bst.insert(root, 50);
    root = bst.insert(root, 30);
    root = bst.insert(root, 20);
    root = bst.insert(root, 40);
    root = bst.insert(root, 70);
    root = bst.insert(root, 60);
    root = bst.insert(root, 80);

    System.out.println("In-order traversal of the initial BST:");
    bst.inOrderTraversal(root); // Expected output: 20 30 40 50 60 70 80
    System.out.println();

    // Search for a value in the BST
    int keyToSearch = 40;
    TreeNode foundNode = bst.search(root, keyToSearch);
    if (foundNode != null) {
      System.out.println("Node with value " + keyToSearch + " found.");
    } else {
      System.out.println("Node with value " + keyToSearch + " not found.");
    }

    // Remove a node from the BST
    int keyToRemove = 50;
    root = bst.remove(root, keyToRemove);
    System.out.println("In-order traversal after removing " + keyToRemove + ":");
    bst.inOrderTraversal(root); // Expected output: 20 30 40 60 70 80
    System.out.println();
  }

  /*
   Time Complexity:
   - Search: O(h), where h is the height of the tree. In the worst case (unbalanced tree), the height can be O(n). In the best case (balanced tree), it's O(log n).
   - Insert: O(h), where h is the height of the tree. Similar to the search operation.
   - Remove: O(h), where h is the height of the tree. In the worst case, removal can involve finding the inorder successor and restructuring the tree.

   Space Complexity:
   - O(h) for recursive calls, where h is the height of the tree.
  */
}
