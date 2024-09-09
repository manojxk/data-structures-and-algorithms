package cools.dp.knapsack;

/*The partition problem is to determine whether a given set can be partitioned into two subsets such that the sum of elements in both subsets is the same.

Examples:

Input: arr[] = {1, 5, 11, 5}
Output: true
The array can be partitioned as {1, 5, 5} and {11}

Input: arr[] = {1, 5, 3}
Output: false
The array cannot be partitioned into equal sum sets.*/

public class A03EqualSumPartition {

  public static boolean canPartition(int[] arr) {
    int totalSum = 0;

    // Find the total sum of the array
    for (int num : arr) {
      totalSum += num;
    }

    // If the total sum is odd, we cannot partition the set into two equal subsets
    if (totalSum % 2 != 0) {
      return false;
    }

    // Find if there exists a subset with sum equal to total_sum / 2

    return A02SubsetSum.isSubsetSumMemoization(arr, arr.length, totalSum / 2);
  }

  public static void main(String[] args) {
    int[] arr = {1, 5, 11, 5};
    System.out.println("Memoization result: " + canPartition(arr)); // Output: true
  }
}
