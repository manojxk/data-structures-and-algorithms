package hard.stack;/*
 Problem: Shorten Path

 You are given a string that represents a file path in a Unix-like file system. Your task is to simplify the path,
 such that it removes unnecessary components like redundant slashes, current directory references (".")
 and parent directory references ("..").

 Absolute paths (start with a "/") should be shortened as much as possible, and relative paths (without a leading "/")
 should also be shortened. If the final path ends up being an empty string, return "/".

 Example 1:
 Input: "/foo/../test/../test/../foo//bar/./baz"
 Output: "/foo/bar/baz"

 Example 2:
 Input: "foo/bar/../bar/baz"
 Output: "foo/bar/baz"

 Example 3:
 Input: "/a/./b/../../c/"
 Output: "/c"

 Constraints:
 - The input path may contain multiple slashes.
 - The path will always be a valid path string.

 Solution Approach:
 1. Split the path by the "/" delimiter.
 2. Use a stack (deque) to simulate navigating through the path.
 3. Push directories onto the stack and pop them when encountering "..".
 4. Skip over "." and empty strings caused by multiple slashes.
 5. Finally, construct the simplified path by joining elements in the stack.

 Time Complexity: O(n), where n is the length of the path string.
 Space Complexity: O(n), due to the space used by the stack and the resulting path string.
*/

import java.util.*;

public class A01ShortenPath {

  // Function to shorten a given path
  public String simplifyPath(String path) {
    Deque<String> stack = new LinkedList<>();
    boolean isAbsolutePath = path.startsWith("/");

    // Split the path by "/"
    String[] tokens = path.split("/");

    for (String token : tokens) {
      if (token.equals(".") || token.isEmpty()) {
        continue; // Skip over current directory (.) and empty tokens (caused by //)
      } else if (token.equals("..")) {
        if (!stack.isEmpty() && !stack.peek().equals("..")) {
          stack.pop(); // Go up one directory
        } else if (!isAbsolutePath) {
          stack.push(".."); // For relative paths, add ".."
        }
      } else {
        stack.push(token); // Valid directory name
      }
    }

    // Convert stack to list, reverse, and join to form the final path
    List<String> result = new ArrayList<>(stack);
    Collections.reverse(result);
    String simplifiedPath = String.join("/", result);

    return isAbsolutePath ? "/" + simplifiedPath : simplifiedPath;
  }

  // Main function to test the solution
  public static void main(String[] args) {
    A01ShortenPath solution = new A01ShortenPath();

    // Example 1
    String path1 = "/foo/../test/../test/../foo//bar/./baz";
    System.out.println("Shortened path (Example 1): " + solution.simplifyPath(path1));  // Output: /foo/bar/baz

    // Example 2
    String path2 = "foo/bar/../bar/baz";
    System.out.println("Shortened path (Example 2): " + solution.simplifyPath(path2));  // Output: foo/bar/baz

    // Example 3
    String path3 = "/a/./b/../../c/";
    System.out.println("Shortened path (Example 3): " + solution.simplifyPath(path3));  // Output: /c
  }

    /*
     Time Complexity:
     - O(n), where n is the length of the input path string. We split the path and iterate through it once.

     Space Complexity:
     - O(n), due to the space used by the stack and the resulting shortened path string.
    */
}
