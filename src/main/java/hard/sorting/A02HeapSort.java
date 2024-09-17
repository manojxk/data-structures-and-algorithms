package hard.sorting;

/*
 Problem: Heap Sort

 Heap Sort is a comparison-based sorting algorithm that uses a binary heap data structure.
 It divides its input into a sorted and an unsorted region and iteratively shrinks the unsorted region
 by extracting the largest element and moving it to the sorted region.

 The algorithm first builds a max heap from the input array, then repeatedly extracts the maximum element
 from the heap and places it at the end of the array, reducing the heap size with each iteration.

 Example:

 Input: [12, 11, 13, 5, 6, 7]
 Output: [5, 6, 7, 11, 12, 13]

 Explanation:
 The array is first turned into a max heap, and then the largest element is swapped with the last element of the array.
 The heap size is reduced, and the process is repeated, sorting the array in ascending order.
*/

/*
 Solution Steps:

 1. Build a max heap from the input array.
 2. Once the max heap is built, repeatedly extract the maximum element from the heap and place it at the end of the array.
 3. Swap the first element of the heap (the root, which is the largest element) with the last element.
 4. Reduce the heap size and call the heapify function to maintain the max heap property.
 5. Repeat until all elements are sorted.
*/

public class A02HeapSort {

  // Function to perform Heap Sort on an array
  public void heapSort(int[] arr) {
    int n = arr.length;

    // Step 1: Build the max heap
    for (int i = n / 2 - 1; i >= 0; i--) {
      heapify(arr, n, i);
    }

    // Step 2: Extract elements one by one from the heap
    for (int i = n - 1; i > 0; i--) {
      // Move the current root (largest) to the end of the array
      swap(arr, 0, i);

      // Call heapify on the reduced heap
      heapify(arr, i, 0);
    }
  }

  // Heapify function to maintain the max heap property
  private void heapify(int[] arr, int n, int i) {
    int largest = i; // Initialize the largest as the root
    int left = 2 * i + 1; // Left child
    int right = 2 * i + 2; // Right child

    // If the left child is larger than the root
    if (left < n && arr[left] > arr[largest]) {
      largest = left;
    }

    // If the right child is larger than the current largest
    if (right < n && arr[right] > arr[largest]) {
      largest = right;
    }

    // If the largest is not the root, swap and heapify the affected subtree
    if (largest != i) {
      swap(arr, i, largest);

      // Recursively heapify the affected subtree
      heapify(arr, n, largest);
    }
  }

  // Helper function to swap two elements in an array
  private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // Main function to test the Heap Sort implementation
  public static void main(String[] args) {
    A02HeapSort sorter = new A02HeapSort();

    // Example array
    int[] arr = {12, 11, 13, 5, 6, 7};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Heap Sort
    sorter.heapSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of elements in the array. Building the max heap takes O(n) time, and extracting each element (and re-heapifying) takes O(log n).

   Space Complexity:
   - O(1), since Heap Sort is an in-place sorting algorithm and does not require additional space.
  */
}
