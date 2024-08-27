/*
 * Problem Statement:
 *
 * You are given two non-empty arrays of integers. Write a function to find the
 * pair of numbers (one from each array) whose absolute difference is closest to
 * zero. The function should return an array containing these two numbers, with
 * the number from the first array in the first position.
 *
 * The absolute difference of two integers is the distance between them on the
 * real number line. For example, the absolute difference of -5 and 5 is 10,
 * and the absolute difference of -5 and -4 is 1.
 *
 * Note: There will be only one pair of numbers with the smallest difference.
 *
 * Sample Input:
 * arrayOne = [-1, 5, 10, 20, 28, 3]
 * arrayTwo = [26, 134, 135, 15, 17]
 *
 * Sample Output:
 * [28, 26]
 *


O(n log n + m log m) - time
* space - O(1)
 */


package medium;

import java.util.Arrays;

public class SmallestDifference {

  public static int[] findClosestDifferencePair(int[] arrayOne, int[] arrayTwo) {
    // Sort both arrays
    Arrays.sort(arrayOne);
    Arrays.sort(arrayTwo);

    // Initialize pointers for both arrays
    int i = 0, j = 0;
    int minDifference = Integer.MAX_VALUE;
    int[] closestPair = new int[2];

    // Traverse both arrays to find the closest pair
    while (i < arrayOne.length && j < arrayTwo.length) {
      int num1 = arrayOne[i];
      int num2 = arrayTwo[j];
      int currentDifference = Math.abs(num1 - num2);

      // Check if the current pair is closer than the previously found pair
      if (currentDifference < minDifference) {
        minDifference = currentDifference;
        closestPair[0] = num1;
        closestPair[1] = num2;
      }

      // Move the pointer in the array with the smaller element
      if (num1 < num2) {
        i++;
      } else {
        j++;
      }
    }

    return closestPair;
  }

  public static void main(String[] args) {
    // Sample Input
    int[] arrayOne = {-1, 5, 10, 20, 28, 3};
    int[] arrayTwo = {26, 134, 135, 15, 17};

    // Find and print the pair with the closest difference
    int[] result = findClosestDifferencePair(arrayOne, arrayTwo);
    System.out.println(Arrays.toString(result)); // Expected Output: [28, 26]
  }
}
