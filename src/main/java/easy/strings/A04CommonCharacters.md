**Problem Restatement**

Given a non‐empty list of strings, each string itself non‐empty, find **all distinct characters** that appear in **every** string. Return them as a list (order doesn’t matter).

* **Example:**

  * Input: `["abc", "bcd", "cbaccd"]`
  * Characters in all three:

    * `"abc"` has `{a,b,c}`
    * `"bcd"` has `{b,c,d}`
    * `"cbaccd"` has `{a,b,c,d}`
  * Intersection = `{b, c}`
  * Output: `["b","c"]` (in any order)

---

## Approach: Set Intersection

1. **Initialize** a set **commonSet** with the characters of the **first** string.
2. **Iterate** through each **subsequent** string:

   * Build a (**new**) set **currentSet** of its characters.
   * **Intersect**: remove from **commonSet** any character not in **currentSet**.
3. After processing all strings, **commonSet** contains exactly those characters present in **every** string.
4. **Return** a list built from **commonSet**.

This requires only one pass over each character in each string, and one set‐intersection operation per string.

---

## Java Implementation

```java
package easy.strings;

import java.util.*;

public class A04CommonCharacters {

  /**
   * Returns the list of distinct characters that appear in every string.
   *
   * @param strings list of non-empty strings
   * @return list of characters common to all strings (order arbitrary)
   *
   * Time Complexity: O(n * m)
   *   - n = number of strings
   *   - m = average length of each string
   *   We scan each string once, performing O(1) set operations per character.
   *
   * Space Complexity: O(k)
   *   - k = number of distinct characters in the first string
   *   We store up to k characters in the intersection set.
   */
  public static List<Character> commonChars(List<String> strings) {
    if (strings == null || strings.isEmpty()) {
      return Collections.emptyList();
    }

    // 1) Initialize commonSet with chars from the first string
    Set<Character> commonSet = new HashSet<>();
    for (char c : strings.get(0).toCharArray()) {
      commonSet.add(c);
    }

    // 2) Intersect with each subsequent string
    for (int i = 1; i < strings.size() && !commonSet.isEmpty(); i++) {
      String s = strings.get(i);
      Set<Character> currentSet = new HashSet<>();
      for (char c : s.toCharArray()) {
        // Only keep those already in commonSet
        if (commonSet.contains(c)) {
          currentSet.add(c);
        }
      }
      // Update intersection
      commonSet = currentSet;
    }

    // 3) Convert to list and return
    return new ArrayList<>(commonSet);
  }

  // Demonstration
  public static void main(String[] args) {
    List<String> strings = Arrays.asList("abc", "bcd", "cbaccd");
    List<Character> result = commonChars(strings);
    System.out.println(result);  // Example output: [b, c]
  }
}
```

---

### Explanation

1. **commonSet** starts with all characters from the **first** string.
2. For each later string, we build **currentSet** of its characters that also lie in **commonSet**, effectively taking the intersection.
3. We replace **commonSet** with that smaller set.
4. After processing every string, **commonSet** holds exactly those characters seen in **every** input string.
5. Finally, we return those as a list.

This runs in **O(n⋅m)** time (one pass per string) and uses **O(k)** extra space for the set of common characters.
