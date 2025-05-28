**Problem Explanation**

You have a **string** of lowercase English letters (e.g. `"abcdcaf"`). You need to find the **index** of the very **first** character in the string that **appears exactly once** in the entire string. If **every** character repeats at least once, return **-1**.

> **Example**
> Input: `"abcdcaf"`
> The character frequencies are:
>
> * a → 2 times
> * b → 1 time
> * c → 2 times
> * d → 1 time
> * f → 1 time
>
> Scanning left to right:
>
> * `a` repeats → skip
> * `b` appears only once → **return index 1**

---

## Approach 1: Brute-Force (O(n²) Time | O(1) Space)

1. **For** each position `i` in the string:
2. Let `ch = string.charAt(i)`.
3. **Scan** the entire string again (all `j ≠ i`) to see if any other character equals `ch`.
4. If you never find a match, **return** `i`.
5. If you finish the outer loop without finding any unique character, **return** `-1`.

```java
public static int firstNonRepeatingCharacterBruteForce(String s) {
  int n = s.length();
  for (int i = 0; i < n; i++) {
    char c = s.charAt(i);
    boolean repeats = false;
    // Check every other position
    for (int j = 0; j < n; j++) {
      if (i != j && s.charAt(j) == c) {
        repeats = true;
        break;
      }
    }
    if (!repeats) {
      return i;  // found a non-repeating char
    }
  }
  return -1;  // none found
}
```

* **Time Complexity:**
  Outer loop runs **n** times, inner loop up to **n** times → **O(n²)**.
* **Space Complexity:**
  Only a few variables → **O(1)**.

---

## Approach 2: Optimized with Frequency Map (O(n) Time | O(1) Space)

Since there are only **26** lowercase letters, you can use a fixed-size array or a hash map to count frequencies **in one pass**, then scan once more to find the first character whose count is **1**.

### Steps

1. **Count Frequencies**

   * Create an `int[26] freq = new int[26]`.
   * For each `char c` in the string, do `freq[c - 'a']++`.

2. **Find First Unique**

   * Loop `i` from `0` to `s.length()-1`.
   * If `freq[s.charAt(i) - 'a'] == 1`, **return** `i`.

3. If none found, **return** `-1`.

```java
public static int firstNonRepeatingCharacter(String s) {
  int[] freq = new int[26];
  // 1) Build frequency array
  for (char c : s.toCharArray()) {
    freq[c - 'a']++;
  }

  // 2) Find the first character with count == 1
  for (int i = 0; i < s.length(); i++) {
    if (freq[s.charAt(i) - 'a'] == 1) {
      return i;
    }
  }
  return -1;
}
```

* **Time Complexity:**

  * First pass: **O(n)**
  * Second pass: **O(n)**
    → **Overall: O(n)**

* **Space Complexity:**

  * `freq[26]` is constant size → **O(1)**

---

## Full Java Class

```java
package easy.strings;

public class A06FirstNonRepeatingCharacter {

  /**
   * Returns the index of the first non-repeating character in the string,
   * or -1 if every character repeats.
   *
   * Time:  O(n)
   * Space: O(1)   (26-element frequency array)
   */
  public static int firstNonRepeatingCharacter(String s) {
    // Frequency array for 'a' to 'z'
    int[] freq = new int[26];
    for (char c : s.toCharArray()) {
      freq[c - 'a']++;
    }

    // Find first with frequency 1
    for (int i = 0; i < s.length(); i++) {
      if (freq[s.charAt(i) - 'a'] == 1) {
        return i;
      }
    }
    return -1;
  }

  // Example usage
  public static void main(String[] args) {
    String input = "abcdcaf";
    int index = firstNonRepeatingCharacter(input);
    System.out.println(index);  // Outputs 1
  }
}
```

This two-pass approach is simple, fast, and uses only constant extra memory—ideal for finding the first non-repeating character in a lowercase string.
