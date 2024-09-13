package hard.dp;

import cools.arrays.A10JumpGameII;

public class A03MinNumberOfJumps {

  public static int minNumberOfJumps(int[] array) {
    A10JumpGameII a10JumpGameII = new A10JumpGameII();
    return a10JumpGameII.jump(array);
  }

  public static void main(String[] args) {
    int[] array = {3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3};
    System.out.println(minNumberOfJumps(array)); // Output: 4
  }
}
