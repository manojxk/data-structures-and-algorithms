package medium;/*
To determine if a directed graph contains a cycle, we can utilize Depth-First Search (DFS). The approach involves tracking the state of each node to detect any back edges, which indicate the presence of a cycle.

Approach
Use DFS for Cycle Detection:

        During DFS, each node can be in one of three states:
Not Visited: The node has not been visited yet.
        Visiting: The node is currently in the DFS call stack.
        Visited: The node and all its descendants have been fully explored.
Detect Back Edges:

A back edge is an edge that points back to a node that is currently in the DFS stack (i.e., in the "Visiting" state). This indicates a cycle.
DFS Execution:

Start a DFS from every node that hasn’t been visited yet.
If during the DFS you encounter a node that is already in the "Visiting" state, a cycle is detected.*/
import java.util.List;

public class CycleInGraph {

  public static boolean containsCycle(List<List<Integer>> edges) {
    int numNodes = edges.size();
    boolean[] visited = new boolean[numNodes];
    boolean[] inStack = new boolean[numNodes];

    for (int node = 0; node < numNodes; node++) {
      if (!visited[node]) {
        if (dfsHasCycle(edges, node, visited, inStack)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean dfsHasCycle(
      List<List<Integer>> edges, int node, boolean[] visited, boolean[] inStack) {
    visited[node] = true;
    inStack[node] = true;

    for (int neighbor : edges.get(node)) {
      if (!visited[neighbor]) {
        if (dfsHasCycle(edges, neighbor, visited, inStack)) {
          return true;
        }
      } else if (inStack[neighbor]) {
        return true; // A cycle is detected.
      }
    }

    inStack[node] = false; // Backtrack: mark the node as not being in the current stack.
    return false;
  }

  public static void main(String[] args) {
    List<List<Integer>> edges =
        List.of(List.of(1, 3), List.of(2, 3, 4), List.of(0), List.of(), List.of(2, 5), List.of());

    boolean result = containsCycle(edges);
    System.out.println(result); // Expected output: true
  }
}

/*
Explanation
Initialization:

visited array keeps track of whether a node has been visited.
inStack array tracks nodes that are in the current recursion stack (i.e., those that are "Visiting").
DFS Logic:

If we encounter a node that is already in the recursion stack (inStack[node] == true), it means we've found a back edge, hence a cycle.
After visiting all descendants of a node, we backtrack by marking it as not in the stack (inStack[node] = false).
Result:

If any DFS call finds a cycle, the function immediately returns true.
If no cycles are found after exploring all nodes, the function returns false.
Time and Space Complexity
Time Complexity:
𝑂
        (
                𝑉
+
        𝐸
        )
O(V+E), where
𝑉
V is the number of vertices (nodes) and
𝐸
E is the number of edges. Each node and edge is processed once.

Space Complexity:
𝑂
        (
                𝑉
                )
O(V) due to the recursion stack and the additional arrays (visited and inStack) used for tracking node states.*/
