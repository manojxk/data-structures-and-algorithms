package hard.dp;

/*
 Problem: Disk Stacking

 You are given a list of disks. Each disk has three properties: width, depth, and height.
 Your goal is to stack up the disks such that:
 - The disk at the top of the stack has a strictly smaller width, depth, and height than the disk below it.
 - You can only stack disks on top of each other if they follow this rule.

 Write a function that returns the stack with the maximum height.

 Example 1:
 Input:
    disks = [
        [2, 1, 2],  // width, depth, height
        [3, 2, 3],
        [2, 2, 8],
        [2, 3, 4],
        [4, 4, 5]
    ]
 Output:
    [[2, 1, 2], [3, 2, 3], [4, 4, 5]]
 Explanation:
    The maximum height can be achieved by stacking disks [2, 1, 2] -> [3, 2, 3] -> [4, 4, 5].

 Constraints:
 - Disks are represented as arrays of integers.
 - The function should return the list of disks in the correct order.
 - There can be at most 1000 disks.

 Solution Approach:
 1. Sort the disks based on their height to ensure that smaller disks are considered first.
 2. Use dynamic programming to store the maximum possible height at each disk.
 3. Backtrack from the maximum height to get the sequence of disks that form the maximum stack.
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A06DiskStacking {

  // Function to find the maximum height stack of disks
  public static List<int[]> diskStacking(List<int[]> disks) {
    // Step 1: Sort disks by their height
    disks.sort((a, b) -> a[2] - b[2]); // Sort by height (index 2)

    // Step 2: Initialize heights array and sequences array
    int[] heights = new int[disks.size()];
    int[] previousDiskIndices = new int[disks.size()]; // To store the sequence of disks
    Arrays.fill(previousDiskIndices, -1); // Initially, no previous disk

    // Populate the heights array with initial heights (each disk's height by itself)
    for (int i = 0; i < disks.size(); i++) {
      heights[i] = disks.get(i)[2];
    }

    // Step 3: Dynamic programming to compute maximum height
    int maxHeightIdx = 0; // Track the index of the maximum height
    for (int i = 1; i < disks.size(); i++) {
      int[] currentDisk = disks.get(i);
      for (int j = 0; j < i; j++) {
        int[] otherDisk = disks.get(j);
        if (canStack(otherDisk, currentDisk)) {
          if (heights[i] < heights[j] + currentDisk[2]) {
            heights[i] = heights[j] + currentDisk[2];
            previousDiskIndices[i] = j; // Track the previous disk in the sequence
          }
        }
      }
      if (heights[i] > heights[maxHeightIdx]) {
        maxHeightIdx = i;
      }
    }

    // Step 4: Reconstruct the sequence of disks
    return buildSequence(disks, previousDiskIndices, maxHeightIdx);
  }

  // Helper function to check if one disk can be stacked on top of another
  private static boolean canStack(int[] bottom, int[] top) {
    return bottom[0] < top[0] && bottom[1] < top[1] && bottom[2] < top[2];
  }

  // Helper function to reconstruct the sequence of disks from the maxHeight index
  private static List<int[]> buildSequence(
      List<int[]> disks, int[] previousDiskIndices, int currentIdx) {
    List<int[]> sequence = new ArrayList<>();
    while (currentIdx != -1) {
      sequence.add(0, disks.get(currentIdx)); // Add the current disk to the sequence
      currentIdx = previousDiskIndices[currentIdx]; // Move to the previous disk
    }
    return sequence;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example disks
    List<int[]> disks = new ArrayList<>();
    disks.add(new int[] {2, 1, 2});
    disks.add(new int[] {3, 2, 3});
    disks.add(new int[] {2, 2, 8});
    disks.add(new int[] {2, 3, 4});
    disks.add(new int[] {4, 4, 5});

    // Get the maximum height stack
    List<int[]> result = diskStacking(disks);

    // Print the result
    System.out.println("Maximum height stack of disks:");
    for (int[] disk : result) {
      System.out.println(Arrays.toString(disk));
    }
  }

  /*
   Time Complexity:
   - O(n^2), where n is the number of disks. We compare each disk with every other disk, resulting in a quadratic complexity.

   Space Complexity:
   - O(n), where n is the number of disks. We use extra space for storing the 'heights' array, the 'previousDiskIndices' array, and the sequence list.
  */
}
