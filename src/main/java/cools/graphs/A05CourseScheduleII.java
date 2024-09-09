package cools.graphs;

/*
 Problem: Course Schedule II

 You are given a total of numCourses labeled from 0 to numCourses - 1, and an array prerequisites where
 prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.

 Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them.
 If it is impossible to finish all courses, return an empty array.

 Example 1:
 Input: numCourses = 2, prerequisites = [[1, 0]]
 Output: [0, 1]
 Explanation: You must take course 0 before course 1.

 Example 2:
 Input: numCourses = 4, prerequisites = [[1, 0], [2, 0], [3, 1], [3, 2]]
 Output: [0, 2, 1, 3]
 Explanation: A valid course order is [0, 2, 1, 3].

 Example 3:
 Input: numCourses = 1, prerequisites = []
 Output: [0]

 Constraints:
 - The number of courses is in the range [1, 2000].
 - The number of prerequisite pairs is in the range [0, 5000].
 - prerequisites[i].length == 2
 - 0 <= ai, bi < numCourses
 - All the prerequisite pairs are unique.

 Solution Approach:
 1. This is a topological sort problem, where we need to find a valid order of taking courses given the prerequisites.
 2. We can perform a topological sort using Kahn’s Algorithm (BFS) or DFS.
 3. If a cycle is detected, it means it's impossible to complete all courses, so we return an empty array.
*/

import java.util.*;

public class A05CourseScheduleII {

  // Function to return the ordering of courses to finish all courses
  public int[] findOrder(int numCourses, int[][] prerequisites) {
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

    // Step 5: Process the courses and store the ordering
    int[] order = new int[numCourses];
    int index = 0; // Index to store the order of courses

    while (!queue.isEmpty()) {
      int currentCourse = queue.poll();
      order[index++] = currentCourse; // Add the course to the order

      // Reduce the in-degree of all courses dependent on the current course
      for (int nextCourse : graph.get(currentCourse)) {
        inDegree[nextCourse]--;
        if (inDegree[nextCourse] == 0) {
          queue.offer(
              nextCourse); // Add the course to the queue if it has no remaining prerequisites
        }
      }
    }

    // Step 6: If all courses have been processed, return the order. Otherwise, return an empty
    // array.
    return index == numCourses ? order : new int[0];
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A05CourseScheduleII solution = new A05CourseScheduleII();

    // Example 1
    int numCourses1 = 2;
    int[][] prerequisites1 = {{1, 0}};
    System.out.println(
        "Course Order: "
            + Arrays.toString(solution.findOrder(numCourses1, prerequisites1))); // Output: [0, 1]

    // Example 2
    int numCourses2 = 4;
    int[][] prerequisites2 = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
    System.out.println(
        "Course Order: "
            + Arrays.toString(
                solution.findOrder(numCourses2, prerequisites2))); // Output: [0, 2, 1, 3]

    // Example 3
    int numCourses3 = 1;
    int[][] prerequisites3 = {};
    System.out.println(
        "Course Order: "
            + Arrays.toString(solution.findOrder(numCourses3, prerequisites3))); // Output: [0]
  }

  /*
   Time Complexity:
   - O(V + E), where V is the number of courses (nodes) and E is the number of prerequisites (edges). Each node and edge is visited once during the BFS.

   Space Complexity:
   - O(V + E), where V is the number of courses and E is the number of prerequisites. This is the space required to store the graph (adjacency list) and the in-degree array.
  */
}
