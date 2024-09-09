package hard.sorting;

/**
 * Problem Statement:
 *
 * <p>You are given an array of integers. The task is to return a sorted version of that array using
 * the Radix Sort algorithm.
 *
 * <p>Radix Sort is a non-comparative sorting algorithm that sorts integers by processing individual
 * digits. It works by sorting the numbers starting from the least significant digit to the most
 * significant digit using a stable sorting algorithm (such as Counting Sort).
 *
 * <p>Sample Input: array = [170, 45, 75, 90, 802, 24, 2, 66]
 *
 * <p>Sample Output: [2, 24, 45, 66, 75, 90, 170, 802]
 */
import java.util.Arrays;

public class RadixSort {

  // Radix Sort Algorithm
  // Time Complexity: O(d * (n + b)) - where 'd' is the number of digits, 'n' is the number of
  // elements, and 'b' is the base (10 for decimal system).
  // Space Complexity: O(n + b) - Additional space for the counting sort and bucket storage.
  public static void radixSort(int[] array) {
    // Find the maximum number to know the number of digits
    int max = getMax(array);

    // Perform counting sort for each digit
    // Start with the least significant digit (units place), move to the tens, hundreds, etc.
    for (int exp = 1; max / exp > 0; exp *= 10) {
      countingSortByDigit(array, exp);
    }
  }

  // Helper function to get the maximum value in the array
  private static int getMax(int[] array) {
    int max = array[0];
    for (int num : array) {
      if (num > max) {
        max = num;
      }
    }
    return max;
  }

  // Counting Sort based on the digit represented by `exp`
  // `exp` is 1 for units place, 10 for tens place, 100 for hundreds place, etc.
  private static void countingSortByDigit(int[] array, int exp) {
    int n = array.length;
    int[] output = new int[n]; // Output array to store sorted numbers
    int[] count =
        new int[10]; // Since digits are in the range [0-9], we use a fixed size array for counting

    // Store the count of occurrences of digits
    for (int i = 0; i < n; i++) {
      int digit = (array[i] / exp) % 10;
      count[digit]++;
    }

    // Change count[i] so that it now contains the actual position of the digit in the output array
    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }

    // Build the output array by placing elements in their sorted order
    for (int i = n - 1; i >= 0; i--) {
      int digit = (array[i] / exp) % 10;
      output[count[digit] - 1] = array[i];
      count[digit]--;
    }

    // Copy the sorted numbers back to the original array
    for (int i = 0; i < n; i++) {
      array[i] = output[i];
    }
  }

  // Main function to test the Radix Sort implementation
  public static void main(String[] args) {
    int[] array = {170, 45, 75, 90, 802, 24, 2, 66};

    System.out.println("Original Array: " + Arrays.toString(array));

    radixSort(array);

    System.out.println("Sorted Array (Radix Sort): " + Arrays.toString(array));
  }
}
