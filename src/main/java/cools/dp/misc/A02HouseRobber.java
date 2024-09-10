package cools.dp.misc;

/*
 Problem: House Robber

 You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 but adjacent houses have security systems connected, so you can't rob two adjacent houses.

 Given an integer array nums representing the amount of money in each house, return the maximum amount of money you can rob
 tonight without alerting the police.

 Example 1:
 Input: nums = [1,2,3,1]
 Output: 4
 Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.

 Example 2:
 Input: nums = [2,7,9,3,1]
 Output: 12
 Explanation: Rob house 1 (money = 2), rob house 3 (money = 9), and rob house 5 (money = 1). Total amount you can rob = 2 + 9 + 1 = 12.

 Constraints:
 - 1 <= nums.length <= 100
 - 0 <= nums[i] <= 400

 Solution Approach:
 1. This is a dynamic programming problem where the decision to rob each house depends on the decision for previous houses.
 2. For each house i, the decision is to either:
    - Rob the current house (i) and add its money to the result of house (i-2), or
    - Skip robbing the current house and take the result from house (i-1).
 3. The recurrence relation becomes:
    - dp[i] = max(dp[i-1], dp[i-2] + nums[i])
 4. The base cases are:
    - If there is only 1 house, the maximum money is the value of the first house.
    - If there are 2 houses, the maximum money is the maximum of the two houses.

*/

public class A02HouseRobber {

  // Function to return the maximum amount of money that can be robbed
  public int rob(int[] nums) {
    // Base case: if there is only one house
    if (nums.length == 1) {
      return nums[0];
    }

    // Base case: if there are two houses
    if (nums.length == 2) {
      return Math.max(nums[0], nums[1]);
    }

    // Variables to store the maximum amount robbed from the previous two houses
    int twoHousesBefore = nums[0];
    int oneHouseBefore = Math.max(nums[0], nums[1]);

    // Iterate over the remaining houses to calculate the maximum amount of money
    for (int i = 2; i < nums.length; i++) {
      int currentHouse = Math.max(oneHouseBefore, twoHousesBefore + nums[i]);
      twoHousesBefore = oneHouseBefore; // Update the value for two houses before
      oneHouseBefore = currentHouse; // Update the value for the current house
    }

    return oneHouseBefore; // This holds the maximum money that can be robbed
  }

  static int findMaxSubsetSumCopilot(int arr[]) {
    int n = arr.length;
    if (n == 1) return arr[0]; // If there's only one element, return it
    if (n == 2) return Math.max(arr[0], arr[1]); // If there are two elements, return the maximum

    // dp[i] will be max sum till index i such that we do not include any two elements that are
    // adjacent
    int dp[] = new int[n];

    dp[0] = arr[0]; // For first element, max sum will be the element itself
    dp[1] =
            Math.max(
                    arr[0],
                    arr[1]); // For second element, max sum will be the maximum of first and second elements

    // Fill remaining positions
    for (int i = 2; i < n; i++) {
      dp[i] = Math.max(arr[i] + dp[i - 2], dp[i - 1]);
    }

    return dp[n - 1]; // The last element of dp will be our answer
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    A02HouseRobber solution = new A02HouseRobber();

    // Example 1
    int[] nums1 = {1, 2, 3, 1};
    System.out.println("Maximum money that can be robbed: " + solution.rob(nums1)); // Output: 4

    // Example 2
    int[] nums2 = {2, 7, 9, 3, 1};
    System.out.println("Maximum money that can be robbed: " + solution.rob(nums2)); // Output: 12
  }

  /*
   Time Complexity:
   - O(n), where n is the number of houses. We iterate through the list of houses once.

   Space Complexity:
   - O(1), since we are only using a constant amount of extra space to store variables for the previous two houses.
  */
}
