package hard.famousalgorithms;

import java.util.*;

public class JobScheduling {

  public static List<Integer> topologicalSort(List<Integer> jobs, List<int[]> deps) {
    // Create the graph and the in-degree map
    Map<Integer, List<Integer>> graph = new HashMap<>();
    Map<Integer, Integer> inDegree = new HashMap<>();

    // Initialize the graph and in-degree map
    for (int job : jobs) {
      graph.put(job, new ArrayList<>());
      inDegree.put(job, 0);
    }

    // Build the graph and populate the in-degree map
    for (int[] dep : deps) {
      int prereq = dep[0];
      int job = dep[1];
      graph.get(prereq).add(job);
      inDegree.put(job, inDegree.get(job) + 1);
    }

    // Queue for jobs with 0 in-degree
    Queue<Integer> zeroInDegreeQueue = new LinkedList<>();
    for (int job : inDegree.keySet()) {
      if (inDegree.get(job) == 0) {
        zeroInDegreeQueue.add(job);
      }
    }

    // List to store the topological order
    List<Integer> topologicalOrder = new ArrayList<>();

    // Process the jobs in topological order
    while (!zeroInDegreeQueue.isEmpty()) {
      int currentJob = zeroInDegreeQueue.poll();
      topologicalOrder.add(currentJob);

      // Reduce the in-degree of the neighboring jobs
      for (int neighbor : graph.get(currentJob)) {
        inDegree.put(neighbor, inDegree.get(neighbor) + (-1));
        if (inDegree.get(neighbor) == 0) {
          zeroInDegreeQueue.add(neighbor);
        }
      }
    }

    // If all jobs are not processed, return an empty list (cycle exists)
    if (topologicalOrder.size() == jobs.size()) {
      return topologicalOrder;
    } else {
      return new ArrayList<>(); // Return empty array if no valid order exists
    }
  }

  public static void main(String[] args) {
    // Sample input
    List<Integer> jobs = Arrays.asList(1, 2, 3, 4);
    List<int[]> deps =
        Arrays.asList(
            new int[] {1, 2},
            new int[] {1, 3},
            new int[] {3, 2},
            new int[] {4, 2},
            new int[] {4, 3});

    // Find the valid job order
    List<Integer> jobOrder = topologicalSort(jobs, deps);

    // Output the result
    if (jobOrder.isEmpty()) {
      System.out.println("No valid job order exists.");
    } else {
      System.out.println("Valid job order: " + jobOrder);
    }
  }
}
/*
Explanation
Graph and In-degree Construction:

For each job, we maintain a list of dependent jobs (its neighbors) in the adjacency list graph.
We also keep track of the in-degree (number of prerequisites) of each job in the inDegree map.
Processing Jobs with Zero In-degree:

We start by adding all jobs with zero in-degree (jobs with no prerequisites) to the queue zeroInDegreeQueue.
As we process each job, we reduce the in-degree of its dependent jobs. If any job’s in-degree becomes 0, it means all its prerequisites are satisfied, so we can add it to the queue.
Topological Sort:

The order in which we remove jobs from the queue forms the topological order. If, after processing, all jobs have been visited, then we have found a valid order.
If some jobs remain unprocessed (i.e., they have a non-zero in-degree), it means there’s a cycle, and no valid job order exists.
Time Complexity
Building the graph and in-degree map: O(V + E), where V is the number of jobs and E is the number of dependencies.
Topological sort using Kahn’s algorithm: O(V + E), because each job is processed once, and each dependency is processed once.
Total Time Complexity: O(V + E).
Space Complexity
Graph and in-degree map: O(V + E), since we store all jobs and their dependencies.
Queue and output list: O(V), as we store the jobs in the queue and result list.
Conclusion
This solution ensures that you can efficiently determine a valid order to complete the jobs or detect if it’s impossible due to cyclic dependencies. The use of Kahn's algorithm ensures that the problem is solved optimally with minimal time and space overhead.*/
