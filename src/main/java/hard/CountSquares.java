/*Problem Statement
Write a function that takes in a list of Cartesian coordinates (i.e., (x, y) coordinates) and
returns the number of squares that can be formed by these coordinates. A square must have its
four corners among the coordinates in order to be counted. A single coordinate can be used as a
corner for multiple different squares.

You can assume that no coordinate will be farther than 100 units from the origin.*/

/*Approach:
Generate All Combinations:
Check each combination of four points to determine if they form a square.
Distance Calculation:
Calculate distances between each pair of the four points.
Verify that the four sides are of equal length and the two diagonals are equal and longer than the sides.
Time Complexity:
O(n^4): We check all combinations of four points out of n points.
O(1): Checking if four points form a square involves a constant amount of operations.
Space Complexity:
O(1): Only a constant amount of extra space is used for storing distances and indices.*/

package hard;

import java.util.*;

public class CountSquares {
  // Method to calculate squared distance between two points
  private static int distanceSquared(int[] p1, int[] p2) {
    return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
  }

  // Method to check if four points form a square
  private static boolean isSquare(int[][] points) {
    int[] distances = new int[6];
    int index = 0;
    for (int i = 0; i < 4; i++) {
      for (int j = i + 1; j < 4; j++) {
        distances[index++] = distanceSquared(points[i], points[j]);
      }
    }
    Arrays.sort(distances);
    return distances[0] == distances[1]
        && distances[1] == distances[2]
        && distances[2] == distances[3]
        && distances[4] == distances[5]
        && distances[0] * 2 == distances[4];
  }

  // Method to find number of squares using brute force
  public static int countSquaresBruteForce(int[][] points) {
    int n = points.length;
    int count = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          for (int l = k + 1; l < n; l++) {
            int[][] square = {points[i], points[j], points[k], points[l]};
            if (isSquare(square)) {
              count++;
            }
          }
        }
      }
    }
    return count;
  }

  // Method to find number of squares using optimized approach

  public static int countSquaresOptimized(int[][] points) {
    Set<String> pointSet = new HashSet<>();
    for (int[] point : points) {
      pointSet.add(Arrays.toString(point));
    }

    int count = 0;
    int n = points.length;

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int[] p1 = points[i];
        int[] p2 = points[j];

        // Calculate the potential other two points
        int dx = p2[0] - p1[0];
        int dy = p2[1] - p1[1];

        int[] p3 = {p1[0] - dy, p1[1] + dx};
        int[] p4 = {p2[0] - dy, p2[1] + dx};

        int[] p5 = {p1[0] + dy, p1[1] - dx};
        int[] p6 = {p2[0] + dy, p2[1] - dx};

        // Check if these points exist
        if (pointSet.contains(Arrays.toString(p3)) && pointSet.contains(Arrays.toString(p4))) {
          count++;
        }

        if (pointSet.contains(Arrays.toString(p5)) && pointSet.contains(Arrays.toString(p6))) {
          count++;
        }
      }
    }

    // Each square is counted twice, so divide by 2
    return count / 4;
  }

  public static void main(String[] args) {
    int[][] points = {
      {1, 1},
      {0, 0},
      {-4, 2},
      {-2, -1},
      {0, 1},
      {1, 0},
      {-1, 4}
    };
    System.out.println(countSquaresOptimized(points)); // Output: 2
  }
}
