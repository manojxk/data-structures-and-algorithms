package medium.stacks;

/*
 * Problem: Next Greater Element
 *
 * You are given an array of integers. For each element in the array, find the next greater element.
 * The next greater element for an element x is the first greater element on the right side of x in the array.
 * If there is no greater element on the right, return -1 for that element.
 *
 * Example 1:
 * Input: [4, 5, 2, 10]
 * Output: [5, 10, 10, -1]
 * Explanation:
 * - For 4, the next greater element is 5.
 * - For 5, the next greater element is 10.
 * - For 2, the next greater element is 10.
 * - For 10, there is no next greater element, so return -1.
 *
 * Example 2:
 * Input: [3, 2, 1]
 * Output: [-1, -1, -1]
 * Explanation: Since there are no elements greater than any of the elements, all return -1.
 */

/*
 * Solution Steps:
 *
 * 1. Use a stack to keep track of the indices of elements for which we haven't yet found the next greater element.
 * 2. Traverse the array from left to right.
 * 3. For each element, if the element is greater than the element corresponding to the index on top of the stack,
 *    pop the stack and record the current element as the next greater element for that index.
 * 4. Push the current index onto the stack and continue to the next element.
 * 5. At the end of the traversal, for any remaining indices in the stack, there is no greater element, so record -1 for them.
 */

import java.util.Stack;

public class A06NextGreaterElement {

  // Function to find the next greater element for each element in the array
  public static int[] nextGreaterElement(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>();

    // Traverse the array from left to right
    for (int i = 0; i < n; i++) {
      // While the current element is greater than the element corresponding to the index on top of
      // the stack
      // it means we have found the next greater element for that index
      while (!stack.isEmpty() && arr[i] > arr[stack.peek()]) {
        int index = stack.pop();
        result[index] = arr[i];
      }
      // Push the current index onto the stack
      stack.push(i);
    }

    // For any remaining indices in the stack, there is no greater element
    while (!stack.isEmpty()) {
      int index = stack.pop();
      result[index] = -1;
    }

    return result;
  }

  public static void main(String[] args) {
    // Example 1:
    int[] arr1 = {4, 5, 2, 10};
    int[] result1 = nextGreaterElement(arr1);
    System.out.print("Next greater elements for [4, 5, 2, 10]: ");
    for (int val : result1) {
      System.out.print(val + " "); // Output: 5 10 10 -1
    }
    System.out.println();

    // Example 2:
    int[] arr2 = {3, 2, 1};
    int[] result2 = nextGreaterElement(arr2);
    System.out.print("Next greater elements for [3, 2, 1]: ");
    for (int val : result2) {
      System.out.print(val + " "); // Output: -1 -1 -1
    }
  }

  /*
   * Time Complexity:
   * O(n), where n is the number of elements in the array. Each element is pushed and popped from the stack once.
   *
   * Space Complexity:
   * O(n), for storing the result and the stack.
   */
}
