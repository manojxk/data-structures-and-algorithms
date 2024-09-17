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
package cools.stacks;

import java.util.*;

public class NextGreaterElementCircularArray {

  // Brute Force Solution
  /*  The brute force approach involves iterating over each element in the array and checking all subsequent elements to find the next greater element. Since the array is circular, if we reach the end of the array, we continue checking from the start of the array.

  Time Complexity:
  O(n^2): For each element, we might need to look at every other element, leading to a quadratic time complexity.
  Space Complexity:
  O(n): We need an array of the same size as the input to store the results.*/
  public static int[] nextGreaterElementBruteForce(int[] array) {
    int n = array.length;
    int[] result = new int[n];

    for (int i = 0; i < n; i++) {
      result[i] = -1; // Default value if no greater element is found
      for (int j = 1; j < n; j++) {
        int nextIndex = (i + j) % n;
        if (array[nextIndex] > array[i]) {
          result[i] = array[nextIndex];
          break;
        }
      }
    }
    return result;
  }



  public static int[] nextGreaterElementStack(int[] array) {
    int n = array.length;
    int[] result = new int[n];
    Stack<Integer> stack = new Stack<>();

    // Initialize the result array with -1
    Arrays.fill(result, -1);

    // Traverse the array twice to handle the circular nature
    for (int i = 0; i < 2 * n; i++) {
      int currentIndex = i % n;
      while (!stack.isEmpty() && array[stack.peek()] < array[currentIndex]) {
        result[stack.pop()] = array[currentIndex];
      }
      stack.push(currentIndex);
    }

    return result;
  }

  public static void main(String[] args) {
    int[] array = {2, 5, -3, -4, 6, 7, 2};
    int[] result = nextGreaterElementStack(array);
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
