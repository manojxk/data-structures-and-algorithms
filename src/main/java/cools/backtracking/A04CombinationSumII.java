package cools.backtracking;

/*Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.

Each number in candidates may only be used once in the combination.

Note: The solution set must not contain duplicate combinations.*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A04CombinationSumII {
  List<List<Integer>> result = new ArrayList<>(); // Stores all valid combinations
  List<Integer> currentCombination = new ArrayList<>(); // Temporary combination being explored

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates); // Sort to handle duplicates easily
    backtrack(candidates, 0, target, 0); // Start backtracking
    return result;
  }

  private void backtrack(int[] candidates, int start, int target, int currentSum) {
    // Base case: if the current sum matches the target, add the current combination to the result
    if (currentSum == target) {
      result.add(new ArrayList<>(currentCombination));
      return;
    }

    // Iterate through the candidates starting from 'start'
    for (int i = start; i < candidates.length; i++) {
      // Skip duplicates (ensure we pick the first occurrence of any duplicate element)
      if (i > start && candidates[i] == candidates[i - 1]) continue; // Change 1: Skip duplicates

      // If adding candidates[i] exceeds the target, we stop (early stopping)
      if (currentSum + candidates[i] > target)
        continue; // Early stopping if the sum exceeds the target

      currentCombination.add(candidates[i]); // Choose the current candidate
      backtrack(
          candidates,
          i + 1,
          target,
          currentSum + candidates[i]); // Change 2: Move to the next element (i + 1)
      currentCombination.remove(
          currentCombination.size() - 1); // Backtrack by removing the last element
    }
  }

  public static void main(String[] args) {
    A04CombinationSumII solution = new A04CombinationSumII();

    int[] candidates1 = {10, 1, 2, 7, 6, 1, 5};
    int target1 = 8;
    System.out.println(solution.combinationSum2(candidates1, target1));
    // Output: [[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]

    int[] candidates2 = {2, 5, 2, 1, 2};
    int target2 = 5;
    System.out.println(solution.combinationSum2(candidates2, target2));
    // Output: [[1, 2, 2], [5]]
  }
}
