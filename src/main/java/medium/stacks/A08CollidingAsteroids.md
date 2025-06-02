**Problem Explanation**
You have a list of asteroids, each represented by an integer in an array `asteroids[]`. The absolute value of each integer is the size (mass) of the asteroid. The sign of the integer indicates its direction:

* A **positive** value (e.g. `5`) means the asteroid is moving **right**.
* A **negative** value (e.g. `-5`) means the asteroid is moving **left**.

All asteroids move at the same speed. Whenever two asteroids collide (one moving right meets one moving left), the smaller asteroid (by absolute size) **explodes**—i.e., it disappears. If they are the same size, **both** explode. Asteroids moving in the same direction never collide.

Your job is to simulate all such collisions and return the final list of asteroids that remain (in the same original order they occupy, except for any that have exploded).

> **Example 1**
>
> ```
> asteroids = [5, 10, -5]
> ```
>
> * The asteroid `5` is moving right.
> * The asteroid `10` is moving right.
> * The asteroid `-5` is moving left.
>
> Reading left to right:
>
> 1. `5` moves right → stack = \[5]
> 2. `10` moves right → stack = \[5, 10]
> 3. `-5` moves left → now it collides with the rightmost “moving right” asteroid on the stack, which is `10`.
>
>    * Compare sizes: `|−5| = 5` vs. `10`. Since 5 < 10, the left‐moving asteroid `−5` explodes (disappears).
>    * Result: only `[5, 10]` remain.
>
> Final result: `[5, 10]`.

> **Example 2**
>
> ```
> asteroids = [8, -8]
> ```
>
> * `8` (right), `-8` (left) collide simultaneously: same size → both explode.
>   Final result: `[]` (empty).

> **Example 3**
>
> ```
> asteroids = [10, 2, -5]
> ```
>
> * `10` (right), `2` (right), `-5` (left).
> * First, `2` and `-5` collide: `|−5|=5` vs. `2`: 5 > 2 → `2` explodes, leaving `10` and `-5`.
> * Next, `10` (right) collides with `-5` (left): 10 > 5 → `−5` explodes.
>   Final: `[10]`.

> **Example 4**
>
> ```
> asteroids = [-2, -1, 1, 2]
> ```
>
> * The first two move left, next two move right.
> * No “right-moving” asteroid ever meets a “left-moving” asteroid, because those moving left are already on the left side.
>   Final: `[-2, -1, 1, 2]` (no collisions).

---

## Approach (Using a Stack)

We can simulate these collisions with a stack, processing asteroids one by one from left to right:

1. **Initialize an empty stack** of integers. This stack will represent “live” asteroids that haven’t exploded yet and are still traveling to the right (or have survived collisions).

2. **For each asteroid** `ast` in the input array, do:

   * **Case A:** If `ast > 0` (moving right), it cannot immediately collide with anything on its left side—since collisions only occur when a right-moving asteroid meets a left-moving one on its right. So we simply **push** `ast` onto the stack.
   * **Case B:** If `ast < 0` (moving left), it might collide with one or more right‐moving asteroids currently on the top of the stack. We handle these collisions as follows:

     1. **While** the stack is nonempty **and** the top of the stack is a positive asteroid (`stack.peek() > 0`) **and** there is still a possibility of collision:

        * Let `top = stack.peek()`, a positive asteroid size. Compare `|ast|` with `top`:

          * If `|ast| > top`:

            * The left‐moving asteroid `ast` is bigger. The right‐moving asteroid on the stack (size `top`) **explodes**: pop it from the stack.
            * Continue the while‐loop, because the same `ast` might collide with the next right‐moving asteroid underneath.
          * Else if `|ast| == top`:

            * They are the same size. **Both** explode. Pop `top` from the stack, and mark `ast` as destroyed. Break out of the loop (stop trying to collide this `ast` further).
          * Else (`|ast| < top`):

            * The right‐moving asteroid on the stack is bigger. `ast` explodes (is destroyed). Break out of the loop.

     2. After that while‐loop, if `ast` was **not** marked destroyed (meaning either the stack became empty or the top of the stack is now negative or we never collided), **push** `ast` onto the stack.

        * Pushing a negative asteroid simply means “there’s no right‐moving asteroid blocking it,” so it survives.
        * If the top of the stack is negative (another left‐mover), they never collide—both stay in the stack in order.

3. **At the end**, the stack contains all surviving asteroids (some positive, some negative) in the correct left‐to‐right order, because we only ever pop destroyed asteroids and push survivors.

4. **Convert** the stack to an output array in left‐to‐right order (bottom of the stack is the leftmost asteroid, top is the rightmost).

This simulation is **O(n)** because each asteroid is pushed onto the stack at most once and popped at most once.

---

## Step-by-Step Code

```java
package medium.stacks;

import java.util.Stack;

public class A08CollidingAsteroids {

  /**
   * Simulates asteroid collisions and returns the surviving asteroids.
   *
   * @param asteroids An array of integers, positive = moving right, negative = moving left.
   * @return An array of the asteroids that remain after all collisions.
   */
  public static int[] asteroidCollision(int[] asteroids) {
    Stack<Integer> stack = new Stack<>();

    // Process each asteroid in order
    for (int ast : asteroids) {
      boolean destroyed = false;

      // Only possible collisions happen if 'ast' is moving left (< 0)
      // and there is a right-moving (> 0) asteroid on top of the stack.
      while (!stack.isEmpty() && ast < 0 && stack.peek() > 0) {
        int top = stack.peek();  // a positive asteroid moving right

        if (Math.abs(ast) > top) {
          // The left-moving 'ast' is larger. The right-moving 'top' is destroyed.
          stack.pop();          
          // Continue loop to see if this 'ast' collides with another right-mover below.
        }
        else if (Math.abs(ast) == top) {
          // They are equal size → both explode
          stack.pop();  // remove the right-mover
          destroyed = true;  // mark current 'ast' as destroyed
          break;             // stop checking further collisions for 'ast'
        }
        else {
          // The right-moving asteroid is larger → 'ast' explodes
          destroyed = true;
          break;  // stop checking; 'ast' is gone
        }
      }

      // If 'ast' has not been destroyed in collisions, push it onto the stack
      if (!destroyed) {
        stack.push(ast);
      }
    }

    // Convert stack to result array in correct left-to-right order
    int size = stack.size();
    int[] result = new int[size];
    for (int i = size - 1; i >= 0; i--) {
      result[i] = stack.pop();
    }
    return result;
  }

  public static void main(String[] args) {
    // Test Case 1:  [5, 10, -5] → [5, 10]
    int[] ast1 = {5, 10, -5};
    int[] res1 = asteroidCollision(ast1);
    System.out.print("Result 1: ");
    for (int x : res1) System.out.print(x + " ");
    System.out.println();

    // Test Case 2:  [8, -8] → []
    int[] ast2 = {8, -8};
    int[] res2 = asteroidCollision(ast2);
    System.out.print("Result 2: ");
    for (int x : res2) System.out.print(x + " ");
    System.out.println();

    // Test Case 3:  [10, 2, -5] → [10]
    int[] ast3 = {10, 2, -5};
    int[] res3 = asteroidCollision(ast3);
    System.out.print("Result 3: ");
    for (int x : res3) System.out.print(x + " ");
    System.out.println();

    // Test Case 4:  [-2, -1, 1, 2] → [-2, -1, 1, 2]
    int[] ast4 = {-2, -1, 1, 2};
    int[] res4 = asteroidCollision(ast4);
    System.out.print("Result 4: ");
    for (int x : res4) System.out.print(x + " ");
    System.out.println();
  }
}
```

---

## Detailed Explanation for Beginners

1. **Why a stack?**

   * We only ever collide a **left-moving** asteroid with the most recent right-moving asteroid it “meets.”
   * A stack naturally keeps track of “recent right-moving survivors” on its top.
   * When a new left‐mover arrives, we look at the stack’s top to resolve collisions one at a time.

2. **Collision logic inside the while‐loop**

   * We only enter the loop if:

     * `ast < 0` (current asteroid moves left), and
     * `stack.peek() > 0` (the top of the stack is a right‐moving asteroid).
   * Compare sizes (`|ast|` vs. `stack.peek()`):

     * If `|ast|` is bigger → the right‐mover on the top of the stack explodes (pop it). Keep comparing the same `ast` with the next right‐mover below on the stack, if any.
     * If they are equal → both explode: pop the top, mark `ast` destroyed, break.
     * If `stack.peek()` is bigger → `ast` (the left‐mover) is destroyed, break.

3. **Why do we then push `ast` if it was not destroyed?**

   * If `ast` remains after popping all smaller right‐movers, and either the stack is empty or the top is also a left‐mover, there is no further collision.
   * So `ast` survives—push it on the stack so it can potentially collide with future right‐movers (though actually, future right‐movers will never collide with a left‐mover to their right, so effectively that left‐mover will just sit there).

4. **Result construction**

   * At the end of processing all asteroids, the stack holds exactly the surviving asteroids in left‐to‐right order: the bottom of the stack is the leftmost survivor, and the top is the rightmost.
   * We pop them off into an array from the end to the front, recreating a left‐to‐right order.

---

## Complexity Analysis

* **Time Complexity: O(n)**

  * We loop over `n` asteroids once.
  * Inside that loop, we may pop from the stack multiple times—but each asteroid can be popped at most once (either because it was destroyed or because a bigger left‐mover “empties” smaller right‐movers beneath). Each asteroid is also pushed at most once.
  * Hence, the total number of pushes + pops is $≤ 2n$. All operations are O(1). So overall O(n).

* **Space Complexity: O(n)**

  * In the worst case (no collisions at all), we push all `n` asteroids onto the stack.
  * The extra array holding the final survivors also uses at most `n` slots.
  * Thus, auxiliary space is O(n).

This stack‐based simulation exactly reproduces the collision rules and efficiently yields the final set of surviving asteroids.
