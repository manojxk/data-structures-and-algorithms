package medium.stacks;

/*
 * Problem: Colliding Asteroids
 *
 * You are given an array `asteroids` representing the size and direction of asteroids in space.
 * Each asteroid is represented by an integer:
 * - A positive integer means the asteroid is moving to the right.
 * - A negative integer means the asteroid is moving to the left.
 *
 * If two asteroids meet, the smaller one (in absolute value) explodes. If both are the same size, both explode.
 * Two asteroids moving in the same direction will never meet.
 * Return an array of the asteroids after all collisions.
 *
 * Example 1:
 * Input: asteroids = [5, 10, -5]
 * Output: [5, 10]
 * Explanation: The 10 and -5 collide, resulting in 10. The 5 and 10 never collide.
 *
 * Example 2:
 * Input: asteroids = [8, -8]
 * Output: []
 * Explanation: The 8 and -8 collide and both explode.
 *
 * Example 3:
 * Input: asteroids = [10, 2, -5]
 * Output: [10]
 * Explanation: The 2 and -5 collide, resulting in -5. The 10 and -5 collide, resulting in 10.
 *
 * Example 4:
 * Input: asteroids = [-2, -1, 1, 2]
 * Output: [-2, -1, 1, 2]
 * Explanation: No collisions occur since the asteroids move in opposite directions.
 */

/*
 * Solution Steps:
 *
 * 1. Use a stack to simulate the collision process.
 * 2. Traverse the asteroid array:
 *    - If the asteroid is moving to the right (positive value), push it onto the stack.
 *    - If the asteroid is moving to the left (negative value), check the top of the stack:
 *      - If the top of the stack is moving to the right (positive value), simulate the collision.
 *      - If the stack is empty or the top is moving left (negative value), push the current asteroid.
 * 3. After processing all asteroids, the stack will contain the final state of all surviving asteroids.
 */

import java.util.Stack;

public class A08CollidingAsteroids {

  // Function to simulate asteroid collisions
  public static int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();

    for (int asteroid : asteroids) {
      boolean destroyed = false;

      // Process asteroids moving left that may collide with those moving right in the stack
      while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
        int top = stack.peek();

        // Compare sizes: if top of the stack is smaller, it explodes
        if (Math.abs(asteroid) > top) {
          stack.pop(); // The asteroid in the stack is destroyed
        }
        // If the top is the same size, both explode
        else if (Math.abs(asteroid) == top) {
          stack.pop(); // Both explode
          destroyed = true; // Mark the current asteroid as destroyed
          break;
        }
        // If the top is larger, the current asteroid is destroyed
        else {
          destroyed = true;
          break;
        }
      }

      // If the asteroid is not destroyed, add it to the stack
      if (!destroyed) {
        stack.push(asteroid);
      }
    }

    // Convert the stack back to an array
    int[] result = new int[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) {
      result[i] = stack.pop();
    }

    return result;
  }

  public static void main(String[] args) {
    // Test Case 1: 5, 10 survive
    int[] asteroids1 = {5, 10, -5};
    int[] result1 = asteroidCollision(asteroids1);
    System.out.print("Result 1: ");
    for (int val : result1) {
      System.out.print(val + " ");
    }
    System.out.println(); // Output: 5 10

    // Test Case 2: No asteroids survive
    int[] asteroids2 = {8, -8};
    int[] result2 = asteroidCollision(asteroids2);
    System.out.print("Result 2: ");
    for (int val : result2) {
      System.out.print(val + " ");
    }
    System.out.println(); // Output: []

    // Test Case 3: Only 10 survives
    int[] asteroids3 = {10, 2, -5};
    int[] result3 = asteroidCollision(asteroids3);
    System.out.print("Result 3: ");
    for (int val : result3) {
      System.out.print(val + " ");
    }
    System.out.println(); // Output: 10

    // Test Case 4: No collisions occur
    int[] asteroids4 = {-2, -1, 1, 2};
    int[] result4 = asteroidCollision(asteroids4);
    System.out.print("Result 4: ");
    for (int val : result4) {
      System.out.print(val + " ");
    }
    System.out.println(); // Output: -2 -1 1 2
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of asteroids. Each asteroid is processed once.
   *
   * Space Complexity:
   * O(n), for the stack used to simulate collisions and store surviving asteroids.
   */
}
