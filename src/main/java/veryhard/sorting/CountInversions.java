package veryhard.sorting; /*
                           Problem: Count Inversions

                           Inversion Count for an array indicates how far (or close) the array is from being sorted.
                           If array is already sorted, the inversion count is 0.
                           If the array is sorted in reverse order, the inversion count is the maximum.

                           An inversion occurs when for any pair of indices i and j (i < j), arr[i] > arr[j].

                           Example:

                           Input: [8, 4, 2, 1]
                           Output: 6

                           Explanation:
                           The six inversions are:
                           (8,4), (8,2), (8,1), (4,2), (4,1), and (2,1).

                           Another Example:

                           Input: [3, 1, 2]
                           Output: 2

                           Explanation:
                           The two inversions are:
                           (3,1) and (3,2).
                          */

/*
 Solution Steps:

 1. We can count inversions in O(n log n) time using a modified merge sort.
 2. During the merge step, we count the number of inversions where an element from the right half
    is smaller than an element from the left half.
 3. The total number of inversions is the sum of inversions in the left half, the right half,
    and the number of inversions counted during the merge step.
*/

public class CountInversions {

  // Function to count inversions using merge sort
  public int countInversions(int[] arr) {
    return mergeSortAndCount(arr, 0, arr.length - 1);
  }

  // Helper function to perform merge sort and count inversions
  private int mergeSortAndCount(int[] arr, int left, int right) {
    int invCount = 0;
    if (left < right) {
      // Find the middle point
      int mid = left + (right - left) / 2;

      // Recursively count inversions in the left half
      invCount += mergeSortAndCount(arr, left, mid);

      // Recursively count inversions in the right half
      invCount += mergeSortAndCount(arr, mid + 1, right);

      // Merge the two halves and count the inversions during merge
      invCount += mergeAndCount(arr, left, mid, right);
    }
    return invCount;
  }

  // Helper function to merge two halves and count inversions during the merge
  private int mergeAndCount(int[] arr, int left, int mid, int right) {
    // Create temporary arrays for the left and right halves
    int n1 = mid - left + 1;
    int n2 = right - mid;

    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    // Copy data to temporary arrays
    for (int i = 0; i < n1; i++) {
      leftArray[i] = arr[left + i];
    }
    for (int j = 0; j < n2; j++) {
      rightArray[j] = arr[mid + 1 + j];
    }

    // Merge the temporary arrays and count inversions
    int i = 0, j = 0, k = left;
    int invCount = 0;

    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        arr[k] = leftArray[i];
        i++;
      } else {
        // There are n1 - i inversions, because all the remaining elements in the left array
        // are greater than the current element in the right array
        arr[k] = rightArray[j];
        invCount += (n1 - i);
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

    return invCount;
  }

  // Main function to test the Count Inversions implementation
  public static void main(String[] args) {
    CountInversions inversionCounter = new CountInversions();

    // Example array
    int[] arr = {8, 4, 2, 1};

    System.out.println("Original Array:");
    for (int num : arr) {
      System.out.print(num + " ");
    }

    // Count and print the number of inversions
    int invCount = inversionCounter.countInversions(arr);
    System.out.println("\nNumber of inversions: " + invCount); // Output: 6
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of elements in the array. This is because we use a modified merge sort algorithm that recursively splits the array and counts inversions.

   Space Complexity:
   - O(n), as the merge sort algorithm requires additional space for the temporary arrays used during the merge process.
  */
}
