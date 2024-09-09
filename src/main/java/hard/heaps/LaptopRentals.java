package hard.heaps;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LaptopRentals {

  // Function to calculate the minimum number of laptops required
  // Time Complexity: O(n log n) due to sorting and heap operations
  // Space Complexity: O(n) for storing intervals in the heap
  public static int minLaptopsRequired(int[][] times) {
    if (times.length == 0) return 0;

    // Sort intervals based on start times
    Arrays.sort(times, (a, b) -> a[0] - b[0]);

    // Min-heap to track the end times of intervals
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    // Iterate through the intervals
    for (int[] interval : times) {
      int start = interval[0];
      int end = interval[1];

      // If the start time of the current interval is >= the earliest available laptop (top of
      // heap),
      // we can reuse the laptop, so remove the earliest end time.
      if (!minHeap.isEmpty() && start >= minHeap.peek()) {
        minHeap.poll();
      }

      // Add the end time of the current interval to the heap
      minHeap.add(end);
    }

    // The size of the heap will represent the minimum number of laptops required
    return minHeap.size();
  }

  public static void main(String[] args) {
    int[][] times = {
      {0, 2},
      {1, 4},
      {4, 6},
      {0, 4},
      {7, 8},
      {9, 11},
      {3, 10}
    };

    System.out.println("Minimum Laptops Required: " + minLaptopsRequired(times)); // Output: 3
  }
}
/*


Approach:
We can think of each interval as representing the time when a laptop is required. If a new interval starts before any laptop is available, we need a new laptop.
        We'll maintain a min-heap to keep track of the earliest time a laptop becomes available.
For each time interval, we'll:
Add the end time of the current interval to the heap (indicating that a laptop will be available at this time).
If the start time of the current interval is greater than or equal to the minimum available laptop time (smallest value in the heap), we reuse a laptop by removing the earliest available laptop time from the heap.


Explanation:
Sorting:
We first sort the time intervals based on the start time. This ensures that we're considering each interval in the order of when the students start needing the laptops.
Using a Min-Heap:
We maintain a min-heap that stores the end times of all currently active laptop usage intervals.
For each new interval:
If the start time of the current interval is greater than or equal to the minimum end time from the heap, we can reuse that laptop, so we remove the smallest end time from the heap.
Otherwise, a new laptop is needed, so we add the end time of the current interval to the heap.
Final Answer:
After processing all intervals, the size of the heap represents the number of laptops in use at the same time, which corresponds to the minimum number of laptops required.

*/
