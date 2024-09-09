package hard.arrays;

import java.util.*;

public class KnightConnection {

  /**
   * Determines the minimum number of turns required for one knight to capture another knight on an
   * infinite chessboard.
   *
   * <p>A knight can move in one of eight possible "L" shaped moves: - Two squares in one direction
   * and one square perpendicular - This results in a total of eight possible moves from any
   * position
   *
   * <p>Given two positions of knights on an infinite chessboard, where each knight starts at a
   * given position, this function calculates the minimum number of turns needed for one knight to
   * move to the position occupied by the other knight.
   *
   * <p>Assumptions: - The chessboard is infinite, meaning there are no boundaries. - Each knight
   * moves independently and optimally towards the other knight. - The knights move simultaneously
   * each turn.
   *
   * @param knightA An array representing the position [x, y] of the first knight.
   * @param knightB An array representing the position [x, y] of the second knight.
   * @return The minimum number of turns required for one knight to capture the other. If the
   *     knights cannot capture each other, returns -1.
   *     <p>Example: For knightA = [0, 0] and knightB = [4, 2], the output will be 3. Explanation:
   *     Knight A moves to [2, 1] and Knight B moves to [2, 1] in the first two turns. In the third
   *     turn, Knight B captures Knight A at [2, 1].
   */
  public static int knightConnection(int[] knightA, int[] knightB) {
    // Define possible moves for a knight
    int[][] possibleMoves =
        new int[][] {{-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}};

    // Queue for BFS
    Queue<List<Integer>> queue = new LinkedList<>();
    queue.add(Arrays.asList(knightA[0], knightA[1], 0));

    // Set for tracking visited positions
    Set<String> visited = new HashSet<>();
    visited.add(knightA[0] + "," + knightA[1]);

    while (!queue.isEmpty()) {
      List<Integer> currentPosition = queue.poll();
      int x = currentPosition.get(0);
      int y = currentPosition.get(1);
      int moves = currentPosition.get(2);

      // Check if we have reached the target position
      if (x == knightB[0] && y == knightB[1]) {
        return moves;
      }

      // Explore all possible knight moves
      for (int[] possibleMove : possibleMoves) {
        int newX = x + possibleMove[0];
        int newY = y + possibleMove[1];
        String positionString = newX + "," + newY;

        // Continue BFS if the position has not been visited
        if (!visited.contains(positionString)) {
          queue.add(Arrays.asList(newX, newY, moves + 1));
          visited.add(positionString);
        }
      }
    }

    // Return -1 if no connection is possible
    return -1;
  }

  public static void main(String[] args) {

    int[] knightA = {0, 0};
    int[] knightB = {4, 2};
    System.out.println(knightConnection(knightA, knightB)); // Output: 3
  }
}
/*
Explanation
Approach:
Initialize BFS:

Start BFS from knightA's position, adding it to the queue along with an initial move count of 0.
Use a Set to track visited positions to avoid reprocessing the same position.
BFS Traversal:

Dequeue a position from the queue. If it matches knightB's position, return the number of moves taken.
For each possible knight move, compute the new position.
If the new position has not been visited, add it to the queue with an incremented move count and mark it as visited.
        Termination:

If the queue becomes empty and the target position hasn’t been reached, return -1 to indicate that no path was found.
Time Complexity:
O(n * m): Each position (node) is processed once, where n and m are the horizontal and vertical distances between the knights. This depends on the number of valid positions a knight can reach.
Space Complexity:
O(n * m): Space for the queue and the visited set, which stores all positions visited during the BFS traversal.
Key Points:
Visited Positions: The use of a Set ensures we do not reprocess positions and thus avoids infinite loops.
Queue Structure: The queue holds positions along with the number of moves taken to reach those positions, enabling the BFS to process each level correctly.
Edge Cases: If the starting position is the same as the target position, it directly returns 0 moves.
This BFS approach efficiently determines the minimum number of moves required for one knight to capture the other, ensuring that both knights are considered in their moves.*/
