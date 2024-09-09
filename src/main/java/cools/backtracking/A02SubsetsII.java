package cools.backtracking;

/*Given an integer array nums that may contain duplicates, return all possible
subsets(the power set).
The solution set must not contain duplicate subsets. Return the solution in any order.*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A02SubsetsII {
  List<List<Integer>> powerset = new ArrayList<>(); // stores all subsets
  List<Integer> subset = new ArrayList<Integer>();

  public List<List<Integer>> subsets(int[] nums) {
    // temporary subset which will be updated as the recursive function executes
    Arrays.sort(nums); // change 1
    backtrack(nums, 0);
    return powerset;
  }

  private void backtrack(int[] nums, int start) {

    powerset.add(new ArrayList<>(subset)); // Add the current subset to the result

    for (int i = start; i < nums.length; i++) {
      if (i > start && nums[i] == nums[i - 1]) continue; // change 2 skip over duplicates
      subset.add(nums[i]); // Choose the element nums[i]
      backtrack(nums, i + 1); // Explore further by including nums[i]
      subset.remove(subset.size() - 1); // Backtrack by removing the last element
    }
  }

  public static void main(String[] args) {
    A02SubsetsII solution = new A02SubsetsII();

    int[] nums1 = {1, 2, 2};
    System.out.println(
        solution.subsets(nums1)); // Output: [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
  }
}
