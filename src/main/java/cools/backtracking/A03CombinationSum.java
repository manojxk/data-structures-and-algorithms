package cools.backtracking;

/*Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.*/

import java.util.*;

public class A03CombinationSum {
  List<List<Integer>> result = new ArrayList<>(); // Stores all valid combinations
  List<Integer> currentCombination = new ArrayList<>(); // Temporary combination being explored

  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    // Same base approach, but we track the sum and allow repeated elements
    backtrack(candidates, 0, target, 0); // Start with an initial sum of 0
    return result;
  }

  private void backtrack(int[] candidates, int start, int target, int currentSum) {
    // Base case: when the current sum matches the target, add the current combination to the result
    if (currentSum == target) {
      result.add(new ArrayList<>(currentCombination)); // yes! a valid solution found
      return;
    }

    // Iterate through the candidates array
    for (int i = start; i < candidates.length; i++) {
      // If adding candidates[i] exceeds the target, we skip it (early stopping)
      if (currentSum + candidates[i] > target) continue; // Change 1: Skip invalid combinations

      currentCombination.add(candidates[i]); // Choose the current candidate
      backtrack(
          candidates,
          i,
          target,
          currentSum
              + candidates[i]); // Change 2: Allow repetition by passing `i` instead of `i + 1`
      currentCombination.remove(
          currentCombination.size() - 1); // Backtrack by removing the last element
    }
  }

  public static void main(String[] args) {
    A03CombinationSum solution = new A03CombinationSum();

    int[] candidates1 = {2, 3, 6, 7};
    int target1 = 7;
    System.out.println(solution.combinationSum(candidates1, target1));
    // Output: [[2, 2, 3], [7]]

    int[] candidates2 = {2, 3, 5};
    int target2 = 8;
    System.out.println(solution.combinationSum(candidates2, target2));
    // Output: [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
  }
}
