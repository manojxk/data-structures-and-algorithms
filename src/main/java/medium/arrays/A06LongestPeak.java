package medium.arrays;/*
 Problem: Longest Peak

 A peak is defined as adjacent integers in the array that are strictly increasing until they reach a peak (the highest value),
 at which point they become strictly decreasing. At least three numbers are required to form a peak.

 Given an array of integers, write a function that returns the length of the longest peak.

 Example:

 Input: [1, 4, 10, 2, 1, 5, 6, 3, 2, 0]
 Output: 6

 Explanation:
 The longest peak is [1, 5, 6, 3, 2, 0], and its length is 6.
*/

/*
 Solution Steps:

 1. Iterate through the array to identify peaks.
 2. A peak is identified when an element is greater than its adjacent elements (both left and right).
 3. Once a peak is identified, expand outwards in both directions (left and right) to find the length of the peak.
 4. Track the maximum length of all peaks found.
 5. Return the maximum peak length.
*/

public class A06LongestPeak {

// Solution: Linear Scan Approach
    // Time Complexity: O(n) | Space Complexity: O(1)
    public static int longestPeak(int[] array) {
        int longestPeakLength = 0;
        int i = 1;

        // Iterate through the array to find peaks
        while (i < array.length - 1) {
            // Check if current element is a peak
            boolean isPeak = array[i - 1] < array[i] && array[i] > array[i + 1];

            if (!isPeak) {
                i++;
                continue;
            }

            // Expand left and right from the peak
            int leftIdx = i - 2;
            while (leftIdx >= 0 && array[leftIdx] < array[leftIdx + 1]) {
                leftIdx--;
            }

            int rightIdx = i + 2;
            while (rightIdx < array.length && array[rightIdx] < array[rightIdx - 1]) {
                rightIdx++;
            }

            // Calculate the length of the current peak
            int currentPeakLength = rightIdx - leftIdx - 1;
            longestPeakLength = Math.max(longestPeakLength, currentPeakLength);

            // Move to the next element after the peak
            i = rightIdx;
        }

        return longestPeakLength;
    }


  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array = {4, 10, 2};
    System.out.println("Longest Peak Length: " + longestPeak(array));  // Output: 6
  }

    /*
     Time Complexity:
     - O(n), where n is the number of elements in the array. We traverse the array once and expand at each peak.

     Space Complexity:
     - O(1), since we are using only a few extra variables to track indices and peak lengths.
    */
}
