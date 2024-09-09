package cools.graphs;

/*
 Problem: Number of Islands

 Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
 You may assume all four edges of the grid are all surrounded by water.

 Example 1:

 Input:
 grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
 ]
 Output: 1

 Example 2:

 Input:
 grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
 ]
 Output: 3

 Constraints:
 - m == grid.length
 - n == grid[i].length
 - 1 <= m, n <= 300
 - grid[i][j] is '0' or '1'.
*/

/*
 Solution Steps:

 1. Iterate over every cell in the grid.
 2. When a '1' (land) is encountered, trigger a depth-first search (DFS) or breadth-first search (BFS) to mark the entire island as visited.
 3. During the DFS/BFS, convert all connected '1's to '0' (or mark them as visited).
 4. Increment the island count for each DFS/BFS initiation (i.e., for each connected component of '1's).
 5. Continue traversing the grid to find other islands and count them.
*/

public class A01NumberOfIslands {

  // Directions array to simplify moving up, down, left, and right
  private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

  // Function to calculate the number of islands
  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;

    int numIslands = 0;
    int rows = grid.length;
    int cols = grid[0].length;

    // Iterate over each cell in the grid
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        // If the current cell is '1' (land), we found a new island
        if (grid[i][j] == '1') {
          numIslands++; // Increment the island count
          dfs(grid, i, j); // Mark all connected lands as visited
        }
      }
    }
    return numIslands; // Return the total count of islands
  }

  // Depth-first search (DFS) to mark all connected lands ('1's) as visited
  private void dfs(char[][] grid, int row, int col) {
    int rows = grid.length;
    int cols = grid[0].length;

    // Base case: If out of bounds or not land, return
    if (row < 0 || row >= rows || col < 0 || col >= cols || grid[row][col] == '0') {
      return;
    }

    // Mark the current cell as visited by setting it to '0' (water)
    grid[row][col] = '0';

    // Visit all adjacent cells (up, down, left, right)
    for (int[] direction : DIRECTIONS) {
      dfs(grid, row + direction[0], col + direction[1]);
    }
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A01NumberOfIslands solution = new A01NumberOfIslands();

    // Test case 1
    char[][] grid1 = {
      {'1', '1', '1', '1', '0'},
      {'1', '1', '0', '1', '0'},
      {'1', '1', '0', '0', '0'},
      {'0', '0', '0', '0', '0'}
    };
    System.out.println("Number of Islands: " + solution.numIslands(grid1)); // Output: 1

    // Test case 2
    char[][] grid2 = {
      {'1', '1', '0', '0', '0'},
      {'1', '1', '0', '0', '0'},
      {'0', '0', '1', '0', '0'},
      {'0', '0', '0', '1', '1'}
    };
    System.out.println("Number of Islands: " + solution.numIslands(grid2)); // Output: 3
  }

  /*
   Time Complexity:
   - O(m * n), where m is the number of rows and n is the number of columns in the grid. We visit each cell once.

   Space Complexity:
   - O(m * n), in the worst case, the DFS recursion stack can go as deep as the number of cells in the grid.
  */
}
