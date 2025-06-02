**Problem Restatement**
Design a stack that, in addition to the usual `push`, `pop`, and `peek` operations, can also report the current minimum and maximum values in the stack in **O(1)** time. All operations (`push`, `pop`, `peek`, `getMin`, `getMax`) must run in constant time, and we’re allowed only $O(n)$ extra space for storing these auxiliary values.

---

## Approach

The key idea is to keep, alongside the main stack of values, two auxiliary stacks:

1. **`minStack`**: At each position $i$, `minStack[i]` holds the minimum of all elements in the main stack from index $0$ up to $i$.
2. **`maxStack`**: Similarly, `maxStack[i]` holds the maximum of all elements in the main stack from index $0$ up to $i$.

Whenever we **push** a new value $v$:

* We push $v$ onto the main `stack`.
* To update the current minimum:

  * If `minStack` is empty (i.e.\ we had no elements before), then the new minimum is $v$.
  * Otherwise, compare $v$ to the previous minimum `minStack.peek()`. The new minimum is `Math.min(v, minStack.peek())`. Push that onto `minStack`.
* To update the current maximum:

  * If `maxStack` is empty, the new maximum is $v$.
  * Otherwise, compare $v$ to the previous maximum `maxStack.peek()`. The new maximum is `Math.max(v, maxStack.peek())`. Push that onto `maxStack`.

This way, after the push,

* `stack.peek()` is the top value,
* `minStack.peek()` is the minimum of all values in `stack`,
* `maxStack.peek()` is the maximum of all values in `stack`.

Whenever we **pop**:

* We simply pop from all three stacks (`stack`, `minStack`, `maxStack`). The popped value from `stack` is returned; popping from `minStack` and `maxStack` discards their top, so the new tops correctly reflect the min/max after the pop.

This guarantees that at any point, `minStack.peek()` and `maxStack.peek()` each give us the minimum and maximum of the current contents of `stack`, all in constant time.

---

## Step-by-Step Explanation for a Beginner

1. **Data Structures**
   We maintain three Java `Stack<Integer>` objects:

   * `stack`: holds all the values in the order they were pushed
   * `minStack`: parallel to `stack`, but at each index it holds “the minimum so far”
   * `maxStack`: parallel to `stack`, but at each index it holds “the maximum so far”

2. **Constructor**

   ```java
   public A01MinMaxStack() {
     stack = new Stack<>();
     minStack = new Stack<>();
     maxStack = new Stack<>();
   }
   ```

3. **Push Operation**

   ```java
   public void push(int value) {
     // 1) Push onto the main stack
     stack.push(value);

     // 2) Update minStack:
     if (minStack.isEmpty() || value <= minStack.peek()) {
       // If no minimum yet, or 'value' is new smaller, push 'value'
       minStack.push(value);
     } else {
       // Otherwise, repeat the old minimum so that minStack stays in sync
       minStack.push(minStack.peek());
     }

     // 3) Update maxStack:
     if (maxStack.isEmpty() || value >= maxStack.peek()) {
       maxStack.push(value);
     } else {
       maxStack.push(maxStack.peek());
     }
   }
   ```

   * After these steps, **all three stacks** have the same size.
   * `minStack.peek()` is the minimum of everything in `stack` up to this new top.
   * `maxStack.peek()` is the maximum of everything in `stack` up to this new top.

4. **Pop Operation**

   ```java
   public int pop() {
     // Pop all three stacks (they are guaranteed to have the same number of elements).
     minStack.pop();
     maxStack.pop();
     return stack.pop();
   }
   ```

   * We return the popped value from the main `stack`.
   * Popping from `minStack` and `maxStack` simply discards their top entries, restoring the previous min/max.

5. **Peek Operation**

   ```java
   public int peek() {
     return stack.peek();  // Top of the main stack
   }
   ```

6. **getMin Operation**

   ```java
   public int getMin() {
     return minStack.peek();  // Top of minStack is the current minimum
   }
   ```

7. **getMax Operation**

   ```java
   public int getMax() {
     return maxStack.peek();  // Top of maxStack is the current maximum
   }
   ```

---

## Complete Java Code

```java
package medium.stacks;

import java.util.Stack;

/**
 * A stack that supports push, pop, peek, getMin, and getMax in O(1) time.
 */
public class A01MinMaxStack {
  private Stack<Integer> stack;    // Holds all values
  private Stack<Integer> minStack; // Holds the min so far at each depth
  private Stack<Integer> maxStack; // Holds the max so far at each depth

  // Constructor
  public A01MinMaxStack() {
    stack = new Stack<>();
    minStack = new Stack<>();
    maxStack = new Stack<>();
  }

  /**
   * Pushes a new value onto the stack.
   * Updates the minStack and maxStack to maintain O(1) min/max retrieval.
   */
  public void push(int value) {
    // 1) Push onto main stack
    stack.push(value);

    // 2) Maintain minStack
    if (minStack.isEmpty() || value <= minStack.peek()) {
      minStack.push(value);
    } else {
      // Re‐push the old minimum so minStack size = stack size
      minStack.push(minStack.peek());
    }

    // 3) Maintain maxStack
    if (maxStack.isEmpty() || value >= maxStack.peek()) {
      maxStack.push(value);
    } else {
      maxStack.push(maxStack.peek());
    }
  }

  /**
   * Pops the top value from the stack and returns it.
   * Also pops from minStack and maxStack to stay in sync.
   */
  public int pop() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("Cannot pop from an empty stack.");
    }
    minStack.pop();
    maxStack.pop();
    return stack.pop();
  }

  /**
   * Returns (without removing) the top value of the stack.
   */
  public int peek() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("Cannot peek on an empty stack.");
    }
    return stack.peek();
  }

  /**
   * Returns the minimum element currently in the stack.
   */
  public int getMin() {
    if (minStack.isEmpty()) {
      throw new IllegalStateException("Stack is empty; no minimum.");
    }
    return minStack.peek();
  }

  /**
   * Returns the maximum element currently in the stack.
   */
  public int getMax() {
    if (maxStack.isEmpty()) {
      throw new IllegalStateException("Stack is empty; no maximum.");
    }
    return maxStack.peek();
  }

  // --- Example / Test ---
  public static void main(String[] args) {
    A01MinMaxStack stack = new A01MinMaxStack();

    stack.push(5);
    System.out.println("Min: " + stack.getMin()); // 5
    System.out.println("Max: " + stack.getMax()); // 5
    System.out.println("Peek: " + stack.peek());  // 5

    stack.push(7);
    System.out.println("Min: " + stack.getMin()); // 5
    System.out.println("Max: " + stack.getMax()); // 7
    System.out.println("Peek: " + stack.peek());  // 7

    stack.push(2);
    System.out.println("Min: " + stack.getMin()); // 2
    System.out.println("Max: " + stack.getMax()); // 7
    System.out.println("Peek: " + stack.peek());  // 2

    System.out.println("Pop: " + stack.pop());    // pops 2
    System.out.println("Min: " + stack.getMin()); // back to 5
    System.out.println("Max: " + stack.getMax()); // still 7

    System.out.println("Pop: " + stack.pop());    // pops 7
    System.out.println("Min: " + stack.getMin()); // 5
    System.out.println("Max: " + stack.getMax()); // 5

    System.out.println("Peek: " + stack.peek());  // 5
  }
}
```

---

## Complexity Analysis

* **Time Complexity**

  * `push(int value)`: $O(1)$ — We do a constant number of `stack.push()` and `stack.peek()` calls.
  * `pop()`: $O(1)$ — We pop from three stacks in constant time.
  * `peek()`: $O(1)$ — Direct `stack.peek()`.
  * `getMin()`: $O(1)$ — Direct `minStack.peek()`.
  * `getMax()`: $O(1)$ — Direct `maxStack.peek()`.

* **Space Complexity**

  * We use three stacks of the same size in the worst case. If the main `stack` has $n$ elements, then `minStack` and `maxStack` each also have $n$ elements, so total extra space is $O(n)$. In big‐O terms, that is still linear space, but each method call itself uses only $O(1)$ additional work.

All operations run in **amortized constant time** and maintain **constant additional work** per operation, fulfilling the requirement of a MinMaxStack with $O(1)$ time per operation and $O(n)$ total auxiliary space.
