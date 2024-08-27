/*
 * Problem Statement:
 *
 * Write a function that takes in an n x m two-dimensional array (that can be
 * square-shaped when n == m) and returns a one-dimensional array of all the
 * array's elements in spiral order.
 *
 * Spiral order starts at the top left corner of the two-dimensional array, goes
 * to the right, and proceeds in a spiral pattern all the way until every element
 * has been visited.
 *
 * Sample Input:
 * array = [
 *   [1,   2,  3, 4],
 *   [12, 13, 14, 5],
 *   [11, 16, 15, 6],
 *   [10,  9,  8, 7],
 * ]
 *
 * Sample Output:
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
 */

package medium;

import java.util.*;

public class SpiralOrderTraversal {

  public static List<Integer> spiralOrder(int[][] array) {
    List<Integer> result = new ArrayList<>();

    if (array.length == 0) {
      return result;
    }

    int top = 0;
    int bottom = array.length - 1;
    int left = 0;
    int right = array[0].length - 1;

    while (top <= bottom && left <= right) {
      // Traverse from left to right across the top boundary
      for (int i = left; i <= right; i++) {
        result.add(array[top][i]);
      }
      top++;

      // Traverse from top to bottom down the right boundary
      for (int i = top; i <= bottom; i++) {
        result.add(array[i][right]);
      }
      right--;

      // Traverse from right to left across the bottom boundary
      if (top <= bottom) {
        for (int i = right; i >= left; i--) {
          result.add(array[bottom][i]);
        }
        bottom--;
      }

      // Traverse from bottom to top up the left boundary
      if (left <= right) {
        for (int i = bottom; i >= top; i--) {
          result.add(array[i][left]);
        }
        left++;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Sample Input
    int[][] array = {
      {1, 2, 3, 4},
      {12, 13, 14, 5},
      {11, 16, 15, 6},
      {10, 9, 8, 7}
    };

    // Getting spiral order
    List<Integer> result = spiralOrder(array);

    // Print the result
    System.out.println(
        result); // Expected Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
  }
}


/*Time Complexity:
O(n * m): Every element is visited exactly once, where
𝑛
n and
𝑚
m are the dimensions of the array.
Space Complexity:
O(1): The solution operates in place with respect to the input array, but the output array does take space proportional to the number of elements.*/
