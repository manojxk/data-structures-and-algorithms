/*
 * Problem Statement:
 * Implement a MinHeap class that supports the following operations:
 *
 * 1. Building a MinHeap from an input array of integers.
 * 2. Inserting integers into the heap.
 * 3. Removing the heap's minimum (root) value.
 * 4. Peeking at the heap's minimum (root) value.
 * 5. Sifting integers up and down the heap, which is used when inserting and removing values.
 *
 * The heap should be represented as an array.
 *
 * Example Usage:
 *
 * Input: array = [48, 12, 24, 7, 8, -5, 24, 391, 24, 56, 2, 6, 8, 41]
 * MinHeap heap = new MinHeap(array); // Initializes the MinHeap
 * heap.insert(76);                   // After insertion, the heap is [-5, 2, 6, 7, 8, 8, 24, 391, 24, 56, 12, 24, 48, 41, 76]
 * heap.peek();                       // Returns -5, the minimum value in the heap
 * heap.remove();                     // Removes -5, and the heap is now [2, 7, 6, 24, 8, 8, 24, 391, 76, 56, 12, 24, 48, 41]
 * heap.peek();                       // Returns 2, the new minimum value
 * heap.insert(87);                   // After insertion, the heap is [2, 7, 6, 24, 8, 8, 24, 391, 76, 56, 12, 24, 48, 41, 87]
 */

package medium.heaps;

import java.util.ArrayList;
import java.util.List;

public class MinHeap {
  private List<Integer> heap;

  // Constructor that initializes the heap and builds it using the buildHeap method
  public MinHeap(List<Integer> array) {
    heap = array;
    buildHeap();
  }

  // Builds the heap from an input array
  public void buildHeap() {
    int firstParentIdx = (heap.size() - 2) / 2;
    for (int currentIdx = firstParentIdx; currentIdx >= 0; currentIdx--) {
      siftDown(currentIdx, heap.size() - 1);
    }
  }

  // Sifts an element down the heap to maintain heap property
  private void siftDown(int currentIdx, int endIdx) {
    int childOneIdx = currentIdx * 2 + 1;
    while (childOneIdx <= endIdx) {
      int childTwoIdx = (childOneIdx + 1 <= endIdx) ? childOneIdx + 1 : -1;
      int idxToSwap;
      if (childTwoIdx != -1 && heap.get(childTwoIdx) < heap.get(childOneIdx)) {
        idxToSwap = childTwoIdx;
      } else {
        idxToSwap = childOneIdx;
      }
      if (heap.get(idxToSwap) < heap.get(currentIdx)) {
        swap(currentIdx, idxToSwap);
        currentIdx = idxToSwap;
        childOneIdx = currentIdx * 2 + 1;
      } else {
        break;
      }
    }
  }

  // Sifts an element up the heap to maintain heap property
  private void siftUp(int currentIdx) {
    int parentIdx = (currentIdx - 1) / 2;
    while (currentIdx > 0 && heap.get(currentIdx) < heap.get(parentIdx)) {
      swap(currentIdx, parentIdx);
      currentIdx = parentIdx;
      parentIdx = (currentIdx - 1) / 2;
    }
  }

  // Inserts a new value into the heap
  public void insert(int value) {
    heap.add(value);
    siftUp(heap.size() - 1);
  }

  // Removes and returns the minimum value (root) from the heap
  public int remove() {
    swap(0, heap.size() - 1);
    int valueToRemove = heap.remove(heap.size() - 1);
    siftDown(0, heap.size() - 1);
    return valueToRemove;
  }

  // Returns the minimum value (root) without removing it
  public int peek() {
    return heap.get(0);
  }

  // Swaps two elements in the heap array
  private void swap(int i, int j) {
    int temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  // Main method for testing the MinHeap class
  public static void main(String[] args) {
    List<Integer> array =
        new ArrayList<>(List.of(48, 12, 24, 7, 8, -5, 24, 391, 24, 56, 2, 6, 8, 41));
    MinHeap heap = new MinHeap(array);

    System.out.println(heap.heap); // Output: [-5, 2, 6, 7, 8, 8, 24, 391, 24, 56, 12, 24, 48, 41]

    heap.insert(76);
    System.out.println(
        heap.heap); // Output: [-5, 2, 6, 7, 8, 8, 24, 391, 24, 56, 12, 24, 48, 41, 76]

    System.out.println(heap.peek()); // Output: -5

    heap.remove();
    System.out.println(heap.heap); // Output: [2, 7, 6, 24, 8, 8, 24, 391, 76, 56, 12, 24, 48, 41]

    System.out.println(heap.peek()); // Output: 2

    heap.insert(87);
    System.out.println(
        heap.heap); // Output: [2, 7, 6, 24, 8, 8, 24, 391, 76, 56, 12, 24, 48, 41, 87]
  }
}
/*
Explanation of Key Operations
Build Heap:

Converts an arbitrary array into a MinHeap by sifting down every parent node starting from the last parent up to the root.
Time Complexity: O(n) (although it may seem like O(n log n), the build process is linear due to the nature of the sifting operations).
Sift Down:

Used during the buildHeap and remove operations to maintain the MinHeap property by moving an element down the heap.
Time Complexity: O(log n).
Sift Up:

Used during the insert operation to maintain the MinHeap property by moving an element up the heap.
Time Complexity: O(log n).
Insert:

Adds a new element to the heap and sifts it up to maintain the MinHeap property.
Time Complexity: O(log n).
Remove:

Removes and returns the root element (minimum value) and sifts down the last element to maintain the MinHeap property.
Time Complexity: O(log n).
Peek:

Returns the root element without removing it.
Time Complexity: O(1).
Summary
The MinHeap class supports efficient insertion, removal, and retrieval of the minimum element.
The operations are primarily based on sifting elements up or down to maintain the heap's properties.
The heap is represented as an array, making it memory-efficient and easy to implement.*/
