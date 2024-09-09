package cools.arrays;

/*
 Problem: Trapping Rain Water using leftMax and rightMax arrays

 This approach uses two arrays to store the maximum height to the left and right of each index,
 then calculates the trapped water at each index based on these values.

 Constraints:
 - 1 <= heights.length <= 2 * 10^4
 - 0 <= heights[i] <= 10^5
*/

public class A15TrappingRainWater {

  // Function to calculate the trapped water
  public int trap(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0; // No pillars, no trapped water
    }

    int n = heights.length;
    int totalWater = 0;

    // Arrays to store the maximum height to the left and right of each element
    int[] leftMax = new int[n];
    int[] rightMax = new int[n];

    // Step 1: Fill leftMax array
    leftMax[0] = heights[0];
    for (int i = 1; i < n; i++) {
      leftMax[i] = Math.max(leftMax[i - 1], heights[i]);
    }

    // Step 2: Fill rightMax array
    rightMax[n - 1] = heights[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      rightMax[i] = Math.max(rightMax[i + 1], heights[i]);
    }

    // Step 3: Calculate trapped water at each index
    for (int i = 0; i < n; i++) {
      int waterLevel = Math.min(leftMax[i], rightMax[i]); // Min of leftMax and rightMax
      if (waterLevel > heights[i]) {
        totalWater += waterLevel - heights[i]; // Add trapped water at index i
      }
    }

    return totalWater; // Return the total trapped water
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A15TrappingRainWater solution = new A15TrappingRainWater();

    // Example 1
    int[] heights1 = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    System.out.println("Trapped Water: " + solution.trap(heights1)); // Output: 6

    // Example 2
    int[] heights2 = {4, 2, 0, 3, 2, 5};
    System.out.println("Trapped Water: " + solution.trap(heights2)); // Output: 9
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the heights array. We traverse the array three times to fill leftMax, rightMax, and calculate trapped water.

   Space Complexity:
   - O(n), since we are using two extra arrays (leftMax and rightMax) to store the maximum heights.
  */
}
