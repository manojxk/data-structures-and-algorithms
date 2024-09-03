/*
 * Problem Statement:
 * You're given a two-dimensional array (a matrix) of distinct integers and a target integer.
 * Each row in the matrix is sorted, and each column is also sorted. The matrix doesn't
 * necessarily have the same height and width.
 *
 * Write a function that returns an array of the row and column indices of the target integer
 * if it's contained in the matrix; otherwise, return [-1, -1].
 *
 * Example:
 *
 * Sample Input:
 * matrix = [
 *   [1, 4, 7, 12, 15, 1000],
 *   [2, 5, 19, 31, 32, 1001],
 *   [3, 8, 24, 33, 35, 1002],
 *   [40, 41, 42, 44, 45, 1003],
 *   [99, 100, 103, 106, 128, 1004],
 * ]
 * target = 44
 *
 * Sample Output:
 * [3, 3]
 */
/*Approach:
Start from the Top-Right Corner: Start from the top-right corner of the matrix.
Comparison Logic:
If the current element is equal to the target, return the indices.
If the current element is greater than the target, move left (decrement the column).
If the current element is less than the target, move down (increment the row).
Handle Target Not Found: If you move out of bounds, return [-1, -1].
Time Complexity:
O(m + n): At most, you traverse one full row and one full column.
Space Complexity:
O(1): Only a constant amount of extra space is used.*/

package medium.searching;

public class SearchInMatrix {

  // Optimized Solution
  public static int[] searchMatrix(int[][] matrix, int target) {
    int row = 0;
    int col = matrix[0].length - 1;

    while (row < matrix.length && col >= 0) {
      if (matrix[row][col] == target) {
        return new int[] {row, col}; // Target found
      } else if (matrix[row][col] > target) {
        col--; // Move left
      } else {
        row++; // Move down
      }
    }

    return new int[] {-1, -1}; // Target not found
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 4, 7, 12, 15, 1000},
      {2, 5, 19, 31, 32, 1001},
      {3, 8, 24, 33, 35, 1002},
      {40, 41, 42, 44, 45, 1003},
      {99, 100, 103, 106, 128, 1004}
    };
    int target = 44;
    int[] result = searchMatrix(matrix, target);
    System.out.println(java.util.Arrays.toString(result)); // Output: [3, 3]
  }
}
