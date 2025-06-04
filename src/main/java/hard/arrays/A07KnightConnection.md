**Problem Restatement**
You have an infinite chessboard, and a knight starts at position `(startX, startY)` and wants to reach position `(targetX, targetY)`. A knight moves in “L” shapes—two squares in one direction and one square perpendicular—giving 8 possible moves at any position. Find the **minimum number of moves** needed for the knight to reach the target.

For example:

* From `(0,0)` to `(2,1)`, a knight can do it in exactly 1 move (for instance, `(0,0) → (2,1)`).
* From `(0,0)` to `(5,5)`, the optimal sequence takes 4 moves.

Because the board is infinite, there are no edges or boundaries—only that any square can be visited. Since we need the fewest moves, this is a classic **shortest‐path** problem on an unweighted graph. Each board‐square is a node, and each legal knight jump is an edge of length 1. The standard way to find a shortest path on such an implicit grid is **Breadth‐First Search (BFS)**.

---

## How BFS Solves “Minimum Knight Moves”

1. **Graph View**

   * Every integer coordinate `(x,y)` is a node.
   * From `(x,y)`, you have up to 8 neighbors:

     ```
     (x +  2, y +  1)
     (x +  2, y -  1)
     (x -  2, y +  1)
     (x -  2, y -  1)
     (x +  1, y +  2)
     (x +  1, y -  2)
     (x -  1, y +  2)
     (x -  1, y -  2)
     ```
   * All edges have “cost” 1 (one knight move).

2. **BFS Idea**

   * Start a queue with the starting node `(startX, startY, moves=0)`.
   * Pop a node `(x,y,moves)` in FIFO order.

     * If `(x,y) == (targetX, targetY)`, return `moves` immediately, because BFS explores in increasing order of `moves`.
     * Otherwise, enqueue all unvisited neighbors `(newX, newY, moves+1)`, marking them visited so you never revisit the same square.
   * Continue until the target is found.

3. **Why BFS Is Guaranteed to Be Optimal**

   * BFS explores all positions at distance 0 (just the start), then all positions at distance 1, then all at distance 2, etc.
   * The moment you dequeue `(targetX, targetY, k)`, you know there is no way to reach the target in fewer than `k` moves, because BFS would have visited all nodes at distance `< k` already.

4. **Infinite Board Caveat**

   * Since there is no boundary, in principle there are infinitely many squares. However, you will only ever enqueue squares within a “growing circle” around the start and target. In practice, once you reach the target, BFS stops.
   * We track visited positions in a `Set` so we never revisit and loop infinitely.

---

## Step‐by‐Step Code Explanation

```java
import java.util.*;

public class A07KnightConnection {

  // 8 possible knight moves: (dx, dy)
  private static final int[][] DIRECTIONS = {
    { 2,  1}, { 2, -1}, { -2,  1}, { -2, -1},
    { 1,  2}, { 1, -2}, { -1,  2}, { -1, -2}
  };

  /**
   * Returns the minimum number of knight moves needed to go
   * from (startX, startY) to (targetX, targetY) on an infinite board.
   */
  public int minKnightMoves(int startX, int startY, int targetX, int targetY) {
    // 1) If already at the target, zero moves are needed.
    if (startX == targetX && startY == targetY) {
      return 0;
    }

    // 2) Use a queue to perform BFS. Each entry is [x, y, movesTaken].
    Queue<int[]> queue = new LinkedList<>();
    // 3) Keep track of visited squares so we don't enqueue the same coordinate twice.
    Set<String> visited = new HashSet<>();

    // 4) Enqueue the start position, with 0 moves so far.
    queue.add(new int[]{ startX, startY, 0 });
    // Mark it visited using a string key "x,y"
    visited.add(startX + "," + startY);

    // 5) Standard BFS loop
    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int x = current[0];
      int y = current[1];
      int moves = current[2];

      // 6) Try all 8 next knight moves
      for (int[] dir : DIRECTIONS) {
        int newX = x + dir[0];
        int newY = y + dir[1];

        // If we've reached the target, return moves+1
        if (newX == targetX && newY == targetY) {
          return moves + 1;
        }

        // Otherwise, if we haven't visited (newX,newY) yet, enqueue it
        String key = newX + "," + newY;
        if (!visited.contains(key)) {
          visited.add(key);
          queue.add(new int[]{ newX, newY, moves + 1 });
        }
      }
    }

    // In theory, on an infinite board, you'll always reach the target,
    // so we should never actually get here. Return -1 just to satisfy the signature.
    return -1;
  }

  // Quick test of the method
  public static void main(String[] args) {
    A07KnightConnection solution = new A07KnightConnection();

    // Example 1: (0,0) → (2,1)
    System.out.println(
      "Moves from (0,0) to (2,1): " 
      + solution.minKnightMoves(0, 0, 2, 1)
    ); // Expected output: 1

    // Example 2: (0,0) → (5,5)
    System.out.println(
      "Moves from (0,0) to (5,5): " 
      + solution.minKnightMoves(0, 0, 5, 5)
    ); // Expected output: 4
  }
}
```

---

### Detailed Walkthrough

1. **Edge Case: Start Equals Target**

   ```java
   if (startX == targetX && startY == targetY) {
     return 0;
   }
   ```

   * If the knight already stands on the target square, no moves are needed.

2. **BFS Queue & Visited Set**

   ```java
   Queue<int[]> queue = new LinkedList<>();
   Set<String> visited = new HashSet<>();
   ```

   * The queue stores entries `[x, y, moves]`.
   * The `visited` set stores strings of the form `"x,y"` so we never enqueue the same coordinate twice. Without this, BFS could loop forever on an infinite board.

3. **Initialize BFS with the Start**

   ```java
   queue.add(new int[]{ startX, startY, 0 });
   visited.add(startX + "," + startY);
   ```

   * We enqueue `(startX, startY, 0 moves)`.
   * Mark `(startX, startY)` as visited immediately.

4. **BFS Loop**

   ```java
   while (!queue.isEmpty()) {
     int[] current = queue.poll();
     int x = current[0];
     int y = current[1];
     int moves = current[2];
     // … explore all 8 knight moves …
   }
   ```

   * Repeatedly take the front of the queue. That `current` node is the next square in BFS order, with exactly `moves` knight moves used to get there.

5. **Explore All 8 Directions**

   ```java
   for (int[] dir : DIRECTIONS) {
     int newX = x + dir[0];
     int newY = y + dir[1];
     // …
   }
   ```

   * Each `dir` is one of the 8 possible `(dx, dy)` pairs. We compute the new coordinates `(newX, newY)` and see if that square is the target or if we should enqueue it.

6. **Check If We Reached the Target**

   ```java
   if (newX == targetX && newY == targetY) {
     return moves + 1;
   }
   ```

   * The moment we see `(newX, newY)` equals the target, we know this path uses exactly `moves+1` knight jumps, and BFS guarantees there is no shorter way. So we return `moves+1`.

7. **Otherwise, Enqueue Unvisited**

   ```java
   String key = newX + "," + newY;
   if (!visited.contains(key)) {
     visited.add(key);
     queue.add(new int[]{ newX, newY, moves + 1 });
   }
   ```

   * We skip enqueueing if `(newX,newY)` was already visited.
   * Otherwise, we mark it visited and push `(newX,newY,moves+1)` onto the queue.

8. **Termination**

   * On an infinite board, BFS will eventually reach every coordinate that is at most `k` knight‐moves away from the start, for increasing `k`. As soon as the target is within reach at distance `d`, BFS dequeues it in the iteration `moves = d` and returns `d`.
   * There is no explicit boundary. We only stop when either the queue is empty (which cannot happen unless we never reach the target—but on an infinite grid of integer coordinates, the target is always reachable) or when we find the target.

---

## Complexity Analysis

* **Time Complexity: O(N<sub>explored</sub>)**

  * Each time we dequeue a position `(x,y)`, we explore its 8 neighbors.
  * Because we mark squares as soon as we enqueue them, each unique square is enqueued exactly once, and dequeued exactly once.
  * In the worst case, we visit every square that lies within “distance d” of the start, where d is the minimum number of knight moves to reach `(targetX, targetY)`. If that distance is `D`, then roughly $O(D^2)$ squares are visited, but in terms of the final answer, we simply say “each visited square costs O(1) in BFS,” so total time is proportional to how many squares you must explore before hitting the target. Still, from an algorithmic standpoint, it’s **O(N)** where N = number of visited squares until reaching the target.

* **Space Complexity: O(N<sub>visited</sub>)**

  * We store each visited position in the `visited` set plus in the queue. So if you visit N squares, you use O(N) memory.

Because the board is infinite, we do not index a large 2D array—rather, we only store the squares we actually reach.

---

**That completes the BFS solution for “Minimum Knight Moves on an Infinite Board.”** The BFS guarantees the fewest number of moves, and using a hash‐based visited set ensures we never cycle or revisit any square.
