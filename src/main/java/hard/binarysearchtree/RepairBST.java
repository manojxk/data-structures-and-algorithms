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
package hard.binarysearchtree;

public class RepairBST {
  static class BSTNode {
    int value;
    BSTNode left;
    BSTNode right;

    public BSTNode(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
    }
  }

  private static BSTNode firstElement = null;
  private static BSTNode secondElement = null;
  private static BSTNode prevElement = new BSTNode(Integer.MIN_VALUE);

  // Helper function to perform in-order traversal and find nodes to swap
  private static void traverse(BSTNode root) {
    if (root == null) return;

    traverse(root.left);

    if (firstElement == null && prevElement.value >= root.value) {
      firstElement = prevElement;
    }
    if (firstElement != null && prevElement.value >= root.value) {
      secondElement = root;
    }
    prevElement = root;

    traverse(root.right);
  }

  // Function to correct the BST
  public static void correctBST(BSTNode root) {
    // Reset pointers
    firstElement = null;
    secondElement = null;
    prevElement = new BSTNode(Integer.MIN_VALUE);

    traverse(root);

    if (firstElement != null && secondElement != null) {
      int temp = firstElement.value;
      firstElement.value = secondElement.value;
      secondElement.value = temp;
    }
  }

  public static void main(String[] args) {
    // Sample tree setup
    BSTNode root = new BSTNode(10);
    root.left = new BSTNode(7);
    root.right = new BSTNode(20);
    root.left.left = new BSTNode(3);
    root.left.right = new BSTNode(12);
    root.right.left = new BSTNode(8);
    root.right.right = new BSTNode(22);
    root.left.left.left = new BSTNode(2);
    root.right.left.right = new BSTNode(14);

    correctBST(root);

    // Output corrected tree structure for validation
  }
}

 /*
 Brute Force Solution
Approach:
The brute force approach involves in-order traversal of the BST to find the two nodes that are out of order and then swapping their values back.

Time Complexity:
O(n): We traverse each node exactly once.
Space Complexity:
O(n): Space used by the recursion stack during the traversal, where n is the number of nodes.
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
