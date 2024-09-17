package cools.stacks;

/*
 * Problem: Design a Stack that supports getMin() in O(1) time and O(1) extra space.
 *
 * Implement the MinStack class:
 * - `MinStack()`: Initializes the stack object.
 * - `void push(int val)`: Pushes the element val onto the stack.
 * - `void pop()`: Removes the element on the top of the stack.
 * - `int top()`: Gets the top element of the stack.
 * - `int getMin()`: Retrieves the minimum element in the stack.
 *
 * The getMin() function must run in O(1) time and O(1) extra space.
 */

import java.util.Stack;

public class MinStack {

  private Stack<Integer> stack; // The stack to hold the elements
  private int minElement; // To keep track of the current minimum element

  // Initialize the MinStack object
  public MinStack() {
    stack = new Stack<>();
  }

  // Push an element onto the stack
  public void push(int val) {
    if (stack.isEmpty()) {
      stack.push(val);
      minElement = val; // Set the current element as the minElement if the stack is empty
    } else {
      if (val >= minElement) {
        stack.push(val); // Push the value normally if it's greater than or equal to minElement
      } else {
        // Store a modified value in the stack to indicate that this value is the new minimum
        stack.push(2 * val - minElement);
        minElement = val; // Update the minElement to the new minimum value
      }
    }
  }

  // Pop the top element from the stack
  public void pop() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }

    int top = stack.pop();

    if (top < minElement) {
      // If the popped value is less than the current minElement, it means it is an encoded value
      // Restore the previous minElement value
      minElement = 2 * minElement - top;
    }
  }

  // Get the top element of the stack
  public int top() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }

    int top = stack.peek();

    if (top < minElement) {
      // If the top value is encoded (less than minElement), return minElement as it was modified
      return minElement;
    } else {
      // Otherwise, return the actual top value
      return top;
    }
  }

  // Get the minimum element in the stack
  public int getMin() {
    if (stack.isEmpty()) {
      throw new IllegalStateException("Stack is empty");
    }
    return minElement;
  }

  public static void main(String[] args) {
    // Example usage of MinStack
    MinStack minStack = new MinStack();

    minStack.push(3);
    minStack.push(5);
    System.out.println("Min: " + minStack.getMin()); // Output: 3

    minStack.push(2);
    minStack.push(1);
    System.out.println("Min: " + minStack.getMin()); // Output: 1

    minStack.pop();
    System.out.println("Min: " + minStack.getMin()); // Output: 2

    minStack.pop();
    System.out.println("Top: " + minStack.top()); // Output: 5
    System.out.println("Min: " + minStack.getMin()); // Output: 3
  }

  /*
   * Time Complexity:
   * - O(1) for all operations: push, pop, top, and getMin.
   *
   * Space Complexity:
   * - O(1) extra space. We're not using additional space proportional to the number of elements, only storing the current minimum element and using some clever encoding when pushing new elements.
   */
}
