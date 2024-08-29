package medium;

/**
 * Problem Statement:
 *
 * <p>You're given a two-dimensional array (a matrix) of potentially unequal height and width
 * containing only integers. Your goal is to convert all negative integers in the matrix to positive
 * integers. A negative integer can only be converted to a positive integer if one or more of its
 * adjacent elements (left, right, above, or below) is positive. Converting a negative to a positive
 * simply involves multiplying it by -1.
 *
 * <p>A single pass through the matrix involves converting all the negative integers that can be
 * converted at that particular point in time. You need to determine the minimum number of passes
 * required to convert all the negative integers in the matrix to positives. If it is not possible
 * to convert all the negative integers to positives, return -1.
 *
 * <p>Note that the value 0 is neither positive nor negative, so it can't convert an adjacent
 * negative to a positive.
 *
 * <p>Example:
 *
 * <p>Input: matrix = [ [0, -1, -3, 2, 0], [1, -2, -5, -1, -3], [3, 0, 0, -4, -1], ]
 *
 * <p>After 1st pass: [ [0, 1, -3, 2, 0], [1, 2, 5, 1, -3], [3, 0, 0, 4, -1], ]
 *
 * <p>After 2nd pass: [ [0, 1, 3, 2, 0], [1, 2, 5, 1, 3], [3, 0, 0, 4, 1], ]
 *
 * <p>After 3rd pass: [ [0, 1, 3, 2, 0], [1, 2, 5, 1, 3], [3, 0, 0, 4, 1], ]
 *
 * <p>Output: Minimum passes required: 3
 *
 * <p>If the matrix contains negatives that cannot be converted, return -1.
 */

/*Initialization:

Identify all the initial positive integers in the matrix. These positive integers are the starting points for our BFS, as they can convert adjacent negative integers to positives.
Use a queue to perform BFS, starting with all the positions of the initial positive integers.
BFS Traversal:

In each BFS level (or pass), process all the elements in the queue. For each positive integer, check its adjacent cells (left, right, up, down).
If an adjacent cell contains a negative integer, convert it to positive (multiply by -1) and add its position to the queue for the next pass.
Keep track of the number of passes required to convert all negatives.
Check Completion:

After the BFS completes, check if there are still any negative integers in the matrix. If there are, it means not all negatives could be converted, so return -1.
Otherwise, return the number of passes required.
Time and Space Complexity
Time Complexity:

O(N×M), where
𝑁
N is the number of rows and
𝑀
M is the number of columns. Each cell is processed at most once.
Space Complexity:

O(N×M) for storing the queue and keeping track of visited cells.*/

/*
Explanation
        Initialization:

        We first add all positive numbers to the queue and count the number of negative numbers.
        BFS Traversal:

        For each positive number in the queue, we explore its adjacent cells. If an adjacent cell contains a negative number, we convert it to positive and add its position to the queue for further exploration.
        Check Completion:

        After processing all cells, we check if there are any remaining negative numbers. If there are, it means not all negatives could be converted, and we return -1. Otherwise, we return the total number of passes.*/
import java.util.*;

public class MinimumPassesMatrix {

  public static int minimumPasses(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int negativeCount = 0;

    // Step 1: Initialize the queue with all positive elements
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (matrix[i][j] > 0) {
          queue.offer(new int[] {i, j});
        } else if (matrix[i][j] < 0) {
          negativeCount++;
        }
      }
    }

    // Early exit if there are no negatives to convert
    if (negativeCount == 0) return 0;

    int passes = 0;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    // Step 2: Perform BFS to convert negatives to positives
    while (!queue.isEmpty()) {
      int size = queue.size();
      boolean converted = false;

      for (int i = 0; i < size; i++) {
        int[] current = queue.poll();
        int row = current[0];
        int col = current[1];

        for (int[] direction : directions) {
          int newRow = row + direction[0];
          int newCol = col + direction[1];

          if (newRow >= 0
              && newRow < rows
              && newCol >= 0
              && newCol < cols
              && matrix[newRow][newCol] < 0) {
            matrix[newRow][newCol] *= -1;
            queue.offer(new int[] {newRow, newCol});
            negativeCount--;
            converted = true;
          }
        }
      }

      if (converted) {
        passes++;
      }
    }

    // Step 3: Check if all negatives have been converted
    return negativeCount == 0 ? passes : -1;
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {0, -1, -3, 2, 0},
      {1, -2, -5, -1, -3},
      {3, 0, 0, -4, -1},
    };

    int result = minimumPasses(matrix);
    System.out.println("Minimum passes required: " + result); // Output: 3
  }
}
