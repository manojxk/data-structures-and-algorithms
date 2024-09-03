package medium.arrays;

public class LongestPeak {
  public static int longestPeak(int[] array) {
    int longestPeakLength = 0;
    int i = 1;
    while (i < array.length - 1) {
      // Check if the current element is a peak.
      boolean isPeak = array[i - 1] < array[i] && array[i] > array[i + 1];
      if (!isPeak) {
        i++;
        continue;
      }

      // If the current element is a peak, expand outwards to find the length of the peak.
      int leftIdx = i - 2;
      while (leftIdx >= 0 && array[leftIdx] < array[leftIdx + 1]) {
        leftIdx--;
      }
      int rightIdx = i + 2;
      while (rightIdx < array.length && array[rightIdx] < array[rightIdx - 1]) {
        rightIdx++;
      }

      // Update the longest peak length if necessary.
      int peakLength = rightIdx - leftIdx - 1;
      longestPeakLength = Math.max(longestPeakLength, peakLength);

      // Move the pointer to the right side of the peak.
      i = rightIdx;
    }

    return longestPeakLength;
  }

  public static void main(String[] args) {
    int[] array = {1, 2, 3, 3, 4, 0, 10, 6, 5, -1, -3, 2, 3};
    System.out.println(longestPeak(array)); // Expected Output: 6
  }
}

/*Time Complexity:
O(n): The array is traversed a constant number of times, ensuring that the solution is linear in relation to the input size.
Space Complexity:
O(1): Only a few variables are used to store indices and lengths, resulting in constant space usage.*/

/*
Explanation:
Peak Identification:

The loop begins at index 1 and ends at array.length - 2, ensuring that we always have a left and right neighbor to compare.
isPeak checks if the current element is greater than both its neighbors.
Boundary Expansion:

Once a peak is found, the algorithm expands to the left (leftIdx) and right (rightIdx) to find the full extent of the peak.
The length of the peak is calculated as rightIdx - leftIdx - 1.
Result:

The length of the longest peak found during the traversal is returned.
*/
