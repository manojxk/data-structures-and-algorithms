/*Insertion Sort Algorithm
Approach:
Build Sorted Subarray: Start with the first element, considering it as a sorted subarray of one element.
Insert Each Element: For each subsequent element, compare it with the elements in the sorted subarray and insert it in the correct position.
Shift Elements: Shift all larger elements one position to the right to make space for the current element.
        Repeat: Repeat this process until the entire array is sorted.*/

/*Time Complexity:
O(n^2): In the worst case, where the array is sorted in reverse order, each element must be compared with all previously sorted elements, resulting in quadratic time complexity.
Best Case O(n): If the array is already sorted, only one comparison per element is needed.
Space Complexity:
O(1): Insertion Sort is an in-place sorting algorithm, requiring a constant amount of extra space.*/

package easy.sorting;

import java.util.Arrays;

public class InsertionSort {

  // Function to perform insertion sort
  public static void insertionSort(int[] array) {
    int n = array.length;

    // Outer loop for each element in the array (starting from the second element)
    for (int i = 1; i < n; i++) {
      int key = array[i];
      int j = i - 1;

      // Inner loop to find the correct position for the key
      while (j >= 0 && array[j] > key) {
        array[j + 1] = array[j]; // Shift larger elements to the right
        j--;
      }

      array[j + 1] = key; // Place the key in its correct position
    }
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {8, 4, 3, 7, 6, 5, 2};

    // Perform insertion sort
    insertionSort(array);

    // Output the sorted array
    System.out.println(Arrays.toString(array)); // Expected Output: [2, 3, 4, 5, 6, 7, 8]
  }
}
