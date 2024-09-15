package hard.strings;

/*
 Problem: Pattern Matcher

 Given a "pattern" and a string "str," determine if the string follows the same pattern.
 You can think of the pattern as a string composed of only the characters 'x' and 'y'.
 For example, a pattern of "xyxy" and a string "gogopowerrangergogopowerranger" would match since 'x' corresponds to "gogo" and 'y' corresponds to "powerranger."

 Example:

 Input:
 Pattern: "xxyxxy"
 String: "gogopowerrangergogopowerranger"

 Output: ["gogo", "powerranger"]

 Explanation:
 The pattern "xxyxxy" maps 'x' to "gogo" and 'y' to "powerranger". The string follows the pattern.
*/

/*
 Solution Steps:

 1. Count the occurrences of 'x' and 'y' in the pattern.
 2. Determine which string segment corresponds to 'x' and which corresponds to 'y'.
 3. Try all possible lengths of 'x' and find corresponding 'y' segments.
 4. Validate that the entire string matches the pattern.
 5. If a match is found, return the segments for 'x' and 'y'.
*/

import java.util.Arrays;

public class PatternMatcher {

  // Function to match the pattern and return the corresponding x and y strings
  public static String[] patternMatcher(String pattern, String str) {
    if (pattern.length() > str.length()) return new String[0];

    // Normalize pattern so that 'x' is the most frequent character
    String newPattern = normalizePattern(pattern);
    boolean didSwitch = newPattern.charAt(0) != pattern.charAt(0);

    // Count the occurrences of 'x' and 'y' in the pattern
    int[] counts = countXandY(newPattern);
    int countOfX = counts[0];
    int countOfY = counts[1];

    // Try different lengths for 'x' and calculate the corresponding length for 'y'
    for (int lenOfX = 1; lenOfX * countOfX <= str.length(); lenOfX++) {
      int lenOfY = (str.length() - (lenOfX * countOfX)) / (countOfY == 0 ? 1 : countOfY);

      if (lenOfX * countOfX + lenOfY * countOfY != str.length()) {
        continue;
      }

      // Get the potential 'x' and 'y' substrings
      String potentialX = str.substring(0, lenOfX);
      String potentialY =
          countOfY == 0
              ? ""
              : str.substring(
                  lenOfX * newPattern.indexOf('y'), lenOfX * newPattern.indexOf('y') + lenOfY);

      // Rebuild the string using the potential 'x' and 'y' to validate the match
      String rebuiltStr = buildFromPattern(newPattern, potentialX, potentialY);

      if (rebuiltStr.equals(str)) {
        return didSwitch
            ? new String[] {potentialY, potentialX}
            : new String[] {potentialX, potentialY};
      }
    }

    return new String[0];
  }

  // Step 1: Normalize pattern by ensuring 'x' is the most frequent character
  private static String normalizePattern(String pattern) {
    if (pattern.charAt(0) == 'x') return pattern;
    StringBuilder newPattern = new StringBuilder();
    for (char c : pattern.toCharArray()) {
      newPattern.append(c == 'x' ? 'y' : 'x');
    }
    return newPattern.toString();
  }

  // Step 2: Count the occurrences of 'x' and 'y' in the pattern
  private static int[] countXandY(String pattern) {
    int countOfX = 0;
    int countOfY = 0;
    for (char c : pattern.toCharArray()) {
      if (c == 'x') countOfX++;
      else countOfY++;
    }
    return new int[] {countOfX, countOfY};
  }

  // Step 3: Build the string using the pattern and potential 'x' and 'y' substrings
  private static String buildFromPattern(String pattern, String x, String y) {
    StringBuilder result = new StringBuilder();
    for (char c : pattern.toCharArray()) {
      if (c == 'x') result.append(x);
      else result.append(y);
    }
    return result.toString();
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    String pattern = "xxyxxy";
    String str = "gogopowerrangergogopowerranger";

    String[] result = patternMatcher(pattern, str);
    System.out.println("Result: " + Arrays.toString(result));
    // Output: ["gogo", "powerranger"]
  }

  /*
   Time Complexity:
   - O(n^2), where n is the length of the string. For each potential length of 'x', we check the string and construct the result to see if it matches the pattern.

   Space Complexity:
   - O(n), where n is the length of the string. This is the space used to store the resulting substring and intermediate variables.
  */
}
