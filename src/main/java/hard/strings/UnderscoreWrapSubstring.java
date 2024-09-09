package hard.strings;

/**
 * Problem Statement:
 *
 * <p>You are given two strings: a main string and a potential substring of the main string. The
 * task is to return a version of the main string where every instance of the substring is wrapped
 * in underscores ('_').
 *
 * <p>If two or more instances of the substring in the main string overlap or are adjacent, you
 * should only place underscores on the far left of the leftmost substring and on the far right of
 * the rightmost substring.
 *
 * <p>If the main string doesn't contain the substring, return the main string unchanged.
 *
 * <p>Sample Input: string = "testthis is a testtest to see if testestest it works" substring =
 * "test"
 *
 * <p>Sample Output: "_test_this is a _testtest_ to see if _testestest_ it works"
 */
import java.util.*;

public class UnderscoreWrapSubstring {

  // Function to wrap every instance of the substring with underscores
  public static String underscorifySubstring(String string, String substring) {
    List<int[]> locations = getLocations(string, substring);
    return underscorify(string, locations);
  }

  // Helper function to find all locations of the substring within the main string
  private static List<int[]> getLocations(String string, String substring) {
    List<int[]> locations = new ArrayList<>();
    int startIdx = 0;

    while (startIdx < string.length()) {
      int nextIdx =
          string.indexOf(substring, startIdx); // Find the next occurrence of the substring
      if (nextIdx == -1) {
        break; // No more substrings found
      }
      locations.add(
          new int[] {
            nextIdx, nextIdx + substring.length()
          }); // Add the start and end index of the substring
      startIdx = nextIdx + 1; // Move to the next index to search
    }

    return locations;
  }

  // Helper function to add underscores around the substrings based on the locations found
  private static String underscorify(String string, List<int[]> locations) {
    if (locations.isEmpty()) {
      return string; // No substrings found, return the original string
    }

    List<int[]> mergedLocations = mergeLocations(locations);
    StringBuilder result = new StringBuilder();
    int stringIdx = 0;
    int locIdx = 0;

    while (stringIdx < string.length() && locIdx < mergedLocations.size()) {
      if (stringIdx == mergedLocations.get(locIdx)[0]) {
        result.append('_'); // Add underscore at the start of the substring
      }
      result.append(string.charAt(stringIdx));
      if (stringIdx == mergedLocations.get(locIdx)[1] - 1) {
        result.append('_'); // Add underscore at the end of the substring
        locIdx++;
      }
      stringIdx++;
    }

    // Add any remaining characters after the last substring
    while (stringIdx < string.length()) {
      result.append(string.charAt(stringIdx));
      stringIdx++;
    }

    return result.toString();
  }

  // Helper function to merge overlapping or adjacent locations
  private static List<int[]> mergeLocations(List<int[]> locations) {
    if (locations.isEmpty()) {
      return locations;
    }

    List<int[]> merged = new ArrayList<>();
    merged.add(locations.get(0));
    int[] prev = merged.get(0);

    for (int i = 1; i < locations.size(); i++) {
      int[] curr = locations.get(i);
      if (curr[0] <= prev[1]) {
        prev[1] = Math.max(prev[1], curr[1]); // Merge overlapping or adjacent locations
      } else {
        merged.add(curr);
        prev = curr;
      }
    }

    return merged;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    String string = "testthis is a testtest to see if testestest it works";
    String substring = "test";

    String result = underscorifySubstring(string, substring);
    System.out.println(
        result); // Output: "_test_this is a _testtest_ to see if _testestest_ it works"
  }
}
