package hard.heaps;

/**
 * Problem Statement:
 *
 * <p>You are given a k-sorted array, where each element is at most 'k' positions away from its
 * sorted position. Write a function that takes in a non-negative integer k and the k-sorted array,
 * and returns the fully sorted array.
 *
 * <p>A k-sorted array is a partially sorted array in which all elements are at most k positions
 * away from their sorted position. The goal is to sort this array in a time complexity better than
 * O(nlog(n)).
 *
 * <p>Approach: Use a min-heap (priority queue) to efficiently manage the smallest element from the
 * sliding window of k elements.
 *
 * <p>Sample Input: array = [3, 2, 1, 5, 4, 7, 6, 5] k = 3
 *
 * <p>Sample Output: [1, 2, 3, 4, 5, 5, 6, 7]
 */
import java.util.PriorityQueue;

public class KSortedArraySorter {

  // Function to sort a k-sorted array
  // Time Complexity: O(n log k), where n is the length of the array, and k is the window size
  // Space Complexity: O(k), because we use a priority queue that holds at most k+1 elements at a
  // time.
  public static int[] sortKSortedArray(int[] array, int k) {
    // Create a min-heap (priority queue)
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    int[] sortedArray = new int[array.length];
    int index = 0;

    // Add the first k+1 elements to the min-heap
    for (int i = 0; i <= k && i < array.length; i++) {
      minHeap.add(array[i]);
    }

    // For each remaining element, insert it into the heap and extract the smallest element
    for (int i = k + 1; i < array.length; i++) {
      minHeap.add(array[i]);
      sortedArray[index++] = minHeap.poll(); // Add the smallest element to the sorted array
    }

    // Extract the remaining elements from the heap
    while (!minHeap.isEmpty()) {
      sortedArray[index++] = minHeap.poll();
    }

    return sortedArray;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int[] array = {3, 2, 1, 5, 4, 7, 6, 5};
    int k = 3;

    int[] sortedArray = sortKSortedArray(array, k);

    // Print the sorted array
    for (int num : sortedArray) {
      System.out.print(num + " ");
    }
  }
}
