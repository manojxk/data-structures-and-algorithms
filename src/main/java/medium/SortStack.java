/*
 * Problem Statement:
 *
 * Write a function that takes an array of integers representing a stack, recursively
 * sorts the stack in place (i.e., doesn't create a brand new array), and returns it.
 *
 * The array must be treated as a stack, with the end of the array as the top of the stack.
 * Therefore, you're only allowed to:
 *
 * - Pop elements from the top of the stack by removing elements from the end of the array
 *   using the built-in `.pop()` method.
 * - Push elements to the top of the stack by appending elements to the end of the array
 *   using the built-in `.append()` method.
 * - Peek at the element on top of the stack by accessing the last element in the array.
 *
 * You're not allowed to perform any other operations on the input array, including
 * accessing elements (except for the last element), moving elements, etc. You're also
 * not allowed to use any other data structures, and your solution must be recursive.
 *
 * Example:
 *
 * Sample Input:
 * stack = [-5, 2, -2, 4, 3, 1]
 *
 * Sample Output:
 * [-5, -2, 1, 2, 3, 4]
 */
/*Brute Force Solution
Note: A brute force solution is not applicable here due to the strict constraints of the problem. Given that we can't use any other data structures or perform any operations other than pop, append, and peek, the only viable solution is a recursive one.

Optimized Recursive Solution
Approach:
Recursive Sort Function:
Base Case: If the stack is empty or has one element, it's already sorted, so return.
Recursive Case: Pop the top element, recursively sort the remaining stack, and then insert the popped element back into the sorted stack using a helper function.
Insert Function:
Base Case: If the stack is empty or the top of the stack is less than or equal to the element to be inserted, append the element.
Recursive Case: Pop the top element, recursively insert the element into the remaining stack, and then push the popped element back on top.
Time Complexity:
O(n^2): In the worst case, for each element in the stack, we might have to move through all previously sorted elements.
Space Complexity:
O(n): The recursion depth can go as deep as the number of elements in the stack.*/

package medium;

import java.util.List;
import java.util.ArrayList;

public class SortStack { // Recursive function to sort the stack
  public static void sortStack(List<Integer> stack) {
    // Base case: If stack is empty, return
    if (stack.isEmpty()) {
      return;
    }

    // Remove the top element
    int top = stack.remove(stack.size() - 1);

    // Recursively sort the remaining stack
    sortStack(stack);

    // Insert the top element back in sorted order
    insertInSortedOrder(stack, top);
  }

  // Helper function to insert an element into the sorted stack
  private static void insertInSortedOrder(List<Integer> stack, int element) {
    // Base case: If stack is empty or top of stack is less than element
    if (stack.isEmpty() || stack.get(stack.size() - 1) <= element) {
      stack.add(element);
      return;
    }

    // Remove the top element
    int top = stack.remove(stack.size() - 1);

    // Recursively insert the element into the remaining stack
    insertInSortedOrder(stack, element);

    // Push the top element back onto the stack
    stack.add(top);
  }

  public static void main(String[] args) {
    List<Integer> stack = new ArrayList<>();
    stack.add(-5);
    stack.add(2);
    stack.add(-2);
    stack.add(4);
    stack.add(3);
    stack.add(1);

    sortStack(stack);

    System.out.println(stack); // Output: [-5, -2, 1, 2, 3, 4]
  }
}


/*Complete the function below*//*

class GfG {
  public Stack<Integer> sort(Stack<Integer> stack) {
    if (stack.isEmpty()) {
      return stack;
    }

    // Remove the top element
    int top = stack.pop();

    // Sort the remaining stack
    sort(stack);

    // Insert the removed element back into the sorted stack
    sortedInsert(stack, top);
    return stack;
    // add code here.
  }
  private void sortedInsert(Stack<Integer> stack, int element) {
    // Base case: if stack is empty or element is greater than the top element
    if (stack.isEmpty() || element > stack.peek()) {
      stack.push(element);
    } else {
      // Remove all elements greater than element
      int temp = stack.pop();
      sortedInsert(stack, element);
      // Push the removed elements back
      stack.push(temp);
    }
  }
}*/
