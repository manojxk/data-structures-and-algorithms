package easy.arrays;

/*
 Problem: Transpose Matrix

 The transpose of a matrix is obtained by flipping the matrix over its diagonal, switching the row and column indices of the matrix.

 Given a 2D matrix (a list of lists), return the transpose of the matrix.

 Example:

 Input:
 [
   [1, 2, 3],
   [4, 5, 6],
   [7, 8, 9]
 ]

 Output:
 [
   [1, 4, 7],
   [2, 5, 8],
   [3, 6, 9]
 ]

 Explanation:
 The transpose of the matrix is obtained by converting the rows of the original matrix into columns of the new matrix.
*/

/*
 Solution Steps:

 1. Initialize a new matrix with the dimensions swapped (i.e., if the input matrix has dimensions m x n,
    the new matrix will have dimensions n x m).
 2. Iterate over the original matrix and assign each element to its corresponding position in the new matrix.
 3. Return the new transposed matrix.
*/

public class TransposeMatrix {

  // Function to transpose a matrix
  public static int[][] transpose(int[][] matrix) {
    int rows = matrix.length; // Number of rows in the original matrix
    int cols = matrix[0].length; // Number of columns in the original matrix

    // Initialize a new matrix with swapped dimensions
    int[][] transposedMatrix = new int[cols][rows];

    // Step 1: Iterate over the matrix and assign elements to their transposed positions
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        transposedMatrix[col][row] = matrix[row][col];
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

  // Main function to run and test the solution
  public static void main(String[] args) {
    // Example matrix
    int[][] matrix = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };

    // Transpose the matrix
    int[][] result = transpose(matrix);

    // Print the transposed matrix
    System.out.println("Transposed Matrix:");
    for (int[] row : result) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }
  }

  /*
   Time Complexity:
   - O(m * n), where m is the number of rows and n is the number of columns in the original matrix.
     We iterate through all elements of the matrix once.

   Space Complexity:
   - O(m * n), where m is the number of rows and n is the number of columns in the original matrix.
     This is the space required for the new transposed matrix.
  */
}
