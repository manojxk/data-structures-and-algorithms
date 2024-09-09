/**
 * Problem Statement: Minimum Number of Jumps
 *
 * <p>You are given a non-empty array of positive integers where each integer represents the maximum
 * number of steps you can take forward in the array. For example, if the element at index 1 is 3,
 * you can go from index 1 to index 2, 3, or 4.
 *
 * <p>Write a function that returns the minimum number of jumps needed to reach the final index.
 *
 * <p>Note that jumping from index i to index i + x always constitutes one jump, no matter how large
 * x is.
 *
 * <p>Function Signature: public static int minNumberOfJumps(int[] array);
 *
 * <p>Input: - A non-empty array of positive integers.
 *
 * <p>Output: - An integer representing the minimum number of jumps to reach the last index.
 *
 * <p>Constraints: - You can assume the input array will always have at least one element. - The
 * input array will always contain positive integers.
 *
 * <p>Example:
 *
 * <p>Input: int[] array = {3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3};
 *
 * <p>Output: 4 // The sequence of jumps is: 3 --> 4 --> 2 --> 7 --> 3
 */

/*
Approach
We'll use a greedy approach to solve this problem efficiently in O(n) time. Here's how it works:

Greedy Strategy:

You traverse the array from left to right, trying to explore the farthest point you can reach from your current position.
You always keep track of the farthest point that can be reached using the current jump, and you only make a jump when you've reached the maximum possible point for that jump.
You also maintain a count of how many jumps are required.
        Variables:

jumps: This tracks the number of jumps made.
        currentEnd: This tracks the end of the range that can be reached with the current number of jumps.
        farthest: This tracks the farthest point that can be reached at each step.
Key Observation:

If you can reach the end of the array, then the problem can be solved by making jumps only when necessary, i.e., when you've reached the farthest point that can be covered by the current jump.*/

package hard.dp;

public class MinNumberOfJumps {

  public static int minNumberOfJumps(int[] array) {
    if (array.length == 1) return 0; // No jump needed if there's only one element

    int jumps = 0; // To count the number of jumps
    int farthest = 0; // The farthest index we can reach
    int currentEnd = 0; // The end of the current jump range

    for (int i = 0; i < array.length - 1; i++) {
      farthest = Math.max(farthest, i + array[i]); // Max distance we can reach from i

      // If we have reached the end of the current jump range
      if (i == currentEnd) {
        jumps++; // Make a jump
        currentEnd = farthest; // Update the current end to the farthest point we can reach

        // If the farthest point we can reach is the last index or beyond, stop
        if (currentEnd >= array.length - 1) {
          break;
        }
      }
    }

    return jumps;
  }

  public static void main(String[] args) {
    int[] array = {3, 4, 2, 1, 2, 3, 7, 1, 1, 1, 3};
    System.out.println(minNumberOfJumps(array)); // Output: 4
  }
}
/*
Explanation:
Initialization:

We start with jumps = 0 because no jumps have been made yet.
currentEnd is initialized to 0, representing the range of the first jump.
farthest is also initialized to 0, representing the farthest point that can be reached at each step.
Greedy Logic:

We loop through the array, updating the farthest point that can be reached from the current index i.
If the current index i reaches currentEnd, it means we need to make a jump. We increment jumps and update currentEnd to the farthest point.
Termination:

The loop stops when currentEnd reaches or exceeds the last index of the array.
Time Complexity:
Time Complexity: O(n), where n is the length of the array. This is because we only traverse the array once.
Space Complexity: O(1) since we are using only a few extra variables.*/
