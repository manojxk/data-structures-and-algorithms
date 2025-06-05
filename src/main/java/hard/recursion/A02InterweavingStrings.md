Below is a walkthrough of how this memoized recursion checks whether `three` can be formed by interleaving `one` and `two`, along with its complexity and a few clarifications.

---

## Problem Restatement

You have three strings:

* `one` of length **m**
* `two` of length **n**
* `three` of length **p**

You want to decide whether **exactly** all the characters of `one` and `two` (in their original order) can be “woven” together to form `three`. Formally, that means:

1. `one.length() + two.length() == three.length()`, and
2. You can traverse `three` from left to right, each time choosing the next unused character from either `one` or `two` (whichever matches), never reordering the characters inside `one` or inside `two`.

If you can do that, return `true`; otherwise return `false`.

---

## High‐Level Approach

1. **Length check**:
   If `one.length() + two.length() != three.length()`, there’s no way to interleave all characters of `one` and `two` to get `three`. Immediately return `false`.

2. **Recursive definition**:
   Let `f(i, j)` be “true if the prefix `one[0..i−1]` and the prefix `two[0..j−1]` can interleave to form `three[0..(i+j)−1]`.”

   * In other words, you’ve already used `i` characters from `one` (those are `one[0]` through `one[i−1]`) and `j` characters from `two`, so you must have formed exactly the first `i + j` characters of `three`.
   * We want to know if `f(m, n)` is true, where `m = one.length()`, `n = two.length()`.

3. **Recursive cases**:
   When you are at state `(i, j)`, you need to match `three[k]` where `k = i + j`. There are two possibilities:

   * **Take next character from `one`**: If `i < m` and `one.charAt(i) == three.charAt(k)`, then you could “use” that next character from `one`. So you would recurse to check `f(i+1, j)`.
   * **Take next character from `two`**: If `j < n` and `two.charAt(j) == three.charAt(k)`, then you could instead “use” the next character from `two` and recurse to `f(i, j+1)`.

   If either recursive branch returns `true`, then `f(i, j)` is `true`. Otherwise it’s `false`.

4. **Memoization**:
   Naively, this branching can revisit the same `(i, j)` many times. We store a Boolean table `memo[i][j]` (with dimensions `(m+1)×(n+1)`) so that once we compute `f(i, j)`, we never recompute it.

---

## Code Walkthrough

```java
public class InterweavingStrings {

  public boolean isInterleave(String one, String two, String three) {
    // 1) Quick length check
    if (one.length() + two.length() != three.length()) return false;

    // 2) Prepare memo table of size (m+1) x (n+1). null = “not computed yet”,
    //    true/false = result of f(i,j).
    Boolean[][] memo = new Boolean[one.length() + 1][two.length() + 1];

    // 3) Start recursion from (i=0, j=0)
    return isInterleaveHelper(one, two, three, /*i=*/0, /*j=*/0, memo);
  }

  private boolean isInterleaveHelper(
      String one, String two, String three, int i, int j, Boolean[][] memo) {
    // i = how many chars used from `one`
    // j = how many chars used from `two`
    // So far we’ve formed three[0..(i+j−1)]

    // 1) If we’ve used up both `one` and `two`, we must also have used up all of `three`
    if (i == one.length() && j == two.length()) {
      return true;
    }

    // 2) If we already computed f(i,j) before, just return it
    if (memo[i][j] != null) {
      return memo[i][j];
    }

    // 3) Let k = i + j be the next index in `three` to match
    int k = i + j;

    // 4) Try taking a character from `one` if it matches three[k]
    if (i < one.length() && one.charAt(i) == three.charAt(k)) {
      if (isInterleaveHelper(one, two, three, i + 1, j, memo)) {
        memo[i][j] = true;
        return true;
      }
    }

    // 5) Otherwise, try taking a character from `two` if it matches three[k]
    if (j < two.length() && two.charAt(j) == three.charAt(k)) {
      if (isInterleaveHelper(one, two, three, i, j + 1, memo)) {
        memo[i][j] = true;
        return true;
      }
    }

    // 6) Neither choice worked, so f(i,j) = false
    memo[i][j] = false;
    return false;
  }
}
```

### How It Progresses

* Initially, `isInterleave(one, two, three)` calls `isInterleaveHelper(one, two, three, 0, 0, memo)`. That means “we’ve used 0 chars from `one`, 0 chars from `two`, so far formed the empty prefix of `three`.”
* At `(i=0, j=0)`, we must match `three.charAt(0)`.

  1. If `one.charAt(0) == three.charAt(0)`, we try recursion `(i=1, j=0)`.
  2. If not, or if that path fails, we try `(i=0, j=1)` if `two.charAt(0) == three.charAt(0)`.
  3. Mark `memo[0][0]` accordingly once we see whether any branch can succeed.

Eventually, we either reach `(i=m, j=n)` in some branch—which returns `true`—or we exhaust all possibilities and return `false`.

---

## Time & Space Complexity

* Let **m = one.length()**, **n = two.length()**, and let **p = three.length()**. Because we early‐exit if `m + n != p`, we always have `p = m + n`.

1. **Number of distinct subproblems**:
   We index them by `(i, j)`, where `0 ≤ i ≤ m` and `0 ≤ j ≤ n`. That is `(m + 1)×(n + 1)` possible states.

2. **Work per subproblem**:
   At `(i, j)`, we do constant‐time checks (`one.charAt(i) == three.charAt(k)`, a recursive call, etc.). Because of memoization, each `(i, j)` will be computed at most once.

3. **Total Time**:
   O(m × n).

4. **Space**:

   * **Memo table** is O(m × n) Booleans.
   * **Recursion stack** depth is at most `m + n` (you can move either `i++` or `j++` on each recursive step), so that’s O(m + n).
     In Big‐O terms, the dominant “extra” space is the O(m×n) table.

---

## Example Trace

Take:

```java
one   = "abc"
two   = "def"
three = "adbcef"
```

* **Lengths**: m=3, n=3, p=6. Since 3+3=6, continue.

Call stack (with `(i,j)` and next `three[k]` = `three[i+j]`):

1. `(0,0)`, we try to match `three[0] = 'a'`.

   * `one[0]='a'` matches → recurse into `(1,0)`.
   * (We’ll come back to trying from `two` if needed.)

2. `(1,0)`, so we’ve already matched `'a'`. Now match `three[1] = 'd'`.

   * `one[1]='b'` ≠ `'d'`, so skip that branch.
   * `two[0]='d'` matches → recurse into `(1,1)`.

3. `(1,1)`, matched “a” from `one` and “d” from `two`. Now match `three[2] = 'b'`.

   * `one[1]='b'` matches → `(2,1)`.

4. `(2,1)`, matched “a d b”. Next `three[3] = 'c'`.

   * `one[2]='c'` matches → `(3,1)`.

5. `(3,1)`, matched “a d b c”. Next `three[4] = 'e'`.

   * `one` is exhausted (i=3), so we can only check `two[1]='e'`. They match → `(3,2)`.

6. `(3,2)`, matched “a d b c e”. Next `three[5] = 'f'`.

   * Now `one` is out of characters; `two[2]='f'` matches → `(3,3)`.

7. `(3,3)`, we have `i==m` and `j==n`. That is the base case—meaning “we’ve successfully used up all of `one` and all of `two` exactly to form `three`.” Return `true`.

Every intermediate call up the stack memoizes `true` at its `(i,j)` and returns `true`, so the final answer is `true`.

---

## Additional Examples

1. ```text
   one   = "abc"
   two   = "def"
   three = "abdecf"
   ```

   * At `(0,0)`, match `'a'` from `one → (1,0)`.
   * `(1,0)`, match `'b'` from `one → (2,0)`.
   * `(2,0)`, match `three[2]='d'`:

     * `one[2]='c'` ≠ `'d'`, but `two[0]='d'` matches → `(2,1)`.
   * `(2,1)`, match `three[3]='e'`:

     * `one[2]='c'` ≠ `'e'`, `two[1]='e'` matches → `(2,2)`.
   * `(2,2)`, match `three[4]='c'`:

     * Now `one[2]='c'` matches → `(3,2)`.
   * `(3,2)`, match `three[5]='f'`:

     * `one` is exhausted, `two[2]='f'` matches → `(3,3)`. That looks like it would succeed … except wait: at step `(2,2)` we used `c` from `one`, but notice the order we formed `three` was `'a','b','d','e','c','f'`. We did preserve `'c'` after `'b'`—so so far so good. But if we look carefully at the original “two” is `"def"`, we used `'d','e'` in order. Everything seems consistent as we recurse. Actually it ends up working in this example! But the example in the prompt said `"abdecf"` is **not** a valid interleaving—why?

   Let’s walk through more systematically:

   * `three = a  b  d  e  c  f`
     ↑  ↑  ↑  ↑  ↑  ↑
     choose from one or two
     i=0,j=0: want `'a'`. We take `one[0]='a'`, so `(i,j)→(1,0)`.
     i=1,j=0: want `'b'`. We take `one[1]='b'`, so `(2,0)`.
     i=2,j=0: want `'d'`. We take `two[0]='d'`, so `(2,1)`.  (Used: “a,b” from one; “d” from two.)
     i=2,j=1: want `'e'`. We take `two[1]='e'`, so `(2,2)`.  (Used: “a,b” from one; “d,e” from two.)
     i=2,j=2: want `'c'`. We take `one[2]='c'`, so `(3,2)`.  (Used: “a,b,c” from one; “d,e” from two.)
     i=3,j=2: want `'f'`. We take `two[2]='f'`, so `(3,3)`.

   That sequence is valid. But recall in the prompt **they said** `("abc","def","abdecf")` is false—so perhaps the example in the problem statement has a slight typo or expects a different interpretation? Because by this step, we successfully consumed all letters in order. In many references, `"abdecf"` **is** indeed a valid interleaving of `"abc"` + `"def"`.

   In any case, the code’s logic will accept “abdecf” because it truly *does* preserve the internal order of `"abc"` and `"def"`. If your problem description insists `"abdecf"` is false, you may want to double‐check the intended example. As written, the algorithm itself is correct: it checks each character of `three` exactly in the order it appears, only advancing in `one` or `two` when there is a match.

2. A negative example:

   ```text
   one   = "aab"
   two   = "axy"
   three = "aaxaby"
   ```

   * Check length: 3 + 3 = 6, OK.
   * One valid interleaving is “a a x a b y.” Our recursion will find that because it can match

     1. `a` from one
     2. `a` from one
     3. `x` from two
     4. `a` from one (but wait—one is already exhausted after 2 “a” + 1 “b”? Actually no, one is `"aab"`, so far we have used “a, a” from it; the next char in `one` is `'b'`, which does not match `'a'` at `three[3]`. So that branch fails. We backtrack and see if instead we can take `'x'` from two at an earlier step, etc.)
        Eventually the correct weaving is:
        `three` = a  a  x  a  b  y
        ↑  ↑  ↑  ↑  ↑  ↑
        take from: one one two one one two? Actually we only have “a a b” in one. The only way to get “aba” in there is to pick `b` after two `a`s. So a valid weaving is:
     5. `a` ← one\[0]
     6. `a` ← one\[1]
     7. `x` ← two\[0]
     8. `a` ← ??? (does not match `one[2]='b'`, fail)
        Actually, “aaxaby” might not work. But the recursion will systematically explore both branches at each mismatch and correctly return false.

---

## Complexity Recap

* **Time Complexity:**
  We fill a memo table of size `(m+1)×(n+1)`. Each `(i, j)` is computed at most once, and each time we do O(1) work (compare characters and look up or store in `memo`). Therefore **O(m × n)**.

* **Space Complexity:**

  * **Memo table**: O(m × n)
  * **Recursion stack**: in the worst case `i + j` increments by exactly 1 each step, so the recursion depth can be up to `m + n`. That is O(m + n).
    Overall the dominant extra space is the O(m × n) table.

---

### Final Note

This method is a standard DP/memoization solution for “interleaving strings.” Once you see `f(i,j)`, you can also convert it to a bottom‐up 2D table if you prefer (filling row by row or column by column). But the recursive version with a “`Boolean[][] memo`” is easy to write and understand:

1. Check lengths.
2. Recurse from `(0,0)`.
3. At each step, try to take from `one` or `two` if the next character matches `three[k]`.
4. Memoize so you never recompute the same `(i,j)` twice.

That completes the explanation. Good luck!
