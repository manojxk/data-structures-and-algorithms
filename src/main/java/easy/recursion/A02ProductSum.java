package easy.recursion;

/*
 Problem: Product Sum

 You are given a "special" array containing integers and nested arrays (which themselves may contain integers or other nested arrays).
 The product sum of a "special" array is the sum of its elements, where the nested arrays are multiplied by their depth.

 Example 1:

 Input: [1, 2, [3, 4, [5]]]
 Output: 27

 Explanation:
 The depth of [1, 2] is 1, the depth of [3, 4] is 2, and the depth of [5] is 3.
 Product sum = 1 + 2 + 2 * (3 + 4 + 3 * (5)) = 1 + 2 + 2 * (7 + 15) = 27.

 Example 2:

 Input: [1, [2, 3], 4, [5, [6]]]
 Output: 42

 Explanation:
 Product sum = 1 + 1 * (2 + 3) + 4 + 2 * (5 + 2 * (6)) = 1 + 5 + 4 + 10 + 24 = 42.
*/

/*
 Solution Steps:

 1. Initialize a recursive function to handle the special array and its depth.
 2. For each element in the array:
    a. If the element is an integer, add it to the sum.
    b. If the element is a nested array, recursively calculate its product sum and multiply it by its depth.
 3. Return the total sum for the array.
*/

import java.util.*;

public class A02ProductSum {

  // Function to calculate the product sum
  public static int productSum(List<Object> array) {
    // Call the helper function with an initial depth of 1
    return productSumHelper(array, 1);
  }

  // Helper function to calculate the product sum recursively
  private static int productSumHelper(List<Object> array, int depth) {
    int sum = 0;

    // Iterate through each element in the list
    for (Object element : array) {
      // If the element is an instance of List (i.e., it's a nested array)
      if (element instanceof List) {
        // Recursively calculate the product sum of the nested array and multiply by the current
        // depth
        sum += productSumHelper((List<Object>) element, depth + 1);
      } else {
        // If the element is an integer, add it to the sum
        sum += (int) element;
      }
    }

    // Multiply the sum by the current depth and return it
    return sum * depth;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1: Create a special array: [1, 2, [3, 4, [5]]]
    List<Object> array1 = Arrays.asList(1, 2, Arrays.asList(3, 4, Arrays.asList(5)));

    // Example 2: Create another special array: [1, [2, 3], 4, [5, [6]]]
    List<Object> array2 =
        Arrays.asList(1, Arrays.asList(2, 3), 4, Arrays.asList(5, Arrays.asList(6)));

    // Calculate and print the product sum
    System.out.println("Product Sum of array1: " + productSum(array1)); // Output: 27
    System.out.println("Product Sum of array2: " + productSum(array2)); // Output: 42
  }

  /*
   Time Complexity:
   - O(n), where n is the total number of elements in the array, including the elements in all nested arrays.
   - We iterate over every element in the input list, and in the worst case, every element is visited once.

   Space Complexity:
   - O(d), where d is the maximum depth of the nested arrays.
   - The space complexity is determined by the recursion stack, which depends on the depth of the nested arrays.
  */
}
