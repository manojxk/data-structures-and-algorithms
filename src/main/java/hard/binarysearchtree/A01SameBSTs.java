package hard.binarysearchtree;

/*
 Problem: Same BSTs

 Write a function that takes in two arrays of integers and determines whether these arrays represent the same Binary Search Tree (BST).

 A BST is defined by two conditions:
 1. The left child must be smaller than the node itself.
 2. The right child must be greater than or equal to the node.

 Arrays represent BSTs by listing nodes in a pre-order traversal order (root, left, right).
 Two arrays represent the same BST if they contain the same structure and values.

 Example 1:
 Input: arrayOne = [10, 15, 8, 12, 94, 81, 5], arrayTwo = [10, 8, 5, 15, 12, 94, 81]
 Output: true
 Explanation: Both arrays represent the same BST.

 Example 2:
 Input: arrayOne = [10, 15, 8, 12], arrayTwo = [10, 8, 15, 12]
 Output: false
 Explanation: The structure is different, so they don't represent the same BST.

 Constraints:
 - Both arrays will always contain at least one element.
 - Arrays contain distinct integers.

 Solution Approach:
 1. If the root of both arrays is not the same, they cannot represent the same BST.
 2. Recursively split the left and right subtrees for both arrays and check if the corresponding left and right subtrees are identical.
 3. Perform this recursively for each subtree.
*/

import java.util.*;

public class A01SameBSTs {

  // Function to check if two arrays represent the same BST
  public static boolean sameBsts(List<Integer> arrayOne, List<Integer> arrayTwo) {
    // Base case: If both arrays are empty, they represent the same BST
    if (arrayOne.isEmpty() && arrayTwo.isEmpty()) {
      return true;
    }

    // If the root values are different, they can't represent the same BST
    if (arrayOne.get(0).intValue() != arrayTwo.get(0).intValue()) {
      return false;
    }

    // If the arrays have different sizes, they can't represent the same BST
    if (arrayOne.size() != arrayTwo.size()) {
      return false;
    }

    // Recursively compare left and right subtrees
    List<Integer> leftSubtreeOne = getSmaller(arrayOne);
    List<Integer> rightSubtreeOne = getGreaterOrEqual(arrayOne);

    List<Integer> leftSubtreeTwo = getSmaller(arrayTwo);
    List<Integer> rightSubtreeTwo = getGreaterOrEqual(arrayTwo);

    return sameBsts(leftSubtreeOne, leftSubtreeTwo) && sameBsts(rightSubtreeOne, rightSubtreeTwo);
  }

  // Helper function to get all elements smaller than the first element (left subtree)
  private static List<Integer> getSmaller(List<Integer> array) {
    List<Integer> smaller = new ArrayList<>();
    for (int i = 1; i < array.size(); i++) {
      if (array.get(i) < array.get(0)) {
        smaller.add(array.get(i));
      }
    }
    return smaller;
  }

  // Helper function to get all elements greater than or equal to the first element (right subtree)
  private static List<Integer> getGreaterOrEqual(List<Integer> array) {
    List<Integer> greaterOrEqual = new ArrayList<>();
    for (int i = 1; i < array.size(); i++) {
      if (array.get(i) >= array.get(0)) {
        greaterOrEqual.add(array.get(i));
      }
    }
    return greaterOrEqual;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1: Same BSTs
    List<Integer> arrayOne1 = Arrays.asList(10, 15, 8, 12, 94, 81, 5);
    List<Integer> arrayTwo1 = Arrays.asList(10, 8, 5, 15, 12, 94, 81);
    System.out.println("Are the BSTs the same? " + sameBsts(arrayOne1, arrayTwo1)); // Output: true

    // Example 2: Different BSTs
    List<Integer> arrayOne2 = Arrays.asList(10, 15, 8, 12);
    List<Integer> arrayTwo2 = Arrays.asList(10, 8, 15, 12);
    System.out.println("Are the BSTs the same? " + sameBsts(arrayOne2, arrayTwo2)); // Output: false
  }

  /*
   Time Complexity:
   - O(n^2) in the worst case, where n is the number of elements in the array. For each recursive call, we partition the array and pass it to the next call.

   Space Complexity:
   - O(n^2) due to the recursive function calls and creation of left and right subtrees at each recursive step.
  */
}
