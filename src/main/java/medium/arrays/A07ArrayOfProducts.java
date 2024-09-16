package medium.arrays;

/*
 Problem: Array Of Products

 Given an array of integers, return an array where each element at index i is the product of all the elements in the original array except the element at index i.

 Example:

 Input: [1, 2, 3, 4]
 Output: [24, 12, 8, 6]

 Explanation:
 - The product of all elements except the first is 2 * 3 * 4 = 24.
 - The product of all elements except the second is 1 * 3 * 4 = 12.
 - The product of all elements except the third is 1 * 2 * 4 = 8.
 - The product of all elements except the fourth is 1 * 2 * 3 = 6.
*/

/*
 Solution Steps:

 1. Create two arrays:
    - "leftProducts" where each element at index i contains the product of all the elements to the left of index i in the original array.
    - "rightProducts" where each element at index i contains the product of all the elements to the right of index i in the original array.
 2. Traverse the original array twice:
    - Once from left to right to fill the "leftProducts".
    - Once from right to left to fill the "rightProducts".
 3. Multiply corresponding elements in "leftProducts" and "rightProducts" to get the final product for each index.
*/

import java.util.Arrays;

public class A07ArrayOfProducts {

  // Function to compute the product of all elements except self
  public static int[] arrayOfProducts(int[] array) {
    int n = array.length;
    int[] leftProducts = new int[n];
    int[] rightProducts = new int[n];
    int[] products = new int[n];

    // Step 1: Fill leftProducts array
    leftProducts[0] = 1; // No elements to the left of the first element
    for (int i = 1; i < n; i++) {
      leftProducts[i] = leftProducts[i - 1] * array[i - 1];
    }

    // Step 2: Fill rightProducts array
    rightProducts[n - 1] = 1; // No elements to the right of the last element
    for (int i = n - 2; i >= 0; i--) {
      rightProducts[i] = rightProducts[i + 1] * array[i + 1];
    }

    // Step 3: Multiply leftProducts and rightProducts to get the final product for each index
    for (int i = 0; i < n; i++) {
      products[i] = leftProducts[i] * rightProducts[i];
    }

    return products;
  }

  public static int[] arrayOfProductsCopilot(int[] array) {
    int[] products = new int[array.length];

    // Calculate the product of all numbers to the left of each number.
    int leftRunningProduct = 1;
    for (int i = 0; i < array.length; i++) {
      products[i] = leftRunningProduct;
      leftRunningProduct *= array[i];
    }

    // Calculate the product of all numbers to the right of each number and multiply it with the
    // product of all numbers to the left of the number.
    int rightRunningProduct = 1;
    for (int i = array.length - 1; i >= 0; i--) {
      products[i] *= rightRunningProduct;
      rightRunningProduct *= array[i];
    }

    return products;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array = {1, 2, 3, 4};
    int[] result = arrayOfProducts(array);

    System.out.println("Array of Products: " + Arrays.toString(result));
    // Output: [24, 12, 8, 6]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array three times (left, right, and final multiplication), so the complexity is linear.

   Space Complexity:
   - O(n), where n is the length of the array. This is due to the extra space used for the leftProducts, rightProducts, and products arrays.
  */
}
