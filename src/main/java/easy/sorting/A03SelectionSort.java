package easy.sorting;

/*
 Problem: Selection Sort

 Selection Sort is a simple comparison-based sorting algorithm. The algorithm divides the array into a sorted
 and an unsorted part. It repeatedly selects the smallest (or largest) element from the unsorted part
 and swaps it with the first unsorted element, effectively growing the sorted part of the array.

 Example:

 Input: [64, 25, 12, 22, 11]
 Output: [11, 12, 22, 25, 64]

 Explanation:
 In each iteration, the smallest element from the unsorted part of the array is selected
 and swapped with the first unsorted element, thus growing the sorted portion of the array.
*/

/*
 Solution Steps:

 1. Start from the first element and iterate through the array.
 2. For each element, find the smallest element in the unsorted part of the array.
 3. Swap the smallest element with the first unsorted element.
 4. Move the boundary of the sorted part by one and repeat the process.
 5. Continue this process until the entire array is sorted.
*/

public class A03SelectionSort {

  // Function to perform selection sort on an array
  public void selectionSort(int[] arr) {
    int n = arr.length;

    // Traverse the unsorted part of the array
    for (int i = 0; i < n - 1; i++) {
      // Assume the first unsorted element is the smallest
      int minIndex = i;

      // Find the minimum element in the unsorted part of the array
      for (int j = i + 1; j < n; j++) {
        if (arr[j] < arr[minIndex]) {
          minIndex = j; // Update the index of the minimum element
        }
      }

      // Swap the found minimum element with the first unsorted element
      int temp = arr[minIndex];
      arr[minIndex] = arr[i];
      arr[i] = temp;
    }
  }

  // Main function to test the Selection Sort implementation
  public static void main(String[] args) {
    A03SelectionSort sorter = new A03SelectionSort();

    // Example array
    int[] arr = {64, 25, 12, 22, 11};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform Selection Sort
    sorter.selectionSort(arr);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

  /*
   Time Complexity:
   - O(n^2), where n is the number of elements in the array. This is because we need to find the minimum element in the unsorted part of the array, which takes O(n) time for each of the n elements.

   Space Complexity:
   - O(1), because Selection Sort is an in-place sorting algorithm. It only uses a constant amount of extra memory.
  */
}
