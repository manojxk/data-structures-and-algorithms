package hard.recursion;

import java.util.ArrayList;
import java.util.List;

public class GenerateDivTags {

  // Main function to generate all valid strings of matched div tags
  public static List<String> generateDivTags(int numberOfTags) {
    List<String> result = new ArrayList<>();
    // Call the backtracking helper function to generate the strings
    generateTags(numberOfTags, numberOfTags, "", result);
    return result;
  }

  // Helper function for backtracking
  private static void generateTags(int open, int close, String current, List<String> result) {
    // Base case: if no more open or close tags left to add, we have a valid string
    if (open == 0 && close == 0) {
      result.add(current);
      return;
    }

    // Add an opening tag if there are still opening tags left
    if (open > 0) {
      generateTags(open - 1, close, current + "<div>", result);
    }

    // Add a closing tag if the number of closing tags left is greater than the number of opening
    // tags used
    if (close > open) {
      generateTags(open, close - 1, current + "</div>", result);
    }
  }

  // Main function to test the solution
  public static void main(String[] args) {
    int numberOfTags = 3;
    List<String> result = generateDivTags(numberOfTags);

    // Print the result
    System.out.println("Generated div tags:");
    for (String s : result) {
      System.out.println(s);
    }
  }
}
