package hard.arrays;

/*
 Problem: Count the Number of Squares from Cartesian Coordinates

 Write a function that takes in a list of Cartesian coordinates (i.e., (x, y) coordinates) and returns the number of squares that can be formed by these coordinates.
 A square must have its four corners among the coordinates in order to be counted. A single coordinate can be used as a corner for multiple different squares.

 Assumptions:
 - No coordinate will be farther than 100 units from the origin.
 - Coordinates are provided as a list of integer pairs (x, y).

 Example 1:
 Input: coordinates = [(0, 0), (1, 0), (0, 1), (1, 1)]
 Output: 1
 Explanation: The four coordinates form a square.

 Example 2:
 Input: coordinates = [(0, 0), (1, 0), (0, 1), (2, 0), (1, 1), (2, 1)]
 Output: 2
 Explanation: Two squares can be formed: one from (0, 0), (1, 0), (0, 1), (1, 1) and another from (1, 0), (2, 0), (1, 1), (2, 1).

*/

import java.util.*;

public class A08CountSquares {

  // Method to check if four points form a square
  private static boolean isSquare(int[][] points) {
    int[] distances = new int[6];
    int index = 0;
    // Calculate all pairwise distances between the 4 points
    for (int i = 0; i < 4; i++) {
      for (int j = i + 1; j < 4; j++) {
        distances[index++] = dist(points[i], points[j]);
      }
    }
    // Sort the distances to easily check the conditions for a square
    Arrays.sort(distances);

    // Check if the first four distances are equal (sides of the square)
    // and the last two distances are equal (diagonals)
    return distances[0] == distances[1]
        && distances[1] == distances[2]
        && distances[2] == distances[3]
        && distances[4] == distances[5]
        && distances[0] * 2
            == distances[4]; // Diagonal should be √2 times the side, so squared distance is double
  }

  // Method to find the number of squares using brute force
  public static int countSquaresBruteForce(int[][] points) {
    int n = points.length;
    int count = 0;

    // Check all combinations of 4 points
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

  // Helper function to calculate squared distance between two points (x1, y1) and (x2, y2)
  private static int dist(int[] p1, int[] p2) {
    return (p1[0] - p2[0]) * (p1[0] - p2[0]) + (p1[1] - p2[1]) * (p1[1] - p2[1]);
  }

  // Optimized Solution: Use HashSet and check diagonals
  public static int countSquaresOptimized(int[][] coordinates) {
    int numSquares = 0;
    Set<String> pointsSet = new HashSet<>();

    // Step 1: Store all points in a HashSet for fast lookup
    for (int[] coordinate : coordinates) {
      pointsSet.add(coordinate[0] + "," + coordinate[1]);
    }

    // Step 2: Iterate over all pairs of points to find potential diagonal points of squares
    for (int i = 0; i < coordinates.length; i++) {
      for (int j = i + 1; j < coordinates.length; j++) {
        int[] p1 = coordinates[i];
        int[] p2 = coordinates[j];

        // Calculate the potential square's other two corners
        if (isDiagonal(p1, p2)) {
          int[] p3 = {p1[0], p2[1]}; // Point (x1, y2)
          int[] p4 = {p2[0], p1[1]}; // Point (x2, y1)

          // Check if both points (p3 and p4) exist in the set
          if (pointsSet.contains(p3[0] + "," + p3[1]) && pointsSet.contains(p4[0] + "," + p4[1])) {
            numSquares++;
          }
        }
      }
    }

    // Each square is counted twice, so return half the result
    return numSquares / 2;
  }

  // Helper function to check if two points can be diagonal points of a square
  private static boolean isDiagonal(int[] p1, int[] p2) {
    return Math.abs(p1[0] - p2[0]) == Math.abs(p1[1] - p2[1]);
  }

  // Main function to test the solutions
  public static void main(String[] args) {
    int[][] coordinates1 = {{0, 0}, {1, 0}, {0, 1}, {1, 1}};
    System.out.println(
        "Brute Force - Number of squares: " + countSquaresBruteForce(coordinates1)); // Output: 1
    System.out.println(
        "Optimized - Number of squares: " + countSquaresOptimized(coordinates1)); // Output: 1

    int[][] coordinates2 = {{0, 0}, {1, 0}, {0, 1}, {2, 0}, {1, 1}, {2, 1}};
    System.out.println(
        "Brute Force - Number of squares: " + countSquaresBruteForce(coordinates2)); // Output: 2
    System.out.println(
        "Optimized - Number of squares: " + countSquaresOptimized(coordinates2)); // Output: 2
  }

  /*
   Brute Force:
   - Time Complexity: O(n^4), where n is the number of coordinates. We are checking all combinations of 4 points.
   - Space Complexity: O(1), as we don't use any additional space aside from a few variables.

   Optimized Solution:
   - Time Complexity: O(n^2), where n is the number of coordinates. We compare each pair of points to see if they can be diagonally opposite corners of a square.
   - Space Complexity: O(n), where n is the number of coordinates. We use a HashSet to store all points.
  */
}
