**Problem Restatement**
You have a string `s` consisting only of the characters `(`, `)`, `{`, `}`, `[` and `]`. You need to determine whether the string is **“valid,”** meaning every opening bracket is closed by the same type of bracket and in the correct order.

* Example: `"()[]{}"` is valid.
* Example: `"([)]"` is not valid, because even though each bracket type matches somewhere, the ordering is wrong.

---

## Approach: Use a Stack

1. **Initialize** an empty stack of characters.
2. **Iterate** over each character `c` in the string:

   * **If** `c` is an opening bracket (`(`, `{`, `[`), **push** it onto the stack.
   * **Else** (so `c` is a closing bracket—one of `)`, `}`, `]`):
     a. **If** the stack is empty, return `false` immediately (no opening bracket to match).
     b. Otherwise, **pop** the top of the stack (let’s call it `top`).

     * If `c` is `)` but `top` is not `(`, return `false`.
     * If `c` is `}` but `top` is not `{`, return `false`.
     * If `c` is `]` but `top` is not `[`, return `false`.
3. **After** processing all characters, if the stack is **empty**, return `true`; otherwise, return `false` (leftover opening brackets mean unmatched).

Since each character is pushed or popped at most once, this runs in **O(n)** time and uses **O(n)** space in the worst case (all opens).

---

## Java Implementation

```java
package medium.stacks;

import java.util.Stack;

public class A02BalancedBrackets {

  /**
   * Returns true if the input string s (containing only '(', ')', '{', '}', '[' and ']')
   * is a valid sequence of balanced brackets. Otherwise returns false.
   *
   * Time Complexity:  O(n)    (each char is processed once)
   * Space Complexity: O(n)    (stack may hold up to n opening brackets)
   */
  public static boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();

    for (char c : s.toCharArray()) {
      // 1) If it's an opening bracket, push onto stack
      if (c == '(' || c == '{' || c == '[') {
        stack.push(c);
      } else {
        // 2) Must be a closing bracket now; stack must not be empty
        if (stack.isEmpty()) {
          return false;
        }
        char top = stack.pop();
        // 3) Check matching types
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

    // 4) If anything remains, it's unmatched
    return stack.isEmpty();
  }

  public static void main(String[] args) {
    // Test cases
    System.out.println(isValid("()"));      // true
    System.out.println(isValid("()[]{}"));  // true
    System.out.println(isValid("(]"));      // false
    System.out.println(isValid("([)]"));    // false
    System.out.println(isValid("{[]}"));    // true
  }
}
```

---

### Explanation of Key Steps

1. **Why use a stack?**

   * A stack naturally models “last‐opened, first‐closed.” Whenever you see an opening bracket, you push it.
   * When you see a closing bracket, you pop the top and verify it matches the same type.

2. **Checking for mismatches**

   * If you ever try to pop from an empty stack, there is no matching opening bracket → **invalid**.
   * If the popped opening bracket does not correspond to the current closing bracket (e.g. popped `[` but current is `}`), it’s invalid.

3. **Final stack check**

   * After processing every character, a valid string should have matched every opening with a closing.
   * If the stack is not empty, that means there were leftover unmatched opening brackets → **invalid**.

This solution processes each character exactly once and uses the stack to enforce the correct nesting/order of brackets.
