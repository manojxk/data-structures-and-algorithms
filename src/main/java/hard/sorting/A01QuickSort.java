package hard.sorting;

/*
 Problem: Quick Sort

 Quick Sort is an efficient, comparison-based sorting algorithm. It works by selecting a 'pivot' element from the array
 and partitioning the other elements into two sub-arrays, according to whether they are less than or greater than the pivot.
 The sub-arrays are then sorted recursively. This process is repeated until the entire array is sorted.

 Example:

 Input: [10, 80, 30, 90, 40, 50, 70]
 Output: [10, 30, 40, 50, 70, 80, 90]

 Explanation:
 The pivot element is chosen, and the array is partitioned such that elements smaller than the pivot are moved to its left
 and elements larger are moved to its right. The process is recursively applied to the sub-arrays until the entire array is sorted.
*/

/*
 Solution Steps:

 1. Choose a pivot element from the array (in this case, we'll choose the last element as the pivot).
 2. Partition the array such that all elements smaller than the pivot are moved to the left of the pivot,
    and all elements greater than the pivot are moved to the right.
 3. Recursively apply Quick Sort to the sub-arrays (elements before the pivot and after the pivot).
 4. The base case occurs when the sub-array has fewer than two elements (i.e., when it's already sorted).
*/

public class A01QuickSort {

  // Function to perform Quick Sort on an array
  public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
      // Partition the array and get the pivot index
      int pivotIndex = partition(arr, low, high);

      // Recursively sort the sub-arrays
      quickSort(arr, low, pivotIndex - 1); // Sort elements before the pivot
      quickSort(arr, pivotIndex + 1, high); // Sort elements after the pivot
    }
  }

  // Partition function that chooses the pivot and sorts the elements around the pivot
  private int partition(int[] arr, int low, int high) {
    // Choose the pivot element (last element in this case)
    int pivot = arr[high];
    int i = low - 1; // Pointer for the smaller elements

    // Traverse the array and move smaller elements to the left of the pivot
    for (int j = low; j < high; j++) {
      if (arr[j] < pivot) {
        i++;
        // Swap arr[i] and arr[j]
        swap(arr, i, j);
      }
    }

    // Place the pivot in the correct position
    swap(arr, i + 1, high);

    // Return the index of the pivot element
    return i + 1;
  }

  // Helper function to swap two elements in the array
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // Main function to test the Quick Sort implementation
  public static void main(String[] args) {
    A01QuickSort sorter = new A01QuickSort();

    // Example array
    int[] arr = {10, 80, 30, 90, 40, 50, 70};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Quick Sort
    sorter.quickSort(arr, 0, arr.length - 1);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - Average Case: O(n log n), where n is the number of elements in the array.
     This occurs when the pivot divides the array into roughly equal parts at each step.
   - Worst Case: O(n^2), which can happen if the array is already sorted (or reverse sorted) and the pivot is always chosen as the last element.

   Space Complexity:
   - O(log n) for the recursion stack in the average case.
   - O(n) in the worst case due to recursion stack depth.
  */
}
