package medium.stacks;

/*
 * Problem: Sort Stack (Recursive Solution)
 *
 * You are given a stack of integers. Write a recursive function that sorts the stack in ascending order such that the smallest elements are on the top.
 *
 * Example:
 * Input: [34, 3, 31, 98, 92, 23]
 * Output: [3, 23, 31, 34, 92, 98]
 *
 * Example 2:
 * Input: [-3, 14, 18, -5, 30]
 * Output: [-5, -3, 14, 18, 30]
 */

/*
 * Solution Steps:
 *
 * 1. Create a recursive function that pops all elements from the stack.
 * 2. Once the stack is empty, insert each popped element back in sorted order.
 * 3. Create a helper function to insert an element into the stack at its correct position.
 */

import java.util.Stack;

public class A05SortStack {

  // Function to sort the stack using recursion
  public static void sortStack(Stack<Integer> stack) {
    // Base case: If the stack is empty, return
    if (stack.isEmpty()) {
      return;
    }

    // Step 1: Pop the top element from the stack
    int top = stack.pop();

    // Step 2: Recursively sort the remaining stack
    sortStack(stack);

    // Step 3: Insert the top element back into the sorted stack
    insertInSortedOrder(stack, top);
  }

  // Helper function to insert an element into the sorted stack
  public static void insertInSortedOrder(Stack<Integer> stack, int value) {
    // Base case: If the stack is empty or the top element is smaller than or equal to the value,
    // push the value
    if (stack.isEmpty() || stack.peek() <= value) {
      stack.push(value);
      return;
    }

    // Step 1: Pop the top element from the stack
    int top = stack.pop();

    // Step 2: Recursively insert the value in the sorted stack
    insertInSortedOrder(stack, value);

    // Step 3: Push the popped element back to the stack
    stack.push(top);
  }

  public static void main(String[] args) {
    // Example 1: Create a stack
    Stack<Integer> stack1 = new Stack<>();
    stack1.push(34);
    stack1.push(3);
    stack1.push(31);
    stack1.push(98);
    stack1.push(92);
    stack1.push(23);

    System.out.println("Original Stack: " + stack1);

    // Sort the stack using recursion
    sortStack(stack1);

    System.out.println("Sorted Stack: " + stack1);
    // Output: [3, 23, 31, 34, 92, 98]

    // Example 2: Create another stack
    Stack<Integer> stack2 = new Stack<>();
    stack2.push(-3);
    stack2.push(14);
    stack2.push(18);
    stack2.push(-5);
    stack2.push(30);

    System.out.println("Original Stack: " + stack2);

    // Sort the stack using recursion
    sortStack(stack2);

    System.out.println("Sorted Stack: " + stack2);
    // Output: [-5, -3, 14, 18, 30]
  }

  /*
   * Time Complexity:
   * O(n^2), where n is the number of elements in the stack.
   * In the worst case, for every element, we pop all other elements to find the correct position.
   *
   * Space Complexity:
   * O(n), due to the recursion stack space.
   */
}
