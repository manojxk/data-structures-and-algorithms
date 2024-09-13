package hard.dp;

/*
 Problem: Maximum Sum Submatrix

 You are given a 2D matrix of integers and a positive integer size.
 Write a function that returns the maximum sum of any submatrix within the input matrix with dimensions size x size.

 Example 1:
 Input:
    matrix = [
      [5, 3, -1, 5],
      [-7, 3, 7, 8],
      [12, 8, 0, 10],
      [1, -8, -8, 2]
    ]
    size = 2
 Output:
    18
 Explanation:
    The submatrix with the maximum sum is:
    [
      [3, 7],
      [8, 0]
    ]
    The sum is 18.

 Constraints:
 - The input matrix can have up to 1000 elements.
 - The submatrix size is a positive integer smaller than both the number of rows and columns of the input matrix.
*/

public class A07MaxSumSubmatrix {

  // Brute Force Approach:
  // Time Complexity: O(n * m * size^2), where n is the number of rows, m is the number of columns,
  // and size is the dimension of the submatrix. We check every possible submatrix of size x size.
  // Space Complexity: O(1), since we only use a few variables to store the sum.
  public static int maxSumSubmatrixBruteForce(int[][] matrix, int size) {
    int maxSum = Integer.MIN_VALUE;
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Iterate over all possible top-left corners of the submatrices
    for (int i = 0; i <= rows - size; i++) {
      for (int j = 0; j <= cols - size; j++) {
        int currentSum = 0;

        // Calculate the sum of the current submatrix of size `size x size`
        for (int x = i; x < i + size; x++) {
          for (int y = j; y < j + size; y++) {
            currentSum += matrix[x][y];
          }
        }

        // Update the maximum sum
        maxSum = Math.max(maxSum, currentSum);
      }
    }

    return maxSum;
  }

  // Optimized Approach:
  // Use a sliding window approach by precomputing sums for each row.
  // Time Complexity: O(n * m), where n is the number of rows and m is the number of columns.
  // Space Complexity: O(n * m), for storing the cumulative sums.
  public static int maxSumSubmatrixOptimized(int[][] matrix, int size) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] prefixSums = new int[rows + 1][cols + 1];

    // Calculate the prefix sums for the matrix
    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        prefixSums[i][j] =
            matrix[i - 1][j - 1]
                + prefixSums[i - 1][j]
                + prefixSums[i][j - 1]
                - prefixSums[i - 1][j - 1];
      }
    }

    int maxSum = Integer.MIN_VALUE;

    // Iterate over all possible top-left corners of the submatrices
    for (int i = size; i <= rows; i++) {
      for (int j = size; j <= cols; j++) {
        // Calculate the sum of the submatrix using prefix sums
        int currentSum =
            prefixSums[i][j]
                - prefixSums[i - size][j]
                - prefixSums[i][j - size]
                + prefixSums[i - size][j - size];

        // Update the maximum sum
        maxSum = Math.max(maxSum, currentSum);
      }
    }

    return maxSum;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample Input
    int[][] matrix = {
      {5, 3, -1, 5},
      {-7, 3, 7, 4},
      {12, 8, 0, 0},
      {1, -8, -8, 2}
    };
    int size = 2;

    // Brute Force Solution
    System.out.println("Brute Force Solution: " + maxSumSubmatrixBruteForce(matrix, size));

    // Optimized Solution
    System.out.println("Optimized Solution: " + maxSumSubmatrixOptimized(matrix, size));
  }
}

/*
Brute Force Approach:

Time Complexity: O(n * m * size²), where n is the number of rows, m is the number of columns, and size is the submatrix size. For every possible submatrix starting point, we compute the sum of its elements, which requires iterating over a size x size submatrix.
Space Complexity: O(1), since we are not using any extra space except for variables to store the maximum sum.

Optimized Approach (Prefix Sum):

Time Complexity: O(n * m), where n is the number of rows and m is the number of columns. We use prefix sums to compute the sum of any submatrix in constant time.
Space Complexity: O(n * m), for storing the cumulative sums of the matrix.
*/
