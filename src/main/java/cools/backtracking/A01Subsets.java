package cools.backtracking;

/*Given an integer array nums of unique elements, return all possible
subsets(the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.*/
import java.util.ArrayList;
import java.util.List;

public class A01Subsets {
  List<List<Integer>> powerset = new ArrayList<>(); // stores all subsets
  List<Integer> subset = new ArrayList<Integer>();

  public List<List<Integer>> subsets(int[] nums) {
    // temporary subset which will be updated as the recursive function executes

    backtrack(nums, 0);
    return powerset;
  }

  private void backtrack(int[] nums, int start) {
    powerset.add(new ArrayList<>(subset)); // Add the current subset to the result

    for (int i = start; i < nums.length; i++) {
      subset.add(nums[i]); // Choose the element nums[i]
      backtrack(nums, i + 1); // Explore further by including nums[i]
      subset.remove(subset.size() - 1); // Backtrack by removing the last element
    }
  }

  public static void main(String[] args) {
    A01Subsets solution = new A01Subsets();

    int[] nums1 = {1, 2, 3};
    System.out.println(
        solution.subsets(nums1)); // Output: [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
  }
}

/*
Approach:
Backtracking is the perfect solution for generating all possible subsets of the input array.
The idea is to use a recursive approach where we either include or exclude each element from the subset.
Start with an empty subset and, at each recursive call, try adding the next available element or not adding it, and then backtrack to explore other possibilities.
Code Explanation:
Start from an empty subset.
At each recursive step, decide whether to include the current element in the subset or not.
Add the current subset (path) to the result list.
Recursively proceed until all elements are either included or excluded from the subset.
Time Complexity:
O(2^n) where n is the length of the input array because we generate all 2^n subsets.
Space Complexity:
O(n) due to the recursion stack used during backtracking.*/
