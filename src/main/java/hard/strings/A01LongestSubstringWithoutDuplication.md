**Problem Restatement**
You’re given a string `s`. You need to find the longest substring of `s` that contains no repeated characters. In addition to reporting its length, you should also print out the substring itself—not just its length. For example:

* If `s = "abcabcbb"`, the longest substring without repeating characters is `"abc"`, which has length 3.
* If `s = "bbbbb"`, the longest substring without repeating characters is `"b"`, of length 1.
* If `s = "pwwkew"`, the answer is `"wke"` (length 3), because while `"pwke"` would be longer, it contains two `w`’s.

We want to solve this in **O(n)** time and **O(1)** (or, more precisely, *O(min(n, m))* where *m* is the alphabet size) extra space, using a straightforward “sliding‐window” (two‐pointer) approach.

---

## 1) High-Level Approach (Sliding Window + HashSet)

1. Maintain two indices, `i` and `j`, which define the “window” of characters currently under consideration:

   * `i` = start of window
   * `j` = end of window (exclusive—i.e. the window is `s[i..j−1]`)
2. Use a `HashSet<Character>` to store exactly the characters currently in `s[i..j−1]`. Because it’s a set, if you see a new character that’s not in the set, you can safely expand your window. As soon as you see a character that *is* already in the set, you must shrink the window from the left (increment `i` and remove `s[i]` from the set) until that duplicate has disappeared.
3. At each step, after adjusting for duplicates, your window `s[i..j−1]` is guaranteed to have **no repeating characters**. You compare its length, `j−i`, to the best length found so far, and if it’s larger, record its start index and length.

Because both pointers `i` and `j` only move forward—and each character is added or removed at most once—the overall algorithm runs in **O(n)** time. The `HashSet` holds at most one copy of each character in the window, so it uses **O(min(n, m))** space, where *m* = size of the character set (for a typical ASCII‐or‐Unicode string, *m* is constant).

---

## 2) Detailed Step-by-Step Explanation

### 2.1) Variables and Data Structures

* `int i = 0, j = 0;`
  These represent the inclusive start (`i`) and exclusive end (`j`) of the current sliding window:

  > Window = `s[i]`, `s[i+1]`, …, `s[j−1]`.

* `HashSet<Character> st = new HashSet<>();`
  This keeps track of exactly which characters are in `s[i..j−1]`.

* `int ans = 0;`
  This will store the length of the longest substring found so far.

* `int maxStartIdx = 0;`
  Whenever we discover a strictly larger window (`j − i > ans`), we update `ans = j − i` and set `maxStartIdx = i`. In the end, `s.substring(maxStartIdx, maxStartIdx + ans)` is the actual longest non-repeating substring.

### 2.2) Main Loop

```java
while (j < s.length()) {
    char c = s.charAt(j);

    if (!st.contains(c)) {
        // Case A: c is not yet in the set → no duplication
        st.add(c);
        
        // Now window is s[i..j] (inclusive on j), length = (j - i + 1).
        // Compare to best:
        if ( (j - i + 1) > ans ) {
            ans = j - i + 1;
            maxStartIdx = i;
        }
        
        j++;  // Expand window to the right
    }
    else {
        // Case B: c is already in the set → we must remove from the left until 'c' is gone
        st.remove(s.charAt(i));
        i++;
    }
}
```

#### How this “shrinking and expanding” works:

1. **Expand (`j++`)** only when you can add `s[j]` to the set without creating a duplicate.
2. **Shrink (`i++`)** only when you encounter a duplicate `s[j]`; you remove `s[i]` from the set and increment `i` until the duplicate character is no longer in it. In other words, you keep moving `i` forward (and removing those characters) until `s[j]` can be inserted. Only then do you return to “expand” mode again.

Since each character enters the set at most once and is removed from the set at most once, the total time spent across all expansions and shrinks is O(n).

---

## 3) Complete Java Code

```java
import java.util.HashSet;

public class A01LongestSubstringWithoutDuplication {

  /**
   * Returns the length of the longest substring without repeating characters.
   * Also prints out the substring itself.
   */
  public int lengthOfLongestSubstring(String s) {
    // A set to store the characters currently in the window s[i..j-1].
    HashSet<Character> st = new HashSet<>();

    int i = 0, j = 0;        // window boundaries
    int ans = 0;            // length of the longest window found so far
    int maxStartIdx = 0;    // starting index of that longest window

    // Slide j from 0 to s.length()-1:
    while (j < s.length()) {
      char c = s.charAt(j);

      if (!st.contains(c)) {
        // Case A: We can safely expand by adding s[j] into the set.
        st.add(c);

        // Compare current window length (j - i + 1) to the best 'ans' so far.
        if ( (j - i + 1) > ans ) {
          ans = j - i + 1;
          maxStartIdx = i;
        }

        j++;  // Move right end forward.

      } else {
        // Case B: s[j] is already in the set → must shrink from the left
        // Remove s[i] from the set and move i forward until the duplicate is gone.
        st.remove(s.charAt(i));
        i++;
      }
    }

    // After the loop, 'ans' = length of the longest substring without duplicates,
    // and 'maxStartIdx' = its starting index in s.
    String longestSub = s.substring(maxStartIdx, maxStartIdx + ans);
    System.out.println("Longest Substring Without Duplication: \"" + longestSub + "\"");

    return ans;
  }

  public static void main(String[] args) {
    A01LongestSubstringWithoutDuplication solver = new A01LongestSubstringWithoutDuplication();

    String input;

    // Example 1
    input = "abcabcbb";
    System.out.println("Input: \"" + input + "\"");
    System.out.println("Length of Longest Substring: " 
                       + solver.lengthOfLongestSubstring(input));
    //   → Expected output: 3   (longest substring "abc")

    // Example 2
    input = "bbbbb";
    System.out.println("\nInput: \"" + input + "\"");
    System.out.println("Length of Longest Substring: " 
                       + solver.lengthOfLongestSubstring(input));
    //   → Expected output: 1   (longest substring "b")

    // Example 3
    input = "pwwkew";
    System.out.println("\nInput: \"" + input + "\"");
    System.out.println("Length of Longest Substring: " 
                       + solver.lengthOfLongestSubstring(input));
    //   → Expected output: 3   (longest substring "wke")

    // Example 4 (all distinct)
    input = "abcdef";
    System.out.println("\nInput: \"" + input + "\"");
    System.out.println("Length of Longest Substring: " 
                       + solver.lengthOfLongestSubstring(input));
    //   → Expected output: 6   (longest substring "abcdef")
  }
}

/*
 * Time Complexity: O(n)
 *   Each character is visited at most twice—once when 'j' expands forward (insert into set),
 *   and once when 'i' removes it (shrink). Hence total work is linear in the string length.
 *
 * Space Complexity: O(min(n, m))
 *   The HashSet never stores more characters than the size of the character set (m), or the length of the string (n), 
 *   whichever is smaller. For an ASCII string, m is 128; for extended Unicode, m might be larger but still constant relative to n. 
 */
```

---

## 4) Why This Works in O(n)

* **Each character is added to the HashSet exactly once** (when `j` moves forward).
* **Each character is removed from the HashSet exactly once** (when `i` moves forward to eliminate a duplicate).
* Both `i` and `j` only move in the forward direction and never backtrack. In the worst case, each index can be visited twice—once to insert, once to remove. Hence the total number of steps is proportional to 2·n, i.e. **O(n)**.

Whenever we expand the window (advance `j`), we check for duplication in **O(1)** (HashSet lookup). If a duplicate is found, we remove from the left (advance `i`) until that duplicate is gone—again each removal is O(1). All told, no nested loops over the entire string are needed; each character triggers at most one insertion and one removal. That yields overall **O(n)** time.

---

## 5) Conclusion

Using the “sliding‐window + HashSet” pattern:

* We maintain a window `s[i..j-1]` that always has **no repeating characters**.
* We grow the window by moving `j` forward whenever `s[j]` is not in the set.
* As soon as `s[j]` is already in the set, we shrink from the left (increment `i`) until the duplicate is removed, then continue expanding again.
* Each time the window is valid (no duplicates), we compare its length (`j−i`) to the best solution so far (`ans`).

Because each character enters and leaves the set at most once, the entire algorithm runs in **O(n)** time with **O(min(n, m))** extra space. This is optimal for the “longest substring without duplication” problem.
