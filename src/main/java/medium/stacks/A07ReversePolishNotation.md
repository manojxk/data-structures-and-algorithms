**Problem Explanation (in Simple Terms)**
You have an expression written in **Reverse Polish Notation (RPN)**, also called postfix notation. Instead of writing “3 + 4,” you would write “3 4 +.” In RPN:

* Every time you see a number token (e.g. “5” or “17”), you treat it as an operand.
* Every time you see an operator (one of `+`, `-`, `*`, `/`), you apply that operator to the **two most recent numbers** that haven’t yet been used by an earlier operator.

Because operators come **after** their operands, there is no need for parentheses. For example:

```
3 4 + 2 *   ->   (3 + 4) * 2   ->   14
```

You read left to right:

1. See “3” → push it on a stack.
2. See “4” → push it.
3. See “+” → pop two numbers (4 and 3), compute 3+4=7, then push 7.
4. See “2” → push it.
5. See “*” → pop two numbers (2 and 7), compute 7*2=14, push 14.

At the end, the stack has one value (14), which is the result.

---

## Task in This Problem

You’re given an array of string tokens, e.g.

```
["50", "3", "17", "+", "2", "-", "/"]
```

That corresponds to the infix expression

```
50 / ((3 + 17) - 2).
```

Your job is to **evaluate** that RPN expression and return its integer result. Division is integer division that rounds toward zero (so `-3 / 2 = -1`, just like in Java).

You can assume:

* The input tokens form a valid RPN expression.
* There will never be a “bad” operator or an out‐of‐range number.
* After you finish, exactly one value remains on the stack,—that’s your answer.

---

## Step‐by‐Step Approach Using a Stack

1. **Create an empty stack** of integers.
2. **Iterate** through the input token array from left to right. For each token:

   * **If** the token represents an operator (`+`, `-`, `*`, `/`), then:

     1. **Pop** the top value off the stack (call it `b`).
     2. **Pop** the next top value (call it `a`).
     3. **Compute** `a <operator> b`.

        * For example, if the operator is `“-”`, compute `(a - b)`.
        * If it’s `“/”`, do integer division `a / b`, which in Java automatically truncates toward zero.
     4. **Push** the result back onto the stack.
   * **Else** (the token is a number string), **parse** it as an integer and **push** that integer onto the stack.
3. After you finish processing all tokens, the stack will contain exactly one integer. **Pop** it and return that as the final result.

Because each operator always uses exactly two operands from the stack and pushes exactly one result back, the stack size grows and shrinks in a controlled way. By the time you handle the last token, only the final value remains.

---

## Time and Space Complexity

* **Time Complexity: O(n)**
  You make a single pass over the `tokens[]` array (length `n`). Each token causes at most one stack `push` and—if it’s an operator—two `pop`s and one more `push`. All of these stack operations are O(1). Therefore, processing `n` tokens takes O(n) time.

* **Space Complexity: O(n)**
  In the worst case, all tokens might be numbers (no operators), so you push every number onto the stack. That could use O(n) space. Once you start encountering operators, the stack shrinks, but big‐O worst‐case is still O(n).

---

## Java Solution (Complete Code)

```java
package medium.stacks;

import java.util.Stack;

public class A07ReversePolishNotation {

  /**
   * Evaluates a Reverse Polish Notation (RPN) expression and returns the integer result.
   *
   * @param tokens  An array of strings, each token is either:
   *                1) A valid integer in string form, or
   *                2) One of the four operators: "+", "-", "*", "/"
   * @return        The integer result of evaluating the RPN expression.
   */
  public static int evaluateRPN(String[] tokens) {
    // 1. Create a stack to hold integer operands
    Stack<Integer> stack = new Stack<>();

    // 2. Iterate over every token in order
    for (String token : tokens) {
      // 2a. If the token is one of the four operators, pop two values and apply
      if (token.equals("+")) {
        // Pop b first, then a, because in RPN the operator applies to (a <op> b)
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a + b);
      }
      else if (token.equals("-")) {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a - b);
      }
      else if (token.equals("*")) {
        int b = stack.pop();
        int a = stack.pop();
        stack.push(a * b);
      }
      else if (token.equals("/")) {
        int b = stack.pop();
        int a = stack.pop();
        // Integer division in Java truncates toward zero by default
        stack.push(a / b);
      }
      // 2b. Otherwise, it must be a number; parse it and push onto the stack
      else {
        stack.push(Integer.parseInt(token));
      }
    }

    // 3. At the end, the stack has exactly one value: the final result
    return stack.pop();
  }

  public static void main(String[] args) {
    // Sample Input:
    String[] tokens = {"50", "3", "17", "+", "2", "-", "/"};
    // This corresponds to 50 / ((3 + 17) - 2)

    int result = evaluateRPN(tokens);
    System.out.println(result);  // Expected output: 2
  }
}
```

---

### How the Code Works, Illustrated

Suppose `tokens = ["50", "3", "17", "+", "2", "-", "/"]`.

* Start with an empty stack: `[]`.

1. Read `"50"` → it’s not one of `+ - * /`, so parse `50` and push → stack = `[50]`.
2. Read `"3"`  → parse and push → stack = `[50, 3]`.
3. Read `"17"` → parse and push → stack = `[50, 3, 17]`.
4. Read `"+"`:

   * Pop `17` (`b = 17`), pop `3` (`a = 3`), compute `3 + 17 = 20`.
   * Push `20`.
   * Now stack = `[50, 20]`.
5. Read `"2"` → parse and push → stack = `[50, 20, 2]`.
6. Read `"-"`:

   * Pop `2` (`b = 2`), pop `20` (`a = 20`), compute `20 - 2 = 18`.
   * Push `18`.
   * Now stack = `[50, 18]`.
7. Read `"/"`:

   * Pop `18` (`b = 18`), pop `50` (`a = 50`), compute `50 / 18`.
   * In integer division, `50/18 = 2` (discard remainder).
   * Push `2`.
   * Now stack = `[2]`.

At the end, `stack.pop()` is `2`, which is the correct result.

---

## Key Takeaways

* **Use a stack** to hold numbers while scanning RPN:

  * **Push** whenever you see a number.
  * **Pop two** when you see an operator, apply it, and **push** the result.
* **Integer division** in Java truncates toward zero, which matches the problem requirement (`-3/2 = -1`).
* Because each token leads to at most one push and, if it’s an operator, two pops + one push, the solution is linear time $O(n)$ and uses linear extra space $O(n)$ for the stack.

This completes the end-to-end explanation and Java solution for evaluating an RPN expression.
