package veryhard.arrays;

/*
 * Problem Statement:
 *
 * You are given an array of points represented by their x and y coordinates on a 2D plane.
 * Write a function that returns the minimum area of a rectangle formed by any four points in the array.
 * If no rectangle can be formed, return 0.
 *
 * Example:
 *
 * Input:
 * points = [
 *   [1, 1], [1, 3], [3, 1], [3, 3], [2, 2]
 * ]
 *
 * Output:
 * 4
 *
 * Explanation:
 * The minimum area rectangle is formed by the points (1, 1), (1, 3), (3, 1), and (3, 3), which form a rectangle with an area of 4.
 */

/*
 * Solution Approach:
 *
 * 1. Store all points in a set for O(1) lookup.
 * 2. Iterate over all pairs of points and treat them as diagonal points of a potential rectangle.
 * 3. If the other two points required to form the rectangle exist, calculate the area.
 * 4. Track the minimum area found.
 */

import java.util.*;

public class A04MinimumAreaRectangle {

  // Function to find the minimum area of a rectangle formed by any four points
  public static int minAreaRect(int[][] points) {
    Set<String> pointSet = new HashSet<>();
    for (int[] point : points) {
      pointSet.add(point[0] + "," + point[1]);
    }

    int minArea = Integer.MAX_VALUE;
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        int[] p1 = points[i];
        int[] p2 = points[j];

        // Check if the points are diagonal (i.e., they form opposite corners of a rectangle)
        if (p1[0] != p2[0] && p1[1] != p2[1]) {
          // Check if the other two points of the rectangle exist in the set
          if (pointSet.contains(p1[0] + "," + p2[1]) && pointSet.contains(p2[0] + "," + p1[1])) {
            // Calculate the area of the rectangle
            int area = Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]);
            minArea = Math.min(minArea, area);
          }
        }
      }
    }

    // If no rectangle was found, return 0
    return minArea == Integer.MAX_VALUE ? 0 : minArea;
  }

  // Main function to test the Minimum Area Rectangle implementation
  public static void main(String[] args) {
    int[][] points = {{1, 1}, {1, 3}, {3, 1}, {3, 3}, {2, 2}};

    // Output: 4
    System.out.println("Minimum Area of Rectangle: " + minAreaRect(points));
  }

  /*
   * Time Complexity:
   * O(n^2), where n is the number of points. We iterate over every pair of points and check if the other two points exist in constant time.
   *
   * Space Complexity:
   * O(n), where n is the number of points. We store each point in a set for constant time lookup.
   */
}
