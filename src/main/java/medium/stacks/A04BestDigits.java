**Problem Restatement**
You’re given a positive integer as a string, `number`, and an integer `numDigits`. You must remove exactly `numDigits` digits from `number` (without reordering the remaining digits) so that the resulting number (as a string) is as large as possible.

> **Example**
>
> ```
> number = "462839"
> numDigits = 2
>
> One optimal removal is to drop the ‘4’ and the ‘2’, leaving “6839”, which is the largest possible result.
> ```

---

## Key Idea: Monotonic Stack

1. **Maintain a stack of digits** that will eventually form your largest possible number.
2. Whenever you see a new digit, compare it to the digit on top of the stack:

   * **If** the new digit is greater than the stack’s top *and* you still have digits left to remove (`numDigits > 0`), then pop the top—because dropping a smaller digit earlier allows a larger digit to take its place, making the final number larger.
   * Repeat this “pop” step as long as the next digit in the input is larger than the current stack top and you can still remove digits.
3. **Push** the new digit onto the stack.
4. **After processing all input digits**, if you still haven’t removed `numDigits` digits, pop that many times from the top of the stack (because by then the stack must be non-increasing, so dropping from the end is best).
5. **Reconstruct** the answer from the stack’s contents (in the correct order).
6. Finally, remove any leading zeros if they appear (not strictly needed if the original string never had leading zeros and you don’t drop all digits, but it’s a good safeguard).

This strategy ensures each digit is pushed and popped at most once—yielding an $O(n)$ time algorithm, where $n = \text{number.length()}$. The stack itself can hold up to $n$ digits in the worst case, so space is $O(n)$.

---

## Step-by-Step Walk-Through

Suppose:

```
number = "462839"
numDigits = 2
```

* Start with an empty stack and `digitsToRemove = 2`.

**Iteration 1: read ‘4’**

* Stack is empty, so push ‘4’.
* Stack: \[4], digitsToRemove = 2.

**Iteration 2: read ‘6’**

* Compare ‘6’ with stack top ‘4’. Since 6 > 4 and digitsToRemove > 0, pop ‘4’.
* Stack becomes \[], digitsToRemove = 1.
* Now push ‘6’.
* Stack: \[6], digitsToRemove = 1.

**Iteration 3: read ‘2’**

* Compare ‘2’ to top ‘6’. 2 < 6, so we cannot pop.
* Push ‘2’.
* Stack: \[6, 2], digitsToRemove = 1.

**Iteration 4: read ‘8’**

* Compare ‘8’ to top ‘2’. Since 8 > 2 and digitsToRemove > 0, pop ‘2’. → Stack: \[6], digitsToRemove = 0.
* Now compare ‘8’ to new top ‘6’. We have digitsToRemove = 0, so we cannot pop anymore even though 8 > 6.
* Push ‘8’.
* Stack: \[6, 8], digitsToRemove = 0.

**Iteration 5: read ‘3’**

* digitsToRemove = 0, so you cannot pop regardless.
* Push ‘3’.
* Stack: \[6, 8, 3].

**Iteration 6: read ‘9’**

* digitsToRemove = 0, cannot pop.
* Push ‘9’.
* Stack: \[6, 8, 3, 9].

Now all input digits are processed. We still have `digitsToRemove = 0`, so no further popping is needed.

**Reconstruct** from stack (bottom→top): “6 8 3 9” → `"6839"`. That is the largest possible 4-digit number after removing exactly 2 digits from “462839”.

---

## Java Code

```java
package medium.stacks;

import java.util.Stack;

public class A04BestDigits {

  /**
   * Removes numDigits from the input string ‘number’ to form the largest possible result.
   *
   * @param number     A string of digits (no leading zeros, unless number itself is "0…0").
   * @param numDigits  How many digits to remove (0 ≤ numDigits < number.length()).
   * @return The largest possible number (as a string) after removing exactly numDigits digits.
   */
  public static String bestDigits(String number, int numDigits) {
    Stack<Character> stack = new Stack<>();

    // 1. Iterate through each digit in the input
    for (int i = 0; i < number.length(); i++) {
      char current = number.charAt(i);

      // 2. While we can still remove digits, and the top of the stack is smaller than the current digit:
      //    pop the stack to let the bigger digit “current” take precedence.
      while (numDigits > 0 
             && !stack.isEmpty() 
             && current > stack.peek()) {
        stack.pop();
        numDigits--;
      }

      // 3. Push the current digit onto the stack
      stack.push(current);
    }

    // 4. If any removals remain, pop from the stack’s top
    while (numDigits > 0) {
      stack.pop();
      numDigits--;
    }

    // 5. Build the resulting string from the stack (in correct order)
    StringBuilder sb = new StringBuilder();
    while (!stack.isEmpty()) {
      sb.append(stack.pop());
    }
    sb.reverse(); // because we appended from top→bottom

    // 6. Strip any leading zeros (optional, but safe)
    while (sb.length() > 1 && sb.charAt(0) == '0') {
      sb.deleteCharAt(0);
    }

    return sb.toString();
  }

  public static void main(String[] args) {
    String number   = "462839";
    int numDigits   = 2;
    String result   = bestDigits(number, numDigits);
    System.out.println(result); // prints "6839"
  }
}
```

---

### Complexity Analysis

* **Time Complexity:** $O(n)$, where $n = \text{number.length()}$.

  * Each digit is pushed onto the stack exactly once.
  * Each digit can be popped at most once.
  * Hence total pushes + pops ≤ $2n$. All other operations (like comparisons) are $O(1)$ per digit.

* **Space Complexity:** $O(n)$ for the stack (worst-case: you never pop during the scan, so the stack ends up holding all digits).

---

### Why This Works

* By “popping smaller digits” whenever a larger digit arrives (and you still have “removals” left), you ensure that the highest‐value digits end up as far left as possible.
* Once you’ve used up your `numDigits` removals, you keep pushing every remaining digit, because you no longer can improve by dropping earlier digits.
* Finally, if any removals linger (e.g. the input was entirely non‐increasing so you never popped during the scan), you remove from the **end** of the number, since dropping the rightmost digits in a non‐increasing sequence is the best way to keep the overall number large.

This greedy, stack‐based method always produces the lexicographically largest result possible under the constraint of removing exactly `numDigits` digits.
