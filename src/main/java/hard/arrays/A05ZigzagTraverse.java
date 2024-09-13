package hard.arrays;

/*
 Problem: Zigzag Traverse

 Write a function that takes in a 2D array (matrix) of integers and returns a one-dimensional array of all the array's elements in zigzag order.

 Zigzag order starts at the top-left corner of the matrix, goes down by one element, and proceeds in a zigzag pattern all the way to the bottom-right corner.

 Example 1:
 Input:
 matrix = [
   [1, 3, 4, 10],
   [2, 5, 9, 11],
   [6, 8, 12, 15],
   [7, 13, 14, 16]
 ]
 Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]

 Constraints:
 - The input matrix will have at least 1 element.
 - The number of rows and columns will be at most 100.

 Solution Approach:
 1. Start at the top-left corner of the matrix.
 2. Use direction flags to control whether you're moving "down-right" or "up-left".
 3. Handle boundary conditions when the traversal reaches the edges of the matrix.
*/

import java.util.*;

public class A05ZigzagTraverse {

  // Function to return the zigzag traversal of the matrix
  public static List<Integer> zigzagTraverse(int[][] matrix) {
    List<Integer> result = new ArrayList<>();

    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return result;
    }

    int numRows = matrix.length;
    int numCols = matrix[0].length;
    int row = 0, col = 0;
    boolean goingDown = true; // Initially, we are moving down-right

    // Traverse until we cover all elements in the matrix
    while (row < numRows && col < numCols) {
      result.add(matrix[row][col]);

      // Check if we are moving "down-right"
      if (goingDown) {
        // Handle the boundaries when moving down-right
        if (col == 0 || row == numRows - 1) {
          goingDown = false; // Change direction to up-left
          if (row == numRows - 1) {
            col++; // If we hit the bottom row, move right
          } else {
            row++; // Otherwise, move down
          }
        } else {
          row++;
          col--;
        }
      }
      // Check if we are moving "up-left"
      else {
        // Handle the boundaries when moving up-left
        if (row == 0 || col == numCols - 1) {
          goingDown = true; // Change direction to down-right
          if (col == numCols - 1) {
            row++; // If we hit the last column, move down
          } else {
            col++; // Otherwise, move right
          }
        } else {
          row--;
          col++;
        }
      }
    }

    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    // Example matrix 1
    int[][] matrix1 = {
      {1, 3, 4, 10},
      {2, 5, 9, 11},
      {6, 8, 12, 15},
      {7, 13, 14, 16}
    };
    System.out.println("Zigzag Traverse Output for matrix1: " + zigzagTraverse(matrix1));
    // Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]

    // Example matrix 2
    int[][] matrix2 = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };
    System.out.println("Zigzag Traverse Output for matrix2: " + zigzagTraverse(matrix2));
    // Output: [1, 4, 2, 3, 5, 7, 8, 6, 9]
  }

  /*
   Time Complexity:
   - O(n * m), where n is the number of rows and m is the number of columns. We visit each element once.

   Space Complexity:
   - O(n * m), where n is the number of rows and m is the number of columns. We store the elements in the output list.
  */
}
