package hard.strings;

/*
 Problem: Underscorify Substring

 Given a string and a substring, write a function that adds underscores before and after all occurrences of the substring in the string.
 If two or more occurrences of the substring overlap, the underscores should only appear once around the entire overlapping substring.

 Example:

 Input:
 String = "testthis is a testtest to see if testestest it works"
 Substring = "test"

 Output: " _test_this is a _testtest_ to see if _testestest_ it works"

 Explanation:
 The substring "test" appears in the string, and the goal is to surround each occurrence with underscores.
 When multiple occurrences overlap, they should be treated as a single occurrence with underscores only at the start and end of the group.
*/

/*
 Solution Steps:

 1. Find all occurrences of the substring in the main string and store their start and end indices.
 2. Merge overlapping occurrences into one continuous range.
 3. Insert underscores around the merged ranges.
 4. Return the new string with underscores.
*/

import java.util.ArrayList;
import java.util.List;

public class A02UnderscorifySubstring {

  // Function to underscorify all occurrences of the substring
  public static String underscorifySubstring(String str, String substring) {
    List<int[]> locations =
        getLocations(str, substring); // Step 1: Get all locations of the substring
    List<int[]> mergedLocations = mergeLocations(locations); // Step 2: Merge overlapping locations
    return addUnderscores(
        str, mergedLocations); // Step 3: Add underscores around the merged locations
  }

  // Step 1: Find all occurrences of the substring in the main string
  private static List<int[]> getLocations(String str, String substring) {
    List<int[]> locations = new ArrayList<>();
    int startIdx = 0;

    while (startIdx < str.length()) {
      int nextIdx = str.indexOf(substring, startIdx); // Find the next occurrence of the substring
      if (nextIdx == -1) break; // If no more occurrences, exit

      locations.add(
          new int[] {nextIdx, nextIdx + substring.length()}); // Store the start and end index
      startIdx = nextIdx + 1; // Move the start index forward
    }

    return locations;
  }

  // Step 2: Merge overlapping locations
  private static List<int[]> mergeLocations(List<int[]> locations) {
    if (locations.isEmpty()) return locations;

    List<int[]> mergedLocations = new ArrayList<>();
    int[] previous = locations.get(0); // Initialize with the first location

    for (int i = 1; i < locations.size(); i++) {
      int[] current = locations.get(i);

      // If the current location overlaps with the previous, merge them
      if (current[0] <= previous[1]) {
        previous[1] =
            Math.max(previous[1], current[1]); // Extend the previous range to include the current
      } else {
        mergedLocations.add(previous); // Add the previous range
        previous = current; // Move to the next location
      }
    }

    mergedLocations.add(previous); // Add the last merged range
    return mergedLocations;
  }

  // Step 3: Add underscores around the merged locations
  private static String addUnderscores(String str, List<int[]> locations) {
    StringBuilder result = new StringBuilder();
    int stringIdx = 0;
    int locationIdx = 0;

    while (stringIdx < str.length() && locationIdx < locations.size()) {
      int[] location = locations.get(locationIdx);

      // Add characters before the next underscored substring
      if (stringIdx < location[0]) {
        result.append(str.substring(stringIdx, location[0]));
      }

      // Add underscores before the substring
      result.append('_');
      result.append(str.substring(location[0], location[1]));
      result.append('_');

      // Move the string index to the end of the current underscored substring
      stringIdx = location[1];
      locationIdx++;
    }

    // Add any remaining characters after the last underscored substring
    if (stringIdx < str.length()) {
      result.append(str.substring(stringIdx));
    }

    return result.toString();
  }

  // Main function to run and test the solution
  public static void main(String[] args) {
    String str = "testthis is a testtest to see if testestest it works";
    String substring = "test";

    String result = underscorifySubstring(str, substring);
    System.out.println("Underscorified String: " + result);
    // Output: "_test_this is a _testtest_ to see if _testestest_ it works"
  }

  /*
   Time Complexity:
   - O(n + m), where n is the length of the main string and m is the length of the substring.
   - Finding all occurrences of the substring takes O(n) time. Merging locations and adding underscores also takes O(n) time.

   Space Complexity:
   - O(n), where n is the length of the main string. This is the space required for the result string and for storing locations.
  */
}
