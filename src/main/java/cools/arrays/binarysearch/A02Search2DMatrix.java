package cools.arrays.binarysearch;

/*
 Problem: Search a 2D Matrix

 You are given an m x n integer matrix matrix with the following two properties:
 1. Each row is sorted in non-decreasing order.
 2. The first integer of each row is greater than the last integer of the previous row.

 Given an integer target, return true if the target is in the matrix or false otherwise.

 You must write a solution with O(log(m * n)) time complexity.

 Example 1:
 Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 Output: true

 Example 2:
 Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 Output: false

 Constraints:
 - m == matrix.length
 - n == matrix[i].length
 - 1 <= m, n <= 100
 - -10^4 <= matrix[i][j], target <= 10^4

 Solution Approach:
 1. The matrix can be considered as a flattened array of size `m * n`.
 2. Apply binary search on this conceptual 1D array.
 3. Convert the middle index of the 1D array back to a 2D matrix index using `row = mid / n` and `col = mid % n`.
 4. Check if the value at `matrix[row][col]` is equal to the target. If it is, return true.
 5. Otherwise, adjust the search range according to whether the target is greater or smaller than the current element.
*/

public class A02Search2DMatrix {

  // Function to search a target value in a 2D matrix
  public boolean searchMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return false;
    }

    int m = matrix.length; // Number of rows
    int n = matrix[0].length; // Number of columns

    int left = 0;
    int right = m * n - 1; // Treat the matrix as a 1D array of size m * n

    // Perform binary search
    while (left <= right) {
      int mid = left + (right - left) / 2;
      // Convert the 1D index into 2D row and column
      int row = mid / n;
      int col = mid % n;

      // Check if the current element is the target
      if (matrix[row][col] == target) {
        return true;
      }
      // If target is smaller, search the left half
      else if (matrix[row][col] > target) {
        right = mid - 1;
      }
      // If target is larger, search the right half
      else {
        left = mid + 1;
      }
    }

    // If target is not found, return false
    return false;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02Search2DMatrix solution = new A02Search2DMatrix();

    // Example 1
    int[][] matrix1 = {
      {1, 3, 5, 7},
      {10, 11, 16, 20},
      {23, 30, 34, 60}
    };
    int target1 = 3;
    System.out.println("Result: " + solution.searchMatrix(matrix1, target1)); // Output: true

    // Example 2
    int[][] matrix2 = {
      {1, 3, 5, 7},
      {10, 11, 16, 20},
      {23, 30, 34, 60}
    };
    int target2 = 13;
    System.out.println("Result: " + solution.searchMatrix(matrix2, target2)); // Output: false
  }

  /*
   Time Complexity:
   - O(log(m * n)), where m is the number of rows and n is the number of columns.
     We are performing binary search on a flattened 1D array of size m * n.

   Space Complexity:
   - O(1), since we are using only a constant amount of extra space.
  */
}
