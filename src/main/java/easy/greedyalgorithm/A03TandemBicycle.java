package easy.greedyalgorithm;

/*
 Problem: Tandem Bicycle

 A tandem bicycle is a bicycle that’s operated by two people: person A and person B.
 Each person rides at a certain speed. The speed of the tandem bicycle is determined by the faster rider
 (i.e., the tandem speed is the maximum speed of the two riders).

 You are given two arrays:
 - One contains the speeds of people in group A.
 - The other contains the speeds of people in group B.

 Your task is to pair riders from the two groups in such a way that:
 - You can either maximize the total speed of all tandem bicycles.
 - Or you can minimize the total speed of all tandem bicycles.

 Example:

 Input:
   groupA = [5, 5, 3, 9, 2]
   groupB = [3, 6, 7, 2, 1]
   maximizeSpeed = true
 Output:
   32

 Explanation:
 - When maximizeSpeed is true, we want to pair the fastest riders in group A with the slowest in group B.
 - This maximizes the total speed: 9 + 7 + 6 + 5 + 5 = 32.
*/

/*
 Solution Steps:

 1. If we want to maximize the total speed, pair the fastest rider from one group with the slowest rider from the other group.
 2. If we want to minimize the total speed, pair the fastest rider from one group with the fastest rider from the other group.
 3. Calculate the total speed based on the pairing strategy and return the result.
*/

import java.util.Arrays;

public class A03TandemBicycle {

  // Function to calculate the total speed of tandem bicycles
  public int calculateTandemSpeed(int[] groupA, int[] groupB, boolean maximizeSpeed) {
    // Sort both arrays in ascending order
    Arrays.sort(groupA);
    Arrays.sort(groupB);

    int totalSpeed = 0;
    int n = groupA.length;

    if (maximizeSpeed) {
      // For maximum speed, pair the fastest rider in group A with the slowest rider in group B
      for (int i = 0; i < n; i++) {
        totalSpeed += Math.max(groupA[i], groupB[n - i - 1]);
      }
    } else {
      // For minimum speed, pair the fastest rider in group A with the fastest rider in group B
      for (int i = 0; i < n; i++) {
        totalSpeed += Math.max(groupA[i], groupB[i]);
      }
    }

    return totalSpeed;
  }

  // Main function to test the Tandem Bicycle problem
  public static void main(String[] args) {
    A03TandemBicycle tandemBicycle = new A03TandemBicycle();

    // Example input
    int[] groupA = {5, 5, 3, 9, 2};
    int[] groupB = {3, 6, 7, 2, 1};

    // Calculate and print the total speed for maximizing tandem speed
    int maxSpeed = tandemBicycle.calculateTandemSpeed(groupA, groupB, true);
    System.out.println("Maximum Tandem Speed: " + maxSpeed); // Output: 32

    // Calculate and print the total speed for minimizing tandem speed
    int minSpeed = tandemBicycle.calculateTandemSpeed(groupA, groupB, false);
    System.out.println("Minimum Tandem Speed: " + minSpeed); // Output: 25
  }

  /*
   Time Complexity:
   - O(n log n), where n is the number of riders in each group. Sorting the arrays takes O(n log n) time.

   Space Complexity:
   - O(1), as no additional space is required beyond the input arrays and a few variables.
  */
}
