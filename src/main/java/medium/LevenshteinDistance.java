/*
 * Problem Statement:
 * Write a function that takes in two strings and returns the minimum number of
 * edit operations that need to be performed on the first string to transform it into the second string.
 *
 * There are three types of edit operations:
 * 1. Insertion of a character
 * 2. Deletion of a character
 * 3. Substitution of a character for another
 *
 * Example:
 * str1 = "abc"
 * str2 = "yabd"
 *
 * Output:
 * 2  // Insert "y"; Substitute "c" for "d"
 */
/*Brute Force Solution
Approach:
The brute force approach involves trying all possible operations (insertions, deletions, substitutions) at each character of the strings. This can be done using recursion, where we explore all possible ways to transform str1 into str2 and return the minimum number of operations required.

Time Complexity:
O(3^(m+n)): Where m is the length of str1 and n is the length of str2. At each step, there are 3 possible operations (insertion, deletion, substitution).
Space Complexity:
O(m + n): The space complexity is due to the recursion stack, which can go as deep as the sum of the lengths of the two strings.*/

package medium;

public class LevenshteinDistance {

  // Brute Force Solution: Recursive Approach
  public static int minEditDistance(String str1, String str2) {
    /*    return computeDistance(str1, str2, str1.length(), str2.length());*/
    int m = str1.length(), n = str2.length();
    int[][] memo = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        memo[i][j] = -1;
      }
    }

    return computeDistanceMemo(str1, str2, str1.length(), str2.length(),memo);
  }

  private static int computeDistanceMemo(String str1, String str2, int m, int n, int[][] memo) {
    // Base cases
    if (m == 0) return n; // If str1 is empty, insert all characters of str2
    if (n == 0) return m; // If str2 is empty, delete all characters of str1
    if (memo[m][n] != -1) return memo[m][n];
    // If last characters are the same, ignore and recurse for the remaining
    if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
      return computeDistance(str1, str2, m - 1, n - 1);
    }

    // If last characters are different, consider all three operations
    int insert = computeDistance(str1, str2, m, n - 1); // Insert
    int delete = computeDistance(str1, str2, m - 1, n); // Delete
    int replace = computeDistance(str1, str2, m - 1, n - 1); // Replace

    // Return the minimum of the three operations plus one for the current operation
    return  memo[m][n] = 1 + Math.min(insert, Math.min(delete, replace));
  }

  // Helper method to compute minimum edit distance
  private static int computeDistance(String str1, String str2, int m, int n) {
    // Base cases
    if (m == 0) return n; // If str1 is empty, insert all characters of str2
    if (n == 0) return m; // If str2 is empty, delete all characters of str1

    // If last characters are the same, ignore and recurse for the remaining
    if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
      return computeDistance(str1, str2, m - 1, n - 1);
    }

    // If last characters are different, consider all three operations
    int insert = computeDistance(str1, str2, m, n - 1); // Insert
    int delete = computeDistance(str1, str2, m - 1, n); // Delete
    int replace = computeDistance(str1, str2, m - 1, n - 1); // Replace

    // Return the minimum of the three operations plus one for the current operation
    return 1 + Math.min(insert, Math.min(delete, replace));
  }

  // Optimized Solution: Dynamic Programming Approach
  public static int minEditDistanceDP(String str1, String str2) {
    int m = str1.length();
    int n = str2.length();

    // Create a dp array to store the minimum edit distances
    int[][] dp = new int[m + 1][n + 1];

    // Initialize base cases
    for (int i = 0; i <= m; i++) {
      dp[i][0] = i; // If str2 is empty, delete all characters of str1
    }
    for (int j = 0; j <= n; j++) {
      dp[0][j] = j; // If str1 is empty, insert all characters of str2
    }

    // Fill the dp array
    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1]; // Characters match, no new operation needed
        } else {
          int insert = dp[i][j - 1]; // Insert
          int delete = dp[i - 1][j]; // Delete
          int replace = dp[i - 1][j - 1]; // Replace

          dp[i][j] = 1 + Math.min(insert, Math.min(delete, replace)); // Take the minimum operation
        }
      }
    }

    return dp[m][n]; // The minimum edit distance for the entire strings
  }


  public static void main(String[] args) {
    // Sample Input
    String str1 = "abc";
    String str2 = "yabd";

    // Expected Output: 2 (Insert "y"; Substitute "c" for "d")
    System.out.println(minEditDistance(str1, str2)); // Output: 2
  }
}

/*Let's break down how each of these operations—insert, delete, and replace—works with a detailed example. We’ll use the dynamic programming approach to explain the recursive part.

Example:
Consider the strings str1 = "abc" and str2 = "yabd". We want to transform str1 into str2 with the minimum number of edit operations.

We will focus on the last characters of these strings:

str1: "abc" (last character is 'c')
str2: "yabd" (last character is 'd')
We need to compare the last characters 'c' and 'd'. Since they are different, we have three choices:

Insert: Insert the last character of str2 into str1.
Delete: Remove the last character of str1.
Replace: Replace the last character of str1 with the last character of str2.
Here’s how we compute each operation:

        1. Insert
To insert the last character of str2 into str1, we keep str1 as it is and try to match str2 excluding its last character. Essentially, this means we look at the minimum edit distance required to convert str1 into str2 excluding the last character of str2.

java
Copy code
int insert = computeDistance(str1, str2, m, n - 1);
In our case:

Insert: Compute the distance between "abc" and "yab".
We are now looking to make str1 = "abc" match str2 = "yab", which means we need to consider "abc" and "yab".
        2. Delete
To delete the last character of str1, we keep str2 as it is and try to match str1 excluding its last character. This means we look at the minimum edit distance required to convert str1 excluding its last character into str2.

        java
Copy code
int delete = computeDistance(str1, str2, m - 1, n);
In our case:

Delete: Compute the distance between "ab" and "yabd".
We are now looking to make str1 = "ab" match str2 = "yabd", which means we need to consider "ab" and "yabd".
        3. Replace
To replace the last character of str1 with the last character of str2, we perform the replacement and then find the edit distance for the remaining parts of the strings. This means we look at the minimum edit distance required to convert str1 excluding its last character into str2 excluding its last character.

        java
Copy code
int replace = computeDistance(str1, str2, m - 1, n - 1);
In our case:

Replace: Compute the distance between "ab" and "yab".
We are now looking to make str1 = "ab" match str2 = "yab", which means we need to consider "ab" and "yab".
Putting It All Together
Here’s how you would use these operations:

Insert: To match "abc" with "yabd", you would compute the minimum edit distance required to transform "abc" into "yab", and then add 1 (for the insertion of 'd').

Delete: To match "abc" with "yabd", you would compute the minimum edit distance required to transform "ab" into "yabd", and then add 1 (for the deletion of 'c').

Replace: To match "abc" with "yabd", you would compute the minimum edit distance required to transform "ab" into "yab", and then add 1 (for the replacement of 'c' with 'd').

You choose the operation with the minimum number of edits and add 1 to account for the current operation.

Example Calculation:
Let's say we computed the following distances:

Insert: The distance to convert "abc" to "yab" is 2, so total = 2 (insert 'd') = 3.
Delete: The distance to convert "ab" to "yabd" is 3, so total = 3 (delete 'c') = 4.
Replace: The distance to convert "ab" to "yab" is 1, so total = 1 (replace 'c' with 'd') = 2.
The minimum edit distance from "abc" to "yabd" is 2, which is achieved by replacing 'c' with 'd' and inserting 'y'.

By applying this approach recursively, you build up the minimum edit distance for larger substrings and ultimately solve the problem efficiently.*/

/*

class Solution {
  public:

  int minDistancedp(string s1, string s2, int m, int n)
  {
    int dp[m+1][n+1];
    for(int i = 0; i <= m; i++){
      for(int j = 0; j <= n; j++){
        if(i==0){
          dp[i][j] = j;
        }
        else if(j == 0){
          dp[i][j] = i;
        }
        else if(s1[i-1] == s2[j-1]){
          dp[i][j] = dp[i-1][j-1];
        }
        else{
          dp[i][j] = 1 + min(dp[i][j-1],min(dp[i-1][j-1],dp[i-1][j]));
        }
      }
    }
    return dp[m][n];

  }
  int minDistance(string word1, string word2) {
    return minDistancedp(word1,word2, word1.size(), word2.size());

  }
};*/
