**Problem Explanation**

You’re given a **non‐empty** string of lowercase letters (a–z) and a **non‐negative** integer `key`. You need to **shift** (“rotate”) each letter in the string **forward** by `key` positions in the alphabet. When you pass `'z'`, you **wrap around** back to `'a'`. Return the new “encrypted” string.

* **Example:**

  * Input: `"xyz"`, `key = 2`
  * Shifts:

    * `'x'` → 2 steps forward → `'z'`
    * `'y'` → 2 steps forward → wrap to `'a'`
    * `'z'` → 2 steps forward → wrap to `'b'`
  * Output: `"zab"`

---

## Approach

1. **Normalize the Key**
   Because there are **26** letters, shifting by `key % 26` is equivalent to shifting by `key`.
2. **Iterate** over each character in the input string.
3. **Compute** its **ASCII** code, add the normalized key, and then:

   * **If** it does **not** exceed `'z'` (ASCII 122), simply cast back to `char`.
   * **Otherwise**, compute how far past `'z'` you’ve gone and **wrap** to `'a'`.
4. **Append** each new character to a `StringBuilder`.
5. **Return** the `StringBuilder`’s contents as the result string.

---

## Step-by-Step Solution

1. **Compute** `newKey = key % 26;`
2. **Initialize** an empty `StringBuilder`:

   ```java
   StringBuilder result = new StringBuilder();
   ```
3. **For each** `char c : str.toCharArray()`:

   * `int shifted = c + newKey;`
   * **If** `shifted <= 'z'`:

     ```java
     result.append((char) shifted);
     ```
   * **Else** (wrap around):

     ```java
     // How far beyond 'z'?
     int overflow = shifted - 'z' - 1;
     result.append((char) ('a' + overflow));
     ```
4. **Return** `result.toString()`.

---

## Java Implementation

```java
package easy.strings;

public class A02CaesarCipherEncryptor {

  /**
   * Shifts each character in 'str' forward by 'key' positions in the alphabet,
   * wrapping around from 'z' to 'a' as needed.
   *
   * @param str the input lowercase string
   * @param key number of positions to shift
   * @return the encrypted string
   *
   * Time Complexity:  O(n) — one pass through the string
   * Space Complexity: O(n) — for the StringBuilder result
   */
  public static String caesarCipherEncryptor(String str, int key) {
    StringBuilder result = new StringBuilder();
    int newKey = key % 26;  // Normalize key to [0..25]

    for (char c : str.toCharArray()) {
      // Shift the character code
      int shiftedCode = c + newKey;

      if (shiftedCode <= 'z') {
        // No wrap needed
        result.append((char) shiftedCode);
      } else {
        // Wrap around past 'z' back to 'a'
        int overflow = shiftedCode - 'z' - 1;
        result.append((char) ('a' + overflow));
      }
    }

    return result.toString();
  }

  // Demo
  public static void main(String[] args) {
    String input = "xyz";
    int key = 2;
    String encrypted = caesarCipherEncryptor(input, key);
    System.out.println(encrypted);  // Prints "zab"
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**
  You make a single pass over the input string of length *n*, doing only O(1) work per character → **O(n)**.

* **Space Complexity:**
  You build an output string of length *n* in a `StringBuilder` → **O(n)** extra space.

This simple algorithm efficiently handles very large keys by reducing them modulo 26 and correctly wraps characters around the alphabet.
