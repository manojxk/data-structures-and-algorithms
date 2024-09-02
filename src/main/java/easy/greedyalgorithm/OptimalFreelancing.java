/*
 * Problem Statement:
 *
 * You recently started freelance software development and have been offered
 * a variety of job opportunities. Each job has a deadline, which means there
 * is no value in completing the work after the deadline. Additionally, each job
 * has an associated payment representing the profit for completing that job.
 *
 * The objective is to determine the maximum profit that can be obtained in a
 * 7-day period. Each job takes 1 full day to complete, and each job's deadline
 * specifies the number of days left to complete it. For example, if a job has
 * a deadline of 1, then it can only be completed if it is the first job worked
 * on. If a job has a deadline of 2, then it could be started on the first or
 * second day.
 *
 * Note: There is no requirement to complete all of the jobs. Only one job can
 * be worked on at a time, so in some scenarios, it will be impossible to
 * complete all jobs.
 *
 * Example:
 * Input:
 * jobs = [
 *   {"deadline": 1, "payment": 1},
 *   {"deadline": 2, "payment": 1},
 *   {"deadline": 2, "payment": 2}
 * ]
 *
 * Output:
 * 3  // Job 0 would be completed first, followed by job 2. Job 1 is not completed.
 */

/*Approach:
Sort the jobs by payment in descending order.
Iterate through the jobs and attempt to schedule each job as late as possible before its deadline.
Use a greedy approach to ensure that jobs with higher payments are prioritized.
Time Complexity:
O(n log n): Sorting the jobs dominates the time complexity.
Space Complexity:
O(1): Constant extra space is used.*/

package easy.greedyalgorithm;

import java.util.*;

public class OptimalFreelancing {

  // Inner class to represent a Job
  static class Job {
    int deadline;
    int payment;

    Job(int deadline, int payment) {
      this.deadline = deadline;
      this.payment = payment;
    }
  }

  // Function to calculate the maximum profit using a greedy approach
  public static int maxProfitGreedy(List<Job> jobs) {
    // Sort jobs by payment in descending order
    jobs.sort((a, b) -> b.payment - a.payment);

    // Initialize an array to track the occupied days
    boolean[] daysUsed = new boolean[7];
    int totalProfit = 0;

    // Iterate through the jobs and schedule them
    for (Job job : jobs) {
      for (int i = Math.min(job.deadline, 7) - 1; i >= 0; i--) {
        if (!daysUsed[i]) {
          daysUsed[i] = true;
          totalProfit += job.payment;
          break;
        }
      }
    }

    return totalProfit;
  }

  public static void main(String[] args) {
    List<Job> jobs = new ArrayList<>();
    jobs.add(new Job(1, 1));
    jobs.add(new Job(2, 1));
    jobs.add(new Job(2, 2));

    int result = maxProfitGreedy(jobs);
    System.out.println(result); // Output: 3
  }
}
