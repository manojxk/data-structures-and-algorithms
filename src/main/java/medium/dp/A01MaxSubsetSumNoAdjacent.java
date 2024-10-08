/*
 * Problem Statement:
 * Write a function that takes in an array of positive integers and returns the maximum sum of non-adjacent elements in the array.
 * If the input array is empty, the function should return 0.
 *
 * Example:
 *
 * array = [75, 105, 120, 75, 90, 135]
 *
 * Output:
 * 330  // Explanation: 75 + 120 + 135
 */

/*
 * Brute Force Solution (Recursive):
 * Approach:
 * The brute force approach involves exploring all subsets of non-adjacent elements and finding the subset with the maximum sum.
 * This can be achieved using recursion to generate all combinations of non-adjacent elements.
 *
 * Time Complexity:
 * O(2^n): The time complexity is exponential since we consider each element either to be included or excluded.
 *
 * Space Complexity:
 * O(n): The space complexity is due to the recursion stack.
 */

package medium.dp;

public class A01MaxSubsetSumNoAdjacent {

  // Brute Force Solution: Recursive Approach
  public static int maxSubsetSumNoAdjacent(int[] array) {
    if (array.length == 0) return 0;
    return maxSubsetSumNoAdjacentHelper(array, array.length - 1);
  }

  // Helper method to recursively find the maximum sum of non-adjacent elements
  private static int maxSubsetSumNoAdjacentHelper(int[] array, int index) {
    if (index < 0) return 0;
    if (index == 0) return array[0];

    // Include current element and skip the adjacent one
    int include = array[index] + maxSubsetSumNoAdjacentHelper(array, index - 2);

    // Exclude current element and move to the next one
    int exclude = maxSubsetSumNoAdjacentHelper(array, index - 1);

    return Math.max(include, exclude);
  }

  /*
   * Optimized Solution (Dynamic Programming):
   * Approach:
   * The optimized approach uses dynamic programming to keep track of the maximum sum up to each element while ensuring that no adjacent elements are included.
   * The key idea is to maintain two variables: one for the maximum sum including the current element and one for the maximum sum excluding the current element.
   *
   * Time Complexity:
   * O(n): Linear time complexity since we only traverse the array once.
   *
   * Space Complexity:
   * O(1): Constant space complexity as we only use a few variables for tracking the sums.
   */

  // Optimized Solution: Dynamic Programming Approach
  public static int maxSubsetSumNoAdjacentDP(int[] array) {
    if (array.length == 0) return 0;
    if (array.length == 1) return array[0];

    // Initialize two variables to store the maximum sums
    int prevTwo = array[0];
    int prevOne = Math.max(array[0], array[1]);

    for (int i = 2; i < array.length; i++) {
      int current = Math.max(prevOne, prevTwo + array[i]);
      prevTwo = prevOne;
      prevOne = current;
    }

    return prevOne;
  }

  public static void main(String[] args) {
    int[] array = {75, 105, 120, 75, 90, 135};
    System.out.println(maxSubsetSumNoAdjacent(array)); // Output: 330
    System.out.println(maxSubsetSumNoAdjacentDP(array)); // Output: 330
  }

  /*
   * Additional Dynamic Programming Solution (For reference):
   * Time Complexity: O(n) | Space Complexity: O(n)
   * We can use an array dp[] to track the maximum sum till each index.
   */

  static int findMaxSubsetSum(int arr[]) {
    int n = arr.length;
    if (n == 1) return arr[0]; // If there's only one element, return it
    if (n == 2) return Math.max(arr[0], arr[1]); // If there are two elements, return the maximum

    int dp[] = new int[n]; // dp[i] will store the max sum till index i
    dp[0] = arr[0]; // First element's max sum is itself
    dp[1] = Math.max(arr[0], arr[1]); // Max of first two elements

    // Fill remaining positions
    for (int i = 2; i < n; i++) {
      dp[i] = Math.max(arr[i] + dp[i - 2], dp[i - 1]);
    }

    return dp[n - 1]; // The last element in dp will hold the result
  }

  /*
   * Time Complexities:
   * Brute Force Solution (Recursive): O(2^n)
   * Optimized Solution (Dynamic Programming): O(n)
   *
   * Space Complexities:
   * Brute Force Solution (Recursive): O(n)
   * Optimized Solution (Dynamic Programming): O(1)
   */
}
