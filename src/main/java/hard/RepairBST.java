/*To solve the problem of fixing a Binary Search Tree (BST) where exactly two nodes have had their values swapped, we can follow an approach that leverages the in-order traversal of the BST.

Key Observations:
In-order Traversal: In a correctly formed BST, an in-order traversal (left, root, right) should produce a sorted sequence of values.
Swapped Nodes: If two nodes are swapped, the in-order traversal will show deviations from this sorted order.
Steps to Solve the Problem:
In-order Traversal:

Perform an in-order traversal of the BST to detect the two nodes that have been swapped.
During the traversal, track nodes where the current node’s value is less than the previous node’s value.
Identify the Swapped Nodes:

The first anomaly will occur between the first node where the value is higher than it should be, and the next node in the sequence.
The second anomaly will occur later when a value is smaller than the previous node's value. These two nodes are the ones that need to be swapped.
Swap the Values:

After identifying the two nodes, swap their values to correct the BST.
Java Implementation:*/
package hard;

public class RepairBST {
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

  private TreeNode firstNode = null;
  private TreeNode secondNode = null;
  private TreeNode previousNode = new TreeNode(Integer.MIN_VALUE);

  public void fixBST(TreeNode root) {
    // Perform in-order traversal and find the two swapped nodes
    inorderTraversal(root);

    // Swap the values of the two nodes
    if (firstNode != null && secondNode != null) {
      int temp = firstNode.value;
      firstNode.value = secondNode.value;
      secondNode.value = temp;
    }
  }

  private void inorderTraversal(TreeNode node) {
    if (node == null) return;

    // Traverse the left subtree
    inorderTraversal(node.left);

    // Check if previous node's value is greater than current node's value
    if (previousNode != null && previousNode.value > node.value) {
      // Identify the first and second nodes to be swapped
      if (firstNode == null) {
        firstNode = previousNode;
      }
      secondNode = node;
    }

    // Update the previous node
    previousNode = node;

    // Traverse the right subtree
    inorderTraversal(node.right);
  }

  public static void main(String[] args) {
    // Constructing the tree from the example
    TreeNode root = new TreeNode(10);
    root.left = new TreeNode(7);
    root.right = new TreeNode(20);
    root.left.left = new TreeNode(3);
    root.left.right = new TreeNode(12); // Swapped node
    root.left.left.left = new TreeNode(2);
    root.right.left = new TreeNode(8); // Swapped node
    root.right.right = new TreeNode(22);
    root.right.left.right = new TreeNode(14);

    // Fixing the BST
    RepairBST solution = new RepairBST();
    printInOrder(root);
    System.out.println();
    System.out.println("Above is before and below is after");
    solution.fixBST(root);

    // Output the in-order traversal of the fixed tree
    printInOrder(root); // Should match the expected output
  }

  private static void printInOrder(TreeNode node) {
    if (node == null) return;
    printInOrder(node.left);
    System.out.print(node.value + " ");
    printInOrder(node.right);
  }
} /*
  Explanation of the Code:
  TreeNode Class: Represents a node in the BST with value, left, and right pointers.
  fixBST Method:
  Performs in-order traversal to detect the nodes that are out of order.
  Swaps the values of the two nodes to correct the BST.
  inorderTraversal Method:
  Recursively traverses the BST.
  Identifies nodes that are out of order based on the previous node's value.
  printInOrder Method:
  Prints the in-order traversal of the tree to verify the correction.
  Time and Space Complexity:
  Time Complexity: O(n), where n is the number of nodes in the BST. This is because we traverse the tree exactly once.
  Space Complexity: O(h), where h is the height of the tree due to the recursion stack. For a balanced BST, this will be O(log n), and for a skewed tree, it can be O(n).*/
