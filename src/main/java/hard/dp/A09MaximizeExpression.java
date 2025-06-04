package hard.dp;

/*
 Problem: Maximize Expression

 You are given an array of integers, and you need to maximize the following expression:

 expression = array[a] - array[b] + array[c] - array[d]

 where a < b < c < d. Your goal is to find the maximum possible value of this expression.

 Example 1:
 Input: array = [3, 6, 1, -3, 2, 7]
 Output: 17
 Explanation:
 The expression is maximized by choosing a = 1, b = 3, c = 4, and d = 5, resulting in:
 expression = array[1] - array[3] + array[4] - array[5]
            = 6 - (-3) + 2 - 7
            = 6 + 3 + 2 - 7
            = 17

 Constraints:
 - The input array can have at most 1000 elements.
 - The values in the array can be negative or positive.

 Solution Approach:
 1. The goal is to maximize the expression by choosing valid indices such that a < b < c < d.
 2. We can solve this problem in O(n) time by using dynamic programming to store the best possible values for each stage of the expression:
    - maxA stores the maximum value of array[a].
    - maxAminusB stores the maximum value of array[a] - array[b].
    - maxAminusBplusC stores the maximum value of array[a] - array[b] + array[c].
    - maxAminusBplusCminusD stores the final value of array[a] - array[b] + array[c] - array[d].
*/

public class A08MaximizeExpression {

  // Function to maximize the expression
  public static int maximizeExpression(int[] array) {
    // If the array has fewer than 4 elements, it's not possible to compute the expression
    if (array.length < 4) {
      return 0;
    }

    // Step 1: Initialize the dynamic programming arrays
    int n = array.length;
    int[] maxA = new int[n];
    int[] maxAminusB = new int[n];
    int[] maxAminusBplusC = new int[n];
    int[] maxAminusBplusCminusD = new int[n];

    // Step 2: Populate the maxA array (maximum value of array[a])
    maxA[0] = array[0];
    for (int i = 1; i < n; i++) {
      maxA[i] = Math.max(maxA[i - 1], array[i]);
    }

    // Step 3: Populate the maxAminusB array (maximum value of array[a] - array[b])
    maxAminusB[1] = maxA[0] - array[1];
    for (int i = 2; i < n; i++) {
      maxAminusB[i] = Math.max(maxAminusB[i - 1], maxA[i - 1] - array[i]);
    }

    // Step 4: Populate the maxAminusBplusC array (maximum value of array[a] - array[b] + array[c])
    maxAminusBplusC[2] = maxAminusB[1] + array[2];
    for (int i = 3; i < n; i++) {
      maxAminusBplusC[i] = Math.max(maxAminusBplusC[i - 1], maxAminusB[i - 1] + array[i]);
    }

    // Step 5: Populate the maxAminusBplusCminusD array (maximum value of array[a] - array[b] +
    // array[c] - array[d])
    maxAminusBplusCminusD[3] = maxAminusBplusC[2] - array[3];
    for (int i = 4; i < n; i++) {
      maxAminusBplusCminusD[i] =
          Math.max(maxAminusBplusCminusD[i - 1], maxAminusBplusC[i - 1] - array[i]);
    }

    // Step 6: The final result is stored in maxAminusBplusCminusD[n - 1]
    return maxAminusBplusCminusD[n - 1];
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Example 1
    int[] array1 = {3, 6, 1, -3, 2, 7};
    System.out.println(
        "Maximum expression value (Example 1): " + maximizeExpression(array1)); // Output: 17

    // Example 2
    int[] array2 = {1, 2, 3, 4};
    System.out.println(
        "Maximum expression value (Example 2): "
            + maximizeExpression(array2)); // Output: 0 (no valid a, b, c, d possible)
  }

  /*
   Time Complexity:
   - O(n), where n is the number of elements in the array. We iterate through the array several times (for each stage of the expression).

   Space Complexity:
   - O(n), where n is the number of elements in the array. We use four additional arrays to store the maximum values for each stage.
  */
}
