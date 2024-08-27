/*
 * Problem Statement:
 *
 * Write a function that takes in a non-empty array of integers and returns an
 * array of the same length, where each element in the output array is equal to
 * the product of every other number in the input array.
 *
 * In other words, the value at output[i] is equal to the product of every number
 * in the input array other than input[i].
 *
 * Note that you're expected to solve this problem without using division.
 *
 * Sample Input:
 * array = [5, 1, 4, 2]
 *
 * Sample Output:
 * [8, 40, 10, 20]
 * // 8 is equal to 1 x 4 x 2
 * // 40 is equal to 5 x 4 x 2
 * // 10 is equal to 5 x 1 x 2
 * // 20 is equal to 5 x 1 x 4
 */
/*Approach:
Prefix and Suffix Products:

Prefix Product Array: Compute the product of all elements before each index.
Suffix Product Array: Compute the product of all elements after each index.
Output Array: For each index, the value is the product of the corresponding values in the prefix and suffix arrays.
        Steps:

Traverse the input array from left to right to fill the prefix product array.
Traverse the input array from right to left to fill the suffix product array.
Compute the final output by multiplying the corresponding values from the prefix and suffix arrays.
Time Complexity:
O(n): Each element is processed a constant number of times.
Space Complexity:
O(n): Space is used to store the prefix and suffix arrays.*/

package medium;

public class ArrayOfProducts {

  public static int[] arrayOfProducts(int[] array) {
    int length = array.length;
    int[] output = new int[length];
    int[] prefixProducts = new int[length];
    int[] suffixProducts = new int[length];

    // Compute prefix products
    prefixProducts[0] = 1;
    for (int i = 1; i < length; i++) {
      prefixProducts[i] = prefixProducts[i - 1] * array[i - 1];
    }

    // Compute suffix products
    suffixProducts[length - 1] = 1;
    for (int i = length - 2; i >= 0; i--) {
      suffixProducts[i] = suffixProducts[i + 1] * array[i + 1];
    }

    // Compute final output array
    for (int i = 0; i < length; i++) {
      output[i] = prefixProducts[i] * suffixProducts[i];
    }

    return output;
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {5, 1, 4, 2};

    // Compute the product array
    int[] result = arrayOfProducts(array);

    // Print the result
    System.out.println(java.util.Arrays.toString(result)); // Expected Output: [8, 40, 10, 20]
  }
}
