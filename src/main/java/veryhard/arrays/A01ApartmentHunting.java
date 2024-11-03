package veryhard.arrays;

/*
 * Problem Statement:
 *
 * You're given a list of blocks where each block contains a Boolean indicating the presence of a specific building type: gym, school, or store.
 * You want to choose the block that minimizes the maximum distance to the nearest gym, school, and store.
 * Write a function that returns the index of the best block to live in.
 *
 * Example:
 *
 * Input:
 * blocks = [
 *   {"gym": false, "school": true, "store": false},
 *   {"gym": true, "school": false, "store": false},
 *   {"gym": true, "school": true, "store": false},
 *   {"gym": false, "school": true, "store": false},
 *   {"gym": false, "school": true, "store": true}
 * ]
 *
 * requirements = ["gym", "school", "store"]
 *
 * Output:
 * 3
 *
 * Explanation:
 * The best block to live in is block 3, where the maximum distance to the gym, school, or store is minimized.
 */

/*
 * Solution Approach:
 *
 * 1. For each requirement (gym, school, store), precompute the distance to the nearest gym, school, and store for each block.
 * 2. Calculate the maximum distance to any of the required facilities for each block.
 * 3. Return the index of the block where the maximum distance to any facility is minimized.
 */

import java.util.*;

public class A01ApartmentHunting {

  // Function to find the best block to live in
  public static int apartmentHunting(List<Map<String, Boolean>> blocks, String[] requirements) {
    int[][] minDistancesFromBlocks = new int[requirements.length][blocks.size()];

    // Calculate the minimum distance to each requirement for each block
    for (int i = 0; i < requirements.length; i++) {
      calculateMinDistances(blocks, requirements[i], minDistancesFromBlocks[i]);
    }

    // Find the block that minimizes the maximum distance to any requirement
    return findBlockWithMinMaxDistance(minDistancesFromBlocks, blocks.size());
  }

  // Helper function to calculate the minimum distance to a specific requirement from each block
  private static void calculateMinDistances(
      List<Map<String, Boolean>> blocks, String requirement, int[] minDistances) {
    int closest = Integer.MAX_VALUE;

    // Forward pass: Calculate the minimum distance from the left
    for (int i = 0; i < blocks.size(); i++) {
      if (blocks.get(i).get(requirement)) {
        closest = i;
      }
      minDistances[i] = Math.abs(i - closest);
    }

    closest = Integer.MAX_VALUE;

    // Backward pass: Calculate the minimum distance from the right
    for (int i = blocks.size() - 1; i >= 0; i--) {
      if (blocks.get(i).get(requirement)) {
        closest = i;
      }
      minDistances[i] = Math.min(minDistances[i], Math.abs(i - closest));
    }
  }

  // Helper function to find the block that minimizes the maximum distance to any requirement
  private static int findBlockWithMinMaxDistance(int[][] minDistancesFromBlocks, int blocksCount) {
    int optimalBlockIndex = 0;
    int minMaxDistance = Integer.MAX_VALUE;

    // Iterate through each block to find the block with the smallest maximum distance to any
    // requirement
    for (int i = 0; i < blocksCount; i++) {
      int maxDistanceAtBlock = Integer.MIN_VALUE;

      // Find the maximum distance to any requirement for the current block
      for (int j = 0; j < minDistancesFromBlocks.length; j++) {
        maxDistanceAtBlock = Math.max(maxDistanceAtBlock, minDistancesFromBlocks[j][i]);
      }

      // Update the optimal block if the maximum distance at the current block is smaller
      if (maxDistanceAtBlock < minMaxDistance) {
        minMaxDistance = maxDistanceAtBlock;
        optimalBlockIndex = i;
      }
    }

    return optimalBlockIndex;
  }

  // Main function to test the Apartment Hunting implementation
  public static void main(String[] args) {
    List<Map<String, Boolean>> blocks = new ArrayList<>();
    blocks.add(Map.of("gym", false, "school", true, "store", false));
    blocks.add(Map.of("gym", true, "school", false, "store", false));
    blocks.add(Map.of("gym", true, "school", true, "store", false));
    blocks.add(Map.of("gym", false, "school", true, "store", false));
    blocks.add(Map.of("gym", false, "school", true, "store", true));

    String[] requirements = {"gym", "school", "store"};

    // Output: 3
    System.out.println("Best Block to live in: " + apartmentHunting(blocks, requirements));
  }

  /*
   * Time Complexity:
   * O(b * r), where b is the number of blocks and r is the number of requirements.
   * We perform two passes (forward and backward) for each requirement to calculate the minimum distance from each block.
   *
   * Space Complexity:
   * O(b * r), where b is the number of blocks and r is the number of requirements.
   * We use a 2D array to store the minimum distances for each block for each requirement.
   */
}
