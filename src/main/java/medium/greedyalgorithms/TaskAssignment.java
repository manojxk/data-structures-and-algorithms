/*
 * Problem Statement:
 * You are given an integer `k` representing the number of workers and an array of positive integers representing the durations of tasks that must be completed by the workers.
 * Each worker must complete two unique tasks and can only work on one task at a time.
 * The number of tasks will always be equal to `2k`, ensuring that each worker has exactly two tasks to complete.
 * The tasks are independent and can be completed in any order.
 * The goal is to assign tasks to workers such that the tasks are completed as quickly as possible.
 * The time to complete all tasks is determined by the worker who has the longest total duration of tasks assigned.
 *
 * Your function should return the optimal assignment of tasks to each worker in the form of a list of pairs,
 * where each pair contains the indices of the two tasks assigned to one worker.
 *
 * If multiple optimal assignments exist, any correct assignment will be accepted.
 *
 * Example:
 * Input: k = 3, tasks = [1, 3, 5, 3, 1, 4]
 * Output: [[0, 2], [4, 5], [1, 3]]
 * Explanation:
 * The pairs are (1 + 5 = 6), (1 + 4 = 5), and (3 + 3 = 6). The maximum time taken by a worker is 6.
 */
/*Optimized Solution 1: Sorting and Pairing
Approach:
Sort the tasks by duration.
Pair the shortest task with the longest task, the second shortest with the second longest, and so on.
This minimizes the maximum time taken by a worker, as it balances the workload between the tasks.
Time Complexity:
O(n log n): The complexity is dominated by the sorting step.
Space Complexity:
O(k): The space complexity is linear with respect to the number of workers due to the storage of task pairs.*/

package medium.greedyalgorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskAssignment {

  public static List<int[]> optimalTaskAssignment(int k, int[] tasks) {
    List<int[]> taskPairs = new ArrayList<>();
    int[] sortedIndices = getSortedIndices(tasks);

    for (int i = 0; i < k; i++) {
      int task1 = sortedIndices[i];
      int task2 = sortedIndices[2 * k - 1 - i];
      taskPairs.add(new int[] {task1, task2});
    }

    return taskPairs;
  }

  // Helper function to get the indices of the sorted tasks
  private static int[] getSortedIndices(int[] tasks) {
    Integer[] indices = new Integer[tasks.length];
    for (int i = 0; i < tasks.length; i++) {
      indices[i] = i;
    }

    Arrays.sort(indices, (a, b) -> Integer.compare(tasks[a], tasks[b]));
    return Arrays.stream(indices).mapToInt(Integer::intValue).toArray();
  }

  public static void main(String[] args) {
    int k = 3;
    int[] tasks = {1, 3, 5, 3, 1, 4};
    List<int[]> result = optimalTaskAssignment(k, tasks);

    for (int[] pair : result) {
      System.out.println(Arrays.toString(pair));
    }
    // Output could be: [0, 2], [4, 5], [1, 3] (or any other correct assignment)
  }
}


/*import java.util.*;

class Program {
  // O(n log n) time | O(n) space - where n is the number of tasks
  public ArrayList<ArrayList<Integer>> taskAssignment(
          int k, ArrayList<Integer> tasks
  ) {
    // List to store the pairs of tasks
    ArrayList<ArrayList<Integer>> pairedTasks = new ArrayList<ArrayList<Integer>>();

    // Map to store the indices of each task duration
    HashMap<Integer, ArrayList<Integer>> taskDurationsToIndices = getTaskDurationsToIndices(tasks);

    // Sort the tasks by their duration
    ArrayList<Integer> sortedTasks = new ArrayList<>(tasks);
    Collections.sort(sortedTasks);

    // Pair the tasks by selecting the smallest and largest available tasks
    for (int idx = 0; idx < k; idx++) {
      // Get the duration and index of the first task in the pair
      int task1Duration = sortedTasks.get(idx);
      ArrayList<Integer> indicesWithTask1Duration = taskDurationsToIndices.get(task1Duration);
      int task1Index = indicesWithTask1Duration.remove(indicesWithTask1Duration.size() - 1);

      // Get the duration and index of the second task in the pair
      int task2SortedIndex = tasks.size() - 1 - idx;
      int task2Duration = sortedTasks.get(task2SortedIndex);
      ArrayList<Integer> indicesWithTask2Duration = taskDurationsToIndices.get(task2Duration);
      int task2Index = indicesWithTask2Duration.remove(indicesWithTask2Duration.size() - 1);

      // Add the pair of task indices to the result list
      ArrayList<Integer> pairedTask = new ArrayList<Integer>();
      pairedTask.add(task1Index);
      pairedTask.add(task2Index);
      pairedTasks.add(pairedTask);
    }

    // Return the list of paired tasks
    return pairedTasks;
  }

  // Helper method to map each task duration to its indices in the input list
  private HashMap<Integer, ArrayList<Integer>> getTaskDurationsToIndices(ArrayList<Integer> tasks) {
    HashMap<Integer, ArrayList<Integer>> taskDurationsToIndices = new HashMap<Integer, ArrayList<Integer>>();

    for (int idx = 0; idx < tasks.size(); idx++) {
      int taskDuration = tasks.get(idx);
      if (!taskDurationsToIndices.containsKey(taskDuration)) {
        taskDurationsToIndices.put(taskDuration, new ArrayList<Integer>());
      }
      taskDurationsToIndices.get(taskDuration).add(idx);
    }

    return taskDurationsToIndices;
  }

  public static void main(String[] args) {
    Program program = new Program();
    ArrayList<Integer> tasks = new ArrayList<>(Arrays.asList(1, 3, 5, 3, 1, 4));
    int k = 3;
    ArrayList<ArrayList<Integer>> result = program.taskAssignment(k, tasks);

    // Print the resulting task pairs
    for (ArrayList<Integer> pair : result) {
      System.out.println(pair);
    }
  }
}*/
