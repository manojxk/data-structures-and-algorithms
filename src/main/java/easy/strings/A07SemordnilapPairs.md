**Problem Restatement**
You’re given an array of **unique** lowercase strings, e.g.:

```java
["diaper", "abc", "test", "cba", "repaid"]
```

A **semordnilap** pair is two **different** words where one is the reverse of the other. For example:

* `"diaper"` ↔ `"repaid"`
* `"abc"`   ↔ `"cba"`

Your task is to **find all** such pairs in the input. The order of pairs and the order within each pair doesn’t matter, but each pair should appear exactly once.

---

## Approach Overview (Greedy with a Hash Set)

1. **Fast membership testing**
   Convert your list of words into a `HashSet<String>` so you can check “does the reverse of this word exist?” in **O(1)** time on average.

2. **Iterate once through the original list**
   For each `word`:

   * Compute `reversed = reverse(word)`.
   * If `reversed` is in the set **and** `reversed` is not the **same** string (to avoid palindromes), you’ve found a valid semordnilap pair.
   * **Record** the pair `[word, reversed]` in your results.
   * **Remove** both `word` and `reversed` from the set so you don’t report them again in reverse order later.

3. **Return** the list of collected pairs.

This single pass (plus O(1) reverse and set-lookup per word) ensures you find each pair exactly once.

---

## Step-by-Step Solution

1. **Build the set**

   ```java
   Set<String> set = new HashSet<>(Arrays.asList(words));
   ```

2. **Prepare result container**

   ```java
   List<List<String>> pairs = new ArrayList<>();
   ```

3. **Loop over each word**

   ```java
   for (String word : words) {
     // If it's already removed in a previous pairing, skip it
     if (!set.contains(word)) continue;

     String reversed = new StringBuilder(word)
                          .reverse()
                          .toString();

     // Check for a semordnilap partner
     if (set.contains(reversed) && !word.equals(reversed)) {
       // Record the pair
       pairs.add(Arrays.asList(word, reversed));
       // Remove both to avoid duplicates later
       set.remove(word);
       set.remove(reversed);
     }
   }
   ```

4. **Return** `pairs`.

---

## Java Code

```java
package easy.strings;

import java.util.*;

public class A07SemordnilapPairs {

  /**
   * Finds all semordnilap pairs in the input array.
   *
   * @param words array of unique lowercase words
   * @return list of 2-element lists, each containing a semordnilap pair
   */
  public static List<List<String>> findSemordnilapPairs(String[] words) {
    // 1) Put all words into a HashSet for O(1) lookups
    Set<String> set = new HashSet<>(Arrays.asList(words));
    List<List<String>> pairs = new ArrayList<>();

    // 2) Iterate over original list to preserve order
    for (String word : words) {
      // Skip if this word was already paired and removed
      if (!set.contains(word)) continue;

      // Compute the reverse
      String reversed = new StringBuilder(word).reverse().toString();

      // Check if its reverse exists (and is different)
      if (set.contains(reversed) && !word.equals(reversed)) {
        // Record the semordnilap pair
        pairs.add(Arrays.asList(word, reversed));
        // Remove both from the set so we don't double-count
        set.remove(word);
        set.remove(reversed);
      }
    }

    return pairs;
  }

  public static void main(String[] args) {
    String[] words = {"diaper", "abc", "test", "cba", "repaid"};
    List<List<String>> result = findSemordnilapPairs(words);
    System.out.println(result);
    // Possible output (order may vary):
    // [["diaper", "repaid"], ["abc", "cba"]]
  }
}
```

---

## Complexity Analysis

* **Time Complexity:**

  * **Building the set:** O(n)
  * **Looping through n words:** each word reversal is O(m) (where m = average word length) and one set lookup/removal is O(1) on average.
  * **Overall:** **O(n · m)**

* **Space Complexity:**

  * **O(n · m)** for the hash set storing all words (and their character data).
  * The output list of pairs is at most O(n).

Because the English alphabet is fixed and strings are moderate, this runs efficiently for typical inputs and clearly finds each semordnilap pair exactly once.
