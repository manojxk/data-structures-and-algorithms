/*
 * Problem Statement:
 *
 * You're given a list of string tokens representing a mathematical expression
 * in Reverse Polish Notation (RPN). RPN is a notation where operators come after operands,
 * instead of between them. For example, "2 4 +" would evaluate to "6".
 *
 * Parentheses are implicit in RPN, meaning the expression is evaluated from left to right.
 * All operators in this problem take two operands, which will always be the two values
 * immediately preceding the operator. For example, "18 4 - 7 /" would evaluate to
 * "((18 - 4) / 7)" or "2".
 *
 * Your task is to write a function that takes this list of tokens and returns the final result
 * of evaluating the expression. The function should support the following four operators:
 *  - "+" for addition,
 *  - "-" for subtraction,
 *  - "*" for multiplication,
 *  - "/" for division.
 *
 * Division should be treated as integer division, rounding towards zero. For example:
 *  - "3 / 2" evaluates to "1"
 *  - "-3 / 2" evaluates to "-1"
 *
 * Assumptions:
 * - The input will always be a valid RPN expression.
 * - The input will always produce a valid numerical result.
 * - The input list should not be modified.
 *
 * Example:
 *
 * Sample Input:
 * tokens = ["50", "3", "17", "+", "2", "-", "/"]
 *
 * Sample Output:
 * 2 // (50 / ((3 + 17) - 2))
 */

package medium.stacks;

import java.util.Stack;

public class A07ReversePolishNotation {
  public static int evaluateRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<Integer>();

    for (String token : tokens) {
      if (token.equals("+")) {
        stack.add(stack.pop() + stack.pop());
      } else if (token.equals("-")) {
        int firstNum = (stack.pop());
        stack.add(stack.pop() - firstNum);
      } else if (token.equals("*")) {
        stack.add(stack.pop() * stack.pop());
      } else if (token.equals("/")) {
        int firstNum = stack.pop();
        stack.add(stack.pop() / firstNum);
      } else {
        stack.add(Integer.parseInt(token));
      }
    }
    return stack.pop();
  }

  public static void main(String[] args) {
    String[] tokens = {"50", "3", "17", "+", "2", "-", "/"};
    int result = evaluateRPN(tokens);
    System.out.println(result); // Output: 2
  }
}
