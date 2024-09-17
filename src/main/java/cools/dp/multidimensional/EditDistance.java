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

  /*
   * Time Complexity:
   * O(3^(m + n)) in the worst case, where m is the length of s1 and n is the length of s2.
   * This is because we are solving each subproblem by exploring three possibilities (insert, remove, replace),
   * leading to exponential time complexity.
   *
   * Space Complexity:
   * O(m + n) due to the recursion depth, as each recursive call uses stack space.
   */
}
