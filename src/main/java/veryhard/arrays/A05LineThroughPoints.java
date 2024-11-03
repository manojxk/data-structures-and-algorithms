package veryhard.arrays;

/*
 * Problem Statement:
 *
 * You are given an array of points, where each point is represented as an integer array [x, y].
 * Write a function that returns the maximum number of points that lie on the same straight line.
 *
 * Example:
 *
 * Input:
 * points = [[1, 1], [2, 2], [3, 3], [4, 5], [5, 6]]
 *
 * Output:
 * 3
 *
 * Explanation:
 * The points (1,1), (2,2), and (3,3) are all on the same line, so the output is 3.
 */

/*
 * Solution Approach:
 *
 * 1. For each point, consider it as an anchor point.
 * 2. For every other point, calculate the slope between the anchor point and the other point.
 * 3. Use a hashmap to count how many times each slope occurs.
 * 4. The maximum number of points on the same line is the highest count of a slope for any given anchor point.
 *
 * Slopes can be represented as a fraction (dy/dx). To avoid precision issues with floating-point division,
 * store the slope as a pair of integers (dy, dx) after reducing the fraction to its simplest form.
 */

import java.util.*;

public class A05LineThroughPoints {

  // Function to calculate the maximum number of points on the same line
  public static int maxPointsOnLine(int[][] points) {
    if (points.length < 2) return points.length;

    int maxPoints = 1;

    for (int i = 0; i < points.length; i++) {
      Map<String, Integer> slopeMap = new HashMap<>();
      int maxSlopeCount = 0;

      for (int j = 0; j < points.length; j++) {
        if (i == j) continue;

        // Calculate the slope between points[i] and points[j]
        int[] slope = calculateSlope(points[i], points[j]);
        String slopeKey = slope[0] + "," + slope[1];

        // Count the occurrences of each slope
        slopeMap.put(slopeKey, slopeMap.getOrDefault(slopeKey, 0) + 1);
        maxSlopeCount = Math.max(maxSlopeCount, slopeMap.get(slopeKey));
      }

      // The maximum points on the same line is the highest slope count + the anchor point
      maxPoints = Math.max(maxPoints, maxSlopeCount + 1);
    }

    return maxPoints;
  }

  // Helper function to calculate the slope between two points as a reduced fraction (dy/dx)
  private static int[] calculateSlope(int[] p1, int[] p2) {
    int dy = p2[1] - p1[1]; // y2 - y1
    int dx = p2[0] - p1[0]; // x2 - x1

    if (dx == 0) return new int[] {1, 0}; // Vertical line
    if (dy == 0) return new int[] {0, 1}; // Horizontal line

    int gcd = greatestCommonDivisor(Math.abs(dy), Math.abs(dx));
    dy /= gcd;
    dx /= gcd;

    // Ensure consistent representation of slopes: keep dx positive
    if (dx < 0) {
      dy = -dy;
      dx = -dx;
    }

    return new int[] {dy, dx};
  }

  // Helper function to calculate the greatest common divisor (GCD) using the Euclidean algorithm
  private static int greatestCommonDivisor(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  // Recursive function to calculate the greatest common divisor (GCD) using the Euclidean algorithm
  private static int greatestCommonDivisorRec(int a, int b) {
    // Base case: if b is 0, the GCD is a
    if (b == 0) {
      return a;
    }
    // Recursive case: call GCD with b and a % b
    return greatestCommonDivisor(b, a % b);
  }

  // Main function to test the Line Through Points implementation
  public static void main(String[] args) {
    int[][] points = {{1, 1}, {2, 2}, {3, 3}, {4, 5}, {5, 6}};

    // Output: 3
    System.out.println("Max points on a line: " + maxPointsOnLine(points));
  }

  /*
   * Time Complexity:
   * O(n^2), where n is the number of points. For each point, we calculate the slope between that point and every other point.
   *
   * Space Complexity:
   * O(n), where n is the number of points. We use a hashmap to store the slopes for each anchor point.
   */
}
