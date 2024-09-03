/*
 * Problem Statement:
 * Write a function that takes in a non-empty array of arbitrary intervals, merges any overlapping intervals,
 * and returns the new intervals in no particular order.
 *
 * Each interval is represented as an array of two integers, where interval[0] is the start of the interval
 * and interval[1] is the end of the interval.
 *
 * Conditions:
 * - Back-to-back intervals are not considered overlapping (e.g., [1, 5] and [6, 7] are not overlapping).
 * - The start of any interval will always be less than or equal to the end of that interval.
 *
 * Example:
 * Input: intervals = [[1, 2], [3, 5], [4, 7], [6, 8], [9, 10]]
 * Output: [[1, 2], [3, 8], [9, 10]]
 * // The intervals [3, 5], [4, 7], and [6, 8] overlap and should be merged.
 */

/*Approach:
Sorting: First, sort the intervals based on the starting point of each interval.
Merging: Iterate through the sorted intervals and merge overlapping intervals by comparing the current interval with the last merged interval.
Time Complexity:
O(n log n): The time complexity is dominated by the sorting step.
Space Complexity:
O(n): The merged intervals are stored in an additional list.*/

package medium.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MergeIntervals {

  // Optimized Solution using Sorting and Merging
  public static List<int[]> mergeIntervalsOptimized(int[][] intervals) {
    if (intervals.length == 0) return new ArrayList<>();

    // Sort intervals by starting time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    List<int[]> mergedIntervals = new ArrayList<>();
    int[] currentInterval = intervals[0];
    mergedIntervals.add(currentInterval);

    for (int[] interval : intervals) {
      int currentEnd = currentInterval[1];
      int nextStart = interval[0];
      int nextEnd = interval[1];

      if (currentEnd >= nextStart) {
        // Overlapping intervals, merge them
        currentInterval[1] = Math.max(currentEnd, nextEnd);
      } else {
        // No overlap, add the new interval to the list
        currentInterval = interval;
        mergedIntervals.add(currentInterval);
      }
    }

    return mergedIntervals;
  }

  /**
   * This function merges overlapping intervals.
   *
   * @param intervals The input intervals.
   * @return An array of the merged intervals.
   */
  public static int[][] mergeIntervals(int[][] intervals) {
    // Sort the intervals by the start time.
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

    LinkedList<int[]> merged = new LinkedList<>();

    // Iterate over the sorted intervals.
    for (int[] interval : intervals) {
      // If the list of merged intervals is empty or if the current interval does not overlap with
      // the previous, append it.
      if (merged.isEmpty() || merged.getLast()[1] < interval[0]) {
        merged.add(interval);
      }
      // Otherwise, there is overlap, so we merge the current and previous intervals.
      else {
        merged.getLast()[1] = Math.max(merged.getLast()[1], interval[1]);
      }
    }

    return merged.toArray(new int[merged.size()][]);
  }

  public static void main(String[] args) {
    int[][] intervals = {{1, 2}, {3, 5}, {4, 7}, {6, 8}, {9, 10}};
    List<int[]> result = mergeIntervalsOptimized(intervals);
    result.forEach(
        interval ->
            System.out.println(Arrays.toString(interval))); // Output: [1, 2], [3, 8], [9, 10]
  }
}
