/*
 * Problem Statement:
 * Implement a MinMaxStack class that supports the following operations:
 *
 * 1. push(int value): Push a value onto the stack.
 * 2. pop(): Remove and return the value at the top of the stack.
 * 3. peek(): Return the value at the top of the stack without removing it.
 * 4. getMin(): Return the minimum value in the stack at any given point in time.
 * 5. getMax(): Return the maximum value in the stack at any given point in time.
 *
 * All operations should be executed in constant time O(1) and should use constant space O(1)
 * for each method.
 *
 * Example Usage:
 *
 * MinMaxStack stack = new MinMaxStack();
 * stack.push(5);        // stack: [5], min: 5, max: 5
 * stack.getMin();       // Output: 5
 * stack.getMax();       // Output: 5
 * stack.peek();         // Output: 5
 * stack.push(7);        // stack: [5, 7], min: 5, max: 7
 * stack.getMin();       // Output: 5
 * stack.getMax();       // Output: 7
 * stack.peek();         // Output: 7
 * stack.push(2);        // stack: [5, 7, 2], min: 2, max: 7
 * stack.getMin();       // Output: 2
 * stack.getMax();       // Output: 7
 * stack.peek();         // Output: 2
 * stack.pop();          // Output: 2, stack: [5, 7], min: 5, max: 7
 * stack.pop();          // Output: 7, stack: [5], min: 5, max: 5
 * stack.getMin();       // Output: 5
 * stack.getMax();       // Output: 5
 * stack.peek();         // Output: 5
 */

/*To efficiently implement a MinMaxStack, we need to keep track of the minimum and maximum values at each level of the stack. This can be achieved using two additional stacks: one for the minimum values and one for the maximum values.

push(int value):

Push the value onto the main stack.
Push the value onto the min stack if it is the smallest value seen so far; otherwise, push the current minimum value.
Push the value onto the max stack if it is the largest value seen so far; otherwise, push the current maximum value.
pop():

Pop the value from the main stack.
        Also, pop from the min and max stacks to keep them in sync with the main stack.
peek():

Return the top value from the main stack.
getMin():

Return the top value from the min stack.
getMax():

Return the top value from the max stack.
Time and Space Complexity:
Time Complexity: O(1) for all operations (push, pop, peek, getMin, getMax).
Space Complexity: O(n) where n is the number of elements in the stack, as we maintain additional stacks for min and max values.*/

package medium;

import java.util.Stack;

public class MinMaxStack {
  private Stack<Integer> stack;
  private Stack<Integer> minStack;
  private Stack<Integer> maxStack;

  // Constructor
  public MinMaxStack() {
    stack = new Stack<>();
    minStack = new Stack<>();
    maxStack = new Stack<>();
  }

  // Push a value onto the stack
  public void push(int value) {
    stack.push(value);
    if (minStack.isEmpty() || value <= minStack.peek()) {
      minStack.push(value);
    } else {
      minStack.push(minStack.peek());
    }

    if (maxStack.isEmpty() || value >= maxStack.peek()) {
      maxStack.push(value);
    } else {
      maxStack.push(maxStack.peek());
    }
  }

  // Pop a value off the stack
  public int pop() {
    minStack.pop();
    maxStack.pop();
    return stack.pop();
  }

  // Peek at the value on top of the stack
  public int peek() {
    return stack.peek();
  }

  // Get the minimum value in the stack
  public int getMin() {
    return minStack.peek();
  }

  // Get the maximum value in the stack
  public int getMax() {
    return maxStack.peek();
  }

  public static void main(String[] args) {
    MinMaxStack stack = new MinMaxStack();
    stack.push(5);
    System.out.println(stack.getMin()); // Output: 5
    System.out.println(stack.getMax()); // Output: 5
    System.out.println(stack.peek()); // Output: 5
    stack.push(7);
    System.out.println(stack.getMin()); // Output: 5
    System.out.println(stack.getMax()); // Output: 7
    System.out.println(stack.peek()); // Output: 7
    stack.push(2);
    System.out.println(stack.getMin()); // Output: 2
    System.out.println(stack.getMax()); // Output: 7
    System.out.println(stack.peek()); // Output: 2
    System.out.println(stack.pop()); // Output: 2
    System.out.println(stack.getMin()); // Output: 5
    System.out.println(stack.getMax()); // Output: 7
    System.out.println(stack.pop()); // Output: 7
    System.out.println(stack.getMin()); // Output: 5
    System.out.println(stack.getMax()); // Output: 5
    System.out.println(stack.peek()); // Output: 5
  }
}
