/*Time Complexity:
O(n^2): In the worst-case scenario (e.g., when the array is sorted in reverse order), the algorithm performs
𝑛
        n passes, and in each pass, it makes
𝑛
n comparisons.
Best Case O(n): If the array is already sorted, the algorithm makes a single pass with no swaps.
Space Complexity:
O(1): Bubble Sort is an in-place sorting algorithm, meaning it requires a constant amount of extra space.*/

package easy;

import java.util.Arrays;

public class BubbleSort {

  // Function to perform bubble sort
  public static void bubbleSort(int[] array) {
    int n = array.length;

    // Outer loop for each pass
    for (int i = 0; i < n - 1; i++) {
      boolean swapped = false;

      // Inner loop for comparing adjacent elements
      for (int j = 0; j < n - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          // Swap the elements if they are in the wrong order
          int temp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = temp;

          // Set swapped to true to indicate that a swap has occurred
          swapped = true;
        }
      }

      // If no elements were swapped, the array is already sorted
      if (!swapped) {
        break;
      }
    }
  }

  public static void main(String[] args) {
    // Sample Input
    int[] array = {5, 1, 4, 2, 8};

    // Perform bubble sort
    bubbleSort(array);

    // Output the sorted array
    System.out.println(Arrays.toString(array)); // Expected Output: [1, 2, 4, 5, 8]
  }
}
