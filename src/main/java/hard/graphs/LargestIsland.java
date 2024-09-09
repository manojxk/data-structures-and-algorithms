package hard.graphs;

/**
 * Problem Statement:
 *
 * <p>You are given a two-dimensional matrix of potentially unequal height and width containing only
 * 0's and 1's. Each '1' represents water, and each '0' represents part of a land mass. A land mass
 * consists of any number of '0's that are either horizontally or vertically adjacent (but not
 * diagonally adjacent). The number of adjacent '0's forming a land mass determines its size.
 *
 * <p>The land mass can have complex shapes, like an L-shape. The task is to find the largest
 * possible land mass size after changing exactly one '1' in the matrix to '0'. You may mutate the
 * matrix to compute this, and you are guaranteed that the matrix contains at least one '1'.
 *
 * <p>Sample Input: matrix = [ [0, 1, 1], [0, 0, 1], [1, 1, 0] ]
 *
 * <p>Sample Output: 5 // Changing either matrix[1][2] or matrix[2][1] creates a land mass of size
 * 5.
 */
import java.util.*;

public class LargestIsland {

  // Brute Force Approach:
  // For every water cell ('1'), we try changing it to land ('0') and compute the largest land mass.
  // Time Complexity: O(n^3) - For each '1', we perform a DFS or BFS which can take O(n^2) in the
  // worst case.
  // Space Complexity: O(n^2) - For storing visited states and recursion stack or queue during
  // DFS/BFS.
  public static int largestLandMassBruteForce(int[][] matrix) {
    int maxLandMass = 0;
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Iterate over every cell
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (matrix[i][j] == 1) {
          // Change water to land temporarily
          matrix[i][j] = 0;
          maxLandMass = Math.max(maxLandMass, getLandMassSize(matrix));
          // Change it back to water
          matrix[i][j] = 1;
        }
      }
    }
    return maxLandMass;
  }

  // Helper function to calculate land mass size using DFS
  private static int getLandMassSize(int[][] matrix) {
    int maxLandMass = 0;
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == 0 && !visited[i][j]) {
          maxLandMass = Math.max(maxLandMass, dfs(matrix, visited, i, j));
        }
      }
    }
    return maxLandMass;
  }

  // DFS to explore the land mass
  private static int dfs(int[][] matrix, boolean[][] visited, int i, int j) {
    if (i < 0
        || i >= matrix.length
        || j < 0
        || j >= matrix[0].length
        || matrix[i][j] == 1
        || visited[i][j]) {
      return 0;
    }

    visited[i][j] = true;

    // Explore all 4 directions (no diagonals allowed)
    return 1
        + dfs(matrix, visited, i - 1, j)
        + dfs(matrix, visited, i + 1, j)
        + dfs(matrix, visited, i, j - 1)
        + dfs(matrix, visited, i, j + 1);
  }

  // Optimized Approach:
  // Step 1: Compute sizes of all existing land masses.
  // Step 2: For each water cell ('1'), check how many distinct land masses it can connect to.
  // Time Complexity: O(n^2) - We traverse the matrix multiple times, but each DFS is only performed
  // once.
  // Space Complexity: O(n^2) - For storing visited and land mass sizes.
  public static int largestLandMassOptimized(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] landMassSizes = new int[rows][cols];
    int landMassId = 2; // Start IDs from 2 (since 0 and 1 are taken for land/water)
    Map<Integer, Integer> landMassSizeMap = new HashMap<>();
    boolean[][] visited = new boolean[rows][cols];

    // Step 1: Find all land masses and store their sizes
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (matrix[i][j] == 0 && !visited[i][j]) {
          int size = dfsOptimized(matrix, visited, i, j, landMassId, landMassSizes);
          landMassSizeMap.put(landMassId, size);
          landMassId++;
        }
      }
    }

    // Step 2: Check each water cell ('1') and calculate potential max land mass size
    int maxLandMass = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (matrix[i][j] == 1) {
          Set<Integer> connectedLandMassIds = new HashSet<>();
          // Check all 4 possible directions (up, down, left, right)
          if (i > 0 && landMassSizes[i - 1][j] > 1)
            connectedLandMassIds.add(landMassSizes[i - 1][j]);
          if (i < rows - 1 && landMassSizes[i + 1][j] > 1)
            connectedLandMassIds.add(landMassSizes[i + 1][j]);
          if (j > 0 && landMassSizes[i][j - 1] > 1)
            connectedLandMassIds.add(landMassSizes[i][j - 1]);
          if (j < cols - 1 && landMassSizes[i][j + 1] > 1)
            connectedLandMassIds.add(landMassSizes[i][j + 1]);

          // Sum the sizes of the distinct land masses this '1' can connect to
          int newLandMassSize = 1; // Changing '1' to '0'
          for (int landId : connectedLandMassIds) {
            newLandMassSize += landMassSizeMap.get(landId);
          }

          maxLandMass = Math.max(maxLandMass, newLandMassSize);
        }
      }
    }

    return maxLandMass;
  }

  // DFS for optimized approach
  private static int dfsOptimized(
      int[][] matrix, boolean[][] visited, int i, int j, int landMassId, int[][] landMassSizes) {
    if (i < 0
        || i >= matrix.length
        || j < 0
        || j >= matrix[0].length
        || matrix[i][j] == 1
        || visited[i][j]) {
      return 0;
    }

    visited[i][j] = true;
    landMassSizes[i][j] = landMassId;

    // Explore all 4 directions (no diagonals allowed)
    return 1
        + dfsOptimized(matrix, visited, i - 1, j, landMassId, landMassSizes)
        + dfsOptimized(matrix, visited, i + 1, j, landMassId, landMassSizes)
        + dfsOptimized(matrix, visited, i, j - 1, landMassId, landMassSizes)
        + dfsOptimized(matrix, visited, i, j + 1, landMassId, landMassSizes);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int[][] matrix = {
      {0, 1, 1},
      {0, 0, 1},
      {1, 1, 0}
    };

    // Brute Force Solution
    int resultBruteForce = largestLandMassBruteForce(matrix);
    System.out.println("Brute Force Solution: " + resultBruteForce);

    // Optimized Solution
    int resultOptimized = largestLandMassOptimized(matrix);
    System.out.println("Optimized Solution: " + resultOptimized);
  }
}
