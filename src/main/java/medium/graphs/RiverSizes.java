/*Problem Description
You are given a 2D matrix containing 0s and 1s. Each 1 represents part of a river,
and a river consists of 1s that are horizontally or vertically adjacent. You need
to write a function that returns the sizes of all rivers in the matrix.*/
package medium.graphs;

import java.util.*;

public class RiverSizes {

  /**
   * Returns the sizes of all rivers in the given matrix.
   *
   * @param matrix The 2D matrix representing the land and rivers.
   * @return A list of integers representing the sizes of the rivers.
   */
  public static List<Integer> getRiverSizes(int[][] matrix) {
    List<Integer> riverSizes = new ArrayList<>();
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];

    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix[0].length; col++) {
        if (matrix[row][col] == 1 && !visited[row][col]) {
          int size = exploreRiver(matrix, visited, row, col);
          riverSizes.add(size);
        }
      }
    }

    return riverSizes;
  }

  /**
   * Performs a depth-first search to explore a river and calculate its size.
   *
   * @param matrix The 2D matrix representing the land and rivers.
   * @param visited The matrix tracking visited cells.
   * @param row The current row.
   * @param col The current column.
   * @return The size of the river.
   */
  private static int exploreRiver(int[][] matrix, boolean[][] visited, int row, int col) {
    int size = 0;
    Stack<int[]> stack = new Stack<>();
    stack.push(new int[] {row, col});

    while (!stack.isEmpty()) {
      int[] current = stack.pop();
      int r = current[0];
      int c = current[1];

      if (visited[r][c]) continue;

      visited[r][c] = true;
      size++;

      // Add unvisited neighboring cells
      if (r > 0 && matrix[r - 1][c] == 1 && !visited[r - 1][c]) stack.push(new int[] {r - 1, c});
      if (r < matrix.length - 1 && matrix[r + 1][c] == 1 && !visited[r + 1][c])
        stack.push(new int[] {r + 1, c});
      if (c > 0 && matrix[r][c - 1] == 1 && !visited[r][c - 1]) stack.push(new int[] {r, c - 1});
      if (c < matrix[0].length - 1 && matrix[r][c + 1] == 1 && !visited[r][c + 1])
        stack.push(new int[] {r, c + 1});
    }

    return size;
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 0, 0, 1, 0},
      {1, 0, 1, 0, 0},
      {0, 0, 1, 0, 1},
      {1, 0, 1, 0, 1},
      {1, 0, 1, 1, 0}
    };

    List<Integer> riverSizes = getRiverSizes(matrix);
    System.out.println(riverSizes); // Output: [1, 2, 2, 2, 5]
  }
}
/*
Approach
Initialization:

Use a boolean matrix to keep track of visited cells.
Use a depth-first search (DFS) or breadth-first search (BFS) approach to explore each river starting from an unvisited 1.
Traversal:

Iterate over each cell in the matrix.
If the cell contains a 1 and hasn't been visited, start a DFS or BFS to find the size of the river and mark all its cells as visited.
Collect Sizes:

Add the size of each discovered river to a list.
Continue until all cells in the matrix have been processed.*/

/*Explanation
Initialization:

riverSizes is a list to store the sizes of all rivers.
visited is a 2D boolean array to keep track of which cells have been visited.
DFS Traversal:

For each unvisited 1, initiate a DFS using a stack to explore all connected 1s.
Increment the size count for each connected 1 and mark cells as visited.
Add the size of the discovered river to riverSizes.
Time and Space Complexity
Time Complexity:
𝑂(𝑚⋅𝑛)


Each cell is processed once.
Space Complexity:
𝑂(𝑚⋅𝑛)

Space for the visited matrix and the stack used for DFS.
Pros and Cons
Pros:

Handles complex river shapes and connections efficiently.
Simple to implement using DFS.
        Cons:

Can be memory-intensive for very large matrices due to the stack and visited matrix.*/
