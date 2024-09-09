package cools.dp.knapsack;

/*Given an array arr[] of size N and a given difference diff, the task is to count the number of partitions that we can perform such that the difference between the sum of the two subsets is equal to the given difference.
 Note: A partition in the array means dividing an array into two parts say S1 and S2 such that the union of S1 and S2 is equal to the original array and each element is present in only of the subsets.

Examples:

Input: N = 4, arr[] = [5, 2, 6, 4], diff = 3
Output: 1
Explanation: We can only have a single partition which is shown below:
        {5, 2} and {6, 4} such that S1 = 7 and S2 = 10 and thus the difference is 3

Input: N = 5, arr[] = [1, 2, 3, 1, 2], diff = 1
Output: 5
Explanation: We can have five partitions which is shown below
{1, 3, 1} and {2, 2} – S1 = 5, S2 = 4
        {1, 2, 2} and {1, 3} – S1 = 5, S2 = 4
        {3, 2} and {1, 1, 2} – S1 = 5, S2 = 4
        {1, 2, 2} and {1, 3} – S1 = 5, S2 = 4
        {3, 2} and {1, 1, 2} – S1 = 5, S2 = 4*/

/*S1−S2=diff
S1+S2=sum_arr
S1=(sum_arr+diff)/2
Now we need to count subsets with sum of S1.
*/

public class A06CountOfSubsetsWithGivenDifference {

  // Function to count partitions with given difference
  public static int countPartitions(int[] arr, int n, int diff) {
    // Calculate the sum of the array
    int sum_arr = 0;
    for (int num : arr) {
      sum_arr += num;
    }

    // Calculate the target sum S1 that needs to be achieved
    int target = (sum_arr + diff) / 2;

    // Use the countSubsetsWithSum function to count the number of subsets with sum target
    return A04CountofSubsetsSumWithGivenSum.countSubsetsMemoization(arr, n, target);
  }

  public static void main(String[] args) {
    int[] arr1 = {5, 2, 6, 4};
    int diff1 = 3;
    System.out.println(
        "Number of partitions: " + countPartitions(arr1, arr1.length, diff1)); // Output: 1

    int[] arr2 = {1, 2, 3, 1, 2};
    int diff2 = 1;
    System.out.println(
        "Number of partitions: " + countPartitions(arr2, arr2.length, diff2)); // Output: 5
  }
}
