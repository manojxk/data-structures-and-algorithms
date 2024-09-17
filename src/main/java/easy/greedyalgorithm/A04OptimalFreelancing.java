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

/*
 * Approach:
 * 1. Sort the jobs by payment in descending order to prioritize jobs with higher payments.
 * 2. Use a greedy approach to schedule each job as late as possible before its deadline.
 * 3. Create an array of 7 boolean values to track which days have already been used for a job.
 * 4. For each job, start at its deadline and check if that day is available. If so, assign the job to that day.
 * 5. Continue until all jobs are processed or there are no more available days.
 *
 * Time Complexity:
 * O(n log n): Sorting the jobs dominates the time complexity.
 *
 * Space Complexity:
 * O(1): Constant extra space is used (boolean array for days).
 */

package easy.greedyalgorithm;

import java.util.*;

public class A04OptimalFreelancing {

  // Inner class to represent a Job with a deadline and payment
  static class Job {
    int deadline;
    int payment;

    // Constructor to initialize a job with a deadline and payment
    Job(int deadline, int payment) {
      this.deadline = deadline;
      this.payment = payment;
    }
  }

  // Function to calculate the maximum profit using a greedy approach
  public static int maxProfitGreedy(List<Job> jobs) {
    // Step 1: Sort jobs by payment in descending order
    jobs.sort((a, b) -> b.payment - a.payment);

    // Step 2: Create a boolean array to track which days are already used (max 7 days)
    boolean[] daysUsed = new boolean[7];
    int totalProfit = 0;

    // Step 3: Iterate through the jobs and try to schedule each one
    for (Job job : jobs) {
      // Try to schedule the job on the latest available day before or on its deadline
      for (int i = Math.min(job.deadline, 7) - 1; i >= 0; i--) {
        if (!daysUsed[i]) {
          // If the day is available, assign the job and add its payment to the total profit
          daysUsed[i] = true;
          totalProfit += job.payment;
          break; // Move to the next job after scheduling
        }
      }
    }

    // Return the total profit earned by scheduling jobs within the 7-day limit
    return totalProfit;
  }

  // Main function to test the Optimal Freelancing implementation
  public static void main(String[] args) {
    // Create a list of jobs with their deadlines and payments
    List<Job> jobs = new ArrayList<>();
    jobs.add(new Job(1, 1)); // Job 0: deadline = 1, payment = 1
    jobs.add(new Job(2, 1)); // Job 1: deadline = 2, payment = 1
    jobs.add(new Job(2, 2)); // Job 2: deadline = 2, payment = 2

    // Calculate and print the maximum profit
    int result = maxProfitGreedy(jobs);
    System.out.println(result); // Output: 3
  }
}
