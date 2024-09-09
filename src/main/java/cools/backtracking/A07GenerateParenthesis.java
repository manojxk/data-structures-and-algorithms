package cools.backtracking;

/*
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
*/

import java.util.ArrayList;
import java.util.List;

public class A07GenerateParenthesis {

  public List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), 0, 0, n);
    return result;
  }

  private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
    // Base case: when the current string has n pairs of parentheses
    if (current.length() == max * 2) {
      result.add(current.toString());
      return;
    }

    // If we can still add an open parenthesis, add it and recurse
    if (open < max) {
      current.append('(');
      backtrack(result, current, open + 1, close, max);
      current.deleteCharAt(current.length() - 1); // Backtrack: remove last character
    }

    // If we can still add a close parenthesis, add it and recurse
    if (open > close) {
      current.append(')');
      backtrack(result, current, open, close + 1, max);
      current.deleteCharAt(current.length() - 1); // Backtrack: remove last character
    }
  }

  public static void main(String[] args) {
    A07GenerateParenthesis solution = new A07GenerateParenthesis();

    int n = 3; // Example input
    List<String> result = solution.generateParenthesis(n);

    // Print the result
    for (String combination : result) {
      System.out.println(combination);
    }
  }
}
