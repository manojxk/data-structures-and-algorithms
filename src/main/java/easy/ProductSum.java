/*
 * Problem Statement:
 *
 * You are given a "special" array which is a non-empty array that can contain integers
 * or other "special" arrays. The task is to write a function that returns the product
 * sum of this "special" array.
 *
 * The product sum of a "special" array is defined as the sum of its elements where
 * each element is either an integer or another "special" array. If an element is a
 * "special" array, its sum is multiplied by its level of depth. The depth of an
 * array is determined by how many levels it is nested inside other arrays.
 *
 * Example:
 * - The product sum of [x, y] is calculated as: x + y.
 * - The product sum of [x, [y, z]] is calculated as: x + 2 * (y + z).
 * - The product sum of [x, [y, [z]]] is calculated as: x + 2 * (y + 3z).
 *
 * Sample Input:
 * array = [5, 2, [7, -1], 3, [6, [-13, 8], 4]]
 *
 * Sample Output:
 * 12  // calculated as: 5 + 2 + 2 * (7 - 1) + 3 + 2 * (6 + 3 * (-13 + 8) + 4)
 */
/*Approach:
The brute force solution involves recursively iterating through the array. For each element:

If the element is an integer, add it to the sum.
If the element is another "special" array, calculate its product sum recursively, multiply it by its depth, and add it to the sum.
Time Complexity:
O(n): Where n is the total number of elements across all nested arrays. We visit each element exactly once.
Space Complexity:
O(d): Where d is the maximum depth of the "special" arrays. This space is used by the recursion stack.*/

package easy;

import java.util.*;

public class ProductSum {

  // Function to calculate the product sum of the "special" array
  public static int productSum(List<Object> array) {
    return productSumHelper(array, 1); // Start with depth 1
  }

  // Helper function that recursively calculates the product sum
  private static int productSumHelper(List<Object> array, int depth) {
    int sum = 0;

    // Iterate through each element in the array
    for (Object element : array) {
      // If the element is an integer, add it to the sum
      if (element instanceof Integer) {
        sum += (int) element;
      }
      // If the element is another "special" array, calculate its product sum recursively
      else if (element instanceof List) {
        sum += productSumHelper((List<Object>) element, depth + 1);
      }
    }

    return sum * depth; // Multiply the current sum by the depth
  }

  public static void main(String[] args) {
    // Sample Input
    List<Object> array =
        new ArrayList<>(
            Arrays.asList(
                5, 2, Arrays.asList(7, -1), 3, Arrays.asList(6, Arrays.asList(-13, 8), 4)));

    // Calculate the product sum
    int result = productSum(array);

    // Output the result
    System.out.println(result); // Expected Output: 12
  }
}
