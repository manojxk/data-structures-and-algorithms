package hard.strings;

import java.util.*;

public class PatternMatcher {

  public static List<String> patternMatcher(String pattern, String str) {
    if (pattern.length() > str.length()) {
      return new ArrayList<>(); // Pattern is longer than the string, so no match is possible
    }

    // Normalize the pattern so that it starts with 'x'
    String normalizedPattern = getNormalizedPattern(pattern);
    boolean didSwitch = normalizedPattern.charAt(0) != pattern.charAt(0);

    // Count the number of 'x's and 'y's in the pattern
    int countX = countOccurrences(normalizedPattern, 'x');
    int countY =
        normalizedPattern.length() - countX; // Total pattern length minus 'x' count gives 'y' count

    // Try different lengths for the substrings representing 'x' and 'y'
    for (int lenX = 0; lenX <= str.length(); lenX++) {
      int remainingLength = str.length() - countX * lenX;
      if (countY == 0 || remainingLength % countY == 0) {
        int lenY = countY == 0 ? 0 : remainingLength / countY;
        String x = "";
        String y = "";
        int idx = 0;

        boolean isValid = true;
        for (char ch : normalizedPattern.toCharArray()) {
          if (ch == 'x') {
            String subX = str.substring(idx, idx + lenX);
            if (x.isEmpty()) {
              x = subX;
            } else if (!x.equals(subX)) {
              isValid = false;
              break;
            }
            idx += lenX;
          } else {
            String subY = str.substring(idx, idx + lenY);
            if (y.isEmpty()) {
              y = subY;
            } else if (!y.equals(subY)) {
              isValid = false;
              break;
            }
            idx += lenY;
          }
        }

        if (isValid) {
          if (didSwitch) {
            return Arrays.asList(y, x);
          } else {
            return Arrays.asList(x, y);
          }
        }
      }
    }

    return new ArrayList<>(); // No valid match found
  }

  // Function to normalize the pattern so that it starts with 'x'
  private static String getNormalizedPattern(String pattern) {
    if (pattern.charAt(0) == 'x') {
      return pattern;
    }

    StringBuilder normalized = new StringBuilder();
    for (char ch : pattern.toCharArray()) {
      normalized.append(ch == 'x' ? 'y' : 'x');
    }
    return normalized.toString();
  }

  // Function to count occurrences of 'ch' in a string
  private static int countOccurrences(String pattern, char ch) {
    int count = 0;
    for (char c : pattern.toCharArray()) {
      if (c == ch) {
        count++;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    String pattern = "xxyxxy";
    String str = "gogopowerrangergogopowerranger";

    List<String> result = patternMatcher(pattern, str);
    System.out.println(result); // Output: ["go", "powerranger"]
  }
}
