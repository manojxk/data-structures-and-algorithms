package hard.sorting;

/**
 * Problem Statement:
 *
 * <p>You are given an array of integers. The task is to return a sorted version of that array using
 * the Heap Sort algorithm.
 *
 * <p>Heap Sort works by first converting the array into a max-heap and then repeatedly extracting
 * the maximum element and placing it at the end of the array, shrinking the heap size by one in
 * each iteration.
 *
 * <p>Sample Input: array = [8, 5, 2, 9, 5, 6, 3]
 *
 * <p>Sample Output: [2, 3, 5, 5, 6, 8, 9]
 */
public class HeapSort {

  // Heap Sort Algorithm
  // Time Complexity: O(n log n) - Building the heap takes O(n) and each extraction operation takes
  // O(log n).
  // Space Complexity: O(1) - Sorting is done in place, so we use constant extra space.
  public static int[] heapSort(int[] array) {
    int n = array.length;

    // Step 1: Build a max-heap from the array
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(array, n, i);
    }

    // Step 2: Extract elements from the heap one by one
    for (int i = n - 1; i > 0; i--) {
      // Move the current root (largest element) to the end of the array
      swap(array, 0, i);

      // Heapify the reduced heap
      heapify(array, i, 0);
    }

    return array;
  }

  // Helper function to heapify a subtree rooted at index `i`
  // `n` is the size of the heap
  private static void heapify(int[] array, int n, int i) {
    int largest = i; // Initialize the largest as root
    int left = 2 * i + 1; // Left child index
    int right = 2 * i + 2; // Right child index

    // If the left child is larger than the root
    if (left < n && array[left] > array[largest]) {
      largest = left;
    }

    // If the right child is larger than the largest so far
    if (right < n && array[right] > array[largest]) {
      largest = right;
    }

    // If the largest is not the root
    if (largest != i) {
      swap(array, i, largest);

      // Recursively heapify the affected subtree
      heapify(array, n, largest);
    }
  }

  // Helper function to swap two elements in the array
  private static void swap(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  // Main function to test the Heap Sort implementation
  public static void main(String[] args) {
    int[] array = {8, 5, 2, 9, 5, 6, 3};

    int[] sortedArray = heapSort(array);
    System.out.println("Sorted Array: " + java.util.Arrays.toString(sortedArray));
  }
}
