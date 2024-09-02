/*
 * Problem Statement:
 *
 * You are given an array of at least three integers. Write a function that
 * returns a sorted array containing the three largest integers from the
 * input array without sorting the input array itself.
 *
 * The function should account for duplicate integers if necessary. For
 * instance, if the input array is [10, 5, 9, 10, 12], the function should
 * return [10, 10, 12].
 *
 * Sample Input:
 * array = [141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7]
 *
 * Sample Output:
 * [18, 141, 541]
 */

package easy.searching;

import java.util.Arrays;

public class ThreeLargestNumbers {

  // Function to find the three largest numbers
  public static int[] findThreeLargestNumbers(int[] array) {
    int[] threeLargest = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

    // Iterate through the array to find the three largest numbers
    for (int num : array) {
      updateLargest(threeLargest, num);
    }

    return threeLargest;
  }

  // Helper function to update the three largest numbers array
  private static void updateLargest(int[] threeLargest, int num) {
    if (num > threeLargest[2]) {
      shiftAndUpdate(threeLargest, num, 2);
    } else if (num > threeLargest[1]) {
      shiftAndUpdate(threeLargest, num, 1);
    } else if (num > threeLargest[0]) {
      shiftAndUpdate(threeLargest, num, 0);
    }
  }

  // Helper function to shift and update the largest numbers array
  private static void shiftAndUpdate(int[] array, int num, int idx) {
    for (int i = 0; i < idx; i++) {
      array[i] = array[i + 1];
    }
    array[idx] = num;
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {141, 1, 17, -7, -17, -27, 18, 541, 8, 7, 7};

    // Find the three largest numbers
    int[] result = findThreeLargestNumbers(array);

    // Output the result
    System.out.println(Arrays.toString(result)); // Expected Output: [18, 141, 541]
  }
}
/*
Time Complexity:
O(n): We make a single pass through the array, processing each element exactly once.
Space Complexity:
O(1): We only use a fixed amount of additional space.*/
