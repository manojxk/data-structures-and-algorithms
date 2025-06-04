Below is a guided walk-through of everything you need to know about the three related problems you showed—Longest Common Subsequence (LCS), Longest Common Substring, and how to actually retrieve (print) the LCS—along with the corresponding Java implementations. We’ll proceed piece by piece, assuming you’re a beginner, and explain:

1. **What each problem is asking for**
2. **Why a naïve (purely recursive) approach blows up**
3. **How to add memoization to make it O(m·n)**
4. **How to write a bottom-up (tabulation) solution**
5. **Finally, how to backtrack to “print” one actual LCS (not just its length).**

Throughout, we’ll refer to the code you posted (`A01LongestCommonSubsequence`, `A02LongestCommonSubstring`, and `A03PrintingLongestCommonSubsequence`) and explain each line in detail.

---

## 1. Longest Common Subsequence (LCS)

### 1.1. Problem Statement

> You are given two strings, `S1` and `S2`, of lengths `m` and `n` respectively.
> A **subsequence** of a string is any sequence you get by deleting zero or more characters without changing the relative order of the remaining characters.
>
> For example, the subsequences of `"ABC"` are:
>
> ```
>   "", "A", "B", "C", "AB", "AC", "BC", "ABC"
> ```
>
> The task is to find the **length** of the longest sequence that is a subsequence of *both* `S1` and `S2`.
> If there is no non-empty common subsequence, the answer is `0`.

#### Example

* `S1 = "ABCDEF"`, `S2 = "AEBDF"`.
  One longest common subsequence is `"ABDF"`, whose length is `4`.
  (There are other LCSes of length 4 as well, but `"ABDF"` is a classic.)

---

### 1.2. Purely Recursive Approach (Exponential)

A direct recursive definition of LCS goes as follows:

* Let `lcsRecursive(i, j)` = the length of the longest common subsequence between `S1[0..i-1]` and `S2[0..j-1]`.

Then:

1. **Base case**:
   If `i == 0 || j == 0`, one of the strings is empty → return `0`.
2. **If the last characters match** (`S1[i-1] == S2[j-1]`), then that matching character can be part of the LCS. So:

   ```
   lcsRecursive(i, j) = 1 + lcsRecursive(i-1, j-1)
   ```
3. **Otherwise** (they don’t match), we have two choices:

   * Discard `S1[i-1]` and match `S1[0..i-2]` versus `S2[0..j-1]` → `lcsRecursive(i-1, j)`
   * Discard `S2[j-1]` and match `S1[0..i-1]` versus `S2[0..j-2]` → `lcsRecursive(i, j-1)`
     We take the maximum of the two.

In code (pure recursive):

```java
public static int lcsRecursive(String S1, String S2, int m, int n) {
  // m = length of prefix of S1, n = length of prefix of S2
  if (m == 0 || n == 0) {
    return 0;
  }
  if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
    return 1 + lcsRecursive(S1, S2, m - 1, n - 1);
  } else {
    return Math.max(
      lcsRecursive(S1, S2, m - 1, n),
      lcsRecursive(S1, S2, m, n - 1)
    );
  }
}
```

* **Why is this O(2^(m+n))?**
  Whenever `S1[m-1] != S2[n-1]`, we branch into two calls (`(m-1,n)` and `(m,n-1)`), and those in turn can branch again, etc. In the worst case (no characters ever match), each call splits into two, resulting in exponentially many calls.

* **Space use** is O(m + n) for the recursion stack (in the worst case you go “down” by one character on either string each time).

---

### 1.3. Memoized (Top-Down) DP → O(m·n)

Observe that `lcsRecursive(i, j)` is asked over and over for the same `(i, j)` pairs. We can store the answer once it is computed and avoid recomputing it. That is memoization.

1. Create a 2D array `dp` of size `(m+1)×(n+1)`, initialized to `-1`.

   ```java
   static int[][] dp; // dp[i][j] = LCS length of S1[0..i-1], S2[0..j-1], or -1 if not computed
   ```

2. Every time we enter `lcsMemoization(m, n)`, we first check `if (dp[m][n] != -1) return dp[m][n];`.

3. Otherwise, we compute it exactly as before, store it in `dp[m][n]`, and return.

```java
public static int lcsMemoization(String S1, String S2, int m, int n) {
  // Base case
  if (m == 0 || n == 0) {
    return 0;
  }
  // If already computed, return it
  if (dp[m][n] != -1) {
    return dp[m][n];
  }
  // If last characters match
  if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
    dp[m][n] = 1 + lcsMemoization(S1, S2, m - 1, n - 1);
  } else {
    // Else take max of skipping one character from either string
    dp[m][n] = Math.max(
      lcsMemoization(S1, S2, m - 1, n),
      lcsMemoization(S1, S2, m, n - 1)
    );
  }
  return dp[m][n];
}
```

In your `main` you must do:

```java
String S1 = "ABCDEF";
String S2 = "AEBDF";
int m = S1.length(), n = S2.length();
// Initialize memo table
dp = new int[m + 1][n + 1];
for (int i = 0; i <= m; i++) {
  Arrays.fill(dp[i], -1);
}
System.out.println("Memoization LCS length: " + lcsMemoization(S1, S2, m, n)); // → 4
```

* **Time Complexity**: Every pair `(i,j)` with `0 ≤ i ≤ m`, `0 ≤ j ≤ n` is computed at most once, and the work inside each call is O(1). Hence O(m·n).
* **Space Complexity**: O(m·n) for the `dp` table, plus O(m + n) recursion depth.

---

### 1.4. Bottom-Up (Tabulation) DP → O(m·n)

Instead of top-down, we can build a table `dp[i][j]` directly from `i=0..m`, `j=0..n`, in increasing order. We define:

> `dp[i][j]` = length of LCS of `S1[0..i-1]` and `S2[0..j-1]`.

1. Create `dp` array of size `(m+1)×(n+1)` and fill all with `0` initially:

   ```java
   int[][] dp = new int[m+1][n+1];
   for (int i = 0; i <= m; i++) {
     dp[i][0] = 0;
   }
   for (int j = 0; j <= n; j++) {
     dp[0][j] = 0;
   }
   ```

   In fact, Java automatically does that, but it’s conceptually “the first row/column is 0.”

2. Then fill in row by row (or column by column). The rule is:

   * If `S1.charAt(i-1) == S2.charAt(j-1)`,

     ```
     dp[i][j] = dp[i-1][j-1] + 1;
     ```
   * Otherwise,

     ```
     dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
     ```

   This matches exactly the recursive relation.

3. The final answer is `dp[m][n]`.

```java
public static int lcsTabulation(String S1, String S2) {
  int m = S1.length();
  int n = S2.length();
  int[][] dp = new int[m+1][n+1];

  // We can skip “if (i==0||j==0) dp[i][j]=0” because Java inits to 0.
  for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        dp[i][j] = dp[i - 1][j - 1] + 1;
      } else {
        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
      }
    }
  }

  return dp[m][n];
}
```

* **Time Complexity**: Two nested loops up to `m, n` → O(m·n)
* **Space Complexity**: O(m·n) for the table (in practice you can reduce to O(min(m,n)) if you only need the length, but for printing the actual subsequence you keep the full table).

---

## 2. Longest Common Substring (instead of Subsequence)

### 2.1. Problem Statement

> A **substring** is a contiguous block of characters within the string.
> The task is to find the length of the longest string that occurs *as a contiguous substring* in both `S1` and `S2`.
> (If no common substring exists, the answer is 0.)

#### Example

* `S1 = "ABCDEF"`, `S2 = "AEBDF"`.
  The longest common **substring** is either `"BD"` or `"DF"`, both of length 2.
  (Notice `"AB...DF"` is not a substring because it’s not contiguous in `S2`.)

---

### 2.2. Purely Recursive Approach

A brute recursive way is tricky (and extremely expensive). You’d track something like:

```
lcsSubstrRecursive(s1, s2, i, j, countSoFar)
```

where `countSoFar` is how many consecutive matches you maintained before indices `i, j`. Anytime `s1[i-1] == s2[j-1]`, you can increment `countSoFar` and go to `(i-1, j-1)`. Otherwise, you reset `countSoFar` back to 0 and branch into `(i-1, j)` and `(i, j-1)`. That double branching → a truly exponential O(2^{m+n}) explosion. (We won’t dwell on the pure recursion.)

---

### 2.3. Bottom-Up DP for Longest Common Substring (Tabulation)

The standard DP for “longest common substring” (contiguous) uses a 2D table in which

> `dp[i][j]` = length of the longest common **suffix** of `S1[0..i-1]` and `S2[0..j-1]` that ends exactly at `(i-1)` and `(j-1)`.

Then:

1. If `S1[i-1] == S2[j-1]`,

   ```
   dp[i][j] = 1 + dp[i-1][j-1];
   ```

   (We can extend the common substring by 1 more character.)
2. Otherwise,

   ```
   dp[i][j] = 0;  
   ```

   (Because if the last characters differ, you cannot have any nonzero common suffix ending at `(i-1,j-1)`.)

As we fill the table, we keep track of the overall maximum value in `dp[i][j]`. That maximum is the length of the longest common substring.

```java
public static int lcsTabulation_substring(String S1, String S2) {
  int m = S1.length(), n = S2.length();
  int[][] dp = new int[m+1][n+1];
  int maxLen = 0;

  for (int i = 1; i <= m; i++) {
    for (int j = 1; j <= n; j++) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        dp[i][j] = dp[i - 1][j - 1] + 1;
        if (dp[i][j] > maxLen) {
          maxLen = dp[i][j];
        }
      } else {
        dp[i][j] = 0;
      }
    }
  }

  return maxLen;
}
```

* **Time Complexity**: O(m·n)
* **Space Complexity**: O(m·n)

*(Note: If you only need the length, you could do a rolling 1D array of size `n+1` and keep track of the previous row’s `dp[i-1][j-1]` in a temp variable, reducing to O(n) space. But as given, it’s the straightforward 2D.)*

---

## 3. Printing (Reconstructing) an Actual Longest Common Subsequence

So far, we’ve shown how to get **the length** of the LCS. Often you also want to know **which** subsequence (one of them) achieves that maximum. The trick is to build the same `dp[i][j]` table from the bottom-up approach, then backtrack from `dp[m][n]` to reconstruct one LCS.

### 3.1. Idea (Backtracking)

1. Build the `dp` array exactly as in the LCS tabulation:

   ```text
   dp[i][j] = if S1[i-1] == S2[j-1]:
                1 + dp[i-1][j-1]
              else:
                max(dp[i-1][j], dp[i][j-1])
   ```
2. After filling the table, start at `(i = m, j = n)`.
3. While `i > 0 && j > 0`,

   * If `S1[i-1] == S2[j-1]`, then that character **must** be part of some LCS. So prepend it to your result and move diagonally: `(i, j) ← (i-1, j-1)`.
   * Otherwise, compare `dp[i-1][j]` vs. `dp[i][j-1]`. Whichever is larger tells you which direction to go. If `dp[i-1][j] > dp[i][j-1]`, move to `(i-1, j)`; else move to `(i, j-1)`.
   * Ties can be broken arbitrarily (for instance, choose `(i-1, j)` or `(i, j-1)` if they’re equal).
4. Stop when either `i` or `j` reaches 0. By then, you’ve collected the LCS characters in reverse order, so reverse them before returning.

### 3.2. Code

```java
public class A03PrintingLongestCommonSubsequence {

  // Builds the dp table and then backtracks to print one LCS
  public static String lcsTabulation(String S1, String S2) {
    int m = S1.length(), n = S2.length();
    int[][] dp = new int[m + 1][n + 1];

    // 1) Fill the DP table for lengths
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // 2) Backtrack from dp[m][n]
    StringBuilder lcsBuilder = new StringBuilder();
    int i = m, j = n;
    while (i > 0 && j > 0) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        // Character i-1 = j-1 is part of an LCS
        lcsBuilder.append(S1.charAt(i - 1));
        i--;
        j--;
      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        // Move in the direction of the larger subproblem
        i--;
      } else {
        j--;
      }
    }

    // The LCS was built backward, so reverse it
    return lcsBuilder.reverse().toString();
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";

    // Print one actual LCS
    System.out.println("One LCS is: " + lcsTabulation(S1, S2)); 
    // Possible Output: "ABDF"
  }
}
```

* Notice that we never need recursion here—just a straightforward table + backtracking.
* The backtracking loop ends when `i == 0` or `j == 0`; by then we have traced all matching characters back to the front.

---

## 4. Putting It All Together

Below is a “complete” Java file that contains all three classes, each self-contained. You can copy and paste into your editor to experiment:

```java
package cools.dp.longestcommonsubsequence;

import java.util.Arrays;

public class A01LongestCommonSubsequence {

  // ---------- 1) Pure Recursion (Exponential) -------------
  // (Not used except as illustrative; DO NOT run on large inputs.)
  public static int lcsRecursive(String S1, String S2, int m, int n) {
    if (m == 0 || n == 0) {
      return 0;
    }
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      return 1 + lcsRecursive(S1, S2, m - 1, n - 1);
    } else {
      return Math.max(
        lcsRecursive(S1, S2, m - 1, n),
        lcsRecursive(S1, S2, m, n - 1)
      );
    }
  }

  static int[][] dp; // for memoization

  // ---------- 2) Top-Down with Memoization (O(m·n)) -------------
  public static int lcsMemoization(String S1, String S2, int m, int n) {
    if (m == 0 || n == 0) {
      return 0;
    }
    if (dp[m][n] != -1) {
      return dp[m][n];
    }
    if (S1.charAt(m - 1) == S2.charAt(n - 1)) {
      dp[m][n] = 1 + lcsMemoization(S1, S2, m - 1, n - 1);
    } else {
      dp[m][n] = Math.max(
        lcsMemoization(S1, S2, m - 1, n),
        lcsMemoization(S1, S2, m, n - 1)
      );
    }
    return dp[m][n];
  }

  // ---------- 3) Bottom-Up Tabulation (O(m·n)) -------------
  public static int lcsTabulation(String S1, String S2) {
    int m = S1.length();
    int n = S2.length();

    int[][] tbl = new int[m + 1][n + 1];
    // tbl[0][j] and tbl[i][0] are already zero by default

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          tbl[i][j] = tbl[i - 1][j - 1] + 1;
        } else {
          tbl[i][j] = Math.max(tbl[i - 1][j], tbl[i][j - 1]);
        }
      }
    }
    return tbl[m][n];
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";
    int m = S1.length(), n = S2.length();

    // Memoization approach:
    dp = new int[m + 1][n + 1];
    for (int[] row : dp) {
      Arrays.fill(row, -1);
    }
    System.out.println("Memoization LCS length: " +
      lcsMemoization(S1, S2, m, n)); // → 4

    // Tabulation approach:
    System.out.println("Tabulation LCS length: " + lcsTabulation(S1, S2)); // → 4
  }
}
```

```java
package cools.dp.longestcommonsubsequence;

public class A02LongestCommonSubstring {

  // ---------- 1) Bottom-Up (Tabulation) Only -------------
  public static int lcsTabulation(String S1, String S2) {
    int m = S1.length(), n = S2.length();
    int[][] tbl = new int[m + 1][n + 1];
    int maxLen = 0;

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          tbl[i][j] = tbl[i - 1][j - 1] + 1;
          if (tbl[i][j] > maxLen) {
            maxLen = tbl[i][j];
          }
        } else {
          tbl[i][j] = 0;
        }
      }
    }
    return maxLen;
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";
    System.out.println(
      "Longest Common Substring length: " + lcsTabulation(S1, S2)
    ); // → 2, (because “BD” or “DF” etc.)
  }
}
```

```java
package cools.dp.longestcommonsubsequence;

public class A03PrintingLongestCommonSubsequence {

  // ---------- 1) Bottom-Up Tabulation + Backtracking -------------
  public static String lcsTabulation(String S1, String S2) {
    int m = S1.length(), n = S2.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    // Backtrack to build one LCS string
    StringBuilder lcsBuilder = new StringBuilder();
    int i = m, j = n;
    while (i > 0 && j > 0) {
      if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
        lcsBuilder.append(S1.charAt(i - 1));
        i--;
        j--;
      } else if (dp[i - 1][j] > dp[i][j - 1]) {
        i--;
      } else {
        j--;
      }
    }

    return lcsBuilder.reverse().toString();
  }

  public static void main(String[] args) {
    String S1 = "ABCDEF";
    String S2 = "AEBDF";
    System.out.println("One LCS is: " + lcsTabulation(S1, S2));
    // → “ABDF” (or any valid LCS of length 4)
  }
}
```

---

## 5. Complexity Recap

* **Longest Common Subsequence, Recursion**

  * Time:  O(2^(m+n)) (exponential)
  * Space: O(m + n) (stack)

* **LCS with Memoization (Top-Down)**

  * Time:  O(m·n)
  * Space: O(m·n)
  * (for the memo table) + O(m+n) recursion depth

* **LCS with Tabulation (Bottom-Up)**

  * Time:  O(m·n)
  * Space: O(m·n)

* **Longest Common Substring (Bottom-Up)**

  * Time:  O(m·n)
  * Space: O(m·n)

* **Printing an LCS via Backtracking** (after building the same DP table)

  * Time: O(m·n) to build DP, then O(m+n) to backtrack
  * Space: O(m·n) for DP table

---

### Final Notes

* Whenever you see a “longest common subsequence” problem, think “two indices (i, j), reduce one or both → overlapping subproblems → create an m×n table.”
* Whenever you see “longest common substring (contiguous),” think “common suffix ending exactly here” → dp\[i]\[j] depends on dp\[i−1]\[j−1] + 1 if chars match, else `0`.
* To **print** (reconstruct) any DP solution, store enough information to trace “which choice was picked” (in LCS tabulation, you simply look at the `dp[i-1][j]` vs. `dp[i][j-1]` vs. `dp[i-1][j-1]` relationship).

I hope this step-by-step breakdown clarifies everything! Feel free to run, tinker, or ask follow-up questions on any part of the code or logic.
