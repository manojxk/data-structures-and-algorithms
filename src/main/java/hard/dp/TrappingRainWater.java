package hard.dp;

/**
 * Problem Statement: Trapping Rain Water
 *
 * <p>You're given an array of non-negative integers where each non-zero integer represents the
 * height of a pillar of width 1. Imagine water being poured over all of the pillars; write a
 * function that returns the surface area of the water trapped between the pillars when viewed from
 * the front. Note that spilled water should be ignored.
 *
 * <p>Function Signature: public static int trapWater(int[] heights);
 *
 * <p>Input: - A non-empty array of non-negative integers representing pillar heights.
 *
 * <p>Output: - An integer representing the total area of trapped water between the pillars.
 *
 * <p>Constraints: - The input array will always contain non-negative integers.
 *
 * <p>Example:
 *
 * <p>Input: int[] heights = {0, 8, 0, 0, 5, 0, 0, 10, 0, 0, 1, 1, 0, 3};
 *
 * <p>Output: 48
 *
 * <p>Explanation: - Below is a visual representation of the input where the dots (.) represent
 * trapped water and vertical bars (|) represent the pillars.
 *
 * <p>| | |.....| |.....| |.....| |..|..| |..|..| |..|..|.....| |..|..|.....| _|..|..|..||.|
 *
 * <p>The total trapped water is 48 units.
 */
public class TrappingRainWater {

  public static int trapWater(int[] heights) {
    if (heights == null || heights.length == 0) {
      return 0; // No pillars, no trapped water
    }

    int n = heights.length;
    int totalWater = 0;

    // Arrays to store the maximum height to the left and right of each element
    int[] leftMax = new int[n];
    int[] rightMax = new int[n];

    // Fill leftMax array
    leftMax[0] = heights[0];
    for (int i = 1; i < n; i++) {
      leftMax[i] = Math.max(leftMax[i - 1], heights[i]);
    }

    // Fill rightMax array
    rightMax[n - 1] = heights[n - 1];
    for (int i = n - 2; i >= 0; i--) {
      rightMax[i] = Math.max(rightMax[i + 1], heights[i]);
    }

    // Calculate trapped water
    for (int i = 0; i < n; i++) {
      int waterLevel =
          Math.min(
              leftMax[i], rightMax[i]); // Water is trapped at the minimum of leftMax and rightMax
      if (waterLevel > heights[i]) {
        totalWater += waterLevel - heights[i]; // Add the water trapped at index i
      }
    }

    return totalWater;
  }

  public static void main(String[] args) {
    int[] heights = {0, 8, 0, 0, 5, 0, 0, 10, 0, 0, 1, 1, 0, 3};
    System.out.println(trapWater(heights)); // Output: 48
  }
}


/*
Explanation:
LeftMax and RightMax Arrays:

We create two arrays: leftMax and rightMax. leftMax[i] contains the highest pillar on the left of index i, and rightMax[i] contains the highest pillar on the right of index i.
We fill the leftMax array by iterating from left to right and the rightMax array by iterating from right to left.
Trapped Water Calculation:

For each index i, the water that can be trapped at that index is the difference between the minimum of leftMax[i] and rightMax[i] and the height of the current pillar heights[i].
We add this difference to the total trapped water if the water level is greater than the current height.
Time Complexity:
Time Complexity: O(n) where n is the length of the heights array. We make three passes through the array: one to fill leftMax, one to fill rightMax, and one to calculate the trapped water.
Space Complexity: O(n) for the two auxiliary arrays leftMax and rightMax.*/
