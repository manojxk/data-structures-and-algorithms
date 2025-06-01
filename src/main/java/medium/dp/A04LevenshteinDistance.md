**Problem Explanation**
The **Levenshtein distance** (or “edit distance”) between two strings is the minimum number of single‐character edit operations (insertions, deletions, or substitutions) required to transform the first string into the second.

* **Insertion**: add one character anywhere in the first string.
* **Deletion**: remove one character from the first string.
* **Substitution**: replace one character in the first string with a different character.

For example, to turn `"abc"` into `"yabd"`:

1. **Insert** `'y'` at the front → `"yabc"`.
2. **Substitute** the last character `'c'` with `'d'` → `"yabd"`.

That took **2** operations, so the edit distance is 2.

---

## Dynamic‐Programming (Bottom‐Up) Solution

A classic way to compute this is with a 2D DP table `dp[i][j]` where:

* `dp[i][j]` = the minimum edit distance between the prefixes `s1[0..i-1]` and `s2[0..j-1]`.
* Here `i` ranges from `0` to `m = s1.length()`, and `j` from `0` to `n = s2.length()`.

### Key Recurrence

1. **Base Cases**

   * `dp[0][j] = j`:  transforming an empty prefix of `s1` (`""`) into the prefix `s2[0..j-1]` requires exactly `j` insertions.
   * `dp[i][0] = i`:  transforming the prefix `s1[0..i-1]` into an empty string requires exactly `i` deletions.

2. **Transition** (for `i ≥ 1, j ≥ 1`):

   * If `s1.charAt(i−1) == s2.charAt(j−1)`, then no new operation is needed for the last character, and

     ```
     dp[i][j] = dp[i−1][j−1]
     ```
   * Otherwise, we consider the three possible operations:

     1. **Insert**: Insert `s2[j−1]` into `s1[0..i−1]`, so we pay 1 operation plus whatever it took to match `s1[0..i-1]` with `s2[0..j-2]`.
        → cost = `1 + dp[i][j−1]`
     2. **Delete**: Delete `s1[i−1]`, so we pay 1 operation plus whatever it took to match `s1[0..i-2]` to `s2[0..j-1]`.
        → cost = `1 + dp[i−1][j]`
     3. **Replace**: Replace `s1[i−1]` with `s2[j−1]`, so we pay 1 plus whatever it took to match `s1[0..i-2]` to `s2[0..j-2]`.
        → cost = `1 + dp[i−1][j−1]`

   Hence if the characters differ:

   ```
   dp[i][j] = 1 + min(
       dp[i][j−1],   // insert
       dp[i−1][j],   // delete
       dp[i−1][j−1]  // replace
   )
   ```

After filling in this table row by row (or column by column), the answer is `dp[m][n]`.

---

## Java Implementation

```java
package cools.dp.multidimensional;

import java.util.Arrays;

public class EditDistance {

  /**
   * Returns the Levenshtein edit distance between s1 and s2:
   * the minimum number of insertions, deletions, or substitutions
   * needed to transform s1 into s2.
   *
   * Time Complexity:  O(m * n)
   *   where m = s1.length(), n = s2.length()
   * Space Complexity: O(m * n)
   *   for the DP table of size (m+1) x (n+1)
   */
  public static int editDist(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();

    // dp[i][j] = edit distance between s1[0..i-1] and s2[0..j-1]
    int[][] dp = new int[m + 1][n + 1];

    // 1) Base cases:
    //    dp[i][0] = i  (delete all i characters)
    //    dp[0][j] = j  (insert all j characters)
    for (int i = 0; i <= m; i++) {
      dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
      dp[0][j] = j;
    }

    // 2) Fill table for i=1..m, j=1..n
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          // No new operation needed for the last character
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          // Consider insert, delete, replace
          int insertCost = dp[i][j - 1] + 1;    // insert s2[j-1]
          int deleteCost = dp[i - 1][j] + 1;    // delete s1[i-1]
          int replaceCost = dp[i - 1][j - 1] + 1; // replace s1[i-1] with s2[j-1]
          dp[i][j] = Math.min(insertCost,
                      Math.min(deleteCost, replaceCost));
        }
      }
    }

    // The answer is in dp[m][n]
    return dp[m][n];
  }

  public static void main(String[] args) {
    // Example from the prompt
    String str1 = "abc";
    String str2 = "yabd";
    int distance = editDist(str1, str2);
    System.out.println(distance);
    // Expected output: 2 
    // ("abc" → insert 'y' → "yabc" → replace 'c' with 'd' → "yabd")

    // Another sample test
    String s1 = "GEEXSFRGEEKKS";
    String s2 = "GEEKSFORGEEKS";
    System.out.println(editDist(s1, s2)); 
    // Expected output: 3 (for example, substitute, insert, delete as needed)
  }
}
```

### How the DP Table Works (Example: `"abc"` → `"yabd"`)

1. `m = 3` (`"abc"`), `n = 4` (`"yabd"`).
2. We create a 4×5 table (indices 0..3 and 0..4):

```
     ""   y    a    b    d
""   0    1    2    3    4
a    1    ?    ?    ?    ?
b    2    ?    ?    ?    ?
c    3    ?    ?    ?    ?
```

3. Fill row by row:

* For `i=1, j=1`: compare `'a'` vs `'y'` → different;

  ```
  insert  = dp[1][0] + 1 = 1 + 1 = 2
  delete  = dp[0][1] + 1 = 1 + 1 = 2
  replace = dp[0][0] + 1 = 0 + 1 = 1
  dp[1][1] = 1
  ```

  (That corresponds to “replace `'a'` → `'y'`”.)

* For `i=1, j=2`: compare `'a'` vs `'a'` → same; `dp[1][2] = dp[0][1] = 1`.

* And so on…

Filling the entire table yields:

```
     ""   y    a    b    d
""   0    1    2    3    4
 a   1    1    1    2    3
 b   2    2    2    1    2
 c   3    3    3    2    2
```

* Look at `dp[3][4]` (row `i=3`, column `j=4`) → `2`. That is the final edit distance.

---

## Time & Space Complexity

* **Time Complexity:**
  We fill an $(m + 1) \times (n + 1)$ table, and each cell takes $O(1)$ time to compute. So it is **O(m × n)**.

* **Space Complexity:**
  We store an integer table of size $(m + 1) \times (n + 1)$ → **O(m × n)**.
  (If needed, you can even reduce it to **O(min(m,n))** by only keeping two rows at a time, but the above straightforward version is easiest to understand.)

---

This bottom‐up DP approach is the standard way to compute Levenshtein (edit) distance efficiently—even for strings of length several thousands, it finishes quickly as long as $m \times n$ isn’t astronomically large.
