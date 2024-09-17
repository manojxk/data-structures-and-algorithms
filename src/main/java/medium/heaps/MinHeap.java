package medium.heaps;

/*
 Problem: Min Heap Construction

 A Min Heap is a complete binary tree where the value of each node is less than or equal to the values of its children.
 The root node contains the smallest element, and the heap is structured such that extracting the minimum value and maintaining the heap property can be done efficiently.

 Example:

 Operations:
 - Insert elements into the heap.
 - Remove the minimum element from the heap (root of the tree).

 Example Heap Operations:
 Insert: [3, 1, 6, 5, 2, 4]
 Remove Min: [1, 2, 3, 4, 5, 6] (elements after sequential removals of the min)

 Explanation:
 - Insert elements while maintaining the Min Heap property.
 - Remove the minimum element and re-heapify the structure to maintain the Min Heap property.
*/

/*
 Solution Steps:

 1. Use an array to represent the binary heap structure.
 2. The key operations for a Min Heap are `insert` and `remove`. These operations require maintaining the heap property:
     - Insert: Add the new element to the end of the heap and "bubble it up" to restore the Min Heap property.
     - Remove: Remove the root (the smallest element), replace it with the last element, and "sift it down" to restore the Min Heap property.
 3. Use helper functions for "bubbling up" (for insert) and "sifting down" (for removal).
*/

import java.util.ArrayList;

public class MinHeap {
  private ArrayList<Integer> heap;

  // Constructor to initialize the Min Heap
  public MinHeap() {
    heap = new ArrayList<>();
  }

  // Function to return the index of the parent node
  private int getParent(int index) {
    return (index - 1) / 2;
  }

  // Function to return the index of the left child node
  private int getLeftChild(int index) {
    return 2 * index + 1;
  }

  // Function to return the index of the right child node
  private int getRightChild(int index) {
    return 2 * index + 2;
  }

  // Function to insert an element into the Min Heap
  public void insert(int value) {
    // Add the new element to the end of the heap
    heap.add(value);
    // Bubble up the newly added element to maintain the heap property
    bubbleUp(heap.size() - 1);
  }

  // Function to remove and return the minimum element from the heap (root element)
  public int removeMin() {
    if (heap.isEmpty()) {
      throw new IllegalStateException("Heap is empty");
    }

    // The minimum element is at the root (index 0)
    int min = heap.get(0);

    // Move the last element to the root and remove the last element
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);

    // Sift down the new root element to maintain the heap property
    siftDown(0);

    return min;
  }

  // Function to bubble up the element at index i
  private void bubbleUp(int i) {
    int parent = getParent(i);

    // Continue bubbling up while the current node is smaller than its parent
    while (i > 0 && heap.get(i) < heap.get(parent)) {
      swap(i, parent);
      i = parent;
      parent = getParent(i);
    }
  }

  // Function to sift down the element at index i
  private void siftDown(int i) {
    int left = getLeftChild(i);
    int right = getRightChild(i);
    int smallest = i;

    // Check if the left child exists and is smaller than the current element
    if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
      smallest = left;
    }

    // Check if the right child exists and is smaller than the smallest element so far
    if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
      smallest = right;
    }

    // If the current element is not the smallest, swap with the smallest and continue sifting down
    if (smallest != i) {
      swap(i, smallest);
      siftDown(smallest);
    }
  }

  // Helper function to swap two elements in the heap
  private void swap(int i, int j) {
    int temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  // Function to display the heap elements
  public void displayHeap() {
    System.out.println(heap);
  }

  // Main function to test the Min Heap implementation
  public static void main(String[] args) {
    MinHeap minHeap = new MinHeap();

    // Insert elements into the heap
    minHeap.insert(3);
    minHeap.insert(1);
    minHeap.insert(6);
    minHeap.insert(5);
    minHeap.insert(2);
    minHeap.insert(4);

    // Display the heap after inserts
    System.out.println("Heap after inserts:");
    minHeap.displayHeap(); // Expected: [1, 2, 4, 5, 3, 6]

    // Remove the minimum element from the heap
    System.out.println("Removed min element: " + minHeap.removeMin()); // Expected: 1
    minHeap.displayHeap(); // Expected: [2, 3, 4, 5, 6]

    System.out.println("Removed min element: " + minHeap.removeMin()); // Expected: 2
    minHeap.displayHeap(); // Expected: [3, 5, 4, 6]
  }

  /*
   Time Complexity:
   - Insert: O(log n), where n is the number of elements in the heap. The insert operation requires "bubbling up" the new element, which takes log(n) time.
   - Remove Min: O(log n), as removing the root requires "sifting down" the element to restore the heap property, which also takes log(n) time.

   Space Complexity:
   - O(n), where n is the number of elements stored in the heap.
  */
}
