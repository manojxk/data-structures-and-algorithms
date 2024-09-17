package hard.heaps;

/*
 Problem: Continuous Median

 The problem requires implementing a data structure that can efficiently add numbers from a data stream
 and return the median of the numbers at any point in time.

 The median is the middle number in a sorted list of numbers. If the list has an odd number of elements,
 the median is the middle number. If the list has an even number of elements, the median is the average of the two middle numbers.

 To efficiently calculate the median in a dynamic stream of numbers, we can use two heaps:
 1. A Max Heap to store the lower half of the numbers.
 2. A Min Heap to store the upper half of the numbers.

 Example:

 Operations:
 - Insert numbers: [5, 10, 1, 7]
 - Retrieve median after each insertion: [5, 7.5, 5, 6]

 Explanation:
 - After inserting 5, the median is 5.
 - After inserting 10, the median is (5 + 10) / 2 = 7.5.
 - After inserting 1, the median is 5.
 - After inserting 7, the median is (5 + 7) / 2 = 6.
*/

/*
 Solution Steps:

 1. Use two heaps:
    - A Max Heap for the smaller half of the numbers (left side).
    - A Min Heap for the larger half of the numbers (right side).
 2. For each new number:
    - Add the number to one of the heaps (based on its value).
    - Balance the heaps so that their sizes differ by at most 1.
 3. To retrieve the median:
    - If both heaps are of the same size, return the average of the roots of both heaps.
    - If one heap is larger, return the root of the larger heap.
*/

import java.util.Collections;
import java.util.PriorityQueue;

public class A01ContinuousMedian {
  // Max Heap to store the smaller half of the numbers
  private PriorityQueue<Integer> lowerHalf;

  // Min Heap to store the larger half of the numbers
  private PriorityQueue<Integer> upperHalf;

  // Constructor to initialize the heaps
  public A01ContinuousMedian() {
    lowerHalf = new PriorityQueue<>(Collections.reverseOrder()); // Max Heap
    upperHalf = new PriorityQueue<>(); // Min Heap
  }

  // Function to add a number to the data structure
  public void addNumber(int num) {
    // Add the number to the appropriate heap
    if (lowerHalf.isEmpty() || num <= lowerHalf.peek()) {
      lowerHalf.add(num); // Add to Max Heap (lower half)
    } else {
      upperHalf.add(num); // Add to Min Heap (upper half)
    }

    // Balance the heaps so their sizes differ by at most 1
    if (lowerHalf.size() > upperHalf.size() + 1) {
      upperHalf.add(lowerHalf.poll());
    } else if (upperHalf.size() > lowerHalf.size()) {
      lowerHalf.add(upperHalf.poll());
    }
  }

  // Function to retrieve the median
  public double getMedian() {
    if (lowerHalf.size() == upperHalf.size()) {
      // If both heaps have the same size, the median is the average of the two middle elements
      return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
    } else {
      // If one heap has more elements, the median is the root of that heap
      return lowerHalf.peek();
    }
  }

  // Main function to test the Continuous Median implementation
  public static void main(String[] args) {
    A01ContinuousMedian continuousMedian = new A01ContinuousMedian();

    int[] numbers = {5, 10, 1, 7};

    for (int num : numbers) {
      continuousMedian.addNumber(num);
      System.out.println(
          "Current median after adding " + num + ": " + continuousMedian.getMedian());
    }
  }

  /*
   Time Complexity:
   - Insert: O(log n), where n is the number of elements in the heaps. Inserting into a heap takes log(n) time.
   - Get Median: O(1), as retrieving the median just involves looking at the roots of the heaps.

   Space Complexity:
   - O(n), where n is the number of elements in the heaps.
  */
}
