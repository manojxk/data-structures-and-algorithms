package hard.dp;

/**
 * Problem Statement:
 *
 * <p>You are given a non-empty array of arrays where each subarray holds three integers and
 * represents a disk. Each disk is represented by its width, depth, and height. The goal is to find
 * the maximum possible height of a stack of disks such that each disk placed on top of another has
 * strictly smaller width, depth, and height than the disk below it.
 *
 * <p>The function should return an array representing the disks in the final stack, starting with
 * the top disk and ending with the bottom disk. The integers in each subarray must always represent
 * the width, depth, and height, and the disks cannot be rotated.
 *
 * <p>Assumptions: - There will only be one stack with the maximum total height. - Each disk can
 * only be placed on top of another disk if it has strictly smaller width, depth, and height.
 *
 * <p>Sample Input: disks = [[2, 1, 2], [3, 2, 3], [2, 2, 8], [2, 3, 4], [1, 3, 1], [4, 4, 5]]
 *
 * <p>Sample Output: [[2, 1, 2], [3, 2, 3], [4, 4, 5]] // The maximum height of the stack is 10 (2 +
 * 3 + 5)
 */
import java.util.*;

public class DiskStacking {

  // Helper function to build the final stack sequence
  private static List<int[]> buildSequence(int[][] disks, int[] sequences, int currentIdx) {
    List<int[]> sequence = new ArrayList<>();
    while (currentIdx != -1) {
      sequence.add(disks[currentIdx]);
      currentIdx = sequences[currentIdx];
    }
    Collections.reverse(sequence);
    return sequence;
  }

  // Optimized Approach:
  // Time Complexity: O(n^2) - We compare each disk with all others once.
  // Space Complexity: O(n) - We store the sequence and heights of each disk.
  public static List<int[]> stackDisksOptimized(int[][] disks) {
    // Sort disks based on their heights
    Arrays.sort(disks, (d1, d2) -> d1[2] - d2[2]);
    int[] heights = new int[disks.length];
    int[] sequences = new int[disks.length];
    int maxHeightIdx = 0;

    // Initialize heights with the disk's own height and sequence
    for (int i = 0; i < disks.length; i++) {
      heights[i] = disks[i][2];
      sequences[i] = -1;
    }

    // Compute the maximum height for each disk
    for (int i = 1; i < disks.length; i++) {
      for (int j = 0; j < i; j++) {
        if (disks[j][0] < disks[i][0] && disks[j][1] < disks[i][1] && disks[j][2] < disks[i][2]) {
          if (heights[i] < heights[j] + disks[i][2]) {
            heights[i] = heights[j] + disks[i][2];
            sequences[i] = j;
          }
        }
      }

      // Update the maximum height index
      if (heights[i] > heights[maxHeightIdx]) {
        maxHeightIdx = i;
      }
    }

    // Build the sequence of disks for the result
    return buildSequence(disks, sequences, maxHeightIdx);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int[][] disks = {{2, 1, 2}, {3, 2, 3}, {2, 2, 8}, {2, 3, 4}, {1, 3, 1}, {4, 4, 5}};



    // Optimized Solution
    List<int[]> resultOptimized = stackDisksOptimized(disks);
    System.out.println("Optimized Solution:");
    for (int[] disk : resultOptimized) {
      System.out.println(Arrays.toString(disk));
    }
  }
}
