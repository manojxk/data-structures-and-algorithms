**Problem Explanation**

Run‐length encoding (RLE) is a simple form of lossless compression where **consecutive identical characters** (“runs”) in a string are represented by a **count** and the **character**. For example:

* `"AAA"` → `"3A"`
* `"BB"`  → `"2B"`

Here’s the **twist**: if a run is **10 or more** characters long, you must **split** it into chunks of **at most 9**. So:

* `"AAAAAAAAAAAA"` (12 A’s) → first 9 become `"9A"`, the remaining 3 become `"3A"`, giving `"9A3A"`.

Given a non‐empty string of arbitrary characters (including digits or punctuation), return its correctly‐split run‐length encoding.

---

## Approach

1. **Walk the string once**, keeping track of:

   * `currentRunLength` = length of the run you’re building.
   * `currentChar` = the character you’re counting.

2. **When to “flush”** (emit) a run:

   * **Run ends** because the next character differs.
   * **Run reaches 9**, since we can’t encode more than 9 in one chunk.

3. **Flushing a run** means:

   * Append the run length (as a digit) and `currentChar` to your output.
   * Reset `currentRunLength = 0` (we’ll start counting the next run).

4. **Continue** until you reach the end of the string, then flush the **final** run.

This is a **single‐pass** algorithm, moving left to right, appending output as you go.

---

## Step‐by‐Step

1. Initialize a `StringBuilder encoded = new StringBuilder()`.
2. Set `currentRunLength = 1` and start from index 1.
3. For each `i` from `1` to `string.length() - 1`:

   * Let `currentChar = string.charAt(i)`, `prevChar = string.charAt(i-1)`.
   * **If** `currentChar != prevChar` **or** `currentRunLength == 9`:

     * Append `currentRunLength` then `prevChar` to `encoded`.
     * Reset `currentRunLength = 0`.
   * In **either case** (run ended or still the same), **increment** `currentRunLength++`.
4. After the loop, flush the **last run**:

   * Append `currentRunLength` and `string.charAt(string.length() - 1)`.
5. Return `encoded.toString()`.

---

## Java Implementation

```java
package easy.strings;

public class A03RunLengthEncoding {

  public static String runLengthEncode(String string) {
    StringBuilder encoded = new StringBuilder();
    int currentRunLength = 1;  // we’ve seen one of string[0]

    for (int i = 1; i < string.length(); i++) {
      char currentChar = string.charAt(i);
      char prevChar    = string.charAt(i - 1);

      // If the run must end (different char or max length reached)
      if (currentChar != prevChar || currentRunLength == 9) {
        // Flush the run we've counted so far
        encoded.append(currentRunLength)
               .append(prevChar);
        currentRunLength = 0;  // reset for the new run
      }

      // Count this character into the current run
      currentRunLength++;
    }

    // Flush the final run
    encoded.append(currentRunLength)
           .append(string.charAt(string.length() - 1));

    return encoded.toString();
  }

  // Demo
  public static void main(String[] args) {
    String input  = "AAAAAAAAAAAAABBCCCCDD";
    String output = runLengthEncode(input);
    System.out.println(output);  // prints "9A4A2B4C2D"
  }
}
```

---

## Complexity Analysis

* **Time Complexity:** **O(n)**
  You make exactly one pass through the input string of length n, doing only constant‐time work per character.

* **Space Complexity:** **O(n)**
  In the worst case (e.g. no two identical characters in a row), the encoded string is about twice as long as the input, but still O(n). The `StringBuilder` grows linearly with the size of the output.

This solution handles all edge cases—including runs longer than nine—efficiently in a single pass.
