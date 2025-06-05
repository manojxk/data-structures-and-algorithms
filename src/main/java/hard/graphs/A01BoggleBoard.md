Below is a detailed explanation of both the **brute‐force** and **Trie‐based (optimized)** approaches for finding all the given words in a Boggle board, along with their time/space complexities and a step-by-step walk-through of the code you provided.

---

## Problem Restatement

You have:

1. A 2D grid of characters (the “Boggle board”), of arbitrary dimensions $m \times n$.
2. A list of words (strings).

You want to return **all** of the words from that list which can be formed on the board by chaining adjacent letters—adjacency includes horizontal, vertical, and all four diagonals. Each letter cell may be used **at most once** in forming a single word, but different words can reuse the same cells independently.

For example, given the sample board:

```
  t  h  i  s  i  s  a
  s  i  m  p  l  e  x
  b  x  x  x  x  e  b
  x  o  g  g  l  x  o
  x  x  x  D  T  r  a
  R  E  P  E  A  d  x
  x  x  x  x  x  x  x
  N  O  T  R  E  -  P
  x  x  D  E  T  A  E
```

and word‐list

```
["this","is","not","a","simple","boggle","board","test","REPEATED","NOTRE-PEATED"]
```

the valid output (in any order) is:

```
["this","is","a","simple","boggle","board","NOTRE-PEATED"]
```

because—e.g.—“this” can be traced out by connecting t→h→i→s in the top row, “simple” can be traced down diagonally, etc.

---

## 1) Brute-Force DFS per Word

### 1.1 High-Level Idea

For **each word** in the given list, do a “word‐search” (DFS) starting from every cell that matches the first letter. If any DFS path spells out the entire word (without reusing a cell twice), declare that word as found. Otherwise, it’s not on the board.

### 1.2 Code Walk-Through

```java
public static List<String> findWordsBruteForce(char[][] board, String[] words) {
  List<String> foundWords = new ArrayList<>();
  for (String word : words) {
    // Check if ‘word’ appears somewhere on the board
    if (isWordInBoard(board, word)) {
      foundWords.add(word);
    }
  }
  return foundWords;
}

// Return true iff ‘word’ can be traced somewhere on the boggle board
private static boolean isWordInBoard(char[][] board, String word) {
  for (int i = 0; i < board.length; i++) {
    for (int j = 0; j < board[0].length; j++) {
      // If the board cell matches the first letter, try a DFS from there
      if (board[i][j] == word.charAt(0) && dfs(board, word, i, j, 0)) {
        return true;
      }
    }
  }
  return false;
}

// Standard DFS that tries to match word[index...] by exploring 8 directions
private static boolean dfs(char[][] board, String word, int i, int j, int index) {
  // Base case: if index == word.length(), we’ve matched all letters
  if (index == word.length()) {
    return true;
  }
  // If out of bounds or current letter doesn’t match, fail
  if (i < 0 || i >= board.length
      || j < 0 || j >= board[0].length
      || board[i][j] != word.charAt(index)) {
    return false;
  }

  // Mark this cell as “visited” by temporarily overwriting it
  char temp = board[i][j];
  board[i][j] = '#';

  // Explore all eight neighbors (up, down, left, right, and 4 diagonals)
  boolean found =
      dfs(board, word, i - 1, j,     index + 1)  // up
   || dfs(board, word, i + 1, j,     index + 1)  // down
   || dfs(board, word, i,     j - 1, index + 1)  // left
   || dfs(board, word, i,     j + 1, index + 1)  // right
   || dfs(board, word, i - 1, j - 1, index + 1)  // up-left
   || dfs(board, word, i - 1, j + 1, index + 1)  // up-right
   || dfs(board, word, i + 1, j - 1, index + 1)  // down-left
   || dfs(board, word, i + 1, j + 1, index + 1); // down-right

  // Unmark this cell so future searches can use it
  board[i][j] = temp;
  return found;
}
```

#### What’s Happening, Step by Step

1. **Loop over each `word`** in `words`.
2. For each word, call `isWordInBoard(board, word)`:

   1. Scan every cell `(i, j)` in the `board`.
   2. If `board[i][j] == word.charAt(0)`, attempt a DFS from that cell with `dfs(board, word, i, j, 0)`.
   3. If DFS ever returns `true`, you know this word can be spelled—stop immediately and add it to the result list.
3. The `dfs(board, word, i, j, index)` function:

   1. If `index == word.length()`, we matched all letters; return `true`.
   2. If `(i,j)` is out of bounds or `board[i][j] != word.charAt(index)`, return `false`.
   3. Otherwise, temporarily mark `board[i][j] = '#'` (so we won’t revisit it in this path), then recursively explore all 8 directions to match the next character `(index + 1)`.
   4. After those recursive calls, restore `board[i][j] = temp` and return whether any direction found the entire suffix.

### 1.3 Complexity Analysis

* Let:

  * $m =$ number of rows
  * $n =$ number of columns
  * $b = m \times n$ = total cells
  * $w =$ number of words
  * $L_{\max} =$ maximum length of any word in `words`

* **Time Complexity**
  For each word, we scan all $b$ starting positions, and from each starting position we potentially explore up to 8 directions at each of up to $L$ recursive levels. In the worst case, each DFS can branch into 8 children, so an upper bound is $O(b \cdot 8^{L})$ for a single word of length $L$. Summing over all words (of varying lengths), it is

  $$
    O\Bigl(\sum_{\,\text{each word of length }L\,} \; b \cdot 8^{L}\Bigr)\;\le\; O\bigl(w \times b \times 8^{L_{\max}}\bigr).
  $$

  Because $8^L$ grows exponentially, this approach is only feasible when either $L$ or $b$ is quite small.

* **Space Complexity**

  * The recursion stack can go as deep as $L$.
  * No extra data structures of size more than $O(1)$ (aside from the result list).
    So $O(L)$ auxiliary space plus the space for storing the final list.

---

## 2) Optimized Approach: Build a Trie + Do One DFS over the Board instead of One per Word

### 2.1 High-Level Idea

Instead of searching for each word separately, we insert **all** words into a single Trie (prefix tree). Then we start a DFS from each cell, but **as we walk**, we also traverse the Trie. Whenever the current path in the board corresponds to a node in the Trie that marks the end of a word, we record that word. This way:

* In one pass from each board cell, we can find **all** words that share that prefix.
* We avoid re-searching the same prefix for every word.

### 2.2 Trie Data Structure

```java
// Node in the Trie:
static class TrieNode {
  Map<Character, TrieNode> children = new HashMap<>();
  String word = null;  // If not null, this node corresponds to the end of some inserted word
}

static class Trie {
  TrieNode root = new TrieNode();

  // Insert a word character by character
  public void insert(String word) {
    TrieNode currentNode = root;
    for (char letter : word.toCharArray()) {
      currentNode.children.putIfAbsent(letter, new TrieNode());
      currentNode = currentNode.children.get(letter);
    }
    // Mark the end of this word
    currentNode.word = word;
  }
}
```

* Each `TrieNode` has:

  * A `Map<Character, TrieNode> children` mapping each possible next letter to the next node.
  * A `String word` field that is set **only** if this node completes a full word from the input list.

    * We store the entire word there so that when we reach that node in DFS, we can add `node.word` to our output.
  * If `word` is non-null at any node, it means a valid dictionary word ends exactly at this prefix.

### 2.3 Code Walk-Through

```java
public static List<String> findWordsOptimized(char[][] board, String[] words) {
  Trie trie = new Trie();
  // 1) Build the trie of all input words:
  for (String word : words) {
    trie.insert(word);
  }

  // 2) We’ll store each found word in a set to avoid duplicates:
  Set<String> foundWords = new HashSet<>();

  // 3) From every cell, do a DFS that “follows” both board letters AND trie nodes in lockstep:
  for (int i = 0; i < board.length; i++) {
    for (int j = 0; j < board[0].length; j++) {
      dfsOptimized(board, trie.root, i, j, foundWords);
    }
  }

  // Convert the set to a list and return (order does not matter)
  return new ArrayList<>(foundWords);
}

private static void dfsOptimized(
    char[][] board,
    TrieNode node,
    int i,
    int j,
    Set<String> foundWords
) {
  // 1) Boundary checks + “already visited” check:
  if (i < 0 || i >= board.length
      || j < 0 || j >= board[0].length
      || board[i][j] == '#') {
    return;
  }

  char letter = board[i][j];
  // 2) If this character isn’t in `node.children`, no further matches can happen on this path:
  if (!node.children.containsKey(letter)) {
    return;
  }

  // 3) Otherwise, “step into” that child in the trie:
  TrieNode nextNode = node.children.get(letter);

  // 4) If nextNode.word != null, we have completed one word. Record it:
  if (nextNode.word != null) {
    foundWords.add(nextNode.word);
    // To prevent adding the same word multiple times via different paths, null out the word:
    nextNode.word = null;
  }

  // 5) Mark this board cell as visited, then explore all 8 neighbors
  char temp = board[i][j];
  board[i][j] = '#';
  dfsOptimized(board, nextNode, i - 1, j,     foundWords); // up
  dfsOptimized(board, nextNode, i + 1, j,     foundWords); // down
  dfsOptimized(board, nextNode, i,     j - 1, foundWords); // left
  dfsOptimized(board, nextNode, i,     j + 1, foundWords); // right
  dfsOptimized(board, nextNode, i - 1, j - 1, foundWords); // up-left
  dfsOptimized(board, nextNode, i - 1, j + 1, foundWords); // up-right
  dfsOptimized(board, nextNode, i + 1, j - 1, foundWords); // down-left
  dfsOptimized(board, nextNode, i + 1, j + 1, foundWords); // down-right
  board[i][j] = temp;  // revert visit mark
}
```

#### What’s Happening, Step by Step

1. **Build a Trie** of all `words` (that takes $O(\text{total letters in all words})$). Once built, each node may store a `word` pointer exactly at the Trie node that corresponds to the end of some inserted word.

2. **Initialize** an empty `Set<String> foundWords`, so if the same word can be found multiple ways (e.g.\ in two different board regions), we only record it once.

3. **Loop over every cell** `(i, j)` in `board`. From each cell, call `dfsOptimized(board, trie.root, i, j, foundWords)`.

4. In `dfsOptimized(board, node, i, j, foundWords)`:

   1. If `(i,j)` is out of range or already marked visited (`== '#'`), return immediately.
   2. Let `letter = board[i][j]`. If `node.children` does not contain that `letter`, there is no word in our Trie whose next letter is that character—so this path cannot match any dictionary word; return at once.
   3. Otherwise, we “move into” the Trie by doing `nextNode = node.children.get(letter)`.
   4. If `nextNode.word != null`, it means that by following these board‐steps we have spelled out exactly one of our input dictionary words. Add `nextNode.word` to `foundWords`, and then set `nextNode.word = null` so that we don’t add the same word again if some other DFS path hits the same Trie node.
   5. Mark `board[i][j] = '#'` to avoid revisiting this cell in the current path.
   6. Recursively explore all 8 neighboring cells—passing along `nextNode` as “the current Trie node.”
   7. After exploring all 8 directions, restore `board[i][j] = temp` so that subsequent DFS calls starting from other board positions can reuse it.

5. When every DFS from every `(i,j)` is done, `foundWords` contains exactly those dictionary words that were found somewhere on the board. Convert that set to a list and return.

### 2.4 Complexity Analysis

* Let $m\!\times\!n$ = number of cells in the board (call that $B$), and
  let $N = \sum(\text{length of each word})$ = total letters inserted into the Trie, and
  let $L_{\max}$ = length of the longest word.

* **Building the Trie**

  * Inserting all words costs $O(N)$ time and $O(N)$ space.

* **DFS Traversal**

  * We start a DFS from each of the $B$ cells.
  * In the worst case, each DFS path can go at most $L_{\max}$ steps deep (because once you exceed the longest word, you cannot match anything further).
  * At each step of the DFS, you look up the current letter in a hash‐map `node.children.containsKey(letter)`, which is $O(1)$ on average.
  * From each DFS node, you branch into up to 8 directions, but as soon as you follow a path that does not exist in the Trie, you immediately return.
  * In the absolute worst case—imagine every prefix up to length $L_{\max}$ exists in the Trie, and every board cell can be used—you might explore $8^{L_{\max}}$ paths from a single starting cell. But typically, because the Trie prunes all invalid continuations in $O(1)$ time whenever no child letter matches, the number of actually explored board cells is much smaller.

  As an upper bound, one often cites $O(B \times 8^{L_{\max}})$, but that is extremely pessimistic if the dictionary is large and many prefixes are missing. A more realistic complexity—since each visited board cell in DFS corresponds to one move down in the Trie—is:

  $$
    O\Bigl(B \times L_{\max}\Bigr)\quad\text{(in the average or well-pruned case)},
  $$

  plus the cost of marking and unmarking cells ($O(1)$ each).
  Still, people often say “worst‐case $O(B \times 8^{L_{\max}})$” to be strictly correct, because in principle if the Trie were extremely dense with every possible prefix of length up to $L_{\max}$, you could explore all 8 branches at each depth.

* **Total Time**

  $$
    O\bigl(N + B \times 8^{L_{\max}}\bigr)\quad\text{(worst case)},
    \quad\text{or more practically }O\bigl(N + B \times L_{\max}\bigr).
  $$

* **Space Complexity**

  * The Trie takes $O(N)$ space.
  * The recursion stack per DFS can go up to $L_{\max}$.
  * We also keep a `HashSet<String>` of found words, which in the worst case holds up to $w$ words, summing to at most $N$ characters.
  * Board marking is done in place, so no extra 2D array is needed.

  Overall:

  $$
    O\bigl(N + L_{\max} + w\bigr) \;\approx\; O(N)\quad\text{(since }w\le N\text{ and }L_{\max}\le N).
  $$

---

## 3) Putting It All Together

1. **Brute-Force Per-Word DFS**

   * **When to use:**

     * If the board is small and the word list is tiny.
     * Simple to implement but becomes exponential if words length or board size grow.
   * **Pros:** Very straightforward.
   * **Cons:** Performs a separate DFS for each word; extremely slow when there are many words or they are long.

2. **Trie-based Single DFS**

   * **When to use:**

     * When you have many dictionary words, especially with common prefixes.
     * The Trie prunes out invalid prefixes immediately, so you do not waste time exploring paths that cannot possibly form any word.
   * **Pros:** Shares common prefixes among all words; you do just one big DFS from each board cell instead of one per word.
   * **Cons:** You must build and store a Trie (requires $O(N)$ space), and in the very worst case the Trie could be so dense that you still do many DFS calls. But in practice, when your dictionary is realistic English words (or reasonably varied strings), the pruning is dramatic.

---

## 4) Sample Output on the Provided Example

Given the sample board and word list:

```java
char[][] board = {
  {'t','h','i','s','i','s','a'},
  {'s','i','m','p','l','e','x'},
  {'b','x','x','x','x','e','b'},
  {'x','o','g','g','l','x','o'},
  {'x','x','x','D','T','r','a'},
  {'R','E','P','E','A','d','x'},
  {'x','x','x','x','x','x','x'},
  {'N','O','T','R','E','-','P'},
  {'x','x','D','E','T','A','E'}
};
String[] words = {
  "this","is","not","a","simple","boggle","board","test","REPEATED","NOTRE-PEATED"
};
```

* **Brute Force** prints (order may vary):

  ```
  Brute Force Solution: [this, is, a, simple, boggle, board, NOTRE-PEATED]
  ```
* **Optimized Trie** also prints (order may vary):

  ```
  Optimized Trie Solution: [this, is, a, simple, boggle, board, NOTRE-PEATED]
  ```

Both approaches find exactly the same words that can be chained on the board.

---

### 4.1 Final Recommendations

* If you only have a handful of short words, the **brute-force** per-word DFS may be simpler to code and sufficiently fast.
* If you have hundreds or thousands of words (especially if they share prefixes), the **Trie + single DFS** approach will usually be much faster in practice because each DFS path prunes whenever the prefix no longer matches any dictionary entry.

---

That completes the end-to-end explanation of how to find all valid words on a Boggle board, using both a brute-force DFS for each word and a more efficient Trie-based search that does one big DFS leveraging prefix pruning.
