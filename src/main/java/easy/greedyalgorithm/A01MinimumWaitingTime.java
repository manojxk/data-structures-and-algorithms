package easy.greedyalgorithm;

/*
 Problem: Minimum Waiting Time

 You are given an array of positive integers representing the durations of tasks.
 Each task is completed sequentially, and the waiting time of a task is the total time it takes for all previous tasks to complete.
 The goal is to minimize the total waiting time for all tasks.

 Example:

 Input:
   durations = [3, 2, 1]
 Output:
   3

 Explanation:
 - Task with duration 1 finishes first, so its waiting time is 0.
 - Task with duration 2 starts after task 1 finishes, so its waiting time is 1.
 - Task with duration 3 starts after both task 1 and task 2 finish, so its waiting time is 1 + 2 = 3.
 Total waiting time = 0 + 1 + 3 = 4.

 Strategy:
 - Sort the task durations in ascending order.
 - For each task, calculate its waiting time based on the number of tasks that are processed before it.
*/

/*
 Solution Steps:

 1. Sort the array of task durations in ascending order.
 2. For each task, the waiting time is the sum of the durations of all previous tasks.
 3. Accumulate the total waiting time and return it.
*/

import java.util.Arrays;

public class A01MinimumWaitingTime {

  // Function to calculate the minimum total waiting time
  public int calculateMinimumWaitingTime(int[] durations) {
    // Step 1: Sort the durations array to minimize the total waiting time
    Arrays.sort(durations);

    int totalWaitingTime = 0;
    int n = durations.length;

    // Step 2: For each task, add the waiting time contributed by it
    for (int i = 0; i < n; i++) {
      // The number of tasks that will wait for this task to finish
      int tasksRemaining = n - (i + 1);
      totalWaitingTime += tasksRemaining * durations[i];
    }

    return totalWaitingTime;
  }

  // Main function to test the Minimum Waiting Time implementation
  public static void main(String[] args) {
    A01MinimumWaitingTime waitingTimeCalculator = new A01MinimumWaitingTime();

    // Example input
    int[] durations = {3, 2, 1};

    // Calculate and print the minimum total waiting time
    int result = waitingTimeCalculator.calculateMinimumWaitingTime(durations);
    System.out.println("Minimum Waiting Time: " + result); // Output: 3
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of tasks. Sorting the array takes O(n log n) time, and calculating the waiting time requires O(n) operations.

   Space Complexity:
   - O(1), since no additional space is used beyond the input array and a few variables.
  */
}
