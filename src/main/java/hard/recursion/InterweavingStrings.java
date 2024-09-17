package hard.recursion;

/*
 Problem: Interweaving Strings

 Given three strings `one`, `two`, and `three`, write a function that determines whether `three` is formed
 by interleaving `one` and `two`.

 `three` is said to be interleaving `one` and `two` if it contains all characters of `one` and `two` exactly once,
 while preserving the order of characters within `one` and `two`.

 Example:

 Input:
   one = "abc"
   two = "def"
   three = "adbcef"

 Output: true

 Explanation: The string "adbcef" can be formed by interleaving "abc" and "def".

 Another Example:

 Input:
   one = "abc"
   two = "def"
   three = "abdecf"

 Output: false

 Explanation: The string "abdecf" cannot be formed by interleaving "abc" and "def" since the character "e" comes
 before "c", which violates the order of characters in "abc".
*/

/*
 Solution Steps:

 1. Use a recursive approach to check if we can build the string `three` by interweaving characters from `one` and `two`.
 2. For each character in `three`, check if it can be matched with the current character from either `one` or `two`.
 3. If it matches the current character from `one`, move forward in `one` and `three`. If it matches the current character from `two`, move forward in `two` and `three`.
 4. Use dynamic programming (memoization) to store intermediate results and avoid recomputation.

 5. The base case is that when we reach the end of both `one` and `two`, we should also reach the end of `three`.
*/

public class InterweavingStrings {

  // Helper function to determine if three is an interweaving of one and two using memoization
  public boolean isInterleave(String one, String two, String three) {
    // If the total length of the interweaving string does not match, return false immediately
    if (one.length() + two.length() != three.length()) return false;

    // Initialize a memoization table to store intermediate results
    Boolean[][] memo = new Boolean[one.length() + 1][two.length() + 1];

    return isInterleaveHelper(one, two, three, 0, 0, memo);
  }

  // Recursive helper function with memoization
  private boolean isInterleaveHelper(
      String one, String two, String three, int i, int j, Boolean[][] memo) {
    // Base case: If we have reached the end of all strings, return true
    if (i == one.length() && j == two.length()) {
      return true;
    }

    // Check the memoization table to avoid re-computation
    if (memo[i][j] != null) {
      return memo[i][j];
    }

    // Determine the current position in the string `three`
    int k = i + j;

    // Option 1: If the current character in `one` matches the current character in `three`
    if (i < one.length() && one.charAt(i) == three.charAt(k)) {
      if (isInterleaveHelper(one, two, three, i + 1, j, memo)) {
        memo[i][j] = true;
        return true;
      }
    }

    // Option 2: If the current character in `two` matches the current character in `three`
    if (j < two.length() && two.charAt(j) == three.charAt(k)) {
      if (isInterleaveHelper(one, two, three, i, j + 1, memo)) {
        memo[i][j] = true;
        return true;
      }
    }

    // If neither option is true, memoize the result as false
    memo[i][j] = false;
    return false;
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    InterweavingStrings solution = new InterweavingStrings();

    // Test case 1
    String one = "abc";
    String two = "def";
    String three = "adbcef";
    System.out.println(
        "Is interleaving: " + solution.isInterleave(one, two, three)); // Output: true

    // Test case 2
    String one2 = "abc";
    String two2 = "def";
    String three2 = "abdecf";
    System.out.println(
        "Is interleaving: " + solution.isInterleave(one2, two2, three2)); // Output: false
  }

  /*
   Time Complexity:
   - O(m * n), where m is the length of string `one` and n is the length of string `two`. We compute each subproblem once and store the result in the memoization table.

   Space Complexity:
   - O(m * n), where m is the length of string `one` and n is the length of string `two`, for the memoization table. The recursion stack space is also O(m + n) due to the depth of the recursion.
  */
}
