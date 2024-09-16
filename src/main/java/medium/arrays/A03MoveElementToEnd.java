package medium.arrays;

/*
 Problem: Move Element to End

 You are given an array of integers and a target value. The task is to move all instances of the target value to the end of the array in-place,
 while maintaining the relative order of the other elements.

 Example:

 Input:
 Array: [2, 1, 2, 2, 2, 3, 4, 2]
 Target: 2

 Output: [1, 3, 4, 2, 2, 2, 2, 2]

 Explanation:
 All instances of the target value (2) are moved to the end, and the relative order of the other elements is maintained.
*/
/*Brute Force Solution
Approach:
The brute force solution involves creating a new array where all elements not equal to toMove are placed at the beginning, followed by all occurrences of toMove at the end. Then we copy this new array back into the original array.

Time Complexity:
O(n): We iterate through the array twice—once to count and move the non-toMove elements, and once to add toMove elements.
Space Complexity:
O(n): A new array is used to store the reordered elements.*/

/*
 Solution Steps:

 1. Use two pointers, one starting from the left and the other starting from the right.
 2. The left pointer will move forward to find occurrences of the target element.
 3. The right pointer will move backward to find non-target elements.
 4. Whenever the left pointer finds a target element and the right pointer finds a non-target element, swap them.
 5. Continue this process until the two pointers meet, ensuring all target elements are moved to the end.
*/

import java.util.Arrays;

public class A03MoveElementToEnd {
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

  // Function to move all occurrences of target to the end
  public static int[] moveElementToEnd(int[] array, int target) {
    int left = 0; // Left pointer starts at the beginning
    int right = array.length - 1; // Right pointer starts at the end

    // Step 1: Use two pointers to swap target elements to the end
    while (left < right) {
      // Move the right pointer leftwards until it points to a non-target element
      while (left < right && array[right] == target) {
        right--;
      }

      // Move the left pointer rightwards until it points to a target element
      while (left < right && array[left] != target) {
        left++;
      }

      // Swap the left (target) element with the right (non-target) element
      if (left < right) {
        swap(array, left, right);
      }
    }

    return array;
  }

  // Helper function to swap two elements in an array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array = {2, 1, 2, 2, 2, 3, 4, 2};
    int target = 2;

    int[] result = moveElementToEnd(array, target);
    System.out.println("Array after moving target to end: " + Arrays.toString(result));
    // Output: [1, 3, 4, 2, 2, 2, 2, 2]
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array at most once using the two pointers.

   Space Complexity:
   - O(1), since we are modifying the array in place and only using a few extra variables.
  */
}
