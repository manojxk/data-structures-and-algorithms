package medium.arrays;

/*
 Problem: Smallest Difference

 You are given two arrays of integers. Your task is to find the pair of numbers (one from each array) whose absolute difference is the smallest.
 Return the pair of numbers.

 Example:

 Input:
 Array 1: [-1, 5, 10, 20, 28, 3]
 Array 2: [26, 134, 135, 15, 17]

 Output: [28, 26]

 Explanation:
 The pair with the smallest absolute difference is 28 from Array 1 and 26 from Array 2, with a difference of 2.
*/

/*
 Solution Steps:

 1. Sort both arrays.
 2. Use two pointers, one for each array, to traverse the arrays and find the pair with the smallest difference:
    a) Compare the elements pointed to by the two pointers.
    b) If the difference is smaller than the current smallest difference, update the result.
    c) Move the pointer of the smaller element to try and find a closer match.
 3. Continue until one of the arrays is fully traversed.
 4. Return the pair with the smallest difference.
*/

import java.util.Arrays;

public class A02SmallestDifference {

  // Function to find the pair of numbers with the smallest difference
  public static int[] smallestDifference(int[] array1, int[] array2) {
    // Step 1: Sort both arrays
    Arrays.sort(array1);
    Arrays.sort(array2);

    int idx1 = 0; // Pointer for array1
    int idx2 = 0; // Pointer for array2
    int smallestDiff = Integer.MAX_VALUE; // Variable to store the smallest difference
    int currentDiff; // Variable to store the current difference
    int[] result = new int[2]; // Array to store the pair with the smallest difference

    // Step 2: Traverse both arrays using two pointers
    while (idx1 < array1.length && idx2 < array2.length) {
      int firstNum = array1[idx1];
      int secondNum = array2[idx2];

      // Calculate the current difference
      if (firstNum < secondNum) {
        currentDiff = secondNum - firstNum;
        idx1++; // Move the pointer of the smaller element
      } else if (secondNum < firstNum) {
        currentDiff = firstNum - secondNum;
        idx2++; // Move the pointer of the smaller element
      } else {
        // If the numbers are the same, the difference is zero (smallest possible)
        return new int[] {firstNum, secondNum};
      }

      // Step 3: Update the smallest difference if needed
      if (currentDiff < smallestDiff) {
        smallestDiff = currentDiff;
        result = new int[] {firstNum, secondNum};
      }
    }

    // Step 4: Return the pair with the smallest difference
    return result;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array1 = {-1, 5, 10, 20, 28, 3};
    int[] array2 = {26, 134, 135, 15, 17};

    int[] result = smallestDifference(array1, array2);
    System.out.println("Pair with the smallest difference: [" + result[0] + ", " + result[1] + "]");
    // Output: [28, 26]
  }

  /*
   Time Complexity:
   - O(n log n + m log m), where n is the length of array1 and m is the length of array2. This is because we first sort both arrays, and then we traverse both arrays once (O(n + m)).

   Space Complexity:
   - O(1), since we only use a few extra variables (ignoring the space used for sorting, which is typically O(log n) for in-place sorting).
  */
}
