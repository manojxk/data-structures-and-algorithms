**Problem Restatement**
Given a string `s` (1 ≤ `s.length` ≤ 1000) containing only letters and digits, return the longest substring of `s` that is a palindrome. A palindrome reads the same forwards and backwards (e.g. “racecar” or “bb”).

---

## Optimal Approach: Expand Around Center (O(n²) Time, O(1) Space)

1. **Idea**
   Every palindrome is “centered” somewhere. There are two cases:

   * **Odd‐length palindromes** have a single character as their center (e.g. “aba” is centered at ‘b’).
   * **Even‐length palindromes** have a center between two characters (e.g. “abba” is centered between the two ‘b’s).
     We can iterate over each index `i` as a potential center (and also the gap between `i` and `i+1` as a center), then expand outward as far as characters match on both sides.

2. **Expansion Function**
   Define a helper method

   ```java
   // Returns the length of the longest palindrome centered at (left, right).
   private int expandAroundCenter(String s, int left, int right) {
     while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
       left--;
       right++;
     }
     // After the loop, (left+1, right-1) is the maximal palindromic range.
     return right - left - 1; 
   }
   ```

3. **Main Loop**

   * Maintain two variables `start` and `end` to track the bounds of the longest palindrome found so far.
   * For each index `i` in `[0 .. s.length()-1]`:

     1. Compute `len1 = expandAroundCenter(s, i, i)` → length of the longest **odd‐length** palindrome centered at `i`.
     2. Compute `len2 = expandAroundCenter(s, i, i+1)` → length of the longest **even‐length** palindrome centered between `i` and `i+1`.
     3. Let `len = Math.max(len1, len2)`. If `len` is larger than `(end - start + 1)`, update `start` and `end` so they cover this new palindrome:

        ```java
        // Centered at i with length len:
        start = i - (len - 1) / 2;
        end   = i + len / 2;
        ```
   * After the loop finishes, the longest palindrome is `s.substring(start, end+1)`.

4. **Complexity**

   * **Time:** O(n²) in the worst case. For each center (there are n odd centers and n−1 even centers), we potentially expand to the entire string.
   * **Space:** O(1) extra space (besides the output). We only keep a few integer variables; the recursion/expansion is done with a simple while‐loop and no auxiliary data structures.

---

## Java Implementation

```java
package medium.strings;

public class A01LongestPalindromicSubstring {

  /**
   * Returns the longest palindromic substring of s using the "expand around center" method.
   *
   * @param s a non-empty string of length ≤ 1000, consisting of digits and English letters
   * @return the longest palindromic substring found in s
   */
  public String longestPalindrome(String s) {
    if (s == null || s.length() < 1) return "";

    int start = 0, end = 0; 
    // We'll expand around each index in s (odd‐length centers), 
    // and also around each pair (even‐length centers).
    
    for (int i = 0; i < s.length(); i++) {
      int len1 = expandAroundCenter(s, i, i);     // odd‐length
      int len2 = expandAroundCenter(s, i, i + 1); // even‐length
      int len = Math.max(len1, len2);
      
      if (len > (end - start + 1)) {
        // Compute new start/end indices from center i and length len
        start = i - (len - 1) / 2;
        end   = i + len / 2;
      }
    }

    return s.substring(start, end + 1);
  }

  /**
   * Helper: Expand outwards from indices (left, right) while chars match.
   * @return the length of the palindrome found.
   */
  private int expandAroundCenter(String s, int left, int right) {
    while (left >= 0 
           && right < s.length() 
           && s.charAt(left) == s.charAt(right)) {
      left--;
      right++;
    }
    // When the while loop breaks, (left+1) .. (right-1) was the last valid palindrome
    return right - left - 1;
  }

  // ---------- Demo/Test ----------
  public static void main(String[] args) {
    A01LongestPalindromicSubstring solver = new A01LongestPalindromicSubstring();

    String s1 = "babad";
    System.out.println(
      "Input: \"" + s1 + "\" → Output: \"" 
      + solver.longestPalindrome(s1) + "\"");
    // Output is either "bab" or "aba" (both are valid)

    String s2 = "cbbd";
    System.out.println(
      "Input: \"" + s2 + "\" → Output: \"" 
      + solver.longestPalindrome(s2) + "\"");
    // Output: "bb"
  }
}
```

---

### How It Works

1. **Loop Over Centers**

   * For each index `i`, treat it first as the center of an **odd‐length** palindrome, then as the “left middle” of an **even‐length** palindrome.
   * We call `expandAroundCenter(s, i, i)` (odd) and `expandAroundCenter(s, i, i+1)` (even).

2. **Expanding Logic**

   * In `expandAroundCenter`, we move two pointers `left` and `right` outward as long as:

     * `left ≥ 0`
     * `right < s.length()`
     * `s.charAt(left) == s.charAt(right)`.
   * Once any of those fails, we know we’ve gone just past the matching range. The length of that palindrome is `right – left – 1`.

3. **Tracking the Longest**

   * Whenever we get a palindrome length `len` that exceeds our previously recorded maximum `(end − start + 1)`, we recompute:

     * `start = i − (len − 1) / 2`
     * `end   = i + len / 2`
       This correctly centers and spans a palindrome of length `len` around index `i`.

4. **Result Extraction**

   * After checking all centers, the substring from `start` to `end` (inclusive) is the longest palindromic substring. We return `s.substring(start, end + 1)`.

This method handles both odd and even palindromes in one pass and only uses constant extra memory beyond the input and the returned substring.
