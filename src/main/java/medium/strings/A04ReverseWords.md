Below are two valid approaches—one “brute‐force” (using split/reverse) and one truly in-place (without split or reverse functions)—both of which run in $O(n)$ time and $O(1)$ extra space (aside from the output). The optimized version preserves all original whitespace exactly as it appeared.

---

## 1) Brute‐Force (Using `split` and `reverse`)

> **Complexities:**
>
> * Time: $O(n)$, where $n = $ length of the string (splitting and joining also cost $O(n)$).
> * Space: $O(n)$, because we build an array of words and then re-join them.

```java
import java.util.*;

public class A04ReverseWords {

  /**
   * Splits on one-or-more whitespace, reverses the array of words, and rejoins with single spaces.
   * Note: This will collapse any run of multiple spaces down to a single space in the output.
   */
  public static String reverseWordsBruteForce(String s) {
    // Trim leading/trailing whitespace, then split on one or more spaces
    String[] words = s.trim().split("\\s+");
    // Reverse the array of words
    List<String> wordList = Arrays.asList(words);
    Collections.reverse(wordList);
    // Join with a single space
    return String.join(" ", wordList);
  }

  public static void main(String[] args) {
    String input = "AlgoExpert   is   the    best!  ";
    String output = reverseWordsBruteForce(input);
    System.out.println(output);
    // Prints: "best! the is AlgoExpert"
    // (all extra spaces collapse to single spaces)
  }
}
```

*Drawback:*

* Because it uses `split("\\s+")`, any stretch of multiple spaces in the original string becomes a single space in the result. If you need to preserve the exact spacing (including multiple spaces), see the optimized approach below.

---

## 2) Optimized In-Place Reversal (Preserving All Original Whitespace)

> **Complexities:**
>
> * Time: $O(n)$ — one pass to reverse each word, one pass to reverse the entire character array.
> * Space: $O(1)$ extra (we only manipulate the input character array in place; no auxiliary arrays or lists, aside from the final `String` construction).

**Key Idea:**

1. Convert the string to a `char[]`.
2. **First pass:** Scan left→right. Whenever you detect a whole word (a maximal run of non-space characters), reverse that slice in place.
3. **Second pass:** Reverse the entire `char[]`.
4. Converting back to `String` automatically preserves every space exactly where it belongs (because we never collapsed or removed any whitespace).

In effect, reversing each word individually makes `"Hello   world"` become `"olleH   dlrow"`. Reversing the entire array then yields `"dlrow   olleH"`, which is equivalent to `"world   Hello"` with all spaces preserved.

```java
package medium.strings;

public class A04ReverseWords {

  /**
   * Helper: reverse the subarray s[begin..end], inclusive.
   */
  private static void reverse(char[] s, int begin, int end) {
    while (begin < end) {
      char tmp = s[begin];
      s[begin++] = s[end];
      s[end--] = tmp;
    }
  }

  /**
   * Reverses the order of words in the char[] in place, preserving every original whitespace.
   *
   * @param s A char array that represents the original string.
   *          Words are separated by one or more spaces (ASCII 0x20).
   *          After calling this, s[] will be mutated so that the words appear in reverse order.
   */
  public static void reverseWordsOptimized(char[] s) {
    int n = s.length;
    int wordStart = -1;

    // 1) Reverse each individual word in place.
    for (int i = 0; i < n; i++) {
      // If we see a non-space and wordStart < 0, we’ve just found the start of a new word
      if (wordStart < 0 && s[i] != ' ') {
        wordStart = i;
      }
      // If we are in a word (wordStart ≥ 0) and the next char is a space or i==n-1,
      // we can reverse that word from wordStart..i (inclusive).
      if (wordStart >= 0 && (i == n - 1 || s[i + 1] == ' ')) {
        reverse(s, wordStart, i);
        wordStart = -1;
      }
    }

    // 2) Reverse the entire array to “flip” the word order.
    reverse(s, 0, n - 1);
  }

  public static void main(String[] args) {
    String original = "AlgoExpert   is   the    best!";
    char[] arr = original.toCharArray();

    reverseWordsOptimized(arr);
    String reversed = new String(arr);

    System.out.println("Original: \"" + original + "\"");
    System.out.println("Reversed: \"" + reversed + "\"");
    // Prints:
    // Original: "AlgoExpert   is   the    best!"
    // Reversed: "best!    the   is   AlgoExpert"
    //
    // Notice that all runs of spaces (two, three, four, etc.) remain exactly as in the original.
  }
}
```

**Why this works:**

1. **Reversing each word first**:

   * Suppose the string is `"Hello   world"`. Converting to a `char[]` gives

     ```
     ['H','e','l','l','o',' ',' ',' ','w','o','r','l','d']
     ```
   * Scanning left→right, you reverse the slice `[0..4]` (“Hello” → “olleH”), then skip spaces, then reverse `[8..12]` (“world” → “dlrow”). The array becomes

     ```
     ['o','l','l','e','H',' ',' ',' ','d','l','r','o','w']
     ```

2. **Reversing the entire array next**:

   * That array reversed yields

     ```
     ['w','o','r','l','d',' ',' ',' ','H','e','l','l','o']
     ```
   * Which is exactly `"world   Hello"` with all original spaces preserved.

3. **Leading/trailing spaces** are never removed—whatever was in the input stays in the output, only the words appear in reversed order.

---

### Summary

* **If you don’t care about preserving multiple spaces exactly**, you can use the **brute-force** version that calls `split("\\s+")` and `Collections.reverse(...)`.
* **If you must preserve every single whitespace character exactly**, use the **optimized in-place** approach shown above—no calls to `split` or `reverse(String)`—just constant extra space and two simple reversals on the character array.
