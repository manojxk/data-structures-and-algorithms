**Problem 1: Course Schedule (Can You Finish All Courses?)**
You have `numCourses` labeled `0` through `numCourses−1`. The array `prerequisites[i] = [ai, bi]` means “to take course `ai`, you must first complete course `bi`.” Return `true` if it’s possible to take all courses (i.e. find a scheduling order that respects every prerequisite), or `false` if a cyclic dependency makes it impossible.

---

### 1.1 Why This Is a Cycle‐Detection Problem

* Think of each course as a node in a directed graph.
* An edge `u → v` means “finish course u, then you can take course v.” Equivalently, “v depends on u.”
* If there is any directed cycle in this graph, at least one course in that cycle can never be started (because each node waits for another on the same cycle).
* If there is no cycle, you can always find some order in which each course’s prerequisites come before the course itself.

Thus, we simply need to check whether the directed graph of courses + prerequisites has a cycle. If **no cycle** exists, return `true`; otherwise, return `false`.

---

### 1.2 Kahn’s Algorithm (BFS‐based Topological Sort)

We’ll use Kahn’s algorithm (also known as the “in‐degree + queue” approach) to detect a cycle. In effect, if we can peel off every node in an order that respects prerequisites, no cycle exists. If we get stuck with some nodes unprocessed, a cycle is present.

1. **Build the Graph & In‐Degree Array**

   * Create an adjacency list `graph` of size `numCourses`, where `graph[u]` is a list of courses that depend on `u`.
   * Also keep an integer array `inDegree[]` of length `numCourses`, where `inDegree[v]` = the number of prerequisites for course `v`.

   ```java
   List<List<Integer>> graph = new ArrayList<>();
   int[] inDegree = new int[numCourses];
   for (int i = 0; i < numCourses; i++) {
     graph.add(new ArrayList<>());
     inDegree[i] = 0;
   }
   for (int[] pre : prerequisites) {
     int course = pre[0];
     int prereq = pre[1];
     graph.get(prereq).add(course);
     inDegree[course]++;
   }
   ```

   * After this, `inDegree[v]` tells us how many incoming edges (prerequisites) `v` has, and `graph[u]` lists all courses we can “unlock” once `u` is completed.

2. **Initialize a Queue with All Zero‐In‐Degree Nodes**

   * Any course with `inDegree[i] == 0` has no prerequisites, so it can be taken first.
   * Enqueue all such courses.

   ```java
   Queue<Integer> queue = new LinkedList<>();
   for (int i = 0; i < numCourses; i++) {
     if (inDegree[i] == 0) {
       queue.offer(i);
     }
   }
   ```

3. **BFS Peel‐off Process**

   * Keep a counter `processedCourses = 0`.
   * While the queue is not empty:

     1. Dequeue `currentCourse`.
     2. Increment `processedCourses`.
     3. For each neighbor `nextCourse` in `graph.get(currentCourse)` (**courses that depended on** `currentCourse`), do:

        * Decrement `inDegree[nextCourse]`.
        * If `inDegree[nextCourse]` becomes 0, enqueue `nextCourse`.

   ```java
   int processedCourses = 0;
   while (!queue.isEmpty()) {
     int curr = queue.poll();
     processedCourses++;
     for (int next : graph.get(curr)) {
       inDegree[next]--;
       if (inDegree[next] == 0) {
         queue.offer(next);
       }
     }
   }
   ```

4. **Check for a Cycle**

   * If `processedCourses == numCourses`, every course was “peeled off” in some valid order. No cycle exists → return `true`.
   * Otherwise, at least one course remained with `inDegree > 0`, meaning we never “unlocked” it because of a cycle → return `false`.

```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
  // 1) Build graph & in-degree
  List<List<Integer>> graph = new ArrayList<>();
  int[] inDegree = new int[numCourses];
  for (int i = 0; i < numCourses; i++) {
    graph.add(new ArrayList<>());
  }
  for (int[] pre : prerequisites) {
    int course = pre[0], prereq = pre[1];
    graph.get(prereq).add(course);
    inDegree[course]++;
  }

  // 2) Enqueue all courses with in-degree 0
  Queue<Integer> queue = new LinkedList<>();
  for (int i = 0; i < numCourses; i++) {
    if (inDegree[i] == 0) {
      queue.offer(i);
    }
  }

  // 3) Process courses in BFS order
  int processedCourses = 0;
  while (!queue.isEmpty()) {
    int curr = queue.poll();
    processedCourses++;
    for (int next : graph.get(curr)) {
      inDegree[next]--;
      if (inDegree[next] == 0) {
        queue.offer(next);
      }
    }
  }

  // 4) If we processed all courses, no cycle → can finish
  return processedCourses == numCourses;
}
```

* **Time Complexity:** O(V + E), where V = `numCourses`, E = number of edges (`prerequisites.length`).

  * Building the adjacency list + in‐degree array: O(E).
  * Initial enqueuing of zero‐in‐degree nodes: O(V).
  * Each course is dequeued exactly once, and each edge is “relaxed” once, so BFS is O(V + E).

* **Space Complexity:** O(V + E).

  * Adjacency list stores O(E) edges plus O(V) lists.
  * In‐degree array is O(V).
  * The queue never holds more than O(V) elements.

---

## 2) Course Schedule II (Find a Valid Course Order)

Now we want not just to know **if** we can finish all courses, but to **return one valid ordering** of courses if it exists. If no valid ordering exists (cycle), return an empty array. This is exactly “topological sort.” We can reuse Kahn’s algorithm but collect the nodes in the order we dequeue them.

---

### 2.1 Algorithm (Again, Kahn’s BFS Topological Sort)

1. **Build graph & in‐degree** just as before:

   ```java
   List<List<Integer>> graph = new ArrayList<>();
   int[] inDegree = new int[numCourses];
   for (int i = 0; i < numCourses; i++) {
     graph.add(new ArrayList<>());
   }
   for (int[] pre : prerequisites) {
     int course = pre[0], prereq = pre[1];
     graph.get(prereq).add(course);
     inDegree[course]++;
   }
   ```

2. **Enqueue all zero‐in‐degree courses** (no prerequisites):

   ```java
   Queue<Integer> queue = new LinkedList<>();
   for (int i = 0; i < numCourses; i++) {
     if (inDegree[i] == 0) {
       queue.offer(i);
     }
   }
   ```

3. **Iterate in BFS order**, but this time store the actual course in an output array `order[]`:

   ```java
   int[] order = new int[numCourses];
   int index = 0;

   while (!queue.isEmpty()) {
     int curr = queue.poll();
     order[index++] = curr; // “Schedule” this course next
     for (int next : graph.get(curr)) {
       inDegree[next]--;
       if (inDegree[next] == 0) {
         queue.offer(next);
       }
     }
   }
   ```

4. **Check if we got all courses** (`index == numCourses`).

   * If yes, `order` is a valid topological ordering → return it.
   * Otherwise, a cycle prevented us from ever enqueuing some nodes → return an empty array.

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
  // 1) Build graph & in-degree
  List<List<Integer>> graph = new ArrayList<>();
  int[] inDegree = new int[numCourses];
  for (int i = 0; i < numCourses; i++) {
    graph.add(new ArrayList<>());
  }
  for (int[] pre : prerequisites) {
    int course = pre[0], prereq = pre[1];
    graph.get(prereq).add(course);
    inDegree[course]++;
  }

  // 2) Enqueue all courses with in-degree 0
  Queue<Integer> queue = new LinkedList<>();
  for (int i = 0; i < numCourses; i++) {
    if (inDegree[i] == 0) {
      queue.offer(i);
    }
  }

  // 3) BFS & collect in topological order
  int[] order = new int[numCourses];
  int index = 0;
  while (!queue.isEmpty()) {
    int curr = queue.poll();
    order[index++] = curr;
    for (int next : graph.get(curr)) {
      inDegree[next]--;
      if (inDegree[next] == 0) {
        queue.offer(next);
      }
    }
  }

  // 4) If we scheduled all courses, return the order; else return empty
  return (index == numCourses) ? order : new int[0];
}
```

* **Time Complexity:** O(V + E), exactly as before.
* **Space Complexity:** O(V + E) for the adjacency list, plus O(V) for `inDegree[]` and `order[]`.

---

## 3) Complete Java File

```java
package cools.graphs;

import java.util.*;

public class A04CourseSchedule {

  /** Returns true if you can finish all courses given the prerequisites, else false. */
  public boolean canFinish(int numCourses, int[][] prerequisites) {
    // Build graph & in-degree array
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      graph.add(new ArrayList<>());
    }
    int[] inDegree = new int[numCourses];
    for (int[] pre : prerequisites) {
      int course = pre[0], prereq = pre[1];
      graph.get(prereq).add(course);
      inDegree[course]++;
    }

    // Enqueue courses with no prerequisites
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (inDegree[i] == 0) {
        queue.offer(i);
      }
    }

    int processedCourses = 0;
    // BFS: “poison” courses one layer at a time
    while (!queue.isEmpty()) {
      int curr = queue.poll();
      processedCourses++;
      for (int next : graph.get(curr)) {
        inDegree[next]--;
        if (inDegree[next] == 0) {
          queue.offer(next);
        }
      }
    }

    // If we posted (processed) all courses, no cycle
    return (processedCourses == numCourses);
  }
}

package cools.graphs;

import java.util.*;

public class A05CourseScheduleII {

  /** Returns one valid ordering of courses, or empty if no ordering exists. */
  public int[] findOrder(int numCourses, int[][] prerequisites) {
    // Build graph & in-degree array
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      graph.add(new ArrayList<>());
    }
    int[] inDegree = new int[numCourses];
    for (int[] pre : prerequisites) {
      int course = pre[0], prereq = pre[1];
      graph.get(prereq).add(course);
      inDegree[course]++;
    }

    // Enqueue courses with no prerequisites
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (inDegree[i] == 0) {
        queue.offer(i);
      }
    }

    int[] order = new int[numCourses];
    int index = 0;
    // BFS & collect topological order
    while (!queue.isEmpty()) {
      int curr = queue.poll();
      order[index++] = curr;
      for (int next : graph.get(curr)) {
        inDegree[next]--;
        if (inDegree[next] == 0) {
          queue.offer(next);
        }
      }
    }

    // If we scheduled all courses, return the order; otherwise, empty
    return (index == numCourses) ? order : new int[0];
  }
}
```

---

### 4) Complexity Summary

* **`canFinish(...)`**

  * **Time:** O(V + E) to build the adjacency list + in‐degree, enqueue zero‐in‐degree nodes, then process each edge once during the BFS.
  * **Space:** O(V + E) to store the graph (adjacency list) and the in‐degree array; plus O(V) for the queue.

* **`findOrder(...)`**

  * **Time:** O(V + E), same reasoning (just storing a topological ordering as we go).
  * **Space:** O(V + E), plus O(V) for the array `order[]`.

Because both tasks reduce to detecting whether there is a cycle in a directed graph, we use the same data structures:

* An adjacency list to track “which courses depend on which prerequisites,”
* An in‐degree array to count how many prerequisites each course still needs,
* A queue to process “currently available” courses whose in‐degree has dropped to zero.

If we manage to process all courses, there is no cycle (so `canFinish` is true), and we can also produce one valid topological ordering (`findOrder` returns it). If we ever finish the BFS early (fewer than `numCourses` processed), a cycle exists, so `canFinish` returns `false` and `findOrder` returns an empty array.
