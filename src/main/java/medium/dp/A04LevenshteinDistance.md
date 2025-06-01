**Problem Statement**
Given two strings `s1` and `s2`, compute the **Levenshtein distance** (edit distance) between them—that is, the minimum number of single‐character insertions, deletions, or substitutions required to transform `s1` into `s2`.

---

## 1. Bottom-Up DP (Full 2D Table)

```java
package cools.dp.multidimensional;

public class EditDistanceBottomUp {
  /**
   * Time:  O(m × n)
   * Space: O(m × n)
   */
  public static int editDist(String s1, String s2) {
    int m = s1.length();
    int n = s2.length();
    int[][] dp = new int[m + 1][n + 1];

    // Base cases
    for (int i = 0; i <= m; i++) {
      dp[i][0] = i;       // delete all i chars from s1 to match ""
    }
    for (int j = 0; j <= n; j++) {
      dp[0][j] = j;       // insert all j chars into "" to match s2
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          int insertCost  = dp[i][j - 1] + 1;      // insert s2[j-1]
          int deleteCost  = dp[i - 1][j] + 1;      // delete s1[i-1]
          int replaceCost = dp[i - 1][j - 1] + 1;  // replace s1[i-1] with s2[j-1]
          dp[i][j] = Math.min(insertCost,
                       Math.min(deleteCost, replaceCost));
        }
      }
    }
    return dp[m][n];
  }

  public static void main(String[] args) {
    System.out.println(editDist("abc", "yabd"));            // 2
    System.out.println(editDist("GEEXSFRGEEKKS", "GEEKSFORGEEKS")); // 3
  }
}
```

* **Time Complexity:** O(m × n)
* **Space Complexity:** O(m × n)

---

## 2. Recursive + Memoization (Top-Down)

```java
package cools.dp.multidimensional;

import java.util.Arrays;

public class EditDistance {
  // Recursive function to calculate minimum edit distance
  public static int editDistRec(String s1, String s2, int m, int n, int[][] memo) {
    // Base Case: If the first string is empty, we need to insert all characters of s2
    if (m == 0) return n;

    // Base Case: If the second string is empty, we need to remove all characters of s1
    if (n == 0) return m;
    if (memo[m][n] != -1) return memo[m][n];

    // If the last characters of both strings match, no operation is needed
    if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
      return memo[m][n] = editDistRec(s1, s2, m - 1, n - 1, memo);
    }

    return memo[m][n] =
        1
            + Math.min(
                Math.min(
                    editDistRec(s1, s2, m, n - 1, memo), // Insert
                    editDistRec(s1, s2, m - 1, n, memo)), // Remove
                editDistRec(s1, s2, m - 1, n - 1, memo)); // Replace
  }

  // Wrapper function to initiate the recursive calculation for the entire strings
  public static int editDist(String s1, String s2) {
    // Initialize the memoization table with -1 (indicating uncomputed subproblems)
    int[][] memo = new int[s1.length() + 1][s2.length() + 1];
    for (int[] row : memo) {
      Arrays.fill(row, -1);
    }

    return editDistRec(s1, s2, s1.length(), s2.length(), memo);
  }

  // Main function to test the solution
  public static void main(String[] args) {
    String s1 = "GEEXSFRGEEKKS";
    String s2 = "GEEKSFORGEEKS";

    // Print the minimum edit distance
    System.out.println(editDist(s1, s2)); // Output: 3
  }


}
```

* **Time Complexity:** O(m × n) (each `(i, j)` computed once)
* **Space Complexity:** O(m × n) for `memo[][]` plus O(m + n) for the recursion stack

---

### Summary of Complexities

| Approach                              | Time     | Space                     |
| ------------------------------------- | -------- | ------------------------- |
| 1. Bottom-Up (full table)             | O(m × n) | O(m × n)                  |
| 2. Recursive + Memoization (top-down) | O(m × n) | O(m × n) + O(m + n) stack |

Choose whichever fits your constraints best:

* If clarity matters more than memory, use approach 1.
* If you need to handle very large strings without O(m × n) memory, use approach 2.
* If you prefer a recursive style, use approach 3.
