package hard.heaps;/*
 Problem: Sort K-Sorted Array

 A k-sorted array is an array where each element is at most k positions away from its correct position in the sorted order.
 The problem is to sort such an array efficiently.

 Example:

 Input:
   arr = [6, 5, 3, 2, 8, 10, 9], k = 3
 Output:
   [2, 3, 5, 6, 8, 9, 10]

 Explanation:
 The element 6 is at most 3 positions away from its correct position in the sorted array.
 Similarly, other elements are at most k positions away from their correct positions.

 Strategy:
 Use a Min Heap to maintain the smallest k+1 elements and place them in the correct order.

*/

/*
 Solution Steps:

 1. Create a Min Heap to store k+1 elements.
 2. Traverse the array:
    - Add the current element to the heap.
    - If the heap size exceeds k, extract the minimum element from the heap and place it into the array at the correct position.
 3. After traversing the array, extract the remaining elements from the heap and place them into the array.
*/

import java.util.PriorityQueue;

public class A02KSortedArraySorter {

  // Function to sort a k-sorted array
  public void sortKSortedArray(int[] arr, int k) {
    // Min heap to store k+1 elements
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    // Index to place sorted elements in the array
    int index = 0;

    // Step 1: Add first k+1 elements to the heap
    for (int i = 0; i < Math.min(arr.length, k + 1); i++) {
      minHeap.add(arr[i]);
    }

    // Step 2: Traverse the rest of the array
    for (int i = k + 1; i < arr.length; i++) {
      // Extract the minimum element from the heap and place it in the correct position
      arr[index++] = minHeap.poll();
      // Add the current element to the heap
      minHeap.add(arr[i]);
    }

    // Step 3: Extract the remaining elements from the heap
    while (!minHeap.isEmpty()) {
      arr[index++] = minHeap.poll();
    }
  }

  // Main function to test the Sort K-Sorted Array implementation
  public static void main(String[] args) {
    A02KSortedArraySorter sorter = new A02KSortedArraySorter();

    // Example array and k value
    int[] arr = {6, 5, 3, 2, 8, 10, 9};
    int k = 3;

    System.out.println("Original K-Sorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Perform sorting of the k-sorted array
    sorter.sortKSortedArray(arr, k);

    System.out.println("\nSorted Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }
  }

    /*
     Time Complexity:
     - O(n log k), where n is the number of elements in the array and k is the number of allowed unsorted positions.
       Inserting and extracting from the heap (both O(log k)) is done n times.

     Space Complexity:
     - O(k), where k is the size of the heap, as the heap stores at most k+1 elements at any given time.
    */
}
