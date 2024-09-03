/*
 * Problem Statement:
 * Write a function that takes in an array of unique integers and returns its powerset.
 *
 * The powerset P(X) of a set X is the set of all possible subsets of X.
 * For example, the powerset of [1,2] is [[], [1], [2], [1,2]].
 *
 * The subsets in the powerset do not need to be in any particular order.
 *
 * Example:
 * Input: array = [1, 2, 3]
 * Output: [[], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]
 */
/*Backtracking Approach
Approach:
The backtracking approach systematically explores all potential subsets by either including or excluding each element. The solution uses a recursive function that keeps track of the current subset and explores all possible subsets by iterating over the remaining elements.

Time Complexity:
O(n * 2^n): The backtracking approach generates all
2
𝑛
2
n
subsets. For each subset, it takes O(n) time to copy it to the result.
Space Complexity:
O(n * 2^n): The space complexity accounts for storing all subsets and the recursive call stack.*/
package medium.recursion;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {

  private static List<List<Integer>> powerset = new ArrayList<>();
  private static List<Integer> subset = new ArrayList<>();

  // Backtracking function to generate the powerset
  private static void backtrack(int start, int[] nums) {
    powerset.add(new ArrayList<>(subset)); // Add the current subset to the powerset

    for (int i = start; i < nums.length; i++) {
      subset.add(nums[i]); // Include the current element
      backtrack(i + 1, nums); // Recurse to the next element
      subset.remove(subset.size() - 1); // Backtrack by removing the last element
    }
  }

  // Function to initiate the backtracking and return the powerset
  public static List<List<Integer>> subsets(int[] nums) {
    backtrack(0, nums); // Start the backtracking from the first element
    return powerset; // Return the generated powerset
  }

  public static void main(String[] args) {

    int[] array = {1, 2, 3};
    List<List<Integer>> result = subsets(array);
    System.out.println(result);
    // Output: [[], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]]
  }
}
