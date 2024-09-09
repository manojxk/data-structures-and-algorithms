package cools.arrays;

/*
 Problem: H-Index

 Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper,
 return the researcher's h-index.

 The h-index is defined as the maximum value of h such that the given researcher has published at least h papers that have each been
 cited at least h times.

 Constraints:
 - citations.length >= 1
 - 0 <= citations[i] <= 1000

 Example 1:
 Input: citations = [3, 0, 6, 1, 5]
 Output: 3
 Explanation:
 The researcher has 5 papers in total.
 - 3 papers have at least 3 citations each (3, 6, and 5), and the remaining 2 papers have fewer than 3 citations (0 and 1).
 Hence, the h-index is 3.

 Example 2:
 Input: citations = [1, 3, 1]
 Output: 1

 Solution Approach:
 1. Sort the citations array in descending order.
 2. For each citation, find the maximum value of h such that the researcher has published at least h papers with h or more citations.

*/

import java.util.Arrays;

public class A11HIndex {

  // Function to calculate the H-Index
  public int hIndex(int[] citations) {
    // Step 1: Sort the citations array in descending order
    Arrays.sort(citations);

    int n = citations.length;

    // Step 2: Find the maximum h-index
    for (int i = 0; i < n; i++) {
      int h = n - i; // h is the number of papers with at least citations[i] citations
      if (citations[i] >= h) {
        return h; // Return h-index as soon as we find a valid h
      }
    }

    return 0; // If no valid h-index found, return 0
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A11HIndex solution = new A11HIndex();

    // Example 1
    int[] citations1 = {3, 0, 6, 1, 5};
    System.out.println("H-Index: " + solution.hIndex(citations1)); // Output: 3

    // Example 2
    int[] citations2 = {1, 3, 1};
    System.out.println("H-Index: " + solution.hIndex(citations2)); // Output: 1
  }

  /*
   Time Complexity:
   - O(n log n), where n is the length of the citations array. The array is sorted first (O(n log n)) and then scanned once (O(n)).

   Space Complexity:
   - O(1), since the sorting is done in place, and we use only constant extra space for the calculation.
  */
}
