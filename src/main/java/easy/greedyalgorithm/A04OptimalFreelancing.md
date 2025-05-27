**Problem Restatement**
You have a list of freelance **jobs**, each takes exactly **1 day**, and each has:

* a **deadline** (the latest day by which it must be finished, between 1 and 7),
* a **payment** (profit you earn if you finish it on or before its deadline).

You have exactly a **7-day window** and can do **at most one job per day**. You don’t have to do every job—your goal is to pick and schedule a subset so that **no job misses its deadline** and your **total payment** is **maximized**.

---

## Naïve (Brute-Force) Idea

You could try **every** subset of jobs and, for each subset, see if there’s a schedule that meets all deadlines, then pick the subset with the highest payment. But there are $2^n$ subsets, and checking each for a valid schedule is another combinatorial step—totally infeasible for even moderate $n$.

---

## Greedy Insight & Strategy

1. **Prioritize high-paying jobs**: If you can only do a limited number of jobs, you want the most profitable ones.
2. **Schedule each job as late as possible** (but no later than its deadline). Why “as late as possible”? Because it leaves earlier days free for other jobs with even tighter (earlier) deadlines.

Putting both together:

1. **Sort** all jobs in **descending** order of `payment`.
2. Maintain a 7-element boolean array `daysUsed[0..6]`, all initially `false`, where `daysUsed[d]` means “I’ve already scheduled a job on day $d+1$.”
3. **Iterate** over jobs in that sorted order:

   * For the current job with deadline `D`, look at days `min(D,7)` down to `1`.
   * Find the **latest** day `d` in that range that’s still free (`daysUsed[d-1] == false`).
   * If you find one, schedule the job there (`daysUsed[d-1] = true`) and add its payment to your total.
   * If you don’t find any free day before the deadline, you **skip** the job.
4. At the end, the accumulated payment is the **maximum** you can achieve in seven days.

---

## Step-by-Step Example

Given jobs:

```
(deadline, payment)
[ (1,1), (2,1), (2,2) ]
```

1. **Sort by payment desc**:
   → `[ (2,2), (1,1), (2,1) ]`

2. **Initialize** `daysUsed = [F,F,F,F,F,F,F]`, `total = 0`.

3. **Job (2,2)**:

   * Deadlines allow days 1 or 2.
   * Check day 2 (index 1): free → schedule it on day 2.
   * Mark `daysUsed[1]=T`, add `2` to `total` (now `2`).

4. **Job (1,1)**:

   * Must be on day 1.
   * Check day 1 (index 0): free → schedule it day 1.
   * Mark `daysUsed[0]=T`, add `1` (total now `3`).

5. **Job (2,1)**:

   * Can go on day 2 or 1.
   * Check day 2 (index 1): **already taken**.
   * Check day 1 (index 0): **already taken**.
   * No slot → skip this job.

**Result:** total payment = **3**.

---

## Java Implementation

```java
package easy.greedyalgorithm;

import java.util.*;

public class A04OptimalFreelancing {

  // Represents a job with a deadline (1..7) and a payment (profit).
  static class Job {
    int deadline;
    int payment;
    Job(int deadline, int payment) {
      this.deadline = deadline;
      this.payment  = payment;
    }
  }

  /**
   * Returns the maximum total payment achievable in a 7-day window.
   * Each job takes exactly one day, and must finish by its deadline.
   *
   * Time:  O(n log n)  — sorting the jobs by payment
   * Space: O(1)       — only a fixed-size array of 7 booleans + a few variables
   */
  public static int maxProfitGreedy(List<Job> jobs) {
    // 1) Sort jobs by descending payment
    jobs.sort((a, b) -> b.payment - a.payment);

    boolean[] daysUsed = new boolean[7];  // daysUsed[i] means day (i+1) is occupied
    int totalProfit = 0;

    // 2) Greedily schedule each job as late as possible
    for (Job job : jobs) {
      // We can only schedule on days 1..job.deadline, but no later than day 7
      int lastDay = Math.min(job.deadline, 7);
      // Try from lastDay down to day 1
      for (int d = lastDay; d >= 1; d--) {
        if (!daysUsed[d - 1]) {
          // Found a free slot
          daysUsed[d - 1] = true;
          totalProfit   += job.payment;
          break;  // move on to next job
        }
      }
    }

    return totalProfit;
  }

  public static void main(String[] args) {
    List<Job> jobs = Arrays.asList(
      new Job(1, 1),
      new Job(2, 1),
      new Job(2, 2)
    );

    int result = maxProfitGreedy(jobs);
    System.out.println(result); // Should print 3
  }
}
```

---

### Complexity Analysis

* **Time Complexity:**

  * **Sorting** the $n$ jobs by payment: **O(n log n)**.
  * **Scheduling loop:** for each job, we check at most 7 days → **O(n)**.
  * **Total:** **O(n log n)**.

* **Space Complexity:**

  * We use a fixed-size array of 7 booleans: **O(1)** extra space.
  * No other data structures grow with $n$.

This greedy approach is optimal for this “one-day-per-job” scheduling with deadlines and profits.
