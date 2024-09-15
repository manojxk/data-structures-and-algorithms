package hard.stack;

/*
 Problem: Largest Rectangle Under Skyline

 Given an array of integers where each integer represents the height of a bar in a histogram,
 find the area of the largest rectangle that can be formed under the histogram.

 Example:
 Input: [2, 1, 5, 6, 2, 3]
 Output: 10
 Explanation: The largest rectangle can be formed by the bars of heights 5 and 6, with an area of 10 (width = 2, height = 5).

 Approach:
 1. Use stacks to find the Nearest Smaller to Left (NS2L) and Nearest Smaller to Right (NS2R) for each bar.
 2. Calculate the width for each bar using NS2L and NS2R.
 3. The area of the rectangle for each bar is its height multiplied by its width.
 4. Return the maximum area.

 Time Complexity:
 - O(n), where n is the number of bars in the histogram.
   We process each element once while calculating NS2L, NS2R, and maximum area.

 Space Complexity:
 - O(n), for storing the indices in the stack, and for auxiliary arrays for NS2L, NS2R, and the area calculation.
*/

import java.util.*;

public class A02LargestRectangleInHistogram {

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

  // Function to find the nearest smaller to left for each element
  public static List<Integer> NS2L(int[] arr, int n) {
    Stack<int[]> stack = new Stack<>();
    List<Integer> result = new ArrayList<>();
    int pseudoIndex = -1;

    for (int i = 0; i < n; i++) {
      // If stack is empty, no smaller element on the left
      if (stack.isEmpty()) {
        result.add(pseudoIndex);
      }
      // If the element at the top of the stack is smaller, use its index
      else if (stack.peek()[0] < arr[i]) {
        result.add(stack.peek()[1]);
      }
      // If the element at the top of the stack is greater or equal, keep popping
      else {
        while (!stack.isEmpty() && stack.peek()[0] >= arr[i]) {
          stack.pop();
        }
        if (stack.isEmpty()) {
          result.add(pseudoIndex);
        } else {
          result.add(stack.peek()[1]);
        }
      }
      // Push current element and index to the stack
      stack.push(new int[] {arr[i], i});
    }
    return result;
  }

  // Function to find the nearest smaller to right for each element
  public static List<Integer> NS2R(int[] arr, int n) {
    Stack<int[]> stack = new Stack<>();
    List<Integer> result = new ArrayList<>();
    int pseudoIndex = n;

    for (int i = n - 1; i >= 0; i--) {
      // If stack is empty, no smaller element on the right
      if (stack.isEmpty()) {
        result.add(pseudoIndex);
      }
      // If the element at the top of the stack is smaller, use its index
      else if (stack.peek()[0] < arr[i]) {
        result.add(stack.peek()[1]);
      }
      // If the element at the top of the stack is greater or equal, keep popping
      else {
        while (!stack.isEmpty() && stack.peek()[0] >= arr[i]) {
          stack.pop();
        }
        if (stack.isEmpty()) {
          result.add(pseudoIndex);
        } else {
          result.add(stack.peek()[1]);
        }
      }
      // Push current element and index to the stack
      stack.push(new int[] {arr[i], i});
    }

    // Since we traversed from right to left, we need to reverse the result
    Collections.reverse(result);
    return result;
  }

  // Function to calculate the maximum area under the histogram
  public static int getMaxArea(int[] hist, int n) {
    List<Integer> left = NS2L(hist, n);
    List<Integer> right = NS2R(hist, n);
    int[] width = new int[n];
    int[] area = new int[n];

    // Calculate the width for each bar
    for (int i = 0; i < n; i++) {
      width[i] = right.get(i) - left.get(i) - 1;
    }

    // Calculate the area for each bar
    for (int i = 0; i < n; i++) {
      area[i] = width[i] * hist[i];
    }

    // Return the maximum area
    return Arrays.stream(area).max().getAsInt();
  }

  // Main function to find the largest rectangle area
  public static int largestRectangleArea(int[] heights) {
    return getMaxArea(heights, heights.length);
  }

  // Test the solution
  public static void main(String[] args) {
    A02LargestRectangleInHistogram sol = new A02LargestRectangleInHistogram();
    int[] heights = {2, 1, 5, 6, 2, 3};
    System.out.println(
        "Largest Rectangle Area: " + sol.largestRectangleArea(heights)); // Output: 10
  }
}
