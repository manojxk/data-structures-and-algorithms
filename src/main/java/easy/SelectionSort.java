package easy;

import java.util.Arrays;

public class SelectionSort {

  // Function to perform selection sort
  public static void selectionSort(int[] array) {
    int n = array.length;

    // Outer loop for each element in the array
    for (int i = 0; i < n - 1; i++) {
      int minIndex = i; // Assume the first element is the minimum

      // Inner loop to find the minimum element in the unsorted part
      for (int j = i + 1; j < n; j++) {
        if (array[j] < array[minIndex]) {
          minIndex = j; // Update the minimum element's index
        }
      }

      // Swap the found minimum element with the first element of the unsorted part
      int temp = array[minIndex];
      array[minIndex] = array[i];
      array[i] = temp;
    }
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {29, 10, 14, 37, 13};

    // Perform selection sort
    selectionSort(array);

    // Output the sorted array
    System.out.println(Arrays.toString(array)); // Expected Output: [10, 13, 14, 29, 37]
  }
}

/*Time Complexity: The time complexity is O(n^2)
in all cases (worst, average, and best)
Space - O(1)

*/
