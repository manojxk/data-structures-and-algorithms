/*
 * Problem Statement:
 * Write a function that takes in an array of integers and returns a new array
 * containing, at each index, the next element in the input array that's greater
 * than the element at that index in the input array.
 *
 * Your function should return a new array where outputArray[i] is the next element in the input array
 * that's greater than inputArray[i]. If there's no such next greater element for a particular index,
 * the value at that index in the output array should be -1.
 *
 * Additionally, your function should treat the input array as a circular array. A circular array
 * wraps around itself as if it were connected end-to-end.
 *
 * Example:
 *
 * Sample Input:
 * array = [2, 5, -3, -4, 6, 7, 2]
 *
 * Sample Output:
 * [5, 6, 6, 6, 7, -1, 5]
 */
/*Brute Force Solution
Approach:
Iterate Through Each Element: For each element in the array, search for the next greater element in the subsequent indices.
Handle Circular Array: If the end of the array is reached without finding a greater element, continue searching from the start of the array.
        Return -1 If No Greater Element Found: If no greater element exists, return -1 for that index.
Time Complexity:
O(n^2): For each element, we may need to traverse the entire array.
Space Complexity:
O(n): Space is needed to store the output array.*/
package medium.stacks;
import java.util.*;

public class NextGreaterElementCircularArray {

  // Brute Force Solution
  public static int[] nextGreaterElements(int[] array) {
    int n = array.length;
    int[] result = new int[n];

    for (int i = 0; i < n; i++) {
      result[i] = -1; // Default to -1
      for (int j = 1; j < n; j++) {
        if (array[(i + j) % n] > array[i]) {
          result[i] = array[(i + j) % n];
          break;
        }
      }
    }

    return result;
  }
    // Optimized Solution
    public static int[] nextGreaterElementss(int[] array) {
        int n = array.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Initialize the result array with -1 (for cases where no greater element exists)
        for (int i = 0; i < n; i++) {
            result[i] = -1;
        }

        // Iterate twice over the array to simulate circularity
        for (int i = 0; i < 2 * n; i++) {
            int index = i % n;
            while (!stack.isEmpty() && array[stack.peek()] < array[index]) {
                result[stack.pop()] = array[index];
            }
            if (i < n) {
                stack.push(index);
            }
        }

        return result;
    }

  public static void main(String[] args) {
    int[] array = {2, 5, -3, -4, 6, 7, 2};
    int[] result = nextGreaterElements(array);
    System.out.println(java.util.Arrays.toString(result)); // Output: [5, 6, 6, 6, 7, -1, 5]
  }
}

// Copyright © 2023 AlgoExpert LLC. All rights reserved.

/*import java.util.*;

class Program {
    // O(n) time | O(n) space - where n is the length of the array
    public int[] nextGreaterElement(int[] array) {
        int[] result = new int[array.length];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<Integer>();

        for (int idx = 0; idx < 2 * array.length; idx++) {
            int circularIdx = idx % array.length;

            while (stack.size() > 0 && array[stack.peek()] < array[circularIdx]) {
                int top = stack.pop();
                result[top] = array[circularIdx];
            }

            stack.push(circularIdx);
        }

        return result;
    }
}*/


/*
Optimized Solution: Using Stack
Approach:
Use a Stack: Utilize a stack to keep track of indices of elements for which the next greater element has not been found.
Iterate Twice Over the Array: Treat the array as circular by iterating over it twice. The second pass helps in wrapping around the array.
Maintain Indices: During iteration, if a greater element is found, update the result for all indices in the stack that have a smaller element.
Time Complexity:
O(n): Each element is pushed and popped from the stack at most once.
Space Complexity:
O(n): Space is used for the output array and the stack.*/
