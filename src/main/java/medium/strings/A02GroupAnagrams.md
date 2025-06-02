**Problem Restatement**
You need to group words that are anagrams of each other. Given an array of strings, return a list of lists, where each sublist contains words that share the same letters (i.e., one is a rearrangement of the other). The order of groups and the order within each group does not matter.

> **Example**
>
> ```
> Input:  ["yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"]
> Output: [["yo","oy"], ["act","tac","cat"], ["flop","olfp"], ["foo"]]
> ```

---

## Approach: Sort Each Word as a Key (O(n·m·log m) Time, O(n·m) Space)

1. **Canonical Form**
   Two words are anagrams if and only if, when you sort their letters alphabetically, you get the same string. For instance:

   * `"act" → "act"`
   * `"tac" → "act"`
   * `"cat" → "act"`
     All share the sorted key `"act"`.

2. **Use a HashMap**

   * Map from `String sortedKey` → `List<String>` of original words that share that key.
   * For each word in the input array:

     1. Convert it to a character array, sort it, and convert back to a new string `sortedWord`.
     2. Look up `sortedWord` in the map. If absent, insert a new `ArrayList<>`.
     3. Add the original word to `map.get(sortedWord)`.

3. **Collect Result**

   * After processing all words, the map’s values (each a `List<String>`) are exactly the anagram groups. Return `new ArrayList<>(map.values())`.

This runs in $O(n \cdot m \log m)$ time, where $n$ = number of words and $m$ = maximum word length, because sorting each word costs $O(m \log m)$. Space is $O(n \cdot m)$ for storing keys and grouped lists.

---

## Java Implementation

```java
package medium.strings;

import java.util.*;

public class A02GroupAnagrams {

  /**
   * Groups anagrams from the input array of words.
   *
   * @param words an array of lowercase/uppercase letters and digit strings
   * @return a list of lists, where each inner list contains words that are anagrams
   */
  public static List<List<String>> groupAnagrams(String[] words) {
    // Map: sorted-character-string -> list of original words
    Map<String, List<String>> map = new HashMap<>();

    for (String word : words) {
      // 1) Sort the characters of "word" to get its canonical form
      char[] chars = word.toCharArray();
      Arrays.sort(chars);
      String sortedKey = new String(chars);

      // 2) Insert into map
      map.computeIfAbsent(sortedKey, k -> new ArrayList<>()).add(word);
      // Equivalent to:
      // if (!map.containsKey(sortedKey)) {
      //   map.put(sortedKey, new ArrayList<>());
      // }
      // map.get(sortedKey).add(word);
    }

    // 3) Gather all grouped lists
    return new ArrayList<>(map.values());
  }

  public static void main(String[] args) {
    String[] words = {
      "yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"
    };

    List<List<String>> result = groupAnagrams(words);

    // Print (groups may appear in any order)
    System.out.println(result);
    // Possible output: [[yo, oy], [flop, olfp], [act, tac, cat], [foo]]
  }
}
```

---

### Explanation of Key Steps

1. **Sorting Each Word**

   ```java
   char[] chars = word.toCharArray();
   Arrays.sort(chars);
   String sortedKey = new String(chars);
   ```

   * If `word = "flop"`, sorting → `['f','l','o','p']` (already sorted).
   * If `word = "olfp"`, sorting → `['f','l','o','p']` → `"flop"`.
     Both yield `"flop"` as the key.

2. **Building the Map**

   ```java
   map.computeIfAbsent(sortedKey, k -> new ArrayList<>()).add(word);
   ```

   * If `sortedKey` is not present, this creates a new `ArrayList<>()` and associates it.
   * Then it always `add(word)` to the list at that key.

3. **Retrieving Groups**

   ```java
   return new ArrayList<>(map.values());
   ```

   * `map.values()` is a `Collection<List<String>>`. Wrapping it in `new ArrayList<>(...)` builds the `List<List<String>>` the problem asks for.

---

## Complexity Analysis

* **Time Complexity:**

  * There are $n$ words. For each word of length $m$ (worst case), we:

    * Convert to `char[]`: O(m)
    * Sort that array: O(m log m)
    * Build new `String`: O(m)
    * Insert/lookup in HashMap: average O(m) to hash the key (since hashing a length-m string is O(m)), plus O(1) for the map insertion.
      → Overall: $O(n \times m \log m)$.

* **Space Complexity:**

  * We store a HashMap with at most $n$ keys (each key is a sorted string up to length $m$) and values totaling all $n$ words.
  * So the extra space is $O(n \times m)$, dominated by storing the keys and all the grouped word lists.

This method cleanly groups anagrams together by using sorted strings as canonical keys.
