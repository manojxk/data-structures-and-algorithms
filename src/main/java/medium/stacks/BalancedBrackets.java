/*
 * Problem Statement:
 * Write a function that takes in a string made up of brackets ('(', '[', '{', ')', ']', '}')
 * and other optional characters. The function should return a boolean representing whether
 * the string is balanced with regards to brackets.
 *
 * A string is considered balanced if:
 * 1. It contains an equal number of opening and closing brackets of each type.
 * 2. No closing bracket appears before its corresponding opening bracket.
 * 3. Brackets are properly nested, meaning no brackets overlap (e.g., '[(])' is not valid).
 *
 * Example:
 *
 * Sample Input:
 * string = "([])(){}(())()()"
 *
 * Sample Output:
 * true // it's balanced
 */
/*Optimized Solution
Approach:
Same as Brute Force: The approach remains largely the same as the brute force solution since the brute force approach is already optimal for this problem.
Avoid Unnecessary Checks: We only process characters that are brackets, ignoring any other characters in the string.
Time Complexity:
O(n): Each character is processed exactly once.
Space Complexity:
O(n): Stack space usage remains the same.*/
package medium.stacks;

import java.util.Stack;

public class BalancedBrackets {

  // Optimized Solution
  public static boolean isBalanced(String string) {
    Stack<Character> stack = new Stack<>();

    for (char ch : string.toCharArray()) {
      if (ch == '(' || ch == '{' || ch == '[') {
        stack.push(ch);
      } else if (ch == ')' || ch == '}' || ch == ']') {
        if (stack.isEmpty()) return false;

        char top = stack.pop();
        if ((ch == ')' && top != '(') || (ch == '}' && top != '{') || (ch == ']' && top != '[')) {
          return false;
        }
      }
    }

    return stack.isEmpty();
  }

  public static void main(String[] args) {
    String input = "([])(){}(())()()";
    boolean result = isBalanced(input);
    System.out.println(result); // Output: true
  }
}
