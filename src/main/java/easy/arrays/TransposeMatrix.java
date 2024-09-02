/*
 * Problem Statement:
 *
 * You're given a 2D array of integers `matrix`. Write a function that returns
 * the transpose of the matrix.
 *
 * The transpose of a matrix is a flipped version of the original matrix across
 * its main diagonal (which runs from top-left to bottom-right); it switches
 * the row and column indices of the original matrix.
 *
 * You can assume the input matrix always has at least one value; however, its
 * width and height are not necessarily the same.
 *
 * Example 1:
 * Input: matrix = [[1, 2]]
 * Output: [[1], [2]]
 *
 * Example 2:
 * Input: matrix = [[1, 2], [3, 4], [5, 6]]
 * Output: [[1, 3, 5], [2, 4, 6]]
 */

package easy.arrays;

public class TransposeMatrix {

  // Brute Force Solution to Transpose a Matrix
  public static int[][] transposeMatrix(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Create a new matrix with swapped rows and columns
    int[][] transposedMatrix = new int[cols][rows];
    // Iterate over the original matrix and transpose the elements
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        transposedMatrix[j][i] = matrix[i][j];
      }
    }
    return transposedMatrix;
  }

  // In-Place Transpose for Square Matrices
  public static void transposeSquareMatrix(int[][] matrix) {
    int n = matrix.length;

    // Swap elements across the diagonal
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        // Swap element at (i, j) with element at (j, i)
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }
  }

  public static void main(String[] args) {
    int[][] matrix1 = {{1, 2}, {3, 4}};

    // Transpose of matrix1
    int[][] result1 = transposeMatrix(matrix1);
    System.out.println("Transpose of matrix1:");
    for (int[] row : result1) {
      for (int value : row) {
        System.out.print(value + " ");
      }
      System.out.println();
    }
  }
}
/*
Time Complexity: O(n * m)
Space Complexity: O(n * m)*/
