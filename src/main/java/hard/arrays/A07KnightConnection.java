package hard.arrays;/*
 Problem: Minimum Knight Moves on an Infinite Chessboard

 A knight can move in one of eight possible "L" shaped moves on a chessboard:
 - Two squares in one direction and one square perpendicular.
 - This results in a total of eight possible moves from any position.

 Given two positions of knights on an infinite chessboard, where each knight starts at a given position,
 this function calculates the minimum number of turns needed for one knight to move to the position occupied by the other knight.

 Assumptions:
 - The chessboard is infinite, meaning there are no boundaries.
 - The knight moves optimally towards the other knight.

 Example 1:
 Input: startX = 0, startY = 0, targetX = 2, targetY = 1
 Output: 1
 Explanation: The knight can reach the target in one move.

 Example 2:
 Input: startX = 0, startY = 0, targetX = 5, targetY = 5
 Output: 4
 Explanation: The knight can reach the target in 4 moves.

 Solution Approach:
 1. This is a typical shortest-path problem that can be solved using **Breadth-First Search (BFS)**.
 2. From each position, the knight has 8 possible moves. We explore each move, using BFS to calculate the minimum number of moves required to reach the target.
 3. We can represent the board as an infinite grid and track visited positions to avoid cycles and redundant exploration.

*/

import java.util.*;

public class A07KnightConnection {

  // Directions of the 8 possible moves a knight can make
  private static final int[][] DIRECTIONS = {
    {2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
  };

  // Function to calculate the minimum number of turns for one knight to capture the other
  public int minKnightMoves(int startX, int startY, int targetX, int targetY) {
    // BFS queue to store the current position and the number of moves
    Queue<int[]> queue = new LinkedList<>();
    Set<String> visited = new HashSet<>();

    // Start the BFS from the knight's start position
    queue.add(new int[] {startX, startY, 0});
    visited.add(startX + "," + startY); // Mark the start position as visited

    // Perform BFS to find the minimum moves
    while (!queue.isEmpty()) {
      int[] current = queue.poll();
      int x = current[0];
      int y = current[1];
      int moves = current[2];

      // If the current position matches the target, return the number of moves
      if (x == targetX && y == targetY) {
        return moves;
      }

      // Explore all 8 possible knight moves
      for (int[] direction : DIRECTIONS) {
        int newX = x + direction[0];
        int newY = y + direction[1];

        // If this new position has not been visited, add it to the queue
        String key = newX + "," + newY;
        if (!visited.contains(key)) {
          queue.add(new int[] {newX, newY, moves + 1});
          visited.add(key); // Mark this position as visited
        }
      }
    }

    return -1; // This should never be reached since we are on an infinite chessboard
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A07KnightConnection solution = new A07KnightConnection();

    // Example 1: Knight moves from (0, 0) to (2, 1)
    System.out.println(
        "Minimum moves from (0, 0) to (2, 1): " + solution.minKnightMoves(0, 0, 2, 1)); // Output: 1

    // Example 2: Knight moves from (0, 0) to (5, 5)
    System.out.println(
        "Minimum moves from (0, 0) to (5, 5): " + solution.minKnightMoves(0, 0, 5, 5)); // Output: 4
  }

  /*
   Time Complexity:
   - O(N^2), where N is the number of positions explored. This is because each position has 8 possible knight moves,
     and we explore each position exactly once in the BFS.

   Space Complexity:
   - O(N), where N is the number of unique positions visited, due to the queue and the visited set.
  */
}
