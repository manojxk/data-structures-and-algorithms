package hard.sorting;

/**
 * Problem Statement:
 *
 * <p>Write a function that takes an array of integers and returns a sorted version of that array.
 * The sorting must be done using the Quick Sort algorithm.
 *
 * <p>Quick Sort is a comparison sort that divides the array based on a pivot and recursively sorts
 * the subarrays. It operates by selecting a pivot and partitioning the array so that elements
 * smaller than the pivot are on the left, and elements larger than the pivot are on the right.
 * Then, the process is repeated for each subarray.
 *
 * <p>Time Complexity (on average): O(n log n), where n is the number of elements in the array.
 * Worst-case Time Complexity: O(n^2) when the pivot is always the largest or smallest element.
 * Space Complexity: O(log n) due to recursive calls (stack space).
 *
 * <p>Sample Input: array = [8, 5, 2, 9, 5, 6, 3]
 *
 * <p>Sample Output: [2, 3, 5, 5, 6, 8, 9]
 */
import java.util.*;

public class QuickSortAlgorithm {

  // Quick Sort function
  // Time Complexity: O(n log n) on average, O(n^2) in the worst case.
  // Space Complexity: O(log n) due to recursive calls (in-place sort).
  public static int[] quickSort(int[] array) {
    quickSortHelper(array, 0, array.length - 1);
    return array;
  }

  // Helper function to perform Quick Sort recursively
  private static void quickSortHelper(int[] array, int start, int end) {
    if (start >= end) {
      return; // Base case: When the array is reduced to one element or less.
    }

    // Partition the array and get the index of the pivot
    int pivotIndex = partition(array, start, end);

    // Recursively sort the subarrays
    quickSortHelper(array, start, pivotIndex - 1); // Sort the left subarray
    quickSortHelper(array, pivotIndex + 1, end); // Sort the right subarray
  }

  // Partition function to place the pivot in the correct position
  private static int partition(int[] array, int start, int end) {
    // Choose the pivot as the last element in the array
    int pivot = array[end];
    int low = start;

    // Rearrange the elements so that those smaller than the pivot come to the left
    for (int i = start; i < end; i++) {
      if (array[i] <= pivot) {
        swap(array, i, low);
        low++;
      }
    }

    // Swap the pivot with the element at the low index
    swap(array, low, end);
    return low;
  }

  // Helper function to swap two elements in the array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  // Main function to test the Quick Sort algorithm
  public static void main(String[] args) {
    int[] array = {8, 5, 2, 9, 5, 6, 3};

    // Sorting the array using Quick Sort
    int[] sortedArray = quickSort(array);

    // Print the sorted array
    System.out.println("Sorted Array: " + Arrays.toString(sortedArray));
  }
}
