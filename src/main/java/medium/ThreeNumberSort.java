/*
 * Problem Statement:
 * You're given an array of integers and another array of three distinct integers.
 * The first array is guaranteed to only contain integers that are in the second array,
 * and the second array represents a desired order for the integers in the first array.
 * For example, a second array of [x, y, z] represents a desired order of
 * [x, x, ..., x, y, y, ..., y, z, z, ..., z] in the first array.
 *
 * Write a function that sorts the first array according to the desired order in the second array.
 *
 * The function should perform this in place (i.e., it should mutate the input array),
 * and it shouldn't use any auxiliary space (i.e., it should run with constant space: O(1) space).
 *
 * Note:
 * - The desired order won't necessarily be ascending or descending.
 * - The first array won't necessarily contain all three integers found in the second array—it might only contain one or two.
 *
 * Example:
 *
 * Sample Input:
 * array = [1, 0, 0, -1, -1, 0, 1, 1]
 * order = [0, 1, -1]
 *
 * Sample Output:
 * [0, 0, 0, 1, 1, 1, -1, -1]
 */
/*Brute Force Solution
Approach:
Count Occurrences: First, count the number of occurrences of each of the three integers specified in the order array.
Reconstruct Array: Using the counts, overwrite the original array with the integers in the specified order.
Time Complexity:
O(n): Where n is the length of the array. We traverse the array twice—once to count, and once to reconstruct.
Space Complexity:
O(1): Only a constant amount of extra space is used for counting*/
package medium;

public class ThreeNumberSort {

  // Brute Force Solution
  public static void threeNumberSort(int[] array, int[] order) {
    int firstValue = order[0];
    int secondValue = order[1];
    int thirdValue = order[2];

    // Step 1: Count occurrences of each value
    int firstCount = 0, secondCount = 0, thirdCount = 0;
    for (int num : array) {
      if (num == firstValue) {
        firstCount++;
      } else if (num == secondValue) {
        secondCount++;
      } else if (num == thirdValue) {
        thirdCount++;
      }
    }

    // Step 2: Overwrite the original array in the desired order
    int index = 0;
    while (firstCount-- > 0) array[index++] = firstValue;
    while (secondCount-- > 0) array[index++] = secondValue;
    while (thirdCount-- > 0) array[index++] = thirdValue;
  }

  // Optimized Solution: Three-Way Partitioning
  public static void threeNumberSortOP(int[] array, int[] order) {
    int firstValue = order[0];
    int thirdValue = order[2];

    int low = 0, mid = 0, high = array.length - 1;

    while (mid <= high) {
      if (array[mid] == firstValue) {
        swap(array, low, mid);
        low++;
        mid++;
      } else if (array[mid] == thirdValue) {
        swap(array, mid, high);
        high--;
      } else {
        mid++;
      }
    }
  }

  // Helper method to swap elements in the array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void main(String[] args) {
    int[] array = {1, 0, 0, -1, -1, 0, 1, 1};
    int[] order = {0, 1, -1};
    threeNumberSort(array, order);
    System.out.println(java.util.Arrays.toString(array)); // Output: [0, 0, 0, 1, 1, 1, -1, -1]
  }
}

/*Optimized Solution: Three-Way Partitioning (Dutch National Flag Problem)
Approach:
Use Three Pointers: Maintain three pointers:
low starts at the beginning of the array to place elements equal to the first value in the order array.
mid traverses the array.
high starts at the end of the array to place elements equal to the third value in the order array.
Partition the Array:
If array[mid] equals the first value, swap it with array[low] and increment both low and mid.
If array[mid] equals the third value, swap it with array[high] and decrement high.
If array[mid] equals the second value, just increment mid.
Time Complexity:
O(n): Each element is processed at most twice.
Space Complexity:
O(1): The sorting is done in place with constant extra space.*/

// Copyright © 2023 AlgoExpert LLC. All rights reserved.

/*import java.util.*;

class Program {
  // O(n) time | O(1) space - where n is the length of the array
  public int[] threeNumberSort(int[] array, int[] order) {
    int firstValue = order[0];
    int thirdValue = order[2];

    int firstIdx = 0;
    for (int idx = 0; idx < array.length; idx++) {
      if (array[idx] == firstValue) {
        swap(firstIdx, idx, array);
        firstIdx += 1;
      }
    }

    int thirdIdx = array.length - 1;
    for (int idx = array.length - 1; idx >= 0; idx--) {
      if (array[idx] == thirdValue) {
        swap(thirdIdx, idx, array);
        thirdIdx -= 1;
      }
    }

    return array;
  }

  public void swap(int i, int j, int[] array) {
    int temp = array[j];
    array[j] = array[i];
    array[i] = temp;
  }
}*/
