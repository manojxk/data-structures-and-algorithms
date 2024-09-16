package medium.arrays;/*
 Problem: Monotonic Array

 An array is monotonic if it is either entirely non-increasing or entirely non-decreasing.

 Given an array of integers, return true if the array is monotonic, or false otherwise.

 Example 1:

 Input: [1, 2, 2, 3]
 Output: true

 Explanation:
 The array is non-decreasing (monotonic).

 Example 2:

 Input: [6, 5, 4, 4]
 Output: true

 Explanation:
 The array is non-increasing (monotonic).

 Example 3:

 Input: [1, 3, 2]
 Output: false

 Explanation:
 The array is neither non-increasing nor non-decreasing.
*/

/*
 Solution Steps:

 1. Check whether the array is either non-increasing or non-decreasing.
 2. Traverse the array once to check if it is non-decreasing:
    a) For each element, if the next element is smaller than the current element, mark it as not non-decreasing.
 3. Traverse the array once to check if it is non-increasing:
    a) For each element, if the next element is larger than the current element, mark it as not non-increasing.
 4. Return true if the array is either non-decreasing or non-increasing, otherwise return false.
*/

public class A04MonotonicArray {

  // Function to check if the array is monotonic
  public static boolean isMonotonic(int[] array) {
    if (array.length <= 1) return true;  // Edge case: single element or empty array

    boolean isNonDecreasing = true;  // Flag to check non-decreasing property
    boolean isNonIncreasing = true;  // Flag to check non-increasing property

    // Step 1: Traverse the array to check both conditions
    for (int i = 1; i < array.length; i++) {
      if (array[i] < array[i - 1]) {
        isNonDecreasing = false;  // It's not non-decreasing
      }
      if (array[i] > array[i - 1]) {
        isNonIncreasing = false;  // It's not non-increasing
      }
    }

    // Step 2: Return true if it's either non-decreasing or non-increasing
    return isNonDecreasing || isNonIncreasing;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array1 = {1, 2, 2, 3};
    System.out.println("Is monotonic: " + isMonotonic(array1));  // Output: true

    int[] array2 = {6, 5, 4, 4};
    System.out.println("Is monotonic: " + isMonotonic(array2));  // Output: true

    int[] array3 = {1, 3, 2};
    System.out.println("Is monotonic: " + isMonotonic(array3));  // Output: false
  }

    /*
     Time Complexity:
     - O(n), where n is the number of elements in the array. We traverse the array once to check the monotonic property.

     Space Complexity:
     - O(1), since we only use a few extra variables to track the properties.
    */
}
