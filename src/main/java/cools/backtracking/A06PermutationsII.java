package cools.backtracking;

/*Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A06PermutationsII {

  public List<List<Integer>> permuteUnique(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPermutation = new ArrayList<>();
    boolean[] used = new boolean[nums.length]; // To track used elements
    Arrays.sort(nums); // Sort the input array to handle duplicates
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

    // Try each number that hasn't been used yet in the current permutation
    for (int i = 0; i < nums.length; i++) {
      // Skip duplicates: if nums[i] == nums[i - 1] and nums[i - 1] hasn't been used in this branch,
      // skip
      if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
        continue;
      }

      // If the number is already used in this permutation, skip it
      if (used[i]) {
        continue;
      }

      // Choose the current number
      currentPermutation.add(nums[i]);
      used[i] = true; // Mark it as used

      // Recurse with the updated permutation
      backtrack(nums, result, currentPermutation, used);

      // Backtrack: remove the last number and mark it as unused
      currentPermutation.remove(currentPermutation.size() - 1);
      used[i] = false;
    }
  }

  public static void main(String[] args) {
    A06PermutationsII solution = new A06PermutationsII();

    int[] nums1 = {1, 1, 2};
    System.out.println(solution.permuteUnique(nums1));
    // Output: [[1, 1, 2], [1, 2, 1], [2, 1, 1]]

    int[] nums2 = {2, 2, 1, 1};
    System.out.println(solution.permuteUnique(nums2));
    // Output: [[1, 1, 2, 2], [1, 2, 1, 2], [1, 2, 2, 1], [2, 1, 1, 2], [2, 1, 2, 1], [2, 2, 1, 1]]
  }
}
