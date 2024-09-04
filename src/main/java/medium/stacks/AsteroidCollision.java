/*
 * Problem Statement:
 *
 * You're given an array of integers `asteroids`, where each integer represents
 * the size of an asteroid. The sign of the integer indicates the direction in which
 * the asteroid is moving:
 *  - Positive: The asteroid is moving to the right.
 *  - Negative: The asteroid is moving to the left.
 *
 * All asteroids move at the same speed, so asteroids moving in the same direction
 * can never collide with each other.
 *
 * If two asteroids collide:
 * - The smaller asteroid (by absolute value) explodes.
 * - If two colliding asteroids are of the same size, both asteroids explode.
 *
 * Write a function that takes this array of asteroids and returns an array of integers
 * representing the state of the asteroids after all possible collisions have occurred.
 *
 * Example:
 *
 * Sample Input:
 * asteroids = [-3, 5, -8, 6, 7, -4, -7]
 *
 * Sample Output:
 * [-3, -8, 6] // The -3 moves left, having no collisions.
 *             // The 5 moves right, colliding with the -8 and being destroyed by it.
 *             // The 6 never collides with another asteroid.
 *             // The 7 first collides with the -4, destroying it.
 *             // The 7 and the -7 then collide, both being destroyed.
 */
/*Optimized Solution
Approach:
Use a Stack: Utilize a stack to manage the collisions more efficiently.

Iterate through the array and push each asteroid onto the stack.
If an asteroid moves left and the top of the stack is a right-moving asteroid, resolve the collision.
Continue resolving collisions until the stack is stable or the asteroid stops.
Return the Stack: After all asteroids are processed, the stack will contain the final state.

Time Complexity:
O(n): Each asteroid is processed once.
Space Complexity:
O(n): The stack can potentially store all asteroids.*/

package medium.stacks;

import java.util.Stack;

public class AsteroidCollision {
  // Optimized Solution using Stack
  public static int[] asteroidCollisionStack(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();

    for (int asteroid : asteroids) {
      boolean destroyed = false;
      while (!stack.isEmpty() && stack.peek() > 0 && asteroid < 0) {
        int top = stack.peek();
        if (Math.abs(top) < Math.abs(asteroid)) {
          stack.pop();
        } else if (Math.abs(top) == Math.abs(asteroid)) {
          stack.pop();
          destroyed = true;
          break;
        } else {
          destroyed = true;
          break;
        }
      }
      if (!destroyed) {
        stack.push(asteroid);
      }
    }

    int[] result = new int[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) {
      result[i] = stack.pop();
    }
    return result;
  }

  public static void main(String[] args) {
    int[] asteroids = {-3, 5, -8, 6, 7, -4, -7};
    int[] result = asteroidCollisionStack(asteroids);
    for (int val : result) {
      System.out.print(val + " "); // Output: -3 -8 6
    }
  }
}
