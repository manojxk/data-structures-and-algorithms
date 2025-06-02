**Problem Restatement**
Given a string `str`, build a **suffix trie**—i.e., a trie data structure that contains every possible suffix of `str`. Once constructed, you should be able to query whether any given substring appears among those suffixes (by checking if that substring is a prefix of one of the stored suffixes).

For example, if `str = "babc"`, the trie must represent these four suffixes:

* `"babc"`
* `"abc"`
* `"bc"`
* `"c"`

You insert each suffix character by character, and after you finish inserting a suffix, you place a special end‐of‐suffix marker (denoted by `'*'`) to signal that a suffix ends there.

---

## Key Ideas and Steps

1. **TrieNode Structure**
   Each `TrieNode` holds a map from a character → `TrieNode`. In Java, we represent it as:

   ```java
   static class TrieNode {
     Map<Character, TrieNode> children = new HashMap<>();
   }
   ```

   That map can store any lowercase letter, uppercase letter, punctuation, etc., plus one special symbol `'*'` to mark “end of suffix.” Whenever a node has a child keyed by `'*'`, it means that path corresponds exactly to a full suffix.

2. **Root Node**
   The `SuffixTrie` has a single `root` of type `TrieNode`. Initially, `root.children` is empty.

3. **Building the Trie (`constructTrie`)**

   ```java
   private void constructTrie(String str) {
     for (int i = 0; i < str.length(); i++) {
       insertSuffixStartingAt(str, i);
     }
   }
   ```

   We loop over every starting index `i` in `str` and call `insertSuffixStartingAt(str, i)`. In other words, we take the substring `str[i .. end]` (the suffix that begins at index `i`) and insert it into our trie, character by character.

4. **Inserting One Suffix (`insertSuffixStartingAt`)**

   ```java
   private void insertSuffixStartingAt(String str, int startIdx) {
     TrieNode currentNode = root;
     for (int i = startIdx; i < str.length(); i++) {
       char currentChar = str.charAt(i);
       // If there’s no child for currentChar, create one
       if (!currentNode.children.containsKey(currentChar)) {
         currentNode.children.put(currentChar, new TrieNode());
       }
       // Move down into that child
       currentNode = currentNode.children.get(currentChar);
     }
     // After we’ve consumed all characters of this suffix, add the end symbol:
     currentNode.children.put(END_SYMBOL, null);
   }
   ```

   * We start from `root`.
   * For each character of the suffix (from `str[startIdx]` up to `str[str.length()-1]`), we check if `currentNode.children` already has a child node under that character.

     * If not, we create a new `TrieNode` and put it into `currentNode.children.put(character, new TrieNode())`.
     * Then we descend into `currentNode = currentNode.children.get(character)`.
   * Once all suffix characters are inserted, we insert a special child keyed by `END_SYMBOL` (`'*'`) to mark “this node represents a complete suffix.”

   Because we iterate over all `i` from `0..str.length()-1`, we guarantee every suffix (of length 1 up to `str.length()`) ends up in the trie.

5. **Searching (`contains`)**

   ```java
   public boolean contains(String s) {
     TrieNode currentNode = root;
     for (int i = 0; i < s.length(); i++) {
       char c = s.charAt(i);
       if (!currentNode.children.containsKey(c)) {
         return false; // No path for this character → not a valid suffix prefix
       }
       currentNode = currentNode.children.get(c);
     }
     // After consuming every character of s, we must check if that node has the END_SYMBOL child
     return currentNode.children.containsKey(END_SYMBOL);
   }
   ```

   * We begin at `root` and “walk down” one character at a time.
   * If at any point the next character `c` is not in `currentNode.children.keySet()`, then `s` cannot be a suffix. Return `false`.
   * If we successfully match all `s.length()` characters down to some node, we still must ensure that this node has `'*'` in its `children` (meaning we exactly matched one of the inserted suffixes). If so, return `true`; otherwise return `false`.

---

## Time and Space Complexity

* **Time Complexity (construction):**
  We insert **`N`** suffixes. The $i$-th suffix has length $(N - i)$. So in total, we perform

  $$
    (N) + (N-1) + (N-2) + \dots + 1 \;=\; \frac{N \times (N+1)}{2} \;=\; O(N^2)
  $$

  character insertions. Each character‐insertion into a `HashMap` and node lookup/intention is $O(1)$ on average, so building the entire suffix trie costs **$O(N^2)$** time.

* **Space Complexity:**
  In the worst case (all characters in the string are distinct), the trie stores every suffix separately. Each suffix of length $k$ occupies its own chain of $k$ nodes (plus the `'*'` marker). Summing up, the total number of nodes is on the order of

  $$
    1 + 2 + 3 + \dots + N \;=\; O(N^2).
  $$

  Hence we use **$O(N^2)$** space to store all suffixes. (In practice, if many suffixes share common prefixes, you save space by re‐using those shared subtrees; the upper bound is still $O(N^2)$.)

---

## Example: Constructing the Trie for `"babc"`

1. **Insert suffix** starting at index 0: `"babc"`.

   * Start at root.
   * Insert `'b'` → new node under `root.children['b']`.
   * Insert `'a'` → new node under that.
   * Insert `'b'` → new node under that.
   * Insert `'c'` → new node under that.
   * Finally, under that `'c'`‐node, insert `'*'` to mark end of suffix.
     Therefore the path is:

   ```
   root 
     └─ 'b' 
        └─ 'a'
           └─ 'b'
              └─ 'c'
                 └─ '*'
   ```
2. **Insert suffix** starting at index 1: `"abc"`.

   * Starting at `root`, we look for `children['a']`. It doesn’t exist, so create a new node under `'a'`.
   * Under that `'a'`, insert `'b'` → new node.
   * Under that `'b'`, insert `'c'` → new node.
   * Under that `'c'`, insert `'*'`.
     Now we have a second branch:

   ```
   root
     ├─ 'b' ─ a ─ b ─ c ─ '*'
     └─ 'a' ─ b ─ c ─ '*'
   ```
3. **Insert suffix** starting at index 2: `"bc"`.

   * At `root`, look for `children['b']`. It already exists from step 1, so reuse that node.
   * Under that `'b'`, look for `children['c']`, but actually under `'b'` we have only `'a'` (from “babc”). So we add `children['c']` as a new node.
   * Then under that `'c'`, insert `'*'`.
     Now the `'b'`‐subtree has two branches:

   ```
   root
     ├─ 'b'
     │   ├─ 'a' ─ b ─ c ─ '*'
     │   └─ 'c' ─ '*'
     └─ 'a' ─ b ─ c ─ '*'
   ```
4. **Insert suffix** starting at index 3: `"c"`.

   * From `root`, no `children['c']` yet, so create a node under `'c'`.
   * Then under this new `'c'`‐node, insert `'*'`.
     Final trie shape:

   ```
   root
     ├─ 'b'
     │   ├─ 'a'
     │   │   └─ 'b'
     │   │      └─ 'c'
     │   │         └─ '*'
     │   └─ 'c'
     │       └─ '*'
     ├─ 'a'
     │   └─ 'b'
     │      └─ 'c'
     │         └─ '*'
     └─ 'c'
         └─ '*'
   ```

---

## Using the Trie

* `contains("abc")`

  1. From `root`, see `children['a']` → go to that node.
  2. Next letter `'b'`: see `children['b']` → go to that node.
  3. Next letter `'c'`: see `children['c']` → go to that node.
  4. Now check if that node has `children['*']`. It does—so return `true`.

* `contains("babc")` → follows the `'b'→'a'→'b'→'c'` path, sees `'*'` at the end, returns `true`.

* `contains("ba")` →

  1. `root.children['b']` exists → go there.
  2. Next letter `'a'`: `children['a']` exists → go there.
  3. Now we are at the node reached by “`b→a`.” But does it have `'*'`? No—it only has a child `'b'`. Therefore `contains("ba")` is `false`, since “ba” itself was never inserted as a whole suffix.

---

## Final Java Code (Concise)

```java
package medium.tries;

import java.util.HashMap;
import java.util.Map;

public class A01SuffixTrieConstruction {

  static class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
  }

  static class SuffixTrie {
    private final TrieNode root = new TrieNode();
    private static final char END_SYMBOL = '*';

    public SuffixTrie(String str) {
      constructTrie(str);
    }

    private void constructTrie(String str) {
      for (int i = 0; i < str.length(); i++) {
        insertSuffixStartingAt(str, i);
      }
    }

    private void insertSuffixStartingAt(String str, int startIdx) {
      TrieNode currentNode = root;
      for (int i = startIdx; i < str.length(); i++) {
        char c = str.charAt(i);
        currentNode.children.putIfAbsent(c, new TrieNode());
        currentNode = currentNode.children.get(c);
      }
      currentNode.children.put(END_SYMBOL, null);
    }

    public boolean contains(String s) {
      TrieNode currentNode = root;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!currentNode.children.containsKey(c)) {
          return false;
        }
        currentNode = currentNode.children.get(c);
      }
      // Check if we ended exactly at a suffix‐terminating node
      return currentNode.children.containsKey(END_SYMBOL);
    }
  }

  public static void main(String[] args) {
    String input = "babc";
    SuffixTrie trie = new SuffixTrie(input);

    System.out.println("contains(\"abc\")? " + trie.contains("abc"));   // true
    System.out.println("contains(\"babc\")? " + trie.contains("babc")); // true
    System.out.println("contains(\"ba\")? "  + trie.contains("ba"));    // false
    System.out.println("contains(\"xyz\")? " + trie.contains("xyz"));   // false
  }
}
```

---

### Recap

* We insert **every suffix** of the original string into one shared trie.
* Each node’s `children` map can hold a normal character (continuing a suffix) or the special `'*'` to signal “end of a suffix.”
* To check if a given substring `s` is one of those stored suffixes, we simply walk down the trie along the characters of `s` and then verify that the final node has `'*'` as a child.

This construction and lookup both run in $O(n^2)$ time and $O(n^2)$ space in the worst case (for an input string of length $n$).
