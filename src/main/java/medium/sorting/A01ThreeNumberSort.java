package medium.sorting;

/*
 Problem: Three Number Sort

 You are given an array of integers and another array of three distinct integers.
 The integer array contains only elements that are present in the three-number array.
 Write a function that sorts the integer array according to the order defined by the three-number array.

 Example:

 Input:
   array = [1, 0, 0, -1, -1, 0, 1, 1]
   order = [0, 1, -1]
 Output:
   [0, 0, 0, 1, 1, 1, -1, -1]

 Explanation:
 The array is sorted according to the order defined by the `order` array. The elements of the `order` array are
 used to rearrange the input `array` such that all `0`s come first, followed by all `1`s, and finally all `-1`s.
*/

/*
 Solution Steps:

 1. The array needs to be sorted such that the elements are in the same order as they appear in the `order` array.
 2. We can use a three-pass solution to count and place the elements in their respective places:
     - In the first pass, count all occurrences of the first element in `order` and place them at the beginning.
     - In the second pass, count all occurrences of the second element and place them after the first.
     - In the third pass, place the remaining occurrences of the third element.
 3. This approach ensures that the array is sorted with respect to the `order` array in linear time.
*/

public class A01ThreeNumberSort {

  // Function to perform three-number sort
  public void threeNumberSort(int[] array, int[] order) {
    // Count occurrences of the three numbers
    int first = order[0], second = order[1], third = order[2];
    int firstCount = 0, secondCount = 0, thirdCount = 0;

    // First pass: Count occurrences of each number
    for (int num : array) {
      if (num == first) {
        firstCount++;
      } else if (num == second) {
        secondCount++;
      } else if (num == third) {
        thirdCount++;
      }
    }

    // Second pass: Overwrite array based on counts
    // Place all occurrences of the first number
    int index = 0;
    while (firstCount > 0) {
      array[index++] = first;
      firstCount--;
    }

    // Place all occurrences of the second number
    while (secondCount > 0) {
      array[index++] = second;
      secondCount--;
    }

    // Place all occurrences of the third number
    while (thirdCount > 0) {
      array[index++] = third;
      thirdCount--;
    }
  }

  // Main function to test the Three Number Sort implementation
  public static void main(String[] args) {
    A01ThreeNumberSort sorter = new A01ThreeNumberSort();

    // Example array and order
    int[] array = {1, 0, 0, -1, -1, 0, 1, 1};
    int[] order = {0, 1, -1};

    System.out.println("Original Array:");
    for (int num : array) {
      System.out.print(num + " ");
    }

    // Perform Three Number Sort
    sorter.threeNumberSort(array, order);

    System.out.println("\nSorted Array:");
    for (int num : array) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We perform two passes: one to count the elements and another to place them in order.

   Space Complexity:
   - O(1), because we are sorting the array in place without using any additional space other than a few variables.
  */
}
