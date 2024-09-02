/*
 * Problem Statement:
 *
 * You are given a non-empty array of positive integers, where each integer represents
 * the amount of time that a specific query takes to execute. Only one query can be
 * executed at a time, but the queries can be executed in any order.
 *
 * A query's *waiting time* is the amount of time it must wait before its execution starts.
 * If a query is executed second, its waiting time is the duration of the first query.
 * If it is executed third, its waiting time is the sum of the durations of the first
 * two queries, and so on.
 *
 * Write a function that returns the minimum total waiting time for all of the queries.
 *
 * For example, if you are given the query durations [1, 4, 5], then the total waiting
 * time if the queries were executed in the order [5, 1, 4] would be:
 *
 * (0) + (5) + (5 + 1) = 11
 *
 * The first query of duration 5 would be executed immediately, so its waiting time
 * would be 0, the second query of duration 1 would have to wait 5 seconds
 * (the duration of the first query) to be executed, and the last query would have
 * to wait the duration of the first two queries before being executed.
 *
 * Your task is to find the order of execution that results in the minimum total
 * waiting time.
 *
 * Note: You are allowed to mutate the input array.
 *
 * Example:
 *
 * Input: queries = [3, 2, 1, 2, 6]
 * Output: 17
 *
 * Explanation:
 * The optimal order is [1, 2, 2, 3, 6].
 * The waiting times are:
 * 0 + 1 + (1 + 2) + (1 + 2 + 2) + (1 + 2 + 2 + 3) = 17
 */

package easy.greedyalgorithm;

import java.util.Arrays;

public class MinimumWaitingTime {
  // Alternate greedy solution to calculate the minimum waiting time
  public static int alternateMinWaitingTime(int[] queries) {
    // Sort the queries to minimize waiting time
    Arrays.sort(queries);
    int totalWaitingTime = 0;

    // Loop through the sorted array and calculate the total waiting time
    for (int i = 0; i < queries.length; i++) {
      // Queries left after the current query
      int queriesLeft = queries.length - (i + 1);
      totalWaitingTime += queries[i] * queriesLeft;
    }

    return totalWaitingTime;
  }

  public static void main(String[] args) {
    int[] queries = {3, 2, 1, 2, 6};
    int result = alternateMinWaitingTime(queries);
    System.out.println(result); // Output: 17
  }
}

/*Time Complexity:
O(n log n): Sorting the array.
Space Complexity:
O(1): The sorting operation is in-place, and no extra space is needed beyond the input array.*/
