package cools.backtracking;

/*
Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.

*/

import java.util.ArrayList;
import java.util.List;

public class A05Permutations {

  public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPermutation = new ArrayList<>();
    boolean[] used = new boolean[nums.length]; // New Change: To track used elements
    backtrack(nums, result, currentPermutation, used);
    return result;
  }

  private void backtrack(
      int[] nums, List<List<Integer>> result, List<Integer> currentPermutation, boolean[] used) {
    // Base case: If the current permutation is complete, add it to the result
    if (currentPermutation.size() == nums.length) {
      result.add(new ArrayList<>(currentPermutation));
      return;
    }

    // Try each number that has not been used yet in the current permutation
    for (int i = 0; i < nums.length; i++) {
      if (used[i]) continue; // Skip if the number has already been used

      // Choose the number
      currentPermutation.add(nums[i]);
      used[i] = true; // Mark the number as used

      // Recurse with the updated permutation
      backtrack(nums, result, currentPermutation, used);

      // Backtrack: remove the number and mark it as unused
      currentPermutation.remove(currentPermutation.size() - 1);
      used[i] = false;
    }
  }

  public static void main(String[] args) {
    A05Permutations solution = new A05Permutations();
    int[] nums1 = {1, 2, 3};
    System.out.println(solution.permute(nums1));
    // Output: [[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
  }
}
