package medium.stacks;

/*
 * Problem: Balanced Brackets
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * An input string is valid if:
 * 1. Open brackets are closed by the same type of brackets.
 * 2. Open brackets are closed in the correct order.
 *
 * Example 1:
 * Input: "()"
 * Output: true
 *
 * Example 2:
 * Input: "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: "(]"
 * Output: false
 *
 * Example 4:
 * Input: "([)]"
 * Output: false
 *
 * Example 5:
 * Input: "{[]}"
 * Output: true
 */

/*
 * Solution Steps:
 *
 * 1. Use a stack to keep track of open brackets.
 * 2. Traverse the input string:
 *    - If an open bracket is encountered ('(', '{', '['), push it onto the stack.
 *    - If a closing bracket is encountered (')', '}', ']'):
 *      - Check if the stack is empty (if it is, return false because there's no matching opening bracket).
 *      - Pop the top of the stack and check if it matches the current closing bracket.
 * 3. After the loop, if the stack is empty, all the brackets were matched correctly; otherwise, it's unbalanced.
 */

import java.util.Stack;

public class A02BalancedBrackets {

  // Function to check if brackets are balanced
  public static boolean isValid(String s) {
    // Stack to keep track of opening brackets
    Stack<Character> stack = new Stack<>();

    // Traverse the string
    for (char c : s.toCharArray()) {
      // If it's an opening bracket, push it onto the stack
      if (c == '(' || c == '{' || c == '[') {
        stack.push(c);
      }
      // If it's a closing bracket, check for matching opening bracket
      else {
        // If stack is empty, it means there's no matching opening bracket
        if (stack.isEmpty()) {
          return false;
        }

        // Pop the top of the stack and check if it matches the closing bracket
        char top = stack.pop();
        if (c == ')' && top != '(') {
          return false;
        }
        if (c == '}' && top != '{') {
          return false;
        }
        if (c == ']' && top != '[') {
          return false;
        }
      }
    }

    // If the stack is empty, all brackets were matched correctly
    return stack.isEmpty();
  }

  public static void main(String[] args) {
    // Test cases
    String test1 = "()"; // true
    String test2 = "()[]{}"; // true
    String test3 = "(]"; // false
    String test4 = "([)]"; // false
    String test5 = "{[]}"; // true

    System.out.println(isValid(test1)); // Output: true
    System.out.println(isValid(test2)); // Output: true
    System.out.println(isValid(test3)); // Output: false
    System.out.println(isValid(test4)); // Output: false
    System.out.println(isValid(test5)); // Output: true
  }

  /*
   * Time Complexity:
   * O(n), where n is the length of the input string. We process each character exactly once.
   *
   * Space Complexity:
   * O(n), in the worst case, if all the characters in the string are opening brackets, the stack will store all of them.
   */
}
