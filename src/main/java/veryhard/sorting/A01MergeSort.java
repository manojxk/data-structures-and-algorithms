package veryhard.sorting;

/*
 Problem: Merge Sort

 Merge Sort is a divide-and-conquer sorting algorithm. It works by recursively splitting the input array into smaller sub-arrays,
 sorting each sub-array, and then merging the sorted sub-arrays back together.

 Example:

 Input: [38, 27, 43, 3, 9, 82, 10]
 Output: [3, 9, 10, 27, 38, 43, 82]

 Explanation:
 The array is recursively divided into two halves until each sub-array contains a single element.
 Then, the sub-arrays are merged back together in sorted order.
*/

/*
 Solution Steps:

 1. If the array has more than one element, split it into two halves.
 2. Recursively apply Merge Sort to the left and right halves.
 3. Merge the sorted halves by comparing the smallest elements from each half and building the sorted array.
 4. Continue merging until the entire array is sorted.
*/

public class A01MergeSort {

  // Function to perform Merge Sort on an array
  public void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
      // Find the middle point
      int middle = left + (right - left) / 2;

      // Recursively sort the left half
      mergeSort(arr, left, middle);

      // Recursively sort the right half
      mergeSort(arr, middle + 1, right);

      // Merge the two sorted halves
      merge(arr, left, middle, right);
    }
  }

  // Helper function to merge two halves of the array
  private void merge(int[] arr, int left, int middle, int right) {
    // Sizes of the two sub-arrays to be merged
    int n1 = middle - left + 1;
    int n2 = right - middle;

    // Create temporary arrays
    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    // Copy data to temporary arrays
    for (int i = 0; i < n1; i++) {
      leftArray[i] = arr[left + i];
    }
    for (int j = 0; j < n2; j++) {
      rightArray[j] = arr[middle + 1 + j];
    }

    // Merge the temporary arrays back into the original array

    int i = 0, j = 0;
    int k = left;

    // Compare elements from both halves and merge them in sorted order
    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        arr[k] = leftArray[i];
        i++;
      } else {
        arr[k] = rightArray[j];
        j++;
      }
      k++;
    }

    // Copy the remaining elements of leftArray, if any
    while (i < n1) {
      arr[k] = leftArray[i];
      i++;
      k++;
    }

    // Copy the remaining elements of rightArray, if any
    while (j < n2) {
      arr[k] = rightArray[j];
      j++;
      k++;
    }
  }

  // Main function to test the Merge Sort implementation
  public static void main(String[] args) {
    A01MergeSort sorter = new A01MergeSort();

    // Example array
    int[] arr = {38, 27, 43, 3, 9, 82, 10};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Merge Sort
    sorter.mergeSort(arr, 0, arr.length - 1);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of elements in the array. The array is split in half log(n) times, and the merging process takes O(n) time for each split.

   Space Complexity:
   - O(n), because additional space is required for the temporary arrays used in the merging process.
  */
}
