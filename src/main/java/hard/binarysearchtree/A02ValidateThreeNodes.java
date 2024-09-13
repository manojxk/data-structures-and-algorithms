package hard.binarysearchtree;

/*
 Problem: Validate Three Nodes

 You are given three nodes in a Binary Search Tree (BST). Write a function that takes in these three nodes
 and returns a boolean indicating whether one of the nodes is an ancestor of the second node, and the second node is an ancestor of the third node.
 In other words, you need to verify if the first node is an ancestor of the second node, and the second node is an ancestor of the third node (or vice versa).

 A Binary Search Tree is defined as follows:
 - The left subtree of a node contains only nodes with values less than the node's value.
 - The right subtree of a node contains only nodes with values greater than the node's value.

 Example 1:
 Input: nodeOne = 5, nodeTwo = 2, nodeThree = 3
 Output: true
 Explanation: node 5 is an ancestor of node 2, and node 2 is an ancestor of node 3.

 Example 2:
 Input: nodeOne = 2, nodeTwo = 5, nodeThree = 3
 Output: false
 Explanation: node 2 cannot be an ancestor of node 5.

 Example 3:
 Input: nodeOne = 5, nodeTwo = 8, nodeThree = 6
 Output: false
 Explanation: No valid ancestor relationship between nodes 5, 8, and 6.

 Constraints:
 - The tree contains distinct values.

 Solution Approach:
 1. Check if nodeOne is an ancestor of nodeTwo and nodeTwo is an ancestor of nodeThree.
 2. Also, check the opposite case: nodeThree is an ancestor of nodeTwo, and nodeTwo is an ancestor of nodeOne.
 3. Use BST properties to traverse the tree and validate ancestor relationships.
*/

public class A02ValidateThreeNodes {

  // Node class definition
  static class BST {
    int value;
    BST left;
    BST right;

    public BST(int value) {
      this.value = value;
    }
  }

  // Function to check if nodeOne is an ancestor of nodeTwo and nodeTwo is an ancestor of nodeThree
  public static boolean validateThreeNodes(BST nodeOne, BST nodeTwo, BST nodeThree) {
    // Check the first case: nodeOne -> nodeTwo -> nodeThree
    if (isAncestor(nodeOne, nodeTwo) && isAncestor(nodeTwo, nodeThree)) {
      return true;
    }

    // Check the second case: nodeThree -> nodeTwo -> nodeOne
    if (isAncestor(nodeThree, nodeTwo) && isAncestor(nodeTwo, nodeOne)) {
      return true;
    }

    // If none of the cases hold, return false
    return false;
  }

  // Helper function to determine if nodeA is an ancestor of nodeB in the BST
  private static boolean isAncestor(BST nodeA, BST nodeB) {
    // Traverse down the tree starting from nodeA, searching for nodeB
    while (nodeA != null && nodeA != nodeB) {
      if (nodeB.value < nodeA.value) {
        nodeA = nodeA.left;
      } else {
        nodeA = nodeA.right;
      }
    }
    return nodeA == nodeB;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1:
    BST nodeOne = new BST(5);
    BST nodeTwo = new BST(2);
    BST nodeThree = new BST(3);
    nodeOne.left = nodeTwo;
    nodeTwo.right = nodeThree;

    System.out.println(validateThreeNodes(nodeOne, nodeTwo, nodeThree)); // Output: true

    // Example 2:
    BST nodeFour = new BST(8);
    BST nodeFive = new BST(6);
    nodeOne.right = nodeFour;
    nodeFour.left = nodeFive;

    System.out.println(validateThreeNodes(nodeOne, nodeFour, nodeFive)); // Output: false
  }

  /*
   Time Complexity:
   - O(h), where h is the height of the Binary Search Tree. We traverse down the tree from the given node to check for ancestors.

   Space Complexity:
   - O(1), since we only use constant extra space during the traversal.
  */
}
