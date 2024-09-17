package easy.sorting;

/*
 Problem: Insertion Sort

 Insertion Sort is a simple sorting algorithm that builds the final sorted array one element at a time.
 It iterates over the array, and for each element, it inserts it into its correct position relative to the already sorted part of the array.

 Example:

 Input: [12, 11, 13, 5, 6]
 Output: [5, 6, 11, 12, 13]

 Explanation:
 In each iteration, the current element is compared with the elements in the sorted portion of the array
 (to the left) and placed in the correct position by shifting the other elements as needed.
*/

/*
 Solution Steps:

 1. The array is virtually split into a sorted and an unsorted part. Initially, the sorted part is just the first element.
 2. Starting from the second element, pick the current element (key) and compare it with the elements to its left.
 3. Shift all elements that are greater than the key to one position ahead.
 4. Insert the key into its correct position in the sorted part of the array.
 5. Repeat this process for all elements until the array is fully sorted.
*/

public class A02InsertionSort {

  // Function to perform insertion sort on an array
  public void insertionSort(int[] arr) {
    int n = arr.length;

    // Start from the second element and iterate through the array
    for (int i = 1; i < n; i++) {
      // The current element to be inserted
      int key = arr[i];

      // Index of the last element in the sorted portion of the array
      int j = i - 1;

      // Shift elements of the sorted part that are greater than key to one position ahead
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j]; // Shift element to the right
        j--; // Move to the next element on the left
      }

      // Insert the key into its correct position
      arr[j + 1] = key;
    }
  }

  // Main function to test the Insertion Sort implementation
  public static void main(String[] args) {
    A02InsertionSort sorter = new A02InsertionSort();

    // Example array
    int[] arr = {12, 11, 13, 5, 6};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Insertion Sort
    sorter.insertionSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - Worst and Average Case: O(n^2), where n is the number of elements in the array.
     In the worst case (reverse sorted array), each element must be compared with all previous elements,
     resulting in n comparisons for each element.
   - Best Case: O(n), when the array is already sorted. Only one comparison is made per element, and no shifts are needed.

   Space Complexity:
   - O(1), because Insertion Sort is an in-place sorting algorithm. It only uses a constant amount of extra memory.
  */
}
