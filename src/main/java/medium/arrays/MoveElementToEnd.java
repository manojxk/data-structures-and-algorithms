/*
 * Problem Statement:
 *
 * Given an array of integers and an integer `toMove`, write a function that moves
 * all instances of `toMove` in the array to the end of the array and returns
 * the modified array.
 *
 * The function should perform this operation in place (i.e., it should mutate the
 * input array) and does not need to maintain the order of the other integers.
 *
 * Sample Input:
 * array = [2, 1, 2, 2, 2, 3, 4, 2]
 * toMove = 2
 *
 * Sample Output:
 * [1, 3, 4, 2, 2, 2, 2, 2]
 * Note: The numbers 1, 3, and 4 could be ordered differently.
 */

/*Brute Force Solution
Approach:
The brute force solution involves creating a new array where all elements not equal to toMove are placed at the beginning, followed by all occurrences of toMove at the end. Then we copy this new array back into the original array.

Time Complexity:
O(n): We iterate through the array twice—once to count and move the non-toMove elements, and once to add toMove elements.
Space Complexity:
O(n): A new array is used to store the reordered elements.*/

package medium.arrays;

public class MoveElementToEnd {
  // Brute Force Solution
  public static int[] moveElementToEndBruteForce(int[] array, int toMove) {
    int[] result = new int[array.length];
    int index = 0;

    // Place all non-toMove elements in the result array first
    for (int num : array) {
      if (num != toMove) {
        result[index++] = num;
      }
    }

    // Fill the remaining positions with toMove elements
    while (index < array.length) {
      result[index++] = toMove;
    }

    // Copy the result array back to the original array
    System.arraycopy(result, 0, array, 0, array.length);
    return array;
  }

  // Optimized Solution using In-Place Swap
  public static void moveElementToEnd(int[] array, int toMove) {
    int i = 0;
    int j = array.length - 1;

    while (i < j) {
      while (i < j && array[j] == toMove) {
        j--;
      }
      if (array[i] == toMove) {
        // Swap elements
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
      i++;
    }
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {2, 1, 2, 2, 2, 3, 4, 2};
    int toMove = 2;

    // Move the specified element to the end of the array
    moveElementToEnd(array, toMove);

    // Print the modified array
    System.out.println(
        java.util.Arrays.toString(array)); // Expected Output: [1, 3, 4, 2, 2, 2, 2, 2]
  }
}
/*Optimized Solution: In-Place Swap
Approach:
An optimized solution uses two pointers: one starting from the beginning and the other from the end of the array. The goal is to find an occurrence of toMove near the beginning and swap it with an element near the end that is not toMove.

Time Complexity:
O(n): We iterate through the array once, making this solution linear in time.
Space Complexity:
O(1): The operation is done in place without using extra space.*/
