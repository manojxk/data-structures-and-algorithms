/*
 * Problem Statement:
 * You are in a theatre and need to find the best seat in a given row.
 * The row is represented as an integer array where '1' represents an occupied seat
 * and '0' represents an empty seat.
 * You want to sit in a seat that gives you the most space, preferably with equal space on both sides.
 *
 * Constraints:
 * - Someone is always sitting in the first and last seat of the row.
 * - If there are two equally good seats, you should choose the one with the lower index.
 * - If there is no seat available, return -1.
 * - The array will have at least one seat and will contain only ones and zeroes.
 *
 * Example:
 * Input: seats = [1, 0, 1, 0, 0, 0, 1]
 * Output: 4
 * // The best seat is at index 4, with equal space on both sides.
 */
package medium.arrays;

public class BestSeat {
  // O(n) time | O(1) space - where n is the number of seats
  public static int bestSeat(int[] seats) {
    int bestSeat = -1;
    int maxSpace = 0;

    int left = 0;
    while (left < seats.length) {
      int right = left + 1;
      while (right < seats.length && seats[right] == 0) {
        right++;
      }

      int availableSpace = right - left - 1;
      if (availableSpace > maxSpace) {
        bestSeat = (left + right) / 2;
        maxSpace = availableSpace;
      }
      left = right;
    }

    return bestSeat;
  }

  public static void main(String[] args) {
    int[] seats = {1, 0, 0, 1, 0, 0, 0, 1};
    System.out.println(bestSeat(seats)); // Output: 4
  }
}
