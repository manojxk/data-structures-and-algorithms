package hard.dp;

/**
 * Problem Statement:
 *
 * <p>Given an array of integers, you need to find the largest possible value for the expression
 * `array[a] - array[b] + array[c] - array[d]`, where `a < b < c < d` are indices of the array.
 *
 * <p>If the array has fewer than 4 elements, the function should return 0.
 *
 * <p>Example: array = [3, 6, 1, -3, 2, 7]
 *
 * <p>The expression that maximizes the result is: array[0] - array[1] + array[4] - array[5] = 3 - 6
 * + 2 - 7 = -1 + 2 = 4
 *
 * <p>Sample Input: array = [3, 6, 1, -3, 2, 7]
 *
 * <p>Sample Output: 4
 */
public class MaxExpressionValue {

  // Optimized Approach using Dynamic Programming
  // Time Complexity: O(n), where n is the length of the array. We process the array in 4 passes.
  // Space Complexity: O(1), as we only use a few extra variables.
  public static int maxExpressionValue(int[] array) {
    int n = array.length;

    // If the array has fewer than 4 elements, return 0 as the problem constraints state
    if (n < 4) return 0;

    // Initialize variables to store the maximum values of the subexpressions
    int maxA = Integer.MIN_VALUE; // max(array[a])
    int maxA_minus_B = Integer.MIN_VALUE; // max(array[a] - array[b])
    int maxA_minus_B_plus_C = Integer.MIN_VALUE; // max(array[a] - array[b] + array[c])
    int maxExpression = Integer.MIN_VALUE; // max(array[a] - array[b] + array[c] - array[d])

    // Traverse through the array to calculate the maximum expression value
    for (int i = 0; i < n; i++) {
      // Calculate the value of array[a] - array[b] + array[c] - array[d]
      if (i >= 3) {
        maxExpression = Math.max(maxExpression, maxA_minus_B_plus_C - array[i]);
      }

      // Calculate the value of array[a] - array[b] + array[c]
      if (i >= 2) {
        maxA_minus_B_plus_C = Math.max(maxA_minus_B_plus_C, maxA_minus_B + array[i]);
      }

      // Calculate the value of array[a] - array[b]
      if (i >= 1) {
        maxA_minus_B = Math.max(maxA_minus_B, maxA - array[i]);
      }

      // Calculate the value of array[a]
      maxA = Math.max(maxA, array[i]);
    }

    return maxExpression;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    // Sample Input
    int[] array = {3, 6, 1, -3, 2, 7};

    // Output the result
    System.out.println("Max Expression Value: " + maxExpressionValue(array)); // Expected output: 4
  }
}
