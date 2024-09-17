package medium.graphs;

/*
 * Problem Statement:
 * Given an array of integers, where each integer represents a jump of its value in the array, write a function that returns a boolean
 * indicating whether the jumps form a single cycle. A "single cycle" means that starting from any index in the array,
 * you can visit every other index exactly once before returning to the starting index.
 *
 * The jumps in the array can be positive or negative, and the array is circular, so jumping beyond the bounds of the array wraps around to the other side.
 *
 * Example:
 *
 * Input:
 * array = [2, 3, 1, -4, -4, 2]
 *
 * Output:
 * true // Explanation: The jumps form a single cycle that visits every element exactly once.
 */

/*
 * Solution Approach:
 *
 * 1. Initialize a `visited` count to 0 and track the current index starting from 0.
 * 2. Keep jumping according to the values in the array.
 * 3. Keep track of the number of elements visited. If you visit `n` elements (where `n` is the length of the array) and return to the starting index, it's a single cycle.
 * 4. If you return to the starting index before visiting all elements or if any element is visited more than once before completing a cycle, it is not a single cycle.
 */

public class A01SingleCycleCheck {

  // Function to check if the array forms a single cycle
  public static boolean hasSingleCycle(int[] array) {
    int numElementsVisited = 0; // Count of visited elements
    int currentIndex = 0; // Start at index 0

    // Loop through until we've either visited all elements or encountered a violation
    while (numElementsVisited < array.length) {
      // If we return to the starting index before visiting all elements, it's not a single cycle
      if (numElementsVisited > 0 && currentIndex == 0) {
        return false;
      }

      // Increment the number of elements visited
      numElementsVisited++;

      // Calculate the next index using the jump value
      currentIndex = getNextIndex(currentIndex, array);
    }

    // After visiting all elements, check if we returned to the starting index
    return currentIndex == 0;
  }

  // Helper function to calculate the next index in a circular manner
  private static int getNextIndex(int currentIndex, int[] array) {
    int jump = array[currentIndex];
    int nextIndex = (currentIndex + jump) % array.length;

    // Handle negative wrapping
    if (nextIndex < 0) {
      nextIndex += array.length;
    }

    return nextIndex;
  }

  // Main function to test the Single Cycle Check implementation
  public static void main(String[] args) {
    int[] array = {2, 3, 1, -4, -4, 2};

    // Output: true
    System.out.println("Does the array form a single cycle? " + hasSingleCycle(array));
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of elements in the array.
   * Each element is visited once during the traversal.
   *
   * Space Complexity:
   * O(1), because we only use a few additional variables regardless of the input size.
   */
}
