/*Problem Description
You are given a 2D matrix containing 0s and 1s. Each 1 represents part of a river,
and a river consists of 1s that are horizontally or vertically adjacent. You need
to write a function that returns the sizes of all rivers in the matrix.*/
package medium.graphs;

import java.util.*;

import java.util.ArrayList;
import java.util.List;

public class RiverSizes {

  // Main function to find all river sizes
  public static List<Integer> riverSizes(int[][] matrix) {
    List<Integer> sizes = new ArrayList<>();
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];

    // Traverse through the matrix
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (!visited[i][j] && matrix[i][j] == 1) {
          // If it's a new part of a river, calculate its size
          int size = calculateRiverSize(matrix, visited, i, j);
          sizes.add(size);
        }
      }
    }
    return sizes;
  }

  // Recursive function to calculate the size of a river
  private static int calculateRiverSize(int[][] matrix, boolean[][] visited, int i, int j) {
    // Base case: if out of bounds or already visited or not part of a river
    if (i < 0
        || i >= matrix.length
        || j < 0
        || j >= matrix[0].length
        || visited[i][j]
        || matrix[i][j] == 0) {
      return 0;
    }

    // Mark the current cell as visited
    visited[i][j] = true;

    // Start counting the size from this cell
    int size = 1;

    // Recursively explore all four possible directions (up, down, left, right)
    size += calculateRiverSize(matrix, visited, i - 1, j); // up
    size += calculateRiverSize(matrix, visited, i + 1, j); // down
    size += calculateRiverSize(matrix, visited, i, j - 1); // left
    size += calculateRiverSize(matrix, visited, i, j + 1); // right

    return size;
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 0, 0, 1, 0},
      {1, 0, 1, 0, 0},
      {0, 0, 1, 0, 1},
      {1, 0, 1, 1, 1}
    };
    List<Integer> sizes = riverSizes(matrix);
    System.out.println(sizes); // Output: [2, 1, 5]
  }
}
/*
Time Complexity:
O(n*m): We visit every cell in the matrix at most once, where n is the number of rows and m is the number of columns.
Space Complexity:
O(n*m): In the worst case, the recursion stack could go as deep as the number of cells in the matrix.*/
