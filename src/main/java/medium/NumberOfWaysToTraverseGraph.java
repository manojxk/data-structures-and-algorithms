/*
 * Problem Statement:
 * Given the width and height of a grid-shaped rectangular graph, write a function that returns
 * the number of ways to reach the bottom-right corner of the grid from the top-left corner.
 * Each move must either go down or right. You cannot move up or left.
 *
 * Example:
 *
 * For a grid with width = 4 and height = 3:
 *
 * There are 10 ways to reach the bottom-right corner.
 *
 * Sample Input:
 * width = 4
 * height = 3
 *
 * Sample Output:
 * 10
 */

package medium;

import java.util.*;

public class NumberOfWaysToTraverseGraph { // O(2^(n + m)) time | O(n + m) space - where n
  // is the width of the graph and m is the height
  public int numberOfWaysToTraverseGraph(int width, int height) {
    if (width == 1 || height == 1) {
      return 1;
    }

    return numberOfWaysToTraverseGraph(width - 1, height)
        + numberOfWaysToTraverseGraph(width, height - 1);
  }

  // O(n * m) time | O(n * m) space - where n
  // is the width of the graph and m is the height
  public int numberOfWaysToTraverseGraph2(int width, int height) {
    int[][] numberOfWays = new int[height + 1][width + 1];

    for (int widthIdx = 1; widthIdx < width + 1; widthIdx++) {
      for (int heightIdx = 1; heightIdx < height + 1; heightIdx++) {
        if (widthIdx == 1 || heightIdx == 1) {
          numberOfWays[heightIdx][widthIdx] = 1;
        } else {
          int waysLeft = numberOfWays[heightIdx][widthIdx - 1];
          int waysUp = numberOfWays[heightIdx - 1][widthIdx];
          numberOfWays[heightIdx][widthIdx] = waysLeft + waysUp;
        }
      }
    }

    return numberOfWays[height][width];
  }

  // O(n + m) time | O(1) space - where n is
  // the width of the graph and m is the height
  public int numberOfWaysToTraverseGraph3(int width, int height) {
    int xDistanceToCorner = width - 1;
    int yDistanceToCorner = height - 1;

    // The number of permutations of right and down movements
    // is the number of ways to reach the bottom right corner.
    int numerator = factorial(xDistanceToCorner + yDistanceToCorner);
    int denominator = factorial(xDistanceToCorner) * factorial(yDistanceToCorner);
    return numerator / denominator;
  }

  public int factorial(int num) {
    int result = 1;

    for (int n = 2; n < num + 1; n++) {
      result *= n;
    }

    return result;
  }
}
