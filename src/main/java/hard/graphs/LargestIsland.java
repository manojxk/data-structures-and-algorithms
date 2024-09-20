package hard.graphs;

/*
 * Problem Statement:
 *
 * You are given an `n x n` binary matrix `grid`. Each cell is either 0 (representing water) or 1 (representing land).
 * An island is a group of 1's connected 4-directionally (horizontal or vertical). You may change at most one 0 to 1,
 * and you need to return the size of the largest island in the grid after doing so.
 *
 * If no land exists after flipping a 0 to 1, return 0.
 *
 * Example:
 *
 * Input:
 * grid = [
 *   [1, 0],
 *   [0, 1]
 * ]
 *
 * Output: 3
 *
 * Explanation:
 * By flipping a 0 in the grid to 1, we can connect the two 1s to form a single island of size 3.
 */

/*
 * Solution Approach:
 *
 * 1. Use Depth First Search (DFS) to calculate the size of each island and assign a unique index to each island.
 * 2. Create a mapping from island index to its size.
 * 3. For every water cell (0), check its neighbors (adjacent land cells), and calculate the potential size of the island if that water cell is flipped to land.
 * 4. Return the largest possible island size.
 */

import java.util.*;

public class LargestIsland {

  // Helper function to perform DFS and calculate island size
  public static int dfs(int[][] grid, int i, int j, int islandIndex) {
    // Base case: out of bounds or water cell (0)
    if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != 1) {
      return 0;
    }

    // Mark the cell with the island index
    grid[i][j] = islandIndex;

    // Calculate the size of the island by exploring in all 4 directions
    int size = 1; // Count current land cell
    size += dfs(grid, i + 1, j, islandIndex); // Down
    size += dfs(grid, i - 1, j, islandIndex); // Up
    size += dfs(grid, i, j + 1, islandIndex); // Right
    size += dfs(grid, i, j - 1, islandIndex); // Left

    return size;
  }

  // Function to find the largest island size
  public static int largestIsland(int[][] grid) {
    int n = grid.length;
    Map<Integer, Integer> islandSizeMap = new HashMap<>();
    int islandIndex = 2; // Start island index from 2 (since 1 is land and 0 is water)
    int maxIslandSize = 0;

    // Step 1: Find all islands and calculate their sizes
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 1) {
          // Calculate the size of the island and assign an index to it
          int size = dfs(grid, i, j, islandIndex);
          islandSizeMap.put(islandIndex, size);
          maxIslandSize = Math.max(maxIslandSize, size);
          islandIndex++;
        }
      }
    }

    // Step 2: Check each water cell (0) and calculate the potential size of the island if we flip
    // it to 1
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] == 0) {
          Set<Integer> adjacentIslands = new HashSet<>();
          int potentialSize = 1; // We are flipping one water cell to land

          // Check all 4 directions
          if (i > 0 && grid[i - 1][j] > 1) adjacentIslands.add(grid[i - 1][j]);
          if (i < n - 1 && grid[i + 1][j] > 1) adjacentIslands.add(grid[i + 1][j]);
          if (j > 0 && grid[i][j - 1] > 1) adjacentIslands.add(grid[i][j - 1]);
          if (j < n - 1 && grid[i][j + 1] > 1) adjacentIslands.add(grid[i][j + 1]);

          // Add the sizes of all adjacent islands
          for (int island : adjacentIslands) {
            potentialSize += islandSizeMap.get(island);
          }

          // Update the maximum island size if the potential size is larger
          maxIslandSize = Math.max(maxIslandSize, potentialSize);
        }
      }
    }

    return maxIslandSize;
  }

  // Main function to test the largestIsland implementation
  public static void main(String[] args) {
    int[][] grid = {{0, 1, 1},
                    {0, 0, 1},
                    {1, 1, 0}};

    // Output: 3
    System.out.println("Largest Island Size: " + largestIsland(grid));
  }

  /*
   * Time Complexity:
   * O(n^2), where n is the size of the grid. We perform DFS on each land cell once and then check each water cell.
   *
   * Space Complexity:
   * O(n^2), for storing the grid, the island size map, and the recursion stack during DFS.
   */
}
