**Problem Explanation**
You have an array of words (strings). You want to determine the smallest collection of individual characters such that, if you had exactly those characters available, you could spell every one of the given words (all of them). In other words, for each letter (or symbol, since words may include punctuation or digits) that appears in any word, you must have as many copies of that character as the maximum number of times it appears in any single word.

> **Example:**
>
> ```
> words = ["this", "that", "did", "deed", "them!", "a"]
> ```
>
> * Look at each character:
>
>   * ‘t’:
>
>     * “this” has 1 ‘t’,
>     * “that” has 2 ‘t’s,
>     * others have fewer.
>       → So you need at least **2** copies of ‘t’.
>   * ‘h’:
>
>     * “this” has 1 ‘h’,
>     * “that” has 1 ‘h’,
>     * “them!” has 1 ‘h’.
>       → You need **1** copy of ‘h’.
>   * ‘i’:
>
>     * “this” has 1 ‘i’,
>     * “did” has 1 ‘i’.
>       → You need **1** copy of ‘i’.
>   * ‘s’:
>
>     * “this” has 1 ‘s’.
>       → You need **1** copy of ‘s’.
>   * ‘a’:
>
>     * “that” has 1 ‘a’,
>     * “a” has 1 ’a’.
>       → You need **1** copy of ‘a’.
>   * ‘d’:
>
>     * “did” has 2 ‘d’s,
>     * “deed” has 2 ‘d’s.
>       → You need **2** copies of ‘d’.
>   * ‘e’:
>
>     * “deed” has 2 ‘e’s.
>       → You need **2** copies of ‘e’.
>   * ‘m’:
>
>     * “them!” has 1 ‘m’.
>       → You need **1** copy of ‘m’.
>   * ‘!’:
>
>     * “them!” has 1 ‘!’.
>       → You need **1** copy of ‘!’.

Putting those together yields an array (or list) of characters that includes:

```
['t', 't',   // two t’s
 'h',        // one h
 'i',        // one i
 's',        // one s
 'a',        // one a
 'd', 'd',   // two d’s
 'e', 'e',   // two e’s
 'm',        // one m
 '!'         // one exclamation
]
```

In any order, that is a minimal collection of characters that lets you spell all six of the words at once if you had exactly those tiles.

---

## Approach Overview (Brute Force / Frequency Map)

1. **Count Frequencies in Each Word**

   * For each word in the array, build a small frequency map that counts how many times each character appears in that single word.

2. **Merge into a Global “Maximum Frequency” Map**

   * As you process each word’s frequency map, update a global map `maxCharFreq` so that for every character `c`, `maxCharFreq.get(c)` holds the **maximum** number of times `c` was seen in any one word so far.

3. **Build the Result List**

   * Once you’ve seen all words, iterate over each `(character, maxCount)` pair in `maxCharFreq`.
   * Add that character to the result list exactly `maxCount` times.

Because the underlying character‐set (letters, digits, punctuation) is finite, the global map never grows proportionally to the input words—its size is bounded by “at most one entry for each possible character.” Hence we say the extra space is **O(1)**. The process of scanning through all characters of all words takes **O(n·m)** total time if you have `n` words of average length `m`.

---

## Step‐by‐Step Solution

1. **Initialize an empty map**:

   ```java
   Map<Character, Integer> maxCharFreq = new HashMap<>();
   ```

   This will eventually hold, for each character `c`, the largest count it ever needs.

2. **For each word** in `words[]`:
   a. Build a temporary frequency map `charFreq` for just this word:

   ```java
   Map<Character, Integer> charFreq = new HashMap<>();
   for (char c : word.toCharArray()) {
     charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
   }
   ```

   b. **Merge** these counts into `maxCharFreq`:

   ```java
   for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
     char c = entry.getKey();
     int count = entry.getValue();
     // If max so far is smaller than this word’s count, update it
     maxCharFreq.put(c, Math.max(maxCharFreq.getOrDefault(c, 0), count));
   }
   ```

3. **Build the final result list**:

   ```java
   List<Character> result = new ArrayList<>();
   for (Map.Entry<Character, Integer> entry : maxCharFreq.entrySet()) {
     char c = entry.getKey();
     int freq = entry.getValue();
     // Add that character freq times
     for (int i = 0; i < freq; i++) {
       result.add(c);
     }
   }
   ```

   The list `result` now has exactly the characters needed (possibly in any order).

4. **Return** `result`.

---

## Complete Java Implementation

```java
package medium.strings;

import java.util.*;

public class A05SmallestCharArray {

  /**
   * Returns the smallest collection of characters needed to form every word in the input array.
   * Each character appears as many times as it appears in the word requiring the highest count.
   *
   * Time Complexity:  O(n * m)
   *   - n = number of words, m = average length of each word.
   *   - We build a frequency map per word (O(m)) and merge it (O(m)).
   *
   * Space Complexity: O(1) “extra” 
   *   - The global frequency map has at most one entry per possible character.
   *   - We only store the result list, which is inevitable (it has size ≦ total distinct characters × max needed count).  
   */
  public static List<Character> smallestCharArray(String[] words) {
    // 1) Global map: for each character, store the maximum count needed among all words
    Map<Character, Integer> maxCharFreq = new HashMap<>();

    // 2) For each word, build a local frequency map and merge
    for (String word : words) {
      Map<Character, Integer> charFreq = new HashMap<>();
      // Count this word’s characters
      for (char c : word.toCharArray()) {
        charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
      }
      // Merge into global max counts
      for (Map.Entry<Character, Integer> entry : charFreq.entrySet()) {
        char c = entry.getKey();
        int count = entry.getValue();
        maxCharFreq.put(
            c, 
            Math.max(maxCharFreq.getOrDefault(c, 0), count)
        );
      }
    }

    // 3) Build the final result list by repeating each character maxCount times
    List<Character> result = new ArrayList<>();
    for (Map.Entry<Character, Integer> entry : maxCharFreq.entrySet()) {
      char c = entry.getKey();
      int freq = entry.getValue();
      for (int i = 0; i < freq; i++) {
        result.add(c);
      }
    }

    return result;
  }

  public static void main(String[] args) {
    // Example input
    String[] words = {"this", "that", "did", "deed", "them!", "a"};

    // Compute smallest character array
    List<Character> result = smallestCharArray(words);

    // Print the result (order may vary)
    System.out.println(result);
    // Example output: [t, t, h, i, s, a, d, d, e, e, m, !]
  }
}
```

---

## Explanation for Beginners

1. **Why take the maximum frequency per character?**

   * If one word needs two ‘d’s (for example, “did”) but another word only needs one ‘d’, you have to be able to spell the word that needs two. Hence you keep track of **the largest count** seen for each character across all words.

2. **Why is this minimal?**

   * Suppose you ended up using fewer than that maximum number of ‘d’s in your character collection. Then you wouldn’t be able to spell the word that actually needs all of them.
   * Using exactly the maximum ensures you can spell every word at least once, and you never keep more copies of a character than necessary.

3. **Why is the space “O(1)”?**

   * Although we build a `Map<Character, Integer>`, the number of distinct characters (letters, digits, punctuation) is fixed—at most something like 128 or 256 in ASCII. It does not grow if you add more words. Hence we say it is constant extra space, not depending on the input size `n`.

4. **Walking through the example**

   * For each word we build a quick frequency: e.g.

     * “this” → { t:1, h:1, i:1, s:1 }
     * “that” → { t:2, h:1, a:1 }
     * “did” → { d:2, i:1 }
     * “deed” → { d:2, e:2 }
     * “them!” → { t:1, h:1, e:1, m:1, !:1 }
     * “a” → { a:1 }
   * Merging “this” and then “that” updates global `maxCharFreq`:

     * ‘t’ max(1,2) → 2,
     * ‘h’ max(1,1) → 1,
     * ‘i’ max(1,0) → 1,
     * ‘s’ max(1,0) → 1,
     * ‘a’ max(0,1) → 1.
   * Continue merging “did”:

     * ‘d’ max(0,2) → 2,
     * ‘i’ max(1,1) → 1.
   * And so on, until you end up with exactly the counts:

     ```
     t → 2
     h → 1
     i → 1
     s → 1
     a → 1
     d → 2
     e → 2
     m → 1
     ! → 1
     ```
   * Finally, you produce a list that has each character repeated the required number of times:

     ```
     [t, t, h, i, s, a, d, d, e, e, m, !]
     ```
   * That list is minimal yet sufficient to spell every word in the original array.

---

**Time Complexity:** $O(n \times m)$, where $n$ = number of words and $m$ = average length of each word.
**Space Complexity:** $O(1)$ extra (since the frequency map’s size is bounded by the finite character set), plus the output list itself.
