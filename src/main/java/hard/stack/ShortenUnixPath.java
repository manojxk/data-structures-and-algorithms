package hard.stack;

/**
 * Problem Statement:
 *
 * <p>You are given a non-empty string that represents a valid Unix-shell path. A path is a notation
 * representing the location of a file or directory in a file system. The task is to return a
 * shortened version of that path, ensuring that the shortened version points to the same file or
 * directory as the original.
 *
 * <p>The path may be absolute (starts with "/") or relative (doesn't start with "/"). The path
 * follows Unix file system rules, including special symbols: - "/" represents the root directory or
 * is used as a separator between directories. - ".." represents the parent directory. - "."
 * represents the current directory.
 *
 * <p>The function should remove unnecessary symbols (like ".", "..", and "//") to return the
 * canonical, simplified path.
 *
 * <p>Sample Input: path = "/foo/../test/../test/../foo//bar/./baz"
 *
 * <p>Sample Output: "/foo/bar/baz"
 */
import java.util.*;

public class ShortenUnixPath {

  // Optimized Approach using Stack:
  // Time Complexity: O(n), where n is the length of the input path.
  // Space Complexity: O(n), for storing the components in the stack.
  public static String shortenPathOptimized(String path) {
    Deque<String> stack = new LinkedList<>();
    boolean isAbsolutePath = path.startsWith("/");

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

    List<String> result = new ArrayList<>(stack);
    Collections.reverse(result);
    String simplifiedPath = String.join("/", result);

    return isAbsolutePath ? "/" + simplifiedPath : simplifiedPath;
  }

  // Main function to test both solutions
  public static void main(String[] args) {
    String path = "/foo/../test/../test/../foo//bar/./baz";

    // Optimized Solution
    String resultOptimized = shortenPathOptimized(path);
    System.out.println("Optimized Solution: " + resultOptimized);
  }
}
