package veryhard.stack;

/*
 Problem: Largest Park Area in a City

 A city wants to build a new public park, and you've been tasked with finding the largest park they can build without disturbing existing infrastructure.

 You are given a 2D array `land` where:
  - land[i][j] = false represents unused land.
  - land[i][j] = true represents land that is already occupied by infrastructure and cannot be used for the park.

 Your task is to find the **largest rectangular park** that can be built exclusively on unused land (`false` values). The park should form a perfect rectangle.
 If no land is available to build a park, return 0.

 Example:
 Input:
  boolean[][] land = {
    {false, true,  true,  true,  false},
    {false, false, false, true,  false},
    {false, false, false, false, false},
    {false, true,  true,  true,  true }
  };
 Output: 6

 Explanation:
 The largest rectangle park (made up of `false` values) is of size 2x3, from row 1 to row 2 and columns 0 to 2, giving an area of 6.

 Approach:
 1. Treat the problem as finding the largest rectangle in a histogram. For each row, build a histogram of heights where consecutive `false` values increase the height.
 2. For each row (histogram), apply the largest rectangle in histogram algorithm using a stack to find the maximum area.
 3. Repeat the process for each row and update the largest park area encountered.

 Time Complexity: O(rows * cols), where rows and cols are the dimensions of the matrix. Each row is processed as a histogram, and for each row, we find the largest rectangle in O(cols) time.

 Space Complexity: O(cols), for storing the height array and the stack.
*/

import hard.stack.A02LargestRectangleInHistogram;

public class A01LargestParkArea {



  // Main function to find the largest possible park area
  public int largestPark(boolean[][] land) {
    if (land == null || land.length == 0 || land[0].length == 0) {
      return 0;
    }

    int rows = land.length;
    int cols = land[0].length;
    int[] heights = new int[cols]; // To store the histogram heights
    int maxArea = 0; // Store the largest area found

    // Iterate through each row of the matrix
    for (int i = 0; i < rows; i++) {
      // Update the heights array for the current row
      for (int j = 0; j < cols; j++) {
        if (land[i][j]) {
          heights[j] = 0; // Reset the height to 0 if infrastructure is found
        } else {
          heights[j]++; // Increment the height if it's unused land (false)
        }
      }

      // Find the largest rectangle area for the current row's histogram
      int currentMaxArea = A02LargestRectangleInHistogram.largestRectangleArea(heights);
      maxArea = Math.max(maxArea, currentMaxArea);
    }

    return maxArea;
  }

  // Test the solution with a sample input
  public static void main(String[] args) {
    A01LargestParkArea solution = new A01LargestParkArea();
    boolean[][] land = {
      {false, true, true, true, false},
      {false, false, false, true, false},
      {false, false, false, false, false},
      {false, true, true, true, true}
    };

    System.out.println("Largest Park Area: " + solution.largestPark(land)); // Output: 6
  }

  /*
   Time Complexity:
   - O(rows * cols), where `rows` is the number of rows and `cols` is the number of columns in the matrix. Each row is processed as a histogram, and we calculate the largest rectangle in O(cols) time.

   Space Complexity:
   - O(cols), where `cols` is the number of columns in the matrix. We use additional space for the `heights` array and the stack.
  */
}
