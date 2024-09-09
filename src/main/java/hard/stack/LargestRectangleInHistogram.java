package hard.stack;

/**
 * Problem Statement:
 *
 * <p>You are given an array of positive integers representing the heights of adjacent buildings.
 * The task is to return the area of the largest rectangle that can be created by any number of
 * adjacent buildings. All buildings have a width of 1 unit, and the height of the rectangle is
 * determined by the smallest building in the selected range.
 *
 * <p>If no rectangles can be created, the function should return 0.
 *
 * <p>Sample Input: buildings = [1, 3, 3, 2, 4, 1, 5, 3, 2]
 *
 * <p>Sample Output: 9
 *
 * <p>Explanation: The largest rectangle is formed using the buildings with heights [3, 3, 2, 4].
 * The width of this rectangle is 3 and the height is 3, resulting in an area of 9.
 */
import java.util.*;

public class LargestRectangleInHistogram {

  // Brute Force Approach:
  // For each building, try to expand to the left and right as far as the height allows,
  // then calculate the area of the rectangle formed.
  // Time Complexity: O(n^2), where n is the number of buildings. For each building, we expand in
  // both directions.
  // Space Complexity: O(1), no extra space required besides a few variables.
  public static int largestRectangleBruteForce(int[] buildings) {
    int maxArea = 0;
    for (int i = 0; i < buildings.length; i++) {
      int minHeight = buildings[i];
      for (int j = i; j < buildings.length; j++) {
        minHeight = Math.min(minHeight, buildings[j]); // Minimum height in the range [i, j]
        int width = j - i + 1; // Number of buildings considered
        int area = minHeight * width;
        maxArea = Math.max(maxArea, area); // Keep track of the largest area
      }
    }
    return maxArea;
  }

  // Optimized Approach using a Stack:
  // Time Complexity: O(n), where n is the number of buildings.
  // Space Complexity: O(n), for storing indices in the stack.
  public static int largestRectangleOptimized(int[] buildings) {
    Stack<Integer> stack = new Stack<>();
    int maxArea = 0;
    int n = buildings.length;

    for (int i = 0; i <= n; i++) {
      int height = (i == n) ? 0 : buildings[i];
      while (!stack.isEmpty() && height < buildings[stack.peek()]) {
        int h = buildings[stack.pop()];
        int width = stack.isEmpty() ? i : i - stack.peek() - 1;
        maxArea = Math.max(maxArea, h * width);
      }
      stack.push(i);
    }

    return maxArea;
  }

  // Main function to test both solutions
  public static void main(String[] args) {
    int[] buildings = {1, 3, 3, 2, 4, 1, 5, 3, 2};

    // Brute Force Solution
    int resultBruteForce = largestRectangleBruteForce(buildings);
    System.out.println("Brute Force Solution: " + resultBruteForce);

    // Optimized Solution using Stack
    int resultOptimized = largestRectangleOptimized(buildings);
    System.out.println("Optimized Solution: " + resultOptimized);
  }
}
