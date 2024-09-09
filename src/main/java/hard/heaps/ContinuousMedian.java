package hard.heaps;

/**
 * Problem Statement:
 *
 * <p>Implement a class called `ContinuousMedianHandler` that supports continuous insertion of
 * numbers and allows retrieval of the median of all inserted numbers in constant time O(1).
 *
 * <p>The `insert` method inserts a number into the data structure, and the `getMedian` method
 * returns the median of all the numbers inserted so far.
 *
 * <p>The median of a set of numbers is the "middle" number when they are ordered from smallest to
 * largest. If the set has an odd number of numbers, the median is the number in the middle. If the
 * set has an even number of numbers, the median is the average of the two middle numbers.
 *
 * <p>Time Complexity: - `insert`: O(log n) due to the insertion into heaps. - `getMedian`: O(1)
 * because retrieving the median is instantaneous.
 *
 * <p>Space Complexity: - O(n), where `n` is the number of numbers inserted into the data structure.
 *
 * <p>Sample Usage: ContinuousMedianHandler medianHandler = new ContinuousMedianHandler();
 * medianHandler.insert(5); medianHandler.insert(10); System.out.println(medianHandler.getMedian());
 * // 7.5 medianHandler.insert(100); System.out.println(medianHandler.getMedian()); // 10
 */
import java.util.PriorityQueue;

public class ContinuousMedian {
  // Min heap to store the larger half of numbers
  private PriorityQueue<Integer> minHeap;
  // Max heap to store the smaller half of numbers
  private PriorityQueue<Integer> maxHeap;
  private double median;

  // Constructor to initialize the heaps
  public ContinuousMedian() {
    minHeap = new PriorityQueue<>(); // Min heap for larger numbers
    maxHeap = new PriorityQueue<>((a, b) -> b - a); // Max heap for smaller numbers
    median = 0;
  }

  // Insert a number into the data structure
  public void insert(int number) {
    // Insert into max heap if number is less than or equal to the maxHeap's root
    if (maxHeap.isEmpty() || number <= maxHeap.peek()) {
      maxHeap.offer(number);
    } else {
      minHeap.offer(number);
    }

    // Balance the heaps: maxHeap should either have the same number of elements or one more than
    // minHeap
    if (maxHeap.size() > minHeap.size() + 1) {
      minHeap.offer(maxHeap.poll());
    } else if (minHeap.size() > maxHeap.size()) {
      maxHeap.offer(minHeap.poll());
    }

    // Update the median based on the size of heaps
    if (maxHeap.size() == minHeap.size()) {
      median = (maxHeap.peek() + minHeap.peek()) / 2.0;
    } else {
      median = maxHeap.peek();
    }
  }

  // Retrieve the median of numbers inserted so far
  public double getMedian() {
    return median;
  }

  // Main function to test the implementation
  public static void main(String[] args) {
    ContinuousMedian medianHandler = new ContinuousMedian();
    medianHandler.insert(5);
    medianHandler.insert(10);
    System.out.println("Median: " + medianHandler.getMedian()); // 7.5
    medianHandler.insert(100);
    System.out.println("Median: " + medianHandler.getMedian()); // 10.0
  }
}
