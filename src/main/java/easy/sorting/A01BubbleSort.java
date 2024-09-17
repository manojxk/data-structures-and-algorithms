package easy.sorting;

/*
 Problem: Bubble Sort

 Bubble Sort is a simple sorting algorithm that repeatedly steps through the list, compares adjacent elements,
 and swaps them if they are in the wrong order. The pass through the list is repeated until the list is sorted.

 Example:

 Input: [64, 34, 25, 12, 22, 11, 90]
 Output: [11, 12, 22, 25, 34, 64, 90]

 Explanation:
 The algorithm compares each adjacent pair and swaps if necessary.
 After the first pass, the largest element will be at the end.
 The algorithm repeats this process, ignoring the last sorted elements, until the entire list is sorted.
*/

/*
 Solution Steps:

 1. Start at the beginning of the array and iterate through each element.
 2. Compare adjacent elements, and if the first element is greater than the second, swap them.
 3. Repeat this process for all elements in the array.
 4. After each complete pass through the array, the largest element will "bubble up" to the correct position.
 5. Reduce the problem size by ignoring the last sorted elements in subsequent passes.
 6. The process repeats until no swaps are made, indicating that the array is sorted.
*/

public class A01BubbleSort {

  // Function to perform bubble sort on an array
  public void bubbleSort(int[] arr) {
    int n = arr.length;

    // Outer loop for the number of passes
    for (int i = 0; i < n - 1; i++) {
      // A flag to detect if a swap has occurred in this pass
      boolean swapped = false;

      // Inner loop for comparing adjacent elements
      for (int j = 0; j < n - 1 - i; j++) {
        // If the current element is greater than the next element, swap them
        if (arr[j] > arr[j + 1]) {
          // Swap the elements
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;

          // Mark that a swap has occurred
          swapped = true;
        }
      }

      // If no elements were swapped in this pass, the array is already sorted
      if (!swapped) break;
    }
  }

  // Main function to test the Bubble Sort implementation
  public static void main(String[] args) {
    A01BubbleSort sorter = new A01BubbleSort();

    // Example array
    int[] arr = {64, 34, 25, 12, 22, 11, 90};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Bubble Sort
    sorter.bubbleSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - Worst and Average Case: O(n^2), where n is the number of elements in the array.
     This is because, in the worst case, we may have to perform n passes, and in each pass, we perform n-1 comparisons.
   - Best Case: O(n), when the array is already sorted. The algorithm makes one pass and exits early due to no swaps.

   Space Complexity:
   - O(1), as Bubble Sort is an in-place sorting algorithm. It requires only a constant amount of extra memory.
  */
}
