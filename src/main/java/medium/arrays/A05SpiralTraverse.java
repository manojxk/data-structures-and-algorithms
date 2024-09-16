package medium.arrays;

/*
 Problem: Spiral Traverse

 Given a 2D array (matrix), return all the elements of the array in spiral order.

 Example:

 Input:
 [
   [1, 2, 3],
   [4, 5, 6],
   [7, 8, 9]
 ]

 Output: [1, 2, 3, 6, 9, 8, 7, 4, 5]

 Explanation:
 The matrix is traversed in a spiral order starting from the top-left corner and moving right, then down, then left, and then up, repeating this process until all elements are visited.
*/

/*
 Solution Steps:

 1. Define boundaries: top, bottom, left, and right to keep track of the matrix's current boundary.
 2. Start the traversal from the top-left corner and move:
    a) From left to right along the top boundary.
    b) From top to bottom along the right boundary.
    c) From right to left along the bottom boundary.
    d) From bottom to top along the left boundary.
 3. After completing each direction, move the boundary inward and repeat until all elements are visited.
*/

import java.util.ArrayList;
import java.util.List;

public class A05SpiralTraverse {

  // Function to perform spiral traversal of a matrix
  public static List<Integer> spiralTraverse(int[][] matrix) {
    List<Integer> result = new ArrayList<>();

    if (matrix.length == 0) {
      return result; // If the matrix is empty, return an empty list
    }

    int top = 0; // Top boundary
    int bottom = matrix.length - 1; // Bottom boundary
    int left = 0; // Left boundary
    int right = matrix[0].length - 1; // Right boundary

    // Step 1: Traverse the matrix in spiral order
    while (top <= bottom && left <= right) {
      // Traverse from left to right along the top boundary
      for (int i = left; i <= right; i++) {
        result.add(matrix[top][i]);
      }
      top++; // Move the top boundary down

      // Traverse from top to bottom along the right boundary
      for (int i = top; i <= bottom; i++) {
        result.add(matrix[i][right]);
      }
      right--; // Move the right boundary left

      // Traverse from right to left along the bottom boundary (only if within bounds)
      if (top <= bottom) {
        for (int i = right; i >= left; i--) {
          result.add(matrix[bottom][i]);
        }
        bottom--; // Move the bottom boundary up
      }

      // Traverse from bottom to top along the left boundary (only if within bounds)
      if (left <= right) {
        for (int i = bottom; i >= top; i--) {
          result.add(matrix[i][left]);
        }
        left++; // Move the left boundary right
      }
    }

    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    // Example matrix
    int[][] matrix = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };

    // Get the spiral order traversal
    List<Integer> result = spiralTraverse(matrix);

    // Print the result
    System.out.println("Spiral Order: " + result);
    // Output: [1, 2, 3, 6, 9, 8, 7, 4, 5]
  }

  /*
   Time Complexity:
   - O(n), where n is the total number of elements in the matrix. Each element is visited once.

   Space Complexity:
   - O(n), where n is the total number of elements in the matrix. This is the space used to store the result.
  */
}
