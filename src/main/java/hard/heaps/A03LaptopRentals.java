package hard.heaps;

/*
 Problem: Laptop Rentals

 You are given a list of intervals where each interval represents a start and end time of a laptop rental.
 You are required to return the minimum number of laptops that are required to satisfy all rentals.

 Example 1:
 Input:
    intervals = [[0, 2], [1, 4], [4, 6], [2, 5], [5, 7]]
 Output:
    3
 Explanation:
    At time 0: one laptop is required.
    At time 1: another laptop is required.
    At time 2: another laptop is required (total: 3 laptops).
    After time 4: the number of laptops required decreases as rentals end.

 Constraints:
 - The intervals are non-negative integers.
 - No interval will have an invalid start time (i.e., start < end).

 Solution Approach:
 1. Separate the start and end times of the intervals into two lists.
 2. Sort the start and end times.
 3. Traverse through the start and end times, keeping track of how many laptops are needed.
    Increase the laptop count when a rental starts and decrease when a rental ends.
*/

import java.util.Arrays;

public class A03LaptopRentals {

  // Function to calculate the minimum number of laptops required
  public static int minLaptopsRequired(int[][] intervals) {
    if (intervals.length == 0) {
      return 0;
    }

    // Step 1: Create arrays for start and end times
    int[] startTimes = new int[intervals.length];
    int[] endTimes = new int[intervals.length];

    for (int i = 0; i < intervals.length; i++) {
      startTimes[i] = intervals[i][0];
      endTimes[i] = intervals[i][1];
    }

    // Step 2: Sort the start and end times
    Arrays.sort(startTimes);
    Arrays.sort(endTimes);

    // Step 3: Traverse the start and end times and keep track of laptop count
    int laptopsRequired = 0;
    int maxLaptops = 0;
    int startPointer = 0;
    int endPointer = 0;

    while (startPointer < intervals.length) {
      // If the next event is a start time, we need an additional laptop
      if (startTimes[startPointer] < endTimes[endPointer]) {
        laptopsRequired++;
        maxLaptops = Math.max(maxLaptops, laptopsRequired);
        startPointer++;
      } else {
        // If the next event is an end time, a laptop is freed up
        laptopsRequired--;
        endPointer++;
      }
    }

    return maxLaptops;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int[][] intervals1 = {{0, 2}, {1, 4}, {4, 6}, {2, 5}, {5, 7}};
    System.out.println(
        "Minimum laptops required (Example 1): " + minLaptopsRequired(intervals1)); // Output: 3

    // Example 2
    int[][] intervals2 = {{0, 2}, {2, 3}, {3, 4}, {1, 5}};
    System.out.println(
        "Minimum laptops required (Example 2): " + minLaptopsRequired(intervals2)); // Output: 2
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of intervals. Sorting the start and end times takes O(n log n) time, and then we iterate through the lists in O(n) time.

   Space Complexity:
   - O(n), where n is the number of intervals. We store the start and end times in arrays of size n.
  */
}
