package medium.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class A09MergeIntervals {
  public List<int[]> merge(int[][] intervals) {
    // Step 1: Sort intervals by their start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

    List<int[]> ans = new ArrayList<>();

    int n = intervals.length;
    if (n <= 1) {
      // If there is 1 or no intervals, return as is
      return Arrays.asList(intervals);
    }

    // Step 2: Initialize the answer list with the first interval
    ans.add(intervals[0]);

    // Step 3: Traverse through the sorted intervals and merge them
    for (int i = 1; i < n; i++) {
      // If the current interval overlaps with the last interval in the answer list
      if (ans.get(ans.size() - 1)[1] >= intervals[i][0]) {
        // Merge the intervals by updating the end time
        ans.get(ans.size() - 1)[1] = Math.max(ans.get(ans.size() - 1)[1], intervals[i][1]);
      } else {
        // If no overlap, add the current interval to the answer list
        ans.add(intervals[i]);
      }
    }

    return ans;
  }

  public static void main(String[] args) {
    A09MergeIntervals solution = new A09MergeIntervals();

    int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
    List<int[]> result = solution.merge(intervals);

    // Printing the merged intervals
    System.out.println("Merged Intervals:");
    for (int[] interval : result) {
      System.out.println(Arrays.toString(interval));
    }
  }
}
