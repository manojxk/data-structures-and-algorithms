package medium.greedyalgorithms;

/*
 * Problem Statement:
 *
 * You are given a list of tasks, where each task has a specific duration. You need to assign these tasks to workers in pairs,
 * such that each worker gets exactly two tasks. The goal is to minimize the maximum working time of any worker.
 *
 * You will return a list of pairs, where each pair contains the indices of the two tasks assigned to a worker.
 *
 * Example:
 *
 * Input:
 * tasks = [1, 3, 5, 3, 1, 4]
 *
 * Output:
 * [[0, 2], [4, 5], [1, 3]]
 *
 * Explanation:
 * - Task 0 (duration 1) and Task 2 (duration 5) are assigned to one worker (1 + 5 = 6).
 * - Task 4 (duration 1) and Task 5 (duration 4) are assigned to another worker (1 + 4 = 5).
 * - Task 1 (duration 3) and Task 3 (duration 3) are assigned to another worker (3 + 3 = 6).
 * The maximum working time for each worker is minimized to 6.
 */

/*
 * Approach:
 * 1. Pair the longest-duration task with the shortest-duration task to minimize the maximum working time.
 * 2. Sort the tasks by duration in ascending order.
 * 3. Pair the first task with the last task, the second task with the second-to-last task, and so on.
 * 4. Return the pairs of indices of the tasks.
 *
 * Time Complexity:
 * O(n log n): Sorting the tasks dominates the time complexity.
 *
 * Space Complexity:
 * O(n): We need to store the indices of the tasks after sorting.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A01TaskAssignment {

  // Function to assign tasks to workers in pairs
  public static List<int[]> assignTasks(int[] tasks) {
    // List to store the pairs of task indices assigned to workers
    List<int[]> taskPairs = new ArrayList<>();

    // Step 1: Store the original indices of the tasks
    Integer[] indices = new Integer[tasks.length];
    for (int i = 0; i < tasks.length; i++) {
      indices[i] = i;
    }

    // Step 2: Sort the task indices based on the task durations in ascending order
    Arrays.sort(indices, (a, b) -> tasks[a] - tasks[b]);

    // Step 3: Pair the tasks
    int left = 0; // Pointer to the shortest-duration task
    int right = tasks.length - 1; // Pointer to the longest-duration task
    while (left < right) {
      // Add the pair of tasks (with their original indices) to the result
      taskPairs.add(new int[] {indices[left], indices[right]});
      left++;
      right--;
    }

    return taskPairs;
  }

  // Main function to test the Task Assignment implementation
  public static void main(String[] args) {
    // Example input
    int[] tasks = {1, 3, 5, 3, 1, 4};

    // Assign tasks and print the pairs of indices
    List<int[]> taskPairs = assignTasks(tasks);

    // Output the pairs
    System.out.println("Task Assignments:");
    for (int[] pair : taskPairs) {
      System.out.println(Arrays.toString(pair));
    }
  }

  /*
   Time Complexity:
   O(n log n): Sorting the tasks array takes O(n log n) time, where n is the number of tasks.

   Space Complexity:
   O(n): We store the indices of the tasks in an auxiliary array.
  */
}
