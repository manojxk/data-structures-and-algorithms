Below is a concise walkthrough of the “Numbers In Pi” problem and why the provided dynamic‐programming solution runs in $O(n^2)$ time and $O(n)$ space (versus a brute‐force $O(2^n)$ approach).

---

## Problem Restatement

* You have a string `pi` of length $n$ (the first $n$ digits of π, e.g. `"31415926…"`).
* You also have a set of valid “number” strings (each itself made of digits).
* You want to insert as few spaces as possible into `pi` so that every chunk (between spaces) is one of those valid numbers.
* Return the minimum number of spaces needed. If no such segmentation exists, return $-1$.

*For example:*

```text
pi = "3141592653589793238462643383279"
numbers = [
  "314159265358979323846",
  "26433",
  "8",
  "3279",
  "314159265",
  "35897932384626433832",
  "79"
]
// One valid segmentation is
// "314159265 | 35897932384626433832 | 79"
// which uses exactly 2 spaces. Hence the answer is 2.
```

---

## 1. Brute‐Force Approach (Exponential)

1. Recursively try every possible “cut” position in `pi`.
2. At each index `idx`, attempt to take `pi[idx..i]` (for all $i \ge idx$) as the next chunk.
3. If that chunk is in the dictionary, recurse on the suffix starting at `i+1`, counting one more space.
4. Among all valid splits, pick the minimum number of spaces. If no split works, return $\infty$.

Because each character can either be “spaced” or “not spaced,” there are on the order of $2^n$ ways to insert spaces. Hence this is $O(2^n)$ time. In code:

```java
private static int findMinSpacesBruteForce(String pi, Set<String> numbers, int idx) {
  if (idx == pi.length()) {
    // Reached the end: no further spaces needed
    return -1;
  }
  int minSpaces = Integer.MAX_VALUE;
  // Try every possible next cut from idx up to end
  for (int i = idx; i < pi.length(); i++) {
    String chunk = pi.substring(idx, i + 1);
    if (numbers.contains(chunk)) {
      int spacesInSuffix = findMinSpacesBruteForce(pi, numbers, i + 1);
      if (spacesInSuffix != Integer.MAX_VALUE) {
        // If suffix can be split, add one space here
        minSpaces = Math.min(minSpaces, spacesInSuffix + 1);
      }
    }
  }
  return minSpaces;
}
```

* **Time Complexity**: $O(2^n)$ in the worst case (exponential).
* **Space Complexity**: $O(n)$ recursion depth plus the cost of substring creation.

Because $n$ can be quite large (e.g. 25–30 digits of π), this blows up quickly. We must optimize.

---

## 2. Optimized Dynamic Programming (O(n²) Time, O(n) Space)

### Core Idea

Instead of recursively re‐computing every suffix from scratch, we build a one‐dimensional array `dp` of length $n$, where:

* `dp[i]` = “the minimum number of spaces needed to split `pi[0..i]` into valid chunks.”
* If `pi[0..i]` itself is a valid number, we set `dp[i] = 0` (no spaces needed).
* Otherwise, we look for a position `j < i` such that:

  1. `dp[j]` is not $\infty$, and
  2. `pi[j+1..i]` is in the dictionary.
     Then

  $$
    dp[i] \;=\; \min_{\,0 \le j < i,\; \text{“}pi[j+1..i]\in\mathrm{numbers}\text{”}} 
      \bigl(dp[j] + 1\bigr).
  $$

  (We add 1 because there must be a space between the chunk ending at index $j$ and the chunk $j+1..i$.)

At the end, `dp[n−1]` is the answer. If it never got updated (remains $\infty$), return $-1$.

### Pseudocode Outline

```java
public static int minSpacesOptimized(String pi, Set<String> numbers) {
  int n = pi.length();
  // dp[i] = minimum spaces to split pi[0..i], or INF if not possible
  int[] dp = new int[n];
  Arrays.fill(dp, Integer.MAX_VALUE);

  for (int i = 0; i < n; i++) {
    // 1) If the entire prefix pi[0..i] is itself a valid number, no spaces needed.
    String prefix = pi.substring(0, i + 1);
    if (numbers.contains(prefix)) {
      dp[i] = 0;
    }

    // 2) If dp[i] is still INF, we can’t end a chunk exactly at i. Skip.
    if (dp[i] == Integer.MAX_VALUE) continue;

    // 3) Otherwise, try to extend to positions j > i, forming a new chunk pi[i+1..j].
    for (int j = i + 1; j < n; j++) {
      String chunk = pi.substring(i + 1, j + 1);
      if (numbers.contains(chunk)) {
        dp[j] = Math.min(dp[j], dp[i] + 1);
      }
    }
  }

  // If dp[n-1] was never updated, return -1
  return (dp[n - 1] == Integer.MAX_VALUE) ? -1 : dp[n - 1];
}
```

### Detailed Explanation

1. **Initialization**
   We create `int[] dp = new int[n]` and fill it with a large sentinel (e.g. `Integer.MAX_VALUE`), meaning “no valid segmentation up to here yet.”

2. **First Pass (i = 0 … n−1)**

   * We check if `pi.substring(0, i+1)` is in `numbers`. If yes, `dp[i] = 0` because the prefix itself forms a single valid chunk and needs 0 spaces.
   * If `dp[i]` is still $\infty$, that means there’s no way to split exactly at position $i$. We `continue` to the next $i$.

3. **Extend to Later Indices (j = i+1 … n−1)**

   * Suppose we have found a valid segmentation that ends exactly at $i$. That segmentation used `dp[i]` spaces so far.
   * Now, consider any $j > i$. If the substring `pi[i+1..j]` (inclusive) is in `numbers`, then we can place one more space between positions $i$ and $i+1$. So

     $$
       \text{candidate} = dp[i] + 1,\quad
       dp[j] = \min\bigl(dp[j], \text{ candidate } \bigr).
     $$
   * In that way, any time you lock in a valid prefix up to $i$, you attempt to make a new valid chunk from $i+1$ to $j$. You keep track of the best (fewest‐spaces) way to get to $j$.

4. **Final Answer**

   * At the end, we look at `dp[n−1]`. If it’s still `Integer.MAX_VALUE`, no valid segmentation existed ⇒ return $-1$. Otherwise return `dp[n−1]`.

---

## Complexity Analysis

* **Time Complexity**

  * We have two nested loops over indices $i$ and $j$. In the worst case, for each $i$ (from 0 to $n-1$) we try all $j$ from $i+1$ to $n-1$. That is $O(n^2)$ substrings.
  * Checking `numbers.contains(substring)` is an O(1) hash‐lookup, but computing `pi.substring(...)` is $O(\text{length of substring})$. In the worst case that substring could be length $O(n)$. If you do it naively, that can push toward $O(n^3)$ in Java. However, most implementations either (a) keep a rolling hash or (b) rely on the fact that building a substring from `i+1` to `j` is $O(j - (i+1) + 1) = O(n)$. In practice, one can optimize substring checks with a trie or by precomputing all valid dictionary words. But as written, we say “each substring creation and hash‐lookup is $O(1)$” if the language’s substring is $O(1)$ (e.g. a view).
  * Assuming substring + hash‐lookup is effectively $O(1)$ or amortized constant, the double loop is $O(n^2)$.

* **Space Complexity**

  * We store `dp[n]`, an array of length $n$.
  * We also store the dictionary of valid numbers in a hash set (size up to the number of given “numbers”).
  * Hence $\boxed{O(n + \text{(size of numbers set)})}$. If the dictionary size is considered a constant relative to $n$, we write $O(n)$ extra space.

---

## Putting It All Together

```java
import java.util.*;

public class PiDigits {

  // Brute‐force (exponential) for reference
  public static int minSpacesBruteForce(String pi, Set<String> numbers) {
    int result = findMinSpacesBruteForce(pi, numbers, 0);
    return (result == Integer.MAX_VALUE ? -1 : result);
  }

  private static int findMinSpacesBruteForce(String pi, Set<String> numbers, int idx) {
    if (idx == pi.length()) {
      // If we've consumed the entire string with valid chunks, no extra space needed
      return -1;
    }
    int minSpaces = Integer.MAX_VALUE;
    // Try every possible end-of-chunk from idx…end
    for (int i = idx; i < pi.length(); i++) {
      String chunk = pi.substring(idx, i + 1);
      if (numbers.contains(chunk)) {
        int spacesInSuffix = findMinSpacesBruteForce(pi, numbers, i + 1);
        if (spacesInSuffix != Integer.MAX_VALUE) {
          // We place one space between chunk (idx..i) and whatever follows
          minSpaces = Math.min(minSpaces, spacesInSuffix + 1);
        }
      }
    }
    return minSpaces;
  }

  // Optimized DP: O(n^2) time, O(n) space
  public static int minSpacesOptimized(String pi, Set<String> numbers) {
    int n = pi.length();
    int[] dp = new int[n];
    Arrays.fill(dp, Integer.MAX_VALUE);

    for (int i = 0; i < n; i++) {
      // 1) If pi[0..i] itself is a valid number, no space needed
      String prefix = pi.substring(0, i + 1);
      if (numbers.contains(prefix)) {
        dp[i] = 0;
      }

      // 2) If we have a valid way to reach i, try to form a new chunk from i+1…j
      if (dp[i] != Integer.MAX_VALUE) {
        for (int j = i + 1; j < n; j++) {
          String chunk = pi.substring(i + 1, j + 1);
          if (numbers.contains(chunk)) {
            // dp[j] can be either its current value or “one more space after i”
            dp[j] = Math.min(dp[j], dp[i] + 1);
          }
        }
      }
    }

    return (dp[n - 1] == Integer.MAX_VALUE ? -1 : dp[n - 1]);
  }

  public static void main(String[] args) {
    String pi = "3141592653589793238462643383279";
    String[] numsArray = {
      "314159265358979323846",
      "26433",
      "8",
      "3279",
      "314159265",
      "35897932384626433832",
      "79"
    };

    Set<String> numbers = new HashSet<>(Arrays.asList(numsArray));

    System.out.println("Brute Force: " 
        + minSpacesBruteForce(pi, numbers));    // (very slow for large pi)
    System.out.println("Optimized: " 
        + minSpacesOptimized(pi, numbers));    // should print 2
  }
}
```

**Key Takeaways**

* The brute‐force backtracking tries all possible ways to slice `pi`, which is $O(2^n)$.
* The DP version reuses previously computed best splits up to each index $i$, so each pair $(i,j)$ is only considered once → $O(n^2)$.
* Because `dp[i]` stores “minimum spaces needed to split `pi[0..i]`,” you never recalculate the suffix’s cost more than once. As soon as you know the best way up to $i$, building forward to $j$ is just a matter of “one extra space” if `pi[i+1..j]` is valid.

That is why the **optimized solution** runs in $O(n^2)$ time and $O(n)$ additional space (plus whatever space the dictionary of numbers requires). If `pi.length()` is a few dozen digits, this is perfectly feasible—whereas the brute‐force recursion would blow up exponentially.
