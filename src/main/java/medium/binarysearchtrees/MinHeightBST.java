package medium.binarysearchtrees;

/*
 Problem: Min Height BST

 You are given a sorted array of distinct integers. Your task is to create a binary search tree (BST) with minimal height.
 The height of a binary search tree is defined as the number of edges on the longest path from the root to a leaf.

 Example:

 Input:
   array = [1, 2, 5, 7, 10, 13, 14, 15, 22]
 Output:
   A binary search tree with minimal height.

 Explanation:
 - The idea is to create the root node from the middle element of the array, and then recursively do the same for the left and right halves of the array.
 - This ensures that the tree is balanced, and thus has minimal height.

*/

/*
 Solution Steps:

 1. Start with the entire array and pick the middle element as the root of the tree.
 2. Recursively build the left subtree using the elements left of the middle.
 3. Recursively build the right subtree using the elements right of the middle.
 4. Repeat the process until all elements are inserted into the tree.

 This process ensures that the height of the tree is minimal because we are always dividing the array into two halves, which balances the tree.
*/

public class MinHeightBST {

  // Class representing a node of the binary search tree
  static class BST {
    int value;
    BST left;
    BST right;

    public BST(int value) {
      this.value = value;
      left = null;
      right = null;
    }
  }

  // Function to construct a minimum height BST from a sorted array
  public BST minHeightBST(int[] array) {
    return constructMinHeightBST(array, 0, array.length - 1);
  }

  // Helper function to recursively build the BST
  private BST constructMinHeightBST(int[] array, int start, int end) {
    if (start > end) {
      return null; // Base case: no elements to add
    }

    // Pick the middle element as the root
    int mid = (start + end) / 2;
    BST root = new BST(array[mid]);

    // Recursively construct the left and right subtrees
    root.left = constructMinHeightBST(array, start, mid - 1);
    root.right = constructMinHeightBST(array, mid + 1, end);

    return root;
  }

  // Function to print the BST in-order (for testing purposes)
  public void printInOrder(BST root) {
    if (root == null) {
      return;
    }
    printInOrder(root.left);
    System.out.print(root.value + " ");
    printInOrder(root.right);
  }

  // Main function to test the Min Height BST implementation
  public static void main(String[] args) {
    MinHeightBST bstBuilder = new MinHeightBST();

    // Example input array
    int[] array = {1, 2, 5, 7, 10, 13, 14, 15, 22};

    // Construct the minimal height BST
    BST root = bstBuilder.minHeightBST(array);

    // Print the in-order traversal of the BST (should match the sorted array)
    System.out.println("In-order traversal of the BST:");
    bstBuilder.printInOrder(root); // Output: 1 2 5 7 10 13 14 15 22
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We visit each element once to insert it into the tree.

   Space Complexity:
   - O(log n), for the recursion stack in the best case (balanced tree). In the worst case (unbalanced tree), the space complexity can be O(n).
  */
}
