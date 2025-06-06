Let’s walk through this **Pattern Matcher** problem step by step, with a focus on clarity and no gaps—just like a beginner’s coding lesson. We’ll start by restating the problem, work through a concrete example, explain each part of the code in detail, and finish by analyzing time and space complexity.

---

## 📝 Problem Restatement

You have two non-empty strings:

1. **`pattern`**: A string of length *P* consisting only of the characters `'x'` and `'y'`.
2. **`str`**: A longer string of length *N*.

We want to find two (nonempty) substrings—let’s call them **`xSubstring`** and **`ySubstring`**—such that when you replace every `'x'` in `pattern` by `xSubstring` and every `'y'` in `pattern` by `ySubstring`, you reconstruct exactly the input `str`. In other words, if

```
pattern = "xxyxxy"
xSubstring = "gogo"
ySubstring = "powerranger"
```

then “xxyxxy” → “gogo” + “gogo” + “powerranger” + “gogo” + “gogo” + “powerranger” = `"gogopowerrangergogopowerranger"`, which matches `str`.

If no such pair (`xSubstring`, `ySubstring`) exists, we return an empty array (`new String[]{}`).

---

## 🔎 Concrete Example

Let’s take the sample from the prompt:

```
pattern = "xxyxxy"
str     = "gogopowerrangergogopowerranger"
```

* Pattern length *P* = 6.
* String length *N* = 28.

We want to find:

* Some substring for `'x'` (call it `X`),
* Some substring for `'y'` (call it `Y`),

so that if we expand the pattern “xxyxxy” → X + X + Y + X + X + Y, we get exactly `"gogopowerrangergogopowerranger"`.

The solution is:

* X = `"gogo"`
* Y = `"powerranger"`

Check:

1. pattern\[0] = 'x'  → `"gogo"`
2. pattern\[1] = 'x'  → `"gogo"`
3. pattern\[2] = 'y'  → `"powerranger"`
4. pattern\[3] = 'x'  → `"gogo"`
5. pattern\[4] = 'x'  → `"gogo"`
6. pattern\[5] = 'y'  → `"powerranger"`

Concatenate:
`"gogo"` + `"gogo"` + `"powerranger"` + `"gogo"` + `"gogo"` + `"powerranger"`
\= `"gogopowerrangergogopowerranger"` exactly.

Hence the result array is `["gogo", "powerranger"]`.

---

## 🎯 High-Level Approach

1. **Edge Cases**

   * If `pattern.length()` > `str.length()`, it’s impossible: return `new String[]{}` immediately.

2. **Ensure Pattern Starts with 'x'**

   * It’s easier to assume the first character of `pattern` is `'x'` (so `'x'` is our “primary” letter).
   * If `pattern.charAt(0) == 'y'`, we “flip” every `'x'` ↔ `'y'` (swap them) so that the new pattern starts with `'x'`.
   * Remember (by a boolean flag) whether we flipped, so that when we return, we know in which order to output `[xSubstring, ySubstring]`.

3. **Count how many 'x' and 'y' appear in the (possibly flipped) pattern**

   * Let `countX` = number of `'x'` in `pattern`.
   * Let `countY` = number of `'y'` in `pattern`.

4. **Guess the length of X-substring**

   * We don’t know upfront how many characters X should be in `str`. But we do know:

     * If X is of length `lenX`, then total characters used by all X-placements = `countX * lenX`.
     * The rest of the characters (of `str`) must go to Y-placements, i.e. total characters for Y = `str.length()` − `countX * lenX`.
   * If `countY > 0`, then `lenY` must be exactly `(str.length() − countX * lenX) / countY`.

     * **But** `(str.length() − countX * lenX)` must be divisible by `countY`, otherwise no valid integer length for Y → skip.
   * If `countY == 0` (pattern contains only X’s), then `lenY = 0` and all of `str` must be made up of X repeats. (We handle that as a special case.)

5. **From a particular `lenX` (and derived `lenY`), “read off” where X and Y must appear in `str`**

   * We know the first `'x'` of the pattern corresponds to the first `lenX` characters of `str` → candidate X = `str.substring(0, lenX)`.
   * We know the first `'y'` appears at index `pattern.indexOf('y')` (in the pattern). Suppose that index in the pattern is `firstYPosPattern`.

     * The substring in `str` that corresponds to that first Y must begin at
       `firstYPosPattern * lenX` characters into `str`
       (because every earlier letter in the pattern is guaranteed to be an ‘x’ or ‘y’, each mapped to `lenX` or `lenY` sequentially).
     * So candidate Y =

       ```
       str.substring(
         firstYPosPattern * lenX,
         firstYPosPattern * lenX + lenY
       );
       ```

6. **Reconstruct the entire string from the guessed X and Y**

   * Build a `StringBuilder` by iterating over each character in the (possibly flipped) `pattern`:

     * if char == 'x', append candidate X;
     * if char == 'y', append candidate Y.
   * If the constructed string equals `str`, we have found a valid mapping.

7. **Return the two substrings in the correct order**

   * If we had flipped originally (because `pattern` started with `'y'`), then we must return `[Y, X]` instead of `[X, Y]`, because “x” and “y” swapped meaning.
   * Otherwise return `[X, Y]`.

If we exhaust all possible `lenX` (from 0 up to `str.length()`), and never find a match, we return an empty array.

---

## 🔍 Detailed Step-by-Step Breakdown

We’ll go line by line, clarifying every detail:

```java
public static String[] patternMatcher(String pattern, String str) {
    // If pattern is longer than str, impossible to match.
    if (pattern.length() > str.length()) return new String[] {};
```

* If `pattern` has more characters than `str`, you cannot map each character of `pattern` to at least one character in `str`. So return `[]` immediately.

```java
    // STEP 1: Flip the pattern if it starts with 'y'.
    boolean isFlipped = (pattern.charAt(0) != 'x');
    if (isFlipped) {
        pattern = flipPattern(pattern);
    }
```

* We want `pattern.charAt(0)` to be `'x'`.

  * If it’s `'y'`, set a flag `isFlipped = true` and call `flipPattern(pattern)`, which returns a new string where every `'x'`↔ `'y'` is swapped.
  * Example: if pattern was `"yxyx"`, `flipPattern` → `"xyxy"`.
  * We keep track that we flipped so we can swap back in the output order later.

```java
    // STEP 2: Count how many 'x' and 'y' in the (possibly flipped) pattern
    Map<Character, Integer> counts = countPatternChars(pattern);
    int countOfX = counts.get('x');
    int countOfY = counts.get('y');
```

* Traverse each char in `pattern`—increment the counter for `'x'` or `'y'`.
* In our example `"xxyxxy"`, we get `countOfX = 4`, `countOfY = 2`.

```java
    // STEP 3: Try every possible length for X (from 0 up to str.length())
    for (int lenOfX = 0; lenOfX <= str.length(); lenOfX++) {
        // Total chars contributed by X's in the final string:
        int totalXChars = lenOfX * countOfX;
        // Remaining chars (to be used by Y's) = N - totalXChars
        int remainingLength = str.length() - totalXChars;

        // If there are no Y's, we do not divide by zero: we just require remainingLength == 0.
        if (countOfY == 0) {
            if (remainingLength != 0) {
                // If pattern has no 'y', then all of str must be made up of repeats of X.
                // But if remainingLength != 0, that means lenOfX * countOfX != str.length(),
                // so skip this lenOfX.
                continue;
            }
            // If we get here, countOfY == 0 AND remainingLength == 0, so lenOfY = 0.
        } else {
            // Otherwise (pattern contains 'y'), remainingLength must be divisible by countOfY.
            if (remainingLength < 0 || remainingLength % countOfY != 0) {
                continue; // not an integer length for Y → skip
            }
        }

        int lenOfY = (countOfY == 0) ? 0 : (remainingLength / countOfY);
```

* We loop `lenOfX` from 0 to `str.length()`.

  * `totalXChars = lenOfX * countOfX`: how many characters in `str` will come from all X’s.
  * `remainingLength = str.length() − totalXChars`: how many characters must come from all Y’s.
  * If `countOfY == 0`, it means the pattern has no `'y'` at all; so the entire string must be X repeated. In that special case, we require `remainingLength == 0` so that `lenOfX * countOfX == str.length()`. If not, skip. If yes, then `lenOfY = 0`.
  * If `countOfY > 0`, then `remainingLength` must be ≥ 0 and divisible by `countOfY`. If not, skip. Otherwise `lenOfY = remainingLength / countOfY`.

At this point, we have integers `lenOfX` and (if relevant) `lenOfY`.

```java
        // STEP 4: Deduce the actual substrings X and Y from the positions in str.

        // 4a. The first 'x' in pattern is at index 0 in pattern, 
        // so X must be the first lenOfX characters of str.
        String potentialX = str.substring(0, lenOfX);

        // 4b. To find Y, we need the index of the first 'y' in the pattern.
        // Suppose firstYPos = pattern.indexOf('y'). Then X appears exactly lenOfX characters
        // for each 'x' before that, so the starting index in `str` for Y is:
        //   firstYPos * lenOfX  (because every 'x' before that consumes lenOfX chars each).
        String potentialY = "";
        if (countOfY != 0) {
            int firstYPos = pattern.indexOf('y'); 
            int startOfYInStr = firstYPos * lenOfX;
            int endOfYInStr = startOfYInStr + lenOfY;
            // Make sure we don’t go out of bounds (but we already checked divisibility).
            potentialY = str.substring(startOfYInStr, endOfYInStr);
        }
```

* **Important detail**:

  * The very first character of `pattern` must be `'x'` (because we flipped if necessary). So the first time we see an `'x'` in the pattern, it corresponds to `str.substring(0, lenOfX)`. That is our candidate for X.
  * To get Y, we look at where the first `'y'` appears in `pattern`. Let that index in `pattern` be `firstYPos`. Every character in `pattern` before that was `'x'`, each contributing exactly `lenOfX` characters to the final `str`. Therefore, the substring for Y must start at index `firstYPos * lenOfX` inside `str`, and must be `lenOfY` long.

```java
        // STEP 5: Reconstruct entire string from pattern using potentialX and potentialY
        String candidate = buildFromPattern(pattern, potentialX, potentialY);

        // If the re-built string matches str exactly, we have found a valid solution.
        if (str.equals(candidate)) {
            // If we flipped at the start, we must return [Y, X]; otherwise [X, Y].
            if (isFlipped) {
                return new String[]{ potentialY, potentialX };
            } else {
                return new String[]{ potentialX, potentialY };
            }
        }
    }

    // If no valid mapping found, return an empty array.
    return new String[]{};
}
```

* `buildFromPattern(...)` simply loops through each character in `pattern`. If it sees `'x'`, it appends `potentialX` to a `StringBuilder`; if `'y'`, it appends `potentialY`. In the end, we compare that built string to the original `str`. If they match exactly, we’ve succeeded.

---

## 📜 Helper Methods Explained

### 1. `flipPattern(String pattern)`

```java
private static String flipPattern(String pattern) {
    char[] flipped = new char[pattern.length()];
    for (int i = 0; i < pattern.length(); i++) {
        // If original is 'x', make it 'y'; if 'y', make it 'x'.
        flipped[i] = (pattern.charAt(i) == 'x') ? 'y' : 'x';
    }
    return new String(flipped);
}
```

* This runs in O(P) time, creating a new string where every `'x'` becomes `'y'`, and every `'y'` becomes `'x'`. We do this so that the **first character of `pattern` is guaranteed to be `'x'`**. That simplifies the indexing logic.

---

### 2. `countPatternChars(String pattern)`

```java
private static Map<Character, Integer> countPatternChars(String pattern) {
    Map<Character, Integer> counts = new HashMap<>();
    counts.put('x', 0);
    counts.put('y', 0);
    for (char ch : pattern.toCharArray()) {
        counts.put(ch, counts.get(ch) + 1);
    }
    return counts;
}
```

* Just loops once over the pattern (O(P)) and returns a map `{ 'x' → countOfX, 'y' → countOfY }`.

---

### 3. `buildFromPattern(String pattern, String x, String y)`

```java
private static String buildFromPattern(String pattern, String x, String y) {
    StringBuilder sb = new StringBuilder();
    for (char ch : pattern.toCharArray()) {
        if (ch == 'x') {
            sb.append(x);
        } else { // ch == 'y'
            sb.append(y);
        }
    }
    return sb.toString();
}
```

* Also O(P \* (max(lenX, lenY))) in the worst case because each pattern character appends a full substring. But in practice, the total length appended is exactly N (the length of `str`), so rebuilding is O(N).

---

## 🧮 Full Code (for Reference)

```java
package hard.strings;

import java.util.*;

public class A03PatternMatcher {

  // Main function to match pattern to string
  public static String[] patternMatcher(String pattern, String str) {
    // Edge case: pattern longer than str → impossible
    if (pattern.length() > str.length()) return new String[] {};

    // STEP 1: If pattern starts with 'y', flip all x<->y
    boolean isFlipped = (pattern.charAt(0) != 'x');
    if (isFlipped) {
      pattern = flipPattern(pattern);
    }

    // STEP 2: Count how many x's and y's in the (possibly flipped) pattern
    Map<Character, Integer> counts = countPatternChars(pattern);
    int countOfX = counts.get('x');
    int countOfY = counts.get('y');

    // STEP 3: Try all possible lengths for substring X
    for (int lenOfX = 0; lenOfX <= str.length(); lenOfX++) {
      int totalXChars = lenOfX * countOfX;
      int remainingLength = str.length() - totalXChars;

      // Determine if we can find an integer length for Y
      if (countOfY == 0) {
        // If no Y in pattern, then remainingLength must be 0
        if (remainingLength != 0) {
          continue;
        }
      } else {
        // If there are Y’s, remainingLength must be divisible by countOfY
        if (remainingLength < 0 || remainingLength % countOfY != 0) {
          continue;
        }
      }

      int lenOfY = (countOfY == 0) ? 0 : (remainingLength / countOfY);

      // STEP 4: Deduce the actual substrings for X and Y from `str`
      String potentialX = str.substring(0, lenOfX);
      String potentialY = "";
      if (countOfY != 0) {
        int firstYPos = pattern.indexOf('y');
        int startOfYInStr = firstYPos * lenOfX;
        int endOfYInStr = startOfYInStr + lenOfY;
        potentialY = str.substring(startOfYInStr, endOfYInStr);
      }

      // STEP 5: Reconstruct full string from pattern and check
      String candidate = buildFromPattern(pattern, potentialX, potentialY);
      if (str.equals(candidate)) {
        // If we flipped at the start, swap X/Y back in the output
        if (isFlipped) {
          return new String[]{ potentialY, potentialX };
        } else {
          return new String[]{ potentialX, potentialY };
        }
      }
    }

    // No valid mapping found
    return new String[]{};
  }

  // Helper: flip every 'x'<->'y' in the pattern
  private static String flipPattern(String pattern) {
    char[] flipped = new char[pattern.length()];
    for (int i = 0; i < pattern.length(); i++) {
      flipped[i] = (pattern.charAt(i) == 'x') ? 'y' : 'x';
    }
    return new String(flipped);
  }

  // Helper: count 'x' and 'y' in the pattern
  private static Map<Character, Integer> countPatternChars(String pattern) {
    Map<Character, Integer> counts = new HashMap<>();
    counts.put('x', 0);
    counts.put('y', 0);
    for (char ch : pattern.toCharArray()) {
      counts.put(ch, counts.get(ch) + 1);
    }
    return counts;
  }

  // Helper: rebuild a string from the pattern using substrings x and y
  private static String buildFromPattern(String pattern, String x, String y) {
    StringBuilder sb = new StringBuilder();
    for (char ch : pattern.toCharArray()) {
      sb.append(ch == 'x' ? x : y);
    }
    return sb.toString();
  }

  // Example usage in main
  public static void main(String[] args) {
    // Example 1
    String pattern1 = "xxyxxy";
    String str1 = "gogopowerrangergogopowerranger";
    System.out.println(Arrays.toString(patternMatcher(pattern1, str1)));
    // Expected output: ["gogo", "powerranger"]

    // Example 2 (pattern starts with 'y')
    String pattern2 = "yxy";
    String str2 = "abababab";
    System.out.println(Arrays.toString(patternMatcher(pattern2, str2)));
    // Expected output: ["a", "b"]
  }
}
```

---

## ⏱ Time Complexity Analysis

1. **Flipping the pattern (if needed)**: O(P) where P = `pattern.length()`.
2. **Counting 'x' and 'y'**: O(P).
3. **Main loop over `lenOfX` from 0 to N**:

   * There are up to N+1 choices for `lenOfX`.
   * For each `lenOfX`, we do O(1) arithmetic, then:

     * Extract `potentialX` via `substring(0, lenOfX)` → O(lenOfX), but amortized over all iterations this is ≤ O(N) each time.
     * Extract `potentialY` via one `substring(...)` → O(lenOfY), ≤ O(N) each time.
     * Build `candidate` by walking through the pattern:

       * There are P pattern characters, and each append is either length `lenOfX` or `lenOfY`. In total, building that candidate string is O(N) because the final length of `candidate` is exactly N (matching `str.length()`).
     * Compare `candidate.equals(str)` → O(N).

   So for each `lenOfX`, we spend O(N + P) time. Since P ≤ N, that’s O(N) per iteration. There are up to O(N) iterations.
   **Total time** = O(N) × O(N) = **O(N²)**.

---

## 💾 Space Complexity Analysis

* We use:

  * A few integer counters (`countOfX`, `countOfY`, etc.) → O(1).
  * Possibly one flipped copy of `pattern` → O(P) ≤ O(N).
  * In each iteration, we extract two substrings `potentialX`, `potentialY` → in total those combined store O(lenOfX + lenOfY) characters = O(N) in the largest case.
  * We build one `candidate` string of length N to compare with `str` → O(N).
* We never store more than about O(N) extra space overall (besides the input).

Hence **space complexity** is **O(N)**.

---

## 🔑 Key Takeaways for Beginners

1. **Why flip the pattern?**

   * We force the pattern’s first character to be `'x'` so that `X` is always found at `str.substring(0, lenOfX)`. Otherwise, if pattern started with `'y'`, the first substring in `str` would correspond to `'y'` instead. Flipping standardizes the logic.

2. **Counting X’s and Y’s**

   * By knowing how many X’s (`countOfX`) and how many Y’s (`countOfY`) appear, we can write the constraint

     ```
     countOfX * lenOfX + countOfY * lenOfY = str.length()
     ```

     and solve for `lenOfY` once we guess `lenOfX`.

3. **Finding where Y begins**

   * Because the first `'y'` in the pattern occurs at index `pattern.indexOf('y')`, and each preceding letter in `pattern` uses exactly `lenOfX` (if it’s `'x'`) or `lenOfY` (if it’s `'y'`) characters in the final expansion, we can compute exactly where in `str` that first Y must live:

     ```
     startOfY = (number_of_letters_before_firstY_that_are_x) * lenOfX
              + (number_of_letters_before_firstY_that_are_y) * lenOfY
     ```

     But since we guaranteed pattern\[0] = 'x', and we know how many X’s appear before that first Y, it simplifies to

     ```
     startOfY = firstYPos * lenOfX
     ```

     when `firstYPos` is the index of the first `'y'`.

4. **Rebuilding and checking**

   * Once you guess `X` and `Y`, you literally rebuild the entire string from `pattern` to see if it matches `str`. If it does, you’re done.

5. **Edge Cases**

   * If pattern has **only X’s** (`countOfY == 0`), then `str` must be X repeated exactly `countOfX` times. So `lenOfX = str.length() / countOfX` must be an integer and `lenOfY = 0`.
   * If pattern has **only Y’s** (`countOfX == 0` after flipping, which can’t happen because we always flip so that first char is `'x'`—pattern can’t be zero X’s if it starts with X).
   * If the very first character in `pattern` was `'y'`, we swapped everything so that our logic always treats the first letter as `'x'`. In the end, we swap back the result array.

6. **Complexity**

   * Because we try every possible `lenOfX` (0 … N) and for each we do an O(N) reconstruction/comparison, total is O(N²).
   * Space is O(N) because we hold at most a few substring copies of `str` and one rebuilt candidate of length N.

---

Once you understand these steps—flipping, counting, iterating on `lenOfX`, deducing `lenOfY`, extracting “where X and Y live” in `str`, rebuilding, and comparing—you can see why the code works end to end.

Feel free to run the provided `main(...)` examples or create your own patterns/strings to watch how `patternMatcher(...)` finds (or doesn’t find) valid X/Y mappings.
