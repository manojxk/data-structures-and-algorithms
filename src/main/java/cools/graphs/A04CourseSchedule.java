package cools.graphs;

/*
 Problem: Course Schedule

 You are given a total of numCourses labeled from 0 to numCourses - 1, and an array prerequisites where
 prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

 Return true if you can finish all the courses. Otherwise, return false.

 Example 1:
 Input: numCourses = 2, prerequisites = [[1,0]]
 Output: true
 Explanation: You can take course 0 first and then course 1.

 Example 2:
 Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 Output: false
 Explanation: There is a cycle, making it impossible to finish all courses.

 Constraints:
 - The number of courses is in the range [1, 2000].
 - The number of prerequisite pairs is in the range [0, 5000].
 - prerequisites[i].length == 2
 - 0 <= ai, bi < numCourses
 - All the prerequisite pairs are unique.

 Solution Approach:
 1. This is a problem of detecting cycles in a directed graph.
 2. The courses can be represented as nodes, and prerequisites as directed edges between nodes.
 3. We can perform a topological sort using Kahn’s Algorithm (BFS) or DFS to detect cycles.
 4. If there is a cycle, it is impossible to finish all courses, otherwise, it is possible.
*/

import java.util.*;

public class A04CourseSchedule {

  // Function to check if you can finish all courses
  public boolean canFinish(int numCourses, int[][] prerequisites) {
    // Step 1: Create an adjacency list to represent the graph
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      graph.add(new ArrayList<>());
    }

    // Step 2: Create an array to track the in-degree (number of prerequisites) for each course
    int[] inDegree = new int[numCourses];

    // Step 3: Build the graph and populate the in-degree array
    for (int[] prerequisite : prerequisites) {
      int course = prerequisite[0];
      int prerequisiteCourse = prerequisite[1];
      graph.get(prerequisiteCourse).add(course); // Add edge from prerequisiteCourse to course
      inDegree[course]++; // Increment in-degree for the course
    }

    // Step 4: Perform BFS using a queue to process courses with no prerequisites (in-degree == 0)
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (inDegree[i] == 0) {
        queue.offer(i); // Add courses with no prerequisites to the queue
      }
    }

    int processedCourses = 0; // Track the number of courses processed

    // Step 5: Process the courses using BFS
    while (!queue.isEmpty()) {
      int currentCourse = queue.poll();
      processedCourses++; // Process the current course

      // Reduce the in-degree of all courses dependent on the current course
      for (int nextCourse : graph.get(currentCourse)) {
        inDegree[nextCourse]--;
        if (inDegree[nextCourse] == 0) {
          queue.offer(
              nextCourse); // Add the course to the queue if it has no remaining prerequisites
        }
      }
    }

    // Step 6: If all courses have been processed, return true. Otherwise, return false.
    return processedCourses == numCourses;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A04CourseSchedule solution = new A04CourseSchedule();

    // Example 1
    int numCourses1 = 2;
    int[][] prerequisites1 = {{1, 0}};
    System.out.println(
        "Can finish all courses? "
            + solution.canFinish(numCourses1, prerequisites1)); // Output: true

    // Example 2
    int numCourses2 = 2;
    int[][] prerequisites2 = {{1, 0}, {0, 1}};
    System.out.println(
        "Can finish all courses? "
            + solution.canFinish(numCourses2, prerequisites2)); // Output: false
  }

  /*
   Time Complexity:
   - O(V + E), where V is the number of courses (nodes) and E is the number of prerequisites (edges). We process each node and edge once during the BFS.

   Space Complexity:
   - O(V + E), where V is the number of courses and E is the number of prerequisites. This is the space required to store the graph (adjacency list) and the in-degree array.
  */
}
