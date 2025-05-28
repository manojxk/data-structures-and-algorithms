**Problem Restatement**

You have two strings:

* **`characters`**: the pool of available characters (may include letters, digits, punctuation, spaces, etc.),
* **`document`**: the string you want to build.

You can “use up” each character in the pool at most once. Determine if you can form the entire `document` by picking characters from `characters`. Return **true** only if **every** character in `document` appears at least as many times in `characters`; otherwise, return **false**.

---

## Approach: Frequency Counting

1. **Count Available Characters**
   Scan the `characters` string once and build a **frequency map** (`Map<Character,Integer>`) that records how many times each character appears.

2. **Consume for the Document**
   Scan the `document` string. For each character `c`:

   * If `c` isn’t in the frequency map or its count is already **0**, you **cannot** build the document → return **false**.
   * Otherwise, **decrement** the count for `c` in the map (you’ve “used up” one instance).

3. **Success**
   If you finish scanning the document without running out of any character, return **true**.

This is an **O(n + m)** time solution (where *n* = length of `characters`, *m* = length of `document`) with **O(k)** extra space (where *k* = distinct characters in the pool).

---

## Detailed Steps

```text
canGenerateDocument(characters, document):
  1. freqMap = empty HashMap<Character,Integer>
  2. for each ch in characters:
       freqMap[ch] = freqMap.getOrDefault(ch,0) + 1

  3. for each ch in document:
       if freqMap.get(ch) == null or freqMap.get(ch) == 0:
         return false
       freqMap.put(ch, freqMap.get(ch) - 1)

  4. return true
```

---

## Time & Space Complexity

* **Time Complexity**:

  * Building the map: O(n), where *n* = length of `characters`.
  * Verifying the document: O(m), where *m* = length of `document`.
    → **O(n + m)** overall.

* **Space Complexity**:

  * O(k) for the frequency map, where *k* = number of unique characters in `characters`.

Both are optimal for this problem.

---

## Java Code Walkthrough

```java
public static boolean canGenerateDocumentOptimized(
    String characters, String document
) {
  Map<Character, Integer> freq = new HashMap<>();

  // 1) Count each available character
  for (char c : characters.toCharArray()) {
    freq.put(c, freq.getOrDefault(c, 0) + 1);
  }

  // 2) Try to consume characters for the document
  for (char c : document.toCharArray()) {
    Integer count = freq.get(c);
    if (count == null || count == 0) {
      // Missing or used up
      return false;
    }
    // Use one instance
    freq.put(c, count - 1);
  }

  // 3) All characters available in sufficient quantity
  return true;
}
```

* **Line 1–5:** Build frequency map from `characters`.
* **Line 7–14:** For each `document` character, check availability and decrement.
* **Line 15:** If you never ran out, you can indeed generate the document.

This solution handles any character (letters, digits, symbols, spaces) and runs in linear time with respect to the input sizes.
