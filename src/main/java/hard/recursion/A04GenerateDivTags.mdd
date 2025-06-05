**Problem Restatement**
Generate all valid strings consisting of exactly `n` opening `<div>` tags and `n` closing `</div>` tags, such that they are properly nested (every `</div>` matches an earlier unmatched `<div>`). For example, when `n = 3`, we seek all strings like

```
"<div><div><div></div></div></div>"
"<div><div></div><div></div></div>"
...  
```

that have exactly three `<div>` and three `</div>`, with correct nesting (no premature closing).

---

## How the Backtracking Solution Works

We maintain two counters:

* `open` = how many opening tags (`<div>`) remain to be placed,
* `close` = how many closing tags (`</div>`) remain to be placed.

We build a partial string `current` step by step. At each recursive call:

1. **Base Case**
   If `open == 0 && close == 0`, we have used up exactly `n` opening tags and `n` closing tags, and the current string is fully balanced. We add `current` to our result list.

2. **Add an Opening Tag** (if possible)
   If `open > 0`, we can still place another `<div>`. We append `"<div>"` to `current`, decrement `open` by 1, and recurse:

   ```java
   generateTags(open - 1, close, current + "<div>", result);
   ```

   Placing an opening tag never invalidates the nesting, so it’s always safe whenever `open > 0`.

3. **Add a Closing Tag** (if valid)
   We may only add `"</div>"` if doing so does not create a “prefix” with more closing than opening tags. Concretely, that means there must already be at least one unmatched `<div>` in the partial string. In terms of our counters, since `open` counts how many `<div>` tags remain unused, and `close` counts how many `</div>` remain unused, a valid condition to place a closing tag is:

   ```
   close > open
   ```

   Why?

   * Initially, `open == close == n`.
   * Every time you place another `<div>`, `open` decreases by 1—but `close` stays the same—so `close` eventually becomes strictly greater than `open`. That gap exactly measures “how many more closings we can place.”
   * If `close == open`, that means we have placed exactly as many `<div>`’s as `</div>`’s so far; you cannot close yet, or you’d be “closing before opening.”
   * Therefore, only when `close > open` do we have at least one unmatched `<div>` to match with a `</div>`.

   In that case we recurse:

   ```java
   generateTags(open, close - 1, current + "</div>", result);
   ```

By always exploring “place an opening tag” first (if `open > 0`), then—only when valid—“place a closing tag” (if `close > open`), we systematically enumerate every way to interleave exactly `n` opening and `n` closing tags so that no prefix is invalid.

---

## Full Code with Comments

```java
package hard.recursion;

import java.util.ArrayList;
import java.util.List;

public class GenerateDivTags {

  /**
   * Public API: returns a list of all valid combinations of n matched <div>…</div> tags.
   * 
   * @param numberOfTags  the number n of <div>…</div> pairs to generate
   * @return  a List<String> containing all properly nested sequences of n "<div>" and n "</div>"
   */
  public static List<String> generateDivTags(int numberOfTags) {
    List<String> result = new ArrayList<>();
    // Start the backtracking with `open = n`, `close = n`, and an empty current string.
    generateTags(numberOfTags, numberOfTags, "", result);
    return result;
  }

  /**
   * Recursive helper that builds up all valid sequences. 
   * 
   * @param open     how many "<div>" tags remain to be placed
   * @param close    how many "</div>" tags remain to be placed
   * @param current  the partial string built so far
   * @param result   the List<String> to collect valid full sequences
   */
  private static void generateTags(int open, int close, String current, List<String> result) {
    // Base case: no tags left to place → current is a fully valid sequence
    if (open == 0 && close == 0) {
      result.add(current);
      return;
    }

    // 1) If we still have opening tags left, place one <div> now:
    if (open > 0) {
      // Append "<div>" and decrement open
      generateTags(open - 1, close, current + "<div>", result);
    }

    // 2) If we can place a closing tag without invalidating nesting, do so:
    //    That is only allowed when close > open, meaning 
    //    there are unmatched "<div>" in the partially built string.
    if (close > open) {
      // Append "</div>" and decrement close
      generateTags(open, close - 1, current + "</div>", result);
    }
  }

  public static void main(String[] args) {
    int numberOfTags = 3;
    List<String> result = generateDivTags(numberOfTags);

    System.out.println("Generated div tags:");
    for (String s : result) {
      System.out.println(s);
    }
  }
}
```

---

## Example Trace: `n = 2`

Let’s walk through how `generateDivTags(2)` builds all valid strings of two `<div>` tags and two `</div>` tags. We call:

```
generateTags(open=2, close=2, current="", result)
```

1. `open=2, close=2, current=""`.

   * `open > 0`? Yes, so recurse with `(1, 2, "<div>")`.
   * `close > open`? No (2 ≯ 2), so we don’t place `</div>` yet.

2. Now in `(open=1, close=2, current="<div>")`:

   * `open > 0`? Yes → recurse `(0, 2, "<div><div>")`.
   * `close > open`? (2 > 1)? Yes → recurse `(1, 1, "<div></div>")`.

   We’ll branch into those two calls:

   **2a.** `(open=0, close=2, current="<div><div>")`
     • `open > 0`? No.
     • `close > open`? (2 > 0)? Yes → recurse `(0, 1, "<div><div></div>")`.

   **2a(i).** `(open=0, close=1, current="<div><div></div>")`
     • `open>0`? No.
     • `close>open`? (1>0)? Yes → recurse `(0, 0, "<div><div></div></div>")`.

   **2a(i)(A).** `(open=0, close=0, current="<div><div></div></div>")`
     Base case! Add `"<div><div></div></div>"` to `result`.

   (Return to 2a, no other branches, return up.)

   **2b.** `(open=1, close=1, current="<div></div>")`
     • `open > 0`? (1>0)? Yes → recurse `(0, 1, "<div></div><div>")`.
     • `close > open`? (1>1)? No → skip placing `</div>` right now.

   **2b(i).** `(open=0, close=1, current="<div></div><div>")`
     • `open>0`? No.
     • `close>open`? (1>0)? Yes → recurse `(0, 0, "<div></div><div></div>")`.

   **2b(i)(A).** `(open=0, close=0, current="<div></div><div></div>")`
     Base case! Add `"<div></div><div></div>"` to `result`.

   (Return to 2b, no other branches, return up.)

(Return to the top call, no further branches, done.)

We have found exactly two valid sequences for `n=2`:

```
"<div><div></div></div>"
"<div></div><div></div>"
```

which is correct.

---

## Complexity Analysis

* **Time Complexity**
  This exactly mirrors the “generate parentheses” problem. The total number of valid sequences of `n` tags is the nth Catalan number, $C_n = \frac{1}{n+1}\binom{2n}{n}$, which grows roughly as $O(4^n / (n^{1.5}))$. Our backtracking explores each valid sequence exactly once, in addition to some partial prefixes that get pruned. So the overall runtime is **O(C\_n \times n)** in the worst case, which is often said to be **O($\dfrac{4^n}{\sqrt{n}}$)**. In simpler terms, it is exponential in `n`.

* **Space Complexity**

  * We store up to O($C_n$) full strings in the `result` list. Each string has length $O(n × (lengthOf"<div>"))\approx O(5n)$.
  * The call‐stack depth is at most `2n` (each recursive call uses one `open` or `close`). So the recursion stack is **O(n)** deep.
  * Overall, memory is dominated by storing all generated strings (exponential in `n`), so the space cost is also exponential in `n`.

---

### Summary

1. Call `generateDivTags(n)`.
2. Internally, we track how many opening (`open`) and closing (`close`) tags remain to place.
3. Whenever `open > 0`, we can safely append `"<div>"` and recurse.
4. Whenever `close > open`, we can safely append `"</div>"` and recurse (that ensures we never close more than we have opened).
5. Once `open == close == 0`, the string is complete and valid. Add it to `result`.

This backtracking guarantees we only build properly balanced sequences, and we collect them all. Continuous pruning (by checking `close > open`) ensures we never form an invalid prefix.
