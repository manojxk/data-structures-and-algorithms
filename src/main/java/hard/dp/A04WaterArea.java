package hard.dp;

import cools.arrays.A15TrappingRainWater;

public class A04WaterArea {

  public static int trapWater(int[] heights) {
    return A15TrappingRainWater.trap(heights);
  }

  public static void main(String[] args) {
    int[] heights = {0, 8, 0, 0, 5, 0, 0, 10, 0, 0, 1, 1, 0, 3};
    System.out.println(trapWater(heights)); // Output: 48
  }
}
