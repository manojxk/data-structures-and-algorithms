package medium.arrays;

/*
 Problem: First Duplicate Value

 Given an array of integers where each integer is between 1 and n (inclusive), where n is the length of the array,
 find the first integer that appears more than once in the array. The first duplicate should have the minimal index.

 Example:

 Input: [2, 1, 3, 5, 3, 2]
 Output: 3

 Explanation:
 The integer 3 is the first duplicate because it appears again after its first occurrence,
 and it has the minimal index compared to 2 (which also appears twice, but later).
*/

/*
 Solution Steps:

 1. Use the fact that the numbers are in the range 1 to n, and use the array itself to track duplicates by marking visited numbers.
 2. Iterate through the array, for each element, check if the number corresponding to the index (based on the value of the element) has been visited:
    - If it has been visited (i.e., the value is negative), return the element as the first duplicate.
    - If it has not been visited, mark it by negating the value at the corresponding index.
 3. If no duplicates are found, return -1.
*/

import java.util.HashSet;

public class A08FirstDuplicateValue {
  /*  Brute Force Solution
  Approach:
  The brute force solution involves using two nested loops. For each element, we check if it appears again in the subsequent elements.

  Time Complexity:
  O(n^2): We have a nested loop where each element is compared with all other elements, leading to quadratic time complexity.
  Space Complexity:
  O(1): The solution only uses a constant amount of extra space.*/
  public static int firstDuplicateValueBruteForce(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = i + 1; j < array.length; j++) {
        if (array[i] == array[j]) {
          return array[i];
        }
      }
    }
    return -1; // Return -1 if no duplicate is found
  }

  /*    Optimized Solution 1: Using a HashSet
  Approach:
  We can optimize the solution by using a HashSet to keep track of the elements we’ve already seen. As we iterate through the array, we check if the current element is already in the HashSet. If it is, then it’s the first duplicate.

  Time Complexity:
  O(n): We iterate through the array once, making this solution linear in time.
  Space Complexity:
  O(n): The HashSet requires space proportional to the number of elements in the array.*/
  public static int firstDuplicateValueHashSet(int[] array) {
    HashSet<Integer> seen = new HashSet<>();
    for (int value : array) {
      if (seen.contains(value)) {
        return value;
      } else {
        seen.add(value);
      }
    }
    return -1; // Return -1 if no duplicate is found
  }

  // Function to find the first duplicate value
  public static int firstDuplicateValue(int[] array) {
    // Step 1: Traverse the array
    for (int i = 0; i < array.length; i++) {
      int absValue = Math.abs(array[i]); // Get the absolute value of the current element

      // Step 2: Check if the value at index absValue - 1 is negative (i.e., it has been visited)
      if (array[absValue - 1] < 0) {
        return absValue; // First duplicate found
      }

      // Step 3: Mark the element as visited by negating the value at index absValue - 1
      array[absValue - 1] = -array[absValue - 1];
    }

    return -1; // If no duplicate is found, return -1
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    int[] array = {2, 1, 3, 5, 3, 2};
    System.out.println("First duplicate value: " + firstDuplicateValue(array)); // Output: 3
  }

  /*
   Time Complexity:
   - O(n), where n is the length of the array. We traverse the array once, and each lookup and modification is O(1).

   Space Complexity:
   - O(1), since we are modifying the array in place and only using a few extra variables.
  */
}
