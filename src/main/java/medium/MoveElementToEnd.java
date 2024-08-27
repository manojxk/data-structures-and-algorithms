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

package medium;

public class MoveElementToEnd {

  public static int[] moveElementToEnd(int[] array, int toMove) {
    int i = 0;
    int j = array.length - 1;
    while (i < j) {
      while (i < j && array[i] == toMove) j--;
      if (array[i] == toMove) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
    }
    return array;
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
/*O(n) time | O(1) space*/
